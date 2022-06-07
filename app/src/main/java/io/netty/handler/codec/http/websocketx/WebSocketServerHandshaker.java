package io.netty.handler.codec.http.websocketx;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelPromise;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.internal.EmptyArrays;
import io.netty.util.internal.StringUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.nio.channels.ClosedChannelException;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

/* loaded from: classes4.dex */
public abstract class WebSocketServerHandshaker {
    public static final String SUB_PROTOCOL_WILDCARD = "*";
    private final String b;
    private final String[] c;
    private final WebSocketVersion d;
    private final int e;
    private String f;
    protected static final InternalLogger logger = InternalLoggerFactory.getInstance(WebSocketServerHandshaker.class);
    private static final ClosedChannelException a = new ClosedChannelException();

    protected abstract FullHttpResponse newHandshakeResponse(FullHttpRequest fullHttpRequest, HttpHeaders httpHeaders);

    protected abstract WebSocketFrameEncoder newWebSocketEncoder();

    protected abstract WebSocketFrameDecoder newWebsocketDecoder();

    static {
        a.setStackTrace(EmptyArrays.EMPTY_STACK_TRACE);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public WebSocketServerHandshaker(WebSocketVersion webSocketVersion, String str, String str2, int i) {
        this.d = webSocketVersion;
        this.b = str;
        if (str2 != null) {
            String[] split = StringUtil.split(str2, StringUtil.COMMA);
            for (int i2 = 0; i2 < split.length; i2++) {
                split[i2] = split[i2].trim();
            }
            this.c = split;
        } else {
            this.c = EmptyArrays.EMPTY_STRINGS;
        }
        this.e = i;
    }

    public String uri() {
        return this.b;
    }

    public Set<String> subprotocols() {
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        Collections.addAll(linkedHashSet, this.c);
        return linkedHashSet;
    }

    public WebSocketVersion version() {
        return this.d;
    }

    public int maxFramePayloadLength() {
        return this.e;
    }

    public ChannelFuture handshake(Channel channel, FullHttpRequest fullHttpRequest) {
        return handshake(channel, fullHttpRequest, (HttpHeaders) null, channel.newPromise());
    }

    public final ChannelFuture handshake(Channel channel, FullHttpRequest fullHttpRequest, HttpHeaders httpHeaders, final ChannelPromise channelPromise) {
        final String str;
        if (logger.isDebugEnabled()) {
            logger.debug("{} WebSocket version {} server handshake", channel, version());
        }
        FullHttpResponse newHandshakeResponse = newHandshakeResponse(fullHttpRequest, httpHeaders);
        ChannelPipeline pipeline = channel.pipeline();
        if (pipeline.get(HttpObjectAggregator.class) != null) {
            pipeline.remove(HttpObjectAggregator.class);
        }
        if (pipeline.get(HttpContentCompressor.class) != null) {
            pipeline.remove(HttpContentCompressor.class);
        }
        ChannelHandlerContext context = pipeline.context(HttpRequestDecoder.class);
        if (context == null) {
            ChannelHandlerContext context2 = pipeline.context(HttpServerCodec.class);
            if (context2 == null) {
                channelPromise.setFailure((Throwable) new IllegalStateException("No HttpDecoder and no HttpServerCodec in the pipeline"));
                return channelPromise;
            }
            pipeline.addBefore(context2.name(), "wsdecoder", newWebsocketDecoder());
            pipeline.addBefore(context2.name(), "wsencoder", newWebSocketEncoder());
            str = context2.name();
        } else {
            pipeline.replace(context.name(), "wsdecoder", newWebsocketDecoder());
            String name = pipeline.context(HttpResponseEncoder.class).name();
            pipeline.addBefore(name, "wsencoder", newWebSocketEncoder());
            str = name;
        }
        channel.writeAndFlush(newHandshakeResponse).addListener((GenericFutureListener<? extends Future<? super Void>>) new ChannelFutureListener() { // from class: io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker.1
            /* renamed from: a */
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if (channelFuture.isSuccess()) {
                    channelFuture.channel().pipeline().remove(str);
                    channelPromise.setSuccess();
                    return;
                }
                channelPromise.setFailure(channelFuture.cause());
            }
        });
        return channelPromise;
    }

    public ChannelFuture handshake(Channel channel, HttpRequest httpRequest) {
        return handshake(channel, httpRequest, (HttpHeaders) null, channel.newPromise());
    }

    public final ChannelFuture handshake(final Channel channel, HttpRequest httpRequest, final HttpHeaders httpHeaders, final ChannelPromise channelPromise) {
        if (httpRequest instanceof FullHttpRequest) {
            return handshake(channel, (FullHttpRequest) httpRequest, httpHeaders, channelPromise);
        }
        if (logger.isDebugEnabled()) {
            logger.debug("{} WebSocket version {} server handshake", channel, version());
        }
        ChannelPipeline pipeline = channel.pipeline();
        ChannelHandlerContext context = pipeline.context(HttpRequestDecoder.class);
        if (context == null && (context = pipeline.context(HttpServerCodec.class)) == null) {
            channelPromise.setFailure((Throwable) new IllegalStateException("No HttpDecoder and no HttpServerCodec in the pipeline"));
            return channelPromise;
        }
        pipeline.addAfter(context.name(), "httpAggregator", new HttpObjectAggregator(8192));
        pipeline.addAfter("httpAggregator", "handshaker", new SimpleChannelInboundHandler<FullHttpRequest>() { // from class: io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker.2
            /* JADX INFO: Access modifiers changed from: protected */
            /* renamed from: a */
            public void channelRead0(ChannelHandlerContext channelHandlerContext, FullHttpRequest fullHttpRequest) throws Exception {
                channelHandlerContext.pipeline().remove(this);
                WebSocketServerHandshaker.this.handshake(channel, fullHttpRequest, httpHeaders, channelPromise);
            }

            @Override // io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelHandlerAdapter, io.netty.channel.ChannelHandler, io.netty.channel.ChannelInboundHandler
            public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable th) throws Exception {
                channelHandlerContext.pipeline().remove(this);
                channelPromise.tryFailure(th);
                channelHandlerContext.fireExceptionCaught(th);
            }

            @Override // io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelInboundHandler
            public void channelInactive(ChannelHandlerContext channelHandlerContext) throws Exception {
                channelPromise.tryFailure(WebSocketServerHandshaker.a);
                channelHandlerContext.fireChannelInactive();
            }
        });
        try {
            context.fireChannelRead(ReferenceCountUtil.retain(httpRequest));
        } catch (Throwable th) {
            channelPromise.setFailure(th);
        }
        return channelPromise;
    }

    public ChannelFuture close(Channel channel, CloseWebSocketFrame closeWebSocketFrame) {
        if (channel != null) {
            return close(channel, closeWebSocketFrame, channel.newPromise());
        }
        throw new NullPointerException("channel");
    }

    /* JADX WARN: Type inference failed for: r1v3, types: [io.netty.channel.ChannelFuture] */
    public ChannelFuture close(Channel channel, CloseWebSocketFrame closeWebSocketFrame, ChannelPromise channelPromise) {
        if (channel != null) {
            return channel.writeAndFlush(closeWebSocketFrame, channelPromise).addListener((GenericFutureListener<? extends Future<? super Void>>) ChannelFutureListener.CLOSE);
        }
        throw new NullPointerException("channel");
    }

    protected String selectSubprotocol(String str) {
        if (str == null || this.c.length == 0) {
            return null;
        }
        for (String str2 : StringUtil.split(str, StringUtil.COMMA)) {
            String trim = str2.trim();
            String[] strArr = this.c;
            for (String str3 : strArr) {
                if ("*".equals(str3) || trim.equals(str3)) {
                    this.f = trim;
                    return trim;
                }
            }
        }
        return null;
    }

    public String selectedSubprotocol() {
        return this.f;
    }
}
