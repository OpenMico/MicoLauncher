package com.xiaomi.miplay.videoclient.sdk.api;

import com.xiaomi.miplay.videoclient.sdk.MiplayVideoCallback;

/* loaded from: classes4.dex */
public interface MiplayVideoClient {
    boolean getCollectAudio();

    int getMediaInfo(String str);

    int getPlayState(String str);

    int getPosition(String str);

    int getVolume(String str);

    boolean initAsync(MiplayVideoCallback miplayVideoCallback, String str, String str2);

    int next(String str);

    int pause(String str);

    int play(String str, int i);

    int prev(String str);

    int resume(String str);

    int seek(String str, long j);

    int setVolume(String str, double d);

    void startDiscovery(int i);

    int stop(String str);

    void stopDiscovery();

    void unInit();
}
