package io.netty.handler.codec.http.websocketx.extensions.compression;

import io.netty.handler.codec.http.websocketx.extensions.WebSocketClientExtension;
import io.netty.handler.codec.http.websocketx.extensions.WebSocketClientExtensionHandshaker;
import io.netty.handler.codec.http.websocketx.extensions.WebSocketExtensionData;
import io.netty.handler.codec.http.websocketx.extensions.WebSocketExtensionDecoder;
import io.netty.handler.codec.http.websocketx.extensions.WebSocketExtensionEncoder;
import java.util.Collections;

/* loaded from: classes4.dex */
public final class DeflateFrameClientExtensionHandshaker implements WebSocketClientExtensionHandshaker {
    private final int a;
    private final boolean b;

    public DeflateFrameClientExtensionHandshaker(boolean z) {
        this(6, z);
    }

    public DeflateFrameClientExtensionHandshaker(int i, boolean z) {
        if (i < 0 || i > 9) {
            throw new IllegalArgumentException("compressionLevel: " + i + " (expected: 0-9)");
        }
        this.a = i;
        this.b = z;
    }

    @Override // io.netty.handler.codec.http.websocketx.extensions.WebSocketClientExtensionHandshaker
    public WebSocketExtensionData newRequestData() {
        return new WebSocketExtensionData(this.b ? "x-webkit-deflate-frame" : "deflate-frame", Collections.emptyMap());
    }

    @Override // io.netty.handler.codec.http.websocketx.extensions.WebSocketClientExtensionHandshaker
    public WebSocketClientExtension handshakeExtension(WebSocketExtensionData webSocketExtensionData) {
        if (("x-webkit-deflate-frame".equals(webSocketExtensionData.name()) || "deflate-frame".equals(webSocketExtensionData.name())) && webSocketExtensionData.parameters().isEmpty()) {
            return new a(this.a);
        }
        return null;
    }

    /* loaded from: classes4.dex */
    private static class a implements WebSocketClientExtension {
        private final int a;

        @Override // io.netty.handler.codec.http.websocketx.extensions.WebSocketExtension
        public int rsv() {
            return 4;
        }

        public a(int i) {
            this.a = i;
        }

        @Override // io.netty.handler.codec.http.websocketx.extensions.WebSocketExtension
        public WebSocketExtensionEncoder newExtensionEncoder() {
            return new d(this.a, 15, false);
        }

        @Override // io.netty.handler.codec.http.websocketx.extensions.WebSocketExtension
        public WebSocketExtensionDecoder newExtensionDecoder() {
            return new c(false);
        }
    }
}
