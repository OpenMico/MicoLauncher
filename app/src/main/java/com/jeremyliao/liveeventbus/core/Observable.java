package com.jeremyliao.liveeventbus.core;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

/* loaded from: classes2.dex */
public interface Observable<T> {
    @Deprecated
    void broadcast(T t);

    void broadcast(T t, boolean z, boolean z2);

    void observe(@NonNull LifecycleOwner lifecycleOwner, @NonNull Observer<T> observer);

    void observeForever(@NonNull Observer<T> observer);

    void observeSticky(@NonNull LifecycleOwner lifecycleOwner, @NonNull Observer<T> observer);

    void observeStickyForever(@NonNull Observer<T> observer);

    void post(T t);

    void postAcrossApp(T t);

    void postAcrossProcess(T t);

    void postDelay(LifecycleOwner lifecycleOwner, T t, long j);

    void postDelay(T t, long j);

    void postOrderly(T t);

    void removeObserver(@NonNull Observer<T> observer);
}
