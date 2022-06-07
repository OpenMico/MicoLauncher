package io.netty.handler.codec.http;

import io.netty.util.internal.ObjectUtil;

/* loaded from: classes4.dex */
public abstract class DefaultHttpMessage extends DefaultHttpObject implements HttpMessage {
    private HttpVersion a;
    private final HttpHeaders b;

    protected DefaultHttpMessage(HttpVersion httpVersion) {
        this(httpVersion, true, false);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public DefaultHttpMessage(HttpVersion httpVersion, boolean z, boolean z2) {
        this(httpVersion, z2 ? new CombinedHttpHeaders(z) : new DefaultHttpHeaders(z));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public DefaultHttpMessage(HttpVersion httpVersion, HttpHeaders httpHeaders) {
        this.a = (HttpVersion) ObjectUtil.checkNotNull(httpVersion, "version");
        this.b = (HttpHeaders) ObjectUtil.checkNotNull(httpHeaders, "headers");
    }

    @Override // io.netty.handler.codec.http.HttpMessage
    public HttpHeaders headers() {
        return this.b;
    }

    @Override // io.netty.handler.codec.http.HttpMessage
    @Deprecated
    public HttpVersion getProtocolVersion() {
        return protocolVersion();
    }

    @Override // io.netty.handler.codec.http.HttpMessage
    public HttpVersion protocolVersion() {
        return this.a;
    }

    @Override // io.netty.handler.codec.http.DefaultHttpObject
    public int hashCode() {
        return ((((this.b.hashCode() + 31) * 31) + this.a.hashCode()) * 31) + super.hashCode();
    }

    @Override // io.netty.handler.codec.http.DefaultHttpObject
    public boolean equals(Object obj) {
        if (!(obj instanceof DefaultHttpMessage)) {
            return false;
        }
        DefaultHttpMessage defaultHttpMessage = (DefaultHttpMessage) obj;
        return headers().equals(defaultHttpMessage.headers()) && protocolVersion().equals(defaultHttpMessage.protocolVersion()) && super.equals(obj);
    }

    public HttpMessage setProtocolVersion(HttpVersion httpVersion) {
        if (httpVersion != null) {
            this.a = httpVersion;
            return this;
        }
        throw new NullPointerException("version");
    }
}
