package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: RegularImmutableList.java */
@GwtCompatible(emulated = true, serializable = true)
/* loaded from: classes2.dex */
public class bt<E> extends ImmutableList<E> {
    static final ImmutableList<Object> a = new bt(new Object[0], 0);
    @VisibleForTesting
    final transient Object[] b;
    private final transient int c;

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.common.collect.ImmutableCollection
    public boolean a() {
        return false;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public bt(Object[] objArr, int i) {
        this.b = objArr;
        this.c = i;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public int size() {
        return this.c;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.common.collect.ImmutableList, com.google.common.collect.ImmutableCollection
    public int a(Object[] objArr, int i) {
        System.arraycopy(this.b, 0, objArr, i, this.c);
        return i + this.c;
    }

    @Override // java.util.List
    public E get(int i) {
        Preconditions.checkElementIndex(i, this.c);
        return (E) this.b[i];
    }
}
