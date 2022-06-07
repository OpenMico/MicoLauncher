package io.netty.handler.codec.http.websocketx.extensions;

/* loaded from: classes4.dex */
public interface WebSocketClientExtensionHandshaker {
    WebSocketClientExtension handshakeExtension(WebSocketExtensionData webSocketExtensionData);

    WebSocketExtensionData newRequestData();
}
