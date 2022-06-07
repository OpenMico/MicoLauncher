package io.netty.handler.codec.http2;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

/* loaded from: classes4.dex */
public interface Http2ConnectionEncoder extends Http2FrameWriter {
    Http2Connection connection();

    Http2RemoteFlowController flowController();

    Http2FrameWriter frameWriter();

    void lifecycleManager(Http2LifecycleManager http2LifecycleManager);

    Http2Settings pollSentSettings();

    void remoteSettings(Http2Settings http2Settings) throws Http2Exception;

    @Override // io.netty.handler.codec.http2.Http2FrameWriter
    ChannelFuture writeFrame(ChannelHandlerContext channelHandlerContext, byte b, int i, Http2Flags http2Flags, ByteBuf byteBuf, ChannelPromise channelPromise);
}
