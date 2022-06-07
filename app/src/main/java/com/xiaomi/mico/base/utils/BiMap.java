package com.xiaomi.mico.base.utils;

import java.util.HashMap;

/* loaded from: classes3.dex */
public class BiMap<K, V> {
    private HashMap<K, V> a = new HashMap<>();
    private HashMap<V, K> b = new HashMap<>();

    public BiMap<K, V> put(K k, V v) {
        this.a.put(k, v);
        this.b.put(v, k);
        return this;
    }

    public V value(K k) {
        return this.a.get(k);
    }

    public K key(V v) {
        return this.b.get(v);
    }
}
