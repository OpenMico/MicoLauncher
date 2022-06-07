package io.netty.handler.stream;

import com.xiaomi.smarthome.setting.ServerSetting;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;

/* loaded from: classes4.dex */
public class ChunkedNioStream implements ChunkedInput<ByteBuf> {
    private final ReadableByteChannel a;
    private final int b;
    private long c;
    private final ByteBuffer d;

    @Override // io.netty.handler.stream.ChunkedInput
    public long length() {
        return -1L;
    }

    public ChunkedNioStream(ReadableByteChannel readableByteChannel) {
        this(readableByteChannel, 8192);
    }

    public ChunkedNioStream(ReadableByteChannel readableByteChannel, int i) {
        if (readableByteChannel == null) {
            throw new NullPointerException(ServerSetting.SERVER_IN);
        } else if (i > 0) {
            this.a = readableByteChannel;
            this.c = 0L;
            this.b = i;
            this.d = ByteBuffer.allocate(i);
        } else {
            throw new IllegalArgumentException("chunkSize: " + i + " (expected: a positive integer)");
        }
    }

    public long transferredBytes() {
        return this.c;
    }

    @Override // io.netty.handler.stream.ChunkedInput
    public boolean isEndOfInput() throws Exception {
        int read;
        if (this.d.position() > 0) {
            return false;
        }
        if (!this.a.isOpen() || (read = this.a.read(this.d)) < 0) {
            return true;
        }
        this.c += read;
        return false;
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
        if (isEndOfInput()) {
            return null;
        }
        int position = this.d.position();
        do {
            int read = this.a.read(this.d);
            if (read < 0) {
                break;
            }
            position += read;
            this.c += read;
        } while (position != this.b);
        this.d.flip();
        ByteBuf buffer = byteBufAllocator.buffer(this.d.remaining());
        try {
            buffer.writeBytes(this.d);
            this.d.clear();
            return buffer;
        } catch (Throwable th) {
            buffer.release();
            throw th;
        }
    }

    @Override // io.netty.handler.stream.ChunkedInput
    public long progress() {
        return this.c;
    }
}
