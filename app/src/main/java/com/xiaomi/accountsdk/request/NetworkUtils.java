package com.xiaomi.accountsdk.request;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import com.umeng.analytics.pro.ai;
import com.xiaomi.accountsdk.utils.CloudCoder;
import com.xiaomi.smarthome.library.common.network.Network;

/* loaded from: classes2.dex */
public class NetworkUtils {
    public static String getNetworkNameForMiUrlStat(Context context) {
        return deleteUrlUnsafeChar(getActiveConnPoint(context));
    }

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        if (connectivityManager == null) {
            return true;
        }
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    static String getActiveConnPoint(Context context) {
        String str = null;
        if (context == null) {
            return null;
        }
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
            if (connectivityManager == null) {
                return "";
            }
            try {
                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                if (activeNetworkInfo == null) {
                    return "";
                }
                if (activeNetworkInfo.getType() != 1) {
                    return String.format("%s-%s-%s", activeNetworkInfo.getTypeName(), replacePlusToPChar(activeNetworkInfo.getSubtypeName()), activeNetworkInfo.getExtraInfo()).toLowerCase();
                }
                str = "";
                try {
                    WifiManager wifiManager = (WifiManager) context.getSystemService(Network.NETWORK_TYPE_WIFI);
                    if (wifiManager.getConnectionInfo() != null) {
                        str = wifiManager.getConnectionInfo().getSSID();
                    }
                } catch (Exception unused) {
                }
                if (TextUtils.isEmpty(str)) {
                    return Network.NETWORK_TYPE_WIFI;
                }
                return "wifi-" + CloudCoder.hashDeviceInfo(str).substring(0, 3).toLowerCase();
            } catch (Exception unused2) {
                return "";
            }
        } catch (Exception unused3) {
            return "";
        }
    }

    static String replacePlusToPChar(String str) {
        if (str != null) {
            return str.replaceAll("\\+", ai.av);
        }
        return null;
    }

    static String deleteUrlUnsafeChar(String str) {
        if (str != null) {
            return str.replaceAll("[^a-zA-Z0-9-_.]", "");
        }
        return null;
    }
}
