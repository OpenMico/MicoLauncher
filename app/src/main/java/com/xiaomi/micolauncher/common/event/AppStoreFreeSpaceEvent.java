package com.xiaomi.micolauncher.common.event;

import com.google.gson.annotations.SerializedName;

/* loaded from: classes3.dex */
public class AppStoreFreeSpaceEvent {
    @SerializedName("alarm")
    public boolean alarm;

    public AppStoreFreeSpaceEvent(boolean z) {
        this.alarm = z;
    }
}
