package io.netty.handler.codec.http2;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import java.io.Closeable;

/* loaded from: classes4.dex */
public interface Http2FrameReader extends Closeable {

    /* loaded from: classes4.dex */
    public interface Configuration {
        Http2FrameSizePolicy frameSizePolicy();

        Http2HeaderTable headerTable();
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    void close();

    Configuration configuration();

    void readFrame(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, Http2FrameListener http2FrameListener) throws Http2Exception;
}
