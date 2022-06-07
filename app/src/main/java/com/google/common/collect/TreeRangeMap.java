package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Maps;
import java.lang.Comparable;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NoSuchElementException;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@Beta
@GwtIncompatible
/* loaded from: classes2.dex */
public final class TreeRangeMap<K extends Comparable, V> implements RangeMap<K, V> {
    private static final RangeMap b = new RangeMap() { // from class: com.google.common.collect.TreeRangeMap.1
        @Override // com.google.common.collect.RangeMap
        public void clear() {
        }

        @Override // com.google.common.collect.RangeMap
        @NullableDecl
        public Object get(Comparable comparable) {
            return null;
        }

        @Override // com.google.common.collect.RangeMap
        @NullableDecl
        public Map.Entry<Range, Object> getEntry(Comparable comparable) {
            return null;
        }

        @Override // com.google.common.collect.RangeMap
        public Range span() {
            throw new NoSuchElementException();
        }

        @Override // com.google.common.collect.RangeMap
        public void put(Range range, Object obj) {
            Preconditions.checkNotNull(range);
            throw new IllegalArgumentException("Cannot insert range " + range + " into an empty subRangeMap");
        }

        @Override // com.google.common.collect.RangeMap
        public void putCoalescing(Range range, Object obj) {
            Preconditions.checkNotNull(range);
            throw new IllegalArgumentException("Cannot insert range " + range + " into an empty subRangeMap");
        }

        @Override // com.google.common.collect.RangeMap
        public void putAll(RangeMap rangeMap) {
            if (!rangeMap.asMapOfRanges().isEmpty()) {
                throw new IllegalArgumentException("Cannot putAll(nonEmptyRangeMap) into an empty subRangeMap");
            }
        }

        @Override // com.google.common.collect.RangeMap
        public void remove(Range range) {
            Preconditions.checkNotNull(range);
        }

        @Override // com.google.common.collect.RangeMap
        public Map<Range, Object> asMapOfRanges() {
            return Collections.emptyMap();
        }

        @Override // com.google.common.collect.RangeMap
        public Map<Range, Object> asDescendingMapOfRanges() {
            return Collections.emptyMap();
        }

        @Override // com.google.common.collect.RangeMap
        public RangeMap subRangeMap(Range range) {
            Preconditions.checkNotNull(range);
            return this;
        }
    };
    private final NavigableMap<ab<K>, b<K, V>> a = Maps.newTreeMap();

    public static <K extends Comparable, V> TreeRangeMap<K, V> create() {
        return new TreeRangeMap<>();
    }

    private TreeRangeMap() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static final class b<K extends Comparable, V> extends f<Range<K>, V> {
        private final Range<K> a;
        private final V b;

        b(ab<K> abVar, ab<K> abVar2, V v) {
            this(Range.a((ab) abVar, (ab) abVar2), v);
        }

        b(Range<K> range, V v) {
            this.a = range;
            this.b = v;
        }

        /* renamed from: a */
        public Range<K> getKey() {
            return this.a;
        }

        @Override // com.google.common.collect.f, java.util.Map.Entry
        public V getValue() {
            return this.b;
        }

        public boolean a(K k) {
            return this.a.contains(k);
        }

        ab<K> b() {
            return this.a.lowerBound;
        }

        ab<K> c() {
            return this.a.upperBound;
        }
    }

    @Override // com.google.common.collect.RangeMap
    @NullableDecl
    public V get(K k) {
        Map.Entry<Range<K>, V> entry = getEntry(k);
        if (entry == null) {
            return null;
        }
        return entry.getValue();
    }

    @Override // com.google.common.collect.RangeMap
    @NullableDecl
    public Map.Entry<Range<K>, V> getEntry(K k) {
        Map.Entry<ab<K>, b<K, V>> floorEntry = this.a.floorEntry(ab.b(k));
        if (floorEntry == null || !floorEntry.getValue().a(k)) {
            return null;
        }
        return floorEntry.getValue();
    }

    @Override // com.google.common.collect.RangeMap
    public void put(Range<K> range, V v) {
        if (!range.isEmpty()) {
            Preconditions.checkNotNull(v);
            remove(range);
            this.a.put(range.lowerBound, new b(range, v));
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.collect.RangeMap
    public void putCoalescing(Range<K> range, V v) {
        if (this.a.isEmpty()) {
            put(range, v);
        } else {
            put(a(range, Preconditions.checkNotNull(v)), v);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Range<K> a(Range<K> range, V v) {
        return a(a(range, v, this.a.lowerEntry(range.lowerBound)), v, this.a.floorEntry(range.upperBound));
    }

    private static <K extends Comparable, V> Range<K> a(Range<K> range, V v, @NullableDecl Map.Entry<ab<K>, b<K, V>> entry) {
        return (entry == null || !entry.getValue().getKey().isConnected(range) || !entry.getValue().getValue().equals(v)) ? range : range.span(entry.getValue().getKey());
    }

    @Override // com.google.common.collect.RangeMap
    public void putAll(RangeMap<K, V> rangeMap) {
        for (Map.Entry<Range<K>, V> entry : rangeMap.asMapOfRanges().entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    @Override // com.google.common.collect.RangeMap
    public void clear() {
        this.a.clear();
    }

    @Override // com.google.common.collect.RangeMap
    public Range<K> span() {
        Map.Entry<ab<K>, b<K, V>> firstEntry = this.a.firstEntry();
        Map.Entry<ab<K>, b<K, V>> lastEntry = this.a.lastEntry();
        if (firstEntry != null) {
            return Range.a((ab) firstEntry.getValue().getKey().lowerBound, (ab) lastEntry.getValue().getKey().upperBound);
        }
        throw new NoSuchElementException();
    }

    private void a(ab<K> abVar, ab<K> abVar2, V v) {
        this.a.put(abVar, new b(abVar, abVar2, v));
    }

    @Override // com.google.common.collect.RangeMap
    public void remove(Range<K> range) {
        if (!range.isEmpty()) {
            Map.Entry<ab<K>, b<K, V>> lowerEntry = this.a.lowerEntry(range.lowerBound);
            if (lowerEntry != null) {
                b<K, V> value = lowerEntry.getValue();
                if (value.c().a(range.lowerBound) > 0) {
                    if (value.c().a(range.upperBound) > 0) {
                        a(range.upperBound, value.c(), (ab<K>) lowerEntry.getValue().getValue());
                    }
                    a(value.b(), range.lowerBound, (ab<K>) lowerEntry.getValue().getValue());
                }
            }
            Map.Entry<ab<K>, b<K, V>> lowerEntry2 = this.a.lowerEntry(range.upperBound);
            if (lowerEntry2 != null) {
                b<K, V> value2 = lowerEntry2.getValue();
                if (value2.c().a(range.upperBound) > 0) {
                    a(range.upperBound, value2.c(), (ab<K>) lowerEntry2.getValue().getValue());
                }
            }
            this.a.subMap(range.lowerBound, range.upperBound).clear();
        }
    }

    @Override // com.google.common.collect.RangeMap
    public Map<Range<K>, V> asMapOfRanges() {
        return new a(this.a.values());
    }

    @Override // com.google.common.collect.RangeMap
    public Map<Range<K>, V> asDescendingMapOfRanges() {
        return new a(this.a.descendingMap().values());
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public final class a extends Maps.m<Range<K>, V> {
        final Iterable<Map.Entry<Range<K>, V>> a;

        a(Iterable<b<K, V>> iterable) {
            this.a = iterable;
        }

        @Override // java.util.AbstractMap, java.util.Map
        public boolean containsKey(@NullableDecl Object obj) {
            return get(obj) != null;
        }

        @Override // java.util.AbstractMap, java.util.Map
        public V get(@NullableDecl Object obj) {
            if (!(obj instanceof Range)) {
                return null;
            }
            Range range = (Range) obj;
            b bVar = (b) TreeRangeMap.this.a.get(range.lowerBound);
            if (bVar == null || !bVar.getKey().equals(range)) {
                return null;
            }
            return (V) bVar.getValue();
        }

        @Override // java.util.AbstractMap, java.util.Map
        public int size() {
            return TreeRangeMap.this.a.size();
        }

        @Override // com.google.common.collect.Maps.m
        Iterator<Map.Entry<Range<K>, V>> b() {
            return this.a.iterator();
        }
    }

    @Override // com.google.common.collect.RangeMap
    public RangeMap<K, V> subRangeMap(Range<K> range) {
        return range.equals(Range.all()) ? this : new c(range);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public RangeMap<K, V> a() {
        return b;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public class c implements RangeMap<K, V> {
        private final Range<K> b;

        c(Range<K> range) {
            this.b = range;
        }

        @Override // com.google.common.collect.RangeMap
        @NullableDecl
        public V get(K k) {
            if (this.b.contains(k)) {
                return (V) TreeRangeMap.this.get(k);
            }
            return null;
        }

        @Override // com.google.common.collect.RangeMap
        @NullableDecl
        public Map.Entry<Range<K>, V> getEntry(K k) {
            Map.Entry<Range<K>, V> entry;
            if (!this.b.contains(k) || (entry = TreeRangeMap.this.getEntry(k)) == null) {
                return null;
            }
            return Maps.immutableEntry(entry.getKey().intersection((Range<K>) this.b), entry.getValue());
        }

        @Override // com.google.common.collect.RangeMap
        public Range<K> span() {
            ab abVar;
            ab abVar2;
            Map.Entry floorEntry = TreeRangeMap.this.a.floorEntry(this.b.lowerBound);
            if (floorEntry == null || ((b) floorEntry.getValue()).c().a((ab<K>) this.b.lowerBound) <= 0) {
                abVar = (ab) TreeRangeMap.this.a.ceilingKey(this.b.lowerBound);
                if (abVar == null || abVar.a((ab) this.b.upperBound) >= 0) {
                    throw new NoSuchElementException();
                }
            } else {
                abVar = this.b.lowerBound;
            }
            Map.Entry lowerEntry = TreeRangeMap.this.a.lowerEntry(this.b.upperBound);
            if (lowerEntry != null) {
                if (((b) lowerEntry.getValue()).c().a((ab<K>) this.b.upperBound) >= 0) {
                    abVar2 = this.b.upperBound;
                } else {
                    abVar2 = ((b) lowerEntry.getValue()).c();
                }
                return Range.a(abVar, abVar2);
            }
            throw new NoSuchElementException();
        }

        @Override // com.google.common.collect.RangeMap
        public void put(Range<K> range, V v) {
            Preconditions.checkArgument(this.b.encloses(range), "Cannot put range %s into a subRangeMap(%s)", range, this.b);
            TreeRangeMap.this.put(range, v);
        }

        @Override // com.google.common.collect.RangeMap
        public void putCoalescing(Range<K> range, V v) {
            if (TreeRangeMap.this.a.isEmpty() || range.isEmpty() || !this.b.encloses(range)) {
                put(range, v);
            } else {
                put(TreeRangeMap.this.a(range, Preconditions.checkNotNull(v)).intersection(this.b), v);
            }
        }

        @Override // com.google.common.collect.RangeMap
        public void putAll(RangeMap<K, V> rangeMap) {
            if (!rangeMap.asMapOfRanges().isEmpty()) {
                Range<K> span = rangeMap.span();
                Preconditions.checkArgument(this.b.encloses(span), "Cannot putAll rangeMap with span %s into a subRangeMap(%s)", span, this.b);
                TreeRangeMap.this.putAll(rangeMap);
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.common.collect.RangeMap
        public void clear() {
            TreeRangeMap.this.remove(this.b);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.common.collect.RangeMap
        public void remove(Range<K> range) {
            if (range.isConnected(this.b)) {
                TreeRangeMap.this.remove(range.intersection(this.b));
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.common.collect.RangeMap
        public RangeMap<K, V> subRangeMap(Range<K> range) {
            if (!range.isConnected(this.b)) {
                return TreeRangeMap.this.a();
            }
            return TreeRangeMap.this.subRangeMap(range.intersection(this.b));
        }

        @Override // com.google.common.collect.RangeMap
        public Map<Range<K>, V> asMapOfRanges() {
            return new a();
        }

        @Override // com.google.common.collect.RangeMap
        public Map<Range<K>, V> asDescendingMapOfRanges() {
            return new TreeRangeMap<K, V>.c.a() { // from class: com.google.common.collect.TreeRangeMap.c.1
                @Override // com.google.common.collect.TreeRangeMap.c.a
                Iterator<Map.Entry<Range<K>, V>> a() {
                    if (c.this.b.isEmpty()) {
                        return Iterators.a();
                    }
                    final Iterator<V> it = TreeRangeMap.this.a.headMap(c.this.b.upperBound, false).descendingMap().values().iterator();
                    return new AbstractIterator<Map.Entry<Range<K>, V>>() { // from class: com.google.common.collect.TreeRangeMap.c.1.1
                        /* JADX INFO: Access modifiers changed from: protected */
                        /* renamed from: a */
                        public Map.Entry<Range<K>, V> computeNext() {
                            if (!it.hasNext()) {
                                return (Map.Entry) endOfData();
                            }
                            b bVar = (b) it.next();
                            if (bVar.c().a((ab<K>) c.this.b.lowerBound) <= 0) {
                                return (Map.Entry) endOfData();
                            }
                            return Maps.immutableEntry(bVar.getKey().intersection(c.this.b), bVar.getValue());
                        }
                    };
                }
            };
        }

        @Override // com.google.common.collect.RangeMap
        public boolean equals(@NullableDecl Object obj) {
            if (obj instanceof RangeMap) {
                return asMapOfRanges().equals(((RangeMap) obj).asMapOfRanges());
            }
            return false;
        }

        @Override // com.google.common.collect.RangeMap
        public int hashCode() {
            return asMapOfRanges().hashCode();
        }

        @Override // com.google.common.collect.RangeMap
        public String toString() {
            return asMapOfRanges().toString();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* loaded from: classes2.dex */
        public class a extends AbstractMap<Range<K>, V> {
            a() {
            }

            @Override // java.util.AbstractMap, java.util.Map
            public boolean containsKey(Object obj) {
                return get(obj) != null;
            }

            @Override // java.util.AbstractMap, java.util.Map
            public V get(Object obj) {
                b bVar;
                try {
                    if (obj instanceof Range) {
                        Range range = (Range) obj;
                        if (c.this.b.encloses(range) && !range.isEmpty()) {
                            if (range.lowerBound.a((ab) c.this.b.lowerBound) == 0) {
                                Map.Entry floorEntry = TreeRangeMap.this.a.floorEntry(range.lowerBound);
                                if (floorEntry != null) {
                                    bVar = (b) floorEntry.getValue();
                                } else {
                                    bVar = null;
                                }
                            } else {
                                bVar = (b) TreeRangeMap.this.a.get(range.lowerBound);
                            }
                            if (bVar != null && bVar.getKey().isConnected(c.this.b) && bVar.getKey().intersection(c.this.b).equals(range)) {
                                return (V) bVar.getValue();
                            }
                        }
                        return null;
                    }
                    return null;
                } catch (ClassCastException unused) {
                    return null;
                }
            }

            @Override // java.util.AbstractMap, java.util.Map
            public V remove(Object obj) {
                V v = (V) get(obj);
                if (v == null) {
                    return null;
                }
                TreeRangeMap.this.remove((Range) obj);
                return v;
            }

            @Override // java.util.AbstractMap, java.util.Map
            public void clear() {
                c.this.clear();
            }

            /* JADX INFO: Access modifiers changed from: private */
            public boolean a(Predicate<? super Map.Entry<Range<K>, V>> predicate) {
                ArrayList<Range<K>> newArrayList = Lists.newArrayList();
                for (Map.Entry<Range<K>, V> entry : entrySet()) {
                    if (predicate.apply(entry)) {
                        newArrayList.add(entry.getKey());
                    }
                }
                for (Range<K> range : newArrayList) {
                    TreeRangeMap.this.remove(range);
                }
                return !newArrayList.isEmpty();
            }

            @Override // java.util.AbstractMap, java.util.Map
            public Set<Range<K>> keySet() {
                return new Maps.n<Range<K>, V>(this) { // from class: com.google.common.collect.TreeRangeMap.c.a.1
                    @Override // com.google.common.collect.Maps.n, java.util.AbstractCollection, java.util.Collection, java.util.Set
                    public boolean remove(@NullableDecl Object obj) {
                        return a.this.remove(obj) != null;
                    }

                    @Override // com.google.common.collect.Sets.f, java.util.AbstractCollection, java.util.Collection, java.util.Set
                    public boolean retainAll(Collection<?> collection) {
                        return a.this.a(Predicates.compose(Predicates.not(Predicates.in(collection)), Maps.a()));
                    }
                };
            }

            @Override // java.util.AbstractMap, java.util.Map
            public Set<Map.Entry<Range<K>, V>> entrySet() {
                return new Maps.f<Range<K>, V>() { // from class: com.google.common.collect.TreeRangeMap.c.a.2
                    @Override // com.google.common.collect.Maps.f
                    Map<Range<K>, V> a() {
                        return a.this;
                    }

                    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
                    public Iterator<Map.Entry<Range<K>, V>> iterator() {
                        return a.this.a();
                    }

                    @Override // com.google.common.collect.Maps.f, com.google.common.collect.Sets.f, java.util.AbstractCollection, java.util.Collection, java.util.Set
                    public boolean retainAll(Collection<?> collection) {
                        return a.this.a(Predicates.not(Predicates.in(collection)));
                    }

                    @Override // com.google.common.collect.Maps.f, java.util.AbstractCollection, java.util.Collection, java.util.Set
                    public int size() {
                        return Iterators.size(iterator());
                    }

                    @Override // com.google.common.collect.Maps.f, java.util.AbstractCollection, java.util.Collection, java.util.Set
                    public boolean isEmpty() {
                        return !iterator().hasNext();
                    }
                };
            }

            Iterator<Map.Entry<Range<K>, V>> a() {
                if (c.this.b.isEmpty()) {
                    return Iterators.a();
                }
                final Iterator<V> it = TreeRangeMap.this.a.tailMap((ab) MoreObjects.firstNonNull(TreeRangeMap.this.a.floorKey(c.this.b.lowerBound), c.this.b.lowerBound), true).values().iterator();
                return new AbstractIterator<Map.Entry<Range<K>, V>>() { // from class: com.google.common.collect.TreeRangeMap.c.a.3
                    /* JADX INFO: Access modifiers changed from: protected */
                    /* renamed from: a */
                    public Map.Entry<Range<K>, V> computeNext() {
                        while (it.hasNext()) {
                            b bVar = (b) it.next();
                            if (bVar.b().a((ab<K>) c.this.b.upperBound) >= 0) {
                                return (Map.Entry) endOfData();
                            }
                            if (bVar.c().a((ab<K>) c.this.b.lowerBound) > 0) {
                                return Maps.immutableEntry(bVar.getKey().intersection(c.this.b), bVar.getValue());
                            }
                        }
                        return (Map.Entry) endOfData();
                    }
                };
            }

            @Override // java.util.AbstractMap, java.util.Map
            public Collection<V> values() {
                return new Maps.ac<Range<K>, V>(this) { // from class: com.google.common.collect.TreeRangeMap.c.a.4
                    @Override // com.google.common.collect.Maps.ac, java.util.AbstractCollection, java.util.Collection
                    public boolean removeAll(Collection<?> collection) {
                        return a.this.a(Predicates.compose(Predicates.in(collection), Maps.b()));
                    }

                    @Override // com.google.common.collect.Maps.ac, java.util.AbstractCollection, java.util.Collection
                    public boolean retainAll(Collection<?> collection) {
                        return a.this.a(Predicates.compose(Predicates.not(Predicates.in(collection)), Maps.b()));
                    }
                };
            }
        }
    }

    @Override // com.google.common.collect.RangeMap
    public boolean equals(@NullableDecl Object obj) {
        if (obj instanceof RangeMap) {
            return asMapOfRanges().equals(((RangeMap) obj).asMapOfRanges());
        }
        return false;
    }

    @Override // com.google.common.collect.RangeMap
    public int hashCode() {
        return asMapOfRanges().hashCode();
    }

    @Override // com.google.common.collect.RangeMap
    public String toString() {
        return this.a.values().toString();
    }
}
