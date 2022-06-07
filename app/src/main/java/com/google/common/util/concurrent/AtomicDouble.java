package com.google.common.util.concurrent;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.concurrent.atomic.AtomicLong;

/* loaded from: classes2.dex */
public class AtomicDouble extends Number implements Serializable {
    private static final long serialVersionUID = 0;
    private transient AtomicLong a;

    public AtomicDouble(double d) {
        this.a = new AtomicLong(Double.doubleToRawLongBits(d));
    }

    public AtomicDouble() {
        this(0.0d);
    }

    public final double get() {
        return Double.longBitsToDouble(this.a.get());
    }

    public final void set(double d) {
        this.a.set(Double.doubleToRawLongBits(d));
    }

    public final void lazySet(double d) {
        this.a.lazySet(Double.doubleToRawLongBits(d));
    }

    public final double getAndSet(double d) {
        return Double.longBitsToDouble(this.a.getAndSet(Double.doubleToRawLongBits(d)));
    }

    public final boolean compareAndSet(double d, double d2) {
        return this.a.compareAndSet(Double.doubleToRawLongBits(d), Double.doubleToRawLongBits(d2));
    }

    public final boolean weakCompareAndSet(double d, double d2) {
        return this.a.weakCompareAndSet(Double.doubleToRawLongBits(d), Double.doubleToRawLongBits(d2));
    }

    @CanIgnoreReturnValue
    public final double getAndAdd(double d) {
        long j;
        double longBitsToDouble;
        do {
            j = this.a.get();
            longBitsToDouble = Double.longBitsToDouble(j);
        } while (!this.a.compareAndSet(j, Double.doubleToRawLongBits(longBitsToDouble + d)));
        return longBitsToDouble;
    }

    @CanIgnoreReturnValue
    public final double addAndGet(double d) {
        long j;
        double longBitsToDouble;
        do {
            j = this.a.get();
            longBitsToDouble = Double.longBitsToDouble(j) + d;
        } while (!this.a.compareAndSet(j, Double.doubleToRawLongBits(longBitsToDouble)));
        return longBitsToDouble;
    }

    public String toString() {
        return Double.toString(get());
    }

    @Override // java.lang.Number
    public int intValue() {
        return (int) get();
    }

    @Override // java.lang.Number
    public long longValue() {
        return (long) get();
    }

    @Override // java.lang.Number
    public float floatValue() {
        return (float) get();
    }

    @Override // java.lang.Number
    public double doubleValue() {
        return get();
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeDouble(get());
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.a = new AtomicLong();
        set(objectInputStream.readDouble());
    }
}
