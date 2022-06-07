package io.netty.util.internal.chmv8;

import io.netty.util.internal.LongCounter;
import io.netty.util.internal.chmv8.a;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/* loaded from: classes4.dex */
public class LongAdderV8 extends a implements LongCounter, Serializable {
    private static final long serialVersionUID = 7249069246863182397L;

    @Override // io.netty.util.internal.chmv8.a
    final long a(long j, long j2) {
        return j + j2;
    }

    @Override // io.netty.util.internal.LongCounter
    public void add(long j) {
        int length;
        a.C0210a aVar;
        a.C0210a[] aVarArr = this.d;
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

    @Override // io.netty.util.internal.LongCounter
    public void increment() {
        add(1L);
    }

    @Override // io.netty.util.internal.LongCounter
    public void decrement() {
        add(-1L);
    }

    public long sum() {
        long j = this.e;
        a.C0210a[] aVarArr = this.d;
        if (aVarArr != null) {
            for (a.C0210a aVar : aVarArr) {
                if (aVar != null) {
                    j += aVar.a;
                }
            }
        }
        return j;
    }

    public void reset() {
        a(0L);
    }

    public long sumThenReset() {
        long j = this.e;
        a.C0210a[] aVarArr = this.d;
        this.e = 0L;
        if (aVarArr != null) {
            for (a.C0210a aVar : aVarArr) {
                if (aVar != null) {
                    j += aVar.a;
                    aVar.a = 0L;
                }
            }
        }
        return j;
    }

    public String toString() {
        return Long.toString(sum());
    }

    @Override // java.lang.Number
    public long longValue() {
        return sum();
    }

    @Override // java.lang.Number
    public int intValue() {
        return (int) sum();
    }

    @Override // java.lang.Number
    public float floatValue() {
        return (float) sum();
    }

    @Override // java.lang.Number
    public double doubleValue() {
        return sum();
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeLong(sum());
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.f = 0;
        this.d = null;
        this.e = objectInputStream.readLong();
    }

    @Override // io.netty.util.internal.LongCounter
    public long value() {
        return sum();
    }
}
