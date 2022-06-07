package com.zhy.http.okhttp.builder;

import java.util.Map;

/* loaded from: classes4.dex */
public interface HasParamsable {
    OkHttpRequestBuilder addParams(String str, String str2);

    OkHttpRequestBuilder params(Map<String, String> map);
}
