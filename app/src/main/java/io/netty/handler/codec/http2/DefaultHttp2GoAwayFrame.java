package io.netty.handler.codec.http2;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.DefaultByteBufHolder;
import io.netty.buffer.Unpooled;

/* loaded from: classes4.dex */
public final class DefaultHttp2GoAwayFrame extends DefaultByteBufHolder implements Http2GoAwayFrame {
    private final long a;
    private int b;

    public DefaultHttp2GoAwayFrame(Http2Error http2Error) {
        this(http2Error.code());
    }

    public DefaultHttp2GoAwayFrame(long j) {
        this(j, Unpooled.EMPTY_BUFFER);
    }

    public DefaultHttp2GoAwayFrame(Http2Error http2Error, ByteBuf byteBuf) {
        this(http2Error.code(), byteBuf);
    }

    public DefaultHttp2GoAwayFrame(long j, ByteBuf byteBuf) {
        super(byteBuf);
        this.a = j;
    }

    @Override // io.netty.handler.codec.http2.Http2GoAwayFrame
    public long errorCode() {
        return this.a;
    }

    @Override // io.netty.handler.codec.http2.Http2GoAwayFrame
    public int extraStreamIds() {
        return this.b;
    }

    @Override // io.netty.handler.codec.http2.Http2GoAwayFrame
    public Http2GoAwayFrame setExtraStreamIds(int i) {
        if (i >= 0) {
            this.b = i;
            return this;
        }
        throw new IllegalArgumentException("extraStreamIds must be non-negative");
    }

    @Override // io.netty.buffer.DefaultByteBufHolder, io.netty.buffer.ByteBufHolder
    public Http2GoAwayFrame copy() {
        return (Http2GoAwayFrame) super.copy();
    }

    @Override // io.netty.buffer.DefaultByteBufHolder, io.netty.buffer.ByteBufHolder
    public Http2GoAwayFrame duplicate() {
        return (Http2GoAwayFrame) super.duplicate();
    }

    @Override // io.netty.buffer.DefaultByteBufHolder, io.netty.buffer.ByteBufHolder
    public Http2GoAwayFrame retainedDuplicate() {
        return (Http2GoAwayFrame) super.retainedDuplicate();
    }

    @Override // io.netty.buffer.DefaultByteBufHolder, io.netty.buffer.ByteBufHolder
    public Http2GoAwayFrame replace(ByteBuf byteBuf) {
        return new DefaultHttp2GoAwayFrame(this.a, byteBuf).setExtraStreamIds(this.b);
    }

    @Override // io.netty.buffer.DefaultByteBufHolder, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    public Http2GoAwayFrame retain() {
        super.retain();
        return this;
    }

    @Override // io.netty.buffer.DefaultByteBufHolder, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    public Http2GoAwayFrame retain(int i) {
        super.retain(i);
        return this;
    }

    @Override // io.netty.buffer.DefaultByteBufHolder, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    public Http2GoAwayFrame touch() {
        super.touch();
        return this;
    }

    @Override // io.netty.buffer.DefaultByteBufHolder, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    public Http2GoAwayFrame touch(Object obj) {
        super.touch(obj);
        return this;
    }

    @Override // io.netty.buffer.DefaultByteBufHolder
    public boolean equals(Object obj) {
        if (!(obj instanceof DefaultHttp2GoAwayFrame)) {
            return false;
        }
        DefaultHttp2GoAwayFrame defaultHttp2GoAwayFrame = (DefaultHttp2GoAwayFrame) obj;
        return super.equals(obj) && this.a == defaultHttp2GoAwayFrame.a && content().equals(defaultHttp2GoAwayFrame.content()) && this.b == defaultHttp2GoAwayFrame.b;
    }

    @Override // io.netty.buffer.DefaultByteBufHolder
    public int hashCode() {
        long j = this.a;
        return (((((-1230679765) + ((int) (j ^ (j >>> 32)))) * 31) + content().hashCode()) * 31) + this.b;
    }

    @Override // io.netty.buffer.DefaultByteBufHolder
    public String toString() {
        return "DefaultHttp2GoAwayFrame(errorCode=" + this.a + ", content=" + content() + ", extraStreamIds=" + this.b + ")";
    }
}
