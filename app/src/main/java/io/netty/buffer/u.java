package io.netty.buffer;

import io.netty.util.internal.PlatformDependent;
import java.nio.ByteBuffer;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: ReadOnlyUnsafeDirectByteBuf.java */
/* loaded from: classes4.dex */
public final class u extends t {
    private final long e;

    /* JADX INFO: Access modifiers changed from: package-private */
    public u(ByteBufAllocator byteBufAllocator, ByteBuffer byteBuffer) {
        super(byteBufAllocator, byteBuffer);
        this.e = PlatformDependent.directBufferAddress(byteBuffer);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.t, io.netty.buffer.AbstractByteBuf
    public byte _getByte(int i) {
        return ab.a(a(i));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.t, io.netty.buffer.AbstractByteBuf
    public short _getShort(int i) {
        return ab.b(a(i));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.t, io.netty.buffer.AbstractByteBuf
    public int _getUnsignedMedium(int i) {
        return ab.d(a(i));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.t, io.netty.buffer.AbstractByteBuf
    public int _getInt(int i) {
        return ab.f(a(i));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.t, io.netty.buffer.AbstractByteBuf
    public long _getLong(int i) {
        return ab.h(a(i));
    }

    @Override // io.netty.buffer.t, io.netty.buffer.ByteBuf
    public ByteBuf getBytes(int i, ByteBuf byteBuf, int i2, int i3) {
        checkIndex(i, i3);
        if (byteBuf == null) {
            throw new NullPointerException("dst");
        } else if (i2 < 0 || i2 > byteBuf.capacity() - i3) {
            throw new IndexOutOfBoundsException("dstIndex: " + i2);
        } else {
            if (byteBuf.hasMemoryAddress()) {
                PlatformDependent.copyMemory(a(i), i2 + byteBuf.memoryAddress(), i3);
            } else if (byteBuf.hasArray()) {
                PlatformDependent.copyMemory(a(i), byteBuf.array(), byteBuf.arrayOffset() + i2, i3);
            } else {
                byteBuf.setBytes(i2, this, i, i3);
            }
            return this;
        }
    }

    @Override // io.netty.buffer.t, io.netty.buffer.ByteBuf
    public ByteBuf getBytes(int i, byte[] bArr, int i2, int i3) {
        checkIndex(i, i3);
        if (bArr == null) {
            throw new NullPointerException("dst");
        } else if (i2 < 0 || i2 > bArr.length - i3) {
            throw new IndexOutOfBoundsException(String.format("dstIndex: %d, length: %d (expected: range(0, %d))", Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(bArr.length)));
        } else {
            if (i3 != 0) {
                PlatformDependent.copyMemory(a(i), bArr, i2, i3);
            }
            return this;
        }
    }

    @Override // io.netty.buffer.t, io.netty.buffer.ByteBuf
    public ByteBuf getBytes(int i, ByteBuffer byteBuffer) {
        checkIndex(i);
        if (byteBuffer != null) {
            int min = Math.min(capacity() - i, byteBuffer.remaining());
            ByteBuffer b = b();
            b.clear().position(i).limit(i + min);
            byteBuffer.put(b);
            return this;
        }
        throw new NullPointerException("dst");
    }

    @Override // io.netty.buffer.t, io.netty.buffer.ByteBuf
    public ByteBuf copy(int i, int i2) {
        checkIndex(i, i2);
        ByteBuf directBuffer = alloc().directBuffer(i2, maxCapacity());
        if (i2 != 0) {
            if (directBuffer.hasMemoryAddress()) {
                PlatformDependent.copyMemory(a(i), directBuffer.memoryAddress(), i2);
                directBuffer.setIndex(0, i2);
            } else {
                directBuffer.writeBytes(this, i, i2);
            }
        }
        return directBuffer;
    }

    private long a(int i) {
        return this.e + i;
    }
}
