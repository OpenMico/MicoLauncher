package io.netty.handler.codec.http.websocketx;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.ssl.SslHandler;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

/* compiled from: WebSocketServerProtocolHandshakeHandler.java */
/* loaded from: classes4.dex */
class d extends ChannelInboundHandlerAdapter {
    private final String a;
    private final String b;
    private final boolean c;
    private final int d;
    private final boolean e;

    /* JADX INFO: Access modifiers changed from: package-private */
    public d(String str, String str2, boolean z, int i, boolean z2) {
        this.a = str;
        this.b = str2;
        this.c = z;
        this.d = i;
        this.e = z2;
    }

    @Override // io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelInboundHandler
    public void channelRead(final ChannelHandlerContext channelHandlerContext, Object obj) throws Exception {
        FullHttpRequest fullHttpRequest = (FullHttpRequest) obj;
        if (!this.a.equals(fullHttpRequest.uri())) {
            channelHandlerContext.fireChannelRead(obj);
            return;
        }
        try {
            if (fullHttpRequest.method() != HttpMethod.GET) {
                a(channelHandlerContext, fullHttpRequest, new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.FORBIDDEN));
                return;
            }
            WebSocketServerHandshaker newHandshaker = new WebSocketServerHandshakerFactory(a(channelHandlerContext.pipeline(), fullHttpRequest, this.a), this.b, this.c, this.d, this.e).newHandshaker(fullHttpRequest);
            if (newHandshaker == null) {
                WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(channelHandlerContext.channel());
            } else {
                newHandshaker.handshake(channelHandlerContext.channel(), fullHttpRequest).addListener((GenericFutureListener<? extends Future<? super Void>>) new ChannelFutureListener() { // from class: io.netty.handler.codec.http.websocketx.d.1
                    /* renamed from: a */
                    public void operationComplete(ChannelFuture channelFuture) throws Exception {
                        if (!channelFuture.isSuccess()) {
                            channelHandlerContext.fireExceptionCaught(channelFuture.cause());
                        } else {
                            channelHandlerContext.fireUserEventTriggered((Object) WebSocketServerProtocolHandler.ServerHandshakeStateEvent.HANDSHAKE_COMPLETE);
                        }
                    }
                });
                WebSocketServerProtocolHandler.a(channelHandlerContext.channel(), newHandshaker);
                channelHandlerContext.pipeline().replace(this, "WS403Responder", WebSocketServerProtocolHandler.a());
            }
        } finally {
            fullHttpRequest.release();
        }
    }

    private static void a(ChannelHandlerContext channelHandlerContext, HttpRequest httpRequest, HttpResponse httpResponse) {
        ChannelFuture writeAndFlush = channelHandlerContext.channel().writeAndFlush(httpResponse);
        if (!HttpUtil.isKeepAlive(httpRequest) || httpResponse.status().code() != 200) {
            writeAndFlush.addListener((GenericFutureListener<? extends Future<? super Void>>) ChannelFutureListener.CLOSE);
        }
    }

    private static String a(ChannelPipeline channelPipeline, HttpRequest httpRequest, String str) {
        String str2 = "ws";
        if (channelPipeline.get(SslHandler.class) != null) {
            str2 = "wss";
        }
        return str2 + "://" + httpRequest.headers().get(HttpHeaderNames.HOST) + str;
    }
}
