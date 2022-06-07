package io.netty.handler.codec.http.websocketx;

import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import java.util.List;

/* loaded from: classes4.dex */
public class WebSocketServerProtocolHandler extends c {
    private static final AttributeKey<WebSocketServerHandshaker> a = AttributeKey.valueOf(WebSocketServerHandshaker.class, "HANDSHAKER");
    private final String b;
    private final String c;
    private final boolean d;
    private final int e;
    private final boolean f;

    /* loaded from: classes4.dex */
    public enum ServerHandshakeStateEvent {
        HANDSHAKE_COMPLETE
    }

    @Override // io.netty.handler.codec.http.websocketx.c, io.netty.handler.codec.MessageToMessageDecoder
    public /* bridge */ /* synthetic */ void decode(ChannelHandlerContext channelHandlerContext, WebSocketFrame webSocketFrame, List list) throws Exception {
        decode(channelHandlerContext, webSocketFrame, (List<Object>) list);
    }

    public WebSocketServerProtocolHandler(String str) {
        this(str, null, false);
    }

    public WebSocketServerProtocolHandler(String str, String str2) {
        this(str, str2, false);
    }

    public WebSocketServerProtocolHandler(String str, String str2, boolean z) {
        this(str, str2, z, 65536);
    }

    public WebSocketServerProtocolHandler(String str, String str2, boolean z, int i) {
        this(str, str2, z, i, false);
    }

    public WebSocketServerProtocolHandler(String str, String str2, boolean z, int i, boolean z2) {
        this.b = str;
        this.c = str2;
        this.d = z;
        this.e = i;
        this.f = z2;
    }

    @Override // io.netty.channel.ChannelHandlerAdapter, io.netty.channel.ChannelHandler
    public void handlerAdded(ChannelHandlerContext channelHandlerContext) {
        ChannelPipeline pipeline = channelHandlerContext.pipeline();
        if (pipeline.get(d.class) == null) {
            channelHandlerContext.pipeline().addBefore(channelHandlerContext.name(), d.class.getName(), new d(this.b, this.c, this.d, this.e, this.f));
        }
        if (pipeline.get(Utf8FrameValidator.class) == null) {
            channelHandlerContext.pipeline().addBefore(channelHandlerContext.name(), Utf8FrameValidator.class.getName(), new Utf8FrameValidator());
        }
    }

    @Override // io.netty.handler.codec.http.websocketx.c
    public void decode(ChannelHandlerContext channelHandlerContext, WebSocketFrame webSocketFrame, List<Object> list) throws Exception {
        if (webSocketFrame instanceof CloseWebSocketFrame) {
            WebSocketServerHandshaker a2 = a(channelHandlerContext.channel());
            if (a2 != null) {
                webSocketFrame.retain();
                a2.close(channelHandlerContext.channel(), (CloseWebSocketFrame) webSocketFrame);
                return;
            }
            channelHandlerContext.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener((GenericFutureListener<? extends Future<? super Void>>) ChannelFutureListener.CLOSE);
            return;
        }
        super.decode(channelHandlerContext, webSocketFrame, list);
    }

    @Override // io.netty.handler.codec.http.websocketx.c, io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelHandlerAdapter, io.netty.channel.ChannelHandler, io.netty.channel.ChannelInboundHandler
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable th) throws Exception {
        if (th instanceof WebSocketHandshakeException) {
            channelHandlerContext.channel().writeAndFlush(new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST, Unpooled.wrappedBuffer(th.getMessage().getBytes()))).addListener((GenericFutureListener<? extends Future<? super Void>>) ChannelFutureListener.CLOSE);
            return;
        }
        channelHandlerContext.close();
    }

    static WebSocketServerHandshaker a(Channel channel) {
        return (WebSocketServerHandshaker) channel.attr(a).get();
    }

    public static void a(Channel channel, WebSocketServerHandshaker webSocketServerHandshaker) {
        channel.attr(a).set(webSocketServerHandshaker);
    }

    public static ChannelHandler a() {
        return new ChannelInboundHandlerAdapter() { // from class: io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler.1
            @Override // io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelInboundHandler
            public void channelRead(ChannelHandlerContext channelHandlerContext, Object obj) throws Exception {
                if (obj instanceof FullHttpRequest) {
                    ((FullHttpRequest) obj).release();
                    channelHandlerContext.channel().writeAndFlush(new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.FORBIDDEN));
                    return;
                }
                channelHandlerContext.fireChannelRead(obj);
            }
        };
    }
}
