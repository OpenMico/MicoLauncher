package io.netty.handler.codec.protobuf;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import java.io.IOException;

@ChannelHandler.Sharable
/* loaded from: classes4.dex */
public class ProtobufVarint32LengthFieldPrepender extends MessageToByteEncoder<ByteBuf> {
    static int a(int i) {
        if ((i & (-128)) == 0) {
            return 1;
        }
        if ((i & (-16384)) == 0) {
            return 2;
        }
        if (((-2097152) & i) == 0) {
            return 3;
        }
        return (i & (-268435456)) == 0 ? 4 : 5;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void encode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, ByteBuf byteBuf2) throws Exception {
        int readableBytes = byteBuf.readableBytes();
        byteBuf2.ensureWritable(a(readableBytes) + readableBytes);
        a(byteBuf2, readableBytes);
        byteBuf2.writeBytes(byteBuf, byteBuf.readerIndex(), readableBytes);
    }

    static void a(ByteBuf byteBuf, int i) throws IOException {
        while ((i & (-128)) != 0) {
            byteBuf.writeByte((i & 127) | 128);
            i >>>= 7;
        }
        byteBuf.writeByte(i);
    }
}
