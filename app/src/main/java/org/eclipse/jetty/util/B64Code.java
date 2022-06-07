package org.eclipse.jetty.util;

import com.fasterxml.jackson.core.JsonPointer;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import okio.Utf8;

/* loaded from: classes5.dex */
public class B64Code {
    static final char pad = '=';
    static final char[] rfc1421alphabet = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', JsonPointer.SEPARATOR};
    static final byte[] rfc1421nibbles = new byte[256];

    static {
        for (int i = 0; i < 256; i++) {
            rfc1421nibbles[i] = -1;
        }
        for (byte b = 0; b < 64; b = (byte) (b + 1)) {
            rfc1421nibbles[(byte) rfc1421alphabet[b]] = b;
        }
        rfc1421nibbles[61] = 0;
    }

    public static String encode(String str) {
        try {
            return encode(str, (String) null);
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException(e.toString());
        }
    }

    public static String encode(String str, String str2) throws UnsupportedEncodingException {
        byte[] bArr;
        if (str2 == null) {
            bArr = str.getBytes("ISO-8859-1");
        } else {
            bArr = str.getBytes(str2);
        }
        return new String(encode(bArr));
    }

    public static char[] encode(byte[] bArr) {
        return encode(bArr, false);
    }

    public static char[] encode(byte[] bArr, boolean z) {
        if (bArr == null) {
            return null;
        }
        int length = bArr.length;
        int i = ((length + 2) / 3) * 4;
        if (z) {
            i += ((i * 2) / 76) + 2;
        }
        char[] cArr = new char[i];
        int i2 = (length / 3) * 3;
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        while (i3 < i2) {
            int i6 = i3 + 1;
            byte b = bArr[i3];
            int i7 = i6 + 1;
            byte b2 = bArr[i6];
            int i8 = i7 + 1;
            byte b3 = bArr[i7];
            int i9 = i4 + 1;
            char[] cArr2 = rfc1421alphabet;
            cArr[i4] = cArr2[(b >>> 2) & 63];
            int i10 = i9 + 1;
            cArr[i9] = cArr2[((b << 4) & 63) | ((b2 >>> 4) & 15)];
            int i11 = i10 + 1;
            cArr[i10] = cArr2[((b2 << 2) & 63) | ((b3 >>> 6) & 3)];
            i4 = i11 + 1;
            cArr[i11] = cArr2[b3 & Utf8.REPLACEMENT_BYTE];
            i5 += 4;
            if (!z || i5 % 76 != 0) {
                i3 = i8;
            } else {
                int i12 = i4 + 1;
                cArr[i4] = '\r';
                i4 = i12 + 1;
                cArr[i12] = '\n';
                i3 = i8;
            }
        }
        if (length != i3) {
            switch (length % 3) {
                case 1:
                    byte b4 = bArr[i3];
                    int i13 = i4 + 1;
                    char[] cArr3 = rfc1421alphabet;
                    cArr[i4] = cArr3[(b4 >>> 2) & 63];
                    int i14 = i13 + 1;
                    cArr[i13] = cArr3[(b4 << 4) & 63];
                    int i15 = i14 + 1;
                    cArr[i14] = pad;
                    i4 = i15 + 1;
                    cArr[i15] = pad;
                    break;
                case 2:
                    int i16 = i3 + 1;
                    byte b5 = bArr[i3];
                    byte b6 = bArr[i16];
                    int i17 = i4 + 1;
                    char[] cArr4 = rfc1421alphabet;
                    cArr[i4] = cArr4[(b5 >>> 2) & 63];
                    int i18 = i17 + 1;
                    cArr[i17] = cArr4[((b5 << 4) & 63) | ((b6 >>> 4) & 15)];
                    int i19 = i18 + 1;
                    cArr[i18] = cArr4[(b6 << 2) & 63];
                    i4 = i19 + 1;
                    cArr[i19] = pad;
                    break;
            }
        }
        if (z) {
            cArr[i4] = '\r';
            cArr[i4 + 1] = '\n';
        }
        return cArr;
    }

    public static String decode(String str, String str2) throws UnsupportedEncodingException {
        byte[] decode = decode(str);
        if (str2 == null) {
            return new String(decode);
        }
        return new String(decode, str2);
    }

    public static byte[] decode(char[] cArr) {
        byte b;
        if (cArr == null) {
            return null;
        }
        int length = cArr.length;
        if (length % 4 == 0) {
            int i = length - 1;
            while (i >= 0 && cArr[i] == '=') {
                i--;
            }
            int i2 = 0;
            if (i < 0) {
                return new byte[0];
            }
            int i3 = ((i + 1) * 3) / 4;
            byte[] bArr = new byte[i3];
            int i4 = (i3 / 3) * 3;
            int i5 = 0;
            while (i2 < i4) {
                try {
                    i4 = i5 + 1;
                    try {
                        byte b2 = rfc1421nibbles[cArr[i5]];
                        int i6 = i4 + 1;
                        try {
                            byte b3 = rfc1421nibbles[cArr[i4]];
                            int i7 = i6 + 1;
                            try {
                                byte b4 = rfc1421nibbles[cArr[i6]];
                                int i8 = i7 + 1;
                                try {
                                    byte b5 = rfc1421nibbles[cArr[i7]];
                                    if (b2 < 0 || b3 < 0 || b4 < 0 || b5 < 0) {
                                        throw new IllegalArgumentException("Not B64 encoded");
                                    }
                                    int i9 = i2 + 1;
                                    bArr[i2] = (byte) ((b2 << 2) | (b3 >>> 4));
                                    int i10 = i9 + 1;
                                    bArr[i9] = (byte) ((b3 << 4) | (b4 >>> 2));
                                    i2 = i10 + 1;
                                    b = (byte) ((b4 << 6) | b5);
                                    bArr[i10] = b;
                                    i5 = i8;
                                } catch (IndexOutOfBoundsException unused) {
                                    i4 = i8;
                                    throw new IllegalArgumentException("char " + i4 + " was not B64 encoded");
                                }
                            } catch (IndexOutOfBoundsException unused2) {
                                i4 = i7;
                            }
                        } catch (IndexOutOfBoundsException unused3) {
                            i4 = i6;
                        }
                    } catch (IndexOutOfBoundsException unused4) {
                    }
                } catch (IndexOutOfBoundsException unused5) {
                    i4 = i5;
                }
            }
            if (i3 != i2) {
                try {
                    try {
                        switch (i3 % 3) {
                            case 1:
                                int i11 = i5 + 1;
                                byte b6 = rfc1421nibbles[cArr[i5]];
                                int i12 = i11 + 1;
                                byte b7 = rfc1421nibbles[cArr[i11]];
                                if (b6 >= 0 && b7 >= 0) {
                                    bArr[i2] = (byte) ((b7 >>> 4) | (b6 << 2));
                                    i4 = i11;
                                    break;
                                } else {
                                    throw new IllegalArgumentException("Not B64 encoded");
                                }
                            case 2:
                                int i13 = i5 + 1;
                                byte b8 = rfc1421nibbles[cArr[i5]];
                                int i14 = i13 + 1;
                                byte b9 = rfc1421nibbles[cArr[i13]];
                                int i15 = i14 + 1;
                                byte b10 = rfc1421nibbles[cArr[i14]];
                                if (b8 >= 0 && b9 >= 0 && b10 >= 0) {
                                    bArr[i2] = (byte) ((b8 << 2) | (b9 >>> 4));
                                    bArr[i2 + 1] = (byte) ((b10 >>> 2) | (b9 << 4));
                                    i4 = b9;
                                    break;
                                } else {
                                    throw new IllegalArgumentException("Not B64 encoded");
                                }
                        }
                    } catch (IndexOutOfBoundsException unused6) {
                        i4 = b;
                        throw new IllegalArgumentException("char " + i4 + " was not B64 encoded");
                    }
                } catch (IndexOutOfBoundsException unused7) {
                    throw new IllegalArgumentException("char " + i4 + " was not B64 encoded");
                }
            }
            return bArr;
        }
        throw new IllegalArgumentException("Input block size is not 4");
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static byte[] decode(String str) {
        if (str == null) {
            return null;
        }
        byte[] bArr = new byte[4];
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream((str.length() * 4) / 3);
        int i = 0;
        int i2 = 0;
        while (i < str.length()) {
            int i3 = i + 1;
            char charAt = str.charAt(i);
            if (charAt == '=') {
                return byteArrayOutputStream.toByteArray();
            }
            if (Character.isWhitespace(charAt)) {
                i = i3;
            } else {
                byte[] bArr2 = rfc1421nibbles;
                if (bArr2[charAt] >= 0) {
                    int i4 = i2 + 1;
                    bArr[i2] = bArr2[charAt];
                    switch (i4) {
                        case 1:
                        default:
                            i2 = i4;
                            break;
                        case 2:
                            byteArrayOutputStream.write((bArr[0] << 2) | (bArr[1] >>> 4));
                            i2 = i4;
                            break;
                        case 3:
                            byteArrayOutputStream.write((bArr[2] >>> 2) | (bArr[1] << 4));
                            i2 = i4;
                            break;
                        case 4:
                            byteArrayOutputStream.write((bArr[2] << 6) | bArr[3]);
                            i2 = 0;
                            break;
                    }
                    i = i3;
                } else {
                    throw new IllegalArgumentException("Not B64 encoded");
                }
            }
        }
        return byteArrayOutputStream.toByteArray();
    }
}
