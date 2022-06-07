package io.reactivex.internal.schedulers;

import io.reactivex.Scheduler;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes4.dex */
public final class SingleScheduler extends Scheduler {
    final ThreadFactory c;
    final AtomicReference<ScheduledExecutorService> d;
    static final ScheduledExecutorService f = Executors.newScheduledThreadPool(0);
    static final RxThreadFactory e = new RxThreadFactory("RxSingleScheduler", Math.max(1, Math.min(10, Integer.getInteger("rx2.single-priority", 5).intValue())), true);

    static {
        f.shutdown();
    }

    public SingleScheduler() {
        this(e);
    }

    public SingleScheduler(ThreadFactory threadFactory) {
        this.d = new AtomicReference<>();
        this.c = threadFactory;
        this.d.lazySet(a(threadFactory));
    }

    static ScheduledExecutorService a(ThreadFactory threadFactory) {
        return SchedulerPoolFactory.create(threadFactory);
    }

    @Override // io.reactivex.Scheduler
    public void start() {
        ScheduledExecutorService scheduledExecutorService;
        ScheduledExecutorService scheduledExecutorService2 = null;
        do {
            scheduledExecutorService = this.d.get();
            if (scheduledExecutorService != f) {
                if (scheduledExecutorService2 != null) {
                    scheduledExecutorService2.shutdown();
                    return;
                }
                return;
            } else if (scheduledExecutorService2 == null) {
                scheduledExecutorService2 = a(this.c);
            }
        } while (!this.d.compareAndSet(scheduledExecutorService, scheduledExecutorService2));
    }

    @Override // io.reactivex.Scheduler
    public void shutdown() {
        ScheduledExecutorService andSet;
        ScheduledExecutorService scheduledExecutorService = this.d.get();
        ScheduledExecutorService scheduledExecutorService2 = f;
        if (scheduledExecutorService != scheduledExecutorService2 && (andSet = this.d.getAndSet(scheduledExecutorService2)) != f) {
            andSet.shutdownNow();
        }
    }

    @Override // io.reactivex.Scheduler
    @NonNull
    public Scheduler.Worker createWorker() {
        return new a(this.d.get());
    }

    @Override // io.reactivex.Scheduler
    @NonNull
    public Disposable scheduleDirect(@NonNull Runnable runnable, long j, TimeUnit timeUnit) {
        Future<?> future;
        ScheduledDirectTask scheduledDirectTask = new ScheduledDirectTask(RxJavaPlugins.onSchedule(runnable));
        try {
            if (j <= 0) {
                future = this.d.get().submit(scheduledDirectTask);
            } else {
                future = this.d.get().schedule(scheduledDirectTask, j, timeUnit);
            }
            scheduledDirectTask.setFuture(future);
            return scheduledDirectTask;
        } catch (RejectedExecutionException e2) {
            RxJavaPlugins.onError(e2);
            return EmptyDisposable.INSTANCE;
        }
    }

    @Override // io.reactivex.Scheduler
    @NonNull
    public Disposable schedulePeriodicallyDirect(@NonNull Runnable runnable, long j, long j2, TimeUnit timeUnit) {
        Future<?> future;
        Runnable onSchedule = RxJavaPlugins.onSchedule(runnable);
        if (j2 <= 0) {
            ScheduledExecutorService scheduledExecutorService = this.d.get();
            c cVar = new c(onSchedule, scheduledExecutorService);
            try {
                if (j <= 0) {
                    future = scheduledExecutorService.submit(cVar);
                } else {
                    future = scheduledExecutorService.schedule(cVar, j, timeUnit);
                }
                cVar.a(future);
                return cVar;
            } catch (RejectedExecutionException e2) {
                RxJavaPlugins.onError(e2);
                return EmptyDisposable.INSTANCE;
            }
        } else {
            ScheduledDirectPeriodicTask scheduledDirectPeriodicTask = new ScheduledDirectPeriodicTask(onSchedule);
            try {
                scheduledDirectPeriodicTask.setFuture(this.d.get().scheduleAtFixedRate(scheduledDirectPeriodicTask, j, j2, timeUnit));
                return scheduledDirectPeriodicTask;
            } catch (RejectedExecutionException e3) {
                RxJavaPlugins.onError(e3);
                return EmptyDisposable.INSTANCE;
            }
        }
    }

    /* loaded from: classes4.dex */
    static final class a extends Scheduler.Worker {
        final ScheduledExecutorService a;
        final CompositeDisposable b = new CompositeDisposable();
        volatile boolean c;

        a(ScheduledExecutorService scheduledExecutorService) {
            this.a = scheduledExecutorService;
        }

        @Override // io.reactivex.Scheduler.Worker
        @NonNull
        public Disposable schedule(@NonNull Runnable runnable, long j, @NonNull TimeUnit timeUnit) {
            Future<?> future;
            if (this.c) {
                return EmptyDisposable.INSTANCE;
            }
            ScheduledRunnable scheduledRunnable = new ScheduledRunnable(RxJavaPlugins.onSchedule(runnable), this.b);
            this.b.add(scheduledRunnable);
            try {
                if (j <= 0) {
                    future = this.a.submit((Callable) scheduledRunnable);
                } else {
                    future = this.a.schedule((Callable) scheduledRunnable, j, timeUnit);
                }
                scheduledRunnable.setFuture(future);
                return scheduledRunnable;
            } catch (RejectedExecutionException e) {
                dispose();
                RxJavaPlugins.onError(e);
                return EmptyDisposable.INSTANCE;
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            if (!this.c) {
                this.c = true;
                this.b.dispose();
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.c;
        }
    }
}
