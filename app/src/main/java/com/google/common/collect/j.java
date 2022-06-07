package com.google.common.collect;

import com.google.common.annotations.GwtIncompatible;
import java.lang.Comparable;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* compiled from: AbstractRangeSet.java */
@GwtIncompatible
/* loaded from: classes2.dex */
abstract class j<C extends Comparable> implements RangeSet<C> {
    @Override // com.google.common.collect.RangeSet
    public abstract boolean encloses(Range<C> range);

    @Override // com.google.common.collect.RangeSet
    public abstract Range<C> rangeContaining(C c);

    @Override // com.google.common.collect.RangeSet
    public boolean contains(C c) {
        return rangeContaining(c) != null;
    }

    @Override // com.google.common.collect.RangeSet
    public boolean isEmpty() {
        return asRanges().isEmpty();
    }

    @Override // com.google.common.collect.RangeSet
    public void add(Range<C> range) {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.common.collect.RangeSet
    public void remove(Range<C> range) {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.common.collect.RangeSet
    public void clear() {
        remove(Range.all());
    }

    @Override // com.google.common.collect.RangeSet
    public boolean enclosesAll(RangeSet<C> rangeSet) {
        return enclosesAll(rangeSet.asRanges());
    }

    @Override // com.google.common.collect.RangeSet
    public boolean enclosesAll(Iterable<Range<C>> iterable) {
        for (Range<C> range : iterable) {
            if (!encloses(range)) {
                return false;
            }
        }
        return true;
    }

    @Override // com.google.common.collect.RangeSet
    public void addAll(RangeSet<C> rangeSet) {
        addAll(rangeSet.asRanges());
    }

    @Override // com.google.common.collect.RangeSet
    public void addAll(Iterable<Range<C>> iterable) {
        for (Range<C> range : iterable) {
            add(range);
        }
    }

    @Override // com.google.common.collect.RangeSet
    public void removeAll(RangeSet<C> rangeSet) {
        removeAll(rangeSet.asRanges());
    }

    @Override // com.google.common.collect.RangeSet
    public void removeAll(Iterable<Range<C>> iterable) {
        for (Range<C> range : iterable) {
            remove(range);
        }
    }

    @Override // com.google.common.collect.RangeSet
    public boolean intersects(Range<C> range) {
        return !subRangeSet(range).isEmpty();
    }

    @Override // com.google.common.collect.RangeSet
    public boolean equals(@NullableDecl Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof RangeSet) {
            return asRanges().equals(((RangeSet) obj).asRanges());
        }
        return false;
    }

    @Override // com.google.common.collect.RangeSet
    public final int hashCode() {
        return asRanges().hashCode();
    }

    @Override // com.google.common.collect.RangeSet
    public final String toString() {
        return asRanges().toString();
    }
}
