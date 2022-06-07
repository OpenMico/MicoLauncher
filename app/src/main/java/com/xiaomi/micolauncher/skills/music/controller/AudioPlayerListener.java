package com.xiaomi.micolauncher.skills.music.controller;

/* loaded from: classes3.dex */
public interface AudioPlayerListener {
    void onComplete();

    void onError(int i, Exception exc);

    void onPaused();

    void onPrepareTimeout();

    void onPrepared();

    void onStarted();

    void onStopped();
}
