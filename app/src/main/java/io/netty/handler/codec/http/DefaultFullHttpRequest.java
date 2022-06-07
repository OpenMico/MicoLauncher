package io.netty.handler.codec.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.IllegalReferenceCountException;
import io.netty.util.internal.ObjectUtil;

/* loaded from: classes4.dex */
public class DefaultFullHttpRequest extends DefaultHttpRequest implements FullHttpRequest {
    private final ByteBuf a;
    private final HttpHeaders b;
    private int c;

    public DefaultFullHttpRequest(HttpVersion httpVersion, HttpMethod httpMethod, String str) {
        this(httpVersion, httpMethod, str, Unpooled.buffer(0));
    }

    public DefaultFullHttpRequest(HttpVersion httpVersion, HttpMethod httpMethod, String str, ByteBuf byteBuf) {
        this(httpVersion, httpMethod, str, byteBuf, true);
    }

    public DefaultFullHttpRequest(HttpVersion httpVersion, HttpMethod httpMethod, String str, boolean z) {
        this(httpVersion, httpMethod, str, Unpooled.buffer(0), z);
    }

    public DefaultFullHttpRequest(HttpVersion httpVersion, HttpMethod httpMethod, String str, ByteBuf byteBuf, boolean z) {
        super(httpVersion, httpMethod, str, z);
        this.a = (ByteBuf) ObjectUtil.checkNotNull(byteBuf, "content");
        this.b = new DefaultHttpHeaders(z);
    }

    public DefaultFullHttpRequest(HttpVersion httpVersion, HttpMethod httpMethod, String str, ByteBuf byteBuf, HttpHeaders httpHeaders, HttpHeaders httpHeaders2) {
        super(httpVersion, httpMethod, str, httpHeaders);
        this.a = (ByteBuf) ObjectUtil.checkNotNull(byteBuf, "content");
        this.b = (HttpHeaders) ObjectUtil.checkNotNull(httpHeaders2, "trailingHeader");
    }

    @Override // io.netty.handler.codec.http.LastHttpContent
    public HttpHeaders trailingHeaders() {
        return this.b;
    }

    @Override // io.netty.buffer.ByteBufHolder
    public ByteBuf content() {
        return this.a;
    }

    @Override // io.netty.util.ReferenceCounted
    public int refCnt() {
        return this.a.refCnt();
    }

    @Override // io.netty.handler.codec.http.FullHttpRequest, io.netty.handler.codec.http.FullHttpMessage, io.netty.handler.codec.http.LastHttpContent, io.netty.handler.codec.http.HttpContent, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    public FullHttpRequest retain() {
        this.a.retain();
        return this;
    }

    @Override // io.netty.handler.codec.http.FullHttpRequest, io.netty.handler.codec.http.FullHttpMessage, io.netty.handler.codec.http.LastHttpContent, io.netty.handler.codec.http.HttpContent, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    public FullHttpRequest retain(int i) {
        this.a.retain(i);
        return this;
    }

    @Override // io.netty.handler.codec.http.FullHttpRequest, io.netty.handler.codec.http.FullHttpMessage, io.netty.handler.codec.http.LastHttpContent, io.netty.handler.codec.http.HttpContent, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    public FullHttpRequest touch() {
        this.a.touch();
        return this;
    }

    @Override // io.netty.handler.codec.http.FullHttpRequest, io.netty.handler.codec.http.FullHttpMessage, io.netty.handler.codec.http.LastHttpContent, io.netty.handler.codec.http.HttpContent, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    public FullHttpRequest touch(Object obj) {
        this.a.touch(obj);
        return this;
    }

    @Override // io.netty.util.ReferenceCounted
    public boolean release() {
        return this.a.release();
    }

    @Override // io.netty.util.ReferenceCounted
    public boolean release(int i) {
        return this.a.release(i);
    }

    @Override // io.netty.handler.codec.http.DefaultHttpRequest, io.netty.handler.codec.http.DefaultHttpMessage, io.netty.handler.codec.http.HttpMessage, io.netty.handler.codec.http.HttpRequest, io.netty.handler.codec.http.FullHttpRequest
    public FullHttpRequest setProtocolVersion(HttpVersion httpVersion) {
        super.setProtocolVersion(httpVersion);
        return this;
    }

    @Override // io.netty.handler.codec.http.DefaultHttpRequest, io.netty.handler.codec.http.HttpRequest, io.netty.handler.codec.http.FullHttpRequest
    public FullHttpRequest setMethod(HttpMethod httpMethod) {
        super.setMethod(httpMethod);
        return this;
    }

    @Override // io.netty.handler.codec.http.DefaultHttpRequest, io.netty.handler.codec.http.HttpRequest, io.netty.handler.codec.http.FullHttpRequest
    public FullHttpRequest setUri(String str) {
        super.setUri(str);
        return this;
    }

    @Override // io.netty.handler.codec.http.FullHttpRequest, io.netty.handler.codec.http.FullHttpMessage, io.netty.handler.codec.http.LastHttpContent, io.netty.handler.codec.http.HttpContent, io.netty.buffer.ByteBufHolder
    public FullHttpRequest copy() {
        return replace(content().copy());
    }

    @Override // io.netty.handler.codec.http.FullHttpRequest, io.netty.handler.codec.http.FullHttpMessage, io.netty.handler.codec.http.LastHttpContent, io.netty.handler.codec.http.HttpContent, io.netty.buffer.ByteBufHolder
    public FullHttpRequest duplicate() {
        return replace(content().duplicate());
    }

    @Override // io.netty.handler.codec.http.FullHttpRequest, io.netty.handler.codec.http.FullHttpMessage, io.netty.handler.codec.http.LastHttpContent, io.netty.handler.codec.http.HttpContent, io.netty.buffer.ByteBufHolder
    public FullHttpRequest retainedDuplicate() {
        return replace(content().retainedDuplicate());
    }

    @Override // io.netty.handler.codec.http.FullHttpRequest, io.netty.handler.codec.http.FullHttpMessage, io.netty.handler.codec.http.LastHttpContent, io.netty.handler.codec.http.HttpContent, io.netty.buffer.ByteBufHolder
    public FullHttpRequest replace(ByteBuf byteBuf) {
        return new DefaultFullHttpRequest(protocolVersion(), method(), uri(), byteBuf, headers(), trailingHeaders());
    }

    @Override // io.netty.handler.codec.http.DefaultHttpRequest, io.netty.handler.codec.http.DefaultHttpMessage, io.netty.handler.codec.http.DefaultHttpObject
    public int hashCode() {
        int i;
        int i2 = this.c;
        if (i2 != 0) {
            return i2;
        }
        if (content().refCnt() != 0) {
            try {
                i = content().hashCode() + 31;
            } catch (IllegalReferenceCountException unused) {
                i = 31;
            }
        } else {
            i = 31;
        }
        int hashCode = (((i * 31) + trailingHeaders().hashCode()) * 31) + super.hashCode();
        this.c = hashCode;
        return hashCode;
    }

    @Override // io.netty.handler.codec.http.DefaultHttpRequest, io.netty.handler.codec.http.DefaultHttpMessage, io.netty.handler.codec.http.DefaultHttpObject
    public boolean equals(Object obj) {
        if (!(obj instanceof DefaultFullHttpRequest)) {
            return false;
        }
        DefaultFullHttpRequest defaultFullHttpRequest = (DefaultFullHttpRequest) obj;
        return super.equals(defaultFullHttpRequest) && content().equals(defaultFullHttpRequest.content()) && trailingHeaders().equals(defaultFullHttpRequest.trailingHeaders());
    }

    @Override // io.netty.handler.codec.http.DefaultHttpRequest
    public String toString() {
        return d.a(new StringBuilder(256), (FullHttpRequest) this).toString();
    }
}
