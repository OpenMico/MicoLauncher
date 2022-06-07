package com.xiaomi.micolauncher.common.event;

/* loaded from: classes3.dex */
public class ScreenSaverSwitchEvent {
    private boolean a;

    public ScreenSaverSwitchEvent(boolean z) {
        this.a = z;
    }

    public boolean screenSaverSwitchOn() {
        return this.a;
    }

    public String toString() {
        return "ScreenSaverSwitchEvent{screenSaverSwitch=" + this.a + '}';
    }
}
