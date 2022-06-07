package org.apache.commons.lang3.math;

import org.apache.commons.lang3.Validate;

/* loaded from: classes5.dex */
public class IEEE754rUtils {
    public static double min(double... dArr) {
        if (dArr != null) {
            Validate.isTrue(dArr.length != 0, "Array cannot be empty.", new Object[0]);
            double d = dArr[0];
            for (int i = 1; i < dArr.length; i++) {
                d = min(dArr[i], d);
            }
            return d;
        }
        throw new IllegalArgumentException("The Array must not be null");
    }

    public static float min(float... fArr) {
        if (fArr != null) {
            Validate.isTrue(fArr.length != 0, "Array cannot be empty.", new Object[0]);
            float f = fArr[0];
            for (int i = 1; i < fArr.length; i++) {
                f = min(fArr[i], f);
            }
            return f;
        }
        throw new IllegalArgumentException("The Array must not be null");
    }

    public static double min(double d, double d2, double d3) {
        return min(min(d, d2), d3);
    }

    public static double min(double d, double d2) {
        return Double.isNaN(d) ? d2 : Double.isNaN(d2) ? d : Math.min(d, d2);
    }

    public static float min(float f, float f2, float f3) {
        return min(min(f, f2), f3);
    }

    public static float min(float f, float f2) {
        return Float.isNaN(f) ? f2 : Float.isNaN(f2) ? f : Math.min(f, f2);
    }

    public static double max(double... dArr) {
        if (dArr != null) {
            Validate.isTrue(dArr.length != 0, "Array cannot be empty.", new Object[0]);
            double d = dArr[0];
            for (int i = 1; i < dArr.length; i++) {
                d = max(dArr[i], d);
            }
            return d;
        }
        throw new IllegalArgumentException("The Array must not be null");
    }

    public static float max(float... fArr) {
        if (fArr != null) {
            Validate.isTrue(fArr.length != 0, "Array cannot be empty.", new Object[0]);
            float f = fArr[0];
            for (int i = 1; i < fArr.length; i++) {
                f = max(fArr[i], f);
            }
            return f;
        }
        throw new IllegalArgumentException("The Array must not be null");
    }

    public static double max(double d, double d2, double d3) {
        return max(max(d, d2), d3);
    }

    public static double max(double d, double d2) {
        return Double.isNaN(d) ? d2 : Double.isNaN(d2) ? d : Math.max(d, d2);
    }

    public static float max(float f, float f2, float f3) {
        return max(max(f, f2), f3);
    }

    public static float max(float f, float f2) {
        return Float.isNaN(f) ? f2 : Float.isNaN(f2) ? f : Math.max(f, f2);
    }
}
