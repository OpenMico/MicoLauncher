package io.netty.handler.codec.http.websocketx.extensions.compression;

import io.netty.handler.codec.compression.ZlibCodecFactory;
import io.netty.handler.codec.http.websocketx.extensions.WebSocketClientExtension;
import io.netty.handler.codec.http.websocketx.extensions.WebSocketClientExtensionHandshaker;
import io.netty.handler.codec.http.websocketx.extensions.WebSocketExtensionData;
import io.netty.handler.codec.http.websocketx.extensions.WebSocketExtensionDecoder;
import io.netty.handler.codec.http.websocketx.extensions.WebSocketExtensionEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* loaded from: classes4.dex */
public final class PerMessageDeflateClientExtensionHandshaker implements WebSocketClientExtensionHandshaker {
    private final int a;
    private final boolean b;
    private final int c;
    private final boolean d;
    private final boolean e;

    public PerMessageDeflateClientExtensionHandshaker() {
        this(6, ZlibCodecFactory.isSupportingWindowSizeAndMemLevel(), 15, false, false);
    }

    public PerMessageDeflateClientExtensionHandshaker(int i, boolean z, int i2, boolean z2, boolean z3) {
        if (i2 > 15 || i2 < 8) {
            throw new IllegalArgumentException("requestedServerWindowSize: " + i2 + " (expected: 8-15)");
        } else if (i < 0 || i > 9) {
            throw new IllegalArgumentException("compressionLevel: " + i + " (expected: 0-9)");
        } else {
            this.a = i;
            this.b = z;
            this.c = i2;
            this.d = z2;
            this.e = z3;
        }
    }

    @Override // io.netty.handler.codec.http.websocketx.extensions.WebSocketClientExtensionHandshaker
    public WebSocketExtensionData newRequestData() {
        HashMap hashMap = new HashMap(4);
        if (this.c != 15) {
            hashMap.put("server_no_context_takeover", null);
        }
        if (this.d) {
            hashMap.put("client_no_context_takeover", null);
        }
        int i = this.c;
        if (i != 15) {
            hashMap.put("server_max_window_bits", Integer.toString(i));
        }
        if (this.b) {
            hashMap.put("client_max_window_bits", null);
        }
        return new WebSocketExtensionData("permessage-deflate", hashMap);
    }

    @Override // io.netty.handler.codec.http.websocketx.extensions.WebSocketClientExtensionHandshaker
    public WebSocketClientExtension handshakeExtension(WebSocketExtensionData webSocketExtensionData) {
        if (!"permessage-deflate".equals(webSocketExtensionData.name())) {
            return null;
        }
        Iterator<Map.Entry<String, String>> it = webSocketExtensionData.parameters().entrySet().iterator();
        boolean z = false;
        int i = 15;
        int i2 = 15;
        boolean z2 = true;
        boolean z3 = false;
        boolean z4 = false;
        while (z2 && it.hasNext()) {
            Map.Entry<String, String> next = it.next();
            if ("client_max_window_bits".equalsIgnoreCase(next.getKey())) {
                if (this.b) {
                    i2 = Integer.parseInt(next.getValue());
                } else {
                    z2 = false;
                }
            } else if ("server_max_window_bits".equalsIgnoreCase(next.getKey())) {
                int parseInt = Integer.parseInt(next.getValue());
                if (i2 > 15 || i2 < 8) {
                    z2 = false;
                    i = parseInt;
                } else {
                    i = parseInt;
                }
            } else if ("client_no_context_takeover".equalsIgnoreCase(next.getKey())) {
                if (this.d) {
                    z4 = true;
                } else {
                    z2 = false;
                }
            } else if (!"server_no_context_takeover".equalsIgnoreCase(next.getKey())) {
                z2 = false;
            } else if (this.e) {
                z3 = true;
            } else {
                z2 = false;
            }
        }
        if ((!this.e || z3) && this.c == i) {
            z = z2;
        }
        if (z) {
            return new a(z3, i, z4, i2);
        }
        return null;
    }

    /* loaded from: classes4.dex */
    private final class a implements WebSocketClientExtension {
        private final boolean b;
        private final int c;
        private final boolean d;
        private final int e;

        @Override // io.netty.handler.codec.http.websocketx.extensions.WebSocketExtension
        public int rsv() {
            return 4;
        }

        public a(boolean z, int i, boolean z2, int i2) {
            this.b = z;
            this.c = i;
            this.d = z2;
            this.e = i2;
        }

        @Override // io.netty.handler.codec.http.websocketx.extensions.WebSocketExtension
        public WebSocketExtensionEncoder newExtensionEncoder() {
            return new f(PerMessageDeflateClientExtensionHandshaker.this.a, this.c, this.b);
        }

        @Override // io.netty.handler.codec.http.websocketx.extensions.WebSocketExtension
        public WebSocketExtensionDecoder newExtensionDecoder() {
            return new e(this.d);
        }
    }
}
