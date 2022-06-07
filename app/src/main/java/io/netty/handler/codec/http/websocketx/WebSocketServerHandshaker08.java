package io.netty.handler.codec.http.websocketx;

import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;

/* loaded from: classes4.dex */
public class WebSocketServerHandshaker08 extends WebSocketServerHandshaker {
    public static final String WEBSOCKET_08_ACCEPT_GUID = "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";
    private final boolean a;
    private final boolean b;

    public WebSocketServerHandshaker08(String str, String str2, boolean z, int i) {
        this(str, str2, z, i, false);
    }

    public WebSocketServerHandshaker08(String str, String str2, boolean z, int i, boolean z2) {
        super(WebSocketVersion.V08, str, str2, i);
        this.a = z;
        this.b = z2;
    }

    @Override // io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker
    protected FullHttpResponse newHandshakeResponse(FullHttpRequest fullHttpRequest, HttpHeaders httpHeaders) {
        DefaultFullHttpResponse defaultFullHttpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.SWITCHING_PROTOCOLS);
        if (httpHeaders != null) {
            defaultFullHttpResponse.headers().add(httpHeaders);
        }
        String str = fullHttpRequest.headers().get(HttpHeaderNames.SEC_WEBSOCKET_KEY);
        if (str != null) {
            String c = e.c(e.b((((Object) str) + "258EAFA5-E914-47DA-95CA-C5AB0DC85B11").getBytes(CharsetUtil.US_ASCII)));
            if (logger.isDebugEnabled()) {
                logger.debug("WebSocket version 08 server handshake key: {}, response: {}", str, c);
            }
            defaultFullHttpResponse.headers().add(HttpHeaderNames.UPGRADE, HttpHeaderValues.WEBSOCKET);
            defaultFullHttpResponse.headers().add(HttpHeaderNames.CONNECTION, HttpHeaderValues.UPGRADE);
            defaultFullHttpResponse.headers().add(HttpHeaderNames.SEC_WEBSOCKET_ACCEPT, c);
            String str2 = fullHttpRequest.headers().get(HttpHeaderNames.SEC_WEBSOCKET_PROTOCOL);
            if (str2 != null) {
                String selectSubprotocol = selectSubprotocol(str2);
                if (selectSubprotocol != null) {
                    defaultFullHttpResponse.headers().add(HttpHeaderNames.SEC_WEBSOCKET_PROTOCOL, selectSubprotocol);
                } else if (logger.isDebugEnabled()) {
                    logger.debug("Requested subprotocol(s) not supported: {}", str2);
                }
            }
            return defaultFullHttpResponse;
        }
        throw new WebSocketHandshakeException("not a WebSocket request: missing key");
    }

    @Override // io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker
    protected WebSocketFrameDecoder newWebsocketDecoder() {
        return new WebSocket08FrameDecoder(true, this.a, maxFramePayloadLength(), this.b);
    }

    @Override // io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker
    protected WebSocketFrameEncoder newWebSocketEncoder() {
        return new WebSocket08FrameEncoder(false);
    }
}
