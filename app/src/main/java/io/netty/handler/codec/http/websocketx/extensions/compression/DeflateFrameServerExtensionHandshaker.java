package io.netty.handler.codec.http.websocketx.extensions.compression;

import io.netty.handler.codec.http.websocketx.extensions.WebSocketExtensionData;
import io.netty.handler.codec.http.websocketx.extensions.WebSocketExtensionDecoder;
import io.netty.handler.codec.http.websocketx.extensions.WebSocketExtensionEncoder;
import io.netty.handler.codec.http.websocketx.extensions.WebSocketServerExtension;
import io.netty.handler.codec.http.websocketx.extensions.WebSocketServerExtensionHandshaker;
import java.util.Collections;

/* loaded from: classes4.dex */
public final class DeflateFrameServerExtensionHandshaker implements WebSocketServerExtensionHandshaker {
    private final int a;

    public DeflateFrameServerExtensionHandshaker() {
        this(6);
    }

    public DeflateFrameServerExtensionHandshaker(int i) {
        if (i < 0 || i > 9) {
            throw new IllegalArgumentException("compressionLevel: " + i + " (expected: 0-9)");
        }
        this.a = i;
    }

    @Override // io.netty.handler.codec.http.websocketx.extensions.WebSocketServerExtensionHandshaker
    public WebSocketServerExtension handshakeExtension(WebSocketExtensionData webSocketExtensionData) {
        if (("x-webkit-deflate-frame".equals(webSocketExtensionData.name()) || "deflate-frame".equals(webSocketExtensionData.name())) && webSocketExtensionData.parameters().isEmpty()) {
            return new a(this.a, webSocketExtensionData.name());
        }
        return null;
    }

    /* loaded from: classes4.dex */
    private static class a implements WebSocketServerExtension {
        private final String a;
        private final int b;

        @Override // io.netty.handler.codec.http.websocketx.extensions.WebSocketExtension
        public int rsv() {
            return 4;
        }

        public a(int i, String str) {
            this.a = str;
            this.b = i;
        }

        @Override // io.netty.handler.codec.http.websocketx.extensions.WebSocketExtension
        public WebSocketExtensionEncoder newExtensionEncoder() {
            return new d(this.b, 15, false);
        }

        @Override // io.netty.handler.codec.http.websocketx.extensions.WebSocketExtension
        public WebSocketExtensionDecoder newExtensionDecoder() {
            return new c(false);
        }

        @Override // io.netty.handler.codec.http.websocketx.extensions.WebSocketServerExtension
        public WebSocketExtensionData newReponseData() {
            return new WebSocketExtensionData(this.a, Collections.emptyMap());
        }
    }
}
