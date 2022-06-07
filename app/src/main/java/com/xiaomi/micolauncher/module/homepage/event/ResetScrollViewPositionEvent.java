package com.xiaomi.micolauncher.module.homepage.event;

/* loaded from: classes3.dex */
public class ResetScrollViewPositionEvent {
    public static final int EVENT_TYPE_LAUNCHER_IDLE_OVER_TIME = 1;
    private int a;

    public ResetScrollViewPositionEvent(int i) {
        this.a = i;
    }

    public int getEventType() {
        return this.a;
    }

    public void setEventType(int i) {
        this.a = i;
    }
}
