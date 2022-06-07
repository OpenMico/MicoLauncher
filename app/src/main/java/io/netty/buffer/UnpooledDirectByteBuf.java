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
public class UnpooledDirectByteBuf extends AbstractReferenceCountedByteBuf {
    private final ByteBufAllocator d;
    private ByteBuffer e;
    private ByteBuffer f;
    private int g;
    private boolean h;

    @Override // io.netty.buffer.ByteBuf
    public boolean hasArray() {
        return false;
    }

    @Override // io.netty.buffer.ByteBuf
    public boolean hasMemoryAddress() {
        return false;
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

    /* JADX INFO: Access modifiers changed from: protected */
    public UnpooledDirectByteBuf(ByteBufAllocator byteBufAllocator, int i, int i2) {
        super(i2);
        if (byteBufAllocator == null) {
            throw new NullPointerException("alloc");
        } else if (i < 0) {
            throw new IllegalArgumentException("initialCapacity: " + i);
        } else if (i2 < 0) {
            throw new IllegalArgumentException("maxCapacity: " + i2);
        } else if (i <= i2) {
            this.d = byteBufAllocator;
            a(ByteBuffer.allocateDirect(i));
        } else {
            throw new IllegalArgumentException(String.format("initialCapacity(%d) > maxCapacity(%d)", Integer.valueOf(i), Integer.valueOf(i2)));
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public UnpooledDirectByteBuf(ByteBufAllocator byteBufAllocator, ByteBuffer byteBuffer, int i) {
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
                this.d = byteBufAllocator;
                this.h = true;
                a(byteBuffer.slice().order(ByteOrder.BIG_ENDIAN));
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

    private void a(ByteBuffer byteBuffer) {
        ByteBuffer byteBuffer2 = this.e;
        if (byteBuffer2 != null) {
            if (this.h) {
                this.h = false;
            } else {
                freeDirect(byteBuffer2);
            }
        }
        this.e = byteBuffer;
        this.f = null;
        this.g = byteBuffer.remaining();
    }

    @Override // io.netty.buffer.ByteBuf
    public int capacity() {
        return this.g;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf capacity(int i) {
        ensureAccessible();
        if (i < 0 || i > maxCapacity()) {
            throw new IllegalArgumentException("newCapacity: " + i);
        }
        int readerIndex = readerIndex();
        int writerIndex = writerIndex();
        int i2 = this.g;
        if (i > i2) {
            ByteBuffer byteBuffer = this.e;
            ByteBuffer allocateDirect = allocateDirect(i);
            byteBuffer.position(0).limit(byteBuffer.capacity());
            allocateDirect.position(0).limit(byteBuffer.capacity());
            allocateDirect.put(byteBuffer);
            allocateDirect.clear();
            a(allocateDirect);
        } else if (i < i2) {
            ByteBuffer byteBuffer2 = this.e;
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
            a(allocateDirect2);
        }
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBufAllocator alloc() {
        return this.d;
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
        throw new UnsupportedOperationException();
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public byte getByte(int i) {
        ensureAccessible();
        return _getByte(i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.AbstractByteBuf
    public byte _getByte(int i) {
        return this.e.get(i);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public short getShort(int i) {
        ensureAccessible();
        return _getShort(i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.AbstractByteBuf
    public short _getShort(int i) {
        return this.e.getShort(i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.AbstractByteBuf
    public short _getShortLE(int i) {
        return ByteBufUtil.swapShort(this.e.getShort(i));
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public int getUnsignedMedium(int i) {
        ensureAccessible();
        return _getUnsignedMedium(i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.AbstractByteBuf
    public int _getUnsignedMedium(int i) {
        return (getByte(i + 2) & 255) | ((getByte(i) & 255) << 16) | ((getByte(i + 1) & 255) << 8);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.AbstractByteBuf
    public int _getUnsignedMediumLE(int i) {
        return ((getByte(i + 2) & 255) << 16) | (getByte(i) & 255) | ((getByte(i + 1) & 255) << 8);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public int getInt(int i) {
        ensureAccessible();
        return _getInt(i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.AbstractByteBuf
    public int _getInt(int i) {
        return this.e.getInt(i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.AbstractByteBuf
    public int _getIntLE(int i) {
        return ByteBufUtil.swapInt(this.e.getInt(i));
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public long getLong(int i) {
        ensureAccessible();
        return _getLong(i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.AbstractByteBuf
    public long _getLong(int i) {
        return this.e.getLong(i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.AbstractByteBuf
    public long _getLongLE(int i) {
        return ByteBufUtil.swapLong(this.e.getLong(i));
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
        a(i, bArr, i2, i3, false);
        return this;
    }

    private void a(int i, byte[] bArr, int i2, int i3, boolean z) {
        ByteBuffer byteBuffer;
        checkDstIndex(i, i3, i2, bArr.length);
        if (z) {
            byteBuffer = b();
        } else {
            byteBuffer = this.e.duplicate();
        }
        byteBuffer.clear().position(i).limit(i + i3);
        byteBuffer.get(bArr, i2, i3);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public ByteBuf readBytes(byte[] bArr, int i, int i2) {
        checkReadableBytes(i2);
        a(this.b, bArr, i, i2, true);
        this.b += i2;
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf getBytes(int i, ByteBuffer byteBuffer) {
        a(i, byteBuffer, false);
        return this;
    }

    private void a(int i, ByteBuffer byteBuffer, boolean z) {
        ByteBuffer byteBuffer2;
        checkIndex(i);
        if (byteBuffer != null) {
            int min = Math.min(capacity() - i, byteBuffer.remaining());
            if (z) {
                byteBuffer2 = b();
            } else {
                byteBuffer2 = this.e.duplicate();
            }
            byteBuffer2.clear().position(i).limit(i + min);
            byteBuffer.put(byteBuffer2);
            return;
        }
        throw new NullPointerException("dst");
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public ByteBuf readBytes(ByteBuffer byteBuffer) {
        int remaining = byteBuffer.remaining();
        checkReadableBytes(remaining);
        a(this.b, byteBuffer, true);
        this.b += remaining;
        return this;
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public ByteBuf setByte(int i, int i2) {
        ensureAccessible();
        _setByte(i, i2);
        return this;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.AbstractByteBuf
    public void _setByte(int i, int i2) {
        this.e.put(i, (byte) i2);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public ByteBuf setShort(int i, int i2) {
        ensureAccessible();
        _setShort(i, i2);
        return this;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.AbstractByteBuf
    public void _setShort(int i, int i2) {
        this.e.putShort(i, (short) i2);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.AbstractByteBuf
    public void _setShortLE(int i, int i2) {
        this.e.putShort(i, ByteBufUtil.swapShort((short) i2));
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public ByteBuf setMedium(int i, int i2) {
        ensureAccessible();
        _setMedium(i, i2);
        return this;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.AbstractByteBuf
    public void _setMedium(int i, int i2) {
        setByte(i, (byte) (i2 >>> 16));
        setByte(i + 1, (byte) (i2 >>> 8));
        setByte(i + 2, (byte) i2);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.AbstractByteBuf
    public void _setMediumLE(int i, int i2) {
        setByte(i, (byte) i2);
        setByte(i + 1, (byte) (i2 >>> 8));
        setByte(i + 2, (byte) (i2 >>> 16));
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public ByteBuf setInt(int i, int i2) {
        ensureAccessible();
        _setInt(i, i2);
        return this;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.AbstractByteBuf
    public void _setInt(int i, int i2) {
        this.e.putInt(i, i2);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.AbstractByteBuf
    public void _setIntLE(int i, int i2) {
        this.e.putInt(i, ByteBufUtil.swapInt(i2));
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public ByteBuf setLong(int i, long j) {
        ensureAccessible();
        _setLong(i, j);
        return this;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.AbstractByteBuf
    public void _setLong(int i, long j) {
        this.e.putLong(i, j);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.AbstractByteBuf
    public void _setLongLE(int i, long j) {
        this.e.putLong(i, ByteBufUtil.swapLong(j));
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf setBytes(int i, ByteBuf byteBuf, int i2, int i3) {
        checkSrcIndex(i, i3, i2, byteBuf.capacity());
        if (byteBuf.nioBufferCount() > 0) {
            ByteBuffer[] nioBuffers = byteBuf.nioBuffers(i2, i3);
            for (ByteBuffer byteBuffer : nioBuffers) {
                int remaining = byteBuffer.remaining();
                setBytes(i, byteBuffer);
                i += remaining;
            }
        } else {
            byteBuf.getBytes(i2, this, i, i3);
        }
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf setBytes(int i, byte[] bArr, int i2, int i3) {
        checkSrcIndex(i, i3, i2, bArr.length);
        ByteBuffer b = b();
        b.clear().position(i).limit(i + i3);
        b.put(bArr, i2, i3);
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf setBytes(int i, ByteBuffer byteBuffer) {
        ensureAccessible();
        ByteBuffer b = b();
        if (byteBuffer == b) {
            byteBuffer = byteBuffer.duplicate();
        }
        b.clear().position(i).limit(i + byteBuffer.remaining());
        b.put(byteBuffer);
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf getBytes(int i, OutputStream outputStream, int i2) throws IOException {
        a(i, outputStream, i2, false);
        return this;
    }

    private void a(int i, OutputStream outputStream, int i2, boolean z) throws IOException {
        ByteBuffer byteBuffer;
        ensureAccessible();
        if (i2 != 0) {
            if (this.e.hasArray()) {
                outputStream.write(this.e.array(), i + this.e.arrayOffset(), i2);
                return;
            }
            byte[] bArr = new byte[i2];
            if (z) {
                byteBuffer = b();
            } else {
                byteBuffer = this.e.duplicate();
            }
            byteBuffer.clear().position(i);
            byteBuffer.get(bArr);
            outputStream.write(bArr);
        }
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public ByteBuf readBytes(OutputStream outputStream, int i) throws IOException {
        checkReadableBytes(i);
        a(this.b, outputStream, i, true);
        this.b += i;
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
            byteBuffer = this.e.duplicate();
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
        ByteBuffer b = z ? b() : this.e.duplicate();
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
        ensureAccessible();
        if (this.e.hasArray()) {
            return inputStream.read(this.e.array(), this.e.arrayOffset() + i, i2);
        }
        byte[] bArr = new byte[i2];
        int read = inputStream.read(bArr);
        if (read <= 0) {
            return read;
        }
        ByteBuffer b = b();
        b.clear().position(i);
        b.put(bArr, 0, read);
        return read;
    }

    @Override // io.netty.buffer.ByteBuf
    public int setBytes(int i, ScatteringByteChannel scatteringByteChannel, int i2) throws IOException {
        ensureAccessible();
        b().clear().position(i).limit(i + i2);
        try {
            return scatteringByteChannel.read(this.f);
        } catch (ClosedChannelException unused) {
            return -1;
        }
    }

    @Override // io.netty.buffer.ByteBuf
    public int setBytes(int i, FileChannel fileChannel, long j, int i2) throws IOException {
        ensureAccessible();
        b().clear().position(i).limit(i + i2);
        try {
            return fileChannel.read(this.f, j);
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
        ensureAccessible();
        try {
            return alloc().directBuffer(i2, maxCapacity()).writeBytes((ByteBuffer) this.e.duplicate().clear().position(i).limit(i + i2));
        } catch (IllegalArgumentException unused) {
            throw new IndexOutOfBoundsException("Too many bytes to read - Need " + (i + i2));
        }
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuffer internalNioBuffer(int i, int i2) {
        checkIndex(i, i2);
        return (ByteBuffer) b().clear().position(i).limit(i + i2);
    }

    private ByteBuffer b() {
        ByteBuffer byteBuffer = this.f;
        if (byteBuffer != null) {
            return byteBuffer;
        }
        ByteBuffer duplicate = this.e.duplicate();
        this.f = duplicate;
        return duplicate;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuffer nioBuffer(int i, int i2) {
        checkIndex(i, i2);
        return ((ByteBuffer) this.e.duplicate().position(i).limit(i + i2)).slice();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.AbstractReferenceCountedByteBuf
    public void deallocate() {
        ByteBuffer byteBuffer = this.e;
        if (byteBuffer != null) {
            this.e = null;
            if (!this.h) {
                freeDirect(byteBuffer);
            }
        }
    }
}
