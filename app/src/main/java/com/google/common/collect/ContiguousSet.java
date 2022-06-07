package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSortedSet;
import java.lang.Comparable;
import java.util.NoSuchElementException;

@GwtCompatible(emulated = true)
/* loaded from: classes2.dex */
public abstract class ContiguousSet<C extends Comparable> extends ImmutableSortedSet<C> {
    final DiscreteDomain<C> domain;

    abstract ContiguousSet<C> a(C c, boolean z);

    abstract ContiguousSet<C> a(C c, boolean z, C c2, boolean z2);

    abstract ContiguousSet<C> b(C c, boolean z);

    public abstract ContiguousSet<C> intersection(ContiguousSet<C> contiguousSet);

    public abstract Range<C> range();

    public abstract Range<C> range(BoundType boundType, BoundType boundType2);

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.collect.ImmutableSortedSet
    /* synthetic */ ImmutableSortedSet a(Object obj, boolean z) {
        return b((ContiguousSet<C>) ((Comparable) obj), z);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.collect.ImmutableSortedSet
    /* bridge */ /* synthetic */ ImmutableSortedSet a(Object obj, boolean z, Object obj2, boolean z2) {
        return a((boolean) ((Comparable) obj), z, (boolean) ((Comparable) obj2), z2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.collect.ImmutableSortedSet
    public /* synthetic */ ImmutableSortedSet b(Object obj, boolean z) {
        return a((ContiguousSet<C>) ((Comparable) obj), z);
    }

    public static <C extends Comparable> ContiguousSet<C> create(Range<C> range, DiscreteDomain<C> discreteDomain) {
        Preconditions.checkNotNull(range);
        Preconditions.checkNotNull(discreteDomain);
        try {
            Range<C> intersection = !range.hasLowerBound() ? range.intersection(Range.atLeast(discreteDomain.minValue())) : range;
            if (!range.hasUpperBound()) {
                intersection = intersection.intersection(Range.atMost(discreteDomain.maxValue()));
            }
            return intersection.isEmpty() || Range.a(range.lowerBound.a(discreteDomain), range.upperBound.b(discreteDomain)) > 0 ? new ag(discreteDomain) : new br(intersection, discreteDomain);
        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Beta
    public static ContiguousSet<Integer> closed(int i, int i2) {
        return create(Range.closed(Integer.valueOf(i), Integer.valueOf(i2)), DiscreteDomain.integers());
    }

    @Beta
    public static ContiguousSet<Long> closed(long j, long j2) {
        return create(Range.closed(Long.valueOf(j), Long.valueOf(j2)), DiscreteDomain.longs());
    }

    @Beta
    public static ContiguousSet<Integer> closedOpen(int i, int i2) {
        return create(Range.closedOpen(Integer.valueOf(i), Integer.valueOf(i2)), DiscreteDomain.integers());
    }

    @Beta
    public static ContiguousSet<Long> closedOpen(long j, long j2) {
        return create(Range.closedOpen(Long.valueOf(j), Long.valueOf(j2)), DiscreteDomain.longs());
    }

    public ContiguousSet(DiscreteDomain<C> discreteDomain) {
        super(Ordering.natural());
        this.domain = discreteDomain;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public ContiguousSet<C> headSet(C c) {
        return a((ContiguousSet<C>) ((Comparable) Preconditions.checkNotNull(c)), false);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @GwtIncompatible
    public ContiguousSet<C> headSet(C c, boolean z) {
        return a((ContiguousSet<C>) ((Comparable) Preconditions.checkNotNull(c)), z);
    }

    public ContiguousSet<C> subSet(C c, C c2) {
        Preconditions.checkNotNull(c);
        Preconditions.checkNotNull(c2);
        Preconditions.checkArgument(comparator().compare(c, c2) <= 0);
        return a((boolean) c, true, (boolean) c2, false);
    }

    @GwtIncompatible
    public ContiguousSet<C> subSet(C c, boolean z, C c2, boolean z2) {
        Preconditions.checkNotNull(c);
        Preconditions.checkNotNull(c2);
        Preconditions.checkArgument(comparator().compare(c, c2) <= 0);
        return a((boolean) c, z, (boolean) c2, z2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public ContiguousSet<C> tailSet(C c) {
        return b((ContiguousSet<C>) ((Comparable) Preconditions.checkNotNull(c)), true);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @GwtIncompatible
    public ContiguousSet<C> tailSet(C c, boolean z) {
        return b((ContiguousSet<C>) ((Comparable) Preconditions.checkNotNull(c)), z);
    }

    @Override // com.google.common.collect.ImmutableSortedSet
    @GwtIncompatible
    ImmutableSortedSet<C> b() {
        return new ae(this);
    }

    @Override // java.util.AbstractCollection
    public String toString() {
        return range().toString();
    }

    @Deprecated
    public static <E> ImmutableSortedSet.Builder<E> builder() {
        throw new UnsupportedOperationException();
    }
}
