package io.netty.handler.codec.http2;

import io.netty.channel.ChannelHandlerContext;

/* loaded from: classes4.dex */
public interface Http2FlowController {
    void channelHandlerContext(ChannelHandlerContext channelHandlerContext) throws Http2Exception;

    void incrementWindowSize(Http2Stream http2Stream, int i) throws Http2Exception;

    int initialWindowSize();

    void initialWindowSize(int i) throws Http2Exception;

    int windowSize(Http2Stream http2Stream);
}
