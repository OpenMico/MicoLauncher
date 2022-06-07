package com.google.android.exoplayer2.extractor;

import com.google.android.exoplayer2.util.Assertions;

/* loaded from: classes2.dex */
public final class VorbisBitArray {
    private final byte[] a;
    private final int b;
    private int c;
    private int d;

    public VorbisBitArray(byte[] bArr) {
        this.a = bArr;
        this.b = bArr.length;
    }

    public void reset() {
        this.c = 0;
        this.d = 0;
    }

    public boolean readBit() {
        boolean z = (((this.a[this.c] & 255) >> this.d) & 1) == 1;
        skipBits(1);
        return z;
    }

    public int readBits(int i) {
        int i2 = this.c;
        int min = Math.min(i, 8 - this.d);
        int i3 = i2 + 1;
        int i4 = ((this.a[i2] & 255) >> this.d) & (255 >> (8 - min));
        while (min < i) {
            i3++;
            i4 |= (this.a[i3] & 255) << min;
            min += 8;
        }
        int i5 = i4 & ((-1) >>> (32 - i));
        skipBits(i);
        return i5;
    }

    public void skipBits(int i) {
        int i2 = i / 8;
        this.c += i2;
        this.d += i - (i2 * 8);
        int i3 = this.d;
        if (i3 > 7) {
            this.c++;
            this.d = i3 - 8;
        }
        a();
    }

    public int getPosition() {
        return (this.c * 8) + this.d;
    }

    public void setPosition(int i) {
        this.c = i / 8;
        this.d = i - (this.c * 8);
        a();
    }

    public int bitsLeft() {
        return ((this.b - this.c) * 8) - this.d;
    }

    private void a() {
        int i;
        int i2 = this.c;
        Assertions.checkState(i2 >= 0 && (i2 < (i = this.b) || (i2 == i && this.d == 0)));
    }
}
