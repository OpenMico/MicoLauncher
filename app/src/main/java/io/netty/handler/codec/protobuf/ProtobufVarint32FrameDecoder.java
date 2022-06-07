package io.netty.handler.codec.protobuf;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.CorruptedFrameException;
import java.util.List;

/* loaded from: classes4.dex */
public class ProtobufVarint32FrameDecoder extends ByteToMessageDecoder {
    @Override // io.netty.handler.codec.ByteToMessageDecoder
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        byteBuf.markReaderIndex();
        int readerIndex = byteBuf.readerIndex();
        int a = a(byteBuf);
        if (readerIndex != byteBuf.readerIndex()) {
            if (a < 0) {
                throw new CorruptedFrameException("negative length: " + a);
            } else if (byteBuf.readableBytes() < a) {
                byteBuf.resetReaderIndex();
            } else {
                list.add(byteBuf.readRetainedSlice(a));
            }
        }
    }

    private static int a(ByteBuf byteBuf) {
        if (!byteBuf.isReadable()) {
            return 0;
        }
        byteBuf.markReaderIndex();
        byte readByte = byteBuf.readByte();
        if (readByte >= 0) {
            return readByte;
        }
        int i = readByte & Byte.MAX_VALUE;
        if (!byteBuf.isReadable()) {
            byteBuf.resetReaderIndex();
            return 0;
        }
        byte readByte2 = byteBuf.readByte();
        if (readByte2 >= 0) {
            return (readByte2 << 7) | i;
        }
        int i2 = i | ((readByte2 & Byte.MAX_VALUE) << 7);
        if (!byteBuf.isReadable()) {
            byteBuf.resetReaderIndex();
            return 0;
        }
        byte readByte3 = byteBuf.readByte();
        if (readByte3 >= 0) {
            return (readByte3 << 14) | i2;
        }
        int i3 = i2 | ((readByte3 & Byte.MAX_VALUE) << 14);
        if (!byteBuf.isReadable()) {
            byteBuf.resetReaderIndex();
            return 0;
        }
        byte readByte4 = byteBuf.readByte();
        if (readByte4 >= 0) {
            return (readByte4 << 21) | i3;
        }
        int i4 = i3 | ((readByte4 & Byte.MAX_VALUE) << 21);
        if (!byteBuf.isReadable()) {
            byteBuf.resetReaderIndex();
            return 0;
        }
        byte readByte5 = byteBuf.readByte();
        int i5 = i4 | (readByte5 << 28);
        if (readByte5 >= 0) {
            return i5;
        }
        throw new CorruptedFrameException("malformed varint.");
    }
}
