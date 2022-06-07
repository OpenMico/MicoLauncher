package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.Multimaps;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* compiled from: AbstractMultimap.java */
@GwtCompatible
/* loaded from: classes2.dex */
public abstract class g<K, V> implements Multimap<K, V> {
    @MonotonicNonNullDecl
    private transient Collection<Map.Entry<K, V>> a;
    @MonotonicNonNullDecl
    private transient Set<K> b;
    @MonotonicNonNullDecl
    private transient Multiset<K> c;
    @MonotonicNonNullDecl
    private transient Collection<V> d;
    @MonotonicNonNullDecl
    private transient Map<K, Collection<V>> e;

    abstract Set<K> f();

    abstract Collection<V> h();

    abstract Multiset<K> j();

    abstract Collection<Map.Entry<K, V>> k();

    abstract Iterator<Map.Entry<K, V>> l();

    abstract Map<K, Collection<V>> m();

    @Override // com.google.common.collect.Multimap
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override // com.google.common.collect.Multimap
    public boolean containsValue(@NullableDecl Object obj) {
        for (Collection<V> collection : asMap().values()) {
            if (collection.contains(obj)) {
                return true;
            }
        }
        return false;
    }

    @Override // com.google.common.collect.Multimap
    public boolean containsEntry(@NullableDecl Object obj, @NullableDecl Object obj2) {
        Collection<V> collection = asMap().get(obj);
        return collection != null && collection.contains(obj2);
    }

    @Override // com.google.common.collect.Multimap
    @CanIgnoreReturnValue
    public boolean remove(@NullableDecl Object obj, @NullableDecl Object obj2) {
        Collection<V> collection = asMap().get(obj);
        return collection != null && collection.remove(obj2);
    }

    @Override // com.google.common.collect.Multimap
    @CanIgnoreReturnValue
    public boolean put(@NullableDecl K k, @NullableDecl V v) {
        return get(k).add(v);
    }

    @Override // com.google.common.collect.Multimap
    @CanIgnoreReturnValue
    public boolean putAll(@NullableDecl K k, Iterable<? extends V> iterable) {
        Preconditions.checkNotNull(iterable);
        if (iterable instanceof Collection) {
            Collection<? extends V> collection = (Collection) iterable;
            return !collection.isEmpty() && get(k).addAll(collection);
        }
        Iterator<? extends V> it = iterable.iterator();
        return it.hasNext() && Iterators.addAll(get(k), it);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.collect.Multimap
    @CanIgnoreReturnValue
    public boolean putAll(Multimap<? extends K, ? extends V> multimap) {
        boolean z = false;
        for (Map.Entry<? extends K, ? extends V> entry : multimap.entries()) {
            z |= put(entry.getKey(), entry.getValue());
        }
        return z;
    }

    @Override // com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
    @CanIgnoreReturnValue
    public Collection<V> replaceValues(@NullableDecl K k, Iterable<? extends V> iterable) {
        Preconditions.checkNotNull(iterable);
        Collection<V> removeAll = removeAll(k);
        putAll(k, iterable);
        return removeAll;
    }

    @Override // com.google.common.collect.Multimap
    public Collection<Map.Entry<K, V>> entries() {
        Collection<Map.Entry<K, V>> collection = this.a;
        if (collection != null) {
            return collection;
        }
        Collection<Map.Entry<K, V>> k = k();
        this.a = k;
        return k;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: AbstractMultimap.java */
    /* loaded from: classes2.dex */
    public class a extends Multimaps.f<K, V> {
        public a() {
            g.this = r1;
        }

        @Override // com.google.common.collect.Multimaps.f
        Multimap<K, V> a() {
            return g.this;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
        public Iterator<Map.Entry<K, V>> iterator() {
            return g.this.l();
        }
    }

    /* compiled from: AbstractMultimap.java */
    /* loaded from: classes2.dex */
    class b extends g<K, V>.a implements Set<Map.Entry<K, V>> {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public b() {
            super();
            g.this = r1;
        }

        @Override // java.util.Collection, java.util.Set
        public int hashCode() {
            return Sets.a(this);
        }

        @Override // java.util.Collection, java.util.Set
        public boolean equals(@NullableDecl Object obj) {
            return Sets.a(this, obj);
        }
    }

    @Override // com.google.common.collect.Multimap
    public Set<K> keySet() {
        Set<K> set = this.b;
        if (set != null) {
            return set;
        }
        Set<K> f = f();
        this.b = f;
        return f;
    }

    @Override // com.google.common.collect.Multimap
    public Multiset<K> keys() {
        Multiset<K> multiset = this.c;
        if (multiset != null) {
            return multiset;
        }
        Multiset<K> j = j();
        this.c = j;
        return j;
    }

    @Override // com.google.common.collect.Multimap
    public Collection<V> values() {
        Collection<V> collection = this.d;
        if (collection != null) {
            return collection;
        }
        Collection<V> h = h();
        this.d = h;
        return h;
    }

    /* compiled from: AbstractMultimap.java */
    /* loaded from: classes2.dex */
    class c extends AbstractCollection<V> {
        public c() {
            g.this = r1;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
        public Iterator<V> iterator() {
            return g.this.i();
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public int size() {
            return g.this.size();
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean contains(@NullableDecl Object obj) {
            return g.this.containsValue(obj);
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public void clear() {
            g.this.clear();
        }
    }

    Iterator<V> i() {
        return Maps.b(entries().iterator());
    }

    @Override // com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
    public Map<K, Collection<V>> asMap() {
        Map<K, Collection<V>> map = this.e;
        if (map != null) {
            return map;
        }
        Map<K, Collection<V>> m = m();
        this.e = m;
        return m;
    }

    @Override // com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
    public boolean equals(@NullableDecl Object obj) {
        return Multimaps.a(this, obj);
    }

    @Override // com.google.common.collect.Multimap
    public int hashCode() {
        return asMap().hashCode();
    }

    public String toString() {
        return asMap().toString();
    }
}
