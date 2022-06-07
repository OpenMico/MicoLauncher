package com.xiaomi.miplay.mylibrary.mirror;

import android.graphics.Rect;
import com.google.android.exoplayer2.audio.AacUtil;
import com.google.android.exoplayer2.audio.OpusUtil;

/* loaded from: classes4.dex */
public class Options {
    private Rect d;
    private String g;
    private String i;
    private String j;
    private Size k;
    private int[] p;
    private int a = 1920;
    private int b = 5000000;
    private int c = 30;
    private boolean e = true;
    private boolean f = false;
    private int h = 7236;
    private int l = AacUtil.AAC_XHE_MAX_RATE_BYTES_PER_SECOND;
    private int m = OpusUtil.SAMPLE_RATE;
    private int n = 2;
    private int o = 16;

    public int getMaxSize() {
        return this.a;
    }

    public void setMaxSize(int i) {
        this.a = i;
    }

    public int getVideoBitRate() {
        return this.b;
    }

    public void setVideoBitRate(int i) {
        this.b = i;
    }

    public int getMaxFps() {
        return this.c;
    }

    public void setMaxFps(int i) {
        this.c = i;
    }

    public Rect getCrop() {
        return this.d;
    }

    public void setCrop(Rect rect) {
        this.d = rect;
    }

    public boolean getSendFrameMeta() {
        return this.e;
    }

    public void setSendFrameMeta(boolean z) {
        this.e = z;
    }

    public boolean getControl() {
        return this.f;
    }

    public void setControl(boolean z) {
        this.f = z;
    }

    public void setIp(String str) {
        this.g = str;
    }

    public String getIp() {
        return this.g;
    }

    public void setPort(int i) {
        this.h = i;
    }

    public int getPort() {
        return this.h;
    }

    public void setMultiPort(String str) {
        this.i = str;
    }

    public String getMultiPort() {
        return this.i;
    }

    public void setVideoSize(Size size) {
        this.k = size;
    }

    public Size getVideoSize() {
        return this.k;
    }

    public void setAudioBitRate(int i) {
        this.l = i;
    }

    public int getAudioBitRate() {
        return this.l;
    }

    public void setSampleRate(int i) {
        this.m = i;
    }

    public int getSampleRate() {
        return this.m;
    }

    public void setChannel(int i) {
        this.n = i;
    }

    public int getChannel() {
        return this.n;
    }

    public void setAudioBits(int i) {
        this.o = i;
    }

    public int getAudioBits() {
        return this.o;
    }

    public void setUidList(int[] iArr) {
        this.p = iArr;
    }

    public int[] getUidList() {
        return this.p;
    }

    public String getAudioName() {
        return this.j;
    }

    public void setAudioName(String str) {
        this.j = str;
    }
}
