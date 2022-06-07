package io.reactivex.rxjava3.parallel;

import io.reactivex.rxjava3.annotations.NonNull;

@FunctionalInterface
/* loaded from: classes5.dex */
public interface ParallelFlowableConverter<T, R> {
    @NonNull
    R apply(@NonNull ParallelFlowable<T> parallelFlowable);
}
