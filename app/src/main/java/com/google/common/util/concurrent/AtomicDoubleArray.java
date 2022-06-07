package com.google.common.util.concurrent;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.primitives.ImmutableLongArray;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import io.netty.util.internal.StringUtil;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.concurrent.atomic.AtomicLongArray;
import okhttp3.HttpUrl;

@GwtIncompatible
/* loaded from: classes2.dex */
public class AtomicDoubleArray implements Serializable {
    private static final long serialVersionUID = 0;
    private transient AtomicLongArray a;

    public AtomicDoubleArray(int i) {
        this.a = new AtomicLongArray(i);
    }

    public AtomicDoubleArray(double[] dArr) {
        int length = dArr.length;
        long[] jArr = new long[length];
        for (int i = 0; i < length; i++) {
            jArr[i] = Double.doubleToRawLongBits(dArr[i]);
        }
        this.a = new AtomicLongArray(jArr);
    }

    public final int length() {
        return this.a.length();
    }

    public final double get(int i) {
        return Double.longBitsToDouble(this.a.get(i));
    }

    public final void set(int i, double d) {
        this.a.set(i, Double.doubleToRawLongBits(d));
    }

    public final void lazySet(int i, double d) {
        this.a.lazySet(i, Double.doubleToRawLongBits(d));
    }

    public final double getAndSet(int i, double d) {
        return Double.longBitsToDouble(this.a.getAndSet(i, Double.doubleToRawLongBits(d)));
    }

    public final boolean compareAndSet(int i, double d, double d2) {
        return this.a.compareAndSet(i, Double.doubleToRawLongBits(d), Double.doubleToRawLongBits(d2));
    }

    public final boolean weakCompareAndSet(int i, double d, double d2) {
        return this.a.weakCompareAndSet(i, Double.doubleToRawLongBits(d), Double.doubleToRawLongBits(d2));
    }

    @CanIgnoreReturnValue
    public final double getAndAdd(int i, double d) {
        long j;
        double longBitsToDouble;
        do {
            j = this.a.get(i);
            longBitsToDouble = Double.longBitsToDouble(j);
        } while (!this.a.compareAndSet(i, j, Double.doubleToRawLongBits(longBitsToDouble + d)));
        return longBitsToDouble;
    }

    @CanIgnoreReturnValue
    public double addAndGet(int i, double d) {
        long j;
        double longBitsToDouble;
        do {
            j = this.a.get(i);
            longBitsToDouble = Double.longBitsToDouble(j) + d;
        } while (!this.a.compareAndSet(i, j, Double.doubleToRawLongBits(longBitsToDouble)));
        return longBitsToDouble;
    }

    public String toString() {
        int length = length() - 1;
        if (length == -1) {
            return HttpUrl.PATH_SEGMENT_ENCODE_SET_URI;
        }
        StringBuilder sb = new StringBuilder((length + 1) * 19);
        sb.append('[');
        int i = 0;
        while (true) {
            sb.append(Double.longBitsToDouble(this.a.get(i)));
            if (i == length) {
                sb.append(']');
                return sb.toString();
            }
            sb.append(StringUtil.COMMA);
            sb.append(' ');
            i++;
        }
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        int length = length();
        objectOutputStream.writeInt(length);
        for (int i = 0; i < length; i++) {
            objectOutputStream.writeDouble(get(i));
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        int readInt = objectInputStream.readInt();
        ImmutableLongArray.Builder builder = ImmutableLongArray.builder();
        for (int i = 0; i < readInt; i++) {
            builder.add(Double.doubleToRawLongBits(objectInputStream.readDouble()));
        }
        this.a = new AtomicLongArray(builder.build().toArray());
    }
}
