package io.netty.handler.codec.http.websocketx.extensions;

import java.util.Collections;
import java.util.Map;

/* loaded from: classes4.dex */
public final class WebSocketExtensionData {
    private final String a;
    private final Map<String, String> b;

    public WebSocketExtensionData(String str, Map<String, String> map) {
        if (str == null) {
            throw new NullPointerException("name");
        } else if (map != null) {
            this.a = str;
            this.b = Collections.unmodifiableMap(map);
        } else {
            throw new NullPointerException("parameters");
        }
    }

    public String name() {
        return this.a;
    }

    public Map<String, String> parameters() {
        return this.b;
    }
}
