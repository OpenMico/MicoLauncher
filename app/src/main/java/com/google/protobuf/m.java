package com.google.protobuf;

import com.google.protobuf.Internal;
import java.util.Arrays;
import java.util.Collection;
import java.util.RandomAccess;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: FloatArrayList.java */
/* loaded from: classes2.dex */
public final class m extends a<Float> implements Internal.FloatList, ag, RandomAccess {
    private static final m a = new m(new float[0], 0);
    private float[] b;
    private int c;

    static {
        a.makeImmutable();
    }

    public static m a() {
        return a;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public m() {
        this(new float[10], 0);
    }

    private m(float[] fArr, int i) {
        this.b = fArr;
        this.c = i;
    }

    @Override // java.util.AbstractList
    protected void removeRange(int i, int i2) {
        ensureIsMutable();
        if (i2 >= i) {
            float[] fArr = this.b;
            System.arraycopy(fArr, i2, fArr, i, this.c - i2);
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
        if (!(obj instanceof m)) {
            return super.equals(obj);
        }
        m mVar = (m) obj;
        if (this.c != mVar.c) {
            return false;
        }
        float[] fArr = mVar.b;
        for (int i = 0; i < this.c; i++) {
            if (Float.floatToIntBits(this.b[i]) != Float.floatToIntBits(fArr[i])) {
                return false;
            }
        }
        return true;
    }

    @Override // com.google.protobuf.a, java.util.AbstractList, java.util.Collection, java.util.List
    public int hashCode() {
        int i = 1;
        for (int i2 = 0; i2 < this.c; i2++) {
            i = (i * 31) + Float.floatToIntBits(this.b[i2]);
        }
        return i;
    }

    @Override // com.google.protobuf.Internal.ProtobufList
    /* renamed from: mutableCopyWithCapacity */
    public Internal.ProtobufList<Float> mutableCopyWithCapacity2(int i) {
        if (i >= this.c) {
            return new m(Arrays.copyOf(this.b, i), this.c);
        }
        throw new IllegalArgumentException();
    }

    /* renamed from: a */
    public Float get(int i) {
        return Float.valueOf(getFloat(i));
    }

    @Override // com.google.protobuf.Internal.FloatList
    public float getFloat(int i) {
        c(i);
        return this.b[i];
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public int size() {
        return this.c;
    }

    /* renamed from: a */
    public Float set(int i, Float f) {
        return Float.valueOf(setFloat(i, f.floatValue()));
    }

    @Override // com.google.protobuf.Internal.FloatList
    public float setFloat(int i, float f) {
        ensureIsMutable();
        c(i);
        float[] fArr = this.b;
        float f2 = fArr[i];
        fArr[i] = f;
        return f2;
    }

    /* renamed from: a */
    public boolean add(Float f) {
        addFloat(f.floatValue());
        return true;
    }

    /* renamed from: b */
    public void add(int i, Float f) {
        a(i, f.floatValue());
    }

    @Override // com.google.protobuf.Internal.FloatList
    public void addFloat(float f) {
        ensureIsMutable();
        int i = this.c;
        float[] fArr = this.b;
        if (i == fArr.length) {
            float[] fArr2 = new float[((i * 3) / 2) + 1];
            System.arraycopy(fArr, 0, fArr2, 0, i);
            this.b = fArr2;
        }
        float[] fArr3 = this.b;
        int i2 = this.c;
        this.c = i2 + 1;
        fArr3[i2] = f;
    }

    private void a(int i, float f) {
        int i2;
        ensureIsMutable();
        if (i < 0 || i > (i2 = this.c)) {
            throw new IndexOutOfBoundsException(d(i));
        }
        float[] fArr = this.b;
        if (i2 < fArr.length) {
            System.arraycopy(fArr, i, fArr, i + 1, i2 - i);
        } else {
            float[] fArr2 = new float[((i2 * 3) / 2) + 1];
            System.arraycopy(fArr, 0, fArr2, 0, i);
            System.arraycopy(this.b, i, fArr2, i + 1, this.c - i);
            this.b = fArr2;
        }
        this.b[i] = f;
        this.c++;
        this.modCount++;
    }

    @Override // com.google.protobuf.a, java.util.AbstractCollection, java.util.Collection, java.util.List
    public boolean addAll(Collection<? extends Float> collection) {
        ensureIsMutable();
        Internal.a(collection);
        if (!(collection instanceof m)) {
            return super.addAll(collection);
        }
        m mVar = (m) collection;
        int i = mVar.c;
        if (i == 0) {
            return false;
        }
        int i2 = this.c;
        if (Integer.MAX_VALUE - i2 >= i) {
            int i3 = i2 + i;
            float[] fArr = this.b;
            if (i3 > fArr.length) {
                this.b = Arrays.copyOf(fArr, i3);
            }
            System.arraycopy(mVar.b, 0, this.b, this.c, mVar.c);
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
            if (obj.equals(Float.valueOf(this.b[i]))) {
                float[] fArr = this.b;
                System.arraycopy(fArr, i + 1, fArr, i, (this.c - i) - 1);
                this.c--;
                this.modCount++;
                return true;
            }
        }
        return false;
    }

    /* renamed from: b */
    public Float remove(int i) {
        ensureIsMutable();
        c(i);
        float[] fArr = this.b;
        float f = fArr[i];
        int i2 = this.c;
        if (i < i2 - 1) {
            System.arraycopy(fArr, i + 1, fArr, i, (i2 - i) - 1);
        }
        this.c--;
        this.modCount++;
        return Float.valueOf(f);
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
