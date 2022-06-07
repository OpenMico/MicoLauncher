package io.netty.util.internal;

/* loaded from: classes4.dex */
public final class MathUtil {
    static final /* synthetic */ boolean a = !MathUtil.class.desiredAssertionStatus();

    public static int compare(long j, long j2) {
        int i = (j > j2 ? 1 : (j == j2 ? 0 : -1));
        if (i < 0) {
            return -1;
        }
        return i > 0 ? 1 : 0;
    }

    public static boolean isOutOfBounds(int i, int i2, int i3) {
        int i4 = i | i2;
        int i5 = i + i2;
        return ((i4 | i5) | (i3 - i5)) < 0;
    }

    private MathUtil() {
    }

    public static int findNextPositivePowerOfTwo(int i) {
        if (a || (i > Integer.MIN_VALUE && i < 1073741824)) {
            return 1 << (32 - Integer.numberOfLeadingZeros(i - 1));
        }
        throw new AssertionError();
    }
}
