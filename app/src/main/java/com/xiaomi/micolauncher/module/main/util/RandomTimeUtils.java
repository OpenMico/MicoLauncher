package com.xiaomi.micolauncher.module.main.util;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class RandomTimeUtils {
    public static final int MAX_EXPONENT = 16;

    /* loaded from: classes3.dex */
    private static final class a {
        static final Random a = new Random();
    }

    public static Random getRandomGenerator() {
        return a.a;
    }

    public static long getRandomTimeInMills(long j) {
        return (long) (Math.random() * j);
    }

    public static long getRandomMillisInNextMin(int i) {
        int nextInt = a.a.nextInt((int) TimeUnit.MINUTES.toSeconds(i));
        if (nextInt < 20) {
            nextInt += a.a.nextInt(200);
        }
        return TimeUnit.SECONDS.toMillis(nextInt);
    }

    public static long getExponentRandomTimeInMills(int i, long j) {
        double pow = Math.pow(2.0d, i - 1);
        double d = j;
        return (long) ((pow * d) + (Math.random() * d));
    }

    public static long getAnchorOfFactorInMillis(long j, long j2) {
        if (j < j2) {
            return j + j2;
        }
        int nextInt = a.a.nextInt((int) j2);
        return (nextInt & 1) == 1 ? j + nextInt : j - nextInt;
    }
}
