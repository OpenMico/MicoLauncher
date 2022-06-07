package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.collect.Sets;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.SortedSet;

@GwtIncompatible
/* loaded from: classes2.dex */
public abstract class ForwardingNavigableSet<E> extends ForwardingSortedSet<E> implements NavigableSet<E> {
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.common.collect.ForwardingSortedSet, com.google.common.collect.ForwardingSet, com.google.common.collect.ForwardingCollection, com.google.common.collect.ForwardingObject
    public abstract NavigableSet<E> delegate();

    @Override // java.util.NavigableSet
    public E lower(E e) {
        return delegate().lower(e);
    }

    protected E standardLower(E e) {
        return (E) Iterators.getNext(headSet(e, false).descendingIterator(), null);
    }

    @Override // java.util.NavigableSet
    public E floor(E e) {
        return delegate().floor(e);
    }

    protected E standardFloor(E e) {
        return (E) Iterators.getNext(headSet(e, true).descendingIterator(), null);
    }

    @Override // java.util.NavigableSet
    public E ceiling(E e) {
        return delegate().ceiling(e);
    }

    protected E standardCeiling(E e) {
        return (E) Iterators.getNext(tailSet(e, true).iterator(), null);
    }

    @Override // java.util.NavigableSet
    public E higher(E e) {
        return delegate().higher(e);
    }

    protected E standardHigher(E e) {
        return (E) Iterators.getNext(tailSet(e, false).iterator(), null);
    }

    @Override // java.util.NavigableSet
    public E pollFirst() {
        return delegate().pollFirst();
    }

    protected E standardPollFirst() {
        return (E) Iterators.a(iterator());
    }

    @Override // java.util.NavigableSet
    public E pollLast() {
        return delegate().pollLast();
    }

    protected E standardPollLast() {
        return (E) Iterators.a(descendingIterator());
    }

    protected E standardFirst() {
        return iterator().next();
    }

    protected E standardLast() {
        return descendingIterator().next();
    }

    @Override // java.util.NavigableSet
    public NavigableSet<E> descendingSet() {
        return delegate().descendingSet();
    }

    @Beta
    /* loaded from: classes2.dex */
    protected class StandardDescendingSet extends Sets.b<E> {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public StandardDescendingSet() {
            super(r1);
            ForwardingNavigableSet.this = r1;
        }
    }

    @Override // java.util.NavigableSet
    public Iterator<E> descendingIterator() {
        return delegate().descendingIterator();
    }

    @Override // java.util.NavigableSet
    public NavigableSet<E> subSet(E e, boolean z, E e2, boolean z2) {
        return delegate().subSet(e, z, e2, z2);
    }

    @Beta
    protected NavigableSet<E> standardSubSet(E e, boolean z, E e2, boolean z2) {
        return tailSet(e, z).headSet(e2, z2);
    }

    @Override // com.google.common.collect.ForwardingSortedSet
    protected SortedSet<E> standardSubSet(E e, E e2) {
        return subSet(e, true, e2, false);
    }

    @Override // java.util.NavigableSet
    public NavigableSet<E> headSet(E e, boolean z) {
        return delegate().headSet(e, z);
    }

    protected SortedSet<E> standardHeadSet(E e) {
        return headSet(e, false);
    }

    @Override // java.util.NavigableSet
    public NavigableSet<E> tailSet(E e, boolean z) {
        return delegate().tailSet(e, z);
    }

    protected SortedSet<E> standardTailSet(E e) {
        return tailSet(e, true);
    }
}
