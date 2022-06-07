package com.zlw.main.recorderlib.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Environment;
import android.os.SystemClock;
import android.util.Log;
import java.io.File;
import java.util.Locale;

/* loaded from: classes4.dex */
public class Logger {
    public static boolean IsDebug = true;
    public static boolean LOGD = false;
    public static boolean LOGI = false;
    private static final String a = "Logger";
    private static boolean b = false;
    private static boolean c = true;
    private static boolean d = true;

    @TargetApi(19)
    private static void a(String str, String str2, Throwable th) {
    }

    static {
        boolean z = IsDebug;
        LOGD = z;
        LOGI = z;
    }

    public static void v(String str, String str2, Object... objArr) {
        if (b) {
            String a2 = a(str2, objArr);
            String a3 = a("^_^" + str, 28);
            Log.v(a3, a2);
            a(a3, a2);
        }
    }

    public static void v(Throwable th, String str, String str2, Object... objArr) {
        if (b) {
            String a2 = a(str2, objArr);
            String a3 = a("^_^" + str, 28);
            Log.v(a3, a2, th);
            a(a3, a2, th);
        }
    }

    public static void d(String str, String str2, Object... objArr) {
        if (LOGD) {
            String a2 = a(str2, objArr);
            String a3 = a("^_^" + str, 28);
            Log.d(a3, a2);
            a(a3, a2);
        }
    }

    public static void d(Throwable th, String str, String str2, Object... objArr) {
        if (LOGD) {
            String a2 = a(str2, objArr);
            String a3 = a("^_^" + str, 28);
            Log.d(a3, a2, th);
            a(a3, a2, th);
        }
    }

    public static void i(String str, String str2, Object... objArr) {
        if (LOGI) {
            String a2 = a(str2, objArr);
            String a3 = a("^_^" + str, 28);
            Log.i(a3, a2);
            a(a3, a2);
        }
    }

    public static void i(Throwable th, String str, String str2, Object... objArr) {
        if (LOGI) {
            String a2 = a(str2, objArr);
            String a3 = a("^_^" + str, 28);
            Log.i(a3, a2, th);
            a(a3, a2, th);
        }
    }

    public static void w(String str, String str2, Object... objArr) {
        if (c) {
            String a2 = a(str2, objArr);
            String a3 = a("^_^" + str, 28);
            Log.w(a3, a2);
            a(a3, a2);
        }
    }

    public static void w(Throwable th, String str, String str2, Object... objArr) {
        if (c) {
            String a2 = a(str2, objArr);
            String a3 = a("^_^" + str, 28);
            Log.w(a3, a2, th);
            a(a3, a2, th);
        }
    }

    public static void e(String str, String str2, Object... objArr) {
        if (d) {
            String a2 = a(str2, objArr);
            String a3 = a("^_^" + str, 28);
            Log.e(a3, a2);
            a(a3, a2);
        }
    }

    public static void e(Throwable th, String str, String str2, Object... objArr) {
        if (d) {
            String a2 = a(str2, objArr);
            String a3 = a("^_^" + str, 28);
            Log.e(a3, a2, th);
            a(a3, a2, th);
        }
    }

    private static void a(String str, String str2) {
        a(str, str2, null);
    }

    public static void printCaller() {
        if (IsDebug) {
            try {
                StackTraceElement[] stackTrace = new Throwable().fillInStackTrace().getStackTrace();
                StringBuilder sb = new StringBuilder();
                sb.append("print caller info\n==========BEGIN OF CALLER INFO============\n");
                for (int i = 2; i < stackTrace.length; i++) {
                    String className = stackTrace[i].getClassName();
                    sb.append(String.format(Locale.US, "[%03d] %s.%s(%s:%d)", Long.valueOf(Thread.currentThread().getId()), className.substring(className.lastIndexOf(46) + 1), stackTrace[i].getMethodName(), stackTrace[i].getFileName(), Integer.valueOf(stackTrace[i].getLineNumber())));
                    sb.append("\n");
                }
                sb.append("==========END OF CALLER INFO============");
                i(a, sb.toString(), new Object[0]);
            } catch (Exception e) {
                e(e, a, e.getMessage(), new Object[0]);
            }
        }
    }

    private static String a(String str, Object[] objArr) {
        int i;
        if (objArr != null) {
            try {
                if (objArr.length != 0) {
                    str = String.format(Locale.US, str, objArr);
                }
            } catch (Exception e) {
                e(e, a, e.getMessage(), new Object[0]);
                return "----->ERROR LOG STRING<------";
            }
        }
        if (!IsDebug) {
            return str;
        }
        StackTraceElement[] stackTrace = new Throwable().fillInStackTrace().getStackTrace();
        String str2 = "";
        String str3 = "";
        String str4 = "";
        int i2 = 2;
        while (true) {
            if (i2 >= stackTrace.length) {
                i = 0;
                break;
            } else if (!stackTrace[i2].getClass().equals(Logger.class)) {
                String className = stackTrace[i2].getClassName();
                str3 = className.substring(className.lastIndexOf(46) + 1);
                str2 = stackTrace[i2].getMethodName();
                str4 = stackTrace[i2].getFileName();
                i = stackTrace[i2].getLineNumber();
                break;
            } else {
                i2++;
            }
        }
        return String.format(Locale.US, "%s> %s", a(String.format(Locale.US, "[%03d] %s.%s(%s:%d)", Long.valueOf(Thread.currentThread().getId()), str3, str2, str4, Integer.valueOf(i)), 93), str);
    }

    private static String a(String str, int i) {
        StringBuilder sb = new StringBuilder();
        if (str.length() >= i) {
            sb.append(str);
        } else {
            sb.append(str);
            sb.append("====================================================================================================".substring(0, i - str.length()));
        }
        return sb.toString();
    }

    @TargetApi(19)
    public static File getAvailableExternalCacheDir(Context context) {
        File[] externalCacheDirs = context.getExternalCacheDirs();
        for (int length = externalCacheDirs.length - 1; length >= 0; length--) {
            File file = externalCacheDirs[length];
            if (file != null && "mounted".equals(Environment.getStorageState(file))) {
                return file;
            }
        }
        return null;
    }

    public static boolean isFileExists(String str) {
        return isFileExists(new File(str));
    }

    public static boolean isFileExists(File file) {
        return file != null && file.exists();
    }

    /* loaded from: classes4.dex */
    public static class TimeCalculator {
        long a = SystemClock.elapsedRealtime();

        public long end() {
            return SystemClock.elapsedRealtime() - this.a;
        }
    }
}
