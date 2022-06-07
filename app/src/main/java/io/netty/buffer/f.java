package io.netty.buffer;

import io.netty.util.internal.EmptyArrays;
import io.netty.util.internal.RecyclableArrayList;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ReadOnlyBufferException;
import java.nio.channels.FileChannel;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;
import java.util.Collections;
import kotlin.UShort;

/* compiled from: FixedCompositeByteBuf.java */
/* loaded from: classes4.dex */
final class f extends AbstractReferenceCountedByteBuf {
    private static final ByteBuf[] d = {Unpooled.EMPTY_BUFFER};
    private final int e;
    private final int f;
    private final ByteBufAllocator g;
    private final ByteOrder h;
    private final Object[] i;
    private final boolean j;

    @Override // io.netty.buffer.ByteBuf
    public boolean hasArray() {
        return false;
    }

    @Override // io.netty.buffer.ByteBuf
    public boolean hasMemoryAddress() {
        return false;
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public boolean isWritable() {
        return false;
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public boolean isWritable(int i) {
        return false;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf unwrap() {
        return null;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public f(ByteBufAllocator byteBufAllocator, ByteBuf... byteBufArr) {
        super(Integer.MAX_VALUE);
        if (byteBufArr.length == 0) {
            this.i = d;
            this.h = ByteOrder.BIG_ENDIAN;
            this.e = 1;
            this.f = 0;
            this.j = false;
        } else {
            ByteBuf byteBuf = byteBufArr[0];
            this.i = new Object[byteBufArr.length];
            this.i[0] = byteBuf;
            int nioBufferCount = byteBuf.nioBufferCount();
            int readableBytes = byteBuf.readableBytes();
            this.h = byteBuf.order();
            boolean z = true;
            for (int i = 1; i < byteBufArr.length; i++) {
                ByteBuf byteBuf2 = byteBufArr[i];
                if (byteBufArr[i].order() == this.h) {
                    nioBufferCount += byteBuf2.nioBufferCount();
                    readableBytes += byteBuf2.readableBytes();
                    if (!byteBuf2.isDirect()) {
                        z = false;
                    }
                    this.i[i] = byteBuf2;
                } else {
                    throw new IllegalArgumentException("All ByteBufs need to have same ByteOrder");
                }
            }
            this.e = nioBufferCount;
            this.f = readableBytes;
            this.j = z;
        }
        setIndex(0, capacity());
        this.g = byteBufAllocator;
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public ByteBuf discardReadBytes() {
        throw new ReadOnlyBufferException();
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

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public ByteBuf setByte(int i, int i2) {
        throw new ReadOnlyBufferException();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.AbstractByteBuf
    public void _setByte(int i, int i2) {
        throw new ReadOnlyBufferException();
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public ByteBuf setShort(int i, int i2) {
        throw new ReadOnlyBufferException();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.AbstractByteBuf
    public void _setShort(int i, int i2) {
        throw new ReadOnlyBufferException();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.AbstractByteBuf
    public void _setShortLE(int i, int i2) {
        throw new ReadOnlyBufferException();
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public ByteBuf setMedium(int i, int i2) {
        throw new ReadOnlyBufferException();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.AbstractByteBuf
    public void _setMedium(int i, int i2) {
        throw new ReadOnlyBufferException();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.AbstractByteBuf
    public void _setMediumLE(int i, int i2) {
        throw new ReadOnlyBufferException();
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public ByteBuf setInt(int i, int i2) {
        throw new ReadOnlyBufferException();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.AbstractByteBuf
    public void _setInt(int i, int i2) {
        throw new ReadOnlyBufferException();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.AbstractByteBuf
    public void _setIntLE(int i, int i2) {
        throw new ReadOnlyBufferException();
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public ByteBuf setLong(int i, long j) {
        throw new ReadOnlyBufferException();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.AbstractByteBuf
    public void _setLong(int i, long j) {
        throw new ReadOnlyBufferException();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.AbstractByteBuf
    public void _setLongLE(int i, long j) {
        throw new ReadOnlyBufferException();
    }

    @Override // io.netty.buffer.ByteBuf
    public int setBytes(int i, InputStream inputStream, int i2) {
        throw new ReadOnlyBufferException();
    }

    @Override // io.netty.buffer.ByteBuf
    public int setBytes(int i, ScatteringByteChannel scatteringByteChannel, int i2) {
        throw new ReadOnlyBufferException();
    }

    @Override // io.netty.buffer.ByteBuf
    public int setBytes(int i, FileChannel fileChannel, long j, int i2) {
        throw new ReadOnlyBufferException();
    }

    @Override // io.netty.buffer.ByteBuf
    public int capacity() {
        return this.f;
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public int maxCapacity() {
        return this.f;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf capacity(int i) {
        throw new ReadOnlyBufferException();
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBufAllocator alloc() {
        return this.g;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteOrder order() {
        return this.h;
    }

    @Override // io.netty.buffer.ByteBuf
    public boolean isDirect() {
        return this.j;
    }

    private a a(int i) {
        boolean z;
        ByteBuf byteBuf;
        int i2 = 0;
        int i3 = 0;
        while (true) {
            Object[] objArr = this.i;
            if (i2 < objArr.length) {
                a aVar = null;
                Object obj = objArr[i2];
                if (obj instanceof ByteBuf) {
                    byteBuf = (ByteBuf) obj;
                    z = true;
                } else {
                    aVar = (a) obj;
                    byteBuf = aVar.c;
                    z = false;
                }
                i3 += byteBuf.readableBytes();
                if (i >= i3) {
                    i2++;
                } else if (!z) {
                    return aVar;
                } else {
                    a aVar2 = new a(i2, i3 - byteBuf.readableBytes(), byteBuf);
                    this.i[i2] = aVar2;
                    return aVar2;
                }
            } else {
                throw new IllegalStateException();
            }
        }
    }

    private ByteBuf b(int i) {
        Object obj = this.i[i];
        if (obj instanceof ByteBuf) {
            return (ByteBuf) obj;
        }
        return ((a) obj).c;
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public byte getByte(int i) {
        return _getByte(i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.AbstractByteBuf
    public byte _getByte(int i) {
        a a2 = a(i);
        return a2.c.getByte(i - a2.b);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.AbstractByteBuf
    public short _getShort(int i) {
        a a2 = a(i);
        if (i + 2 <= a2.d) {
            return a2.c.getShort(i - a2.b);
        }
        if (order() == ByteOrder.BIG_ENDIAN) {
            return (short) ((_getByte(i + 1) & 255) | ((_getByte(i) & 255) << 8));
        }
        return (short) (((_getByte(i + 1) & 255) << 8) | (_getByte(i) & 255));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.AbstractByteBuf
    public short _getShortLE(int i) {
        a a2 = a(i);
        if (i + 2 <= a2.d) {
            return a2.c.getShortLE(i - a2.b);
        }
        if (order() == ByteOrder.BIG_ENDIAN) {
            return (short) (((_getByte(i + 1) & 255) << 8) | (_getByte(i) & 255));
        }
        return (short) ((_getByte(i + 1) & 255) | ((_getByte(i) & 255) << 8));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.AbstractByteBuf
    public int _getUnsignedMedium(int i) {
        a a2 = a(i);
        if (i + 3 <= a2.d) {
            return a2.c.getUnsignedMedium(i - a2.b);
        }
        if (order() == ByteOrder.BIG_ENDIAN) {
            return (_getByte(i + 2) & 255) | ((_getShort(i) & UShort.MAX_VALUE) << 8);
        }
        return ((_getByte(i + 2) & 255) << 16) | (_getShort(i) & UShort.MAX_VALUE);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.AbstractByteBuf
    public int _getUnsignedMediumLE(int i) {
        a a2 = a(i);
        if (i + 3 <= a2.d) {
            return a2.c.getUnsignedMediumLE(i - a2.b);
        }
        if (order() == ByteOrder.BIG_ENDIAN) {
            return ((_getByte(i + 2) & 255) << 16) | (_getShortLE(i) & UShort.MAX_VALUE);
        }
        return (_getByte(i + 2) & 255) | ((_getShortLE(i) & UShort.MAX_VALUE) << 8);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.AbstractByteBuf
    public int _getInt(int i) {
        a a2 = a(i);
        if (i + 4 <= a2.d) {
            return a2.c.getInt(i - a2.b);
        }
        if (order() == ByteOrder.BIG_ENDIAN) {
            return (_getShort(i + 2) & UShort.MAX_VALUE) | ((_getShort(i) & UShort.MAX_VALUE) << 16);
        }
        return ((_getShort(i + 2) & UShort.MAX_VALUE) << 16) | (_getShort(i) & UShort.MAX_VALUE);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.AbstractByteBuf
    public int _getIntLE(int i) {
        a a2 = a(i);
        if (i + 4 <= a2.d) {
            return a2.c.getIntLE(i - a2.b);
        }
        if (order() == ByteOrder.BIG_ENDIAN) {
            return ((_getShortLE(i + 2) & UShort.MAX_VALUE) << 16) | (_getShortLE(i) & UShort.MAX_VALUE);
        }
        return (_getShortLE(i + 2) & UShort.MAX_VALUE) | ((_getShortLE(i) & UShort.MAX_VALUE) << 16);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.AbstractByteBuf
    public long _getLong(int i) {
        a a2 = a(i);
        if (i + 8 <= a2.d) {
            return a2.c.getLong(i - a2.b);
        }
        if (order() == ByteOrder.BIG_ENDIAN) {
            return ((_getInt(i) & 4294967295L) << 32) | (_getInt(i + 4) & 4294967295L);
        }
        return (_getInt(i) & 4294967295L) | ((4294967295L & _getInt(i + 4)) << 32);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.AbstractByteBuf
    public long _getLongLE(int i) {
        a a2 = a(i);
        if (i + 8 <= a2.d) {
            return a2.c.getLongLE(i - a2.b);
        }
        if (order() == ByteOrder.BIG_ENDIAN) {
            return (_getIntLE(i) & 4294967295L) | ((4294967295L & _getIntLE(i + 4)) << 32);
        }
        return ((_getIntLE(i) & 4294967295L) << 32) | (_getIntLE(i + 4) & 4294967295L);
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf getBytes(int i, byte[] bArr, int i2, int i3) {
        checkDstIndex(i, i3, i2, bArr.length);
        if (i3 == 0) {
            return this;
        }
        a a2 = a(i);
        int i4 = a2.a;
        int i5 = a2.b;
        ByteBuf byteBuf = a2.c;
        while (true) {
            int i6 = i - i5;
            int min = Math.min(i3, byteBuf.readableBytes() - i6);
            byteBuf.getBytes(i6, bArr, i2, min);
            i += min;
            i2 += min;
            i3 -= min;
            i5 += byteBuf.readableBytes();
            if (i3 <= 0) {
                return this;
            }
            i4++;
            byteBuf = b(i4);
        }
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf getBytes(int i, ByteBuffer byteBuffer) {
        int limit = byteBuffer.limit();
        int remaining = byteBuffer.remaining();
        checkIndex(i, remaining);
        if (remaining == 0) {
            return this;
        }
        try {
            a a2 = a(i);
            int i2 = a2.a;
            int i3 = a2.b;
            ByteBuf byteBuf = a2.c;
            while (true) {
                int i4 = i - i3;
                int min = Math.min(remaining, byteBuf.readableBytes() - i4);
                byteBuffer.limit(byteBuffer.position() + min);
                byteBuf.getBytes(i4, byteBuffer);
                i += min;
                remaining -= min;
                i3 += byteBuf.readableBytes();
                if (remaining <= 0) {
                    return this;
                }
                i2++;
                byteBuf = b(i2);
            }
        } finally {
            byteBuffer.limit(limit);
        }
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf getBytes(int i, ByteBuf byteBuf, int i2, int i3) {
        checkDstIndex(i, i3, i2, byteBuf.capacity());
        if (i3 == 0) {
            return this;
        }
        a a2 = a(i);
        int i4 = a2.a;
        int i5 = a2.b;
        ByteBuf byteBuf2 = a2.c;
        while (true) {
            int i6 = i - i5;
            int min = Math.min(i3, byteBuf2.readableBytes() - i6);
            byteBuf2.getBytes(i6, byteBuf, i2, min);
            i += min;
            i2 += min;
            i3 -= min;
            i5 += byteBuf2.readableBytes();
            if (i3 <= 0) {
                return this;
            }
            i4++;
            byteBuf2 = b(i4);
        }
    }

    @Override // io.netty.buffer.ByteBuf
    public int getBytes(int i, GatheringByteChannel gatheringByteChannel, int i2) throws IOException {
        if (nioBufferCount() == 1) {
            return gatheringByteChannel.write(internalNioBuffer(i, i2));
        }
        long write = gatheringByteChannel.write(nioBuffers(i, i2));
        if (write > 2147483647L) {
            return Integer.MAX_VALUE;
        }
        return (int) write;
    }

    @Override // io.netty.buffer.ByteBuf
    public int getBytes(int i, FileChannel fileChannel, long j, int i2) throws IOException {
        if (nioBufferCount() == 1) {
            return fileChannel.write(internalNioBuffer(i, i2), j);
        }
        long j2 = 0;
        for (ByteBuffer byteBuffer : nioBuffers(i, i2)) {
            j2 += fileChannel.write(byteBuffer, j + j2);
        }
        if (j2 > 2147483647L) {
            return Integer.MAX_VALUE;
        }
        return (int) j2;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf getBytes(int i, OutputStream outputStream, int i2) throws IOException {
        checkIndex(i, i2);
        if (i2 == 0) {
            return this;
        }
        a a2 = a(i);
        int i3 = a2.a;
        int i4 = a2.b;
        ByteBuf byteBuf = a2.c;
        while (true) {
            int i5 = i - i4;
            int min = Math.min(i2, byteBuf.readableBytes() - i5);
            byteBuf.getBytes(i5, outputStream, min);
            i += min;
            i2 -= min;
            i4 += byteBuf.readableBytes();
            if (i2 <= 0) {
                return this;
            }
            i3++;
            byteBuf = b(i3);
        }
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf copy(int i, int i2) {
        checkIndex(i, i2);
        ByteBuf buffer = alloc().buffer(i2);
        try {
            buffer.writeBytes(this, i, i2);
            return buffer;
        } catch (Throwable th) {
            buffer.release();
            throw th;
        }
    }

    @Override // io.netty.buffer.ByteBuf
    public int nioBufferCount() {
        return this.e;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuffer nioBuffer(int i, int i2) {
        checkIndex(i, i2);
        if (this.i.length == 1) {
            ByteBuf b = b(0);
            if (b.nioBufferCount() == 1) {
                return b.nioBuffer(i, i2);
            }
        }
        ByteBuffer order = ByteBuffer.allocate(i2).order(order());
        for (ByteBuffer byteBuffer : nioBuffers(i, i2)) {
            order.put(byteBuffer);
        }
        order.flip();
        return order;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuffer internalNioBuffer(int i, int i2) {
        if (this.i.length == 1) {
            return b(0).internalNioBuffer(i, i2);
        }
        throw new UnsupportedOperationException();
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuffer[] nioBuffers(int i, int i2) {
        checkIndex(i, i2);
        if (i2 == 0) {
            return EmptyArrays.EMPTY_BYTE_BUFFERS;
        }
        RecyclableArrayList newInstance = RecyclableArrayList.newInstance(this.i.length);
        try {
            a a2 = a(i);
            int i3 = a2.a;
            int i4 = a2.b;
            ByteBuf byteBuf = a2.c;
            while (true) {
                int i5 = i - i4;
                int min = Math.min(i2, byteBuf.readableBytes() - i5);
                switch (byteBuf.nioBufferCount()) {
                    case 0:
                        throw new UnsupportedOperationException();
                    case 1:
                        newInstance.add(byteBuf.nioBuffer(i5, min));
                        break;
                    default:
                        Collections.addAll(newInstance, byteBuf.nioBuffers(i5, min));
                        break;
                }
                i += min;
                i2 -= min;
                i4 += byteBuf.readableBytes();
                if (i2 <= 0) {
                    return (ByteBuffer[]) newInstance.toArray(new ByteBuffer[newInstance.size()]);
                }
                i3++;
                byteBuf = b(i3);
            }
        } finally {
            newInstance.recycle();
        }
    }

    @Override // io.netty.buffer.ByteBuf
    public byte[] array() {
        throw new UnsupportedOperationException();
    }

    @Override // io.netty.buffer.ByteBuf
    public int arrayOffset() {
        throw new UnsupportedOperationException();
    }

    @Override // io.netty.buffer.ByteBuf
    public long memoryAddress() {
        throw new UnsupportedOperationException();
    }

    @Override // io.netty.buffer.AbstractReferenceCountedByteBuf
    protected void deallocate() {
        for (int i = 0; i < this.i.length; i++) {
            b(i).release();
        }
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public String toString() {
        String abstractReferenceCountedByteBuf = super.toString();
        String substring = abstractReferenceCountedByteBuf.substring(0, abstractReferenceCountedByteBuf.length() - 1);
        return substring + ", components=" + this.i.length + ')';
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: FixedCompositeByteBuf.java */
    /* loaded from: classes4.dex */
    public static final class a {
        private final int a;
        private final int b;
        private final ByteBuf c;
        private final int d;

        a(int i, int i2, ByteBuf byteBuf) {
            this.a = i;
            this.b = i2;
            this.d = i2 + byteBuf.readableBytes();
            this.c = byteBuf;
        }
    }
}
