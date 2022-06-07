package com.google.zxing.pdf417.decoder;

import com.google.zxing.common.detector.MathUtils;
import com.google.zxing.pdf417.PDF417Common;
import java.lang.reflect.Array;

/* compiled from: PDF417CodewordDecoder.java */
/* loaded from: classes2.dex */
final class i {
    private static final float[][] a = (float[][]) Array.newInstance(float.class, PDF417Common.SYMBOL_TABLE.length, 8);

    static {
        int i;
        for (int i2 = 0; i2 < PDF417Common.SYMBOL_TABLE.length; i2++) {
            int i3 = PDF417Common.SYMBOL_TABLE[i2];
            int i4 = i3 & 1;
            int i5 = i3;
            int i6 = 0;
            while (i6 < 8) {
                float f = 0.0f;
                while (true) {
                    i = i5 & 1;
                    if (i == i4) {
                        f += 1.0f;
                        i5 >>= 1;
                    }
                }
                a[i2][(8 - i6) - 1] = f / 17.0f;
                i6++;
                i4 = i;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int a(int[] iArr) {
        int c = c(b(iArr));
        return c != -1 ? c : e(iArr);
    }

    private static int[] b(int[] iArr) {
        float sum = MathUtils.sum(iArr);
        int[] iArr2 = new int[8];
        int i = 0;
        int i2 = 0;
        for (int i3 = 0; i3 < 17; i3++) {
            if (iArr[i2] + i <= (sum / 34.0f) + ((i3 * sum) / 17.0f)) {
                i += iArr[i2];
                i2++;
            }
            iArr2[i2] = iArr2[i2] + 1;
        }
        return iArr2;
    }

    private static int c(int[] iArr) {
        int d = d(iArr);
        if (PDF417Common.getCodeword(d) == -1) {
            return -1;
        }
        return d;
    }

    private static int d(int[] iArr) {
        long j = 0;
        int i = 0;
        while (i < iArr.length) {
            long j2 = j;
            for (int i2 = 0; i2 < iArr[i]; i2++) {
                int i3 = 1;
                long j3 = j2 << 1;
                if (i % 2 != 0) {
                    i3 = 0;
                }
                j2 = j3 | i3;
            }
            i++;
            j = j2;
        }
        return (int) j;
    }

    private static int e(int[] iArr) {
        int sum = MathUtils.sum(iArr);
        float[] fArr = new float[8];
        if (sum > 1) {
            for (int i = 0; i < 8; i++) {
                fArr[i] = iArr[i] / sum;
            }
        }
        float f = Float.MAX_VALUE;
        int i2 = -1;
        int i3 = 0;
        while (true) {
            float[][] fArr2 = a;
            if (i3 >= fArr2.length) {
                return i2;
            }
            float f2 = 0.0f;
            float[] fArr3 = fArr2[i3];
            for (int i4 = 0; i4 < 8; i4++) {
                float f3 = fArr3[i4] - fArr[i4];
                f2 += f3 * f3;
                if (f2 >= f) {
                    break;
                }
            }
            if (f2 < f) {
                i2 = PDF417Common.SYMBOL_TABLE[i3];
                f = f2;
            }
            i3++;
        }
    }
}
