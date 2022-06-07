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
public class UnpooledHeapByteBuf extends AbstractReferenceCountedByteBuf {
    byte[] d;
    private final ByteBufAllocator e;
    private ByteBuffer f;

    @Override // io.netty.buffer.ByteBuf
    public int arrayOffset() {
        return 0;
    }

    @Override // io.netty.buffer.ByteBuf
    public boolean hasArray() {
        return true;
    }

    @Override // io.netty.buffer.ByteBuf
    public boolean hasMemoryAddress() {
        return false;
    }

    @Override // io.netty.buffer.ByteBuf
    public boolean isDirect() {
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

    /* JADX INFO: Access modifiers changed from: protected */
    public UnpooledHeapByteBuf(ByteBufAllocator byteBufAllocator, int i, int i2) {
        this(byteBufAllocator, new byte[i], 0, 0, i2);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public UnpooledHeapByteBuf(ByteBufAllocator byteBufAllocator, byte[] bArr, int i) {
        this(byteBufAllocator, bArr, 0, bArr.length, i);
    }

    private UnpooledHeapByteBuf(ByteBufAllocator byteBufAllocator, byte[] bArr, int i, int i2, int i3) {
        super(i3);
        if (byteBufAllocator == null) {
            throw new NullPointerException("alloc");
        } else if (bArr == null) {
            throw new NullPointerException("initialArray");
        } else if (bArr.length <= i3) {
            this.e = byteBufAllocator;
            a(bArr);
            setIndex(i, i2);
        } else {
            throw new IllegalArgumentException(String.format("initialCapacity(%d) > maxCapacity(%d)", Integer.valueOf(bArr.length), Integer.valueOf(i3)));
        }
    }

    private void a(byte[] bArr) {
        this.d = bArr;
        this.f = null;
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
    public int capacity() {
        ensureAccessible();
        return this.d.length;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf capacity(int i) {
        ensureAccessible();
        if (i < 0 || i > maxCapacity()) {
            throw new IllegalArgumentException("newCapacity: " + i);
        }
        byte[] bArr = this.d;
        int length = bArr.length;
        if (i > length) {
            byte[] bArr2 = new byte[i];
            System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
            a(bArr2);
        } else if (i < length) {
            byte[] bArr3 = new byte[i];
            int readerIndex = readerIndex();
            if (readerIndex < i) {
                int writerIndex = writerIndex();
                if (writerIndex > i) {
                    writerIndex(i);
                } else {
                    i = writerIndex;
                }
                System.arraycopy(this.d, readerIndex, bArr3, readerIndex, i - readerIndex);
            } else {
                setIndex(i, i);
            }
            a(bArr3);
        }
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public byte[] array() {
        ensureAccessible();
        return this.d;
    }

    @Override // io.netty.buffer.ByteBuf
    public long memoryAddress() {
        throw new UnsupportedOperationException();
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf getBytes(int i, ByteBuf byteBuf, int i2, int i3) {
        checkDstIndex(i, i3, i2, byteBuf.capacity());
        if (byteBuf.hasMemoryAddress()) {
            PlatformDependent.copyMemory(this.d, i, byteBuf.memoryAddress() + i2, i3);
        } else if (byteBuf.hasArray()) {
            getBytes(i, byteBuf.array(), byteBuf.arrayOffset() + i2, i3);
        } else {
            byteBuf.setBytes(i2, this.d, i, i3);
        }
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf getBytes(int i, byte[] bArr, int i2, int i3) {
        checkDstIndex(i, i3, i2, bArr.length);
        System.arraycopy(this.d, i, bArr, i2, i3);
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf getBytes(int i, ByteBuffer byteBuffer) {
        ensureAccessible();
        byteBuffer.put(this.d, i, Math.min(capacity() - i, byteBuffer.remaining()));
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf getBytes(int i, OutputStream outputStream, int i2) throws IOException {
        ensureAccessible();
        outputStream.write(this.d, i, i2);
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public int getBytes(int i, GatheringByteChannel gatheringByteChannel, int i2) throws IOException {
        ensureAccessible();
        return a(i, gatheringByteChannel, i2, false);
    }

    @Override // io.netty.buffer.ByteBuf
    public int getBytes(int i, FileChannel fileChannel, long j, int i2) throws IOException {
        ensureAccessible();
        return a(i, fileChannel, j, i2, false);
    }

    private int a(int i, GatheringByteChannel gatheringByteChannel, int i2, boolean z) throws IOException {
        ByteBuffer byteBuffer;
        ensureAccessible();
        if (z) {
            byteBuffer = b();
        } else {
            byteBuffer = ByteBuffer.wrap(this.d);
        }
        return gatheringByteChannel.write((ByteBuffer) byteBuffer.clear().position(i).limit(i + i2));
    }

    private int a(int i, FileChannel fileChannel, long j, int i2, boolean z) throws IOException {
        ensureAccessible();
        return fileChannel.write((ByteBuffer) (z ? b() : ByteBuffer.wrap(this.d)).clear().position(i).limit(i + i2), j);
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
    public ByteBuf setBytes(int i, ByteBuf byteBuf, int i2, int i3) {
        checkSrcIndex(i, i3, i2, byteBuf.capacity());
        if (byteBuf.hasMemoryAddress()) {
            PlatformDependent.copyMemory(byteBuf.memoryAddress() + i2, this.d, i, i3);
        } else if (byteBuf.hasArray()) {
            setBytes(i, byteBuf.array(), byteBuf.arrayOffset() + i2, i3);
        } else {
            byteBuf.getBytes(i2, this.d, i, i3);
        }
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf setBytes(int i, byte[] bArr, int i2, int i3) {
        checkSrcIndex(i, i3, i2, bArr.length);
        System.arraycopy(bArr, i2, this.d, i, i3);
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf setBytes(int i, ByteBuffer byteBuffer) {
        ensureAccessible();
        byteBuffer.get(this.d, i, byteBuffer.remaining());
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public int setBytes(int i, InputStream inputStream, int i2) throws IOException {
        ensureAccessible();
        return inputStream.read(this.d, i, i2);
    }

    @Override // io.netty.buffer.ByteBuf
    public int setBytes(int i, ScatteringByteChannel scatteringByteChannel, int i2) throws IOException {
        ensureAccessible();
        try {
            return scatteringByteChannel.read((ByteBuffer) b().clear().position(i).limit(i + i2));
        } catch (ClosedChannelException unused) {
            return -1;
        }
    }

    @Override // io.netty.buffer.ByteBuf
    public int setBytes(int i, FileChannel fileChannel, long j, int i2) throws IOException {
        ensureAccessible();
        try {
            return fileChannel.read((ByteBuffer) b().clear().position(i).limit(i + i2), j);
        } catch (ClosedChannelException unused) {
            return -1;
        }
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuffer nioBuffer(int i, int i2) {
        ensureAccessible();
        return ByteBuffer.wrap(this.d, i, i2).slice();
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuffer[] nioBuffers(int i, int i2) {
        return new ByteBuffer[]{nioBuffer(i, i2)};
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuffer internalNioBuffer(int i, int i2) {
        checkIndex(i, i2);
        return (ByteBuffer) b().clear().position(i).limit(i + i2);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public byte getByte(int i) {
        ensureAccessible();
        return _getByte(i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.AbstractByteBuf
    public byte _getByte(int i) {
        return g.a(this.d, i);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public short getShort(int i) {
        ensureAccessible();
        return _getShort(i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.AbstractByteBuf
    public short _getShort(int i) {
        return g.b(this.d, i);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public short getShortLE(int i) {
        ensureAccessible();
        return _getShortLE(i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.AbstractByteBuf
    public short _getShortLE(int i) {
        return g.c(this.d, i);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public int getUnsignedMedium(int i) {
        ensureAccessible();
        return _getUnsignedMedium(i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.AbstractByteBuf
    public int _getUnsignedMedium(int i) {
        return g.d(this.d, i);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public int getUnsignedMediumLE(int i) {
        ensureAccessible();
        return _getUnsignedMediumLE(i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.AbstractByteBuf
    public int _getUnsignedMediumLE(int i) {
        return g.e(this.d, i);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public int getInt(int i) {
        ensureAccessible();
        return _getInt(i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.AbstractByteBuf
    public int _getInt(int i) {
        return g.f(this.d, i);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public int getIntLE(int i) {
        ensureAccessible();
        return _getIntLE(i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.AbstractByteBuf
    public int _getIntLE(int i) {
        return g.g(this.d, i);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public long getLong(int i) {
        ensureAccessible();
        return _getLong(i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.AbstractByteBuf
    public long _getLong(int i) {
        return g.h(this.d, i);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public long getLongLE(int i) {
        ensureAccessible();
        return _getLongLE(i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.AbstractByteBuf
    public long _getLongLE(int i) {
        return g.i(this.d, i);
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
        g.a(this.d, i, i2);
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
        g.b(this.d, i, i2);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public ByteBuf setShortLE(int i, int i2) {
        ensureAccessible();
        _setShortLE(i, i2);
        return this;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.AbstractByteBuf
    public void _setShortLE(int i, int i2) {
        g.c(this.d, i, i2);
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
        g.d(this.d, i, i2);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public ByteBuf setMediumLE(int i, int i2) {
        ensureAccessible();
        _setMediumLE(i, i2);
        return this;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.AbstractByteBuf
    public void _setMediumLE(int i, int i2) {
        g.e(this.d, i, i2);
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
        g.f(this.d, i, i2);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public ByteBuf setIntLE(int i, int i2) {
        ensureAccessible();
        _setIntLE(i, i2);
        return this;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.AbstractByteBuf
    public void _setIntLE(int i, int i2) {
        g.g(this.d, i, i2);
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
        g.a(this.d, i, j);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public ByteBuf setLongLE(int i, long j) {
        ensureAccessible();
        _setLongLE(i, j);
        return this;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.AbstractByteBuf
    public void _setLongLE(int i, long j) {
        g.b(this.d, i, j);
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf copy(int i, int i2) {
        checkIndex(i, i2);
        byte[] bArr = new byte[i2];
        System.arraycopy(this.d, i, bArr, 0, i2);
        return new UnpooledHeapByteBuf(alloc(), bArr, maxCapacity());
    }

    private ByteBuffer b() {
        ByteBuffer byteBuffer = this.f;
        if (byteBuffer != null) {
            return byteBuffer;
        }
        ByteBuffer wrap = ByteBuffer.wrap(this.d);
        this.f = wrap;
        return wrap;
    }

    @Override // io.netty.buffer.AbstractReferenceCountedByteBuf
    protected void deallocate() {
        this.d = null;
    }
}
