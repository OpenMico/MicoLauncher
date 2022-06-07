package io.netty.handler.codec.serialization;

import java.lang.ref.Reference;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/* compiled from: ReferenceMap.java */
/* loaded from: classes4.dex */
abstract class e<K, V> implements Map<K, V> {
    private final Map<K, Reference<V>> a;

    abstract Reference<V> a(V v);

    /* JADX INFO: Access modifiers changed from: protected */
    public e(Map<K, Reference<V>> map) {
        this.a = map;
    }

    private V a(Reference<V> reference) {
        if (reference == null) {
            return null;
        }
        return reference.get();
    }

    @Override // java.util.Map
    public int size() {
        return this.a.size();
    }

    @Override // java.util.Map
    public boolean isEmpty() {
        return this.a.isEmpty();
    }

    @Override // java.util.Map
    public boolean containsKey(Object obj) {
        return this.a.containsKey(obj);
    }

    @Override // java.util.Map
    public boolean containsValue(Object obj) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Map
    public V get(Object obj) {
        return a((Reference) this.a.get(obj));
    }

    @Override // java.util.Map
    public V put(K k, V v) {
        return a((Reference) this.a.put(k, a((e<K, V>) v)));
    }

    @Override // java.util.Map
    public V remove(Object obj) {
        return a((Reference) this.a.remove(obj));
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.Map
    public void putAll(Map<? extends K, ? extends V> map) {
        for (Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
            this.a.put(entry.getKey(), a((e<K, V>) entry.getValue()));
        }
    }

    @Override // java.util.Map
    public void clear() {
        this.a.clear();
    }

    @Override // java.util.Map
    public Set<K> keySet() {
        return this.a.keySet();
    }

    @Override // java.util.Map
    public Collection<V> values() {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Map
    public Set<Map.Entry<K, V>> entrySet() {
        throw new UnsupportedOperationException();
    }
}
