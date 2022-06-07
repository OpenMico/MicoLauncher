package com.xiaomi.micolauncher.common.utils;

import android.content.Context;
import android.provider.Settings;

/* loaded from: classes3.dex */
public class SecurePrefUtils {
    public static int getSecureInt(Context context, String str) throws Settings.SettingNotFoundException {
        return Settings.Secure.getInt(context.getContentResolver(), str);
    }

    public static String getSecureString(Context context, String str) {
        return Settings.Secure.getString(context.getContentResolver(), str);
    }

    public static boolean putSecureInt(Context context, String str, int i) {
        return Settings.Secure.putInt(context.getContentResolver(), str, i);
    }

    public static boolean putSecureString(Context context, String str, String str2) {
        return Settings.Secure.putString(context.getContentResolver(), str, str2);
    }
}
