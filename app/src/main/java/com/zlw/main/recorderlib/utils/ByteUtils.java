package com.zlw.main.recorderlib.utils;

import java.nio.ByteBuffer;
import java.util.Arrays;

/* loaded from: classes4.dex */
public class ByteUtils {
    public static byte[] toBytes(short[] sArr) {
        int length = sArr.length;
        byte[] bArr = new byte[length << 1];
        for (int i = 0; i < length; i++) {
            int i2 = i * 2;
            bArr[i2] = (byte) sArr[i];
            bArr[i2 + 1] = (byte) (sArr[i] >> 8);
        }
        return bArr;
    }

    public static byte[] toBytes(short s) {
        return new byte[]{(byte) s, (byte) (s >> 8)};
    }

    public static byte[] toBytes(int i) {
        return new byte[]{(byte) (i & 255), (byte) ((i >> 8) & 255), (byte) ((i >> 16) & 255), (byte) ((i >> 24) & 255)};
    }

    public static byte[] toBytes(String str) {
        return str.getBytes();
    }

    public static byte[] toBytes(long j) {
        ByteBuffer allocate = ByteBuffer.allocate(8);
        allocate.putLong(0, j);
        return allocate.array();
    }

    public static int toInt(byte[] bArr, int i) {
        return ((bArr[i + 3] & 255) << 24) | (bArr[i] & 255) | ((bArr[i + 1] & 255) << 8) | ((bArr[i + 2] & 255) << 16);
    }

    public static int toInt(byte[] bArr) {
        return toInt(bArr, 0);
    }

    public static long toLong(byte[] bArr) {
        ByteBuffer allocate = ByteBuffer.allocate(8);
        allocate.put(bArr, 0, bArr.length);
        return allocate.getLong();
    }

    public static short[] toShorts(byte[] bArr) {
        int length = bArr.length >> 1;
        short[] sArr = new short[length];
        for (int i = 0; i < length; i++) {
            int i2 = i * 2;
            sArr[i] = (short) (((bArr[i2 + 1] & 255) << 8) | (bArr[i2] & 255));
        }
        return sArr;
    }

    public static byte[] merger(byte[] bArr, byte[] bArr2) {
        byte[] bArr3 = new byte[bArr.length + bArr2.length];
        System.arraycopy(bArr, 0, bArr3, 0, bArr.length);
        System.arraycopy(bArr2, 0, bArr3, bArr.length, bArr2.length);
        return bArr3;
    }

    public static String toString(byte[] bArr) {
        return Arrays.toString(bArr);
    }
}
