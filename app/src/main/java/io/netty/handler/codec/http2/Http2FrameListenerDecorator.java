package io.netty.handler.codec.http2;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.internal.ObjectUtil;

/* loaded from: classes4.dex */
public class Http2FrameListenerDecorator implements Http2FrameListener {
    protected final Http2FrameListener listener;

    public Http2FrameListenerDecorator(Http2FrameListener http2FrameListener) {
        this.listener = (Http2FrameListener) ObjectUtil.checkNotNull(http2FrameListener, "listener");
    }

    @Override // io.netty.handler.codec.http2.Http2FrameListener
    public int onDataRead(ChannelHandlerContext channelHandlerContext, int i, ByteBuf byteBuf, int i2, boolean z) throws Http2Exception {
        return this.listener.onDataRead(channelHandlerContext, i, byteBuf, i2, z);
    }

    @Override // io.netty.handler.codec.http2.Http2FrameListener
    public void onHeadersRead(ChannelHandlerContext channelHandlerContext, int i, Http2Headers http2Headers, int i2, boolean z) throws Http2Exception {
        this.listener.onHeadersRead(channelHandlerContext, i, http2Headers, i2, z);
    }

    @Override // io.netty.handler.codec.http2.Http2FrameListener
    public void onHeadersRead(ChannelHandlerContext channelHandlerContext, int i, Http2Headers http2Headers, int i2, short s, boolean z, int i3, boolean z2) throws Http2Exception {
        this.listener.onHeadersRead(channelHandlerContext, i, http2Headers, i2, s, z, i3, z2);
    }

    @Override // io.netty.handler.codec.http2.Http2FrameListener
    public void onPriorityRead(ChannelHandlerContext channelHandlerContext, int i, int i2, short s, boolean z) throws Http2Exception {
        this.listener.onPriorityRead(channelHandlerContext, i, i2, s, z);
    }

    @Override // io.netty.handler.codec.http2.Http2FrameListener
    public void onRstStreamRead(ChannelHandlerContext channelHandlerContext, int i, long j) throws Http2Exception {
        this.listener.onRstStreamRead(channelHandlerContext, i, j);
    }

    @Override // io.netty.handler.codec.http2.Http2FrameListener
    public void onSettingsAckRead(ChannelHandlerContext channelHandlerContext) throws Http2Exception {
        this.listener.onSettingsAckRead(channelHandlerContext);
    }

    @Override // io.netty.handler.codec.http2.Http2FrameListener
    public void onSettingsRead(ChannelHandlerContext channelHandlerContext, Http2Settings http2Settings) throws Http2Exception {
        this.listener.onSettingsRead(channelHandlerContext, http2Settings);
    }

    @Override // io.netty.handler.codec.http2.Http2FrameListener
    public void onPingRead(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Http2Exception {
        this.listener.onPingRead(channelHandlerContext, byteBuf);
    }

    @Override // io.netty.handler.codec.http2.Http2FrameListener
    public void onPingAckRead(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Http2Exception {
        this.listener.onPingAckRead(channelHandlerContext, byteBuf);
    }

    @Override // io.netty.handler.codec.http2.Http2FrameListener
    public void onPushPromiseRead(ChannelHandlerContext channelHandlerContext, int i, int i2, Http2Headers http2Headers, int i3) throws Http2Exception {
        this.listener.onPushPromiseRead(channelHandlerContext, i, i2, http2Headers, i3);
    }

    @Override // io.netty.handler.codec.http2.Http2FrameListener
    public void onGoAwayRead(ChannelHandlerContext channelHandlerContext, int i, long j, ByteBuf byteBuf) throws Http2Exception {
        this.listener.onGoAwayRead(channelHandlerContext, i, j, byteBuf);
    }

    @Override // io.netty.handler.codec.http2.Http2FrameListener
    public void onWindowUpdateRead(ChannelHandlerContext channelHandlerContext, int i, int i2) throws Http2Exception {
        this.listener.onWindowUpdateRead(channelHandlerContext, i, i2);
    }

    @Override // io.netty.handler.codec.http2.Http2FrameListener
    public void onUnknownFrame(ChannelHandlerContext channelHandlerContext, byte b, int i, Http2Flags http2Flags, ByteBuf byteBuf) throws Http2Exception {
        this.listener.onUnknownFrame(channelHandlerContext, b, i, http2Flags, byteBuf);
    }
}
