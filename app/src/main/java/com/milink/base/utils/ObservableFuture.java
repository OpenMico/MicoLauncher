package com.milink.base.utils;

import androidx.annotation.NonNull;
import java.util.concurrent.Future;

/* loaded from: classes2.dex */
public interface ObservableFuture<T> extends Future<T> {

    /* loaded from: classes2.dex */
    public interface IObserver<T> {
        void onUpdate(@NonNull ObservableFuture<T> observableFuture, @NonNull T t);
    }

    void addListener(@NonNull IObserver<T> iObserver);

    void addListener(@NonNull IObserver<T> iObserver, boolean z);

    void deleteListener(@NonNull IObserver<T> iObserver);

    void deleteListeners();

    boolean hasData();
}
