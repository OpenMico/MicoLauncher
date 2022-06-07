package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.concurrent.LazyInit;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: SingletonImmutableSet.java */
@GwtCompatible(emulated = true, serializable = true)
/* loaded from: classes2.dex */
public final class cd<E> extends ImmutableSet<E> {
    final transient E a;
    @LazyInit
    private transient int b;

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.common.collect.ImmutableCollection
    public boolean a() {
        return false;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public int size() {
        return 1;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public cd(E e) {
        this.a = (E) Preconditions.checkNotNull(e);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public cd(E e, int i) {
        this.a = e;
        this.b = i;
    }

    @Override // com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection
    public boolean contains(Object obj) {
        return this.a.equals(obj);
    }

    @Override // com.google.common.collect.ImmutableSet, com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
    public UnmodifiableIterator<E> iterator() {
        return Iterators.singletonIterator(this.a);
    }

    @Override // com.google.common.collect.ImmutableSet
    ImmutableList<E> f() {
        return ImmutableList.of((Object) this.a);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.common.collect.ImmutableCollection
    public int a(Object[] objArr, int i) {
        objArr[i] = this.a;
        return i + 1;
    }

    @Override // com.google.common.collect.ImmutableSet, java.util.Collection, java.util.Set
    public final int hashCode() {
        int i = this.b;
        if (i != 0) {
            return i;
        }
        int hashCode = this.a.hashCode();
        this.b = hashCode;
        return hashCode;
    }

    @Override // com.google.common.collect.ImmutableSet
    boolean e() {
        return this.b != 0;
    }

    @Override // java.util.AbstractCollection
    public String toString() {
        return '[' + this.a.toString() + ']';
    }
}
