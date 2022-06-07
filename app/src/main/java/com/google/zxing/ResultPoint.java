package com.google.zxing;

import com.google.zxing.common.detector.MathUtils;
import io.netty.util.internal.StringUtil;

/* loaded from: classes2.dex */
public class ResultPoint {
    private final float a;
    private final float b;

    public ResultPoint(float f, float f2) {
        this.a = f;
        this.b = f2;
    }

    public final float getX() {
        return this.a;
    }

    public final float getY() {
        return this.b;
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof ResultPoint)) {
            return false;
        }
        ResultPoint resultPoint = (ResultPoint) obj;
        return this.a == resultPoint.a && this.b == resultPoint.b;
    }

    public final int hashCode() {
        return (Float.floatToIntBits(this.a) * 31) + Float.floatToIntBits(this.b);
    }

    public final String toString() {
        return "(" + this.a + StringUtil.COMMA + this.b + ')';
    }

    public static void orderBestPatterns(ResultPoint[] resultPointArr) {
        ResultPoint resultPoint;
        ResultPoint resultPoint2;
        ResultPoint resultPoint3;
        float distance = distance(resultPointArr[0], resultPointArr[1]);
        float distance2 = distance(resultPointArr[1], resultPointArr[2]);
        float distance3 = distance(resultPointArr[0], resultPointArr[2]);
        if (distance2 >= distance && distance2 >= distance3) {
            resultPoint3 = resultPointArr[0];
            resultPoint2 = resultPointArr[1];
            resultPoint = resultPointArr[2];
        } else if (distance3 < distance2 || distance3 < distance) {
            resultPoint3 = resultPointArr[2];
            resultPoint2 = resultPointArr[0];
            resultPoint = resultPointArr[1];
        } else {
            resultPoint3 = resultPointArr[1];
            resultPoint2 = resultPointArr[0];
            resultPoint = resultPointArr[2];
        }
        if (a(resultPoint2, resultPoint3, resultPoint) < 0.0f) {
            resultPoint = resultPoint2;
            resultPoint2 = resultPoint;
        }
        resultPointArr[0] = resultPoint2;
        resultPointArr[1] = resultPoint3;
        resultPointArr[2] = resultPoint;
    }

    public static float distance(ResultPoint resultPoint, ResultPoint resultPoint2) {
        return MathUtils.distance(resultPoint.a, resultPoint.b, resultPoint2.a, resultPoint2.b);
    }

    private static float a(ResultPoint resultPoint, ResultPoint resultPoint2, ResultPoint resultPoint3) {
        float f = resultPoint2.a;
        float f2 = resultPoint2.b;
        return ((resultPoint3.a - f) * (resultPoint.b - f2)) - ((resultPoint3.b - f2) * (resultPoint.a - f));
    }
}
