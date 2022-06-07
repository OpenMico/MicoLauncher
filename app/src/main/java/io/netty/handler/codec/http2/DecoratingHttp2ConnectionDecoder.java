package io.netty.handler.codec.http2;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.internal.ObjectUtil;
import java.util.List;

/* loaded from: classes4.dex */
public class DecoratingHttp2ConnectionDecoder implements Http2ConnectionDecoder {
    private final Http2ConnectionDecoder a;

    public DecoratingHttp2ConnectionDecoder(Http2ConnectionDecoder http2ConnectionDecoder) {
        this.a = (Http2ConnectionDecoder) ObjectUtil.checkNotNull(http2ConnectionDecoder, "delegate");
    }

    @Override // io.netty.handler.codec.http2.Http2ConnectionDecoder
    public void lifecycleManager(Http2LifecycleManager http2LifecycleManager) {
        this.a.lifecycleManager(http2LifecycleManager);
    }

    @Override // io.netty.handler.codec.http2.Http2ConnectionDecoder
    public Http2Connection connection() {
        return this.a.connection();
    }

    @Override // io.netty.handler.codec.http2.Http2ConnectionDecoder
    public Http2LocalFlowController flowController() {
        return this.a.flowController();
    }

    @Override // io.netty.handler.codec.http2.Http2ConnectionDecoder
    public void frameListener(Http2FrameListener http2FrameListener) {
        this.a.frameListener(http2FrameListener);
    }

    @Override // io.netty.handler.codec.http2.Http2ConnectionDecoder
    public Http2FrameListener frameListener() {
        return this.a.frameListener();
    }

    @Override // io.netty.handler.codec.http2.Http2ConnectionDecoder
    public void decodeFrame(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Http2Exception {
        this.a.decodeFrame(channelHandlerContext, byteBuf, list);
    }

    @Override // io.netty.handler.codec.http2.Http2ConnectionDecoder
    public Http2Settings localSettings() {
        return this.a.localSettings();
    }

    @Override // io.netty.handler.codec.http2.Http2ConnectionDecoder
    public void localSettings(Http2Settings http2Settings) throws Http2Exception {
        this.a.localSettings(http2Settings);
    }

    @Override // io.netty.handler.codec.http2.Http2ConnectionDecoder
    public boolean prefaceReceived() {
        return this.a.prefaceReceived();
    }

    @Override // io.netty.handler.codec.http2.Http2ConnectionDecoder, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        this.a.close();
    }
}
