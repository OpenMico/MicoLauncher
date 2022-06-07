package io.netty.handler.codec.http.websocketx.extensions;

/* loaded from: classes4.dex */
public interface WebSocketExtension {
    public static final int RSV1 = 4;
    public static final int RSV2 = 2;
    public static final int RSV3 = 1;

    WebSocketExtensionDecoder newExtensionDecoder();

    WebSocketExtensionEncoder newExtensionEncoder();

    int rsv();
}
