package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.Maps;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;

/* compiled from: WellBehavedMap.java */
@GwtCompatible
/* loaded from: classes2.dex */
final class ct<K, V> extends ForwardingMap<K, V> {
    private final Map<K, V> a;
    @MonotonicNonNullDecl
    private Set<Map.Entry<K, V>> b;

    private ct(Map<K, V> map) {
        this.a = map;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <K, V> ct<K, V> a(Map<K, V> map) {
        return new ct<>(map);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.common.collect.ForwardingMap, com.google.common.collect.ForwardingObject
    public Map<K, V> delegate() {
        return this.a;
    }

    @Override // com.google.common.collect.ForwardingMap, java.util.Map
    public Set<Map.Entry<K, V>> entrySet() {
        Set<Map.Entry<K, V>> set = this.b;
        if (set != null) {
            return set;
        }
        a aVar = new a();
        this.b = aVar;
        return aVar;
    }

    /* compiled from: WellBehavedMap.java */
    /* loaded from: classes2.dex */
    private final class a extends Maps.f<K, V> {
        private a() {
        }

        @Override // com.google.common.collect.Maps.f
        Map<K, V> a() {
            return ct.this;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<Map.Entry<K, V>> iterator() {
            return new cp<K, Map.Entry<K, V>>(ct.this.keySet().iterator()) { // from class: com.google.common.collect.ct.a.1
                /* JADX INFO: Access modifiers changed from: package-private */
                /* renamed from: b */
                public Map.Entry<K, V> a(final K k) {
                    return new f<K, V>() { // from class: com.google.common.collect.ct.a.1.1
                        @Override // com.google.common.collect.f, java.util.Map.Entry
                        public K getKey() {
                            return (K) k;
                        }

                        @Override // com.google.common.collect.f, java.util.Map.Entry
                        public V getValue() {
                            return ct.this.get(k);
                        }

                        /* JADX WARN: Multi-variable type inference failed */
                        @Override // com.google.common.collect.f, java.util.Map.Entry
                        public V setValue(V v) {
                            return (V) ct.this.put(k, v);
                        }
                    };
                }
            };
        }
    }
}
