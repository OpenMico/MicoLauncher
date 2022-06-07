package com.google.zxing.qrcode.detector;

import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.ResultPoint;
import com.google.zxing.ResultPointCallback;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.DetectorResult;
import com.google.zxing.common.GridSampler;
import com.google.zxing.common.PerspectiveTransform;
import com.google.zxing.common.detector.MathUtils;
import com.google.zxing.qrcode.decoder.Version;
import java.util.Map;

/* loaded from: classes2.dex */
public class Detector {
    private final BitMatrix a;
    private ResultPointCallback b;

    public Detector(BitMatrix bitMatrix) {
        this.a = bitMatrix;
    }

    protected final BitMatrix getImage() {
        return this.a;
    }

    protected final ResultPointCallback getResultPointCallback() {
        return this.b;
    }

    public DetectorResult detect() throws NotFoundException, FormatException {
        return detect(null);
    }

    public final DetectorResult detect(Map<DecodeHintType, ?> map) throws NotFoundException, FormatException {
        this.b = map == null ? null : (ResultPointCallback) map.get(DecodeHintType.NEED_RESULT_POINT_CALLBACK);
        return processFinderPatternInfo(new FinderPatternFinder(this.a, this.b).b(map));
    }

    protected final DetectorResult processFinderPatternInfo(FinderPatternInfo finderPatternInfo) throws NotFoundException, FormatException {
        FinderPattern topLeft = finderPatternInfo.getTopLeft();
        FinderPattern topRight = finderPatternInfo.getTopRight();
        FinderPattern bottomLeft = finderPatternInfo.getBottomLeft();
        float calculateModuleSize = calculateModuleSize(topLeft, topRight, bottomLeft);
        if (calculateModuleSize >= 1.0f) {
            int a = a(topLeft, topRight, bottomLeft, calculateModuleSize);
            Version provisionalVersionForDimension = Version.getProvisionalVersionForDimension(a);
            int dimensionForVersion = provisionalVersionForDimension.getDimensionForVersion() - 7;
            AlignmentPattern alignmentPattern = null;
            if (provisionalVersionForDimension.getAlignmentPatternCenters().length > 0) {
                float x = (topRight.getX() - topLeft.getX()) + bottomLeft.getX();
                float y = (topRight.getY() - topLeft.getY()) + bottomLeft.getY();
                float f = 1.0f - (3.0f / dimensionForVersion);
                int x2 = (int) (topLeft.getX() + ((x - topLeft.getX()) * f));
                int y2 = (int) (topLeft.getY() + (f * (y - topLeft.getY())));
                for (int i = 4; i <= 16; i <<= 1) {
                    try {
                        alignmentPattern = findAlignmentInRegion(calculateModuleSize, x2, y2, i);
                        break;
                    } catch (NotFoundException unused) {
                    }
                }
            }
            return new DetectorResult(a(this.a, a(topLeft, topRight, bottomLeft, alignmentPattern, a), a), alignmentPattern == null ? new ResultPoint[]{bottomLeft, topLeft, topRight} : new ResultPoint[]{bottomLeft, topLeft, topRight, alignmentPattern});
        }
        throw NotFoundException.getNotFoundInstance();
    }

    private static PerspectiveTransform a(ResultPoint resultPoint, ResultPoint resultPoint2, ResultPoint resultPoint3, ResultPoint resultPoint4, int i) {
        float f;
        float f2;
        float f3;
        float f4 = i - 3.5f;
        if (resultPoint4 != null) {
            f3 = f4 - 3.0f;
            f2 = resultPoint4.getX();
            f = resultPoint4.getY();
        } else {
            f2 = (resultPoint2.getX() - resultPoint.getX()) + resultPoint3.getX();
            f = (resultPoint2.getY() - resultPoint.getY()) + resultPoint3.getY();
            f3 = f4;
        }
        return PerspectiveTransform.quadrilateralToQuadrilateral(3.5f, 3.5f, f4, 3.5f, f3, f3, 3.5f, f4, resultPoint.getX(), resultPoint.getY(), resultPoint2.getX(), resultPoint2.getY(), f2, f, resultPoint3.getX(), resultPoint3.getY());
    }

    private static BitMatrix a(BitMatrix bitMatrix, PerspectiveTransform perspectiveTransform, int i) throws NotFoundException {
        return GridSampler.getInstance().sampleGrid(bitMatrix, i, i, perspectiveTransform);
    }

    private static int a(ResultPoint resultPoint, ResultPoint resultPoint2, ResultPoint resultPoint3, float f) throws NotFoundException {
        int round = ((MathUtils.round(ResultPoint.distance(resultPoint, resultPoint2) / f) + MathUtils.round(ResultPoint.distance(resultPoint, resultPoint3) / f)) / 2) + 7;
        int i = round & 3;
        if (i == 0) {
            return round + 1;
        }
        switch (i) {
            case 2:
                return round - 1;
            case 3:
                throw NotFoundException.getNotFoundInstance();
            default:
                return round;
        }
    }

    protected final float calculateModuleSize(ResultPoint resultPoint, ResultPoint resultPoint2, ResultPoint resultPoint3) {
        return (a(resultPoint, resultPoint2) + a(resultPoint, resultPoint3)) / 2.0f;
    }

    private float a(ResultPoint resultPoint, ResultPoint resultPoint2) {
        float a = a((int) resultPoint.getX(), (int) resultPoint.getY(), (int) resultPoint2.getX(), (int) resultPoint2.getY());
        float a2 = a((int) resultPoint2.getX(), (int) resultPoint2.getY(), (int) resultPoint.getX(), (int) resultPoint.getY());
        return Float.isNaN(a) ? a2 / 7.0f : Float.isNaN(a2) ? a / 7.0f : (a + a2) / 14.0f;
    }

    private float a(int i, int i2, int i3, int i4) {
        float f;
        int i5;
        float f2;
        float b = b(i, i2, i3, i4);
        int i6 = i - (i3 - i);
        int i7 = 0;
        if (i6 < 0) {
            f = i / (i - i6);
            i5 = 0;
        } else if (i6 >= this.a.getWidth()) {
            f = ((this.a.getWidth() - 1) - i) / (i6 - i);
            i5 = this.a.getWidth() - 1;
        } else {
            i5 = i6;
            f = 1.0f;
        }
        float f3 = i2;
        int i8 = (int) (f3 - ((i4 - i2) * f));
        if (i8 < 0) {
            f2 = f3 / (i2 - i8);
        } else if (i8 >= this.a.getHeight()) {
            f2 = ((this.a.getHeight() - 1) - i2) / (i8 - i2);
            i7 = this.a.getHeight() - 1;
        } else {
            i7 = i8;
            f2 = 1.0f;
        }
        return (b + b(i, i2, (int) (i + ((i5 - i) * f2)), i7)) - 1.0f;
    }

    private float b(int i, int i2, int i3, int i4) {
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        Detector detector;
        boolean z;
        boolean z2 = true;
        boolean z3 = Math.abs(i4 - i2) > Math.abs(i3 - i);
        if (z3) {
            i5 = i;
            i7 = i2;
            i6 = i3;
            i8 = i4;
        } else {
            i7 = i;
            i5 = i2;
            i8 = i3;
            i6 = i4;
        }
        int abs = Math.abs(i8 - i7);
        int abs2 = Math.abs(i6 - i5);
        int i10 = 2;
        int i11 = (-abs) / 2;
        int i12 = -1;
        int i13 = i7 < i8 ? 1 : -1;
        if (i5 < i6) {
            i12 = 1;
        }
        int i14 = i8 + i13;
        int i15 = i5;
        int i16 = i11;
        int i17 = 0;
        int i18 = i7;
        while (true) {
            if (i18 == i14) {
                i9 = i14;
                break;
            }
            int i19 = z3 ? i15 : i18;
            int i20 = z3 ? i18 : i15;
            if (i17 == z2) {
                z3 = z3;
                i9 = i14;
                z = z2;
                detector = this;
            } else {
                detector = this;
                z3 = z3;
                i9 = i14;
                z = false;
            }
            if (z == detector.a.get(i19, i20)) {
                if (i17 == 2) {
                    return MathUtils.distance(i18, i15, i7, i5);
                }
                i17++;
            }
            i16 += abs2;
            if (i16 > 0) {
                if (i15 == i6) {
                    i10 = 2;
                    break;
                }
                i15 += i12;
                i16 -= abs;
            }
            i18 += i13;
            i14 = i9;
            z2 = true;
            i10 = 2;
        }
        if (i17 == i10) {
            return MathUtils.distance(i9, i6, i7, i5);
        }
        return Float.NaN;
    }

    protected final AlignmentPattern findAlignmentInRegion(float f, int i, int i2, float f2) throws NotFoundException {
        int i3 = (int) (f2 * f);
        int max = Math.max(0, i - i3);
        int min = Math.min(this.a.getWidth() - 1, i + i3) - max;
        float f3 = 3.0f * f;
        if (min >= f3) {
            int max2 = Math.max(0, i2 - i3);
            int min2 = Math.min(this.a.getHeight() - 1, i2 + i3) - max2;
            if (min2 >= f3) {
                return new a(this.a, max, max2, min, min2, f, this.b).a();
            }
            throw NotFoundException.getNotFoundInstance();
        }
        throw NotFoundException.getNotFoundInstance();
    }
}
