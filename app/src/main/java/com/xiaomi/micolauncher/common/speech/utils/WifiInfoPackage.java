package com.xiaomi.micolauncher.common.speech.utils;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import com.xiaomi.smarthome.library.common.network.Network;

/* loaded from: classes3.dex */
public class WifiInfoPackage {
    public String bssid;
    public int rssi;
    public String ssid;

    public WifiInfoPackage(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Network.NETWORK_TYPE_WIFI);
        if (wifiManager.isWifiEnabled()) {
            WifiInfo connectionInfo = wifiManager.getConnectionInfo();
            this.bssid = connectionInfo.getBSSID();
            this.ssid = connectionInfo.getSSID();
            if (this.ssid.startsWith("\"") && this.ssid.endsWith("\"")) {
                String str = this.ssid;
                this.ssid = str.substring(1, str.length() - 1);
            }
            this.rssi = connectionInfo.getRssi();
            return;
        }
        this.bssid = "";
        this.ssid = "";
        this.rssi = -127;
    }
}
