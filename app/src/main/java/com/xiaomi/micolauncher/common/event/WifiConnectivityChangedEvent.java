package com.xiaomi.micolauncher.common.event;

/* loaded from: classes3.dex */
public class WifiConnectivityChangedEvent {
    public final boolean connected;
    public final boolean isWifiSetting;

    public WifiConnectivityChangedEvent(boolean z, boolean z2) {
        this.connected = z;
        this.isWifiSetting = z2;
    }

    public boolean isConnectedStablely() {
        return this.connected && !this.isWifiSetting;
    }

    public boolean isNotConnectedStablely() {
        return !this.connected && !this.isWifiSetting;
    }
}
