package com.xiaomi.smarthome.devicelibrary.bluetooth;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

/* loaded from: classes4.dex */
public class BluetoothContextManager {
    private static Handler a;
    private static Context b;

    public static void setContext(Context context) {
        b = context.getApplicationContext();
    }

    public static Context getContext() {
        return b;
    }

    public static void post(Runnable runnable) {
        postDelayed(runnable, 0L);
    }

    public static void postDelayed(Runnable runnable, long j) {
        if (a == null) {
            a = new Handler(Looper.getMainLooper());
        }
        a.postDelayed(runnable, j);
    }

    public static String getCurrentMethodName() {
        return Thread.currentThread().getStackTrace()[4].getMethodName();
    }
}
