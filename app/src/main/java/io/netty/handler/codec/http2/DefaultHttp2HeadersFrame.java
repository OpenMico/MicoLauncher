package io.netty.handler.codec.http2;

import io.netty.util.internal.ObjectUtil;

/* loaded from: classes4.dex */
public final class DefaultHttp2HeadersFrame extends AbstractHttp2StreamFrame implements Http2HeadersFrame {
    private final Http2Headers a;
    private final boolean b;
    private final int c;

    public DefaultHttp2HeadersFrame(Http2Headers http2Headers) {
        this(http2Headers, false);
    }

    public DefaultHttp2HeadersFrame(Http2Headers http2Headers, boolean z) {
        this(http2Headers, z, 0);
    }

    public DefaultHttp2HeadersFrame(Http2Headers http2Headers, boolean z, int i) {
        this.a = (Http2Headers) ObjectUtil.checkNotNull(http2Headers, "headers");
        this.b = z;
        if (i < 0 || i > 255) {
            throw new IllegalArgumentException("padding must be non-negative and less than 256");
        }
        this.c = i;
    }

    @Override // io.netty.handler.codec.http2.AbstractHttp2StreamFrame, io.netty.handler.codec.http2.Http2StreamFrame
    public DefaultHttp2HeadersFrame setStream(Object obj) {
        super.setStream(obj);
        return this;
    }

    @Override // io.netty.handler.codec.http2.Http2HeadersFrame
    public Http2Headers headers() {
        return this.a;
    }

    @Override // io.netty.handler.codec.http2.Http2HeadersFrame
    public boolean isEndStream() {
        return this.b;
    }

    @Override // io.netty.handler.codec.http2.Http2HeadersFrame
    public int padding() {
        return this.c;
    }

    public String toString() {
        return "DefaultHttp2HeadersFrame(stream=" + stream() + ", headers=" + this.a + ", endStream=" + this.b + ", padding=" + this.c + ")";
    }

    @Override // io.netty.handler.codec.http2.AbstractHttp2StreamFrame
    public boolean equals(Object obj) {
        if (!(obj instanceof DefaultHttp2HeadersFrame)) {
            return false;
        }
        DefaultHttp2HeadersFrame defaultHttp2HeadersFrame = (DefaultHttp2HeadersFrame) obj;
        return super.equals(defaultHttp2HeadersFrame) && this.a.equals(defaultHttp2HeadersFrame.a) && this.b == defaultHttp2HeadersFrame.b && this.c == defaultHttp2HeadersFrame.c;
    }

    @Override // io.netty.handler.codec.http2.AbstractHttp2StreamFrame
    public int hashCode() {
        return (((((super.hashCode() * 31) + this.a.hashCode()) * 31) + (!this.b ? 1 : 0)) * 31) + this.c;
    }
}
