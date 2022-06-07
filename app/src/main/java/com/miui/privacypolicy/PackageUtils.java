package com.miui.privacypolicy;

import android.content.Context;
import android.util.Log;

/* loaded from: classes2.dex */
public class PackageUtils {
    private static final String TAG = "Privacy_PackageUtils";

    private PackageUtils() {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public static String getAppVersionName(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (Exception e) {
            Log.e(TAG, "Exception", e);
            return "";
        }
    }
}
