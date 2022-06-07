package com.xiaomi.mi_connect_sdk.util;

import android.content.Context;
import android.content.pm.PackageManager;
import com.xiaomi.mi_connect_sdk.Version;

/* loaded from: classes3.dex */
public class VersionUtil {
    public static String getVersionName(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getSdkVersion() {
        return Version.getMiconnectSpecVersionCode() + "." + Version.getSdkVersionCode() + "." + Version.getAidlVersionCode() + "." + Version.getLittleVersionCode();
    }
}
