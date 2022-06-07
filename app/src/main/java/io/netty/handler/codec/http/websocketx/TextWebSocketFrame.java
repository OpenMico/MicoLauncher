package io.netty.handler.codec.http.websocketx;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

/* loaded from: classes4.dex */
public class TextWebSocketFrame extends WebSocketFrame {
    public TextWebSocketFrame() {
        super(Unpooled.buffer(0));
    }

    public TextWebSocketFrame(String str) {
        super(a(str));
    }

    public TextWebSocketFrame(ByteBuf byteBuf) {
        super(byteBuf);
    }

    public TextWebSocketFrame(boolean z, int i, String str) {
        super(z, i, a(str));
    }

    private static ByteBuf a(String str) {
        if (str == null || str.isEmpty()) {
            return Unpooled.EMPTY_BUFFER;
        }
        return Unpooled.copiedBuffer(str, CharsetUtil.UTF_8);
    }

    public TextWebSocketFrame(boolean z, int i, ByteBuf byteBuf) {
        super(z, i, byteBuf);
    }

    public String text() {
        return content().toString(CharsetUtil.UTF_8);
    }

    @Override // io.netty.handler.codec.http.websocketx.WebSocketFrame, io.netty.buffer.DefaultByteBufHolder, io.netty.buffer.ByteBufHolder
    public TextWebSocketFrame copy() {
        return (TextWebSocketFrame) super.copy();
    }

    @Override // io.netty.handler.codec.http.websocketx.WebSocketFrame, io.netty.buffer.DefaultByteBufHolder, io.netty.buffer.ByteBufHolder
    public TextWebSocketFrame duplicate() {
        return (TextWebSocketFrame) super.duplicate();
    }

    @Override // io.netty.handler.codec.http.websocketx.WebSocketFrame, io.netty.buffer.DefaultByteBufHolder, io.netty.buffer.ByteBufHolder
    public TextWebSocketFrame retainedDuplicate() {
        return (TextWebSocketFrame) super.retainedDuplicate();
    }

    @Override // io.netty.handler.codec.http.websocketx.WebSocketFrame, io.netty.buffer.DefaultByteBufHolder, io.netty.buffer.ByteBufHolder
    public TextWebSocketFrame replace(ByteBuf byteBuf) {
        return new TextWebSocketFrame(isFinalFragment(), rsv(), byteBuf);
    }

    @Override // io.netty.handler.codec.http.websocketx.WebSocketFrame, io.netty.buffer.DefaultByteBufHolder, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    public TextWebSocketFrame retain() {
        super.retain();
        return this;
    }

    @Override // io.netty.handler.codec.http.websocketx.WebSocketFrame, io.netty.buffer.DefaultByteBufHolder, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    public TextWebSocketFrame retain(int i) {
        super.retain(i);
        return this;
    }

    @Override // io.netty.handler.codec.http.websocketx.WebSocketFrame, io.netty.buffer.DefaultByteBufHolder, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    public TextWebSocketFrame touch() {
        super.touch();
        return this;
    }

    @Override // io.netty.handler.codec.http.websocketx.WebSocketFrame, io.netty.buffer.DefaultByteBufHolder, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    public TextWebSocketFrame touch(Object obj) {
        super.touch(obj);
        return this;
    }
}
