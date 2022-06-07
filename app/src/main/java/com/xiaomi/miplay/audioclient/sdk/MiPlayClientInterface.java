package com.xiaomi.miplay.audioclient.sdk;

import com.xiaomi.miplay.audioclient.MediaMetaData;
import com.xiaomi.miplay.audioclient.MiPlayDevice;
import com.xiaomi.miplay.audioclient.MiPlayDeviceControlCenter;
import java.util.List;

/* loaded from: classes3.dex */
public interface MiPlayClientInterface {
    int closeDevice(String[] strArr);

    int getBtFrequency(String[] strArr);

    int getChannel(String[] strArr);

    boolean getCollectAudio();

    List<MiPlayDevice> getDevices();

    MediaMetaData getLocalMediaInfo();

    int getMediaInfo(String[] strArr);

    int getPlayState(String[] strArr);

    int getPosition(String[] strArr);

    List<MiPlayDeviceControlCenter> getStereoDevices(String str);

    int getVolume(String[] strArr);

    boolean initAsync(MiPlayClientCallback miPlayClientCallback, String str);

    boolean initAsync(MiPlayClientCallback miPlayClientCallback, String str, String str2);

    boolean isDiscovering();

    int onNext(String[] strArr);

    int onPause(String[] strArr);

    int onPlay(String[] strArr, String str);

    int onPlay(String[] strArr, String str, int i);

    int onPrev(String[] strArr);

    int onRefreshDeviceInfo();

    int onResume(String[] strArr);

    int onSeek(String[] strArr, long j);

    int onTimelineChange();

    int setBoxPause(String[] strArr);

    int setBoxResume(String[] strArr);

    int setBtFrequency(String[] strArr, int i);

    int setChannel(String str, int i);

    int setMediaInfo(String[] strArr, MediaMetaData mediaMetaData);

    int setVolume(String[] strArr, int i);

    void startDiscovery(int i);

    int stop(String[] strArr);

    void stopDiscovery();

    void stopUwbDiscovery();

    void unInit();
}
