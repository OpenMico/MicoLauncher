package io.netty.handler.codec.http.websocketx.extensions.compression;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.ContinuationWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import java.util.List;

/* compiled from: PerMessageDeflateDecoder.java */
/* loaded from: classes4.dex */
class e extends a {
    private boolean b;

    public e(boolean z) {
        super(z);
    }

    @Override // io.netty.handler.codec.MessageToMessageDecoder
    public boolean acceptInboundMessage(Object obj) throws Exception {
        return (((obj instanceof TextWebSocketFrame) || (obj instanceof BinaryWebSocketFrame)) && (((WebSocketFrame) obj).rsv() & 4) > 0) || ((obj instanceof ContinuationWebSocketFrame) && this.b);
    }

    @Override // io.netty.handler.codec.http.websocketx.extensions.compression.a
    protected int b(WebSocketFrame webSocketFrame) {
        return (webSocketFrame.rsv() & 4) > 0 ? webSocketFrame.rsv() ^ 4 : webSocketFrame.rsv();
    }

    @Override // io.netty.handler.codec.http.websocketx.extensions.compression.a
    protected boolean a(WebSocketFrame webSocketFrame) {
        return webSocketFrame.isFinalFragment();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.handler.codec.http.websocketx.extensions.compression.a
    /* renamed from: a */
    public void decode(ChannelHandlerContext channelHandlerContext, WebSocketFrame webSocketFrame, List<Object> list) throws Exception {
        super.decode(channelHandlerContext, webSocketFrame, list);
        if (webSocketFrame.isFinalFragment()) {
            this.b = false;
        } else if ((webSocketFrame instanceof TextWebSocketFrame) || (webSocketFrame instanceof BinaryWebSocketFrame)) {
            this.b = true;
        }
    }
}
