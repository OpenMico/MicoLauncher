package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible
/* loaded from: classes2.dex */
public abstract class ForwardingList<E> extends ForwardingCollection<E> implements List<E> {
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.common.collect.ForwardingCollection, com.google.common.collect.ForwardingObject
    public abstract List<E> delegate();

    @Override // java.util.List
    public void add(int i, E e) {
        delegate().add(i, e);
    }

    @Override // java.util.List
    @CanIgnoreReturnValue
    public boolean addAll(int i, Collection<? extends E> collection) {
        return delegate().addAll(i, collection);
    }

    @Override // java.util.List
    public E get(int i) {
        return delegate().get(i);
    }

    @Override // java.util.List
    public int indexOf(Object obj) {
        return delegate().indexOf(obj);
    }

    @Override // java.util.List
    public int lastIndexOf(Object obj) {
        return delegate().lastIndexOf(obj);
    }

    @Override // java.util.List
    public ListIterator<E> listIterator() {
        return delegate().listIterator();
    }

    @Override // java.util.List
    public ListIterator<E> listIterator(int i) {
        return delegate().listIterator(i);
    }

    @Override // java.util.List
    @CanIgnoreReturnValue
    public E remove(int i) {
        return delegate().remove(i);
    }

    @Override // java.util.List
    @CanIgnoreReturnValue
    public E set(int i, E e) {
        return delegate().set(i, e);
    }

    @Override // java.util.List
    public List<E> subList(int i, int i2) {
        return delegate().subList(i, i2);
    }

    @Override // java.util.Collection, java.util.List
    public boolean equals(@NullableDecl Object obj) {
        return obj == this || delegate().equals(obj);
    }

    @Override // java.util.Collection, java.util.List
    public int hashCode() {
        return delegate().hashCode();
    }

    protected boolean standardAdd(E e) {
        add(size(), e);
        return true;
    }

    protected boolean standardAddAll(int i, Iterable<? extends E> iterable) {
        return Lists.a(this, i, iterable);
    }

    protected int standardIndexOf(@NullableDecl Object obj) {
        return Lists.b(this, obj);
    }

    protected int standardLastIndexOf(@NullableDecl Object obj) {
        return Lists.c(this, obj);
    }

    protected Iterator<E> standardIterator() {
        return listIterator();
    }

    protected ListIterator<E> standardListIterator() {
        return listIterator(0);
    }

    @Beta
    protected ListIterator<E> standardListIterator(int i) {
        return Lists.a(this, i);
    }

    @Beta
    protected List<E> standardSubList(int i, int i2) {
        return Lists.a(this, i, i2);
    }

    @Beta
    protected boolean standardEquals(@NullableDecl Object obj) {
        return Lists.a(this, obj);
    }

    @Beta
    protected int standardHashCode() {
        return Lists.a((List<?>) this);
    }
}
