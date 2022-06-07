package io.netty.buffer;

import io.netty.util.ResourceLeak;
import java.nio.ByteOrder;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: SimpleLeakAwareCompositeByteBuf.java */
/* loaded from: classes4.dex */
public final class w extends af {
    private final ResourceLeak e;

    /* JADX INFO: Access modifiers changed from: package-private */
    public w(CompositeByteBuf compositeByteBuf, ResourceLeak resourceLeak) {
        super(compositeByteBuf);
        this.e = resourceLeak;
    }

    @Override // io.netty.buffer.af, io.netty.buffer.AbstractReferenceCountedByteBuf, io.netty.util.ReferenceCounted
    public boolean release() {
        boolean release = super.release();
        if (release) {
            this.e.close();
        }
        return release;
    }

    @Override // io.netty.buffer.af, io.netty.buffer.AbstractReferenceCountedByteBuf, io.netty.util.ReferenceCounted
    public boolean release(int i) {
        boolean release = super.release(i);
        if (release) {
            this.e.close();
        }
        return release;
    }

    @Override // io.netty.buffer.af, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public ByteBuf order(ByteOrder byteOrder) {
        this.e.record();
        return order() == byteOrder ? this : new v(super.order(byteOrder), this.e);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public ByteBuf slice() {
        return new v(super.slice(), this.e);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public ByteBuf retainedSlice() {
        return new v(super.retainedSlice(), this.e);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public ByteBuf slice(int i, int i2) {
        return new v(super.slice(i, i2), this.e);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public ByteBuf retainedSlice(int i, int i2) {
        return new v(super.retainedSlice(i, i2), this.e);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public ByteBuf duplicate() {
        return new v(super.duplicate(), this.e);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public ByteBuf retainedDuplicate() {
        return new v(super.retainedDuplicate(), this.e);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public ByteBuf readSlice(int i) {
        return new v(super.readSlice(i), this.e);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public ByteBuf readRetainedSlice(int i) {
        return new v(super.readRetainedSlice(i), this.e);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public ByteBuf asReadOnly() {
        return new v(super.asReadOnly(), this.e);
    }
}
