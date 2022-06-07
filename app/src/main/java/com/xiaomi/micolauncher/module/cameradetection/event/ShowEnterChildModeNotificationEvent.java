package com.xiaomi.micolauncher.module.cameradetection.event;

import android.content.Context;

/* loaded from: classes3.dex */
public class ShowEnterChildModeNotificationEvent {
    public Context context;
    public int status;

    public ShowEnterChildModeNotificationEvent(Context context, int i) {
        this.context = context;
        this.status = i;
    }

    public ShowEnterChildModeNotificationEvent(Context context) {
        this.context = context;
    }
}
