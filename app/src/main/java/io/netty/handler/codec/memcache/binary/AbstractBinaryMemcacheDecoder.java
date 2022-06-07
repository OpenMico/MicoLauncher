package io.netty.handler.codec.memcache.binary;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.memcache.AbstractMemcacheObjectDecoder;
import io.netty.handler.codec.memcache.DefaultLastMemcacheContent;
import io.netty.handler.codec.memcache.MemcacheContent;
import io.netty.handler.codec.memcache.binary.BinaryMemcacheMessage;

/* loaded from: classes4.dex */
public abstract class AbstractBinaryMemcacheDecoder<M extends BinaryMemcacheMessage> extends AbstractMemcacheObjectDecoder {
    public static final int DEFAULT_MAX_CHUNK_SIZE = 8192;
    private final int a;
    private M c;
    private int d;
    private a e;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public enum a {
        READ_HEADER,
        READ_EXTRAS,
        READ_KEY,
        READ_CONTENT,
        BAD_MESSAGE
    }

    protected abstract M buildInvalidMessage();

    protected abstract M decodeHeader(ByteBuf byteBuf);

    protected AbstractBinaryMemcacheDecoder() {
        this(8192);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public AbstractBinaryMemcacheDecoder(int i) {
        this.e = a.READ_HEADER;
        if (i >= 0) {
            this.a = i;
            return;
        }
        throw new IllegalArgumentException("chunkSize must be a positive integer: " + i);
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x004c A[Catch: Exception -> 0x00f2, TryCatch #3 {Exception -> 0x00f2, blocks: (B:12:0x0044, B:14:0x004c, B:17:0x0053, B:18:0x005c), top: B:64:0x0044 }] */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0068 A[Catch: Exception -> 0x00e6, TryCatch #2 {Exception -> 0x00e6, blocks: (B:19:0x0060, B:21:0x0068, B:24:0x006f, B:25:0x0078), top: B:62:0x0060 }] */
    /* JADX WARN: Removed duplicated region for block: B:28:0x009f  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x00cd A[Catch: Exception -> 0x00da, TryCatch #1 {Exception -> 0x00da, blocks: (B:26:0x0085, B:30:0x00a2, B:32:0x00a6, B:33:0x00a8, B:36:0x00af, B:38:0x00ba, B:39:0x00c0, B:40:0x00c5, B:43:0x00cd, B:44:0x00d2), top: B:60:0x0085 }] */
    @Override // io.netty.handler.codec.ByteToMessageDecoder
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void decode(io.netty.channel.ChannelHandlerContext r3, io.netty.buffer.ByteBuf r4, java.util.List<java.lang.Object> r5) throws java.lang.Exception {
        /*
            Method dump skipped, instructions count: 280
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.handler.codec.memcache.binary.AbstractBinaryMemcacheDecoder.decode(io.netty.channel.ChannelHandlerContext, io.netty.buffer.ByteBuf, java.util.List):void");
    }

    private M a(Exception exc) {
        this.e = a.BAD_MESSAGE;
        M buildInvalidMessage = buildInvalidMessage();
        buildInvalidMessage.setDecoderResult(DecoderResult.failure(exc));
        return buildInvalidMessage;
    }

    private MemcacheContent b(Exception exc) {
        this.e = a.BAD_MESSAGE;
        DefaultLastMemcacheContent defaultLastMemcacheContent = new DefaultLastMemcacheContent(Unpooled.EMPTY_BUFFER);
        defaultLastMemcacheContent.setDecoderResult(DecoderResult.failure(exc));
        return defaultLastMemcacheContent;
    }

    @Override // io.netty.handler.codec.ByteToMessageDecoder, io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelInboundHandler
    public void channelInactive(ChannelHandlerContext channelHandlerContext) throws Exception {
        super.channelInactive(channelHandlerContext);
        resetDecoder();
    }

    protected void resetDecoder() {
        M m = this.c;
        if (m != null) {
            m.release();
            this.c = null;
        }
        this.d = 0;
    }
}
