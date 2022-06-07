package io.reactivex.rxjava3.internal.schedulers;

import io.reactivex.rxjava3.plugins.RxJavaPlugins;

/* loaded from: classes5.dex */
public final class ScheduledDirectPeriodicTask extends a implements Runnable {
    private static final long serialVersionUID = 1811839108042568751L;

    @Override // io.reactivex.rxjava3.internal.schedulers.a, io.reactivex.rxjava3.schedulers.SchedulerRunnableIntrospection
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
            dispose();
            RxJavaPlugins.onError(th);
            throw th;
        }
    }
}
