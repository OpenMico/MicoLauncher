package com.google.common.cache;

import com.google.common.annotations.GwtCompatible;
import com.google.common.cache.f;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/* compiled from: LongAdder.java */
@GwtCompatible(emulated = true)
/* loaded from: classes2.dex */
final class d extends f implements b, Serializable {
    private static final long serialVersionUID = 7249069246863182397L;

    @Override // com.google.common.cache.f
    final long a(long j, long j2) {
        return j + j2;
    }

    @Override // com.google.common.cache.b
    public void a(long j) {
        int length;
        f.a aVar;
        f.a[] aVarArr = this.d;
        if (aVarArr == null) {
            long j2 = this.e;
            if (b(j2, j2 + j)) {
                return;
            }
        }
        int[] iArr = (int[]) a.get();
        boolean z = true;
        if (!(iArr == null || aVarArr == null || (length = aVarArr.length) < 1 || (aVar = aVarArr[(length - 1) & iArr[0]]) == null)) {
            long j3 = aVar.a;
            z = aVar.a(j3, j3 + j);
            if (z) {
                return;
            }
        }
        a(j, iArr, z);
    }

    @Override // com.google.common.cache.b
    public void a() {
        a(1L);
    }

    @Override // com.google.common.cache.b
    public long b() {
        long j = this.e;
        f.a[] aVarArr = this.d;
        if (aVarArr != null) {
            for (f.a aVar : aVarArr) {
                if (aVar != null) {
                    j += aVar.a;
                }
            }
        }
        return j;
    }

    public String toString() {
        return Long.toString(b());
    }

    @Override // java.lang.Number
    public long longValue() {
        return b();
    }

    @Override // java.lang.Number
    public int intValue() {
        return (int) b();
    }

    @Override // java.lang.Number
    public float floatValue() {
        return (float) b();
    }

    @Override // java.lang.Number
    public double doubleValue() {
        return b();
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeLong(b());
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.f = 0;
        this.d = null;
        this.e = objectInputStream.readLong();
    }
}
