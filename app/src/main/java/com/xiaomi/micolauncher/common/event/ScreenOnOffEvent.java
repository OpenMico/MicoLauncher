package com.xiaomi.micolauncher.common.event;

/* loaded from: classes3.dex */
public class ScreenOnOffEvent {
    private boolean a;

    public ScreenOnOffEvent(boolean z) {
        this.a = z;
    }

    public boolean isScreenOn() {
        return this.a;
    }

    public String toString() {
        return "ScreenOnOffEvent{screenOn=" + this.a + '}';
    }
}
