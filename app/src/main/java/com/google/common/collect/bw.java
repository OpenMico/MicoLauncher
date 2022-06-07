package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.VisibleForTesting;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: RegularImmutableSet.java */
@GwtCompatible(emulated = true, serializable = true)
/* loaded from: classes2.dex */
public final class bw<E> extends ImmutableSet<E> {
    static final bw<Object> a = new bw<>(new Object[0], 0, null, 0, 0);
    @VisibleForTesting
    final transient Object[] b;
    @VisibleForTesting
    final transient Object[] c;
    private final transient int d;
    private final transient int e;
    private final transient int f;

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.common.collect.ImmutableCollection
    public boolean a() {
        return false;
    }

    @Override // com.google.common.collect.ImmutableSet
    boolean e() {
        return true;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public bw(Object[] objArr, int i, Object[] objArr2, int i2, int i3) {
        this.b = objArr;
        this.c = objArr2;
        this.d = i2;
        this.e = i;
        this.f = i3;
    }

    @Override // com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection
    public boolean contains(@NullableDecl Object obj) {
        Object[] objArr = this.c;
        if (obj == null || objArr == null) {
            return false;
        }
        int a2 = au.a(obj);
        while (true) {
            int i = a2 & this.d;
            Object obj2 = objArr[i];
            if (obj2 == null) {
                return false;
            }
            if (obj2.equals(obj)) {
                return true;
            }
            a2 = i + 1;
        }
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public int size() {
        return this.f;
    }

    @Override // com.google.common.collect.ImmutableSet, com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
    public UnmodifiableIterator<E> iterator() {
        return asList().iterator();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.common.collect.ImmutableCollection
    public int a(Object[] objArr, int i) {
        System.arraycopy(this.b, 0, objArr, i, this.f);
        return i + this.f;
    }

    @Override // com.google.common.collect.ImmutableSet
    ImmutableList<E> f() {
        return ImmutableList.b(this.b, this.f);
    }

    @Override // com.google.common.collect.ImmutableSet, java.util.Collection, java.util.Set
    public int hashCode() {
        return this.e;
    }
}
