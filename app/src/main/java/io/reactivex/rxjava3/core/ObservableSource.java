package io.reactivex.rxjava3.core;

import io.reactivex.rxjava3.annotations.NonNull;

@FunctionalInterface
/* loaded from: classes4.dex */
public interface ObservableSource<T> {
    void subscribe(@NonNull Observer<? super T> observer);
}
