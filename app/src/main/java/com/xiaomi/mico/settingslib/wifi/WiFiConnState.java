package com.xiaomi.mico.settingslib.wifi;

/* loaded from: classes3.dex */
public enum WiFiConnState {
    SUCCESS(0),
    AUTH(1),
    NETWORK_NOT_AVAILABLE(2),
    MAT_ERROR(3),
    TIMEOUT(6),
    UNKNOWN(6),
    WRONG_PASSWORD(8),
    WRONG_SSID(9),
    WIFI_SIGNAL_WEAK(10);
    
    private final int mCode;

    WiFiConnState(int i) {
        this.mCode = i;
    }

    public int getCode() {
        return this.mCode;
    }
}
