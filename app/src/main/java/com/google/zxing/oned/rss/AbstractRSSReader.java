package com.google.zxing.oned.rss;

import com.google.zxing.NotFoundException;
import com.google.zxing.common.detector.MathUtils;
import com.google.zxing.oned.OneDReader;

/* loaded from: classes2.dex */
public abstract class AbstractRSSReader extends OneDReader {
    private final int[] a = new int[4];
    private final int[] b = new int[8];
    private final float[] c = new float[4];
    private final float[] d = new float[4];
    private final int[] e;
    private final int[] f;

    /* JADX INFO: Access modifiers changed from: protected */
    public AbstractRSSReader() {
        int[] iArr = this.b;
        this.e = new int[iArr.length / 2];
        this.f = new int[iArr.length / 2];
    }

    protected final int[] getDecodeFinderCounters() {
        return this.a;
    }

    protected final int[] getDataCharacterCounters() {
        return this.b;
    }

    protected final float[] getOddRoundingErrors() {
        return this.c;
    }

    protected final float[] getEvenRoundingErrors() {
        return this.d;
    }

    protected final int[] getOddCounts() {
        return this.e;
    }

    protected final int[] getEvenCounts() {
        return this.f;
    }

    protected static int parseFinderValue(int[] iArr, int[][] iArr2) throws NotFoundException {
        for (int i = 0; i < iArr2.length; i++) {
            if (patternMatchVariance(iArr, iArr2[i], 0.45f) < 0.2f) {
                return i;
            }
        }
        throw NotFoundException.getNotFoundInstance();
    }

    @Deprecated
    protected static int count(int[] iArr) {
        return MathUtils.sum(iArr);
    }

    protected static void increment(int[] iArr, float[] fArr) {
        float f = fArr[0];
        int i = 0;
        for (int i2 = 1; i2 < iArr.length; i2++) {
            if (fArr[i2] > f) {
                f = fArr[i2];
                i = i2;
            }
        }
        iArr[i] = iArr[i] + 1;
    }

    protected static void decrement(int[] iArr, float[] fArr) {
        float f = fArr[0];
        int i = 0;
        for (int i2 = 1; i2 < iArr.length; i2++) {
            if (fArr[i2] < f) {
                f = fArr[i2];
                i = i2;
            }
        }
        iArr[i] = iArr[i] - 1;
    }

    protected static boolean isFinderPattern(int[] iArr) {
        int i = iArr[0] + iArr[1];
        float f = i / ((iArr[2] + i) + iArr[3]);
        if (f < 0.7916667f || f > 0.89285713f) {
            return false;
        }
        int i2 = Integer.MAX_VALUE;
        int i3 = Integer.MIN_VALUE;
        for (int i4 : iArr) {
            if (i4 > i3) {
                i3 = i4;
            }
            if (i4 < i2) {
                i2 = i4;
            }
        }
        return i3 < i2 * 10;
    }
}
