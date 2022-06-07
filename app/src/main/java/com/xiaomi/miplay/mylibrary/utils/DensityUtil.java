package com.xiaomi.miplay.mylibrary.utils;

import android.content.Context;

/* loaded from: classes4.dex */
public class DensityUtil {
    private static float a(float f) {
        if (f <= 1.0f) {
            return 1.0f;
        }
        if (f <= 1.5d) {
            return 1.5f;
        }
        if (f <= 2.0f) {
            return 2.0f;
        }
        if (f <= 3.0f) {
            return 3.0f;
        }
        return f;
    }

    public static float dip2px(Context context, float f) {
        return (f * a(context)) + 0.5f;
    }

    public static int px2dip(Context context, float f) {
        return (int) ((f / a(context)) + 0.5f);
    }

    public static int px2sp(Context context, float f) {
        return (int) ((f / a(context)) + 0.5f);
    }

    public static int sp2px(Context context, float f) {
        return (int) ((f * a(context)) + 0.5f);
    }

    private static float a(Context context) {
        return a(context.getResources().getDisplayMetrics().scaledDensity);
    }
}
