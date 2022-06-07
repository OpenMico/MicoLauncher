package io.reactivex.functions;

import io.reactivex.annotations.NonNull;

/* loaded from: classes4.dex */
public interface BiFunction<T1, T2, R> {
    @NonNull
    R apply(@NonNull T1 t1, @NonNull T2 t2) throws Exception;
}
