package com.xiaomi.smarthome.library.http;

/* loaded from: classes4.dex */
public class Error {
    private int a;
    private String b;

    public Error(int i, String str) {
        this.a = i;
        this.b = str;
    }

    public final int getCode() {
        return this.a;
    }

    public final String getDetail() {
        return this.b;
    }
}
