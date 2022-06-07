package com.google.common.math;

import com.alibaba.fastjson.asm.Opcodes;
import com.google.android.exoplayer2.C;
import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.primitives.Longs;
import com.google.common.primitives.UnsignedLongs;
import com.umeng.analytics.pro.ai;
import java.math.RoundingMode;
import okhttp3.internal.connection.RealConnection;

@GwtCompatible(emulated = true)
/* loaded from: classes2.dex */
public final class LongMath {
    @VisibleForTesting
    static final byte[] a = {19, 18, 18, 18, 18, 17, 17, 17, 16, 16, 16, 15, 15, 15, 15, 14, 14, 14, 13, 13, 13, 12, 12, 12, 12, 11, 11, 11, 10, 10, 10, 9, 9, 9, 9, 8, 8, 8, 7, 7, 7, 6, 6, 6, 6, 5, 5, 5, 4, 4, 4, 3, 3, 3, 3, 2, 2, 2, 1, 1, 1, 0, 0, 0};
    @VisibleForTesting
    @GwtIncompatible
    static final long[] b = {1, 10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000, C.NANOS_PER_SECOND, RealConnection.IDLE_CONNECTION_HEALTHY_NS, 100000000000L, 1000000000000L, 10000000000000L, 100000000000000L, 1000000000000000L, 10000000000000000L, 100000000000000000L, 1000000000000000000L};
    @VisibleForTesting
    @GwtIncompatible
    static final long[] c = {3, 31, 316, 3162, 31622, 316227, 3162277, 31622776, 316227766, 3162277660L, 31622776601L, 316227766016L, 3162277660168L, 31622776601683L, 316227766016837L, 3162277660168379L, 31622776601683793L, 316227766016837933L, 3162277660168379331L};
    static final long[] d = {1, 1, 2, 6, 24, 120, 720, 5040, 40320, 362880, 3628800, 39916800, 479001600, 6227020800L, 87178291200L, 1307674368000L, 20922789888000L, 355687428096000L, 6402373705728000L, 121645100408832000L, 2432902008176640000L};
    static final int[] e = {Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, 3810779, 121977, 16175, 4337, 1733, 887, 534, 361, 265, 206, Opcodes.RET, 143, 125, 111, 101, 94, 88, 83, 79, 76, 74, 72, 70, 69, 68, 67, 67, 66, 66, 66, 66};
    @VisibleForTesting
    static final int[] f = {Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, 2642246, 86251, 11724, 3218, 1313, 684, 419, 287, 214, Opcodes.RET, 139, 119, 105, 95, 87, 81, 76, 73, 70, 68, 66, 64, 63, 62, 62, 61, 61, 61};
    private static final long[][] g = {new long[]{291830, 126401071349994536L}, new long[]{885594168, 725270293939359937L, 3569819667048198375L}, new long[]{273919523040L, 15, 7363882082L, 992620450144556L}, new long[]{47636622961200L, 2, 2570940, 211991001, 3749873356L}, new long[]{7999252175582850L, 2, 4130806001517L, 149795463772692060L, 186635894390467037L, 3967304179347715805L}, new long[]{585226005592931976L, 2, 123635709730000L, 9233062284813009L, 43835965440333360L, 761179012939631437L, 1263739024124850375L}, new long[]{Long.MAX_VALUE, 2, 325, 9375, 28178, 450775, 9780504, 1795265022}};

    @VisibleForTesting
    static int a(long j, long j2) {
        return (int) ((~(~(j - j2))) >>> 63);
    }

    static boolean b(long j) {
        return ((long) ((int) j)) == j;
    }

    public static boolean isPowerOfTwo(long j) {
        boolean z = true;
        boolean z2 = j > 0;
        if ((j & (j - 1)) != 0) {
            z = false;
        }
        return z2 & z;
    }

    public static long mean(long j, long j2) {
        return (j & j2) + ((j ^ j2) >> 1);
    }

    @Beta
    public static long saturatedAdd(long j, long j2) {
        long j3 = j + j2;
        boolean z = true;
        boolean z2 = (j2 ^ j) < 0;
        if ((j ^ j3) < 0) {
            z = false;
        }
        return z2 | z ? j3 : ((j3 >>> 63) ^ 1) + Long.MAX_VALUE;
    }

    @Beta
    public static long saturatedSubtract(long j, long j2) {
        long j3 = j - j2;
        boolean z = true;
        boolean z2 = (j2 ^ j) >= 0;
        if ((j ^ j3) < 0) {
            z = false;
        }
        return z2 | z ? j3 : ((j3 >>> 63) ^ 1) + Long.MAX_VALUE;
    }

    @Beta
    public static long ceilingPowerOfTwo(long j) {
        b.a("x", j);
        if (j <= Longs.MAX_POWER_OF_TWO) {
            return 1 << (-Long.numberOfLeadingZeros(j - 1));
        }
        throw new ArithmeticException("ceilingPowerOfTwo(" + j + ") is not representable as a long");
    }

    @Beta
    public static long floorPowerOfTwo(long j) {
        b.a("x", j);
        return 1 << (63 - Long.numberOfLeadingZeros(j));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.google.common.math.LongMath$1  reason: invalid class name */
    /* loaded from: classes2.dex */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] a = new int[RoundingMode.values().length];

        static {
            try {
                a[RoundingMode.UNNECESSARY.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                a[RoundingMode.DOWN.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                a[RoundingMode.FLOOR.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                a[RoundingMode.UP.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                a[RoundingMode.CEILING.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                a[RoundingMode.HALF_DOWN.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                a[RoundingMode.HALF_UP.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                a[RoundingMode.HALF_EVEN.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static int log2(long j, RoundingMode roundingMode) {
        b.a("x", j);
        switch (AnonymousClass1.a[roundingMode.ordinal()]) {
            case 1:
                b.a(isPowerOfTwo(j));
                break;
            case 2:
            case 3:
                break;
            case 4:
            case 5:
                return 64 - Long.numberOfLeadingZeros(j - 1);
            case 6:
            case 7:
            case 8:
                int numberOfLeadingZeros = Long.numberOfLeadingZeros(j);
                return (63 - numberOfLeadingZeros) + a((-5402926248376769404) >>> numberOfLeadingZeros, j);
            default:
                throw new AssertionError("impossible");
        }
        return 63 - Long.numberOfLeadingZeros(j);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @GwtIncompatible
    public static int log10(long j, RoundingMode roundingMode) {
        b.a("x", j);
        int a2 = a(j);
        long j2 = b[a2];
        switch (AnonymousClass1.a[roundingMode.ordinal()]) {
            case 1:
                b.a(j == j2);
                break;
            case 2:
            case 3:
                break;
            case 4:
            case 5:
                return a2 + a(j2, j);
            case 6:
            case 7:
            case 8:
                return a2 + a(c[a2], j);
            default:
                throw new AssertionError();
        }
        return a2;
    }

    @GwtIncompatible
    static int a(long j) {
        byte b2 = a[Long.numberOfLeadingZeros(j)];
        return b2 - a(j, b[b2]);
    }

    @GwtIncompatible
    public static long pow(long j, int i) {
        b.b("exponent", i);
        if (-2 > j || j > 2) {
            long j2 = j;
            long j3 = 1;
            while (true) {
                switch (i) {
                    case 0:
                        return j3;
                    case 1:
                        return j3 * j2;
                    default:
                        j3 *= (i & 1) == 0 ? 1L : j2;
                        j2 *= j2;
                        i >>= 1;
                }
            }
        } else {
            switch ((int) j) {
                case -2:
                    if (i < 64) {
                        return (i & 1) == 0 ? 1 << i : -(1 << i);
                    }
                    return 0L;
                case -1:
                    return (i & 1) == 0 ? 1L : -1L;
                case 0:
                    return i == 0 ? 1L : 0L;
                case 1:
                    return 1L;
                case 2:
                    if (i < 64) {
                        return 1 << i;
                    }
                    return 0L;
                default:
                    throw new AssertionError();
            }
        }
    }

    @GwtIncompatible
    public static long sqrt(long j, RoundingMode roundingMode) {
        b.b("x", j);
        if (b(j)) {
            return IntMath.sqrt((int) j, roundingMode);
        }
        long sqrt = (long) Math.sqrt(j);
        long j2 = sqrt * sqrt;
        boolean z = true;
        int i = 1;
        switch (AnonymousClass1.a[roundingMode.ordinal()]) {
            case 1:
                if (j2 != j) {
                    z = false;
                }
                b.a(z);
                return sqrt;
            case 2:
            case 3:
                return j < j2 ? sqrt - 1 : sqrt;
            case 4:
            case 5:
                return j > j2 ? sqrt + 1 : sqrt;
            case 6:
            case 7:
            case 8:
                if (j >= j2) {
                    i = 0;
                }
                long j3 = sqrt - i;
                return j3 + a((j3 * j3) + j3, j);
            default:
                throw new AssertionError();
        }
    }

    @GwtIncompatible
    public static long divide(long j, long j2, RoundingMode roundingMode) {
        Preconditions.checkNotNull(roundingMode);
        long j3 = j / j2;
        long j4 = j - (j2 * j3);
        int i = (j4 > 0L ? 1 : (j4 == 0L ? 0 : -1));
        if (i == 0) {
            return j3;
        }
        boolean z = true;
        int i2 = ((int) ((j ^ j2) >> 63)) | 1;
        switch (AnonymousClass1.a[roundingMode.ordinal()]) {
            case 1:
                if (i != 0) {
                    z = false;
                }
                b.a(z);
            case 2:
                z = false;
                break;
            case 3:
                if (i2 >= 0) {
                    z = false;
                    break;
                }
                break;
            case 4:
                break;
            case 5:
                if (i2 <= 0) {
                    z = false;
                    break;
                }
                break;
            case 6:
            case 7:
            case 8:
                long abs = Math.abs(j4);
                int i3 = ((abs - (Math.abs(j2) - abs)) > 0L ? 1 : ((abs - (Math.abs(j2) - abs)) == 0L ? 0 : -1));
                if (i3 != 0) {
                    if (i3 <= 0) {
                        z = false;
                        break;
                    }
                } else {
                    boolean z2 = roundingMode == RoundingMode.HALF_UP;
                    boolean z3 = roundingMode == RoundingMode.HALF_EVEN;
                    if ((1 & j3) == 0) {
                        z = false;
                    }
                    z = (z & z3) | z2;
                    break;
                }
                break;
            default:
                throw new AssertionError();
        }
        return z ? j3 + i2 : j3;
    }

    @GwtIncompatible
    public static int mod(long j, int i) {
        return (int) mod(j, i);
    }

    @GwtIncompatible
    public static long mod(long j, long j2) {
        if (j2 > 0) {
            long j3 = j % j2;
            return j3 >= 0 ? j3 : j3 + j2;
        }
        throw new ArithmeticException("Modulus must be positive");
    }

    public static long gcd(long j, long j2) {
        b.b(ai.at, j);
        b.b("b", j2);
        if (j == 0) {
            return j2;
        }
        if (j2 == 0) {
            return j;
        }
        int numberOfTrailingZeros = Long.numberOfTrailingZeros(j);
        long j3 = j >> numberOfTrailingZeros;
        int numberOfTrailingZeros2 = Long.numberOfTrailingZeros(j2);
        long j4 = j2 >> numberOfTrailingZeros2;
        while (j3 != j4) {
            long j5 = j3 - j4;
            long j6 = (j5 >> 63) & j5;
            long j7 = (j5 - j6) - j6;
            j4 += j6;
            j3 = j7 >> Long.numberOfTrailingZeros(j7);
        }
        return j3 << Math.min(numberOfTrailingZeros, numberOfTrailingZeros2);
    }

    @GwtIncompatible
    public static long checkedAdd(long j, long j2) {
        long j3 = j + j2;
        boolean z = true;
        boolean z2 = (j ^ j2) < 0;
        if ((j ^ j3) < 0) {
            z = false;
        }
        b.a(z2 | z, "checkedAdd", j, j2);
        return j3;
    }

    @GwtIncompatible
    public static long checkedSubtract(long j, long j2) {
        long j3 = j - j2;
        boolean z = true;
        boolean z2 = (j ^ j2) >= 0;
        if ((j ^ j3) < 0) {
            z = false;
        }
        b.a(z2 | z, "checkedSubtract", j, j2);
        return j3;
    }

    public static long checkedMultiply(long j, long j2) {
        int numberOfLeadingZeros = Long.numberOfLeadingZeros(j) + Long.numberOfLeadingZeros(~j) + Long.numberOfLeadingZeros(j2) + Long.numberOfLeadingZeros(~j2);
        if (numberOfLeadingZeros > 65) {
            return j * j2;
        }
        b.a(numberOfLeadingZeros >= 64, "checkedMultiply", j, j2);
        int i = (j > 0L ? 1 : (j == 0L ? 0 : -1));
        b.a((i >= 0) | (j2 != Long.MIN_VALUE), "checkedMultiply", j, j2);
        long j3 = j * j2;
        b.a(i == 0 || j3 / j == j2, "checkedMultiply", j, j2);
        return j3;
    }

    @GwtIncompatible
    public static long checkedPow(long j, int i) {
        b.b("exponent", i);
        long j2 = 1;
        if (!(j >= -2) || !(j <= 2)) {
            while (true) {
                switch (i) {
                    case 0:
                        return j2;
                    case 1:
                        return checkedMultiply(j2, j);
                    default:
                        if ((i & 1) != 0) {
                            j2 = checkedMultiply(j2, j);
                        }
                        i >>= 1;
                        if (i > 0) {
                            b.a(-3037000499L <= j && j <= 3037000499L, "checkedPow", j, i);
                            j *= j;
                        }
                        break;
                }
            }
        } else {
            switch ((int) j) {
                case -2:
                    b.a(i < 64, "checkedPow", j, i);
                    return (i & 1) == 0 ? 1 << i : (-1) << i;
                case -1:
                    return (i & 1) == 0 ? 1L : -1L;
                case 0:
                    return i == 0 ? 1L : 0L;
                case 1:
                    return 1L;
                case 2:
                    b.a(i < 63, "checkedPow", j, i);
                    return 1 << i;
                default:
                    throw new AssertionError();
            }
        }
    }

    @Beta
    public static long saturatedMultiply(long j, long j2) {
        int numberOfLeadingZeros = Long.numberOfLeadingZeros(j) + Long.numberOfLeadingZeros(~j) + Long.numberOfLeadingZeros(j2) + Long.numberOfLeadingZeros(~j2);
        if (numberOfLeadingZeros > 65) {
            return j * j2;
        }
        long j3 = ((j ^ j2) >>> 63) + Long.MAX_VALUE;
        boolean z = true;
        boolean z2 = numberOfLeadingZeros < 64;
        int i = (j > 0L ? 1 : (j == 0L ? 0 : -1));
        boolean z3 = i < 0;
        if (j2 != Long.MIN_VALUE) {
            z = false;
        }
        if (z2 || (z & z3)) {
            return j3;
        }
        long j4 = j * j2;
        return (i == 0 || j4 / j == j2) ? j4 : j3;
    }

    @Beta
    public static long saturatedPow(long j, int i) {
        b.b("exponent", i);
        long j2 = 1;
        if ((j >= -2) && (j <= 2)) {
            switch ((int) j) {
                case -2:
                    return i >= 64 ? (i & 1) + Long.MAX_VALUE : (i & 1) == 0 ? 1 << i : (-1) << i;
                case -1:
                    return (i & 1) == 0 ? 1L : -1L;
                case 0:
                    return i == 0 ? 1L : 0L;
                case 1:
                    return 1L;
                case 2:
                    if (i >= 63) {
                        return Long.MAX_VALUE;
                    }
                    return 1 << i;
                default:
                    throw new AssertionError();
            }
        } else {
            long j3 = ((j >>> 63) & i & 1) + Long.MAX_VALUE;
            while (true) {
                switch (i) {
                    case 0:
                        return j2;
                    case 1:
                        return saturatedMultiply(j2, j);
                    default:
                        if ((i & 1) != 0) {
                            j2 = saturatedMultiply(j2, j);
                        }
                        i >>= 1;
                        if (i > 0) {
                            if ((-3037000499L > j) || (j > 3037000499L)) {
                                return j3;
                            }
                            j *= j;
                        }
                }
            }
        }
    }

    @GwtIncompatible
    public static long factorial(int i) {
        b.b("n", i);
        long[] jArr = d;
        if (i < jArr.length) {
            return jArr[i];
        }
        return Long.MAX_VALUE;
    }

    public static long binomial(int i, int i2) {
        b.b("n", i);
        b.b("k", i2);
        Preconditions.checkArgument(i2 <= i, "k (%s) > n (%s)", i2, i);
        if (i2 > (i >> 1)) {
            i2 = i - i2;
        }
        switch (i2) {
            case 0:
                return 1L;
            case 1:
                return i;
            default:
                long[] jArr = d;
                if (i < jArr.length) {
                    return jArr[i] / (jArr[i2] * jArr[i - i2]);
                }
                int[] iArr = e;
                if (i2 >= iArr.length || i > iArr[i2]) {
                    return Long.MAX_VALUE;
                }
                int[] iArr2 = f;
                int i3 = 2;
                if (i2 >= iArr2.length || i > iArr2[i2]) {
                    long j = i;
                    int log2 = log2(j, RoundingMode.CEILING);
                    int i4 = i - 1;
                    long j2 = 1;
                    long j3 = 1;
                    long j4 = j;
                    int i5 = log2;
                    while (i3 <= i2) {
                        i5 += log2;
                        if (i5 < 63) {
                            j4 *= i4;
                            j3 *= i3;
                        } else {
                            long a2 = a(j2, j4, j3);
                            j4 = i4;
                            j3 = i3;
                            i5 = log2;
                            j2 = a2;
                        }
                        i3++;
                        i4--;
                    }
                    return a(j2, j4, j3);
                }
                int i6 = i - 1;
                long j5 = i;
                while (i3 <= i2) {
                    j5 = (j5 * i6) / i3;
                    i6--;
                    i3++;
                }
                return j5;
        }
    }

    static long a(long j, long j2, long j3) {
        if (j == 1) {
            return j2 / j3;
        }
        long gcd = gcd(j, j3);
        return (j / gcd) * (j2 / (j3 / gcd));
    }

    @Beta
    @GwtIncompatible
    public static boolean isPrime(long j) {
        int i = (j > 2L ? 1 : (j == 2L ? 0 : -1));
        if (i < 0) {
            b.b("n", j);
            return false;
        } else if (i == 0 || j == 3 || j == 5 || j == 7 || j == 11 || j == 13) {
            return true;
        } else {
            if (((-545925251) & (1 << ((int) (j % 30)))) != 0 || j % 7 == 0 || j % 11 == 0 || j % 13 == 0) {
                return false;
            }
            if (j < 289) {
                return true;
            }
            long[][] jArr = g;
            for (long[] jArr2 : jArr) {
                if (j <= jArr2[0]) {
                    for (int i2 = 1; i2 < jArr2.length; i2++) {
                        if (!a.a(jArr2[i2], j)) {
                            return false;
                        }
                    }
                    return true;
                }
            }
            throw new AssertionError();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public enum a {
        SMALL {
            @Override // com.google.common.math.LongMath.a
            long a(long j, long j2, long j3) {
                return (j * j2) % j3;
            }

            @Override // com.google.common.math.LongMath.a
            long b(long j, long j2) {
                return (j * j) % j2;
            }
        },
        LARGE {
            private long b(long j, long j2, long j3) {
                return j >= j3 - j2 ? (j + j2) - j3 : j + j2;
            }

            private long c(long j, long j2) {
                int i = 32;
                do {
                    int min = Math.min(i, Long.numberOfLeadingZeros(j));
                    j = UnsignedLongs.remainder(j << min, j2);
                    i -= min;
                } while (i > 0);
                return j;
            }

            @Override // com.google.common.math.LongMath.a
            long a(long j, long j2, long j3) {
                long j4 = j >>> 32;
                long j5 = j2 >>> 32;
                long j6 = j & 4294967295L;
                long j7 = j2 & 4294967295L;
                long c = c(j4 * j5, j3) + (j4 * j7);
                if (c < 0) {
                    c = UnsignedLongs.remainder(c, j3);
                }
                return b(c(c + (j5 * j6), j3), UnsignedLongs.remainder(j6 * j7, j3), j3);
            }

            @Override // com.google.common.math.LongMath.a
            long b(long j, long j2) {
                long j3 = j >>> 32;
                long j4 = j & 4294967295L;
                long c = c(j3 * j3, j2);
                long j5 = j3 * j4 * 2;
                if (j5 < 0) {
                    j5 = UnsignedLongs.remainder(j5, j2);
                }
                return b(c(c + j5, j2), UnsignedLongs.remainder(j4 * j4, j2), j2);
            }
        };

        abstract long a(long j, long j2, long j3);

        abstract long b(long j, long j2);

        /* synthetic */ a(AnonymousClass1 r3) {
            this();
        }

        static boolean a(long j, long j2) {
            return (j2 <= 3037000499L ? SMALL : LARGE).c(j, j2);
        }

        private long b(long j, long j2, long j3) {
            long j4 = 1;
            while (j2 != 0) {
                if ((j2 & 1) != 0) {
                    j4 = a(j4, j, j3);
                }
                j = b(j, j3);
                j2 >>= 1;
            }
            return j4;
        }

        private boolean c(long j, long j2) {
            long j3 = j2 - 1;
            int numberOfTrailingZeros = Long.numberOfTrailingZeros(j3);
            long j4 = j3 >> numberOfTrailingZeros;
            long j5 = j % j2;
            if (j5 == 0) {
                return true;
            }
            long b = b(j5, j4, j2);
            if (b == 1) {
                return true;
            }
            int i = 0;
            while (b != j3) {
                i++;
                if (i == numberOfTrailingZeros) {
                    return false;
                }
                b = b(b, j2);
            }
            return true;
        }
    }

    private LongMath() {
    }
}
