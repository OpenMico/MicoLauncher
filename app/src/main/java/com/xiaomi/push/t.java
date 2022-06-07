package com.xiaomi.push;

import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.micolauncher.module.setting.bluetooth.BluetoothConstants;

/* loaded from: classes4.dex */
public class t {
    public static String a(String str, String str2) {
        try {
            return (String) Class.forName("android.os.SystemProperties").getMethod(BluetoothConstants.GET, String.class, String.class).invoke(null, str, str2);
        } catch (Exception e) {
            b.m149a("SystemProperties.get: " + e);
            return str2;
        }
    }
}
