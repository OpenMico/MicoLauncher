package com.zhy.http.okhttp.request;

import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.utils.Exceptions;
import java.util.Map;
import okhttp3.Headers;
import okhttp3.Request;
import okhttp3.RequestBody;

/* loaded from: classes4.dex */
public abstract class OkHttpRequest {
    protected Request.Builder builder = new Request.Builder();
    protected Map<String, String> headers;
    protected int id;
    protected Map<String, String> params;
    protected Object tag;
    protected String url;

    protected abstract Request buildRequest(RequestBody requestBody);

    protected abstract RequestBody buildRequestBody();

    protected RequestBody wrapRequestBody(RequestBody requestBody, Callback callback) {
        return requestBody;
    }

    public OkHttpRequest(String str, Object obj, Map<String, String> map, Map<String, String> map2, int i) {
        this.url = str;
        this.tag = obj;
        this.params = map;
        this.headers = map2;
        this.id = i;
        if (str == null) {
            Exceptions.illegalArgument("url can not be null.", new Object[0]);
        }
        a();
    }

    private void a() {
        this.builder.url(this.url).tag(this.tag);
        appendHeaders();
    }

    public RequestCall build() {
        return new RequestCall(this);
    }

    public Request generateRequest(Callback callback) {
        return buildRequest(wrapRequestBody(buildRequestBody(), callback));
    }

    protected void appendHeaders() {
        Headers.Builder builder = new Headers.Builder();
        Map<String, String> map = this.headers;
        if (!(map == null || map.isEmpty())) {
            for (String str : this.headers.keySet()) {
                builder.add(str, this.headers.get(str));
            }
            this.builder.headers(builder.build());
        }
    }

    public int getId() {
        return this.id;
    }
}
