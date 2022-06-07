package com.google.zxing.qrcode.detector;

import com.google.zxing.NotFoundException;
import com.google.zxing.ResultPointCallback;
import com.google.zxing.common.BitMatrix;
import java.util.ArrayList;
import java.util.List;

/* compiled from: AlignmentPatternFinder.java */
/* loaded from: classes2.dex */
final class a {
    private final BitMatrix a;
    private final int c;
    private final int d;
    private final int e;
    private final int f;
    private final float g;
    private final ResultPointCallback i;
    private final List<AlignmentPattern> b = new ArrayList(5);
    private final int[] h = new int[3];

    /* JADX INFO: Access modifiers changed from: package-private */
    public a(BitMatrix bitMatrix, int i, int i2, int i3, int i4, float f, ResultPointCallback resultPointCallback) {
        this.a = bitMatrix;
        this.c = i;
        this.d = i2;
        this.e = i3;
        this.f = i4;
        this.g = f;
        this.i = resultPointCallback;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public AlignmentPattern a() throws NotFoundException {
        AlignmentPattern a;
        AlignmentPattern a2;
        int i = this.c;
        int i2 = this.f;
        int i3 = this.e + i;
        int i4 = this.d + (i2 / 2);
        int[] iArr = new int[3];
        for (int i5 = 0; i5 < i2; i5++) {
            int i6 = ((i5 & 1) == 0 ? (i5 + 1) / 2 : -((i5 + 1) / 2)) + i4;
            iArr[0] = 0;
            iArr[1] = 0;
            iArr[2] = 0;
            int i7 = i;
            while (i7 < i3 && !this.a.get(i7, i6)) {
                i7++;
            }
            int i8 = 0;
            while (i7 < i3) {
                if (!this.a.get(i7, i6)) {
                    if (i8 == 1) {
                        i8++;
                    }
                    iArr[i8] = iArr[i8] + 1;
                } else if (i8 == 1) {
                    iArr[1] = iArr[1] + 1;
                } else if (i8 != 2) {
                    i8++;
                    iArr[i8] = iArr[i8] + 1;
                } else if (a(iArr) && (a2 = a(iArr, i6, i7)) != null) {
                    return a2;
                } else {
                    iArr[0] = iArr[2];
                    iArr[1] = 1;
                    iArr[2] = 0;
                    i8 = 1;
                }
                i7++;
            }
            if (a(iArr) && (a = a(iArr, i6, i3)) != null) {
                return a;
            }
        }
        if (!this.b.isEmpty()) {
            return this.b.get(0);
        }
        throw NotFoundException.getNotFoundInstance();
    }

    private static float a(int[] iArr, int i) {
        return (i - iArr[2]) - (iArr[1] / 2.0f);
    }

    private boolean a(int[] iArr) {
        float f = this.g;
        float f2 = f / 2.0f;
        for (int i = 0; i < 3; i++) {
            if (Math.abs(f - iArr[i]) >= f2) {
                return false;
            }
        }
        return true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:31:0x0062, code lost:
        if (r2[1] <= r12) goto L_0x0065;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x0065, code lost:
        if (r10 >= r1) goto L_0x0079;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x006b, code lost:
        if (r0.get(r11, r10) != false) goto L_0x0079;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x006f, code lost:
        if (r2[2] > r12) goto L_0x0079;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x0071, code lost:
        r2[2] = r2[2] + 1;
        r10 = r10 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x007b, code lost:
        if (r2[2] <= r12) goto L_0x007e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x007d, code lost:
        return Float.NaN;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x008e, code lost:
        if ((java.lang.Math.abs(((r2[0] + r2[1]) + r2[2]) - r13) * 5) < (r13 * 2)) goto L_0x0091;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x0090, code lost:
        return Float.NaN;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x0095, code lost:
        if (a(r2) == false) goto L_0x009c;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x009b, code lost:
        return a(r2, r10);
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x009c, code lost:
        return Float.NaN;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private float a(int r10, int r11, int r12, int r13) {
        /*
            r9 = this;
            com.google.zxing.common.BitMatrix r0 = r9.a
            int r1 = r0.getHeight()
            int[] r2 = r9.h
            r3 = 0
            r2[r3] = r3
            r4 = 1
            r2[r4] = r3
            r5 = 2
            r2[r5] = r3
            r6 = r10
        L_0x0012:
            if (r6 < 0) goto L_0x0026
            boolean r7 = r0.get(r11, r6)
            if (r7 == 0) goto L_0x0026
            r7 = r2[r4]
            if (r7 > r12) goto L_0x0026
            r7 = r2[r4]
            int r7 = r7 + r4
            r2[r4] = r7
            int r6 = r6 + (-1)
            goto L_0x0012
        L_0x0026:
            r7 = 2143289344(0x7fc00000, float:NaN)
            if (r6 < 0) goto L_0x009e
            r8 = r2[r4]
            if (r8 <= r12) goto L_0x0030
            goto L_0x009e
        L_0x0030:
            if (r6 < 0) goto L_0x0044
            boolean r8 = r0.get(r11, r6)
            if (r8 != 0) goto L_0x0044
            r8 = r2[r3]
            if (r8 > r12) goto L_0x0044
            r8 = r2[r3]
            int r8 = r8 + r4
            r2[r3] = r8
            int r6 = r6 + (-1)
            goto L_0x0030
        L_0x0044:
            r6 = r2[r3]
            if (r6 <= r12) goto L_0x0049
            return r7
        L_0x0049:
            int r10 = r10 + r4
        L_0x004a:
            if (r10 >= r1) goto L_0x005e
            boolean r6 = r0.get(r11, r10)
            if (r6 == 0) goto L_0x005e
            r6 = r2[r4]
            if (r6 > r12) goto L_0x005e
            r6 = r2[r4]
            int r6 = r6 + r4
            r2[r4] = r6
            int r10 = r10 + 1
            goto L_0x004a
        L_0x005e:
            if (r10 == r1) goto L_0x009d
            r6 = r2[r4]
            if (r6 <= r12) goto L_0x0065
            goto L_0x009d
        L_0x0065:
            if (r10 >= r1) goto L_0x0079
            boolean r6 = r0.get(r11, r10)
            if (r6 != 0) goto L_0x0079
            r6 = r2[r5]
            if (r6 > r12) goto L_0x0079
            r6 = r2[r5]
            int r6 = r6 + r4
            r2[r5] = r6
            int r10 = r10 + 1
            goto L_0x0065
        L_0x0079:
            r11 = r2[r5]
            if (r11 <= r12) goto L_0x007e
            return r7
        L_0x007e:
            r11 = r2[r3]
            r12 = r2[r4]
            int r11 = r11 + r12
            r12 = r2[r5]
            int r11 = r11 + r12
            int r11 = r11 - r13
            int r11 = java.lang.Math.abs(r11)
            int r11 = r11 * 5
            int r13 = r13 * r5
            if (r11 < r13) goto L_0x0091
            return r7
        L_0x0091:
            boolean r11 = r9.a(r2)
            if (r11 == 0) goto L_0x009c
            float r10 = a(r2, r10)
            return r10
        L_0x009c:
            return r7
        L_0x009d:
            return r7
        L_0x009e:
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.qrcode.detector.a.a(int, int, int, int):float");
    }

    private AlignmentPattern a(int[] iArr, int i, int i2) {
        int i3 = iArr[0] + iArr[1] + iArr[2];
        float a = a(iArr, i2);
        float a2 = a(i, (int) a, iArr[1] * 2, i3);
        if (Float.isNaN(a2)) {
            return null;
        }
        float f = ((iArr[0] + iArr[1]) + iArr[2]) / 3.0f;
        for (AlignmentPattern alignmentPattern : this.b) {
            if (alignmentPattern.a(f, a2, a)) {
                return alignmentPattern.b(a2, a, f);
            }
        }
        AlignmentPattern alignmentPattern2 = new AlignmentPattern(a, a2, f);
        this.b.add(alignmentPattern2);
        ResultPointCallback resultPointCallback = this.i;
        if (resultPointCallback == null) {
            return null;
        }
        resultPointCallback.foundPossibleResultPoint(alignmentPattern2);
        return null;
    }
}
