package com.google.common.math;

import com.alibaba.fastjson.asm.Opcodes;
import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.primitives.Ints;
import com.umeng.analytics.pro.ai;
import com.xiaomi.mico.base.utils.BitmapCache;
import java.math.RoundingMode;

@GwtCompatible(emulated = true)
/* loaded from: classes2.dex */
public final class IntMath {
    @VisibleForTesting
    static final byte[] a = {9, 9, 9, 8, 8, 8, 7, 7, 7, 6, 6, 6, 6, 5, 5, 5, 4, 4, 4, 3, 3, 3, 3, 2, 2, 2, 1, 1, 1, 0, 0, 0, 0};
    @VisibleForTesting
    static final int[] b = {1, 10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000, 1000000000};
    @VisibleForTesting
    static final int[] c = {3, 31, 316, 3162, 31622, 316227, 3162277, 31622776, 316227766, Integer.MAX_VALUE};
    private static final int[] e = {1, 1, 2, 6, 24, 120, BitmapCache.MAX_WIDTH, 5040, 40320, 362880, 3628800, 39916800, 479001600};
    @VisibleForTesting
    static int[] d = {Integer.MAX_VALUE, Integer.MAX_VALUE, 65536, 2345, 477, Opcodes.INSTANCEOF, 110, 75, 58, 49, 43, 39, 37, 35, 34, 34, 33};

    @VisibleForTesting
    static int a(int i, int i2) {
        return (~(~(i - i2))) >>> 31;
    }

    public static boolean isPowerOfTwo(int i) {
        boolean z = false;
        boolean z2 = i > 0;
        if ((i & (i - 1)) == 0) {
            z = true;
        }
        return z2 & z;
    }

    public static int mean(int i, int i2) {
        return (i & i2) + ((i ^ i2) >> 1);
    }

    @Beta
    public static int ceilingPowerOfTwo(int i) {
        b.a("x", i);
        if (i <= 1073741824) {
            return 1 << (-Integer.numberOfLeadingZeros(i - 1));
        }
        throw new ArithmeticException("ceilingPowerOfTwo(" + i + ") not representable as an int");
    }

    @Beta
    public static int floorPowerOfTwo(int i) {
        b.a("x", i);
        return Integer.highestOneBit(i);
    }

    /* renamed from: com.google.common.math.IntMath$1 */
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
    public static int log2(int i, RoundingMode roundingMode) {
        b.a("x", i);
        switch (AnonymousClass1.a[roundingMode.ordinal()]) {
            case 1:
                b.a(isPowerOfTwo(i));
                break;
            case 2:
            case 3:
                break;
            case 4:
            case 5:
                return 32 - Integer.numberOfLeadingZeros(i - 1);
            case 6:
            case 7:
            case 8:
                int numberOfLeadingZeros = Integer.numberOfLeadingZeros(i);
                return (31 - numberOfLeadingZeros) + a((-1257966797) >>> numberOfLeadingZeros, i);
            default:
                throw new AssertionError();
        }
        return 31 - Integer.numberOfLeadingZeros(i);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @GwtIncompatible
    public static int log10(int i, RoundingMode roundingMode) {
        b.a("x", i);
        int a2 = a(i);
        int i2 = b[a2];
        switch (AnonymousClass1.a[roundingMode.ordinal()]) {
            case 1:
                b.a(i == i2);
                break;
            case 2:
            case 3:
                break;
            case 4:
            case 5:
                return a2 + a(i2, i);
            case 6:
            case 7:
            case 8:
                return a2 + a(c[a2], i);
            default:
                throw new AssertionError();
        }
        return a2;
    }

    private static int a(int i) {
        byte b2 = a[Integer.numberOfLeadingZeros(i)];
        return b2 - a(i, b[b2]);
    }

    @GwtIncompatible
    public static int pow(int i, int i2) {
        b.b("exponent", i2);
        switch (i) {
            case -2:
                if (i2 < 32) {
                    return (i2 & 1) == 0 ? 1 << i2 : -(1 << i2);
                }
                return 0;
            case -1:
                return (i2 & 1) == 0 ? 1 : -1;
            case 0:
                return i2 == 0 ? 1 : 0;
            case 1:
                return 1;
            case 2:
                if (i2 < 32) {
                    return 1 << i2;
                }
                return 0;
            default:
                int i3 = i;
                int i4 = 1;
                while (true) {
                    switch (i2) {
                        case 0:
                            return i4;
                        case 1:
                            return i3 * i4;
                        default:
                            i4 *= (i2 & 1) == 0 ? 1 : i3;
                            i3 *= i3;
                            i2 >>= 1;
                    }
                }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @GwtIncompatible
    public static int sqrt(int i, RoundingMode roundingMode) {
        b.b("x", i);
        int b2 = b(i);
        switch (AnonymousClass1.a[roundingMode.ordinal()]) {
            case 1:
                b.a(b2 * b2 == i);
                break;
            case 2:
            case 3:
                break;
            case 4:
            case 5:
                return b2 + a(b2 * b2, i);
            case 6:
            case 7:
            case 8:
                return b2 + a((b2 * b2) + b2, i);
            default:
                throw new AssertionError();
        }
        return b2;
    }

    private static int b(int i) {
        return (int) Math.sqrt(i);
    }

    public static int divide(int i, int i2, RoundingMode roundingMode) {
        Preconditions.checkNotNull(roundingMode);
        if (i2 != 0) {
            int i3 = i / i2;
            int i4 = i - (i2 * i3);
            if (i4 == 0) {
                return i3;
            }
            boolean z = true;
            int i5 = ((i ^ i2) >> 31) | 1;
            switch (AnonymousClass1.a[roundingMode.ordinal()]) {
                case 1:
                    if (i4 != 0) {
                        z = false;
                    }
                    b.a(z);
                case 2:
                    z = false;
                    break;
                case 3:
                    if (i5 >= 0) {
                        z = false;
                        break;
                    }
                    break;
                case 4:
                    break;
                case 5:
                    if (i5 <= 0) {
                        z = false;
                        break;
                    }
                    break;
                case 6:
                case 7:
                case 8:
                    int abs = Math.abs(i4);
                    int abs2 = abs - (Math.abs(i2) - abs);
                    if (abs2 != 0) {
                        if (abs2 <= 0) {
                            z = false;
                            break;
                        }
                    } else if (roundingMode != RoundingMode.HALF_UP) {
                        if (!(roundingMode == RoundingMode.HALF_EVEN) || !((i3 & 1) != 0)) {
                            z = false;
                            break;
                        }
                    }
                    break;
                default:
                    throw new AssertionError();
            }
            return z ? i3 + i5 : i3;
        }
        throw new ArithmeticException("/ by zero");
    }

    public static int mod(int i, int i2) {
        if (i2 > 0) {
            int i3 = i % i2;
            return i3 >= 0 ? i3 : i3 + i2;
        }
        throw new ArithmeticException("Modulus " + i2 + " must be > 0");
    }

    public static int gcd(int i, int i2) {
        b.b(ai.at, i);
        b.b("b", i2);
        if (i == 0) {
            return i2;
        }
        if (i2 == 0) {
            return i;
        }
        int numberOfTrailingZeros = Integer.numberOfTrailingZeros(i);
        int i3 = i >> numberOfTrailingZeros;
        int numberOfTrailingZeros2 = Integer.numberOfTrailingZeros(i2);
        int i4 = i2 >> numberOfTrailingZeros2;
        while (i3 != i4) {
            int i5 = i3 - i4;
            int i6 = (i5 >> 31) & i5;
            int i7 = (i5 - i6) - i6;
            i4 += i6;
            i3 = i7 >> Integer.numberOfTrailingZeros(i7);
        }
        return i3 << Math.min(numberOfTrailingZeros, numberOfTrailingZeros2);
    }

    public static int checkedAdd(int i, int i2) {
        long j = i + i2;
        int i3 = (int) j;
        b.a(j == ((long) i3), "checkedAdd", i, i2);
        return i3;
    }

    public static int checkedSubtract(int i, int i2) {
        long j = i - i2;
        int i3 = (int) j;
        b.a(j == ((long) i3), "checkedSubtract", i, i2);
        return i3;
    }

    public static int checkedMultiply(int i, int i2) {
        long j = i * i2;
        int i3 = (int) j;
        b.a(j == ((long) i3), "checkedMultiply", i, i2);
        return i3;
    }

    public static int checkedPow(int i, int i2) {
        b.b("exponent", i2);
        boolean z = false;
        switch (i) {
            case -2:
                if (i2 < 32) {
                    z = true;
                }
                b.a(z, "checkedPow", i, i2);
                return (i2 & 1) == 0 ? 1 << i2 : (-1) << i2;
            case -1:
                return (i2 & 1) == 0 ? 1 : -1;
            case 0:
                return i2 == 0 ? 1 : 0;
            case 1:
                return 1;
            case 2:
                if (i2 < 31) {
                    z = true;
                }
                b.a(z, "checkedPow", i, i2);
                return 1 << i2;
            default:
                int i3 = i;
                int i4 = 1;
                while (true) {
                    switch (i2) {
                        case 0:
                            return i4;
                        case 1:
                            return checkedMultiply(i4, i3);
                        default:
                            if ((i2 & 1) != 0) {
                                i4 = checkedMultiply(i4, i3);
                            }
                            i2 >>= 1;
                            if (i2 > 0) {
                                b.a((-46340 <= i3) & (i3 <= 46340), "checkedPow", i3, i2);
                                i3 *= i3;
                            }
                    }
                }
        }
    }

    @Beta
    public static int saturatedAdd(int i, int i2) {
        return Ints.saturatedCast(i + i2);
    }

    @Beta
    public static int saturatedSubtract(int i, int i2) {
        return Ints.saturatedCast(i - i2);
    }

    @Beta
    public static int saturatedMultiply(int i, int i2) {
        return Ints.saturatedCast(i * i2);
    }

    @Beta
    public static int saturatedPow(int i, int i2) {
        b.b("exponent", i2);
        switch (i) {
            case -2:
                return i2 >= 32 ? (i2 & 1) + Integer.MAX_VALUE : (i2 & 1) == 0 ? 1 << i2 : (-1) << i2;
            case -1:
                return (i2 & 1) == 0 ? 1 : -1;
            case 0:
                return i2 == 0 ? 1 : 0;
            case 1:
                return 1;
            case 2:
                if (i2 >= 31) {
                    return Integer.MAX_VALUE;
                }
                return 1 << i2;
            default:
                int i3 = ((i >>> 31) & i2 & 1) + Integer.MAX_VALUE;
                int i4 = i;
                int i5 = 1;
                while (true) {
                    switch (i2) {
                        case 0:
                            return i5;
                        case 1:
                            return saturatedMultiply(i5, i4);
                        default:
                            if ((i2 & 1) != 0) {
                                i5 = saturatedMultiply(i5, i4);
                            }
                            i2 >>= 1;
                            if (i2 > 0) {
                                if ((-46340 > i4) || (i4 > 46340)) {
                                    return i3;
                                }
                                i4 *= i4;
                            }
                    }
                }
        }
    }

    public static int factorial(int i) {
        b.b("n", i);
        int[] iArr = e;
        if (i < iArr.length) {
            return iArr[i];
        }
        return Integer.MAX_VALUE;
    }

    public static int binomial(int i, int i2) {
        b.b("n", i);
        b.b("k", i2);
        int i3 = 0;
        Preconditions.checkArgument(i2 <= i, "k (%s) > n (%s)", i2, i);
        if (i2 > (i >> 1)) {
            i2 = i - i2;
        }
        int[] iArr = d;
        if (i2 >= iArr.length || i > iArr[i2]) {
            return Integer.MAX_VALUE;
        }
        switch (i2) {
            case 0:
                return 1;
            case 1:
                return i;
            default:
                long j = 1;
                while (i3 < i2) {
                    i3++;
                    j = (j * (i - i3)) / i3;
                }
                return (int) j;
        }
    }

    @Beta
    @GwtIncompatible
    public static boolean isPrime(int i) {
        return LongMath.isPrime(i);
    }

    private IntMath() {
    }
}
