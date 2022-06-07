package io.netty.buffer;

import io.netty.util.ByteProcessor;
import io.netty.util.Recycler;
import io.netty.util.internal.MathUtil;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: PooledSlicedByteBuf.java */
/* loaded from: classes4.dex */
public final class q extends a<q> {
    private static final Recycler<q> d = new Recycler<q>() { // from class: io.netty.buffer.q.1
        /* JADX INFO: Access modifiers changed from: protected */
        /* renamed from: a */
        public q newObject(Recycler.Handle<q> handle) {
            return new q(handle);
        }
    };
    private int e;

    /* JADX INFO: Access modifiers changed from: package-private */
    public static q b(AbstractByteBuf abstractByteBuf, int i, int i2, int i3) {
        if (!MathUtil.isOutOfBounds(i, i2, abstractByteBuf.capacity())) {
            q qVar = d.get();
            qVar.a(abstractByteBuf, 0, i2, i2);
            qVar.a();
            qVar.e = i3;
            return qVar;
        }
        throw new IndexOutOfBoundsException(abstractByteBuf + ".slice(" + i + ", " + i2 + ')');
    }

    private q(Recycler.Handle<q> handle) {
        super(handle);
    }

    @Override // io.netty.buffer.ByteBuf
    public int capacity() {
        return maxCapacity();
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf capacity(int i) {
        return c();
    }

    @Override // io.netty.buffer.ByteBuf
    public int arrayOffset() {
        return a(unwrap().arrayOffset());
    }

    @Override // io.netty.buffer.ByteBuf
    public long memoryAddress() {
        return unwrap().memoryAddress() + this.e;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuffer nioBuffer(int i, int i2) {
        a(i, i2);
        return unwrap().nioBuffer(a(i), i2);
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuffer[] nioBuffers(int i, int i2) {
        a(i, i2);
        return unwrap().nioBuffers(a(i), i2);
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf copy(int i, int i2) {
        a(i, i2);
        return unwrap().copy(a(i), i2);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public byte getByte(int i) {
        a(i, 1);
        return unwrap().getByte(a(i));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.AbstractByteBuf
    public byte _getByte(int i) {
        return unwrap()._getByte(a(i));
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public short getShort(int i) {
        a(i, 2);
        return unwrap().getShort(a(i));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.AbstractByteBuf
    public short _getShort(int i) {
        return unwrap()._getShort(a(i));
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public short getShortLE(int i) {
        a(i, 2);
        return unwrap().getShortLE(a(i));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.AbstractByteBuf
    public short _getShortLE(int i) {
        return unwrap()._getShortLE(a(i));
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public int getUnsignedMedium(int i) {
        a(i, 3);
        return unwrap().getUnsignedMedium(a(i));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.AbstractByteBuf
    public int _getUnsignedMedium(int i) {
        return unwrap()._getUnsignedMedium(a(i));
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public int getUnsignedMediumLE(int i) {
        a(i, 3);
        return unwrap().getUnsignedMediumLE(a(i));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.AbstractByteBuf
    public int _getUnsignedMediumLE(int i) {
        return unwrap()._getUnsignedMediumLE(a(i));
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public int getInt(int i) {
        a(i, 4);
        return unwrap().getInt(a(i));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.AbstractByteBuf
    public int _getInt(int i) {
        return unwrap()._getInt(a(i));
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public int getIntLE(int i) {
        a(i, 4);
        return unwrap().getIntLE(a(i));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.AbstractByteBuf
    public int _getIntLE(int i) {
        return unwrap()._getIntLE(a(i));
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public long getLong(int i) {
        a(i, 8);
        return unwrap().getLong(a(i));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.AbstractByteBuf
    public long _getLong(int i) {
        return unwrap()._getLong(a(i));
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public long getLongLE(int i) {
        a(i, 8);
        return unwrap().getLongLE(a(i));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.AbstractByteBuf
    public long _getLongLE(int i) {
        return unwrap()._getLongLE(a(i));
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf getBytes(int i, ByteBuf byteBuf, int i2, int i3) {
        a(i, i3);
        unwrap().getBytes(a(i), byteBuf, i2, i3);
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf getBytes(int i, byte[] bArr, int i2, int i3) {
        a(i, i3);
        unwrap().getBytes(a(i), bArr, i2, i3);
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf getBytes(int i, ByteBuffer byteBuffer) {
        a(i, byteBuffer.remaining());
        unwrap().getBytes(a(i), byteBuffer);
        return this;
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public ByteBuf setByte(int i, int i2) {
        a(i, 1);
        unwrap().setByte(a(i), i2);
        return this;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.AbstractByteBuf
    public void _setByte(int i, int i2) {
        unwrap()._setByte(a(i), i2);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public ByteBuf setShort(int i, int i2) {
        a(i, 2);
        unwrap().setShort(a(i), i2);
        return this;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.AbstractByteBuf
    public void _setShort(int i, int i2) {
        unwrap()._setShort(a(i), i2);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public ByteBuf setShortLE(int i, int i2) {
        a(i, 2);
        unwrap().setShortLE(a(i), i2);
        return this;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.AbstractByteBuf
    public void _setShortLE(int i, int i2) {
        unwrap()._setShortLE(a(i), i2);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public ByteBuf setMedium(int i, int i2) {
        a(i, 3);
        unwrap().setMedium(a(i), i2);
        return this;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.AbstractByteBuf
    public void _setMedium(int i, int i2) {
        unwrap()._setMedium(a(i), i2);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public ByteBuf setMediumLE(int i, int i2) {
        a(i, 3);
        unwrap().setMediumLE(a(i), i2);
        return this;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.AbstractByteBuf
    public void _setMediumLE(int i, int i2) {
        unwrap()._setMediumLE(a(i), i2);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public ByteBuf setInt(int i, int i2) {
        a(i, 4);
        unwrap().setInt(a(i), i2);
        return this;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.AbstractByteBuf
    public void _setInt(int i, int i2) {
        unwrap()._setInt(a(i), i2);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public ByteBuf setIntLE(int i, int i2) {
        a(i, 4);
        unwrap().setIntLE(a(i), i2);
        return this;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.AbstractByteBuf
    public void _setIntLE(int i, int i2) {
        unwrap()._setIntLE(a(i), i2);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public ByteBuf setLong(int i, long j) {
        a(i, 8);
        unwrap().setLong(a(i), j);
        return this;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.AbstractByteBuf
    public void _setLong(int i, long j) {
        unwrap()._setLong(a(i), j);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public ByteBuf setLongLE(int i, long j) {
        a(i, 8);
        unwrap().setLongLE(a(i), j);
        return this;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.AbstractByteBuf
    public void _setLongLE(int i, long j) {
        unwrap().setLongLE(a(i), j);
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf setBytes(int i, byte[] bArr, int i2, int i3) {
        a(i, i3);
        unwrap().setBytes(a(i), bArr, i2, i3);
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf setBytes(int i, ByteBuf byteBuf, int i2, int i3) {
        a(i, i3);
        unwrap().setBytes(a(i), byteBuf, i2, i3);
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf setBytes(int i, ByteBuffer byteBuffer) {
        a(i, byteBuffer.remaining());
        unwrap().setBytes(a(i), byteBuffer);
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf getBytes(int i, OutputStream outputStream, int i2) throws IOException {
        a(i, i2);
        unwrap().getBytes(a(i), outputStream, i2);
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public int getBytes(int i, GatheringByteChannel gatheringByteChannel, int i2) throws IOException {
        a(i, i2);
        return unwrap().getBytes(a(i), gatheringByteChannel, i2);
    }

    @Override // io.netty.buffer.ByteBuf
    public int getBytes(int i, FileChannel fileChannel, long j, int i2) throws IOException {
        a(i, i2);
        return unwrap().getBytes(a(i), fileChannel, j, i2);
    }

    @Override // io.netty.buffer.ByteBuf
    public int setBytes(int i, InputStream inputStream, int i2) throws IOException {
        a(i, i2);
        return unwrap().setBytes(a(i), inputStream, i2);
    }

    @Override // io.netty.buffer.ByteBuf
    public int setBytes(int i, ScatteringByteChannel scatteringByteChannel, int i2) throws IOException {
        a(i, i2);
        return unwrap().setBytes(a(i), scatteringByteChannel, i2);
    }

    @Override // io.netty.buffer.ByteBuf
    public int setBytes(int i, FileChannel fileChannel, long j, int i2) throws IOException {
        a(i, i2);
        return unwrap().setBytes(a(i), fileChannel, j, i2);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public int forEachByte(int i, int i2, ByteProcessor byteProcessor) {
        a(i, i2);
        int forEachByte = unwrap().forEachByte(a(i), i2, byteProcessor);
        int i3 = this.e;
        if (forEachByte < i3) {
            return -1;
        }
        return forEachByte - i3;
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public int forEachByteDesc(int i, int i2, ByteProcessor byteProcessor) {
        a(i, i2);
        int forEachByteDesc = unwrap().forEachByteDesc(a(i), i2, byteProcessor);
        int i3 = this.e;
        if (forEachByteDesc < i3) {
            return -1;
        }
        return forEachByteDesc - i3;
    }

    private int a(int i) {
        return i + this.e;
    }

    private static ByteBuf c() {
        throw new UnsupportedOperationException("sliced buffer");
    }
}
