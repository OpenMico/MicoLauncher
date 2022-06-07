package com.xiaomi.miplay.audioclient.tv;

/* loaded from: classes3.dex */
public interface MiPlayTVClientInterface {
    int audioFcControl();

    int getMediaInfo();

    int getPlayState();

    int getPosition();

    String getSourceName();

    int getVolume();

    boolean initAsync(MiPlayTVClientCallback miPlayTVClientCallback);

    boolean initAsync(MiPlayTVClientCallback miPlayTVClientCallback, String str);

    int musicRelay(String str, int i);

    int onNext();

    int onPause();

    int onPrev();

    int onResume();

    int onSeek(long j);

    int setLocalDeviceInfo(String str);

    int setLocalMediaInfo(TVMediaMetaData tVMediaMetaData);

    int setLocalMediaState(int i);

    int setMiplayAudioFcControl(int i);

    int setMiplayVolumeControl(int i);

    int setVolume(int i);

    int stop();

    void unInit();

    int volumeControl();
}
