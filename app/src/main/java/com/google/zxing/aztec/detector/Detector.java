package com.google.zxing.aztec.detector;

import com.google.zxing.NotFoundException;
import com.google.zxing.ResultPoint;
import com.google.zxing.aztec.AztecDetectorResult;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.GridSampler;
import com.google.zxing.common.detector.MathUtils;
import com.google.zxing.common.detector.WhiteRectangleDetector;
import com.google.zxing.common.reedsolomon.GenericGF;
import com.google.zxing.common.reedsolomon.ReedSolomonDecoder;
import com.google.zxing.common.reedsolomon.ReedSolomonException;
import kotlin.text.Typography;

/* loaded from: classes2.dex */
public final class Detector {
    private static final int[] a = {3808, 476, 2107, 1799};
    private final BitMatrix b;
    private boolean c;
    private int d;
    private int e;
    private int f;
    private int g;

    public Detector(BitMatrix bitMatrix) {
        this.b = bitMatrix;
    }

    public AztecDetectorResult detect() throws NotFoundException {
        return detect(false);
    }

    public AztecDetectorResult detect(boolean z) throws NotFoundException {
        ResultPoint[] a2 = a(a());
        if (z) {
            ResultPoint resultPoint = a2[0];
            a2[0] = a2[2];
            a2[2] = resultPoint;
        }
        a(a2);
        BitMatrix bitMatrix = this.b;
        int i = this.g;
        return new AztecDetectorResult(a(bitMatrix, a2[i % 4], a2[(i + 1) % 4], a2[(i + 2) % 4], a2[(i + 3) % 4]), b(a2), this.c, this.e, this.d);
    }

    private void a(ResultPoint[] resultPointArr) throws NotFoundException {
        long j;
        long j2;
        if (!a(resultPointArr[0]) || !a(resultPointArr[1]) || !a(resultPointArr[2]) || !a(resultPointArr[3])) {
            throw NotFoundException.getNotFoundInstance();
        }
        int i = this.f * 2;
        int[] iArr = {a(resultPointArr[0], resultPointArr[1], i), a(resultPointArr[1], resultPointArr[2], i), a(resultPointArr[2], resultPointArr[3], i), a(resultPointArr[3], resultPointArr[0], i)};
        this.g = a(iArr, i);
        long j3 = 0;
        for (int i2 = 0; i2 < 4; i2++) {
            int i3 = iArr[(this.g + i2) % 4];
            if (this.c) {
                j2 = j3 << 7;
                j = (i3 >> 1) & 127;
            } else {
                j2 = j3 << 10;
                j = ((i3 >> 2) & 992) + ((i3 >> 1) & 31);
            }
            j3 = j2 + j;
        }
        int a2 = a(j3, this.c);
        if (this.c) {
            this.d = (a2 >> 6) + 1;
            this.e = (a2 & 63) + 1;
            return;
        }
        this.d = (a2 >> 11) + 1;
        this.e = (a2 & 2047) + 1;
    }

    private static int a(int[] iArr, int i) throws NotFoundException {
        int i2 = 0;
        for (int i3 : iArr) {
            i2 = (i2 << 3) + ((i3 >> (i - 2)) << 1) + (i3 & 1);
        }
        int i4 = ((i2 & 1) << 11) + (i2 >> 1);
        for (int i5 = 0; i5 < 4; i5++) {
            if (Integer.bitCount(a[i5] ^ i4) <= 2) {
                return i5;
            }
        }
        throw NotFoundException.getNotFoundInstance();
    }

    private static int a(long j, boolean z) throws NotFoundException {
        int i;
        int i2;
        if (z) {
            i = 7;
            i2 = 2;
        } else {
            i = 10;
            i2 = 4;
        }
        int i3 = i - i2;
        int[] iArr = new int[i];
        for (int i4 = i - 1; i4 >= 0; i4--) {
            iArr[i4] = ((int) j) & 15;
            j >>= 4;
        }
        try {
            new ReedSolomonDecoder(GenericGF.AZTEC_PARAM).decode(iArr, i3);
            int i5 = 0;
            for (int i6 = 0; i6 < i2; i6++) {
                i5 = (i5 << 4) + iArr[i6];
            }
            return i5;
        } catch (ReedSolomonException unused) {
            throw NotFoundException.getNotFoundInstance();
        }
    }

    private ResultPoint[] a(a aVar) throws NotFoundException {
        this.f = 1;
        a aVar2 = aVar;
        a aVar3 = aVar2;
        a aVar4 = aVar3;
        boolean z = true;
        while (this.f < 9) {
            a a2 = a(aVar, z, 1, -1);
            a a3 = a(aVar2, z, 1, 1);
            a a4 = a(aVar3, z, -1, 1);
            a a5 = a(aVar4, z, -1, -1);
            if (this.f > 2) {
                double b = (b(a5, a2) * this.f) / (b(aVar4, aVar) * (this.f + 2));
                if (b < 0.75d || b > 1.25d || !a(a2, a3, a4, a5)) {
                    break;
                }
            }
            z = !z;
            this.f++;
            aVar4 = a5;
            aVar = a2;
            aVar2 = a3;
            aVar3 = a4;
        }
        int i = this.f;
        if (i == 5 || i == 7) {
            this.c = this.f == 5;
            ResultPoint[] resultPointArr = {new ResultPoint(aVar.b() + 0.5f, aVar.c() - 0.5f), new ResultPoint(aVar2.b() + 0.5f, aVar2.c() + 0.5f), new ResultPoint(aVar3.b() - 0.5f, aVar3.c() + 0.5f), new ResultPoint(aVar4.b() - 0.5f, aVar4.c() - 0.5f)};
            int i2 = this.f;
            return a(resultPointArr, (i2 * 2) - 3, i2 * 2);
        }
        throw NotFoundException.getNotFoundInstance();
    }

    private a a() {
        ResultPoint resultPoint;
        ResultPoint resultPoint2;
        ResultPoint resultPoint3;
        ResultPoint resultPoint4;
        ResultPoint resultPoint5;
        ResultPoint resultPoint6;
        ResultPoint resultPoint7;
        ResultPoint resultPoint8;
        try {
            ResultPoint[] detect = new WhiteRectangleDetector(this.b).detect();
            resultPoint3 = detect[0];
            resultPoint2 = detect[1];
            resultPoint = detect[2];
            resultPoint4 = detect[3];
        } catch (NotFoundException unused) {
            int width = this.b.getWidth() / 2;
            int height = this.b.getHeight() / 2;
            int i = width + 7;
            int i2 = height - 7;
            resultPoint3 = a(new a(i, i2), false, 1, -1).a();
            int i3 = height + 7;
            resultPoint2 = a(new a(i, i3), false, 1, 1).a();
            int i4 = width - 7;
            resultPoint = a(new a(i4, i3), false, -1, 1).a();
            resultPoint4 = a(new a(i4, i2), false, -1, -1).a();
        }
        int round = MathUtils.round((((resultPoint3.getX() + resultPoint4.getX()) + resultPoint2.getX()) + resultPoint.getX()) / 4.0f);
        int round2 = MathUtils.round((((resultPoint3.getY() + resultPoint4.getY()) + resultPoint2.getY()) + resultPoint.getY()) / 4.0f);
        try {
            ResultPoint[] detect2 = new WhiteRectangleDetector(this.b, 15, round, round2).detect();
            resultPoint6 = detect2[0];
            resultPoint5 = detect2[1];
            resultPoint7 = detect2[2];
            resultPoint8 = detect2[3];
        } catch (NotFoundException unused2) {
            int i5 = round + 7;
            int i6 = round2 - 7;
            resultPoint6 = a(new a(i5, i6), false, 1, -1).a();
            int i7 = round2 + 7;
            resultPoint5 = a(new a(i5, i7), false, 1, 1).a();
            int i8 = round - 7;
            resultPoint7 = a(new a(i8, i7), false, -1, 1).a();
            resultPoint8 = a(new a(i8, i6), false, -1, -1).a();
        }
        return new a(MathUtils.round((((resultPoint6.getX() + resultPoint8.getX()) + resultPoint5.getX()) + resultPoint7.getX()) / 4.0f), MathUtils.round((((resultPoint6.getY() + resultPoint8.getY()) + resultPoint5.getY()) + resultPoint7.getY()) / 4.0f));
    }

    private ResultPoint[] b(ResultPoint[] resultPointArr) {
        return a(resultPointArr, this.f * 2, b());
    }

    private BitMatrix a(BitMatrix bitMatrix, ResultPoint resultPoint, ResultPoint resultPoint2, ResultPoint resultPoint3, ResultPoint resultPoint4) throws NotFoundException {
        GridSampler instance = GridSampler.getInstance();
        int b = b();
        float f = b / 2.0f;
        int i = this.f;
        float f2 = f - i;
        float f3 = f + i;
        return instance.sampleGrid(bitMatrix, b, b, f2, f2, f3, f2, f3, f3, f2, f3, resultPoint.getX(), resultPoint.getY(), resultPoint2.getX(), resultPoint2.getY(), resultPoint3.getX(), resultPoint3.getY(), resultPoint4.getX(), resultPoint4.getY());
    }

    private int a(ResultPoint resultPoint, ResultPoint resultPoint2, int i) {
        float a2 = a(resultPoint, resultPoint2);
        float f = a2 / i;
        float x = resultPoint.getX();
        float y = resultPoint.getY();
        float x2 = ((resultPoint2.getX() - resultPoint.getX()) * f) / a2;
        float y2 = (f * (resultPoint2.getY() - resultPoint.getY())) / a2;
        int i2 = 0;
        for (int i3 = 0; i3 < i; i3++) {
            float f2 = i3;
            if (this.b.get(MathUtils.round((f2 * x2) + x), MathUtils.round((f2 * y2) + y))) {
                i2 |= 1 << ((i - i3) - 1);
            }
        }
        return i2;
    }

    private boolean a(a aVar, a aVar2, a aVar3, a aVar4) {
        a aVar5 = new a(aVar.b() - 3, aVar.c() + 3);
        a aVar6 = new a(aVar2.b() - 3, aVar2.c() - 3);
        a aVar7 = new a(aVar3.b() + 3, aVar3.c() - 3);
        a aVar8 = new a(aVar4.b() + 3, aVar4.c() + 3);
        int a2 = a(aVar8, aVar5);
        return a2 != 0 && a(aVar5, aVar6) == a2 && a(aVar6, aVar7) == a2 && a(aVar7, aVar8) == a2;
    }

    private int a(a aVar, a aVar2) {
        float b = b(aVar, aVar2);
        float b2 = (aVar2.b() - aVar.b()) / b;
        float c = (aVar2.c() - aVar.c()) / b;
        float b3 = aVar.b();
        float c2 = aVar.c();
        boolean z = this.b.get(aVar.b(), aVar.c());
        int ceil = (int) Math.ceil(b);
        boolean z2 = false;
        int i = 0;
        for (int i2 = 0; i2 < ceil; i2++) {
            b3 += b2;
            c2 += c;
            if (this.b.get(MathUtils.round(b3), MathUtils.round(c2)) != z) {
                i++;
            }
        }
        float f = i / b;
        if (f > 0.1f && f < 0.9f) {
            return 0;
        }
        if (f <= 0.1f) {
            z2 = true;
        }
        return z2 == z ? 1 : -1;
    }

    private a a(a aVar, boolean z, int i, int i2) {
        int b = aVar.b() + i;
        int c = aVar.c();
        while (true) {
            c += i2;
            if (!a(b, c) || this.b.get(b, c) != z) {
                break;
            }
            b += i;
        }
        int i3 = b - i;
        int i4 = c - i2;
        while (a(i3, i4) && this.b.get(i3, i4) == z) {
            i3 += i;
        }
        int i5 = i3 - i;
        while (a(i5, i4) && this.b.get(i5, i4) == z) {
            i4 += i2;
        }
        return new a(i5, i4 - i2);
    }

    private static ResultPoint[] a(ResultPoint[] resultPointArr, float f, float f2) {
        float f3 = f2 / (f * 2.0f);
        float x = resultPointArr[0].getX() - resultPointArr[2].getX();
        float y = resultPointArr[0].getY() - resultPointArr[2].getY();
        float x2 = (resultPointArr[0].getX() + resultPointArr[2].getX()) / 2.0f;
        float y2 = (resultPointArr[0].getY() + resultPointArr[2].getY()) / 2.0f;
        float f4 = x * f3;
        float f5 = y * f3;
        ResultPoint resultPoint = new ResultPoint(x2 + f4, y2 + f5);
        ResultPoint resultPoint2 = new ResultPoint(x2 - f4, y2 - f5);
        float x3 = resultPointArr[1].getX() - resultPointArr[3].getX();
        float y3 = resultPointArr[1].getY() - resultPointArr[3].getY();
        float x4 = (resultPointArr[1].getX() + resultPointArr[3].getX()) / 2.0f;
        float y4 = (resultPointArr[1].getY() + resultPointArr[3].getY()) / 2.0f;
        float f6 = x3 * f3;
        float f7 = f3 * y3;
        return new ResultPoint[]{resultPoint, new ResultPoint(x4 + f6, y4 + f7), resultPoint2, new ResultPoint(x4 - f6, y4 - f7)};
    }

    private boolean a(int i, int i2) {
        return i >= 0 && i < this.b.getWidth() && i2 > 0 && i2 < this.b.getHeight();
    }

    private boolean a(ResultPoint resultPoint) {
        return a(MathUtils.round(resultPoint.getX()), MathUtils.round(resultPoint.getY()));
    }

    private static float b(a aVar, a aVar2) {
        return MathUtils.distance(aVar.b(), aVar.c(), aVar2.b(), aVar2.c());
    }

    private static float a(ResultPoint resultPoint, ResultPoint resultPoint2) {
        return MathUtils.distance(resultPoint.getX(), resultPoint.getY(), resultPoint2.getX(), resultPoint2.getY());
    }

    private int b() {
        if (this.c) {
            return (this.d * 4) + 11;
        }
        int i = this.d;
        return i <= 4 ? (i * 4) + 15 : (i * 4) + ((((i - 4) / 8) + 1) * 2) + 15;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public static final class a {
        private final int a;
        private final int b;

        ResultPoint a() {
            return new ResultPoint(b(), c());
        }

        a(int i, int i2) {
            this.a = i;
            this.b = i2;
        }

        int b() {
            return this.a;
        }

        int c() {
            return this.b;
        }

        public String toString() {
            return "<" + this.a + ' ' + this.b + Typography.greater;
        }
    }
}
