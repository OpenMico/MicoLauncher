package io.netty.handler.codec.spdy;

/* loaded from: classes4.dex */
public interface SpdySynStreamFrame extends SpdyHeadersFrame {
    int associatedStreamId();

    boolean isUnidirectional();

    byte priority();

    SpdySynStreamFrame setAssociatedStreamId(int i);

    @Override // io.netty.handler.codec.spdy.SpdyHeadersFrame
    SpdySynStreamFrame setInvalid();

    @Override // io.netty.handler.codec.spdy.SpdyHeadersFrame, io.netty.handler.codec.spdy.SpdyStreamFrame, io.netty.handler.codec.spdy.SpdyDataFrame
    SpdySynStreamFrame setLast(boolean z);

    SpdySynStreamFrame setPriority(byte b);

    @Override // io.netty.handler.codec.spdy.SpdyHeadersFrame, io.netty.handler.codec.spdy.SpdyStreamFrame, io.netty.handler.codec.spdy.SpdyDataFrame
    SpdySynStreamFrame setStreamId(int i);

    SpdySynStreamFrame setUnidirectional(boolean z);
}
