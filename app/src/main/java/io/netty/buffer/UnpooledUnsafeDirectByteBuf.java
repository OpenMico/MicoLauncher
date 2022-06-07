package io.netty.buffer;

import io.netty.util.internal.PlatformDependent;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.FileChannel;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;

/* loaded from: classes4.dex */
public class UnpooledUnsafeDirectByteBuf extends AbstractReferenceCountedByteBuf {
    ByteBuffer d;
    private final ByteBufAllocator e;
    private long f;
    private ByteBuffer g;
    private int h;
    private boolean i;

    @Override // io.netty.buffer.ByteBuf
    public boolean hasArray() {
        return false;
    }

    @Override // io.netty.buffer.ByteBuf
    public boolean hasMemoryAddress() {
        return true;
    }

    @Override // io.netty.buffer.ByteBuf
    public boolean isDirect() {
        return true;
    }

    @Override // io.netty.buffer.ByteBuf
    public int nioBufferCount() {
        return 1;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf unwrap() {
        return null;
    }

    public UnpooledUnsafeDirectByteBuf(ByteBufAllocator byteBufAllocator, int i, int i2) {
        super(i2);
        if (byteBufAllocator == null) {
            throw new NullPointerException("alloc");
        } else if (i < 0) {
            throw new IllegalArgumentException("initialCapacity: " + i);
        } else if (i2 < 0) {
            throw new IllegalArgumentException("maxCapacity: " + i2);
        } else if (i <= i2) {
            this.e = byteBufAllocator;
            a(allocateDirect(i), false);
        } else {
            throw new IllegalArgumentException(String.format("initialCapacity(%d) > maxCapacity(%d)", Integer.valueOf(i), Integer.valueOf(i2)));
        }
    }

    public UnpooledUnsafeDirectByteBuf(ByteBufAllocator byteBufAllocator, ByteBuffer byteBuffer, int i) {
        super(i);
        if (byteBufAllocator == null) {
            throw new NullPointerException("alloc");
        } else if (byteBuffer == null) {
            throw new NullPointerException("initialBuffer");
        } else if (!byteBuffer.isDirect()) {
            throw new IllegalArgumentException("initialBuffer is not a direct buffer.");
        } else if (!byteBuffer.isReadOnly()) {
            int remaining = byteBuffer.remaining();
            if (remaining <= i) {
                this.e = byteBufAllocator;
                this.i = true;
                a(byteBuffer.slice().order(ByteOrder.BIG_ENDIAN), false);
                writerIndex(remaining);
                return;
            }
            throw new IllegalArgumentException(String.format("initialCapacity(%d) > maxCapacity(%d)", Integer.valueOf(remaining), Integer.valueOf(i)));
        } else {
            throw new IllegalArgumentException("initialBuffer is a read-only buffer.");
        }
    }

    protected ByteBuffer allocateDirect(int i) {
        return ByteBuffer.allocateDirect(i);
    }

    protected void freeDirect(ByteBuffer byteBuffer) {
        PlatformDependent.freeDirectBuffer(byteBuffer);
    }

    final void a(ByteBuffer byteBuffer, boolean z) {
        ByteBuffer byteBuffer2;
        if (z && (byteBuffer2 = this.d) != null) {
            if (this.i) {
                this.i = false;
            } else {
                freeDirect(byteBuffer2);
            }
        }
        this.d = byteBuffer;
        this.f = PlatformDependent.directBufferAddress(byteBuffer);
        this.g = null;
        this.h = byteBuffer.remaining();
    }

    @Override // io.netty.buffer.ByteBuf
    public int capacity() {
        return this.h;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf capacity(int i) {
        ensureAccessible();
        if (i < 0 || i > maxCapacity()) {
            throw new IllegalArgumentException("newCapacity: " + i);
        }
        int readerIndex = readerIndex();
        int writerIndex = writerIndex();
        int i2 = this.h;
        if (i > i2) {
            ByteBuffer byteBuffer = this.d;
            ByteBuffer allocateDirect = allocateDirect(i);
            byteBuffer.position(0).limit(byteBuffer.capacity());
            allocateDirect.position(0).limit(byteBuffer.capacity());
            allocateDirect.put(byteBuffer);
            allocateDirect.clear();
            a(allocateDirect, true);
        } else if (i < i2) {
            ByteBuffer byteBuffer2 = this.d;
            ByteBuffer allocateDirect2 = allocateDirect(i);
            if (readerIndex < i) {
                if (writerIndex > i) {
                    writerIndex(i);
                } else {
                    i = writerIndex;
                }
                byteBuffer2.position(readerIndex).limit(i);
                allocateDirect2.position(readerIndex).limit(i);
                allocateDirect2.put(byteBuffer2);
                allocateDirect2.clear();
            } else {
                setIndex(i, i);
            }
            a(allocateDirect2, true);
        }
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBufAllocator alloc() {
        return this.e;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteOrder order() {
        return ByteOrder.BIG_ENDIAN;
    }

    @Override // io.netty.buffer.ByteBuf
    public byte[] array() {
        throw new UnsupportedOperationException("direct buffer");
    }

    @Override // io.netty.buffer.ByteBuf
    public int arrayOffset() {
        throw new UnsupportedOperationException("direct buffer");
    }

    @Override // io.netty.buffer.ByteBuf
    public long memoryAddress() {
        ensureAccessible();
        return this.f;
    }

    @Override // io.netty.buffer.AbstractByteBuf
    public byte _getByte(int i) {
        return ab.a(a(i));
    }

    @Override // io.netty.buffer.AbstractByteBuf
    public short _getShort(int i) {
        return ab.b(a(i));
    }

    @Override // io.netty.buffer.AbstractByteBuf
    public short _getShortLE(int i) {
        return ab.c(a(i));
    }

    @Override // io.netty.buffer.AbstractByteBuf
    public int _getUnsignedMedium(int i) {
        return ab.d(a(i));
    }

    @Override // io.netty.buffer.AbstractByteBuf
    public int _getUnsignedMediumLE(int i) {
        return ab.e(a(i));
    }

    @Override // io.netty.buffer.AbstractByteBuf
    public int _getInt(int i) {
        return ab.f(a(i));
    }

    @Override // io.netty.buffer.AbstractByteBuf
    public int _getIntLE(int i) {
        return ab.g(a(i));
    }

    @Override // io.netty.buffer.AbstractByteBuf
    public long _getLong(int i) {
        return ab.h(a(i));
    }

    @Override // io.netty.buffer.AbstractByteBuf
    public long _getLongLE(int i) {
        return ab.i(a(i));
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf getBytes(int i, ByteBuf byteBuf, int i2, int i3) {
        ab.a(this, a(i), i, byteBuf, i2, i3);
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf getBytes(int i, byte[] bArr, int i2, int i3) {
        ab.a(this, a(i), i, bArr, i2, i3);
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf getBytes(int i, ByteBuffer byteBuffer) {
        ab.a(this, a(i), i, byteBuffer);
        return this;
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public ByteBuf readBytes(ByteBuffer byteBuffer) {
        int remaining = byteBuffer.remaining();
        checkReadableBytes(remaining);
        getBytes(this.b, byteBuffer);
        this.b += remaining;
        return this;
    }

    @Override // io.netty.buffer.AbstractByteBuf
    public void _setByte(int i, int i2) {
        ab.a(a(i), i2);
    }

    @Override // io.netty.buffer.AbstractByteBuf
    public void _setShort(int i, int i2) {
        ab.b(a(i), i2);
    }

    @Override // io.netty.buffer.AbstractByteBuf
    public void _setShortLE(int i, int i2) {
        ab.c(a(i), i2);
    }

    @Override // io.netty.buffer.AbstractByteBuf
    public void _setMedium(int i, int i2) {
        ab.d(a(i), i2);
    }

    @Override // io.netty.buffer.AbstractByteBuf
    public void _setMediumLE(int i, int i2) {
        ab.e(a(i), i2);
    }

    @Override // io.netty.buffer.AbstractByteBuf
    public void _setInt(int i, int i2) {
        ab.f(a(i), i2);
    }

    @Override // io.netty.buffer.AbstractByteBuf
    public void _setIntLE(int i, int i2) {
        ab.g(a(i), i2);
    }

    @Override // io.netty.buffer.AbstractByteBuf
    public void _setLong(int i, long j) {
        ab.a(a(i), j);
    }

    @Override // io.netty.buffer.AbstractByteBuf
    public void _setLongLE(int i, long j) {
        ab.b(a(i), j);
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf setBytes(int i, ByteBuf byteBuf, int i2, int i3) {
        ab.b(this, a(i), i, byteBuf, i2, i3);
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf setBytes(int i, byte[] bArr, int i2, int i3) {
        ab.b(this, a(i), i, bArr, i2, i3);
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf setBytes(int i, ByteBuffer byteBuffer) {
        ab.b(this, a(i), i, byteBuffer);
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf getBytes(int i, OutputStream outputStream, int i2) throws IOException {
        ab.a(this, a(i), i, outputStream, i2);
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public int getBytes(int i, GatheringByteChannel gatheringByteChannel, int i2) throws IOException {
        return a(i, gatheringByteChannel, i2, false);
    }

    private int a(int i, GatheringByteChannel gatheringByteChannel, int i2, boolean z) throws IOException {
        ByteBuffer byteBuffer;
        ensureAccessible();
        if (i2 == 0) {
            return 0;
        }
        if (z) {
            byteBuffer = b();
        } else {
            byteBuffer = this.d.duplicate();
        }
        byteBuffer.clear().position(i).limit(i + i2);
        return gatheringByteChannel.write(byteBuffer);
    }

    @Override // io.netty.buffer.ByteBuf
    public int getBytes(int i, FileChannel fileChannel, long j, int i2) throws IOException {
        return a(i, fileChannel, j, i2, false);
    }

    private int a(int i, FileChannel fileChannel, long j, int i2, boolean z) throws IOException {
        ensureAccessible();
        if (i2 == 0) {
            return 0;
        }
        ByteBuffer b = z ? b() : this.d.duplicate();
        b.clear().position(i).limit(i + i2);
        return fileChannel.write(b, j);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public int readBytes(GatheringByteChannel gatheringByteChannel, int i) throws IOException {
        checkReadableBytes(i);
        int a = a(this.b, gatheringByteChannel, i, true);
        this.b += a;
        return a;
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public int readBytes(FileChannel fileChannel, long j, int i) throws IOException {
        checkReadableBytes(i);
        int a = a(this.b, fileChannel, j, i, true);
        this.b += a;
        return a;
    }

    @Override // io.netty.buffer.ByteBuf
    public int setBytes(int i, InputStream inputStream, int i2) throws IOException {
        return ab.a(this, a(i), i, inputStream, i2);
    }

    @Override // io.netty.buffer.ByteBuf
    public int setBytes(int i, ScatteringByteChannel scatteringByteChannel, int i2) throws IOException {
        ensureAccessible();
        ByteBuffer b = b();
        b.clear().position(i).limit(i + i2);
        try {
            return scatteringByteChannel.read(b);
        } catch (ClosedChannelException unused) {
            return -1;
        }
    }

    @Override // io.netty.buffer.ByteBuf
    public int setBytes(int i, FileChannel fileChannel, long j, int i2) throws IOException {
        ensureAccessible();
        ByteBuffer b = b();
        b.clear().position(i).limit(i + i2);
        try {
            return fileChannel.read(b, j);
        } catch (ClosedChannelException unused) {
            return -1;
        }
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuffer[] nioBuffers(int i, int i2) {
        return new ByteBuffer[]{nioBuffer(i, i2)};
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf copy(int i, int i2) {
        return ab.a(this, a(i), i, i2);
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuffer internalNioBuffer(int i, int i2) {
        checkIndex(i, i2);
        return (ByteBuffer) b().clear().position(i).limit(i + i2);
    }

    private ByteBuffer b() {
        ByteBuffer byteBuffer = this.g;
        if (byteBuffer != null) {
            return byteBuffer;
        }
        ByteBuffer duplicate = this.d.duplicate();
        this.g = duplicate;
        return duplicate;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuffer nioBuffer(int i, int i2) {
        checkIndex(i, i2);
        return ((ByteBuffer) this.d.duplicate().position(i).limit(i + i2)).slice();
    }

    @Override // io.netty.buffer.AbstractReferenceCountedByteBuf
    public void deallocate() {
        ByteBuffer byteBuffer = this.d;
        if (byteBuffer != null) {
            this.d = null;
            if (!this.i) {
                freeDirect(byteBuffer);
            }
        }
    }

    long a(int i) {
        return this.f + i;
    }

    @Override // io.netty.buffer.AbstractByteBuf
    protected SwappedByteBuf newSwappedByteBuf() {
        if (PlatformDependent.isUnaligned()) {
            return new ac(this);
        }
        return super.newSwappedByteBuf();
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public ByteBuf setZero(int i, int i2) {
        ab.b(this, a(i), i, i2);
        return this;
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public ByteBuf writeZero(int i) {
        ensureWritable(i);
        int i2 = this.c;
        setZero(i2, i);
        this.c = i2 + i;
        return this;
    }
}
