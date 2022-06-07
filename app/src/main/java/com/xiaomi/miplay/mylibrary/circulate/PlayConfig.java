package com.xiaomi.miplay.mylibrary.circulate;

import com.xiaomi.miplay.phoneclientsdk.info.MediaMetaData;

/* loaded from: classes4.dex */
public class PlayConfig {
    private String a;
    private int b;
    private String c;
    private MediaMetaData d;

    public String getDeviceId() {
        return this.a;
    }

    public void setDeviceId(String str) {
        this.a = str;
    }

    public int getType() {
        return this.b;
    }

    public void setType(int i) {
        this.b = i;
    }

    public String getId() {
        return this.c;
    }

    public void setId(String str) {
        this.c = str;
    }

    public MediaMetaData getMeta() {
        return this.d;
    }

    public void setMeta(MediaMetaData mediaMetaData) {
        this.d = mediaMetaData;
    }
}
