package io.netty.handler.codec.spdy;

/* loaded from: classes4.dex */
public interface SpdyWindowUpdateFrame extends SpdyFrame {
    int deltaWindowSize();

    SpdyWindowUpdateFrame setDeltaWindowSize(int i);

    SpdyWindowUpdateFrame setStreamId(int i);

    int streamId();
}
