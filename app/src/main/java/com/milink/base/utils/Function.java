package com.milink.base.utils;

@FunctionalInterface
/* loaded from: classes2.dex */
public interface Function<T, R> {
    R apply(T t);

    default <V> Function<V, R> compose(Function<? super V, ? extends T> function) {
        throw new RuntimeException("Stub!");
    }

    default <V> Function<T, V> andThen(Function<? super R, ? extends V> function) {
        throw new RuntimeException("Stub!");
    }

    static <T> Function<T, T> identity() {
        throw new RuntimeException("Stub!");
    }
}
