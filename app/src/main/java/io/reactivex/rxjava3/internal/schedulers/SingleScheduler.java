package io.reactivex.rxjava3.internal.schedulers;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.disposables.EmptyDisposable;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes5.dex */
public final class SingleScheduler extends Scheduler {
    final ThreadFactory b;
    final AtomicReference<ScheduledExecutorService> c;
    static final ScheduledExecutorService e = Executors.newScheduledThreadPool(0);
    static final RxThreadFactory d = new RxThreadFactory("RxSingleScheduler", Math.max(1, Math.min(10, Integer.getInteger("rx3.single-priority", 5).intValue())), true);

    static {
        e.shutdown();
    }

    public SingleScheduler() {
        this(d);
    }

    public SingleScheduler(ThreadFactory threadFactory) {
        this.c = new AtomicReference<>();
        this.b = threadFactory;
        this.c.lazySet(a(threadFactory));
    }

    static ScheduledExecutorService a(ThreadFactory threadFactory) {
        return SchedulerPoolFactory.create(threadFactory);
    }

    @Override // io.reactivex.rxjava3.core.Scheduler
    public void start() {
        ScheduledExecutorService scheduledExecutorService;
        ScheduledExecutorService scheduledExecutorService2 = null;
        do {
            scheduledExecutorService = this.c.get();
            if (scheduledExecutorService != e) {
                if (scheduledExecutorService2 != null) {
                    scheduledExecutorService2.shutdown();
                    return;
                }
                return;
            } else if (scheduledExecutorService2 == null) {
                scheduledExecutorService2 = a(this.b);
            }
        } while (!this.c.compareAndSet(scheduledExecutorService, scheduledExecutorService2));
    }

    @Override // io.reactivex.rxjava3.core.Scheduler
    public void shutdown() {
        ScheduledExecutorService andSet = this.c.getAndSet(e);
        if (andSet != e) {
            andSet.shutdownNow();
        }
    }

    @Override // io.reactivex.rxjava3.core.Scheduler
    @NonNull
    public Scheduler.Worker createWorker() {
        return new a(this.c.get());
    }

    @Override // io.reactivex.rxjava3.core.Scheduler
    @NonNull
    public Disposable scheduleDirect(@NonNull Runnable runnable, long j, TimeUnit timeUnit) {
        Future<?> future;
        ScheduledDirectTask scheduledDirectTask = new ScheduledDirectTask(RxJavaPlugins.onSchedule(runnable));
        try {
            if (j <= 0) {
                future = this.c.get().submit(scheduledDirectTask);
            } else {
                future = this.c.get().schedule(scheduledDirectTask, j, timeUnit);
            }
            scheduledDirectTask.setFuture(future);
            return scheduledDirectTask;
        } catch (RejectedExecutionException e2) {
            RxJavaPlugins.onError(e2);
            return EmptyDisposable.INSTANCE;
        }
    }

    @Override // io.reactivex.rxjava3.core.Scheduler
    @NonNull
    public Disposable schedulePeriodicallyDirect(@NonNull Runnable runnable, long j, long j2, TimeUnit timeUnit) {
        Future<?> future;
        Runnable onSchedule = RxJavaPlugins.onSchedule(runnable);
        if (j2 <= 0) {
            ScheduledExecutorService scheduledExecutorService = this.c.get();
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
                scheduledDirectPeriodicTask.setFuture(this.c.get().scheduleAtFixedRate(scheduledDirectPeriodicTask, j, j2, timeUnit));
                return scheduledDirectPeriodicTask;
            } catch (RejectedExecutionException e3) {
                RxJavaPlugins.onError(e3);
                return EmptyDisposable.INSTANCE;
            }
        }
    }

    /* loaded from: classes5.dex */
    static final class a extends Scheduler.Worker {
        final ScheduledExecutorService a;
        final CompositeDisposable b = new CompositeDisposable();
        volatile boolean c;

        a(ScheduledExecutorService scheduledExecutorService) {
            this.a = scheduledExecutorService;
        }

        @Override // io.reactivex.rxjava3.core.Scheduler.Worker
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

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            if (!this.c) {
                this.c = true;
                this.b.dispose();
            }
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.c;
        }
    }
}
