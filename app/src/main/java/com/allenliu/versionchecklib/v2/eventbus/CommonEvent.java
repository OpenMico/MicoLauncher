package com.allenliu.versionchecklib.v2.eventbus;

/* loaded from: classes.dex */
public class CommonEvent<T> extends BaseEvent {
    private boolean a;
    private String b;
    private T c;
    private int d;

    public boolean isSuccessful() {
        return this.a;
    }

    public CommonEvent setSuccessful(boolean z) {
        this.a = z;
        return this;
    }

    public int getResponseCode() {
        return this.d;
    }

    public CommonEvent setResponseCode(int i) {
        this.d = i;
        return this;
    }

    public String getMessage() {
        return this.b;
    }

    public CommonEvent setMessage(String str) {
        this.b = str;
        return this;
    }

    public T getData() {
        return this.c;
    }

    public CommonEvent setData(T t) {
        this.c = t;
        return this;
    }

    public static CommonEvent getSimpleEvent(int i) {
        CommonEvent commonEvent = new CommonEvent();
        commonEvent.setSuccessful(true);
        commonEvent.setEventType(i);
        return commonEvent;
    }
}
