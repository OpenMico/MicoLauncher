package io.netty.buffer;

import io.netty.util.Recycler;
import io.netty.util.internal.PlatformDependent;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: PooledUnsafeHeapByteBuf.java */
/* loaded from: classes4.dex */
public final class s extends p {
    private static final Recycler<s> l = new Recycler<s>() { // from class: io.netty.buffer.s.1
        /* JADX INFO: Access modifiers changed from: protected */
        /* renamed from: a */
        public s newObject(Recycler.Handle<s> handle) {
            return new s(handle, 0);
        }
    };

    /* JADX INFO: Access modifiers changed from: package-private */
    public static s d(int i) {
        s sVar = l.get();
        sVar.a(i);
        return sVar;
    }

    private s(Recycler.Handle<s> handle, int i) {
        super(handle, i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.p, io.netty.buffer.AbstractByteBuf
    public byte _getByte(int i) {
        return ab.a((byte[]) this.f, b(i));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.p, io.netty.buffer.AbstractByteBuf
    public short _getShort(int i) {
        return ab.b((byte[]) this.f, b(i));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.p, io.netty.buffer.AbstractByteBuf
    public short _getShortLE(int i) {
        return ab.c((byte[]) this.f, b(i));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.p, io.netty.buffer.AbstractByteBuf
    public int _getUnsignedMedium(int i) {
        return ab.d((byte[]) this.f, b(i));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.p, io.netty.buffer.AbstractByteBuf
    public int _getUnsignedMediumLE(int i) {
        return ab.e((byte[]) this.f, b(i));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.p, io.netty.buffer.AbstractByteBuf
    public int _getInt(int i) {
        return ab.f((byte[]) this.f, b(i));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.p, io.netty.buffer.AbstractByteBuf
    public int _getIntLE(int i) {
        return ab.g((byte[]) this.f, b(i));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.p, io.netty.buffer.AbstractByteBuf
    public long _getLong(int i) {
        return ab.h((byte[]) this.f, b(i));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.p, io.netty.buffer.AbstractByteBuf
    public long _getLongLE(int i) {
        return ab.i((byte[]) this.f, b(i));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.p, io.netty.buffer.AbstractByteBuf
    public void _setByte(int i, int i2) {
        ab.a((byte[]) this.f, b(i), i2);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.p, io.netty.buffer.AbstractByteBuf
    public void _setShort(int i, int i2) {
        ab.b((byte[]) this.f, b(i), i2);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.p, io.netty.buffer.AbstractByteBuf
    public void _setShortLE(int i, int i2) {
        ab.c((byte[]) this.f, b(i), i2);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.p, io.netty.buffer.AbstractByteBuf
    public void _setMedium(int i, int i2) {
        ab.d((byte[]) this.f, b(i), i2);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.p, io.netty.buffer.AbstractByteBuf
    public void _setMediumLE(int i, int i2) {
        ab.e((byte[]) this.f, b(i), i2);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.p, io.netty.buffer.AbstractByteBuf
    public void _setInt(int i, int i2) {
        ab.f((byte[]) this.f, b(i), i2);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.p, io.netty.buffer.AbstractByteBuf
    public void _setIntLE(int i, int i2) {
        ab.g((byte[]) this.f, b(i), i2);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.p, io.netty.buffer.AbstractByteBuf
    public void _setLong(int i, long j) {
        ab.a((byte[]) this.f, b(i), j);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.p, io.netty.buffer.AbstractByteBuf
    public void _setLongLE(int i, long j) {
        ab.b((byte[]) this.f, b(i), j);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public ByteBuf setZero(int i, int i2) {
        if (PlatformDependent.javaVersion() < 7) {
            return super.setZero(i, i2);
        }
        c(i, i2);
        return this;
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public ByteBuf writeZero(int i) {
        if (PlatformDependent.javaVersion() < 7) {
            return super.writeZero(i);
        }
        ensureWritable(i);
        int i2 = this.c;
        c(i2, i);
        this.c = i2 + i;
        return this;
    }

    private void c(int i, int i2) {
        checkIndex(i, i2);
        ab.h((byte[]) this.f, b(i), i2);
    }

    @Override // io.netty.buffer.AbstractByteBuf
    @Deprecated
    protected SwappedByteBuf newSwappedByteBuf() {
        if (PlatformDependent.isUnaligned()) {
            return new ad(this);
        }
        return super.newSwappedByteBuf();
    }
}
