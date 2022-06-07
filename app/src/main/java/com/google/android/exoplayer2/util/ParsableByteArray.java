package com.google.android.exoplayer2.util;

import androidx.annotation.Nullable;
import com.google.common.base.Charsets;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;
import okio.Utf8;

/* loaded from: classes2.dex */
public final class ParsableByteArray {
    private byte[] a;
    private int b;
    private int c;

    public ParsableByteArray() {
        this.a = Util.EMPTY_BYTE_ARRAY;
    }

    public ParsableByteArray(int i) {
        this.a = new byte[i];
        this.c = i;
    }

    public ParsableByteArray(byte[] bArr) {
        this.a = bArr;
        this.c = bArr.length;
    }

    public ParsableByteArray(byte[] bArr, int i) {
        this.a = bArr;
        this.c = i;
    }

    public void reset(int i) {
        reset(capacity() < i ? new byte[i] : this.a, i);
    }

    public void reset(byte[] bArr) {
        reset(bArr, bArr.length);
    }

    public void reset(byte[] bArr, int i) {
        this.a = bArr;
        this.c = i;
        this.b = 0;
    }

    public void ensureCapacity(int i) {
        if (i > capacity()) {
            this.a = Arrays.copyOf(this.a, i);
        }
    }

    public int bytesLeft() {
        return this.c - this.b;
    }

    public int limit() {
        return this.c;
    }

    public void setLimit(int i) {
        Assertions.checkArgument(i >= 0 && i <= this.a.length);
        this.c = i;
    }

    public int getPosition() {
        return this.b;
    }

    public void setPosition(int i) {
        Assertions.checkArgument(i >= 0 && i <= this.c);
        this.b = i;
    }

    public byte[] getData() {
        return this.a;
    }

    public int capacity() {
        return this.a.length;
    }

    public void skipBytes(int i) {
        setPosition(this.b + i);
    }

    public void readBytes(ParsableBitArray parsableBitArray, int i) {
        readBytes(parsableBitArray.data, 0, i);
        parsableBitArray.setPosition(0);
    }

    public void readBytes(byte[] bArr, int i, int i2) {
        System.arraycopy(this.a, this.b, bArr, i, i2);
        this.b += i2;
    }

    public void readBytes(ByteBuffer byteBuffer, int i) {
        byteBuffer.put(this.a, this.b, i);
        this.b += i;
    }

    public int peekUnsignedByte() {
        return this.a[this.b] & 255;
    }

    public char peekChar() {
        byte[] bArr = this.a;
        int i = this.b;
        return (char) ((bArr[i + 1] & 255) | ((bArr[i] & 255) << 8));
    }

    public int readUnsignedByte() {
        byte[] bArr = this.a;
        int i = this.b;
        this.b = i + 1;
        return bArr[i] & 255;
    }

    public int readUnsignedShort() {
        byte[] bArr = this.a;
        int i = this.b;
        this.b = i + 1;
        int i2 = this.b;
        this.b = i2 + 1;
        return (bArr[i2] & 255) | ((bArr[i] & 255) << 8);
    }

    public int readLittleEndianUnsignedShort() {
        byte[] bArr = this.a;
        int i = this.b;
        this.b = i + 1;
        int i2 = this.b;
        this.b = i2 + 1;
        return ((bArr[i2] & 255) << 8) | (bArr[i] & 255);
    }

    public short readShort() {
        byte[] bArr = this.a;
        int i = this.b;
        this.b = i + 1;
        int i2 = this.b;
        this.b = i2 + 1;
        return (short) ((bArr[i2] & 255) | ((bArr[i] & 255) << 8));
    }

    public short readLittleEndianShort() {
        byte[] bArr = this.a;
        int i = this.b;
        this.b = i + 1;
        int i2 = this.b;
        this.b = i2 + 1;
        return (short) (((bArr[i2] & 255) << 8) | (bArr[i] & 255));
    }

    public int readUnsignedInt24() {
        byte[] bArr = this.a;
        int i = this.b;
        this.b = i + 1;
        int i2 = this.b;
        this.b = i2 + 1;
        int i3 = ((bArr[i] & 255) << 16) | ((bArr[i2] & 255) << 8);
        int i4 = this.b;
        this.b = i4 + 1;
        return (bArr[i4] & 255) | i3;
    }

    public int readInt24() {
        byte[] bArr = this.a;
        int i = this.b;
        this.b = i + 1;
        int i2 = this.b;
        this.b = i2 + 1;
        int i3 = (((bArr[i] & 255) << 24) >> 8) | ((bArr[i2] & 255) << 8);
        int i4 = this.b;
        this.b = i4 + 1;
        return (bArr[i4] & 255) | i3;
    }

    public int readLittleEndianInt24() {
        byte[] bArr = this.a;
        int i = this.b;
        this.b = i + 1;
        int i2 = this.b;
        this.b = i2 + 1;
        int i3 = (bArr[i] & 255) | ((bArr[i2] & 255) << 8);
        int i4 = this.b;
        this.b = i4 + 1;
        return ((bArr[i4] & 255) << 16) | i3;
    }

    public int readLittleEndianUnsignedInt24() {
        byte[] bArr = this.a;
        int i = this.b;
        this.b = i + 1;
        int i2 = this.b;
        this.b = i2 + 1;
        int i3 = (bArr[i] & 255) | ((bArr[i2] & 255) << 8);
        int i4 = this.b;
        this.b = i4 + 1;
        return ((bArr[i4] & 255) << 16) | i3;
    }

    public long readUnsignedInt() {
        byte[] bArr = this.a;
        int i = this.b;
        this.b = i + 1;
        int i2 = this.b;
        this.b = i2 + 1;
        long j = ((bArr[i] & 255) << 24) | ((bArr[i2] & 255) << 16);
        int i3 = this.b;
        this.b = i3 + 1;
        long j2 = j | ((bArr[i3] & 255) << 8);
        int i4 = this.b;
        this.b = i4 + 1;
        return j2 | (255 & bArr[i4]);
    }

    public long readLittleEndianUnsignedInt() {
        byte[] bArr = this.a;
        int i = this.b;
        this.b = i + 1;
        int i2 = this.b;
        this.b = i2 + 1;
        long j = (bArr[i] & 255) | ((bArr[i2] & 255) << 8);
        int i3 = this.b;
        this.b = i3 + 1;
        long j2 = j | ((bArr[i3] & 255) << 16);
        int i4 = this.b;
        this.b = i4 + 1;
        return j2 | ((255 & bArr[i4]) << 24);
    }

    public int readInt() {
        byte[] bArr = this.a;
        int i = this.b;
        this.b = i + 1;
        int i2 = this.b;
        this.b = i2 + 1;
        int i3 = ((bArr[i] & 255) << 24) | ((bArr[i2] & 255) << 16);
        int i4 = this.b;
        this.b = i4 + 1;
        int i5 = i3 | ((bArr[i4] & 255) << 8);
        int i6 = this.b;
        this.b = i6 + 1;
        return (bArr[i6] & 255) | i5;
    }

    public int readLittleEndianInt() {
        byte[] bArr = this.a;
        int i = this.b;
        this.b = i + 1;
        int i2 = this.b;
        this.b = i2 + 1;
        int i3 = (bArr[i] & 255) | ((bArr[i2] & 255) << 8);
        int i4 = this.b;
        this.b = i4 + 1;
        int i5 = i3 | ((bArr[i4] & 255) << 16);
        int i6 = this.b;
        this.b = i6 + 1;
        return ((bArr[i6] & 255) << 24) | i5;
    }

    public long readLong() {
        byte[] bArr = this.a;
        int i = this.b;
        this.b = i + 1;
        int i2 = this.b;
        this.b = i2 + 1;
        long j = ((bArr[i] & 255) << 56) | ((bArr[i2] & 255) << 48);
        int i3 = this.b;
        this.b = i3 + 1;
        long j2 = j | ((bArr[i3] & 255) << 40);
        int i4 = this.b;
        this.b = i4 + 1;
        long j3 = j2 | ((bArr[i4] & 255) << 32);
        int i5 = this.b;
        this.b = i5 + 1;
        long j4 = j3 | ((bArr[i5] & 255) << 24);
        int i6 = this.b;
        this.b = i6 + 1;
        long j5 = j4 | ((bArr[i6] & 255) << 16);
        int i7 = this.b;
        this.b = i7 + 1;
        long j6 = j5 | ((bArr[i7] & 255) << 8);
        int i8 = this.b;
        this.b = i8 + 1;
        return j6 | (255 & bArr[i8]);
    }

    public long readLittleEndianLong() {
        byte[] bArr = this.a;
        int i = this.b;
        this.b = i + 1;
        int i2 = this.b;
        this.b = i2 + 1;
        long j = (bArr[i] & 255) | ((bArr[i2] & 255) << 8);
        int i3 = this.b;
        this.b = i3 + 1;
        long j2 = j | ((bArr[i3] & 255) << 16);
        int i4 = this.b;
        this.b = i4 + 1;
        long j3 = j2 | ((bArr[i4] & 255) << 24);
        int i5 = this.b;
        this.b = i5 + 1;
        long j4 = j3 | ((bArr[i5] & 255) << 32);
        int i6 = this.b;
        this.b = i6 + 1;
        long j5 = j4 | ((bArr[i6] & 255) << 40);
        int i7 = this.b;
        this.b = i7 + 1;
        long j6 = j5 | ((bArr[i7] & 255) << 48);
        int i8 = this.b;
        this.b = i8 + 1;
        return j6 | ((255 & bArr[i8]) << 56);
    }

    public int readUnsignedFixedPoint1616() {
        byte[] bArr = this.a;
        int i = this.b;
        this.b = i + 1;
        int i2 = this.b;
        this.b = i2 + 1;
        int i3 = (bArr[i2] & 255) | ((bArr[i] & 255) << 8);
        this.b += 2;
        return i3;
    }

    public int readSynchSafeInt() {
        return (readUnsignedByte() << 21) | (readUnsignedByte() << 14) | (readUnsignedByte() << 7) | readUnsignedByte();
    }

    public int readUnsignedIntToInt() {
        int readInt = readInt();
        if (readInt >= 0) {
            return readInt;
        }
        StringBuilder sb = new StringBuilder(29);
        sb.append("Top bit not zero: ");
        sb.append(readInt);
        throw new IllegalStateException(sb.toString());
    }

    public int readLittleEndianUnsignedIntToInt() {
        int readLittleEndianInt = readLittleEndianInt();
        if (readLittleEndianInt >= 0) {
            return readLittleEndianInt;
        }
        StringBuilder sb = new StringBuilder(29);
        sb.append("Top bit not zero: ");
        sb.append(readLittleEndianInt);
        throw new IllegalStateException(sb.toString());
    }

    public long readUnsignedLongToLong() {
        long readLong = readLong();
        if (readLong >= 0) {
            return readLong;
        }
        StringBuilder sb = new StringBuilder(38);
        sb.append("Top bit not zero: ");
        sb.append(readLong);
        throw new IllegalStateException(sb.toString());
    }

    public float readFloat() {
        return Float.intBitsToFloat(readInt());
    }

    public double readDouble() {
        return Double.longBitsToDouble(readLong());
    }

    public String readString(int i) {
        return readString(i, Charsets.UTF_8);
    }

    public String readString(int i, Charset charset) {
        String str = new String(this.a, this.b, i, charset);
        this.b += i;
        return str;
    }

    public String readNullTerminatedString(int i) {
        if (i == 0) {
            return "";
        }
        int i2 = (this.b + i) - 1;
        String fromUtf8Bytes = Util.fromUtf8Bytes(this.a, this.b, (i2 >= this.c || this.a[i2] != 0) ? i : i - 1);
        this.b += i;
        return fromUtf8Bytes;
    }

    @Nullable
    public String readNullTerminatedString() {
        return readDelimiterTerminatedString((char) 0);
    }

    @Nullable
    public String readDelimiterTerminatedString(char c) {
        if (bytesLeft() == 0) {
            return null;
        }
        int i = this.b;
        while (i < this.c && this.a[i] != c) {
            i++;
        }
        byte[] bArr = this.a;
        int i2 = this.b;
        String fromUtf8Bytes = Util.fromUtf8Bytes(bArr, i2, i - i2);
        this.b = i;
        int i3 = this.b;
        if (i3 < this.c) {
            this.b = i3 + 1;
        }
        return fromUtf8Bytes;
    }

    @Nullable
    public String readLine() {
        if (bytesLeft() == 0) {
            return null;
        }
        int i = this.b;
        while (i < this.c && !Util.isLinebreak(this.a[i])) {
            i++;
        }
        int i2 = this.b;
        if (i - i2 >= 3) {
            byte[] bArr = this.a;
            if (bArr[i2] == -17 && bArr[i2 + 1] == -69 && bArr[i2 + 2] == -65) {
                this.b = i2 + 3;
            }
        }
        byte[] bArr2 = this.a;
        int i3 = this.b;
        String fromUtf8Bytes = Util.fromUtf8Bytes(bArr2, i3, i - i3);
        this.b = i;
        int i4 = this.b;
        int i5 = this.c;
        if (i4 == i5) {
            return fromUtf8Bytes;
        }
        if (this.a[i4] == 13) {
            this.b = i4 + 1;
            if (this.b == i5) {
                return fromUtf8Bytes;
            }
        }
        byte[] bArr3 = this.a;
        int i6 = this.b;
        if (bArr3[i6] == 10) {
            this.b = i6 + 1;
        }
        return fromUtf8Bytes;
    }

    public long readUtf8EncodedLong() {
        int i;
        int i2;
        long j = this.a[this.b];
        int i3 = 7;
        while (true) {
            if (i3 < 0) {
                break;
            }
            int i4 = 1 << i3;
            if ((i4 & j) != 0) {
                i3--;
            } else if (i3 < 6) {
                j &= i4 - 1;
                i2 = 7 - i3;
            } else if (i3 == 7) {
                i2 = 1;
            }
        }
        i2 = 0;
        if (i2 != 0) {
            for (i = 1; i < i2; i++) {
                byte b = this.a[this.b + i];
                if ((b & 192) == 128) {
                    j = (j << 6) | (b & Utf8.REPLACEMENT_BYTE);
                } else {
                    StringBuilder sb = new StringBuilder(62);
                    sb.append("Invalid UTF-8 sequence continuation byte: ");
                    sb.append(j);
                    throw new NumberFormatException(sb.toString());
                }
            }
            this.b += i2;
            return j;
        }
        StringBuilder sb2 = new StringBuilder(55);
        sb2.append("Invalid UTF-8 sequence first byte: ");
        sb2.append(j);
        throw new NumberFormatException(sb2.toString());
    }
}
