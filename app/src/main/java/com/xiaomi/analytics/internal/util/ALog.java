package com.xiaomi.analytics.internal.util;

import android.util.Log;

/* loaded from: classes3.dex */
public class ALog {
    public static boolean sEnable = false;

    public static void d(String str, String str2) {
        if (sEnable) {
            Log.d(addPrefix(str), str2);
        }
    }

    public static void d(String str, String str2, Throwable th) {
        if (sEnable) {
            Log.d(addPrefix(str), str2, th);
        }
    }

    public static void e(String str, String str2) {
        if (sEnable) {
            Log.e(addPrefix(str), str2);
        }
    }

    public static void e(String str, String str2, Throwable th) {
        if (sEnable) {
            Log.e(addPrefix(str), str2, th);
        }
    }

    public static void w(String str, String str2) {
        if (sEnable) {
            Log.w(addPrefix(str), str2);
        }
    }

    public static void w(String str, String str2, Throwable th) {
        if (sEnable) {
            Log.w(addPrefix(str), str2, th);
        }
    }

    public static void i(String str, String str2) {
        if (sEnable) {
            Log.i(addPrefix(str), str2);
        }
    }

    public static void i(String str, String str2, Throwable th) {
        if (sEnable) {
            Log.i(addPrefix(str), str2, th);
        }
    }

    public static String addPrefix(String str) {
        return "Analytics-Api-" + str;
    }
}
