package io.netty.handler.codec.http.websocketx;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.HttpVersion;

/* loaded from: classes4.dex */
public class WebSocketServerHandshakerFactory {
    private final String a;
    private final String b;
    private final boolean c;
    private final int d;
    private final boolean e;

    public WebSocketServerHandshakerFactory(String str, String str2, boolean z) {
        this(str, str2, z, 65536);
    }

    public WebSocketServerHandshakerFactory(String str, String str2, boolean z, int i) {
        this(str, str2, z, i, false);
    }

    public WebSocketServerHandshakerFactory(String str, String str2, boolean z, int i, boolean z2) {
        this.a = str;
        this.b = str2;
        this.c = z;
        this.d = i;
        this.e = z2;
    }

    public WebSocketServerHandshaker newHandshaker(HttpRequest httpRequest) {
        String str = httpRequest.headers().get(HttpHeaderNames.SEC_WEBSOCKET_VERSION);
        if (str == null) {
            return new WebSocketServerHandshaker00(this.a, this.b, this.d);
        }
        if (str.equals(WebSocketVersion.V13.toHttpHeaderValue())) {
            return new WebSocketServerHandshaker13(this.a, this.b, this.c, this.d, this.e);
        }
        if (str.equals(WebSocketVersion.V08.toHttpHeaderValue())) {
            return new WebSocketServerHandshaker08(this.a, this.b, this.c, this.d, this.e);
        }
        if (str.equals(WebSocketVersion.V07.toHttpHeaderValue())) {
            return new WebSocketServerHandshaker07(this.a, this.b, this.c, this.d, this.e);
        }
        return null;
    }

    @Deprecated
    public static void sendUnsupportedWebSocketVersionResponse(Channel channel) {
        sendUnsupportedVersionResponse(channel);
    }

    public static ChannelFuture sendUnsupportedVersionResponse(Channel channel) {
        return sendUnsupportedVersionResponse(channel, channel.newPromise());
    }

    public static ChannelFuture sendUnsupportedVersionResponse(Channel channel, ChannelPromise channelPromise) {
        DefaultFullHttpResponse defaultFullHttpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.UPGRADE_REQUIRED);
        defaultFullHttpResponse.headers().set(HttpHeaderNames.SEC_WEBSOCKET_VERSION, WebSocketVersion.V13.toHttpHeaderValue());
        HttpUtil.setContentLength(defaultFullHttpResponse, 0L);
        return channel.writeAndFlush(defaultFullHttpResponse, channelPromise);
    }
}
