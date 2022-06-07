package com.xiaomi.analytics.internal.util;

import android.util.Log;
import com.xiaomi.micolauncher.module.setting.bluetooth.BluetoothConstants;

/* loaded from: classes3.dex */
public class SystemProperties {
    public static String get(String str, String str2) {
        try {
            return (String) Class.forName("android.os.SystemProperties").getMethod(BluetoothConstants.GET, String.class, String.class).invoke(null, str, str2);
        } catch (Exception e) {
            Log.e(ALog.addPrefix("SystemProperties"), "get e", e);
            return str2;
        }
    }

    public static String get(String str) {
        return get(str, "");
    }
}
