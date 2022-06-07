package com.xiaomi.miplay.mylibrary.mirror;

/* loaded from: classes4.dex */
public class AudioRecodeBean {
    private int[] a;
    private int b;
    private int c;
    private int d;

    public AudioRecodeBean(int[] iArr, int i, int i2, int i3) {
        this.a = iArr;
        this.b = i;
        this.c = i2;
        this.d = i3;
    }

    public int[] getUidList() {
        return this.a;
    }

    public void setUidList(int[] iArr) {
        this.a = iArr;
    }

    public int getSampleRate() {
        return this.b;
    }

    public void setSampleRate(int i) {
        this.b = i;
    }

    public int getChannel() {
        return this.c;
    }

    public void setChannel(int i) {
        this.c = i;
    }

    public int getAudioBits() {
        return this.d;
    }

    public void setAudioBits(int i) {
        this.d = i;
    }
}
