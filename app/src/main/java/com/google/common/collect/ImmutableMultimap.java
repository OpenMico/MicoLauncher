package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.Multiset;
import com.google.common.collect.cc;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.j2objc.annotations.Weak;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible(emulated = true)
/* loaded from: classes2.dex */
public abstract class ImmutableMultimap<K, V> extends g<K, V> implements Serializable {
    private static final long serialVersionUID = 0;
    final transient ImmutableMap<K, ? extends ImmutableCollection<V>> b;
    final transient int c;

    @GwtIncompatible
    /* loaded from: classes2.dex */
    static class b {
        static final cc.a<ImmutableMultimap> a = cc.a(ImmutableMultimap.class, "map");
        static final cc.a<ImmutableMultimap> b = cc.a(ImmutableMultimap.class, "size");
    }

    @Override // com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
    public abstract ImmutableCollection<V> get(K k);

    public abstract ImmutableMultimap<V, K> inverse();

    @Override // com.google.common.collect.g, com.google.common.collect.Multimap
    public /* bridge */ /* synthetic */ boolean containsEntry(@NullableDecl Object obj, @NullableDecl Object obj2) {
        return super.containsEntry(obj, obj2);
    }

    @Override // com.google.common.collect.g, com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
    public /* bridge */ /* synthetic */ boolean equals(@NullableDecl Object obj) {
        return super.equals(obj);
    }

    @Override // com.google.common.collect.g, com.google.common.collect.Multimap
    public /* bridge */ /* synthetic */ int hashCode() {
        return super.hashCode();
    }

    @Override // com.google.common.collect.g, com.google.common.collect.Multimap
    public /* bridge */ /* synthetic */ boolean isEmpty() {
        return super.isEmpty();
    }

    @Override // com.google.common.collect.g
    public /* bridge */ /* synthetic */ String toString() {
        return super.toString();
    }

    public static <K, V> ImmutableMultimap<K, V> of() {
        return ImmutableListMultimap.of();
    }

    public static <K, V> ImmutableMultimap<K, V> of(K k, V v) {
        return ImmutableListMultimap.of((Object) k, (Object) v);
    }

    public static <K, V> ImmutableMultimap<K, V> of(K k, V v, K k2, V v2) {
        return ImmutableListMultimap.of((Object) k, (Object) v, (Object) k2, (Object) v2);
    }

    public static <K, V> ImmutableMultimap<K, V> of(K k, V v, K k2, V v2, K k3, V v3) {
        return ImmutableListMultimap.of((Object) k, (Object) v, (Object) k2, (Object) v2, (Object) k3, (Object) v3);
    }

    public static <K, V> ImmutableMultimap<K, V> of(K k, V v, K k2, V v2, K k3, V v3, K k4, V v4) {
        return ImmutableListMultimap.of((Object) k, (Object) v, (Object) k2, (Object) v2, (Object) k3, (Object) v3, (Object) k4, (Object) v4);
    }

    public static <K, V> ImmutableMultimap<K, V> of(K k, V v, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5) {
        return ImmutableListMultimap.of((Object) k, (Object) v, (Object) k2, (Object) v2, (Object) k3, (Object) v3, (Object) k4, (Object) v4, (Object) k5, (Object) v5);
    }

    public static <K, V> Builder<K, V> builder() {
        return new Builder<>();
    }

    /* loaded from: classes2.dex */
    public static class Builder<K, V> {
        Map<K, Collection<V>> a = bp.a();
        @MonotonicNonNullDecl
        Comparator<? super K> b;
        @MonotonicNonNullDecl
        Comparator<? super V> c;

        Collection<V> a() {
            return new ArrayList();
        }

        @CanIgnoreReturnValue
        public Builder<K, V> put(K k, V v) {
            t.a(k, v);
            Collection<V> collection = this.a.get(k);
            if (collection == null) {
                Map<K, Collection<V>> map = this.a;
                Collection<V> a = a();
                map.put(k, a);
                collection = a;
            }
            collection.add(v);
            return this;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @CanIgnoreReturnValue
        public Builder<K, V> put(Map.Entry<? extends K, ? extends V> entry) {
            return put(entry.getKey(), entry.getValue());
        }

        @CanIgnoreReturnValue
        @Beta
        public Builder<K, V> putAll(Iterable<? extends Map.Entry<? extends K, ? extends V>> iterable) {
            for (Map.Entry<? extends K, ? extends V> entry : iterable) {
                put(entry);
            }
            return this;
        }

        @CanIgnoreReturnValue
        public Builder<K, V> putAll(K k, Iterable<? extends V> iterable) {
            if (k != null) {
                Collection<V> collection = this.a.get(k);
                if (collection != null) {
                    for (V v : iterable) {
                        t.a(k, v);
                        collection.add(v);
                    }
                    return this;
                }
                Iterator<? extends V> it = iterable.iterator();
                if (!it.hasNext()) {
                    return this;
                }
                Collection<V> a = a();
                while (it.hasNext()) {
                    V v2 = (V) it.next();
                    t.a(k, v2);
                    a.add(v2);
                }
                this.a.put(k, a);
                return this;
            }
            throw new NullPointerException("null key in entry: null=" + Iterables.toString(iterable));
        }

        @CanIgnoreReturnValue
        public Builder<K, V> putAll(K k, V... vArr) {
            return putAll((Builder<K, V>) k, Arrays.asList(vArr));
        }

        /* JADX WARN: Multi-variable type inference failed */
        @CanIgnoreReturnValue
        public Builder<K, V> putAll(Multimap<? extends K, ? extends V> multimap) {
            for (Map.Entry<? extends K, Collection<? extends V>> entry : multimap.asMap().entrySet()) {
                putAll((Builder<K, V>) entry.getKey(), entry.getValue());
            }
            return this;
        }

        @CanIgnoreReturnValue
        public Builder<K, V> orderKeysBy(Comparator<? super K> comparator) {
            this.b = (Comparator) Preconditions.checkNotNull(comparator);
            return this;
        }

        @CanIgnoreReturnValue
        public Builder<K, V> orderValuesBy(Comparator<? super V> comparator) {
            this.c = (Comparator) Preconditions.checkNotNull(comparator);
            return this;
        }

        public ImmutableMultimap<K, V> build() {
            Collection entrySet = this.a.entrySet();
            Comparator<? super K> comparator = this.b;
            if (comparator != null) {
                entrySet = Ordering.from(comparator).a().immutableSortedCopy(entrySet);
            }
            return ImmutableListMultimap.a(entrySet, this.c);
        }
    }

    public static <K, V> ImmutableMultimap<K, V> copyOf(Multimap<? extends K, ? extends V> multimap) {
        if (multimap instanceof ImmutableMultimap) {
            ImmutableMultimap<K, V> immutableMultimap = (ImmutableMultimap) multimap;
            if (!immutableMultimap.a()) {
                return immutableMultimap;
            }
        }
        return ImmutableListMultimap.copyOf((Multimap) multimap);
    }

    @Beta
    public static <K, V> ImmutableMultimap<K, V> copyOf(Iterable<? extends Map.Entry<? extends K, ? extends V>> iterable) {
        return ImmutableListMultimap.copyOf((Iterable) iterable);
    }

    public ImmutableMultimap(ImmutableMap<K, ? extends ImmutableCollection<V>> immutableMap, int i) {
        this.b = immutableMap;
        this.c = i;
    }

    @Override // com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
    @CanIgnoreReturnValue
    @Deprecated
    public ImmutableCollection<V> removeAll(Object obj) {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.common.collect.g, com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
    @CanIgnoreReturnValue
    @Deprecated
    public ImmutableCollection<V> replaceValues(K k, Iterable<? extends V> iterable) {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.common.collect.Multimap
    @Deprecated
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.common.collect.g, com.google.common.collect.Multimap
    @CanIgnoreReturnValue
    @Deprecated
    public boolean put(K k, V v) {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.common.collect.g, com.google.common.collect.Multimap
    @CanIgnoreReturnValue
    @Deprecated
    public boolean putAll(K k, Iterable<? extends V> iterable) {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.common.collect.g, com.google.common.collect.Multimap
    @CanIgnoreReturnValue
    @Deprecated
    public boolean putAll(Multimap<? extends K, ? extends V> multimap) {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.common.collect.g, com.google.common.collect.Multimap
    @CanIgnoreReturnValue
    @Deprecated
    public boolean remove(Object obj, Object obj2) {
        throw new UnsupportedOperationException();
    }

    boolean a() {
        return this.b.b();
    }

    @Override // com.google.common.collect.Multimap
    public boolean containsKey(@NullableDecl Object obj) {
        return this.b.containsKey(obj);
    }

    @Override // com.google.common.collect.g, com.google.common.collect.Multimap
    public boolean containsValue(@NullableDecl Object obj) {
        return obj != null && super.containsValue(obj);
    }

    @Override // com.google.common.collect.Multimap
    public int size() {
        return this.c;
    }

    @Override // com.google.common.collect.g, com.google.common.collect.Multimap
    public ImmutableSet<K> keySet() {
        return this.b.keySet();
    }

    @Override // com.google.common.collect.g
    Set<K> f() {
        throw new AssertionError("unreachable");
    }

    @Override // com.google.common.collect.g, com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
    public ImmutableMap<K, Collection<V>> asMap() {
        return (ImmutableMap<K, ? extends ImmutableCollection<V>>) this.b;
    }

    @Override // com.google.common.collect.g
    Map<K, Collection<V>> m() {
        throw new AssertionError("should never be called");
    }

    @Override // com.google.common.collect.g, com.google.common.collect.Multimap
    public ImmutableCollection<Map.Entry<K, V>> entries() {
        return (ImmutableCollection) super.entries();
    }

    /* renamed from: b */
    public ImmutableCollection<Map.Entry<K, V>> k() {
        return new a(this);
    }

    /* loaded from: classes2.dex */
    public static class a<K, V> extends ImmutableCollection<Map.Entry<K, V>> {
        private static final long serialVersionUID = 0;
        @Weak
        final ImmutableMultimap<K, V> multimap;

        a(ImmutableMultimap<K, V> immutableMultimap) {
            this.multimap = immutableMultimap;
        }

        @Override // com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
        public UnmodifiableIterator<Map.Entry<K, V>> iterator() {
            return this.multimap.l();
        }

        @Override // com.google.common.collect.ImmutableCollection
        public boolean a() {
            return this.multimap.a();
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public int size() {
            return this.multimap.size();
        }

        @Override // com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection
        public boolean contains(Object obj) {
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            return this.multimap.containsEntry(entry.getKey(), entry.getValue());
        }
    }

    /* renamed from: c */
    public UnmodifiableIterator<Map.Entry<K, V>> l() {
        return new UnmodifiableIterator<Map.Entry<K, V>>() { // from class: com.google.common.collect.ImmutableMultimap.1
            final Iterator<? extends Map.Entry<K, ? extends ImmutableCollection<V>>> a;
            K b = null;
            Iterator<V> c = Iterators.a();

            {
                ImmutableMultimap.this = this;
                this.a = ImmutableMultimap.this.b.entrySet().iterator();
            }

            @Override // java.util.Iterator
            public boolean hasNext() {
                return this.c.hasNext() || this.a.hasNext();
            }

            /* renamed from: a */
            public Map.Entry<K, V> next() {
                if (!this.c.hasNext()) {
                    Map.Entry entry = (Map.Entry) this.a.next();
                    this.b = (K) entry.getKey();
                    this.c = ((ImmutableCollection) entry.getValue()).iterator();
                }
                return Maps.immutableEntry(this.b, this.c.next());
            }
        };
    }

    @Override // com.google.common.collect.g, com.google.common.collect.Multimap
    public ImmutableMultiset<K> keys() {
        return (ImmutableMultiset) super.keys();
    }

    /* renamed from: d */
    public ImmutableMultiset<K> j() {
        return new c();
    }

    /* loaded from: classes2.dex */
    public class c extends ImmutableMultiset<K> {
        @Override // com.google.common.collect.ImmutableCollection
        public boolean a() {
            return true;
        }

        c() {
            ImmutableMultimap.this = r1;
        }

        @Override // com.google.common.collect.ImmutableMultiset, com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection
        public boolean contains(@NullableDecl Object obj) {
            return ImmutableMultimap.this.containsKey(obj);
        }

        @Override // com.google.common.collect.Multiset
        public int count(@NullableDecl Object obj) {
            Collection collection = (Collection) ImmutableMultimap.this.b.get(obj);
            if (collection == null) {
                return 0;
            }
            return collection.size();
        }

        @Override // com.google.common.collect.ImmutableMultiset, com.google.common.collect.Multiset
        public ImmutableSet<K> elementSet() {
            return ImmutableMultimap.this.keySet();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, com.google.common.collect.Multiset
        public int size() {
            return ImmutableMultimap.this.size();
        }

        @Override // com.google.common.collect.ImmutableMultiset
        Multiset.Entry<K> a(int i) {
            Map.Entry<K, ? extends ImmutableCollection<V>> entry = ImmutableMultimap.this.b.entrySet().asList().get(i);
            return Multisets.immutableEntry(entry.getKey(), ((Collection) entry.getValue()).size());
        }

        @Override // com.google.common.collect.ImmutableMultiset, com.google.common.collect.ImmutableCollection
        @GwtIncompatible
        Object writeReplace() {
            return new d(ImmutableMultimap.this);
        }
    }

    @GwtIncompatible
    /* loaded from: classes2.dex */
    private static final class d implements Serializable {
        final ImmutableMultimap<?, ?> multimap;

        d(ImmutableMultimap<?, ?> immutableMultimap) {
            this.multimap = immutableMultimap;
        }

        Object readResolve() {
            return this.multimap.keys();
        }
    }

    @Override // com.google.common.collect.g, com.google.common.collect.Multimap
    public ImmutableCollection<V> values() {
        return (ImmutableCollection) super.values();
    }

    /* renamed from: e */
    public ImmutableCollection<V> h() {
        return new e(this);
    }

    /* renamed from: g */
    public UnmodifiableIterator<V> i() {
        return new UnmodifiableIterator<V>() { // from class: com.google.common.collect.ImmutableMultimap.2
            Iterator<? extends ImmutableCollection<V>> a;
            Iterator<V> b = Iterators.a();

            {
                ImmutableMultimap.this = this;
                this.a = ImmutableMultimap.this.b.values().iterator();
            }

            @Override // java.util.Iterator
            public boolean hasNext() {
                return this.b.hasNext() || this.a.hasNext();
            }

            @Override // java.util.Iterator
            public V next() {
                if (!this.b.hasNext()) {
                    this.b = ((ImmutableCollection) this.a.next()).iterator();
                }
                return this.b.next();
            }
        };
    }

    /* loaded from: classes2.dex */
    public static final class e<K, V> extends ImmutableCollection<V> {
        private static final long serialVersionUID = 0;
        @Weak
        private final transient ImmutableMultimap<K, V> a;

        @Override // com.google.common.collect.ImmutableCollection
        public boolean a() {
            return true;
        }

        e(ImmutableMultimap<K, V> immutableMultimap) {
            this.a = immutableMultimap;
        }

        @Override // com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection
        public boolean contains(@NullableDecl Object obj) {
            return this.a.containsValue(obj);
        }

        @Override // com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
        public UnmodifiableIterator<V> iterator() {
            return this.a.i();
        }

        @Override // com.google.common.collect.ImmutableCollection
        @GwtIncompatible
        public int a(Object[] objArr, int i) {
            UnmodifiableIterator<? extends ImmutableCollection<V>> it = this.a.b.values().iterator();
            while (it.hasNext()) {
                i = ((ImmutableCollection) it.next()).a(objArr, i);
            }
            return i;
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public int size() {
            return this.a.size();
        }
    }
}
