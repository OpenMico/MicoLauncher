package org.apache.commons.lang3.mutable;

import org.apache.commons.lang3.math.NumberUtils;

/* loaded from: classes5.dex */
public class MutableInt extends Number implements Comparable<MutableInt>, Mutable<Number> {
    private static final long serialVersionUID = 512176391864L;
    private int value;

    public MutableInt() {
    }

    public MutableInt(int i) {
        this.value = i;
    }

    public MutableInt(Number number) {
        this.value = number.intValue();
    }

    public MutableInt(String str) throws NumberFormatException {
        this.value = Integer.parseInt(str);
    }

    @Override // org.apache.commons.lang3.mutable.Mutable
    /* renamed from: getValue */
    public Number getValue2() {
        return Integer.valueOf(this.value);
    }

    public void setValue(int i) {
        this.value = i;
    }

    public void setValue(Number number) {
        this.value = number.intValue();
    }

    public void increment() {
        this.value++;
    }

    public int getAndIncrement() {
        int i = this.value;
        this.value = i + 1;
        return i;
    }

    public int incrementAndGet() {
        this.value++;
        return this.value;
    }

    public void decrement() {
        this.value--;
    }

    public int getAndDecrement() {
        int i = this.value;
        this.value = i - 1;
        return i;
    }

    public int decrementAndGet() {
        this.value--;
        return this.value;
    }

    public void add(int i) {
        this.value += i;
    }

    public void add(Number number) {
        this.value += number.intValue();
    }

    public void subtract(int i) {
        this.value -= i;
    }

    public void subtract(Number number) {
        this.value -= number.intValue();
    }

    public int addAndGet(int i) {
        this.value += i;
        return this.value;
    }

    public int addAndGet(Number number) {
        this.value += number.intValue();
        return this.value;
    }

    public int getAndAdd(int i) {
        int i2 = this.value;
        this.value = i + i2;
        return i2;
    }

    public int getAndAdd(Number number) {
        int i = this.value;
        this.value = number.intValue() + i;
        return i;
    }

    @Override // java.lang.Number
    public int intValue() {
        return this.value;
    }

    @Override // java.lang.Number
    public long longValue() {
        return this.value;
    }

    @Override // java.lang.Number
    public float floatValue() {
        return this.value;
    }

    @Override // java.lang.Number
    public double doubleValue() {
        return this.value;
    }

    public Integer toInteger() {
        return Integer.valueOf(intValue());
    }

    public boolean equals(Object obj) {
        return (obj instanceof MutableInt) && this.value == ((MutableInt) obj).intValue();
    }

    public int hashCode() {
        return this.value;
    }

    public int compareTo(MutableInt mutableInt) {
        return NumberUtils.compare(this.value, mutableInt.value);
    }

    public String toString() {
        return String.valueOf(this.value);
    }
}
