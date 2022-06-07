package io.netty.handler.codec.http.websocketx.extensions.compression;

import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.ContinuationWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;

/* compiled from: PerFrameDeflateDecoder.java */
/* loaded from: classes4.dex */
class c extends a {
    @Override // io.netty.handler.codec.http.websocketx.extensions.compression.a
    protected boolean a(WebSocketFrame webSocketFrame) {
        return true;
    }

    public c(boolean z) {
        super(z);
    }

    @Override // io.netty.handler.codec.MessageToMessageDecoder
    public boolean acceptInboundMessage(Object obj) throws Exception {
        return ((obj instanceof TextWebSocketFrame) || (obj instanceof BinaryWebSocketFrame) || (obj instanceof ContinuationWebSocketFrame)) && (((WebSocketFrame) obj).rsv() & 4) > 0;
    }

    @Override // io.netty.handler.codec.http.websocketx.extensions.compression.a
    protected int b(WebSocketFrame webSocketFrame) {
        return webSocketFrame.rsv() ^ 4;
    }
}
