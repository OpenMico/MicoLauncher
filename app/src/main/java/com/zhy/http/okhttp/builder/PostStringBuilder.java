package com.zhy.http.okhttp.builder;

import com.zhy.http.okhttp.request.PostStringRequest;
import com.zhy.http.okhttp.request.RequestCall;
import okhttp3.MediaType;

/* loaded from: classes4.dex */
public class PostStringBuilder extends OkHttpRequestBuilder<PostStringBuilder> {
    private String a;
    private MediaType b;

    public PostStringBuilder content(String str) {
        this.a = str;
        return this;
    }

    public PostStringBuilder mediaType(MediaType mediaType) {
        this.b = mediaType;
        return this;
    }

    @Override // com.zhy.http.okhttp.builder.OkHttpRequestBuilder
    public RequestCall build() {
        return new PostStringRequest(this.url, this.tag, this.params, this.headers, this.a, this.b, this.id).build();
    }
}
