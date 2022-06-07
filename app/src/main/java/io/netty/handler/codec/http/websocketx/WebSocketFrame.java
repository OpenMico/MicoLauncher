package io.netty.handler.codec.http.websocketx;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.DefaultByteBufHolder;
import io.netty.util.internal.StringUtil;

/* loaded from: classes4.dex */
public abstract class WebSocketFrame extends DefaultByteBufHolder {
    private final boolean a;
    private final int b;

    @Override // io.netty.buffer.DefaultByteBufHolder, io.netty.buffer.ByteBufHolder
    public abstract WebSocketFrame replace(ByteBuf byteBuf);

    /* JADX INFO: Access modifiers changed from: protected */
    public WebSocketFrame(ByteBuf byteBuf) {
        this(true, 0, byteBuf);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public WebSocketFrame(boolean z, int i, ByteBuf byteBuf) {
        super(byteBuf);
        this.a = z;
        this.b = i;
    }

    public boolean isFinalFragment() {
        return this.a;
    }

    public int rsv() {
        return this.b;
    }

    @Override // io.netty.buffer.DefaultByteBufHolder, io.netty.buffer.ByteBufHolder
    public WebSocketFrame copy() {
        return (WebSocketFrame) super.copy();
    }

    @Override // io.netty.buffer.DefaultByteBufHolder, io.netty.buffer.ByteBufHolder
    public WebSocketFrame duplicate() {
        return (WebSocketFrame) super.duplicate();
    }

    @Override // io.netty.buffer.DefaultByteBufHolder, io.netty.buffer.ByteBufHolder
    public WebSocketFrame retainedDuplicate() {
        return (WebSocketFrame) super.retainedDuplicate();
    }

    @Override // io.netty.buffer.DefaultByteBufHolder
    public String toString() {
        return StringUtil.simpleClassName(this) + "(data: " + contentToString() + ')';
    }

    @Override // io.netty.buffer.DefaultByteBufHolder, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    public WebSocketFrame retain() {
        super.retain();
        return this;
    }

    @Override // io.netty.buffer.DefaultByteBufHolder, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    public WebSocketFrame retain(int i) {
        super.retain(i);
        return this;
    }

    @Override // io.netty.buffer.DefaultByteBufHolder, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    public WebSocketFrame touch() {
        super.touch();
        return this;
    }

    @Override // io.netty.buffer.DefaultByteBufHolder, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    public WebSocketFrame touch(Object obj) {
        super.touch(obj);
        return this;
    }
}
