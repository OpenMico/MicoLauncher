package com.google.common.math;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@GwtCompatible(emulated = true)
/* loaded from: classes2.dex */
public final class BigIntegerMath {
    @VisibleForTesting
    static final BigInteger a = new BigInteger("16a09e667f3bcc908b2fb1366ea957d3e3adec17512775099da2f590b0667322a", 16);
    private static final double b = Math.log(10.0d);
    private static final double c = Math.log(2.0d);

    @Beta
    public static BigInteger ceilingPowerOfTwo(BigInteger bigInteger) {
        return BigInteger.ZERO.setBit(log2(bigInteger, RoundingMode.CEILING));
    }

    @Beta
    public static BigInteger floorPowerOfTwo(BigInteger bigInteger) {
        return BigInteger.ZERO.setBit(log2(bigInteger, RoundingMode.FLOOR));
    }

    public static boolean isPowerOfTwo(BigInteger bigInteger) {
        Preconditions.checkNotNull(bigInteger);
        return bigInteger.signum() > 0 && bigInteger.getLowestSetBit() == bigInteger.bitLength() - 1;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static int log2(BigInteger bigInteger, RoundingMode roundingMode) {
        b.a("x", (BigInteger) Preconditions.checkNotNull(bigInteger));
        int bitLength = bigInteger.bitLength() - 1;
        switch (AnonymousClass1.a[roundingMode.ordinal()]) {
            case 1:
                b.a(isPowerOfTwo(bigInteger));
                break;
            case 2:
            case 3:
                break;
            case 4:
            case 5:
                return isPowerOfTwo(bigInteger) ? bitLength : bitLength + 1;
            case 6:
            case 7:
            case 8:
                return bitLength < 256 ? bigInteger.compareTo(a.shiftRight(256 - bitLength)) <= 0 ? bitLength : bitLength + 1 : bigInteger.pow(2).bitLength() + (-1) < (bitLength * 2) + 1 ? bitLength : bitLength + 1;
            default:
                throw new AssertionError();
        }
        return bitLength;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.google.common.math.BigIntegerMath$1  reason: invalid class name */
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
    @GwtIncompatible
    public static int log10(BigInteger bigInteger, RoundingMode roundingMode) {
        int i;
        b.a("x", bigInteger);
        if (a(bigInteger)) {
            return LongMath.log10(bigInteger.longValue(), roundingMode);
        }
        int log2 = (int) ((log2(bigInteger, RoundingMode.FLOOR) * c) / b);
        BigInteger pow = BigInteger.TEN.pow(log2);
        int compareTo = pow.compareTo(bigInteger);
        if (compareTo > 0) {
            do {
                log2--;
                pow = pow.divide(BigInteger.TEN);
                i = pow.compareTo(bigInteger);
            } while (i > 0);
        } else {
            BigInteger multiply = BigInteger.TEN.multiply(pow);
            int compareTo2 = multiply.compareTo(bigInteger);
            i = compareTo;
            int i2 = compareTo2;
            BigInteger bigInteger2 = multiply;
            while (i2 <= 0) {
                log2++;
                BigInteger multiply2 = BigInteger.TEN.multiply(bigInteger2);
                int compareTo3 = multiply2.compareTo(bigInteger);
                bigInteger2 = multiply2;
                pow = bigInteger2;
                i = i2;
                i2 = compareTo3;
            }
        }
        switch (AnonymousClass1.a[roundingMode.ordinal()]) {
            case 1:
                b.a(i == 0);
                break;
            case 2:
            case 3:
                break;
            case 4:
            case 5:
                return pow.equals(bigInteger) ? log2 : log2 + 1;
            case 6:
            case 7:
            case 8:
                return bigInteger.pow(2).compareTo(pow.pow(2).multiply(BigInteger.TEN)) <= 0 ? log2 : log2 + 1;
            default:
                throw new AssertionError();
        }
        return log2;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @GwtIncompatible
    public static BigInteger sqrt(BigInteger bigInteger, RoundingMode roundingMode) {
        b.b("x", bigInteger);
        if (a(bigInteger)) {
            return BigInteger.valueOf(LongMath.sqrt(bigInteger.longValue(), roundingMode));
        }
        BigInteger b2 = b(bigInteger);
        switch (AnonymousClass1.a[roundingMode.ordinal()]) {
            case 1:
                b.a(b2.pow(2).equals(bigInteger));
                break;
            case 2:
            case 3:
                break;
            case 4:
            case 5:
                int intValue = b2.intValue();
                return intValue * intValue == bigInteger.intValue() && b2.pow(2).equals(bigInteger) ? b2 : b2.add(BigInteger.ONE);
            case 6:
            case 7:
            case 8:
                return b2.pow(2).add(b2).compareTo(bigInteger) >= 0 ? b2 : b2.add(BigInteger.ONE);
            default:
                throw new AssertionError();
        }
        return b2;
    }

    @GwtIncompatible
    private static BigInteger b(BigInteger bigInteger) {
        BigInteger bigInteger2;
        int log2 = log2(bigInteger, RoundingMode.FLOOR);
        if (log2 < 1023) {
            bigInteger2 = c(bigInteger);
        } else {
            int i = (log2 - 52) & (-2);
            bigInteger2 = c(bigInteger.shiftRight(i)).shiftLeft(i >> 1);
        }
        BigInteger shiftRight = bigInteger2.add(bigInteger.divide(bigInteger2)).shiftRight(1);
        if (bigInteger2.equals(shiftRight)) {
            return bigInteger2;
        }
        while (true) {
            BigInteger shiftRight2 = shiftRight.add(bigInteger.divide(shiftRight)).shiftRight(1);
            if (shiftRight2.compareTo(shiftRight) >= 0) {
                return shiftRight;
            }
            shiftRight = shiftRight2;
        }
    }

    @GwtIncompatible
    private static BigInteger c(BigInteger bigInteger) {
        return DoubleMath.roundToBigInteger(Math.sqrt(a.a(bigInteger)), RoundingMode.HALF_EVEN);
    }

    @GwtIncompatible
    public static BigInteger divide(BigInteger bigInteger, BigInteger bigInteger2, RoundingMode roundingMode) {
        return new BigDecimal(bigInteger).divide(new BigDecimal(bigInteger2), 0, roundingMode).toBigIntegerExact();
    }

    public static BigInteger factorial(int i) {
        b.b("n", i);
        if (i < LongMath.d.length) {
            return BigInteger.valueOf(LongMath.d[i]);
        }
        ArrayList arrayList = new ArrayList(IntMath.divide(IntMath.log2(i, RoundingMode.CEILING) * i, 64, RoundingMode.CEILING));
        int length = LongMath.d.length;
        long j = LongMath.d[length - 1];
        int numberOfTrailingZeros = Long.numberOfTrailingZeros(j);
        long j2 = j >> numberOfTrailingZeros;
        int log2 = LongMath.log2(j2, RoundingMode.FLOOR) + 1;
        long j3 = length;
        int log22 = LongMath.log2(j3, RoundingMode.FLOOR) + 1;
        int i2 = 1 << (log22 - 1);
        while (j3 <= i) {
            if ((j3 & i2) != 0) {
                i2 <<= 1;
                log22++;
            }
            int numberOfTrailingZeros2 = Long.numberOfTrailingZeros(j3);
            long j4 = j3 >> numberOfTrailingZeros2;
            numberOfTrailingZeros += numberOfTrailingZeros2;
            if ((log22 - numberOfTrailingZeros2) + log2 >= 64) {
                arrayList.add(BigInteger.valueOf(j2));
                j2 = 1;
            }
            j2 *= j4;
            log2 = LongMath.log2(j2, RoundingMode.FLOOR) + 1;
            j3++;
        }
        if (j2 > 1) {
            arrayList.add(BigInteger.valueOf(j2));
        }
        return a(arrayList).shiftLeft(numberOfTrailingZeros);
    }

    static BigInteger a(List<BigInteger> list) {
        return a(list, 0, list.size());
    }

    static BigInteger a(List<BigInteger> list, int i, int i2) {
        switch (i2 - i) {
            case 0:
                return BigInteger.ONE;
            case 1:
                return list.get(i);
            case 2:
                return list.get(i).multiply(list.get(i + 1));
            case 3:
                return list.get(i).multiply(list.get(i + 1)).multiply(list.get(i + 2));
            default:
                int i3 = (i2 + i) >>> 1;
                return a(list, i, i3).multiply(a(list, i3, i2));
        }
    }

    public static BigInteger binomial(int i, int i2) {
        b.b("n", i);
        b.b("k", i2);
        int i3 = 1;
        Preconditions.checkArgument(i2 <= i, "k (%s) > n (%s)", i2, i);
        if (i2 > (i >> 1)) {
            i2 = i - i2;
        }
        if (i2 < LongMath.e.length && i <= LongMath.e[i2]) {
            return BigInteger.valueOf(LongMath.binomial(i, i2));
        }
        BigInteger bigInteger = BigInteger.ONE;
        long j = i;
        long j2 = 1;
        int log2 = LongMath.log2(j, RoundingMode.CEILING);
        int i4 = log2;
        while (i3 < i2) {
            int i5 = i - i3;
            i3++;
            i4 += log2;
            if (i4 >= 63) {
                bigInteger = bigInteger.multiply(BigInteger.valueOf(j)).divide(BigInteger.valueOf(j2));
                j = i5;
                j2 = i3;
                i4 = log2;
            } else {
                j *= i5;
                j2 *= i3;
            }
        }
        return bigInteger.multiply(BigInteger.valueOf(j)).divide(BigInteger.valueOf(j2));
    }

    @GwtIncompatible
    static boolean a(BigInteger bigInteger) {
        return bigInteger.bitLength() <= 63;
    }

    private BigIntegerMath() {
    }
}
