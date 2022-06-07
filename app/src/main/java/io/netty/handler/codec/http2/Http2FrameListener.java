package io.netty.handler.codec.http2;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

/* loaded from: classes4.dex */
public interface Http2FrameListener {
    int onDataRead(ChannelHandlerContext channelHandlerContext, int i, ByteBuf byteBuf, int i2, boolean z) throws Http2Exception;

    void onGoAwayRead(ChannelHandlerContext channelHandlerContext, int i, long j, ByteBuf byteBuf) throws Http2Exception;

    void onHeadersRead(ChannelHandlerContext channelHandlerContext, int i, Http2Headers http2Headers, int i2, short s, boolean z, int i3, boolean z2) throws Http2Exception;

    void onHeadersRead(ChannelHandlerContext channelHandlerContext, int i, Http2Headers http2Headers, int i2, boolean z) throws Http2Exception;

    void onPingAckRead(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Http2Exception;

    void onPingRead(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Http2Exception;

    void onPriorityRead(ChannelHandlerContext channelHandlerContext, int i, int i2, short s, boolean z) throws Http2Exception;

    void onPushPromiseRead(ChannelHandlerContext channelHandlerContext, int i, int i2, Http2Headers http2Headers, int i3) throws Http2Exception;

    void onRstStreamRead(ChannelHandlerContext channelHandlerContext, int i, long j) throws Http2Exception;

    void onSettingsAckRead(ChannelHandlerContext channelHandlerContext) throws Http2Exception;

    void onSettingsRead(ChannelHandlerContext channelHandlerContext, Http2Settings http2Settings) throws Http2Exception;

    void onUnknownFrame(ChannelHandlerContext channelHandlerContext, byte b, int i, Http2Flags http2Flags, ByteBuf byteBuf) throws Http2Exception;

    void onWindowUpdateRead(ChannelHandlerContext channelHandlerContext, int i, int i2) throws Http2Exception;
}
