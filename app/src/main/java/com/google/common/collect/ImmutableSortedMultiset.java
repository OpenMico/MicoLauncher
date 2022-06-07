package com.google.common.collect;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMultiset;
import com.google.common.collect.Multiset;
import com.google.common.math.IntMath;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.concurrent.LazyInit;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

@GwtIncompatible
/* loaded from: classes2.dex */
public abstract class ImmutableSortedMultiset<E> extends be<E> implements SortedMultiset<E> {
    @LazyInit
    transient ImmutableSortedMultiset<E> a;

    @Override // com.google.common.collect.ImmutableMultiset, com.google.common.collect.Multiset
    public abstract ImmutableSortedSet<E> elementSet();

    @Override // com.google.common.collect.SortedMultiset
    public abstract ImmutableSortedMultiset<E> headMultiset(E e, BoundType boundType);

    @Override // com.google.common.collect.SortedMultiset
    public abstract ImmutableSortedMultiset<E> tailMultiset(E e, BoundType boundType);

    public static <E> ImmutableSortedMultiset<E> of() {
        return (ImmutableSortedMultiset<E>) bx.b;
    }

    public static ImmutableSortedMultiset of(Comparable comparable) {
        return new bx((by) ImmutableSortedSet.of(comparable), new long[]{0, 1}, 0, 1);
    }

    public static ImmutableSortedMultiset of(Comparable comparable, Comparable comparable2) {
        return copyOf(Ordering.natural(), Arrays.asList(comparable, comparable2));
    }

    public static ImmutableSortedMultiset of(Comparable comparable, Comparable comparable2, Comparable comparable3) {
        return copyOf(Ordering.natural(), Arrays.asList(comparable, comparable2, comparable3));
    }

    public static ImmutableSortedMultiset of(Comparable comparable, Comparable comparable2, Comparable comparable3, Comparable comparable4) {
        return copyOf(Ordering.natural(), Arrays.asList(comparable, comparable2, comparable3, comparable4));
    }

    public static ImmutableSortedMultiset of(Comparable comparable, Comparable comparable2, Comparable comparable3, Comparable comparable4, Comparable comparable5) {
        return copyOf(Ordering.natural(), Arrays.asList(comparable, comparable2, comparable3, comparable4, comparable5));
    }

    public static ImmutableSortedMultiset of(Comparable comparable, Comparable comparable2, Comparable comparable3, Comparable comparable4, Comparable comparable5, Comparable comparable6, Comparable... comparableArr) {
        ArrayList newArrayListWithCapacity = Lists.newArrayListWithCapacity(comparableArr.length + 6);
        Collections.addAll(newArrayListWithCapacity, comparable, comparable2, comparable3, comparable4, comparable5, comparable6);
        Collections.addAll(newArrayListWithCapacity, comparableArr);
        return copyOf(Ordering.natural(), newArrayListWithCapacity);
    }

    public static ImmutableSortedMultiset copyOf(Comparable[] comparableArr) {
        return copyOf(Ordering.natural(), Arrays.asList(comparableArr));
    }

    public static <E> ImmutableSortedMultiset<E> copyOf(Iterable<? extends E> iterable) {
        return copyOf(Ordering.natural(), iterable);
    }

    public static <E> ImmutableSortedMultiset<E> copyOf(Iterator<? extends E> it) {
        return copyOf(Ordering.natural(), it);
    }

    public static <E> ImmutableSortedMultiset<E> copyOf(Comparator<? super E> comparator, Iterator<? extends E> it) {
        Preconditions.checkNotNull(comparator);
        return new Builder(comparator).addAll((Iterator) it).build();
    }

    public static <E> ImmutableSortedMultiset<E> copyOf(Comparator<? super E> comparator, Iterable<? extends E> iterable) {
        if (iterable instanceof ImmutableSortedMultiset) {
            ImmutableSortedMultiset<E> immutableSortedMultiset = (ImmutableSortedMultiset) iterable;
            if (comparator.equals(immutableSortedMultiset.comparator())) {
                return immutableSortedMultiset.a() ? a(comparator, immutableSortedMultiset.entrySet().asList()) : immutableSortedMultiset;
            }
        }
        return new Builder(comparator).addAll((Iterable) iterable).build();
    }

    public static <E> ImmutableSortedMultiset<E> copyOfSorted(SortedMultiset<E> sortedMultiset) {
        return a(sortedMultiset.comparator(), Lists.newArrayList(sortedMultiset.entrySet()));
    }

    private static <E> ImmutableSortedMultiset<E> a(Comparator<? super E> comparator, Collection<Multiset.Entry<E>> collection) {
        if (collection.isEmpty()) {
            return a(comparator);
        }
        ImmutableList.Builder builder = new ImmutableList.Builder(collection.size());
        long[] jArr = new long[collection.size() + 1];
        int i = 0;
        for (Multiset.Entry<E> entry : collection) {
            builder.add((ImmutableList.Builder) entry.getElement());
            int i2 = i + 1;
            jArr[i2] = jArr[i] + entry.getCount();
            i = i2;
        }
        return new bx(new by(builder.build(), comparator), jArr, 0, collection.size());
    }

    static <E> ImmutableSortedMultiset<E> a(Comparator<? super E> comparator) {
        if (Ordering.natural().equals(comparator)) {
            return (ImmutableSortedMultiset<E>) bx.b;
        }
        return new bx(comparator);
    }

    @Override // com.google.common.collect.SortedMultiset, com.google.common.collect.cf
    public final Comparator<? super E> comparator() {
        return elementSet().comparator();
    }

    @Override // com.google.common.collect.SortedMultiset
    public ImmutableSortedMultiset<E> descendingMultiset() {
        ImmutableSortedMultiset<E> immutableSortedMultiset = this.a;
        if (immutableSortedMultiset != null) {
            return immutableSortedMultiset;
        }
        ImmutableSortedMultiset<E> a2 = isEmpty() ? a(Ordering.from(comparator()).reverse()) : new ad<>(this);
        this.a = a2;
        return a2;
    }

    @Override // com.google.common.collect.SortedMultiset
    @CanIgnoreReturnValue
    @Deprecated
    public final Multiset.Entry<E> pollFirstEntry() {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.common.collect.SortedMultiset
    @CanIgnoreReturnValue
    @Deprecated
    public final Multiset.Entry<E> pollLastEntry() {
        throw new UnsupportedOperationException();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.collect.SortedMultiset
    public ImmutableSortedMultiset<E> subMultiset(E e, BoundType boundType, E e2, BoundType boundType2) {
        Preconditions.checkArgument(comparator().compare(e, e2) <= 0, "Expected lowerBound <= upperBound but %s > %s", e, e2);
        return tailMultiset((ImmutableSortedMultiset<E>) e, boundType).headMultiset((ImmutableSortedMultiset<E>) e2, boundType2);
    }

    public static <E> Builder<E> orderedBy(Comparator<E> comparator) {
        return new Builder<>(comparator);
    }

    public static <E extends Comparable<?>> Builder<E> reverseOrder() {
        return new Builder<>(Ordering.natural().reverse());
    }

    public static <E extends Comparable<?>> Builder<E> naturalOrder() {
        return new Builder<>(Ordering.natural());
    }

    /* loaded from: classes2.dex */
    public static class Builder<E> extends ImmutableMultiset.Builder<E> {
        private final Comparator<? super E> e;
        private int g;
        private boolean h;
        @VisibleForTesting
        E[] d = (E[]) new Object[4];
        private int[] f = new int[4];

        public Builder(Comparator<? super E> comparator) {
            super(true);
            this.e = (Comparator) Preconditions.checkNotNull(comparator);
        }

        private void a() {
            int i = this.g;
            E[] eArr = this.d;
            if (i == eArr.length) {
                a(true);
            } else if (this.h) {
                this.d = (E[]) Arrays.copyOf(eArr, eArr.length);
            }
            this.h = false;
        }

        private void a(boolean z) {
            int i = this.g;
            if (i != 0) {
                E[] eArr = (E[]) Arrays.copyOf(this.d, i);
                Arrays.sort(eArr, this.e);
                int i2 = 1;
                for (int i3 = 1; i3 < eArr.length; i3++) {
                    if (this.e.compare((Object) eArr[i2 - 1], (Object) eArr[i3]) < 0) {
                        eArr[i2] = eArr[i3];
                        i2++;
                    }
                }
                Arrays.fill(eArr, i2, this.g, (Object) null);
                if (z) {
                    int i4 = i2 * 4;
                    int i5 = this.g;
                    if (i4 > i5 * 3) {
                        eArr = (E[]) Arrays.copyOf(eArr, IntMath.saturatedAdd(i5, (i5 / 2) + 1));
                    }
                }
                int[] iArr = new int[eArr.length];
                for (int i6 = 0; i6 < this.g; i6++) {
                    int binarySearch = Arrays.binarySearch(eArr, 0, i2, this.d[i6], this.e);
                    int[] iArr2 = this.f;
                    if (iArr2[i6] >= 0) {
                        iArr[binarySearch] = iArr[binarySearch] + iArr2[i6];
                    } else {
                        iArr[binarySearch] = ~iArr2[i6];
                    }
                }
                this.d = eArr;
                this.f = iArr;
                this.g = i2;
            }
        }

        @Override // com.google.common.collect.ImmutableMultiset.Builder, com.google.common.collect.ImmutableCollection.Builder
        @CanIgnoreReturnValue
        public Builder<E> add(E e) {
            return addCopies((Builder<E>) e, 1);
        }

        @Override // com.google.common.collect.ImmutableMultiset.Builder, com.google.common.collect.ImmutableCollection.Builder
        @CanIgnoreReturnValue
        public Builder<E> add(E... eArr) {
            for (E e : eArr) {
                add((Builder<E>) e);
            }
            return this;
        }

        @Override // com.google.common.collect.ImmutableMultiset.Builder
        @CanIgnoreReturnValue
        public Builder<E> addCopies(E e, int i) {
            Preconditions.checkNotNull(e);
            t.a(i, "occurrences");
            if (i == 0) {
                return this;
            }
            a();
            E[] eArr = this.d;
            int i2 = this.g;
            eArr[i2] = e;
            this.f[i2] = i;
            this.g = i2 + 1;
            return this;
        }

        @Override // com.google.common.collect.ImmutableMultiset.Builder
        @CanIgnoreReturnValue
        public Builder<E> setCount(E e, int i) {
            Preconditions.checkNotNull(e);
            t.a(i, "count");
            a();
            E[] eArr = this.d;
            int i2 = this.g;
            eArr[i2] = e;
            this.f[i2] = ~i;
            this.g = i2 + 1;
            return this;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.common.collect.ImmutableMultiset.Builder, com.google.common.collect.ImmutableCollection.Builder
        @CanIgnoreReturnValue
        public Builder<E> addAll(Iterable<? extends E> iterable) {
            if (iterable instanceof Multiset) {
                for (Multiset.Entry<E> entry : ((Multiset) iterable).entrySet()) {
                    addCopies((Builder<E>) entry.getElement(), entry.getCount());
                }
            } else {
                Iterator<? extends E> it = iterable.iterator();
                while (it.hasNext()) {
                    add((Builder<E>) it.next());
                }
            }
            return this;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.common.collect.ImmutableMultiset.Builder, com.google.common.collect.ImmutableCollection.Builder
        @CanIgnoreReturnValue
        public Builder<E> addAll(Iterator<? extends E> it) {
            while (it.hasNext()) {
                add((Builder<E>) it.next());
            }
            return this;
        }

        private void b() {
            a(false);
            int i = 0;
            int i2 = 0;
            while (true) {
                int i3 = this.g;
                if (i < i3) {
                    int[] iArr = this.f;
                    if (iArr[i] > 0) {
                        E[] eArr = this.d;
                        eArr[i2] = eArr[i];
                        iArr[i2] = iArr[i];
                        i2++;
                    }
                    i++;
                } else {
                    Arrays.fill(this.d, i2, i3, (Object) null);
                    Arrays.fill(this.f, i2, this.g, 0);
                    this.g = i2;
                    return;
                }
            }
        }

        @Override // com.google.common.collect.ImmutableMultiset.Builder, com.google.common.collect.ImmutableCollection.Builder
        public ImmutableSortedMultiset<E> build() {
            b();
            int i = this.g;
            if (i == 0) {
                return ImmutableSortedMultiset.a(this.e);
            }
            by byVar = (by) ImmutableSortedSet.a(this.e, i, this.d);
            long[] jArr = new long[this.g + 1];
            int i2 = 0;
            while (true) {
                int i3 = this.g;
                if (i2 < i3) {
                    int i4 = i2 + 1;
                    jArr[i4] = jArr[i2] + this.f[i2];
                    i2 = i4;
                } else {
                    this.h = true;
                    return new bx(byVar, jArr, 0, i3);
                }
            }
        }
    }

    /* loaded from: classes2.dex */
    private static final class a<E> implements Serializable {
        final Comparator<? super E> comparator;
        final int[] counts;
        final E[] elements;

        a(SortedMultiset<E> sortedMultiset) {
            this.comparator = sortedMultiset.comparator();
            int size = sortedMultiset.entrySet().size();
            this.elements = (E[]) new Object[size];
            this.counts = new int[size];
            int i = 0;
            for (Multiset.Entry<E> entry : sortedMultiset.entrySet()) {
                this.elements[i] = entry.getElement();
                this.counts[i] = entry.getCount();
                i++;
            }
        }

        Object readResolve() {
            int length = this.elements.length;
            Builder builder = new Builder(this.comparator);
            for (int i = 0; i < length; i++) {
                builder.addCopies((Builder) this.elements[i], this.counts[i]);
            }
            return builder.build();
        }
    }

    @Override // com.google.common.collect.ImmutableMultiset, com.google.common.collect.ImmutableCollection
    Object writeReplace() {
        return new a(this);
    }
}
