package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: AbstractMultiset.java */
@GwtCompatible
/* loaded from: classes2.dex */
public abstract class h<E> extends AbstractCollection<E> implements Multiset<E> {
    @MonotonicNonNullDecl
    private transient Set<E> a;
    @MonotonicNonNullDecl
    private transient Set<Multiset.Entry<E>> b;

    abstract Iterator<E> a();

    abstract Iterator<Multiset.Entry<E>> b();

    abstract int c();

    @Override // java.util.AbstractCollection, java.util.Collection
    public abstract void clear();

    @Override // java.util.AbstractCollection, java.util.Collection
    public boolean isEmpty() {
        return entrySet().isEmpty();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, com.google.common.collect.Multiset
    public boolean contains(@NullableDecl Object obj) {
        return count(obj) > 0;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, com.google.common.collect.Multiset
    @CanIgnoreReturnValue
    public final boolean add(@NullableDecl E e) {
        add(e, 1);
        return true;
    }

    @CanIgnoreReturnValue
    public int add(@NullableDecl E e, int i) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, com.google.common.collect.Multiset
    @CanIgnoreReturnValue
    public final boolean remove(@NullableDecl Object obj) {
        return remove(obj, 1) > 0;
    }

    @CanIgnoreReturnValue
    public int remove(@NullableDecl Object obj, int i) {
        throw new UnsupportedOperationException();
    }

    @CanIgnoreReturnValue
    public int setCount(@NullableDecl E e, int i) {
        return Multisets.a(this, e, i);
    }

    @CanIgnoreReturnValue
    public boolean setCount(@NullableDecl E e, int i, int i2) {
        return Multisets.a(this, e, i, i2);
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    @CanIgnoreReturnValue
    public final boolean addAll(Collection<? extends E> collection) {
        return Multisets.a((Multiset) this, (Collection) collection);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, com.google.common.collect.Multiset
    @CanIgnoreReturnValue
    public final boolean removeAll(Collection<?> collection) {
        return Multisets.b(this, collection);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, com.google.common.collect.Multiset
    @CanIgnoreReturnValue
    public final boolean retainAll(Collection<?> collection) {
        return Multisets.c(this, collection);
    }

    public Set<E> elementSet() {
        Set<E> set = this.a;
        if (set != null) {
            return set;
        }
        Set<E> d = d();
        this.a = d;
        return d;
    }

    Set<E> d() {
        return new a();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: AbstractMultiset.java */
    /* loaded from: classes2.dex */
    public class a extends Multisets.c<E> {
        a() {
        }

        @Override // com.google.common.collect.Multisets.c
        Multiset<E> a() {
            return h.this;
        }

        @Override // com.google.common.collect.Multisets.c, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<E> iterator() {
            return h.this.a();
        }
    }

    public Set<Multiset.Entry<E>> entrySet() {
        Set<Multiset.Entry<E>> set = this.b;
        if (set != null) {
            return set;
        }
        Set<Multiset.Entry<E>> createEntrySet = createEntrySet();
        this.b = createEntrySet;
        return createEntrySet;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: AbstractMultiset.java */
    /* loaded from: classes2.dex */
    public class b extends Multisets.d<E> {
        /* JADX INFO: Access modifiers changed from: package-private */
        public b() {
        }

        @Override // com.google.common.collect.Multisets.d
        Multiset<E> a() {
            return h.this;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<Multiset.Entry<E>> iterator() {
            return h.this.b();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return h.this.c();
        }
    }

    Set<Multiset.Entry<E>> createEntrySet() {
        return new b();
    }

    @Override // java.util.Collection, com.google.common.collect.Multiset
    public final boolean equals(@NullableDecl Object obj) {
        return Multisets.a(this, obj);
    }

    @Override // java.util.Collection, com.google.common.collect.Multiset
    public final int hashCode() {
        return entrySet().hashCode();
    }

    @Override // java.util.AbstractCollection, com.google.common.collect.Multiset
    public final String toString() {
        return entrySet().toString();
    }
}
