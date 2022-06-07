package io.netty.handler.codec.http.cors;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

/* loaded from: classes4.dex */
public class CorsHandler extends ChannelDuplexHandler {
    private static final InternalLogger a = InternalLoggerFactory.getInstance(CorsHandler.class);
    private final CorsConfig b;
    private HttpRequest c;

    public CorsHandler(CorsConfig corsConfig) {
        this.b = (CorsConfig) ObjectUtil.checkNotNull(corsConfig, "config");
    }

    @Override // io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelInboundHandler
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object obj) throws Exception {
        if (this.b.isCorsSupportEnabled() && (obj instanceof HttpRequest)) {
            this.c = (HttpRequest) obj;
            if (a(this.c)) {
                a(channelHandlerContext, this.c);
                return;
            } else if (this.b.isShortCircuit() && !a()) {
                b(channelHandlerContext, this.c);
                return;
            }
        }
        channelHandlerContext.fireChannelRead(obj);
    }

    private void a(ChannelHandlerContext channelHandlerContext, HttpRequest httpRequest) {
        DefaultFullHttpResponse defaultFullHttpResponse = new DefaultFullHttpResponse(httpRequest.protocolVersion(), HttpResponseStatus.OK, true, true);
        if (b(defaultFullHttpResponse)) {
            i(defaultFullHttpResponse);
            j(defaultFullHttpResponse);
            g(defaultFullHttpResponse);
            k(defaultFullHttpResponse);
            a(defaultFullHttpResponse);
        }
        ReferenceCountUtil.release(httpRequest);
        channelHandlerContext.writeAndFlush(defaultFullHttpResponse).addListener((GenericFutureListener<? extends Future<? super Void>>) ChannelFutureListener.CLOSE);
    }

    private void a(HttpResponse httpResponse) {
        httpResponse.headers().add(this.b.preflightResponseHeaders());
    }

    private boolean b(HttpResponse httpResponse) {
        String str = this.c.headers().get(HttpHeaderNames.ORIGIN);
        if (str == null) {
            return false;
        }
        if ("null".equals(str) && this.b.isNullOriginAllowed()) {
            f(httpResponse);
            return true;
        } else if (this.b.isAnyOriginSupported()) {
            if (this.b.isCredentialsAllowed()) {
                c(httpResponse);
                d(httpResponse);
            } else {
                e(httpResponse);
            }
            return true;
        } else if (this.b.origins().contains(str)) {
            a(httpResponse, str);
            d(httpResponse);
            return true;
        } else {
            a.debug("Request origin [{}]] was not among the configured origins [{}]", str, this.b.origins());
            return false;
        }
    }

    private boolean a() {
        String str;
        if (this.b.isAnyOriginSupported() || (str = this.c.headers().get(HttpHeaderNames.ORIGIN)) == null) {
            return true;
        }
        if (!"null".equals(str) || !this.b.isNullOriginAllowed()) {
            return this.b.origins().contains(str);
        }
        return true;
    }

    private void c(HttpResponse httpResponse) {
        a(httpResponse, this.c.headers().get(HttpHeaderNames.ORIGIN));
    }

    private static void d(HttpResponse httpResponse) {
        httpResponse.headers().set(HttpHeaderNames.VARY, HttpHeaderNames.ORIGIN);
    }

    private static void e(HttpResponse httpResponse) {
        a(httpResponse, "*");
    }

    private static void f(HttpResponse httpResponse) {
        a(httpResponse, "null");
    }

    private static void a(HttpResponse httpResponse, String str) {
        httpResponse.headers().set(HttpHeaderNames.ACCESS_CONTROL_ALLOW_ORIGIN, str);
    }

    private void g(HttpResponse httpResponse) {
        if (this.b.isCredentialsAllowed() && !httpResponse.headers().get(HttpHeaderNames.ACCESS_CONTROL_ALLOW_ORIGIN).equals("*")) {
            httpResponse.headers().set(HttpHeaderNames.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
        }
    }

    private static boolean a(HttpRequest httpRequest) {
        HttpHeaders headers = httpRequest.headers();
        return httpRequest.method().equals(HttpMethod.OPTIONS) && headers.contains(HttpHeaderNames.ORIGIN) && headers.contains(HttpHeaderNames.ACCESS_CONTROL_REQUEST_METHOD);
    }

    private void h(HttpResponse httpResponse) {
        if (!this.b.exposedHeaders().isEmpty()) {
            httpResponse.headers().set((CharSequence) HttpHeaderNames.ACCESS_CONTROL_EXPOSE_HEADERS, (Iterable<?>) this.b.exposedHeaders());
        }
    }

    private void i(HttpResponse httpResponse) {
        httpResponse.headers().set((CharSequence) HttpHeaderNames.ACCESS_CONTROL_ALLOW_METHODS, (Iterable<?>) this.b.allowedRequestMethods());
    }

    private void j(HttpResponse httpResponse) {
        httpResponse.headers().set((CharSequence) HttpHeaderNames.ACCESS_CONTROL_ALLOW_HEADERS, (Iterable<?>) this.b.allowedRequestHeaders());
    }

    private void k(HttpResponse httpResponse) {
        httpResponse.headers().set(HttpHeaderNames.ACCESS_CONTROL_MAX_AGE, Long.valueOf(this.b.maxAge()));
    }

    @Override // io.netty.channel.ChannelDuplexHandler, io.netty.channel.ChannelOutboundHandler
    public void write(ChannelHandlerContext channelHandlerContext, Object obj, ChannelPromise channelPromise) throws Exception {
        if (this.b.isCorsSupportEnabled() && (obj instanceof HttpResponse)) {
            HttpResponse httpResponse = (HttpResponse) obj;
            if (b(httpResponse)) {
                g(httpResponse);
                j(httpResponse);
                h(httpResponse);
            }
        }
        channelHandlerContext.writeAndFlush(obj, channelPromise);
    }

    private static void b(ChannelHandlerContext channelHandlerContext, HttpRequest httpRequest) {
        channelHandlerContext.writeAndFlush(new DefaultFullHttpResponse(httpRequest.protocolVersion(), HttpResponseStatus.FORBIDDEN)).addListener((GenericFutureListener<? extends Future<? super Void>>) ChannelFutureListener.CLOSE);
        ReferenceCountUtil.release(httpRequest);
    }
}
