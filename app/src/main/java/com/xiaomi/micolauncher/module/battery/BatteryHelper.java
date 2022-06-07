package com.xiaomi.micolauncher.module.battery;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class BatteryHelper {
    public static final int LOW_BATTERY_THRESHOLD_MAX = 20;
    public static final int LOW_BATTERY_THRESHOLD_MEDIUM = 10;
    public static final int LOW_BATTERY_THRESHOLD_MIN = 5;
    private static final int[] a = {5, 10, 20};

    public static boolean shouldShowPrompt(int i) {
        return Arrays.binarySearch(a, i) >= 0;
    }

    public static long convertScreenOffTime(int i) {
        if (i == 0) {
            return TimeUnit.SECONDS.toMillis(30L);
        }
        switch (i) {
            case 2:
                return TimeUnit.MINUTES.toMillis(2L);
            case 3:
                return TimeUnit.MINUTES.toMillis(5L);
            case 4:
                return TimeUnit.MINUTES.toMillis(10L);
            case 5:
                return 2147483647L;
            default:
                return TimeUnit.MINUTES.toMillis(1L);
        }
    }
}
