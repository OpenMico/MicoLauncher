package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ch;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.Serializable;
import java.lang.Comparable;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@Beta
@GwtIncompatible
/* loaded from: classes2.dex */
public class ImmutableRangeMap<K extends Comparable<?>, V> implements RangeMap<K, V>, Serializable {
    private static final ImmutableRangeMap<Comparable<?>, Object> a = new ImmutableRangeMap<>(ImmutableList.of(), ImmutableList.of());
    private static final long serialVersionUID = 0;
    private final transient ImmutableList<Range<K>> b;
    private final transient ImmutableList<V> c;

    public static <K extends Comparable<?>, V> ImmutableRangeMap<K, V> of() {
        return (ImmutableRangeMap<K, V>) a;
    }

    public static <K extends Comparable<?>, V> ImmutableRangeMap<K, V> of(Range<K> range, V v) {
        return new ImmutableRangeMap<>(ImmutableList.of(range), ImmutableList.of(v));
    }

    public static <K extends Comparable<?>, V> ImmutableRangeMap<K, V> copyOf(RangeMap<K, ? extends V> rangeMap) {
        if (rangeMap instanceof ImmutableRangeMap) {
            return (ImmutableRangeMap) rangeMap;
        }
        Map<Range<K>, ? extends V> asMapOfRanges = rangeMap.asMapOfRanges();
        ImmutableList.Builder builder = new ImmutableList.Builder(asMapOfRanges.size());
        ImmutableList.Builder builder2 = new ImmutableList.Builder(asMapOfRanges.size());
        for (Map.Entry<Range<K>, ? extends V> entry : asMapOfRanges.entrySet()) {
            builder.add((ImmutableList.Builder) entry.getKey());
            builder2.add((ImmutableList.Builder) entry.getValue());
        }
        return new ImmutableRangeMap<>(builder.build(), builder2.build());
    }

    public static <K extends Comparable<?>, V> Builder<K, V> builder() {
        return new Builder<>();
    }

    /* loaded from: classes2.dex */
    public static final class Builder<K extends Comparable<?>, V> {
        private final List<Map.Entry<Range<K>, V>> a = Lists.newArrayList();

        @CanIgnoreReturnValue
        public Builder<K, V> put(Range<K> range, V v) {
            Preconditions.checkNotNull(range);
            Preconditions.checkNotNull(v);
            Preconditions.checkArgument(!range.isEmpty(), "Range must not be empty, but was %s", range);
            this.a.add(Maps.immutableEntry(range, v));
            return this;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @CanIgnoreReturnValue
        public Builder<K, V> putAll(RangeMap<K, ? extends V> rangeMap) {
            for (Map.Entry<Range<K>, ? extends V> entry : rangeMap.asMapOfRanges().entrySet()) {
                put(entry.getKey(), entry.getValue());
            }
            return this;
        }

        public ImmutableRangeMap<K, V> build() {
            Collections.sort(this.a, Range.c().a());
            ImmutableList.Builder builder = new ImmutableList.Builder(this.a.size());
            ImmutableList.Builder builder2 = new ImmutableList.Builder(this.a.size());
            for (int i = 0; i < this.a.size(); i++) {
                Range<K> key = this.a.get(i).getKey();
                if (i > 0) {
                    Range<K> key2 = this.a.get(i - 1).getKey();
                    if (key.isConnected(key2) && !key.intersection(key2).isEmpty()) {
                        throw new IllegalArgumentException("Overlapping ranges: range " + key2 + " overlaps with entry " + key);
                    }
                }
                builder.add((ImmutableList.Builder) key);
                builder2.add((ImmutableList.Builder) this.a.get(i).getValue());
            }
            return new ImmutableRangeMap<>(builder.build(), builder2.build());
        }
    }

    ImmutableRangeMap(ImmutableList<Range<K>> immutableList, ImmutableList<V> immutableList2) {
        this.b = immutableList;
        this.c = immutableList2;
    }

    @Override // com.google.common.collect.RangeMap
    @NullableDecl
    public V get(K k) {
        int a2 = ch.a(this.b, Range.a(), ab.b(k), ch.b.ANY_PRESENT, ch.a.NEXT_LOWER);
        if (a2 != -1 && this.b.get(a2).contains(k)) {
            return this.c.get(a2);
        }
        return null;
    }

    @Override // com.google.common.collect.RangeMap
    @NullableDecl
    public Map.Entry<Range<K>, V> getEntry(K k) {
        int a2 = ch.a(this.b, Range.a(), ab.b(k), ch.b.ANY_PRESENT, ch.a.NEXT_LOWER);
        if (a2 == -1) {
            return null;
        }
        Range<K> range = this.b.get(a2);
        if (range.contains(k)) {
            return Maps.immutableEntry(range, this.c.get(a2));
        }
        return null;
    }

    @Override // com.google.common.collect.RangeMap
    public Range<K> span() {
        if (!this.b.isEmpty()) {
            ImmutableList<Range<K>> immutableList = this.b;
            return Range.a((ab) this.b.get(0).lowerBound, (ab) immutableList.get(immutableList.size() - 1).upperBound);
        }
        throw new NoSuchElementException();
    }

    @Override // com.google.common.collect.RangeMap
    @Deprecated
    public void put(Range<K> range, V v) {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.common.collect.RangeMap
    @Deprecated
    public void putCoalescing(Range<K> range, V v) {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.common.collect.RangeMap
    @Deprecated
    public void putAll(RangeMap<K, V> rangeMap) {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.common.collect.RangeMap
    @Deprecated
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.common.collect.RangeMap
    @Deprecated
    public void remove(Range<K> range) {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.common.collect.RangeMap
    public ImmutableMap<Range<K>, V> asMapOfRanges() {
        if (this.b.isEmpty()) {
            return ImmutableMap.of();
        }
        return new ImmutableSortedMap(new by(this.b, Range.c()), this.c);
    }

    @Override // com.google.common.collect.RangeMap
    public ImmutableMap<Range<K>, V> asDescendingMapOfRanges() {
        if (this.b.isEmpty()) {
            return ImmutableMap.of();
        }
        return new ImmutableSortedMap(new by(this.b.reverse(), Range.c().reverse()), this.c.reverse());
    }

    @Override // com.google.common.collect.RangeMap
    public ImmutableRangeMap<K, V> subRangeMap(final Range<K> range) {
        if (((Range) Preconditions.checkNotNull(range)).isEmpty()) {
            return of();
        }
        if (this.b.isEmpty() || range.encloses(span())) {
            return this;
        }
        final int a2 = ch.a(this.b, Range.b(), range.lowerBound, ch.b.FIRST_AFTER, ch.a.NEXT_HIGHER);
        int a3 = ch.a(this.b, Range.a(), range.upperBound, ch.b.ANY_PRESENT, ch.a.NEXT_HIGHER);
        if (a2 >= a3) {
            return of();
        }
        final int i = a3 - a2;
        return (ImmutableRangeMap<K, V>) new ImmutableRangeMap<K, V>(new ImmutableList<Range<K>>() { // from class: com.google.common.collect.ImmutableRangeMap.1
            /* JADX INFO: Access modifiers changed from: package-private */
            @Override // com.google.common.collect.ImmutableCollection
            public boolean a() {
                return true;
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
            public int size() {
                return i;
            }

            /* renamed from: a */
            public Range<K> get(int i2) {
                Preconditions.checkElementIndex(i2, i);
                return (i2 == 0 || i2 == i + (-1)) ? ((Range) ImmutableRangeMap.this.b.get(i2 + a2)).intersection(range) : (Range) ImmutableRangeMap.this.b.get(i2 + a2);
            }
        }, this.c.subList(a2, a3)) { // from class: com.google.common.collect.ImmutableRangeMap.2
            @Override // com.google.common.collect.ImmutableRangeMap, com.google.common.collect.RangeMap
            public /* bridge */ /* synthetic */ Map asDescendingMapOfRanges() {
                return ImmutableRangeMap.super.asDescendingMapOfRanges();
            }

            @Override // com.google.common.collect.ImmutableRangeMap, com.google.common.collect.RangeMap
            public /* bridge */ /* synthetic */ Map asMapOfRanges() {
                return ImmutableRangeMap.super.asMapOfRanges();
            }

            @Override // com.google.common.collect.ImmutableRangeMap, com.google.common.collect.RangeMap
            public ImmutableRangeMap<K, V> subRangeMap(Range<K> range2) {
                if (range.isConnected(range2)) {
                    return this.subRangeMap((Range) range2.intersection(range));
                }
                return ImmutableRangeMap.of();
            }
        };
    }

    @Override // com.google.common.collect.RangeMap
    public int hashCode() {
        return asMapOfRanges().hashCode();
    }

    @Override // com.google.common.collect.RangeMap
    public boolean equals(@NullableDecl Object obj) {
        if (obj instanceof RangeMap) {
            return asMapOfRanges().equals(((RangeMap) obj).asMapOfRanges());
        }
        return false;
    }

    @Override // com.google.common.collect.RangeMap
    public String toString() {
        return asMapOfRanges().toString();
    }

    /* loaded from: classes2.dex */
    private static class a<K extends Comparable<?>, V> implements Serializable {
        private static final long serialVersionUID = 0;
        private final ImmutableMap<Range<K>, V> mapOfRanges;

        a(ImmutableMap<Range<K>, V> immutableMap) {
            this.mapOfRanges = immutableMap;
        }

        Object readResolve() {
            if (this.mapOfRanges.isEmpty()) {
                return ImmutableRangeMap.of();
            }
            return a();
        }

        Object a() {
            Builder builder = new Builder();
            UnmodifiableIterator<Map.Entry<Range<K>, V>> it = this.mapOfRanges.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<Range<K>, V> next = it.next();
                builder.put(next.getKey(), next.getValue());
            }
            return builder.build();
        }
    }

    Object writeReplace() {
        return new a(asMapOfRanges());
    }
}
