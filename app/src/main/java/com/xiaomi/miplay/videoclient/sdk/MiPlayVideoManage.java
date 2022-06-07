package com.xiaomi.miplay.videoclient.sdk;

import android.content.Context;

/* loaded from: classes4.dex */
public class MiPlayVideoManage {
    private MiplayVideoControl a;

    public MiPlayVideoManage(Context context) {
        this.a = new MiplayVideoControl(context);
    }

    public boolean initAsync(MiplayVideoCallback miplayVideoCallback, String str, String str2) {
        return this.a.initAsync(miplayVideoCallback, str, str2);
    }

    public void unInit() {
        this.a.unInit();
    }

    public void startDiscovery(int i) {
        this.a.startDiscovery(i);
    }

    public void stopDiscovery() {
        this.a.stopDiscovery();
    }

    public int play(String str, int i) {
        return this.a.play(str, i);
    }

    public int stop(String str) {
        return this.a.stop(str);
    }

    public int pause(String str) {
        return this.a.pause(str);
    }

    public int resume(String str) {
        return this.a.resume(str);
    }

    public int seek(String str, long j) {
        return this.a.seek(str, j);
    }

    public int setVolume(String str, double d) {
        return this.a.setVolume(str, d);
    }

    public int getVolume(String str) {
        return this.a.getVolume(str);
    }

    public int getPosition(String str) {
        return this.a.getPosition(str);
    }

    public int getPlayState(String str) {
        return this.a.getPlayState(str);
    }

    public int getMediaInfo(String str) {
        return this.a.getMediaInfo(str);
    }

    public int next(String str) {
        return this.a.next(str);
    }

    public int prev(String str) {
        return this.a.prev(str);
    }

    public boolean getCollectAudio() {
        return this.a.getCollectAudio();
    }
}
