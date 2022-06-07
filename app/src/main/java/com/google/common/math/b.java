package com.google.common.math;

import com.google.common.annotations.GwtCompatible;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.math.BigInteger;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* compiled from: MathPreconditions.java */
@CanIgnoreReturnValue
@GwtCompatible
/* loaded from: classes2.dex */
final class b {
    /* JADX INFO: Access modifiers changed from: package-private */
    public static int a(@NullableDecl String str, int i) {
        if (i > 0) {
            return i;
        }
        throw new IllegalArgumentException(str + " (" + i + ") must be > 0");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static long a(@NullableDecl String str, long j) {
        if (j > 0) {
            return j;
        }
        throw new IllegalArgumentException(str + " (" + j + ") must be > 0");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static BigInteger a(@NullableDecl String str, BigInteger bigInteger) {
        if (bigInteger.signum() > 0) {
            return bigInteger;
        }
        throw new IllegalArgumentException(str + " (" + bigInteger + ") must be > 0");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int b(@NullableDecl String str, int i) {
        if (i >= 0) {
            return i;
        }
        throw new IllegalArgumentException(str + " (" + i + ") must be >= 0");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static long b(@NullableDecl String str, long j) {
        if (j >= 0) {
            return j;
        }
        throw new IllegalArgumentException(str + " (" + j + ") must be >= 0");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static BigInteger b(@NullableDecl String str, BigInteger bigInteger) {
        if (bigInteger.signum() >= 0) {
            return bigInteger;
        }
        throw new IllegalArgumentException(str + " (" + bigInteger + ") must be >= 0");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static double a(@NullableDecl String str, double d) {
        if (d >= 0.0d) {
            return d;
        }
        throw new IllegalArgumentException(str + " (" + d + ") must be >= 0");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void a(boolean z) {
        if (!z) {
            throw new ArithmeticException("mode was UNNECESSARY, but rounding was necessary");
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void b(boolean z) {
        if (!z) {
            throw new ArithmeticException("not in range");
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void a(boolean z, String str, int i, int i2) {
        if (!z) {
            throw new ArithmeticException("overflow: " + str + "(" + i + ", " + i2 + ")");
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void a(boolean z, String str, long j, long j2) {
        if (!z) {
            throw new ArithmeticException("overflow: " + str + "(" + j + ", " + j2 + ")");
        }
    }
}
