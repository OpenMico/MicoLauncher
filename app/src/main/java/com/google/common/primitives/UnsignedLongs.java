package com.google.common.primitives;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Comparator;

@Beta
@GwtCompatible
/* loaded from: classes2.dex */
public final class UnsignedLongs {
    public static final long MAX_VALUE = -1;

    private static long a(long j) {
        return j ^ Long.MIN_VALUE;
    }

    private UnsignedLongs() {
    }

    public static int compare(long j, long j2) {
        return Longs.compare(a(j), a(j2));
    }

    public static long min(long... jArr) {
        Preconditions.checkArgument(jArr.length > 0);
        long a2 = a(jArr[0]);
        for (int i = 1; i < jArr.length; i++) {
            long a3 = a(jArr[i]);
            if (a3 < a2) {
                a2 = a3;
            }
        }
        return a(a2);
    }

    public static long max(long... jArr) {
        Preconditions.checkArgument(jArr.length > 0);
        long a2 = a(jArr[0]);
        for (int i = 1; i < jArr.length; i++) {
            long a3 = a(jArr[i]);
            if (a3 > a2) {
                a2 = a3;
            }
        }
        return a(a2);
    }

    public static String join(String str, long... jArr) {
        Preconditions.checkNotNull(str);
        if (jArr.length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder(jArr.length * 5);
        sb.append(toString(jArr[0]));
        for (int i = 1; i < jArr.length; i++) {
            sb.append(str);
            sb.append(toString(jArr[i]));
        }
        return sb.toString();
    }

    public static Comparator<long[]> lexicographicalComparator() {
        return a.INSTANCE;
    }

    /* loaded from: classes2.dex */
    enum a implements Comparator<long[]> {
        INSTANCE;

        @Override // java.lang.Enum
        public String toString() {
            return "UnsignedLongs.lexicographicalComparator()";
        }

        /* renamed from: a */
        public int compare(long[] jArr, long[] jArr2) {
            int min = Math.min(jArr.length, jArr2.length);
            for (int i = 0; i < min; i++) {
                if (jArr[i] != jArr2[i]) {
                    return UnsignedLongs.compare(jArr[i], jArr2[i]);
                }
            }
            return jArr.length - jArr2.length;
        }
    }

    public static void sort(long[] jArr) {
        Preconditions.checkNotNull(jArr);
        sort(jArr, 0, jArr.length);
    }

    public static void sort(long[] jArr, int i, int i2) {
        Preconditions.checkNotNull(jArr);
        Preconditions.checkPositionIndexes(i, i2, jArr.length);
        for (int i3 = i; i3 < i2; i3++) {
            jArr[i3] = a(jArr[i3]);
        }
        Arrays.sort(jArr, i, i2);
        while (i < i2) {
            jArr[i] = a(jArr[i]);
            i++;
        }
    }

    public static void sortDescending(long[] jArr) {
        Preconditions.checkNotNull(jArr);
        sortDescending(jArr, 0, jArr.length);
    }

    public static void sortDescending(long[] jArr, int i, int i2) {
        Preconditions.checkNotNull(jArr);
        Preconditions.checkPositionIndexes(i, i2, jArr.length);
        for (int i3 = i; i3 < i2; i3++) {
            jArr[i3] = Long.MAX_VALUE ^ jArr[i3];
        }
        Arrays.sort(jArr, i, i2);
        while (i < i2) {
            jArr[i] = jArr[i] ^ Long.MAX_VALUE;
            i++;
        }
    }

    public static long divide(long j, long j2) {
        if (j2 < 0) {
            return compare(j, j2) < 0 ? 0L : 1L;
        }
        if (j >= 0) {
            return j / j2;
        }
        int i = 1;
        long j3 = ((j >>> 1) / j2) << 1;
        if (compare(j - (j3 * j2), j2) < 0) {
            i = 0;
        }
        return j3 + i;
    }

    public static long remainder(long j, long j2) {
        if (j2 < 0) {
            return compare(j, j2) < 0 ? j : j - j2;
        }
        if (j >= 0) {
            return j % j2;
        }
        long j3 = j - ((((j >>> 1) / j2) << 1) * j2);
        if (compare(j3, j2) < 0) {
            j2 = 0;
        }
        return j3 - j2;
    }

    @CanIgnoreReturnValue
    public static long parseUnsignedLong(String str) {
        return parseUnsignedLong(str, 10);
    }

    @CanIgnoreReturnValue
    public static long parseUnsignedLong(String str, int i) {
        Preconditions.checkNotNull(str);
        if (str.length() == 0) {
            throw new NumberFormatException("empty string");
        } else if (i < 2 || i > 36) {
            throw new NumberFormatException("illegal radix: " + i);
        } else {
            int i2 = b.c[i] - 1;
            long j = 0;
            for (int i3 = 0; i3 < str.length(); i3++) {
                int digit = Character.digit(str.charAt(i3), i);
                if (digit == -1) {
                    throw new NumberFormatException(str);
                } else if (i3 <= i2 || !b.a(j, digit, i)) {
                    j = (j * i) + digit;
                } else {
                    throw new NumberFormatException("Too large for unsigned long: " + str);
                }
            }
            return j;
        }
    }

    @CanIgnoreReturnValue
    public static long decode(String str) {
        a a2 = a.a(str);
        try {
            return parseUnsignedLong(a2.a, a2.b);
        } catch (NumberFormatException e) {
            NumberFormatException numberFormatException = new NumberFormatException("Error parsing value: " + str);
            numberFormatException.initCause(e);
            throw numberFormatException;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static final class b {
        static final long[] a = new long[37];
        static final int[] b = new int[37];
        static final int[] c = new int[37];

        static {
            BigInteger bigInteger = new BigInteger("10000000000000000", 16);
            for (int i = 2; i <= 36; i++) {
                long j = i;
                a[i] = UnsignedLongs.divide(-1L, j);
                b[i] = (int) UnsignedLongs.remainder(-1L, j);
                c[i] = bigInteger.toString(i).length() - 1;
            }
        }

        static boolean a(long j, int i, int i2) {
            if (j < 0) {
                return true;
            }
            long[] jArr = a;
            if (j < jArr[i2]) {
                return false;
            }
            return j > jArr[i2] || i > b[i2];
        }
    }

    public static String toString(long j) {
        return toString(j, 10);
    }

    public static String toString(long j, int i) {
        long j2;
        Preconditions.checkArgument(i >= 2 && i <= 36, "radix (%s) must be between Character.MIN_RADIX and Character.MAX_RADIX", i);
        int i2 = (j > 0L ? 1 : (j == 0L ? 0 : -1));
        if (i2 == 0) {
            return "0";
        }
        if (i2 > 0) {
            return Long.toString(j, i);
        }
        char[] cArr = new char[64];
        int length = cArr.length;
        int i3 = i - 1;
        if ((i & i3) == 0) {
            int numberOfTrailingZeros = Integer.numberOfTrailingZeros(i);
            do {
                length--;
                cArr[length] = Character.forDigit(((int) j) & i3, i);
                j >>>= numberOfTrailingZeros;
            } while (j != 0);
        } else {
            if ((i & 1) == 0) {
                j2 = (j >>> 1) / (i >>> 1);
            } else {
                j2 = divide(j, i);
            }
            long j3 = i;
            length--;
            cArr[length] = Character.forDigit((int) (j - (j2 * j3)), i);
            while (j2 > 0) {
                length--;
                cArr[length] = Character.forDigit((int) (j2 % j3), i);
                j2 /= j3;
            }
        }
        return new String(cArr, length, cArr.length - length);
    }
}
