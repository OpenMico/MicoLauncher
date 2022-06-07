package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import com.google.common.collect.cj;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;

/* compiled from: DescendingMultiset.java */
@GwtCompatible(emulated = true)
/* loaded from: classes2.dex */
abstract class af<E> extends ForwardingMultiset<E> implements SortedMultiset<E> {
    @MonotonicNonNullDecl
    private transient Comparator<? super E> a;
    @MonotonicNonNullDecl
    private transient NavigableSet<E> b;
    @MonotonicNonNullDecl
    private transient Set<Multiset.Entry<E>> c;

    abstract SortedMultiset<E> a();

    abstract Iterator<Multiset.Entry<E>> b();

    @Override // com.google.common.collect.SortedMultiset, com.google.common.collect.cf
    public Comparator<? super E> comparator() {
        Comparator<? super E> comparator = this.a;
        if (comparator != null) {
            return comparator;
        }
        Ordering reverse = Ordering.from(a().comparator()).reverse();
        this.a = reverse;
        return reverse;
    }

    @Override // com.google.common.collect.ForwardingMultiset, com.google.common.collect.Multiset
    public NavigableSet<E> elementSet() {
        NavigableSet<E> navigableSet = this.b;
        if (navigableSet != null) {
            return navigableSet;
        }
        cj.b bVar = new cj.b(this);
        this.b = bVar;
        return bVar;
    }

    @Override // com.google.common.collect.SortedMultiset
    public Multiset.Entry<E> pollFirstEntry() {
        return a().pollLastEntry();
    }

    @Override // com.google.common.collect.SortedMultiset
    public Multiset.Entry<E> pollLastEntry() {
        return a().pollFirstEntry();
    }

    @Override // com.google.common.collect.SortedMultiset
    public SortedMultiset<E> headMultiset(E e, BoundType boundType) {
        return a().tailMultiset(e, boundType).descendingMultiset();
    }

    @Override // com.google.common.collect.SortedMultiset
    public SortedMultiset<E> subMultiset(E e, BoundType boundType, E e2, BoundType boundType2) {
        return a().subMultiset(e2, boundType2, e, boundType).descendingMultiset();
    }

    @Override // com.google.common.collect.SortedMultiset
    public SortedMultiset<E> tailMultiset(E e, BoundType boundType) {
        return a().headMultiset(e, boundType).descendingMultiset();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.common.collect.ForwardingMultiset, com.google.common.collect.ForwardingCollection, com.google.common.collect.ForwardingObject
    public Multiset<E> delegate() {
        return a();
    }

    @Override // com.google.common.collect.SortedMultiset
    public SortedMultiset<E> descendingMultiset() {
        return a();
    }

    @Override // com.google.common.collect.SortedMultiset
    public Multiset.Entry<E> firstEntry() {
        return a().lastEntry();
    }

    @Override // com.google.common.collect.SortedMultiset
    public Multiset.Entry<E> lastEntry() {
        return a().firstEntry();
    }

    @Override // com.google.common.collect.ForwardingMultiset, com.google.common.collect.Multiset
    public Set<Multiset.Entry<E>> entrySet() {
        Set<Multiset.Entry<E>> set = this.c;
        if (set != null) {
            return set;
        }
        Set<Multiset.Entry<E>> c = c();
        this.c = c;
        return c;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: DescendingMultiset.java */
    /* loaded from: classes2.dex */
    public class a extends Multisets.d<E> {
        a() {
        }

        @Override // com.google.common.collect.Multisets.d
        Multiset<E> a() {
            return af.this;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<Multiset.Entry<E>> iterator() {
            return af.this.b();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return af.this.a().entrySet().size();
        }
    }

    Set<Multiset.Entry<E>> c() {
        return new a();
    }

    @Override // com.google.common.collect.ForwardingCollection, java.util.Collection, java.lang.Iterable
    public Iterator<E> iterator() {
        return Multisets.a((Multiset) this);
    }

    @Override // com.google.common.collect.ForwardingCollection, java.util.Collection
    public Object[] toArray() {
        return standardToArray();
    }

    @Override // com.google.common.collect.ForwardingCollection, java.util.Collection
    public <T> T[] toArray(T[] tArr) {
        return (T[]) standardToArray(tArr);
    }

    @Override // com.google.common.collect.ForwardingObject
    public String toString() {
        return entrySet().toString();
    }
}
