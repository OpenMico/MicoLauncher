package io.netty.handler.codec.spdy;

import io.netty.util.internal.StringUtil;

/* loaded from: classes4.dex */
public class DefaultSpdyWindowUpdateFrame implements SpdyWindowUpdateFrame {
    private int a;
    private int b;

    public DefaultSpdyWindowUpdateFrame(int i, int i2) {
        setStreamId(i);
        setDeltaWindowSize(i2);
    }

    @Override // io.netty.handler.codec.spdy.SpdyWindowUpdateFrame
    public int streamId() {
        return this.a;
    }

    @Override // io.netty.handler.codec.spdy.SpdyWindowUpdateFrame
    public SpdyWindowUpdateFrame setStreamId(int i) {
        if (i >= 0) {
            this.a = i;
            return this;
        }
        throw new IllegalArgumentException("Stream-ID cannot be negative: " + i);
    }

    @Override // io.netty.handler.codec.spdy.SpdyWindowUpdateFrame
    public int deltaWindowSize() {
        return this.b;
    }

    @Override // io.netty.handler.codec.spdy.SpdyWindowUpdateFrame
    public SpdyWindowUpdateFrame setDeltaWindowSize(int i) {
        if (i > 0) {
            this.b = i;
            return this;
        }
        throw new IllegalArgumentException("Delta-Window-Size must be positive: " + i);
    }

    public String toString() {
        return StringUtil.simpleClassName(this) + StringUtil.NEWLINE + "--> Stream-ID = " + streamId() + StringUtil.NEWLINE + "--> Delta-Window-Size = " + deltaWindowSize();
    }
}
