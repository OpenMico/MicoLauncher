package com.allenliu.versionchecklib.v2.eventbus;

/* loaded from: classes.dex */
public class BaseEvent {
    private int a;

    public int getEventType() {
        return this.a;
    }

    public BaseEvent setEventType(int i) {
        this.a = i;
        return this;
    }
}
