package androidx.constraintlayout.motion.utils;

import android.util.Log;
import java.util.Arrays;

/* loaded from: classes.dex */
public class Easing {
    String b = "identity";
    static Easing a = new Easing();
    public static String[] NAMED_EASING = {"standard", "accelerate", "decelerate", "linear"};

    public double get(double d) {
        return d;
    }

    public double getDiff(double d) {
        return 1.0d;
    }

    public static Easing getInterpolator(String str) {
        if (str == null) {
            return null;
        }
        if (str.startsWith("cubic")) {
            return new a(str);
        }
        char c = 65535;
        int hashCode = str.hashCode();
        if (hashCode != -1354466595) {
            if (hashCode != -1263948740) {
                if (hashCode != -1102672091) {
                    if (hashCode == 1312628413 && str.equals("standard")) {
                        c = 0;
                    }
                } else if (str.equals("linear")) {
                    c = 3;
                }
            } else if (str.equals("decelerate")) {
                c = 2;
            }
        } else if (str.equals("accelerate")) {
            c = 1;
        }
        switch (c) {
            case 0:
                return new a("cubic(0.4, 0.0, 0.2, 1)");
            case 1:
                return new a("cubic(0.4, 0.05, 0.8, 0.7)");
            case 2:
                return new a("cubic(0.0, 0.0, 0.2, 0.95)");
            case 3:
                return new a("cubic(1, 1, 0, 0)");
            default:
                Log.e("ConstraintSet", "transitionEasing syntax error syntax:transitionEasing=\"cubic(1.0,0.5,0.0,0.6)\" or " + Arrays.toString(NAMED_EASING));
                return a;
        }
    }

    public String toString() {
        return this.b;
    }

    /* loaded from: classes.dex */
    static class a extends Easing {
        private static double g = 0.01d;
        private static double h = 1.0E-4d;
        double c;
        double d;
        double e;
        double f;

        a(String str) {
            this.b = str;
            int indexOf = str.indexOf(40);
            int indexOf2 = str.indexOf(44, indexOf);
            this.c = Double.parseDouble(str.substring(indexOf + 1, indexOf2).trim());
            int i = indexOf2 + 1;
            int indexOf3 = str.indexOf(44, i);
            this.d = Double.parseDouble(str.substring(i, indexOf3).trim());
            int i2 = indexOf3 + 1;
            int indexOf4 = str.indexOf(44, i2);
            this.e = Double.parseDouble(str.substring(i2, indexOf4).trim());
            int i3 = indexOf4 + 1;
            this.f = Double.parseDouble(str.substring(i3, str.indexOf(41, i3)).trim());
        }

        private double a(double d) {
            double d2 = 1.0d - d;
            double d3 = 3.0d * d2;
            return (this.c * d2 * d3 * d) + (this.e * d3 * d * d) + (d * d * d);
        }

        private double b(double d) {
            double d2 = 1.0d - d;
            double d3 = 3.0d * d2;
            return (this.d * d2 * d3 * d) + (this.f * d3 * d * d) + (d * d * d);
        }

        @Override // androidx.constraintlayout.motion.utils.Easing
        public double getDiff(double d) {
            double d2 = 0.5d;
            double d3 = 0.5d;
            while (d2 > h) {
                d2 *= 0.5d;
                d3 = a(d3) < d ? d3 + d2 : d3 - d2;
            }
            double d4 = d3 - d2;
            double d5 = d3 + d2;
            return (b(d5) - b(d4)) / (a(d5) - a(d4));
        }

        @Override // androidx.constraintlayout.motion.utils.Easing
        public double get(double d) {
            if (d <= 0.0d) {
                return 0.0d;
            }
            if (d >= 1.0d) {
                return 1.0d;
            }
            double d2 = 0.5d;
            double d3 = 0.5d;
            while (d2 > g) {
                d2 *= 0.5d;
                d3 = a(d3) < d ? d3 + d2 : d3 - d2;
            }
            double d4 = d3 - d2;
            double a = a(d4);
            double d5 = d3 + d2;
            double a2 = a(d5);
            double b = b(d4);
            return (((b(d5) - b) * (d - a)) / (a2 - a)) + b;
        }
    }
}
