package io.netty.buffer;

import java.nio.ByteOrder;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: UnreleasableByteBuf.java */
/* loaded from: classes4.dex */
public final class aa extends ae {
    private SwappedByteBuf b;

    @Override // io.netty.buffer.ae, io.netty.util.ReferenceCounted
    public boolean release() {
        return false;
    }

    @Override // io.netty.buffer.ae, io.netty.util.ReferenceCounted
    public boolean release(int i) {
        return false;
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf, io.netty.util.ReferenceCounted
    public ByteBuf retain() {
        return this;
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf, io.netty.util.ReferenceCounted
    public ByteBuf retain(int i) {
        return this;
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf, io.netty.util.ReferenceCounted
    public ByteBuf touch() {
        return this;
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf, io.netty.util.ReferenceCounted
    public ByteBuf touch(Object obj) {
        return this;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public aa(ByteBuf byteBuf) {
        super(byteBuf);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public ByteBuf order(ByteOrder byteOrder) {
        if (byteOrder == null) {
            throw new NullPointerException("endianness");
        } else if (byteOrder == order()) {
            return this;
        } else {
            SwappedByteBuf swappedByteBuf = this.b;
            if (swappedByteBuf != null) {
                return swappedByteBuf;
            }
            SwappedByteBuf swappedByteBuf2 = new SwappedByteBuf(this);
            this.b = swappedByteBuf2;
            return swappedByteBuf2;
        }
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public ByteBuf asReadOnly() {
        return new aa(this.a.asReadOnly());
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public ByteBuf readSlice(int i) {
        return new aa(this.a.readSlice(i));
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public ByteBuf readRetainedSlice(int i) {
        return new aa(this.a.readRetainedSlice(i));
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public ByteBuf slice() {
        return new aa(this.a.slice());
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public ByteBuf retainedSlice() {
        return new aa(this.a.retainedSlice());
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public ByteBuf slice(int i, int i2) {
        return new aa(this.a.slice(i, i2));
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public ByteBuf retainedSlice(int i, int i2) {
        return new aa(this.a.retainedSlice(i, i2));
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public ByteBuf duplicate() {
        return new aa(this.a.duplicate());
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public ByteBuf retainedDuplicate() {
        return new aa(this.a.retainedDuplicate());
    }
}
