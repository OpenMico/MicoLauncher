package io.netty.handler.codec.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.stream.ChunkedInput;

/* loaded from: classes4.dex */
public class HttpChunkedInput implements ChunkedInput<HttpContent> {
    private final ChunkedInput<ByteBuf> a;
    private final LastHttpContent b;
    private boolean c;

    public HttpChunkedInput(ChunkedInput<ByteBuf> chunkedInput) {
        this.a = chunkedInput;
        this.b = LastHttpContent.EMPTY_LAST_CONTENT;
    }

    public HttpChunkedInput(ChunkedInput<ByteBuf> chunkedInput, LastHttpContent lastHttpContent) {
        this.a = chunkedInput;
        this.b = lastHttpContent;
    }

    @Override // io.netty.handler.stream.ChunkedInput
    public boolean isEndOfInput() throws Exception {
        if (this.a.isEndOfInput()) {
            return this.c;
        }
        return false;
    }

    @Override // io.netty.handler.stream.ChunkedInput
    public void close() throws Exception {
        this.a.close();
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // io.netty.handler.stream.ChunkedInput
    @Deprecated
    public HttpContent readChunk(ChannelHandlerContext channelHandlerContext) throws Exception {
        return readChunk(channelHandlerContext.alloc());
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // io.netty.handler.stream.ChunkedInput
    public HttpContent readChunk(ByteBufAllocator byteBufAllocator) throws Exception {
        if (!this.a.isEndOfInput()) {
            return new DefaultHttpContent(this.a.readChunk(byteBufAllocator));
        }
        if (this.c) {
            return null;
        }
        this.c = true;
        return this.b;
    }

    @Override // io.netty.handler.stream.ChunkedInput
    public long length() {
        return this.a.length();
    }

    @Override // io.netty.handler.stream.ChunkedInput
    public long progress() {
        return this.a.progress();
    }
}
