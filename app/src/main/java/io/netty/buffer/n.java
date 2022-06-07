package io.netty.buffer;

import io.netty.util.Recycler;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.FileChannel;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: PooledDirectByteBuf.java */
/* loaded from: classes4.dex */
public final class n extends m<ByteBuffer> {
    private static final Recycler<n> l = new Recycler<n>() { // from class: io.netty.buffer.n.1
        /* JADX INFO: Access modifiers changed from: protected */
        /* renamed from: a */
        public n newObject(Recycler.Handle<n> handle) {
            return new n(handle, 0);
        }
    };

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

    /* JADX INFO: Access modifiers changed from: package-private */
    public static n c(int i) {
        n nVar = l.get();
        nVar.a(i);
        return nVar;
    }

    private n(Recycler.Handle<n> handle, int i) {
        super(handle, i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public ByteBuffer a(ByteBuffer byteBuffer) {
        return byteBuffer.duplicate();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.AbstractByteBuf
    public byte _getByte(int i) {
        return ((ByteBuffer) this.f).get(b(i));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.AbstractByteBuf
    public short _getShort(int i) {
        return ((ByteBuffer) this.f).getShort(b(i));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.AbstractByteBuf
    public short _getShortLE(int i) {
        return ByteBufUtil.swapShort(_getShort(i));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.AbstractByteBuf
    public int _getUnsignedMedium(int i) {
        int b = b(i);
        return (((ByteBuffer) this.f).get(b + 2) & 255) | ((((ByteBuffer) this.f).get(b) & 255) << 16) | ((((ByteBuffer) this.f).get(b + 1) & 255) << 8);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.AbstractByteBuf
    public int _getUnsignedMediumLE(int i) {
        int b = b(i);
        return ((((ByteBuffer) this.f).get(b + 2) & 255) << 16) | (((ByteBuffer) this.f).get(b) & 255) | ((((ByteBuffer) this.f).get(b + 1) & 255) << 8);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.AbstractByteBuf
    public int _getInt(int i) {
        return ((ByteBuffer) this.f).getInt(b(i));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.AbstractByteBuf
    public int _getIntLE(int i) {
        return ByteBufUtil.swapInt(_getInt(i));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.AbstractByteBuf
    public long _getLong(int i) {
        return ((ByteBuffer) this.f).getLong(b(i));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.AbstractByteBuf
    public long _getLongLE(int i) {
        return ByteBufUtil.swapLong(_getLong(i));
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
            byteBuffer = ((ByteBuffer) this.f).duplicate();
        }
        int b = b(i);
        byteBuffer.clear().position(b).limit(b + i3);
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
        int min = Math.min(capacity() - i, byteBuffer.remaining());
        if (z) {
            byteBuffer2 = b();
        } else {
            byteBuffer2 = ((ByteBuffer) this.f).duplicate();
        }
        int b = b(i);
        byteBuffer2.clear().position(b).limit(b + min);
        byteBuffer.put(byteBuffer2);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public ByteBuf readBytes(ByteBuffer byteBuffer) {
        int remaining = byteBuffer.remaining();
        checkReadableBytes(remaining);
        a(this.b, byteBuffer, true);
        this.b += remaining;
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf getBytes(int i, OutputStream outputStream, int i2) throws IOException {
        a(i, outputStream, i2, false);
        return this;
    }

    private void a(int i, OutputStream outputStream, int i2, boolean z) throws IOException {
        ByteBuffer byteBuffer;
        checkIndex(i, i2);
        if (i2 != 0) {
            byte[] bArr = new byte[i2];
            if (z) {
                byteBuffer = b();
            } else {
                byteBuffer = ((ByteBuffer) this.f).duplicate();
            }
            byteBuffer.clear().position(b(i));
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
        checkIndex(i, i2);
        if (i2 == 0) {
            return 0;
        }
        if (z) {
            byteBuffer = b();
        } else {
            byteBuffer = ((ByteBuffer) this.f).duplicate();
        }
        int b = b(i);
        byteBuffer.clear().position(b).limit(b + i2);
        return gatheringByteChannel.write(byteBuffer);
    }

    @Override // io.netty.buffer.ByteBuf
    public int getBytes(int i, FileChannel fileChannel, long j, int i2) throws IOException {
        return a(i, fileChannel, j, i2, false);
    }

    private int a(int i, FileChannel fileChannel, long j, int i2, boolean z) throws IOException {
        checkIndex(i, i2);
        if (i2 == 0) {
            return 0;
        }
        ByteBuffer b = z ? b() : ((ByteBuffer) this.f).duplicate();
        int b2 = b(i);
        b.clear().position(b2).limit(b2 + i2);
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

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.AbstractByteBuf
    public void _setByte(int i, int i2) {
        ((ByteBuffer) this.f).put(b(i), (byte) i2);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.AbstractByteBuf
    public void _setShort(int i, int i2) {
        ((ByteBuffer) this.f).putShort(b(i), (short) i2);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.AbstractByteBuf
    public void _setShortLE(int i, int i2) {
        _setShort(i, ByteBufUtil.swapShort((short) i2));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.AbstractByteBuf
    public void _setMedium(int i, int i2) {
        int b = b(i);
        ((ByteBuffer) this.f).put(b, (byte) (i2 >>> 16));
        ((ByteBuffer) this.f).put(b + 1, (byte) (i2 >>> 8));
        ((ByteBuffer) this.f).put(b + 2, (byte) i2);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.AbstractByteBuf
    public void _setMediumLE(int i, int i2) {
        int b = b(i);
        ((ByteBuffer) this.f).put(b, (byte) i2);
        ((ByteBuffer) this.f).put(b + 1, (byte) (i2 >>> 8));
        ((ByteBuffer) this.f).put(b + 2, (byte) (i2 >>> 16));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.AbstractByteBuf
    public void _setInt(int i, int i2) {
        ((ByteBuffer) this.f).putInt(b(i), i2);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.AbstractByteBuf
    public void _setIntLE(int i, int i2) {
        _setInt(i, ByteBufUtil.swapInt(i2));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.AbstractByteBuf
    public void _setLong(int i, long j) {
        ((ByteBuffer) this.f).putLong(b(i), j);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.AbstractByteBuf
    public void _setLongLE(int i, long j) {
        _setLong(i, ByteBufUtil.swapLong(j));
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf setBytes(int i, ByteBuf byteBuf, int i2, int i3) {
        checkSrcIndex(i, i3, i2, byteBuf.capacity());
        if (byteBuf.hasArray()) {
            setBytes(i, byteBuf.array(), byteBuf.arrayOffset() + i2, i3);
        } else if (byteBuf.nioBufferCount() > 0) {
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
        int b2 = b(i);
        b.clear().position(b2).limit(b2 + i3);
        b.put(bArr, i2, i3);
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf setBytes(int i, ByteBuffer byteBuffer) {
        checkIndex(i, byteBuffer.remaining());
        ByteBuffer b = b();
        if (byteBuffer == b) {
            byteBuffer = byteBuffer.duplicate();
        }
        int b2 = b(i);
        b.clear().position(b2).limit(b2 + byteBuffer.remaining());
        b.put(byteBuffer);
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public int setBytes(int i, InputStream inputStream, int i2) throws IOException {
        checkIndex(i, i2);
        byte[] bArr = new byte[i2];
        int read = inputStream.read(bArr);
        if (read <= 0) {
            return read;
        }
        ByteBuffer b = b();
        b.clear().position(b(i));
        b.put(bArr, 0, read);
        return read;
    }

    @Override // io.netty.buffer.ByteBuf
    public int setBytes(int i, ScatteringByteChannel scatteringByteChannel, int i2) throws IOException {
        checkIndex(i, i2);
        ByteBuffer b = b();
        int b2 = b(i);
        b.clear().position(b2).limit(b2 + i2);
        try {
            return scatteringByteChannel.read(b);
        } catch (ClosedChannelException unused) {
            return -1;
        }
    }

    @Override // io.netty.buffer.ByteBuf
    public int setBytes(int i, FileChannel fileChannel, long j, int i2) throws IOException {
        checkIndex(i, i2);
        ByteBuffer b = b();
        int b2 = b(i);
        b.clear().position(b2).limit(b2 + i2);
        try {
            return fileChannel.read(b, j);
        } catch (ClosedChannelException unused) {
            return -1;
        }
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf copy(int i, int i2) {
        checkIndex(i, i2);
        ByteBuf directBuffer = alloc().directBuffer(i2, maxCapacity());
        directBuffer.writeBytes(this, i, i2);
        return directBuffer;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuffer nioBuffer(int i, int i2) {
        checkIndex(i, i2);
        int b = b(i);
        return ((ByteBuffer) ((ByteBuffer) this.f).duplicate().position(b).limit(b + i2)).slice();
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuffer[] nioBuffers(int i, int i2) {
        return new ByteBuffer[]{nioBuffer(i, i2)};
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuffer internalNioBuffer(int i, int i2) {
        checkIndex(i, i2);
        int b = b(i);
        return (ByteBuffer) b().clear().position(b).limit(b + i2);
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
}
