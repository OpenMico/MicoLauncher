package com.xiaomi.micolauncher.common.media;

import android.graphics.Bitmap;

/* loaded from: classes3.dex */
public class RelayMediaEvent {
    private final CharSequence a;
    private final CharSequence b;
    private final Bitmap c;
    private final String d;
    private final String e;
    private final String f;
    private final int g;
    private final String h;

    public RelayMediaEvent(String str, CharSequence charSequence, CharSequence charSequence2, Bitmap bitmap, String str2, String str3, int i, String str4) {
        this.a = charSequence;
        this.b = charSequence2;
        this.c = bitmap;
        this.d = str;
        this.e = str2;
        this.f = str3;
        this.g = i;
        this.h = str4;
    }

    public CharSequence getTitle() {
        return this.a;
    }

    public CharSequence getArtist() {
        return this.b;
    }

    public Bitmap getBitmap() {
        return this.c;
    }

    public String getMediaType() {
        return this.d;
    }

    public String getSource() {
        return this.e;
    }

    public String getDeviceId() {
        return this.f;
    }

    public int getDeviceState() {
        return this.g;
    }

    public String getSourceBtMac() {
        return this.h;
    }

    public String toString() {
        return "RelayMediaEvent{title=" + ((Object) this.a) + ", artist=" + ((Object) this.b) + ", bitmap=" + this.c + ", mediaType='" + this.d + "', source='" + this.e + "', deviceId='" + this.f + "', deviceState=" + this.g + ", sourceBtMac='" + this.h + "'}";
    }
}
