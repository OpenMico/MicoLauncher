package com.xiaomi.passport.ui.internal.util;

import android.content.res.Resources;
import android.util.DisplayMetrics;

/* loaded from: classes4.dex */
public class Build extends android.os.Build {
    public static final boolean IS_MIPAD = "mocha".equals(DEVICE);
    public static final boolean IS_N7 = "flo".equals(DEVICE);
    public static final boolean IS_TABLET = isTablet();

    private static boolean isTablet() {
        if (IS_N7 || IS_MIPAD) {
            return true;
        }
        DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
        return ((int) ((((float) Math.min(displayMetrics.widthPixels, displayMetrics.heightPixels)) / displayMetrics.density) + 0.5f)) >= 600;
    }
}
