package io.reactivex.rxjava3.parallel;

import io.reactivex.rxjava3.functions.BiFunction;

/* loaded from: classes5.dex */
public enum ParallelFailureHandling implements BiFunction<Long, Throwable, ParallelFailureHandling> {
    STOP,
    ERROR,
    SKIP,
    RETRY;

    public ParallelFailureHandling apply(Long l, Throwable th) {
        return this;
    }
}
