package com.xiaomi.phonenum.http;

import com.xiaomi.phonenum.http.HttpClientConfig;

/* loaded from: classes4.dex */
public abstract class HttpFactory {
    public abstract HttpClient createHttpClient(HttpClientConfig httpClientConfig);

    public HttpClient createHttpClient() {
        return createHttpClient(new HttpClientConfig.Builder().build());
    }

    public HttpClient createDataHttpClient(int i) {
        return createHttpClient(new HttpClientConfig.Builder().useDataNetWork(i).build());
    }
}
