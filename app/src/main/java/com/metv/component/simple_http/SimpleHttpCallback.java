package com.metv.component.simple_http;

/* loaded from: classes2.dex */
public interface SimpleHttpCallback {
    void onError(SimpleHttpRequest simpleHttpRequest, int i, String str);

    void onSuccess(SimpleHttpRequest simpleHttpRequest, int i, byte[] bArr);
}
