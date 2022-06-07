package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: IndexedImmutableSet.java */
@GwtCompatible(emulated = true)
/* loaded from: classes2.dex */
public abstract class bg<E> extends ImmutableSet<E> {
    abstract E a(int i);

    @Override // com.google.common.collect.ImmutableSet, com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
    public UnmodifiableIterator<E> iterator() {
        return asList().iterator();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.common.collect.ImmutableCollection
    @GwtIncompatible
    public int a(Object[] objArr, int i) {
        return asList().a(objArr, i);
    }

    @Override // com.google.common.collect.ImmutableSet
    ImmutableList<E> f() {
        return new ImmutableList<E>() { // from class: com.google.common.collect.bg.1
            @Override // java.util.List
            public E get(int i) {
                return (E) bg.this.a(i);
            }

            /* JADX INFO: Access modifiers changed from: package-private */
            @Override // com.google.common.collect.ImmutableCollection
            public boolean a() {
                return bg.this.a();
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
            public int size() {
                return bg.this.size();
            }
        };
    }
}
