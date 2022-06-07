package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import java.io.Serializable;
import java.lang.Comparable;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TreeMap;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@Beta
@GwtIncompatible
/* loaded from: classes2.dex */
public class TreeRangeSet<C extends Comparable<?>> extends j<C> implements Serializable {
    @MonotonicNonNullDecl
    private transient Set<Range<C>> a;
    @MonotonicNonNullDecl
    private transient Set<Range<C>> b;
    @MonotonicNonNullDecl
    private transient RangeSet<C> c;
    @VisibleForTesting
    final NavigableMap<ab<C>, Range<C>> rangesByLowerBound;

    @Override // com.google.common.collect.j, com.google.common.collect.RangeSet
    public /* bridge */ /* synthetic */ void addAll(RangeSet rangeSet) {
        super.addAll(rangeSet);
    }

    @Override // com.google.common.collect.j, com.google.common.collect.RangeSet
    public /* bridge */ /* synthetic */ void addAll(Iterable iterable) {
        super.addAll(iterable);
    }

    @Override // com.google.common.collect.j, com.google.common.collect.RangeSet
    public /* bridge */ /* synthetic */ void clear() {
        super.clear();
    }

    @Override // com.google.common.collect.j, com.google.common.collect.RangeSet
    public /* bridge */ /* synthetic */ boolean contains(Comparable comparable) {
        return super.contains(comparable);
    }

    @Override // com.google.common.collect.j, com.google.common.collect.RangeSet
    public /* bridge */ /* synthetic */ boolean enclosesAll(RangeSet rangeSet) {
        return super.enclosesAll(rangeSet);
    }

    @Override // com.google.common.collect.j, com.google.common.collect.RangeSet
    public /* bridge */ /* synthetic */ boolean enclosesAll(Iterable iterable) {
        return super.enclosesAll(iterable);
    }

    @Override // com.google.common.collect.j, com.google.common.collect.RangeSet
    public /* bridge */ /* synthetic */ boolean equals(@NullableDecl Object obj) {
        return super.equals(obj);
    }

    @Override // com.google.common.collect.j, com.google.common.collect.RangeSet
    public /* bridge */ /* synthetic */ boolean isEmpty() {
        return super.isEmpty();
    }

    @Override // com.google.common.collect.j, com.google.common.collect.RangeSet
    public /* bridge */ /* synthetic */ void removeAll(RangeSet rangeSet) {
        super.removeAll(rangeSet);
    }

    @Override // com.google.common.collect.j, com.google.common.collect.RangeSet
    public /* bridge */ /* synthetic */ void removeAll(Iterable iterable) {
        super.removeAll(iterable);
    }

    public static <C extends Comparable<?>> TreeRangeSet<C> create() {
        return new TreeRangeSet<>(new TreeMap());
    }

    public static <C extends Comparable<?>> TreeRangeSet<C> create(RangeSet<C> rangeSet) {
        TreeRangeSet<C> create = create();
        create.addAll(rangeSet);
        return create;
    }

    public static <C extends Comparable<?>> TreeRangeSet<C> create(Iterable<Range<C>> iterable) {
        TreeRangeSet<C> create = create();
        create.addAll(iterable);
        return create;
    }

    private TreeRangeSet(NavigableMap<ab<C>, Range<C>> navigableMap) {
        this.rangesByLowerBound = navigableMap;
    }

    @Override // com.google.common.collect.RangeSet
    public Set<Range<C>> asRanges() {
        Set<Range<C>> set = this.a;
        if (set != null) {
            return set;
        }
        a aVar = new a(this.rangesByLowerBound.values());
        this.a = aVar;
        return aVar;
    }

    @Override // com.google.common.collect.RangeSet
    public Set<Range<C>> asDescendingSetOfRanges() {
        Set<Range<C>> set = this.b;
        if (set != null) {
            return set;
        }
        a aVar = new a(this.rangesByLowerBound.descendingMap().values());
        this.b = aVar;
        return aVar;
    }

    /* loaded from: classes2.dex */
    final class a extends ForwardingCollection<Range<C>> implements Set<Range<C>> {
        final Collection<Range<C>> a;

        a(Collection<Range<C>> collection) {
            this.a = collection;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.common.collect.ForwardingCollection, com.google.common.collect.ForwardingObject
        public Collection<Range<C>> delegate() {
            return (Collection<Range<C>>) this.a;
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

    @Override // com.google.common.collect.j, com.google.common.collect.RangeSet
    @NullableDecl
    public Range<C> rangeContaining(C c2) {
        Preconditions.checkNotNull(c2);
        Map.Entry<ab<C>, Range<C>> floorEntry = this.rangesByLowerBound.floorEntry(ab.b(c2));
        if (floorEntry == null || !floorEntry.getValue().contains(c2)) {
            return null;
        }
        return floorEntry.getValue();
    }

    @Override // com.google.common.collect.j, com.google.common.collect.RangeSet
    public boolean intersects(Range<C> range) {
        Preconditions.checkNotNull(range);
        Map.Entry<ab<C>, Range<C>> ceilingEntry = this.rangesByLowerBound.ceilingEntry(range.lowerBound);
        if (ceilingEntry != null && ceilingEntry.getValue().isConnected(range) && !ceilingEntry.getValue().intersection(range).isEmpty()) {
            return true;
        }
        Map.Entry<ab<C>, Range<C>> lowerEntry = this.rangesByLowerBound.lowerEntry(range.lowerBound);
        return lowerEntry != null && lowerEntry.getValue().isConnected(range) && !lowerEntry.getValue().intersection(range).isEmpty();
    }

    @Override // com.google.common.collect.j, com.google.common.collect.RangeSet
    public boolean encloses(Range<C> range) {
        Preconditions.checkNotNull(range);
        Map.Entry<ab<C>, Range<C>> floorEntry = this.rangesByLowerBound.floorEntry(range.lowerBound);
        return floorEntry != null && floorEntry.getValue().encloses(range);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @NullableDecl
    public Range<C> a(Range<C> range) {
        Preconditions.checkNotNull(range);
        Map.Entry<ab<C>, Range<C>> floorEntry = this.rangesByLowerBound.floorEntry(range.lowerBound);
        if (floorEntry == null || !floorEntry.getValue().encloses(range)) {
            return null;
        }
        return floorEntry.getValue();
    }

    @Override // com.google.common.collect.RangeSet
    public Range<C> span() {
        Map.Entry<ab<C>, Range<C>> firstEntry = this.rangesByLowerBound.firstEntry();
        Map.Entry<ab<C>, Range<C>> lastEntry = this.rangesByLowerBound.lastEntry();
        if (firstEntry != null) {
            return Range.a((ab) firstEntry.getValue().lowerBound, (ab) lastEntry.getValue().upperBound);
        }
        throw new NoSuchElementException();
    }

    @Override // com.google.common.collect.j, com.google.common.collect.RangeSet
    public void add(Range<C> range) {
        Preconditions.checkNotNull(range);
        if (!range.isEmpty()) {
            ab<C> abVar = range.lowerBound;
            ab<C> abVar2 = range.upperBound;
            Map.Entry<ab<C>, Range<C>> lowerEntry = this.rangesByLowerBound.lowerEntry(abVar);
            if (lowerEntry != null) {
                Range<C> value = lowerEntry.getValue();
                if (value.upperBound.a(abVar) >= 0) {
                    if (value.upperBound.a(abVar2) >= 0) {
                        abVar2 = value.upperBound;
                    }
                    abVar = value.lowerBound;
                }
            }
            Map.Entry<ab<C>, Range<C>> floorEntry = this.rangesByLowerBound.floorEntry(abVar2);
            if (floorEntry != null) {
                Range<C> value2 = floorEntry.getValue();
                if (value2.upperBound.a(abVar2) >= 0) {
                    abVar2 = value2.upperBound;
                }
            }
            this.rangesByLowerBound.subMap(abVar, abVar2).clear();
            b(Range.a((ab) abVar, (ab) abVar2));
        }
    }

    @Override // com.google.common.collect.j, com.google.common.collect.RangeSet
    public void remove(Range<C> range) {
        Preconditions.checkNotNull(range);
        if (!range.isEmpty()) {
            Map.Entry<ab<C>, Range<C>> lowerEntry = this.rangesByLowerBound.lowerEntry(range.lowerBound);
            if (lowerEntry != null) {
                Range<C> value = lowerEntry.getValue();
                if (value.upperBound.a(range.lowerBound) >= 0) {
                    if (range.hasUpperBound() && value.upperBound.a(range.upperBound) >= 0) {
                        b(Range.a((ab) range.upperBound, (ab) value.upperBound));
                    }
                    b(Range.a((ab) value.lowerBound, (ab) range.lowerBound));
                }
            }
            Map.Entry<ab<C>, Range<C>> floorEntry = this.rangesByLowerBound.floorEntry(range.upperBound);
            if (floorEntry != null) {
                Range<C> value2 = floorEntry.getValue();
                if (range.hasUpperBound() && value2.upperBound.a(range.upperBound) >= 0) {
                    b(Range.a((ab) range.upperBound, (ab) value2.upperBound));
                }
            }
            this.rangesByLowerBound.subMap(range.lowerBound, range.upperBound).clear();
        }
    }

    private void b(Range<C> range) {
        if (range.isEmpty()) {
            this.rangesByLowerBound.remove(range.lowerBound);
        } else {
            this.rangesByLowerBound.put(range.lowerBound, range);
        }
    }

    @Override // com.google.common.collect.RangeSet
    public RangeSet<C> complement() {
        RangeSet<C> rangeSet = this.c;
        if (rangeSet != null) {
            return rangeSet;
        }
        b bVar = new b();
        this.c = bVar;
        return bVar;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @VisibleForTesting
    /* loaded from: classes2.dex */
    public static final class d<C extends Comparable<?>> extends i<ab<C>, Range<C>> {
        private final NavigableMap<ab<C>, Range<C>> a;
        private final Range<ab<C>> b;

        d(NavigableMap<ab<C>, Range<C>> navigableMap) {
            this.a = navigableMap;
            this.b = Range.all();
        }

        private d(NavigableMap<ab<C>, Range<C>> navigableMap, Range<ab<C>> range) {
            this.a = navigableMap;
            this.b = range;
        }

        private NavigableMap<ab<C>, Range<C>> a(Range<ab<C>> range) {
            if (range.isConnected(this.b)) {
                return new d(this.a, range.intersection(this.b));
            }
            return ImmutableSortedMap.of();
        }

        /* renamed from: a */
        public NavigableMap<ab<C>, Range<C>> subMap(ab<C> abVar, boolean z, ab<C> abVar2, boolean z2) {
            return a((Range) Range.range(abVar, BoundType.a(z), abVar2, BoundType.a(z2)));
        }

        /* renamed from: a */
        public NavigableMap<ab<C>, Range<C>> headMap(ab<C> abVar, boolean z) {
            return a((Range) Range.upTo(abVar, BoundType.a(z)));
        }

        /* renamed from: b */
        public NavigableMap<ab<C>, Range<C>> tailMap(ab<C> abVar, boolean z) {
            return a((Range) Range.downTo(abVar, BoundType.a(z)));
        }

        @Override // java.util.SortedMap
        public Comparator<? super ab<C>> comparator() {
            return Ordering.natural();
        }

        @Override // java.util.AbstractMap, java.util.Map
        public boolean containsKey(@NullableDecl Object obj) {
            return get(obj) != null;
        }

        /* renamed from: a */
        public Range<C> get(@NullableDecl Object obj) {
            Map.Entry<ab<C>, Range<C>> lowerEntry;
            if (obj instanceof ab) {
                try {
                    ab<C> abVar = (ab) obj;
                    if (this.b.contains(abVar) && (lowerEntry = this.a.lowerEntry(abVar)) != null && lowerEntry.getValue().upperBound.equals(abVar)) {
                        return lowerEntry.getValue();
                    }
                } catch (ClassCastException unused) {
                    return null;
                }
            }
            return null;
        }

        @Override // com.google.common.collect.Maps.m
        Iterator<Map.Entry<ab<C>, Range<C>>> b() {
            final Iterator<Range<C>> it;
            if (!this.b.hasLowerBound()) {
                it = this.a.values().iterator();
            } else {
                Map.Entry<ab<C>, Range<C>> lowerEntry = this.a.lowerEntry(this.b.lowerEndpoint());
                if (lowerEntry == null) {
                    it = this.a.values().iterator();
                } else if (this.b.lowerBound.a((ab<ab<C>>) lowerEntry.getValue().upperBound)) {
                    it = this.a.tailMap(lowerEntry.getKey(), true).values().iterator();
                } else {
                    it = this.a.tailMap(this.b.lowerEndpoint(), true).values().iterator();
                }
            }
            return new AbstractIterator<Map.Entry<ab<C>, Range<C>>>() { // from class: com.google.common.collect.TreeRangeSet.d.1
                /* JADX INFO: Access modifiers changed from: protected */
                /* renamed from: a */
                public Map.Entry<ab<C>, Range<C>> computeNext() {
                    if (!it.hasNext()) {
                        return (Map.Entry) endOfData();
                    }
                    Range range = (Range) it.next();
                    if (d.this.b.upperBound.a((ab<C>) range.upperBound)) {
                        return (Map.Entry) endOfData();
                    }
                    return Maps.immutableEntry(range.upperBound, range);
                }
            };
        }

        @Override // com.google.common.collect.i
        Iterator<Map.Entry<ab<C>, Range<C>>> a() {
            Collection<Range<C>> collection;
            if (this.b.hasUpperBound()) {
                collection = this.a.headMap(this.b.upperEndpoint(), false).descendingMap().values();
            } else {
                collection = this.a.descendingMap().values();
            }
            final PeekingIterator peekingIterator = Iterators.peekingIterator(collection.iterator());
            if (peekingIterator.hasNext() && this.b.upperBound.a((ab<ab<C>>) ((Range) peekingIterator.peek()).upperBound)) {
                peekingIterator.next();
            }
            return new AbstractIterator<Map.Entry<ab<C>, Range<C>>>() { // from class: com.google.common.collect.TreeRangeSet.d.2
                /* JADX INFO: Access modifiers changed from: protected */
                /* renamed from: a */
                public Map.Entry<ab<C>, Range<C>> computeNext() {
                    if (!peekingIterator.hasNext()) {
                        return (Map.Entry) endOfData();
                    }
                    Range range = (Range) peekingIterator.next();
                    if (d.this.b.lowerBound.a((ab<C>) range.upperBound)) {
                        return Maps.immutableEntry(range.upperBound, range);
                    }
                    return (Map.Entry) endOfData();
                }
            };
        }

        @Override // java.util.AbstractMap, java.util.Map
        public int size() {
            if (this.b.equals(Range.all())) {
                return this.a.size();
            }
            return Iterators.size(b());
        }

        @Override // java.util.AbstractMap, java.util.Map
        public boolean isEmpty() {
            if (this.b.equals(Range.all())) {
                return this.a.isEmpty();
            }
            return !b().hasNext();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static final class c<C extends Comparable<?>> extends i<ab<C>, Range<C>> {
        private final NavigableMap<ab<C>, Range<C>> a;
        private final NavigableMap<ab<C>, Range<C>> b;
        private final Range<ab<C>> c;

        c(NavigableMap<ab<C>, Range<C>> navigableMap) {
            this(navigableMap, Range.all());
        }

        private c(NavigableMap<ab<C>, Range<C>> navigableMap, Range<ab<C>> range) {
            this.a = navigableMap;
            this.b = new d(navigableMap);
            this.c = range;
        }

        private NavigableMap<ab<C>, Range<C>> a(Range<ab<C>> range) {
            if (!this.c.isConnected(range)) {
                return ImmutableSortedMap.of();
            }
            return new c(this.a, range.intersection(this.c));
        }

        /* renamed from: a */
        public NavigableMap<ab<C>, Range<C>> subMap(ab<C> abVar, boolean z, ab<C> abVar2, boolean z2) {
            return a((Range) Range.range(abVar, BoundType.a(z), abVar2, BoundType.a(z2)));
        }

        /* renamed from: a */
        public NavigableMap<ab<C>, Range<C>> headMap(ab<C> abVar, boolean z) {
            return a((Range) Range.upTo(abVar, BoundType.a(z)));
        }

        /* renamed from: b */
        public NavigableMap<ab<C>, Range<C>> tailMap(ab<C> abVar, boolean z) {
            return a((Range) Range.downTo(abVar, BoundType.a(z)));
        }

        @Override // java.util.SortedMap
        public Comparator<? super ab<C>> comparator() {
            return Ordering.natural();
        }

        @Override // com.google.common.collect.Maps.m
        Iterator<Map.Entry<ab<C>, Range<C>>> b() {
            Collection<Range<C>> collection;
            final ab abVar;
            if (this.c.hasLowerBound()) {
                collection = this.b.tailMap(this.c.lowerEndpoint(), this.c.lowerBoundType() == BoundType.CLOSED).values();
            } else {
                collection = this.b.values();
            }
            final PeekingIterator peekingIterator = Iterators.peekingIterator(collection.iterator());
            if (this.c.contains(ab.d()) && (!peekingIterator.hasNext() || ((Range) peekingIterator.peek()).lowerBound != ab.d())) {
                abVar = ab.d();
            } else if (!peekingIterator.hasNext()) {
                return Iterators.a();
            } else {
                abVar = ((Range) peekingIterator.next()).upperBound;
            }
            return new AbstractIterator<Map.Entry<ab<C>, Range<C>>>() { // from class: com.google.common.collect.TreeRangeSet.c.1
                ab<C> a;

                {
                    this.a = abVar;
                }

                /* JADX INFO: Access modifiers changed from: protected */
                /* renamed from: a */
                public Map.Entry<ab<C>, Range<C>> computeNext() {
                    Range range;
                    if (c.this.c.upperBound.a((ab<C>) this.a) || this.a == ab.e()) {
                        return (Map.Entry) endOfData();
                    }
                    if (peekingIterator.hasNext()) {
                        Range range2 = (Range) peekingIterator.next();
                        range = Range.a((ab) this.a, (ab) range2.lowerBound);
                        this.a = range2.upperBound;
                    } else {
                        range = Range.a((ab) this.a, ab.e());
                        this.a = ab.e();
                    }
                    return Maps.immutableEntry(range.lowerBound, range);
                }
            };
        }

        @Override // com.google.common.collect.i
        Iterator<Map.Entry<ab<C>, Range<C>>> a() {
            ab<C> abVar;
            ab<C> abVar2;
            if (this.c.hasUpperBound()) {
                abVar = this.c.upperEndpoint();
            } else {
                abVar = ab.e();
            }
            final PeekingIterator peekingIterator = Iterators.peekingIterator(this.b.headMap(abVar, this.c.hasUpperBound() && this.c.upperBoundType() == BoundType.CLOSED).descendingMap().values().iterator());
            if (peekingIterator.hasNext()) {
                if (((Range) peekingIterator.peek()).upperBound == ab.e()) {
                    abVar2 = ((Range) peekingIterator.next()).lowerBound;
                } else {
                    abVar2 = this.a.higherKey(((Range) peekingIterator.peek()).upperBound);
                }
            } else if (!this.c.contains(ab.d()) || this.a.containsKey(ab.d())) {
                return Iterators.a();
            } else {
                abVar2 = this.a.higherKey(ab.d());
            }
            final ab abVar3 = (ab) MoreObjects.firstNonNull(abVar2, ab.e());
            return new AbstractIterator<Map.Entry<ab<C>, Range<C>>>() { // from class: com.google.common.collect.TreeRangeSet.c.2
                ab<C> a;

                {
                    this.a = abVar3;
                }

                /* JADX INFO: Access modifiers changed from: protected */
                /* renamed from: a */
                public Map.Entry<ab<C>, Range<C>> computeNext() {
                    if (this.a == ab.d()) {
                        return (Map.Entry) endOfData();
                    }
                    if (peekingIterator.hasNext()) {
                        Range range = (Range) peekingIterator.next();
                        Range a = Range.a((ab) range.upperBound, (ab) this.a);
                        this.a = range.lowerBound;
                        if (c.this.c.lowerBound.a((ab<C>) a.lowerBound)) {
                            return Maps.immutableEntry(a.lowerBound, a);
                        }
                    } else if (c.this.c.lowerBound.a((ab<C>) ab.d())) {
                        Range a2 = Range.a(ab.d(), (ab) this.a);
                        this.a = ab.d();
                        return Maps.immutableEntry(ab.d(), a2);
                    }
                    return (Map.Entry) endOfData();
                }
            };
        }

        @Override // java.util.AbstractMap, java.util.Map
        public int size() {
            return Iterators.size(b());
        }

        @NullableDecl
        /* renamed from: a */
        public Range<C> get(Object obj) {
            if (obj instanceof ab) {
                try {
                    ab<C> abVar = (ab) obj;
                    Map.Entry<ab<C>, Range<C>> firstEntry = tailMap(abVar, true).firstEntry();
                    if (firstEntry != null && firstEntry.getKey().equals(abVar)) {
                        return firstEntry.getValue();
                    }
                } catch (ClassCastException unused) {
                    return null;
                }
            }
            return null;
        }

        @Override // java.util.AbstractMap, java.util.Map
        public boolean containsKey(Object obj) {
            return get(obj) != null;
        }
    }

    /* loaded from: classes2.dex */
    private final class b extends TreeRangeSet<C> {
        b() {
            super(new c(TreeRangeSet.this.rangesByLowerBound));
        }

        @Override // com.google.common.collect.TreeRangeSet, com.google.common.collect.j, com.google.common.collect.RangeSet
        public void add(Range<C> range) {
            TreeRangeSet.this.remove(range);
        }

        @Override // com.google.common.collect.TreeRangeSet, com.google.common.collect.j, com.google.common.collect.RangeSet
        public void remove(Range<C> range) {
            TreeRangeSet.this.add(range);
        }

        @Override // com.google.common.collect.TreeRangeSet, com.google.common.collect.j, com.google.common.collect.RangeSet
        public boolean contains(C c) {
            return !TreeRangeSet.this.contains(c);
        }

        @Override // com.google.common.collect.TreeRangeSet, com.google.common.collect.RangeSet
        public RangeSet<C> complement() {
            return TreeRangeSet.this;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static final class f<C extends Comparable<?>> extends i<ab<C>, Range<C>> {
        private final Range<ab<C>> a;
        private final Range<C> b;
        private final NavigableMap<ab<C>, Range<C>> c;
        private final NavigableMap<ab<C>, Range<C>> d;

        private f(Range<ab<C>> range, Range<C> range2, NavigableMap<ab<C>, Range<C>> navigableMap) {
            this.a = (Range) Preconditions.checkNotNull(range);
            this.b = (Range) Preconditions.checkNotNull(range2);
            this.c = (NavigableMap) Preconditions.checkNotNull(navigableMap);
            this.d = new d(navigableMap);
        }

        private NavigableMap<ab<C>, Range<C>> a(Range<ab<C>> range) {
            if (!range.isConnected(this.a)) {
                return ImmutableSortedMap.of();
            }
            return new f(this.a.intersection(range), this.b, this.c);
        }

        /* renamed from: a */
        public NavigableMap<ab<C>, Range<C>> subMap(ab<C> abVar, boolean z, ab<C> abVar2, boolean z2) {
            return a((Range) Range.range(abVar, BoundType.a(z), abVar2, BoundType.a(z2)));
        }

        /* renamed from: a */
        public NavigableMap<ab<C>, Range<C>> headMap(ab<C> abVar, boolean z) {
            return a((Range) Range.upTo(abVar, BoundType.a(z)));
        }

        /* renamed from: b */
        public NavigableMap<ab<C>, Range<C>> tailMap(ab<C> abVar, boolean z) {
            return a((Range) Range.downTo(abVar, BoundType.a(z)));
        }

        @Override // java.util.SortedMap
        public Comparator<? super ab<C>> comparator() {
            return Ordering.natural();
        }

        @Override // java.util.AbstractMap, java.util.Map
        public boolean containsKey(@NullableDecl Object obj) {
            return get(obj) != null;
        }

        @NullableDecl
        /* renamed from: a */
        public Range<C> get(@NullableDecl Object obj) {
            if (obj instanceof ab) {
                try {
                    ab<C> abVar = (ab) obj;
                    if (this.a.contains(abVar) && abVar.a(this.b.lowerBound) >= 0 && abVar.a(this.b.upperBound) < 0) {
                        if (abVar.equals(this.b.lowerBound)) {
                            Range range = (Range) Maps.c(this.c.floorEntry(abVar));
                            if (range != null && range.upperBound.a((ab) this.b.lowerBound) > 0) {
                                return range.intersection(this.b);
                            }
                        } else {
                            Range range2 = (Range) this.c.get(abVar);
                            if (range2 != null) {
                                return range2.intersection(this.b);
                            }
                        }
                    }
                    return null;
                } catch (ClassCastException unused) {
                    return null;
                }
            }
            return null;
        }

        @Override // com.google.common.collect.Maps.m
        Iterator<Map.Entry<ab<C>, Range<C>>> b() {
            final Iterator<Range<C>> it;
            if (this.b.isEmpty()) {
                return Iterators.a();
            }
            if (this.a.upperBound.a((ab<ab<C>>) this.b.lowerBound)) {
                return Iterators.a();
            }
            boolean z = false;
            if (this.a.lowerBound.a((ab<ab<C>>) this.b.lowerBound)) {
                it = this.d.tailMap(this.b.lowerBound, false).values().iterator();
            } else {
                NavigableMap<ab<C>, Range<C>> navigableMap = this.c;
                ab<C> c = this.a.lowerBound.c();
                if (this.a.lowerBoundType() == BoundType.CLOSED) {
                    z = true;
                }
                it = navigableMap.tailMap(c, z).values().iterator();
            }
            final ab abVar = (ab) Ordering.natural().min(this.a.upperBound, ab.b(this.b.upperBound));
            return new AbstractIterator<Map.Entry<ab<C>, Range<C>>>() { // from class: com.google.common.collect.TreeRangeSet.f.1
                /* JADX INFO: Access modifiers changed from: protected */
                /* renamed from: a */
                public Map.Entry<ab<C>, Range<C>> computeNext() {
                    if (!it.hasNext()) {
                        return (Map.Entry) endOfData();
                    }
                    Range range = (Range) it.next();
                    if (abVar.a((ab) range.lowerBound)) {
                        return (Map.Entry) endOfData();
                    }
                    Range intersection = range.intersection(f.this.b);
                    return Maps.immutableEntry(intersection.lowerBound, intersection);
                }
            };
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.common.collect.i
        Iterator<Map.Entry<ab<C>, Range<C>>> a() {
            if (this.b.isEmpty()) {
                return Iterators.a();
            }
            ab abVar = (ab) Ordering.natural().min(this.a.upperBound, ab.b(this.b.upperBound));
            final Iterator it = this.c.headMap(abVar.c(), abVar.b() == BoundType.CLOSED).descendingMap().values().iterator();
            return new AbstractIterator<Map.Entry<ab<C>, Range<C>>>() { // from class: com.google.common.collect.TreeRangeSet.f.2
                /* JADX INFO: Access modifiers changed from: protected */
                /* renamed from: a */
                public Map.Entry<ab<C>, Range<C>> computeNext() {
                    if (!it.hasNext()) {
                        return (Map.Entry) endOfData();
                    }
                    Range range = (Range) it.next();
                    if (f.this.b.lowerBound.a((ab) range.upperBound) >= 0) {
                        return (Map.Entry) endOfData();
                    }
                    Range intersection = range.intersection(f.this.b);
                    if (f.this.a.contains(intersection.lowerBound)) {
                        return Maps.immutableEntry(intersection.lowerBound, intersection);
                    }
                    return (Map.Entry) endOfData();
                }
            };
        }

        @Override // java.util.AbstractMap, java.util.Map
        public int size() {
            return Iterators.size(b());
        }
    }

    @Override // com.google.common.collect.RangeSet
    public RangeSet<C> subRangeSet(Range<C> range) {
        return range.equals(Range.all()) ? this : new e(range);
    }

    /* loaded from: classes2.dex */
    private final class e extends TreeRangeSet<C> {
        private final Range<C> restriction;

        e(Range<C> range) {
            super(new f(Range.all(), range, TreeRangeSet.this.rangesByLowerBound));
            this.restriction = range;
        }

        @Override // com.google.common.collect.TreeRangeSet, com.google.common.collect.j, com.google.common.collect.RangeSet
        public boolean encloses(Range<C> range) {
            Range a;
            return !this.restriction.isEmpty() && this.restriction.encloses(range) && (a = TreeRangeSet.this.a(range)) != null && !a.intersection(this.restriction).isEmpty();
        }

        @Override // com.google.common.collect.TreeRangeSet, com.google.common.collect.j, com.google.common.collect.RangeSet
        @NullableDecl
        public Range<C> rangeContaining(C c) {
            Range<C> rangeContaining;
            if (this.restriction.contains(c) && (rangeContaining = TreeRangeSet.this.rangeContaining(c)) != null) {
                return rangeContaining.intersection((Range<C>) this.restriction);
            }
            return null;
        }

        @Override // com.google.common.collect.TreeRangeSet, com.google.common.collect.j, com.google.common.collect.RangeSet
        public void add(Range<C> range) {
            Preconditions.checkArgument(this.restriction.encloses(range), "Cannot add range %s to subRangeSet(%s)", range, this.restriction);
            TreeRangeSet.super.add(range);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.common.collect.TreeRangeSet, com.google.common.collect.j, com.google.common.collect.RangeSet
        public void remove(Range<C> range) {
            if (range.isConnected(this.restriction)) {
                TreeRangeSet.this.remove(range.intersection(this.restriction));
            }
        }

        @Override // com.google.common.collect.TreeRangeSet, com.google.common.collect.j, com.google.common.collect.RangeSet
        public boolean contains(C c) {
            return this.restriction.contains(c) && TreeRangeSet.this.contains(c);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.common.collect.TreeRangeSet, com.google.common.collect.j, com.google.common.collect.RangeSet
        public void clear() {
            TreeRangeSet.this.remove(this.restriction);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.common.collect.TreeRangeSet, com.google.common.collect.RangeSet
        public RangeSet<C> subRangeSet(Range<C> range) {
            if (range.encloses(this.restriction)) {
                return this;
            }
            if (range.isConnected(this.restriction)) {
                return new e(this.restriction.intersection(range));
            }
            return ImmutableRangeSet.of();
        }
    }
}
