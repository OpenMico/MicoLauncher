package com.xiaomi.miplay.mylibrary.utils;

import android.text.TextUtils;
import androidx.annotation.NonNull;
import com.xiaomi.micolauncher.module.setting.bluetooth.BluetoothConstants;
import com.xiaomi.miplay.mylibrary.session.utils.Logger;
import java.lang.reflect.Method;

/* loaded from: classes4.dex */
public class SystemProperties {
    private static Class<?> a;
    private static Method b;

    @NonNull
    public static String getString(String str, String str2) {
        try {
            String str3 = (String) b.invoke(a, str, str2);
            return !TextUtils.isEmpty(str3) ? str3 : str2;
        } catch (Exception e) {
            Logger.e("MarketSdkUtils", e.getMessage(), e);
            return str2;
        }
    }

    static {
        try {
            a = Class.forName("android.os.SystemProperties");
            b = a.getDeclaredMethod(BluetoothConstants.GET, String.class, String.class);
        } catch (Exception e) {
            Logger.e("MarketSdkUtils", e.getMessage(), e);
        }
    }
}
