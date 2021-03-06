package com.google.android.exoplayer2.util;

/* loaded from: classes2.dex */
public final class ParsableNalUnitBitArray {
    private byte[] a;
    private int b;
    private int c;
    private int d;

    public ParsableNalUnitBitArray(byte[] bArr, int i, int i2) {
        reset(bArr, i, i2);
    }

    public void reset(byte[] bArr, int i, int i2) {
        this.a = bArr;
        this.c = i;
        this.b = i2;
        this.d = 0;
        b();
    }

    public void skipBit() {
        int i = 1;
        int i2 = this.d + 1;
        this.d = i2;
        if (i2 == 8) {
            this.d = 0;
            int i3 = this.c;
            if (a(i3 + 1)) {
                i = 2;
            }
            this.c = i3 + i;
        }
        b();
    }

    public void skipBits(int i) {
        int i2 = this.c;
        int i3 = i / 8;
        this.c = i2 + i3;
        this.d += i - (i3 * 8);
        int i4 = this.d;
        if (i4 > 7) {
            this.c++;
            this.d = i4 - 8;
        }
        while (true) {
            i2++;
            if (i2 > this.c) {
                b();
                return;
            } else if (a(i2)) {
                this.c++;
                i2 += 2;
            }
        }
    }

    public boolean canReadBits(int i) {
        int i2 = this.c;
        int i3 = i / 8;
        int i4 = i2 + i3;
        int i5 = (this.d + i) - (i3 * 8);
        if (i5 > 7) {
            i4++;
            i5 -= 8;
        }
        while (true) {
            i2++;
            if (i2 > i4 || i4 >= this.b) {
                break;
            } else if (a(i2)) {
                i4++;
                i2 += 2;
            }
        }
        int i6 = this.b;
        if (i4 >= i6) {
            return i4 == i6 && i5 == 0;
        }
        return true;
    }

    public boolean readBit() {
        boolean z = (this.a[this.c] & (128 >> this.d)) != 0;
        skipBit();
        return z;
    }

    public int readBits(int i) {
        int i2;
        int i3;
        this.d += i;
        int i4 = 0;
        while (true) {
            i2 = this.d;
            i3 = 2;
            if (i2 <= 8) {
                break;
            }
            this.d = i2 - 8;
            byte[] bArr = this.a;
            int i5 = this.c;
            i4 |= (bArr[i5] & 255) << this.d;
            if (!a(i5 + 1)) {
                i3 = 1;
            }
            this.c = i5 + i3;
        }
        byte[] bArr2 = this.a;
        int i6 = this.c;
        int i7 = ((-1) >>> (32 - i)) & (i4 | ((bArr2[i6] & 255) >> (8 - i2)));
        if (i2 == 8) {
            this.d = 0;
            if (!a(i6 + 1)) {
                i3 = 1;
            }
            this.c = i6 + i3;
        }
        b();
        return i7;
    }

    public boolean canReadExpGolombCodedNum() {
        int i = this.c;
        int i2 = this.d;
        int i3 = 0;
        while (this.c < this.b && !readBit()) {
            i3++;
        }
        boolean z = this.c == this.b;
        this.c = i;
        this.d = i2;
        return !z && canReadBits((i3 * 2) + 1);
    }

    public int readUnsignedExpGolombCodedInt() {
        return a();
    }

    public int readSignedExpGolombCodedInt() {
        int a = a();
        return (a % 2 == 0 ? -1 : 1) * ((a + 1) / 2);
    }

    private int a() {
        int i = 0;
        int i2 = 0;
        while (!readBit()) {
            i2++;
        }
        int i3 = (1 << i2) - 1;
        if (i2 > 0) {
            i = readBits(i2);
        }
        return i3 + i;
    }

    private boolean a(int i) {
        if (2 <= i && i < this.b) {
            byte[] bArr = this.a;
            if (bArr[i] == 3 && bArr[i - 2] == 0 && bArr[i - 1] == 0) {
                return true;
            }
        }
        return false;
    }

    private void b() {
        int i;
        int i2 = this.c;
        Assertions.checkState(i2 >= 0 && (i2 < (i = this.b) || (i2 == i && this.d == 0)));
    }
}
