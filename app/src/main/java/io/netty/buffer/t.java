package io.netty.buffer;

import io.netty.util.internal.StringUtil;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ReadOnlyBufferException;
import java.nio.channels.FileChannel;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;

/* compiled from: ReadOnlyByteBufferBuf.java */
/* loaded from: classes4.dex */
public class t extends AbstractReferenceCountedByteBuf {
    protected final ByteBuffer d;
    private final ByteBufAllocator e;
    private ByteBuffer f;

    @Override // io.netty.buffer.AbstractReferenceCountedByteBuf
    protected void deallocate() {
    }

    @Override // io.netty.buffer.ByteBuf
    public boolean hasMemoryAddress() {
        return false;
    }

    @Override // io.netty.buffer.ByteBuf
    public int nioBufferCount() {
        return 1;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf unwrap() {
        return null;
    }

    public t(ByteBufAllocator byteBufAllocator, ByteBuffer byteBuffer) {
        super(byteBuffer.remaining());
        if (byteBuffer.isReadOnly()) {
            this.e = byteBufAllocator;
            this.d = byteBuffer.slice().order(ByteOrder.BIG_ENDIAN);
            writerIndex(this.d.limit());
            return;
        }
        throw new IllegalArgumentException("must be a readonly buffer: " + StringUtil.simpleClassName(byteBuffer));
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public byte getByte(int i) {
        ensureAccessible();
        return _getByte(i);
    }

    @Override // io.netty.buffer.AbstractByteBuf
    public byte _getByte(int i) {
        return this.d.get(i);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public short getShort(int i) {
        ensureAccessible();
        return _getShort(i);
    }

    @Override // io.netty.buffer.AbstractByteBuf
    public short _getShort(int i) {
        return this.d.getShort(i);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public short getShortLE(int i) {
        ensureAccessible();
        return _getShortLE(i);
    }

    @Override // io.netty.buffer.AbstractByteBuf
    public short _getShortLE(int i) {
        return ByteBufUtil.swapShort(this.d.getShort(i));
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public int getUnsignedMedium(int i) {
        ensureAccessible();
        return _getUnsignedMedium(i);
    }

    @Override // io.netty.buffer.AbstractByteBuf
    public int _getUnsignedMedium(int i) {
        return (getByte(i + 2) & 255) | ((getByte(i) & 255) << 16) | ((getByte(i + 1) & 255) << 8);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public int getUnsignedMediumLE(int i) {
        ensureAccessible();
        return _getUnsignedMediumLE(i);
    }

    @Override // io.netty.buffer.AbstractByteBuf
    public int _getUnsignedMediumLE(int i) {
        return ((getByte(i + 2) & 255) << 16) | (getByte(i) & 255) | ((getByte(i + 1) & 255) << 8);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public int getInt(int i) {
        ensureAccessible();
        return _getInt(i);
    }

    @Override // io.netty.buffer.AbstractByteBuf
    public int _getInt(int i) {
        return this.d.getInt(i);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public int getIntLE(int i) {
        ensureAccessible();
        return _getIntLE(i);
    }

    @Override // io.netty.buffer.AbstractByteBuf
    public int _getIntLE(int i) {
        return ByteBufUtil.swapInt(this.d.getInt(i));
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public long getLong(int i) {
        ensureAccessible();
        return _getLong(i);
    }

    @Override // io.netty.buffer.AbstractByteBuf
    public long _getLong(int i) {
        return this.d.getLong(i);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public long getLongLE(int i) {
        ensureAccessible();
        return _getLongLE(i);
    }

    @Override // io.netty.buffer.AbstractByteBuf
    public long _getLongLE(int i) {
        return ByteBufUtil.swapLong(this.d.getLong(i));
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf getBytes(int i, ByteBuf byteBuf, int i2, int i3) {
        checkDstIndex(i, i3, i2, byteBuf.capacity());
        if (byteBuf.hasArray()) {
            getBytes(i, byteBuf.array(), byteBuf.arrayOffset() + i2, i3);
        } else if (byteBuf.nioBufferCount() > 0) {
            ByteBuffer[] nioBuffers = byteBuf.nioBuffers(i2, i3);
            for (ByteBuffer byteBuffer : nioBuffers) {
                int remaining = byteBuffer.remaining();
                getBytes(i, byteBuffer);
                i += remaining;
            }
        } else {
            byteBuf.setBytes(i2, this, i, i3);
        }
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf getBytes(int i, byte[] bArr, int i2, int i3) {
        checkDstIndex(i, i3, i2, bArr.length);
        if (i2 < 0 || i2 > bArr.length - i3) {
            throw new IndexOutOfBoundsException(String.format("dstIndex: %d, length: %d (expected: range(0, %d))", Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(bArr.length)));
        }
        ByteBuffer b = b();
        b.clear().position(i).limit(i + i3);
        b.get(bArr, i2, i3);
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
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

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public ByteBuf setByte(int i, int i2) {
        throw new ReadOnlyBufferException();
    }

    @Override // io.netty.buffer.AbstractByteBuf
    public void _setByte(int i, int i2) {
        throw new ReadOnlyBufferException();
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public ByteBuf setShort(int i, int i2) {
        throw new ReadOnlyBufferException();
    }

    @Override // io.netty.buffer.AbstractByteBuf
    public void _setShort(int i, int i2) {
        throw new ReadOnlyBufferException();
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public ByteBuf setShortLE(int i, int i2) {
        throw new ReadOnlyBufferException();
    }

    @Override // io.netty.buffer.AbstractByteBuf
    public void _setShortLE(int i, int i2) {
        throw new ReadOnlyBufferException();
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public ByteBuf setMedium(int i, int i2) {
        throw new ReadOnlyBufferException();
    }

    @Override // io.netty.buffer.AbstractByteBuf
    public void _setMedium(int i, int i2) {
        throw new ReadOnlyBufferException();
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public ByteBuf setMediumLE(int i, int i2) {
        throw new ReadOnlyBufferException();
    }

    @Override // io.netty.buffer.AbstractByteBuf
    public void _setMediumLE(int i, int i2) {
        throw new ReadOnlyBufferException();
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public ByteBuf setInt(int i, int i2) {
        throw new ReadOnlyBufferException();
    }

    @Override // io.netty.buffer.AbstractByteBuf
    public void _setInt(int i, int i2) {
        throw new ReadOnlyBufferException();
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public ByteBuf setIntLE(int i, int i2) {
        throw new ReadOnlyBufferException();
    }

    @Override // io.netty.buffer.AbstractByteBuf
    public void _setIntLE(int i, int i2) {
        throw new ReadOnlyBufferException();
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public ByteBuf setLong(int i, long j) {
        throw new ReadOnlyBufferException();
    }

    @Override // io.netty.buffer.AbstractByteBuf
    public void _setLong(int i, long j) {
        throw new ReadOnlyBufferException();
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public ByteBuf setLongLE(int i, long j) {
        throw new ReadOnlyBufferException();
    }

    @Override // io.netty.buffer.AbstractByteBuf
    public void _setLongLE(int i, long j) {
        throw new ReadOnlyBufferException();
    }

    @Override // io.netty.buffer.ByteBuf
    public int capacity() {
        return maxCapacity();
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf capacity(int i) {
        throw new ReadOnlyBufferException();
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBufAllocator alloc() {
        return this.e;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteOrder order() {
        return ByteOrder.BIG_ENDIAN;
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public boolean isReadOnly() {
        return this.d.isReadOnly();
    }

    @Override // io.netty.buffer.ByteBuf
    public boolean isDirect() {
        return this.d.isDirect();
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf getBytes(int i, OutputStream outputStream, int i2) throws IOException {
        ensureAccessible();
        if (i2 == 0) {
            return this;
        }
        if (this.d.hasArray()) {
            outputStream.write(this.d.array(), i + this.d.arrayOffset(), i2);
        } else {
            byte[] bArr = new byte[i2];
            ByteBuffer b = b();
            b.clear().position(i);
            b.get(bArr);
            outputStream.write(bArr);
        }
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public int getBytes(int i, GatheringByteChannel gatheringByteChannel, int i2) throws IOException {
        ensureAccessible();
        if (i2 == 0) {
            return 0;
        }
        ByteBuffer b = b();
        b.clear().position(i).limit(i + i2);
        return gatheringByteChannel.write(b);
    }

    @Override // io.netty.buffer.ByteBuf
    public int getBytes(int i, FileChannel fileChannel, long j, int i2) throws IOException {
        ensureAccessible();
        if (i2 == 0) {
            return 0;
        }
        ByteBuffer b = b();
        b.clear().position(i).limit(i + i2);
        return fileChannel.write(b, j);
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf setBytes(int i, ByteBuf byteBuf, int i2, int i3) {
        throw new ReadOnlyBufferException();
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf setBytes(int i, byte[] bArr, int i2, int i3) {
        throw new ReadOnlyBufferException();
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf setBytes(int i, ByteBuffer byteBuffer) {
        throw new ReadOnlyBufferException();
    }

    @Override // io.netty.buffer.ByteBuf
    public int setBytes(int i, InputStream inputStream, int i2) throws IOException {
        throw new ReadOnlyBufferException();
    }

    @Override // io.netty.buffer.ByteBuf
    public int setBytes(int i, ScatteringByteChannel scatteringByteChannel, int i2) throws IOException {
        throw new ReadOnlyBufferException();
    }

    @Override // io.netty.buffer.ByteBuf
    public int setBytes(int i, FileChannel fileChannel, long j, int i2) throws IOException {
        throw new ReadOnlyBufferException();
    }

    protected final ByteBuffer b() {
        ByteBuffer byteBuffer = this.f;
        if (byteBuffer != null) {
            return byteBuffer;
        }
        ByteBuffer duplicate = this.d.duplicate();
        this.f = duplicate;
        return duplicate;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf copy(int i, int i2) {
        ensureAccessible();
        try {
            ByteBuffer byteBuffer = (ByteBuffer) b().clear().position(i).limit(i + i2);
            ByteBuffer allocateDirect = ByteBuffer.allocateDirect(i2);
            allocateDirect.put(byteBuffer);
            allocateDirect.order(order());
            allocateDirect.clear();
            return new UnpooledDirectByteBuf(alloc(), allocateDirect, maxCapacity());
        } catch (IllegalArgumentException unused) {
            throw new IndexOutOfBoundsException("Too many bytes to read - Need " + (i + i2));
        }
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuffer[] nioBuffers(int i, int i2) {
        return new ByteBuffer[]{nioBuffer(i, i2)};
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuffer nioBuffer(int i, int i2) {
        return (ByteBuffer) this.d.duplicate().position(i).limit(i + i2);
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuffer internalNioBuffer(int i, int i2) {
        ensureAccessible();
        return (ByteBuffer) b().clear().position(i).limit(i + i2);
    }

    @Override // io.netty.buffer.ByteBuf
    public boolean hasArray() {
        return this.d.hasArray();
    }

    @Override // io.netty.buffer.ByteBuf
    public byte[] array() {
        return this.d.array();
    }

    @Override // io.netty.buffer.ByteBuf
    public int arrayOffset() {
        return this.d.arrayOffset();
    }

    @Override // io.netty.buffer.ByteBuf
    public long memoryAddress() {
        throw new UnsupportedOperationException();
    }
}
