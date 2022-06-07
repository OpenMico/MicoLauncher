package com.xiaomi.infra.galaxy.fds.result;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
/* loaded from: classes3.dex */
public class StorageAccessTokenResult {
    private String a;
    private long b;

    public StorageAccessTokenResult() {
    }

    public StorageAccessTokenResult(String str, long j) {
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
