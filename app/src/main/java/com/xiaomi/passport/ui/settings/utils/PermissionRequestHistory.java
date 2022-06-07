package com.xiaomi.passport.ui.settings.utils;

import android.app.Activity;
import android.content.Context;
import androidx.core.app.ActivityCompat;
import com.xiaomi.passport.utils.SharedPreferencesUtil;

/* loaded from: classes4.dex */
public class PermissionRequestHistory {
    private static final String PREFERENCE_NAME = "permission_request_history";

    public static void setPermissionRequested(Context context, String str) {
        new SharedPreferencesUtil(context, PREFERENCE_NAME).saveBoolean(str, true);
    }

    public static boolean getPermissionRequested(Context context, String str) {
        return new SharedPreferencesUtil(context, PREFERENCE_NAME).getBoolean(str, false);
    }

    public static boolean isPermissionPermanentlyDenied(Activity activity, String str) {
        return getPermissionRequested(activity, str) && !ActivityCompat.shouldShowRequestPermissionRationale(activity, str);
    }
}
