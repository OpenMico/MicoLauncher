package io.netty.handler.proxy;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.base64.Base64;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.util.AsciiString;
import io.netty.util.CharsetUtil;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

/* loaded from: classes4.dex */
public final class HttpProxyHandler extends ProxyHandler {
    private final HttpClientCodec a;
    private final String b;
    private final String c;
    private final CharSequence d;
    private HttpResponseStatus e;

    @Override // io.netty.handler.proxy.ProxyHandler
    public String protocol() {
        return "http";
    }

    public HttpProxyHandler(SocketAddress socketAddress) {
        super(socketAddress);
        this.a = new HttpClientCodec();
        this.b = null;
        this.c = null;
        this.d = null;
    }

    public HttpProxyHandler(SocketAddress socketAddress, String str, String str2) {
        super(socketAddress);
        this.a = new HttpClientCodec();
        if (str == null) {
            throw new NullPointerException("username");
        } else if (str2 != null) {
            this.b = str;
            this.c = str2;
            ByteBuf copiedBuffer = Unpooled.copiedBuffer(str + ':' + str2, CharsetUtil.UTF_8);
            ByteBuf encode = Base64.encode(copiedBuffer, false);
            this.d = new AsciiString("Basic " + encode.toString(CharsetUtil.US_ASCII));
            copiedBuffer.release();
            encode.release();
        } else {
            throw new NullPointerException("password");
        }
    }

    @Override // io.netty.handler.proxy.ProxyHandler
    public String authScheme() {
        return this.d != null ? "basic" : "none";
    }

    public String username() {
        return this.b;
    }

    public String password() {
        return this.c;
    }

    @Override // io.netty.handler.proxy.ProxyHandler
    protected void addCodec(ChannelHandlerContext channelHandlerContext) throws Exception {
        channelHandlerContext.pipeline().addBefore(channelHandlerContext.name(), null, this.a);
    }

    @Override // io.netty.handler.proxy.ProxyHandler
    protected void removeEncoder(ChannelHandlerContext channelHandlerContext) throws Exception {
        this.a.removeOutboundHandler();
    }

    @Override // io.netty.handler.proxy.ProxyHandler
    protected void removeDecoder(ChannelHandlerContext channelHandlerContext) throws Exception {
        this.a.removeInboundHandler();
    }

    @Override // io.netty.handler.proxy.ProxyHandler
    protected Object newInitialMessage(ChannelHandlerContext channelHandlerContext) throws Exception {
        String str;
        InetSocketAddress inetSocketAddress = (InetSocketAddress) destinationAddress();
        if (inetSocketAddress.isUnresolved()) {
            str = inetSocketAddress.getHostString();
        } else {
            str = inetSocketAddress.getAddress().getHostAddress();
        }
        HttpVersion httpVersion = HttpVersion.HTTP_1_0;
        HttpMethod httpMethod = HttpMethod.CONNECT;
        DefaultFullHttpRequest defaultFullHttpRequest = new DefaultFullHttpRequest(httpVersion, httpMethod, str + ':' + inetSocketAddress.getPort(), Unpooled.EMPTY_BUFFER, false);
        SocketAddress proxyAddress = proxyAddress();
        if (proxyAddress instanceof InetSocketAddress) {
            InetSocketAddress inetSocketAddress2 = (InetSocketAddress) proxyAddress;
            HttpHeaders headers = defaultFullHttpRequest.headers();
            AsciiString asciiString = HttpHeaderNames.HOST;
            headers.set(asciiString, inetSocketAddress2.getHostString() + ':' + inetSocketAddress2.getPort());
        }
        if (this.d != null) {
            defaultFullHttpRequest.headers().set(HttpHeaderNames.PROXY_AUTHORIZATION, this.d);
        }
        return defaultFullHttpRequest;
    }

    @Override // io.netty.handler.proxy.ProxyHandler
    protected boolean handleResponse(ChannelHandlerContext channelHandlerContext, Object obj) throws Exception {
        if (obj instanceof HttpResponse) {
            if (this.e == null) {
                this.e = ((HttpResponse) obj).status();
            } else {
                throw new ProxyConnectException(exceptionMessage("too many responses"));
            }
        }
        boolean z = obj instanceof LastHttpContent;
        if (z) {
            HttpResponseStatus httpResponseStatus = this.e;
            if (httpResponseStatus == null) {
                throw new ProxyConnectException(exceptionMessage("missing response"));
            } else if (httpResponseStatus.code() != 200) {
                throw new ProxyConnectException(exceptionMessage("status: " + this.e));
            }
        }
        return z;
    }
}
