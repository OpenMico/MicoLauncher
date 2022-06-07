package io.netty.handler.codec.http2;

/* loaded from: classes4.dex */
public interface Http2ResetFrame extends Http2StreamFrame {
    long errorCode();

    @Override // io.netty.handler.codec.http2.Http2StreamFrame
    Http2ResetFrame setStream(Object obj);
}
