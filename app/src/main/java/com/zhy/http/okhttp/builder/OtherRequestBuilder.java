package com.zhy.http.okhttp.builder;

import com.zhy.http.okhttp.request.OtherRequest;
import com.zhy.http.okhttp.request.RequestCall;
import okhttp3.RequestBody;

/* loaded from: classes4.dex */
public class OtherRequestBuilder extends OkHttpRequestBuilder<OtherRequestBuilder> {
    private RequestBody a;
    private String b;
    private String c;

    public OtherRequestBuilder(String str) {
        this.b = str;
    }

    @Override // com.zhy.http.okhttp.builder.OkHttpRequestBuilder
    public RequestCall build() {
        return new OtherRequest(this.a, this.c, this.b, this.url, this.tag, this.params, this.headers, this.id).build();
    }

    public OtherRequestBuilder requestBody(RequestBody requestBody) {
        this.a = requestBody;
        return this;
    }

    public OtherRequestBuilder requestBody(String str) {
        this.c = str;
        return this;
    }
}
