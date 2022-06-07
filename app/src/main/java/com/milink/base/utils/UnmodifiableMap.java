package com.milink.base.utils;

import android.util.ArrayMap;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/* loaded from: classes2.dex */
public class UnmodifiableMap<K, V> implements Map<K, V> {
    private final Map<K, V> a = new ArrayMap();

    protected void onInit(@NonNull Map<K, V> map) {
    }

    public UnmodifiableMap() {
        onInit(this.a);
    }

    @Override // java.util.Map
    public final int size() {
        return this.a.size();
    }

    @Override // java.util.Map
    public final boolean isEmpty() {
        return this.a.isEmpty();
    }

    @Override // java.util.Map
    public final boolean containsKey(@Nullable Object obj) {
        return this.a.containsKey(obj);
    }

    @Override // java.util.Map
    public final boolean containsValue(@Nullable Object obj) {
        return this.a.containsValue(obj);
    }

    @Override // java.util.Map
    @Nullable
    public final V get(@Nullable Object obj) {
        return this.a.get(obj);
    }

    @Override // java.util.Map
    @Nullable
    public final V put(K k, V v) {
        throw new IllegalStateException("not support");
    }

    @Override // java.util.Map
    @Nullable
    public final V remove(@Nullable Object obj) {
        throw new IllegalStateException("not support");
    }

    @Override // java.util.Map
    public final void putAll(@NonNull Map<? extends K, ? extends V> map) {
        throw new IllegalStateException("not support");
    }

    @Override // java.util.Map
    public final void clear() {
        throw new IllegalStateException("not support");
    }

    @Override // java.util.Map
    @NonNull
    public final Set<K> keySet() {
        return this.a.keySet();
    }

    @Override // java.util.Map
    @NonNull
    public final Collection<V> values() {
        return this.a.values();
    }

    @Override // java.util.Map
    @NonNull
    public final Set<Map.Entry<K, V>> entrySet() {
        return this.a.entrySet();
    }

    @Override // java.util.Map
    public final int hashCode() {
        return this.a.hashCode();
    }
}
