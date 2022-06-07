package io.netty.handler.codec.http2;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http2.Http2FrameWriter;
import io.netty.util.internal.ObjectUtil;

/* loaded from: classes4.dex */
public class DecoratingHttp2FrameWriter implements Http2FrameWriter {
    private final Http2FrameWriter a;

    public DecoratingHttp2FrameWriter(Http2FrameWriter http2FrameWriter) {
        this.a = (Http2FrameWriter) ObjectUtil.checkNotNull(http2FrameWriter, "delegate");
    }

    @Override // io.netty.handler.codec.http2.Http2DataWriter
    public ChannelFuture writeData(ChannelHandlerContext channelHandlerContext, int i, ByteBuf byteBuf, int i2, boolean z, ChannelPromise channelPromise) {
        return this.a.writeData(channelHandlerContext, i, byteBuf, i2, z, channelPromise);
    }

    @Override // io.netty.handler.codec.http2.Http2FrameWriter
    public ChannelFuture writeHeaders(ChannelHandlerContext channelHandlerContext, int i, Http2Headers http2Headers, int i2, boolean z, ChannelPromise channelPromise) {
        return this.a.writeHeaders(channelHandlerContext, i, http2Headers, i2, z, channelPromise);
    }

    @Override // io.netty.handler.codec.http2.Http2FrameWriter
    public ChannelFuture writeHeaders(ChannelHandlerContext channelHandlerContext, int i, Http2Headers http2Headers, int i2, short s, boolean z, int i3, boolean z2, ChannelPromise channelPromise) {
        return this.a.writeHeaders(channelHandlerContext, i, http2Headers, i2, s, z, i3, z2, channelPromise);
    }

    @Override // io.netty.handler.codec.http2.Http2FrameWriter
    public ChannelFuture writePriority(ChannelHandlerContext channelHandlerContext, int i, int i2, short s, boolean z, ChannelPromise channelPromise) {
        return this.a.writePriority(channelHandlerContext, i, i2, s, z, channelPromise);
    }

    @Override // io.netty.handler.codec.http2.Http2FrameWriter
    public ChannelFuture writeRstStream(ChannelHandlerContext channelHandlerContext, int i, long j, ChannelPromise channelPromise) {
        return this.a.writeRstStream(channelHandlerContext, i, j, channelPromise);
    }

    @Override // io.netty.handler.codec.http2.Http2FrameWriter
    public ChannelFuture writeSettings(ChannelHandlerContext channelHandlerContext, Http2Settings http2Settings, ChannelPromise channelPromise) {
        return this.a.writeSettings(channelHandlerContext, http2Settings, channelPromise);
    }

    @Override // io.netty.handler.codec.http2.Http2FrameWriter
    public ChannelFuture writeSettingsAck(ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) {
        return this.a.writeSettingsAck(channelHandlerContext, channelPromise);
    }

    @Override // io.netty.handler.codec.http2.Http2FrameWriter
    public ChannelFuture writePing(ChannelHandlerContext channelHandlerContext, boolean z, ByteBuf byteBuf, ChannelPromise channelPromise) {
        return this.a.writePing(channelHandlerContext, z, byteBuf, channelPromise);
    }

    @Override // io.netty.handler.codec.http2.Http2FrameWriter
    public ChannelFuture writePushPromise(ChannelHandlerContext channelHandlerContext, int i, int i2, Http2Headers http2Headers, int i3, ChannelPromise channelPromise) {
        return this.a.writePushPromise(channelHandlerContext, i, i2, http2Headers, i3, channelPromise);
    }

    @Override // io.netty.handler.codec.http2.Http2FrameWriter
    public ChannelFuture writeGoAway(ChannelHandlerContext channelHandlerContext, int i, long j, ByteBuf byteBuf, ChannelPromise channelPromise) {
        return this.a.writeGoAway(channelHandlerContext, i, j, byteBuf, channelPromise);
    }

    @Override // io.netty.handler.codec.http2.Http2FrameWriter
    public ChannelFuture writeWindowUpdate(ChannelHandlerContext channelHandlerContext, int i, int i2, ChannelPromise channelPromise) {
        return this.a.writeWindowUpdate(channelHandlerContext, i, i2, channelPromise);
    }

    @Override // io.netty.handler.codec.http2.Http2FrameWriter
    public ChannelFuture writeFrame(ChannelHandlerContext channelHandlerContext, byte b, int i, Http2Flags http2Flags, ByteBuf byteBuf, ChannelPromise channelPromise) {
        return this.a.writeFrame(channelHandlerContext, b, i, http2Flags, byteBuf, channelPromise);
    }

    @Override // io.netty.handler.codec.http2.Http2FrameWriter
    public Http2FrameWriter.Configuration configuration() {
        return this.a.configuration();
    }

    @Override // io.netty.handler.codec.http2.Http2FrameWriter, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        this.a.close();
    }
}
