package com.xiaomi.push;

import com.fasterxml.jackson.core.JsonPointer;

/* loaded from: classes4.dex */
public class aw {
    private static final String a = System.getProperty("line.separator");
    private static char[] b = new char[64];
    private static byte[] c;

    static {
        int i = 0;
        for (char c2 = 'A'; c2 <= 'Z'; c2 = (char) (c2 + 1)) {
            i++;
            b[i] = c2;
        }
        for (char c3 = 'a'; c3 <= 'z'; c3 = (char) (c3 + 1)) {
            i++;
            b[i] = c3;
        }
        for (char c4 = '0'; c4 <= '9'; c4 = (char) (c4 + 1)) {
            i++;
            b[i] = c4;
        }
        char[] cArr = b;
        cArr[i] = '+';
        cArr[i + 1] = JsonPointer.SEPARATOR;
        c = new byte[128];
        int i2 = 0;
        while (true) {
            byte[] bArr = c;
            if (i2 < bArr.length) {
                bArr[i2] = -1;
                i2++;
            }
        }
        for (int i3 = 0; i3 < 64; i3++) {
            c[b[i3]] = (byte) i3;
        }
    }

    public static byte[] a(String str) {
        return a(str.toCharArray());
    }

    public static byte[] a(char[] cArr) {
        return a(cArr, 0, cArr.length);
    }

    public static byte[] a(char[] cArr, int i, int i2) {
        char c2;
        int i3;
        if (i2 % 4 == 0) {
            while (i2 > 0 && cArr[(i + i2) - 1] == '=') {
                i2--;
            }
            int i4 = (i2 * 3) / 4;
            byte[] bArr = new byte[i4];
            int i5 = i2 + i;
            int i6 = 0;
            while (i < i5) {
                int i7 = i + 1;
                char c3 = cArr[i];
                int i8 = i7 + 1;
                char c4 = cArr[i7];
                char c5 = 'A';
                if (i8 < i5) {
                    i = i8 + 1;
                    c2 = cArr[i8];
                } else {
                    i = i8;
                    c2 = 'A';
                }
                if (i < i5) {
                    i++;
                    c5 = cArr[i];
                }
                if (c3 > 127 || c4 > 127 || c2 > 127 || c5 > 127) {
                    throw new IllegalArgumentException("Illegal character in Base64 encoded data.");
                }
                byte[] bArr2 = c;
                byte b2 = bArr2[c3];
                byte b3 = bArr2[c4];
                byte b4 = bArr2[c2];
                byte b5 = bArr2[c5];
                if (b2 < 0 || b3 < 0 || b4 < 0 || b5 < 0) {
                    throw new IllegalArgumentException("Illegal character in Base64 encoded data.");
                }
                int i9 = (b2 << 2) | (b3 >>> 4);
                int i10 = ((b3 & 15) << 4) | (b4 >>> 2);
                int i11 = ((b4 & 3) << 6) | b5;
                int i12 = i6 + 1;
                bArr[i6] = (byte) i9;
                if (i12 < i4) {
                    i3 = i12 + 1;
                    bArr[i12] = (byte) i10;
                } else {
                    i3 = i12;
                }
                if (i3 < i4) {
                    i6 = i3 + 1;
                    bArr[i3] = (byte) i11;
                } else {
                    i6 = i3;
                }
            }
            return bArr;
        }
        throw new IllegalArgumentException("Length of Base64 encoded input string is not a multiple of 4.");
    }

    public static char[] a(byte[] bArr) {
        return a(bArr, 0, bArr.length);
    }

    public static char[] a(byte[] bArr, int i, int i2) {
        int i3;
        int i4;
        int i5;
        int i6 = ((i2 * 4) + 2) / 3;
        char[] cArr = new char[((i2 + 2) / 3) * 4];
        int i7 = i2 + i;
        int i8 = 0;
        while (i < i7) {
            int i9 = i + 1;
            int i10 = bArr[i] & 255;
            if (i9 < i7) {
                i3 = i9 + 1;
                i4 = bArr[i9] & 255;
            } else {
                i3 = i9;
                i4 = 0;
            }
            if (i3 < i7) {
                i = i3 + 1;
                i5 = bArr[i3] & 255;
            } else {
                i = i3;
                i5 = 0;
            }
            int i11 = i10 >>> 2;
            int i12 = ((i10 & 3) << 4) | (i4 >>> 4);
            int i13 = ((i4 & 15) << 2) | (i5 >>> 6);
            int i14 = i5 & 63;
            int i15 = i8 + 1;
            char[] cArr2 = b;
            cArr[i8] = cArr2[i11];
            int i16 = i15 + 1;
            cArr[i15] = cArr2[i12];
            char c2 = '=';
            cArr[i16] = i16 < i6 ? cArr2[i13] : '=';
            int i17 = i16 + 1;
            if (i17 < i6) {
                c2 = b[i14];
            }
            cArr[i17] = c2;
            i8 = i17 + 1;
        }
        return cArr;
    }
}
