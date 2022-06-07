package io.reactivex.schedulers;

import io.reactivex.annotations.NonNull;

/* loaded from: classes5.dex */
public interface SchedulerRunnableIntrospection {
    @NonNull
    Runnable getWrappedRunnable();
}
