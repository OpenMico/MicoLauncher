package com.milink.base.utils;

/* loaded from: classes2.dex */
public interface IFlat<T> {
    default <R> R flatTo(Function<T, R> function) {
        return function.apply(this);
    }
}
