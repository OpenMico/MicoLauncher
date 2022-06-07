package com.xiaomi.micolauncher.skills.music.controller;

/* loaded from: classes3.dex */
public interface AudioPlayer {
    int getCurrentPosition();

    long getDuration();

    String getResourcePath();

    boolean isManualPause();

    boolean isPaused();

    boolean isPlaying();

    void pause();

    void play(String str);

    void release();

    void reset();

    void resume();

    void seekTo(long j);

    void setListener(AudioPlayerListener audioPlayerListener);

    void setPlayerControlCallback(PlayerControlCallback playerControlCallback);

    void setVolume(float f);

    void stop();
}
