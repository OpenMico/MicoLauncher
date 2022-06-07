package io.netty.handler.codec.http.websocketx;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.AsciiString;
import java.net.URI;
import java.nio.ByteBuffer;

/* loaded from: classes4.dex */
public class WebSocketClientHandshaker00 extends WebSocketClientHandshaker {
    private static final AsciiString a = new AsciiString(HttpHeaders.Values.WEBSOCKET);
    private ByteBuf b;

    public WebSocketClientHandshaker00(URI uri, WebSocketVersion webSocketVersion, String str, HttpHeaders httpHeaders, int i) {
        super(uri, webSocketVersion, str, httpHeaders, i);
    }

    @Override // io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker
    protected FullHttpRequest newHandshakeRequest() {
        int a2 = e.a(1, 12);
        int a3 = e.a(1, 12);
        int a4 = e.a(0, Integer.MAX_VALUE / a2);
        int a5 = e.a(0, Integer.MAX_VALUE / a3);
        String num = Integer.toString(a4 * a2);
        String num2 = Integer.toString(a5 * a3);
        String a6 = a(num);
        String a7 = a(num2);
        String a8 = a(a6, a2);
        String a9 = a(a7, a3);
        byte[] a10 = e.a(8);
        ByteBuffer allocate = ByteBuffer.allocate(4);
        allocate.putInt(a4);
        byte[] array = allocate.array();
        ByteBuffer allocate2 = ByteBuffer.allocate(4);
        allocate2.putInt(a5);
        byte[] array2 = allocate2.array();
        byte[] bArr = new byte[16];
        System.arraycopy(array, 0, bArr, 0, 4);
        System.arraycopy(array2, 0, bArr, 4, 4);
        System.arraycopy(a10, 0, bArr, 8, 8);
        this.b = Unpooled.wrappedBuffer(e.a(bArr));
        URI uri = uri();
        DefaultFullHttpRequest defaultFullHttpRequest = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, a(uri));
        HttpHeaders headers = defaultFullHttpRequest.headers();
        headers.add(HttpHeaderNames.UPGRADE, a).add(HttpHeaderNames.CONNECTION, HttpHeaderValues.UPGRADE).add(HttpHeaderNames.HOST, uri.getHost());
        int port = uri.getPort();
        String str = "http://" + uri.getHost();
        if (!(port == 80 || port == 443)) {
            str = str + ':' + port;
        }
        headers.add(HttpHeaderNames.ORIGIN, str).add(HttpHeaderNames.SEC_WEBSOCKET_KEY1, a8).add(HttpHeaderNames.SEC_WEBSOCKET_KEY2, a9);
        String expectedSubprotocol = expectedSubprotocol();
        if (expectedSubprotocol != null && !expectedSubprotocol.isEmpty()) {
            headers.add(HttpHeaderNames.SEC_WEBSOCKET_PROTOCOL, expectedSubprotocol);
        }
        if (this.customHeaders != null) {
            headers.add(this.customHeaders);
        }
        headers.set(HttpHeaderNames.CONTENT_LENGTH, Integer.valueOf(a10.length));
        defaultFullHttpRequest.content().writeBytes(a10);
        return defaultFullHttpRequest;
    }

    @Override // io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker
    protected void verify(FullHttpResponse fullHttpResponse) {
        if (fullHttpResponse.status().equals(new HttpResponseStatus(101, "WebSocket Protocol Handshake"))) {
            HttpHeaders headers = fullHttpResponse.headers();
            String str = headers.get(HttpHeaderNames.UPGRADE);
            if (!a.contentEqualsIgnoreCase(str)) {
                throw new WebSocketHandshakeException("Invalid handshake response upgrade: " + ((Object) str));
            } else if (!headers.containsValue(HttpHeaderNames.CONNECTION, HttpHeaderValues.UPGRADE, true)) {
                throw new WebSocketHandshakeException("Invalid handshake response connection: " + headers.get(HttpHeaderNames.CONNECTION));
            } else if (!fullHttpResponse.content().equals(this.b)) {
                throw new WebSocketHandshakeException("Invalid challenge");
            }
        } else {
            throw new WebSocketHandshakeException("Invalid handshake response getStatus: " + fullHttpResponse.status());
        }
    }

    private static String a(String str) {
        int a2 = e.a(1, 12);
        char[] cArr = new char[a2];
        int i = 0;
        while (i < a2) {
            int random = (int) ((Math.random() * 126.0d) + 33.0d);
            if ((33 < random && random < 47) || (58 < random && random < 126)) {
                cArr[i] = (char) random;
                i++;
            }
        }
        String str2 = str;
        for (int i2 = 0; i2 < a2; i2++) {
            int a3 = e.a(0, str2.length());
            str2 = str2.substring(0, a3) + cArr[i2] + str2.substring(a3);
        }
        return str2;
    }

    private static String a(String str, int i) {
        String str2 = str;
        for (int i2 = 0; i2 < i; i2++) {
            int a2 = e.a(1, str2.length() - 1);
            str2 = str2.substring(0, a2) + ' ' + str2.substring(a2);
        }
        return str2;
    }

    @Override // io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker
    protected WebSocketFrameDecoder newWebsocketDecoder() {
        return new WebSocket00FrameDecoder(maxFramePayloadLength());
    }

    @Override // io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker
    protected WebSocketFrameEncoder newWebSocketEncoder() {
        return new WebSocket00FrameEncoder();
    }
}
