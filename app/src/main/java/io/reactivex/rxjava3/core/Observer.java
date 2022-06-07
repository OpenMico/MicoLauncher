package io.reactivex.rxjava3.core;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.disposables.Disposable;

/* loaded from: classes4.dex */
public interface Observer<T> {
    void onComplete();

    void onError(@NonNull Throwable th);

    void onNext(@NonNull T t);

    void onSubscribe(@NonNull Disposable disposable);
}
