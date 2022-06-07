package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableMap;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.SortedMap;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible(emulated = true, serializable = true)
/* loaded from: classes2.dex */
public final class ImmutableSortedMap<K, V> extends bd<K, V> implements NavigableMap<K, V> {
    private static final Comparator<Comparable> b = Ordering.natural();
    private static final ImmutableSortedMap<Comparable, Object> c = new ImmutableSortedMap<>(ImmutableSortedSet.a((Comparator) Ordering.natural()), ImmutableList.of());
    private static final long serialVersionUID = 0;
    private final transient by<K> d;
    private final transient ImmutableList<V> e;
    private transient ImmutableSortedMap<K, V> f;

    static <K, V> ImmutableSortedMap<K, V> a(Comparator<? super K> comparator) {
        if (Ordering.natural().equals(comparator)) {
            return of();
        }
        return new ImmutableSortedMap<>(ImmutableSortedSet.a((Comparator) comparator), ImmutableList.of());
    }

    public static <K, V> ImmutableSortedMap<K, V> of() {
        return (ImmutableSortedMap<K, V>) c;
    }

    public static ImmutableSortedMap of(Comparable comparable, Object obj) {
        return b(Ordering.natural(), comparable, obj);
    }

    public static <K, V> ImmutableSortedMap<K, V> b(Comparator<? super K> comparator, K k, V v) {
        return new ImmutableSortedMap<>(new by(ImmutableList.of(k), (Comparator) Preconditions.checkNotNull(comparator)), ImmutableList.of(v));
    }

    public static ImmutableSortedMap of(Comparable comparable, Object obj, Comparable comparable2, Object obj2) {
        return a(a(comparable, obj), a(comparable2, obj2));
    }

    public static ImmutableSortedMap of(Comparable comparable, Object obj, Comparable comparable2, Object obj2, Comparable comparable3, Object obj3) {
        return a(a(comparable, obj), a(comparable2, obj2), a(comparable3, obj3));
    }

    public static ImmutableSortedMap of(Comparable comparable, Object obj, Comparable comparable2, Object obj2, Comparable comparable3, Object obj3, Comparable comparable4, Object obj4) {
        return a(a(comparable, obj), a(comparable2, obj2), a(comparable3, obj3), a(comparable4, obj4));
    }

    public static ImmutableSortedMap of(Comparable comparable, Object obj, Comparable comparable2, Object obj2, Comparable comparable3, Object obj3, Comparable comparable4, Object obj4, Comparable comparable5, Object obj5) {
        return a(a(comparable, obj), a(comparable2, obj2), a(comparable3, obj3), a(comparable4, obj4), a(comparable5, obj5));
    }

    private static <K extends Comparable<? super K>, V> ImmutableSortedMap<K, V> a(Map.Entry<K, V>... entryArr) {
        return a((Comparator) Ordering.natural(), false, (Map.Entry[]) entryArr, entryArr.length);
    }

    public static <K, V> ImmutableSortedMap<K, V> copyOf(Map<? extends K, ? extends V> map) {
        return a((Map) map, (Comparator) ((Ordering) b));
    }

    public static <K, V> ImmutableSortedMap<K, V> copyOf(Map<? extends K, ? extends V> map, Comparator<? super K> comparator) {
        return a((Map) map, (Comparator) Preconditions.checkNotNull(comparator));
    }

    @Beta
    public static <K, V> ImmutableSortedMap<K, V> copyOf(Iterable<? extends Map.Entry<? extends K, ? extends V>> iterable) {
        return copyOf(iterable, (Ordering) b);
    }

    @Beta
    public static <K, V> ImmutableSortedMap<K, V> copyOf(Iterable<? extends Map.Entry<? extends K, ? extends V>> iterable, Comparator<? super K> comparator) {
        return a((Comparator) Preconditions.checkNotNull(comparator), false, (Iterable) iterable);
    }

    public static <K, V> ImmutableSortedMap<K, V> copyOfSorted(SortedMap<K, ? extends V> sortedMap) {
        Comparator<? super K> comparator = sortedMap.comparator();
        if (comparator == null) {
            comparator = b;
        }
        if (sortedMap instanceof ImmutableSortedMap) {
            ImmutableSortedMap<K, V> immutableSortedMap = (ImmutableSortedMap) sortedMap;
            if (!immutableSortedMap.b()) {
                return immutableSortedMap;
            }
        }
        return a((Comparator) comparator, true, (Iterable) sortedMap.entrySet());
    }

    private static <K, V> ImmutableSortedMap<K, V> a(Map<? extends K, ? extends V> map, Comparator<? super K> comparator) {
        boolean z = false;
        if (map instanceof SortedMap) {
            Comparator<? super K> comparator2 = ((SortedMap) map).comparator();
            if (comparator2 != null) {
                z = comparator.equals(comparator2);
            } else if (comparator == b) {
                z = true;
            }
        }
        if (z && (map instanceof ImmutableSortedMap)) {
            ImmutableSortedMap<K, V> immutableSortedMap = (ImmutableSortedMap) map;
            if (!immutableSortedMap.b()) {
                return immutableSortedMap;
            }
        }
        return a(comparator, z, map.entrySet());
    }

    private static <K, V> ImmutableSortedMap<K, V> a(Comparator<? super K> comparator, boolean z, Iterable<? extends Map.Entry<? extends K, ? extends V>> iterable) {
        Map.Entry[] entryArr = (Map.Entry[]) Iterables.a(iterable, a);
        return a(comparator, z, entryArr, entryArr.length);
    }

    private static <K, V> ImmutableSortedMap<K, V> a(final Comparator<? super K> comparator, boolean z, Map.Entry<K, V>[] entryArr, int i) {
        switch (i) {
            case 0:
                return a(comparator);
            case 1:
                return b(comparator, entryArr[0].getKey(), entryArr[0].getValue());
            default:
                Object[] objArr = new Object[i];
                Object[] objArr2 = new Object[i];
                if (z) {
                    for (int i2 = 0; i2 < i; i2++) {
                        K key = entryArr[i2].getKey();
                        V value = entryArr[i2].getValue();
                        t.a(key, value);
                        objArr[i2] = key;
                        objArr2[i2] = value;
                    }
                } else {
                    Arrays.sort(entryArr, 0, i, new Comparator<Map.Entry<K, V>>() { // from class: com.google.common.collect.ImmutableSortedMap.1
                        /* renamed from: a */
                        public int compare(Map.Entry<K, V> entry, Map.Entry<K, V> entry2) {
                            return comparator.compare(entry.getKey(), entry2.getKey());
                        }
                    });
                    K key2 = entryArr[0].getKey();
                    objArr[0] = key2;
                    objArr2[0] = entryArr[0].getValue();
                    t.a(objArr[0], objArr2[0]);
                    Object obj = key2;
                    int i3 = 1;
                    while (i3 < i) {
                        Object key3 = entryArr[i3].getKey();
                        V value2 = entryArr[i3].getValue();
                        t.a(key3, value2);
                        objArr[i3] = key3;
                        objArr2[i3] = value2;
                        a(comparator.compare(obj, key3) != 0, "key", (Map.Entry<?, ?>) entryArr[i3 - 1], (Map.Entry<?, ?>) entryArr[i3]);
                        i3++;
                        obj = key3;
                    }
                }
                return new ImmutableSortedMap<>(new by(ImmutableList.a(objArr), comparator), ImmutableList.a(objArr2));
        }
    }

    public static <K extends Comparable<?>, V> Builder<K, V> naturalOrder() {
        return new Builder<>(Ordering.natural());
    }

    public static <K, V> Builder<K, V> orderedBy(Comparator<K> comparator) {
        return new Builder<>(comparator);
    }

    public static <K extends Comparable<?>, V> Builder<K, V> reverseOrder() {
        return new Builder<>(Ordering.natural().reverse());
    }

    /* loaded from: classes2.dex */
    public static class Builder<K, V> extends ImmutableMap.Builder<K, V> {
        private transient Object[] e;
        private transient Object[] f;
        private final Comparator<? super K> g;

        public Builder(Comparator<? super K> comparator) {
            this(comparator, 4);
        }

        private Builder(Comparator<? super K> comparator, int i) {
            this.g = (Comparator) Preconditions.checkNotNull(comparator);
            this.e = new Object[i];
            this.f = new Object[i];
        }

        private void a(int i) {
            Object[] objArr = this.e;
            if (i > objArr.length) {
                int a = ImmutableCollection.Builder.a(objArr.length, i);
                this.e = Arrays.copyOf(this.e, a);
                this.f = Arrays.copyOf(this.f, a);
            }
        }

        @Override // com.google.common.collect.ImmutableMap.Builder
        @CanIgnoreReturnValue
        public Builder<K, V> put(K k, V v) {
            a(this.c + 1);
            t.a(k, v);
            this.e[this.c] = k;
            this.f[this.c] = v;
            this.c++;
            return this;
        }

        @Override // com.google.common.collect.ImmutableMap.Builder
        @CanIgnoreReturnValue
        public Builder<K, V> put(Map.Entry<? extends K, ? extends V> entry) {
            super.put((Map.Entry) entry);
            return this;
        }

        @Override // com.google.common.collect.ImmutableMap.Builder
        @CanIgnoreReturnValue
        public Builder<K, V> putAll(Map<? extends K, ? extends V> map) {
            super.putAll((Map) map);
            return this;
        }

        @Override // com.google.common.collect.ImmutableMap.Builder
        @CanIgnoreReturnValue
        @Beta
        public Builder<K, V> putAll(Iterable<? extends Map.Entry<? extends K, ? extends V>> iterable) {
            super.putAll((Iterable) iterable);
            return this;
        }

        @Override // com.google.common.collect.ImmutableMap.Builder
        @CanIgnoreReturnValue
        @Beta
        @Deprecated
        public Builder<K, V> orderEntriesByValue(Comparator<? super V> comparator) {
            throw new UnsupportedOperationException("Not available on ImmutableSortedMap.Builder");
        }

        @Override // com.google.common.collect.ImmutableMap.Builder
        public ImmutableSortedMap<K, V> build() {
            switch (this.c) {
                case 0:
                    return ImmutableSortedMap.a(this.g);
                case 1:
                    return ImmutableSortedMap.b(this.g, this.e[0], this.f[0]);
                default:
                    Object[] copyOf = Arrays.copyOf(this.e, this.c);
                    Object[] objArr = copyOf;
                    Arrays.sort(objArr, this.g);
                    Object[] objArr2 = new Object[this.c];
                    for (int i = 0; i < this.c; i++) {
                        if (i > 0) {
                            int i2 = i - 1;
                            if (this.g.compare(copyOf[i2], copyOf[i]) == 0) {
                                throw new IllegalArgumentException("keys required to be distinct but compared as equal: " + copyOf[i2] + " and " + copyOf[i]);
                            }
                        }
                        objArr2[Arrays.binarySearch(objArr, this.e[i], this.g)] = this.f[i];
                    }
                    return new ImmutableSortedMap<>(new by(ImmutableList.a(copyOf), this.g), ImmutableList.a(objArr2));
            }
        }
    }

    public ImmutableSortedMap(by<K> byVar, ImmutableList<V> immutableList) {
        this(byVar, immutableList, null);
    }

    ImmutableSortedMap(by<K> byVar, ImmutableList<V> immutableList, ImmutableSortedMap<K, V> immutableSortedMap) {
        this.d = byVar;
        this.e = immutableList;
        this.f = immutableSortedMap;
    }

    @Override // java.util.Map
    public int size() {
        return this.e.size();
    }

    @Override // com.google.common.collect.ImmutableMap, java.util.Map
    public V get(@NullableDecl Object obj) {
        int a2 = this.d.a(obj);
        if (a2 == -1) {
            return null;
        }
        return this.e.get(a2);
    }

    @Override // com.google.common.collect.ImmutableMap
    public boolean b() {
        return this.d.a() || this.e.a();
    }

    @Override // com.google.common.collect.ImmutableMap, java.util.Map
    public ImmutableSet<Map.Entry<K, V>> entrySet() {
        return super.entrySet();
    }

    /* loaded from: classes2.dex */
    public class a extends az<K, V> {
        a() {
            ImmutableSortedMap.this = r1;
        }

        @Override // com.google.common.collect.ImmutableSet, com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
        public UnmodifiableIterator<Map.Entry<K, V>> iterator() {
            return asList().iterator();
        }

        @Override // com.google.common.collect.ImmutableSet
        ImmutableList<Map.Entry<K, V>> f() {
            return new ImmutableList<Map.Entry<K, V>>() { // from class: com.google.common.collect.ImmutableSortedMap.a.1
                @Override // com.google.common.collect.ImmutableCollection
                public boolean a() {
                    return true;
                }

                /* renamed from: a */
                public Map.Entry<K, V> get(int i) {
                    return new AbstractMap.SimpleImmutableEntry(ImmutableSortedMap.this.d.asList().get(i), ImmutableSortedMap.this.e.get(i));
                }

                @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
                public int size() {
                    return ImmutableSortedMap.this.size();
                }
            };
        }

        @Override // com.google.common.collect.az
        ImmutableMap<K, V> b() {
            return ImmutableSortedMap.this;
        }
    }

    @Override // com.google.common.collect.ImmutableMap
    ImmutableSet<Map.Entry<K, V>> e() {
        return isEmpty() ? ImmutableSet.of() : new a();
    }

    @Override // com.google.common.collect.ImmutableMap, java.util.Map
    public ImmutableSortedSet<K> keySet() {
        return this.d;
    }

    @Override // com.google.common.collect.ImmutableMap
    ImmutableSet<K> c() {
        throw new AssertionError("should never be called");
    }

    @Override // com.google.common.collect.ImmutableMap, java.util.Map, com.google.common.collect.BiMap
    public ImmutableCollection<V> values() {
        return this.e;
    }

    @Override // com.google.common.collect.ImmutableMap
    ImmutableCollection<V> d() {
        throw new AssertionError("should never be called");
    }

    @Override // java.util.SortedMap
    public Comparator<? super K> comparator() {
        return keySet().comparator();
    }

    @Override // java.util.SortedMap
    public K firstKey() {
        return keySet().first();
    }

    @Override // java.util.SortedMap
    public K lastKey() {
        return keySet().last();
    }

    private ImmutableSortedMap<K, V> a(int i, int i2) {
        if (i == 0 && i2 == size()) {
            return this;
        }
        if (i == i2) {
            return a(comparator());
        }
        return new ImmutableSortedMap<>(this.d.b(i, i2), this.e.subList(i, i2));
    }

    @Override // java.util.NavigableMap, java.util.SortedMap
    public ImmutableSortedMap<K, V> headMap(K k) {
        return headMap((ImmutableSortedMap<K, V>) k, false);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.NavigableMap
    public ImmutableSortedMap<K, V> headMap(K k, boolean z) {
        return a(0, this.d.c(Preconditions.checkNotNull(k), z));
    }

    @Override // java.util.NavigableMap, java.util.SortedMap
    public ImmutableSortedMap<K, V> subMap(K k, K k2) {
        return subMap((boolean) k, true, (boolean) k2, false);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.NavigableMap
    public ImmutableSortedMap<K, V> subMap(K k, boolean z, K k2, boolean z2) {
        Preconditions.checkNotNull(k);
        Preconditions.checkNotNull(k2);
        Preconditions.checkArgument(comparator().compare(k, k2) <= 0, "expected fromKey <= toKey but %s > %s", k, k2);
        return headMap((ImmutableSortedMap<K, V>) k2, z2).tailMap((ImmutableSortedMap<K, V>) k, z);
    }

    @Override // java.util.NavigableMap, java.util.SortedMap
    public ImmutableSortedMap<K, V> tailMap(K k) {
        return tailMap((ImmutableSortedMap<K, V>) k, true);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.NavigableMap
    public ImmutableSortedMap<K, V> tailMap(K k, boolean z) {
        return a(this.d.d(Preconditions.checkNotNull(k), z), size());
    }

    @Override // java.util.NavigableMap
    public Map.Entry<K, V> lowerEntry(K k) {
        return headMap((ImmutableSortedMap<K, V>) k, false).lastEntry();
    }

    @Override // java.util.NavigableMap
    public K lowerKey(K k) {
        return (K) Maps.b(lowerEntry(k));
    }

    @Override // java.util.NavigableMap
    public Map.Entry<K, V> floorEntry(K k) {
        return headMap((ImmutableSortedMap<K, V>) k, true).lastEntry();
    }

    @Override // java.util.NavigableMap
    public K floorKey(K k) {
        return (K) Maps.b(floorEntry(k));
    }

    @Override // java.util.NavigableMap
    public Map.Entry<K, V> ceilingEntry(K k) {
        return tailMap((ImmutableSortedMap<K, V>) k, true).firstEntry();
    }

    @Override // java.util.NavigableMap
    public K ceilingKey(K k) {
        return (K) Maps.b(ceilingEntry(k));
    }

    @Override // java.util.NavigableMap
    public Map.Entry<K, V> higherEntry(K k) {
        return tailMap((ImmutableSortedMap<K, V>) k, false).firstEntry();
    }

    @Override // java.util.NavigableMap
    public K higherKey(K k) {
        return (K) Maps.b(higherEntry(k));
    }

    @Override // java.util.NavigableMap
    public Map.Entry<K, V> firstEntry() {
        if (isEmpty()) {
            return null;
        }
        return entrySet().asList().get(0);
    }

    @Override // java.util.NavigableMap
    public Map.Entry<K, V> lastEntry() {
        if (isEmpty()) {
            return null;
        }
        return entrySet().asList().get(size() - 1);
    }

    @Override // java.util.NavigableMap
    @CanIgnoreReturnValue
    @Deprecated
    public final Map.Entry<K, V> pollFirstEntry() {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.NavigableMap
    @CanIgnoreReturnValue
    @Deprecated
    public final Map.Entry<K, V> pollLastEntry() {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.NavigableMap
    public ImmutableSortedMap<K, V> descendingMap() {
        ImmutableSortedMap<K, V> immutableSortedMap = this.f;
        if (immutableSortedMap != null) {
            return immutableSortedMap;
        }
        if (isEmpty()) {
            return a(Ordering.from(comparator()).reverse());
        }
        return new ImmutableSortedMap<>((by) this.d.descendingSet(), this.e.reverse(), this);
    }

    @Override // java.util.NavigableMap
    public ImmutableSortedSet<K> navigableKeySet() {
        return this.d;
    }

    @Override // java.util.NavigableMap
    public ImmutableSortedSet<K> descendingKeySet() {
        return this.d.descendingSet();
    }

    /* loaded from: classes2.dex */
    private static class b extends ImmutableMap.c {
        private static final long serialVersionUID = 0;
        private final Comparator<Object> comparator;

        b(ImmutableSortedMap<?, ?> immutableSortedMap) {
            super(immutableSortedMap);
            this.comparator = immutableSortedMap.comparator();
        }

        @Override // com.google.common.collect.ImmutableMap.c
        Object readResolve() {
            return a(new Builder(this.comparator));
        }
    }

    @Override // com.google.common.collect.ImmutableMap
    Object writeReplace() {
        return new b(this);
    }
}
