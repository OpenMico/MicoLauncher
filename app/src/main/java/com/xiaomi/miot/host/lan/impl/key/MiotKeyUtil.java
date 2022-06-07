package com.xiaomi.miot.host.lan.impl.key;

/* loaded from: classes2.dex */
public class MiotKeyUtil {
    private static final byte[] KEY = {55, 51, 98, 99, 101, 97, 50, 75};

    private static void xorEncrypt(byte[] bArr, byte[] bArr2) {
        for (int i = 0; i < bArr2.length; i++) {
            bArr2[i] = (byte) (bArr2[i] ^ bArr[i % bArr.length]);
        }
    }

    public static String replaceKey(String str) {
        byte[] bytes = str.getBytes();
        xorEncrypt(KEY, bytes);
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) (bytes[i] - 15);
        }
        return hex2str(bytes);
    }

    private static String hex2str(byte[] bArr) {
        char[] cArr = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        char[] cArr2 = new char[bArr.length * 2];
        int i = 0;
        for (byte b : bArr) {
            int i2 = i + 1;
            cArr2[i] = cArr[(b >>> 4) & 15];
            i = i2 + 1;
            cArr2[i2] = cArr[b & 15];
        }
        return new String(cArr2);
    }
}
