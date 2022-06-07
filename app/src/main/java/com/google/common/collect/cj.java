package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import com.google.j2objc.annotations.Weak;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.NoSuchElementException;
import java.util.SortedSet;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* compiled from: SortedMultisets.java */
@GwtCompatible(emulated = true)
/* loaded from: classes2.dex */
final class cj {

    /* compiled from: SortedMultisets.java */
    /* loaded from: classes2.dex */
    static class a<E> extends Multisets.c<E> implements SortedSet<E> {
        @Weak
        private final SortedMultiset<E> a;

        a(SortedMultiset<E> sortedMultiset) {
            this.a = sortedMultiset;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: b */
        public final SortedMultiset<E> a() {
            return this.a;
        }

        @Override // com.google.common.collect.Multisets.c, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<E> iterator() {
            return Multisets.a(a().entrySet().iterator());
        }

        @Override // java.util.SortedSet
        public Comparator<? super E> comparator() {
            return a().comparator();
        }

        @Override // java.util.SortedSet
        public SortedSet<E> subSet(E e, E e2) {
            return a().subMultiset(e, BoundType.CLOSED, e2, BoundType.OPEN).elementSet();
        }

        @Override // java.util.SortedSet
        public SortedSet<E> headSet(E e) {
            return a().headMultiset(e, BoundType.OPEN).elementSet();
        }

        @Override // java.util.SortedSet
        public SortedSet<E> tailSet(E e) {
            return a().tailMultiset(e, BoundType.CLOSED).elementSet();
        }

        @Override // java.util.SortedSet
        public E first() {
            return (E) cj.c(a().firstEntry());
        }

        @Override // java.util.SortedSet
        public E last() {
            return (E) cj.c(a().lastEntry());
        }
    }

    /* compiled from: SortedMultisets.java */
    @GwtIncompatible
    /* loaded from: classes2.dex */
    static class b<E> extends a<E> implements NavigableSet<E> {
        /* JADX INFO: Access modifiers changed from: package-private */
        public b(SortedMultiset<E> sortedMultiset) {
            super(sortedMultiset);
        }

        @Override // java.util.NavigableSet
        public E lower(E e) {
            return (E) cj.d(a().headMultiset(e, BoundType.OPEN).lastEntry());
        }

        @Override // java.util.NavigableSet
        public E floor(E e) {
            return (E) cj.d(a().headMultiset(e, BoundType.CLOSED).lastEntry());
        }

        @Override // java.util.NavigableSet
        public E ceiling(E e) {
            return (E) cj.d(a().tailMultiset(e, BoundType.CLOSED).firstEntry());
        }

        @Override // java.util.NavigableSet
        public E higher(E e) {
            return (E) cj.d(a().tailMultiset(e, BoundType.OPEN).firstEntry());
        }

        @Override // java.util.NavigableSet
        public NavigableSet<E> descendingSet() {
            return new b(a().descendingMultiset());
        }

        @Override // java.util.NavigableSet
        public Iterator<E> descendingIterator() {
            return descendingSet().iterator();
        }

        @Override // java.util.NavigableSet
        public E pollFirst() {
            return (E) cj.d(a().pollFirstEntry());
        }

        @Override // java.util.NavigableSet
        public E pollLast() {
            return (E) cj.d(a().pollLastEntry());
        }

        @Override // java.util.NavigableSet
        public NavigableSet<E> subSet(E e, boolean z, E e2, boolean z2) {
            return new b(a().subMultiset(e, BoundType.a(z), e2, BoundType.a(z2)));
        }

        @Override // java.util.NavigableSet
        public NavigableSet<E> headSet(E e, boolean z) {
            return new b(a().headMultiset(e, BoundType.a(z)));
        }

        @Override // java.util.NavigableSet
        public NavigableSet<E> tailSet(E e, boolean z) {
            return new b(a().tailMultiset(e, BoundType.a(z)));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <E> E c(Multiset.Entry<E> entry) {
        if (entry != null) {
            return entry.getElement();
        }
        throw new NoSuchElementException();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <E> E d(@NullableDecl Multiset.Entry<E> entry) {
        if (entry == null) {
            return null;
        }
        return entry.getElement();
    }
}
