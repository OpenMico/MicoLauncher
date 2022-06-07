package com.bumptech.glide.util;

import androidx.collection.ArrayMap;
import androidx.collection.SimpleArrayMap;

/* loaded from: classes.dex */
public final class CachedHashCodeArrayMap<K, V> extends ArrayMap<K, V> {
    private int k;

    @Override // androidx.collection.SimpleArrayMap, java.util.Map
    public void clear() {
        this.k = 0;
        super.clear();
    }

    @Override // androidx.collection.SimpleArrayMap
    public V setValueAt(int i, V v) {
        this.k = 0;
        return (V) super.setValueAt(i, v);
    }

    @Override // androidx.collection.SimpleArrayMap, java.util.Map
    public V put(K k, V v) {
        this.k = 0;
        return (V) super.put(k, v);
    }

    @Override // androidx.collection.SimpleArrayMap
    public void putAll(SimpleArrayMap<? extends K, ? extends V> simpleArrayMap) {
        this.k = 0;
        super.putAll(simpleArrayMap);
    }

    @Override // androidx.collection.SimpleArrayMap
    public V removeAt(int i) {
        this.k = 0;
        return (V) super.removeAt(i);
    }

    @Override // androidx.collection.SimpleArrayMap, java.util.Map
    public int hashCode() {
        if (this.k == 0) {
            this.k = super.hashCode();
        }
        return this.k;
    }
}
