package com.google.zxing.common;

/* loaded from: classes2.dex */
public final class BitSource {
    private final byte[] a;
    private int b;
    private int c;

    public BitSource(byte[] bArr) {
        this.a = bArr;
    }

    public int getBitOffset() {
        return this.c;
    }

    public int getByteOffset() {
        return this.b;
    }

    public int readBits(int i) {
        int i2;
        if (i <= 0 || i > 32 || i > available()) {
            throw new IllegalArgumentException(String.valueOf(i));
        }
        int i3 = this.c;
        if (i3 > 0) {
            int i4 = 8 - i3;
            int i5 = i < i4 ? i : i4;
            int i6 = i4 - i5;
            byte[] bArr = this.a;
            int i7 = this.b;
            i2 = (((255 >> (8 - i5)) << i6) & bArr[i7]) >> i6;
            i -= i5;
            this.c += i5;
            if (this.c == 8) {
                this.c = 0;
                this.b = i7 + 1;
            }
        } else {
            i2 = 0;
        }
        if (i <= 0) {
            return i2;
        }
        while (i >= 8) {
            byte[] bArr2 = this.a;
            int i8 = this.b;
            i2 = (i2 << 8) | (bArr2[i8] & 255);
            this.b = i8 + 1;
            i -= 8;
        }
        if (i <= 0) {
            return i2;
        }
        int i9 = 8 - i;
        int i10 = (i2 << i) | ((((255 >> i9) << i9) & this.a[this.b]) >> i9);
        this.c += i;
        return i10;
    }

    public int available() {
        return ((this.a.length - this.b) * 8) - this.c;
    }
}
