package com.xiaomi.infra.galaxy.fds.result;

/* loaded from: classes3.dex */
public class NotificationResult {
    private String a;
    private String b;
    private long c;
    private long d;
    private String e;

    /* loaded from: classes3.dex */
    public enum NotificationMethod {
        PUT,
        POST,
        DELETE
    }

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

    public long getUpdateTime() {
        return this.c;
    }

    public void setUpdateTime(long j) {
        this.c = j;
    }

    public long getContentLength() {
        return this.d;
    }

    public void setContentLength(long j) {
        this.d = j;
    }

    public String getMethod() {
        return this.e;
    }

    public void setMethod(String str) {
        this.e = str;
    }
}
