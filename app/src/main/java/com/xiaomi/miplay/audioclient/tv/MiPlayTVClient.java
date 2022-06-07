package com.xiaomi.miplay.audioclient.tv;

import android.content.Context;

/* loaded from: classes3.dex */
public class MiPlayTVClient {
    private MiPlayTVClientImpl a;

    public MiPlayTVClient(Context context) {
        this.a = new MiPlayTVClientImpl(context);
    }

    public boolean initAsync(MiPlayTVClientCallback miPlayTVClientCallback) {
        return this.a.initAsync(miPlayTVClientCallback);
    }

    public boolean initAsync(MiPlayTVClientCallback miPlayTVClientCallback, String str) {
        return this.a.initAsync(miPlayTVClientCallback, str);
    }

    public void unInit() {
        this.a.unInit();
    }

    public int stop() {
        return this.a.stop();
    }

    public int Pause() {
        return this.a.onPause();
    }

    public int Resume() {
        return this.a.onResume();
    }

    public int getPlayState() {
        return this.a.getPlayState();
    }

    public int seek(long j) {
        return this.a.onSeek(j);
    }

    public int getPosition() {
        return this.a.getPosition();
    }

    public int getMediaInfo() {
        return this.a.getMediaInfo();
    }

    public int setVolume(int i) {
        return this.a.setVolume(i);
    }

    public int getVolume() {
        return this.a.getVolume();
    }

    public int onNext() {
        return this.a.onNext();
    }

    public int onPrev() {
        return this.a.onPrev();
    }

    public String getSourceName() {
        return this.a.getSourceName();
    }

    public int musicRelay(String str, int i) {
        return this.a.musicRelay(str, i);
    }

    public int setLocalDeviceInfo(String str) {
        return this.a.setLocalDeviceInfo(str);
    }

    public int setLocalMediaInfo(TVMediaMetaData tVMediaMetaData) {
        return this.a.setLocalMediaInfo(tVMediaMetaData);
    }

    public int setLocalMediaState(int i) {
        return this.a.setLocalMediaState(i);
    }

    public int volumeControl() {
        return this.a.volumeControl();
    }

    public int setMiplayVolumeControl(int i) {
        return this.a.setMiplayVolumeControl(i);
    }

    public int audioFcControl() {
        return this.a.audioFcControl();
    }

    public int setMiplayAudioFcControl(int i) {
        return this.a.setMiplayAudioFcControl(i);
    }
}
