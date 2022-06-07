package com.xiaomi.mico.base.utils;

import android.content.Context;
import android.os.PowerManager;
import android.os.SystemClock;
import com.xiaomi.mico.utils.LogCallback;
import com.xiaomi.mico.utils.UtilsConfig;

/* loaded from: classes3.dex */
public class ScreenUtil {
    private static boolean a = true;

    public static void turnScreenOn(Context context) {
        turnScreenOn(context, false);
    }

    public static void turnScreenOff(Context context) {
        turnScreenOff(context, false);
    }

    public static void turnScreenOn(Context context, boolean z) {
        if (context != null) {
            ((PowerManager) context.getSystemService("power")).wakeUp(SystemClock.uptimeMillis());
            a = !z;
        }
        LogCallback logCallback = UtilsConfig.getLogCallback();
        if (logCallback != null) {
            logCallback.i("%s turn screen on by voice %s", "ScreenUtil ", Boolean.valueOf(z));
        }
    }

    public static void turnScreenOff(Context context, boolean z) {
        ((PowerManager) context.getSystemService("power")).goToSleep(SystemClock.uptimeMillis());
        a = !z;
        LogCallback logCallback = UtilsConfig.getLogCallback();
        if (logCallback != null) {
            logCallback.i("%s turn screen off by voice %s", "ScreenUtil ", Boolean.valueOf(z));
        }
    }

    public static void setFromPwrKey() {
        a = true;
    }

    public static boolean isFromPwrKey() {
        return a;
    }
}
