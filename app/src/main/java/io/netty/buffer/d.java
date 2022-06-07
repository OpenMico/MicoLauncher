package io.netty.buffer;

import io.netty.util.ByteProcessor;
import io.netty.util.ResourceLeak;
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
/* compiled from: AdvancedLeakAwareCompositeByteBuf.java */
/* loaded from: classes4.dex */
public final class d extends af {
    private final ResourceLeak e;

    /* JADX INFO: Access modifiers changed from: package-private */
    public d(CompositeByteBuf compositeByteBuf, ResourceLeak resourceLeak) {
        super(compositeByteBuf);
        this.e = resourceLeak;
    }

    @Override // io.netty.buffer.af, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public ByteBuf order(ByteOrder byteOrder) {
        c.a(this.e);
        return order() == byteOrder ? this : new c(super.order(byteOrder), this.e);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public ByteBuf slice() {
        c.a(this.e);
        return new c(super.slice(), this.e);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public ByteBuf retainedSlice() {
        c.a(this.e);
        return new c(super.retainedSlice(), this.e);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public ByteBuf slice(int i, int i2) {
        c.a(this.e);
        return new c(super.slice(i, i2), this.e);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public ByteBuf retainedSlice(int i, int i2) {
        c.a(this.e);
        return new c(super.retainedSlice(i, i2), this.e);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public ByteBuf duplicate() {
        c.a(this.e);
        return new c(super.duplicate(), this.e);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public ByteBuf retainedDuplicate() {
        c.a(this.e);
        return new c(super.retainedDuplicate(), this.e);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public ByteBuf readSlice(int i) {
        c.a(this.e);
        return new c(super.readSlice(i), this.e);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public ByteBuf readRetainedSlice(int i) {
        c.a(this.e);
        return new c(super.readRetainedSlice(i), this.e);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public ByteBuf asReadOnly() {
        c.a(this.e);
        return new c(super.asReadOnly(), this.e);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public boolean isReadOnly() {
        c.a(this.e);
        return super.isReadOnly();
    }

    @Override // io.netty.buffer.af, io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf discardReadBytes() {
        c.a(this.e);
        return super.discardReadBytes();
    }

    @Override // io.netty.buffer.af, io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf discardSomeReadBytes() {
        c.a(this.e);
        return super.discardSomeReadBytes();
    }

    @Override // io.netty.buffer.af, io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf ensureWritable(int i) {
        c.a(this.e);
        return super.ensureWritable(i);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public int ensureWritable(int i, boolean z) {
        c.a(this.e);
        return super.ensureWritable(i, z);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public boolean getBoolean(int i) {
        c.a(this.e);
        return super.getBoolean(i);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public byte getByte(int i) {
        c.a(this.e);
        return super.getByte(i);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public short getUnsignedByte(int i) {
        c.a(this.e);
        return super.getUnsignedByte(i);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public short getShort(int i) {
        c.a(this.e);
        return super.getShort(i);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public int getUnsignedShort(int i) {
        c.a(this.e);
        return super.getUnsignedShort(i);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public int getMedium(int i) {
        c.a(this.e);
        return super.getMedium(i);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public int getUnsignedMedium(int i) {
        c.a(this.e);
        return super.getUnsignedMedium(i);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public int getInt(int i) {
        c.a(this.e);
        return super.getInt(i);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public long getUnsignedInt(int i) {
        c.a(this.e);
        return super.getUnsignedInt(i);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public long getLong(int i) {
        c.a(this.e);
        return super.getLong(i);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public char getChar(int i) {
        c.a(this.e);
        return super.getChar(i);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public float getFloat(int i) {
        c.a(this.e);
        return super.getFloat(i);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public double getDouble(int i) {
        c.a(this.e);
        return super.getDouble(i);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf getBytes(int i, ByteBuf byteBuf) {
        c.a(this.e);
        return super.getBytes(i, byteBuf);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf getBytes(int i, ByteBuf byteBuf, int i2) {
        c.a(this.e);
        return super.getBytes(i, byteBuf, i2);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.CompositeByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf getBytes(int i, ByteBuf byteBuf, int i2, int i3) {
        c.a(this.e);
        return super.getBytes(i, byteBuf, i2, i3);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf getBytes(int i, byte[] bArr) {
        c.a(this.e);
        return super.getBytes(i, bArr);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.CompositeByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf getBytes(int i, byte[] bArr, int i2, int i3) {
        c.a(this.e);
        return super.getBytes(i, bArr, i2, i3);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.CompositeByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf getBytes(int i, ByteBuffer byteBuffer) {
        c.a(this.e);
        return super.getBytes(i, byteBuffer);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.CompositeByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf getBytes(int i, OutputStream outputStream, int i2) throws IOException {
        c.a(this.e);
        return super.getBytes(i, outputStream, i2);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.CompositeByteBuf, io.netty.buffer.ByteBuf
    public int getBytes(int i, GatheringByteChannel gatheringByteChannel, int i2) throws IOException {
        c.a(this.e);
        return super.getBytes(i, gatheringByteChannel, i2);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CharSequence getCharSequence(int i, int i2, Charset charset) {
        c.a(this.e);
        return super.getCharSequence(i, i2, charset);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf setBoolean(int i, boolean z) {
        c.a(this.e);
        return super.setBoolean(i, z);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf setByte(int i, int i2) {
        c.a(this.e);
        return super.setByte(i, i2);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf setShort(int i, int i2) {
        c.a(this.e);
        return super.setShort(i, i2);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf setMedium(int i, int i2) {
        c.a(this.e);
        return super.setMedium(i, i2);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf setInt(int i, int i2) {
        c.a(this.e);
        return super.setInt(i, i2);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf setLong(int i, long j) {
        c.a(this.e);
        return super.setLong(i, j);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf setChar(int i, int i2) {
        c.a(this.e);
        return super.setChar(i, i2);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf setFloat(int i, float f) {
        c.a(this.e);
        return super.setFloat(i, f);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf setDouble(int i, double d) {
        c.a(this.e);
        return super.setDouble(i, d);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf setBytes(int i, ByteBuf byteBuf) {
        c.a(this.e);
        return super.setBytes(i, byteBuf);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf setBytes(int i, ByteBuf byteBuf, int i2) {
        c.a(this.e);
        return super.setBytes(i, byteBuf, i2);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.CompositeByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf setBytes(int i, ByteBuf byteBuf, int i2, int i3) {
        c.a(this.e);
        return super.setBytes(i, byteBuf, i2, i3);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf setBytes(int i, byte[] bArr) {
        c.a(this.e);
        return super.setBytes(i, bArr);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.CompositeByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf setBytes(int i, byte[] bArr, int i2, int i3) {
        c.a(this.e);
        return super.setBytes(i, bArr, i2, i3);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.CompositeByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf setBytes(int i, ByteBuffer byteBuffer) {
        c.a(this.e);
        return super.setBytes(i, byteBuffer);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.CompositeByteBuf, io.netty.buffer.ByteBuf
    public int setBytes(int i, InputStream inputStream, int i2) throws IOException {
        c.a(this.e);
        return super.setBytes(i, inputStream, i2);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.CompositeByteBuf, io.netty.buffer.ByteBuf
    public int setBytes(int i, ScatteringByteChannel scatteringByteChannel, int i2) throws IOException {
        c.a(this.e);
        return super.setBytes(i, scatteringByteChannel, i2);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf setZero(int i, int i2) {
        c.a(this.e);
        return super.setZero(i, i2);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public boolean readBoolean() {
        c.a(this.e);
        return super.readBoolean();
    }

    @Override // io.netty.buffer.af, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public byte readByte() {
        c.a(this.e);
        return super.readByte();
    }

    @Override // io.netty.buffer.af, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public short readUnsignedByte() {
        c.a(this.e);
        return super.readUnsignedByte();
    }

    @Override // io.netty.buffer.af, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public short readShort() {
        c.a(this.e);
        return super.readShort();
    }

    @Override // io.netty.buffer.af, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public int readUnsignedShort() {
        c.a(this.e);
        return super.readUnsignedShort();
    }

    @Override // io.netty.buffer.af, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public int readMedium() {
        c.a(this.e);
        return super.readMedium();
    }

    @Override // io.netty.buffer.af, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public int readUnsignedMedium() {
        c.a(this.e);
        return super.readUnsignedMedium();
    }

    @Override // io.netty.buffer.af, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public int readInt() {
        c.a(this.e);
        return super.readInt();
    }

    @Override // io.netty.buffer.af, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public long readUnsignedInt() {
        c.a(this.e);
        return super.readUnsignedInt();
    }

    @Override // io.netty.buffer.af, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public long readLong() {
        c.a(this.e);
        return super.readLong();
    }

    @Override // io.netty.buffer.af, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public char readChar() {
        c.a(this.e);
        return super.readChar();
    }

    @Override // io.netty.buffer.af, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public float readFloat() {
        c.a(this.e);
        return super.readFloat();
    }

    @Override // io.netty.buffer.af, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public double readDouble() {
        c.a(this.e);
        return super.readDouble();
    }

    @Override // io.netty.buffer.af, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public ByteBuf readBytes(int i) {
        c.a(this.e);
        return super.readBytes(i);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf readBytes(ByteBuf byteBuf) {
        c.a(this.e);
        return super.readBytes(byteBuf);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf readBytes(ByteBuf byteBuf, int i) {
        c.a(this.e);
        return super.readBytes(byteBuf, i);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf readBytes(ByteBuf byteBuf, int i, int i2) {
        c.a(this.e);
        return super.readBytes(byteBuf, i, i2);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf readBytes(byte[] bArr) {
        c.a(this.e);
        return super.readBytes(bArr);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf readBytes(byte[] bArr, int i, int i2) {
        c.a(this.e);
        return super.readBytes(bArr, i, i2);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf readBytes(ByteBuffer byteBuffer) {
        c.a(this.e);
        return super.readBytes(byteBuffer);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf readBytes(OutputStream outputStream, int i) throws IOException {
        c.a(this.e);
        return super.readBytes(outputStream, i);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public int readBytes(GatheringByteChannel gatheringByteChannel, int i) throws IOException {
        c.a(this.e);
        return super.readBytes(gatheringByteChannel, i);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CharSequence readCharSequence(int i, Charset charset) {
        c.a(this.e);
        return super.readCharSequence(i, charset);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf skipBytes(int i) {
        c.a(this.e);
        return super.skipBytes(i);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf writeBoolean(boolean z) {
        c.a(this.e);
        return super.writeBoolean(z);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf writeByte(int i) {
        c.a(this.e);
        return super.writeByte(i);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf writeShort(int i) {
        c.a(this.e);
        return super.writeShort(i);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf writeMedium(int i) {
        c.a(this.e);
        return super.writeMedium(i);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf writeInt(int i) {
        c.a(this.e);
        return super.writeInt(i);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf writeLong(long j) {
        c.a(this.e);
        return super.writeLong(j);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf writeChar(int i) {
        c.a(this.e);
        return super.writeChar(i);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf writeFloat(float f) {
        c.a(this.e);
        return super.writeFloat(f);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf writeDouble(double d) {
        c.a(this.e);
        return super.writeDouble(d);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf writeBytes(ByteBuf byteBuf) {
        c.a(this.e);
        return super.writeBytes(byteBuf);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf writeBytes(ByteBuf byteBuf, int i) {
        c.a(this.e);
        return super.writeBytes(byteBuf, i);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf writeBytes(ByteBuf byteBuf, int i, int i2) {
        c.a(this.e);
        return super.writeBytes(byteBuf, i, i2);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf writeBytes(byte[] bArr) {
        c.a(this.e);
        return super.writeBytes(bArr);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf writeBytes(byte[] bArr, int i, int i2) {
        c.a(this.e);
        return super.writeBytes(bArr, i, i2);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf writeBytes(ByteBuffer byteBuffer) {
        c.a(this.e);
        return super.writeBytes(byteBuffer);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public int writeBytes(InputStream inputStream, int i) throws IOException {
        c.a(this.e);
        return super.writeBytes(inputStream, i);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public int writeBytes(ScatteringByteChannel scatteringByteChannel, int i) throws IOException {
        c.a(this.e);
        return super.writeBytes(scatteringByteChannel, i);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf writeZero(int i) {
        c.a(this.e);
        return super.writeZero(i);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public int writeCharSequence(CharSequence charSequence, Charset charset) {
        c.a(this.e);
        return super.writeCharSequence(charSequence, charset);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public int indexOf(int i, int i2, byte b) {
        c.a(this.e);
        return super.indexOf(i, i2, b);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public int bytesBefore(byte b) {
        c.a(this.e);
        return super.bytesBefore(b);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public int bytesBefore(int i, byte b) {
        c.a(this.e);
        return super.bytesBefore(i, b);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public int bytesBefore(int i, int i2, byte b) {
        c.a(this.e);
        return super.bytesBefore(i, i2, b);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public int forEachByte(ByteProcessor byteProcessor) {
        c.a(this.e);
        return super.forEachByte(byteProcessor);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public int forEachByte(int i, int i2, ByteProcessor byteProcessor) {
        c.a(this.e);
        return super.forEachByte(i, i2, byteProcessor);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public int forEachByteDesc(ByteProcessor byteProcessor) {
        c.a(this.e);
        return super.forEachByteDesc(byteProcessor);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public int forEachByteDesc(int i, int i2, ByteProcessor byteProcessor) {
        c.a(this.e);
        return super.forEachByteDesc(i, i2, byteProcessor);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public ByteBuf copy() {
        c.a(this.e);
        return super.copy();
    }

    @Override // io.netty.buffer.af, io.netty.buffer.CompositeByteBuf, io.netty.buffer.ByteBuf
    public ByteBuf copy(int i, int i2) {
        c.a(this.e);
        return super.copy(i, i2);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.CompositeByteBuf, io.netty.buffer.ByteBuf
    public int nioBufferCount() {
        c.a(this.e);
        return super.nioBufferCount();
    }

    @Override // io.netty.buffer.af, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public ByteBuffer nioBuffer() {
        c.a(this.e);
        return super.nioBuffer();
    }

    @Override // io.netty.buffer.af, io.netty.buffer.CompositeByteBuf, io.netty.buffer.ByteBuf
    public ByteBuffer nioBuffer(int i, int i2) {
        c.a(this.e);
        return super.nioBuffer(i, i2);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public ByteBuffer[] nioBuffers() {
        c.a(this.e);
        return super.nioBuffers();
    }

    @Override // io.netty.buffer.af, io.netty.buffer.CompositeByteBuf, io.netty.buffer.ByteBuf
    public ByteBuffer[] nioBuffers(int i, int i2) {
        c.a(this.e);
        return super.nioBuffers(i, i2);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.CompositeByteBuf, io.netty.buffer.ByteBuf
    public ByteBuffer internalNioBuffer(int i, int i2) {
        c.a(this.e);
        return super.internalNioBuffer(i, i2);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public String toString(Charset charset) {
        c.a(this.e);
        return super.toString(charset);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public String toString(int i, int i2, Charset charset) {
        c.a(this.e);
        return super.toString(i, i2, charset);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.CompositeByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf capacity(int i) {
        c.a(this.e);
        return super.capacity(i);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public short getShortLE(int i) {
        c.a(this.e);
        return super.getShortLE(i);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public int getUnsignedShortLE(int i) {
        c.a(this.e);
        return super.getUnsignedShortLE(i);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public int getUnsignedMediumLE(int i) {
        c.a(this.e);
        return super.getUnsignedMediumLE(i);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public int getMediumLE(int i) {
        c.a(this.e);
        return super.getMediumLE(i);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public int getIntLE(int i) {
        c.a(this.e);
        return super.getIntLE(i);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public long getUnsignedIntLE(int i) {
        c.a(this.e);
        return super.getUnsignedIntLE(i);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public long getLongLE(int i) {
        c.a(this.e);
        return super.getLongLE(i);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public ByteBuf setShortLE(int i, int i2) {
        c.a(this.e);
        return super.setShortLE(i, i2);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public ByteBuf setMediumLE(int i, int i2) {
        c.a(this.e);
        return super.setMediumLE(i, i2);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public ByteBuf setIntLE(int i, int i2) {
        c.a(this.e);
        return super.setIntLE(i, i2);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public ByteBuf setLongLE(int i, long j) {
        c.a(this.e);
        return super.setLongLE(i, j);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public int setCharSequence(int i, CharSequence charSequence, Charset charset) {
        c.a(this.e);
        return super.setCharSequence(i, charSequence, charset);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public short readShortLE() {
        c.a(this.e);
        return super.readShortLE();
    }

    @Override // io.netty.buffer.af, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public int readUnsignedShortLE() {
        c.a(this.e);
        return super.readUnsignedShortLE();
    }

    @Override // io.netty.buffer.af, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public int readMediumLE() {
        c.a(this.e);
        return super.readMediumLE();
    }

    @Override // io.netty.buffer.af, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public int readUnsignedMediumLE() {
        c.a(this.e);
        return super.readUnsignedMediumLE();
    }

    @Override // io.netty.buffer.af, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public int readIntLE() {
        c.a(this.e);
        return super.readIntLE();
    }

    @Override // io.netty.buffer.af, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public long readUnsignedIntLE() {
        c.a(this.e);
        return super.readUnsignedIntLE();
    }

    @Override // io.netty.buffer.af, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public long readLongLE() {
        c.a(this.e);
        return super.readLongLE();
    }

    @Override // io.netty.buffer.af, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public ByteBuf writeShortLE(int i) {
        c.a(this.e);
        return super.writeShortLE(i);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public ByteBuf writeMediumLE(int i) {
        c.a(this.e);
        return super.writeMediumLE(i);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public ByteBuf writeIntLE(int i) {
        c.a(this.e);
        return super.writeIntLE(i);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public ByteBuf writeLongLE(long j) {
        c.a(this.e);
        return super.writeLongLE(j);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.CompositeByteBuf
    public CompositeByteBuf addComponent(ByteBuf byteBuf) {
        c.a(this.e);
        return super.addComponent(byteBuf);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.CompositeByteBuf
    public CompositeByteBuf addComponents(ByteBuf... byteBufArr) {
        c.a(this.e);
        return super.addComponents(byteBufArr);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.CompositeByteBuf
    public CompositeByteBuf addComponents(Iterable<ByteBuf> iterable) {
        c.a(this.e);
        return super.addComponents(iterable);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.CompositeByteBuf
    public CompositeByteBuf addComponent(int i, ByteBuf byteBuf) {
        c.a(this.e);
        return super.addComponent(i, byteBuf);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.CompositeByteBuf
    public CompositeByteBuf addComponents(int i, ByteBuf... byteBufArr) {
        c.a(this.e);
        return super.addComponents(i, byteBufArr);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.CompositeByteBuf
    public CompositeByteBuf addComponents(int i, Iterable<ByteBuf> iterable) {
        c.a(this.e);
        return super.addComponents(i, iterable);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.CompositeByteBuf
    public CompositeByteBuf addComponent(boolean z, ByteBuf byteBuf) {
        c.a(this.e);
        return super.addComponent(z, byteBuf);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.CompositeByteBuf
    public CompositeByteBuf addComponents(boolean z, ByteBuf... byteBufArr) {
        c.a(this.e);
        return super.addComponents(z, byteBufArr);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.CompositeByteBuf
    public CompositeByteBuf addComponents(boolean z, Iterable<ByteBuf> iterable) {
        c.a(this.e);
        return super.addComponents(z, iterable);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.CompositeByteBuf
    public CompositeByteBuf addComponent(boolean z, int i, ByteBuf byteBuf) {
        c.a(this.e);
        return super.addComponent(z, i, byteBuf);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.CompositeByteBuf
    public CompositeByteBuf removeComponent(int i) {
        c.a(this.e);
        return super.removeComponent(i);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.CompositeByteBuf
    public CompositeByteBuf removeComponents(int i, int i2) {
        c.a(this.e);
        return super.removeComponents(i, i2);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.CompositeByteBuf, java.lang.Iterable
    public Iterator<ByteBuf> iterator() {
        c.a(this.e);
        return super.iterator();
    }

    @Override // io.netty.buffer.af, io.netty.buffer.CompositeByteBuf
    public List<ByteBuf> decompose(int i, int i2) {
        c.a(this.e);
        return super.decompose(i, i2);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.CompositeByteBuf
    public CompositeByteBuf consolidate() {
        c.a(this.e);
        return super.consolidate();
    }

    @Override // io.netty.buffer.af, io.netty.buffer.CompositeByteBuf
    public CompositeByteBuf discardReadComponents() {
        c.a(this.e);
        return super.discardReadComponents();
    }

    @Override // io.netty.buffer.af, io.netty.buffer.CompositeByteBuf
    public CompositeByteBuf consolidate(int i, int i2) {
        c.a(this.e);
        return super.consolidate(i, i2);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.CompositeByteBuf, io.netty.buffer.ByteBuf
    public int getBytes(int i, FileChannel fileChannel, long j, int i2) throws IOException {
        c.a(this.e);
        return super.getBytes(i, fileChannel, j, i2);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.CompositeByteBuf, io.netty.buffer.ByteBuf
    public int setBytes(int i, FileChannel fileChannel, long j, int i2) throws IOException {
        c.a(this.e);
        return super.setBytes(i, fileChannel, j, i2);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public int readBytes(FileChannel fileChannel, long j, int i) throws IOException {
        c.a(this.e);
        return super.readBytes(fileChannel, j, i);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public int writeBytes(FileChannel fileChannel, long j, int i) throws IOException {
        c.a(this.e);
        return super.writeBytes(fileChannel, j, i);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractReferenceCountedByteBuf, io.netty.buffer.ByteBuf, io.netty.util.ReferenceCounted
    public CompositeByteBuf retain() {
        this.e.record();
        return super.retain();
    }

    @Override // io.netty.buffer.af, io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractReferenceCountedByteBuf, io.netty.buffer.ByteBuf, io.netty.util.ReferenceCounted
    public CompositeByteBuf retain(int i) {
        this.e.record();
        return super.retain(i);
    }

    @Override // io.netty.buffer.af, io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractReferenceCountedByteBuf, io.netty.buffer.ByteBuf, io.netty.util.ReferenceCounted
    public CompositeByteBuf touch() {
        this.e.record();
        return this;
    }

    @Override // io.netty.buffer.af, io.netty.buffer.CompositeByteBuf, io.netty.buffer.AbstractReferenceCountedByteBuf, io.netty.buffer.ByteBuf, io.netty.util.ReferenceCounted
    public CompositeByteBuf touch(Object obj) {
        this.e.record(obj);
        return this;
    }

    @Override // io.netty.buffer.af, io.netty.buffer.AbstractReferenceCountedByteBuf, io.netty.util.ReferenceCounted
    public boolean release() {
        boolean release = super.release();
        if (release) {
            this.e.close();
        } else {
            this.e.record();
        }
        return release;
    }

    @Override // io.netty.buffer.af, io.netty.buffer.AbstractReferenceCountedByteBuf, io.netty.util.ReferenceCounted
    public boolean release(int i) {
        boolean release = super.release(i);
        if (release) {
            this.e.close();
        } else {
            this.e.record();
        }
        return release;
    }
}
