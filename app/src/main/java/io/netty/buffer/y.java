package io.netty.buffer;

import io.netty.util.internal.PlatformDependent;

/* compiled from: UnpooledUnsafeHeapByteBuf.java */
/* loaded from: classes4.dex */
final class y extends UnpooledHeapByteBuf {
    /* JADX INFO: Access modifiers changed from: package-private */
    public y(ByteBufAllocator byteBufAllocator, int i, int i2) {
        super(byteBufAllocator, i, i2);
    }

    @Override // io.netty.buffer.UnpooledHeapByteBuf, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public byte getByte(int i) {
        checkIndex(i);
        return _getByte(i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.UnpooledHeapByteBuf, io.netty.buffer.AbstractByteBuf
    public byte _getByte(int i) {
        return ab.a(this.d, i);
    }

    @Override // io.netty.buffer.UnpooledHeapByteBuf, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public short getShort(int i) {
        checkIndex(i, 2);
        return _getShort(i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.UnpooledHeapByteBuf, io.netty.buffer.AbstractByteBuf
    public short _getShort(int i) {
        return ab.b(this.d, i);
    }

    @Override // io.netty.buffer.UnpooledHeapByteBuf, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public short getShortLE(int i) {
        checkIndex(i, 2);
        return _getShortLE(i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.UnpooledHeapByteBuf, io.netty.buffer.AbstractByteBuf
    public short _getShortLE(int i) {
        return ab.c(this.d, i);
    }

    @Override // io.netty.buffer.UnpooledHeapByteBuf, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public int getUnsignedMedium(int i) {
        checkIndex(i, 3);
        return _getUnsignedMedium(i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.UnpooledHeapByteBuf, io.netty.buffer.AbstractByteBuf
    public int _getUnsignedMedium(int i) {
        return ab.d(this.d, i);
    }

    @Override // io.netty.buffer.UnpooledHeapByteBuf, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public int getUnsignedMediumLE(int i) {
        checkIndex(i, 3);
        return _getUnsignedMediumLE(i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.UnpooledHeapByteBuf, io.netty.buffer.AbstractByteBuf
    public int _getUnsignedMediumLE(int i) {
        return ab.e(this.d, i);
    }

    @Override // io.netty.buffer.UnpooledHeapByteBuf, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public int getInt(int i) {
        checkIndex(i, 4);
        return _getInt(i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.UnpooledHeapByteBuf, io.netty.buffer.AbstractByteBuf
    public int _getInt(int i) {
        return ab.f(this.d, i);
    }

    @Override // io.netty.buffer.UnpooledHeapByteBuf, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public int getIntLE(int i) {
        checkIndex(i, 4);
        return _getIntLE(i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.UnpooledHeapByteBuf, io.netty.buffer.AbstractByteBuf
    public int _getIntLE(int i) {
        return ab.g(this.d, i);
    }

    @Override // io.netty.buffer.UnpooledHeapByteBuf, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public long getLong(int i) {
        checkIndex(i, 8);
        return _getLong(i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.UnpooledHeapByteBuf, io.netty.buffer.AbstractByteBuf
    public long _getLong(int i) {
        return ab.h(this.d, i);
    }

    @Override // io.netty.buffer.UnpooledHeapByteBuf, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public long getLongLE(int i) {
        checkIndex(i, 8);
        return _getLongLE(i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.UnpooledHeapByteBuf, io.netty.buffer.AbstractByteBuf
    public long _getLongLE(int i) {
        return ab.i(this.d, i);
    }

    @Override // io.netty.buffer.UnpooledHeapByteBuf, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public ByteBuf setByte(int i, int i2) {
        checkIndex(i);
        _setByte(i, i2);
        return this;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.UnpooledHeapByteBuf, io.netty.buffer.AbstractByteBuf
    public void _setByte(int i, int i2) {
        ab.a(this.d, i, i2);
    }

    @Override // io.netty.buffer.UnpooledHeapByteBuf, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public ByteBuf setShort(int i, int i2) {
        checkIndex(i, 2);
        _setShort(i, i2);
        return this;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.UnpooledHeapByteBuf, io.netty.buffer.AbstractByteBuf
    public void _setShort(int i, int i2) {
        ab.b(this.d, i, i2);
    }

    @Override // io.netty.buffer.UnpooledHeapByteBuf, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public ByteBuf setShortLE(int i, int i2) {
        checkIndex(i, 2);
        _setShortLE(i, i2);
        return this;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.UnpooledHeapByteBuf, io.netty.buffer.AbstractByteBuf
    public void _setShortLE(int i, int i2) {
        ab.c(this.d, i, i2);
    }

    @Override // io.netty.buffer.UnpooledHeapByteBuf, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public ByteBuf setMedium(int i, int i2) {
        checkIndex(i, 3);
        _setMedium(i, i2);
        return this;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.UnpooledHeapByteBuf, io.netty.buffer.AbstractByteBuf
    public void _setMedium(int i, int i2) {
        ab.d(this.d, i, i2);
    }

    @Override // io.netty.buffer.UnpooledHeapByteBuf, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public ByteBuf setMediumLE(int i, int i2) {
        checkIndex(i, 3);
        _setMediumLE(i, i2);
        return this;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.UnpooledHeapByteBuf, io.netty.buffer.AbstractByteBuf
    public void _setMediumLE(int i, int i2) {
        ab.e(this.d, i, i2);
    }

    @Override // io.netty.buffer.UnpooledHeapByteBuf, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public ByteBuf setInt(int i, int i2) {
        checkIndex(i, 4);
        _setInt(i, i2);
        return this;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.UnpooledHeapByteBuf, io.netty.buffer.AbstractByteBuf
    public void _setInt(int i, int i2) {
        ab.f(this.d, i, i2);
    }

    @Override // io.netty.buffer.UnpooledHeapByteBuf, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public ByteBuf setIntLE(int i, int i2) {
        checkIndex(i, 4);
        _setIntLE(i, i2);
        return this;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.UnpooledHeapByteBuf, io.netty.buffer.AbstractByteBuf
    public void _setIntLE(int i, int i2) {
        ab.g(this.d, i, i2);
    }

    @Override // io.netty.buffer.UnpooledHeapByteBuf, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public ByteBuf setLong(int i, long j) {
        checkIndex(i, 8);
        _setLong(i, j);
        return this;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.UnpooledHeapByteBuf, io.netty.buffer.AbstractByteBuf
    public void _setLong(int i, long j) {
        ab.a(this.d, i, j);
    }

    @Override // io.netty.buffer.UnpooledHeapByteBuf, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public ByteBuf setLongLE(int i, long j) {
        checkIndex(i, 8);
        _setLongLE(i, j);
        return this;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.UnpooledHeapByteBuf, io.netty.buffer.AbstractByteBuf
    public void _setLongLE(int i, long j) {
        ab.b(this.d, i, j);
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
        ab.h(this.d, i, i2);
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
