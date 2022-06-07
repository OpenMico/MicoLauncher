package com.alibaba.fastjson.asm;

/* loaded from: classes.dex */
public class ByteVector {
    public byte[] data;
    public int length;

    public ByteVector() {
        this.data = new byte[64];
    }

    public ByteVector(int i) {
        this.data = new byte[i];
    }

    public ByteVector putByte(int i) {
        int i2 = this.length;
        int i3 = i2 + 1;
        if (i3 > this.data.length) {
            enlarge(1);
        }
        this.data[i2] = (byte) i;
        this.length = i3;
        return this;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public ByteVector put11(int i, int i2) {
        int i3 = this.length;
        if (i3 + 2 > this.data.length) {
            enlarge(2);
        }
        byte[] bArr = this.data;
        int i4 = i3 + 1;
        bArr[i3] = (byte) i;
        bArr[i4] = (byte) i2;
        this.length = i4 + 1;
        return this;
    }

    public ByteVector putShort(int i) {
        int i2 = this.length;
        if (i2 + 2 > this.data.length) {
            enlarge(2);
        }
        byte[] bArr = this.data;
        int i3 = i2 + 1;
        bArr[i2] = (byte) (i >>> 8);
        bArr[i3] = (byte) i;
        this.length = i3 + 1;
        return this;
    }

    public ByteVector put12(int i, int i2) {
        int i3 = this.length;
        if (i3 + 3 > this.data.length) {
            enlarge(3);
        }
        byte[] bArr = this.data;
        int i4 = i3 + 1;
        bArr[i3] = (byte) i;
        int i5 = i4 + 1;
        bArr[i4] = (byte) (i2 >>> 8);
        bArr[i5] = (byte) i2;
        this.length = i5 + 1;
        return this;
    }

    public ByteVector putInt(int i) {
        int i2 = this.length;
        if (i2 + 4 > this.data.length) {
            enlarge(4);
        }
        byte[] bArr = this.data;
        int i3 = i2 + 1;
        bArr[i2] = (byte) (i >>> 24);
        int i4 = i3 + 1;
        bArr[i3] = (byte) (i >>> 16);
        int i5 = i4 + 1;
        bArr[i4] = (byte) (i >>> 8);
        bArr[i5] = (byte) i;
        this.length = i5 + 1;
        return this;
    }

    public ByteVector putUTF8(String str) {
        int length = str.length();
        int i = this.length;
        if (i + 2 + length > this.data.length) {
            enlarge(length + 2);
        }
        byte[] bArr = this.data;
        int i2 = i + 1;
        bArr[i] = (byte) (length >>> 8);
        int i3 = i2 + 1;
        bArr[i2] = (byte) length;
        for (int i4 = 0; i4 < length; i4++) {
            char charAt = str.charAt(i4);
            if (charAt < 1 || charAt > 127) {
                throw new UnsupportedOperationException();
            }
            i3++;
            bArr[i3] = (byte) charAt;
        }
        this.length = i3;
        return this;
    }

    public ByteVector putByteArray(byte[] bArr, int i, int i2) {
        if (this.length + i2 > this.data.length) {
            enlarge(i2);
        }
        if (bArr != null) {
            System.arraycopy(bArr, i, this.data, this.length, i2);
        }
        this.length += i2;
        return this;
    }

    private void enlarge(int i) {
        int length = this.data.length * 2;
        int i2 = i + this.length;
        if (length > i2) {
            i2 = length;
        }
        byte[] bArr = new byte[i2];
        System.arraycopy(this.data, 0, bArr, 0, this.length);
        this.data = bArr;
    }
}
