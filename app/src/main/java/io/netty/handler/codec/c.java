package io.netty.handler.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.SwappedByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.ByteProcessor;
import io.netty.util.Signal;
import io.netty.util.internal.StringUtil;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;
import java.nio.charset.Charset;

/* compiled from: ReplayingDecoderByteBuf.java */
/* loaded from: classes4.dex */
final class c extends ByteBuf {
    private ByteBuf c;
    private boolean d;
    private SwappedByteBuf e;
    private static final Signal b = ReplayingDecoder.a;
    static final c a = new c(Unpooled.EMPTY_BUFFER);

    @Override // io.netty.buffer.ByteBuf
    public boolean equals(Object obj) {
        return this == obj;
    }

    @Override // io.netty.buffer.ByteBuf
    public boolean hasArray() {
        return false;
    }

    @Override // io.netty.buffer.ByteBuf
    public boolean hasMemoryAddress() {
        return false;
    }

    @Override // io.netty.buffer.ByteBuf
    public boolean isReadOnly() {
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
    public int maxWritableBytes() {
        return 0;
    }

    @Override // io.netty.buffer.ByteBuf
    public int writableBytes() {
        return 0;
    }

    static {
        a.a();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public c() {
    }

    c(ByteBuf byteBuf) {
        a(byteBuf);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(ByteBuf byteBuf) {
        this.c = byteBuf;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a() {
        this.d = true;
    }

    @Override // io.netty.buffer.ByteBuf
    public int capacity() {
        if (this.d) {
            return this.c.capacity();
        }
        return Integer.MAX_VALUE;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf capacity(int i) {
        throw b();
    }

    @Override // io.netty.buffer.ByteBuf
    public int maxCapacity() {
        return capacity();
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBufAllocator alloc() {
        return this.c.alloc();
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf asReadOnly() {
        return Unpooled.unmodifiableBuffer(this);
    }

    @Override // io.netty.buffer.ByteBuf
    public boolean isDirect() {
        return this.c.isDirect();
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

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf clear() {
        throw b();
    }

    @Override // io.netty.buffer.ByteBuf
    public int compareTo(ByteBuf byteBuf) {
        throw b();
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf copy() {
        throw b();
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf copy(int i, int i2) {
        a(i, i2);
        return this.c.copy(i, i2);
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf discardReadBytes() {
        throw b();
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf ensureWritable(int i) {
        throw b();
    }

    @Override // io.netty.buffer.ByteBuf
    public int ensureWritable(int i, boolean z) {
        throw b();
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf duplicate() {
        throw b();
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf retainedDuplicate() {
        throw b();
    }

    @Override // io.netty.buffer.ByteBuf
    public boolean getBoolean(int i) {
        a(i, 1);
        return this.c.getBoolean(i);
    }

    @Override // io.netty.buffer.ByteBuf
    public byte getByte(int i) {
        a(i, 1);
        return this.c.getByte(i);
    }

    @Override // io.netty.buffer.ByteBuf
    public short getUnsignedByte(int i) {
        a(i, 1);
        return this.c.getUnsignedByte(i);
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf getBytes(int i, byte[] bArr, int i2, int i3) {
        a(i, i3);
        this.c.getBytes(i, bArr, i2, i3);
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf getBytes(int i, byte[] bArr) {
        a(i, bArr.length);
        this.c.getBytes(i, bArr);
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf getBytes(int i, ByteBuffer byteBuffer) {
        throw b();
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf getBytes(int i, ByteBuf byteBuf, int i2, int i3) {
        a(i, i3);
        this.c.getBytes(i, byteBuf, i2, i3);
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf getBytes(int i, ByteBuf byteBuf, int i2) {
        throw b();
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf getBytes(int i, ByteBuf byteBuf) {
        throw b();
    }

    @Override // io.netty.buffer.ByteBuf
    public int getBytes(int i, GatheringByteChannel gatheringByteChannel, int i2) {
        throw b();
    }

    @Override // io.netty.buffer.ByteBuf
    public int getBytes(int i, FileChannel fileChannel, long j, int i2) {
        throw b();
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf getBytes(int i, OutputStream outputStream, int i2) {
        throw b();
    }

    @Override // io.netty.buffer.ByteBuf
    public int getInt(int i) {
        a(i, 4);
        return this.c.getInt(i);
    }

    @Override // io.netty.buffer.ByteBuf
    public int getIntLE(int i) {
        a(i, 4);
        return this.c.getIntLE(i);
    }

    @Override // io.netty.buffer.ByteBuf
    public long getUnsignedInt(int i) {
        a(i, 4);
        return this.c.getUnsignedInt(i);
    }

    @Override // io.netty.buffer.ByteBuf
    public long getUnsignedIntLE(int i) {
        a(i, 4);
        return this.c.getUnsignedIntLE(i);
    }

    @Override // io.netty.buffer.ByteBuf
    public long getLong(int i) {
        a(i, 8);
        return this.c.getLong(i);
    }

    @Override // io.netty.buffer.ByteBuf
    public long getLongLE(int i) {
        a(i, 8);
        return this.c.getLongLE(i);
    }

    @Override // io.netty.buffer.ByteBuf
    public int getMedium(int i) {
        a(i, 3);
        return this.c.getMedium(i);
    }

    @Override // io.netty.buffer.ByteBuf
    public int getMediumLE(int i) {
        a(i, 3);
        return this.c.getMediumLE(i);
    }

    @Override // io.netty.buffer.ByteBuf
    public int getUnsignedMedium(int i) {
        a(i, 3);
        return this.c.getUnsignedMedium(i);
    }

    @Override // io.netty.buffer.ByteBuf
    public int getUnsignedMediumLE(int i) {
        a(i, 3);
        return this.c.getUnsignedMediumLE(i);
    }

    @Override // io.netty.buffer.ByteBuf
    public short getShort(int i) {
        a(i, 2);
        return this.c.getShort(i);
    }

    @Override // io.netty.buffer.ByteBuf
    public short getShortLE(int i) {
        a(i, 2);
        return this.c.getShortLE(i);
    }

    @Override // io.netty.buffer.ByteBuf
    public int getUnsignedShort(int i) {
        a(i, 2);
        return this.c.getUnsignedShort(i);
    }

    @Override // io.netty.buffer.ByteBuf
    public int getUnsignedShortLE(int i) {
        a(i, 2);
        return this.c.getUnsignedShortLE(i);
    }

    @Override // io.netty.buffer.ByteBuf
    public char getChar(int i) {
        a(i, 2);
        return this.c.getChar(i);
    }

    @Override // io.netty.buffer.ByteBuf
    public float getFloat(int i) {
        a(i, 4);
        return this.c.getFloat(i);
    }

    @Override // io.netty.buffer.ByteBuf
    public double getDouble(int i) {
        a(i, 8);
        return this.c.getDouble(i);
    }

    @Override // io.netty.buffer.ByteBuf
    public CharSequence getCharSequence(int i, int i2, Charset charset) {
        a(i, i2);
        return this.c.getCharSequence(i, i2, charset);
    }

    @Override // io.netty.buffer.ByteBuf
    public int hashCode() {
        throw b();
    }

    @Override // io.netty.buffer.ByteBuf
    public int indexOf(int i, int i2, byte b2) {
        if (i == i2) {
            return -1;
        }
        if (Math.max(i, i2) <= this.c.writerIndex()) {
            return this.c.indexOf(i, i2, b2);
        }
        throw b;
    }

    @Override // io.netty.buffer.ByteBuf
    public int bytesBefore(byte b2) {
        int bytesBefore = this.c.bytesBefore(b2);
        if (bytesBefore >= 0) {
            return bytesBefore;
        }
        throw b;
    }

    @Override // io.netty.buffer.ByteBuf
    public int bytesBefore(int i, byte b2) {
        return bytesBefore(this.c.readerIndex(), i, b2);
    }

    @Override // io.netty.buffer.ByteBuf
    public int bytesBefore(int i, int i2, byte b2) {
        int writerIndex = this.c.writerIndex();
        if (i >= writerIndex) {
            throw b;
        } else if (i <= writerIndex - i2) {
            return this.c.bytesBefore(i, i2, b2);
        } else {
            int bytesBefore = this.c.bytesBefore(i, writerIndex - i, b2);
            if (bytesBefore >= 0) {
                return bytesBefore;
            }
            throw b;
        }
    }

    @Override // io.netty.buffer.ByteBuf
    public int forEachByte(ByteProcessor byteProcessor) {
        int forEachByte = this.c.forEachByte(byteProcessor);
        if (forEachByte >= 0) {
            return forEachByte;
        }
        throw b;
    }

    @Override // io.netty.buffer.ByteBuf
    public int forEachByte(int i, int i2, ByteProcessor byteProcessor) {
        int writerIndex = this.c.writerIndex();
        if (i >= writerIndex) {
            throw b;
        } else if (i <= writerIndex - i2) {
            return this.c.forEachByte(i, i2, byteProcessor);
        } else {
            int forEachByte = this.c.forEachByte(i, writerIndex - i, byteProcessor);
            if (forEachByte >= 0) {
                return forEachByte;
            }
            throw b;
        }
    }

    @Override // io.netty.buffer.ByteBuf
    public int forEachByteDesc(ByteProcessor byteProcessor) {
        if (this.d) {
            return this.c.forEachByteDesc(byteProcessor);
        }
        throw b();
    }

    @Override // io.netty.buffer.ByteBuf
    public int forEachByteDesc(int i, int i2, ByteProcessor byteProcessor) {
        if (i + i2 <= this.c.writerIndex()) {
            return this.c.forEachByteDesc(i, i2, byteProcessor);
        }
        throw b;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf markReaderIndex() {
        this.c.markReaderIndex();
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf markWriterIndex() {
        throw b();
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteOrder order() {
        return this.c.order();
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf order(ByteOrder byteOrder) {
        if (byteOrder == null) {
            throw new NullPointerException("endianness");
        } else if (byteOrder == order()) {
            return this;
        } else {
            SwappedByteBuf swappedByteBuf = this.e;
            if (swappedByteBuf != null) {
                return swappedByteBuf;
            }
            SwappedByteBuf swappedByteBuf2 = new SwappedByteBuf(this);
            this.e = swappedByteBuf2;
            return swappedByteBuf2;
        }
    }

    @Override // io.netty.buffer.ByteBuf
    public boolean isReadable() {
        if (this.d) {
            return this.c.isReadable();
        }
        return true;
    }

    @Override // io.netty.buffer.ByteBuf
    public boolean isReadable(int i) {
        if (this.d) {
            return this.c.isReadable(i);
        }
        return true;
    }

    @Override // io.netty.buffer.ByteBuf
    public int readableBytes() {
        if (this.d) {
            return this.c.readableBytes();
        }
        return Integer.MAX_VALUE - this.c.readerIndex();
    }

    @Override // io.netty.buffer.ByteBuf
    public boolean readBoolean() {
        a(1);
        return this.c.readBoolean();
    }

    @Override // io.netty.buffer.ByteBuf
    public byte readByte() {
        a(1);
        return this.c.readByte();
    }

    @Override // io.netty.buffer.ByteBuf
    public short readUnsignedByte() {
        a(1);
        return this.c.readUnsignedByte();
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf readBytes(byte[] bArr, int i, int i2) {
        a(i2);
        this.c.readBytes(bArr, i, i2);
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf readBytes(byte[] bArr) {
        a(bArr.length);
        this.c.readBytes(bArr);
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf readBytes(ByteBuffer byteBuffer) {
        throw b();
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf readBytes(ByteBuf byteBuf, int i, int i2) {
        a(i2);
        this.c.readBytes(byteBuf, i, i2);
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf readBytes(ByteBuf byteBuf, int i) {
        throw b();
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf readBytes(ByteBuf byteBuf) {
        a(byteBuf.writableBytes());
        this.c.readBytes(byteBuf);
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public int readBytes(GatheringByteChannel gatheringByteChannel, int i) {
        throw b();
    }

    @Override // io.netty.buffer.ByteBuf
    public int readBytes(FileChannel fileChannel, long j, int i) {
        throw b();
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf readBytes(int i) {
        a(i);
        return this.c.readBytes(i);
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf readSlice(int i) {
        a(i);
        return this.c.readSlice(i);
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf readRetainedSlice(int i) {
        a(i);
        return this.c.readRetainedSlice(i);
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf readBytes(OutputStream outputStream, int i) {
        throw b();
    }

    @Override // io.netty.buffer.ByteBuf
    public int readerIndex() {
        return this.c.readerIndex();
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf readerIndex(int i) {
        this.c.readerIndex(i);
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public int readInt() {
        a(4);
        return this.c.readInt();
    }

    @Override // io.netty.buffer.ByteBuf
    public int readIntLE() {
        a(4);
        return this.c.readIntLE();
    }

    @Override // io.netty.buffer.ByteBuf
    public long readUnsignedInt() {
        a(4);
        return this.c.readUnsignedInt();
    }

    @Override // io.netty.buffer.ByteBuf
    public long readUnsignedIntLE() {
        a(4);
        return this.c.readUnsignedIntLE();
    }

    @Override // io.netty.buffer.ByteBuf
    public long readLong() {
        a(8);
        return this.c.readLong();
    }

    @Override // io.netty.buffer.ByteBuf
    public long readLongLE() {
        a(8);
        return this.c.readLongLE();
    }

    @Override // io.netty.buffer.ByteBuf
    public int readMedium() {
        a(3);
        return this.c.readMedium();
    }

    @Override // io.netty.buffer.ByteBuf
    public int readMediumLE() {
        a(3);
        return this.c.readMediumLE();
    }

    @Override // io.netty.buffer.ByteBuf
    public int readUnsignedMedium() {
        a(3);
        return this.c.readUnsignedMedium();
    }

    @Override // io.netty.buffer.ByteBuf
    public int readUnsignedMediumLE() {
        a(3);
        return this.c.readUnsignedMediumLE();
    }

    @Override // io.netty.buffer.ByteBuf
    public short readShort() {
        a(2);
        return this.c.readShort();
    }

    @Override // io.netty.buffer.ByteBuf
    public short readShortLE() {
        a(2);
        return this.c.readShortLE();
    }

    @Override // io.netty.buffer.ByteBuf
    public int readUnsignedShort() {
        a(2);
        return this.c.readUnsignedShort();
    }

    @Override // io.netty.buffer.ByteBuf
    public int readUnsignedShortLE() {
        a(2);
        return this.c.readUnsignedShortLE();
    }

    @Override // io.netty.buffer.ByteBuf
    public char readChar() {
        a(2);
        return this.c.readChar();
    }

    @Override // io.netty.buffer.ByteBuf
    public float readFloat() {
        a(4);
        return this.c.readFloat();
    }

    @Override // io.netty.buffer.ByteBuf
    public double readDouble() {
        a(8);
        return this.c.readDouble();
    }

    @Override // io.netty.buffer.ByteBuf
    public CharSequence readCharSequence(int i, Charset charset) {
        a(i);
        return this.c.readCharSequence(i, charset);
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf resetReaderIndex() {
        this.c.resetReaderIndex();
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf resetWriterIndex() {
        throw b();
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf setBoolean(int i, boolean z) {
        throw b();
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf setByte(int i, int i2) {
        throw b();
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf setBytes(int i, byte[] bArr, int i2, int i3) {
        throw b();
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf setBytes(int i, byte[] bArr) {
        throw b();
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf setBytes(int i, ByteBuffer byteBuffer) {
        throw b();
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf setBytes(int i, ByteBuf byteBuf, int i2, int i3) {
        throw b();
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf setBytes(int i, ByteBuf byteBuf, int i2) {
        throw b();
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf setBytes(int i, ByteBuf byteBuf) {
        throw b();
    }

    @Override // io.netty.buffer.ByteBuf
    public int setBytes(int i, InputStream inputStream, int i2) {
        throw b();
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf setZero(int i, int i2) {
        throw b();
    }

    @Override // io.netty.buffer.ByteBuf
    public int setBytes(int i, ScatteringByteChannel scatteringByteChannel, int i2) {
        throw b();
    }

    @Override // io.netty.buffer.ByteBuf
    public int setBytes(int i, FileChannel fileChannel, long j, int i2) {
        throw b();
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf setIndex(int i, int i2) {
        throw b();
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf setInt(int i, int i2) {
        throw b();
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf setIntLE(int i, int i2) {
        throw b();
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf setLong(int i, long j) {
        throw b();
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf setLongLE(int i, long j) {
        throw b();
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf setMedium(int i, int i2) {
        throw b();
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf setMediumLE(int i, int i2) {
        throw b();
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf setShort(int i, int i2) {
        throw b();
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf setShortLE(int i, int i2) {
        throw b();
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf setChar(int i, int i2) {
        throw b();
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf setFloat(int i, float f) {
        throw b();
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf setDouble(int i, double d) {
        throw b();
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf skipBytes(int i) {
        a(i);
        this.c.skipBytes(i);
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf slice() {
        throw b();
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf retainedSlice() {
        throw b();
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf slice(int i, int i2) {
        a(i, i2);
        return this.c.slice(i, i2);
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf retainedSlice(int i, int i2) {
        a(i, i2);
        return this.c.slice(i, i2);
    }

    @Override // io.netty.buffer.ByteBuf
    public int nioBufferCount() {
        return this.c.nioBufferCount();
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuffer nioBuffer() {
        throw b();
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuffer nioBuffer(int i, int i2) {
        a(i, i2);
        return this.c.nioBuffer(i, i2);
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuffer[] nioBuffers() {
        throw b();
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuffer[] nioBuffers(int i, int i2) {
        a(i, i2);
        return this.c.nioBuffers(i, i2);
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuffer internalNioBuffer(int i, int i2) {
        a(i, i2);
        return this.c.internalNioBuffer(i, i2);
    }

    @Override // io.netty.buffer.ByteBuf
    public String toString(int i, int i2, Charset charset) {
        a(i, i2);
        return this.c.toString(i, i2, charset);
    }

    @Override // io.netty.buffer.ByteBuf
    public String toString(Charset charset) {
        throw b();
    }

    @Override // io.netty.buffer.ByteBuf
    public String toString() {
        return StringUtil.simpleClassName(this) + "(ridx=" + readerIndex() + ", widx=" + writerIndex() + ')';
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf writeBoolean(boolean z) {
        throw b();
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf writeByte(int i) {
        throw b();
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf writeBytes(byte[] bArr, int i, int i2) {
        throw b();
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf writeBytes(byte[] bArr) {
        throw b();
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf writeBytes(ByteBuffer byteBuffer) {
        throw b();
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf writeBytes(ByteBuf byteBuf, int i, int i2) {
        throw b();
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf writeBytes(ByteBuf byteBuf, int i) {
        throw b();
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf writeBytes(ByteBuf byteBuf) {
        throw b();
    }

    @Override // io.netty.buffer.ByteBuf
    public int writeBytes(InputStream inputStream, int i) {
        throw b();
    }

    @Override // io.netty.buffer.ByteBuf
    public int writeBytes(ScatteringByteChannel scatteringByteChannel, int i) {
        throw b();
    }

    @Override // io.netty.buffer.ByteBuf
    public int writeBytes(FileChannel fileChannel, long j, int i) {
        throw b();
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf writeInt(int i) {
        throw b();
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf writeIntLE(int i) {
        throw b();
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf writeLong(long j) {
        throw b();
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf writeLongLE(long j) {
        throw b();
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf writeMedium(int i) {
        throw b();
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf writeMediumLE(int i) {
        throw b();
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf writeZero(int i) {
        throw b();
    }

    @Override // io.netty.buffer.ByteBuf
    public int writerIndex() {
        return this.c.writerIndex();
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf writerIndex(int i) {
        throw b();
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf writeShort(int i) {
        throw b();
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf writeShortLE(int i) {
        throw b();
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf writeChar(int i) {
        throw b();
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf writeFloat(float f) {
        throw b();
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf writeDouble(double d) {
        throw b();
    }

    @Override // io.netty.buffer.ByteBuf
    public int setCharSequence(int i, CharSequence charSequence, Charset charset) {
        throw b();
    }

    @Override // io.netty.buffer.ByteBuf
    public int writeCharSequence(CharSequence charSequence, Charset charset) {
        throw b();
    }

    private void a(int i, int i2) {
        if (i + i2 > this.c.writerIndex()) {
            throw b;
        }
    }

    private void a(int i) {
        if (this.c.readableBytes() < i) {
            throw b;
        }
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf discardSomeReadBytes() {
        throw b();
    }

    @Override // io.netty.util.ReferenceCounted
    public int refCnt() {
        return this.c.refCnt();
    }

    @Override // io.netty.buffer.ByteBuf, io.netty.util.ReferenceCounted
    public ByteBuf retain() {
        throw b();
    }

    @Override // io.netty.buffer.ByteBuf, io.netty.util.ReferenceCounted
    public ByteBuf retain(int i) {
        throw b();
    }

    @Override // io.netty.buffer.ByteBuf, io.netty.util.ReferenceCounted
    public ByteBuf touch() {
        this.c.touch();
        return this;
    }

    @Override // io.netty.buffer.ByteBuf, io.netty.util.ReferenceCounted
    public ByteBuf touch(Object obj) {
        this.c.touch(obj);
        return this;
    }

    @Override // io.netty.util.ReferenceCounted
    public boolean release() {
        throw b();
    }

    @Override // io.netty.util.ReferenceCounted
    public boolean release(int i) {
        throw b();
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf unwrap() {
        throw b();
    }

    private static UnsupportedOperationException b() {
        return new UnsupportedOperationException("not a replayable operation");
    }
}
