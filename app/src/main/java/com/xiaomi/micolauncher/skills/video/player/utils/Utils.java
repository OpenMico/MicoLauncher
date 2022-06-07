package com.xiaomi.micolauncher.skills.video.player.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.blankj.utilcode.constant.CacheConstants;
import com.xiaomi.mipush.sdk.Constants;

/* loaded from: classes3.dex */
public class Utils {
    public static String formatTime(int i) {
        if (i < 0) {
            return "";
        }
        int i2 = i / 1000;
        int i3 = i2 / CacheConstants.HOUR;
        int i4 = i2 % CacheConstants.HOUR;
        int i5 = i4 / 60;
        int i6 = i4 % 60;
        StringBuilder sb = new StringBuilder();
        if (i3 > 0) {
            sb.append(i3);
            sb.append(Constants.COLON_SEPARATOR);
        }
        if (i5 < 10) {
            sb.append("0");
        }
        sb.append(i5);
        sb.append(Constants.COLON_SEPARATOR);
        if (i6 < 10) {
            sb.append("0");
        }
        sb.append(i6);
        return sb.toString();
    }

    public static boolean isNetworkConncected(Context context) {
        NetworkInfo activeNetworkInfo;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        if (connectivityManager == null || (activeNetworkInfo = connectivityManager.getActiveNetworkInfo()) == null) {
            return false;
        }
        return activeNetworkInfo.isConnected();
    }

    public static int getUid(Context context, String str) {
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(str, 0);
            if (applicationInfo != null) {
                return applicationInfo.uid;
            }
            return -1;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}
