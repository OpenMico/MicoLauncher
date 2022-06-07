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
import java.util.Iterator;
import java.util.List;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: WrappedCompositeByteBuf.java */
/* loaded from: classes4.dex */
public class af extends CompositeByteBuf {
    private final CompositeByteBuf e;

    public af(CompositeByteBuf compositeByteBuf) {
        super(compositeByteBuf.alloc());
        this.e = compositeByteBuf;
    }

    @Override // io.netty.buffer.AbstractReferenceCountedByteBuf, io.netty.util.ReferenceCounted
    public boolean release() {
        return this.e.release();
    }

    @Override // io.netty.buffer.AbstractReferenceCountedByteBuf, io.netty.util.ReferenceCounted
    public boolean release(int i) {
        return this.e.release(i);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public final int maxCapacity() {
        return this.e.maxCapacity();
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public final int readerIndex() {
        return this.e.readerIndex();
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public final int writerIndex() {
        return this.e.writerIndex();
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public final boolean isReadable() {
        return this.e.isReadable();
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public final boolean isReadable(int i) {
        return this.e.isReadable(i);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public final boolean isWritable() {
        return this.e.isWritable();
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public final boolean isWritable(int i) {
        return this.e.isWritable(i);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public final int readableBytes() {
        return this.e.readableBytes();
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public final int writableBytes() {
        return this.e.writableBytes();
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public final int maxWritableBytes() {
        return this.e.maxWritableBytes();
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public int ensureWritable(int i, boolean z) {
        return this.e.ensureWritable(i, z);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public ByteBuf order(ByteOrder byteOrder) {
        return this.e.order(byteOrder);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public boolean getBoolean(int i) {
        return this.e.getBoolean(i);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public short getUnsignedByte(int i) {
        return this.e.getUnsignedByte(i);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public short getShort(int i) {
        return this.e.getShort(i);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public short getShortLE(int i) {
        return this.e.getShortLE(i);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public int getUnsignedShort(int i) {
        return this.e.getUnsignedShort(i);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public int getUnsignedShortLE(int i) {
        return this.e.getUnsignedShortLE(i);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public int getUnsignedMedium(int i) {
        return this.e.getUnsignedMedium(i);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public int getUnsignedMediumLE(int i) {
        return this.e.getUnsignedMediumLE(i);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public int getMedium(int i) {
        return this.e.getMedium(i);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public int getMediumLE(int i) {
        return this.e.getMediumLE(i);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public int getInt(int i) {
        return this.e.getInt(i);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public int getIntLE(int i) {
        return this.e.getIntLE(i);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public long getUnsignedInt(int i) {
        return this.e.getUnsignedInt(i);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public long getUnsignedIntLE(int i) {
        return this.e.getUnsignedIntLE(i);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public long getLong(int i) {
        return this.e.getLong(i);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public long getLongLE(int i) {
        return this.e.getLongLE(i);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public char getChar(int i) {
        return this.e.getChar(i);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public float getFloat(int i) {
        return this.e.getFloat(i);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public double getDouble(int i) {
        return this.e.getDouble(i);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public ByteBuf setShortLE(int i, int i2) {
        return this.e.setShortLE(i, i2);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public ByteBuf setMediumLE(int i, int i2) {
        return this.e.setMediumLE(i, i2);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public ByteBuf setIntLE(int i, int i2) {
        return this.e.setIntLE(i, i2);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public ByteBuf setLongLE(int i, long j) {
        return this.e.setLongLE(i, j);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public byte readByte() {
        return this.e.readByte();
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public boolean readBoolean() {
        return this.e.readBoolean();
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public short readUnsignedByte() {
        return this.e.readUnsignedByte();
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public short readShort() {
        return this.e.readShort();
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public short readShortLE() {
        return this.e.readShortLE();
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public int readUnsignedShort() {
        return this.e.readUnsignedShort();
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public int readUnsignedShortLE() {
        return this.e.readUnsignedShortLE();
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public int readMedium() {
        return this.e.readMedium();
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public int readMediumLE() {
        return this.e.readMediumLE();
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public int readUnsignedMedium() {
        return this.e.readUnsignedMedium();
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public int readUnsignedMediumLE() {
        return this.e.readUnsignedMediumLE();
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public int readInt() {
        return this.e.readInt();
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public int readIntLE() {
        return this.e.readIntLE();
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public long readUnsignedInt() {
        return this.e.readUnsignedInt();
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public long readUnsignedIntLE() {
        return this.e.readUnsignedIntLE();
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public long readLong() {
        return this.e.readLong();
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public long readLongLE() {
        return this.e.readLongLE();
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public char readChar() {
        return this.e.readChar();
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public float readFloat() {
        return this.e.readFloat();
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public double readDouble() {
        return this.e.readDouble();
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public ByteBuf readBytes(int i) {
        return this.e.readBytes(i);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public ByteBuf slice() {
        return this.e.slice();
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public ByteBuf retainedSlice() {
        return this.e.retainedSlice();
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public ByteBuf slice(int i, int i2) {
        return this.e.slice(i, i2);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public ByteBuf retainedSlice(int i, int i2) {
        return this.e.retainedSlice(i, i2);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public ByteBuffer nioBuffer() {
        return this.e.nioBuffer();
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public String toString(Charset charset) {
        return this.e.toString(charset);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public String toString(int i, int i2, Charset charset) {
        return this.e.toString(i, i2, charset);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public int indexOf(int i, int i2, byte b) {
        return this.e.indexOf(i, i2, b);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public int bytesBefore(byte b) {
        return this.e.bytesBefore(b);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public int bytesBefore(int i, byte b) {
        return this.e.bytesBefore(i, b);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public int bytesBefore(int i, int i2, byte b) {
        return this.e.bytesBefore(i, i2, b);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public int forEachByte(ByteProcessor byteProcessor) {
        return this.e.forEachByte(byteProcessor);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public int forEachByte(int i, int i2, ByteProcessor byteProcessor) {
        return this.e.forEachByte(i, i2, byteProcessor);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public int forEachByteDesc(ByteProcessor byteProcessor) {
        return this.e.forEachByteDesc(byteProcessor);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public int forEachByteDesc(int i, int i2, ByteProcessor byteProcessor) {
        return this.e.forEachByteDesc(i, i2, byteProcessor);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public final int hashCode() {
        return this.e.hashCode();
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public final boolean equals(Object obj) {
        return this.e.equals(obj);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public final int compareTo(ByteBuf byteBuf) {
        return this.e.compareTo(byteBuf);
    }

    @Override // io.netty.buffer.AbstractReferenceCountedByteBuf, io.netty.util.ReferenceCounted
    public final int refCnt() {
        return this.e.refCnt();
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public ByteBuf duplicate() {
        return this.e.duplicate();
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public ByteBuf retainedDuplicate() {
        return this.e.retainedDuplicate();
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public ByteBuf readSlice(int i) {
        return this.e.readSlice(i);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public ByteBuf readRetainedSlice(int i) {
        return this.e.readRetainedSlice(i);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public int readBytes(GatheringByteChannel gatheringByteChannel, int i) throws IOException {
        return this.e.readBytes(gatheringByteChannel, i);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public ByteBuf writeShortLE(int i) {
        return this.e.writeShortLE(i);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public ByteBuf writeMediumLE(int i) {
        return this.e.writeMediumLE(i);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public ByteBuf writeIntLE(int i) {
        return this.e.writeIntLE(i);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public ByteBuf writeLongLE(long j) {
        return this.e.writeLongLE(j);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public int writeBytes(InputStream inputStream, int i) throws IOException {
        return this.e.writeBytes(inputStream, i);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public int writeBytes(ScatteringByteChannel scatteringByteChannel, int i) throws IOException {
        return this.e.writeBytes(scatteringByteChannel, i);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public ByteBuf copy() {
        return this.e.copy();
    }

    @Override // io.netty.buffer.CompositeByteBuf
    public CompositeByteBuf addComponent(ByteBuf byteBuf) {
        this.e.addComponent(byteBuf);
        return this;
    }

    @Override // io.netty.buffer.CompositeByteBuf
    public CompositeByteBuf addComponents(ByteBuf... byteBufArr) {
        this.e.addComponents(byteBufArr);
        return this;
    }

    @Override // io.netty.buffer.CompositeByteBuf
    public CompositeByteBuf addComponents(Iterable<ByteBuf> iterable) {
        this.e.addComponents(iterable);
        return this;
    }

    @Override // io.netty.buffer.CompositeByteBuf
    public CompositeByteBuf addComponent(int i, ByteBuf byteBuf) {
        this.e.addComponent(i, byteBuf);
        return this;
    }

    @Override // io.netty.buffer.CompositeByteBuf
    public CompositeByteBuf addComponents(int i, ByteBuf... byteBufArr) {
        this.e.addComponents(i, byteBufArr);
        return this;
    }

    @Override // io.netty.buffer.CompositeByteBuf
    public CompositeByteBuf addComponents(int i, Iterable<ByteBuf> iterable) {
        this.e.addComponents(i, iterable);
        return this;
    }

    @Override // io.netty.buffer.CompositeByteBuf
    public CompositeByteBuf addComponent(boolean z, ByteBuf byteBuf) {
        this.e.addComponent(z, byteBuf);
        return this;
    }

    @Override // io.netty.buffer.CompositeByteBuf
    public CompositeByteBuf addComponents(boolean z, ByteBuf... byteBufArr) {
        this.e.addComponents(z, byteBufArr);
        return this;
    }

    @Override // io.netty.buffer.CompositeByteBuf
    public CompositeByteBuf addComponents(boolean z, Iterable<ByteBuf> iterable) {
        this.e.addComponents(z, iterable);
        return this;
    }

    @Override // io.netty.buffer.CompositeByteBuf
    public CompositeByteBuf addComponent(boolean z, int i, ByteBuf byteBuf) {
        this.e.addComponent(z, i, byteBuf);
        return this;
    }

    @Override // io.netty.buffer.CompositeByteBuf
    public CompositeByteBuf removeComponent(int i) {
        this.e.removeComponent(i);
        return this;
    }

    @Override // io.netty.buffer.CompositeByteBuf
    public CompositeByteBuf removeComponents(int i, int i2) {
        this.e.removeComponents(i, i2);
        return this;
    }

    @Override // io.netty.buffer.CompositeByteBuf, java.lang.Iterable
    public Iterator<ByteBuf> iterator() {
        return this.e.iterator();
    }

    @Override // io.netty.buffer.CompositeByteBuf
    public List<ByteBuf> decompose(int i, int i2) {
        return this.e.decompose(i, i2);
    }

    @Override // io.netty.buffer.CompositeByteBuf, io.netty.buffer.ByteBuf
    public final boolean isDirect() {
        return this.e.isDirect();
    }

    @Override // io.netty.buffer.CompositeByteBuf, io.netty.buffer.ByteBuf
    public final boolean hasArray() {
        return this.e.hasArray();
    }

    @Override // io.netty.buffer.CompositeByteBuf, io.netty.buffer.ByteBuf
    public final byte[] array() {
        return this.e.array();
    }

    @Override // io.netty.buffer.CompositeByteBuf, io.netty.buffer.ByteBuf
    public final int arrayOffset() {
        return this.e.arrayOffset();
    }

    @Override // io.netty.buffer.CompositeByteBuf, io.netty.buffer.ByteBuf
    public final boolean hasMemoryAddress() {
        return this.e.hasMemoryAddress();
    }

    @Override // io.netty.buffer.CompositeByteBuf, io.netty.buffer.ByteBuf
    public final long memoryAddress() {
        return this.e.memoryAddress();
    }

    @Override // io.netty.buffer.CompositeByteBuf, io.netty.buffer.ByteBuf
    public final int capacity() {
        return this.e.capacity();
    }

    @Override // io.netty.buffer.CompositeByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf capacity(int i) {
        this.e.capacity(i);
        return this;
    }

    @Override // io.netty.buffer.CompositeByteBuf, io.netty.buffer.ByteBuf
    public final ByteBufAllocator alloc() {
        return this.e.alloc();
    }

    @Override // io.netty.buffer.CompositeByteBuf, io.netty.buffer.ByteBuf
    public final ByteOrder order() {
        return this.e.order();
    }

    @Override // io.netty.buffer.CompositeByteBuf
    public final int numComponents() {
        return this.e.numComponents();
    }

    @Override // io.netty.buffer.CompositeByteBuf
    public final int maxNumComponents() {
        return this.e.maxNumComponents();
    }

    @Override // io.netty.buffer.CompositeByteBuf
    public final int toComponentIndex(int i) {
        return this.e.toComponentIndex(i);
    }

    @Override // io.netty.buffer.CompositeByteBuf
    public final int toByteIndex(int i) {
        return this.e.toByteIndex(i);
    }

    @Override // io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public byte getByte(int i) {
        return this.e.getByte(i);
    }

    @Override // io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractByteBuf
    public final byte _getByte(int i) {
        return this.e._getByte(i);
    }

    @Override // io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractByteBuf
    public final short _getShort(int i) {
        return this.e._getShort(i);
    }

    @Override // io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractByteBuf
    public final short _getShortLE(int i) {
        return this.e._getShortLE(i);
    }

    @Override // io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractByteBuf
    public final int _getUnsignedMedium(int i) {
        return this.e._getUnsignedMedium(i);
    }

    @Override // io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractByteBuf
    public final int _getUnsignedMediumLE(int i) {
        return this.e._getUnsignedMediumLE(i);
    }

    @Override // io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractByteBuf
    public final int _getInt(int i) {
        return this.e._getInt(i);
    }

    @Override // io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractByteBuf
    public final int _getIntLE(int i) {
        return this.e._getIntLE(i);
    }

    @Override // io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractByteBuf
    public final long _getLong(int i) {
        return this.e._getLong(i);
    }

    @Override // io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractByteBuf
    public final long _getLongLE(int i) {
        return this.e._getLongLE(i);
    }

    @Override // io.netty.buffer.CompositeByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf getBytes(int i, byte[] bArr, int i2, int i3) {
        this.e.getBytes(i, bArr, i2, i3);
        return this;
    }

    @Override // io.netty.buffer.CompositeByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf getBytes(int i, ByteBuffer byteBuffer) {
        this.e.getBytes(i, byteBuffer);
        return this;
    }

    @Override // io.netty.buffer.CompositeByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf getBytes(int i, ByteBuf byteBuf, int i2, int i3) {
        this.e.getBytes(i, byteBuf, i2, i3);
        return this;
    }

    @Override // io.netty.buffer.CompositeByteBuf, io.netty.buffer.ByteBuf
    public int getBytes(int i, GatheringByteChannel gatheringByteChannel, int i2) throws IOException {
        return this.e.getBytes(i, gatheringByteChannel, i2);
    }

    @Override // io.netty.buffer.CompositeByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf getBytes(int i, OutputStream outputStream, int i2) throws IOException {
        this.e.getBytes(i, outputStream, i2);
        return this;
    }

    @Override // io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf setByte(int i, int i2) {
        this.e.setByte(i, i2);
        return this;
    }

    @Override // io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractByteBuf
    public final void _setByte(int i, int i2) {
        this.e._setByte(i, i2);
    }

    @Override // io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf setShort(int i, int i2) {
        this.e.setShort(i, i2);
        return this;
    }

    @Override // io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractByteBuf
    public final void _setShort(int i, int i2) {
        this.e._setShort(i, i2);
    }

    @Override // io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractByteBuf
    public final void _setShortLE(int i, int i2) {
        this.e._setShortLE(i, i2);
    }

    @Override // io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf setMedium(int i, int i2) {
        this.e.setMedium(i, i2);
        return this;
    }

    @Override // io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractByteBuf
    public final void _setMedium(int i, int i2) {
        this.e._setMedium(i, i2);
    }

    @Override // io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractByteBuf
    public final void _setMediumLE(int i, int i2) {
        this.e._setMediumLE(i, i2);
    }

    @Override // io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf setInt(int i, int i2) {
        this.e.setInt(i, i2);
        return this;
    }

    @Override // io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractByteBuf
    public final void _setInt(int i, int i2) {
        this.e._setInt(i, i2);
    }

    @Override // io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractByteBuf
    public final void _setIntLE(int i, int i2) {
        this.e._setIntLE(i, i2);
    }

    @Override // io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf setLong(int i, long j) {
        this.e.setLong(i, j);
        return this;
    }

    @Override // io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractByteBuf
    public final void _setLong(int i, long j) {
        this.e._setLong(i, j);
    }

    @Override // io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractByteBuf
    public final void _setLongLE(int i, long j) {
        this.e._setLongLE(i, j);
    }

    @Override // io.netty.buffer.CompositeByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf setBytes(int i, byte[] bArr, int i2, int i3) {
        this.e.setBytes(i, bArr, i2, i3);
        return this;
    }

    @Override // io.netty.buffer.CompositeByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf setBytes(int i, ByteBuffer byteBuffer) {
        this.e.setBytes(i, byteBuffer);
        return this;
    }

    @Override // io.netty.buffer.CompositeByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf setBytes(int i, ByteBuf byteBuf, int i2, int i3) {
        this.e.setBytes(i, byteBuf, i2, i3);
        return this;
    }

    @Override // io.netty.buffer.CompositeByteBuf, io.netty.buffer.ByteBuf
    public int setBytes(int i, InputStream inputStream, int i2) throws IOException {
        return this.e.setBytes(i, inputStream, i2);
    }

    @Override // io.netty.buffer.CompositeByteBuf, io.netty.buffer.ByteBuf
    public int setBytes(int i, ScatteringByteChannel scatteringByteChannel, int i2) throws IOException {
        return this.e.setBytes(i, scatteringByteChannel, i2);
    }

    @Override // io.netty.buffer.CompositeByteBuf, io.netty.buffer.ByteBuf
    public ByteBuf copy(int i, int i2) {
        return this.e.copy(i, i2);
    }

    @Override // io.netty.buffer.CompositeByteBuf
    public final ByteBuf component(int i) {
        return this.e.component(i);
    }

    @Override // io.netty.buffer.CompositeByteBuf
    public final ByteBuf componentAtOffset(int i) {
        return this.e.componentAtOffset(i);
    }

    @Override // io.netty.buffer.CompositeByteBuf
    public final ByteBuf internalComponent(int i) {
        return this.e.internalComponent(i);
    }

    @Override // io.netty.buffer.CompositeByteBuf
    public final ByteBuf internalComponentAtOffset(int i) {
        return this.e.internalComponentAtOffset(i);
    }

    @Override // io.netty.buffer.CompositeByteBuf, io.netty.buffer.ByteBuf
    public int nioBufferCount() {
        return this.e.nioBufferCount();
    }

    @Override // io.netty.buffer.CompositeByteBuf, io.netty.buffer.ByteBuf
    public ByteBuffer internalNioBuffer(int i, int i2) {
        return this.e.internalNioBuffer(i, i2);
    }

    @Override // io.netty.buffer.CompositeByteBuf, io.netty.buffer.ByteBuf
    public ByteBuffer nioBuffer(int i, int i2) {
        return this.e.nioBuffer(i, i2);
    }

    @Override // io.netty.buffer.CompositeByteBuf, io.netty.buffer.ByteBuf
    public ByteBuffer[] nioBuffers(int i, int i2) {
        return this.e.nioBuffers(i, i2);
    }

    @Override // io.netty.buffer.CompositeByteBuf
    public CompositeByteBuf consolidate() {
        this.e.consolidate();
        return this;
    }

    @Override // io.netty.buffer.CompositeByteBuf
    public CompositeByteBuf consolidate(int i, int i2) {
        this.e.consolidate(i, i2);
        return this;
    }

    @Override // io.netty.buffer.CompositeByteBuf
    public CompositeByteBuf discardReadComponents() {
        this.e.discardReadComponents();
        return this;
    }

    @Override // io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf discardReadBytes() {
        this.e.discardReadBytes();
        return this;
    }

    @Override // io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public final String toString() {
        return this.e.toString();
    }

    @Override // io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public final CompositeByteBuf readerIndex(int i) {
        this.e.readerIndex(i);
        return this;
    }

    @Override // io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public final CompositeByteBuf writerIndex(int i) {
        this.e.writerIndex(i);
        return this;
    }

    @Override // io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public final CompositeByteBuf setIndex(int i, int i2) {
        this.e.setIndex(i, i2);
        return this;
    }

    @Override // io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public final CompositeByteBuf clear() {
        this.e.clear();
        return this;
    }

    @Override // io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public final CompositeByteBuf markReaderIndex() {
        this.e.markReaderIndex();
        return this;
    }

    @Override // io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public final CompositeByteBuf resetReaderIndex() {
        this.e.resetReaderIndex();
        return this;
    }

    @Override // io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public final CompositeByteBuf markWriterIndex() {
        this.e.markWriterIndex();
        return this;
    }

    @Override // io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public final CompositeByteBuf resetWriterIndex() {
        this.e.resetWriterIndex();
        return this;
    }

    @Override // io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf ensureWritable(int i) {
        this.e.ensureWritable(i);
        return this;
    }

    @Override // io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf getBytes(int i, ByteBuf byteBuf) {
        this.e.getBytes(i, byteBuf);
        return this;
    }

    @Override // io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf getBytes(int i, ByteBuf byteBuf, int i2) {
        this.e.getBytes(i, byteBuf, i2);
        return this;
    }

    @Override // io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf getBytes(int i, byte[] bArr) {
        this.e.getBytes(i, bArr);
        return this;
    }

    @Override // io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf setBoolean(int i, boolean z) {
        this.e.setBoolean(i, z);
        return this;
    }

    @Override // io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf setChar(int i, int i2) {
        this.e.setChar(i, i2);
        return this;
    }

    @Override // io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf setFloat(int i, float f) {
        this.e.setFloat(i, f);
        return this;
    }

    @Override // io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf setDouble(int i, double d) {
        this.e.setDouble(i, d);
        return this;
    }

    @Override // io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf setBytes(int i, ByteBuf byteBuf) {
        this.e.setBytes(i, byteBuf);
        return this;
    }

    @Override // io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf setBytes(int i, ByteBuf byteBuf, int i2) {
        this.e.setBytes(i, byteBuf, i2);
        return this;
    }

    @Override // io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf setBytes(int i, byte[] bArr) {
        this.e.setBytes(i, bArr);
        return this;
    }

    @Override // io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf setZero(int i, int i2) {
        this.e.setZero(i, i2);
        return this;
    }

    @Override // io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf readBytes(ByteBuf byteBuf) {
        this.e.readBytes(byteBuf);
        return this;
    }

    @Override // io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf readBytes(ByteBuf byteBuf, int i) {
        this.e.readBytes(byteBuf, i);
        return this;
    }

    @Override // io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf readBytes(ByteBuf byteBuf, int i, int i2) {
        this.e.readBytes(byteBuf, i, i2);
        return this;
    }

    @Override // io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf readBytes(byte[] bArr) {
        this.e.readBytes(bArr);
        return this;
    }

    @Override // io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf readBytes(byte[] bArr, int i, int i2) {
        this.e.readBytes(bArr, i, i2);
        return this;
    }

    @Override // io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf readBytes(ByteBuffer byteBuffer) {
        this.e.readBytes(byteBuffer);
        return this;
    }

    @Override // io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf readBytes(OutputStream outputStream, int i) throws IOException {
        this.e.readBytes(outputStream, i);
        return this;
    }

    @Override // io.netty.buffer.CompositeByteBuf, io.netty.buffer.ByteBuf
    public int getBytes(int i, FileChannel fileChannel, long j, int i2) throws IOException {
        return this.e.getBytes(i, fileChannel, j, i2);
    }

    @Override // io.netty.buffer.CompositeByteBuf, io.netty.buffer.ByteBuf
    public int setBytes(int i, FileChannel fileChannel, long j, int i2) throws IOException {
        return this.e.setBytes(i, fileChannel, j, i2);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public boolean isReadOnly() {
        return this.e.isReadOnly();
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public ByteBuf asReadOnly() {
        return this.e.asReadOnly();
    }

    @Override // io.netty.buffer.AbstractByteBuf
    protected SwappedByteBuf newSwappedByteBuf() {
        return this.e.newSwappedByteBuf();
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CharSequence getCharSequence(int i, int i2, Charset charset) {
        return this.e.getCharSequence(i, i2, charset);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CharSequence readCharSequence(int i, Charset charset) {
        return this.e.readCharSequence(i, charset);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public int setCharSequence(int i, CharSequence charSequence, Charset charset) {
        return this.e.setCharSequence(i, charSequence, charset);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public int readBytes(FileChannel fileChannel, long j, int i) throws IOException {
        return this.e.readBytes(fileChannel, j, i);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public int writeBytes(FileChannel fileChannel, long j, int i) throws IOException {
        return this.e.writeBytes(fileChannel, j, i);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public int writeCharSequence(CharSequence charSequence, Charset charset) {
        return this.e.writeCharSequence(charSequence, charset);
    }

    @Override // io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf skipBytes(int i) {
        this.e.skipBytes(i);
        return this;
    }

    @Override // io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf writeBoolean(boolean z) {
        this.e.writeBoolean(z);
        return this;
    }

    @Override // io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf writeByte(int i) {
        this.e.writeByte(i);
        return this;
    }

    @Override // io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf writeShort(int i) {
        this.e.writeShort(i);
        return this;
    }

    @Override // io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf writeMedium(int i) {
        this.e.writeMedium(i);
        return this;
    }

    @Override // io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf writeInt(int i) {
        this.e.writeInt(i);
        return this;
    }

    @Override // io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf writeLong(long j) {
        this.e.writeLong(j);
        return this;
    }

    @Override // io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf writeChar(int i) {
        this.e.writeChar(i);
        return this;
    }

    @Override // io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf writeFloat(float f) {
        this.e.writeFloat(f);
        return this;
    }

    @Override // io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf writeDouble(double d) {
        this.e.writeDouble(d);
        return this;
    }

    @Override // io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf writeBytes(ByteBuf byteBuf) {
        this.e.writeBytes(byteBuf);
        return this;
    }

    @Override // io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf writeBytes(ByteBuf byteBuf, int i) {
        this.e.writeBytes(byteBuf, i);
        return this;
    }

    @Override // io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf writeBytes(ByteBuf byteBuf, int i, int i2) {
        this.e.writeBytes(byteBuf, i, i2);
        return this;
    }

    @Override // io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf writeBytes(byte[] bArr) {
        this.e.writeBytes(bArr);
        return this;
    }

    @Override // io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf writeBytes(byte[] bArr, int i, int i2) {
        this.e.writeBytes(bArr, i, i2);
        return this;
    }

    @Override // io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf writeBytes(ByteBuffer byteBuffer) {
        this.e.writeBytes(byteBuffer);
        return this;
    }

    @Override // io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf writeZero(int i) {
        this.e.writeZero(i);
        return this;
    }

    @Override // io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractReferenceCountedByteBuf, io.netty.buffer.ByteBuf, io.netty.util.ReferenceCounted
    public CompositeByteBuf retain(int i) {
        this.e.retain(i);
        return this;
    }

    @Override // io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractReferenceCountedByteBuf, io.netty.buffer.ByteBuf, io.netty.util.ReferenceCounted
    public CompositeByteBuf retain() {
        this.e.retain();
        return this;
    }

    @Override // io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractReferenceCountedByteBuf, io.netty.buffer.ByteBuf, io.netty.util.ReferenceCounted
    public CompositeByteBuf touch() {
        this.e.touch();
        return this;
    }

    @Override // io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractReferenceCountedByteBuf, io.netty.buffer.ByteBuf, io.netty.util.ReferenceCounted
    public CompositeByteBuf touch(Object obj) {
        this.e.touch(obj);
        return this;
    }

    @Override // io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public ByteBuffer[] nioBuffers() {
        return this.e.nioBuffers();
    }

    @Override // io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf discardSomeReadBytes() {
        this.e.discardSomeReadBytes();
        return this;
    }

    @Override // io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractReferenceCountedByteBuf
    public final void deallocate() {
        this.e.deallocate();
    }

    @Override // io.netty.buffer.CompositeByteBuf, io.netty.buffer.ByteBuf
    public final ByteBuf unwrap() {
        return this.e;
    }
}
