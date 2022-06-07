package com.google.common.collect;

import com.google.common.annotations.GwtIncompatible;
import java.util.Arrays;
import java.util.Collection;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: CompactLinkedHashSet.java */
@GwtIncompatible
/* loaded from: classes2.dex */
public class x<E> extends v<E> {
    @MonotonicNonNullDecl
    private transient int[] d;
    @MonotonicNonNullDecl
    private transient int[] e;
    private transient int f;
    private transient int g;

    public static <E> x<E> e(int i) {
        return new x<>(i);
    }

    x() {
    }

    x(int i) {
        super(i);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.common.collect.v
    public void a(int i, float f) {
        super.a(i, f);
        this.d = new int[i];
        this.e = new int[i];
        Arrays.fill(this.d, -1);
        Arrays.fill(this.e, -1);
        this.f = -2;
        this.g = -2;
    }

    private void b(int i, int i2) {
        if (i == -2) {
            this.f = i2;
        } else {
            this.e[i] = i2;
        }
        if (i2 == -2) {
            this.g = i;
        } else {
            this.d[i2] = i;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.common.collect.v
    public void a(int i, E e, int i2) {
        super.a(i, (int) e, i2);
        b(this.g, i);
        b(i, -2);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.common.collect.v
    public void c(int i) {
        int size = size() - 1;
        super.c(i);
        b(this.d[i], this.e[i]);
        if (size != i) {
            b(this.d[size], i);
            b(i, this.e[size]);
        }
        this.d[size] = -1;
        this.e[size] = -1;
    }

    @Override // com.google.common.collect.v, java.util.AbstractCollection, java.util.Collection, java.util.Set
    public void clear() {
        super.clear();
        this.f = -2;
        this.g = -2;
        Arrays.fill(this.d, -1);
        Arrays.fill(this.e, -1);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.common.collect.v
    public void b(int i) {
        super.b(i);
        int[] iArr = this.d;
        int length = iArr.length;
        this.d = Arrays.copyOf(iArr, i);
        this.e = Arrays.copyOf(this.e, i);
        if (length < i) {
            Arrays.fill(this.d, length, i, -1);
            Arrays.fill(this.e, length, i, -1);
        }
    }

    @Override // com.google.common.collect.v, java.util.AbstractCollection, java.util.Collection, java.util.Set
    public Object[] toArray() {
        return ObjectArrays.a(this);
    }

    @Override // com.google.common.collect.v, java.util.AbstractCollection, java.util.Collection, java.util.Set
    public <T> T[] toArray(T[] tArr) {
        return (T[]) ObjectArrays.a((Collection<?>) this, (Object[]) tArr);
    }

    @Override // com.google.common.collect.v
    int b() {
        return this.f;
    }

    @Override // com.google.common.collect.v
    int a(int i, int i2) {
        return i == size() ? i2 : i;
    }

    @Override // com.google.common.collect.v
    int d(int i) {
        return this.e[i];
    }
}
