package io.netty.buffer;

import io.netty.util.ByteProcessor;
import io.netty.util.Recycler;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;

/* compiled from: PooledDuplicatedByteBuf.java */
/* loaded from: classes4.dex */
public final class o extends a<o> {
    private static final Recycler<o> d = new Recycler<o>() { // from class: io.netty.buffer.o.1
        /* renamed from: a */
        public o newObject(Recycler.Handle<o> handle) {
            return new o(handle);
        }
    };

    public static o a(AbstractByteBuf abstractByteBuf, int i, int i2) {
        o oVar = d.get();
        oVar.a(abstractByteBuf, i, i2, abstractByteBuf.maxCapacity());
        oVar.markReaderIndex();
        oVar.markWriterIndex();
        return oVar;
    }

    private o(Recycler.Handle<o> handle) {
        super(handle);
    }

    @Override // io.netty.buffer.ByteBuf
    public int capacity() {
        return unwrap().capacity();
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf capacity(int i) {
        unwrap().capacity(i);
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public int arrayOffset() {
        return unwrap().arrayOffset();
    }

    @Override // io.netty.buffer.ByteBuf
    public long memoryAddress() {
        return unwrap().memoryAddress();
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuffer nioBuffer(int i, int i2) {
        return unwrap().nioBuffer(i, i2);
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuffer[] nioBuffers(int i, int i2) {
        return unwrap().nioBuffers(i, i2);
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf copy(int i, int i2) {
        return unwrap().copy(i, i2);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public byte getByte(int i) {
        return unwrap().getByte(i);
    }

    @Override // io.netty.buffer.AbstractByteBuf
    public byte _getByte(int i) {
        return unwrap()._getByte(i);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public short getShort(int i) {
        return unwrap().getShort(i);
    }

    @Override // io.netty.buffer.AbstractByteBuf
    public short _getShort(int i) {
        return unwrap()._getShort(i);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public short getShortLE(int i) {
        return unwrap().getShortLE(i);
    }

    @Override // io.netty.buffer.AbstractByteBuf
    public short _getShortLE(int i) {
        return unwrap()._getShortLE(i);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public int getUnsignedMedium(int i) {
        return unwrap().getUnsignedMedium(i);
    }

    @Override // io.netty.buffer.AbstractByteBuf
    public int _getUnsignedMedium(int i) {
        return unwrap()._getUnsignedMedium(i);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public int getUnsignedMediumLE(int i) {
        return unwrap().getUnsignedMediumLE(i);
    }

    @Override // io.netty.buffer.AbstractByteBuf
    public int _getUnsignedMediumLE(int i) {
        return unwrap()._getUnsignedMediumLE(i);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public int getInt(int i) {
        return unwrap().getInt(i);
    }

    @Override // io.netty.buffer.AbstractByteBuf
    public int _getInt(int i) {
        return unwrap()._getInt(i);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public int getIntLE(int i) {
        return unwrap().getIntLE(i);
    }

    @Override // io.netty.buffer.AbstractByteBuf
    public int _getIntLE(int i) {
        return unwrap()._getIntLE(i);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public long getLong(int i) {
        return unwrap().getLong(i);
    }

    @Override // io.netty.buffer.AbstractByteBuf
    public long _getLong(int i) {
        return unwrap()._getLong(i);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public long getLongLE(int i) {
        return unwrap().getLongLE(i);
    }

    @Override // io.netty.buffer.AbstractByteBuf
    public long _getLongLE(int i) {
        return unwrap()._getLongLE(i);
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf getBytes(int i, ByteBuf byteBuf, int i2, int i3) {
        unwrap().getBytes(i, byteBuf, i2, i3);
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf getBytes(int i, byte[] bArr, int i2, int i3) {
        unwrap().getBytes(i, bArr, i2, i3);
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf getBytes(int i, ByteBuffer byteBuffer) {
        unwrap().getBytes(i, byteBuffer);
        return this;
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public ByteBuf setByte(int i, int i2) {
        unwrap().setByte(i, i2);
        return this;
    }

    @Override // io.netty.buffer.AbstractByteBuf
    public void _setByte(int i, int i2) {
        unwrap()._setByte(i, i2);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public ByteBuf setShort(int i, int i2) {
        unwrap().setShort(i, i2);
        return this;
    }

    @Override // io.netty.buffer.AbstractByteBuf
    public void _setShort(int i, int i2) {
        unwrap()._setShort(i, i2);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public ByteBuf setShortLE(int i, int i2) {
        unwrap().setShortLE(i, i2);
        return this;
    }

    @Override // io.netty.buffer.AbstractByteBuf
    public void _setShortLE(int i, int i2) {
        unwrap()._setShortLE(i, i2);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public ByteBuf setMedium(int i, int i2) {
        unwrap().setMedium(i, i2);
        return this;
    }

    @Override // io.netty.buffer.AbstractByteBuf
    public void _setMedium(int i, int i2) {
        unwrap()._setMedium(i, i2);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public ByteBuf setMediumLE(int i, int i2) {
        unwrap().setMediumLE(i, i2);
        return this;
    }

    @Override // io.netty.buffer.AbstractByteBuf
    public void _setMediumLE(int i, int i2) {
        unwrap()._setMediumLE(i, i2);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public ByteBuf setInt(int i, int i2) {
        unwrap().setInt(i, i2);
        return this;
    }

    @Override // io.netty.buffer.AbstractByteBuf
    public void _setInt(int i, int i2) {
        unwrap()._setInt(i, i2);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public ByteBuf setIntLE(int i, int i2) {
        unwrap().setIntLE(i, i2);
        return this;
    }

    @Override // io.netty.buffer.AbstractByteBuf
    public void _setIntLE(int i, int i2) {
        unwrap()._setIntLE(i, i2);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public ByteBuf setLong(int i, long j) {
        unwrap().setLong(i, j);
        return this;
    }

    @Override // io.netty.buffer.AbstractByteBuf
    public void _setLong(int i, long j) {
        unwrap()._setLong(i, j);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public ByteBuf setLongLE(int i, long j) {
        unwrap().setLongLE(i, j);
        return this;
    }

    @Override // io.netty.buffer.AbstractByteBuf
    public void _setLongLE(int i, long j) {
        unwrap().setLongLE(i, j);
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf setBytes(int i, byte[] bArr, int i2, int i3) {
        unwrap().setBytes(i, bArr, i2, i3);
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf setBytes(int i, ByteBuf byteBuf, int i2, int i3) {
        unwrap().setBytes(i, byteBuf, i2, i3);
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf setBytes(int i, ByteBuffer byteBuffer) {
        unwrap().setBytes(i, byteBuffer);
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf getBytes(int i, OutputStream outputStream, int i2) throws IOException {
        unwrap().getBytes(i, outputStream, i2);
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public int getBytes(int i, GatheringByteChannel gatheringByteChannel, int i2) throws IOException {
        return unwrap().getBytes(i, gatheringByteChannel, i2);
    }

    @Override // io.netty.buffer.ByteBuf
    public int getBytes(int i, FileChannel fileChannel, long j, int i2) throws IOException {
        return unwrap().getBytes(i, fileChannel, j, i2);
    }

    @Override // io.netty.buffer.ByteBuf
    public int setBytes(int i, InputStream inputStream, int i2) throws IOException {
        return unwrap().setBytes(i, inputStream, i2);
    }

    @Override // io.netty.buffer.ByteBuf
    public int setBytes(int i, ScatteringByteChannel scatteringByteChannel, int i2) throws IOException {
        return unwrap().setBytes(i, scatteringByteChannel, i2);
    }

    @Override // io.netty.buffer.ByteBuf
    public int setBytes(int i, FileChannel fileChannel, long j, int i2) throws IOException {
        return unwrap().setBytes(i, fileChannel, j, i2);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public int forEachByte(int i, int i2, ByteProcessor byteProcessor) {
        return unwrap().forEachByte(i, i2, byteProcessor);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public int forEachByteDesc(int i, int i2, ByteProcessor byteProcessor) {
        return unwrap().forEachByteDesc(i, i2, byteProcessor);
    }
}
