package java8.lang;

/* loaded from: classes5.dex */
public final class Doubles {
    public static double sum(double d, double d2) {
        return d + d2;
    }

    public static int hashCode(double d) {
        long doubleToLongBits = Double.doubleToLongBits(d);
        return (int) (doubleToLongBits ^ (doubleToLongBits >>> 32));
    }

    public static double max(double d, double d2) {
        return Math.max(d, d2);
    }

    public static double min(double d, double d2) {
        return Math.min(d, d2);
    }

    public static boolean isFinite(double d) {
        return Math.abs(d) <= Double.MAX_VALUE;
    }

    private Doubles() {
    }
}
