package io.netty.buffer;

import io.netty.util.ByteProcessor;
import io.netty.util.ResourceLeak;
import io.netty.util.internal.SystemPropertyUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;
import java.nio.charset.Charset;

/* compiled from: AdvancedLeakAwareByteBuf.java */
/* loaded from: classes4.dex */
final class c extends ae {
    private final ResourceLeak d;
    private static final InternalLogger c = InternalLoggerFactory.getInstance(c.class);
    private static final boolean b = SystemPropertyUtil.getBoolean("io.netty.leakDetection.acquireAndReleaseOnly", false);

    static {
        if (c.isDebugEnabled()) {
            c.debug("-D{}: {}", "io.netty.leakDetection.acquireAndReleaseOnly", Boolean.valueOf(b));
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public c(ByteBuf byteBuf, ResourceLeak resourceLeak) {
        super(byteBuf);
        this.d = resourceLeak;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void a(ResourceLeak resourceLeak) {
        if (!b) {
            resourceLeak.record();
        }
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public ByteBuf order(ByteOrder byteOrder) {
        a(this.d);
        return order() == byteOrder ? this : new c(super.order(byteOrder), this.d);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public ByteBuf slice() {
        a(this.d);
        return new c(super.slice(), this.d);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public ByteBuf retainedSlice() {
        a(this.d);
        return new c(super.retainedSlice(), this.d);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public ByteBuf slice(int i, int i2) {
        a(this.d);
        return new c(super.slice(i, i2), this.d);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public ByteBuf retainedSlice(int i, int i2) {
        a(this.d);
        return new c(super.retainedSlice(i, i2), this.d);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public ByteBuf duplicate() {
        a(this.d);
        return new c(super.duplicate(), this.d);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public ByteBuf retainedDuplicate() {
        a(this.d);
        return new c(super.retainedDuplicate(), this.d);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public ByteBuf readSlice(int i) {
        a(this.d);
        return new c(super.readSlice(i), this.d);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public ByteBuf readRetainedSlice(int i) {
        a(this.d);
        return new c(super.readRetainedSlice(i), this.d);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public ByteBuf discardReadBytes() {
        a(this.d);
        return super.discardReadBytes();
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public ByteBuf discardSomeReadBytes() {
        a(this.d);
        return super.discardSomeReadBytes();
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public ByteBuf ensureWritable(int i) {
        a(this.d);
        return super.ensureWritable(i);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public int ensureWritable(int i, boolean z) {
        a(this.d);
        return super.ensureWritable(i, z);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public boolean getBoolean(int i) {
        a(this.d);
        return super.getBoolean(i);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public byte getByte(int i) {
        a(this.d);
        return super.getByte(i);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public short getUnsignedByte(int i) {
        a(this.d);
        return super.getUnsignedByte(i);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public short getShort(int i) {
        a(this.d);
        return super.getShort(i);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public int getUnsignedShort(int i) {
        a(this.d);
        return super.getUnsignedShort(i);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public int getMedium(int i) {
        a(this.d);
        return super.getMedium(i);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public int getUnsignedMedium(int i) {
        a(this.d);
        return super.getUnsignedMedium(i);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public int getInt(int i) {
        a(this.d);
        return super.getInt(i);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public long getUnsignedInt(int i) {
        a(this.d);
        return super.getUnsignedInt(i);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public long getLong(int i) {
        a(this.d);
        return super.getLong(i);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public char getChar(int i) {
        a(this.d);
        return super.getChar(i);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public float getFloat(int i) {
        a(this.d);
        return super.getFloat(i);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public double getDouble(int i) {
        a(this.d);
        return super.getDouble(i);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public ByteBuf getBytes(int i, ByteBuf byteBuf) {
        a(this.d);
        return super.getBytes(i, byteBuf);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public ByteBuf getBytes(int i, ByteBuf byteBuf, int i2) {
        a(this.d);
        return super.getBytes(i, byteBuf, i2);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public ByteBuf getBytes(int i, ByteBuf byteBuf, int i2, int i3) {
        a(this.d);
        return super.getBytes(i, byteBuf, i2, i3);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public ByteBuf getBytes(int i, byte[] bArr) {
        a(this.d);
        return super.getBytes(i, bArr);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public ByteBuf getBytes(int i, byte[] bArr, int i2, int i3) {
        a(this.d);
        return super.getBytes(i, bArr, i2, i3);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public ByteBuf getBytes(int i, ByteBuffer byteBuffer) {
        a(this.d);
        return super.getBytes(i, byteBuffer);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public ByteBuf getBytes(int i, OutputStream outputStream, int i2) throws IOException {
        a(this.d);
        return super.getBytes(i, outputStream, i2);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public int getBytes(int i, GatheringByteChannel gatheringByteChannel, int i2) throws IOException {
        a(this.d);
        return super.getBytes(i, gatheringByteChannel, i2);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public CharSequence getCharSequence(int i, int i2, Charset charset) {
        a(this.d);
        return super.getCharSequence(i, i2, charset);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public ByteBuf setBoolean(int i, boolean z) {
        a(this.d);
        return super.setBoolean(i, z);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public ByteBuf setByte(int i, int i2) {
        a(this.d);
        return super.setByte(i, i2);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public ByteBuf setShort(int i, int i2) {
        a(this.d);
        return super.setShort(i, i2);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public ByteBuf setMedium(int i, int i2) {
        a(this.d);
        return super.setMedium(i, i2);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public ByteBuf setInt(int i, int i2) {
        a(this.d);
        return super.setInt(i, i2);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public ByteBuf setLong(int i, long j) {
        a(this.d);
        return super.setLong(i, j);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public ByteBuf setChar(int i, int i2) {
        a(this.d);
        return super.setChar(i, i2);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public ByteBuf setFloat(int i, float f) {
        a(this.d);
        return super.setFloat(i, f);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public ByteBuf setDouble(int i, double d) {
        a(this.d);
        return super.setDouble(i, d);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public ByteBuf setBytes(int i, ByteBuf byteBuf) {
        a(this.d);
        return super.setBytes(i, byteBuf);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public ByteBuf setBytes(int i, ByteBuf byteBuf, int i2) {
        a(this.d);
        return super.setBytes(i, byteBuf, i2);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public ByteBuf setBytes(int i, ByteBuf byteBuf, int i2, int i3) {
        a(this.d);
        return super.setBytes(i, byteBuf, i2, i3);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public ByteBuf setBytes(int i, byte[] bArr) {
        a(this.d);
        return super.setBytes(i, bArr);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public ByteBuf setBytes(int i, byte[] bArr, int i2, int i3) {
        a(this.d);
        return super.setBytes(i, bArr, i2, i3);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public ByteBuf setBytes(int i, ByteBuffer byteBuffer) {
        a(this.d);
        return super.setBytes(i, byteBuffer);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public int setBytes(int i, InputStream inputStream, int i2) throws IOException {
        a(this.d);
        return super.setBytes(i, inputStream, i2);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public int setBytes(int i, ScatteringByteChannel scatteringByteChannel, int i2) throws IOException {
        a(this.d);
        return super.setBytes(i, scatteringByteChannel, i2);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public ByteBuf setZero(int i, int i2) {
        a(this.d);
        return super.setZero(i, i2);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public int setCharSequence(int i, CharSequence charSequence, Charset charset) {
        a(this.d);
        return super.setCharSequence(i, charSequence, charset);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public boolean readBoolean() {
        a(this.d);
        return super.readBoolean();
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public byte readByte() {
        a(this.d);
        return super.readByte();
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public short readUnsignedByte() {
        a(this.d);
        return super.readUnsignedByte();
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public short readShort() {
        a(this.d);
        return super.readShort();
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public int readUnsignedShort() {
        a(this.d);
        return super.readUnsignedShort();
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public int readMedium() {
        a(this.d);
        return super.readMedium();
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public int readUnsignedMedium() {
        a(this.d);
        return super.readUnsignedMedium();
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public int readInt() {
        a(this.d);
        return super.readInt();
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public long readUnsignedInt() {
        a(this.d);
        return super.readUnsignedInt();
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public long readLong() {
        a(this.d);
        return super.readLong();
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public char readChar() {
        a(this.d);
        return super.readChar();
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public float readFloat() {
        a(this.d);
        return super.readFloat();
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public double readDouble() {
        a(this.d);
        return super.readDouble();
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public ByteBuf readBytes(int i) {
        a(this.d);
        return super.readBytes(i);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public ByteBuf readBytes(ByteBuf byteBuf) {
        a(this.d);
        return super.readBytes(byteBuf);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public ByteBuf readBytes(ByteBuf byteBuf, int i) {
        a(this.d);
        return super.readBytes(byteBuf, i);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public ByteBuf readBytes(ByteBuf byteBuf, int i, int i2) {
        a(this.d);
        return super.readBytes(byteBuf, i, i2);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public ByteBuf readBytes(byte[] bArr) {
        a(this.d);
        return super.readBytes(bArr);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public ByteBuf readBytes(byte[] bArr, int i, int i2) {
        a(this.d);
        return super.readBytes(bArr, i, i2);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public ByteBuf readBytes(ByteBuffer byteBuffer) {
        a(this.d);
        return super.readBytes(byteBuffer);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public ByteBuf readBytes(OutputStream outputStream, int i) throws IOException {
        a(this.d);
        return super.readBytes(outputStream, i);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public int readBytes(GatheringByteChannel gatheringByteChannel, int i) throws IOException {
        a(this.d);
        return super.readBytes(gatheringByteChannel, i);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public CharSequence readCharSequence(int i, Charset charset) {
        a(this.d);
        return super.readCharSequence(i, charset);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public ByteBuf skipBytes(int i) {
        a(this.d);
        return super.skipBytes(i);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public ByteBuf writeBoolean(boolean z) {
        a(this.d);
        return super.writeBoolean(z);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public ByteBuf writeByte(int i) {
        a(this.d);
        return super.writeByte(i);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public ByteBuf writeShort(int i) {
        a(this.d);
        return super.writeShort(i);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public ByteBuf writeMedium(int i) {
        a(this.d);
        return super.writeMedium(i);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public ByteBuf writeInt(int i) {
        a(this.d);
        return super.writeInt(i);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public ByteBuf writeLong(long j) {
        a(this.d);
        return super.writeLong(j);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public ByteBuf writeChar(int i) {
        a(this.d);
        return super.writeChar(i);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public ByteBuf writeFloat(float f) {
        a(this.d);
        return super.writeFloat(f);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public ByteBuf writeDouble(double d) {
        a(this.d);
        return super.writeDouble(d);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public ByteBuf writeBytes(ByteBuf byteBuf) {
        a(this.d);
        return super.writeBytes(byteBuf);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public ByteBuf writeBytes(ByteBuf byteBuf, int i) {
        a(this.d);
        return super.writeBytes(byteBuf, i);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public ByteBuf writeBytes(ByteBuf byteBuf, int i, int i2) {
        a(this.d);
        return super.writeBytes(byteBuf, i, i2);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public ByteBuf writeBytes(byte[] bArr) {
        a(this.d);
        return super.writeBytes(bArr);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public ByteBuf writeBytes(byte[] bArr, int i, int i2) {
        a(this.d);
        return super.writeBytes(bArr, i, i2);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public ByteBuf writeBytes(ByteBuffer byteBuffer) {
        a(this.d);
        return super.writeBytes(byteBuffer);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public int writeBytes(InputStream inputStream, int i) throws IOException {
        a(this.d);
        return super.writeBytes(inputStream, i);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public int writeBytes(ScatteringByteChannel scatteringByteChannel, int i) throws IOException {
        a(this.d);
        return super.writeBytes(scatteringByteChannel, i);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public ByteBuf writeZero(int i) {
        a(this.d);
        return super.writeZero(i);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public int indexOf(int i, int i2, byte b2) {
        a(this.d);
        return super.indexOf(i, i2, b2);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public int bytesBefore(byte b2) {
        a(this.d);
        return super.bytesBefore(b2);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public int bytesBefore(int i, byte b2) {
        a(this.d);
        return super.bytesBefore(i, b2);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public int bytesBefore(int i, int i2, byte b2) {
        a(this.d);
        return super.bytesBefore(i, i2, b2);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public int forEachByte(ByteProcessor byteProcessor) {
        a(this.d);
        return super.forEachByte(byteProcessor);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public int forEachByte(int i, int i2, ByteProcessor byteProcessor) {
        a(this.d);
        return super.forEachByte(i, i2, byteProcessor);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public int forEachByteDesc(ByteProcessor byteProcessor) {
        a(this.d);
        return super.forEachByteDesc(byteProcessor);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public int forEachByteDesc(int i, int i2, ByteProcessor byteProcessor) {
        a(this.d);
        return super.forEachByteDesc(i, i2, byteProcessor);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public ByteBuf copy() {
        a(this.d);
        return super.copy();
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public ByteBuf copy(int i, int i2) {
        a(this.d);
        return super.copy(i, i2);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public int nioBufferCount() {
        a(this.d);
        return super.nioBufferCount();
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public ByteBuffer nioBuffer() {
        a(this.d);
        return super.nioBuffer();
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public ByteBuffer nioBuffer(int i, int i2) {
        a(this.d);
        return super.nioBuffer(i, i2);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public ByteBuffer[] nioBuffers() {
        a(this.d);
        return super.nioBuffers();
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public ByteBuffer[] nioBuffers(int i, int i2) {
        a(this.d);
        return super.nioBuffers(i, i2);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public ByteBuffer internalNioBuffer(int i, int i2) {
        a(this.d);
        return super.internalNioBuffer(i, i2);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public String toString(Charset charset) {
        a(this.d);
        return super.toString(charset);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public String toString(int i, int i2, Charset charset) {
        a(this.d);
        return super.toString(i, i2, charset);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public ByteBuf capacity(int i) {
        a(this.d);
        return super.capacity(i);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public short getShortLE(int i) {
        a(this.d);
        return super.getShortLE(i);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public int getUnsignedShortLE(int i) {
        a(this.d);
        return super.getUnsignedShortLE(i);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public int getMediumLE(int i) {
        a(this.d);
        return super.getMediumLE(i);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public int getUnsignedMediumLE(int i) {
        a(this.d);
        return super.getUnsignedMediumLE(i);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public int getIntLE(int i) {
        a(this.d);
        return super.getIntLE(i);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public long getUnsignedIntLE(int i) {
        a(this.d);
        return super.getUnsignedIntLE(i);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public long getLongLE(int i) {
        a(this.d);
        return super.getLongLE(i);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public ByteBuf setShortLE(int i, int i2) {
        a(this.d);
        return super.setShortLE(i, i2);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public ByteBuf setIntLE(int i, int i2) {
        a(this.d);
        return super.setIntLE(i, i2);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public ByteBuf setMediumLE(int i, int i2) {
        a(this.d);
        return super.setMediumLE(i, i2);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public ByteBuf setLongLE(int i, long j) {
        a(this.d);
        return super.setLongLE(i, j);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public short readShortLE() {
        a(this.d);
        return super.readShortLE();
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public int readUnsignedShortLE() {
        a(this.d);
        return super.readUnsignedShortLE();
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public int readMediumLE() {
        a(this.d);
        return super.readMediumLE();
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public int readUnsignedMediumLE() {
        a(this.d);
        return super.readUnsignedMediumLE();
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public int readIntLE() {
        a(this.d);
        return super.readIntLE();
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public long readUnsignedIntLE() {
        a(this.d);
        return super.readUnsignedIntLE();
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public long readLongLE() {
        a(this.d);
        return super.readLongLE();
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public ByteBuf writeShortLE(int i) {
        a(this.d);
        return super.writeShortLE(i);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public ByteBuf writeMediumLE(int i) {
        a(this.d);
        return super.writeMediumLE(i);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public ByteBuf writeIntLE(int i) {
        a(this.d);
        return super.writeIntLE(i);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public ByteBuf writeLongLE(long j) {
        a(this.d);
        return super.writeLongLE(j);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public int writeCharSequence(CharSequence charSequence, Charset charset) {
        a(this.d);
        return super.writeCharSequence(charSequence, charset);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public int getBytes(int i, FileChannel fileChannel, long j, int i2) throws IOException {
        a(this.d);
        return super.getBytes(i, fileChannel, j, i2);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public int setBytes(int i, FileChannel fileChannel, long j, int i2) throws IOException {
        a(this.d);
        return super.setBytes(i, fileChannel, j, i2);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public int readBytes(FileChannel fileChannel, long j, int i) throws IOException {
        a(this.d);
        return super.readBytes(fileChannel, j, i);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public int writeBytes(FileChannel fileChannel, long j, int i) throws IOException {
        a(this.d);
        return super.writeBytes(fileChannel, j, i);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf
    public ByteBuf asReadOnly() {
        a(this.d);
        return new c(super.asReadOnly(), this.d);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf, io.netty.util.ReferenceCounted
    public ByteBuf retain() {
        this.d.record();
        return super.retain();
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf, io.netty.util.ReferenceCounted
    public ByteBuf retain(int i) {
        this.d.record();
        return super.retain(i);
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf, io.netty.util.ReferenceCounted
    public ByteBuf touch() {
        this.d.record();
        return this;
    }

    @Override // io.netty.buffer.ae, io.netty.buffer.ByteBuf, io.netty.util.ReferenceCounted
    public ByteBuf touch(Object obj) {
        this.d.record(obj);
        return this;
    }

    @Override // io.netty.buffer.ae, io.netty.util.ReferenceCounted
    public boolean release() {
        boolean release = super.release();
        if (release) {
            this.d.close();
        } else {
            this.d.record();
        }
        return release;
    }

    @Override // io.netty.buffer.ae, io.netty.util.ReferenceCounted
    public boolean release(int i) {
        boolean release = super.release(i);
        if (release) {
            this.d.close();
        } else {
            this.d.record();
        }
        return release;
    }
}
