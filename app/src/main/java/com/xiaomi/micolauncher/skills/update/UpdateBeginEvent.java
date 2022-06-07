package com.xiaomi.micolauncher.skills.update;

/* loaded from: classes3.dex */
public class UpdateBeginEvent {
    public boolean silentUpdate;

    public UpdateBeginEvent() {
        this(false);
    }

    public UpdateBeginEvent(boolean z) {
        this.silentUpdate = z;
    }
}
