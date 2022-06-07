package io.netty.handler.codec.spdy;

import io.netty.util.internal.StringUtil;

/* loaded from: classes4.dex */
public class DefaultSpdyGoAwayFrame implements SpdyGoAwayFrame {
    private int a;
    private SpdySessionStatus b;

    public DefaultSpdyGoAwayFrame(int i) {
        this(i, 0);
    }

    public DefaultSpdyGoAwayFrame(int i, int i2) {
        this(i, SpdySessionStatus.valueOf(i2));
    }

    public DefaultSpdyGoAwayFrame(int i, SpdySessionStatus spdySessionStatus) {
        setLastGoodStreamId(i);
        setStatus(spdySessionStatus);
    }

    @Override // io.netty.handler.codec.spdy.SpdyGoAwayFrame
    public int lastGoodStreamId() {
        return this.a;
    }

    @Override // io.netty.handler.codec.spdy.SpdyGoAwayFrame
    public SpdyGoAwayFrame setLastGoodStreamId(int i) {
        if (i >= 0) {
            this.a = i;
            return this;
        }
        throw new IllegalArgumentException("Last-good-stream-ID cannot be negative: " + i);
    }

    @Override // io.netty.handler.codec.spdy.SpdyGoAwayFrame
    public SpdySessionStatus status() {
        return this.b;
    }

    @Override // io.netty.handler.codec.spdy.SpdyGoAwayFrame
    public SpdyGoAwayFrame setStatus(SpdySessionStatus spdySessionStatus) {
        this.b = spdySessionStatus;
        return this;
    }

    public String toString() {
        return StringUtil.simpleClassName(this) + StringUtil.NEWLINE + "--> Last-good-stream-ID = " + lastGoodStreamId() + StringUtil.NEWLINE + "--> Status: " + status();
    }
}
