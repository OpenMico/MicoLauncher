package io.reactivex.rxjava3.internal.schedulers;

import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.concurrent.Callable;

/* loaded from: classes5.dex */
public final class ScheduledDirectTask extends a implements Callable<Void> {
    private static final long serialVersionUID = 1811839108042568751L;

    @Override // io.reactivex.rxjava3.internal.schedulers.a, io.reactivex.rxjava3.schedulers.SchedulerRunnableIntrospection
    public /* bridge */ /* synthetic */ Runnable getWrappedRunnable() {
        return super.getWrappedRunnable();
    }

    public ScheduledDirectTask(Runnable runnable) {
        super(runnable);
    }

    @Override // java.util.concurrent.Callable
    public Void call() {
        this.runner = Thread.currentThread();
        try {
            this.runnable.run();
            lazySet(FINISHED);
            this.runner = null;
            return null;
        } catch (Throwable th) {
            RxJavaPlugins.onError(th);
            throw th;
        }
    }
}
