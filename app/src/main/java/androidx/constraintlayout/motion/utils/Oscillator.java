package androidx.constraintlayout.motion.utils;

import java.util.Arrays;

/* loaded from: classes.dex */
public class Oscillator {
    public static final int BOUNCE = 6;
    public static final int COS_WAVE = 5;
    public static final int REVERSE_SAW_WAVE = 4;
    public static final int SAW_WAVE = 3;
    public static final int SIN_WAVE = 0;
    public static final int SQUARE_WAVE = 1;
    public static String TAG = "Oscillator";
    public static final int TRIANGLE_WAVE = 2;
    double[] c;
    int d;
    float[] a = new float[0];
    double[] b = new double[0];
    double e = 6.283185307179586d;
    private boolean f = false;

    public String toString() {
        return "pos =" + Arrays.toString(this.b) + " period=" + Arrays.toString(this.a);
    }

    public void setType(int i) {
        this.d = i;
    }

    public void addPoint(double d, float f) {
        int length = this.a.length + 1;
        int binarySearch = Arrays.binarySearch(this.b, d);
        if (binarySearch < 0) {
            binarySearch = (-binarySearch) - 1;
        }
        this.b = Arrays.copyOf(this.b, length);
        this.a = Arrays.copyOf(this.a, length);
        this.c = new double[length];
        double[] dArr = this.b;
        System.arraycopy(dArr, binarySearch, dArr, binarySearch + 1, (length - binarySearch) - 1);
        this.b[binarySearch] = d;
        this.a[binarySearch] = f;
        this.f = false;
    }

    public void normalize() {
        int i = 0;
        double d = 0.0d;
        while (true) {
            float[] fArr = this.a;
            if (i >= fArr.length) {
                break;
            }
            d += fArr[i];
            i++;
        }
        double d2 = 0.0d;
        int i2 = 1;
        while (true) {
            float[] fArr2 = this.a;
            if (i2 >= fArr2.length) {
                break;
            }
            int i3 = i2 - 1;
            double[] dArr = this.b;
            d2 += (dArr[i2] - dArr[i3]) * ((fArr2[i3] + fArr2[i2]) / 2.0f);
            i2++;
        }
        int i4 = 0;
        while (true) {
            float[] fArr3 = this.a;
            if (i4 >= fArr3.length) {
                break;
            }
            fArr3[i4] = (float) (fArr3[i4] * (d / d2));
            i4++;
        }
        this.c[0] = 0.0d;
        int i5 = 1;
        while (true) {
            float[] fArr4 = this.a;
            if (i5 < fArr4.length) {
                int i6 = i5 - 1;
                double[] dArr2 = this.b;
                double d3 = dArr2[i5] - dArr2[i6];
                double[] dArr3 = this.c;
                dArr3[i5] = dArr3[i6] + (d3 * ((fArr4[i6] + fArr4[i5]) / 2.0f));
                i5++;
            } else {
                this.f = true;
                return;
            }
        }
    }

    double a(double d) {
        if (d < 0.0d) {
            d = 0.0d;
        } else if (d > 1.0d) {
            d = 1.0d;
        }
        int binarySearch = Arrays.binarySearch(this.b, d);
        if (binarySearch > 0) {
            return 1.0d;
        }
        if (binarySearch == 0) {
            return 0.0d;
        }
        int i = (-binarySearch) - 1;
        float[] fArr = this.a;
        int i2 = i - 1;
        double d2 = fArr[i] - fArr[i2];
        double[] dArr = this.b;
        double d3 = d2 / (dArr[i] - dArr[i2]);
        return this.c[i2] + ((fArr[i2] - (dArr[i2] * d3)) * (d - dArr[i2])) + ((d3 * ((d * d) - (dArr[i2] * dArr[i2]))) / 2.0d);
    }

    public double getValue(double d) {
        switch (this.d) {
            case 1:
                return Math.signum(0.5d - (a(d) % 1.0d));
            case 2:
                return 1.0d - Math.abs((((a(d) * 4.0d) + 1.0d) % 4.0d) - 2.0d);
            case 3:
                return (((a(d) * 2.0d) + 1.0d) % 2.0d) - 1.0d;
            case 4:
                return 1.0d - (((a(d) * 2.0d) + 1.0d) % 2.0d);
            case 5:
                return Math.cos(this.e * a(d));
            case 6:
                double abs = 1.0d - Math.abs(((a(d) * 4.0d) % 4.0d) - 2.0d);
                return 1.0d - (abs * abs);
            default:
                return Math.sin(this.e * a(d));
        }
    }

    double b(double d) {
        if (d <= 0.0d) {
            d = 1.0E-5d;
        } else if (d >= 1.0d) {
            d = 0.999999d;
        }
        int binarySearch = Arrays.binarySearch(this.b, d);
        if (binarySearch > 0 || binarySearch == 0) {
            return 0.0d;
        }
        int i = (-binarySearch) - 1;
        float[] fArr = this.a;
        int i2 = i - 1;
        double d2 = fArr[i] - fArr[i2];
        double[] dArr = this.b;
        double d3 = d2 / (dArr[i] - dArr[i2]);
        return (fArr[i2] - (d3 * dArr[i2])) + (d * d3);
    }

    public double getSlope(double d) {
        switch (this.d) {
            case 1:
                return 0.0d;
            case 2:
                return b(d) * 4.0d * Math.signum((((a(d) * 4.0d) + 3.0d) % 4.0d) - 2.0d);
            case 3:
                return b(d) * 2.0d;
            case 4:
                return (-b(d)) * 2.0d;
            case 5:
                return (-this.e) * b(d) * Math.sin(this.e * a(d));
            case 6:
                return b(d) * 4.0d * ((((a(d) * 4.0d) + 2.0d) % 4.0d) - 2.0d);
            default:
                return this.e * b(d) * Math.cos(this.e * a(d));
        }
    }
}
