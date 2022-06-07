package com.xiaomi.analytics.internal.util;

/* loaded from: classes3.dex */
public class TimeUtils {
    public static int HALF_DAY_IN_MS = 43200000;
    public static int ONE_DAY_IN_MS = 86400000;
    public static int ONE_HOUR_IN_MS = 3600000;
    public static int ONE_MINUTE_IN_MS = 60000;
    public static int ONE_SECOND_IN_MS = 1000;
    public static int ONE_WEEK_IN_MS = 604800000;

    public static boolean expired(long j, long j2) {
        return Math.abs(System.currentTimeMillis() - j) > j2;
    }
}
