package com.xiaomi.account.utils;

import com.fasterxml.jackson.core.JsonPointer;

/* loaded from: classes2.dex */
public class Base64Coder {
    private static byte[] map2;
    private static final String systemLineSeparator = System.getProperty("line.separator");
    private static char[] map1 = new char[64];

    static {
        int i = 0;
        for (char c = 'A'; c <= 'Z'; c = (char) (c + 1)) {
            i++;
            map1[i] = c;
        }
        for (char c2 = 'a'; c2 <= 'z'; c2 = (char) (c2 + 1)) {
            i++;
            map1[i] = c2;
        }
        for (char c3 = '0'; c3 <= '9'; c3 = (char) (c3 + 1)) {
            i++;
            map1[i] = c3;
        }
        char[] cArr = map1;
        cArr[i] = '+';
        cArr[i + 1] = JsonPointer.SEPARATOR;
        map2 = new byte[128];
        int i2 = 0;
        while (true) {
            byte[] bArr = map2;
            if (i2 < bArr.length) {
                bArr[i2] = -1;
                i2++;
            }
        }
        for (int i3 = 0; i3 < 64; i3++) {
            map2[map1[i3]] = (byte) i3;
        }
    }

    public static String encodeString(String str) {
        return new String(encode(str.getBytes()));
    }

    public static String encodeLines(byte[] bArr) {
        return encodeLines(bArr, 0, bArr.length, 76, systemLineSeparator);
    }

    public static String encodeLines(byte[] bArr, int i, int i2, int i3, String str) {
        int i4 = (i3 * 3) / 4;
        if (i4 > 0) {
            StringBuilder sb = new StringBuilder((((i2 + 2) / 3) * 4) + ((((i2 + i4) - 1) / i4) * str.length()));
            int i5 = 0;
            while (i5 < i2) {
                int min = Math.min(i2 - i5, i4);
                sb.append(encode(bArr, i + i5, min));
                sb.append(str);
                i5 += min;
            }
            return sb.toString();
        }
        throw new IllegalArgumentException();
    }

    public static char[] encode(byte[] bArr) {
        return encode(bArr, 0, bArr.length);
    }

    public static char[] encode(byte[] bArr, int i) {
        return encode(bArr, 0, i);
    }

    public static char[] encode(byte[] bArr, int i, int i2) {
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
            char[] cArr2 = map1;
            cArr[i8] = cArr2[i11];
            int i16 = i15 + 1;
            cArr[i15] = cArr2[i12];
            char c = '=';
            cArr[i16] = i16 < i6 ? cArr2[i13] : '=';
            int i17 = i16 + 1;
            if (i17 < i6) {
                c = map1[i14];
            }
            cArr[i17] = c;
            i8 = i17 + 1;
        }
        return cArr;
    }

    public static String decodeString(String str) {
        return new String(decode(str));
    }

    public static byte[] decodeLines(String str) {
        char[] cArr = new char[str.length()];
        int i = 0;
        for (int i2 = 0; i2 < str.length(); i2++) {
            char charAt = str.charAt(i2);
            if (!(charAt == ' ' || charAt == '\r' || charAt == '\n' || charAt == '\t')) {
                i++;
                cArr[i] = charAt;
            }
        }
        return decode(cArr, 0, i);
    }

    public static byte[] decode(String str) {
        return decode(str.toCharArray());
    }

    public static byte[] decode(char[] cArr) {
        return decode(cArr, 0, cArr.length);
    }

    public static byte[] decode(char[] cArr, int i, int i2) {
        char c;
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
                char c2 = cArr[i];
                int i8 = i7 + 1;
                char c3 = cArr[i7];
                char c4 = 'A';
                if (i8 < i5) {
                    i = i8 + 1;
                    c = cArr[i8];
                } else {
                    i = i8;
                    c = 'A';
                }
                if (i < i5) {
                    i++;
                    c4 = cArr[i];
                }
                if (c2 > 127 || c3 > 127 || c > 127 || c4 > 127) {
                    throw new IllegalArgumentException("Illegal character in Base64 encoded data.");
                }
                byte[] bArr2 = map2;
                byte b = bArr2[c2];
                byte b2 = bArr2[c3];
                byte b3 = bArr2[c];
                byte b4 = bArr2[c4];
                if (b < 0 || b2 < 0 || b3 < 0 || b4 < 0) {
                    throw new IllegalArgumentException("Illegal character in Base64 encoded data.");
                }
                int i9 = (b << 2) | (b2 >>> 4);
                int i10 = ((b2 & 15) << 4) | (b3 >>> 2);
                int i11 = ((b3 & 3) << 6) | b4;
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

    private Base64Coder() {
    }
}
