package com.xiaomi.micolauncher.skills.video.controller.uievent;

/* loaded from: classes3.dex */
public class VideoSelectEvent {
    public String dialogId;
    public int index;

    public VideoSelectEvent(int i) {
        this.index = i;
    }

    public VideoSelectEvent(int i, String str) {
        this.index = i;
        this.dialogId = str;
    }
}
