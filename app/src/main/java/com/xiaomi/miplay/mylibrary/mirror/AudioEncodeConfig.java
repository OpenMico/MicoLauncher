package com.xiaomi.miplay.mylibrary.mirror;

import android.content.Context;
import android.media.MediaFormat;
import java.util.Arrays;
import java.util.Objects;

/* loaded from: classes4.dex */
public class AudioEncodeConfig {
    private String a;
    private String b;
    private int c;
    private int d;
    private int e;
    private int f;
    private int[] g;
    private Context h;

    public AudioEncodeConfig(Context context, String str, String str2, int[] iArr, int i, int i2, int i3, int i4) {
        this.g = iArr;
        this.a = str;
        this.b = (String) Objects.requireNonNull(str2);
        this.c = i;
        this.d = i2;
        this.e = i3;
        this.f = i4;
        this.h = context;
    }

    public String getCodecName() {
        return this.a;
    }

    public String getMimeType() {
        return this.b;
    }

    public int getBitRate() {
        return this.c;
    }

    public int getSampleRate() {
        return this.d;
    }

    public int getChannelCount() {
        return this.e;
    }

    public int getProfile() {
        return this.f;
    }

    public int[] getUidList() {
        return this.g;
    }

    public Context getmContext() {
        return this.h;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public MediaFormat a() {
        MediaFormat createAudioFormat = MediaFormat.createAudioFormat(this.b, this.d, this.e);
        createAudioFormat.setInteger("aac-profile", this.f);
        createAudioFormat.setInteger("bitrate", this.c);
        return createAudioFormat;
    }

    public String toString() {
        return "AudioEncodeConfig{codecName='" + this.a + "', mimeType='" + this.b + "', bitRate=" + this.c + ", sampleRate=" + this.d + ", channelCount=" + this.e + ", profile=" + this.f + ", uidList=" + Arrays.toString(this.g) + '}';
    }
}
