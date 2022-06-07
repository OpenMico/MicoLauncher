package com.xiaomi.miplay.phoneclientsdk.external;

import android.content.Context;
import com.xiaomi.miplay.phoneclientsdk.info.MediaMetaData;
import com.xiaomi.miplay.phoneclientsdk.info.PropertiesInfo;

/* loaded from: classes4.dex */
public class MiPlayClientManage {
    private MiplayClientControl a;

    public MiPlayClientManage(Context context) {
        this.a = new MiplayClientControl(context);
    }

    public boolean initAsync(MiplayClientCallback miplayClientCallback) {
        return this.a.initAsync(miplayClientCallback);
    }

    public void unInit() {
        this.a.unInit();
    }

    public int play(String str, MediaMetaData mediaMetaData) {
        return this.a.play(str, mediaMetaData);
    }

    public int playStateChange(String str, int i) {
        return this.a.playStateChange(str, i);
    }

    public int onBufferStateChanged(String str, int i) {
        return this.a.onBufferStateChanged(str, i);
    }

    public int sendPropertiesInfo(String str, PropertiesInfo propertiesInfo) {
        return this.a.sendPropertiesInfo(str, propertiesInfo);
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

    public int setVolume(String str, int i) {
        return this.a.setVolume(str, i);
    }

    public int getPosition(String str) {
        return this.a.getPosition(str);
    }

    public int getVolume(String str) {
        return this.a.getVolume(str);
    }

    public String getSourceName(String str) {
        return this.a.getSourceName(str);
    }

    public int getCirculateMode() {
        return this.a.getCirculateMode();
    }

    public int cancelCirculate(String str, int i) {
        return this.a.cancelCirculate(str, i);
    }
}
