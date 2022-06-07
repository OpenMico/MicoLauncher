package com.xiaomi.onetrack.util;

import android.util.Log;
import com.xiaomi.micolauncher.module.setting.bluetooth.BluetoothConstants;

/* loaded from: classes4.dex */
public class ab {
    private static final String a = "SystemProperties";

    public static String a(String str, String str2) {
        try {
            return (String) Class.forName("android.os.SystemProperties").getMethod(BluetoothConstants.GET, String.class, String.class).invoke(null, str, str2);
        } catch (Throwable th) {
            String a2 = p.a(a);
            Log.e(a2, "get e" + th.getMessage());
            return str2;
        }
    }

    public static String a(String str) {
        return a(str, "");
    }

    public static long a(String str, Long l) {
        try {
            return ((Long) Class.forName("android.os.SystemProperties").getMethod("getLong", String.class, Long.TYPE).invoke(null, str, l)).longValue();
        } catch (Throwable th) {
            String a2 = p.a(a);
            Log.e(a2, "getLong e" + th.getMessage());
            return l.longValue();
        }
    }
}
