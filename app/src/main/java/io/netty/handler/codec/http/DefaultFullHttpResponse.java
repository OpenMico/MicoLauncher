package io.netty.handler.codec.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.IllegalReferenceCountException;
import io.netty.util.internal.ObjectUtil;

/* loaded from: classes4.dex */
public class DefaultFullHttpResponse extends DefaultHttpResponse implements FullHttpResponse {
    private final ByteBuf a;
    private final HttpHeaders b;
    private int c;

    public DefaultFullHttpResponse(HttpVersion httpVersion, HttpResponseStatus httpResponseStatus) {
        this(httpVersion, httpResponseStatus, Unpooled.buffer(0));
    }

    public DefaultFullHttpResponse(HttpVersion httpVersion, HttpResponseStatus httpResponseStatus, ByteBuf byteBuf) {
        this(httpVersion, httpResponseStatus, byteBuf, true);
    }

    public DefaultFullHttpResponse(HttpVersion httpVersion, HttpResponseStatus httpResponseStatus, boolean z) {
        this(httpVersion, httpResponseStatus, Unpooled.buffer(0), z, false);
    }

    public DefaultFullHttpResponse(HttpVersion httpVersion, HttpResponseStatus httpResponseStatus, boolean z, boolean z2) {
        this(httpVersion, httpResponseStatus, Unpooled.buffer(0), z, z2);
    }

    public DefaultFullHttpResponse(HttpVersion httpVersion, HttpResponseStatus httpResponseStatus, ByteBuf byteBuf, boolean z) {
        this(httpVersion, httpResponseStatus, byteBuf, z, false);
    }

    public DefaultFullHttpResponse(HttpVersion httpVersion, HttpResponseStatus httpResponseStatus, ByteBuf byteBuf, boolean z, boolean z2) {
        super(httpVersion, httpResponseStatus, z, z2);
        this.a = (ByteBuf) ObjectUtil.checkNotNull(byteBuf, "content");
        this.b = z2 ? new CombinedHttpHeaders(z) : new DefaultHttpHeaders(z);
    }

    public DefaultFullHttpResponse(HttpVersion httpVersion, HttpResponseStatus httpResponseStatus, ByteBuf byteBuf, HttpHeaders httpHeaders, HttpHeaders httpHeaders2) {
        super(httpVersion, httpResponseStatus, httpHeaders);
        this.a = (ByteBuf) ObjectUtil.checkNotNull(byteBuf, "content");
        this.b = (HttpHeaders) ObjectUtil.checkNotNull(httpHeaders2, "trailingHeaders");
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

    @Override // io.netty.handler.codec.http.FullHttpResponse, io.netty.handler.codec.http.FullHttpMessage, io.netty.handler.codec.http.LastHttpContent, io.netty.handler.codec.http.HttpContent, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    public FullHttpResponse retain() {
        this.a.retain();
        return this;
    }

    @Override // io.netty.handler.codec.http.FullHttpResponse, io.netty.handler.codec.http.FullHttpMessage, io.netty.handler.codec.http.LastHttpContent, io.netty.handler.codec.http.HttpContent, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    public FullHttpResponse retain(int i) {
        this.a.retain(i);
        return this;
    }

    @Override // io.netty.handler.codec.http.FullHttpResponse, io.netty.handler.codec.http.FullHttpMessage, io.netty.handler.codec.http.LastHttpContent, io.netty.handler.codec.http.HttpContent, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    public FullHttpResponse touch() {
        this.a.touch();
        return this;
    }

    @Override // io.netty.handler.codec.http.FullHttpResponse, io.netty.handler.codec.http.FullHttpMessage, io.netty.handler.codec.http.LastHttpContent, io.netty.handler.codec.http.HttpContent, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    public FullHttpResponse touch(Object obj) {
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

    @Override // io.netty.handler.codec.http.DefaultHttpResponse, io.netty.handler.codec.http.DefaultHttpMessage, io.netty.handler.codec.http.HttpMessage, io.netty.handler.codec.http.HttpRequest, io.netty.handler.codec.http.FullHttpRequest
    public FullHttpResponse setProtocolVersion(HttpVersion httpVersion) {
        super.setProtocolVersion(httpVersion);
        return this;
    }

    @Override // io.netty.handler.codec.http.DefaultHttpResponse, io.netty.handler.codec.http.HttpResponse, io.netty.handler.codec.http.FullHttpResponse
    public FullHttpResponse setStatus(HttpResponseStatus httpResponseStatus) {
        super.setStatus(httpResponseStatus);
        return this;
    }

    @Override // io.netty.handler.codec.http.FullHttpResponse, io.netty.handler.codec.http.FullHttpMessage, io.netty.handler.codec.http.LastHttpContent, io.netty.handler.codec.http.HttpContent, io.netty.buffer.ByteBufHolder
    public FullHttpResponse copy() {
        return replace(content().copy());
    }

    @Override // io.netty.handler.codec.http.FullHttpResponse, io.netty.handler.codec.http.FullHttpMessage, io.netty.handler.codec.http.LastHttpContent, io.netty.handler.codec.http.HttpContent, io.netty.buffer.ByteBufHolder
    public FullHttpResponse duplicate() {
        return replace(content().duplicate());
    }

    @Override // io.netty.handler.codec.http.FullHttpResponse, io.netty.handler.codec.http.FullHttpMessage, io.netty.handler.codec.http.LastHttpContent, io.netty.handler.codec.http.HttpContent, io.netty.buffer.ByteBufHolder
    public FullHttpResponse retainedDuplicate() {
        return replace(content().retainedDuplicate());
    }

    @Override // io.netty.handler.codec.http.FullHttpResponse, io.netty.handler.codec.http.FullHttpMessage, io.netty.handler.codec.http.LastHttpContent, io.netty.handler.codec.http.HttpContent, io.netty.buffer.ByteBufHolder
    public FullHttpResponse replace(ByteBuf byteBuf) {
        return new DefaultFullHttpResponse(protocolVersion(), status(), byteBuf, headers(), trailingHeaders());
    }

    @Override // io.netty.handler.codec.http.DefaultHttpMessage, io.netty.handler.codec.http.DefaultHttpObject
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

    @Override // io.netty.handler.codec.http.DefaultHttpMessage, io.netty.handler.codec.http.DefaultHttpObject
    public boolean equals(Object obj) {
        if (!(obj instanceof DefaultFullHttpResponse)) {
            return false;
        }
        DefaultFullHttpResponse defaultFullHttpResponse = (DefaultFullHttpResponse) obj;
        return super.equals(defaultFullHttpResponse) && content().equals(defaultFullHttpResponse.content()) && trailingHeaders().equals(defaultFullHttpResponse.trailingHeaders());
    }

    @Override // io.netty.handler.codec.http.DefaultHttpResponse
    public String toString() {
        return d.a(new StringBuilder(256), (FullHttpResponse) this).toString();
    }
}
