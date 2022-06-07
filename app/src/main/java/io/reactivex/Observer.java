package io.reactivex;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/* loaded from: classes4.dex */
public interface Observer<T> {
    void onComplete();

    void onError(@NonNull Throwable th);

    void onNext(@NonNull T t);

    void onSubscribe(@NonNull Disposable disposable);
}
