package com.xiaomi.infra.galaxy.fds.bean;

import java.util.Date;

/* loaded from: classes3.dex */
public class ObjectBean {
    private String a;
    private String b;
    private OwnerBean c;
    private Date d;
    private long e;
    private long f;

    public ObjectBean() {
    }

    public ObjectBean(String str) {
        this.a = str;
    }

    public String getName() {
        return this.a;
    }

    public void setName(String str) {
        this.a = str;
    }

    public String getEtag() {
        return this.b;
    }

    public void setEtag(String str) {
        this.b = str;
    }

    public OwnerBean getOwner() {
        return this.c;
    }

    public void setOwner(OwnerBean ownerBean) {
        this.c = ownerBean;
    }

    public Date getLastModified() {
        return this.d;
    }

    public void setLastModified(Date date) {
        this.d = date;
    }

    public long getUploadTime() {
        return this.e;
    }

    public void setUploadTime(long j) {
        this.e = j;
    }

    public long getSize() {
        return this.f;
    }

    public void setSize(long j) {
        this.f = j;
    }
}
