package io.reactivex.rxjava3.internal.schedulers;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Scheduler;
import java.util.concurrent.ThreadFactory;

/* loaded from: classes5.dex */
public final class NewThreadScheduler extends Scheduler {
    private static final RxThreadFactory c = new RxThreadFactory("RxNewThreadScheduler", Math.max(1, Math.min(10, Integer.getInteger("rx3.newthread-priority", 5).intValue())));
    final ThreadFactory b;

    public NewThreadScheduler() {
        this(c);
    }

    public NewThreadScheduler(ThreadFactory threadFactory) {
        this.b = threadFactory;
    }

    @Override // io.reactivex.rxjava3.core.Scheduler
    @NonNull
    public Scheduler.Worker createWorker() {
        return new NewThreadWorker(this.b);
    }
}
