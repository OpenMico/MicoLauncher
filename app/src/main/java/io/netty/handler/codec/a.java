package io.netty.handler.codec;

import io.netty.util.Recycler;
import io.netty.util.internal.ObjectUtil;
import java.util.AbstractList;
import java.util.RandomAccess;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: CodecOutputList.java */
/* loaded from: classes4.dex */
public final class a extends AbstractList<Object> implements RandomAccess {
    private static final Recycler<a> a = new Recycler<a>() { // from class: io.netty.handler.codec.a.1
        /* JADX INFO: Access modifiers changed from: protected */
        /* renamed from: a */
        public a newObject(Recycler.Handle<a> handle) {
            return new a(handle);
        }
    };
    private final Recycler.Handle<a> b;
    private int c;
    private Object[] d;
    private boolean e;

    /* JADX INFO: Access modifiers changed from: package-private */
    public static a a() {
        return a.get();
    }

    private a(Recycler.Handle<a> handle) {
        this.d = new Object[16];
        this.b = handle;
    }

    @Override // java.util.AbstractList, java.util.List
    public Object get(int i) {
        b(i);
        return this.d[i];
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public int size() {
        return this.c;
    }

    @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public boolean add(Object obj) {
        ObjectUtil.checkNotNull(obj, "element");
        try {
            a(this.c, obj);
        } catch (IndexOutOfBoundsException unused) {
            d();
            a(this.c, obj);
        }
        this.c++;
        return true;
    }

    @Override // java.util.AbstractList, java.util.List
    public Object set(int i, Object obj) {
        ObjectUtil.checkNotNull(obj, "element");
        b(i);
        Object obj2 = this.d[i];
        a(i, obj);
        return obj2;
    }

    @Override // java.util.AbstractList, java.util.List
    public void add(int i, Object obj) {
        ObjectUtil.checkNotNull(obj, "element");
        b(i);
        if (this.c == this.d.length) {
            d();
        }
        int i2 = this.c;
        if (i != i2 - 1) {
            Object[] objArr = this.d;
            System.arraycopy(objArr, i, objArr, i + 1, i2 - i);
        }
        a(i, obj);
        this.c++;
    }

    @Override // java.util.AbstractList, java.util.List
    public Object remove(int i) {
        b(i);
        Object[] objArr = this.d;
        Object obj = objArr[i];
        int i2 = (this.c - i) - 1;
        if (i2 > 0) {
            System.arraycopy(objArr, i + 1, objArr, i, i2);
        }
        Object[] objArr2 = this.d;
        int i3 = this.c - 1;
        this.c = i3;
        objArr2[i3] = null;
        return obj;
    }

    @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public void clear() {
        this.c = 0;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean b() {
        return this.e;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void c() {
        for (int i = 0; i < this.c; i++) {
            this.d[i] = null;
        }
        clear();
        this.e = false;
        this.b.recycle(this);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Object a(int i) {
        return this.d[i];
    }

    private void b(int i) {
        if (i >= this.c) {
            throw new IndexOutOfBoundsException();
        }
    }

    private void a(int i, Object obj) {
        this.d[i] = obj;
        this.e = true;
    }

    private void d() {
        Object[] objArr = this.d;
        int length = objArr.length << 1;
        if (length >= 0) {
            Object[] objArr2 = new Object[length];
            System.arraycopy(objArr, 0, objArr2, 0, objArr.length);
            this.d = objArr2;
            return;
        }
        throw new OutOfMemoryError();
    }
}
