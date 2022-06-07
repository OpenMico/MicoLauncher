package com.google.common.collect;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.collect.Multiset;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: DescendingImmutableSortedMultiset.java */
@GwtIncompatible
/* loaded from: classes2.dex */
public final class ad<E> extends ImmutableSortedMultiset<E> {
    private final transient ImmutableSortedMultiset<E> b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public ad(ImmutableSortedMultiset<E> immutableSortedMultiset) {
        this.b = immutableSortedMultiset;
    }

    @Override // com.google.common.collect.Multiset
    public int count(@NullableDecl Object obj) {
        return this.b.count(obj);
    }

    @Override // com.google.common.collect.SortedMultiset
    public Multiset.Entry<E> firstEntry() {
        return this.b.lastEntry();
    }

    @Override // com.google.common.collect.SortedMultiset
    public Multiset.Entry<E> lastEntry() {
        return this.b.firstEntry();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, com.google.common.collect.Multiset
    public int size() {
        return this.b.size();
    }

    @Override // com.google.common.collect.ImmutableSortedMultiset, com.google.common.collect.ImmutableMultiset, com.google.common.collect.Multiset
    public ImmutableSortedSet<E> elementSet() {
        return this.b.elementSet().descendingSet();
    }

    @Override // com.google.common.collect.ImmutableMultiset
    Multiset.Entry<E> a(int i) {
        return this.b.entrySet().asList().reverse().get(i);
    }

    @Override // com.google.common.collect.ImmutableSortedMultiset, com.google.common.collect.SortedMultiset
    public ImmutableSortedMultiset<E> descendingMultiset() {
        return this.b;
    }

    @Override // com.google.common.collect.ImmutableSortedMultiset, com.google.common.collect.SortedMultiset
    public ImmutableSortedMultiset<E> headMultiset(E e, BoundType boundType) {
        return this.b.tailMultiset((ImmutableSortedMultiset<E>) e, boundType).descendingMultiset();
    }

    @Override // com.google.common.collect.ImmutableSortedMultiset, com.google.common.collect.SortedMultiset
    public ImmutableSortedMultiset<E> tailMultiset(E e, BoundType boundType) {
        return this.b.headMultiset((ImmutableSortedMultiset<E>) e, boundType).descendingMultiset();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.common.collect.ImmutableCollection
    public boolean a() {
        return this.b.a();
    }
}
