package io.reactivex;

import io.reactivex.annotations.NonNull;

/* loaded from: classes4.dex */
public interface FlowableConverter<T, R> {
    @NonNull
    R apply(@NonNull Flowable<T> flowable);
}
