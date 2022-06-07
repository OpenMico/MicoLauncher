package com.xiaomi.mico.common;

import android.content.Context;

/* loaded from: classes3.dex */
public class TypefaceUtil {
    public static String FONT_WEIGHT_W200;
    public static String FONT_WEIGHT_W300;
    public static String FONT_WEIGHT_W400;
    public static String FONT_WEIGHT_W500;
    public static String FONT_WEIGHT_W600;
    public static String FONT_WEIGHT_W700;
    public static String FONT_WEIGHT_W800;

    public static void init(Context context) {
        FONT_WEIGHT_W200 = context.getString(R.string.font_weight_w200);
        FONT_WEIGHT_W300 = context.getString(R.string.font_weight_w300);
        FONT_WEIGHT_W400 = context.getString(R.string.font_weight_w400);
        FONT_WEIGHT_W500 = context.getString(R.string.font_weight_w500);
        FONT_WEIGHT_W600 = context.getString(R.string.font_weight_w600);
        FONT_WEIGHT_W700 = context.getString(R.string.font_weight_w700);
        FONT_WEIGHT_W800 = context.getString(R.string.font_weight_w800);
    }

    private TypefaceUtil() {
    }
}
