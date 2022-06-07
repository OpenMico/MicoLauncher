package com.xiaomi.passport.ui.settings.utils;

import android.content.Context;

/* loaded from: classes4.dex */
public class PackageUtils {
    public static boolean isDebuggable(Context context) {
        return (context.getApplicationInfo().flags & 2) != 0;
    }
}
