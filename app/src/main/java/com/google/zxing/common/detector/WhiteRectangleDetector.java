package com.google.zxing.common.detector;

import com.google.zxing.NotFoundException;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitMatrix;

/* loaded from: classes2.dex */
public final class WhiteRectangleDetector {
    private final BitMatrix a;
    private final int b;
    private final int c;
    private final int d;
    private final int e;
    private final int f;
    private final int g;

    public WhiteRectangleDetector(BitMatrix bitMatrix) throws NotFoundException {
        this(bitMatrix, 10, bitMatrix.getWidth() / 2, bitMatrix.getHeight() / 2);
    }

    public WhiteRectangleDetector(BitMatrix bitMatrix, int i, int i2, int i3) throws NotFoundException {
        this.a = bitMatrix;
        this.b = bitMatrix.getHeight();
        this.c = bitMatrix.getWidth();
        int i4 = i / 2;
        this.d = i2 - i4;
        this.e = i2 + i4;
        this.g = i3 - i4;
        this.f = i3 + i4;
        if (this.g < 0 || this.d < 0 || this.f >= this.b || this.e >= this.c) {
            throw NotFoundException.getNotFoundInstance();
        }
    }

    public ResultPoint[] detect() throws NotFoundException {
        int i = this.d;
        int i2 = this.e;
        int i3 = this.g;
        int i4 = this.f;
        boolean z = false;
        int i5 = i;
        boolean z2 = false;
        boolean z3 = false;
        boolean z4 = false;
        boolean z5 = false;
        boolean z6 = false;
        boolean z7 = true;
        while (true) {
            if (!z7) {
                break;
            }
            boolean z8 = false;
            boolean z9 = true;
            while (true) {
                if ((z9 || !z2) && i2 < this.c) {
                    z9 = a(i3, i4, i2, false);
                    if (z9) {
                        i2++;
                        z2 = true;
                        z8 = true;
                    } else if (!z2) {
                        i2++;
                    }
                }
            }
            if (i2 >= this.c) {
                z = true;
                break;
            }
            boolean z10 = true;
            while (true) {
                if ((z10 || !z3) && i4 < this.b) {
                    z10 = a(i5, i2, i4, true);
                    if (z10) {
                        i4++;
                        z3 = true;
                        z8 = true;
                    } else if (!z3) {
                        i4++;
                    }
                }
            }
            if (i4 >= this.b) {
                z = true;
                break;
            }
            boolean z11 = true;
            while (true) {
                if ((z11 || !z4) && i5 >= 0) {
                    z11 = a(i3, i4, i5, false);
                    if (z11) {
                        i5--;
                        z4 = true;
                        z8 = true;
                    } else if (!z4) {
                        i5--;
                    }
                }
            }
            if (i5 < 0) {
                z = true;
                break;
            }
            boolean z12 = true;
            while (true) {
                if ((z12 || !z6) && i3 >= 0) {
                    z12 = a(i5, i2, i3, true);
                    if (z12) {
                        i3--;
                        z6 = true;
                        z8 = true;
                    } else if (!z6) {
                        i3--;
                    }
                }
            }
            if (i3 < 0) {
                z = true;
                break;
            }
            if (z8) {
                z5 = true;
            }
            z7 = z8;
        }
        if (z || !z5) {
            throw NotFoundException.getNotFoundInstance();
        }
        int i6 = i2 - i5;
        ResultPoint resultPoint = null;
        ResultPoint resultPoint2 = null;
        for (int i7 = 1; resultPoint2 == null && i7 < i6; i7++) {
            resultPoint2 = a(i5, i4 - i7, i5 + i7, i4);
        }
        if (resultPoint2 != null) {
            ResultPoint resultPoint3 = null;
            for (int i8 = 1; resultPoint3 == null && i8 < i6; i8++) {
                resultPoint3 = a(i5, i3 + i8, i5 + i8, i3);
            }
            if (resultPoint3 != null) {
                ResultPoint resultPoint4 = null;
                for (int i9 = 1; resultPoint4 == null && i9 < i6; i9++) {
                    resultPoint4 = a(i2, i3 + i9, i2 - i9, i3);
                }
                if (resultPoint4 != null) {
                    for (int i10 = 1; resultPoint == null && i10 < i6; i10++) {
                        resultPoint = a(i2, i4 - i10, i2 - i10, i4);
                    }
                    if (resultPoint != null) {
                        return a(resultPoint, resultPoint2, resultPoint4, resultPoint3);
                    }
                    throw NotFoundException.getNotFoundInstance();
                }
                throw NotFoundException.getNotFoundInstance();
            }
            throw NotFoundException.getNotFoundInstance();
        }
        throw NotFoundException.getNotFoundInstance();
    }

    private ResultPoint a(float f, float f2, float f3, float f4) {
        int round = MathUtils.round(MathUtils.distance(f, f2, f3, f4));
        float f5 = round;
        float f6 = (f3 - f) / f5;
        float f7 = (f4 - f2) / f5;
        for (int i = 0; i < round; i++) {
            float f8 = i;
            int round2 = MathUtils.round((f8 * f6) + f);
            int round3 = MathUtils.round((f8 * f7) + f2);
            if (this.a.get(round2, round3)) {
                return new ResultPoint(round2, round3);
            }
        }
        return null;
    }

    private ResultPoint[] a(ResultPoint resultPoint, ResultPoint resultPoint2, ResultPoint resultPoint3, ResultPoint resultPoint4) {
        float x = resultPoint.getX();
        float y = resultPoint.getY();
        float x2 = resultPoint2.getX();
        float y2 = resultPoint2.getY();
        float x3 = resultPoint3.getX();
        float y3 = resultPoint3.getY();
        float x4 = resultPoint4.getX();
        float y4 = resultPoint4.getY();
        return x < ((float) this.c) / 2.0f ? new ResultPoint[]{new ResultPoint(x4 - 1.0f, y4 + 1.0f), new ResultPoint(x2 + 1.0f, y2 + 1.0f), new ResultPoint(x3 - 1.0f, y3 - 1.0f), new ResultPoint(x + 1.0f, y - 1.0f)} : new ResultPoint[]{new ResultPoint(x4 + 1.0f, y4 + 1.0f), new ResultPoint(x2 + 1.0f, y2 - 1.0f), new ResultPoint(x3 - 1.0f, y3 + 1.0f), new ResultPoint(x - 1.0f, y - 1.0f)};
    }

    private boolean a(int i, int i2, int i3, boolean z) {
        if (z) {
            while (i <= i2) {
                if (this.a.get(i, i3)) {
                    return true;
                }
                i++;
            }
            return false;
        }
        while (i <= i2) {
            if (this.a.get(i3, i)) {
                return true;
            }
            i++;
        }
        return false;
    }
}
