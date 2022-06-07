package io.netty.buffer;

import io.netty.util.ResourceLeak;
import java.nio.ByteOrder;

/* compiled from: SimpleLeakAwareByteBuf.java */
/* loaded from: classes4.dex */
final class v extends ae {
    private final ResourceLeak b;

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf, io.netty.util.ReferenceCounted
    public ByteBuf touch() {
        return this;
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf, io.netty.util.ReferenceCounted
    public ByteBuf touch(Object obj) {
        return this;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public v(ByteBuf byteBuf, ResourceLeak resourceLeak) {
        super(byteBuf);
        this.b = resourceLeak;
    }

    @Override // io.netty.buffer.ae, io.netty.util.ReferenceCounted
    public boolean release() {
        boolean release = super.release();
        if (release) {
            this.b.close();
        }
        return release;
    }

    @Override // io.netty.buffer.ae, io.netty.util.ReferenceCounted
    public boolean release(int i) {
        boolean release = super.release(i);
        if (release) {
            this.b.close();
        }
        return release;
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public ByteBuf order(ByteOrder byteOrder) {
        this.b.record();
        return order() == byteOrder ? this : new v(super.order(byteOrder), this.b);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public ByteBuf slice() {
        return new v(super.slice(), this.b);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public ByteBuf retainedSlice() {
        return new v(super.retainedSlice(), this.b);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public ByteBuf slice(int i, int i2) {
        return new v(super.slice(i, i2), this.b);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public ByteBuf retainedSlice(int i, int i2) {
        return new v(super.retainedSlice(i, i2), this.b);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public ByteBuf duplicate() {
        return new v(super.duplicate(), this.b);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public ByteBuf retainedDuplicate() {
        return new v(super.retainedDuplicate(), this.b);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public ByteBuf readSlice(int i) {
        return new v(super.readSlice(i), this.b);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public ByteBuf readRetainedSlice(int i) {
        return new v(super.readRetainedSlice(i), this.b);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public ByteBuf asReadOnly() {
        return new v(super.asReadOnly(), this.b);
    }
}
