package com.xiaomi.micolauncher.common.event;

/* loaded from: classes3.dex */
public class LoginResultEvent {
    boolean a;

    public LoginResultEvent(boolean z) {
        this.a = false;
        this.a = z;
    }

    public boolean getResult() {
        return this.a;
    }
}
