package io.reactivex.internal.schedulers;

import java.util.concurrent.Callable;

/* loaded from: classes4.dex */
public final class ScheduledDirectTask extends a implements Callable<Void> {
    private static final long serialVersionUID = 1811839108042568751L;

    @Override // io.reactivex.internal.schedulers.a, io.reactivex.schedulers.SchedulerRunnableIntrospection
    public /* bridge */ /* synthetic */ Runnable getWrappedRunnable() {
        return super.getWrappedRunnable();
    }

    public ScheduledDirectTask(Runnable runnable) {
        super(runnable);
    }

    @Override // java.util.concurrent.Callable
    public Void call() throws Exception {
        this.runner = Thread.currentThread();
        try {
            this.runnable.run();
            return null;
        } finally {
            lazySet(FINISHED);
            this.runner = null;
        }
    }
}
