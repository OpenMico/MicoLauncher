package io.netty.handler.codec.http2;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.IllegalReferenceCountException;
import io.netty.util.internal.ObjectUtil;

/* loaded from: classes4.dex */
public final class DefaultHttp2DataFrame extends AbstractHttp2StreamFrame implements Http2DataFrame {
    private final ByteBuf a;
    private final boolean b;
    private final int c;

    public DefaultHttp2DataFrame(ByteBuf byteBuf) {
        this(byteBuf, false);
    }

    public DefaultHttp2DataFrame(boolean z) {
        this(Unpooled.EMPTY_BUFFER, z);
    }

    public DefaultHttp2DataFrame(ByteBuf byteBuf, boolean z) {
        this(byteBuf, z, 0);
    }

    public DefaultHttp2DataFrame(ByteBuf byteBuf, boolean z, int i) {
        this.a = (ByteBuf) ObjectUtil.checkNotNull(byteBuf, "content");
        this.b = z;
        if (i < 0 || i > 255) {
            throw new IllegalArgumentException("padding must be non-negative and less than 256");
        }
        this.c = i;
    }

    @Override // io.netty.handler.codec.http2.AbstractHttp2StreamFrame, io.netty.handler.codec.http2.Http2StreamFrame
    public DefaultHttp2DataFrame setStream(Object obj) {
        super.setStream(obj);
        return this;
    }

    @Override // io.netty.handler.codec.http2.Http2DataFrame
    public boolean isEndStream() {
        return this.b;
    }

    @Override // io.netty.handler.codec.http2.Http2DataFrame
    public int padding() {
        return this.c;
    }

    @Override // io.netty.handler.codec.http2.Http2DataFrame, io.netty.buffer.ByteBufHolder
    public ByteBuf content() {
        if (this.a.refCnt() > 0) {
            return this.a;
        }
        throw new IllegalReferenceCountException(this.a.refCnt());
    }

    @Override // io.netty.handler.codec.http2.Http2DataFrame, io.netty.buffer.ByteBufHolder
    public DefaultHttp2DataFrame copy() {
        return replace(content().copy());
    }

    @Override // io.netty.handler.codec.http2.Http2DataFrame, io.netty.buffer.ByteBufHolder
    public DefaultHttp2DataFrame duplicate() {
        return replace(content().duplicate());
    }

    @Override // io.netty.handler.codec.http2.Http2DataFrame, io.netty.buffer.ByteBufHolder
    public DefaultHttp2DataFrame retainedDuplicate() {
        return replace(content().retainedDuplicate());
    }

    @Override // io.netty.handler.codec.http2.Http2DataFrame, io.netty.buffer.ByteBufHolder
    public DefaultHttp2DataFrame replace(ByteBuf byteBuf) {
        return new DefaultHttp2DataFrame(byteBuf, this.b, this.c);
    }

    @Override // io.netty.util.ReferenceCounted
    public int refCnt() {
        return this.a.refCnt();
    }

    @Override // io.netty.util.ReferenceCounted
    public boolean release() {
        return this.a.release();
    }

    @Override // io.netty.util.ReferenceCounted
    public boolean release(int i) {
        return this.a.release(i);
    }

    @Override // io.netty.handler.codec.http2.Http2DataFrame, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    public DefaultHttp2DataFrame retain() {
        this.a.retain();
        return this;
    }

    @Override // io.netty.handler.codec.http2.Http2DataFrame, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    public DefaultHttp2DataFrame retain(int i) {
        this.a.retain(i);
        return this;
    }

    public String toString() {
        return "DefaultHttp2DataFrame(stream=" + stream() + ", content=" + this.a + ", endStream=" + this.b + ", padding=" + this.c + ")";
    }

    @Override // io.netty.handler.codec.http2.Http2DataFrame, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    public DefaultHttp2DataFrame touch() {
        this.a.touch();
        return this;
    }

    @Override // io.netty.handler.codec.http2.Http2DataFrame, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    public DefaultHttp2DataFrame touch(Object obj) {
        this.a.touch(obj);
        return this;
    }

    @Override // io.netty.handler.codec.http2.AbstractHttp2StreamFrame
    public boolean equals(Object obj) {
        if (!(obj instanceof DefaultHttp2DataFrame)) {
            return false;
        }
        DefaultHttp2DataFrame defaultHttp2DataFrame = (DefaultHttp2DataFrame) obj;
        return super.equals(defaultHttp2DataFrame) && this.a.equals(defaultHttp2DataFrame.content()) && this.b == defaultHttp2DataFrame.b && this.c == defaultHttp2DataFrame.c;
    }

    @Override // io.netty.handler.codec.http2.AbstractHttp2StreamFrame
    public int hashCode() {
        return (((((super.hashCode() * 31) + this.a.hashCode()) * 31) + (!this.b ? 1 : 0)) * 31) + this.c;
    }
}
