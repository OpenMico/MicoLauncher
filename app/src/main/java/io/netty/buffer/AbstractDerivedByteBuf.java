package io.netty.buffer;

import java.nio.ByteBuffer;

@Deprecated
/* loaded from: classes4.dex */
public abstract class AbstractDerivedByteBuf extends AbstractByteBuf {
    /* JADX INFO: Access modifiers changed from: protected */
    public AbstractDerivedByteBuf(int i) {
        super(i);
    }

    @Override // io.netty.util.ReferenceCounted
    public final int refCnt() {
        return unwrap().refCnt();
    }

    @Override // io.netty.buffer.ByteBuf, io.netty.util.ReferenceCounted
    public final ByteBuf retain() {
        unwrap().retain();
        return this;
    }

    @Override // io.netty.buffer.ByteBuf, io.netty.util.ReferenceCounted
    public final ByteBuf retain(int i) {
        unwrap().retain(i);
        return this;
    }

    @Override // io.netty.buffer.ByteBuf, io.netty.util.ReferenceCounted
    public final ByteBuf touch() {
        unwrap().touch();
        return this;
    }

    @Override // io.netty.buffer.ByteBuf, io.netty.util.ReferenceCounted
    public final ByteBuf touch(Object obj) {
        unwrap().touch(obj);
        return this;
    }

    @Override // io.netty.util.ReferenceCounted
    public final boolean release() {
        return unwrap().release();
    }

    @Override // io.netty.util.ReferenceCounted
    public final boolean release(int i) {
        return unwrap().release(i);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public boolean isReadOnly() {
        return unwrap().isReadOnly();
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuffer internalNioBuffer(int i, int i2) {
        return nioBuffer(i, i2);
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuffer nioBuffer(int i, int i2) {
        return unwrap().nioBuffer(i, i2);
    }
}
