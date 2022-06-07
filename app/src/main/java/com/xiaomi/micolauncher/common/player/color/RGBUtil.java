package com.xiaomi.micolauncher.common.player.color;

import java.lang.reflect.Array;

/* loaded from: classes3.dex */
public class RGBUtil {
    public static int packRGB(int[] iArr) {
        if (iArr.length == 3) {
            return iArr[2] | (iArr[0] << 16) | (iArr[1] << 8);
        }
        throw new IllegalArgumentException("RGB array should contain exactly 3 values.");
    }

    public static int[] unpackRGB(int i) {
        return new int[]{(i >> 16) & 255, (i >> 8) & 255, i & 255};
    }

    public static int[] packRGBArray(int[][] iArr) {
        int[] iArr2 = new int[iArr.length];
        for (int i = 0; i < iArr.length; i++) {
            iArr2[i] = packRGB(iArr[i]);
        }
        return iArr2;
    }

    public static int[][] unpackRGBArray(int[] iArr) {
        int[][] iArr2 = (int[][]) Array.newInstance(int.class, iArr.length, 3);
        for (int i = 0; i < iArr.length; i++) {
            iArr2[i] = unpackRGB(iArr[i]);
        }
        return iArr2;
    }
}
