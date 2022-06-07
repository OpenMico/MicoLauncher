package com.xiaomi.miplay.mylibrary.session.utils;

import android.util.Log;

/* loaded from: classes4.dex */
public class Logger {
    private static boolean a = false;

    public static void setDebug(boolean z) {
        a = z;
    }

    public static void v(String str, String str2, Object... objArr) {
        if (a) {
            Log.v(str, a(str2, objArr));
        }
    }

    public static void v(Throwable th, String str, String str2, Object... objArr) {
        if (a) {
            Log.v(str, a(str2, objArr), th);
        }
    }

    public static void v(String str, String str2, Throwable th) {
        v(th, str, str2, new Object[0]);
    }

    public static void d(String str, String str2, Object... objArr) {
        if (a) {
            Log.d(str, a(str2, objArr));
        }
    }

    public static void d(Throwable th, String str, String str2, Object... objArr) {
        if (a) {
            Log.d(str, a(str2, objArr), th);
        }
    }

    public static void d(String str, String str2, Throwable th) {
        d(th, str, str2, new Object[0]);
    }

    public static void i(String str, String str2, Object... objArr) {
        Log.i(str, a(str2, objArr));
    }

    public static void i(Throwable th, String str, String str2, Object... objArr) {
        Log.i(str, a(str2, objArr), th);
    }

    public static void i(String str, String str2, Throwable th) {
        i(th, str, str2, new Object[0]);
    }

    public static void w(String str, String str2, Object... objArr) {
        Log.w(str, a(str2, objArr));
    }

    public static void w(Throwable th, String str, String str2, Object... objArr) {
        Log.w(str, a(str2, objArr), th);
    }

    public static void w(String str, String str2, Throwable th) {
        w(th, str, str2, new Object[0]);
    }

    public static void e(String str, String str2, Object... objArr) {
        Log.e(str, a(str2, objArr));
    }

    public static void e(Throwable th, String str, String str2, Object... objArr) {
        Log.e(str, a(str2, objArr), th);
    }

    public static void e(String str, String str2, Throwable th) {
        e(th, str, str2, new Object[0]);
    }

    private static String a(String str, Object... objArr) {
        return objArr.length == 0 ? str : String.format(str, objArr);
    }
}
