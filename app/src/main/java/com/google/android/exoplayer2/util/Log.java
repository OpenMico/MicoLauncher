package com.google.android.exoplayer2.util;

import android.text.TextUtils;
import androidx.annotation.Nullable;
import java.net.UnknownHostException;
import org.checkerframework.dataflow.qual.Pure;

/* loaded from: classes2.dex */
public final class Log {
    public static final int LOG_LEVEL_ALL = 0;
    public static final int LOG_LEVEL_ERROR = 3;
    public static final int LOG_LEVEL_INFO = 1;
    public static final int LOG_LEVEL_OFF = Integer.MAX_VALUE;
    public static final int LOG_LEVEL_WARNING = 2;
    private static int a = 0;
    private static boolean b = true;

    private Log() {
    }

    @Pure
    public static int getLogLevel() {
        return a;
    }

    public static void setLogLevel(int i) {
        a = i;
    }

    public static void setLogStackTraces(boolean z) {
        b = z;
    }

    @Pure
    public static void d(String str, String str2) {
        if (a == 0) {
            android.util.Log.d(str, str2);
        }
    }

    @Pure
    public static void d(String str, String str2, @Nullable Throwable th) {
        d(str, a(str2, th));
    }

    @Pure
    public static void i(String str, String str2) {
        if (a <= 1) {
            android.util.Log.i(str, str2);
        }
    }

    @Pure
    public static void i(String str, String str2, @Nullable Throwable th) {
        i(str, a(str2, th));
    }

    @Pure
    public static void w(String str, String str2) {
        if (a <= 2) {
            android.util.Log.w(str, str2);
        }
    }

    @Pure
    public static void w(String str, String str2, @Nullable Throwable th) {
        w(str, a(str2, th));
    }

    @Pure
    public static void e(String str, String str2) {
        if (a <= 3) {
            android.util.Log.e(str, str2);
        }
    }

    @Pure
    public static void e(String str, String str2, @Nullable Throwable th) {
        e(str, a(str2, th));
    }

    @Nullable
    @Pure
    public static String getThrowableString(@Nullable Throwable th) {
        if (th == null) {
            return null;
        }
        if (a(th)) {
            return "UnknownHostException (no network)";
        }
        if (!b) {
            return th.getMessage();
        }
        return android.util.Log.getStackTraceString(th).trim().replace("\t", "    ");
    }

    @Pure
    private static String a(String str, @Nullable Throwable th) {
        String throwableString = getThrowableString(th);
        if (TextUtils.isEmpty(throwableString)) {
            return str;
        }
        String valueOf = String.valueOf(str);
        String replace = throwableString.replace("\n", "\n  ");
        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 4 + String.valueOf(replace).length());
        sb.append(valueOf);
        sb.append("\n  ");
        sb.append(replace);
        sb.append('\n');
        return sb.toString();
    }

    @Pure
    private static boolean a(@Nullable Throwable th) {
        while (th != null) {
            if (th instanceof UnknownHostException) {
                return true;
            }
            th = th.getCause();
        }
        return false;
    }
}
