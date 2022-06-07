package io.netty.handler.codec.spdy;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandler;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.UnsupportedMessageTypeException;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import java.net.SocketAddress;
import java.util.List;

/* loaded from: classes4.dex */
public class SpdyFrameCodec extends ByteToMessageDecoder implements ChannelOutboundHandler, SpdyFrameDecoderDelegate {
    private static final SpdyProtocolException a = new SpdyProtocolException("Received invalid frame");
    private final SpdyFrameDecoder c;
    private final SpdyFrameEncoder d;
    private final b e;
    private final c f;
    private SpdyHeadersFrame g;
    private SpdySettingsFrame h;
    private ChannelHandlerContext i;
    private boolean j;
    private final boolean k;

    public SpdyFrameCodec(SpdyVersion spdyVersion) {
        this(spdyVersion, true);
    }

    public SpdyFrameCodec(SpdyVersion spdyVersion, boolean z) {
        this(spdyVersion, 8192, 16384, 6, 15, 8, z);
    }

    public SpdyFrameCodec(SpdyVersion spdyVersion, int i, int i2, int i3, int i4, int i5) {
        this(spdyVersion, i, i2, i3, i4, i5, true);
    }

    public SpdyFrameCodec(SpdyVersion spdyVersion, int i, int i2, int i3, int i4, int i5, boolean z) {
        this(spdyVersion, i, b.a(spdyVersion, i2), c.a(spdyVersion, i3, i4, i5), z);
    }

    protected SpdyFrameCodec(SpdyVersion spdyVersion, int i, b bVar, c cVar, boolean z) {
        this.c = new SpdyFrameDecoder(spdyVersion, this, i);
        this.d = new SpdyFrameEncoder(spdyVersion);
        this.e = bVar;
        this.f = cVar;
        this.k = z;
    }

    @Override // io.netty.channel.ChannelHandlerAdapter, io.netty.channel.ChannelHandler
    public void handlerAdded(ChannelHandlerContext channelHandlerContext) throws Exception {
        super.handlerAdded(channelHandlerContext);
        this.i = channelHandlerContext;
        channelHandlerContext.channel().closeFuture().addListener((GenericFutureListener<? extends Future<? super Void>>) new ChannelFutureListener() { // from class: io.netty.handler.codec.spdy.SpdyFrameCodec.1
            /* renamed from: a */
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                SpdyFrameCodec.this.e.a();
                SpdyFrameCodec.this.f.a();
            }
        });
    }

    @Override // io.netty.handler.codec.ByteToMessageDecoder
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        this.c.decode(byteBuf);
    }

    @Override // io.netty.handler.codec.ByteToMessageDecoder, io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelInboundHandler
    public void channelReadComplete(ChannelHandlerContext channelHandlerContext) throws Exception {
        if (!this.j && !channelHandlerContext.channel().config().isAutoRead()) {
            channelHandlerContext.read();
        }
        this.j = false;
        super.channelReadComplete(channelHandlerContext);
    }

    @Override // io.netty.channel.ChannelOutboundHandler
    public void bind(ChannelHandlerContext channelHandlerContext, SocketAddress socketAddress, ChannelPromise channelPromise) throws Exception {
        channelHandlerContext.bind(socketAddress, channelPromise);
    }

    @Override // io.netty.channel.ChannelOutboundHandler
    public void connect(ChannelHandlerContext channelHandlerContext, SocketAddress socketAddress, SocketAddress socketAddress2, ChannelPromise channelPromise) throws Exception {
        channelHandlerContext.connect(socketAddress, socketAddress2, channelPromise);
    }

    @Override // io.netty.channel.ChannelOutboundHandler
    public void disconnect(ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) throws Exception {
        channelHandlerContext.disconnect(channelPromise);
    }

    @Override // io.netty.channel.ChannelOutboundHandler
    public void close(ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) throws Exception {
        channelHandlerContext.close(channelPromise);
    }

    @Override // io.netty.channel.ChannelOutboundHandler
    public void deregister(ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) throws Exception {
        channelHandlerContext.deregister(channelPromise);
    }

    @Override // io.netty.channel.ChannelOutboundHandler
    public void read(ChannelHandlerContext channelHandlerContext) throws Exception {
        channelHandlerContext.read();
    }

    @Override // io.netty.channel.ChannelOutboundHandler
    public void flush(ChannelHandlerContext channelHandlerContext) throws Exception {
        channelHandlerContext.flush();
    }

    @Override // io.netty.channel.ChannelOutboundHandler
    public void write(ChannelHandlerContext channelHandlerContext, Object obj, ChannelPromise channelPromise) throws Exception {
        ByteBuf encode;
        if (obj instanceof SpdyDataFrame) {
            SpdyDataFrame spdyDataFrame = (SpdyDataFrame) obj;
            ByteBuf encodeDataFrame = this.d.encodeDataFrame(channelHandlerContext.alloc(), spdyDataFrame.streamId(), spdyDataFrame.isLast(), spdyDataFrame.content());
            spdyDataFrame.release();
            channelHandlerContext.write(encodeDataFrame, channelPromise);
        } else if (obj instanceof SpdySynStreamFrame) {
            SpdySynStreamFrame spdySynStreamFrame = (SpdySynStreamFrame) obj;
            encode = this.f.encode(channelHandlerContext.alloc(), spdySynStreamFrame);
            try {
                ByteBuf encodeSynStreamFrame = this.d.encodeSynStreamFrame(channelHandlerContext.alloc(), spdySynStreamFrame.streamId(), spdySynStreamFrame.associatedStreamId(), spdySynStreamFrame.priority(), spdySynStreamFrame.isLast(), spdySynStreamFrame.isUnidirectional(), encode);
                encode.release();
                channelHandlerContext.write(encodeSynStreamFrame, channelPromise);
            } finally {
            }
        } else if (obj instanceof SpdySynReplyFrame) {
            SpdySynReplyFrame spdySynReplyFrame = (SpdySynReplyFrame) obj;
            encode = this.f.encode(channelHandlerContext.alloc(), spdySynReplyFrame);
            try {
                ByteBuf encodeSynReplyFrame = this.d.encodeSynReplyFrame(channelHandlerContext.alloc(), spdySynReplyFrame.streamId(), spdySynReplyFrame.isLast(), encode);
                encode.release();
                channelHandlerContext.write(encodeSynReplyFrame, channelPromise);
            } finally {
            }
        } else if (obj instanceof SpdyRstStreamFrame) {
            SpdyRstStreamFrame spdyRstStreamFrame = (SpdyRstStreamFrame) obj;
            channelHandlerContext.write(this.d.encodeRstStreamFrame(channelHandlerContext.alloc(), spdyRstStreamFrame.streamId(), spdyRstStreamFrame.status().code()), channelPromise);
        } else if (obj instanceof SpdySettingsFrame) {
            channelHandlerContext.write(this.d.encodeSettingsFrame(channelHandlerContext.alloc(), (SpdySettingsFrame) obj), channelPromise);
        } else if (obj instanceof SpdyPingFrame) {
            channelHandlerContext.write(this.d.encodePingFrame(channelHandlerContext.alloc(), ((SpdyPingFrame) obj).id()), channelPromise);
        } else if (obj instanceof SpdyGoAwayFrame) {
            SpdyGoAwayFrame spdyGoAwayFrame = (SpdyGoAwayFrame) obj;
            channelHandlerContext.write(this.d.encodeGoAwayFrame(channelHandlerContext.alloc(), spdyGoAwayFrame.lastGoodStreamId(), spdyGoAwayFrame.status().code()), channelPromise);
        } else if (obj instanceof SpdyHeadersFrame) {
            SpdyHeadersFrame spdyHeadersFrame = (SpdyHeadersFrame) obj;
            encode = this.f.encode(channelHandlerContext.alloc(), spdyHeadersFrame);
            try {
                ByteBuf encodeHeadersFrame = this.d.encodeHeadersFrame(channelHandlerContext.alloc(), spdyHeadersFrame.streamId(), spdyHeadersFrame.isLast(), encode);
                encode.release();
                channelHandlerContext.write(encodeHeadersFrame, channelPromise);
            } finally {
            }
        } else if (obj instanceof SpdyWindowUpdateFrame) {
            SpdyWindowUpdateFrame spdyWindowUpdateFrame = (SpdyWindowUpdateFrame) obj;
            channelHandlerContext.write(this.d.encodeWindowUpdateFrame(channelHandlerContext.alloc(), spdyWindowUpdateFrame.streamId(), spdyWindowUpdateFrame.deltaWindowSize()), channelPromise);
        } else {
            throw new UnsupportedMessageTypeException(obj, new Class[0]);
        }
    }

    @Override // io.netty.handler.codec.spdy.SpdyFrameDecoderDelegate
    public void readDataFrame(int i, boolean z, ByteBuf byteBuf) {
        this.j = true;
        DefaultSpdyDataFrame defaultSpdyDataFrame = new DefaultSpdyDataFrame(i, byteBuf);
        defaultSpdyDataFrame.setLast(z);
        this.i.fireChannelRead((Object) defaultSpdyDataFrame);
    }

    @Override // io.netty.handler.codec.spdy.SpdyFrameDecoderDelegate
    public void readSynStreamFrame(int i, int i2, byte b, boolean z, boolean z2) {
        DefaultSpdySynStreamFrame defaultSpdySynStreamFrame = new DefaultSpdySynStreamFrame(i, i2, b, this.k);
        defaultSpdySynStreamFrame.setLast(z);
        defaultSpdySynStreamFrame.setUnidirectional(z2);
        this.g = defaultSpdySynStreamFrame;
    }

    @Override // io.netty.handler.codec.spdy.SpdyFrameDecoderDelegate
    public void readSynReplyFrame(int i, boolean z) {
        DefaultSpdySynReplyFrame defaultSpdySynReplyFrame = new DefaultSpdySynReplyFrame(i, this.k);
        defaultSpdySynReplyFrame.setLast(z);
        this.g = defaultSpdySynReplyFrame;
    }

    @Override // io.netty.handler.codec.spdy.SpdyFrameDecoderDelegate
    public void readRstStreamFrame(int i, int i2) {
        this.j = true;
        this.i.fireChannelRead((Object) new DefaultSpdyRstStreamFrame(i, i2));
    }

    @Override // io.netty.handler.codec.spdy.SpdyFrameDecoderDelegate
    public void readSettingsFrame(boolean z) {
        this.j = true;
        this.h = new DefaultSpdySettingsFrame();
        this.h.setClearPreviouslyPersistedSettings(z);
    }

    @Override // io.netty.handler.codec.spdy.SpdyFrameDecoderDelegate
    public void readSetting(int i, int i2, boolean z, boolean z2) {
        this.h.setValue(i, i2, z, z2);
    }

    @Override // io.netty.handler.codec.spdy.SpdyFrameDecoderDelegate
    public void readSettingsEnd() {
        this.j = true;
        SpdySettingsFrame spdySettingsFrame = this.h;
        this.h = null;
        this.i.fireChannelRead((Object) spdySettingsFrame);
    }

    @Override // io.netty.handler.codec.spdy.SpdyFrameDecoderDelegate
    public void readPingFrame(int i) {
        this.j = true;
        this.i.fireChannelRead((Object) new DefaultSpdyPingFrame(i));
    }

    @Override // io.netty.handler.codec.spdy.SpdyFrameDecoderDelegate
    public void readGoAwayFrame(int i, int i2) {
        this.j = true;
        this.i.fireChannelRead((Object) new DefaultSpdyGoAwayFrame(i, i2));
    }

    @Override // io.netty.handler.codec.spdy.SpdyFrameDecoderDelegate
    public void readHeadersFrame(int i, boolean z) {
        this.g = new DefaultSpdyHeadersFrame(i, this.k);
        this.g.setLast(z);
    }

    @Override // io.netty.handler.codec.spdy.SpdyFrameDecoderDelegate
    public void readWindowUpdateFrame(int i, int i2) {
        this.j = true;
        this.i.fireChannelRead((Object) new DefaultSpdyWindowUpdateFrame(i, i2));
    }

    @Override // io.netty.handler.codec.spdy.SpdyFrameDecoderDelegate
    public void readHeaderBlock(ByteBuf byteBuf) {
        try {
            try {
                this.e.a(this.i.alloc(), byteBuf, this.g);
            } catch (Exception e) {
                this.i.fireExceptionCaught((Throwable) e);
            }
        } finally {
            byteBuf.release();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x001b  */
    /* JADX WARN: Removed duplicated region for block: B:17:? A[RETURN, SYNTHETIC] */
    @Override // io.netty.handler.codec.spdy.SpdyFrameDecoderDelegate
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void readHeaderBlockEnd() {
        /*
            r4 = this;
            r0 = 0
            io.netty.handler.codec.spdy.b r1 = r4.e     // Catch: Exception -> 0x0013
            io.netty.handler.codec.spdy.SpdyHeadersFrame r2 = r4.g     // Catch: Exception -> 0x0013
            r1.a(r2)     // Catch: Exception -> 0x0013
            io.netty.handler.codec.spdy.SpdyHeadersFrame r1 = r4.g     // Catch: Exception -> 0x0013
            r4.g = r0     // Catch: Exception -> 0x000e
            r0 = r1
            goto L_0x0019
        L_0x000e:
            r0 = move-exception
            r3 = r1
            r1 = r0
            r0 = r3
            goto L_0x0014
        L_0x0013:
            r1 = move-exception
        L_0x0014:
            io.netty.channel.ChannelHandlerContext r2 = r4.i
            r2.fireExceptionCaught(r1)
        L_0x0019:
            if (r0 == 0) goto L_0x0023
            r1 = 1
            r4.j = r1
            io.netty.channel.ChannelHandlerContext r1 = r4.i
            r1.fireChannelRead(r0)
        L_0x0023:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.handler.codec.spdy.SpdyFrameCodec.readHeaderBlockEnd():void");
    }

    @Override // io.netty.handler.codec.spdy.SpdyFrameDecoderDelegate
    public void readFrameError(String str) {
        this.i.fireExceptionCaught((Throwable) a);
    }
}
