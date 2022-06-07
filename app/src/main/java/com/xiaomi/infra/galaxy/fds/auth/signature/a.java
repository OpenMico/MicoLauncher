package com.xiaomi.infra.galaxy.fds.auth.signature;

import com.fasterxml.jackson.core.JsonPointer;
import com.google.android.exoplayer2.extractor.ts.PsExtractor;
import okio.Utf8;

/* compiled from: Base64Util.java */
/* loaded from: classes3.dex */
class a {
    private static final byte[] a = new byte[128];
    private static final char[] b = new char[64];

    static {
        int i;
        int i2;
        int i3 = 0;
        for (int i4 = 0; i4 < 128; i4++) {
            a[i4] = -1;
        }
        for (int i5 = 90; i5 >= 65; i5--) {
            a[i5] = (byte) (i5 - 65);
        }
        int i6 = 122;
        while (true) {
            i = 26;
            if (i6 < 97) {
                break;
            }
            a[i6] = (byte) ((i6 - 97) + 26);
            i6--;
        }
        int i7 = 57;
        while (true) {
            i2 = 52;
            if (i7 < 48) {
                break;
            }
            a[i7] = (byte) ((i7 - 48) + 52);
            i7--;
        }
        byte[] bArr = a;
        bArr[43] = 62;
        bArr[47] = Utf8.REPLACEMENT_BYTE;
        for (int i8 = 0; i8 <= 25; i8++) {
            b[i8] = (char) (i8 + 65);
        }
        int i9 = 0;
        while (i <= 51) {
            b[i] = (char) (i9 + 97);
            i++;
            i9++;
        }
        while (i2 <= 61) {
            b[i2] = (char) (i3 + 48);
            i2++;
            i3++;
        }
        char[] cArr = b;
        cArr[62] = '+';
        cArr[63] = JsonPointer.SEPARATOR;
    }

    public static String a(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        int length = bArr.length * 8;
        if (length == 0) {
            return "";
        }
        int i = length % 24;
        int i2 = length / 24;
        char[] cArr = new char[(i != 0 ? i2 + 1 : i2) * 4];
        int i3 = 0;
        int i4 = 0;
        for (int i5 = 0; i5 < i2; i5++) {
            int i6 = i3 + 1;
            byte b2 = bArr[i3];
            int i7 = i6 + 1;
            byte b3 = bArr[i6];
            i3 = i7 + 1;
            byte b4 = bArr[i7];
            byte b5 = (byte) (b3 & 15);
            byte b6 = (byte) (b2 & 3);
            byte b7 = (byte) ((b2 & Byte.MIN_VALUE) == 0 ? b2 >> 2 : (b2 >> 2) ^ 192);
            byte b8 = (byte) ((b3 & Byte.MIN_VALUE) == 0 ? b3 >> 4 : (b3 >> 4) ^ PsExtractor.VIDEO_STREAM_MASK);
            int i8 = (b4 & Byte.MIN_VALUE) == 0 ? b4 >> 6 : (b4 >> 6) ^ 252;
            int i9 = i4 + 1;
            char[] cArr2 = b;
            cArr[i4] = cArr2[b7];
            int i10 = i9 + 1;
            cArr[i9] = cArr2[(b6 << 4) | b8];
            int i11 = i10 + 1;
            cArr[i10] = cArr2[(b5 << 2) | ((byte) i8)];
            i4 = i11 + 1;
            cArr[i11] = cArr2[b4 & Utf8.REPLACEMENT_BYTE];
        }
        if (i == 8) {
            byte b9 = bArr[i3];
            byte b10 = (byte) (b9 & 3);
            int i12 = (b9 & Byte.MIN_VALUE) == 0 ? b9 >> 2 : (b9 >> 2) ^ 192;
            int i13 = i4 + 1;
            char[] cArr3 = b;
            cArr[i4] = cArr3[(byte) i12];
            int i14 = i13 + 1;
            cArr[i13] = cArr3[b10 << 4];
            cArr[i14] = '=';
            cArr[i14 + 1] = '=';
        } else if (i == 16) {
            byte b11 = bArr[i3];
            byte b12 = bArr[i3 + 1];
            byte b13 = (byte) (b12 & 15);
            byte b14 = (byte) (b11 & 3);
            byte b15 = (byte) ((b11 & Byte.MIN_VALUE) == 0 ? b11 >> 2 : (b11 >> 2) ^ 192);
            int i15 = (b12 & Byte.MIN_VALUE) == 0 ? b12 >> 4 : (b12 >> 4) ^ PsExtractor.VIDEO_STREAM_MASK;
            int i16 = i4 + 1;
            char[] cArr4 = b;
            cArr[i4] = cArr4[b15];
            int i17 = i16 + 1;
            cArr[i16] = cArr4[((byte) i15) | (b14 << 4)];
            cArr[i17] = cArr4[b13 << 2];
            cArr[i17 + 1] = '=';
        }
        return new String(cArr);
    }
}
