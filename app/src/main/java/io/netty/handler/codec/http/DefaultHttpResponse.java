package io.netty.handler.codec.http;

import io.netty.util.internal.ObjectUtil;

/* loaded from: classes4.dex */
public class DefaultHttpResponse extends DefaultHttpMessage implements HttpResponse {
    private HttpResponseStatus a;

    public DefaultHttpResponse(HttpVersion httpVersion, HttpResponseStatus httpResponseStatus) {
        this(httpVersion, httpResponseStatus, true, false);
    }

    public DefaultHttpResponse(HttpVersion httpVersion, HttpResponseStatus httpResponseStatus, boolean z) {
        this(httpVersion, httpResponseStatus, z, false);
    }

    public DefaultHttpResponse(HttpVersion httpVersion, HttpResponseStatus httpResponseStatus, boolean z, boolean z2) {
        super(httpVersion, z, z2);
        this.a = (HttpResponseStatus) ObjectUtil.checkNotNull(httpResponseStatus, "status");
    }

    public DefaultHttpResponse(HttpVersion httpVersion, HttpResponseStatus httpResponseStatus, HttpHeaders httpHeaders) {
        super(httpVersion, httpHeaders);
        this.a = (HttpResponseStatus) ObjectUtil.checkNotNull(httpResponseStatus, "status");
    }

    @Override // io.netty.handler.codec.http.HttpResponse
    @Deprecated
    public HttpResponseStatus getStatus() {
        return status();
    }

    @Override // io.netty.handler.codec.http.HttpResponse
    public HttpResponseStatus status() {
        return this.a;
    }

    public HttpResponse setStatus(HttpResponseStatus httpResponseStatus) {
        if (httpResponseStatus != null) {
            this.a = httpResponseStatus;
            return this;
        }
        throw new NullPointerException("status");
    }

    @Override // io.netty.handler.codec.http.DefaultHttpMessage, io.netty.handler.codec.http.HttpMessage, io.netty.handler.codec.http.HttpRequest, io.netty.handler.codec.http.FullHttpRequest
    public HttpResponse setProtocolVersion(HttpVersion httpVersion) {
        super.setProtocolVersion(httpVersion);
        return this;
    }

    public String toString() {
        return d.a(new StringBuilder(256), (HttpResponse) this).toString();
    }
}
