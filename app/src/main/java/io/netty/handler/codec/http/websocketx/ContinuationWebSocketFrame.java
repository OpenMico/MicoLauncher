package io.netty.handler.codec.http.websocketx;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

/* loaded from: classes4.dex */
public class ContinuationWebSocketFrame extends WebSocketFrame {
    public ContinuationWebSocketFrame() {
        this(Unpooled.buffer(0));
    }

    public ContinuationWebSocketFrame(ByteBuf byteBuf) {
        super(byteBuf);
    }

    public ContinuationWebSocketFrame(boolean z, int i, ByteBuf byteBuf) {
        super(z, i, byteBuf);
    }

    public ContinuationWebSocketFrame(boolean z, int i, String str) {
        this(z, i, a(str));
    }

    public String text() {
        return content().toString(CharsetUtil.UTF_8);
    }

    private static ByteBuf a(String str) {
        if (str == null || str.isEmpty()) {
            return Unpooled.EMPTY_BUFFER;
        }
        return Unpooled.copiedBuffer(str, CharsetUtil.UTF_8);
    }

    @Override // io.netty.handler.codec.http.websocketx.WebSocketFrame, io.netty.buffer.DefaultByteBufHolder, io.netty.buffer.ByteBufHolder
    public ContinuationWebSocketFrame copy() {
        return (ContinuationWebSocketFrame) super.copy();
    }

    @Override // io.netty.handler.codec.http.websocketx.WebSocketFrame, io.netty.buffer.DefaultByteBufHolder, io.netty.buffer.ByteBufHolder
    public ContinuationWebSocketFrame duplicate() {
        return (ContinuationWebSocketFrame) super.duplicate();
    }

    @Override // io.netty.handler.codec.http.websocketx.WebSocketFrame, io.netty.buffer.DefaultByteBufHolder, io.netty.buffer.ByteBufHolder
    public ContinuationWebSocketFrame retainedDuplicate() {
        return (ContinuationWebSocketFrame) super.retainedDuplicate();
    }

    @Override // io.netty.handler.codec.http.websocketx.WebSocketFrame, io.netty.buffer.DefaultByteBufHolder, io.netty.buffer.ByteBufHolder
    public ContinuationWebSocketFrame replace(ByteBuf byteBuf) {
        return new ContinuationWebSocketFrame(isFinalFragment(), rsv(), byteBuf);
    }

    @Override // io.netty.handler.codec.http.websocketx.WebSocketFrame, io.netty.buffer.DefaultByteBufHolder, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    public ContinuationWebSocketFrame retain() {
        super.retain();
        return this;
    }

    @Override // io.netty.handler.codec.http.websocketx.WebSocketFrame, io.netty.buffer.DefaultByteBufHolder, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    public ContinuationWebSocketFrame retain(int i) {
        super.retain(i);
        return this;
    }

    @Override // io.netty.handler.codec.http.websocketx.WebSocketFrame, io.netty.buffer.DefaultByteBufHolder, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    public ContinuationWebSocketFrame touch() {
        super.touch();
        return this;
    }

    @Override // io.netty.handler.codec.http.websocketx.WebSocketFrame, io.netty.buffer.DefaultByteBufHolder, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    public ContinuationWebSocketFrame touch(Object obj) {
        super.touch(obj);
        return this;
    }
}
