package com.zhy.http.okhttp.request;

import com.zhy.http.okhttp.utils.Exceptions;
import java.util.Map;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

/* loaded from: classes4.dex */
public class PostStringRequest extends OkHttpRequest {
    private static MediaType a = MediaType.parse("text/plain;charset=utf-8");
    private String b;
    private MediaType c;

    public PostStringRequest(String str, Object obj, Map<String, String> map, Map<String, String> map2, String str2, MediaType mediaType, int i) {
        super(str, obj, map, map2, i);
        this.b = str2;
        this.c = mediaType;
        if (this.b == null) {
            Exceptions.illegalArgument("the content can not be null !", new Object[0]);
        }
        if (this.c == null) {
            this.c = a;
        }
    }

    @Override // com.zhy.http.okhttp.request.OkHttpRequest
    protected RequestBody buildRequestBody() {
        return RequestBody.create(this.c, this.b);
    }

    @Override // com.zhy.http.okhttp.request.OkHttpRequest
    protected Request buildRequest(RequestBody requestBody) {
        return this.builder.post(requestBody).build();
    }
}
