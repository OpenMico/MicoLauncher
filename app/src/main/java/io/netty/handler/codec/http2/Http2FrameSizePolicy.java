package io.netty.handler.codec.http2;

/* loaded from: classes4.dex */
public interface Http2FrameSizePolicy {
    int maxFrameSize();

    void maxFrameSize(int i) throws Http2Exception;
}
