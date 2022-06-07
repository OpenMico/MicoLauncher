package io.netty.handler.codec.http.websocketx.extensions.compression;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.CodecException;
import io.netty.handler.codec.compression.ZlibCodecFactory;
import io.netty.handler.codec.compression.ZlibWrapper;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.ContinuationWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.extensions.WebSocketExtensionDecoder;
import java.util.List;

/* compiled from: DeflateDecoder.java */
/* loaded from: classes4.dex */
abstract class a extends WebSocketExtensionDecoder {
    static final byte[] a = {0, 0, -1, -1};
    private final boolean b;
    private EmbeddedChannel c;

    protected abstract boolean a(WebSocketFrame webSocketFrame);

    protected abstract int b(WebSocketFrame webSocketFrame);

    public a(boolean z) {
        this.b = z;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* renamed from: a */
    public void decode(ChannelHandlerContext channelHandlerContext, WebSocketFrame webSocketFrame, List<Object> list) throws Exception {
        Object obj;
        if (this.c == null) {
            if ((webSocketFrame instanceof TextWebSocketFrame) || (webSocketFrame instanceof BinaryWebSocketFrame)) {
                this.c = new EmbeddedChannel(ZlibCodecFactory.newZlibDecoder(ZlibWrapper.NONE));
            } else {
                throw new CodecException("unexpected initial frame type: " + webSocketFrame.getClass().getName());
            }
        }
        boolean isReadable = webSocketFrame.content().isReadable();
        this.c.writeInbound(webSocketFrame.content().retain());
        if (a(webSocketFrame)) {
            this.c.writeInbound(Unpooled.wrappedBuffer(a));
        }
        CompositeByteBuf compositeBuffer = channelHandlerContext.alloc().compositeBuffer();
        while (true) {
            ByteBuf byteBuf = (ByteBuf) this.c.readInbound();
            if (byteBuf == null) {
                break;
            } else if (!byteBuf.isReadable()) {
                byteBuf.release();
            } else {
                compositeBuffer.addComponent(true, byteBuf);
            }
        }
        if (!isReadable || compositeBuffer.numComponents() > 0) {
            if (webSocketFrame.isFinalFragment() && this.b) {
                a();
            }
            if (webSocketFrame instanceof TextWebSocketFrame) {
                obj = new TextWebSocketFrame(webSocketFrame.isFinalFragment(), b(webSocketFrame), compositeBuffer);
            } else if (webSocketFrame instanceof BinaryWebSocketFrame) {
                obj = new BinaryWebSocketFrame(webSocketFrame.isFinalFragment(), b(webSocketFrame), compositeBuffer);
            } else if (webSocketFrame instanceof ContinuationWebSocketFrame) {
                obj = new ContinuationWebSocketFrame(webSocketFrame.isFinalFragment(), b(webSocketFrame), compositeBuffer);
            } else {
                throw new CodecException("unexpected frame type: " + webSocketFrame.getClass().getName());
            }
            list.add(obj);
            return;
        }
        compositeBuffer.release();
        throw new CodecException("cannot read uncompressed buffer");
    }

    @Override // io.netty.channel.ChannelHandlerAdapter, io.netty.channel.ChannelHandler
    public void handlerRemoved(ChannelHandlerContext channelHandlerContext) throws Exception {
        a();
        super.handlerRemoved(channelHandlerContext);
    }

    @Override // io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelInboundHandler
    public void channelInactive(ChannelHandlerContext channelHandlerContext) throws Exception {
        a();
        super.channelInactive(channelHandlerContext);
    }

    private void a() {
        EmbeddedChannel embeddedChannel = this.c;
        if (embeddedChannel != null) {
            if (embeddedChannel.finish()) {
                while (true) {
                    ByteBuf byteBuf = (ByteBuf) this.c.readOutbound();
                    if (byteBuf == null) {
                        break;
                    }
                    byteBuf.release();
                }
            }
            this.c = null;
        }
    }
}
