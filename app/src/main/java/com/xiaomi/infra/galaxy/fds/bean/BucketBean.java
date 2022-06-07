package com.xiaomi.infra.galaxy.fds.bean;

/* loaded from: classes3.dex */
public class BucketBean {
    private String a;
    private String b;
    private long c;
    private long d;
    private long e;

    public String getOrgId() {
        return this.b;
    }

    public void setOrgId(String str) {
        this.b = str;
    }

    public BucketBean() {
    }

    public BucketBean(String str) {
        this.a = str;
    }

    public String getName() {
        return this.a;
    }

    public void setName(String str) {
        this.a = str;
    }

    public long getCreationTime() {
        return this.c;
    }

    public void setCreationTime(long j) {
        this.c = j;
    }

    public long getUsedSpace() {
        return this.d;
    }

    public void setUsedSpace(long j) {
        this.d = j;
    }

    public long getNumObjects() {
        return this.e;
    }

    public void setNumObjects(long j) {
        this.e = j;
    }
}
