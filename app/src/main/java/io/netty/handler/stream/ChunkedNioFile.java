package io.netty.handler.stream;

import com.xiaomi.smarthome.setting.ServerSetting;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/* loaded from: classes4.dex */
public class ChunkedNioFile implements ChunkedInput<ByteBuf> {
    private final FileChannel a;
    private final long b;
    private final long c;
    private final int d;
    private long e;

    public ChunkedNioFile(File file) throws IOException {
        this(new FileInputStream(file).getChannel());
    }

    public ChunkedNioFile(File file, int i) throws IOException {
        this(new FileInputStream(file).getChannel(), i);
    }

    public ChunkedNioFile(FileChannel fileChannel) throws IOException {
        this(fileChannel, 8192);
    }

    public ChunkedNioFile(FileChannel fileChannel, int i) throws IOException {
        this(fileChannel, 0L, fileChannel.size(), i);
    }

    public ChunkedNioFile(FileChannel fileChannel, long j, long j2, int i) throws IOException {
        if (fileChannel != null) {
            int i2 = (j > 0L ? 1 : (j == 0L ? 0 : -1));
            if (i2 < 0) {
                throw new IllegalArgumentException("offset: " + j + " (expected: 0 or greater)");
            } else if (j2 < 0) {
                throw new IllegalArgumentException("length: " + j2 + " (expected: 0 or greater)");
            } else if (i > 0) {
                if (i2 != 0) {
                    fileChannel.position(j);
                }
                this.a = fileChannel;
                this.d = i;
                this.b = j;
                this.e = j;
                this.c = j + j2;
            } else {
                throw new IllegalArgumentException("chunkSize: " + i + " (expected: a positive integer)");
            }
        } else {
            throw new NullPointerException(ServerSetting.SERVER_IN);
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
        return this.e >= this.c || !this.a.isOpen();
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
        ByteBuf buffer = byteBufAllocator.buffer(min);
        int i = 0;
        do {
            try {
                int writeBytes = buffer.writeBytes(this.a, min - i);
                if (writeBytes < 0) {
                    break;
                }
                i += writeBytes;
            } catch (Throwable th) {
                buffer.release();
                throw th;
            }
        } while (i != min);
        this.e += i;
        return buffer;
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
