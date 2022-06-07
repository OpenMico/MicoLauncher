package com.xiaomi.micolauncher.common.speech.utils;

/* loaded from: classes3.dex */
public final class PcmData {
    private int a;
    private int b;
    private int c;
    private boolean d;
    private byte[] e;
    private String f;

    public PcmData(int i, int i2, int i3, boolean z, byte[] bArr) {
        this.a = i;
        this.b = i2;
        this.c = i3;
        this.d = z;
        this.e = bArr;
    }

    public int getSampleRate() {
        return this.a;
    }

    public int getChannels() {
        return this.b;
    }

    public int getBits() {
        return this.c;
    }

    public boolean isEof() {
        return this.d;
    }

    public byte[] getPcm() {
        return this.e;
    }

    public String getDialogId() {
        return this.f;
    }

    public void setDialogId(String str) {
        this.f = str;
    }
}
