package com.xiaomi.micolauncher.skills.video.player.media;

/* loaded from: classes3.dex */
public class AudioTrack {
    public static final int AUDIO_DIGITAL_ELSE = -1;
    public static final int AUDIO_DIGITAL_PCM = 0;
    public static final int AUDIO_DIGITAL_RAW = 1;
    private int b;
    private int a = -1;
    private String c = "";

    public void setId(int i) {
        this.a = i;
    }

    public int getId() {
        return this.a;
    }

    public void setCodec(int i) {
        this.b = i;
    }

    public int getCodec() {
        return this.b;
    }

    public void setLanguage(String str) {
        this.c = str;
    }

    public String getLanguage() {
        return this.c;
    }

    public String toString() {
        return "audio id : " + this.a + ", codec: " + this.b + ", language: " + this.c;
    }
}
