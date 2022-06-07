package com.zhy.http.okhttp.request;

import android.text.TextUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.utils.Exceptions;
import java.util.Map;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.internal.http.HttpMethod;

/* loaded from: classes4.dex */
public class OtherRequest extends OkHttpRequest {
    private static MediaType a = MediaType.parse("text/plain;charset=utf-8");
    private RequestBody b;
    private String c;
    private String d;

    public OtherRequest(RequestBody requestBody, String str, String str2, String str3, Object obj, Map<String, String> map, Map<String, String> map2, int i) {
        super(str3, obj, map, map2, i);
        this.b = requestBody;
        this.c = str2;
        this.d = str;
    }

    @Override // com.zhy.http.okhttp.request.OkHttpRequest
    protected RequestBody buildRequestBody() {
        if (this.b == null && TextUtils.isEmpty(this.d) && HttpMethod.requiresRequestBody(this.c)) {
            Exceptions.illegalArgument("requestBody and content can not be null in method:" + this.c, new Object[0]);
        }
        if (this.b == null && !TextUtils.isEmpty(this.d)) {
            this.b = RequestBody.create(a, this.d);
        }
        return this.b;
    }

    @Override // com.zhy.http.okhttp.request.OkHttpRequest
    protected Request buildRequest(RequestBody requestBody) {
        if (this.c.equals("PUT")) {
            this.builder.put(requestBody);
        } else if (this.c.equals("DELETE")) {
            if (requestBody == null) {
                this.builder.delete();
            } else {
                this.builder.delete(requestBody);
            }
        } else if (this.c.equals("HEAD")) {
            this.builder.head();
        } else if (this.c.equals(OkHttpUtils.METHOD.PATCH)) {
            this.builder.patch(requestBody);
        }
        return this.builder.build();
    }
}
