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
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpContentDecompressor;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseDecoder;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.internal.EmptyArrays;
import io.netty.util.internal.OneTimeTask;
import io.netty.util.internal.StringUtil;
import java.net.URI;
import java.nio.channels.ClosedChannelException;

/* loaded from: classes4.dex */
public abstract class WebSocketClientHandshaker {
    private static final ClosedChannelException a = new ClosedChannelException();
    private final URI b;
    private final WebSocketVersion c;
    protected final HttpHeaders customHeaders;
    private volatile boolean d;
    private final String e;
    private volatile String f;
    private final int g;

    protected abstract FullHttpRequest newHandshakeRequest();

    protected abstract WebSocketFrameEncoder newWebSocketEncoder();

    protected abstract WebSocketFrameDecoder newWebsocketDecoder();

    protected abstract void verify(FullHttpResponse fullHttpResponse);

    static {
        a.setStackTrace(EmptyArrays.EMPTY_STACK_TRACE);
    }

    public WebSocketClientHandshaker(URI uri, WebSocketVersion webSocketVersion, String str, HttpHeaders httpHeaders, int i) {
        this.b = uri;
        this.c = webSocketVersion;
        this.e = str;
        this.customHeaders = httpHeaders;
        this.g = i;
    }

    public URI uri() {
        return this.b;
    }

    public WebSocketVersion version() {
        return this.c;
    }

    public int maxFramePayloadLength() {
        return this.g;
    }

    public boolean isHandshakeComplete() {
        return this.d;
    }

    private void b() {
        this.d = true;
    }

    public String expectedSubprotocol() {
        return this.e;
    }

    public String actualSubprotocol() {
        return this.f;
    }

    private void a(String str) {
        this.f = str;
    }

    public ChannelFuture handshake(Channel channel) {
        if (channel != null) {
            return handshake(channel, channel.newPromise());
        }
        throw new NullPointerException("channel");
    }

    public final ChannelFuture handshake(Channel channel, final ChannelPromise channelPromise) {
        FullHttpRequest newHandshakeRequest = newHandshakeRequest();
        if (((HttpResponseDecoder) channel.pipeline().get(HttpResponseDecoder.class)) == null && ((HttpClientCodec) channel.pipeline().get(HttpClientCodec.class)) == null) {
            channelPromise.setFailure((Throwable) new IllegalStateException("ChannelPipeline does not contain a HttpResponseDecoder or HttpClientCodec"));
            return channelPromise;
        }
        channel.writeAndFlush(newHandshakeRequest).addListener((GenericFutureListener<? extends Future<? super Void>>) new ChannelFutureListener() { // from class: io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker.1
            /* renamed from: a */
            public void operationComplete(ChannelFuture channelFuture) {
                if (channelFuture.isSuccess()) {
                    ChannelPipeline pipeline = channelFuture.channel().pipeline();
                    ChannelHandlerContext context = pipeline.context(HttpRequestEncoder.class);
                    if (context == null) {
                        context = pipeline.context(HttpClientCodec.class);
                    }
                    if (context == null) {
                        channelPromise.setFailure((Throwable) new IllegalStateException("ChannelPipeline does not contain a HttpRequestEncoder or HttpClientCodec"));
                        return;
                    }
                    pipeline.addAfter(context.name(), "ws-encoder", WebSocketClientHandshaker.this.newWebSocketEncoder());
                    channelPromise.setSuccess();
                    return;
                }
                channelPromise.setFailure(channelFuture.cause());
            }
        });
        return channelPromise;
    }

    public final void finishHandshake(Channel channel, FullHttpResponse fullHttpResponse) {
        boolean z;
        verify(fullHttpResponse);
        String str = fullHttpResponse.headers().get(HttpHeaderNames.SEC_WEBSOCKET_PROTOCOL);
        String trim = str != null ? str.trim() : null;
        String str2 = this.e;
        if (str2 == null) {
            str2 = "";
        }
        if (!str2.isEmpty() || trim != null) {
            if (!str2.isEmpty() && trim != null && !trim.isEmpty()) {
                for (String str3 : StringUtil.split(this.e, StringUtil.COMMA)) {
                    if (str3.trim().equals(trim)) {
                        a(trim);
                        z = true;
                        break;
                    }
                }
            }
            z = false;
        } else {
            a(this.e);
            z = true;
        }
        if (z) {
            b();
            final ChannelPipeline pipeline = channel.pipeline();
            HttpContentDecompressor httpContentDecompressor = (HttpContentDecompressor) pipeline.get(HttpContentDecompressor.class);
            if (httpContentDecompressor != null) {
                pipeline.remove(httpContentDecompressor);
            }
            HttpObjectAggregator httpObjectAggregator = (HttpObjectAggregator) pipeline.get(HttpObjectAggregator.class);
            if (httpObjectAggregator != null) {
                pipeline.remove(httpObjectAggregator);
            }
            final ChannelHandlerContext context = pipeline.context(HttpResponseDecoder.class);
            if (context == null) {
                ChannelHandlerContext context2 = pipeline.context(HttpClientCodec.class);
                if (context2 != null) {
                    final HttpClientCodec httpClientCodec = (HttpClientCodec) context2.handler();
                    httpClientCodec.removeOutboundHandler();
                    pipeline.addAfter(context2.name(), "ws-decoder", newWebsocketDecoder());
                    channel.eventLoop().execute(new OneTimeTask() { // from class: io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker.2
                        @Override // java.lang.Runnable
                        public void run() {
                            pipeline.remove(httpClientCodec);
                        }
                    });
                    return;
                }
                throw new IllegalStateException("ChannelPipeline does not contain a HttpRequestEncoder or HttpClientCodec");
            }
            if (pipeline.get(HttpRequestEncoder.class) != null) {
                pipeline.remove(HttpRequestEncoder.class);
            }
            pipeline.addAfter(context.name(), "ws-decoder", newWebsocketDecoder());
            channel.eventLoop().execute(new OneTimeTask() { // from class: io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker.3
                @Override // java.lang.Runnable
                public void run() {
                    pipeline.remove(context.handler());
                }
            });
            return;
        }
        throw new WebSocketHandshakeException(String.format("Invalid subprotocol. Actual: %s. Expected one of: %s", trim, this.e));
    }

    public final ChannelFuture processHandshake(Channel channel, HttpResponse httpResponse) {
        return processHandshake(channel, httpResponse, channel.newPromise());
    }

    public final ChannelFuture processHandshake(final Channel channel, HttpResponse httpResponse, final ChannelPromise channelPromise) {
        if (httpResponse instanceof FullHttpResponse) {
            try {
                finishHandshake(channel, (FullHttpResponse) httpResponse);
                channelPromise.setSuccess();
            } catch (Throwable th) {
                channelPromise.setFailure(th);
            }
        } else {
            ChannelPipeline pipeline = channel.pipeline();
            ChannelHandlerContext context = pipeline.context(HttpResponseDecoder.class);
            if (context == null && (context = pipeline.context(HttpClientCodec.class)) == null) {
                return channelPromise.setFailure((Throwable) new IllegalStateException("ChannelPipeline does not contain a HttpResponseDecoder or HttpClientCodec"));
            }
            pipeline.addAfter(context.name(), "httpAggregator", new HttpObjectAggregator(8192));
            pipeline.addAfter("httpAggregator", "handshaker", new SimpleChannelInboundHandler<FullHttpResponse>() { // from class: io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker.4
                /* renamed from: a */
                public void channelRead0(ChannelHandlerContext channelHandlerContext, FullHttpResponse fullHttpResponse) throws Exception {
                    channelHandlerContext.pipeline().remove(this);
                    try {
                        WebSocketClientHandshaker.this.finishHandshake(channel, fullHttpResponse);
                        channelPromise.setSuccess();
                    } catch (Throwable th2) {
                        channelPromise.setFailure(th2);
                    }
                }

                @Override // io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelHandlerAdapter, io.netty.channel.ChannelHandler, io.netty.channel.ChannelInboundHandler
                public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable th2) throws Exception {
                    channelHandlerContext.pipeline().remove(this);
                    channelPromise.setFailure(th2);
                }

                @Override // io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelInboundHandler
                public void channelInactive(ChannelHandlerContext channelHandlerContext) throws Exception {
                    channelPromise.tryFailure(WebSocketClientHandshaker.a);
                    channelHandlerContext.fireChannelInactive();
                }
            });
            try {
                context.fireChannelRead(ReferenceCountUtil.retain(httpResponse));
            } catch (Throwable th2) {
                channelPromise.setFailure(th2);
            }
        }
        return channelPromise;
    }

    public ChannelFuture close(Channel channel, CloseWebSocketFrame closeWebSocketFrame) {
        if (channel != null) {
            return close(channel, closeWebSocketFrame, channel.newPromise());
        }
        throw new NullPointerException("channel");
    }

    public ChannelFuture close(Channel channel, CloseWebSocketFrame closeWebSocketFrame, ChannelPromise channelPromise) {
        if (channel != null) {
            return channel.writeAndFlush(closeWebSocketFrame, channelPromise);
        }
        throw new NullPointerException("channel");
    }

    static String a(URI uri) {
        String rawPath = uri.getRawPath();
        String query = uri.getQuery();
        if (query != null && !query.isEmpty()) {
            rawPath = rawPath + '?' + query;
        }
        return (rawPath == null || rawPath.isEmpty()) ? "/" : rawPath;
    }
}
