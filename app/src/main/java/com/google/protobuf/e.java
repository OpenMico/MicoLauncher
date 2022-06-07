package com.google.protobuf;

import com.google.protobuf.Internal;
import java.util.Arrays;
import java.util.Collection;
import java.util.RandomAccess;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: BooleanArrayList.java */
/* loaded from: classes2.dex */
public final class e extends a<Boolean> implements Internal.BooleanList, ag, RandomAccess {
    private static final e a = new e(new boolean[0], 0);
    private boolean[] b;
    private int c;

    static {
        a.makeImmutable();
    }

    public static e a() {
        return a;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public e() {
        this(new boolean[10], 0);
    }

    private e(boolean[] zArr, int i) {
        this.b = zArr;
        this.c = i;
    }

    @Override // java.util.AbstractList
    protected void removeRange(int i, int i2) {
        ensureIsMutable();
        if (i2 >= i) {
            boolean[] zArr = this.b;
            System.arraycopy(zArr, i2, zArr, i, this.c - i2);
            this.c -= i2 - i;
            this.modCount++;
            return;
        }
        throw new IndexOutOfBoundsException("toIndex < fromIndex");
    }

    @Override // com.google.protobuf.a, java.util.AbstractList, java.util.Collection, java.util.List
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof e)) {
            return super.equals(obj);
        }
        e eVar = (e) obj;
        if (this.c != eVar.c) {
            return false;
        }
        boolean[] zArr = eVar.b;
        for (int i = 0; i < this.c; i++) {
            if (this.b[i] != zArr[i]) {
                return false;
            }
        }
        return true;
    }

    @Override // com.google.protobuf.a, java.util.AbstractList, java.util.Collection, java.util.List
    public int hashCode() {
        int i = 1;
        for (int i2 = 0; i2 < this.c; i2++) {
            i = (i * 31) + Internal.hashBoolean(this.b[i2]);
        }
        return i;
    }

    @Override // com.google.protobuf.Internal.ProtobufList
    /* renamed from: mutableCopyWithCapacity */
    public Internal.ProtobufList<Boolean> mutableCopyWithCapacity2(int i) {
        if (i >= this.c) {
            return new e(Arrays.copyOf(this.b, i), this.c);
        }
        throw new IllegalArgumentException();
    }

    /* renamed from: a */
    public Boolean get(int i) {
        return Boolean.valueOf(getBoolean(i));
    }

    @Override // com.google.protobuf.Internal.BooleanList
    public boolean getBoolean(int i) {
        c(i);
        return this.b[i];
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public int size() {
        return this.c;
    }

    /* renamed from: a */
    public Boolean set(int i, Boolean bool) {
        return Boolean.valueOf(setBoolean(i, bool.booleanValue()));
    }

    @Override // com.google.protobuf.Internal.BooleanList
    public boolean setBoolean(int i, boolean z) {
        ensureIsMutable();
        c(i);
        boolean[] zArr = this.b;
        boolean z2 = zArr[i];
        zArr[i] = z;
        return z2;
    }

    /* renamed from: a */
    public boolean add(Boolean bool) {
        addBoolean(bool.booleanValue());
        return true;
    }

    /* renamed from: b */
    public void add(int i, Boolean bool) {
        a(i, bool.booleanValue());
    }

    @Override // com.google.protobuf.Internal.BooleanList
    public void addBoolean(boolean z) {
        ensureIsMutable();
        int i = this.c;
        boolean[] zArr = this.b;
        if (i == zArr.length) {
            boolean[] zArr2 = new boolean[((i * 3) / 2) + 1];
            System.arraycopy(zArr, 0, zArr2, 0, i);
            this.b = zArr2;
        }
        boolean[] zArr3 = this.b;
        int i2 = this.c;
        this.c = i2 + 1;
        zArr3[i2] = z;
    }

    private void a(int i, boolean z) {
        int i2;
        ensureIsMutable();
        if (i < 0 || i > (i2 = this.c)) {
            throw new IndexOutOfBoundsException(d(i));
        }
        boolean[] zArr = this.b;
        if (i2 < zArr.length) {
            System.arraycopy(zArr, i, zArr, i + 1, i2 - i);
        } else {
            boolean[] zArr2 = new boolean[((i2 * 3) / 2) + 1];
            System.arraycopy(zArr, 0, zArr2, 0, i);
            System.arraycopy(this.b, i, zArr2, i + 1, this.c - i);
            this.b = zArr2;
        }
        this.b[i] = z;
        this.c++;
        this.modCount++;
    }

    @Override // com.google.protobuf.a, java.util.AbstractCollection, java.util.Collection, java.util.List
    public boolean addAll(Collection<? extends Boolean> collection) {
        ensureIsMutable();
        Internal.a(collection);
        if (!(collection instanceof e)) {
            return super.addAll(collection);
        }
        e eVar = (e) collection;
        int i = eVar.c;
        if (i == 0) {
            return false;
        }
        int i2 = this.c;
        if (Integer.MAX_VALUE - i2 >= i) {
            int i3 = i2 + i;
            boolean[] zArr = this.b;
            if (i3 > zArr.length) {
                this.b = Arrays.copyOf(zArr, i3);
            }
            System.arraycopy(eVar.b, 0, this.b, this.c, eVar.c);
            this.c = i3;
            this.modCount++;
            return true;
        }
        throw new OutOfMemoryError();
    }

    @Override // com.google.protobuf.a, java.util.AbstractCollection, java.util.Collection, java.util.List
    public boolean remove(Object obj) {
        ensureIsMutable();
        for (int i = 0; i < this.c; i++) {
            if (obj.equals(Boolean.valueOf(this.b[i]))) {
                boolean[] zArr = this.b;
                System.arraycopy(zArr, i + 1, zArr, i, (this.c - i) - 1);
                this.c--;
                this.modCount++;
                return true;
            }
        }
        return false;
    }

    /* renamed from: b */
    public Boolean remove(int i) {
        ensureIsMutable();
        c(i);
        boolean[] zArr = this.b;
        boolean z = zArr[i];
        int i2 = this.c;
        if (i < i2 - 1) {
            System.arraycopy(zArr, i + 1, zArr, i, (i2 - i) - 1);
        }
        this.c--;
        this.modCount++;
        return Boolean.valueOf(z);
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
