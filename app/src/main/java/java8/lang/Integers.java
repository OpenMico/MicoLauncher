package java8.lang;

/* loaded from: classes5.dex */
public final class Integers {
    public static int compare(int i, int i2) {
        if (i < i2) {
            return -1;
        }
        return i == i2 ? 0 : 1;
    }

    public static int hashCode(int i) {
        return i;
    }

    public static int sum(int i, int i2) {
        return i + i2;
    }

    public static long toUnsignedLong(int i) {
        return i & 4294967295L;
    }

    public static int divideUnsigned(int i, int i2) {
        return (int) (toUnsignedLong(i) / toUnsignedLong(i2));
    }

    public static int remainderUnsigned(int i, int i2) {
        return (int) (toUnsignedLong(i) % toUnsignedLong(i2));
    }

    public static int compareUnsigned(int i, int i2) {
        return compare(i - 2147483648, i2 - 2147483648);
    }

    public static int max(int i, int i2) {
        return Math.max(i, i2);
    }

    public static int min(int i, int i2) {
        return Math.min(i, i2);
    }

    private Integers() {
    }
}
