package com.xiaomi.smarthome.base;

/* loaded from: classes4.dex */
public class BaseResponse<T> {
    private T a;
    private int b;
    private String c;

    public T getData() {
        return this.a;
    }

    public void setData(T t) {
        this.a = t;
    }

    public int getCode() {
        return this.b;
    }

    public void setCode(int i) {
        this.b = i;
    }

    public String getMsg() {
        return this.c;
    }

    public void setMsg(String str) {
        this.c = str;
    }
}
