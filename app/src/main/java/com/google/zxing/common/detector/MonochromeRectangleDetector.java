package com.google.zxing.common.detector;

import com.google.zxing.NotFoundException;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitMatrix;

@Deprecated
/* loaded from: classes2.dex */
public final class MonochromeRectangleDetector {
    private final BitMatrix a;

    public MonochromeRectangleDetector(BitMatrix bitMatrix) {
        this.a = bitMatrix;
    }

    public ResultPoint[] detect() throws NotFoundException {
        int height = this.a.getHeight();
        int width = this.a.getWidth();
        int i = height / 2;
        int i2 = width / 2;
        int max = Math.max(1, height / 256);
        int max2 = Math.max(1, width / 256);
        int i3 = -max;
        int i4 = i2 / 2;
        int y = ((int) a(i2, 0, 0, width, i, i3, 0, height, i4).getY()) - 1;
        int i5 = i / 2;
        ResultPoint a = a(i2, -max2, 0, width, i, 0, y, height, i5);
        int x = ((int) a.getX()) - 1;
        ResultPoint a2 = a(i2, max2, x, width, i, 0, y, height, i5);
        int x2 = ((int) a2.getX()) + 1;
        ResultPoint a3 = a(i2, 0, x, x2, i, max, y, height, i4);
        return new ResultPoint[]{a(i2, 0, x, x2, i, i3, y, ((int) a3.getY()) + 1, i2 / 4), a, a2, a3};
    }

    private ResultPoint a(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9) throws NotFoundException {
        int[] iArr;
        int[] iArr2 = null;
        int i10 = i;
        int i11 = i5;
        while (i11 < i8 && i11 >= i7 && i10 < i4 && i10 >= i3) {
            if (i2 == 0) {
                iArr = a(i11, i9, i3, i4, true);
            } else {
                iArr = a(i10, i9, i7, i8, false);
            }
            if (iArr != null) {
                i11 += i6;
                i10 += i2;
                iArr2 = iArr;
            } else if (iArr2 != null) {
                char c = 1;
                if (i2 == 0) {
                    int i12 = i11 - i6;
                    if (iArr2[0] >= i) {
                        return new ResultPoint(iArr2[1], i12);
                    }
                    if (iArr2[1] <= i) {
                        return new ResultPoint(iArr2[0], i12);
                    }
                    if (i6 > 0) {
                        c = 0;
                    }
                    return new ResultPoint(iArr2[c], i12);
                }
                int i13 = i10 - i2;
                if (iArr2[0] >= i5) {
                    return new ResultPoint(i13, iArr2[1]);
                }
                if (iArr2[1] <= i5) {
                    return new ResultPoint(i13, iArr2[0]);
                }
                float f = i13;
                if (i2 < 0) {
                    c = 0;
                }
                return new ResultPoint(f, iArr2[c]);
            } else {
                throw NotFoundException.getNotFoundInstance();
            }
        }
        throw NotFoundException.getNotFoundInstance();
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0022  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x005c  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x0035 A[EDGE_INSN: B:57:0x0035->B:18:0x0035 ?: BREAK  , SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:76:0x006f A[EDGE_INSN: B:76:0x006f->B:38:0x006f ?: BREAK  , SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private int[] a(int r6, int r7, int r8, int r9, boolean r10) {
        /*
            r5 = this;
            int r0 = r8 + r9
            r1 = 2
            int r0 = r0 / r1
            r2 = r0
        L_0x0005:
            if (r2 < r8) goto L_0x003e
            if (r10 == 0) goto L_0x0012
            com.google.zxing.common.BitMatrix r3 = r5.a
            boolean r3 = r3.get(r2, r6)
            if (r3 == 0) goto L_0x001d
            goto L_0x001a
        L_0x0012:
            com.google.zxing.common.BitMatrix r3 = r5.a
            boolean r3 = r3.get(r6, r2)
            if (r3 == 0) goto L_0x001d
        L_0x001a:
            int r2 = r2 + (-1)
            goto L_0x0005
        L_0x001d:
            r3 = r2
        L_0x001e:
            int r3 = r3 + (-1)
            if (r3 < r8) goto L_0x0035
            if (r10 == 0) goto L_0x002d
            com.google.zxing.common.BitMatrix r4 = r5.a
            boolean r4 = r4.get(r3, r6)
            if (r4 == 0) goto L_0x001e
            goto L_0x0035
        L_0x002d:
            com.google.zxing.common.BitMatrix r4 = r5.a
            boolean r4 = r4.get(r6, r3)
            if (r4 == 0) goto L_0x001e
        L_0x0035:
            int r4 = r2 - r3
            if (r3 < r8) goto L_0x003e
            if (r4 <= r7) goto L_0x003c
            goto L_0x003e
        L_0x003c:
            r2 = r3
            goto L_0x0005
        L_0x003e:
            r8 = 1
            int r2 = r2 + r8
        L_0x0040:
            if (r0 >= r9) goto L_0x0078
            if (r10 == 0) goto L_0x004d
            com.google.zxing.common.BitMatrix r3 = r5.a
            boolean r3 = r3.get(r0, r6)
            if (r3 == 0) goto L_0x0058
            goto L_0x0055
        L_0x004d:
            com.google.zxing.common.BitMatrix r3 = r5.a
            boolean r3 = r3.get(r6, r0)
            if (r3 == 0) goto L_0x0058
        L_0x0055:
            int r0 = r0 + 1
            goto L_0x0040
        L_0x0058:
            r3 = r0
        L_0x0059:
            int r3 = r3 + r8
            if (r3 >= r9) goto L_0x006f
            if (r10 == 0) goto L_0x0067
            com.google.zxing.common.BitMatrix r4 = r5.a
            boolean r4 = r4.get(r3, r6)
            if (r4 == 0) goto L_0x0059
            goto L_0x006f
        L_0x0067:
            com.google.zxing.common.BitMatrix r4 = r5.a
            boolean r4 = r4.get(r6, r3)
            if (r4 == 0) goto L_0x0059
        L_0x006f:
            int r4 = r3 - r0
            if (r3 >= r9) goto L_0x0078
            if (r4 <= r7) goto L_0x0076
            goto L_0x0078
        L_0x0076:
            r0 = r3
            goto L_0x0040
        L_0x0078:
            int r0 = r0 + (-1)
            if (r0 <= r2) goto L_0x0084
            int[] r6 = new int[r1]
            r7 = 0
            r6[r7] = r2
            r6[r8] = r0
            return r6
        L_0x0084:
            r6 = 0
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.common.detector.MonochromeRectangleDetector.a(int, int, int, int, boolean):int[]");
    }
}
