package com.xiaomi.micolauncher.skills.video.controller.uievent;

import com.xiaomi.micolauncher.skills.video.model.VideoItem;

/* loaded from: classes3.dex */
public class VideoPlayNextEvent {
    public static final int NEXT = -1;
    public static final int PREV = -2;
    public int ci;
    public int index;
    public boolean isReport;
    public VideoItem videoItem;

    public VideoPlayNextEvent() {
        this.isReport = false;
        this.index = -1;
    }

    public VideoPlayNextEvent(int i) {
        this.isReport = false;
        this.index = i;
    }
}
