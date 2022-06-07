package com.allenliu.versionchecklib.v2.builder;

import com.allenliu.versionchecklib.core.http.HttpHeaders;
import com.allenliu.versionchecklib.core.http.HttpParams;
import com.allenliu.versionchecklib.core.http.HttpRequestMethod;
import com.allenliu.versionchecklib.v2.callback.RequestVersionListener;

/* loaded from: classes.dex */
public class RequestVersionBuilder {
    private HttpRequestMethod a = HttpRequestMethod.GET;
    private HttpParams b;
    private String c;
    private HttpHeaders d;
    private RequestVersionListener e;

    public HttpRequestMethod getRequestMethod() {
        return this.a;
    }

    public RequestVersionBuilder setRequestMethod(HttpRequestMethod httpRequestMethod) {
        this.a = httpRequestMethod;
        return this;
    }

    public HttpParams getRequestParams() {
        return this.b;
    }

    public RequestVersionBuilder setRequestParams(HttpParams httpParams) {
        this.b = httpParams;
        return this;
    }

    public String getRequestUrl() {
        return this.c;
    }

    public RequestVersionBuilder setRequestUrl(String str) {
        this.c = str;
        return this;
    }

    public HttpHeaders getHttpHeaders() {
        return this.d;
    }

    public RequestVersionBuilder setHttpHeaders(HttpHeaders httpHeaders) {
        this.d = httpHeaders;
        return this;
    }

    public RequestVersionListener getRequestVersionListener() {
        return this.e;
    }

    public DownloadBuilder request(RequestVersionListener requestVersionListener) {
        this.e = requestVersionListener;
        return new DownloadBuilder(this, null);
    }
}
