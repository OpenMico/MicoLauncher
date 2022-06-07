package io.netty.handler.codec.http.websocketx;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import java.util.regex.Pattern;

/* loaded from: classes4.dex */
public class WebSocketServerHandshaker00 extends WebSocketServerHandshaker {
    private static final Pattern a = Pattern.compile("[^0-9]");
    private static final Pattern b = Pattern.compile("[^ ]");

    public WebSocketServerHandshaker00(String str, String str2, int i) {
        super(WebSocketVersion.V00, str, str2, i);
    }

    @Override // io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker
    protected FullHttpResponse newHandshakeResponse(FullHttpRequest fullHttpRequest, HttpHeaders httpHeaders) {
        boolean z = true;
        if (!fullHttpRequest.headers().containsValue(HttpHeaderNames.CONNECTION, HttpHeaderValues.UPGRADE, true) || !HttpHeaderValues.WEBSOCKET.contentEqualsIgnoreCase(fullHttpRequest.headers().get(HttpHeaderNames.UPGRADE))) {
            throw new WebSocketHandshakeException("not a WebSocket handshake request: missing upgrade");
        }
        if (!fullHttpRequest.headers().contains(HttpHeaderNames.SEC_WEBSOCKET_KEY1) || !fullHttpRequest.headers().contains(HttpHeaderNames.SEC_WEBSOCKET_KEY2)) {
            z = false;
        }
        DefaultFullHttpResponse defaultFullHttpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, new HttpResponseStatus(101, z ? "WebSocket Protocol Handshake" : "Web Socket Protocol Handshake"));
        if (httpHeaders != null) {
            defaultFullHttpResponse.headers().add(httpHeaders);
        }
        defaultFullHttpResponse.headers().add(HttpHeaderNames.UPGRADE, HttpHeaderValues.WEBSOCKET);
        defaultFullHttpResponse.headers().add(HttpHeaderNames.CONNECTION, HttpHeaderValues.UPGRADE);
        if (z) {
            defaultFullHttpResponse.headers().add(HttpHeaderNames.SEC_WEBSOCKET_ORIGIN, fullHttpRequest.headers().get(HttpHeaderNames.ORIGIN));
            defaultFullHttpResponse.headers().add(HttpHeaderNames.SEC_WEBSOCKET_LOCATION, uri());
            String str = fullHttpRequest.headers().get(HttpHeaderNames.SEC_WEBSOCKET_PROTOCOL);
            if (str != null) {
                String selectSubprotocol = selectSubprotocol(str);
                if (selectSubprotocol != null) {
                    defaultFullHttpResponse.headers().add(HttpHeaderNames.SEC_WEBSOCKET_PROTOCOL, selectSubprotocol);
                } else if (logger.isDebugEnabled()) {
                    logger.debug("Requested subprotocol(s) not supported: {}", str);
                }
            }
            String str2 = fullHttpRequest.headers().get(HttpHeaderNames.SEC_WEBSOCKET_KEY1);
            String str3 = fullHttpRequest.headers().get(HttpHeaderNames.SEC_WEBSOCKET_KEY2);
            int parseLong = (int) (Long.parseLong(a.matcher(str2).replaceAll("")) / b.matcher(str2).replaceAll("").length());
            int parseLong2 = (int) (Long.parseLong(a.matcher(str3).replaceAll("")) / b.matcher(str3).replaceAll("").length());
            long readLong = fullHttpRequest.content().readLong();
            ByteBuf buffer = Unpooled.buffer(16);
            buffer.writeInt(parseLong);
            buffer.writeInt(parseLong2);
            buffer.writeLong(readLong);
            defaultFullHttpResponse.content().writeBytes(e.a(buffer.array()));
        } else {
            defaultFullHttpResponse.headers().add(HttpHeaderNames.WEBSOCKET_ORIGIN, fullHttpRequest.headers().get(HttpHeaderNames.ORIGIN));
            defaultFullHttpResponse.headers().add(HttpHeaderNames.WEBSOCKET_LOCATION, uri());
            String str4 = fullHttpRequest.headers().get(HttpHeaderNames.WEBSOCKET_PROTOCOL);
            if (str4 != null) {
                defaultFullHttpResponse.headers().add(HttpHeaderNames.WEBSOCKET_PROTOCOL, selectSubprotocol(str4));
            }
        }
        return defaultFullHttpResponse;
    }

    @Override // io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker
    public ChannelFuture close(Channel channel, CloseWebSocketFrame closeWebSocketFrame, ChannelPromise channelPromise) {
        return channel.writeAndFlush(closeWebSocketFrame, channelPromise);
    }

    @Override // io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker
    protected WebSocketFrameDecoder newWebsocketDecoder() {
        return new WebSocket00FrameDecoder(maxFramePayloadLength());
    }

    @Override // io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker
    protected WebSocketFrameEncoder newWebSocketEncoder() {
        return new WebSocket00FrameEncoder();
    }
}
