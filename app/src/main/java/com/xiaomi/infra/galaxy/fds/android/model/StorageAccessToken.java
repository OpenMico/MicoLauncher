package com.xiaomi.infra.galaxy.fds.android.model;

/* loaded from: classes3.dex */
public class StorageAccessToken {
    private String a;
    private long b;

    public StorageAccessToken() {
    }

    public StorageAccessToken(String str, long j) {
        this.a = str;
        this.b = j;
    }

    public String getToken() {
        return this.a;
    }

    public void setToken(String str) {
        this.a = str;
    }

    public long getExpireTime() {
        return this.b;
    }

    public void setExpireTime(long j) {
        this.b = j;
    }
}
