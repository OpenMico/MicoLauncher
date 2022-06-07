package com.xiaomi.micolauncher.common.event;

/* loaded from: classes3.dex */
public class UploadLoginUrlEvent {
    private boolean a;
    private String b;

    public UploadLoginUrlEvent(boolean z) {
        this.a = z;
    }

    public UploadLoginUrlEvent(boolean z, String str) {
        this.a = z;
        this.b = str;
    }

    public boolean isSuccess() {
        return this.a;
    }

    public String getResult() {
        return this.b;
    }
}
