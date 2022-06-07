package com.google.common.graph;

import com.google.common.base.Preconditions;
import com.google.common.collect.UnmodifiableIterator;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* compiled from: MapIteratorCache.java */
/* loaded from: classes2.dex */
public class u<K, V> {
    private final Map<K, V> a;
    @NullableDecl
    private transient Map.Entry<K, V> b;

    public u(Map<K, V> map) {
        this.a = (Map) Preconditions.checkNotNull(map);
    }

    @CanIgnoreReturnValue
    public V a(@NullableDecl K k, @NullableDecl V v) {
        b();
        return this.a.put(k, v);
    }

    @CanIgnoreReturnValue
    public V a(@NullableDecl Object obj) {
        b();
        return this.a.remove(obj);
    }

    public V b(@NullableDecl Object obj) {
        V e = e(obj);
        return e != null ? e : c(obj);
    }

    public final V c(@NullableDecl Object obj) {
        return this.a.get(obj);
    }

    public final boolean d(@NullableDecl Object obj) {
        return e(obj) != null || this.a.containsKey(obj);
    }

    public final Set<K> a() {
        return new AbstractSet<K>() { // from class: com.google.common.graph.u.1
            /* renamed from: a */
            public UnmodifiableIterator<K> iterator() {
                final Iterator<Map.Entry<K, V>> it = u.this.a.entrySet().iterator();
                return new UnmodifiableIterator<K>() { // from class: com.google.common.graph.u.1.1
                    @Override // java.util.Iterator
                    public boolean hasNext() {
                        return it.hasNext();
                    }

                    @Override // java.util.Iterator
                    public K next() {
                        Map.Entry entry = (Map.Entry) it.next();
                        u.this.b = entry;
                        return (K) entry.getKey();
                    }
                };
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public int size() {
                return u.this.a.size();
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean contains(@NullableDecl Object obj) {
                return u.this.d(obj);
            }
        };
    }

    public V e(@NullableDecl Object obj) {
        Map.Entry<K, V> entry = this.b;
        if (entry == null || entry.getKey() != obj) {
            return null;
        }
        return entry.getValue();
    }

    public void b() {
        this.b = null;
    }
}
