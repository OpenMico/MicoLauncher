package com.xiaomi.infra.galaxy.fds.bean;

/* loaded from: classes3.dex */
public class OwnerBean {
    private String a;
    private String b;

    public OwnerBean() {
    }

    public OwnerBean(String str) {
        this.a = str;
    }

    public OwnerBean(String str, String str2) {
        this.a = str;
        setDisplayName(str2);
    }

    public String getId() {
        return this.a;
    }

    public void setId(String str) {
        this.a = str;
    }

    public String getDisplayName() {
        return this.b;
    }

    public void setDisplayName(String str) {
        this.b = str;
    }
}
