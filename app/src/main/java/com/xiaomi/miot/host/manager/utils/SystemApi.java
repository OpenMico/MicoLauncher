package com.xiaomi.miot.host.manager.utils;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;

/* loaded from: classes2.dex */
public class SystemApi {
    private static SystemApi sInstance;
    private static Object sLock = new Object();

    public String getOsName() {
        return "Android";
    }

    private SystemApi() {
    }

    public static SystemApi getInstance() {
        if (sInstance == null) {
            synchronized (sLock) {
                if (sInstance == null) {
                    sInstance = new SystemApi();
                }
            }
        }
        return sInstance;
    }

    public String getOsVersion() {
        return !TextUtils.isEmpty(Build.VERSION.RELEASE) ? Build.VERSION.RELEASE : "unknown";
    }

    public String getDeviceModel() {
        try {
            checkName(Build.MODEL);
            return Build.MODEL;
        } catch (Exception unused) {
            return "unknown";
        }
    }

    public String getDeviceBrand() {
        try {
            checkName(Build.BRAND);
            return Build.BRAND;
        } catch (Exception unused) {
            return "UnknownBrand";
        }
    }

    public String getOsIncremental() {
        return Build.VERSION.INCREMENTAL;
    }

    public String getAppVersion(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (Exception e) {
            e.printStackTrace();
            return "1.0.0";
        }
    }

    public static void checkName(String str) throws Exception {
        if (str == null) {
            throw new IllegalArgumentException("name == null");
        } else if (!str.isEmpty()) {
            int length = str.length();
            for (int i = 0; i < length; i++) {
                char charAt = str.charAt(i);
                if (charAt <= 31 || charAt >= 127) {
                    throw new IllegalArgumentException(String.format("Unexpected char %#04x at %d in header name: %s", Integer.valueOf(charAt), Integer.valueOf(i), str));
                }
            }
        } else {
            throw new IllegalArgumentException("name is empty");
        }
    }
}
