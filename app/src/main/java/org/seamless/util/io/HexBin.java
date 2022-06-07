package org.seamless.util.io;

import java.io.UnsupportedEncodingException;

/* loaded from: classes4.dex */
public final class HexBin {
    private static final int BASELENGTH = 255;
    private static final int LOOKUPLENGTH = 16;
    private static byte[] hexNumberTable = new byte[255];
    private static byte[] lookUpHexAlphabet = new byte[16];

    static {
        for (int i = 0; i < 255; i++) {
            hexNumberTable[i] = -1;
        }
        for (int i2 = 57; i2 >= 48; i2--) {
            hexNumberTable[i2] = (byte) (i2 - 48);
        }
        for (int i3 = 70; i3 >= 65; i3--) {
            hexNumberTable[i3] = (byte) ((i3 - 65) + 10);
        }
        for (int i4 = 102; i4 >= 97; i4--) {
            hexNumberTable[i4] = (byte) ((i4 - 97) + 10);
        }
        for (int i5 = 0; i5 < 10; i5++) {
            lookUpHexAlphabet[i5] = (byte) (i5 + 48);
        }
        for (int i6 = 10; i6 <= 15; i6++) {
            lookUpHexAlphabet[i6] = (byte) ((i6 + 65) - 10);
        }
    }

    static boolean isHex(byte b) {
        return hexNumberTable[b] != -1;
    }

    public static String bytesToString(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        return new String(encode(bArr));
    }

    public static String bytesToString(byte[] bArr, String str) {
        if (bArr == null) {
            return null;
        }
        String str2 = new String(encode(bArr));
        StringBuilder sb = new StringBuilder();
        int i = 1;
        for (char c : str2.toCharArray()) {
            sb.append(c);
            if (i == 2) {
                sb.append(str);
                i = 1;
            } else {
                i++;
            }
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    public static byte[] stringToBytes(String str) {
        return decode(str.getBytes());
    }

    public static byte[] stringToBytes(String str, String str2) {
        return decode(str.replaceAll(str2, "").getBytes());
    }

    public static byte[] encode(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        int length = bArr.length;
        byte[] bArr2 = new byte[length * 2];
        for (int i = 0; i < length; i++) {
            int i2 = i * 2;
            byte[] bArr3 = lookUpHexAlphabet;
            bArr2[i2] = bArr3[(bArr[i] >> 4) & 15];
            bArr2[i2 + 1] = bArr3[bArr[i] & 15];
        }
        return bArr2;
    }

    public static byte[] decode(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        int length = bArr.length;
        if (length % 2 != 0) {
            return null;
        }
        int i = length / 2;
        byte[] bArr2 = new byte[i];
        for (int i2 = 0; i2 < i; i2++) {
            int i3 = i2 * 2;
            if (isHex(bArr[i3])) {
                int i4 = i3 + 1;
                if (isHex(bArr[i4])) {
                    byte[] bArr3 = hexNumberTable;
                    bArr2[i2] = (byte) ((bArr3[bArr[i3]] << 4) | bArr3[bArr[i4]]);
                }
            }
            return null;
        }
        return bArr2;
    }

    public static String decode(String str) {
        byte[] bArr;
        if (str == null) {
            return null;
        }
        try {
            bArr = decode(str.getBytes("utf-8"));
        } catch (UnsupportedEncodingException unused) {
            bArr = null;
        }
        if (bArr == null) {
            return null;
        }
        return new String(bArr);
    }

    public static String encode(String str) {
        byte[] bArr;
        if (str == null) {
            return null;
        }
        try {
            bArr = encode(str.getBytes("utf-8"));
        } catch (UnsupportedEncodingException unused) {
            bArr = null;
        }
        if (bArr == null) {
            return null;
        }
        return new String(bArr);
    }
}
