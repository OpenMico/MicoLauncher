package com.xiaomi.accountsdk.utils;

import java.util.Locale;

/* loaded from: classes2.dex */
public class VersionUtils {
    public static boolean isMiuiStable = false;
    public static String versionValue = String.format(Locale.US, "accountsdk-%d.%d.%d", 18, 11, 26);

    public static void setToMiuiStableVersion() {
        isMiuiStable = true;
    }

    public static boolean isMiuiStableVersion() {
        return isMiuiStable;
    }

    public static String getVersion() {
        return versionValue;
    }
}
