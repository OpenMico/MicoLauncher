package com.milink.base.utils;

import android.util.Log;
import androidx.annotation.NonNull;
import com.milink.base.itf.ILogger;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class Logger implements ILogger {
    private static final Logger a = new Logger();

    private Logger() {
    }

    public static Logger get() {
        return a;
    }

    public static void v(@NonNull String str, @NonNull String str2, Object... objArr) {
        a(2, str, null, str2, objArr);
    }

    public static void d(@NonNull String str, @NonNull String str2, Object... objArr) {
        a(3, str, null, str2, objArr);
    }

    public static void i(@NonNull String str, @NonNull String str2, Object... objArr) {
        a(4, str, null, str2, objArr);
    }

    public static void w(@NonNull String str, @NonNull String str2, Object... objArr) {
        a(5, str, null, str2, objArr);
    }

    public static void e(@NonNull String str, @NonNull String str2, Object... objArr) {
        a(6, str, null, str2, objArr);
    }

    public static void v(@NonNull String str, Throwable th, @NonNull String str2, Object... objArr) {
        a(2, str, th, str2, objArr);
    }

    public static void d(@NonNull String str, Throwable th, @NonNull String str2, Object... objArr) {
        a(3, str, th, str2, objArr);
    }

    public static void i(@NonNull String str, Throwable th, @NonNull String str2, Object... objArr) {
        a(4, str, th, str2, objArr);
    }

    public static void w(@NonNull String str, Throwable th, @NonNull String str2, Object... objArr) {
        a(5, str, th, str2, objArr);
    }

    public static void e(@NonNull String str, Throwable th, @NonNull String str2, Object... objArr) {
        a(6, str, th, str2, objArr);
    }

    private static void a(int i, String str, Throwable th, String str2, Object... objArr) {
        if (a(i)) {
            a.log(i, str, String.format((String) Objects.requireNonNull(str2), objArr), th);
        }
    }

    private static boolean a(int i) {
        return i > 1 || Log.isLoggable("milink-log", i);
    }

    @Override // com.milink.base.itf.ILogger
    public void log(int i, String str, String str2, Throwable th) {
        a(i, str, str2, th);
    }

    private void a(int i, String str, String str2, Throwable th) {
        String str3 = "new_milink:" + str;
        switch (i) {
            case 2:
                Log.v(str3, str2, th);
                return;
            case 3:
                Log.d(str3, str2, th);
                return;
            case 4:
                Log.i(str3, str2, th);
                return;
            case 5:
                Log.w(str3, str2, th);
                return;
            case 6:
                Log.e(str3, str2, th);
                return;
            default:
                return;
        }
    }
}
