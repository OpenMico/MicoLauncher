package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.collect.ImmutableMultiset;
import com.google.common.collect.Multiset;
import com.google.common.primitives.Ints;
import com.google.errorprone.annotations.concurrent.LazyInit;
import java.io.Serializable;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: RegularImmutableMultiset.java */
@GwtCompatible(emulated = true, serializable = true)
/* loaded from: classes2.dex */
public class bv<E> extends ImmutableMultiset<E> {
    static final bv<Object> a = new bv<>(bn.a());
    final transient bn<E> b;
    private final transient int c;
    @LazyInit
    private transient ImmutableSet<E> d;

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.common.collect.ImmutableCollection
    public boolean a() {
        return false;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public bv(bn<E> bnVar) {
        this.b = bnVar;
        long j = 0;
        for (int i = 0; i < bnVar.c(); i++) {
            j += bnVar.d(i);
        }
        this.c = Ints.saturatedCast(j);
    }

    @Override // com.google.common.collect.Multiset
    public int count(@NullableDecl Object obj) {
        return this.b.b(obj);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, com.google.common.collect.Multiset
    public int size() {
        return this.c;
    }

    @Override // com.google.common.collect.ImmutableMultiset, com.google.common.collect.Multiset
    public ImmutableSet<E> elementSet() {
        ImmutableSet<E> immutableSet = this.d;
        if (immutableSet != null) {
            return immutableSet;
        }
        a aVar = new a();
        this.d = aVar;
        return aVar;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: RegularImmutableMultiset.java */
    /* loaded from: classes2.dex */
    public final class a extends bg<E> {
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.google.common.collect.ImmutableCollection
        public boolean a() {
            return true;
        }

        private a() {
        }

        @Override // com.google.common.collect.bg
        E a(int i) {
            return bv.this.b.c(i);
        }

        @Override // com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection
        public boolean contains(@NullableDecl Object obj) {
            return bv.this.contains(obj);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return bv.this.b.c();
        }
    }

    @Override // com.google.common.collect.ImmutableMultiset
    Multiset.Entry<E> a(int i) {
        return this.b.e(i);
    }

    /* compiled from: RegularImmutableMultiset.java */
    @GwtIncompatible
    /* loaded from: classes2.dex */
    private static class b implements Serializable {
        private static final long serialVersionUID = 0;
        final int[] counts;
        final Object[] elements;

        b(Multiset<?> multiset) {
            int size = multiset.entrySet().size();
            this.elements = new Object[size];
            this.counts = new int[size];
            int i = 0;
            for (Multiset.Entry<?> entry : multiset.entrySet()) {
                this.elements[i] = entry.getElement();
                this.counts[i] = entry.getCount();
                i++;
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        Object readResolve() {
            ImmutableMultiset.Builder builder = new ImmutableMultiset.Builder(this.elements.length);
            int i = 0;
            while (true) {
                Object[] objArr = this.elements;
                if (i >= objArr.length) {
                    return builder.build();
                }
                builder.addCopies(objArr[i], this.counts[i]);
                i++;
            }
        }
    }

    @Override // com.google.common.collect.ImmutableMultiset, com.google.common.collect.ImmutableCollection
    @GwtIncompatible
    Object writeReplace() {
        return new b(this);
    }
}
