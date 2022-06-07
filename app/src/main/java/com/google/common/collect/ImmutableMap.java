package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableCollection;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.concurrent.LazyInit;
import java.io.Serializable;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible(emulated = true, serializable = true)
/* loaded from: classes2.dex */
public abstract class ImmutableMap<K, V> implements Serializable, Map<K, V> {
    static final Map.Entry<?, ?>[] a = new Map.Entry[0];
    @LazyInit
    private transient ImmutableSet<Map.Entry<K, V>> b;
    @LazyInit
    private transient ImmutableSet<K> c;
    @LazyInit
    private transient ImmutableCollection<V> d;
    @LazyInit
    private transient ImmutableSetMultimap<K, V> e;

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract boolean b();

    abstract ImmutableSet<K> c();

    abstract ImmutableCollection<V> d();

    abstract ImmutableSet<Map.Entry<K, V>> e();

    public boolean f() {
        return false;
    }

    @Override // java.util.Map
    public abstract V get(@NullableDecl Object obj);

    public static <K, V> ImmutableMap<K, V> of() {
        return (ImmutableMap<K, V>) bu.b;
    }

    public static <K, V> ImmutableMap<K, V> of(K k, V v) {
        t.a(k, v);
        return bu.a(1, new Object[]{k, v});
    }

    public static <K, V> ImmutableMap<K, V> of(K k, V v, K k2, V v2) {
        t.a(k, v);
        t.a(k2, v2);
        return bu.a(2, new Object[]{k, v, k2, v2});
    }

    public static <K, V> ImmutableMap<K, V> of(K k, V v, K k2, V v2, K k3, V v3) {
        t.a(k, v);
        t.a(k2, v2);
        t.a(k3, v3);
        return bu.a(3, new Object[]{k, v, k2, v2, k3, v3});
    }

    public static <K, V> ImmutableMap<K, V> of(K k, V v, K k2, V v2, K k3, V v3, K k4, V v4) {
        t.a(k, v);
        t.a(k2, v2);
        t.a(k3, v3);
        t.a(k4, v4);
        return bu.a(4, new Object[]{k, v, k2, v2, k3, v3, k4, v4});
    }

    public static <K, V> ImmutableMap<K, V> of(K k, V v, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5) {
        t.a(k, v);
        t.a(k2, v2);
        t.a(k3, v3);
        t.a(k4, v4);
        t.a(k5, v5);
        return bu.a(5, new Object[]{k, v, k2, v2, k3, v3, k4, v4, k5, v5});
    }

    static <K, V> Map.Entry<K, V> a(K k, V v) {
        t.a(k, v);
        return new AbstractMap.SimpleImmutableEntry(k, v);
    }

    public static <K, V> Builder<K, V> builder() {
        return new Builder<>();
    }

    @Beta
    public static <K, V> Builder<K, V> builderWithExpectedSize(int i) {
        t.a(i, "expectedSize");
        return new Builder<>(i);
    }

    static void a(boolean z, String str, Map.Entry<?, ?> entry, Map.Entry<?, ?> entry2) {
        if (!z) {
            throw a(str, entry, entry2);
        }
    }

    static IllegalArgumentException a(String str, Object obj, Object obj2) {
        return new IllegalArgumentException("Multiple entries with same " + str + ": " + obj + " and " + obj2);
    }

    /* loaded from: classes2.dex */
    public static class Builder<K, V> {
        @MonotonicNonNullDecl
        Comparator<? super V> a;
        Object[] b;
        int c;
        boolean d;

        public Builder() {
            this(4);
        }

        public Builder(int i) {
            this.b = new Object[i * 2];
            this.c = 0;
            this.d = false;
        }

        private void a(int i) {
            int i2 = i * 2;
            Object[] objArr = this.b;
            if (i2 > objArr.length) {
                this.b = Arrays.copyOf(objArr, ImmutableCollection.Builder.a(objArr.length, i2));
                this.d = false;
            }
        }

        @CanIgnoreReturnValue
        public Builder<K, V> put(K k, V v) {
            a(this.c + 1);
            t.a(k, v);
            Object[] objArr = this.b;
            int i = this.c;
            objArr[i * 2] = k;
            objArr[(i * 2) + 1] = v;
            this.c = i + 1;
            return this;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @CanIgnoreReturnValue
        public Builder<K, V> put(Map.Entry<? extends K, ? extends V> entry) {
            return put(entry.getKey(), entry.getValue());
        }

        @CanIgnoreReturnValue
        public Builder<K, V> putAll(Map<? extends K, ? extends V> map) {
            return putAll(map.entrySet());
        }

        @CanIgnoreReturnValue
        @Beta
        public Builder<K, V> putAll(Iterable<? extends Map.Entry<? extends K, ? extends V>> iterable) {
            if (iterable instanceof Collection) {
                a(this.c + ((Collection) iterable).size());
            }
            for (Map.Entry<? extends K, ? extends V> entry : iterable) {
                put(entry);
            }
            return this;
        }

        @CanIgnoreReturnValue
        @Beta
        public Builder<K, V> orderEntriesByValue(Comparator<? super V> comparator) {
            Preconditions.checkState(this.a == null, "valueComparator was already set");
            this.a = (Comparator) Preconditions.checkNotNull(comparator, "valueComparator");
            return this;
        }

        public ImmutableMap<K, V> build() {
            a();
            this.d = true;
            return bu.a(this.c, this.b);
        }

        void a() {
            int i;
            if (this.a != null) {
                if (this.d) {
                    this.b = Arrays.copyOf(this.b, this.c * 2);
                }
                Map.Entry[] entryArr = new Map.Entry[this.c];
                int i2 = 0;
                while (true) {
                    i = this.c;
                    if (i2 >= i) {
                        break;
                    }
                    Object[] objArr = this.b;
                    int i3 = i2 * 2;
                    entryArr[i2] = new AbstractMap.SimpleImmutableEntry(objArr[i3], objArr[i3 + 1]);
                    i2++;
                }
                Arrays.sort(entryArr, 0, i, Ordering.from(this.a).onResultOf(Maps.b()));
                for (int i4 = 0; i4 < this.c; i4++) {
                    int i5 = i4 * 2;
                    this.b[i5] = entryArr[i4].getKey();
                    this.b[i5 + 1] = entryArr[i4].getValue();
                }
            }
        }
    }

    public static <K, V> ImmutableMap<K, V> copyOf(Map<? extends K, ? extends V> map) {
        if ((map instanceof ImmutableMap) && !(map instanceof SortedMap)) {
            ImmutableMap<K, V> immutableMap = (ImmutableMap) map;
            if (!immutableMap.b()) {
                return immutableMap;
            }
        }
        return copyOf(map.entrySet());
    }

    @Beta
    public static <K, V> ImmutableMap<K, V> copyOf(Iterable<? extends Map.Entry<? extends K, ? extends V>> iterable) {
        Builder builder = new Builder(iterable instanceof Collection ? ((Collection) iterable).size() : 4);
        builder.putAll(iterable);
        return builder.build();
    }

    /* loaded from: classes2.dex */
    public static abstract class a<K, V> extends ImmutableMap<K, V> {
        abstract UnmodifiableIterator<Map.Entry<K, V>> d_();

        @Override // com.google.common.collect.ImmutableMap, java.util.Map
        public /* bridge */ /* synthetic */ Set entrySet() {
            return ImmutableMap.super.entrySet();
        }

        @Override // com.google.common.collect.ImmutableMap, java.util.Map
        public /* bridge */ /* synthetic */ Set keySet() {
            return ImmutableMap.super.keySet();
        }

        @Override // com.google.common.collect.ImmutableMap, java.util.Map, com.google.common.collect.BiMap
        public /* bridge */ /* synthetic */ Collection values() {
            return ImmutableMap.super.values();
        }

        @Override // com.google.common.collect.ImmutableMap
        public ImmutableSet<K> c() {
            return new ba(this);
        }

        /* renamed from: com.google.common.collect.ImmutableMap$a$a */
        /* loaded from: classes2.dex */
        class C0087a extends az<K, V> {
            C0087a() {
                a.this = r1;
            }

            @Override // com.google.common.collect.az
            ImmutableMap<K, V> b() {
                return a.this;
            }

            @Override // com.google.common.collect.ImmutableSet, com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
            public UnmodifiableIterator<Map.Entry<K, V>> iterator() {
                return a.this.d_();
            }
        }

        @Override // com.google.common.collect.ImmutableMap
        ImmutableSet<Map.Entry<K, V>> e() {
            return new C0087a();
        }

        @Override // com.google.common.collect.ImmutableMap
        ImmutableCollection<V> d() {
            return new bb(this);
        }
    }

    @Override // java.util.Map
    @CanIgnoreReturnValue
    @Deprecated
    public final V put(K k, V v) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Map
    @CanIgnoreReturnValue
    @Deprecated
    public final V remove(Object obj) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Map
    @Deprecated
    public final void putAll(Map<? extends K, ? extends V> map) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Map
    @Deprecated
    public final void clear() {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Map
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override // java.util.Map
    public boolean containsKey(@NullableDecl Object obj) {
        return get(obj) != null;
    }

    @Override // java.util.Map
    public boolean containsValue(@NullableDecl Object obj) {
        return values().contains(obj);
    }

    @Override // java.util.Map
    public final V getOrDefault(@NullableDecl Object obj, @NullableDecl V v) {
        V v2 = get(obj);
        return v2 != null ? v2 : v;
    }

    @Override // java.util.Map
    public ImmutableSet<Map.Entry<K, V>> entrySet() {
        ImmutableSet<Map.Entry<K, V>> immutableSet = this.b;
        if (immutableSet != null) {
            return immutableSet;
        }
        ImmutableSet<Map.Entry<K, V>> e = e();
        this.b = e;
        return e;
    }

    @Override // java.util.Map
    public ImmutableSet<K> keySet() {
        ImmutableSet<K> immutableSet = this.c;
        if (immutableSet != null) {
            return immutableSet;
        }
        ImmutableSet<K> c2 = c();
        this.c = c2;
        return c2;
    }

    public UnmodifiableIterator<K> e_() {
        final UnmodifiableIterator<Map.Entry<K, V>> it = entrySet().iterator();
        return new UnmodifiableIterator<K>() { // from class: com.google.common.collect.ImmutableMap.1
            @Override // java.util.Iterator
            public boolean hasNext() {
                return it.hasNext();
            }

            @Override // java.util.Iterator
            public K next() {
                return (K) ((Map.Entry) it.next()).getKey();
            }
        };
    }

    public ImmutableCollection<V> values() {
        ImmutableCollection<V> immutableCollection = this.d;
        if (immutableCollection != null) {
            return immutableCollection;
        }
        ImmutableCollection<V> d = d();
        this.d = d;
        return d;
    }

    public ImmutableSetMultimap<K, V> asMultimap() {
        if (isEmpty()) {
            return ImmutableSetMultimap.of();
        }
        ImmutableSetMultimap<K, V> immutableSetMultimap = this.e;
        if (immutableSetMultimap != null) {
            return immutableSetMultimap;
        }
        ImmutableSetMultimap<K, V> immutableSetMultimap2 = new ImmutableSetMultimap<>(new b(), size(), null);
        this.e = immutableSetMultimap2;
        return immutableSetMultimap2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public final class b extends a<K, ImmutableSet<V>> {
        private b() {
            ImmutableMap.this = r1;
        }

        @Override // java.util.Map
        public int size() {
            return ImmutableMap.this.size();
        }

        @Override // com.google.common.collect.ImmutableMap.a, com.google.common.collect.ImmutableMap
        public ImmutableSet<K> c() {
            return ImmutableMap.this.keySet();
        }

        @Override // com.google.common.collect.ImmutableMap, java.util.Map
        public boolean containsKey(@NullableDecl Object obj) {
            return ImmutableMap.this.containsKey(obj);
        }

        /* renamed from: a */
        public ImmutableSet<V> get(@NullableDecl Object obj) {
            Object obj2 = ImmutableMap.this.get(obj);
            if (obj2 == null) {
                return null;
            }
            return ImmutableSet.of(obj2);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.google.common.collect.ImmutableMap
        public boolean b() {
            return ImmutableMap.this.b();
        }

        @Override // com.google.common.collect.ImmutableMap, java.util.Map
        public int hashCode() {
            return ImmutableMap.this.hashCode();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.google.common.collect.ImmutableMap
        public boolean f() {
            return ImmutableMap.this.f();
        }

        @Override // com.google.common.collect.ImmutableMap.a
        UnmodifiableIterator<Map.Entry<K, ImmutableSet<V>>> d_() {
            final UnmodifiableIterator<Map.Entry<K, V>> it = ImmutableMap.this.entrySet().iterator();
            return new UnmodifiableIterator<Map.Entry<K, ImmutableSet<V>>>() { // from class: com.google.common.collect.ImmutableMap.b.1
                @Override // java.util.Iterator
                public boolean hasNext() {
                    return it.hasNext();
                }

                /* renamed from: a */
                public Map.Entry<K, ImmutableSet<V>> next() {
                    final Map.Entry entry = (Map.Entry) it.next();
                    return new f<K, ImmutableSet<V>>() { // from class: com.google.common.collect.ImmutableMap.b.1.1
                        @Override // com.google.common.collect.f, java.util.Map.Entry
                        public K getKey() {
                            return (K) entry.getKey();
                        }

                        /* renamed from: a */
                        public ImmutableSet<V> getValue() {
                            return ImmutableSet.of(entry.getValue());
                        }
                    };
                }
            };
        }
    }

    @Override // java.util.Map
    public boolean equals(@NullableDecl Object obj) {
        return Maps.f(this, obj);
    }

    @Override // java.util.Map
    public int hashCode() {
        return Sets.a(entrySet());
    }

    public String toString() {
        return Maps.a(this);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public static class c implements Serializable {
        private static final long serialVersionUID = 0;
        private final Object[] keys;
        private final Object[] values;

        public c(ImmutableMap<?, ?> immutableMap) {
            this.keys = new Object[immutableMap.size()];
            this.values = new Object[immutableMap.size()];
            UnmodifiableIterator<Map.Entry<?, ?>> it = immutableMap.entrySet().iterator();
            int i = 0;
            while (it.hasNext()) {
                Map.Entry<?, ?> next = it.next();
                this.keys[i] = next.getKey();
                this.values[i] = next.getValue();
                i++;
            }
        }

        Object readResolve() {
            return a(new Builder<>(this.keys.length));
        }

        Object a(Builder<Object, Object> builder) {
            int i = 0;
            while (true) {
                Object[] objArr = this.keys;
                if (i >= objArr.length) {
                    return builder.build();
                }
                builder.put(objArr[i], this.values[i]);
                i++;
            }
        }
    }

    Object writeReplace() {
        return new c(this);
    }
}
