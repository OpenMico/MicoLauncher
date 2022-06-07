package com.metv.component.simple_http;

/* loaded from: classes2.dex */
public class SimpleHttpParam {
    private String a;
    private String b;

    public SimpleHttpParam() {
    }

    public SimpleHttpParam(String str, String str2) {
        this.a = str;
        this.b = str2;
    }

    public String getKey() {
        return this.a;
    }

    public void setKey(String str) {
        this.a = str;
    }

    public String getValue() {
        return this.b;
    }

    public void setValue(String str) {
        this.b = str;
    }
}
