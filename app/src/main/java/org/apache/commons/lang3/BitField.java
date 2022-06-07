package org.apache.commons.lang3;

/* loaded from: classes5.dex */
public class BitField {
    private final int a;
    private final int b;

    public BitField(int i) {
        this.a = i;
        this.b = i != 0 ? Integer.numberOfTrailingZeros(i) : 0;
    }

    public int getValue(int i) {
        return getRawValue(i) >> this.b;
    }

    public short getShortValue(short s) {
        return (short) getValue(s);
    }

    public int getRawValue(int i) {
        return i & this.a;
    }

    public short getShortRawValue(short s) {
        return (short) getRawValue(s);
    }

    public boolean isSet(int i) {
        return (i & this.a) != 0;
    }

    public boolean isAllSet(int i) {
        int i2 = this.a;
        return (i & i2) == i2;
    }

    public int setValue(int i, int i2) {
        int i3 = this.a;
        return (i & (~i3)) | ((i2 << this.b) & i3);
    }

    public short setShortValue(short s, short s2) {
        return (short) setValue(s, s2);
    }

    public int clear(int i) {
        return i & (~this.a);
    }

    public short clearShort(short s) {
        return (short) clear(s);
    }

    public byte clearByte(byte b) {
        return (byte) clear(b);
    }

    public int set(int i) {
        return i | this.a;
    }

    public short setShort(short s) {
        return (short) set(s);
    }

    public byte setByte(byte b) {
        return (byte) set(b);
    }

    public int setBoolean(int i, boolean z) {
        return z ? set(i) : clear(i);
    }

    public short setShortBoolean(short s, boolean z) {
        return z ? setShort(s) : clearShort(s);
    }

    public byte setByteBoolean(byte b, boolean z) {
        return z ? setByte(b) : clearByte(b);
    }
}
