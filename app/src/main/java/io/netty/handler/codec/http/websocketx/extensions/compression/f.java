package io.netty.handler.codec.http.websocketx.extensions.compression;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.ContinuationWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import java.util.List;

/* compiled from: PerMessageDeflateEncoder.java */
/* loaded from: classes4.dex */
class f extends b {
    private boolean a;

    public f(int i, int i2, boolean z) {
        super(i, i2, z);
    }

    @Override // io.netty.handler.codec.MessageToMessageEncoder
    public boolean acceptOutboundMessage(Object obj) throws Exception {
        return (((obj instanceof TextWebSocketFrame) || (obj instanceof BinaryWebSocketFrame)) && (((WebSocketFrame) obj).rsv() & 4) == 0) || ((obj instanceof ContinuationWebSocketFrame) && this.a);
    }

    @Override // io.netty.handler.codec.http.websocketx.extensions.compression.b
    protected int a(WebSocketFrame webSocketFrame) {
        return ((webSocketFrame instanceof TextWebSocketFrame) || (webSocketFrame instanceof BinaryWebSocketFrame)) ? webSocketFrame.rsv() | 4 : webSocketFrame.rsv();
    }

    @Override // io.netty.handler.codec.http.websocketx.extensions.compression.b
    protected boolean b(WebSocketFrame webSocketFrame) {
        return webSocketFrame.isFinalFragment();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.handler.codec.http.websocketx.extensions.compression.b
    /* renamed from: a */
    public void encode(ChannelHandlerContext channelHandlerContext, WebSocketFrame webSocketFrame, List<Object> list) throws Exception {
        super.encode(channelHandlerContext, webSocketFrame, list);
        if (webSocketFrame.isFinalFragment()) {
            this.a = false;
        } else if ((webSocketFrame instanceof TextWebSocketFrame) || (webSocketFrame instanceof BinaryWebSocketFrame)) {
            this.a = true;
        }
    }
}
