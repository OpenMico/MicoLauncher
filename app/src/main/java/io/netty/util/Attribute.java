package io.netty.util;

/* loaded from: classes4.dex */
public interface Attribute<T> {
    boolean compareAndSet(T t, T t2);

    T get();

    T getAndRemove();

    T getAndSet(T t);

    AttributeKey<T> key();

    void remove();

    void set(T t);

    T setIfAbsent(T t);
}
