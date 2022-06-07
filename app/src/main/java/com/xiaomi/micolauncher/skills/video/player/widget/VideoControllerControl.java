package com.xiaomi.micolauncher.skills.video.player.widget;

/* loaded from: classes3.dex */
public interface VideoControllerControl {
    int getCurrentPosition();

    int getDuration();

    boolean isPlaying();

    void next();

    void openSelectionList();

    void pause();

    void seekTo(int i);
}
