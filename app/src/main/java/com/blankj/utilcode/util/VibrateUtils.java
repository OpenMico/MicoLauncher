package com.blankj.utilcode.util;

import android.os.Vibrator;
import androidx.annotation.RequiresPermission;

/* loaded from: classes.dex */
public final class VibrateUtils {
    private static Vibrator a;

    private VibrateUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    @RequiresPermission("android.permission.VIBRATE")
    public static void vibrate(long j) {
        Vibrator a2 = a();
        if (a2 != null) {
            a2.vibrate(j);
        }
    }

    @RequiresPermission("android.permission.VIBRATE")
    public static void vibrate(long[] jArr, int i) {
        Vibrator a2 = a();
        if (a2 != null) {
            a2.vibrate(jArr, i);
        }
    }

    @RequiresPermission("android.permission.VIBRATE")
    public static void cancel() {
        Vibrator a2 = a();
        if (a2 != null) {
            a2.cancel();
        }
    }

    private static Vibrator a() {
        if (a == null) {
            a = (Vibrator) Utils.getApp().getSystemService("vibrator");
        }
        return a;
    }
}
