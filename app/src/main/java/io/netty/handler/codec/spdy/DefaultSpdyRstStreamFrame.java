package io.netty.handler.codec.spdy;

import io.netty.util.internal.StringUtil;

/* loaded from: classes4.dex */
public class DefaultSpdyRstStreamFrame extends DefaultSpdyStreamFrame implements SpdyRstStreamFrame {
    private SpdyStreamStatus a;

    public DefaultSpdyRstStreamFrame(int i, int i2) {
        this(i, SpdyStreamStatus.valueOf(i2));
    }

    public DefaultSpdyRstStreamFrame(int i, SpdyStreamStatus spdyStreamStatus) {
        super(i);
        setStatus(spdyStreamStatus);
    }

    @Override // io.netty.handler.codec.spdy.DefaultSpdyStreamFrame, io.netty.handler.codec.spdy.SpdyStreamFrame, io.netty.handler.codec.spdy.SpdyDataFrame
    public SpdyRstStreamFrame setStreamId(int i) {
        super.setStreamId(i);
        return this;
    }

    @Override // io.netty.handler.codec.spdy.DefaultSpdyStreamFrame, io.netty.handler.codec.spdy.SpdyStreamFrame, io.netty.handler.codec.spdy.SpdyDataFrame
    public SpdyRstStreamFrame setLast(boolean z) {
        super.setLast(z);
        return this;
    }

    @Override // io.netty.handler.codec.spdy.SpdyRstStreamFrame
    public SpdyStreamStatus status() {
        return this.a;
    }

    @Override // io.netty.handler.codec.spdy.SpdyRstStreamFrame
    public SpdyRstStreamFrame setStatus(SpdyStreamStatus spdyStreamStatus) {
        this.a = spdyStreamStatus;
        return this;
    }

    public String toString() {
        return StringUtil.simpleClassName(this) + StringUtil.NEWLINE + "--> Stream-ID = " + streamId() + StringUtil.NEWLINE + "--> Status: " + status();
    }
}
