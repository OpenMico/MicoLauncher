package com.xiaomi.miplay.videoswitchmirror.api;

import com.xiaomi.miplay.videoswitchmirror.MiplayVideoSwitchMirrorCallback;

/* loaded from: classes4.dex */
public interface IMiplayClient {
    int connectMirrorSuccess(String str);

    int disconnectMirror(String str);

    int getMirrorMode(String str);

    boolean initAsync(MiplayVideoSwitchMirrorCallback miplayVideoSwitchMirrorCallback);

    int resumeMirrorFail();

    int resumeMirrorSuccess();

    int startDiscovery(int i);

    void unInit();

    int userDisconnectMirror(String str);
}
