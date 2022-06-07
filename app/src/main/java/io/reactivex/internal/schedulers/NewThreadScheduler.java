package io.reactivex.internal.schedulers;

import io.reactivex.Scheduler;
import io.reactivex.annotations.NonNull;
import java.util.concurrent.ThreadFactory;

/* loaded from: classes4.dex */
public final class NewThreadScheduler extends Scheduler {
    private static final RxThreadFactory d = new RxThreadFactory("RxNewThreadScheduler", Math.max(1, Math.min(10, Integer.getInteger("rx2.newthread-priority", 5).intValue())));
    final ThreadFactory c;

    public NewThreadScheduler() {
        this(d);
    }

    public NewThreadScheduler(ThreadFactory threadFactory) {
        this.c = threadFactory;
    }

    @Override // io.reactivex.Scheduler
    @NonNull
    public Scheduler.Worker createWorker() {
        return new NewThreadWorker(this.c);
    }
}
