package com.jackeywong.varhandle;

/* loaded from: classes2.dex */
public interface ITokenHolder<K, V> {
    V get(K k);

    V set(K k, V v);
}
