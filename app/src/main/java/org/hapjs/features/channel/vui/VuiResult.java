package org.hapjs.features.channel.vui;

/* loaded from: classes5.dex */
public class VuiResult<T> {
    private int a;
    private T b;

    public VuiResult(int i, T t) {
        this.a = i;
        this.b = t;
    }

    public int getCode() {
        return this.a;
    }

    public T getData() {
        return this.b;
    }
}
