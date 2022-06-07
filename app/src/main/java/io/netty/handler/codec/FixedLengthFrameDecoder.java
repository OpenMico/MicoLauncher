package io.netty.handler.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import java.util.List;

/* loaded from: classes4.dex */
public class FixedLengthFrameDecoder extends ByteToMessageDecoder {
    private final int a;

    public FixedLengthFrameDecoder(int i) {
        if (i > 0) {
            this.a = i;
            return;
        }
        throw new IllegalArgumentException("frameLength must be a positive integer: " + i);
    }

    @Override // io.netty.handler.codec.ByteToMessageDecoder
    protected final void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        Object decode = decode(channelHandlerContext, byteBuf);
        if (decode != null) {
            list.add(decode);
        }
    }

    protected Object decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
        int readableBytes = byteBuf.readableBytes();
        int i = this.a;
        if (readableBytes < i) {
            return null;
        }
        return byteBuf.readRetainedSlice(i);
    }
}
