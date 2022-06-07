package io.netty.handler.codec.http.websocketx;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.websocketx.WebSocketClientProtocolHandler;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

/* compiled from: WebSocketClientProtocolHandshakeHandler.java */
/* loaded from: classes4.dex */
class b extends ChannelInboundHandlerAdapter {
    private final WebSocketClientHandshaker a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public b(WebSocketClientHandshaker webSocketClientHandshaker) {
        this.a = webSocketClientHandshaker;
    }

    @Override // io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelInboundHandler
    public void channelActive(final ChannelHandlerContext channelHandlerContext) throws Exception {
        super.channelActive(channelHandlerContext);
        this.a.handshake(channelHandlerContext.channel()).addListener((GenericFutureListener<? extends Future<? super Void>>) new ChannelFutureListener() { // from class: io.netty.handler.codec.http.websocketx.b.1
            /* renamed from: a */
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if (!channelFuture.isSuccess()) {
                    channelHandlerContext.fireExceptionCaught(channelFuture.cause());
                } else {
                    channelHandlerContext.fireUserEventTriggered((Object) WebSocketClientProtocolHandler.ClientHandshakeStateEvent.HANDSHAKE_ISSUED);
                }
            }
        });
    }

    @Override // io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelInboundHandler
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object obj) throws Exception {
        if (!(obj instanceof FullHttpResponse)) {
            channelHandlerContext.fireChannelRead(obj);
            return;
        }
        FullHttpResponse fullHttpResponse = (FullHttpResponse) obj;
        try {
            if (!this.a.isHandshakeComplete()) {
                this.a.finishHandshake(channelHandlerContext.channel(), fullHttpResponse);
                channelHandlerContext.fireUserEventTriggered((Object) WebSocketClientProtocolHandler.ClientHandshakeStateEvent.HANDSHAKE_COMPLETE);
                channelHandlerContext.pipeline().remove(this);
                return;
            }
            throw new IllegalStateException("WebSocketClientHandshaker should have been non finished yet");
        } finally {
            fullHttpResponse.release();
        }
    }
}
