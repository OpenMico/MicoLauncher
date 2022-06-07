package io.netty.handler.codec.http.websocketx.extensions.compression;

import io.netty.handler.codec.compression.ZlibCodecFactory;
import io.netty.handler.codec.http.websocketx.extensions.WebSocketExtensionData;
import io.netty.handler.codec.http.websocketx.extensions.WebSocketExtensionDecoder;
import io.netty.handler.codec.http.websocketx.extensions.WebSocketExtensionEncoder;
import io.netty.handler.codec.http.websocketx.extensions.WebSocketServerExtension;
import io.netty.handler.codec.http.websocketx.extensions.WebSocketServerExtensionHandshaker;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* loaded from: classes4.dex */
public final class PerMessageDeflateServerExtensionHandshaker implements WebSocketServerExtensionHandshaker {
    public static final int MAX_WINDOW_SIZE = 15;
    public static final int MIN_WINDOW_SIZE = 8;
    private final int a;
    private final boolean b;
    private final int c;
    private final boolean d;
    private final boolean e;

    public PerMessageDeflateServerExtensionHandshaker() {
        this(6, ZlibCodecFactory.isSupportingWindowSizeAndMemLevel(), 15, false, false);
    }

    public PerMessageDeflateServerExtensionHandshaker(int i, boolean z, int i2, boolean z2, boolean z3) {
        if (i2 > 15 || i2 < 8) {
            throw new IllegalArgumentException("preferredServerWindowSize: " + i2 + " (expected: 8-15)");
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

    @Override // io.netty.handler.codec.http.websocketx.extensions.WebSocketServerExtensionHandshaker
    public WebSocketServerExtension handshakeExtension(WebSocketExtensionData webSocketExtensionData) {
        if (!"permessage-deflate".equals(webSocketExtensionData.name())) {
            return null;
        }
        Iterator<Map.Entry<String, String>> it = webSocketExtensionData.parameters().entrySet().iterator();
        boolean z = true;
        int i = 15;
        int i2 = 15;
        boolean z2 = false;
        boolean z3 = false;
        while (z && it.hasNext()) {
            Map.Entry<String, String> next = it.next();
            if ("client_max_window_bits".equalsIgnoreCase(next.getKey())) {
                i2 = this.c;
            } else if ("server_max_window_bits".equalsIgnoreCase(next.getKey())) {
                if (this.b) {
                    int parseInt = Integer.parseInt(next.getValue());
                    if (parseInt > 15 || parseInt < 8) {
                        z = false;
                        i = parseInt;
                    } else {
                        i = parseInt;
                    }
                } else {
                    z = false;
                }
            } else if ("client_no_context_takeover".equalsIgnoreCase(next.getKey())) {
                z3 = this.e;
            } else if (!"server_no_context_takeover".equalsIgnoreCase(next.getKey())) {
                z = false;
            } else if (this.d) {
                z2 = true;
            } else {
                z = false;
            }
        }
        if (z) {
            return new a(this.a, z2, i, z3, i2);
        }
        return null;
    }

    /* loaded from: classes4.dex */
    private static class a implements WebSocketServerExtension {
        private final int a;
        private final boolean b;
        private final int c;
        private final boolean d;
        private final int e;

        @Override // io.netty.handler.codec.http.websocketx.extensions.WebSocketExtension
        public int rsv() {
            return 4;
        }

        public a(int i, boolean z, int i2, boolean z2, int i3) {
            this.a = i;
            this.b = z;
            this.c = i2;
            this.d = z2;
            this.e = i3;
        }

        @Override // io.netty.handler.codec.http.websocketx.extensions.WebSocketExtension
        public WebSocketExtensionEncoder newExtensionEncoder() {
            return new f(this.a, this.e, this.d);
        }

        @Override // io.netty.handler.codec.http.websocketx.extensions.WebSocketExtension
        public WebSocketExtensionDecoder newExtensionDecoder() {
            return new e(this.b);
        }

        @Override // io.netty.handler.codec.http.websocketx.extensions.WebSocketServerExtension
        public WebSocketExtensionData newReponseData() {
            HashMap hashMap = new HashMap(4);
            if (this.b) {
                hashMap.put("server_no_context_takeover", null);
            }
            if (this.d) {
                hashMap.put("client_no_context_takeover", null);
            }
            int i = this.c;
            if (i != 15) {
                hashMap.put("server_max_window_bits", Integer.toString(i));
            }
            int i2 = this.e;
            if (i2 != 15) {
                hashMap.put("client_max_window_bits", Integer.toString(i2));
            }
            return new WebSocketExtensionData("permessage-deflate", hashMap);
        }
    }
}
