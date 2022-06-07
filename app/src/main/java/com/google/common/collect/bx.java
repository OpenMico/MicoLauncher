package com.google.common.collect;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.collect.Multiset;
import com.google.common.primitives.Ints;
import java.util.Comparator;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: RegularImmutableSortedMultiset.java */
@GwtIncompatible
/* loaded from: classes2.dex */
public final class bx<E> extends ImmutableSortedMultiset<E> {
    @VisibleForTesting
    final transient by<E> c;
    private final transient long[] e;
    private final transient int f;
    private final transient int g;
    private static final long[] d = {0};
    static final ImmutableSortedMultiset<Comparable> b = new bx(Ordering.natural());

    /* JADX INFO: Access modifiers changed from: package-private */
    public bx(Comparator<? super E> comparator) {
        this.c = ImmutableSortedSet.a((Comparator) comparator);
        this.e = d;
        this.f = 0;
        this.g = 0;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public bx(by<E> byVar, long[] jArr, int i, int i2) {
        this.c = byVar;
        this.e = jArr;
        this.f = i;
        this.g = i2;
    }

    private int b(int i) {
        long[] jArr = this.e;
        int i2 = this.f;
        return (int) (jArr[(i2 + i) + 1] - jArr[i2 + i]);
    }

    @Override // com.google.common.collect.ImmutableMultiset
    Multiset.Entry<E> a(int i) {
        return Multisets.immutableEntry(this.c.asList().get(i), b(i));
    }

    @Override // com.google.common.collect.SortedMultiset
    public Multiset.Entry<E> firstEntry() {
        if (isEmpty()) {
            return null;
        }
        return a(0);
    }

    @Override // com.google.common.collect.SortedMultiset
    public Multiset.Entry<E> lastEntry() {
        if (isEmpty()) {
            return null;
        }
        return a(this.g - 1);
    }

    @Override // com.google.common.collect.Multiset
    public int count(@NullableDecl Object obj) {
        int a = this.c.a(obj);
        if (a >= 0) {
            return b(a);
        }
        return 0;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, com.google.common.collect.Multiset
    public int size() {
        long[] jArr = this.e;
        int i = this.f;
        return Ints.saturatedCast(jArr[this.g + i] - jArr[i]);
    }

    @Override // com.google.common.collect.ImmutableSortedMultiset, com.google.common.collect.ImmutableMultiset, com.google.common.collect.Multiset
    public ImmutableSortedSet<E> elementSet() {
        return this.c;
    }

    @Override // com.google.common.collect.ImmutableSortedMultiset, com.google.common.collect.SortedMultiset
    public ImmutableSortedMultiset<E> headMultiset(E e, BoundType boundType) {
        return a(0, this.c.c(e, Preconditions.checkNotNull(boundType) == BoundType.CLOSED));
    }

    @Override // com.google.common.collect.ImmutableSortedMultiset, com.google.common.collect.SortedMultiset
    public ImmutableSortedMultiset<E> tailMultiset(E e, BoundType boundType) {
        return a(this.c.d(e, Preconditions.checkNotNull(boundType) == BoundType.CLOSED), this.g);
    }

    ImmutableSortedMultiset<E> a(int i, int i2) {
        Preconditions.checkPositionIndexes(i, i2, this.g);
        if (i == i2) {
            return a(comparator());
        }
        return (i == 0 && i2 == this.g) ? this : new bx(this.c.b(i, i2), this.e, this.f + i, i2 - i);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.common.collect.ImmutableCollection
    public boolean a() {
        return this.f > 0 || this.g < this.e.length - 1;
    }
}
