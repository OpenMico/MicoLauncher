package io.reactivex.internal.schedulers;

import io.reactivex.Scheduler;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableContainer;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.disposables.SequentialDisposable;
import io.reactivex.internal.functions.Functions;
import io.reactivex.internal.queue.MpscLinkedQueue;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.SchedulerRunnableIntrospection;
import io.reactivex.schedulers.Schedulers;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes4.dex */
public final class ExecutorScheduler extends Scheduler {
    static final Scheduler e = Schedulers.single();
    final boolean c;
    @NonNull
    final Executor d;

    public ExecutorScheduler(@NonNull Executor executor, boolean z) {
        this.d = executor;
        this.c = z;
    }

    @Override // io.reactivex.Scheduler
    @NonNull
    public Scheduler.Worker createWorker() {
        return new ExecutorWorker(this.d, this.c);
    }

    @Override // io.reactivex.Scheduler
    @NonNull
    public Disposable scheduleDirect(@NonNull Runnable runnable) {
        Runnable onSchedule = RxJavaPlugins.onSchedule(runnable);
        try {
            if (this.d instanceof ExecutorService) {
                ScheduledDirectTask scheduledDirectTask = new ScheduledDirectTask(onSchedule);
                scheduledDirectTask.setFuture(((ExecutorService) this.d).submit(scheduledDirectTask));
                return scheduledDirectTask;
            } else if (this.c) {
                ExecutorWorker.b bVar = new ExecutorWorker.b(onSchedule, null);
                this.d.execute(bVar);
                return bVar;
            } else {
                ExecutorWorker.a aVar = new ExecutorWorker.a(onSchedule);
                this.d.execute(aVar);
                return aVar;
            }
        } catch (RejectedExecutionException e2) {
            RxJavaPlugins.onError(e2);
            return EmptyDisposable.INSTANCE;
        }
    }

    @Override // io.reactivex.Scheduler
    @NonNull
    public Disposable scheduleDirect(@NonNull Runnable runnable, long j, TimeUnit timeUnit) {
        Runnable onSchedule = RxJavaPlugins.onSchedule(runnable);
        if (this.d instanceof ScheduledExecutorService) {
            try {
                ScheduledDirectTask scheduledDirectTask = new ScheduledDirectTask(onSchedule);
                scheduledDirectTask.setFuture(((ScheduledExecutorService) this.d).schedule(scheduledDirectTask, j, timeUnit));
                return scheduledDirectTask;
            } catch (RejectedExecutionException e2) {
                RxJavaPlugins.onError(e2);
                return EmptyDisposable.INSTANCE;
            }
        } else {
            b bVar = new b(onSchedule);
            bVar.timed.replace(e.scheduleDirect(new a(bVar), j, timeUnit));
            return bVar;
        }
    }

    @Override // io.reactivex.Scheduler
    @NonNull
    public Disposable schedulePeriodicallyDirect(@NonNull Runnable runnable, long j, long j2, TimeUnit timeUnit) {
        if (!(this.d instanceof ScheduledExecutorService)) {
            return super.schedulePeriodicallyDirect(runnable, j, j2, timeUnit);
        }
        try {
            ScheduledDirectPeriodicTask scheduledDirectPeriodicTask = new ScheduledDirectPeriodicTask(RxJavaPlugins.onSchedule(runnable));
            scheduledDirectPeriodicTask.setFuture(((ScheduledExecutorService) this.d).scheduleAtFixedRate(scheduledDirectPeriodicTask, j, j2, timeUnit));
            return scheduledDirectPeriodicTask;
        } catch (RejectedExecutionException e2) {
            RxJavaPlugins.onError(e2);
            return EmptyDisposable.INSTANCE;
        }
    }

    /* loaded from: classes4.dex */
    public static final class ExecutorWorker extends Scheduler.Worker implements Runnable {
        final boolean a;
        final Executor b;
        volatile boolean d;
        final AtomicInteger e = new AtomicInteger();
        final CompositeDisposable f = new CompositeDisposable();
        final MpscLinkedQueue<Runnable> c = new MpscLinkedQueue<>();

        public ExecutorWorker(Executor executor, boolean z) {
            this.b = executor;
            this.a = z;
        }

        @Override // io.reactivex.Scheduler.Worker
        @NonNull
        public Disposable schedule(@NonNull Runnable runnable) {
            Disposable disposable;
            if (this.d) {
                return EmptyDisposable.INSTANCE;
            }
            Runnable onSchedule = RxJavaPlugins.onSchedule(runnable);
            if (this.a) {
                disposable = new b(onSchedule, this.f);
                this.f.add(disposable);
            } else {
                disposable = new a(onSchedule);
            }
            this.c.offer(disposable);
            if (this.e.getAndIncrement() == 0) {
                try {
                    this.b.execute(this);
                } catch (RejectedExecutionException e) {
                    this.d = true;
                    this.c.clear();
                    RxJavaPlugins.onError(e);
                    return EmptyDisposable.INSTANCE;
                }
            }
            return disposable;
        }

        @Override // io.reactivex.Scheduler.Worker
        @NonNull
        public Disposable schedule(@NonNull Runnable runnable, long j, @NonNull TimeUnit timeUnit) {
            if (j <= 0) {
                return schedule(runnable);
            }
            if (this.d) {
                return EmptyDisposable.INSTANCE;
            }
            SequentialDisposable sequentialDisposable = new SequentialDisposable();
            SequentialDisposable sequentialDisposable2 = new SequentialDisposable(sequentialDisposable);
            ScheduledRunnable scheduledRunnable = new ScheduledRunnable(new c(sequentialDisposable2, RxJavaPlugins.onSchedule(runnable)), this.f);
            this.f.add(scheduledRunnable);
            Executor executor = this.b;
            if (executor instanceof ScheduledExecutorService) {
                try {
                    scheduledRunnable.setFuture(((ScheduledExecutorService) executor).schedule((Callable) scheduledRunnable, j, timeUnit));
                } catch (RejectedExecutionException e) {
                    this.d = true;
                    RxJavaPlugins.onError(e);
                    return EmptyDisposable.INSTANCE;
                }
            } else {
                scheduledRunnable.setFuture(new b(ExecutorScheduler.e.scheduleDirect(scheduledRunnable, j, timeUnit)));
            }
            sequentialDisposable.replace(scheduledRunnable);
            return sequentialDisposable2;
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            if (!this.d) {
                this.d = true;
                this.f.dispose();
                if (this.e.getAndIncrement() == 0) {
                    this.c.clear();
                }
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.d;
        }

        /* JADX WARN: Code restructure failed: missing block: B:10:0x0015, code lost:
            if (r3.d == false) goto L_0x001b;
         */
        /* JADX WARN: Code restructure failed: missing block: B:11:0x0017, code lost:
            r0.clear();
         */
        /* JADX WARN: Code restructure failed: missing block: B:12:0x001a, code lost:
            return;
         */
        /* JADX WARN: Code restructure failed: missing block: B:13:0x001b, code lost:
            r1 = r3.e.addAndGet(-r1);
         */
        /* JADX WARN: Code restructure failed: missing block: B:14:0x0022, code lost:
            if (r1 != 0) goto L_0x0003;
         */
        /* JADX WARN: Code restructure failed: missing block: B:15:0x0024, code lost:
            return;
         */
        @Override // java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void run() {
            /*
                r3 = this;
                io.reactivex.internal.queue.MpscLinkedQueue<java.lang.Runnable> r0 = r3.c
                r1 = 1
            L_0x0003:
                boolean r2 = r3.d
                if (r2 == 0) goto L_0x000b
                r0.clear()
                return
            L_0x000b:
                java.lang.Object r2 = r0.poll()
                java.lang.Runnable r2 = (java.lang.Runnable) r2
                if (r2 != 0) goto L_0x0025
                boolean r2 = r3.d
                if (r2 == 0) goto L_0x001b
                r0.clear()
                return
            L_0x001b:
                java.util.concurrent.atomic.AtomicInteger r2 = r3.e
                int r1 = -r1
                int r1 = r2.addAndGet(r1)
                if (r1 != 0) goto L_0x0003
                return
            L_0x0025:
                r2.run()
                boolean r2 = r3.d
                if (r2 == 0) goto L_0x000b
                r0.clear()
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: io.reactivex.internal.schedulers.ExecutorScheduler.ExecutorWorker.run():void");
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* loaded from: classes4.dex */
        public static final class a extends AtomicBoolean implements Disposable, Runnable {
            private static final long serialVersionUID = -2421395018820541164L;
            final Runnable actual;

            a(Runnable runnable) {
                this.actual = runnable;
            }

            @Override // java.lang.Runnable
            public void run() {
                if (!get()) {
                    try {
                        this.actual.run();
                    } finally {
                        lazySet(true);
                    }
                }
            }

            @Override // io.reactivex.disposables.Disposable
            public void dispose() {
                lazySet(true);
            }

            @Override // io.reactivex.disposables.Disposable
            public boolean isDisposed() {
                return get();
            }
        }

        /* loaded from: classes4.dex */
        final class c implements Runnable {
            private final SequentialDisposable b;
            private final Runnable c;

            c(SequentialDisposable sequentialDisposable, Runnable runnable) {
                this.b = sequentialDisposable;
                this.c = runnable;
            }

            @Override // java.lang.Runnable
            public void run() {
                this.b.replace(ExecutorWorker.this.schedule(this.c));
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* loaded from: classes4.dex */
        public static final class b extends AtomicInteger implements Disposable, Runnable {
            private static final long serialVersionUID = -3603436687413320876L;
            final Runnable run;
            final DisposableContainer tasks;
            volatile Thread thread;

            b(Runnable runnable, DisposableContainer disposableContainer) {
                this.run = runnable;
                this.tasks = disposableContainer;
            }

            @Override // java.lang.Runnable
            public void run() {
                if (get() == 0) {
                    this.thread = Thread.currentThread();
                    if (compareAndSet(0, 1)) {
                        try {
                            this.run.run();
                            this.thread = null;
                            if (compareAndSet(1, 2)) {
                                a();
                                return;
                            }
                            while (get() == 3) {
                                Thread.yield();
                            }
                            Thread.interrupted();
                        } catch (Throwable th) {
                            this.thread = null;
                            if (!compareAndSet(1, 2)) {
                                while (get() == 3) {
                                    Thread.yield();
                                }
                                Thread.interrupted();
                            } else {
                                a();
                            }
                            throw th;
                        }
                    } else {
                        this.thread = null;
                    }
                }
            }

            @Override // io.reactivex.disposables.Disposable
            public void dispose() {
                while (true) {
                    int i = get();
                    if (i < 2) {
                        if (i == 0) {
                            if (compareAndSet(0, 4)) {
                                a();
                                return;
                            }
                        } else if (compareAndSet(1, 3)) {
                            Thread thread = this.thread;
                            if (thread != null) {
                                thread.interrupt();
                                this.thread = null;
                            }
                            set(4);
                            a();
                            return;
                        }
                    } else {
                        return;
                    }
                }
            }

            void a() {
                DisposableContainer disposableContainer = this.tasks;
                if (disposableContainer != null) {
                    disposableContainer.delete(this);
                }
            }

            @Override // io.reactivex.disposables.Disposable
            public boolean isDisposed() {
                return get() >= 2;
            }
        }
    }

    /* loaded from: classes4.dex */
    static final class b extends AtomicReference<Runnable> implements Disposable, SchedulerRunnableIntrospection, Runnable {
        private static final long serialVersionUID = -4101336210206799084L;
        final SequentialDisposable timed = new SequentialDisposable();
        final SequentialDisposable direct = new SequentialDisposable();

        b(Runnable runnable) {
            super(runnable);
        }

        @Override // java.lang.Runnable
        public void run() {
            Runnable runnable = get();
            if (runnable != null) {
                try {
                    runnable.run();
                } finally {
                    lazySet(null);
                    this.timed.lazySet(DisposableHelper.DISPOSED);
                    this.direct.lazySet(DisposableHelper.DISPOSED);
                }
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return get() == null;
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            if (getAndSet(null) != null) {
                this.timed.dispose();
                this.direct.dispose();
            }
        }

        @Override // io.reactivex.schedulers.SchedulerRunnableIntrospection
        public Runnable getWrappedRunnable() {
            Runnable runnable = get();
            return runnable != null ? runnable : Functions.EMPTY_RUNNABLE;
        }
    }

    /* loaded from: classes4.dex */
    final class a implements Runnable {
        private final b b;

        a(b bVar) {
            this.b = bVar;
        }

        @Override // java.lang.Runnable
        public void run() {
            this.b.direct.replace(ExecutorScheduler.this.scheduleDirect(this.b));
        }
    }
}
