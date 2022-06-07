package com.google.android.exoplayer2.util;

import androidx.core.view.MotionEventCompat;
import com.google.common.base.Charsets;
import java.nio.charset.Charset;

/* loaded from: classes2.dex */
public final class ParsableBitArray {
    private int a;
    private int b;
    private int c;
    public byte[] data;

    public ParsableBitArray() {
        this.data = Util.EMPTY_BYTE_ARRAY;
    }

    public ParsableBitArray(byte[] bArr) {
        this(bArr, bArr.length);
    }

    public ParsableBitArray(byte[] bArr, int i) {
        this.data = bArr;
        this.c = i;
    }

    public void reset(byte[] bArr) {
        reset(bArr, bArr.length);
    }

    public void reset(ParsableByteArray parsableByteArray) {
        reset(parsableByteArray.getData(), parsableByteArray.limit());
        setPosition(parsableByteArray.getPosition() * 8);
    }

    public void reset(byte[] bArr, int i) {
        this.data = bArr;
        this.a = 0;
        this.b = 0;
        this.c = i;
    }

    public int bitsLeft() {
        return ((this.c - this.a) * 8) - this.b;
    }

    public int getPosition() {
        return (this.a * 8) + this.b;
    }

    public int getBytePosition() {
        Assertions.checkState(this.b == 0);
        return this.a;
    }

    public void setPosition(int i) {
        this.a = i / 8;
        this.b = i - (this.a * 8);
        a();
    }

    public void skipBit() {
        int i = this.b + 1;
        this.b = i;
        if (i == 8) {
            this.b = 0;
            this.a++;
        }
        a();
    }

    public void skipBits(int i) {
        int i2 = i / 8;
        this.a += i2;
        this.b += i - (i2 * 8);
        int i3 = this.b;
        if (i3 > 7) {
            this.a++;
            this.b = i3 - 8;
        }
        a();
    }

    public boolean readBit() {
        boolean z = (this.data[this.a] & (128 >> this.b)) != 0;
        skipBit();
        return z;
    }

    public int readBits(int i) {
        int i2;
        if (i == 0) {
            return 0;
        }
        this.b += i;
        int i3 = 0;
        while (true) {
            i2 = this.b;
            if (i2 <= 8) {
                break;
            }
            this.b = i2 - 8;
            byte[] bArr = this.data;
            int i4 = this.a;
            this.a = i4 + 1;
            i3 |= (bArr[i4] & 255) << this.b;
        }
        byte[] bArr2 = this.data;
        int i5 = this.a;
        int i6 = ((-1) >>> (32 - i)) & (i3 | ((bArr2[i5] & 255) >> (8 - i2)));
        if (i2 == 8) {
            this.b = 0;
            this.a = i5 + 1;
        }
        a();
        return i6;
    }

    public long readBitsToLong(int i) {
        return i <= 32 ? Util.toUnsignedLong(readBits(i)) : Util.toLong(readBits(i - 32), readBits(32));
    }

    public void readBits(byte[] bArr, int i, int i2) {
        int i3 = (i2 >> 3) + i;
        while (i < i3) {
            byte[] bArr2 = this.data;
            int i4 = this.a;
            this.a = i4 + 1;
            byte b = bArr2[i4];
            int i5 = this.b;
            bArr[i] = (byte) (b << i5);
            bArr[i] = (byte) (((255 & bArr2[this.a]) >> (8 - i5)) | bArr[i]);
            i++;
        }
        int i6 = i2 & 7;
        if (i6 != 0) {
            bArr[i3] = (byte) (bArr[i3] & (255 >> i6));
            int i7 = this.b;
            if (i7 + i6 > 8) {
                byte b2 = bArr[i3];
                byte[] bArr3 = this.data;
                int i8 = this.a;
                this.a = i8 + 1;
                bArr[i3] = (byte) (b2 | ((bArr3[i8] & 255) << i7));
                this.b = i7 - 8;
            }
            this.b += i6;
            byte[] bArr4 = this.data;
            int i9 = this.a;
            int i10 = this.b;
            bArr[i3] = (byte) (((byte) (((bArr4[i9] & 255) >> (8 - i10)) << (8 - i6))) | bArr[i3]);
            if (i10 == 8) {
                this.b = 0;
                this.a = i9 + 1;
            }
            a();
        }
    }

    public void byteAlign() {
        if (this.b != 0) {
            this.b = 0;
            this.a++;
            a();
        }
    }

    public void readBytes(byte[] bArr, int i, int i2) {
        Assertions.checkState(this.b == 0);
        System.arraycopy(this.data, this.a, bArr, i, i2);
        this.a += i2;
        a();
    }

    public void skipBytes(int i) {
        Assertions.checkState(this.b == 0);
        this.a += i;
        a();
    }

    public String readBytesAsString(int i) {
        return readBytesAsString(i, Charsets.UTF_8);
    }

    public String readBytesAsString(int i, Charset charset) {
        byte[] bArr = new byte[i];
        readBytes(bArr, 0, i);
        return new String(bArr, charset);
    }

    public void putInt(int i, int i2) {
        if (i2 < 32) {
            i &= (1 << i2) - 1;
        }
        int min = Math.min(8 - this.b, i2);
        int i3 = this.b;
        int i4 = (8 - i3) - min;
        int i5 = (MotionEventCompat.ACTION_POINTER_INDEX_MASK >> i3) | ((1 << i4) - 1);
        byte[] bArr = this.data;
        int i6 = this.a;
        bArr[i6] = (byte) (i5 & bArr[i6]);
        int i7 = i2 - min;
        bArr[i6] = (byte) (((i >>> i7) << i4) | bArr[i6]);
        int i8 = i6 + 1;
        while (i7 > 8) {
            i8++;
            this.data[i8] = (byte) (i >>> (i7 - 8));
            i7 -= 8;
        }
        int i9 = 8 - i7;
        byte[] bArr2 = this.data;
        bArr2[i8] = (byte) (bArr2[i8] & ((1 << i9) - 1));
        bArr2[i8] = (byte) (((i & ((1 << i7) - 1)) << i9) | bArr2[i8]);
        skipBits(i2);
        a();
    }

    private void a() {
        int i;
        int i2 = this.a;
        Assertions.checkState(i2 >= 0 && (i2 < (i = this.c) || (i2 == i && this.b == 0)));
    }
}
