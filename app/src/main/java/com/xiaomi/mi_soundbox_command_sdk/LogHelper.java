package com.xiaomi.mi_soundbox_command_sdk;

import android.os.Process;
import android.util.Log;

/* loaded from: classes3.dex */
public class LogHelper {
    public static void i(String str) {
        Log.i("MyService", str + ", process=" + Process.myPid());
    }

    public static void i(String str, Throwable th) {
        Log.i("MyService", str + ", process=" + Process.myPid(), th);
    }

    public static void e(String str) {
        Log.e("MyService", str + ", process=" + Process.myPid());
    }

    public static void e(String str, Throwable th) {
        Log.e("MyService", str + ", process=" + Process.myPid(), th);
    }
}
