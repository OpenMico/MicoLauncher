package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.math.IntMath;
import java.util.AbstractList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: CartesianList.java */
@GwtCompatible
/* loaded from: classes2.dex */
public final class s<E> extends AbstractList<List<E>> implements RandomAccess {
    private final transient ImmutableList<List<E>> a;
    private final transient int[] b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <E> List<List<E>> a(List<? extends List<? extends E>> list) {
        ImmutableList.Builder builder = new ImmutableList.Builder(list.size());
        Iterator<? extends List<? extends E>> it = list.iterator();
        while (it.hasNext()) {
            ImmutableList copyOf = ImmutableList.copyOf((Collection) ((List) it.next()));
            if (copyOf.isEmpty()) {
                return ImmutableList.of();
            }
            builder.add((ImmutableList.Builder) copyOf);
        }
        return new s(builder.build());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public s(ImmutableList<List<E>> immutableList) {
        this.a = immutableList;
        int[] iArr = new int[immutableList.size() + 1];
        iArr[immutableList.size()] = 1;
        try {
            for (int size = immutableList.size() - 1; size >= 0; size--) {
                iArr[size] = IntMath.checkedMultiply(iArr[size + 1], immutableList.get(size).size());
            }
            this.b = iArr;
        } catch (ArithmeticException unused) {
            throw new IllegalArgumentException("Cartesian product too large; must have size at most Integer.MAX_VALUE");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int a(int i, int i2) {
        return (i / this.b[i2 + 1]) % this.a.get(i2).size();
    }

    /* renamed from: a */
    public ImmutableList<E> get(final int i) {
        Preconditions.checkElementIndex(i, size());
        return new ImmutableList<E>() { // from class: com.google.common.collect.s.1
            /* JADX INFO: Access modifiers changed from: package-private */
            @Override // com.google.common.collect.ImmutableCollection
            public boolean a() {
                return true;
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
            public int size() {
                return s.this.a.size();
            }

            @Override // java.util.List
            public E get(int i2) {
                Preconditions.checkElementIndex(i2, size());
                return (E) ((List) s.this.a.get(i2)).get(s.this.a(i, i2));
            }
        };
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public int size() {
        return this.b[0];
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public boolean contains(@NullableDecl Object obj) {
        if (!(obj instanceof List)) {
            return false;
        }
        List list = (List) obj;
        if (list.size() != this.a.size()) {
            return false;
        }
        ListIterator<E> listIterator = list.listIterator();
        while (listIterator.hasNext()) {
            if (!this.a.get(listIterator.nextIndex()).contains(listIterator.next())) {
                return false;
            }
        }
        return true;
    }
}
