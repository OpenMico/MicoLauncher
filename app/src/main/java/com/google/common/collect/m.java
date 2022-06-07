package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.Multiset;
import com.google.common.collect.cj;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NavigableSet;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* compiled from: AbstractSortedMultiset.java */
@GwtCompatible(emulated = true)
/* loaded from: classes2.dex */
abstract class m<E> extends h<E> implements SortedMultiset<E> {
    @MonotonicNonNullDecl
    private transient SortedMultiset<E> a;
    final Comparator<? super E> comparator;

    abstract Iterator<Multiset.Entry<E>> f();

    m() {
        this(Ordering.natural());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public m(Comparator<? super E> comparator) {
        this.comparator = (Comparator) Preconditions.checkNotNull(comparator);
    }

    @Override // com.google.common.collect.h, com.google.common.collect.Multiset
    public NavigableSet<E> elementSet() {
        return (NavigableSet) super.elementSet();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: e */
    public NavigableSet<E> d() {
        return new cj.b(this);
    }

    public Comparator<? super E> comparator() {
        return this.comparator;
    }

    public Multiset.Entry<E> firstEntry() {
        Iterator<Multiset.Entry<E>> b = b();
        if (b.hasNext()) {
            return b.next();
        }
        return null;
    }

    public Multiset.Entry<E> lastEntry() {
        Iterator<Multiset.Entry<E>> f = f();
        if (f.hasNext()) {
            return f.next();
        }
        return null;
    }

    public Multiset.Entry<E> pollFirstEntry() {
        Iterator<Multiset.Entry<E>> b = b();
        if (!b.hasNext()) {
            return null;
        }
        Multiset.Entry<E> next = b.next();
        Multiset.Entry<E> immutableEntry = Multisets.immutableEntry(next.getElement(), next.getCount());
        b.remove();
        return immutableEntry;
    }

    public Multiset.Entry<E> pollLastEntry() {
        Iterator<Multiset.Entry<E>> f = f();
        if (!f.hasNext()) {
            return null;
        }
        Multiset.Entry<E> next = f.next();
        Multiset.Entry<E> immutableEntry = Multisets.immutableEntry(next.getElement(), next.getCount());
        f.remove();
        return immutableEntry;
    }

    public SortedMultiset<E> subMultiset(@NullableDecl E e, BoundType boundType, @NullableDecl E e2, BoundType boundType2) {
        Preconditions.checkNotNull(boundType);
        Preconditions.checkNotNull(boundType2);
        return tailMultiset(e, boundType).headMultiset(e2, boundType2);
    }

    Iterator<E> g() {
        return Multisets.a((Multiset) descendingMultiset());
    }

    public SortedMultiset<E> descendingMultiset() {
        SortedMultiset<E> sortedMultiset = this.a;
        if (sortedMultiset != null) {
            return sortedMultiset;
        }
        SortedMultiset<E> h = h();
        this.a = h;
        return h;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: AbstractSortedMultiset.java */
    /* loaded from: classes2.dex */
    public class a extends af<E> {
        a() {
        }

        @Override // com.google.common.collect.af
        SortedMultiset<E> a() {
            return m.this;
        }

        @Override // com.google.common.collect.af
        Iterator<Multiset.Entry<E>> b() {
            return m.this.f();
        }

        @Override // com.google.common.collect.af, com.google.common.collect.ForwardingCollection, java.util.Collection, java.lang.Iterable
        public Iterator<E> iterator() {
            return m.this.g();
        }
    }

    SortedMultiset<E> h() {
        return new a();
    }
}
