package io.reactivex.rxjava3.core;

import io.reactivex.rxjava3.annotations.NonNull;

@FunctionalInterface
/* loaded from: classes4.dex */
public interface ObservableOnSubscribe<T> {
    void subscribe(@NonNull ObservableEmitter<T> observableEmitter) throws Throwable;
}
