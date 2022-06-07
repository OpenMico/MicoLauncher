package com.xiaomi.micolauncher.skills.common;

/* loaded from: classes3.dex */
public class VideoPlayerEvent {
    private VideoPlayerStatus a;

    /* loaded from: classes3.dex */
    public enum VideoPlayerStatus {
        VIDEO_PLAYER_PLAYING,
        VIDEO_PLAYER_PAUSE,
        VIDEO_PLAYER_STOP,
        VIDEO_PLAYER_COMPLETE,
        VIDEO_PLAYER_LIST_COMPLETE
    }

    public VideoPlayerEvent(VideoPlayerStatus videoPlayerStatus) {
        this.a = videoPlayerStatus;
    }

    public VideoPlayerStatus getEvent() {
        return this.a;
    }
}
