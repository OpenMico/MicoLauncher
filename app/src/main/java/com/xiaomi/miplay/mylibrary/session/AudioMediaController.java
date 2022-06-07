package com.xiaomi.miplay.mylibrary.session;

import com.xiaomi.miplay.mylibrary.session.ActiveSessionRecord;
import com.xiaomi.miplay.mylibrary.session.data.MediaMetaData;

/* loaded from: classes4.dex */
public class AudioMediaController {
    private ActiveSessionRecord.MediaControllerStub a;

    public AudioMediaController(ActiveSessionRecord.MediaControllerStub mediaControllerStub) {
        this.a = mediaControllerStub;
    }

    public void next() {
        this.a.next();
    }

    public void previous() {
        this.a.previous();
    }

    public void play() {
        this.a.play();
    }

    public void pause() {
        this.a.pause();
    }

    public long getPosition() {
        return this.a.getPosition();
    }

    public MediaMetaData getMediaMetaData() {
        return this.a.getMediaMetaData();
    }

    public int getPlaybackState() {
        return this.a.getPlaybackState();
    }

    public void seekTo(long j) {
        this.a.seekTo(j);
    }
}
