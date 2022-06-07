package afu.org.checkerframework.checker.units;

/* loaded from: classes.dex */
public class UnitsTools {
    public static final int A = 1;
    public static final int C = 1;
    public static final int K = 1;
    public static final int cd = 1;
    public static final double deg = 1.0d;
    public static final int g = 1;
    public static final int h = 1;
    public static final int kg = 1;
    public static final int km = 1;
    public static final int km2 = 1;
    public static final int kmPERh = 1;
    public static final int m = 1;
    public static final int m2 = 1;
    public static final int mPERs = 1;
    public static final int mPERs2 = 1;
    public static final int min = 1;
    public static final int mm = 1;
    public static final int mm2 = 1;
    public static final int mol = 1;
    public static final double rad = 1.0d;
    public static final int s = 1;

    public static int fromCelsiusToKelvin(int i) {
        return i + 273;
    }

    public static int fromHourToMinute(int i) {
        return i * 60;
    }

    public static int fromKelvinToCelsius(int i) {
        return i - 273;
    }

    public static int fromKiloGramToGram(int i) {
        return i * 1000;
    }

    public static double fromKiloMeterPerHourToMeterPerSecond(double d) {
        return d / 3.6d;
    }

    public static int fromKiloMeterToMeter(int i) {
        return i * 1000;
    }

    public static double fromMeterPerSecondToKiloMeterPerHour(double d) {
        return d * 3.6d;
    }

    public static int fromMeterToMilliMeter(int i) {
        return i * 1000;
    }

    public static int fromMinuteToSecond(int i) {
        return i * 60;
    }

    public static double toRadians(double d) {
        return Math.toRadians(d);
    }

    public static double toDegrees(double d) {
        return Math.toDegrees(d);
    }

    public static int fromMilliMeterToMeter(int i) {
        return i / 1000;
    }

    public static int fromMeterToKiloMeter(int i) {
        return i / 1000;
    }

    public static int fromGramToKiloGram(int i) {
        return i / 1000;
    }

    public static int fromSecondToMinute(int i) {
        return i / 60;
    }

    public static int fromMinuteToHour(int i) {
        return i / 60;
    }
}
