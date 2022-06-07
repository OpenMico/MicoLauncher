package com.metv.component.simple_http;

/* loaded from: classes2.dex */
public class SimpleHttpHeader {
    private String a;
    private String b;

    public SimpleHttpHeader() {
    }

    public SimpleHttpHeader(String str, String str2) {
        this.a = str;
        this.b = str2;
    }

    public String getName() {
        return this.a;
    }

    public void setName(String str) {
        this.a = str;
    }

    public String getValue() {
        return this.b;
    }

    public void setValue(String str) {
        this.b = str;
    }
}
