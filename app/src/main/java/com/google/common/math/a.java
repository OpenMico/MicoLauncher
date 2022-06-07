package com.google.common.math;

import com.google.android.exoplayer2.analytics.AnalyticsListener;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import java.math.BigInteger;

/* compiled from: DoubleUtils.java */
@GwtIncompatible
/* loaded from: classes2.dex */
final class a {
    /* JADX INFO: Access modifiers changed from: package-private */
    public static long a(double d) {
        Preconditions.checkArgument(b(d), "not a normal value");
        int exponent = Math.getExponent(d);
        long doubleToRawLongBits = Double.doubleToRawLongBits(d) & 4503599627370495L;
        return exponent == -1023 ? doubleToRawLongBits << 1 : doubleToRawLongBits | 4503599627370496L;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean b(double d) {
        return Math.getExponent(d) <= 1023;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean c(double d) {
        return Math.getExponent(d) >= -1022;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static double d(double d) {
        return Double.longBitsToDouble((Double.doubleToRawLongBits(d) & 4503599627370495L) | 4607182418800017408L);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static double a(BigInteger bigInteger) {
        BigInteger abs = bigInteger.abs();
        boolean z = true;
        int bitLength = abs.bitLength() - 1;
        if (bitLength < 63) {
            return bigInteger.longValue();
        }
        if (bitLength > 1023) {
            return bigInteger.signum() * Double.POSITIVE_INFINITY;
        }
        int i = (bitLength - 52) - 1;
        long longValue = abs.shiftRight(i).longValue();
        long j = (longValue >> 1) & 4503599627370495L;
        if ((longValue & 1) == 0 || ((j & 1) == 0 && abs.getLowestSetBit() >= i)) {
            z = false;
        }
        if (z) {
            j++;
        }
        return Double.longBitsToDouble((((bitLength + AnalyticsListener.EVENT_DROPPED_VIDEO_FRAMES) << 52) + j) | (bigInteger.signum() & Long.MIN_VALUE));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static double e(double d) {
        Preconditions.checkArgument(!Double.isNaN(d));
        if (d > 0.0d) {
            return d;
        }
        return 0.0d;
    }
}
