package com.xiaomi.micolauncher.common.utils;

import android.os.Build;

/* loaded from: classes3.dex */
public class BuildHelper {
    public static boolean isAndroidO() {
        return Build.VERSION.SDK_INT == 26 || Build.VERSION.SDK_INT == 27;
    }

    public static boolean isAndroidP() {
        return Build.VERSION.SDK_INT == 28;
    }
}
