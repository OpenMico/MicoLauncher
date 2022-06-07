package io.netty.handler.codec.http2;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http2.Http2FrameLogger;
import io.netty.handler.codec.http2.Http2FrameReader;
import io.netty.util.internal.ObjectUtil;

/* loaded from: classes4.dex */
public class Http2InboundFrameLogger implements Http2FrameReader {
    private final Http2FrameReader a;
    private final Http2FrameLogger b;

    public Http2InboundFrameLogger(Http2FrameReader http2FrameReader, Http2FrameLogger http2FrameLogger) {
        this.a = (Http2FrameReader) ObjectUtil.checkNotNull(http2FrameReader, "reader");
        this.b = (Http2FrameLogger) ObjectUtil.checkNotNull(http2FrameLogger, "logger");
    }

    @Override // io.netty.handler.codec.http2.Http2FrameReader
    public void readFrame(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, final Http2FrameListener http2FrameListener) throws Http2Exception {
        this.a.readFrame(channelHandlerContext, byteBuf, new Http2FrameListener() { // from class: io.netty.handler.codec.http2.Http2InboundFrameLogger.1
            @Override // io.netty.handler.codec.http2.Http2FrameListener
            public int onDataRead(ChannelHandlerContext channelHandlerContext2, int i, ByteBuf byteBuf2, int i2, boolean z) throws Http2Exception {
                Http2InboundFrameLogger.this.b.logData(Http2FrameLogger.Direction.INBOUND, channelHandlerContext2, i, byteBuf2, i2, z);
                return http2FrameListener.onDataRead(channelHandlerContext2, i, byteBuf2, i2, z);
            }

            @Override // io.netty.handler.codec.http2.Http2FrameListener
            public void onHeadersRead(ChannelHandlerContext channelHandlerContext2, int i, Http2Headers http2Headers, int i2, boolean z) throws Http2Exception {
                Http2InboundFrameLogger.this.b.logHeaders(Http2FrameLogger.Direction.INBOUND, channelHandlerContext2, i, http2Headers, i2, z);
                http2FrameListener.onHeadersRead(channelHandlerContext2, i, http2Headers, i2, z);
            }

            @Override // io.netty.handler.codec.http2.Http2FrameListener
            public void onHeadersRead(ChannelHandlerContext channelHandlerContext2, int i, Http2Headers http2Headers, int i2, short s, boolean z, int i3, boolean z2) throws Http2Exception {
                Http2InboundFrameLogger.this.b.logHeaders(Http2FrameLogger.Direction.INBOUND, channelHandlerContext2, i, http2Headers, i2, s, z, i3, z2);
                http2FrameListener.onHeadersRead(channelHandlerContext2, i, http2Headers, i2, s, z, i3, z2);
            }

            @Override // io.netty.handler.codec.http2.Http2FrameListener
            public void onPriorityRead(ChannelHandlerContext channelHandlerContext2, int i, int i2, short s, boolean z) throws Http2Exception {
                Http2InboundFrameLogger.this.b.logPriority(Http2FrameLogger.Direction.INBOUND, channelHandlerContext2, i, i2, s, z);
                http2FrameListener.onPriorityRead(channelHandlerContext2, i, i2, s, z);
            }

            @Override // io.netty.handler.codec.http2.Http2FrameListener
            public void onRstStreamRead(ChannelHandlerContext channelHandlerContext2, int i, long j) throws Http2Exception {
                Http2InboundFrameLogger.this.b.logRstStream(Http2FrameLogger.Direction.INBOUND, channelHandlerContext2, i, j);
                http2FrameListener.onRstStreamRead(channelHandlerContext2, i, j);
            }

            @Override // io.netty.handler.codec.http2.Http2FrameListener
            public void onSettingsAckRead(ChannelHandlerContext channelHandlerContext2) throws Http2Exception {
                Http2InboundFrameLogger.this.b.logSettingsAck(Http2FrameLogger.Direction.INBOUND, channelHandlerContext2);
                http2FrameListener.onSettingsAckRead(channelHandlerContext2);
            }

            @Override // io.netty.handler.codec.http2.Http2FrameListener
            public void onSettingsRead(ChannelHandlerContext channelHandlerContext2, Http2Settings http2Settings) throws Http2Exception {
                Http2InboundFrameLogger.this.b.logSettings(Http2FrameLogger.Direction.INBOUND, channelHandlerContext2, http2Settings);
                http2FrameListener.onSettingsRead(channelHandlerContext2, http2Settings);
            }

            @Override // io.netty.handler.codec.http2.Http2FrameListener
            public void onPingRead(ChannelHandlerContext channelHandlerContext2, ByteBuf byteBuf2) throws Http2Exception {
                Http2InboundFrameLogger.this.b.logPing(Http2FrameLogger.Direction.INBOUND, channelHandlerContext2, byteBuf2);
                http2FrameListener.onPingRead(channelHandlerContext2, byteBuf2);
            }

            @Override // io.netty.handler.codec.http2.Http2FrameListener
            public void onPingAckRead(ChannelHandlerContext channelHandlerContext2, ByteBuf byteBuf2) throws Http2Exception {
                Http2InboundFrameLogger.this.b.logPingAck(Http2FrameLogger.Direction.INBOUND, channelHandlerContext2, byteBuf2);
                http2FrameListener.onPingAckRead(channelHandlerContext2, byteBuf2);
            }

            @Override // io.netty.handler.codec.http2.Http2FrameListener
            public void onPushPromiseRead(ChannelHandlerContext channelHandlerContext2, int i, int i2, Http2Headers http2Headers, int i3) throws Http2Exception {
                Http2InboundFrameLogger.this.b.logPushPromise(Http2FrameLogger.Direction.INBOUND, channelHandlerContext2, i, i2, http2Headers, i3);
                http2FrameListener.onPushPromiseRead(channelHandlerContext2, i, i2, http2Headers, i3);
            }

            @Override // io.netty.handler.codec.http2.Http2FrameListener
            public void onGoAwayRead(ChannelHandlerContext channelHandlerContext2, int i, long j, ByteBuf byteBuf2) throws Http2Exception {
                Http2InboundFrameLogger.this.b.logGoAway(Http2FrameLogger.Direction.INBOUND, channelHandlerContext2, i, j, byteBuf2);
                http2FrameListener.onGoAwayRead(channelHandlerContext2, i, j, byteBuf2);
            }

            @Override // io.netty.handler.codec.http2.Http2FrameListener
            public void onWindowUpdateRead(ChannelHandlerContext channelHandlerContext2, int i, int i2) throws Http2Exception {
                Http2InboundFrameLogger.this.b.logWindowsUpdate(Http2FrameLogger.Direction.INBOUND, channelHandlerContext2, i, i2);
                http2FrameListener.onWindowUpdateRead(channelHandlerContext2, i, i2);
            }

            @Override // io.netty.handler.codec.http2.Http2FrameListener
            public void onUnknownFrame(ChannelHandlerContext channelHandlerContext2, byte b, int i, Http2Flags http2Flags, ByteBuf byteBuf2) throws Http2Exception {
                Http2InboundFrameLogger.this.b.logUnknownFrame(Http2FrameLogger.Direction.INBOUND, channelHandlerContext2, b, i, http2Flags, byteBuf2);
                http2FrameListener.onUnknownFrame(channelHandlerContext2, b, i, http2Flags, byteBuf2);
            }
        });
    }

    @Override // io.netty.handler.codec.http2.Http2FrameReader, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        this.a.close();
    }

    @Override // io.netty.handler.codec.http2.Http2FrameReader
    public Http2FrameReader.Configuration configuration() {
        return this.a.configuration();
    }
}
