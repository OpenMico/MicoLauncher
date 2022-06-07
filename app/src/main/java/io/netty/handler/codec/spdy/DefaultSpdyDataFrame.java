package io.netty.handler.codec.spdy;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.IllegalReferenceCountException;
import io.netty.util.internal.StringUtil;

/* loaded from: classes4.dex */
public class DefaultSpdyDataFrame extends DefaultSpdyStreamFrame implements SpdyDataFrame {
    private final ByteBuf a;

    public DefaultSpdyDataFrame(int i) {
        this(i, Unpooled.buffer(0));
    }

    public DefaultSpdyDataFrame(int i, ByteBuf byteBuf) {
        super(i);
        if (byteBuf != null) {
            this.a = a(byteBuf);
            return;
        }
        throw new NullPointerException("data");
    }

    private static ByteBuf a(ByteBuf byteBuf) {
        if (byteBuf.readableBytes() <= 16777215) {
            return byteBuf;
        }
        throw new IllegalArgumentException("data payload cannot exceed 16777215 bytes");
    }

    @Override // io.netty.handler.codec.spdy.DefaultSpdyStreamFrame, io.netty.handler.codec.spdy.SpdyStreamFrame, io.netty.handler.codec.spdy.SpdyDataFrame
    public SpdyDataFrame setStreamId(int i) {
        super.setStreamId(i);
        return this;
    }

    @Override // io.netty.handler.codec.spdy.DefaultSpdyStreamFrame, io.netty.handler.codec.spdy.SpdyStreamFrame, io.netty.handler.codec.spdy.SpdyDataFrame
    public SpdyDataFrame setLast(boolean z) {
        super.setLast(z);
        return this;
    }

    @Override // io.netty.handler.codec.spdy.SpdyDataFrame, io.netty.buffer.ByteBufHolder
    public ByteBuf content() {
        if (this.a.refCnt() > 0) {
            return this.a;
        }
        throw new IllegalReferenceCountException(this.a.refCnt());
    }

    @Override // io.netty.handler.codec.spdy.SpdyDataFrame, io.netty.buffer.ByteBufHolder
    public SpdyDataFrame copy() {
        return replace(content().copy());
    }

    @Override // io.netty.handler.codec.spdy.SpdyDataFrame, io.netty.buffer.ByteBufHolder
    public SpdyDataFrame duplicate() {
        return replace(content().duplicate());
    }

    @Override // io.netty.handler.codec.spdy.SpdyDataFrame, io.netty.buffer.ByteBufHolder
    public SpdyDataFrame retainedDuplicate() {
        return replace(content().retainedDuplicate());
    }

    @Override // io.netty.handler.codec.spdy.SpdyDataFrame, io.netty.buffer.ByteBufHolder
    public SpdyDataFrame replace(ByteBuf byteBuf) {
        DefaultSpdyDataFrame defaultSpdyDataFrame = new DefaultSpdyDataFrame(streamId(), byteBuf);
        defaultSpdyDataFrame.setLast(isLast());
        return defaultSpdyDataFrame;
    }

    @Override // io.netty.util.ReferenceCounted
    public int refCnt() {
        return this.a.refCnt();
    }

    @Override // io.netty.handler.codec.spdy.SpdyDataFrame, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    public SpdyDataFrame retain() {
        this.a.retain();
        return this;
    }

    @Override // io.netty.handler.codec.spdy.SpdyDataFrame, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    public SpdyDataFrame retain(int i) {
        this.a.retain(i);
        return this;
    }

    @Override // io.netty.handler.codec.spdy.SpdyDataFrame, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    public SpdyDataFrame touch() {
        this.a.touch();
        return this;
    }

    @Override // io.netty.handler.codec.spdy.SpdyDataFrame, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    public SpdyDataFrame touch(Object obj) {
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

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(StringUtil.simpleClassName(this));
        sb.append("(last: ");
        sb.append(isLast());
        sb.append(')');
        sb.append(StringUtil.NEWLINE);
        sb.append("--> Stream-ID = ");
        sb.append(streamId());
        sb.append(StringUtil.NEWLINE);
        sb.append("--> Size = ");
        if (refCnt() == 0) {
            sb.append("(freed)");
        } else {
            sb.append(content().readableBytes());
        }
        return sb.toString();
    }
}
