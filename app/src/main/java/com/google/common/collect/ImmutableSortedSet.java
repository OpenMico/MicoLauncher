package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.concurrent.LazyInit;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.SortedSet;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible(emulated = true, serializable = true)
/* loaded from: classes2.dex */
public abstract class ImmutableSortedSet<E> extends bf<E> implements cf<E>, NavigableSet<E> {
    final transient Comparator<? super E> a;
    @GwtIncompatible
    @LazyInit
    transient ImmutableSortedSet<E> b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract int a(@NullableDecl Object obj);

    abstract ImmutableSortedSet<E> a(E e, boolean z);

    abstract ImmutableSortedSet<E> a(E e, boolean z, E e2, boolean z2);

    @GwtIncompatible
    abstract ImmutableSortedSet<E> b();

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract ImmutableSortedSet<E> b(E e, boolean z);

    @GwtIncompatible
    public abstract UnmodifiableIterator<E> descendingIterator();

    @Override // com.google.common.collect.ImmutableSet, com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
    public abstract UnmodifiableIterator<E> iterator();

    public static <E> by<E> a(Comparator<? super E> comparator) {
        if (Ordering.natural().equals(comparator)) {
            return (by<E>) by.c;
        }
        return new by<>(ImmutableList.of(), comparator);
    }

    public static <E> ImmutableSortedSet<E> of() {
        return by.c;
    }

    public static ImmutableSortedSet of(Comparable comparable) {
        return new by(ImmutableList.of(comparable), Ordering.natural());
    }

    public static ImmutableSortedSet of(Comparable comparable, Comparable comparable2) {
        return a(Ordering.natural(), 2, comparable, comparable2);
    }

    public static ImmutableSortedSet of(Comparable comparable, Comparable comparable2, Comparable comparable3) {
        return a(Ordering.natural(), 3, comparable, comparable2, comparable3);
    }

    public static ImmutableSortedSet of(Comparable comparable, Comparable comparable2, Comparable comparable3, Comparable comparable4) {
        return a(Ordering.natural(), 4, comparable, comparable2, comparable3, comparable4);
    }

    public static ImmutableSortedSet of(Comparable comparable, Comparable comparable2, Comparable comparable3, Comparable comparable4, Comparable comparable5) {
        return a(Ordering.natural(), 5, comparable, comparable2, comparable3, comparable4, comparable5);
    }

    public static ImmutableSortedSet of(Comparable comparable, Comparable comparable2, Comparable comparable3, Comparable comparable4, Comparable comparable5, Comparable comparable6, Comparable... comparableArr) {
        Comparable[] comparableArr2 = new Comparable[comparableArr.length + 6];
        comparableArr2[0] = comparable;
        comparableArr2[1] = comparable2;
        comparableArr2[2] = comparable3;
        comparableArr2[3] = comparable4;
        comparableArr2[4] = comparable5;
        comparableArr2[5] = comparable6;
        System.arraycopy(comparableArr, 0, comparableArr2, 6, comparableArr.length);
        return a(Ordering.natural(), comparableArr2.length, comparableArr2);
    }

    public static ImmutableSortedSet copyOf(Comparable[] comparableArr) {
        return a(Ordering.natural(), comparableArr.length, (Object[]) comparableArr.clone());
    }

    public static <E> ImmutableSortedSet<E> copyOf(Iterable<? extends E> iterable) {
        return copyOf(Ordering.natural(), iterable);
    }

    public static <E> ImmutableSortedSet<E> copyOf(Collection<? extends E> collection) {
        return copyOf((Comparator) Ordering.natural(), (Collection) collection);
    }

    public static <E> ImmutableSortedSet<E> copyOf(Iterator<? extends E> it) {
        return copyOf(Ordering.natural(), it);
    }

    public static <E> ImmutableSortedSet<E> copyOf(Comparator<? super E> comparator, Iterator<? extends E> it) {
        return new Builder(comparator).addAll((Iterator) it).build();
    }

    public static <E> ImmutableSortedSet<E> copyOf(Comparator<? super E> comparator, Iterable<? extends E> iterable) {
        Preconditions.checkNotNull(comparator);
        if (cg.a(comparator, iterable) && (iterable instanceof ImmutableSortedSet)) {
            ImmutableSortedSet<E> immutableSortedSet = (ImmutableSortedSet) iterable;
            if (!immutableSortedSet.a()) {
                return immutableSortedSet;
            }
        }
        Object[] a2 = Iterables.a(iterable);
        return a(comparator, a2.length, a2);
    }

    public static <E> ImmutableSortedSet<E> copyOf(Comparator<? super E> comparator, Collection<? extends E> collection) {
        return copyOf((Comparator) comparator, (Iterable) collection);
    }

    public static <E> ImmutableSortedSet<E> copyOfSorted(SortedSet<E> sortedSet) {
        Comparator a2 = cg.a(sortedSet);
        ImmutableList copyOf = ImmutableList.copyOf((Collection) sortedSet);
        if (copyOf.isEmpty()) {
            return a(a2);
        }
        return new by(copyOf, a2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static <E> ImmutableSortedSet<E> a(Comparator<? super E> comparator, int i, E... eArr) {
        if (i == 0) {
            return a((Comparator) comparator);
        }
        ObjectArrays.a((Object[]) eArr, i);
        Arrays.sort(eArr, 0, i, comparator);
        int i2 = 1;
        for (int i3 = 1; i3 < i; i3++) {
            Object obj = (Object) eArr[i3];
            if (comparator.compare(obj, (Object) eArr[i2 - 1]) != 0) {
                i2++;
                eArr[i2] = obj;
            }
        }
        Arrays.fill(eArr, i2, i, (Object) null);
        int length = eArr.length / 2;
        E[] eArr2 = eArr;
        if (i2 < length) {
            eArr2 = (E[]) Arrays.copyOf(eArr, i2);
        }
        return new by(ImmutableList.b(eArr2, i2), comparator);
    }

    public static <E> Builder<E> orderedBy(Comparator<E> comparator) {
        return new Builder<>(comparator);
    }

    public static <E extends Comparable<?>> Builder<E> reverseOrder() {
        return new Builder<>(Collections.reverseOrder());
    }

    public static <E extends Comparable<?>> Builder<E> naturalOrder() {
        return new Builder<>(Ordering.natural());
    }

    /* loaded from: classes2.dex */
    public static final class Builder<E> extends ImmutableSet.Builder<E> {
        private final Comparator<? super E> e;

        public Builder(Comparator<? super E> comparator) {
            this.e = (Comparator) Preconditions.checkNotNull(comparator);
        }

        @Override // com.google.common.collect.ImmutableSet.Builder, com.google.common.collect.ImmutableCollection.a, com.google.common.collect.ImmutableCollection.Builder
        @CanIgnoreReturnValue
        public Builder<E> add(E e) {
            super.add((Builder<E>) e);
            return this;
        }

        @Override // com.google.common.collect.ImmutableSet.Builder, com.google.common.collect.ImmutableCollection.a, com.google.common.collect.ImmutableCollection.Builder
        @CanIgnoreReturnValue
        public Builder<E> add(E... eArr) {
            super.add((Object[]) eArr);
            return this;
        }

        @Override // com.google.common.collect.ImmutableSet.Builder, com.google.common.collect.ImmutableCollection.a, com.google.common.collect.ImmutableCollection.Builder
        @CanIgnoreReturnValue
        public Builder<E> addAll(Iterable<? extends E> iterable) {
            super.addAll((Iterable) iterable);
            return this;
        }

        @Override // com.google.common.collect.ImmutableSet.Builder, com.google.common.collect.ImmutableCollection.Builder
        @CanIgnoreReturnValue
        public Builder<E> addAll(Iterator<? extends E> it) {
            super.addAll((Iterator) it);
            return this;
        }

        @Override // com.google.common.collect.ImmutableSet.Builder, com.google.common.collect.ImmutableCollection.Builder
        public ImmutableSortedSet<E> build() {
            ImmutableSortedSet<E> a = ImmutableSortedSet.a(this.e, this.b, this.a);
            this.b = a.size();
            this.c = true;
            return a;
        }
    }

    int a(Object obj, Object obj2) {
        return a(this.a, obj, obj2);
    }

    static int a(Comparator<?> comparator, Object obj, Object obj2) {
        return comparator.compare(obj, obj2);
    }

    public ImmutableSortedSet(Comparator<? super E> comparator) {
        this.a = comparator;
    }

    @Override // com.google.common.collect.cf
    public Comparator<? super E> comparator() {
        return this.a;
    }

    public ImmutableSortedSet<E> headSet(E e) {
        return headSet((ImmutableSortedSet<E>) e, false);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @GwtIncompatible
    public ImmutableSortedSet<E> headSet(E e, boolean z) {
        return b((ImmutableSortedSet<E>) Preconditions.checkNotNull(e), z);
    }

    public ImmutableSortedSet<E> subSet(E e, E e2) {
        return subSet((boolean) e, true, (boolean) e2, false);
    }

    @GwtIncompatible
    public ImmutableSortedSet<E> subSet(E e, boolean z, E e2, boolean z2) {
        Preconditions.checkNotNull(e);
        Preconditions.checkNotNull(e2);
        Preconditions.checkArgument(this.a.compare(e, e2) <= 0);
        return a(e, z, e2, z2);
    }

    public ImmutableSortedSet<E> tailSet(E e) {
        return tailSet((ImmutableSortedSet<E>) e, true);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @GwtIncompatible
    public ImmutableSortedSet<E> tailSet(E e, boolean z) {
        return a((ImmutableSortedSet<E>) Preconditions.checkNotNull(e), z);
    }

    @Override // java.util.NavigableSet
    @GwtIncompatible
    public E lower(E e) {
        return (E) Iterators.getNext(headSet((ImmutableSortedSet<E>) e, false).descendingIterator(), null);
    }

    @Override // java.util.NavigableSet
    @GwtIncompatible
    public E floor(E e) {
        return (E) Iterators.getNext(headSet((ImmutableSortedSet<E>) e, true).descendingIterator(), null);
    }

    @Override // java.util.NavigableSet
    @GwtIncompatible
    public E ceiling(E e) {
        return (E) Iterables.getFirst(tailSet((ImmutableSortedSet<E>) e, true), null);
    }

    @Override // java.util.NavigableSet
    @GwtIncompatible
    public E higher(E e) {
        return (E) Iterables.getFirst(tailSet((ImmutableSortedSet<E>) e, false), null);
    }

    @Override // java.util.SortedSet
    public E first() {
        return iterator().next();
    }

    @Override // java.util.SortedSet
    public E last() {
        return descendingIterator().next();
    }

    @Override // java.util.NavigableSet
    @CanIgnoreReturnValue
    @GwtIncompatible
    @Deprecated
    public final E pollFirst() {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.NavigableSet
    @CanIgnoreReturnValue
    @GwtIncompatible
    @Deprecated
    public final E pollLast() {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.NavigableSet
    @GwtIncompatible
    public ImmutableSortedSet<E> descendingSet() {
        ImmutableSortedSet<E> immutableSortedSet = this.b;
        if (immutableSortedSet != null) {
            return immutableSortedSet;
        }
        ImmutableSortedSet<E> b = b();
        this.b = b;
        b.b = this;
        return b;
    }

    /* loaded from: classes2.dex */
    private static class a<E> implements Serializable {
        private static final long serialVersionUID = 0;
        final Comparator<? super E> comparator;
        final Object[] elements;

        public a(Comparator<? super E> comparator, Object[] objArr) {
            this.comparator = comparator;
            this.elements = objArr;
        }

        /* JADX WARN: Multi-variable type inference failed */
        Object readResolve() {
            return new Builder(this.comparator).add(this.elements).build();
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Use SerializedForm");
    }

    @Override // com.google.common.collect.ImmutableSet, com.google.common.collect.ImmutableCollection
    Object writeReplace() {
        return new a(this.a, toArray());
    }
}
