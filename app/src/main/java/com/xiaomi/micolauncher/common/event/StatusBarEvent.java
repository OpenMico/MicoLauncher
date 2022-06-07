package com.xiaomi.micolauncher.common.event;

/* loaded from: classes3.dex */
public class StatusBarEvent {
    private StatusBarType a;
    private boolean b;
    private boolean c;

    /* loaded from: classes3.dex */
    public enum StatusBarType {
        STATUS_BAR_TYPE_MIC,
        STATUS_BAR_TYPE_NIGHT_MODE,
        STATUS_BAR_TYPE_WIFI
    }

    public StatusBarEvent(StatusBarType statusBarType, boolean z) {
        this.a = statusBarType;
        this.b = z;
    }

    public StatusBarEvent(StatusBarType statusBarType, boolean z, boolean z2) {
        this.a = statusBarType;
        this.b = z;
        this.c = z2;
    }

    public StatusBarType getType() {
        return this.a;
    }

    public boolean isOfflineStatus() {
        return this.b;
    }

    public boolean isWifiSetting() {
        return this.c;
    }
}
