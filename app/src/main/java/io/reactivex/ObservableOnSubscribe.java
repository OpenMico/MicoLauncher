package io.reactivex;

import io.reactivex.annotations.NonNull;

/* loaded from: classes4.dex */
public interface ObservableOnSubscribe<T> {
    void subscribe(@NonNull ObservableEmitter<T> observableEmitter) throws Exception;
}
