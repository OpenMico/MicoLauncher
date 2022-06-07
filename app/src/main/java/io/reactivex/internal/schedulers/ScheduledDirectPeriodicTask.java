package io.reactivex.internal.schedulers;

import io.reactivex.plugins.RxJavaPlugins;

/* loaded from: classes4.dex */
public final class ScheduledDirectPeriodicTask extends a implements Runnable {
    private static final long serialVersionUID = 1811839108042568751L;

    @Override // io.reactivex.internal.schedulers.a, io.reactivex.schedulers.SchedulerRunnableIntrospection
    public /* bridge */ /* synthetic */ Runnable getWrappedRunnable() {
        return super.getWrappedRunnable();
    }

    public ScheduledDirectPeriodicTask(Runnable runnable) {
        super(runnable);
    }

    @Override // java.lang.Runnable
    public void run() {
        this.runner = Thread.currentThread();
        try {
            this.runnable.run();
            this.runner = null;
        } catch (Throwable th) {
            this.runner = null;
            lazySet(FINISHED);
            RxJavaPlugins.onError(th);
        }
    }
}
