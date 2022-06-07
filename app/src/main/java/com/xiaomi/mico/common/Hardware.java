package com.xiaomi.mico.common;

import android.content.Context;
import android.os.Build;
import androidx.annotation.StringRes;

/* JADX WARN: Failed to restore enum class, 'enum' modifier removed */
/* loaded from: classes3.dex */
public abstract class Hardware extends Enum<Hardware> implements HardwareConfig {
    public static final Hardware HARDWARE = a();

    public static Hardware current(Context context) {
        return a();
    }

    private static Hardware a() {
        return valueOf(getBuildModel());
    }

    @StringRes
    public static int getDefaultNameRes(Context context) {
        return current(context).getDefaultAlias();
    }

    public static String getDefaultName(Context context) {
        return context.getString(current(context).getDefaultAlias());
    }

    public static boolean isBigScreen() {
        return isX08() || isX10() || a() == X6A;
    }

    public static boolean isSmallScreen() {
        return a() == LX04;
    }

    public static boolean isX10() {
        return a() == X10A;
    }

    public static boolean isX08() {
        return a() == X08A || a() == X08C || a() == X08E;
    }

    public static boolean isX08A(Context context) {
        return current(context) == X08A;
    }

    public static boolean isX08C() {
        return a() == X08C;
    }

    public static boolean isX08E() {
        return a() == X08E;
    }

    public static boolean isSupportSpec() {
        Hardware a2 = a();
        return a2 == X08E || a2 == X10A;
    }

    public static boolean isLx04() {
        return a() == LX04;
    }

    public static boolean isX6A() {
        return a() == X6A;
    }

    public static boolean hasBattery() {
        return isX08E();
    }

    public static boolean hasCameraCapability(Context context) {
        if (isLx04()) {
            return false;
        }
        return context.getPackageManager().hasSystemFeature("android.hardware.camera");
    }

    public static String getBuildModel() {
        String str = Build.MODEL;
        return "AS04".equalsIgnoreCase(str) ? "LX04" : "mico_x10a".equalsIgnoreCase(str) ? "X10A" : str;
    }
}
