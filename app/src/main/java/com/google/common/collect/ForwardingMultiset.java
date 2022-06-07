package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Objects;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible
/* loaded from: classes2.dex */
public abstract class ForwardingMultiset<E> extends ForwardingCollection<E> implements Multiset<E> {
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.common.collect.ForwardingCollection, com.google.common.collect.ForwardingObject
    public abstract Multiset<E> delegate();

    @Override // com.google.common.collect.Multiset
    public int count(Object obj) {
        return delegate().count(obj);
    }

    @Override // com.google.common.collect.Multiset
    @CanIgnoreReturnValue
    public int add(E e, int i) {
        return delegate().add(e, i);
    }

    @Override // com.google.common.collect.Multiset
    @CanIgnoreReturnValue
    public int remove(Object obj, int i) {
        return delegate().remove(obj, i);
    }

    @Override // com.google.common.collect.Multiset
    public Set<E> elementSet() {
        return delegate().elementSet();
    }

    @Override // com.google.common.collect.Multiset
    public Set<Multiset.Entry<E>> entrySet() {
        return delegate().entrySet();
    }

    @Override // java.util.Collection, com.google.common.collect.Multiset
    public boolean equals(@NullableDecl Object obj) {
        return obj == this || delegate().equals(obj);
    }

    @Override // java.util.Collection, com.google.common.collect.Multiset
    public int hashCode() {
        return delegate().hashCode();
    }

    @Override // com.google.common.collect.Multiset
    @CanIgnoreReturnValue
    public int setCount(E e, int i) {
        return delegate().setCount(e, i);
    }

    @Override // com.google.common.collect.Multiset
    @CanIgnoreReturnValue
    public boolean setCount(E e, int i, int i2) {
        return delegate().setCount(e, i, i2);
    }

    @Override // com.google.common.collect.ForwardingCollection
    protected boolean standardContains(@NullableDecl Object obj) {
        return count(obj) > 0;
    }

    @Override // com.google.common.collect.ForwardingCollection
    protected void standardClear() {
        Iterators.b(entrySet().iterator());
    }

    @Beta
    protected int standardCount(@NullableDecl Object obj) {
        for (Multiset.Entry<E> entry : entrySet()) {
            if (Objects.equal(entry.getElement(), obj)) {
                return entry.getCount();
            }
        }
        return 0;
    }

    protected boolean standardAdd(E e) {
        add(e, 1);
        return true;
    }

    @Override // com.google.common.collect.ForwardingCollection
    @Beta
    protected boolean standardAddAll(Collection<? extends E> collection) {
        return Multisets.a((Multiset) this, (Collection) collection);
    }

    @Override // com.google.common.collect.ForwardingCollection
    protected boolean standardRemove(Object obj) {
        return remove(obj, 1) > 0;
    }

    @Override // com.google.common.collect.ForwardingCollection
    protected boolean standardRemoveAll(Collection<?> collection) {
        return Multisets.b(this, collection);
    }

    @Override // com.google.common.collect.ForwardingCollection
    protected boolean standardRetainAll(Collection<?> collection) {
        return Multisets.c(this, collection);
    }

    protected int standardSetCount(E e, int i) {
        return Multisets.a(this, e, i);
    }

    protected boolean standardSetCount(E e, int i, int i2) {
        return Multisets.a(this, e, i, i2);
    }

    @Beta
    /* loaded from: classes2.dex */
    protected class StandardElementSet extends Multisets.c<E> {
        public StandardElementSet() {
        }

        @Override // com.google.common.collect.Multisets.c
        Multiset<E> a() {
            return ForwardingMultiset.this;
        }

        @Override // com.google.common.collect.Multisets.c, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<E> iterator() {
            return Multisets.a(a().entrySet().iterator());
        }
    }

    protected Iterator<E> standardIterator() {
        return Multisets.a((Multiset) this);
    }

    protected int standardSize() {
        return Multisets.b((Multiset<?>) this);
    }

    protected boolean standardEquals(@NullableDecl Object obj) {
        return Multisets.a(this, obj);
    }

    protected int standardHashCode() {
        return entrySet().hashCode();
    }

    @Override // com.google.common.collect.ForwardingCollection
    protected String standardToString() {
        return entrySet().toString();
    }
}
