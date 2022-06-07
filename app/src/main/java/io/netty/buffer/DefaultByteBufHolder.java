package io.netty.buffer;

import io.netty.util.IllegalReferenceCountException;
import io.netty.util.internal.StringUtil;

/* loaded from: classes4.dex */
public class DefaultByteBufHolder implements ByteBufHolder {
    private final ByteBuf a;

    public DefaultByteBufHolder(ByteBuf byteBuf) {
        if (byteBuf != null) {
            this.a = byteBuf;
            return;
        }
        throw new NullPointerException("data");
    }

    @Override // io.netty.buffer.ByteBufHolder
    public ByteBuf content() {
        if (this.a.refCnt() > 0) {
            return this.a;
        }
        throw new IllegalReferenceCountException(this.a.refCnt());
    }

    @Override // io.netty.buffer.ByteBufHolder
    public ByteBufHolder copy() {
        return replace(this.a.copy());
    }

    @Override // io.netty.buffer.ByteBufHolder
    public ByteBufHolder duplicate() {
        return replace(this.a.duplicate());
    }

    @Override // io.netty.buffer.ByteBufHolder
    public ByteBufHolder retainedDuplicate() {
        return replace(this.a.retainedDuplicate());
    }

    @Override // io.netty.buffer.ByteBufHolder
    public ByteBufHolder replace(ByteBuf byteBuf) {
        return new DefaultByteBufHolder(byteBuf);
    }

    @Override // io.netty.util.ReferenceCounted
    public int refCnt() {
        return this.a.refCnt();
    }

    @Override // io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    public ByteBufHolder retain() {
        this.a.retain();
        return this;
    }

    @Override // io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    public ByteBufHolder retain(int i) {
        this.a.retain(i);
        return this;
    }

    @Override // io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    public ByteBufHolder touch() {
        this.a.touch();
        return this;
    }

    @Override // io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    public ByteBufHolder touch(Object obj) {
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

    protected final String contentToString() {
        return this.a.toString();
    }

    public String toString() {
        return StringUtil.simpleClassName(this) + '(' + contentToString() + ')';
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof ByteBufHolder) {
            return this.a.equals(((ByteBufHolder) obj).content());
        }
        return false;
    }

    public int hashCode() {
        return this.a.hashCode();
    }
}
