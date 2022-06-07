package com.jackeywong.varhandle;

/* loaded from: classes2.dex */
public class Pair<K, V> {
    public K key;
    public V value;

    public static <K, V> Pair<K, V> create(K k, V v) {
        Pair<K, V> pair = new Pair<>();
        pair.key = k;
        pair.value = v;
        return pair;
    }
}
