package com.xiaomi.analytics.internal.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.telephony.TelephonyManager;

/* loaded from: classes3.dex */
public class NetworkUtils {
    private static String a = "NetworkUtils";

    public static int getNetworkType(Context context) {
        switch (getNetState(context)) {
            case MN2G:
                return 1;
            case MN3G:
                return 2;
            case MN4G:
                return 3;
            case WIFI:
                return 10;
            default:
                return 0;
        }
    }

    public static NetState getNetState(Context context) {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            if (activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting()) {
                if (Build.VERSION.SDK_INT >= 16) {
                    if (!connectivityManager.isActiveNetworkMetered()) {
                        return NetState.WIFI;
                    }
                } else if (activeNetworkInfo.getType() == 1) {
                    return NetState.WIFI;
                }
                return a(((TelephonyManager) context.getSystemService("phone")).getNetworkType());
            }
            return NetState.NONE;
        } catch (Exception e) {
            ALog.e(a, "getNetState", e);
            return NetState.NONE;
        }
    }

    private static NetState a(int i) {
        switch (i) {
            case 1:
            case 2:
            case 4:
            case 7:
            case 11:
            case 16:
                return NetState.MN2G;
            case 3:
            case 5:
            case 6:
            case 8:
            case 9:
            case 10:
            case 12:
            case 14:
            case 15:
            case 17:
                return NetState.MN3G;
            case 13:
            case 18:
            case 19:
                return NetState.MN4G;
            default:
                return NetState.NONE;
        }
    }
}
