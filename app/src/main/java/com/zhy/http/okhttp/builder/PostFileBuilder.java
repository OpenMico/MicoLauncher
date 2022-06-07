package com.zhy.http.okhttp.builder;

import com.zhy.http.okhttp.request.PostFileRequest;
import com.zhy.http.okhttp.request.RequestCall;
import java.io.File;
import okhttp3.MediaType;

/* loaded from: classes4.dex */
public class PostFileBuilder extends OkHttpRequestBuilder<PostFileBuilder> {
    private File a;
    private MediaType b;

    public OkHttpRequestBuilder file(File file) {
        this.a = file;
        return this;
    }

    public OkHttpRequestBuilder mediaType(MediaType mediaType) {
        this.b = mediaType;
        return this;
    }

    @Override // com.zhy.http.okhttp.builder.OkHttpRequestBuilder
    public RequestCall build() {
        return new PostFileRequest(this.url, this.tag, this.params, this.headers, this.a, this.b, this.id).build();
    }
}
