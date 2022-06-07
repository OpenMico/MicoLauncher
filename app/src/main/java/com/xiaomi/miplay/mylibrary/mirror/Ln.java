package com.xiaomi.miplay.mylibrary.mirror;

import android.util.Log;
import java.io.PrintStream;

/* loaded from: classes4.dex */
public final class Ln {
    private static final a a = a.DEBUG;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public enum a {
        DEBUG,
        INFO,
        WARN,
        ERROR
    }

    private Ln() {
    }

    public static boolean isEnabled(a aVar) {
        return aVar.ordinal() >= a.ordinal();
    }

    public static void d(String str) {
        if (isEnabled(a.DEBUG)) {
            Log.d("scrcpy", str);
            PrintStream printStream = System.out;
            printStream.println("[server] DEBUG: " + str);
        }
    }

    public static void i(String str) {
        if (isEnabled(a.INFO)) {
            Log.i("scrcpy", str);
            PrintStream printStream = System.out;
            printStream.println("[server] INFO: " + str);
        }
    }

    public static void w(String str) {
        if (isEnabled(a.WARN)) {
            Log.w("scrcpy", str);
            PrintStream printStream = System.out;
            printStream.println("[server] WARN: " + str);
        }
    }

    public static void e(String str, Throwable th) {
        if (isEnabled(a.ERROR)) {
            Log.e("scrcpy", str, th);
            PrintStream printStream = System.out;
            printStream.println("[server] ERROR: " + str);
            if (th != null) {
                th.printStackTrace();
            }
        }
    }

    public static void e(String str) {
        e(str, null);
    }
}
