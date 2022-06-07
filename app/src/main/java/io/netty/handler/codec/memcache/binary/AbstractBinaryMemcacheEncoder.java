package io.netty.handler.codec.memcache.binary;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.memcache.AbstractMemcacheObjectEncoder;
import io.netty.handler.codec.memcache.MemcacheMessage;
import io.netty.handler.codec.memcache.binary.BinaryMemcacheMessage;

/* loaded from: classes4.dex */
public abstract class AbstractBinaryMemcacheEncoder<M extends BinaryMemcacheMessage> extends AbstractMemcacheObjectEncoder<M> {
    protected abstract void encodeHeader(ByteBuf byteBuf, M m);

    /* JADX WARN: Multi-variable type inference failed */
    @Override // io.netty.handler.codec.memcache.AbstractMemcacheObjectEncoder
    protected /* bridge */ /* synthetic */ ByteBuf encodeMessage(ChannelHandlerContext channelHandlerContext, MemcacheMessage memcacheMessage) {
        return encodeMessage(channelHandlerContext, (ChannelHandlerContext) ((BinaryMemcacheMessage) memcacheMessage));
    }

    protected ByteBuf encodeMessage(ChannelHandlerContext channelHandlerContext, M m) {
        ByteBuf buffer = channelHandlerContext.alloc().buffer(m.extrasLength() + 24 + m.keyLength());
        encodeHeader(buffer, m);
        a(buffer, m.extras());
        b(buffer, m.key());
        return buffer;
    }

    private static void a(ByteBuf byteBuf, ByteBuf byteBuf2) {
        if (byteBuf2 != null && byteBuf2.isReadable()) {
            byteBuf.writeBytes(byteBuf2);
        }
    }

    private static void b(ByteBuf byteBuf, ByteBuf byteBuf2) {
        if (byteBuf2 != null && byteBuf2.isReadable()) {
            byteBuf.writeBytes(byteBuf2);
        }
    }
}
