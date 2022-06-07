package io.reactivex.parallel;

import io.reactivex.annotations.NonNull;

/* loaded from: classes4.dex */
public interface ParallelFlowableConverter<T, R> {
    @NonNull
    R apply(@NonNull ParallelFlowable<T> parallelFlowable);
}
