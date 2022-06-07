package com.xiaomi.miplay.videoswitchmirror;

import android.content.Context;

/* loaded from: classes4.dex */
public class MiPlayClientManage {
    private MiplayClientControl a;

    public MiPlayClientManage(Context context) {
        this.a = new MiplayClientControl(context);
    }

    public boolean initAsync(MiplayVideoSwitchMirrorCallback miplayVideoSwitchMirrorCallback) {
        return this.a.initAsync(miplayVideoSwitchMirrorCallback);
    }

    public void unInit() {
        this.a.unInit();
    }

    public int connectMirrorSuccess(String str) {
        return this.a.connectMirrorSuccess(str);
    }

    public int disconnectMirror(String str) {
        return this.a.disconnectMirror(str);
    }

    public int resumeMirrorSuccess() {
        return this.a.resumeMirrorSuccess();
    }

    public int resumeMirrorFail() {
        return this.a.resumeMirrorFail();
    }

    public int startDiscovery(int i) {
        return this.a.startDiscovery(i);
    }

    public int getMirrorMode(String str) {
        return this.a.getMirrorMode(str);
    }

    public int userDisconnectMirror(String str) {
        return this.a.userDisconnectMirror(str);
    }
}
