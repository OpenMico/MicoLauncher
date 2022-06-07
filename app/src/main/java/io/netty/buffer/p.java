package io.netty.buffer;

import io.netty.util.Recycler;
import io.netty.util.internal.PlatformDependent;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.FileChannel;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: PooledHeapByteBuf.java */
/* loaded from: classes4.dex */
public class p extends m<byte[]> {
    private static final Recycler<p> l = new Recycler<p>() { // from class: io.netty.buffer.p.1
        /* JADX INFO: Access modifiers changed from: protected */
        /* renamed from: a */
        public p newObject(Recycler.Handle<p> handle) {
            return new p(handle, 0);
        }
    };

    @Override // io.netty.buffer.ByteBuf
    public final boolean hasArray() {
        return true;
    }

    @Override // io.netty.buffer.ByteBuf
    public final boolean hasMemoryAddress() {
        return false;
    }

    @Override // io.netty.buffer.ByteBuf
    public final boolean isDirect() {
        return false;
    }

    @Override // io.netty.buffer.ByteBuf
    public final int nioBufferCount() {
        return 1;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static p c(int i) {
        p pVar = l.get();
        pVar.a(i);
        return pVar;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public p(Recycler.Handle<? extends p> handle, int i) {
        super(handle, i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.AbstractByteBuf
    public byte _getByte(int i) {
        return g.a((byte[]) this.f, b(i));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.AbstractByteBuf
    public short _getShort(int i) {
        return g.b((byte[]) this.f, b(i));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.AbstractByteBuf
    public short _getShortLE(int i) {
        return g.c((byte[]) this.f, b(i));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.AbstractByteBuf
    public int _getUnsignedMedium(int i) {
        return g.d((byte[]) this.f, b(i));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.AbstractByteBuf
    public int _getUnsignedMediumLE(int i) {
        return g.e((byte[]) this.f, b(i));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.AbstractByteBuf
    public int _getInt(int i) {
        return g.f((byte[]) this.f, b(i));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.AbstractByteBuf
    public int _getIntLE(int i) {
        return g.g((byte[]) this.f, b(i));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.AbstractByteBuf
    public long _getLong(int i) {
        return g.h((byte[]) this.f, b(i));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.AbstractByteBuf
    public long _getLongLE(int i) {
        return g.i((byte[]) this.f, b(i));
    }

    @Override // io.netty.buffer.ByteBuf
    public final ByteBuf getBytes(int i, ByteBuf byteBuf, int i2, int i3) {
        checkDstIndex(i, i3, i2, byteBuf.capacity());
        if (byteBuf.hasMemoryAddress()) {
            PlatformDependent.copyMemory((byte[]) this.f, b(i), i2 + byteBuf.memoryAddress(), i3);
        } else if (byteBuf.hasArray()) {
            getBytes(i, byteBuf.array(), byteBuf.arrayOffset() + i2, i3);
        } else {
            byteBuf.setBytes(i2, (byte[]) this.f, b(i), i3);
        }
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public final ByteBuf getBytes(int i, byte[] bArr, int i2, int i3) {
        checkDstIndex(i, i3, i2, bArr.length);
        System.arraycopy(this.f, b(i), bArr, i2, i3);
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public final ByteBuf getBytes(int i, ByteBuffer byteBuffer) {
        checkIndex(i);
        byteBuffer.put((byte[]) this.f, b(i), Math.min(capacity() - i, byteBuffer.remaining()));
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public final ByteBuf getBytes(int i, OutputStream outputStream, int i2) throws IOException {
        checkIndex(i, i2);
        outputStream.write((byte[]) this.f, b(i), i2);
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public final int getBytes(int i, GatheringByteChannel gatheringByteChannel, int i2) throws IOException {
        return a(i, gatheringByteChannel, i2, false);
    }

    private int a(int i, GatheringByteChannel gatheringByteChannel, int i2, boolean z) throws IOException {
        ByteBuffer byteBuffer;
        checkIndex(i, i2);
        int b = b(i);
        if (z) {
            byteBuffer = b();
        } else {
            byteBuffer = ByteBuffer.wrap((byte[]) this.f);
        }
        return gatheringByteChannel.write((ByteBuffer) byteBuffer.clear().position(b).limit(b + i2));
    }

    @Override // io.netty.buffer.ByteBuf
    public final int getBytes(int i, FileChannel fileChannel, long j, int i2) throws IOException {
        return a(i, fileChannel, j, i2, false);
    }

    private int a(int i, FileChannel fileChannel, long j, int i2, boolean z) throws IOException {
        checkIndex(i, i2);
        int b = b(i);
        return fileChannel.write((ByteBuffer) (z ? b() : ByteBuffer.wrap((byte[]) this.f)).clear().position(b).limit(b + i2), j);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public final int readBytes(GatheringByteChannel gatheringByteChannel, int i) throws IOException {
        checkReadableBytes(i);
        int a = a(this.b, gatheringByteChannel, i, true);
        this.b += a;
        return a;
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public final int readBytes(FileChannel fileChannel, long j, int i) throws IOException {
        checkReadableBytes(i);
        int a = a(this.b, fileChannel, j, i, true);
        this.b += a;
        return a;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.AbstractByteBuf
    public void _setByte(int i, int i2) {
        g.a((byte[]) this.f, b(i), i2);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.AbstractByteBuf
    public void _setShort(int i, int i2) {
        g.b((byte[]) this.f, b(i), i2);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.AbstractByteBuf
    public void _setShortLE(int i, int i2) {
        g.c((byte[]) this.f, b(i), i2);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.AbstractByteBuf
    public void _setMedium(int i, int i2) {
        g.d((byte[]) this.f, b(i), i2);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.AbstractByteBuf
    public void _setMediumLE(int i, int i2) {
        g.e((byte[]) this.f, b(i), i2);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.AbstractByteBuf
    public void _setInt(int i, int i2) {
        g.f((byte[]) this.f, b(i), i2);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.AbstractByteBuf
    public void _setIntLE(int i, int i2) {
        g.g((byte[]) this.f, b(i), i2);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.AbstractByteBuf
    public void _setLong(int i, long j) {
        g.a((byte[]) this.f, b(i), j);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.buffer.AbstractByteBuf
    public void _setLongLE(int i, long j) {
        g.b((byte[]) this.f, b(i), j);
    }

    @Override // io.netty.buffer.ByteBuf
    public final ByteBuf setBytes(int i, ByteBuf byteBuf, int i2, int i3) {
        checkSrcIndex(i, i3, i2, byteBuf.capacity());
        if (byteBuf.hasMemoryAddress()) {
            PlatformDependent.copyMemory(byteBuf.memoryAddress() + i2, (byte[]) this.f, b(i), i3);
        } else if (byteBuf.hasArray()) {
            setBytes(i, byteBuf.array(), byteBuf.arrayOffset() + i2, i3);
        } else {
            byteBuf.getBytes(i2, (byte[]) this.f, b(i), i3);
        }
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public final ByteBuf setBytes(int i, byte[] bArr, int i2, int i3) {
        checkSrcIndex(i, i3, i2, bArr.length);
        System.arraycopy(bArr, i2, this.f, b(i), i3);
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public final ByteBuf setBytes(int i, ByteBuffer byteBuffer) {
        int remaining = byteBuffer.remaining();
        checkIndex(i, remaining);
        byteBuffer.get((byte[]) this.f, b(i), remaining);
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public final int setBytes(int i, InputStream inputStream, int i2) throws IOException {
        checkIndex(i, i2);
        return inputStream.read((byte[]) this.f, b(i), i2);
    }

    @Override // io.netty.buffer.ByteBuf
    public final int setBytes(int i, ScatteringByteChannel scatteringByteChannel, int i2) throws IOException {
        checkIndex(i, i2);
        int b = b(i);
        try {
            return scatteringByteChannel.read((ByteBuffer) b().clear().position(b).limit(b + i2));
        } catch (ClosedChannelException unused) {
            return -1;
        }
    }

    @Override // io.netty.buffer.ByteBuf
    public final int setBytes(int i, FileChannel fileChannel, long j, int i2) throws IOException {
        checkIndex(i, i2);
        int b = b(i);
        try {
            return fileChannel.read((ByteBuffer) b().clear().position(b).limit(b + i2), j);
        } catch (ClosedChannelException unused) {
            return -1;
        }
    }

    @Override // io.netty.buffer.ByteBuf
    public final ByteBuf copy(int i, int i2) {
        checkIndex(i, i2);
        ByteBuf heapBuffer = alloc().heapBuffer(i2, maxCapacity());
        heapBuffer.writeBytes((byte[]) this.f, b(i), i2);
        return heapBuffer;
    }

    @Override // io.netty.buffer.ByteBuf
    public final ByteBuffer[] nioBuffers(int i, int i2) {
        return new ByteBuffer[]{nioBuffer(i, i2)};
    }

    @Override // io.netty.buffer.ByteBuf
    public final ByteBuffer nioBuffer(int i, int i2) {
        checkIndex(i, i2);
        return ByteBuffer.wrap((byte[]) this.f, b(i), i2).slice();
    }

    @Override // io.netty.buffer.ByteBuf
    public final ByteBuffer internalNioBuffer(int i, int i2) {
        checkIndex(i, i2);
        int b = b(i);
        return (ByteBuffer) b().clear().position(b).limit(b + i2);
    }

    @Override // io.netty.buffer.ByteBuf
    public final byte[] array() {
        ensureAccessible();
        return (byte[]) this.f;
    }

    @Override // io.netty.buffer.ByteBuf
    public final int arrayOffset() {
        return this.g;
    }

    @Override // io.netty.buffer.ByteBuf
    public final long memoryAddress() {
        throw new UnsupportedOperationException();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final ByteBuffer a(byte[] bArr) {
        return ByteBuffer.wrap(bArr);
    }
}
