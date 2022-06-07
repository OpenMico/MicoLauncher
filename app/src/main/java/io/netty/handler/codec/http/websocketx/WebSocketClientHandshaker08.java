package io.netty.handler.codec.http.websocketx;

import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.net.URI;

/* loaded from: classes4.dex */
public class WebSocketClientHandshaker08 extends WebSocketClientHandshaker {
    public static final String MAGIC_GUID = "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";
    private static final InternalLogger a = InternalLoggerFactory.getInstance(WebSocketClientHandshaker08.class);
    private String b;
    private final boolean c;
    private final boolean d;
    private final boolean e;

    public WebSocketClientHandshaker08(URI uri, WebSocketVersion webSocketVersion, String str, boolean z, HttpHeaders httpHeaders, int i) {
        this(uri, webSocketVersion, str, z, httpHeaders, i, true, false);
    }

    public WebSocketClientHandshaker08(URI uri, WebSocketVersion webSocketVersion, String str, boolean z, HttpHeaders httpHeaders, int i, boolean z2, boolean z3) {
        super(uri, webSocketVersion, str, httpHeaders, i);
        this.c = z;
        this.d = z2;
        this.e = z3;
    }

    @Override // io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker
    protected FullHttpRequest newHandshakeRequest() {
        URI uri = uri();
        String a2 = a(uri);
        String c = e.c(e.a(16));
        this.b = e.c(e.b((c + "258EAFA5-E914-47DA-95CA-C5AB0DC85B11").getBytes(CharsetUtil.US_ASCII)));
        if (a.isDebugEnabled()) {
            a.debug("WebSocket version 08 client handshake key: {}, expected response: {}", c, this.b);
        }
        DefaultFullHttpRequest defaultFullHttpRequest = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, a2);
        HttpHeaders headers = defaultFullHttpRequest.headers();
        headers.add(HttpHeaderNames.UPGRADE, HttpHeaderValues.WEBSOCKET).add(HttpHeaderNames.CONNECTION, HttpHeaderValues.UPGRADE).add(HttpHeaderNames.SEC_WEBSOCKET_KEY, c).add(HttpHeaderNames.HOST, uri.getHost());
        int port = uri.getPort();
        String str = "http://" + uri.getHost();
        if (!(port == 80 || port == 443)) {
            str = str + ':' + port;
        }
        headers.add(HttpHeaderNames.SEC_WEBSOCKET_ORIGIN, str);
        String expectedSubprotocol = expectedSubprotocol();
        if (expectedSubprotocol != null && !expectedSubprotocol.isEmpty()) {
            headers.add(HttpHeaderNames.SEC_WEBSOCKET_PROTOCOL, expectedSubprotocol);
        }
        headers.add(HttpHeaderNames.SEC_WEBSOCKET_VERSION, "8");
        if (this.customHeaders != null) {
            headers.add(this.customHeaders);
        }
        return defaultFullHttpRequest;
    }

    @Override // io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker
    protected void verify(FullHttpResponse fullHttpResponse) {
        HttpResponseStatus httpResponseStatus = HttpResponseStatus.SWITCHING_PROTOCOLS;
        HttpHeaders headers = fullHttpResponse.headers();
        if (fullHttpResponse.status().equals(httpResponseStatus)) {
            String str = headers.get(HttpHeaderNames.UPGRADE);
            if (!HttpHeaderValues.WEBSOCKET.contentEqualsIgnoreCase(str)) {
                throw new WebSocketHandshakeException("Invalid handshake response upgrade: " + ((Object) str));
            } else if (headers.containsValue(HttpHeaderNames.CONNECTION, HttpHeaderValues.UPGRADE, true)) {
                String str2 = headers.get(HttpHeaderNames.SEC_WEBSOCKET_ACCEPT);
                if (str2 == null || !str2.equals(this.b)) {
                    throw new WebSocketHandshakeException(String.format("Invalid challenge. Actual: %s. Expected: %s", str2, this.b));
                }
            } else {
                throw new WebSocketHandshakeException("Invalid handshake response connection: " + headers.get(HttpHeaderNames.CONNECTION));
            }
        } else {
            throw new WebSocketHandshakeException("Invalid handshake response getStatus: " + fullHttpResponse.status());
        }
    }

    @Override // io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker
    protected WebSocketFrameDecoder newWebsocketDecoder() {
        return new WebSocket08FrameDecoder(false, this.c, maxFramePayloadLength(), this.e);
    }

    @Override // io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker
    protected WebSocketFrameEncoder newWebSocketEncoder() {
        return new WebSocket08FrameEncoder(this.d);
    }
}
