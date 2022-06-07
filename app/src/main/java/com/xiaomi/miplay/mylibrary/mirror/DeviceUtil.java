package com.xiaomi.miplay.mylibrary.mirror;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.util.Log;
import androidx.annotation.NonNull;
import com.xiaomi.smarthome.library.common.network.Network;

/* loaded from: classes4.dex */
public class DeviceUtil {
    private static final String a = "DeviceUtil";

    public static String getWifiIpAddress(@NonNull Context context) {
        Log.d(a, "getWifiIpAddress");
        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Network.NETWORK_TYPE_WIFI);
        if (!wifiManager.isWifiEnabled()) {
            return "";
        }
        int ipAddress = wifiManager.getConnectionInfo().getIpAddress();
        String str = (ipAddress & 255) + "." + ((ipAddress >> 8) & 255) + "." + ((ipAddress >> 16) & 255) + "." + ((ipAddress >> 24) & 255);
        Log.d(a, "IpAddress:" + str);
        return str;
    }
}
