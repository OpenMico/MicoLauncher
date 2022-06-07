package io.reactivex.rxjava3.core;

import io.reactivex.rxjava3.annotations.NonNull;

@FunctionalInterface
/* loaded from: classes4.dex */
public interface SingleOnSubscribe<T> {
    void subscribe(@NonNull SingleEmitter<T> singleEmitter) throws Throwable;
}
