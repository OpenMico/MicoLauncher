package com.xiaomi.onetrack.util;

import android.app.KeyguardManager;
import android.content.Context;
import android.os.Build;
import android.os.UserManager;
import android.os.storage.StorageManager;
import android.preference.PreferenceManager;

/* loaded from: classes4.dex */
public class j {
    private static final String a = "FbeUtil";

    private j() {
    }

    public static Context a(Context context) {
        if (e(context)) {
            p.a(a, "getSafeContext return origin ctx");
            return context;
        }
        p.a(a, "getSafeContext , create the safe ctx");
        return context.createDeviceProtectedStorageContext();
    }

    public static boolean a() {
        try {
            return ((Boolean) StorageManager.class.getDeclaredMethod("isFileEncryptedNativeOrEmulated", new Class[0]).invoke(null, new Object[0]).getClass().getDeclaredMethod("isFileEncryptedNativeOrEmulated", Boolean.TYPE).invoke(null, new Object[0])).booleanValue();
        } catch (Exception e) {
            p.b(a, "*** " + e);
            return false;
        }
    }

    public static void a(PreferenceManager preferenceManager) {
        boolean z = true;
        boolean z2 = Build.VERSION.SDK_INT == 24 && a();
        boolean z3 = Build.VERSION.SDK_INT == 25 && a();
        if (Build.VERSION.SDK_INT <= 25) {
            z = false;
        }
        if (z2 || z3 || z) {
            preferenceManager.setStorageDeviceProtected();
        }
    }

    public static boolean b(Context context) {
        if (Build.VERSION.SDK_INT < 24) {
            return false;
        }
        try {
            KeyguardManager keyguardManager = (KeyguardManager) context.getSystemService("keyguard");
            if (!a() || keyguardManager == null) {
                return false;
            }
            return keyguardManager.isKeyguardSecure();
        } catch (Exception e) {
            p.a(a, "FBEDeviceAndSetedUpScreenLock Exception: " + e.getMessage());
            return false;
        }
    }

    public static boolean c(Context context) {
        return Build.VERSION.SDK_INT >= 24 && b(context) && !e(context);
    }

    public static boolean d(Context context) {
        return Build.VERSION.SDK_INT >= 24 && !e(context);
    }

    private static boolean e(Context context) {
        if (Build.VERSION.SDK_INT < 24) {
            return false;
        }
        try {
            UserManager userManager = (UserManager) context.getSystemService("user");
            if (userManager != null) {
                return userManager.isUserUnlocked();
            }
            return false;
        } catch (Exception e) {
            p.a(a, "isUserUnlocked Exception: " + e.getMessage());
            return false;
        }
    }

    private static boolean f(Context context) {
        return (Build.VERSION.SDK_INT == 24 || Build.VERSION.SDK_INT == 25) ? context.isDeviceProtectedStorage() || !a() : Build.VERSION.SDK_INT <= 25;
    }
}
