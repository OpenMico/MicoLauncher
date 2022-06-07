package io.reactivex.rxjava3.core;

import io.reactivex.rxjava3.annotations.NonNull;

@FunctionalInterface
/* loaded from: classes4.dex */
public interface ObservableConverter<T, R> {
    R apply(@NonNull Observable<T> observable);
}
