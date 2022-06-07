package io.reactivex.rxjava3.core;

import io.reactivex.rxjava3.annotations.NonNull;

@FunctionalInterface
/* loaded from: classes4.dex */
public interface MaybeSource<T> {
    void subscribe(@NonNull MaybeObserver<? super T> maybeObserver);
}
