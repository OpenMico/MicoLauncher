package com.xiaomi.miplay.mylibrary.circulate;

import android.graphics.Bitmap;

/* loaded from: classes4.dex */
public class MetaData {
    private String a = "";
    private String b = "";
    private long c = 0;
    private Bitmap d = null;
    private long e = 0;
    private String f = "";
    private String g = "";
    private String h = "";
    private String i = "";
    private String j = "";
    private String k = "";

    public String getPlatform() {
        return this.a;
    }

    public void setPlatform(String str) {
        this.a = str;
    }

    public String getTitle() {
        return this.b;
    }

    public void setTitle(String str) {
        this.b = str;
    }

    public long getDuration() {
        return this.c;
    }

    public void setDuration(long j) {
        this.c = j;
    }

    public Bitmap getArt() {
        return this.d;
    }

    public void setArt(Bitmap bitmap) {
        this.d = bitmap;
    }

    public long getPosition() {
        return this.e;
    }

    public void setPosition(long j) {
        this.e = j;
    }

    public String getURL() {
        return this.f;
    }

    public void setURL(String str) {
        this.f = str;
    }

    public String getMux() {
        return this.g;
    }

    public void setMux(String str) {
        this.g = str;
    }

    public String getCodec() {
        return this.h;
    }

    public void setCodec(String str) {
        this.h = str;
    }

    public String getReverso() {
        return this.i;
    }

    public void setReverso(String str) {
        this.i = str;
    }

    public String getType() {
        return this.j;
    }

    public void setType(String str) {
        this.j = str;
    }

    public String getPropertiesInfo() {
        return this.k;
    }

    public void setPropertiesInfo(String str) {
        this.k = str;
    }

    public String toString() {
        return "MetaData{mPlatform='" + this.a + "', mTitle='" + this.b + "', mDuration=" + this.c + ", mArt=" + this.d + ", mPosition=" + this.e + ", mURL='" + this.f + "', mMux='" + this.g + "', mCodec='" + this.h + "', mReverso='" + this.i + "', mType='" + this.j + "', mPropertiesInfo='" + this.k + "'}";
    }
}
