package io.netty.handler.codec.stomp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

/* loaded from: classes4.dex */
public class DefaultStompFrame extends DefaultStompHeadersSubframe implements StompFrame {
    private final ByteBuf a;

    public DefaultStompFrame(StompCommand stompCommand) {
        this(stompCommand, Unpooled.buffer(0));
    }

    public DefaultStompFrame(StompCommand stompCommand, ByteBuf byteBuf) {
        super(stompCommand);
        if (byteBuf != null) {
            this.a = byteBuf;
            return;
        }
        throw new NullPointerException("content");
    }

    @Override // io.netty.buffer.ByteBufHolder
    public ByteBuf content() {
        return this.a;
    }

    @Override // io.netty.handler.codec.stomp.StompFrame, io.netty.handler.codec.stomp.LastStompContentSubframe, io.netty.handler.codec.stomp.StompContentSubframe, io.netty.buffer.ByteBufHolder
    public StompFrame copy() {
        return replace(this.a.copy());
    }

    @Override // io.netty.handler.codec.stomp.StompFrame, io.netty.handler.codec.stomp.LastStompContentSubframe, io.netty.handler.codec.stomp.StompContentSubframe, io.netty.buffer.ByteBufHolder
    public StompFrame duplicate() {
        return replace(this.a.duplicate());
    }

    @Override // io.netty.handler.codec.stomp.StompFrame, io.netty.handler.codec.stomp.LastStompContentSubframe, io.netty.handler.codec.stomp.StompContentSubframe, io.netty.buffer.ByteBufHolder
    public StompFrame retainedDuplicate() {
        return replace(this.a.retainedDuplicate());
    }

    @Override // io.netty.handler.codec.stomp.StompFrame, io.netty.handler.codec.stomp.LastStompContentSubframe, io.netty.handler.codec.stomp.StompContentSubframe, io.netty.buffer.ByteBufHolder
    public StompFrame replace(ByteBuf byteBuf) {
        return new DefaultStompFrame(this.command, byteBuf);
    }

    @Override // io.netty.util.ReferenceCounted
    public int refCnt() {
        return this.a.refCnt();
    }

    @Override // io.netty.handler.codec.stomp.StompFrame, io.netty.handler.codec.stomp.LastStompContentSubframe, io.netty.handler.codec.stomp.StompContentSubframe, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    public StompFrame retain() {
        this.a.retain();
        return this;
    }

    @Override // io.netty.handler.codec.stomp.StompFrame, io.netty.handler.codec.stomp.LastStompContentSubframe, io.netty.handler.codec.stomp.StompContentSubframe, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    public StompFrame retain(int i) {
        this.a.retain(i);
        return this;
    }

    @Override // io.netty.handler.codec.stomp.StompFrame, io.netty.handler.codec.stomp.LastStompContentSubframe, io.netty.handler.codec.stomp.StompContentSubframe, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    public StompFrame touch() {
        this.a.touch();
        return this;
    }

    @Override // io.netty.handler.codec.stomp.StompFrame, io.netty.handler.codec.stomp.LastStompContentSubframe, io.netty.handler.codec.stomp.StompContentSubframe, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    public StompFrame touch(Object obj) {
        this.a.touch(obj);
        return this;
    }

    @Override // io.netty.util.ReferenceCounted
    public boolean release() {
        return this.a.release();
    }

    @Override // io.netty.util.ReferenceCounted
    public boolean release(int i) {
        return this.a.release(i);
    }

    @Override // io.netty.handler.codec.stomp.DefaultStompHeadersSubframe
    public String toString() {
        return "DefaultStompFrame{command=" + this.command + ", headers=" + this.headers + ", content=" + this.a.toString(CharsetUtil.UTF_8) + '}';
    }
}
