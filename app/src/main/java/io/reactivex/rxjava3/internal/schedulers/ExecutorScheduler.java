package io.reactivex.rxjava3.internal.schedulers;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.disposables.DisposableContainer;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.internal.disposables.EmptyDisposable;
import io.reactivex.rxjava3.internal.disposables.SequentialDisposable;
import io.reactivex.rxjava3.internal.functions.Functions;
import io.reactivex.rxjava3.internal.queue.MpscLinkedQueue;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import io.reactivex.rxjava3.schedulers.SchedulerRunnableIntrospection;
import io.reactivex.rxjava3.schedulers.Schedulers;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes5.dex */
public final class ExecutorScheduler extends Scheduler {
    static final Scheduler e = Schedulers.single();
    final boolean b;
    final boolean c;
    @NonNull
    final Executor d;

    public ExecutorScheduler(@NonNull Executor executor, boolean z, boolean z2) {
        this.d = executor;
        this.b = z;
        this.c = z2;
    }

    @Override // io.reactivex.rxjava3.core.Scheduler
    @NonNull
    public Scheduler.Worker createWorker() {
        return new ExecutorWorker(this.d, this.b, this.c);
    }

    @Override // io.reactivex.rxjava3.core.Scheduler
    @NonNull
    public Disposable scheduleDirect(@NonNull Runnable runnable) {
        Runnable onSchedule = RxJavaPlugins.onSchedule(runnable);
        try {
            if (this.d instanceof ExecutorService) {
                ScheduledDirectTask scheduledDirectTask = new ScheduledDirectTask(onSchedule);
                scheduledDirectTask.setFuture(((ExecutorService) this.d).submit(scheduledDirectTask));
                return scheduledDirectTask;
            } else if (this.b) {
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

    @Override // io.reactivex.rxjava3.core.Scheduler
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

    @Override // io.reactivex.rxjava3.core.Scheduler
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

    /* loaded from: classes5.dex */
    public static final class ExecutorWorker extends Scheduler.Worker implements Runnable {
        final boolean a;
        final boolean b;
        final Executor c;
        volatile boolean e;
        final AtomicInteger f = new AtomicInteger();
        final CompositeDisposable g = new CompositeDisposable();
        final MpscLinkedQueue<Runnable> d = new MpscLinkedQueue<>();

        public ExecutorWorker(Executor executor, boolean z, boolean z2) {
            this.c = executor;
            this.a = z;
            this.b = z2;
        }

        @Override // io.reactivex.rxjava3.core.Scheduler.Worker
        @NonNull
        public Disposable schedule(@NonNull Runnable runnable) {
            Disposable disposable;
            if (this.e) {
                return EmptyDisposable.INSTANCE;
            }
            Runnable onSchedule = RxJavaPlugins.onSchedule(runnable);
            if (this.a) {
                disposable = new b(onSchedule, this.g);
                this.g.add(disposable);
            } else {
                disposable = new a(onSchedule);
            }
            this.d.offer(disposable);
            if (this.f.getAndIncrement() == 0) {
                try {
                    this.c.execute(this);
                } catch (RejectedExecutionException e) {
                    this.e = true;
                    this.d.clear();
                    RxJavaPlugins.onError(e);
                    return EmptyDisposable.INSTANCE;
                }
            }
            return disposable;
        }

        @Override // io.reactivex.rxjava3.core.Scheduler.Worker
        @NonNull
        public Disposable schedule(@NonNull Runnable runnable, long j, @NonNull TimeUnit timeUnit) {
            if (j <= 0) {
                return schedule(runnable);
            }
            if (this.e) {
                return EmptyDisposable.INSTANCE;
            }
            SequentialDisposable sequentialDisposable = new SequentialDisposable();
            SequentialDisposable sequentialDisposable2 = new SequentialDisposable(sequentialDisposable);
            ScheduledRunnable scheduledRunnable = new ScheduledRunnable(new c(sequentialDisposable2, RxJavaPlugins.onSchedule(runnable)), this.g);
            this.g.add(scheduledRunnable);
            Executor executor = this.c;
            if (executor instanceof ScheduledExecutorService) {
                try {
                    scheduledRunnable.setFuture(((ScheduledExecutorService) executor).schedule((Callable) scheduledRunnable, j, timeUnit));
                } catch (RejectedExecutionException e) {
                    this.e = true;
                    RxJavaPlugins.onError(e);
                    return EmptyDisposable.INSTANCE;
                }
            } else {
                scheduledRunnable.setFuture(new b(ExecutorScheduler.e.scheduleDirect(scheduledRunnable, j, timeUnit)));
            }
            sequentialDisposable.replace(scheduledRunnable);
            return sequentialDisposable2;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            if (!this.e) {
                this.e = true;
                this.g.dispose();
                if (this.f.getAndIncrement() == 0) {
                    this.d.clear();
                }
            }
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.e;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (this.b) {
                a();
            } else {
                b();
            }
        }

        void a() {
            MpscLinkedQueue<Runnable> mpscLinkedQueue = this.d;
            if (this.e) {
                mpscLinkedQueue.clear();
                return;
            }
            mpscLinkedQueue.poll().run();
            if (this.e) {
                mpscLinkedQueue.clear();
            } else if (this.f.decrementAndGet() != 0) {
                this.c.execute(this);
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:10:0x0015, code lost:
            if (r3.e == false) goto L_0x001b;
         */
        /* JADX WARN: Code restructure failed: missing block: B:11:0x0017, code lost:
            r0.clear();
         */
        /* JADX WARN: Code restructure failed: missing block: B:12:0x001a, code lost:
            return;
         */
        /* JADX WARN: Code restructure failed: missing block: B:13:0x001b, code lost:
            r1 = r3.f.addAndGet(-r1);
         */
        /* JADX WARN: Code restructure failed: missing block: B:14:0x0022, code lost:
            if (r1 != 0) goto L_0x0003;
         */
        /* JADX WARN: Code restructure failed: missing block: B:15:0x0024, code lost:
            return;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        void b() {
            /*
                r3 = this;
                io.reactivex.rxjava3.internal.queue.MpscLinkedQueue<java.lang.Runnable> r0 = r3.d
                r1 = 1
            L_0x0003:
                boolean r2 = r3.e
                if (r2 == 0) goto L_0x000b
                r0.clear()
                return
            L_0x000b:
                java.lang.Object r2 = r0.poll()
                java.lang.Runnable r2 = (java.lang.Runnable) r2
                if (r2 != 0) goto L_0x0025
                boolean r2 = r3.e
                if (r2 == 0) goto L_0x001b
                r0.clear()
                return
            L_0x001b:
                java.util.concurrent.atomic.AtomicInteger r2 = r3.f
                int r1 = -r1
                int r1 = r2.addAndGet(r1)
                if (r1 != 0) goto L_0x0003
                return
            L_0x0025:
                r2.run()
                boolean r2 = r3.e
                if (r2 == 0) goto L_0x000b
                r0.clear()
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: io.reactivex.rxjava3.internal.schedulers.ExecutorScheduler.ExecutorWorker.b():void");
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* loaded from: classes5.dex */
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

            @Override // io.reactivex.rxjava3.disposables.Disposable
            public void dispose() {
                lazySet(true);
            }

            @Override // io.reactivex.rxjava3.disposables.Disposable
            public boolean isDisposed() {
                return get();
            }
        }

        /* loaded from: classes5.dex */
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
        /* loaded from: classes5.dex */
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

            @Override // io.reactivex.rxjava3.disposables.Disposable
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

            @Override // io.reactivex.rxjava3.disposables.Disposable
            public boolean isDisposed() {
                return get() >= 2;
            }
        }
    }

    /* loaded from: classes5.dex */
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
                    lazySet(null);
                    this.timed.lazySet(DisposableHelper.DISPOSED);
                    this.direct.lazySet(DisposableHelper.DISPOSED);
                } catch (Throwable th) {
                    RxJavaPlugins.onError(th);
                    throw th;
                }
            }
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return get() == null;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            if (getAndSet(null) != null) {
                this.timed.dispose();
                this.direct.dispose();
            }
        }

        @Override // io.reactivex.rxjava3.schedulers.SchedulerRunnableIntrospection
        public Runnable getWrappedRunnable() {
            Runnable runnable = get();
            return runnable != null ? runnable : Functions.EMPTY_RUNNABLE;
        }
    }

    /* loaded from: classes5.dex */
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
