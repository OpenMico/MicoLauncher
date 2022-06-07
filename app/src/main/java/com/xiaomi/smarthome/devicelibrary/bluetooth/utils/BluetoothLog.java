package com.xiaomi.smarthome.devicelibrary.bluetooth.utils;

import android.util.Log;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Level;

/* loaded from: classes4.dex */
public class BluetoothLog {
    private static boolean a() {
        return false;
    }

    public static void f(Level level, String str) {
    }

    public static String getNonNullString(String str) {
        return str != null ? str : "";
    }

    public static boolean isShowSearchLog() {
        return false;
    }

    public static void i(String str) {
        if (a()) {
            Log.i("miio-bluetooth", str);
            f(Level.INFO, str);
        }
    }

    public static void e(String str) {
        if (a()) {
            Log.e("miio-bluetooth", str);
            f(Level.SEVERE, str);
        }
    }

    public static void v(String str) {
        if (a()) {
            Log.v("miio-bluetooth", str);
            f(Level.FINE, str);
        }
    }

    public static void d(String str) {
        if (a()) {
            Log.d("miio-bluetooth", str);
            f(Level.FINE, str);
        }
    }

    public static void w(String str) {
        if (a()) {
            Log.w("miio-bluetooth", str);
            f(Level.WARNING, str);
        }
    }

    public static void e(Throwable th) {
        e(a(th));
    }

    public static void w(Throwable th) {
        w(a(th));
    }

    public static void formatLog(String str, Object... objArr) {
        if (a()) {
            String format = String.format(str, objArr);
            Log.d("miio-bluetooth", format);
            f(Level.FINE, format);
        }
    }

    private static String a(Throwable th) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        while (th != null) {
            th.printStackTrace(printWriter);
            th = th.getCause();
        }
        String obj = stringWriter.toString();
        printWriter.close();
        return getNonNullString(obj);
    }

    public static void debug(String str) {
        w(Log.getStackTraceString(new Throwable(str)));
    }
}
