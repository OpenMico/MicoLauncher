package com.xiaomi.micolauncher.common.utils;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import androidx.annotation.NonNull;
import com.elvishew.xlog.Logger;
import com.xiaomi.mico.common.Hardware;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.onetrack.a.a;

/* loaded from: classes3.dex */
public class BatteryUtils {
    private static volatile boolean a = false;
    private static volatile boolean b = false;
    private static volatile boolean c;

    public static int getDefaultScaleValue() {
        return 100;
    }

    public static int getLowBatteryThreshold() {
        return 20;
    }

    public static boolean isBatteryPresent(Context context) {
        if (b) {
            return a;
        }
        boolean z = true;
        b = true;
        Intent registerReceiver = context.registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
        if (registerReceiver == null) {
            L.util.e("batteryStatus is null.");
            return false;
        }
        if (!Hardware.isX08E() || !registerReceiver.getBooleanExtra("present", false)) {
            z = false;
        }
        a = z;
        return a;
    }

    public static boolean isPowerConnected(Context context) {
        if (b && !a) {
            return true;
        }
        IntentFilter intentFilter = new IntentFilter("android.intent.action.BATTERY_CHANGED");
        intentFilter.setPriority(1000);
        int intExtra = context.registerReceiver(null, intentFilter).getIntExtra("status", 0);
        if (intExtra == 2 || intExtra == 5) {
            c = true;
        } else {
            c = false;
        }
        Logger logger = L.util;
        logger.d("BatteryUtils", "isPowerConnected: " + c);
        return c;
    }

    public static boolean isLowBatterLevel(Context context) {
        Intent registerReceiver = context.registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
        if (registerReceiver == null) {
            L.util.e("batteryStatus is null.");
            return false;
        }
        int intExtra = registerReceiver.getIntExtra("status", -1);
        if (intExtra == 2) {
            L.util.d("Battery is in charging.");
            return false;
        } else if (intExtra == 5) {
            L.util.d("Battery is full.");
            return false;
        } else {
            int batteryLevel = getBatteryLevel(registerReceiver);
            if (batteryLevel >= 20) {
                return false;
            }
            Logger logger = L.util;
            logger.i("Low battery level: " + batteryLevel);
            return true;
        }
    }

    public static int getBatteryLevel(@NonNull Intent intent) {
        int intExtra = intent.getIntExtra(a.d, 0);
        int intExtra2 = intent.getIntExtra("scale", 100);
        int calculateBatteryLevel = calculateBatteryLevel(intExtra, intExtra2);
        L.battery.d("Battery level is %s, scale is %s, battery level is %s", Integer.valueOf(intExtra), Integer.valueOf(intExtra2), Integer.valueOf(calculateBatteryLevel));
        return calculateBatteryLevel;
    }

    public static int calculateBatteryLevel(int i, int i2) {
        return (i * 100) / i2;
    }
}
