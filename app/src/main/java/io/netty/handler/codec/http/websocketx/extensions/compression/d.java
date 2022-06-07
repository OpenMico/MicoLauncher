package io.netty.handler.codec.http.websocketx.extensions.compression;

import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.ContinuationWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;

/* compiled from: PerFrameDeflateEncoder.java */
/* loaded from: classes4.dex */
class d extends b {
    @Override // io.netty.handler.codec.http.websocketx.extensions.compression.b
    protected boolean b(WebSocketFrame webSocketFrame) {
        return true;
    }

    public d(int i, int i2, boolean z) {
        super(i, i2, z);
    }

    @Override // io.netty.handler.codec.MessageToMessageEncoder
    public boolean acceptOutboundMessage(Object obj) throws Exception {
        if ((obj instanceof TextWebSocketFrame) || (obj instanceof BinaryWebSocketFrame) || (obj instanceof ContinuationWebSocketFrame)) {
            WebSocketFrame webSocketFrame = (WebSocketFrame) obj;
            if (webSocketFrame.content().readableBytes() > 0 && (webSocketFrame.rsv() & 4) == 0) {
                return true;
            }
        }
        return false;
    }

    @Override // io.netty.handler.codec.http.websocketx.extensions.compression.b
    protected int a(WebSocketFrame webSocketFrame) {
        return webSocketFrame.rsv() | 4;
    }
}
