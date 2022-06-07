package com.xiaomi.mico.base.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import java.util.Locale;

/* loaded from: classes3.dex */
public class VersionUtils {
    private static String a(String str, int i) {
        int intValue = Integer.valueOf(str).intValue();
        int i2 = intValue % i;
        int i3 = intValue / i;
        int i4 = i3 % i;
        return String.format(Locale.ENGLISH, "%d.%d.%d", Integer.valueOf((i3 / i) % i), Integer.valueOf(i4), Integer.valueOf(i2));
    }

    public static String getVersionName(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getFormatVersionCode(Context context) {
        return a(String.valueOf(getVersionCode(context)), 1000);
    }

    public static int getVersionCode(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            if (packageInfo != null) {
                return packageInfo.versionCode;
            }
            return 0;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
