package org.apache.commons.lang.mutable;

/* loaded from: classes5.dex */
public class MutableByte extends Number implements Comparable, Mutable {
    private static final long serialVersionUID = -1585823265;
    private byte value;

    public MutableByte() {
    }

    public MutableByte(byte b) {
        this.value = b;
    }

    public MutableByte(Number number) {
        this.value = number.byteValue();
    }

    public MutableByte(String str) throws NumberFormatException {
        this.value = Byte.parseByte(str);
    }

    @Override // org.apache.commons.lang.mutable.Mutable
    public Object getValue() {
        return new Byte(this.value);
    }

    public void setValue(byte b) {
        this.value = b;
    }

    @Override // org.apache.commons.lang.mutable.Mutable
    public void setValue(Object obj) {
        setValue(((Number) obj).byteValue());
    }

    public void increment() {
        this.value = (byte) (this.value + 1);
    }

    public void decrement() {
        this.value = (byte) (this.value - 1);
    }

    public void add(byte b) {
        this.value = (byte) (this.value + b);
    }

    public void add(Number number) {
        this.value = (byte) (this.value + number.byteValue());
    }

    public void subtract(byte b) {
        this.value = (byte) (this.value - b);
    }

    public void subtract(Number number) {
        this.value = (byte) (this.value - number.byteValue());
    }

    @Override // java.lang.Number
    public byte byteValue() {
        return this.value;
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

    public Byte toByte() {
        return new Byte(byteValue());
    }

    public boolean equals(Object obj) {
        return (obj instanceof MutableByte) && this.value == ((MutableByte) obj).byteValue();
    }

    public int hashCode() {
        return this.value;
    }

    @Override // java.lang.Comparable
    public int compareTo(Object obj) {
        byte b = ((MutableByte) obj).value;
        byte b2 = this.value;
        if (b2 < b) {
            return -1;
        }
        return b2 == b ? 0 : 1;
    }

    public String toString() {
        return String.valueOf((int) this.value);
    }
}
