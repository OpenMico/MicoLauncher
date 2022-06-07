package com.xiaomi.infra.galaxy.fds.bean;

/* loaded from: classes3.dex */
public class GranteeBean {
    private String a;
    private String b;

    public GranteeBean() {
    }

    public GranteeBean(String str) {
        this.a = str;
    }

    public GranteeBean(String str, String str2) {
        this.a = str;
        this.b = str2;
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
