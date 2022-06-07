package io.netty.handler.codec.http.websocketx.extensions;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public class WebSocketServerExtensionHandler extends ChannelDuplexHandler {
    private final List<WebSocketServerExtensionHandshaker> a;
    private List<WebSocketServerExtension> b;

    public WebSocketServerExtensionHandler(WebSocketServerExtensionHandshaker... webSocketServerExtensionHandshakerArr) {
        if (webSocketServerExtensionHandshakerArr == null) {
            throw new NullPointerException("extensionHandshakers");
        } else if (webSocketServerExtensionHandshakerArr.length != 0) {
            this.a = Arrays.asList(webSocketServerExtensionHandshakerArr);
        } else {
            throw new IllegalArgumentException("extensionHandshakers must contains at least one handshaker");
        }
    }

    @Override // io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelInboundHandler
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object obj) throws Exception {
        String asString;
        if (obj instanceof HttpRequest) {
            HttpRequest httpRequest = (HttpRequest) obj;
            if (WebSocketExtensionUtil.a(httpRequest.headers()) && (asString = httpRequest.headers().getAsString(HttpHeaderNames.SEC_WEBSOCKET_EXTENSIONS)) != null) {
                int i = 0;
                for (WebSocketExtensionData webSocketExtensionData : WebSocketExtensionUtil.extractExtensions(asString)) {
                    Iterator<WebSocketServerExtensionHandshaker> it = this.a.iterator();
                    WebSocketServerExtension webSocketServerExtension = null;
                    while (webSocketServerExtension == null && it.hasNext()) {
                        webSocketServerExtension = it.next().handshakeExtension(webSocketExtensionData);
                    }
                    if (webSocketServerExtension != null && (webSocketServerExtension.rsv() & i) == 0) {
                        if (this.b == null) {
                            this.b = new ArrayList(1);
                        }
                        i |= webSocketServerExtension.rsv();
                        this.b.add(webSocketServerExtension);
                    }
                }
            }
        }
        super.channelRead(channelHandlerContext, obj);
    }

    @Override // io.netty.channel.ChannelDuplexHandler, io.netty.channel.ChannelOutboundHandler
    public void write(final ChannelHandlerContext channelHandlerContext, Object obj, ChannelPromise channelPromise) throws Exception {
        if (obj instanceof HttpResponse) {
            HttpResponse httpResponse = (HttpResponse) obj;
            if (WebSocketExtensionUtil.a(httpResponse.headers()) && this.b != null) {
                String asString = httpResponse.headers().getAsString(HttpHeaderNames.SEC_WEBSOCKET_EXTENSIONS);
                for (WebSocketServerExtension webSocketServerExtension : this.b) {
                    WebSocketExtensionData newReponseData = webSocketServerExtension.newReponseData();
                    asString = WebSocketExtensionUtil.a(asString, newReponseData.name(), newReponseData.parameters());
                }
                channelPromise.addListener((GenericFutureListener<? extends Future<? super Void>>) new ChannelFutureListener() { // from class: io.netty.handler.codec.http.websocketx.extensions.WebSocketServerExtensionHandler.1
                    /* renamed from: a */
                    public void operationComplete(ChannelFuture channelFuture) throws Exception {
                        if (channelFuture.isSuccess()) {
                            for (WebSocketServerExtension webSocketServerExtension2 : WebSocketServerExtensionHandler.this.b) {
                                WebSocketExtensionDecoder newExtensionDecoder = webSocketServerExtension2.newExtensionDecoder();
                                WebSocketExtensionEncoder newExtensionEncoder = webSocketServerExtension2.newExtensionEncoder();
                                channelHandlerContext.pipeline().addAfter(channelHandlerContext.name(), newExtensionDecoder.getClass().getName(), newExtensionDecoder);
                                channelHandlerContext.pipeline().addAfter(channelHandlerContext.name(), newExtensionEncoder.getClass().getName(), newExtensionEncoder);
                            }
                        }
                        channelHandlerContext.pipeline().remove(channelHandlerContext.name());
                    }
                });
                if (asString != null) {
                    httpResponse.headers().set(HttpHeaderNames.SEC_WEBSOCKET_EXTENSIONS, asString);
                }
            }
        }
        super.write(channelHandlerContext, obj, channelPromise);
    }
}
