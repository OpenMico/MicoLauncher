package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Converter;
import com.google.common.base.Equivalence;
import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.MapDifference;
import com.google.common.collect.Sets;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.j2objc.annotations.RetainedWith;
import com.google.j2objc.annotations.Weak;
import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.AbstractMap;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.Properties;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible(emulated = true)
/* loaded from: classes2.dex */
public final class Maps {

    /* loaded from: classes2.dex */
    public interface EntryTransformer<K, V1, V2> {
        V2 transformEntry(@NullableDecl K k, @NullableDecl V1 v1);
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier removed */
    /* loaded from: classes2.dex */
    public static abstract class e extends Enum<e> implements Function<Map.Entry<?, ?>, Object> {
    }

    private Maps() {
    }

    public static <K> Function<Map.Entry<K, ?>, K> a() {
        return e.KEY;
    }

    public static <V> Function<Map.Entry<?, V>, V> b() {
        return e.VALUE;
    }

    public static <K, V> Iterator<K> a(Iterator<Map.Entry<K, V>> it) {
        return new cp<Map.Entry<K, V>, K>(it) { // from class: com.google.common.collect.Maps.1
            @Override // com.google.common.collect.cp
            /* bridge */ /* synthetic */ Object a(Object obj) {
                return a((Map.Entry<Object, V>) obj);
            }

            /* JADX WARN: Type inference failed for: r1v1, types: [java.lang.Object, K] */
            K a(Map.Entry<K, V> entry) {
                return entry.getKey();
            }
        };
    }

    public static <K, V> Iterator<V> b(Iterator<Map.Entry<K, V>> it) {
        return new cp<Map.Entry<K, V>, V>(it) { // from class: com.google.common.collect.Maps.6
            @Override // com.google.common.collect.cp
            /* bridge */ /* synthetic */ Object a(Object obj) {
                return a((Map.Entry<K, Object>) obj);
            }

            /* JADX WARN: Type inference failed for: r1v1, types: [V, java.lang.Object] */
            V a(Map.Entry<K, V> entry) {
                return entry.getValue();
            }
        };
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Beta
    @GwtCompatible(serializable = true)
    public static <K extends Enum<K>, V> ImmutableMap<K, V> immutableEnumMap(Map<K, ? extends V> map) {
        if (map instanceof ax) {
            return (ax) map;
        }
        Iterator<Map.Entry<K, ? extends V>> it = map.entrySet().iterator();
        if (!it.hasNext()) {
            return ImmutableMap.of();
        }
        Map.Entry<K, ? extends V> next = it.next();
        K key = next.getKey();
        Object value = next.getValue();
        t.a(key, value);
        EnumMap enumMap = new EnumMap(key.getDeclaringClass());
        enumMap.put((EnumMap) key, (K) value);
        while (it.hasNext()) {
            Map.Entry<K, ? extends V> next2 = it.next();
            K key2 = next2.getKey();
            Object value2 = next2.getValue();
            t.a(key2, value2);
            enumMap.put((EnumMap) key2, (K) value2);
        }
        return ax.a(enumMap);
    }

    public static <K, V> HashMap<K, V> newHashMap() {
        return new HashMap<>();
    }

    public static <K, V> HashMap<K, V> newHashMap(Map<? extends K, ? extends V> map) {
        return new HashMap<>(map);
    }

    public static <K, V> HashMap<K, V> newHashMapWithExpectedSize(int i2) {
        return new HashMap<>(a(i2));
    }

    public static int a(int i2) {
        if (i2 < 3) {
            t.a(i2, "expectedSize");
            return i2 + 1;
        } else if (i2 < 1073741824) {
            return (int) ((i2 / 0.75f) + 1.0f);
        } else {
            return Integer.MAX_VALUE;
        }
    }

    public static <K, V> LinkedHashMap<K, V> newLinkedHashMap() {
        return new LinkedHashMap<>();
    }

    public static <K, V> LinkedHashMap<K, V> newLinkedHashMap(Map<? extends K, ? extends V> map) {
        return new LinkedHashMap<>(map);
    }

    public static <K, V> LinkedHashMap<K, V> newLinkedHashMapWithExpectedSize(int i2) {
        return new LinkedHashMap<>(a(i2));
    }

    public static <K, V> ConcurrentMap<K, V> newConcurrentMap() {
        return new ConcurrentHashMap();
    }

    public static <K extends Comparable, V> TreeMap<K, V> newTreeMap() {
        return new TreeMap<>();
    }

    public static <K, V> TreeMap<K, V> newTreeMap(SortedMap<K, ? extends V> sortedMap) {
        return new TreeMap<>((SortedMap) sortedMap);
    }

    public static <C, K extends C, V> TreeMap<K, V> newTreeMap(@NullableDecl Comparator<C> comparator) {
        return new TreeMap<>((Comparator<? super K>) comparator);
    }

    public static <K extends Enum<K>, V> EnumMap<K, V> newEnumMap(Class<K> cls) {
        return new EnumMap<>((Class) Preconditions.checkNotNull(cls));
    }

    public static <K extends Enum<K>, V> EnumMap<K, V> newEnumMap(Map<K, ? extends V> map) {
        return new EnumMap<>(map);
    }

    public static <K, V> IdentityHashMap<K, V> newIdentityHashMap() {
        return new IdentityHashMap<>();
    }

    public static <K, V> MapDifference<K, V> difference(Map<? extends K, ? extends V> map, Map<? extends K, ? extends V> map2) {
        if (map instanceof SortedMap) {
            return difference((SortedMap) map, (Map) map2);
        }
        return difference(map, map2, Equivalence.equals());
    }

    public static <K, V> MapDifference<K, V> difference(Map<? extends K, ? extends V> map, Map<? extends K, ? extends V> map2, Equivalence<? super V> equivalence) {
        Preconditions.checkNotNull(equivalence);
        LinkedHashMap newLinkedHashMap = newLinkedHashMap();
        LinkedHashMap linkedHashMap = new LinkedHashMap(map2);
        LinkedHashMap newLinkedHashMap2 = newLinkedHashMap();
        LinkedHashMap newLinkedHashMap3 = newLinkedHashMap();
        a(map, map2, equivalence, newLinkedHashMap, linkedHashMap, newLinkedHashMap2, newLinkedHashMap3);
        return new o(newLinkedHashMap, linkedHashMap, newLinkedHashMap2, newLinkedHashMap3);
    }

    public static <K, V> SortedMapDifference<K, V> difference(SortedMap<K, ? extends V> sortedMap, Map<? extends K, ? extends V> map) {
        Preconditions.checkNotNull(sortedMap);
        Preconditions.checkNotNull(map);
        Comparator a2 = a(sortedMap.comparator());
        TreeMap newTreeMap = newTreeMap(a2);
        TreeMap newTreeMap2 = newTreeMap(a2);
        newTreeMap2.putAll(map);
        TreeMap newTreeMap3 = newTreeMap(a2);
        TreeMap newTreeMap4 = newTreeMap(a2);
        a(sortedMap, map, Equivalence.equals(), newTreeMap, newTreeMap2, newTreeMap3, newTreeMap4);
        return new t(newTreeMap, newTreeMap2, newTreeMap3, newTreeMap4);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static <K, V> void a(Map<? extends K, ? extends V> map, Map<? extends K, ? extends V> map2, Equivalence<? super V> equivalence, Map<K, V> map3, Map<K, V> map4, Map<K, V> map5, Map<K, MapDifference.ValueDifference<V>> map6) {
        for (Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
            Object key = entry.getKey();
            Object obj = (Object) entry.getValue();
            if (map2.containsKey(key)) {
                Object obj2 = (V) map4.remove(key);
                if (equivalence.equivalent(obj, obj2)) {
                    map5.put(key, obj);
                } else {
                    map6.put(key, ab.a(obj, obj2));
                }
            } else {
                map3.put(key, obj);
            }
        }
    }

    public static <K, V> Map<K, V> c(Map<K, ? extends V> map) {
        if (map instanceof SortedMap) {
            return Collections.unmodifiableSortedMap((SortedMap) map);
        }
        return Collections.unmodifiableMap(map);
    }

    /* loaded from: classes2.dex */
    public static class o<K, V> implements MapDifference<K, V> {
        final Map<K, V> a;
        final Map<K, V> b;
        final Map<K, V> c;
        final Map<K, MapDifference.ValueDifference<V>> d;

        o(Map<K, V> map, Map<K, V> map2, Map<K, V> map3, Map<K, MapDifference.ValueDifference<V>> map4) {
            this.a = Maps.c(map);
            this.b = Maps.c(map2);
            this.c = Maps.c(map3);
            this.d = Maps.c(map4);
        }

        @Override // com.google.common.collect.MapDifference
        public boolean areEqual() {
            return this.a.isEmpty() && this.b.isEmpty() && this.d.isEmpty();
        }

        @Override // com.google.common.collect.MapDifference
        public Map<K, V> entriesOnlyOnLeft() {
            return this.a;
        }

        @Override // com.google.common.collect.MapDifference
        public Map<K, V> entriesOnlyOnRight() {
            return this.b;
        }

        @Override // com.google.common.collect.MapDifference
        public Map<K, V> entriesInCommon() {
            return this.c;
        }

        @Override // com.google.common.collect.MapDifference
        public Map<K, MapDifference.ValueDifference<V>> entriesDiffering() {
            return this.d;
        }

        @Override // com.google.common.collect.MapDifference
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof MapDifference)) {
                return false;
            }
            MapDifference mapDifference = (MapDifference) obj;
            return entriesOnlyOnLeft().equals(mapDifference.entriesOnlyOnLeft()) && entriesOnlyOnRight().equals(mapDifference.entriesOnlyOnRight()) && entriesInCommon().equals(mapDifference.entriesInCommon()) && entriesDiffering().equals(mapDifference.entriesDiffering());
        }

        @Override // com.google.common.collect.MapDifference
        public int hashCode() {
            return Objects.hashCode(entriesOnlyOnLeft(), entriesOnlyOnRight(), entriesInCommon(), entriesDiffering());
        }

        public String toString() {
            if (areEqual()) {
                return "equal";
            }
            StringBuilder sb = new StringBuilder("not equal");
            if (!this.a.isEmpty()) {
                sb.append(": only on left=");
                sb.append(this.a);
            }
            if (!this.b.isEmpty()) {
                sb.append(": only on right=");
                sb.append(this.b);
            }
            if (!this.d.isEmpty()) {
                sb.append(": value differences=");
                sb.append(this.d);
            }
            return sb.toString();
        }
    }

    /* loaded from: classes2.dex */
    public static class ab<V> implements MapDifference.ValueDifference<V> {
        @NullableDecl
        private final V a;
        @NullableDecl
        private final V b;

        static <V> MapDifference.ValueDifference<V> a(@NullableDecl V v, @NullableDecl V v2) {
            return new ab(v, v2);
        }

        private ab(@NullableDecl V v, @NullableDecl V v2) {
            this.a = v;
            this.b = v2;
        }

        @Override // com.google.common.collect.MapDifference.ValueDifference
        public V leftValue() {
            return this.a;
        }

        @Override // com.google.common.collect.MapDifference.ValueDifference
        public V rightValue() {
            return this.b;
        }

        @Override // com.google.common.collect.MapDifference.ValueDifference
        public boolean equals(@NullableDecl Object obj) {
            if (!(obj instanceof MapDifference.ValueDifference)) {
                return false;
            }
            MapDifference.ValueDifference valueDifference = (MapDifference.ValueDifference) obj;
            return Objects.equal(this.a, valueDifference.leftValue()) && Objects.equal(this.b, valueDifference.rightValue());
        }

        @Override // com.google.common.collect.MapDifference.ValueDifference
        public int hashCode() {
            return Objects.hashCode(this.a, this.b);
        }

        public String toString() {
            return "(" + this.a + ", " + this.b + ")";
        }
    }

    /* loaded from: classes2.dex */
    public static class t<K, V> extends o<K, V> implements SortedMapDifference<K, V> {
        t(SortedMap<K, V> sortedMap, SortedMap<K, V> sortedMap2, SortedMap<K, V> sortedMap3, SortedMap<K, MapDifference.ValueDifference<V>> sortedMap4) {
            super(sortedMap, sortedMap2, sortedMap3, sortedMap4);
        }

        @Override // com.google.common.collect.Maps.o, com.google.common.collect.MapDifference
        public SortedMap<K, MapDifference.ValueDifference<V>> entriesDiffering() {
            return (SortedMap) super.entriesDiffering();
        }

        @Override // com.google.common.collect.Maps.o, com.google.common.collect.MapDifference
        public SortedMap<K, V> entriesInCommon() {
            return (SortedMap) super.entriesInCommon();
        }

        @Override // com.google.common.collect.Maps.o, com.google.common.collect.MapDifference
        public SortedMap<K, V> entriesOnlyOnLeft() {
            return (SortedMap) super.entriesOnlyOnLeft();
        }

        @Override // com.google.common.collect.Maps.o, com.google.common.collect.MapDifference
        public SortedMap<K, V> entriesOnlyOnRight() {
            return (SortedMap) super.entriesOnlyOnRight();
        }
    }

    static <E> Comparator<? super E> a(@NullableDecl Comparator<? super E> comparator) {
        return comparator != null ? comparator : Ordering.natural();
    }

    public static <K, V> Map<K, V> asMap(Set<K> set, Function<? super K, V> function) {
        return new b(set, function);
    }

    public static <K, V> SortedMap<K, V> asMap(SortedSet<K> sortedSet, Function<? super K, V> function) {
        return new r(sortedSet, function);
    }

    @GwtIncompatible
    public static <K, V> NavigableMap<K, V> asMap(NavigableSet<K> navigableSet, Function<? super K, V> function) {
        return new p(navigableSet, function);
    }

    /* loaded from: classes2.dex */
    public static class b<K, V> extends ad<K, V> {
        final Function<? super K, V> a;
        private final Set<K> b;

        Set<K> c() {
            return this.b;
        }

        b(Set<K> set, Function<? super K, V> function) {
            this.b = (Set) Preconditions.checkNotNull(set);
            this.a = (Function) Preconditions.checkNotNull(function);
        }

        @Override // com.google.common.collect.Maps.ad
        public Set<K> h() {
            return Maps.c(c());
        }

        @Override // com.google.common.collect.Maps.ad
        Collection<V> b() {
            return Collections2.transform(this.b, this.a);
        }

        @Override // java.util.AbstractMap, java.util.Map
        public int size() {
            return c().size();
        }

        @Override // java.util.AbstractMap, java.util.Map
        public boolean containsKey(@NullableDecl Object obj) {
            return c().contains(obj);
        }

        @Override // java.util.AbstractMap, java.util.Map
        public V get(@NullableDecl Object obj) {
            if (Collections2.a(c(), obj)) {
                return this.a.apply(obj);
            }
            return null;
        }

        @Override // java.util.AbstractMap, java.util.Map
        public V remove(@NullableDecl Object obj) {
            if (c().remove(obj)) {
                return this.a.apply(obj);
            }
            return null;
        }

        @Override // java.util.AbstractMap, java.util.Map
        public void clear() {
            c().clear();
        }

        /* loaded from: classes2.dex */
        class a extends f<K, V> {
            a() {
                b.this = r1;
            }

            @Override // com.google.common.collect.Maps.f
            Map<K, V> a() {
                return b.this;
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
            public Iterator<Map.Entry<K, V>> iterator() {
                return Maps.a((Set) b.this.c(), (Function) b.this.a);
            }
        }

        @Override // com.google.common.collect.Maps.ad
        protected Set<Map.Entry<K, V>> a() {
            return new a();
        }
    }

    public static <K, V> Iterator<Map.Entry<K, V>> a(Set<K> set, final Function<? super K, V> function) {
        return new cp<K, Map.Entry<K, V>>(set.iterator()) { // from class: com.google.common.collect.Maps.7
            /* renamed from: b */
            public Map.Entry<K, V> a(K k2) {
                return Maps.immutableEntry(k2, function.apply(k2));
            }
        };
    }

    /* loaded from: classes2.dex */
    public static class r<K, V> extends b<K, V> implements SortedMap<K, V> {
        r(SortedSet<K> sortedSet, Function<? super K, V> function) {
            super(sortedSet, function);
        }

        /* renamed from: d */
        public SortedSet<K> c() {
            return (SortedSet) super.c();
        }

        @Override // java.util.SortedMap
        public Comparator<? super K> comparator() {
            return c().comparator();
        }

        @Override // com.google.common.collect.Maps.ad, java.util.AbstractMap, java.util.Map
        public Set<K> keySet() {
            return Maps.b((SortedSet) c());
        }

        @Override // java.util.SortedMap
        public SortedMap<K, V> subMap(K k, K k2) {
            return Maps.asMap((SortedSet) c().subSet(k, k2), this.a);
        }

        @Override // java.util.SortedMap
        public SortedMap<K, V> headMap(K k) {
            return Maps.asMap((SortedSet) c().headSet(k), this.a);
        }

        @Override // java.util.SortedMap
        public SortedMap<K, V> tailMap(K k) {
            return Maps.asMap((SortedSet) c().tailSet(k), this.a);
        }

        @Override // java.util.SortedMap
        public K firstKey() {
            return c().first();
        }

        @Override // java.util.SortedMap
        public K lastKey() {
            return c().last();
        }
    }

    @GwtIncompatible
    /* loaded from: classes2.dex */
    public static final class p<K, V> extends i<K, V> {
        private final NavigableSet<K> a;
        private final Function<? super K, V> b;

        p(NavigableSet<K> navigableSet, Function<? super K, V> function) {
            this.a = (NavigableSet) Preconditions.checkNotNull(navigableSet);
            this.b = (Function) Preconditions.checkNotNull(function);
        }

        @Override // java.util.NavigableMap
        public NavigableMap<K, V> subMap(K k, boolean z, K k2, boolean z2) {
            return Maps.asMap((NavigableSet) this.a.subSet(k, z, k2, z2), (Function) this.b);
        }

        @Override // java.util.NavigableMap
        public NavigableMap<K, V> headMap(K k, boolean z) {
            return Maps.asMap((NavigableSet) this.a.headSet(k, z), (Function) this.b);
        }

        @Override // java.util.NavigableMap
        public NavigableMap<K, V> tailMap(K k, boolean z) {
            return Maps.asMap((NavigableSet) this.a.tailSet(k, z), (Function) this.b);
        }

        @Override // java.util.SortedMap
        public Comparator<? super K> comparator() {
            return this.a.comparator();
        }

        @Override // java.util.AbstractMap, java.util.Map
        @NullableDecl
        public V get(@NullableDecl Object obj) {
            if (Collections2.a(this.a, obj)) {
                return this.b.apply(obj);
            }
            return null;
        }

        @Override // com.google.common.collect.Maps.m, java.util.AbstractMap, java.util.Map
        public void clear() {
            this.a.clear();
        }

        @Override // com.google.common.collect.Maps.m
        Iterator<Map.Entry<K, V>> b() {
            return Maps.a((Set) this.a, (Function) this.b);
        }

        @Override // com.google.common.collect.i
        Iterator<Map.Entry<K, V>> a() {
            return descendingMap().entrySet().iterator();
        }

        @Override // com.google.common.collect.i, java.util.NavigableMap
        public NavigableSet<K> navigableKeySet() {
            return Maps.b((NavigableSet) this.a);
        }

        @Override // java.util.AbstractMap, java.util.Map
        public int size() {
            return this.a.size();
        }

        @Override // com.google.common.collect.i, java.util.NavigableMap
        public NavigableMap<K, V> descendingMap() {
            return Maps.asMap((NavigableSet) this.a.descendingSet(), (Function) this.b);
        }
    }

    public static <E> Set<E> c(final Set<E> set) {
        return new ForwardingSet<E>() { // from class: com.google.common.collect.Maps.8
            @Override // com.google.common.collect.ForwardingSet, com.google.common.collect.ForwardingCollection, com.google.common.collect.ForwardingObject
            public Set<E> delegate() {
                return set;
            }

            @Override // com.google.common.collect.ForwardingCollection, java.util.Collection, java.util.Queue
            public boolean add(E e2) {
                throw new UnsupportedOperationException();
            }

            @Override // com.google.common.collect.ForwardingCollection, java.util.Collection
            public boolean addAll(Collection<? extends E> collection) {
                throw new UnsupportedOperationException();
            }
        };
    }

    public static <E> SortedSet<E> b(final SortedSet<E> sortedSet) {
        return new ForwardingSortedSet<E>() { // from class: com.google.common.collect.Maps.9
            @Override // com.google.common.collect.ForwardingSortedSet, com.google.common.collect.ForwardingSet, com.google.common.collect.ForwardingCollection, com.google.common.collect.ForwardingObject
            public SortedSet<E> delegate() {
                return sortedSet;
            }

            @Override // com.google.common.collect.ForwardingCollection, java.util.Collection, java.util.Queue
            public boolean add(E e2) {
                throw new UnsupportedOperationException();
            }

            @Override // com.google.common.collect.ForwardingCollection, java.util.Collection
            public boolean addAll(Collection<? extends E> collection) {
                throw new UnsupportedOperationException();
            }

            @Override // com.google.common.collect.ForwardingSortedSet, java.util.SortedSet
            public SortedSet<E> headSet(E e2) {
                return Maps.b(super.headSet(e2));
            }

            @Override // com.google.common.collect.ForwardingSortedSet, java.util.SortedSet
            public SortedSet<E> subSet(E e2, E e3) {
                return Maps.b(super.subSet(e2, e3));
            }

            @Override // com.google.common.collect.ForwardingSortedSet, java.util.SortedSet
            public SortedSet<E> tailSet(E e2) {
                return Maps.b(super.tailSet(e2));
            }
        };
    }

    @GwtIncompatible
    public static <E> NavigableSet<E> b(final NavigableSet<E> navigableSet) {
        return new ForwardingNavigableSet<E>() { // from class: com.google.common.collect.Maps.10
            @Override // com.google.common.collect.ForwardingNavigableSet, com.google.common.collect.ForwardingSortedSet, com.google.common.collect.ForwardingSet, com.google.common.collect.ForwardingCollection, com.google.common.collect.ForwardingObject
            public NavigableSet<E> delegate() {
                return navigableSet;
            }

            @Override // com.google.common.collect.ForwardingCollection, java.util.Collection, java.util.Queue
            public boolean add(E e2) {
                throw new UnsupportedOperationException();
            }

            @Override // com.google.common.collect.ForwardingCollection, java.util.Collection
            public boolean addAll(Collection<? extends E> collection) {
                throw new UnsupportedOperationException();
            }

            @Override // com.google.common.collect.ForwardingSortedSet, java.util.SortedSet
            public SortedSet<E> headSet(E e2) {
                return Maps.b(super.headSet(e2));
            }

            @Override // com.google.common.collect.ForwardingNavigableSet, java.util.NavigableSet
            public NavigableSet<E> headSet(E e2, boolean z2) {
                return Maps.b(super.headSet(e2, z2));
            }

            @Override // com.google.common.collect.ForwardingSortedSet, java.util.SortedSet
            public SortedSet<E> subSet(E e2, E e3) {
                return Maps.b(super.subSet(e2, e3));
            }

            @Override // com.google.common.collect.ForwardingNavigableSet, java.util.NavigableSet
            public NavigableSet<E> subSet(E e2, boolean z2, E e3, boolean z3) {
                return Maps.b(super.subSet(e2, z2, e3, z3));
            }

            @Override // com.google.common.collect.ForwardingSortedSet, java.util.SortedSet
            public SortedSet<E> tailSet(E e2) {
                return Maps.b(super.tailSet(e2));
            }

            @Override // com.google.common.collect.ForwardingNavigableSet, java.util.NavigableSet
            public NavigableSet<E> tailSet(E e2, boolean z2) {
                return Maps.b(super.tailSet(e2, z2));
            }

            @Override // com.google.common.collect.ForwardingNavigableSet, java.util.NavigableSet
            public NavigableSet<E> descendingSet() {
                return Maps.b(super.descendingSet());
            }
        };
    }

    public static <K, V> ImmutableMap<K, V> toMap(Iterable<K> iterable, Function<? super K, V> function) {
        return toMap(iterable.iterator(), function);
    }

    public static <K, V> ImmutableMap<K, V> toMap(Iterator<K> it, Function<? super K, V> function) {
        Preconditions.checkNotNull(function);
        LinkedHashMap newLinkedHashMap = newLinkedHashMap();
        while (it.hasNext()) {
            K next = it.next();
            newLinkedHashMap.put(next, function.apply(next));
        }
        return ImmutableMap.copyOf(newLinkedHashMap);
    }

    @CanIgnoreReturnValue
    public static <K, V> ImmutableMap<K, V> uniqueIndex(Iterable<V> iterable, Function<? super V, K> function) {
        return uniqueIndex(iterable.iterator(), function);
    }

    @CanIgnoreReturnValue
    public static <K, V> ImmutableMap<K, V> uniqueIndex(Iterator<V> it, Function<? super V, K> function) {
        Preconditions.checkNotNull(function);
        ImmutableMap.Builder builder = ImmutableMap.builder();
        while (it.hasNext()) {
            V next = it.next();
            builder.put(function.apply(next), next);
        }
        try {
            return builder.build();
        } catch (IllegalArgumentException e2) {
            throw new IllegalArgumentException(e2.getMessage() + ". To index multiple values under a key, use Multimaps.index.");
        }
    }

    @GwtIncompatible
    public static ImmutableMap<String, String> fromProperties(Properties properties) {
        ImmutableMap.Builder builder = ImmutableMap.builder();
        Enumeration<?> propertyNames = properties.propertyNames();
        while (propertyNames.hasMoreElements()) {
            String str = (String) propertyNames.nextElement();
            builder.put(str, properties.getProperty(str));
        }
        return builder.build();
    }

    @GwtCompatible(serializable = true)
    public static <K, V> Map.Entry<K, V> immutableEntry(@NullableDecl K k2, @NullableDecl V v2) {
        return new aw(k2, v2);
    }

    public static <K, V> Set<Map.Entry<K, V>> a(Set<Map.Entry<K, V>> set) {
        return new z(Collections.unmodifiableSet(set));
    }

    static <K, V> Map.Entry<K, V> a(final Map.Entry<? extends K, ? extends V> entry) {
        Preconditions.checkNotNull(entry);
        return new f<K, V>() { // from class: com.google.common.collect.Maps.11
            /* JADX WARN: Type inference failed for: r0v1, types: [java.lang.Object, K] */
            @Override // com.google.common.collect.f, java.util.Map.Entry
            public K getKey() {
                return entry.getKey();
            }

            /* JADX WARN: Type inference failed for: r0v1, types: [V, java.lang.Object] */
            @Override // com.google.common.collect.f, java.util.Map.Entry
            public V getValue() {
                return entry.getValue();
            }
        };
    }

    public static <K, V> UnmodifiableIterator<Map.Entry<K, V>> c(final Iterator<Map.Entry<K, V>> it) {
        return new UnmodifiableIterator<Map.Entry<K, V>>() { // from class: com.google.common.collect.Maps.12
            @Override // java.util.Iterator
            public boolean hasNext() {
                return it.hasNext();
            }

            /* renamed from: a */
            public Map.Entry<K, V> next() {
                return Maps.a((Map.Entry) it.next());
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public static class y<K, V> extends ForwardingCollection<Map.Entry<K, V>> {
        private final Collection<Map.Entry<K, V>> a;

        public y(Collection<Map.Entry<K, V>> collection) {
            this.a = collection;
        }

        @Override // com.google.common.collect.ForwardingCollection, com.google.common.collect.ForwardingObject
        public Collection<Map.Entry<K, V>> delegate() {
            return this.a;
        }

        @Override // com.google.common.collect.ForwardingCollection, java.util.Collection, java.lang.Iterable
        public Iterator<Map.Entry<K, V>> iterator() {
            return Maps.c(this.a.iterator());
        }

        @Override // com.google.common.collect.ForwardingCollection, java.util.Collection
        public Object[] toArray() {
            return standardToArray();
        }

        @Override // com.google.common.collect.ForwardingCollection, java.util.Collection
        public <T> T[] toArray(T[] tArr) {
            return (T[]) standardToArray(tArr);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public static class z<K, V> extends y<K, V> implements Set<Map.Entry<K, V>> {
        z(Set<Map.Entry<K, V>> set) {
            super(set);
        }

        @Override // java.util.Collection, java.util.Set
        public boolean equals(@NullableDecl Object obj) {
            return Sets.a(this, obj);
        }

        @Override // java.util.Collection, java.util.Set
        public int hashCode() {
            return Sets.a(this);
        }
    }

    @Beta
    public static <A, B> Converter<A, B> asConverter(BiMap<A, B> biMap) {
        return new c(biMap);
    }

    /* loaded from: classes2.dex */
    private static final class c<A, B> extends Converter<A, B> implements Serializable {
        private static final long serialVersionUID = 0;
        private final BiMap<A, B> bimap;

        c(BiMap<A, B> biMap) {
            this.bimap = (BiMap) Preconditions.checkNotNull(biMap);
        }

        @Override // com.google.common.base.Converter
        protected B doForward(A a) {
            return (B) a(this.bimap, a);
        }

        @Override // com.google.common.base.Converter
        protected A doBackward(B b) {
            return (A) a(this.bimap.inverse(), b);
        }

        private static <X, Y> Y a(BiMap<X, Y> biMap, X x) {
            Y y = biMap.get(x);
            Preconditions.checkArgument(y != null, "No non-null mapping present for input: %s", x);
            return y;
        }

        @Override // com.google.common.base.Converter, com.google.common.base.Function
        public boolean equals(@NullableDecl Object obj) {
            if (obj instanceof c) {
                return this.bimap.equals(((c) obj).bimap);
            }
            return false;
        }

        public int hashCode() {
            return this.bimap.hashCode();
        }

        public String toString() {
            return "Maps.asConverter(" + this.bimap + ")";
        }
    }

    public static <K, V> BiMap<K, V> synchronizedBiMap(BiMap<K, V> biMap) {
        return cn.a((BiMap) biMap, (Object) null);
    }

    public static <K, V> BiMap<K, V> unmodifiableBiMap(BiMap<? extends K, ? extends V> biMap) {
        return new x(biMap, null);
    }

    /* loaded from: classes2.dex */
    private static class x<K, V> extends ForwardingMap<K, V> implements BiMap<K, V>, Serializable {
        private static final long serialVersionUID = 0;
        @MonotonicNonNullDecl
        transient Set<V> a;
        final BiMap<? extends K, ? extends V> delegate;
        @RetainedWith
        @MonotonicNonNullDecl
        BiMap<V, K> inverse;
        final Map<K, V> unmodifiableMap;

        x(BiMap<? extends K, ? extends V> biMap, @NullableDecl BiMap<V, K> biMap2) {
            this.unmodifiableMap = Collections.unmodifiableMap(biMap);
            this.delegate = biMap;
            this.inverse = biMap2;
        }

        @Override // com.google.common.collect.ForwardingMap, com.google.common.collect.ForwardingObject
        public Map<K, V> delegate() {
            return this.unmodifiableMap;
        }

        @Override // com.google.common.collect.BiMap
        public V forcePut(K k, V v) {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.common.collect.BiMap
        public BiMap<V, K> inverse() {
            BiMap<V, K> biMap = this.inverse;
            if (biMap != null) {
                return biMap;
            }
            x xVar = new x(this.delegate.inverse(), this);
            this.inverse = xVar;
            return xVar;
        }

        @Override // com.google.common.collect.ForwardingMap, java.util.Map, com.google.common.collect.BiMap
        public Set<V> values() {
            Set<V> set = this.a;
            if (set != null) {
                return set;
            }
            Set<V> unmodifiableSet = Collections.unmodifiableSet(this.delegate.values());
            this.a = unmodifiableSet;
            return unmodifiableSet;
        }
    }

    public static <K, V1, V2> Map<K, V2> transformValues(Map<K, V1> map, Function<? super V1, V2> function) {
        return transformEntries(map, a(function));
    }

    public static <K, V1, V2> SortedMap<K, V2> transformValues(SortedMap<K, V1> sortedMap, Function<? super V1, V2> function) {
        return transformEntries((SortedMap) sortedMap, a(function));
    }

    @GwtIncompatible
    public static <K, V1, V2> NavigableMap<K, V2> transformValues(NavigableMap<K, V1> navigableMap, Function<? super V1, V2> function) {
        return transformEntries((NavigableMap) navigableMap, a(function));
    }

    public static <K, V1, V2> Map<K, V2> transformEntries(Map<K, V1> map, EntryTransformer<? super K, ? super V1, V2> entryTransformer) {
        return new u(map, entryTransformer);
    }

    public static <K, V1, V2> SortedMap<K, V2> transformEntries(SortedMap<K, V1> sortedMap, EntryTransformer<? super K, ? super V1, V2> entryTransformer) {
        return new w(sortedMap, entryTransformer);
    }

    @GwtIncompatible
    public static <K, V1, V2> NavigableMap<K, V2> transformEntries(NavigableMap<K, V1> navigableMap, EntryTransformer<? super K, ? super V1, V2> entryTransformer) {
        return new v(navigableMap, entryTransformer);
    }

    public static <K, V1, V2> EntryTransformer<K, V1, V2> a(final Function<? super V1, V2> function) {
        Preconditions.checkNotNull(function);
        return new EntryTransformer<K, V1, V2>() { // from class: com.google.common.collect.Maps.13
            /* JADX WARN: Type inference failed for: r1v2, types: [java.lang.Object, V2] */
            @Override // com.google.common.collect.Maps.EntryTransformer
            public V2 transformEntry(K k2, V1 v1) {
                return function.apply(v1);
            }
        };
    }

    public static <K, V1, V2> Function<V1, V2> a(final EntryTransformer<? super K, V1, V2> entryTransformer, final K k2) {
        Preconditions.checkNotNull(entryTransformer);
        return new Function<V1, V2>() { // from class: com.google.common.collect.Maps.2
            /* JADX WARN: Type inference failed for: r3v1, types: [java.lang.Object, V2] */
            @Override // com.google.common.base.Function
            public V2 apply(@NullableDecl V1 v1) {
                return entryTransformer.transformEntry(k2, v1);
            }
        };
    }

    public static <K, V1, V2> Function<Map.Entry<K, V1>, V2> a(final EntryTransformer<? super K, ? super V1, V2> entryTransformer) {
        Preconditions.checkNotNull(entryTransformer);
        return new Function<Map.Entry<K, V1>, V2>() { // from class: com.google.common.collect.Maps.3
            /* JADX WARN: Type inference failed for: r3v2, types: [java.lang.Object, V2] */
            /* renamed from: a */
            public V2 apply(Map.Entry<K, V1> entry) {
                return entryTransformer.transformEntry(entry.getKey(), entry.getValue());
            }
        };
    }

    static <V2, K, V1> Map.Entry<K, V2> a(final EntryTransformer<? super K, ? super V1, V2> entryTransformer, final Map.Entry<K, V1> entry) {
        Preconditions.checkNotNull(entryTransformer);
        Preconditions.checkNotNull(entry);
        return new f<K, V2>() { // from class: com.google.common.collect.Maps.4
            /* JADX WARN: Type inference failed for: r0v1, types: [java.lang.Object, K] */
            @Override // com.google.common.collect.f, java.util.Map.Entry
            public K getKey() {
                return entry.getKey();
            }

            /* JADX WARN: Type inference failed for: r0v1, types: [java.lang.Object, V2] */
            @Override // com.google.common.collect.f, java.util.Map.Entry
            public V2 getValue() {
                return entryTransformer.transformEntry(entry.getKey(), entry.getValue());
            }
        };
    }

    public static <K, V1, V2> Function<Map.Entry<K, V1>, Map.Entry<K, V2>> b(final EntryTransformer<? super K, ? super V1, V2> entryTransformer) {
        Preconditions.checkNotNull(entryTransformer);
        return new Function<Map.Entry<K, V1>, Map.Entry<K, V2>>() { // from class: com.google.common.collect.Maps.5
            /* renamed from: a */
            public Map.Entry<K, V2> apply(Map.Entry<K, V1> entry) {
                return Maps.a(entryTransformer, (Map.Entry) entry);
            }
        };
    }

    /* loaded from: classes2.dex */
    public static class u<K, V1, V2> extends m<K, V2> {
        final Map<K, V1> a;
        final EntryTransformer<? super K, ? super V1, V2> b;

        u(Map<K, V1> map, EntryTransformer<? super K, ? super V1, V2> entryTransformer) {
            this.a = (Map) Preconditions.checkNotNull(map);
            this.b = (EntryTransformer) Preconditions.checkNotNull(entryTransformer);
        }

        @Override // java.util.AbstractMap, java.util.Map
        public int size() {
            return this.a.size();
        }

        @Override // java.util.AbstractMap, java.util.Map
        public boolean containsKey(Object obj) {
            return this.a.containsKey(obj);
        }

        @Override // java.util.AbstractMap, java.util.Map
        public V2 get(Object obj) {
            V1 v1 = this.a.get(obj);
            if (v1 != null || this.a.containsKey(obj)) {
                return this.b.transformEntry(obj, v1);
            }
            return null;
        }

        @Override // java.util.AbstractMap, java.util.Map
        public V2 remove(Object obj) {
            if (this.a.containsKey(obj)) {
                return this.b.transformEntry(obj, (V1) this.a.remove(obj));
            }
            return null;
        }

        @Override // com.google.common.collect.Maps.m, java.util.AbstractMap, java.util.Map
        public void clear() {
            this.a.clear();
        }

        @Override // java.util.AbstractMap, java.util.Map
        public Set<K> keySet() {
            return this.a.keySet();
        }

        @Override // com.google.common.collect.Maps.m
        Iterator<Map.Entry<K, V2>> b() {
            return Iterators.transform(this.a.entrySet().iterator(), Maps.b(this.b));
        }

        @Override // java.util.AbstractMap, java.util.Map
        public Collection<V2> values() {
            return new ac(this);
        }
    }

    /* loaded from: classes2.dex */
    public static class w<K, V1, V2> extends u<K, V1, V2> implements SortedMap<K, V2> {
        protected SortedMap<K, V1> c() {
            return (SortedMap) this.a;
        }

        w(SortedMap<K, V1> sortedMap, EntryTransformer<? super K, ? super V1, V2> entryTransformer) {
            super(sortedMap, entryTransformer);
        }

        @Override // java.util.SortedMap
        public Comparator<? super K> comparator() {
            return c().comparator();
        }

        @Override // java.util.SortedMap
        public K firstKey() {
            return c().firstKey();
        }

        public SortedMap<K, V2> headMap(K k) {
            return Maps.transformEntries((SortedMap) c().headMap(k), this.b);
        }

        @Override // java.util.SortedMap
        public K lastKey() {
            return c().lastKey();
        }

        public SortedMap<K, V2> subMap(K k, K k2) {
            return Maps.transformEntries((SortedMap) c().subMap(k, k2), this.b);
        }

        public SortedMap<K, V2> tailMap(K k) {
            return Maps.transformEntries((SortedMap) c().tailMap(k), this.b);
        }
    }

    @GwtIncompatible
    /* loaded from: classes2.dex */
    public static class v<K, V1, V2> extends w<K, V1, V2> implements NavigableMap<K, V2> {
        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.common.collect.Maps.w, java.util.SortedMap, java.util.NavigableMap
        public /* synthetic */ SortedMap headMap(Object obj) {
            return a((v<K, V1, V2>) obj);
        }

        v(NavigableMap<K, V1> navigableMap, EntryTransformer<? super K, ? super V1, V2> entryTransformer) {
            super(navigableMap, entryTransformer);
        }

        @Override // java.util.NavigableMap
        public Map.Entry<K, V2> ceilingEntry(K k) {
            return a((Map.Entry) c().ceilingEntry(k));
        }

        @Override // java.util.NavigableMap
        public K ceilingKey(K k) {
            return c().ceilingKey(k);
        }

        @Override // java.util.NavigableMap
        public NavigableSet<K> descendingKeySet() {
            return c().descendingKeySet();
        }

        @Override // java.util.NavigableMap
        public NavigableMap<K, V2> descendingMap() {
            return Maps.transformEntries((NavigableMap) c().descendingMap(), this.b);
        }

        @Override // java.util.NavigableMap
        public Map.Entry<K, V2> firstEntry() {
            return a((Map.Entry) c().firstEntry());
        }

        @Override // java.util.NavigableMap
        public Map.Entry<K, V2> floorEntry(K k) {
            return a((Map.Entry) c().floorEntry(k));
        }

        @Override // java.util.NavigableMap
        public K floorKey(K k) {
            return c().floorKey(k);
        }

        public NavigableMap<K, V2> a(K k) {
            return headMap(k, false);
        }

        @Override // java.util.NavigableMap
        public NavigableMap<K, V2> headMap(K k, boolean z) {
            return Maps.transformEntries((NavigableMap) c().headMap(k, z), this.b);
        }

        @Override // java.util.NavigableMap
        public Map.Entry<K, V2> higherEntry(K k) {
            return a((Map.Entry) c().higherEntry(k));
        }

        @Override // java.util.NavigableMap
        public K higherKey(K k) {
            return c().higherKey(k);
        }

        @Override // java.util.NavigableMap
        public Map.Entry<K, V2> lastEntry() {
            return a((Map.Entry) c().lastEntry());
        }

        @Override // java.util.NavigableMap
        public Map.Entry<K, V2> lowerEntry(K k) {
            return a((Map.Entry) c().lowerEntry(k));
        }

        @Override // java.util.NavigableMap
        public K lowerKey(K k) {
            return c().lowerKey(k);
        }

        @Override // java.util.NavigableMap
        public NavigableSet<K> navigableKeySet() {
            return c().navigableKeySet();
        }

        @Override // java.util.NavigableMap
        public Map.Entry<K, V2> pollFirstEntry() {
            return a((Map.Entry) c().pollFirstEntry());
        }

        @Override // java.util.NavigableMap
        public Map.Entry<K, V2> pollLastEntry() {
            return a((Map.Entry) c().pollLastEntry());
        }

        @Override // java.util.NavigableMap
        public NavigableMap<K, V2> subMap(K k, boolean z, K k2, boolean z2) {
            return Maps.transformEntries((NavigableMap) c().subMap(k, z, k2, z2), this.b);
        }

        /* renamed from: a */
        public NavigableMap<K, V2> subMap(K k, K k2) {
            return subMap(k, true, k2, false);
        }

        /* renamed from: b */
        public NavigableMap<K, V2> tailMap(K k) {
            return tailMap(k, true);
        }

        @Override // java.util.NavigableMap
        public NavigableMap<K, V2> tailMap(K k, boolean z) {
            return Maps.transformEntries((NavigableMap) c().tailMap(k, z), this.b);
        }

        @NullableDecl
        private Map.Entry<K, V2> a(@NullableDecl Map.Entry<K, V1> entry) {
            if (entry == null) {
                return null;
            }
            return Maps.a(this.b, (Map.Entry) entry);
        }

        /* renamed from: a */
        public NavigableMap<K, V1> c() {
            return (NavigableMap) super.c();
        }
    }

    public static <K> Predicate<Map.Entry<K, ?>> a(Predicate<? super K> predicate) {
        return Predicates.compose(predicate, a());
    }

    public static <V> Predicate<Map.Entry<?, V>> b(Predicate<? super V> predicate) {
        return Predicates.compose(predicate, b());
    }

    public static <K, V> Map<K, V> filterKeys(Map<K, V> map, Predicate<? super K> predicate) {
        Preconditions.checkNotNull(predicate);
        Predicate a2 = a(predicate);
        if (map instanceof a) {
            return a((a) map, a2);
        }
        return new k((Map) Preconditions.checkNotNull(map), predicate, a2);
    }

    public static <K, V> SortedMap<K, V> filterKeys(SortedMap<K, V> sortedMap, Predicate<? super K> predicate) {
        return filterEntries((SortedMap) sortedMap, a(predicate));
    }

    @GwtIncompatible
    public static <K, V> NavigableMap<K, V> filterKeys(NavigableMap<K, V> navigableMap, Predicate<? super K> predicate) {
        return filterEntries((NavigableMap) navigableMap, a(predicate));
    }

    public static <K, V> BiMap<K, V> filterKeys(BiMap<K, V> biMap, Predicate<? super K> predicate) {
        Preconditions.checkNotNull(predicate);
        return filterEntries((BiMap) biMap, a(predicate));
    }

    public static <K, V> Map<K, V> filterValues(Map<K, V> map, Predicate<? super V> predicate) {
        return filterEntries(map, b(predicate));
    }

    public static <K, V> SortedMap<K, V> filterValues(SortedMap<K, V> sortedMap, Predicate<? super V> predicate) {
        return filterEntries((SortedMap) sortedMap, b(predicate));
    }

    @GwtIncompatible
    public static <K, V> NavigableMap<K, V> filterValues(NavigableMap<K, V> navigableMap, Predicate<? super V> predicate) {
        return filterEntries((NavigableMap) navigableMap, b(predicate));
    }

    public static <K, V> BiMap<K, V> filterValues(BiMap<K, V> biMap, Predicate<? super V> predicate) {
        return filterEntries((BiMap) biMap, b(predicate));
    }

    public static <K, V> Map<K, V> filterEntries(Map<K, V> map, Predicate<? super Map.Entry<K, V>> predicate) {
        Preconditions.checkNotNull(predicate);
        if (map instanceof a) {
            return a((a) map, (Predicate) predicate);
        }
        return new h((Map) Preconditions.checkNotNull(map), predicate);
    }

    public static <K, V> SortedMap<K, V> filterEntries(SortedMap<K, V> sortedMap, Predicate<? super Map.Entry<K, V>> predicate) {
        Preconditions.checkNotNull(predicate);
        if (sortedMap instanceof j) {
            return a((j) sortedMap, (Predicate) predicate);
        }
        return new j((SortedMap) Preconditions.checkNotNull(sortedMap), predicate);
    }

    @GwtIncompatible
    public static <K, V> NavigableMap<K, V> filterEntries(NavigableMap<K, V> navigableMap, Predicate<? super Map.Entry<K, V>> predicate) {
        Preconditions.checkNotNull(predicate);
        if (navigableMap instanceof i) {
            return a((i) navigableMap, (Predicate) predicate);
        }
        return new i((NavigableMap) Preconditions.checkNotNull(navigableMap), predicate);
    }

    public static <K, V> BiMap<K, V> filterEntries(BiMap<K, V> biMap, Predicate<? super Map.Entry<K, V>> predicate) {
        Preconditions.checkNotNull(biMap);
        Preconditions.checkNotNull(predicate);
        return biMap instanceof g ? a((g) biMap, (Predicate) predicate) : new g(biMap, predicate);
    }

    private static <K, V> Map<K, V> a(a<K, V> aVar, Predicate<? super Map.Entry<K, V>> predicate) {
        return new h(aVar.a, Predicates.and(aVar.b, predicate));
    }

    private static <K, V> SortedMap<K, V> a(j<K, V> jVar, Predicate<? super Map.Entry<K, V>> predicate) {
        return new j(jVar.c(), Predicates.and(jVar.b, predicate));
    }

    @GwtIncompatible
    private static <K, V> NavigableMap<K, V> a(i<K, V> iVar, Predicate<? super Map.Entry<K, V>> predicate) {
        return new i(((i) iVar).a, Predicates.and(((i) iVar).b, predicate));
    }

    private static <K, V> BiMap<K, V> a(g<K, V> gVar, Predicate<? super Map.Entry<K, V>> predicate) {
        return new g(gVar.c(), Predicates.and(gVar.b, predicate));
    }

    /* loaded from: classes2.dex */
    public static abstract class a<K, V> extends ad<K, V> {
        final Map<K, V> a;
        final Predicate<? super Map.Entry<K, V>> b;

        a(Map<K, V> map, Predicate<? super Map.Entry<K, V>> predicate) {
            this.a = map;
            this.b = predicate;
        }

        boolean a(@NullableDecl Object obj, @NullableDecl V v) {
            return this.b.apply(Maps.immutableEntry(obj, v));
        }

        @Override // java.util.AbstractMap, java.util.Map
        public V put(K k, V v) {
            Preconditions.checkArgument(a(k, v));
            return this.a.put(k, v);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // java.util.AbstractMap, java.util.Map
        public void putAll(Map<? extends K, ? extends V> map) {
            for (Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
                Preconditions.checkArgument(a(entry.getKey(), entry.getValue()));
            }
            this.a.putAll(map);
        }

        @Override // java.util.AbstractMap, java.util.Map
        public boolean containsKey(Object obj) {
            return this.a.containsKey(obj) && a(obj, this.a.get(obj));
        }

        @Override // java.util.AbstractMap, java.util.Map
        public V get(Object obj) {
            V v = this.a.get(obj);
            if (v == null || !a(obj, v)) {
                return null;
            }
            return v;
        }

        @Override // java.util.AbstractMap, java.util.Map
        public boolean isEmpty() {
            return entrySet().isEmpty();
        }

        @Override // java.util.AbstractMap, java.util.Map
        public V remove(Object obj) {
            if (containsKey(obj)) {
                return this.a.remove(obj);
            }
            return null;
        }

        @Override // com.google.common.collect.Maps.ad
        Collection<V> b() {
            return new l(this, this.a, this.b);
        }
    }

    /* loaded from: classes2.dex */
    private static final class l<K, V> extends ac<K, V> {
        final Map<K, V> a;
        final Predicate<? super Map.Entry<K, V>> b;

        l(Map<K, V> map, Map<K, V> map2, Predicate<? super Map.Entry<K, V>> predicate) {
            super(map);
            this.a = map2;
            this.b = predicate;
        }

        @Override // com.google.common.collect.Maps.ac, java.util.AbstractCollection, java.util.Collection
        public boolean remove(Object obj) {
            Iterator<Map.Entry<K, V>> it = this.a.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<K, V> next = it.next();
                if (this.b.apply(next) && Objects.equal(next.getValue(), obj)) {
                    it.remove();
                    return true;
                }
            }
            return false;
        }

        @Override // com.google.common.collect.Maps.ac, java.util.AbstractCollection, java.util.Collection
        public boolean removeAll(Collection<?> collection) {
            Iterator<Map.Entry<K, V>> it = this.a.entrySet().iterator();
            boolean z = false;
            while (it.hasNext()) {
                Map.Entry<K, V> next = it.next();
                if (this.b.apply(next) && collection.contains(next.getValue())) {
                    it.remove();
                    z = true;
                }
            }
            return z;
        }

        @Override // com.google.common.collect.Maps.ac, java.util.AbstractCollection, java.util.Collection
        public boolean retainAll(Collection<?> collection) {
            Iterator<Map.Entry<K, V>> it = this.a.entrySet().iterator();
            boolean z = false;
            while (it.hasNext()) {
                Map.Entry<K, V> next = it.next();
                if (this.b.apply(next) && !collection.contains(next.getValue())) {
                    it.remove();
                    z = true;
                }
            }
            return z;
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public Object[] toArray() {
            return Lists.newArrayList(iterator()).toArray();
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public <T> T[] toArray(T[] tArr) {
            return (T[]) Lists.newArrayList(iterator()).toArray(tArr);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class k<K, V> extends a<K, V> {
        final Predicate<? super K> c;

        k(Map<K, V> map, Predicate<? super K> predicate, Predicate<? super Map.Entry<K, V>> predicate2) {
            super(map, predicate2);
            this.c = predicate;
        }

        @Override // com.google.common.collect.Maps.ad
        protected Set<Map.Entry<K, V>> a() {
            return Sets.filter(this.a.entrySet(), this.b);
        }

        @Override // com.google.common.collect.Maps.ad
        Set<K> h() {
            return Sets.filter(this.a.keySet(), this.c);
        }

        @Override // com.google.common.collect.Maps.a, java.util.AbstractMap, java.util.Map
        public boolean containsKey(Object obj) {
            return this.a.containsKey(obj) && this.c.apply(obj);
        }
    }

    /* loaded from: classes2.dex */
    public static class h<K, V> extends a<K, V> {
        final Set<Map.Entry<K, V>> c;

        h(Map<K, V> map, Predicate<? super Map.Entry<K, V>> predicate) {
            super(map, predicate);
            this.c = Sets.filter(map.entrySet(), this.b);
        }

        @Override // com.google.common.collect.Maps.ad
        protected Set<Map.Entry<K, V>> a() {
            return new a();
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* loaded from: classes2.dex */
        public class a extends ForwardingSet<Map.Entry<K, V>> {
            private a() {
                h.this = r1;
            }

            @Override // com.google.common.collect.ForwardingSet, com.google.common.collect.ForwardingCollection, com.google.common.collect.ForwardingObject
            public Set<Map.Entry<K, V>> delegate() {
                return h.this.c;
            }

            @Override // com.google.common.collect.ForwardingCollection, java.util.Collection, java.lang.Iterable
            public Iterator<Map.Entry<K, V>> iterator() {
                return new cp<Map.Entry<K, V>, Map.Entry<K, V>>(h.this.c.iterator()) { // from class: com.google.common.collect.Maps.h.a.1
                    @Override // com.google.common.collect.cp
                    /* bridge */ /* synthetic */ Object a(Object obj) {
                        return a((Map.Entry) ((Map.Entry) obj));
                    }

                    Map.Entry<K, V> a(final Map.Entry<K, V> entry) {
                        return new ForwardingMapEntry<K, V>() { // from class: com.google.common.collect.Maps.h.a.1.1
                            @Override // com.google.common.collect.ForwardingMapEntry, com.google.common.collect.ForwardingObject
                            public Map.Entry<K, V> delegate() {
                                return entry;
                            }

                            @Override // com.google.common.collect.ForwardingMapEntry, java.util.Map.Entry
                            public V setValue(V v) {
                                Preconditions.checkArgument(h.this.a(getKey(), v));
                                return (V) super.setValue(v);
                            }
                        };
                    }
                };
            }
        }

        @Override // com.google.common.collect.Maps.ad
        Set<K> h() {
            return new b();
        }

        static <K, V> boolean a(Map<K, V> map, Predicate<? super Map.Entry<K, V>> predicate, Collection<?> collection) {
            Iterator<Map.Entry<K, V>> it = map.entrySet().iterator();
            boolean z = false;
            while (it.hasNext()) {
                Map.Entry<K, V> next = it.next();
                if (predicate.apply(next) && collection.contains(next.getKey())) {
                    it.remove();
                    z = true;
                }
            }
            return z;
        }

        static <K, V> boolean b(Map<K, V> map, Predicate<? super Map.Entry<K, V>> predicate, Collection<?> collection) {
            Iterator<Map.Entry<K, V>> it = map.entrySet().iterator();
            boolean z = false;
            while (it.hasNext()) {
                Map.Entry<K, V> next = it.next();
                if (predicate.apply(next) && !collection.contains(next.getKey())) {
                    it.remove();
                    z = true;
                }
            }
            return z;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* loaded from: classes2.dex */
        public class b extends n<K, V> {
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            b() {
                super(r1);
                h.this = r1;
            }

            @Override // com.google.common.collect.Maps.n, java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean remove(Object obj) {
                if (!h.this.containsKey(obj)) {
                    return false;
                }
                h.this.a.remove(obj);
                return true;
            }

            @Override // com.google.common.collect.Sets.f, java.util.AbstractSet, java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean removeAll(Collection<?> collection) {
                return h.a(h.this.a, h.this.b, collection);
            }

            @Override // com.google.common.collect.Sets.f, java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean retainAll(Collection<?> collection) {
                return h.b(h.this.a, h.this.b, collection);
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public Object[] toArray() {
                return Lists.newArrayList(iterator()).toArray();
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public <T> T[] toArray(T[] tArr) {
                return (T[]) Lists.newArrayList(iterator()).toArray(tArr);
            }
        }
    }

    /* loaded from: classes2.dex */
    public static class j<K, V> extends h<K, V> implements SortedMap<K, V> {
        j(SortedMap<K, V> sortedMap, Predicate<? super Map.Entry<K, V>> predicate) {
            super(sortedMap, predicate);
        }

        SortedMap<K, V> c() {
            return (SortedMap) this.a;
        }

        /* renamed from: d */
        public SortedSet<K> keySet() {
            return (SortedSet) super.keySet();
        }

        /* renamed from: e */
        public SortedSet<K> h() {
            return new a();
        }

        /* loaded from: classes2.dex */
        public class a extends h<K, V>.b implements SortedSet<K> {
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            a() {
                super();
                j.this = r1;
            }

            @Override // java.util.SortedSet
            public Comparator<? super K> comparator() {
                return j.this.c().comparator();
            }

            @Override // java.util.SortedSet
            public SortedSet<K> subSet(K k, K k2) {
                return (SortedSet) j.this.subMap(k, k2).keySet();
            }

            @Override // java.util.SortedSet
            public SortedSet<K> headSet(K k) {
                return (SortedSet) j.this.headMap(k).keySet();
            }

            @Override // java.util.SortedSet
            public SortedSet<K> tailSet(K k) {
                return (SortedSet) j.this.tailMap(k).keySet();
            }

            @Override // java.util.SortedSet
            public K first() {
                return (K) j.this.firstKey();
            }

            @Override // java.util.SortedSet
            public K last() {
                return (K) j.this.lastKey();
            }
        }

        @Override // java.util.SortedMap
        public Comparator<? super K> comparator() {
            return c().comparator();
        }

        @Override // java.util.SortedMap
        public K firstKey() {
            return keySet().iterator().next();
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // java.util.SortedMap
        public K lastKey() {
            SortedMap<K, V> c = c();
            while (true) {
                K lastKey = c.lastKey();
                if (a(lastKey, this.a.get(lastKey))) {
                    return lastKey;
                }
                c = c().headMap(lastKey);
            }
        }

        @Override // java.util.SortedMap
        public SortedMap<K, V> headMap(K k) {
            return new j(c().headMap(k), this.b);
        }

        @Override // java.util.SortedMap
        public SortedMap<K, V> subMap(K k, K k2) {
            return new j(c().subMap(k, k2), this.b);
        }

        @Override // java.util.SortedMap
        public SortedMap<K, V> tailMap(K k) {
            return new j(c().tailMap(k), this.b);
        }
    }

    @GwtIncompatible
    /* loaded from: classes2.dex */
    public static class i<K, V> extends i<K, V> {
        private final NavigableMap<K, V> a;
        private final Predicate<? super Map.Entry<K, V>> b;
        private final Map<K, V> c;

        i(NavigableMap<K, V> navigableMap, Predicate<? super Map.Entry<K, V>> predicate) {
            this.a = (NavigableMap) Preconditions.checkNotNull(navigableMap);
            this.b = predicate;
            this.c = new h(navigableMap, predicate);
        }

        @Override // java.util.SortedMap
        public Comparator<? super K> comparator() {
            return this.a.comparator();
        }

        @Override // com.google.common.collect.i, java.util.NavigableMap
        public NavigableSet<K> navigableKeySet() {
            return new q<K, V>(this) { // from class: com.google.common.collect.Maps.i.1
                @Override // com.google.common.collect.Sets.f, java.util.AbstractSet, java.util.AbstractCollection, java.util.Collection, java.util.Set
                public boolean removeAll(Collection<?> collection) {
                    return h.a(i.this.a, i.this.b, collection);
                }

                @Override // com.google.common.collect.Sets.f, java.util.AbstractCollection, java.util.Collection, java.util.Set
                public boolean retainAll(Collection<?> collection) {
                    return h.b(i.this.a, i.this.b, collection);
                }
            };
        }

        @Override // java.util.AbstractMap, java.util.Map, java.util.SortedMap
        public Collection<V> values() {
            return new l(this, this.a, this.b);
        }

        @Override // com.google.common.collect.Maps.m
        Iterator<Map.Entry<K, V>> b() {
            return Iterators.filter(this.a.entrySet().iterator(), this.b);
        }

        @Override // com.google.common.collect.i
        Iterator<Map.Entry<K, V>> a() {
            return Iterators.filter(this.a.descendingMap().entrySet().iterator(), this.b);
        }

        @Override // java.util.AbstractMap, java.util.Map
        public int size() {
            return this.c.size();
        }

        @Override // java.util.AbstractMap, java.util.Map
        public boolean isEmpty() {
            return !Iterables.any(this.a.entrySet(), this.b);
        }

        @Override // java.util.AbstractMap, java.util.Map
        @NullableDecl
        public V get(@NullableDecl Object obj) {
            return this.c.get(obj);
        }

        @Override // java.util.AbstractMap, java.util.Map
        public boolean containsKey(@NullableDecl Object obj) {
            return this.c.containsKey(obj);
        }

        @Override // java.util.AbstractMap, java.util.Map
        public V put(K k, V v) {
            return this.c.put(k, v);
        }

        @Override // java.util.AbstractMap, java.util.Map
        public V remove(@NullableDecl Object obj) {
            return this.c.remove(obj);
        }

        @Override // java.util.AbstractMap, java.util.Map
        public void putAll(Map<? extends K, ? extends V> map) {
            this.c.putAll(map);
        }

        @Override // com.google.common.collect.Maps.m, java.util.AbstractMap, java.util.Map
        public void clear() {
            this.c.clear();
        }

        @Override // com.google.common.collect.Maps.m, java.util.AbstractMap, java.util.Map, java.util.SortedMap
        public Set<Map.Entry<K, V>> entrySet() {
            return this.c.entrySet();
        }

        @Override // com.google.common.collect.i, java.util.NavigableMap
        public Map.Entry<K, V> pollFirstEntry() {
            return (Map.Entry) Iterables.a(this.a.entrySet(), this.b);
        }

        @Override // com.google.common.collect.i, java.util.NavigableMap
        public Map.Entry<K, V> pollLastEntry() {
            return (Map.Entry) Iterables.a(this.a.descendingMap().entrySet(), this.b);
        }

        @Override // com.google.common.collect.i, java.util.NavigableMap
        public NavigableMap<K, V> descendingMap() {
            return Maps.filterEntries((NavigableMap) this.a.descendingMap(), (Predicate) this.b);
        }

        @Override // java.util.NavigableMap
        public NavigableMap<K, V> subMap(K k, boolean z, K k2, boolean z2) {
            return Maps.filterEntries((NavigableMap) this.a.subMap(k, z, k2, z2), (Predicate) this.b);
        }

        @Override // java.util.NavigableMap
        public NavigableMap<K, V> headMap(K k, boolean z) {
            return Maps.filterEntries((NavigableMap) this.a.headMap(k, z), (Predicate) this.b);
        }

        @Override // java.util.NavigableMap
        public NavigableMap<K, V> tailMap(K k, boolean z) {
            return Maps.filterEntries((NavigableMap) this.a.tailMap(k, z), (Predicate) this.b);
        }
    }

    /* loaded from: classes2.dex */
    public static final class g<K, V> extends h<K, V> implements BiMap<K, V> {
        @RetainedWith
        private final BiMap<V, K> d;

        private static <K, V> Predicate<Map.Entry<V, K>> a(final Predicate<? super Map.Entry<K, V>> predicate) {
            return new Predicate<Map.Entry<V, K>>() { // from class: com.google.common.collect.Maps.g.1
                /* renamed from: a */
                public boolean apply(Map.Entry<V, K> entry) {
                    return predicate.apply(Maps.immutableEntry(entry.getValue(), entry.getKey()));
                }
            };
        }

        g(BiMap<K, V> biMap, Predicate<? super Map.Entry<K, V>> predicate) {
            super(biMap, predicate);
            this.d = new g(biMap.inverse(), a(predicate), this);
        }

        private g(BiMap<K, V> biMap, Predicate<? super Map.Entry<K, V>> predicate, BiMap<V, K> biMap2) {
            super(biMap, predicate);
            this.d = biMap2;
        }

        BiMap<K, V> c() {
            return (BiMap) this.a;
        }

        @Override // com.google.common.collect.BiMap
        public V forcePut(@NullableDecl K k, @NullableDecl V v) {
            Preconditions.checkArgument(a(k, v));
            return c().forcePut(k, v);
        }

        @Override // com.google.common.collect.BiMap
        public BiMap<V, K> inverse() {
            return this.d;
        }

        @Override // com.google.common.collect.Maps.ad, java.util.AbstractMap, java.util.Map
        public Set<V> values() {
            return this.d.keySet();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @GwtIncompatible
    public static <K, V> NavigableMap<K, V> unmodifiableNavigableMap(NavigableMap<K, ? extends V> navigableMap) {
        Preconditions.checkNotNull(navigableMap);
        return navigableMap instanceof aa ? navigableMap : new aa(navigableMap);
    }

    @NullableDecl
    public static <K, V> Map.Entry<K, V> e(@NullableDecl Map.Entry<K, ? extends V> entry) {
        if (entry == null) {
            return null;
        }
        return a(entry);
    }

    @GwtIncompatible
    /* loaded from: classes2.dex */
    public static class aa<K, V> extends ForwardingSortedMap<K, V> implements Serializable, NavigableMap<K, V> {
        @MonotonicNonNullDecl
        private transient aa<K, V> a;
        private final NavigableMap<K, ? extends V> delegate;

        aa(NavigableMap<K, ? extends V> navigableMap) {
            this.delegate = navigableMap;
        }

        aa(NavigableMap<K, ? extends V> navigableMap, aa<K, V> aaVar) {
            this.delegate = navigableMap;
            this.a = aaVar;
        }

        @Override // com.google.common.collect.ForwardingSortedMap, com.google.common.collect.ForwardingMap, com.google.common.collect.ForwardingObject
        public SortedMap<K, V> delegate() {
            return Collections.unmodifiableSortedMap(this.delegate);
        }

        @Override // java.util.NavigableMap
        public Map.Entry<K, V> lowerEntry(K k) {
            return Maps.e(this.delegate.lowerEntry(k));
        }

        @Override // java.util.NavigableMap
        public K lowerKey(K k) {
            return this.delegate.lowerKey(k);
        }

        @Override // java.util.NavigableMap
        public Map.Entry<K, V> floorEntry(K k) {
            return Maps.e(this.delegate.floorEntry(k));
        }

        @Override // java.util.NavigableMap
        public K floorKey(K k) {
            return this.delegate.floorKey(k);
        }

        @Override // java.util.NavigableMap
        public Map.Entry<K, V> ceilingEntry(K k) {
            return Maps.e(this.delegate.ceilingEntry(k));
        }

        @Override // java.util.NavigableMap
        public K ceilingKey(K k) {
            return this.delegate.ceilingKey(k);
        }

        @Override // java.util.NavigableMap
        public Map.Entry<K, V> higherEntry(K k) {
            return Maps.e(this.delegate.higherEntry(k));
        }

        @Override // java.util.NavigableMap
        public K higherKey(K k) {
            return this.delegate.higherKey(k);
        }

        @Override // java.util.NavigableMap
        public Map.Entry<K, V> firstEntry() {
            return Maps.e(this.delegate.firstEntry());
        }

        @Override // java.util.NavigableMap
        public Map.Entry<K, V> lastEntry() {
            return Maps.e(this.delegate.lastEntry());
        }

        @Override // java.util.NavigableMap
        public final Map.Entry<K, V> pollFirstEntry() {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.NavigableMap
        public final Map.Entry<K, V> pollLastEntry() {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.NavigableMap
        public NavigableMap<K, V> descendingMap() {
            aa<K, V> aaVar = this.a;
            if (aaVar != null) {
                return aaVar;
            }
            aa<K, V> aaVar2 = new aa<>(this.delegate.descendingMap(), this);
            this.a = aaVar2;
            return aaVar2;
        }

        @Override // com.google.common.collect.ForwardingMap, java.util.Map
        public Set<K> keySet() {
            return navigableKeySet();
        }

        @Override // java.util.NavigableMap
        public NavigableSet<K> navigableKeySet() {
            return Sets.unmodifiableNavigableSet(this.delegate.navigableKeySet());
        }

        @Override // java.util.NavigableMap
        public NavigableSet<K> descendingKeySet() {
            return Sets.unmodifiableNavigableSet(this.delegate.descendingKeySet());
        }

        @Override // com.google.common.collect.ForwardingSortedMap, java.util.SortedMap
        public SortedMap<K, V> subMap(K k, K k2) {
            return subMap(k, true, k2, false);
        }

        @Override // java.util.NavigableMap
        public NavigableMap<K, V> subMap(K k, boolean z, K k2, boolean z2) {
            return Maps.unmodifiableNavigableMap(this.delegate.subMap(k, z, k2, z2));
        }

        @Override // com.google.common.collect.ForwardingSortedMap, java.util.SortedMap
        public SortedMap<K, V> headMap(K k) {
            return headMap(k, false);
        }

        @Override // java.util.NavigableMap
        public NavigableMap<K, V> headMap(K k, boolean z) {
            return Maps.unmodifiableNavigableMap(this.delegate.headMap(k, z));
        }

        @Override // com.google.common.collect.ForwardingSortedMap, java.util.SortedMap
        public SortedMap<K, V> tailMap(K k) {
            return tailMap(k, true);
        }

        @Override // java.util.NavigableMap
        public NavigableMap<K, V> tailMap(K k, boolean z) {
            return Maps.unmodifiableNavigableMap(this.delegate.tailMap(k, z));
        }
    }

    @GwtIncompatible
    public static <K, V> NavigableMap<K, V> synchronizedNavigableMap(NavigableMap<K, V> navigableMap) {
        return cn.a(navigableMap);
    }

    @GwtCompatible
    /* loaded from: classes2.dex */
    public static abstract class ad<K, V> extends AbstractMap<K, V> {
        @MonotonicNonNullDecl
        private transient Set<Map.Entry<K, V>> a;
        @MonotonicNonNullDecl
        private transient Set<K> b;
        @MonotonicNonNullDecl
        private transient Collection<V> c;

        abstract Set<Map.Entry<K, V>> a();

        @Override // java.util.AbstractMap, java.util.Map
        public Set<Map.Entry<K, V>> entrySet() {
            Set<Map.Entry<K, V>> set = this.a;
            if (set != null) {
                return set;
            }
            Set<Map.Entry<K, V>> a = a();
            this.a = a;
            return a;
        }

        @Override // java.util.AbstractMap, java.util.Map
        public Set<K> keySet() {
            Set<K> set = this.b;
            if (set != null) {
                return set;
            }
            Set<K> h = h();
            this.b = h;
            return h;
        }

        Set<K> h() {
            return new n(this);
        }

        @Override // java.util.AbstractMap, java.util.Map
        public Collection<V> values() {
            Collection<V> collection = this.c;
            if (collection != null) {
                return collection;
            }
            Collection<V> b = b();
            this.c = b;
            return b;
        }

        Collection<V> b() {
            return new ac(this);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public static abstract class m<K, V> extends AbstractMap<K, V> {
        abstract Iterator<Map.Entry<K, V>> b();

        @Override // java.util.AbstractMap, java.util.Map, java.util.SortedMap
        public Set<Map.Entry<K, V>> entrySet() {
            return new f<K, V>() { // from class: com.google.common.collect.Maps.m.1
                @Override // com.google.common.collect.Maps.f
                Map<K, V> a() {
                    return m.this;
                }

                @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
                public Iterator<Map.Entry<K, V>> iterator() {
                    return m.this.b();
                }
            };
        }

        @Override // java.util.AbstractMap, java.util.Map
        public void clear() {
            Iterators.b(b());
        }
    }

    public static <V> V a(Map<?, V> map, @NullableDecl Object obj) {
        Preconditions.checkNotNull(map);
        try {
            return map.get(obj);
        } catch (ClassCastException | NullPointerException unused) {
            return null;
        }
    }

    public static boolean b(Map<?, ?> map, Object obj) {
        Preconditions.checkNotNull(map);
        try {
            return map.containsKey(obj);
        } catch (ClassCastException | NullPointerException unused) {
            return false;
        }
    }

    public static <V> V c(Map<?, V> map, Object obj) {
        Preconditions.checkNotNull(map);
        try {
            return map.remove(obj);
        } catch (ClassCastException | NullPointerException unused) {
            return null;
        }
    }

    public static boolean d(Map<?, ?> map, @NullableDecl Object obj) {
        return Iterators.contains(a(map.entrySet().iterator()), obj);
    }

    public static boolean e(Map<?, ?> map, @NullableDecl Object obj) {
        return Iterators.contains(b(map.entrySet().iterator()), obj);
    }

    public static <K, V> boolean a(Collection<Map.Entry<K, V>> collection, Object obj) {
        if (!(obj instanceof Map.Entry)) {
            return false;
        }
        return collection.contains(a((Map.Entry) obj));
    }

    public static <K, V> boolean b(Collection<Map.Entry<K, V>> collection, Object obj) {
        if (!(obj instanceof Map.Entry)) {
            return false;
        }
        return collection.remove(a((Map.Entry) obj));
    }

    public static boolean f(Map<?, ?> map, Object obj) {
        if (map == obj) {
            return true;
        }
        if (obj instanceof Map) {
            return map.entrySet().equals(((Map) obj).entrySet());
        }
        return false;
    }

    public static String a(Map<?, ?> map) {
        StringBuilder a2 = Collections2.a(map.size());
        a2.append('{');
        boolean z2 = true;
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            if (!z2) {
                a2.append(", ");
            }
            z2 = false;
            a2.append(entry.getKey());
            a2.append('=');
            a2.append(entry.getValue());
        }
        a2.append('}');
        return a2.toString();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static <K, V> void a(Map<K, V> map, Map<? extends K, ? extends V> map2) {
        for (Map.Entry<? extends K, ? extends V> entry : map2.entrySet()) {
            map.put(entry.getKey(), entry.getValue());
        }
    }

    /* loaded from: classes2.dex */
    public static class n<K, V> extends Sets.f<K> {
        @Weak
        final Map<K, V> d;

        public n(Map<K, V> map) {
            this.d = (Map) Preconditions.checkNotNull(map);
        }

        Map<K, V> c() {
            return this.d;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<K> iterator() {
            return Maps.a(c().entrySet().iterator());
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return c().size();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean isEmpty() {
            return c().isEmpty();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(Object obj) {
            return c().containsKey(obj);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(Object obj) {
            if (!contains(obj)) {
                return false;
            }
            c().remove(obj);
            return true;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public void clear() {
            c().clear();
        }
    }

    @NullableDecl
    public static <K> K b(@NullableDecl Map.Entry<K, ?> entry) {
        if (entry == null) {
            return null;
        }
        return entry.getKey();
    }

    @NullableDecl
    public static <V> V c(@NullableDecl Map.Entry<?, V> entry) {
        if (entry == null) {
            return null;
        }
        return entry.getValue();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public static class s<K, V> extends n<K, V> implements SortedSet<K> {
        public s(SortedMap<K, V> sortedMap) {
            super(sortedMap);
        }

        /* renamed from: b */
        public SortedMap<K, V> c() {
            return (SortedMap) super.c();
        }

        @Override // java.util.SortedSet
        public Comparator<? super K> comparator() {
            return c().comparator();
        }

        public SortedSet<K> subSet(K k, K k2) {
            return new s(c().subMap(k, k2));
        }

        public SortedSet<K> headSet(K k) {
            return new s(c().headMap(k));
        }

        public SortedSet<K> tailSet(K k) {
            return new s(c().tailMap(k));
        }

        @Override // java.util.SortedSet
        public K first() {
            return c().firstKey();
        }

        @Override // java.util.SortedSet
        public K last() {
            return c().lastKey();
        }
    }

    @GwtIncompatible
    /* loaded from: classes2.dex */
    public static class q<K, V> extends s<K, V> implements NavigableSet<K> {
        public q(NavigableMap<K, V> navigableMap) {
            super(navigableMap);
        }

        /* renamed from: a */
        public NavigableMap<K, V> c() {
            return (NavigableMap) this.d;
        }

        @Override // java.util.NavigableSet
        public K lower(K k) {
            return c().lowerKey(k);
        }

        @Override // java.util.NavigableSet
        public K floor(K k) {
            return c().floorKey(k);
        }

        @Override // java.util.NavigableSet
        public K ceiling(K k) {
            return c().ceilingKey(k);
        }

        @Override // java.util.NavigableSet
        public K higher(K k) {
            return c().higherKey(k);
        }

        @Override // java.util.NavigableSet
        public K pollFirst() {
            return (K) Maps.b(c().pollFirstEntry());
        }

        @Override // java.util.NavigableSet
        public K pollLast() {
            return (K) Maps.b(c().pollLastEntry());
        }

        @Override // java.util.NavigableSet
        public NavigableSet<K> descendingSet() {
            return c().descendingKeySet();
        }

        @Override // java.util.NavigableSet
        public Iterator<K> descendingIterator() {
            return descendingSet().iterator();
        }

        @Override // java.util.NavigableSet
        public NavigableSet<K> subSet(K k, boolean z, K k2, boolean z2) {
            return c().subMap(k, z, k2, z2).navigableKeySet();
        }

        @Override // com.google.common.collect.Maps.s, java.util.SortedSet, java.util.NavigableSet
        public SortedSet<K> subSet(K k, K k2) {
            return subSet(k, true, k2, false);
        }

        @Override // java.util.NavigableSet
        public NavigableSet<K> headSet(K k, boolean z) {
            return c().headMap(k, z).navigableKeySet();
        }

        @Override // com.google.common.collect.Maps.s, java.util.SortedSet, java.util.NavigableSet
        public SortedSet<K> headSet(K k) {
            return headSet(k, false);
        }

        @Override // java.util.NavigableSet
        public NavigableSet<K> tailSet(K k, boolean z) {
            return c().tailMap(k, z).navigableKeySet();
        }

        @Override // com.google.common.collect.Maps.s, java.util.SortedSet, java.util.NavigableSet
        public SortedSet<K> tailSet(K k) {
            return tailSet(k, true);
        }
    }

    /* loaded from: classes2.dex */
    public static class ac<K, V> extends AbstractCollection<V> {
        @Weak
        final Map<K, V> c;

        public ac(Map<K, V> map) {
            this.c = (Map) Preconditions.checkNotNull(map);
        }

        final Map<K, V> a() {
            return this.c;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
        public Iterator<V> iterator() {
            return Maps.b(a().entrySet().iterator());
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean remove(Object obj) {
            try {
                return super.remove(obj);
            } catch (UnsupportedOperationException unused) {
                for (Map.Entry<K, V> entry : a().entrySet()) {
                    if (Objects.equal(obj, entry.getValue())) {
                        a().remove(entry.getKey());
                        return true;
                    }
                }
                return false;
            }
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean removeAll(Collection<?> collection) {
            try {
                return super.removeAll((Collection) Preconditions.checkNotNull(collection));
            } catch (UnsupportedOperationException unused) {
                HashSet newHashSet = Sets.newHashSet();
                for (Map.Entry<K, V> entry : a().entrySet()) {
                    if (collection.contains(entry.getValue())) {
                        newHashSet.add(entry.getKey());
                    }
                }
                return a().keySet().removeAll(newHashSet);
            }
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean retainAll(Collection<?> collection) {
            try {
                return super.retainAll((Collection) Preconditions.checkNotNull(collection));
            } catch (UnsupportedOperationException unused) {
                HashSet newHashSet = Sets.newHashSet();
                for (Map.Entry<K, V> entry : a().entrySet()) {
                    if (collection.contains(entry.getValue())) {
                        newHashSet.add(entry.getKey());
                    }
                }
                return a().keySet().retainAll(newHashSet);
            }
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public int size() {
            return a().size();
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean isEmpty() {
            return a().isEmpty();
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean contains(@NullableDecl Object obj) {
            return a().containsValue(obj);
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public void clear() {
            a().clear();
        }
    }

    /* loaded from: classes2.dex */
    public static abstract class f<K, V> extends Sets.f<Map.Entry<K, V>> {
        abstract Map<K, V> a();

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return a().size();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public void clear() {
            a().clear();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(Object obj) {
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            Object key = entry.getKey();
            Object a = Maps.a((Map<?, Object>) a(), key);
            if (Objects.equal(a, entry.getValue())) {
                return a != null || a().containsKey(key);
            }
            return false;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean isEmpty() {
            return a().isEmpty();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(Object obj) {
            if (contains(obj)) {
                return a().keySet().remove(((Map.Entry) obj).getKey());
            }
            return false;
        }

        @Override // com.google.common.collect.Sets.f, java.util.AbstractSet, java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean removeAll(Collection<?> collection) {
            try {
                return super.removeAll((Collection) Preconditions.checkNotNull(collection));
            } catch (UnsupportedOperationException unused) {
                return Sets.a((Set<?>) this, collection.iterator());
            }
        }

        @Override // com.google.common.collect.Sets.f, java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean retainAll(Collection<?> collection) {
            try {
                return super.retainAll((Collection) Preconditions.checkNotNull(collection));
            } catch (UnsupportedOperationException unused) {
                HashSet newHashSetWithExpectedSize = Sets.newHashSetWithExpectedSize(collection.size());
                for (Object obj : collection) {
                    if (contains(obj)) {
                        newHashSetWithExpectedSize.add(((Map.Entry) obj).getKey());
                    }
                }
                return a().keySet().retainAll(newHashSetWithExpectedSize);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @GwtIncompatible
    /* loaded from: classes2.dex */
    public static abstract class d<K, V> extends ForwardingMap<K, V> implements NavigableMap<K, V> {
        @MonotonicNonNullDecl
        private transient Comparator<? super K> a;
        @MonotonicNonNullDecl
        private transient Set<Map.Entry<K, V>> b;
        @MonotonicNonNullDecl
        private transient NavigableSet<K> c;

        abstract NavigableMap<K, V> a();

        abstract Iterator<Map.Entry<K, V>> entryIterator();

        @Override // com.google.common.collect.ForwardingMap, com.google.common.collect.ForwardingObject
        public final Map<K, V> delegate() {
            return a();
        }

        @Override // java.util.SortedMap
        public Comparator<? super K> comparator() {
            Comparator<? super K> comparator = this.a;
            if (comparator != null) {
                return comparator;
            }
            Comparator<? super K> comparator2 = a().comparator();
            if (comparator2 == null) {
                comparator2 = Ordering.natural();
            }
            Ordering a2 = a(comparator2);
            this.a = a2;
            return a2;
        }

        private static <T> Ordering<T> a(Comparator<T> comparator) {
            return Ordering.from(comparator).reverse();
        }

        @Override // java.util.SortedMap
        public K firstKey() {
            return a().lastKey();
        }

        @Override // java.util.SortedMap
        public K lastKey() {
            return a().firstKey();
        }

        @Override // java.util.NavigableMap
        public Map.Entry<K, V> lowerEntry(K k) {
            return a().higherEntry(k);
        }

        @Override // java.util.NavigableMap
        public K lowerKey(K k) {
            return a().higherKey(k);
        }

        @Override // java.util.NavigableMap
        public Map.Entry<K, V> floorEntry(K k) {
            return a().ceilingEntry(k);
        }

        @Override // java.util.NavigableMap
        public K floorKey(K k) {
            return a().ceilingKey(k);
        }

        @Override // java.util.NavigableMap
        public Map.Entry<K, V> ceilingEntry(K k) {
            return a().floorEntry(k);
        }

        @Override // java.util.NavigableMap
        public K ceilingKey(K k) {
            return a().floorKey(k);
        }

        @Override // java.util.NavigableMap
        public Map.Entry<K, V> higherEntry(K k) {
            return a().lowerEntry(k);
        }

        @Override // java.util.NavigableMap
        public K higherKey(K k) {
            return a().lowerKey(k);
        }

        @Override // java.util.NavigableMap
        public Map.Entry<K, V> firstEntry() {
            return a().lastEntry();
        }

        @Override // java.util.NavigableMap
        public Map.Entry<K, V> lastEntry() {
            return a().firstEntry();
        }

        @Override // java.util.NavigableMap
        public Map.Entry<K, V> pollFirstEntry() {
            return a().pollLastEntry();
        }

        @Override // java.util.NavigableMap
        public Map.Entry<K, V> pollLastEntry() {
            return a().pollFirstEntry();
        }

        @Override // java.util.NavigableMap
        public NavigableMap<K, V> descendingMap() {
            return a();
        }

        @Override // com.google.common.collect.ForwardingMap, java.util.Map
        public Set<Map.Entry<K, V>> entrySet() {
            Set<Map.Entry<K, V>> set = this.b;
            if (set != null) {
                return set;
            }
            Set<Map.Entry<K, V>> b = b();
            this.b = b;
            return b;
        }

        /* loaded from: classes2.dex */
        public class a extends f<K, V> {
            a() {
                d.this = r1;
            }

            @Override // com.google.common.collect.Maps.f
            Map<K, V> a() {
                return d.this;
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
            public Iterator<Map.Entry<K, V>> iterator() {
                return d.this.entryIterator();
            }
        }

        Set<Map.Entry<K, V>> b() {
            return new a();
        }

        @Override // com.google.common.collect.ForwardingMap, java.util.Map
        public Set<K> keySet() {
            return navigableKeySet();
        }

        @Override // java.util.NavigableMap
        public NavigableSet<K> navigableKeySet() {
            NavigableSet<K> navigableSet = this.c;
            if (navigableSet != null) {
                return navigableSet;
            }
            q qVar = new q(this);
            this.c = qVar;
            return qVar;
        }

        @Override // java.util.NavigableMap
        public NavigableSet<K> descendingKeySet() {
            return a().navigableKeySet();
        }

        @Override // java.util.NavigableMap
        public NavigableMap<K, V> subMap(K k, boolean z, K k2, boolean z2) {
            return a().subMap(k2, z2, k, z).descendingMap();
        }

        @Override // java.util.NavigableMap, java.util.SortedMap
        public SortedMap<K, V> subMap(K k, K k2) {
            return subMap(k, true, k2, false);
        }

        @Override // java.util.NavigableMap
        public NavigableMap<K, V> headMap(K k, boolean z) {
            return a().tailMap(k, z).descendingMap();
        }

        @Override // java.util.NavigableMap, java.util.SortedMap
        public SortedMap<K, V> headMap(K k) {
            return headMap(k, false);
        }

        @Override // java.util.NavigableMap
        public NavigableMap<K, V> tailMap(K k, boolean z) {
            return a().headMap(k, z).descendingMap();
        }

        @Override // java.util.NavigableMap, java.util.SortedMap
        public SortedMap<K, V> tailMap(K k) {
            return tailMap(k, true);
        }

        @Override // com.google.common.collect.ForwardingMap, java.util.Map, com.google.common.collect.BiMap
        public Collection<V> values() {
            return new ac(this);
        }

        @Override // com.google.common.collect.ForwardingObject
        public String toString() {
            return standardToString();
        }
    }

    public static <E> ImmutableMap<E, Integer> a(Collection<E> collection) {
        ImmutableMap.Builder builder = new ImmutableMap.Builder(collection.size());
        int i2 = 0;
        for (E e2 : collection) {
            i2++;
            builder.put(e2, Integer.valueOf(i2));
        }
        return builder.build();
    }

    @Beta
    @GwtIncompatible
    public static <K extends Comparable<? super K>, V> NavigableMap<K, V> subMap(NavigableMap<K, V> navigableMap, Range<K> range) {
        boolean z2 = true;
        if (navigableMap.comparator() != null && navigableMap.comparator() != Ordering.natural() && range.hasLowerBound() && range.hasUpperBound()) {
            Preconditions.checkArgument(navigableMap.comparator().compare(range.lowerEndpoint(), range.upperEndpoint()) <= 0, "map is using a custom comparator which is inconsistent with the natural ordering.");
        }
        if (range.hasLowerBound() && range.hasUpperBound()) {
            K lowerEndpoint = range.lowerEndpoint();
            boolean z3 = range.lowerBoundType() == BoundType.CLOSED;
            K upperEndpoint = range.upperEndpoint();
            if (range.upperBoundType() != BoundType.CLOSED) {
                z2 = false;
            }
            return navigableMap.subMap(lowerEndpoint, z3, upperEndpoint, z2);
        } else if (range.hasLowerBound()) {
            K lowerEndpoint2 = range.lowerEndpoint();
            if (range.lowerBoundType() != BoundType.CLOSED) {
                z2 = false;
            }
            return navigableMap.tailMap(lowerEndpoint2, z2);
        } else if (!range.hasUpperBound()) {
            return (NavigableMap) Preconditions.checkNotNull(navigableMap);
        } else {
            K upperEndpoint2 = range.upperEndpoint();
            if (range.upperBoundType() != BoundType.CLOSED) {
                z2 = false;
            }
            return navigableMap.headMap(upperEndpoint2, z2);
        }
    }
}
