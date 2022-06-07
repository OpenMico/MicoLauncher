package io.reactivex.rxjava3.functions;

import io.reactivex.rxjava3.annotations.NonNull;

@FunctionalInterface
/* loaded from: classes4.dex */
public interface BiPredicate<T1, T2> {
    boolean test(@NonNull T1 t1, @NonNull T2 t2) throws Throwable;
}
