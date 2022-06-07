package io.netty.handler.codec.spdy;

/* loaded from: classes4.dex */
public abstract class DefaultSpdyStreamFrame implements SpdyStreamFrame {
    private int a;
    private boolean b;

    /* JADX INFO: Access modifiers changed from: protected */
    public DefaultSpdyStreamFrame(int i) {
        setStreamId(i);
    }

    @Override // io.netty.handler.codec.spdy.SpdyStreamFrame
    public int streamId() {
        return this.a;
    }

    @Override // io.netty.handler.codec.spdy.SpdyStreamFrame, io.netty.handler.codec.spdy.SpdyDataFrame
    public SpdyStreamFrame setStreamId(int i) {
        if (i > 0) {
            this.a = i;
            return this;
        }
        throw new IllegalArgumentException("Stream-ID must be positive: " + i);
    }

    @Override // io.netty.handler.codec.spdy.SpdyStreamFrame
    public boolean isLast() {
        return this.b;
    }

    @Override // io.netty.handler.codec.spdy.SpdyStreamFrame, io.netty.handler.codec.spdy.SpdyDataFrame
    public SpdyStreamFrame setLast(boolean z) {
        this.b = z;
        return this;
    }
}
