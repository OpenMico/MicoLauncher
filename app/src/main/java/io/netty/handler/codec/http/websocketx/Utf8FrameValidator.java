package io.netty.handler.codec.http.websocketx;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.CorruptedFrameException;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

/* loaded from: classes4.dex */
public class Utf8FrameValidator extends ChannelInboundHandlerAdapter {
    private int a;
    private a b;

    @Override // io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelInboundHandler
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object obj) throws Exception {
        a aVar;
        if (obj instanceof WebSocketFrame) {
            WebSocketFrame webSocketFrame = (WebSocketFrame) obj;
            if (!webSocketFrame.isFinalFragment()) {
                if (this.a != 0) {
                    a aVar2 = this.b;
                    if (aVar2 != null && aVar2.b()) {
                        a(channelHandlerContext, webSocketFrame.content());
                    }
                } else if (webSocketFrame instanceof TextWebSocketFrame) {
                    a(channelHandlerContext, webSocketFrame.content());
                }
                this.a++;
            } else if (!(webSocketFrame instanceof PingWebSocketFrame)) {
                this.a = 0;
                if ((webSocketFrame instanceof TextWebSocketFrame) || ((aVar = this.b) != null && aVar.b())) {
                    a(channelHandlerContext, webSocketFrame.content());
                    this.b.a();
                }
            }
        }
        super.channelRead(channelHandlerContext, obj);
    }

    private void a(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) {
        try {
            if (this.b == null) {
                this.b = new a();
            }
            this.b.a(byteBuf);
        } catch (CorruptedFrameException unused) {
            if (channelHandlerContext.channel().isActive()) {
                channelHandlerContext.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener((GenericFutureListener<? extends Future<? super Void>>) ChannelFutureListener.CLOSE);
            }
        }
    }
}
