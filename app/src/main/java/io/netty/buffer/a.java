package io.netty.buffer;

import io.netty.util.Recycler;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/* compiled from: AbstractPooledDerivedByteBuf.java */
/* loaded from: classes4.dex */
public abstract class a<T> extends AbstractReferenceCountedByteBuf {
    private final Recycler.Handle<a<T>> d;
    private AbstractByteBuf e;

    /* JADX WARN: Multi-variable type inference failed */
    public a(Recycler.Handle<? extends a<T>> handle) {
        super(0);
        this.d = handle;
    }

    /* renamed from: b */
    public final AbstractByteBuf unwrap() {
        return this.e;
    }

    /* JADX WARN: Multi-variable type inference failed */
    final <U extends a<T>> U a(AbstractByteBuf abstractByteBuf, int i, int i2, int i3) {
        abstractByteBuf.retain();
        this.e = abstractByteBuf;
        try {
            maxCapacity(i3);
            setIndex(i, i2);
            setRefCnt(1);
            return this;
        } catch (Throwable th) {
            this.e = null;
            abstractByteBuf.release();
            throw th;
        }
    }

    @Override // io.netty.buffer.AbstractReferenceCountedByteBuf
    protected final void deallocate() {
        AbstractByteBuf b = unwrap();
        this.d.recycle(this);
        b.release();
    }

    @Override // io.netty.buffer.ByteBuf
    public final ByteBufAllocator alloc() {
        return unwrap().alloc();
    }

    @Override // io.netty.buffer.ByteBuf
    @Deprecated
    public final ByteOrder order() {
        return unwrap().order();
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public boolean isReadOnly() {
        return unwrap().isReadOnly();
    }

    @Override // io.netty.buffer.ByteBuf
    public final boolean isDirect() {
        return unwrap().isDirect();
    }

    @Override // io.netty.buffer.ByteBuf
    public boolean hasArray() {
        return unwrap().hasArray();
    }

    @Override // io.netty.buffer.ByteBuf
    public byte[] array() {
        return unwrap().array();
    }

    @Override // io.netty.buffer.ByteBuf
    public boolean hasMemoryAddress() {
        return unwrap().hasMemoryAddress();
    }

    @Override // io.netty.buffer.ByteBuf
    public final int nioBufferCount() {
        return unwrap().nioBufferCount();
    }

    @Override // io.netty.buffer.ByteBuf
    public final ByteBuffer internalNioBuffer(int i, int i2) {
        return nioBuffer(i, i2);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public final ByteBuf retainedDuplicate() {
        return o.a(this, readerIndex(), writerIndex());
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public final ByteBuf retainedSlice() {
        int readerIndex = readerIndex();
        return retainedSlice(readerIndex, writerIndex() - readerIndex);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public final ByteBuf retainedSlice(int i, int i2) {
        return q.b(this, i, i2, i);
    }
}
