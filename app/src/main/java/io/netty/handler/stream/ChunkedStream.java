package io.netty.handler.stream;

import com.xiaomi.smarthome.setting.ServerSetting;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import java.io.InputStream;
import java.io.PushbackInputStream;

/* loaded from: classes4.dex */
public class ChunkedStream implements ChunkedInput<ByteBuf> {
    private final PushbackInputStream a;
    private final int b;
    private long c;
    private boolean d;

    @Override // io.netty.handler.stream.ChunkedInput
    public long length() {
        return -1L;
    }

    public ChunkedStream(InputStream inputStream) {
        this(inputStream, 8192);
    }

    public ChunkedStream(InputStream inputStream, int i) {
        if (inputStream == null) {
            throw new NullPointerException(ServerSetting.SERVER_IN);
        } else if (i > 0) {
            if (inputStream instanceof PushbackInputStream) {
                this.a = (PushbackInputStream) inputStream;
            } else {
                this.a = new PushbackInputStream(inputStream);
            }
            this.b = i;
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
        if (this.d || (read = this.a.read()) < 0) {
            return true;
        }
        this.a.unread(read);
        return false;
    }

    @Override // io.netty.handler.stream.ChunkedInput
    public void close() throws Exception {
        this.d = true;
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
        int i;
        if (isEndOfInput()) {
            return null;
        }
        if (this.a.available() <= 0) {
            i = this.b;
        } else {
            i = Math.min(this.b, this.a.available());
        }
        ByteBuf buffer = byteBufAllocator.buffer(i);
        try {
            this.c += buffer.writeBytes(this.a, i);
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
