package com.xiaomi.micolauncher.module.initialize;

import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;

/* loaded from: classes3.dex */
public class AccessPoint {
    public boolean isConnected;
    public boolean isSaved;
    public ScanResult scanResult;
    public WifiConfiguration wifiConfiguration;

    public int sortScore() {
        if (this.isConnected) {
            return 10000;
        }
        if (this.isSaved) {
            return this.scanResult.level + 1000;
        }
        return this.scanResult.level + 200;
    }
}
