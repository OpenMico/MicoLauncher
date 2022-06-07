package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ch;
import com.google.common.primitives.Ints;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.concurrent.LazyInit;
import java.io.Serializable;
import java.lang.Comparable;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@Beta
@GwtIncompatible
/* loaded from: classes2.dex */
public final class ImmutableRangeSet<C extends Comparable> extends j<C> implements Serializable {
    private static final ImmutableRangeSet<Comparable<?>> a = new ImmutableRangeSet<>(ImmutableList.of());
    private static final ImmutableRangeSet<Comparable<?>> b = new ImmutableRangeSet<>(ImmutableList.of(Range.all()));
    private final transient ImmutableList<Range<C>> c;
    @LazyInit
    private transient ImmutableRangeSet<C> d;

    @Override // com.google.common.collect.j, com.google.common.collect.RangeSet
    public /* bridge */ /* synthetic */ void clear() {
        super.clear();
    }

    /* JADX WARN: Multi-variable type inference failed */
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

    public static <C extends Comparable> ImmutableRangeSet<C> of() {
        return a;
    }

    public static <C extends Comparable> ImmutableRangeSet<C> of(Range<C> range) {
        Preconditions.checkNotNull(range);
        if (range.isEmpty()) {
            return of();
        }
        if (range.equals(Range.all())) {
            return a();
        }
        return new ImmutableRangeSet<>(ImmutableList.of(range));
    }

    static <C extends Comparable> ImmutableRangeSet<C> a() {
        return b;
    }

    public static <C extends Comparable> ImmutableRangeSet<C> copyOf(RangeSet<C> rangeSet) {
        Preconditions.checkNotNull(rangeSet);
        if (rangeSet.isEmpty()) {
            return of();
        }
        if (rangeSet.encloses(Range.all())) {
            return a();
        }
        if (rangeSet instanceof ImmutableRangeSet) {
            ImmutableRangeSet<C> immutableRangeSet = (ImmutableRangeSet) rangeSet;
            if (!immutableRangeSet.b()) {
                return immutableRangeSet;
            }
        }
        return new ImmutableRangeSet<>(ImmutableList.copyOf((Collection) rangeSet.asRanges()));
    }

    public static <C extends Comparable<?>> ImmutableRangeSet<C> copyOf(Iterable<Range<C>> iterable) {
        return new Builder().addAll(iterable).build();
    }

    public static <C extends Comparable<?>> ImmutableRangeSet<C> unionOf(Iterable<Range<C>> iterable) {
        return copyOf(TreeRangeSet.create(iterable));
    }

    ImmutableRangeSet(ImmutableList<Range<C>> immutableList) {
        this.c = immutableList;
    }

    private ImmutableRangeSet(ImmutableList<Range<C>> immutableList, ImmutableRangeSet<C> immutableRangeSet) {
        this.c = immutableList;
        this.d = immutableRangeSet;
    }

    @Override // com.google.common.collect.j, com.google.common.collect.RangeSet
    public boolean intersects(Range<C> range) {
        int a2 = ch.a(this.c, Range.a(), range.lowerBound, Ordering.natural(), ch.b.ANY_PRESENT, ch.a.NEXT_HIGHER);
        if (a2 < this.c.size() && this.c.get(a2).isConnected(range) && !this.c.get(a2).intersection(range).isEmpty()) {
            return true;
        }
        if (a2 > 0) {
            int i = a2 - 1;
            if (this.c.get(i).isConnected(range) && !this.c.get(i).intersection(range).isEmpty()) {
                return true;
            }
        }
        return false;
    }

    @Override // com.google.common.collect.j, com.google.common.collect.RangeSet
    public boolean encloses(Range<C> range) {
        int a2 = ch.a(this.c, Range.a(), range.lowerBound, Ordering.natural(), ch.b.ANY_PRESENT, ch.a.NEXT_LOWER);
        return a2 != -1 && this.c.get(a2).encloses(range);
    }

    @Override // com.google.common.collect.j, com.google.common.collect.RangeSet
    public Range<C> rangeContaining(C c2) {
        int a2 = ch.a(this.c, Range.a(), ab.b(c2), Ordering.natural(), ch.b.ANY_PRESENT, ch.a.NEXT_LOWER);
        if (a2 == -1) {
            return null;
        }
        Range<C> range = this.c.get(a2);
        if (range.contains(c2)) {
            return range;
        }
        return null;
    }

    @Override // com.google.common.collect.RangeSet
    public Range<C> span() {
        if (!this.c.isEmpty()) {
            ab<C> abVar = this.c.get(0).lowerBound;
            ImmutableList<Range<C>> immutableList = this.c;
            return Range.a((ab) abVar, (ab) immutableList.get(immutableList.size() - 1).upperBound);
        }
        throw new NoSuchElementException();
    }

    @Override // com.google.common.collect.j, com.google.common.collect.RangeSet
    public boolean isEmpty() {
        return this.c.isEmpty();
    }

    @Override // com.google.common.collect.j, com.google.common.collect.RangeSet
    @Deprecated
    public void add(Range<C> range) {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.common.collect.j, com.google.common.collect.RangeSet
    @Deprecated
    public void addAll(RangeSet<C> rangeSet) {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.common.collect.j, com.google.common.collect.RangeSet
    @Deprecated
    public void addAll(Iterable<Range<C>> iterable) {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.common.collect.j, com.google.common.collect.RangeSet
    @Deprecated
    public void remove(Range<C> range) {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.common.collect.j, com.google.common.collect.RangeSet
    @Deprecated
    public void removeAll(RangeSet<C> rangeSet) {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.common.collect.j, com.google.common.collect.RangeSet
    @Deprecated
    public void removeAll(Iterable<Range<C>> iterable) {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.common.collect.RangeSet
    public ImmutableSet<Range<C>> asRanges() {
        if (this.c.isEmpty()) {
            return ImmutableSet.of();
        }
        return new by(this.c, Range.c());
    }

    @Override // com.google.common.collect.RangeSet
    public ImmutableSet<Range<C>> asDescendingSetOfRanges() {
        if (this.c.isEmpty()) {
            return ImmutableSet.of();
        }
        return new by(this.c.reverse(), Range.c().reverse());
    }

    /* loaded from: classes2.dex */
    public final class c extends ImmutableList<Range<C>> {
        private final boolean positiveBoundedAbove;
        private final boolean positiveBoundedBelow;
        private final int size;

        @Override // com.google.common.collect.ImmutableCollection
        public boolean a() {
            return true;
        }

        c() {
            ImmutableRangeSet.this = r3;
            this.positiveBoundedBelow = ((Range) r3.c.get(0)).hasLowerBound();
            this.positiveBoundedAbove = ((Range) Iterables.getLast(r3.c)).hasUpperBound();
            int size = r3.c.size() - 1;
            size = this.positiveBoundedBelow ? size + 1 : size;
            this.size = this.positiveBoundedAbove ? size + 1 : size;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public int size() {
            return this.size;
        }

        /* renamed from: a */
        public Range<C> get(int i) {
            ab<C> abVar;
            ab<C> abVar2;
            Preconditions.checkElementIndex(i, this.size);
            if (this.positiveBoundedBelow) {
                abVar = i == 0 ? ab.d() : ((Range) ImmutableRangeSet.this.c.get(i - 1)).upperBound;
            } else {
                abVar = ((Range) ImmutableRangeSet.this.c.get(i)).upperBound;
            }
            if (!this.positiveBoundedAbove || i != this.size - 1) {
                abVar2 = ((Range) ImmutableRangeSet.this.c.get(i + (!this.positiveBoundedBelow ? 1 : 0))).lowerBound;
            } else {
                abVar2 = ab.e();
            }
            return Range.a((ab) abVar, (ab) abVar2);
        }
    }

    @Override // com.google.common.collect.RangeSet
    public ImmutableRangeSet<C> complement() {
        ImmutableRangeSet<C> immutableRangeSet = this.d;
        if (immutableRangeSet != null) {
            return immutableRangeSet;
        }
        if (this.c.isEmpty()) {
            ImmutableRangeSet<C> a2 = a();
            this.d = a2;
            return a2;
        } else if (this.c.size() != 1 || !this.c.get(0).equals(Range.all())) {
            ImmutableRangeSet<C> immutableRangeSet2 = new ImmutableRangeSet<>(new c(), this);
            this.d = immutableRangeSet2;
            return immutableRangeSet2;
        } else {
            ImmutableRangeSet<C> of = of();
            this.d = of;
            return of;
        }
    }

    public ImmutableRangeSet<C> union(RangeSet<C> rangeSet) {
        return unionOf(Iterables.concat(asRanges(), rangeSet.asRanges()));
    }

    public ImmutableRangeSet<C> intersection(RangeSet<C> rangeSet) {
        TreeRangeSet create = TreeRangeSet.create(this);
        create.removeAll(rangeSet.complement());
        return copyOf(create);
    }

    public ImmutableRangeSet<C> difference(RangeSet<C> rangeSet) {
        TreeRangeSet create = TreeRangeSet.create(this);
        create.removeAll(rangeSet);
        return copyOf(create);
    }

    private ImmutableList<Range<C>> a(final Range<C> range) {
        int i;
        if (this.c.isEmpty() || range.isEmpty()) {
            return ImmutableList.of();
        }
        if (range.encloses(span())) {
            return this.c;
        }
        final int a2 = range.hasLowerBound() ? ch.a(this.c, Range.b(), range.lowerBound, ch.b.FIRST_AFTER, ch.a.NEXT_HIGHER) : 0;
        if (range.hasUpperBound()) {
            i = ch.a(this.c, Range.a(), range.upperBound, ch.b.FIRST_PRESENT, ch.a.NEXT_HIGHER);
        } else {
            i = this.c.size();
        }
        final int i2 = i - a2;
        if (i2 == 0) {
            return ImmutableList.of();
        }
        return (ImmutableList<Range<C>>) new ImmutableList<Range<C>>() { // from class: com.google.common.collect.ImmutableRangeSet.1
            @Override // com.google.common.collect.ImmutableCollection
            public boolean a() {
                return true;
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
            public int size() {
                return i2;
            }

            /* renamed from: a */
            public Range<C> get(int i3) {
                Preconditions.checkElementIndex(i3, i2);
                return (i3 == 0 || i3 == i2 + (-1)) ? ((Range) ImmutableRangeSet.this.c.get(i3 + a2)).intersection(range) : (Range) ImmutableRangeSet.this.c.get(i3 + a2);
            }
        };
    }

    @Override // com.google.common.collect.RangeSet
    public ImmutableRangeSet<C> subRangeSet(Range<C> range) {
        if (!isEmpty()) {
            Range<C> span = span();
            if (range.encloses(span)) {
                return this;
            }
            if (range.isConnected(span)) {
                return new ImmutableRangeSet<>(a(range));
            }
        }
        return of();
    }

    public ImmutableSortedSet<C> asSet(DiscreteDomain<C> discreteDomain) {
        Preconditions.checkNotNull(discreteDomain);
        if (isEmpty()) {
            return ImmutableSortedSet.of();
        }
        Range<C> canonical = span().canonical(discreteDomain);
        if (canonical.hasLowerBound()) {
            if (!canonical.hasUpperBound()) {
                try {
                    discreteDomain.maxValue();
                } catch (NoSuchElementException unused) {
                    throw new IllegalArgumentException("Neither the DiscreteDomain nor this range set are bounded above");
                }
            }
            return new a(discreteDomain);
        }
        throw new IllegalArgumentException("Neither the DiscreteDomain nor this range set are bounded below");
    }

    /* loaded from: classes2.dex */
    public final class a extends ImmutableSortedSet<C> {
        @MonotonicNonNullDecl
        private transient Integer c;
        private final DiscreteDomain<C> domain;

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.common.collect.ImmutableSortedSet
        /* synthetic */ ImmutableSortedSet a(Object obj, boolean z) {
            return b((a) ((Comparable) obj), z);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.common.collect.ImmutableSortedSet
        /* bridge */ /* synthetic */ ImmutableSortedSet a(Object obj, boolean z, Object obj2, boolean z2) {
            return a((boolean) ((Comparable) obj), z, (boolean) ((Comparable) obj2), z2);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.common.collect.ImmutableSortedSet
        public /* synthetic */ ImmutableSortedSet b(Object obj, boolean z) {
            return a((a) ((Comparable) obj), z);
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        a(DiscreteDomain<C> discreteDomain) {
            super(Ordering.natural());
            ImmutableRangeSet.this = r1;
            this.domain = discreteDomain;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            Integer num = this.c;
            if (num == null) {
                long j = 0;
                UnmodifiableIterator it = ImmutableRangeSet.this.c.iterator();
                while (it.hasNext()) {
                    j += ContiguousSet.create((Range) it.next(), this.domain).size();
                    if (j >= 2147483647L) {
                        break;
                    }
                }
                num = Integer.valueOf(Ints.saturatedCast(j));
                this.c = num;
            }
            return num.intValue();
        }

        @Override // com.google.common.collect.ImmutableSortedSet, com.google.common.collect.ImmutableSet, com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
        public UnmodifiableIterator<C> iterator() {
            return new AbstractIterator<C>() { // from class: com.google.common.collect.ImmutableRangeSet.a.1
                final Iterator<Range<C>> a;
                Iterator<C> b = Iterators.a();

                {
                    a.this = this;
                    this.a = ImmutableRangeSet.this.c.iterator();
                }

                /* renamed from: a */
                public C computeNext() {
                    while (!this.b.hasNext()) {
                        if (!this.a.hasNext()) {
                            return (C) ((Comparable) endOfData());
                        }
                        this.b = ContiguousSet.create((Range) this.a.next(), a.this.domain).iterator();
                    }
                    return (C) ((Comparable) this.b.next());
                }
            };
        }

        @Override // com.google.common.collect.ImmutableSortedSet, java.util.NavigableSet
        @GwtIncompatible("NavigableSet")
        public UnmodifiableIterator<C> descendingIterator() {
            return new AbstractIterator<C>() { // from class: com.google.common.collect.ImmutableRangeSet.a.2
                final Iterator<Range<C>> a;
                Iterator<C> b = Iterators.a();

                {
                    a.this = this;
                    this.a = ImmutableRangeSet.this.c.reverse().iterator();
                }

                /* renamed from: a */
                public C computeNext() {
                    while (!this.b.hasNext()) {
                        if (!this.a.hasNext()) {
                            return (C) ((Comparable) endOfData());
                        }
                        this.b = ContiguousSet.create((Range) this.a.next(), a.this.domain).descendingIterator();
                    }
                    return (C) ((Comparable) this.b.next());
                }
            };
        }

        ImmutableSortedSet<C> a(Range<C> range) {
            return ImmutableRangeSet.this.subRangeSet((Range) range).asSet((DiscreteDomain<C>) this.domain);
        }

        ImmutableSortedSet<C> a(C c, boolean z) {
            return a((Range) Range.upTo(c, BoundType.a(z)));
        }

        ImmutableSortedSet<C> a(C c, boolean z, C c2, boolean z2) {
            if (z || z2 || Range.a(c, c2) != 0) {
                return a((Range) Range.range(c, BoundType.a(z), c2, BoundType.a(z2)));
            }
            return ImmutableSortedSet.of();
        }

        ImmutableSortedSet<C> b(C c, boolean z) {
            return a((Range) Range.downTo(c, BoundType.a(z)));
        }

        @Override // com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection
        public boolean contains(@NullableDecl Object obj) {
            if (obj == null) {
                return false;
            }
            try {
                return ImmutableRangeSet.this.contains((Comparable) obj);
            } catch (ClassCastException unused) {
                return false;
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.common.collect.ImmutableSortedSet
        public int a(Object obj) {
            if (!contains(obj)) {
                return -1;
            }
            Comparable comparable = (Comparable) obj;
            long j = 0;
            UnmodifiableIterator it = ImmutableRangeSet.this.c.iterator();
            while (it.hasNext()) {
                Range range = (Range) it.next();
                if (range.contains(comparable)) {
                    return Ints.saturatedCast(j + ContiguousSet.create(range, this.domain).a(comparable));
                }
                j += ContiguousSet.create(range, this.domain).size();
            }
            throw new AssertionError("impossible");
        }

        @Override // com.google.common.collect.ImmutableSortedSet
        ImmutableSortedSet<C> b() {
            return new ae(this);
        }

        @Override // com.google.common.collect.ImmutableCollection
        public boolean a() {
            return ImmutableRangeSet.this.c.a();
        }

        @Override // java.util.AbstractCollection
        public String toString() {
            return ImmutableRangeSet.this.c.toString();
        }

        @Override // com.google.common.collect.ImmutableSortedSet, com.google.common.collect.ImmutableSet, com.google.common.collect.ImmutableCollection
        Object writeReplace() {
            return new b(ImmutableRangeSet.this.c, this.domain);
        }
    }

    /* loaded from: classes2.dex */
    private static class b<C extends Comparable> implements Serializable {
        private final DiscreteDomain<C> domain;
        private final ImmutableList<Range<C>> ranges;

        b(ImmutableList<Range<C>> immutableList, DiscreteDomain<C> discreteDomain) {
            this.ranges = immutableList;
            this.domain = discreteDomain;
        }

        Object readResolve() {
            return new ImmutableRangeSet(this.ranges).asSet(this.domain);
        }
    }

    boolean b() {
        return this.c.a();
    }

    public static <C extends Comparable<?>> Builder<C> builder() {
        return new Builder<>();
    }

    /* loaded from: classes2.dex */
    public static class Builder<C extends Comparable<?>> {
        private final List<Range<C>> a = Lists.newArrayList();

        @CanIgnoreReturnValue
        public Builder<C> add(Range<C> range) {
            Preconditions.checkArgument(!range.isEmpty(), "range must not be empty, but was %s", range);
            this.a.add(range);
            return this;
        }

        @CanIgnoreReturnValue
        public Builder<C> addAll(RangeSet<C> rangeSet) {
            return addAll(rangeSet.asRanges());
        }

        @CanIgnoreReturnValue
        public Builder<C> addAll(Iterable<Range<C>> iterable) {
            for (Range<C> range : iterable) {
                add(range);
            }
            return this;
        }

        public ImmutableRangeSet<C> build() {
            ImmutableList.Builder builder = new ImmutableList.Builder(this.a.size());
            Collections.sort(this.a, Range.c());
            PeekingIterator peekingIterator = Iterators.peekingIterator(this.a.iterator());
            while (peekingIterator.hasNext()) {
                Range range = (Range) peekingIterator.next();
                while (peekingIterator.hasNext()) {
                    Range<C> range2 = (Range) peekingIterator.peek();
                    if (range.isConnected(range2)) {
                        Preconditions.checkArgument(range.intersection(range2).isEmpty(), "Overlapping ranges not permitted but found %s overlapping %s", range, range2);
                        range = range.span((Range) peekingIterator.next());
                    }
                }
                builder.add((ImmutableList.Builder) range);
            }
            ImmutableList build = builder.build();
            if (build.isEmpty()) {
                return ImmutableRangeSet.of();
            }
            if (build.size() != 1 || !((Range) Iterables.getOnlyElement(build)).equals(Range.all())) {
                return new ImmutableRangeSet<>(build);
            }
            return ImmutableRangeSet.a();
        }
    }

    /* loaded from: classes2.dex */
    private static final class d<C extends Comparable> implements Serializable {
        private final ImmutableList<Range<C>> ranges;

        d(ImmutableList<Range<C>> immutableList) {
            this.ranges = immutableList;
        }

        Object readResolve() {
            if (this.ranges.isEmpty()) {
                return ImmutableRangeSet.of();
            }
            if (this.ranges.equals(ImmutableList.of(Range.all()))) {
                return ImmutableRangeSet.a();
            }
            return new ImmutableRangeSet(this.ranges);
        }
    }

    Object writeReplace() {
        return new d(this.c);
    }
}
