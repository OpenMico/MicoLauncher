package io.netty.handler.codec.http.websocketx;

import io.netty.handler.codec.http.HttpHeaders;
import java.net.URI;

/* loaded from: classes4.dex */
public final class WebSocketClientHandshakerFactory {
    private WebSocketClientHandshakerFactory() {
    }

    public static WebSocketClientHandshaker newHandshaker(URI uri, WebSocketVersion webSocketVersion, String str, boolean z, HttpHeaders httpHeaders) {
        return newHandshaker(uri, webSocketVersion, str, z, httpHeaders, 65536);
    }

    public static WebSocketClientHandshaker newHandshaker(URI uri, WebSocketVersion webSocketVersion, String str, boolean z, HttpHeaders httpHeaders, int i) {
        return newHandshaker(uri, webSocketVersion, str, z, httpHeaders, i, true, false);
    }

    public static WebSocketClientHandshaker newHandshaker(URI uri, WebSocketVersion webSocketVersion, String str, boolean z, HttpHeaders httpHeaders, int i, boolean z2, boolean z3) {
        if (webSocketVersion == WebSocketVersion.V13) {
            return new WebSocketClientHandshaker13(uri, WebSocketVersion.V13, str, z, httpHeaders, i, z2, z3);
        }
        if (webSocketVersion == WebSocketVersion.V08) {
            return new WebSocketClientHandshaker08(uri, WebSocketVersion.V08, str, z, httpHeaders, i, z2, z3);
        }
        if (webSocketVersion == WebSocketVersion.V07) {
            return new WebSocketClientHandshaker07(uri, WebSocketVersion.V07, str, z, httpHeaders, i, z2, z3);
        }
        if (webSocketVersion == WebSocketVersion.V00) {
            return new WebSocketClientHandshaker00(uri, WebSocketVersion.V00, str, httpHeaders, i);
        }
        throw new WebSocketHandshakeException("Protocol version " + webSocketVersion + " not supported.");
    }
}
