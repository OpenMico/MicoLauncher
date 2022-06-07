package io.netty.handler.codec.http.websocketx.extensions.compression;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.CodecException;
import io.netty.handler.codec.compression.ZlibCodecFactory;
import io.netty.handler.codec.compression.ZlibWrapper;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.ContinuationWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.extensions.WebSocketExtensionEncoder;
import java.util.List;

/* compiled from: DeflateEncoder.java */
/* loaded from: classes4.dex */
abstract class b extends WebSocketExtensionEncoder {
    private final int a;
    private final int b;
    private final boolean c;
    private EmbeddedChannel d;

    protected abstract int a(WebSocketFrame webSocketFrame);

    protected abstract boolean b(WebSocketFrame webSocketFrame);

    public b(int i, int i2, boolean z) {
        this.a = i;
        this.b = i2;
        this.c = z;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* renamed from: a */
    public void encode(ChannelHandlerContext channelHandlerContext, WebSocketFrame webSocketFrame, List<Object> list) throws Exception {
        Object obj;
        if (this.d == null) {
            this.d = new EmbeddedChannel(ZlibCodecFactory.newZlibEncoder(ZlibWrapper.NONE, this.a, this.b, 8));
        }
        this.d.writeOutbound(webSocketFrame.content().retain());
        CompositeByteBuf compositeBuffer = channelHandlerContext.alloc().compositeBuffer();
        while (true) {
            ByteBuf byteBuf = (ByteBuf) this.d.readOutbound();
            if (byteBuf == null) {
                break;
            } else if (!byteBuf.isReadable()) {
                byteBuf.release();
            } else {
                compositeBuffer.addComponent(true, byteBuf);
            }
        }
        if (compositeBuffer.numComponents() > 0) {
            if (webSocketFrame.isFinalFragment() && this.c) {
                a();
            }
            ByteBuf byteBuf2 = compositeBuffer;
            if (b(webSocketFrame)) {
                byteBuf2 = compositeBuffer.slice(0, compositeBuffer.readableBytes() - e.a.length);
            }
            if (webSocketFrame instanceof TextWebSocketFrame) {
                obj = new TextWebSocketFrame(webSocketFrame.isFinalFragment(), a(webSocketFrame), byteBuf2);
            } else if (webSocketFrame instanceof BinaryWebSocketFrame) {
                obj = new BinaryWebSocketFrame(webSocketFrame.isFinalFragment(), a(webSocketFrame), byteBuf2);
            } else if (webSocketFrame instanceof ContinuationWebSocketFrame) {
                obj = new ContinuationWebSocketFrame(webSocketFrame.isFinalFragment(), a(webSocketFrame), byteBuf2);
            } else {
                throw new CodecException("unexpected frame type: " + webSocketFrame.getClass().getName());
            }
            list.add(obj);
            return;
        }
        compositeBuffer.release();
        throw new CodecException("cannot read compressed buffer");
    }

    @Override // io.netty.channel.ChannelHandlerAdapter, io.netty.channel.ChannelHandler
    public void handlerRemoved(ChannelHandlerContext channelHandlerContext) throws Exception {
        a();
        super.handlerRemoved(channelHandlerContext);
    }

    private void a() {
        EmbeddedChannel embeddedChannel = this.d;
        if (embeddedChannel != null) {
            if (embeddedChannel.finish()) {
                while (true) {
                    ByteBuf byteBuf = (ByteBuf) this.d.readOutbound();
                    if (byteBuf == null) {
                        break;
                    }
                    byteBuf.release();
                }
            }
            this.d = null;
        }
    }
}
