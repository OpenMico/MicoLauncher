package com.xiaomi.micolauncher.skills.video.controller.uievent;

/* loaded from: classes3.dex */
public class VideoInvokeDetailEvent {
    private String a;
    private int b;

    public static boolean isValidEpisodeIndex(int i) {
        return i != 0;
    }

    public VideoInvokeDetailEvent(String str, int i) {
        this.a = str;
        this.b = i;
    }

    public String getVideoId() {
        return this.a;
    }

    public int getIndex() {
        return this.b;
    }

    public boolean hasValidEpisodeIndex() {
        return isValidEpisodeIndex(this.b);
    }
}
