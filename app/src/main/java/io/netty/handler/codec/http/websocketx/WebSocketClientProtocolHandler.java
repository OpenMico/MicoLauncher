package io.netty.handler.codec.http.websocketx;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpHeaders;
import java.net.URI;
import java.util.List;

/* loaded from: classes4.dex */
public class WebSocketClientProtocolHandler extends c {
    private final WebSocketClientHandshaker a;
    private final boolean b;

    /* loaded from: classes4.dex */
    public enum ClientHandshakeStateEvent {
        HANDSHAKE_ISSUED,
        HANDSHAKE_COMPLETE
    }

    @Override // io.netty.handler.codec.http.websocketx.c, io.netty.handler.codec.MessageToMessageDecoder
    public /* bridge */ /* synthetic */ void decode(ChannelHandlerContext channelHandlerContext, WebSocketFrame webSocketFrame, List list) throws Exception {
        decode(channelHandlerContext, webSocketFrame, (List<Object>) list);
    }

    @Override // io.netty.handler.codec.http.websocketx.c, io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelHandlerAdapter, io.netty.channel.ChannelHandler, io.netty.channel.ChannelInboundHandler
    public /* bridge */ /* synthetic */ void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable th) throws Exception {
        super.exceptionCaught(channelHandlerContext, th);
    }

    public WebSocketClientHandshaker handshaker() {
        return this.a;
    }

    public WebSocketClientProtocolHandler(URI uri, WebSocketVersion webSocketVersion, String str, boolean z, HttpHeaders httpHeaders, int i, boolean z2, boolean z3, boolean z4) {
        this(WebSocketClientHandshakerFactory.newHandshaker(uri, webSocketVersion, str, z, httpHeaders, i, z3, z4), z2);
    }

    public WebSocketClientProtocolHandler(URI uri, WebSocketVersion webSocketVersion, String str, boolean z, HttpHeaders httpHeaders, int i, boolean z2) {
        this(uri, webSocketVersion, str, z, httpHeaders, i, z2, true, false);
    }

    public WebSocketClientProtocolHandler(URI uri, WebSocketVersion webSocketVersion, String str, boolean z, HttpHeaders httpHeaders, int i) {
        this(uri, webSocketVersion, str, z, httpHeaders, i, true);
    }

    public WebSocketClientProtocolHandler(WebSocketClientHandshaker webSocketClientHandshaker, boolean z) {
        this.a = webSocketClientHandshaker;
        this.b = z;
    }

    public WebSocketClientProtocolHandler(WebSocketClientHandshaker webSocketClientHandshaker) {
        this(webSocketClientHandshaker, true);
    }

    @Override // io.netty.handler.codec.http.websocketx.c
    public void decode(ChannelHandlerContext channelHandlerContext, WebSocketFrame webSocketFrame, List<Object> list) throws Exception {
        if (!this.b || !(webSocketFrame instanceof CloseWebSocketFrame)) {
            super.decode(channelHandlerContext, webSocketFrame, list);
        } else {
            channelHandlerContext.close();
        }
    }

    @Override // io.netty.channel.ChannelHandlerAdapter, io.netty.channel.ChannelHandler
    public void handlerAdded(ChannelHandlerContext channelHandlerContext) {
        ChannelPipeline pipeline = channelHandlerContext.pipeline();
        if (pipeline.get(b.class) == null) {
            channelHandlerContext.pipeline().addBefore(channelHandlerContext.name(), b.class.getName(), new b(this.a));
        }
        if (pipeline.get(Utf8FrameValidator.class) == null) {
            channelHandlerContext.pipeline().addBefore(channelHandlerContext.name(), Utf8FrameValidator.class.getName(), new Utf8FrameValidator());
        }
    }
}
