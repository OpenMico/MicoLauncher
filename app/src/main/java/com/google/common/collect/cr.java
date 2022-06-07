package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import java.util.Comparator;
import java.util.NavigableSet;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: UnmodifiableSortedMultiset.java */
@GwtCompatible(emulated = true)
/* loaded from: classes2.dex */
public final class cr<E> extends Multisets.h<E> implements SortedMultiset<E> {
    private static final long serialVersionUID = 0;
    @MonotonicNonNullDecl
    private transient cr<E> c;

    /* JADX INFO: Access modifiers changed from: package-private */
    public cr(SortedMultiset<E> sortedMultiset) {
        super(sortedMultiset);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* renamed from: b */
    public SortedMultiset<E> delegate() {
        return (SortedMultiset) super.delegate();
    }

    @Override // com.google.common.collect.SortedMultiset, com.google.common.collect.cf
    public Comparator<? super E> comparator() {
        return delegate().comparator();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: c */
    public NavigableSet<E> a() {
        return Sets.unmodifiableNavigableSet(delegate().elementSet());
    }

    @Override // com.google.common.collect.Multisets.h, com.google.common.collect.ForwardingMultiset, com.google.common.collect.Multiset
    public NavigableSet<E> elementSet() {
        return (NavigableSet) super.elementSet();
    }

    @Override // com.google.common.collect.SortedMultiset
    public SortedMultiset<E> descendingMultiset() {
        cr<E> crVar = this.c;
        if (crVar != null) {
            return crVar;
        }
        cr<E> crVar2 = new cr<>(delegate().descendingMultiset());
        crVar2.c = this;
        this.c = crVar2;
        return crVar2;
    }

    @Override // com.google.common.collect.SortedMultiset
    public Multiset.Entry<E> firstEntry() {
        return delegate().firstEntry();
    }

    @Override // com.google.common.collect.SortedMultiset
    public Multiset.Entry<E> lastEntry() {
        return delegate().lastEntry();
    }

    @Override // com.google.common.collect.SortedMultiset
    public Multiset.Entry<E> pollFirstEntry() {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.common.collect.SortedMultiset
    public Multiset.Entry<E> pollLastEntry() {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.common.collect.SortedMultiset
    public SortedMultiset<E> headMultiset(E e, BoundType boundType) {
        return Multisets.unmodifiableSortedMultiset(delegate().headMultiset(e, boundType));
    }

    @Override // com.google.common.collect.SortedMultiset
    public SortedMultiset<E> subMultiset(E e, BoundType boundType, E e2, BoundType boundType2) {
        return Multisets.unmodifiableSortedMultiset(delegate().subMultiset(e, boundType, e2, boundType2));
    }

    @Override // com.google.common.collect.SortedMultiset
    public SortedMultiset<E> tailMultiset(E e, BoundType boundType) {
        return Multisets.unmodifiableSortedMultiset(delegate().tailMultiset(e, boundType));
    }
}
