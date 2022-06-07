package com.zhy.http.okhttp.builder;

import com.zhy.http.okhttp.request.OtherRequest;
import com.zhy.http.okhttp.request.RequestCall;

/* loaded from: classes4.dex */
public class HeadBuilder extends GetBuilder {
    @Override // com.zhy.http.okhttp.builder.GetBuilder, com.zhy.http.okhttp.builder.OkHttpRequestBuilder
    public RequestCall build() {
        return new OtherRequest(null, null, "HEAD", this.url, this.tag, this.params, this.headers, this.id).build();
    }
}
