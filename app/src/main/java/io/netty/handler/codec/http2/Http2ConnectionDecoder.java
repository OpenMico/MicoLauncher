package io.netty.handler.codec.http2;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import java.io.Closeable;
import java.util.List;

/* loaded from: classes4.dex */
public interface Http2ConnectionDecoder extends Closeable {
    @Override // java.io.Closeable, java.lang.AutoCloseable
    void close();

    Http2Connection connection();

    void decodeFrame(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Http2Exception;

    Http2LocalFlowController flowController();

    Http2FrameListener frameListener();

    void frameListener(Http2FrameListener http2FrameListener);

    void lifecycleManager(Http2LifecycleManager http2LifecycleManager);

    Http2Settings localSettings();

    void localSettings(Http2Settings http2Settings) throws Http2Exception;

    boolean prefaceReceived();
}
