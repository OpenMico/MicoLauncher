package com.xiaomi.infra.galaxy.fds.android.model;

import com.xiaomi.micolauncher.module.music.MusicGroupListActivity;

/* loaded from: classes3.dex */
public class PutObjectResult {
    private String a;
    private String b;
    private String c;
    private String d;
    private String e;
    private String f;
    private long g;

    public String getBucketName() {
        return this.a;
    }

    public void setBucketName(String str) {
        this.a = str;
    }

    public String getObjectName() {
        return this.b;
    }

    public void setObjectName(String str) {
        this.b = str;
    }

    public String getAccessKeyId() {
        return this.c;
    }

    public void setAccessKeyId(String str) {
        this.c = str;
    }

    public String getSignature() {
        return this.d;
    }

    public void setSignature(String str) {
        this.d = str;
    }

    public long getExpires() {
        return this.g;
    }

    public void setExpires(long j) {
        this.g = j;
    }

    public void setFdsServiceBaseUri(String str) {
        this.e = str;
    }

    public void setCdnServiceBaseUri(String str) {
        this.f = str;
    }

    public String getRelativePresignedUri() {
        return "/" + this.a + "/" + this.b + "?GalaxyAccessKeyId=" + this.c + MusicGroupListActivity.SPECIAL_SYMBOL + "Expires=" + this.g + MusicGroupListActivity.SPECIAL_SYMBOL + "Signature=" + this.d;
    }

    public String getAbsolutePresignedUri() {
        return this.e + getRelativePresignedUri();
    }

    public String getCdnPresignedUri() {
        return this.f + getRelativePresignedUri();
    }
}
