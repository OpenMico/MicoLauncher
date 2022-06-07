package io.reactivex.internal.schedulers;

import io.reactivex.Scheduler;
import io.reactivex.annotations.NonNull;

/* loaded from: classes4.dex */
public interface SchedulerMultiWorkerSupport {

    /* loaded from: classes4.dex */
    public interface WorkerCallback {
        void onWorker(int i, @NonNull Scheduler.Worker worker);
    }

    void createWorkers(int i, @NonNull WorkerCallback workerCallback);
}
