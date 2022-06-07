package io.netty.handler.codec.http.websocketx;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/* loaded from: classes4.dex */
public class PingWebSocketFrame extends WebSocketFrame {
    public PingWebSocketFrame() {
        super(true, 0, Unpooled.buffer(0));
    }

    public PingWebSocketFrame(ByteBuf byteBuf) {
        super(byteBuf);
    }

    public PingWebSocketFrame(boolean z, int i, ByteBuf byteBuf) {
        super(z, i, byteBuf);
    }

    @Override // io.netty.handler.codec.http.websocketx.WebSocketFrame, io.netty.buffer.DefaultByteBufHolder, io.netty.buffer.ByteBufHolder
    public PingWebSocketFrame copy() {
        return (PingWebSocketFrame) super.copy();
    }

    @Override // io.netty.handler.codec.http.websocketx.WebSocketFrame, io.netty.buffer.DefaultByteBufHolder, io.netty.buffer.ByteBufHolder
    public PingWebSocketFrame duplicate() {
        return (PingWebSocketFrame) super.duplicate();
    }

    @Override // io.netty.handler.codec.http.websocketx.WebSocketFrame, io.netty.buffer.DefaultByteBufHolder, io.netty.buffer.ByteBufHolder
    public PingWebSocketFrame retainedDuplicate() {
        return (PingWebSocketFrame) super.retainedDuplicate();
    }

    @Override // io.netty.handler.codec.http.websocketx.WebSocketFrame, io.netty.buffer.DefaultByteBufHolder, io.netty.buffer.ByteBufHolder
    public PingWebSocketFrame replace(ByteBuf byteBuf) {
        return new PingWebSocketFrame(isFinalFragment(), rsv(), byteBuf);
    }

    @Override // io.netty.handler.codec.http.websocketx.WebSocketFrame, io.netty.buffer.DefaultByteBufHolder, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    public PingWebSocketFrame retain() {
        super.retain();
        return this;
    }

    @Override // io.netty.handler.codec.http.websocketx.WebSocketFrame, io.netty.buffer.DefaultByteBufHolder, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    public PingWebSocketFrame retain(int i) {
        super.retain(i);
        return this;
    }

    @Override // io.netty.handler.codec.http.websocketx.WebSocketFrame, io.netty.buffer.DefaultByteBufHolder, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    public PingWebSocketFrame touch() {
        super.touch();
        return this;
    }

    @Override // io.netty.handler.codec.http.websocketx.WebSocketFrame, io.netty.buffer.DefaultByteBufHolder, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    public PingWebSocketFrame touch(Object obj) {
        super.touch(obj);
        return this;
    }
}
