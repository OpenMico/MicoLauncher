package io.reactivex.rxjava3.internal.schedulers;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.annotations.Nullable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.disposables.DisposableContainer;
import io.reactivex.rxjava3.internal.disposables.EmptyDisposable;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/* loaded from: classes5.dex */
public class NewThreadWorker extends Scheduler.Worker implements Disposable {
    private final ScheduledExecutorService a;
    volatile boolean b;

    public NewThreadWorker(ThreadFactory threadFactory) {
        this.a = SchedulerPoolFactory.create(threadFactory);
    }

    @Override // io.reactivex.rxjava3.core.Scheduler.Worker
    @NonNull
    public Disposable schedule(@NonNull Runnable runnable) {
        return schedule(runnable, 0L, null);
    }

    @Override // io.reactivex.rxjava3.core.Scheduler.Worker
    @NonNull
    public Disposable schedule(@NonNull Runnable runnable, long j, @NonNull TimeUnit timeUnit) {
        if (this.b) {
            return EmptyDisposable.INSTANCE;
        }
        return scheduleActual(runnable, j, timeUnit, null);
    }

    public Disposable scheduleDirect(Runnable runnable, long j, TimeUnit timeUnit) {
        Future<?> future;
        ScheduledDirectTask scheduledDirectTask = new ScheduledDirectTask(RxJavaPlugins.onSchedule(runnable));
        try {
            if (j <= 0) {
                future = this.a.submit(scheduledDirectTask);
            } else {
                future = this.a.schedule(scheduledDirectTask, j, timeUnit);
            }
            scheduledDirectTask.setFuture(future);
            return scheduledDirectTask;
        } catch (RejectedExecutionException e) {
            RxJavaPlugins.onError(e);
            return EmptyDisposable.INSTANCE;
        }
    }

    public Disposable schedulePeriodicallyDirect(Runnable runnable, long j, long j2, TimeUnit timeUnit) {
        Future<?> future;
        Runnable onSchedule = RxJavaPlugins.onSchedule(runnable);
        if (j2 <= 0) {
            c cVar = new c(onSchedule, this.a);
            try {
                if (j <= 0) {
                    future = this.a.submit(cVar);
                } else {
                    future = this.a.schedule(cVar, j, timeUnit);
                }
                cVar.a(future);
                return cVar;
            } catch (RejectedExecutionException e) {
                RxJavaPlugins.onError(e);
                return EmptyDisposable.INSTANCE;
            }
        } else {
            ScheduledDirectPeriodicTask scheduledDirectPeriodicTask = new ScheduledDirectPeriodicTask(onSchedule);
            try {
                scheduledDirectPeriodicTask.setFuture(this.a.scheduleAtFixedRate(scheduledDirectPeriodicTask, j, j2, timeUnit));
                return scheduledDirectPeriodicTask;
            } catch (RejectedExecutionException e2) {
                RxJavaPlugins.onError(e2);
                return EmptyDisposable.INSTANCE;
            }
        }
    }

    @NonNull
    public ScheduledRunnable scheduleActual(Runnable runnable, long j, @NonNull TimeUnit timeUnit, @Nullable DisposableContainer disposableContainer) {
        Future<?> future;
        ScheduledRunnable scheduledRunnable = new ScheduledRunnable(RxJavaPlugins.onSchedule(runnable), disposableContainer);
        if (disposableContainer != null && !disposableContainer.add(scheduledRunnable)) {
            return scheduledRunnable;
        }
        try {
            if (j <= 0) {
                future = this.a.submit((Callable) scheduledRunnable);
            } else {
                future = this.a.schedule((Callable) scheduledRunnable, j, timeUnit);
            }
            scheduledRunnable.setFuture(future);
        } catch (RejectedExecutionException e) {
            if (disposableContainer != null) {
                disposableContainer.remove(scheduledRunnable);
            }
            RxJavaPlugins.onError(e);
        }
        return scheduledRunnable;
    }

    @Override // io.reactivex.rxjava3.disposables.Disposable
    public void dispose() {
        if (!this.b) {
            this.b = true;
            this.a.shutdownNow();
        }
    }

    public void shutdown() {
        if (!this.b) {
            this.b = true;
            this.a.shutdown();
        }
    }

    @Override // io.reactivex.rxjava3.disposables.Disposable
    public boolean isDisposed() {
        return this.b;
    }
}
