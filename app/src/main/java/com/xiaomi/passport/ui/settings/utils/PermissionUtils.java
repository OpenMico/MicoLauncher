package com.xiaomi.passport.ui.settings.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import com.xiaomi.micolauncher.module.setting.bluetooth.BluetoothConstants;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes4.dex */
public class PermissionUtils {
    private static final String MANUFACTURER = Build.MANUFACTURER.toLowerCase();

    public static void openPermissionSetting(Activity activity, int i) {
        Intent intent;
        if (MANUFACTURER.contains("xiaomi")) {
            String modDevice = getModDevice();
            if (modDevice == null || modDevice.contains("_global")) {
                intent = null;
            } else {
                intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
                intent.putExtra("extra_pkgname", activity.getPackageName());
            }
        } else if (MANUFACTURER.contains("huawei")) {
            if (Build.VERSION.SDK_INT < 23) {
                intent = new Intent();
                intent.setComponent(new ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity"));
            }
            intent = null;
        } else {
            if (!MANUFACTURER.contains("oppo")) {
                if (MANUFACTURER.contains("vivo")) {
                    intent = new Intent();
                    intent.putExtra("packagename", activity.getPackageName());
                    if (Build.VERSION.SDK_INT >= 25) {
                        intent.setComponent(new ComponentName("com.vivo.permissionmanager", "com.vivo.permissionmanager.activity.SoftPermissionDetailActivity"));
                    } else {
                        intent.setComponent(new ComponentName("com.iqoo.secure", "com.iqoo.secure.safeguard.SoftPermissionDetailActivity"));
                    }
                } else if (!MANUFACTURER.contains("samsung")) {
                    if (!MANUFACTURER.contains("meizu")) {
                        MANUFACTURER.contains("smartisan");
                    } else if (Build.VERSION.SDK_INT < 25) {
                        intent = new Intent("com.meizu.safe.security.SHOW_APPSEC");
                        intent.putExtra("packageName", activity.getPackageName());
                        intent.setComponent(new ComponentName("com.meizu.safe", "com.meizu.safe.security.AppSecActivity"));
                    }
                }
            }
            intent = null;
        }
        if (intent != null) {
            try {
                activity.startActivityForResult(intent, i);
            } catch (ActivityNotFoundException | SecurityException unused) {
                intent = null;
            }
        }
        if (intent == null) {
            Intent intent2 = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
            intent2.setData(Uri.fromParts("package", activity.getPackageName(), null));
            activity.startActivityForResult(intent2, i);
        }
    }

    @SuppressLint({"PrivateApi"})
    private static String getModDevice() {
        try {
            Class<?> cls = Class.forName("android.os.SystemProperties");
            return (String) cls.getDeclaredMethod(BluetoothConstants.GET, String.class).invoke(cls, "ro.product.mod_device");
        } catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }
}
