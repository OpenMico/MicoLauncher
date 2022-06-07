package io.netty.buffer;

import io.netty.util.ByteProcessor;
import io.netty.util.internal.EmptyArrays;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.StringUtil;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ReadOnlyBufferException;
import java.nio.channels.FileChannel;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;
import java.nio.charset.Charset;

/* loaded from: classes4.dex */
public final class EmptyByteBuf extends ByteBuf {
    private static final ByteBuffer a = ByteBuffer.allocateDirect(0);
    private static final long b;
    private final ByteBufAllocator c;
    private final ByteOrder d;
    private final String e;
    private EmptyByteBuf f;

    @Override // io.netty.buffer.ByteBuf
    public int arrayOffset() {
        return 0;
    }

    @Override // io.netty.buffer.ByteBuf
    public int bytesBefore(byte b2) {
        return -1;
    }

    @Override // io.netty.buffer.ByteBuf
    public int capacity() {
        return 0;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf clear() {
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf copy() {
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf discardReadBytes() {
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf discardSomeReadBytes() {
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf duplicate() {
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public int forEachByte(ByteProcessor byteProcessor) {
        return -1;
    }

    @Override // io.netty.buffer.ByteBuf
    public int forEachByteDesc(ByteProcessor byteProcessor) {
        return -1;
    }

    @Override // io.netty.buffer.ByteBuf
    public boolean hasArray() {
        return true;
    }

    @Override // io.netty.buffer.ByteBuf
    public int hashCode() {
        return 0;
    }

    @Override // io.netty.buffer.ByteBuf
    public boolean isDirect() {
        return true;
    }

    @Override // io.netty.buffer.ByteBuf
    public boolean isReadOnly() {
        return false;
    }

    @Override // io.netty.buffer.ByteBuf
    public boolean isReadable() {
        return false;
    }

    @Override // io.netty.buffer.ByteBuf
    public boolean isReadable(int i) {
        return false;
    }

    @Override // io.netty.buffer.ByteBuf
    public boolean isWritable() {
        return false;
    }

    @Override // io.netty.buffer.ByteBuf
    public boolean isWritable(int i) {
        return false;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf markReaderIndex() {
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf markWriterIndex() {
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public int maxCapacity() {
        return 0;
    }

    @Override // io.netty.buffer.ByteBuf
    public int maxWritableBytes() {
        return 0;
    }

    @Override // io.netty.buffer.ByteBuf
    public int nioBufferCount() {
        return 1;
    }

    @Override // io.netty.buffer.ByteBuf
    public int readableBytes() {
        return 0;
    }

    @Override // io.netty.buffer.ByteBuf
    public int readerIndex() {
        return 0;
    }

    @Override // io.netty.util.ReferenceCounted
    public int refCnt() {
        return 1;
    }

    @Override // io.netty.util.ReferenceCounted
    public boolean release() {
        return false;
    }

    @Override // io.netty.util.ReferenceCounted
    public boolean release(int i) {
        return false;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf resetReaderIndex() {
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf resetWriterIndex() {
        return this;
    }

    @Override // io.netty.buffer.ByteBuf, io.netty.util.ReferenceCounted
    public ByteBuf retain() {
        return this;
    }

    @Override // io.netty.buffer.ByteBuf, io.netty.util.ReferenceCounted
    public ByteBuf retain(int i) {
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf retainedDuplicate() {
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf retainedSlice() {
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf slice() {
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public String toString(Charset charset) {
        return "";
    }

    @Override // io.netty.buffer.ByteBuf, io.netty.util.ReferenceCounted
    public ByteBuf touch() {
        return this;
    }

    @Override // io.netty.buffer.ByteBuf, io.netty.util.ReferenceCounted
    public ByteBuf touch(Object obj) {
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf unwrap() {
        return null;
    }

    @Override // io.netty.buffer.ByteBuf
    public int writableBytes() {
        return 0;
    }

    @Override // io.netty.buffer.ByteBuf
    public int writerIndex() {
        return 0;
    }

    static {
        long j = 0;
        try {
            if (PlatformDependent.hasUnsafe()) {
                j = PlatformDependent.directBufferAddress(a);
            }
        } catch (Throwable unused) {
        }
        b = j;
    }

    public EmptyByteBuf(ByteBufAllocator byteBufAllocator) {
        this(byteBufAllocator, ByteOrder.BIG_ENDIAN);
    }

    private EmptyByteBuf(ByteBufAllocator byteBufAllocator, ByteOrder byteOrder) {
        if (byteBufAllocator != null) {
            this.c = byteBufAllocator;
            this.d = byteOrder;
            StringBuilder sb = new StringBuilder();
            sb.append(StringUtil.simpleClassName(this));
            sb.append(byteOrder == ByteOrder.BIG_ENDIAN ? "BE" : "LE");
            this.e = sb.toString();
            return;
        }
        throw new NullPointerException("alloc");
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf capacity(int i) {
        throw new ReadOnlyBufferException();
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBufAllocator alloc() {
        return this.c;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteOrder order() {
        return this.d;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf asReadOnly() {
        return Unpooled.unmodifiableBuffer(this);
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf order(ByteOrder byteOrder) {
        if (byteOrder == null) {
            throw new NullPointerException("endianness");
        } else if (byteOrder == order()) {
            return this;
        } else {
            EmptyByteBuf emptyByteBuf = this.f;
            if (emptyByteBuf != null) {
                return emptyByteBuf;
            }
            EmptyByteBuf emptyByteBuf2 = new EmptyByteBuf(alloc(), byteOrder);
            this.f = emptyByteBuf2;
            return emptyByteBuf2;
        }
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf readerIndex(int i) {
        return a(i);
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf writerIndex(int i) {
        return a(i);
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf setIndex(int i, int i2) {
        a(i);
        a(i2);
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf ensureWritable(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("minWritableBytes: " + i + " (expected: >= 0)");
        } else if (i == 0) {
            return this;
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    @Override // io.netty.buffer.ByteBuf
    public int ensureWritable(int i, boolean z) {
        if (i >= 0) {
            return i == 0 ? 0 : 1;
        }
        throw new IllegalArgumentException("minWritableBytes: " + i + " (expected: >= 0)");
    }

    @Override // io.netty.buffer.ByteBuf
    public boolean getBoolean(int i) {
        throw new IndexOutOfBoundsException();
    }

    @Override // io.netty.buffer.ByteBuf
    public byte getByte(int i) {
        throw new IndexOutOfBoundsException();
    }

    @Override // io.netty.buffer.ByteBuf
    public short getUnsignedByte(int i) {
        throw new IndexOutOfBoundsException();
    }

    @Override // io.netty.buffer.ByteBuf
    public short getShort(int i) {
        throw new IndexOutOfBoundsException();
    }

    @Override // io.netty.buffer.ByteBuf
    public short getShortLE(int i) {
        throw new IndexOutOfBoundsException();
    }

    @Override // io.netty.buffer.ByteBuf
    public int getUnsignedShort(int i) {
        throw new IndexOutOfBoundsException();
    }

    @Override // io.netty.buffer.ByteBuf
    public int getUnsignedShortLE(int i) {
        throw new IndexOutOfBoundsException();
    }

    @Override // io.netty.buffer.ByteBuf
    public int getMedium(int i) {
        throw new IndexOutOfBoundsException();
    }

    @Override // io.netty.buffer.ByteBuf
    public int getMediumLE(int i) {
        throw new IndexOutOfBoundsException();
    }

    @Override // io.netty.buffer.ByteBuf
    public int getUnsignedMedium(int i) {
        throw new IndexOutOfBoundsException();
    }

    @Override // io.netty.buffer.ByteBuf
    public int getUnsignedMediumLE(int i) {
        throw new IndexOutOfBoundsException();
    }

    @Override // io.netty.buffer.ByteBuf
    public int getInt(int i) {
        throw new IndexOutOfBoundsException();
    }

    @Override // io.netty.buffer.ByteBuf
    public int getIntLE(int i) {
        throw new IndexOutOfBoundsException();
    }

    @Override // io.netty.buffer.ByteBuf
    public long getUnsignedInt(int i) {
        throw new IndexOutOfBoundsException();
    }

    @Override // io.netty.buffer.ByteBuf
    public long getUnsignedIntLE(int i) {
        throw new IndexOutOfBoundsException();
    }

    @Override // io.netty.buffer.ByteBuf
    public long getLong(int i) {
        throw new IndexOutOfBoundsException();
    }

    @Override // io.netty.buffer.ByteBuf
    public long getLongLE(int i) {
        throw new IndexOutOfBoundsException();
    }

    @Override // io.netty.buffer.ByteBuf
    public char getChar(int i) {
        throw new IndexOutOfBoundsException();
    }

    @Override // io.netty.buffer.ByteBuf
    public float getFloat(int i) {
        throw new IndexOutOfBoundsException();
    }

    @Override // io.netty.buffer.ByteBuf
    public double getDouble(int i) {
        throw new IndexOutOfBoundsException();
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf getBytes(int i, ByteBuf byteBuf) {
        return a(i, byteBuf.writableBytes());
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf getBytes(int i, ByteBuf byteBuf, int i2) {
        return a(i, i2);
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf getBytes(int i, ByteBuf byteBuf, int i2, int i3) {
        return a(i, i3);
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf getBytes(int i, byte[] bArr) {
        return a(i, bArr.length);
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf getBytes(int i, byte[] bArr, int i2, int i3) {
        return a(i, i3);
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf getBytes(int i, ByteBuffer byteBuffer) {
        return a(i, byteBuffer.remaining());
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf getBytes(int i, OutputStream outputStream, int i2) {
        return a(i, i2);
    }

    @Override // io.netty.buffer.ByteBuf
    public int getBytes(int i, GatheringByteChannel gatheringByteChannel, int i2) {
        a(i, i2);
        return 0;
    }

    @Override // io.netty.buffer.ByteBuf
    public int getBytes(int i, FileChannel fileChannel, long j, int i2) {
        a(i, i2);
        return 0;
    }

    @Override // io.netty.buffer.ByteBuf
    public CharSequence getCharSequence(int i, int i2, Charset charset) {
        a(i, i2);
        return null;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf setBoolean(int i, boolean z) {
        throw new IndexOutOfBoundsException();
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf setByte(int i, int i2) {
        throw new IndexOutOfBoundsException();
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf setShort(int i, int i2) {
        throw new IndexOutOfBoundsException();
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf setShortLE(int i, int i2) {
        throw new IndexOutOfBoundsException();
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf setMedium(int i, int i2) {
        throw new IndexOutOfBoundsException();
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf setMediumLE(int i, int i2) {
        throw new IndexOutOfBoundsException();
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf setInt(int i, int i2) {
        throw new IndexOutOfBoundsException();
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf setIntLE(int i, int i2) {
        throw new IndexOutOfBoundsException();
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf setLong(int i, long j) {
        throw new IndexOutOfBoundsException();
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf setLongLE(int i, long j) {
        throw new IndexOutOfBoundsException();
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf setChar(int i, int i2) {
        throw new IndexOutOfBoundsException();
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf setFloat(int i, float f) {
        throw new IndexOutOfBoundsException();
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf setDouble(int i, double d) {
        throw new IndexOutOfBoundsException();
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf setBytes(int i, ByteBuf byteBuf) {
        throw new IndexOutOfBoundsException();
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf setBytes(int i, ByteBuf byteBuf, int i2) {
        return a(i, i2);
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf setBytes(int i, ByteBuf byteBuf, int i2, int i3) {
        return a(i, i3);
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf setBytes(int i, byte[] bArr) {
        return a(i, bArr.length);
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf setBytes(int i, byte[] bArr, int i2, int i3) {
        return a(i, i3);
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf setBytes(int i, ByteBuffer byteBuffer) {
        return a(i, byteBuffer.remaining());
    }

    @Override // io.netty.buffer.ByteBuf
    public int setBytes(int i, InputStream inputStream, int i2) {
        a(i, i2);
        return 0;
    }

    @Override // io.netty.buffer.ByteBuf
    public int setBytes(int i, ScatteringByteChannel scatteringByteChannel, int i2) {
        a(i, i2);
        return 0;
    }

    @Override // io.netty.buffer.ByteBuf
    public int setBytes(int i, FileChannel fileChannel, long j, int i2) {
        a(i, i2);
        return 0;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf setZero(int i, int i2) {
        return a(i, i2);
    }

    @Override // io.netty.buffer.ByteBuf
    public int setCharSequence(int i, CharSequence charSequence, Charset charset) {
        throw new IndexOutOfBoundsException();
    }

    @Override // io.netty.buffer.ByteBuf
    public boolean readBoolean() {
        throw new IndexOutOfBoundsException();
    }

    @Override // io.netty.buffer.ByteBuf
    public byte readByte() {
        throw new IndexOutOfBoundsException();
    }

    @Override // io.netty.buffer.ByteBuf
    public short readUnsignedByte() {
        throw new IndexOutOfBoundsException();
    }

    @Override // io.netty.buffer.ByteBuf
    public short readShort() {
        throw new IndexOutOfBoundsException();
    }

    @Override // io.netty.buffer.ByteBuf
    public short readShortLE() {
        throw new IndexOutOfBoundsException();
    }

    @Override // io.netty.buffer.ByteBuf
    public int readUnsignedShort() {
        throw new IndexOutOfBoundsException();
    }

    @Override // io.netty.buffer.ByteBuf
    public int readUnsignedShortLE() {
        throw new IndexOutOfBoundsException();
    }

    @Override // io.netty.buffer.ByteBuf
    public int readMedium() {
        throw new IndexOutOfBoundsException();
    }

    @Override // io.netty.buffer.ByteBuf
    public int readMediumLE() {
        throw new IndexOutOfBoundsException();
    }

    @Override // io.netty.buffer.ByteBuf
    public int readUnsignedMedium() {
        throw new IndexOutOfBoundsException();
    }

    @Override // io.netty.buffer.ByteBuf
    public int readUnsignedMediumLE() {
        throw new IndexOutOfBoundsException();
    }

    @Override // io.netty.buffer.ByteBuf
    public int readInt() {
        throw new IndexOutOfBoundsException();
    }

    @Override // io.netty.buffer.ByteBuf
    public int readIntLE() {
        throw new IndexOutOfBoundsException();
    }

    @Override // io.netty.buffer.ByteBuf
    public long readUnsignedInt() {
        throw new IndexOutOfBoundsException();
    }

    @Override // io.netty.buffer.ByteBuf
    public long readUnsignedIntLE() {
        throw new IndexOutOfBoundsException();
    }

    @Override // io.netty.buffer.ByteBuf
    public long readLong() {
        throw new IndexOutOfBoundsException();
    }

    @Override // io.netty.buffer.ByteBuf
    public long readLongLE() {
        throw new IndexOutOfBoundsException();
    }

    @Override // io.netty.buffer.ByteBuf
    public char readChar() {
        throw new IndexOutOfBoundsException();
    }

    @Override // io.netty.buffer.ByteBuf
    public float readFloat() {
        throw new IndexOutOfBoundsException();
    }

    @Override // io.netty.buffer.ByteBuf
    public double readDouble() {
        throw new IndexOutOfBoundsException();
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf readBytes(int i) {
        return b(i);
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf readSlice(int i) {
        return b(i);
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf readRetainedSlice(int i) {
        return b(i);
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf readBytes(ByteBuf byteBuf) {
        return b(byteBuf.writableBytes());
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf readBytes(ByteBuf byteBuf, int i) {
        return b(i);
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf readBytes(ByteBuf byteBuf, int i, int i2) {
        return b(i2);
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf readBytes(byte[] bArr) {
        return b(bArr.length);
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf readBytes(byte[] bArr, int i, int i2) {
        return b(i2);
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf readBytes(ByteBuffer byteBuffer) {
        return b(byteBuffer.remaining());
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf readBytes(OutputStream outputStream, int i) {
        return b(i);
    }

    @Override // io.netty.buffer.ByteBuf
    public int readBytes(GatheringByteChannel gatheringByteChannel, int i) {
        b(i);
        return 0;
    }

    @Override // io.netty.buffer.ByteBuf
    public int readBytes(FileChannel fileChannel, long j, int i) {
        b(i);
        return 0;
    }

    @Override // io.netty.buffer.ByteBuf
    public CharSequence readCharSequence(int i, Charset charset) {
        b(i);
        return null;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf skipBytes(int i) {
        return b(i);
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf writeBoolean(boolean z) {
        throw new IndexOutOfBoundsException();
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf writeByte(int i) {
        throw new IndexOutOfBoundsException();
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf writeShort(int i) {
        throw new IndexOutOfBoundsException();
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf writeShortLE(int i) {
        throw new IndexOutOfBoundsException();
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf writeMedium(int i) {
        throw new IndexOutOfBoundsException();
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf writeMediumLE(int i) {
        throw new IndexOutOfBoundsException();
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf writeInt(int i) {
        throw new IndexOutOfBoundsException();
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf writeIntLE(int i) {
        throw new IndexOutOfBoundsException();
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf writeLong(long j) {
        throw new IndexOutOfBoundsException();
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf writeLongLE(long j) {
        throw new IndexOutOfBoundsException();
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf writeChar(int i) {
        throw new IndexOutOfBoundsException();
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf writeFloat(float f) {
        throw new IndexOutOfBoundsException();
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf writeDouble(double d) {
        throw new IndexOutOfBoundsException();
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf writeBytes(ByteBuf byteBuf) {
        throw new IndexOutOfBoundsException();
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf writeBytes(ByteBuf byteBuf, int i) {
        return b(i);
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf writeBytes(ByteBuf byteBuf, int i, int i2) {
        return b(i2);
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf writeBytes(byte[] bArr) {
        return b(bArr.length);
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf writeBytes(byte[] bArr, int i, int i2) {
        return b(i2);
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf writeBytes(ByteBuffer byteBuffer) {
        return b(byteBuffer.remaining());
    }

    @Override // io.netty.buffer.ByteBuf
    public int writeBytes(InputStream inputStream, int i) {
        b(i);
        return 0;
    }

    @Override // io.netty.buffer.ByteBuf
    public int writeBytes(ScatteringByteChannel scatteringByteChannel, int i) {
        b(i);
        return 0;
    }

    @Override // io.netty.buffer.ByteBuf
    public int writeBytes(FileChannel fileChannel, long j, int i) {
        b(i);
        return 0;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf writeZero(int i) {
        return b(i);
    }

    @Override // io.netty.buffer.ByteBuf
    public int writeCharSequence(CharSequence charSequence, Charset charset) {
        throw new IndexOutOfBoundsException();
    }

    @Override // io.netty.buffer.ByteBuf
    public int indexOf(int i, int i2, byte b2) {
        a(i);
        a(i2);
        return -1;
    }

    @Override // io.netty.buffer.ByteBuf
    public int bytesBefore(int i, byte b2) {
        b(i);
        return -1;
    }

    @Override // io.netty.buffer.ByteBuf
    public int bytesBefore(int i, int i2, byte b2) {
        a(i, i2);
        return -1;
    }

    @Override // io.netty.buffer.ByteBuf
    public int forEachByte(int i, int i2, ByteProcessor byteProcessor) {
        a(i, i2);
        return -1;
    }

    @Override // io.netty.buffer.ByteBuf
    public int forEachByteDesc(int i, int i2, ByteProcessor byteProcessor) {
        a(i, i2);
        return -1;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf copy(int i, int i2) {
        return a(i, i2);
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf slice(int i, int i2) {
        return a(i, i2);
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf retainedSlice(int i, int i2) {
        return a(i, i2);
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuffer nioBuffer() {
        return a;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuffer nioBuffer(int i, int i2) {
        a(i, i2);
        return nioBuffer();
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuffer[] nioBuffers() {
        return new ByteBuffer[]{a};
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuffer[] nioBuffers(int i, int i2) {
        a(i, i2);
        return nioBuffers();
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuffer internalNioBuffer(int i, int i2) {
        return a;
    }

    @Override // io.netty.buffer.ByteBuf
    public byte[] array() {
        return EmptyArrays.EMPTY_BYTES;
    }

    @Override // io.netty.buffer.ByteBuf
    public boolean hasMemoryAddress() {
        return b != 0;
    }

    @Override // io.netty.buffer.ByteBuf
    public long memoryAddress() {
        if (hasMemoryAddress()) {
            return b;
        }
        throw new UnsupportedOperationException();
    }

    @Override // io.netty.buffer.ByteBuf
    public String toString(int i, int i2, Charset charset) {
        a(i, i2);
        return toString(charset);
    }

    @Override // io.netty.buffer.ByteBuf
    public boolean equals(Object obj) {
        return (obj instanceof ByteBuf) && !((ByteBuf) obj).isReadable();
    }

    @Override // io.netty.buffer.ByteBuf
    public int compareTo(ByteBuf byteBuf) {
        return byteBuf.isReadable() ? -1 : 0;
    }

    @Override // io.netty.buffer.ByteBuf
    public String toString() {
        return this.e;
    }

    private ByteBuf a(int i) {
        if (i == 0) {
            return this;
        }
        throw new IndexOutOfBoundsException();
    }

    private ByteBuf a(int i, int i2) {
        if (i2 < 0) {
            throw new IllegalArgumentException("length: " + i2);
        } else if (i == 0 && i2 == 0) {
            return this;
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    private ByteBuf b(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("length: " + i + " (expected: >= 0)");
        } else if (i == 0) {
            return this;
        } else {
            throw new IndexOutOfBoundsException();
        }
    }
}
