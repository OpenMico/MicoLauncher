package com.xiaomi.miplay.audioclient.sdk;

import android.content.Context;
import com.xiaomi.miplay.audioclient.MediaMetaData;
import com.xiaomi.miplay.audioclient.MiPlayDevice;
import com.xiaomi.miplay.audioclient.MiPlayDeviceControlCenter;
import java.util.List;

/* loaded from: classes3.dex */
public class MiPlayClient {
    private MiracastClient a;

    public MiPlayClient(Context context) {
        this.a = new MiracastClient(context);
    }

    public boolean initAsync(MiPlayClientCallback miPlayClientCallback, String str) {
        return this.a.initAsync(miPlayClientCallback, str);
    }

    public boolean initAsync(MiPlayClientCallback miPlayClientCallback, String str, String str2) {
        return this.a.initAsync(miPlayClientCallback, str, str2);
    }

    public boolean isDiscovering() {
        return this.a.isDiscovering();
    }

    public void startDiscovery(int i) {
        this.a.startDiscovery(i);
    }

    public void stopDiscovery() {
        this.a.stopDiscovery();
    }

    public void unInit() {
        this.a.unInit();
    }

    public int Play(String[] strArr, boolean z, String str) {
        return this.a.onPlay(strArr, str);
    }

    public int Play(String[] strArr, boolean z, String str, int i) {
        return this.a.onPlay(strArr, str, i);
    }

    public int stop(String[] strArr) {
        return this.a.stop(strArr);
    }

    public int Pause(String[] strArr) {
        return this.a.onPause(strArr);
    }

    public int Resume(String[] strArr) {
        return this.a.onResume(strArr);
    }

    public int getPlayState(String[] strArr) {
        return this.a.getPlayState(strArr);
    }

    public int seek(String[] strArr, long j) {
        return this.a.onSeek(strArr, j);
    }

    public int getPosition(String[] strArr) {
        return this.a.getPosition(strArr);
    }

    public int setChannel(String str, int i) {
        return this.a.setChannel(str, i);
    }

    public int getChannel(String[] strArr) {
        return this.a.getChannel(strArr);
    }

    public int setMediaInfo(String[] strArr, MediaMetaData mediaMetaData) {
        return this.a.setMediaInfo(strArr, mediaMetaData);
    }

    public int getMediaInfo(String[] strArr) {
        return this.a.getMediaInfo(strArr);
    }

    public int closeDevice(String[] strArr) {
        return this.a.closeDevice(strArr);
    }

    public int setVolume(String[] strArr, int i) {
        return this.a.setVolume(strArr, i);
    }

    public int getVolume(String[] strArr) {
        return this.a.getVolume(strArr);
    }

    public int onTimelineChange() {
        return this.a.onTimelineChange();
    }

    public List<MiPlayDevice> getDevices() {
        return this.a.getDevices();
    }

    public boolean getCollectAudio() {
        return this.a.getCollectAudio();
    }

    public int onNext(String[] strArr) {
        return this.a.onNext(strArr);
    }

    public int onPrev(String[] strArr) {
        return this.a.onPrev(strArr);
    }

    public int onRefreshDeviceInfo() {
        return this.a.onRefreshDeviceInfo();
    }

    public int setBtFrequency(String[] strArr, int i) {
        return this.a.setBtFrequency(strArr, i);
    }

    public int getBtFrequency(String[] strArr) {
        return this.a.getBtFrequency(strArr);
    }

    public int setBoxPause(String[] strArr) {
        return this.a.setBoxPause(strArr);
    }

    public int setBoxResume(String[] strArr) {
        return this.a.setBoxResume(strArr);
    }

    public void stopUwbDiscovery() {
        this.a.stopUwbDiscovery();
    }

    public List<MiPlayDeviceControlCenter> getStereoDevices(String str) {
        return this.a.getStereoDevices(str);
    }

    public boolean isInited() {
        return this.a.isInited();
    }

    public MediaMetaData getLocalMediaInfo() {
        return this.a.getLocalMediaInfo();
    }
}
