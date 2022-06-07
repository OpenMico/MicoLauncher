package com.xiaomi.smarthome.library.common.util;

import android.os.Build;

/* loaded from: classes4.dex */
public class Version {
    public static boolean isMarshmallow() {
        return Build.VERSION.SDK_INT >= 23;
    }
}
