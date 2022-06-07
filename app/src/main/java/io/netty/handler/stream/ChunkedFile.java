package io.netty.handler.stream;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/* loaded from: classes4.dex */
public class ChunkedFile implements ChunkedInput<ByteBuf> {
    private final RandomAccessFile a;
    private final long b;
    private final long c;
    private final int d;
    private long e;

    public ChunkedFile(File file) throws IOException {
        this(file, 8192);
    }

    public ChunkedFile(File file, int i) throws IOException {
        this(new RandomAccessFile(file, "r"), i);
    }

    public ChunkedFile(RandomAccessFile randomAccessFile) throws IOException {
        this(randomAccessFile, 8192);
    }

    public ChunkedFile(RandomAccessFile randomAccessFile, int i) throws IOException {
        this(randomAccessFile, 0L, randomAccessFile.length(), i);
    }

    public ChunkedFile(RandomAccessFile randomAccessFile, long j, long j2, int i) throws IOException {
        if (randomAccessFile == null) {
            throw new NullPointerException("file");
        } else if (j < 0) {
            throw new IllegalArgumentException("offset: " + j + " (expected: 0 or greater)");
        } else if (j2 < 0) {
            throw new IllegalArgumentException("length: " + j2 + " (expected: 0 or greater)");
        } else if (i > 0) {
            this.a = randomAccessFile;
            this.b = j;
            this.e = j;
            this.c = j2 + j;
            this.d = i;
            randomAccessFile.seek(j);
        } else {
            throw new IllegalArgumentException("chunkSize: " + i + " (expected: a positive integer)");
        }
    }

    public long startOffset() {
        return this.b;
    }

    public long endOffset() {
        return this.c;
    }

    public long currentOffset() {
        return this.e;
    }

    @Override // io.netty.handler.stream.ChunkedInput
    public boolean isEndOfInput() throws Exception {
        return this.e >= this.c || !this.a.getChannel().isOpen();
    }

    @Override // io.netty.handler.stream.ChunkedInput
    public void close() throws Exception {
        this.a.close();
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // io.netty.handler.stream.ChunkedInput
    @Deprecated
    public ByteBuf readChunk(ChannelHandlerContext channelHandlerContext) throws Exception {
        return readChunk(channelHandlerContext.alloc());
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // io.netty.handler.stream.ChunkedInput
    public ByteBuf readChunk(ByteBufAllocator byteBufAllocator) throws Exception {
        long j = this.e;
        long j2 = this.c;
        if (j >= j2) {
            return null;
        }
        int min = (int) Math.min(this.d, j2 - j);
        ByteBuf heapBuffer = byteBufAllocator.heapBuffer(min);
        try {
            this.a.readFully(heapBuffer.array(), heapBuffer.arrayOffset(), min);
            heapBuffer.writerIndex(min);
            this.e = j + min;
            return heapBuffer;
        } catch (Throwable th) {
            heapBuffer.release();
            throw th;
        }
    }

    @Override // io.netty.handler.stream.ChunkedInput
    public long length() {
        return this.c - this.b;
    }

    @Override // io.netty.handler.stream.ChunkedInput
    public long progress() {
        return this.e - this.b;
    }
}
