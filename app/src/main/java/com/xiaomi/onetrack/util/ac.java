package com.xiaomi.onetrack.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;
import java.util.TimeZone;

/* loaded from: classes4.dex */
public class ac {
    public static final long a = 604800000;
    public static final int b = 86400000;
    public static final int c = 43200000;
    public static final int d = 3600000;
    public static final int e = 60000;
    public static final int f = 1000;
    private static final String g = "TimeUtil";
    private static final long h = 300000;
    private static long i;
    private static long j;
    private static long k;

    public static long a() {
        return System.currentTimeMillis();
    }

    public static boolean a(long j2, long j3) {
        return Math.abs(System.currentTimeMillis() - j2) >= j3;
    }

    public static boolean a(long j2) {
        p.a(g, "inToday,current ts :" + a());
        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(a());
        instance.set(11, 0);
        instance.set(12, 0);
        instance.set(13, 0);
        instance.set(14, 0);
        long timeInMillis = instance.getTimeInMillis();
        long j3 = timeInMillis + 86400000;
        p.a(g, "[start]:" + timeInMillis + "\n[end]:" + j3 + "duration" + ((j3 - timeInMillis) - 86400000));
        StringBuilder sb = new StringBuilder();
        sb.append("is in today:");
        int i2 = (timeInMillis > j2 ? 1 : (timeInMillis == j2 ? 0 : -1));
        sb.append(i2 <= 0 && j2 < j3);
        p.a(g, sb.toString());
        return i2 <= 0 && j2 < j3;
    }

    public static boolean b(long j2) {
        p.a(g, "inTodayClientTime,current ts :" + System.currentTimeMillis());
        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(System.currentTimeMillis());
        instance.set(11, 0);
        instance.set(12, 0);
        instance.set(13, 0);
        instance.set(14, 0);
        long timeInMillis = instance.getTimeInMillis();
        long j3 = timeInMillis + 86400000;
        p.a(g, "[start]:" + timeInMillis + "\n[end]:" + j3 + "duration" + ((j3 - timeInMillis) - 86400000));
        StringBuilder sb = new StringBuilder();
        sb.append("is in today:");
        int i2 = (timeInMillis > j2 ? 1 : (timeInMillis == j2 ? 0 : -1));
        sb.append(i2 <= 0 && j2 < j3);
        p.a(g, sb.toString());
        return i2 <= 0 && j2 < j3;
    }

    public static boolean a(long j2, int i2) {
        return Math.abs(System.currentTimeMillis() - j2) >= ((long) (i2 + new Random().nextInt(i2 / 2)));
    }

    public static String c(long j2) {
        try {
            Calendar instance = Calendar.getInstance();
            instance.setTimeInMillis(j2);
            return new SimpleDateFormat("HH:mm:ss yy-MM-dd").format(instance.getTime());
        } catch (Exception unused) {
            return "";
        }
    }

    public static long b() {
        Calendar calendar;
        try {
            calendar = Calendar.getInstance(TimeZone.getTimeZone(q.b()));
        } catch (Exception unused) {
            calendar = Calendar.getInstance();
        }
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);
        return calendar.getTimeInMillis();
    }

    public static boolean d(long j2) {
        Calendar instance = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        Calendar instance2 = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        instance2.setTimeInMillis(j2);
        return instance2.get(1) == instance.get(1) && instance2.get(2) == instance.get(2) && instance2.get(5) == instance.get(5);
    }
}
