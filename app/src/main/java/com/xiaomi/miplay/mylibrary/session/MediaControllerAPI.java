package com.xiaomi.miplay.mylibrary.session;

import com.xiaomi.miplay.mylibrary.session.data.MediaMetaData;

/* loaded from: classes4.dex */
public interface MediaControllerAPI {
    MediaMetaData getMediaMetaData();

    int getPlaybackState();

    long getPosition();

    void next();

    void pause();

    void play();

    void previous();

    void seekTo(long j);

    void stop();
}
