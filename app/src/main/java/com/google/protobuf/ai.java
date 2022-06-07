package com.google.protobuf;

import java.util.Arrays;
import java.util.RandomAccess;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: ProtobufArrayList.java */
/* loaded from: classes2.dex */
public final class ai<E> extends a<E> implements RandomAccess {
    private static final ai<Object> a = new ai<>(new Object[0], 0);
    private E[] b;
    private int c;

    static {
        a.makeImmutable();
    }

    public static <E> ai<E> a() {
        return (ai<E>) a;
    }

    ai() {
        this(new Object[10], 0);
    }

    private ai(E[] eArr, int i) {
        this.b = eArr;
        this.c = i;
    }

    /* renamed from: a */
    public ai<E> mutableCopyWithCapacity(int i) {
        if (i >= this.c) {
            return new ai<>(Arrays.copyOf(this.b, i), this.c);
        }
        throw new IllegalArgumentException();
    }

    @Override // com.google.protobuf.a, java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public boolean add(E e) {
        ensureIsMutable();
        int i = this.c;
        E[] eArr = this.b;
        if (i == eArr.length) {
            this.b = (E[]) Arrays.copyOf(eArr, ((i * 3) / 2) + 1);
        }
        E[] eArr2 = this.b;
        int i2 = this.c;
        this.c = i2 + 1;
        eArr2[i2] = e;
        this.modCount++;
        return true;
    }

    @Override // com.google.protobuf.a, java.util.AbstractList, java.util.List
    public void add(int i, E e) {
        int i2;
        ensureIsMutable();
        if (i < 0 || i > (i2 = this.c)) {
            throw new IndexOutOfBoundsException(d(i));
        }
        E[] eArr = this.b;
        if (i2 < eArr.length) {
            System.arraycopy(eArr, i, eArr, i + 1, i2 - i);
        } else {
            E[] eArr2 = (E[]) b(((i2 * 3) / 2) + 1);
            System.arraycopy(this.b, 0, eArr2, 0, i);
            System.arraycopy(this.b, i, eArr2, i + 1, this.c - i);
            this.b = eArr2;
        }
        this.b[i] = e;
        this.c++;
        this.modCount++;
    }

    @Override // java.util.AbstractList, java.util.List
    public E get(int i) {
        c(i);
        return this.b[i];
    }

    @Override // com.google.protobuf.a, java.util.AbstractList, java.util.List
    public E remove(int i) {
        ensureIsMutable();
        c(i);
        E[] eArr = this.b;
        E e = eArr[i];
        int i2 = this.c;
        if (i < i2 - 1) {
            System.arraycopy(eArr, i + 1, eArr, i, (i2 - i) - 1);
        }
        this.c--;
        this.modCount++;
        return e;
    }

    @Override // com.google.protobuf.a, java.util.AbstractList, java.util.List
    public E set(int i, E e) {
        ensureIsMutable();
        c(i);
        E[] eArr = this.b;
        E e2 = eArr[i];
        eArr[i] = e;
        this.modCount++;
        return e2;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public int size() {
        return this.c;
    }

    private static <E> E[] b(int i) {
        return (E[]) new Object[i];
    }

    private void c(int i) {
        if (i < 0 || i >= this.c) {
            throw new IndexOutOfBoundsException(d(i));
        }
    }

    private String d(int i) {
        return "Index:" + i + ", Size:" + this.c;
    }
}
