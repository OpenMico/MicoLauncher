package io.reactivex.rxjava3.internal.fuseable;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.annotations.Nullable;

/* loaded from: classes4.dex */
public interface SimpleQueue<T> {
    void clear();

    boolean isEmpty();

    boolean offer(@NonNull T t);

    boolean offer(@NonNull T t, @NonNull T t2);

    @Nullable
    T poll() throws Throwable;
}
