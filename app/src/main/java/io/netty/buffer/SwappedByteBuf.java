package io.netty.buffer;

import io.netty.util.ByteProcessor;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;
import java.nio.charset.Charset;
import kotlin.UShort;

@Deprecated
/* loaded from: classes4.dex */
public class SwappedByteBuf extends ByteBuf {
    private final ByteBuf a;
    private final ByteOrder b;

    public SwappedByteBuf(ByteBuf byteBuf) {
        if (byteBuf != null) {
            this.a = byteBuf;
            if (byteBuf.order() == ByteOrder.BIG_ENDIAN) {
                this.b = ByteOrder.LITTLE_ENDIAN;
            } else {
                this.b = ByteOrder.BIG_ENDIAN;
            }
        } else {
            throw new NullPointerException("buf");
        }
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteOrder order() {
        return this.b;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf order(ByteOrder byteOrder) {
        if (byteOrder != null) {
            return byteOrder == this.b ? this : this.a;
        }
        throw new NullPointerException("endianness");
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf unwrap() {
        return this.a.unwrap();
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBufAllocator alloc() {
        return this.a.alloc();
    }

    @Override // io.netty.buffer.ByteBuf
    public int capacity() {
        return this.a.capacity();
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf capacity(int i) {
        this.a.capacity(i);
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public int maxCapacity() {
        return this.a.maxCapacity();
    }

    @Override // io.netty.buffer.ByteBuf
    public boolean isReadOnly() {
        return this.a.isReadOnly();
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf asReadOnly() {
        return Unpooled.unmodifiableBuffer(this);
    }

    @Override // io.netty.buffer.ByteBuf
    public boolean isDirect() {
        return this.a.isDirect();
    }

    @Override // io.netty.buffer.ByteBuf
    public int readerIndex() {
        return this.a.readerIndex();
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf readerIndex(int i) {
        this.a.readerIndex(i);
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public int writerIndex() {
        return this.a.writerIndex();
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf writerIndex(int i) {
        this.a.writerIndex(i);
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf setIndex(int i, int i2) {
        this.a.setIndex(i, i2);
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public int readableBytes() {
        return this.a.readableBytes();
    }

    @Override // io.netty.buffer.ByteBuf
    public int writableBytes() {
        return this.a.writableBytes();
    }

    @Override // io.netty.buffer.ByteBuf
    public int maxWritableBytes() {
        return this.a.maxWritableBytes();
    }

    @Override // io.netty.buffer.ByteBuf
    public boolean isReadable() {
        return this.a.isReadable();
    }

    @Override // io.netty.buffer.ByteBuf
    public boolean isReadable(int i) {
        return this.a.isReadable(i);
    }

    @Override // io.netty.buffer.ByteBuf
    public boolean isWritable() {
        return this.a.isWritable();
    }

    @Override // io.netty.buffer.ByteBuf
    public boolean isWritable(int i) {
        return this.a.isWritable(i);
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf clear() {
        this.a.clear();
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf markReaderIndex() {
        this.a.markReaderIndex();
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf resetReaderIndex() {
        this.a.resetReaderIndex();
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf markWriterIndex() {
        this.a.markWriterIndex();
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf resetWriterIndex() {
        this.a.resetWriterIndex();
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf discardReadBytes() {
        this.a.discardReadBytes();
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf discardSomeReadBytes() {
        this.a.discardSomeReadBytes();
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf ensureWritable(int i) {
        this.a.ensureWritable(i);
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public int ensureWritable(int i, boolean z) {
        return this.a.ensureWritable(i, z);
    }

    @Override // io.netty.buffer.ByteBuf
    public boolean getBoolean(int i) {
        return this.a.getBoolean(i);
    }

    @Override // io.netty.buffer.ByteBuf
    public byte getByte(int i) {
        return this.a.getByte(i);
    }

    @Override // io.netty.buffer.ByteBuf
    public short getUnsignedByte(int i) {
        return this.a.getUnsignedByte(i);
    }

    @Override // io.netty.buffer.ByteBuf
    public short getShort(int i) {
        return ByteBufUtil.swapShort(this.a.getShort(i));
    }

    @Override // io.netty.buffer.ByteBuf
    public short getShortLE(int i) {
        return this.a.getShort(i);
    }

    @Override // io.netty.buffer.ByteBuf
    public int getUnsignedShort(int i) {
        return getShort(i) & UShort.MAX_VALUE;
    }

    @Override // io.netty.buffer.ByteBuf
    public int getUnsignedShortLE(int i) {
        return getShortLE(i) & UShort.MAX_VALUE;
    }

    @Override // io.netty.buffer.ByteBuf
    public int getMedium(int i) {
        return ByteBufUtil.swapMedium(this.a.getMedium(i));
    }

    @Override // io.netty.buffer.ByteBuf
    public int getMediumLE(int i) {
        return this.a.getMedium(i);
    }

    @Override // io.netty.buffer.ByteBuf
    public int getUnsignedMedium(int i) {
        return getMedium(i) & 16777215;
    }

    @Override // io.netty.buffer.ByteBuf
    public int getUnsignedMediumLE(int i) {
        return getMediumLE(i) & 16777215;
    }

    @Override // io.netty.buffer.ByteBuf
    public int getInt(int i) {
        return ByteBufUtil.swapInt(this.a.getInt(i));
    }

    @Override // io.netty.buffer.ByteBuf
    public int getIntLE(int i) {
        return this.a.getInt(i);
    }

    @Override // io.netty.buffer.ByteBuf
    public long getUnsignedInt(int i) {
        return getInt(i) & 4294967295L;
    }

    @Override // io.netty.buffer.ByteBuf
    public long getUnsignedIntLE(int i) {
        return getIntLE(i) & 4294967295L;
    }

    @Override // io.netty.buffer.ByteBuf
    public long getLong(int i) {
        return ByteBufUtil.swapLong(this.a.getLong(i));
    }

    @Override // io.netty.buffer.ByteBuf
    public long getLongLE(int i) {
        return this.a.getLong(i);
    }

    @Override // io.netty.buffer.ByteBuf
    public char getChar(int i) {
        return (char) getShort(i);
    }

    @Override // io.netty.buffer.ByteBuf
    public float getFloat(int i) {
        return Float.intBitsToFloat(getInt(i));
    }

    @Override // io.netty.buffer.ByteBuf
    public double getDouble(int i) {
        return Double.longBitsToDouble(getLong(i));
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf getBytes(int i, ByteBuf byteBuf) {
        this.a.getBytes(i, byteBuf);
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf getBytes(int i, ByteBuf byteBuf, int i2) {
        this.a.getBytes(i, byteBuf, i2);
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf getBytes(int i, ByteBuf byteBuf, int i2, int i3) {
        this.a.getBytes(i, byteBuf, i2, i3);
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf getBytes(int i, byte[] bArr) {
        this.a.getBytes(i, bArr);
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf getBytes(int i, byte[] bArr, int i2, int i3) {
        this.a.getBytes(i, bArr, i2, i3);
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf getBytes(int i, ByteBuffer byteBuffer) {
        this.a.getBytes(i, byteBuffer);
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf getBytes(int i, OutputStream outputStream, int i2) throws IOException {
        this.a.getBytes(i, outputStream, i2);
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public int getBytes(int i, GatheringByteChannel gatheringByteChannel, int i2) throws IOException {
        return this.a.getBytes(i, gatheringByteChannel, i2);
    }

    @Override // io.netty.buffer.ByteBuf
    public int getBytes(int i, FileChannel fileChannel, long j, int i2) throws IOException {
        return this.a.getBytes(i, fileChannel, j, i2);
    }

    @Override // io.netty.buffer.ByteBuf
    public CharSequence getCharSequence(int i, int i2, Charset charset) {
        return this.a.getCharSequence(i, i2, charset);
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf setBoolean(int i, boolean z) {
        this.a.setBoolean(i, z);
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf setByte(int i, int i2) {
        this.a.setByte(i, i2);
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf setShort(int i, int i2) {
        this.a.setShort(i, ByteBufUtil.swapShort((short) i2));
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf setShortLE(int i, int i2) {
        this.a.setShort(i, (short) i2);
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf setMedium(int i, int i2) {
        this.a.setMedium(i, ByteBufUtil.swapMedium(i2));
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf setMediumLE(int i, int i2) {
        this.a.setMedium(i, i2);
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf setInt(int i, int i2) {
        this.a.setInt(i, ByteBufUtil.swapInt(i2));
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf setIntLE(int i, int i2) {
        this.a.setInt(i, i2);
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf setLong(int i, long j) {
        this.a.setLong(i, ByteBufUtil.swapLong(j));
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf setLongLE(int i, long j) {
        this.a.setLong(i, j);
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf setChar(int i, int i2) {
        setShort(i, i2);
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf setFloat(int i, float f) {
        setInt(i, Float.floatToRawIntBits(f));
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf setDouble(int i, double d) {
        setLong(i, Double.doubleToRawLongBits(d));
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf setBytes(int i, ByteBuf byteBuf) {
        this.a.setBytes(i, byteBuf);
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf setBytes(int i, ByteBuf byteBuf, int i2) {
        this.a.setBytes(i, byteBuf, i2);
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf setBytes(int i, ByteBuf byteBuf, int i2, int i3) {
        this.a.setBytes(i, byteBuf, i2, i3);
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf setBytes(int i, byte[] bArr) {
        this.a.setBytes(i, bArr);
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf setBytes(int i, byte[] bArr, int i2, int i3) {
        this.a.setBytes(i, bArr, i2, i3);
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf setBytes(int i, ByteBuffer byteBuffer) {
        this.a.setBytes(i, byteBuffer);
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public int setBytes(int i, InputStream inputStream, int i2) throws IOException {
        return this.a.setBytes(i, inputStream, i2);
    }

    @Override // io.netty.buffer.ByteBuf
    public int setBytes(int i, ScatteringByteChannel scatteringByteChannel, int i2) throws IOException {
        return this.a.setBytes(i, scatteringByteChannel, i2);
    }

    @Override // io.netty.buffer.ByteBuf
    public int setBytes(int i, FileChannel fileChannel, long j, int i2) throws IOException {
        return this.a.setBytes(i, fileChannel, j, i2);
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf setZero(int i, int i2) {
        this.a.setZero(i, i2);
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public int setCharSequence(int i, CharSequence charSequence, Charset charset) {
        return this.a.setCharSequence(i, charSequence, charset);
    }

    @Override // io.netty.buffer.ByteBuf
    public boolean readBoolean() {
        return this.a.readBoolean();
    }

    @Override // io.netty.buffer.ByteBuf
    public byte readByte() {
        return this.a.readByte();
    }

    @Override // io.netty.buffer.ByteBuf
    public short readUnsignedByte() {
        return this.a.readUnsignedByte();
    }

    @Override // io.netty.buffer.ByteBuf
    public short readShort() {
        return ByteBufUtil.swapShort(this.a.readShort());
    }

    @Override // io.netty.buffer.ByteBuf
    public short readShortLE() {
        return this.a.readShort();
    }

    @Override // io.netty.buffer.ByteBuf
    public int readUnsignedShort() {
        return readShort() & UShort.MAX_VALUE;
    }

    @Override // io.netty.buffer.ByteBuf
    public int readUnsignedShortLE() {
        return readShortLE() & UShort.MAX_VALUE;
    }

    @Override // io.netty.buffer.ByteBuf
    public int readMedium() {
        return ByteBufUtil.swapMedium(this.a.readMedium());
    }

    @Override // io.netty.buffer.ByteBuf
    public int readMediumLE() {
        return this.a.readMedium();
    }

    @Override // io.netty.buffer.ByteBuf
    public int readUnsignedMedium() {
        return readMedium() & 16777215;
    }

    @Override // io.netty.buffer.ByteBuf
    public int readUnsignedMediumLE() {
        return readMediumLE() & 16777215;
    }

    @Override // io.netty.buffer.ByteBuf
    public int readInt() {
        return ByteBufUtil.swapInt(this.a.readInt());
    }

    @Override // io.netty.buffer.ByteBuf
    public int readIntLE() {
        return this.a.readInt();
    }

    @Override // io.netty.buffer.ByteBuf
    public long readUnsignedInt() {
        return readInt() & 4294967295L;
    }

    @Override // io.netty.buffer.ByteBuf
    public long readUnsignedIntLE() {
        return readIntLE() & 4294967295L;
    }

    @Override // io.netty.buffer.ByteBuf
    public long readLong() {
        return ByteBufUtil.swapLong(this.a.readLong());
    }

    @Override // io.netty.buffer.ByteBuf
    public long readLongLE() {
        return this.a.readLong();
    }

    @Override // io.netty.buffer.ByteBuf
    public char readChar() {
        return (char) readShort();
    }

    @Override // io.netty.buffer.ByteBuf
    public float readFloat() {
        return Float.intBitsToFloat(readInt());
    }

    @Override // io.netty.buffer.ByteBuf
    public double readDouble() {
        return Double.longBitsToDouble(readLong());
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf readBytes(int i) {
        return this.a.readBytes(i).order(order());
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf readSlice(int i) {
        return this.a.readSlice(i).order(this.b);
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf readRetainedSlice(int i) {
        return this.a.readRetainedSlice(i).order(this.b);
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf readBytes(ByteBuf byteBuf) {
        this.a.readBytes(byteBuf);
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf readBytes(ByteBuf byteBuf, int i) {
        this.a.readBytes(byteBuf, i);
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf readBytes(ByteBuf byteBuf, int i, int i2) {
        this.a.readBytes(byteBuf, i, i2);
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf readBytes(byte[] bArr) {
        this.a.readBytes(bArr);
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf readBytes(byte[] bArr, int i, int i2) {
        this.a.readBytes(bArr, i, i2);
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf readBytes(ByteBuffer byteBuffer) {
        this.a.readBytes(byteBuffer);
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf readBytes(OutputStream outputStream, int i) throws IOException {
        this.a.readBytes(outputStream, i);
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public int readBytes(GatheringByteChannel gatheringByteChannel, int i) throws IOException {
        return this.a.readBytes(gatheringByteChannel, i);
    }

    @Override // io.netty.buffer.ByteBuf
    public int readBytes(FileChannel fileChannel, long j, int i) throws IOException {
        return this.a.readBytes(fileChannel, j, i);
    }

    @Override // io.netty.buffer.ByteBuf
    public CharSequence readCharSequence(int i, Charset charset) {
        return this.a.readCharSequence(i, charset);
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf skipBytes(int i) {
        this.a.skipBytes(i);
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf writeBoolean(boolean z) {
        this.a.writeBoolean(z);
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf writeByte(int i) {
        this.a.writeByte(i);
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf writeShort(int i) {
        this.a.writeShort(ByteBufUtil.swapShort((short) i));
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf writeShortLE(int i) {
        this.a.writeShort((short) i);
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf writeMedium(int i) {
        this.a.writeMedium(ByteBufUtil.swapMedium(i));
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf writeMediumLE(int i) {
        this.a.writeMedium(i);
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf writeInt(int i) {
        this.a.writeInt(ByteBufUtil.swapInt(i));
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf writeIntLE(int i) {
        this.a.writeInt(i);
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf writeLong(long j) {
        this.a.writeLong(ByteBufUtil.swapLong(j));
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf writeLongLE(long j) {
        this.a.writeLong(j);
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf writeChar(int i) {
        writeShort(i);
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf writeFloat(float f) {
        writeInt(Float.floatToRawIntBits(f));
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf writeDouble(double d) {
        writeLong(Double.doubleToRawLongBits(d));
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf writeBytes(ByteBuf byteBuf) {
        this.a.writeBytes(byteBuf);
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf writeBytes(ByteBuf byteBuf, int i) {
        this.a.writeBytes(byteBuf, i);
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf writeBytes(ByteBuf byteBuf, int i, int i2) {
        this.a.writeBytes(byteBuf, i, i2);
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf writeBytes(byte[] bArr) {
        this.a.writeBytes(bArr);
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf writeBytes(byte[] bArr, int i, int i2) {
        this.a.writeBytes(bArr, i, i2);
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf writeBytes(ByteBuffer byteBuffer) {
        this.a.writeBytes(byteBuffer);
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public int writeBytes(InputStream inputStream, int i) throws IOException {
        return this.a.writeBytes(inputStream, i);
    }

    @Override // io.netty.buffer.ByteBuf
    public int writeBytes(ScatteringByteChannel scatteringByteChannel, int i) throws IOException {
        return this.a.writeBytes(scatteringByteChannel, i);
    }

    @Override // io.netty.buffer.ByteBuf
    public int writeBytes(FileChannel fileChannel, long j, int i) throws IOException {
        return this.a.writeBytes(fileChannel, j, i);
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf writeZero(int i) {
        this.a.writeZero(i);
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public int writeCharSequence(CharSequence charSequence, Charset charset) {
        return this.a.writeCharSequence(charSequence, charset);
    }

    @Override // io.netty.buffer.ByteBuf
    public int indexOf(int i, int i2, byte b) {
        return this.a.indexOf(i, i2, b);
    }

    @Override // io.netty.buffer.ByteBuf
    public int bytesBefore(byte b) {
        return this.a.bytesBefore(b);
    }

    @Override // io.netty.buffer.ByteBuf
    public int bytesBefore(int i, byte b) {
        return this.a.bytesBefore(i, b);
    }

    @Override // io.netty.buffer.ByteBuf
    public int bytesBefore(int i, int i2, byte b) {
        return this.a.bytesBefore(i, i2, b);
    }

    @Override // io.netty.buffer.ByteBuf
    public int forEachByte(ByteProcessor byteProcessor) {
        return this.a.forEachByte(byteProcessor);
    }

    @Override // io.netty.buffer.ByteBuf
    public int forEachByte(int i, int i2, ByteProcessor byteProcessor) {
        return this.a.forEachByte(i, i2, byteProcessor);
    }

    @Override // io.netty.buffer.ByteBuf
    public int forEachByteDesc(ByteProcessor byteProcessor) {
        return this.a.forEachByteDesc(byteProcessor);
    }

    @Override // io.netty.buffer.ByteBuf
    public int forEachByteDesc(int i, int i2, ByteProcessor byteProcessor) {
        return this.a.forEachByteDesc(i, i2, byteProcessor);
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf copy() {
        return this.a.copy().order(this.b);
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf copy(int i, int i2) {
        return this.a.copy(i, i2).order(this.b);
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf slice() {
        return this.a.slice().order(this.b);
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf retainedSlice() {
        return this.a.slice().order(this.b);
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf slice(int i, int i2) {
        return this.a.slice(i, i2).order(this.b);
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf retainedSlice(int i, int i2) {
        return this.a.slice(i, i2).order(this.b);
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf duplicate() {
        return this.a.duplicate().order(this.b);
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf retainedDuplicate() {
        return this.a.retainedDuplicate().order(this.b);
    }

    @Override // io.netty.buffer.ByteBuf
    public int nioBufferCount() {
        return this.a.nioBufferCount();
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuffer nioBuffer() {
        return this.a.nioBuffer().order(this.b);
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuffer nioBuffer(int i, int i2) {
        return this.a.nioBuffer(i, i2).order(this.b);
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuffer internalNioBuffer(int i, int i2) {
        return nioBuffer(i, i2);
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuffer[] nioBuffers() {
        ByteBuffer[] nioBuffers = this.a.nioBuffers();
        for (int i = 0; i < nioBuffers.length; i++) {
            nioBuffers[i] = nioBuffers[i].order(this.b);
        }
        return nioBuffers;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuffer[] nioBuffers(int i, int i2) {
        ByteBuffer[] nioBuffers = this.a.nioBuffers(i, i2);
        for (int i3 = 0; i3 < nioBuffers.length; i3++) {
            nioBuffers[i3] = nioBuffers[i3].order(this.b);
        }
        return nioBuffers;
    }

    @Override // io.netty.buffer.ByteBuf
    public boolean hasArray() {
        return this.a.hasArray();
    }

    @Override // io.netty.buffer.ByteBuf
    public byte[] array() {
        return this.a.array();
    }

    @Override // io.netty.buffer.ByteBuf
    public int arrayOffset() {
        return this.a.arrayOffset();
    }

    @Override // io.netty.buffer.ByteBuf
    public boolean hasMemoryAddress() {
        return this.a.hasMemoryAddress();
    }

    @Override // io.netty.buffer.ByteBuf
    public long memoryAddress() {
        return this.a.memoryAddress();
    }

    @Override // io.netty.buffer.ByteBuf
    public String toString(Charset charset) {
        return this.a.toString(charset);
    }

    @Override // io.netty.buffer.ByteBuf
    public String toString(int i, int i2, Charset charset) {
        return this.a.toString(i, i2, charset);
    }

    @Override // io.netty.util.ReferenceCounted
    public int refCnt() {
        return this.a.refCnt();
    }

    @Override // io.netty.buffer.ByteBuf, io.netty.util.ReferenceCounted
    public ByteBuf retain() {
        this.a.retain();
        return this;
    }

    @Override // io.netty.buffer.ByteBuf, io.netty.util.ReferenceCounted
    public ByteBuf retain(int i) {
        this.a.retain(i);
        return this;
    }

    @Override // io.netty.buffer.ByteBuf, io.netty.util.ReferenceCounted
    public ByteBuf touch() {
        this.a.touch();
        return this;
    }

    @Override // io.netty.buffer.ByteBuf, io.netty.util.ReferenceCounted
    public ByteBuf touch(Object obj) {
        this.a.touch(obj);
        return this;
    }

    @Override // io.netty.util.ReferenceCounted
    public boolean release() {
        return this.a.release();
    }

    @Override // io.netty.util.ReferenceCounted
    public boolean release(int i) {
        return this.a.release(i);
    }

    @Override // io.netty.buffer.ByteBuf
    public int hashCode() {
        return this.a.hashCode();
    }

    @Override // io.netty.buffer.ByteBuf
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof ByteBuf) {
            return ByteBufUtil.equals(this, (ByteBuf) obj);
        }
        return false;
    }

    @Override // io.netty.buffer.ByteBuf
    public int compareTo(ByteBuf byteBuf) {
        return ByteBufUtil.compare(this, byteBuf);
    }

    @Override // io.netty.buffer.ByteBuf
    public String toString() {
        return "Swapped(" + this.a + ')';
    }
}
