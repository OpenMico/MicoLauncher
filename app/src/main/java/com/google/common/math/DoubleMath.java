package com.google.common.math;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.primitives.Booleans;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Iterator;

@GwtCompatible(emulated = true)
/* loaded from: classes2.dex */
public final class DoubleMath {
    private static final double b = Math.log(2.0d);
    @VisibleForTesting
    static final double[] a = {1.0d, 2.0922789888E13d, 2.631308369336935E35d, 1.2413915592536073E61d, 1.2688693218588417E89d, 7.156945704626381E118d, 9.916779348709496E149d, 1.974506857221074E182d, 3.856204823625804E215d, 5.5502938327393044E249d, 4.7147236359920616E284d};

    @GwtIncompatible
    static double a(double d, RoundingMode roundingMode) {
        if (a.b(d)) {
            switch (AnonymousClass1.a[roundingMode.ordinal()]) {
                case 1:
                    b.a(isMathematicalInteger(d));
                    return d;
                case 2:
                    return (d >= 0.0d || isMathematicalInteger(d)) ? d : ((long) d) - 1;
                case 3:
                    return (d <= 0.0d || isMathematicalInteger(d)) ? d : ((long) d) + 1;
                case 4:
                    return d;
                case 5:
                    if (isMathematicalInteger(d)) {
                        return d;
                    }
                    return ((long) d) + (d > 0.0d ? 1 : -1);
                case 6:
                    return Math.rint(d);
                case 7:
                    double rint = Math.rint(d);
                    return Math.abs(d - rint) == 0.5d ? d + Math.copySign(0.5d, d) : rint;
                case 8:
                    double rint2 = Math.rint(d);
                    return Math.abs(d - rint2) == 0.5d ? d : rint2;
                default:
                    throw new AssertionError();
            }
        } else {
            throw new ArithmeticException("input is infinite or NaN");
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.google.common.math.DoubleMath$1  reason: invalid class name */
    /* loaded from: classes2.dex */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] a = new int[RoundingMode.values().length];

        static {
            try {
                a[RoundingMode.UNNECESSARY.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                a[RoundingMode.FLOOR.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                a[RoundingMode.CEILING.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                a[RoundingMode.DOWN.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                a[RoundingMode.UP.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                a[RoundingMode.HALF_EVEN.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                a[RoundingMode.HALF_UP.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                a[RoundingMode.HALF_DOWN.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
        }
    }

    @GwtIncompatible
    public static int roundToInt(double d, RoundingMode roundingMode) {
        double a2 = a(d, roundingMode);
        boolean z = true;
        boolean z2 = a2 > -2.147483649E9d;
        if (a2 >= 2.147483648E9d) {
            z = false;
        }
        b.b(z2 & z);
        return (int) a2;
    }

    @GwtIncompatible
    public static long roundToLong(double d, RoundingMode roundingMode) {
        double a2 = a(d, roundingMode);
        boolean z = true;
        boolean z2 = (-9.223372036854776E18d) - a2 < 1.0d;
        if (a2 >= 9.223372036854776E18d) {
            z = false;
        }
        b.b(z2 & z);
        return (long) a2;
    }

    @GwtIncompatible
    public static BigInteger roundToBigInteger(double d, RoundingMode roundingMode) {
        double a2 = a(d, roundingMode);
        boolean z = true;
        boolean z2 = (-9.223372036854776E18d) - a2 < 1.0d;
        if (a2 >= 9.223372036854776E18d) {
            z = false;
        }
        if (z2 && z) {
            return BigInteger.valueOf((long) a2);
        }
        BigInteger shiftLeft = BigInteger.valueOf(a.a(a2)).shiftLeft(Math.getExponent(a2) - 52);
        return a2 < 0.0d ? shiftLeft.negate() : shiftLeft;
    }

    @GwtIncompatible
    public static boolean isPowerOfTwo(double d) {
        if (d <= 0.0d || !a.b(d)) {
            return false;
        }
        long a2 = a.a(d);
        return (a2 & (a2 - 1)) == 0;
    }

    public static double log2(double d) {
        return Math.log(d) / b;
    }

    @GwtIncompatible
    public static int log2(double d, RoundingMode roundingMode) {
        boolean z = false;
        Preconditions.checkArgument(d > 0.0d && a.b(d), "x must be positive and finite");
        int exponent = Math.getExponent(d);
        if (!a.c(d)) {
            return log2(d * 4.503599627370496E15d, roundingMode) - 52;
        }
        switch (AnonymousClass1.a[roundingMode.ordinal()]) {
            case 1:
                b.a(isPowerOfTwo(d));
                break;
            case 2:
                break;
            case 3:
                z = !isPowerOfTwo(d);
                break;
            case 4:
                if (exponent < 0) {
                    z = true;
                }
                z &= !isPowerOfTwo(d);
                break;
            case 5:
                if (exponent >= 0) {
                    z = true;
                }
                z &= !isPowerOfTwo(d);
                break;
            case 6:
            case 7:
            case 8:
                double d2 = a.d(d);
                if (d2 * d2 > 2.0d) {
                    z = true;
                    break;
                }
                break;
            default:
                throw new AssertionError();
        }
        return z ? exponent + 1 : exponent;
    }

    @GwtIncompatible
    public static boolean isMathematicalInteger(double d) {
        return a.b(d) && (d == 0.0d || 52 - Long.numberOfTrailingZeros(a.a(d)) <= Math.getExponent(d));
    }

    public static double factorial(int i) {
        b.b("n", i);
        if (i > 170) {
            return Double.POSITIVE_INFINITY;
        }
        double d = 1.0d;
        int i2 = i & (-16);
        while (true) {
            i2++;
            if (i2 > i) {
                return d * a[i >> 4];
            }
            d *= i2;
        }
    }

    public static boolean fuzzyEquals(double d, double d2, double d3) {
        b.a("tolerance", d3);
        return Math.copySign(d - d2, 1.0d) <= d3 || d == d2 || (Double.isNaN(d) && Double.isNaN(d2));
    }

    public static int fuzzyCompare(double d, double d2, double d3) {
        if (fuzzyEquals(d, d2, d3)) {
            return 0;
        }
        if (d < d2) {
            return -1;
        }
        if (d > d2) {
            return 1;
        }
        return Booleans.compare(Double.isNaN(d), Double.isNaN(d2));
    }

    @GwtIncompatible
    @Deprecated
    public static double mean(double... dArr) {
        Preconditions.checkArgument(dArr.length > 0, "Cannot take mean of 0 values");
        double a2 = a(dArr[0]);
        long j = 1;
        for (int i = 1; i < dArr.length; i++) {
            a(dArr[i]);
            j++;
            a2 += (dArr[i] - a2) / j;
        }
        return a2;
    }

    @Deprecated
    public static double mean(int... iArr) {
        Preconditions.checkArgument(iArr.length > 0, "Cannot take mean of 0 values");
        long j = 0;
        for (int i : iArr) {
            j += i;
        }
        return j / iArr.length;
    }

    @Deprecated
    public static double mean(long... jArr) {
        Preconditions.checkArgument(jArr.length > 0, "Cannot take mean of 0 values");
        double d = jArr[0];
        long j = 1;
        for (int i = 1; i < jArr.length; i++) {
            j++;
            d += (jArr[i] - d) / j;
        }
        return d;
    }

    @GwtIncompatible
    @Deprecated
    public static double mean(Iterable<? extends Number> iterable) {
        return mean(iterable.iterator());
    }

    @GwtIncompatible
    @Deprecated
    public static double mean(Iterator<? extends Number> it) {
        Preconditions.checkArgument(it.hasNext(), "Cannot take mean of 0 values");
        double a2 = a(((Number) it.next()).doubleValue());
        long j = 1;
        while (it.hasNext()) {
            j++;
            a2 += (a(((Number) it.next()).doubleValue()) - a2) / j;
        }
        return a2;
    }

    @CanIgnoreReturnValue
    @GwtIncompatible
    private static double a(double d) {
        Preconditions.checkArgument(a.b(d));
        return d;
    }

    private DoubleMath() {
    }
}
