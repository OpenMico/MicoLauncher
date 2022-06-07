package com.xiaomi.analytics.internal.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.provider.Settings;

/* loaded from: classes3.dex */
public class MIUI {
    public static boolean isInternationalBuild() {
        try {
            return ((Boolean) Class.forName("miui.os.Build").getField("IS_INTERNATIONAL_BUILD").get(null)).booleanValue();
        } catch (Exception unused) {
            return false;
        }
    }

    public static boolean isAlphaBuild() {
        try {
            return ((Boolean) Class.forName("miui.os.Build").getField("IS_ALPHA_BUILD").get(null)).booleanValue();
        } catch (Exception unused) {
            return false;
        }
    }

    public static boolean isDevBuild() {
        try {
            return ((Boolean) Class.forName("miui.os.Build").getField("IS_DEVELOPMENT_VERSION").get(null)).booleanValue();
        } catch (Exception unused) {
            return false;
        }
    }

    public static boolean isStableBuild() {
        try {
            return ((Boolean) Class.forName("miui.os.Build").getField("IS_STABLE_VERSION").get(null)).booleanValue();
        } catch (Exception unused) {
            return false;
        }
    }

    @TargetApi(17)
    public static boolean isDeviceProvisioned(Context context) {
        try {
            if (Build.VERSION.SDK_INT < 17) {
                return true;
            }
            boolean z = false;
            if (Settings.Global.getInt(context.getContentResolver(), "device_provisioned", 0) != 0) {
                z = true;
            }
            if (!z) {
                ALog.w("MIUI", "Provisioned: " + z);
            }
            return z;
        } catch (Exception e) {
            ALog.e("MIUI", "isDeviceProvisioned exception", e);
            return true;
        }
    }

    public static boolean shouldNotAccessNetworkOrLocation(Context context, String str) {
        if (isDeviceProvisioned(context)) {
            return false;
        }
        ALog.w(str, "should not access network or location, not provisioned");
        return true;
    }
}
