package io.netty.handler.codec.compression;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/* loaded from: classes4.dex */
public class SnappyFrameEncoder extends MessageToByteEncoder<ByteBuf> {
    private static final byte[] a = {-1, 6, 0, 0, 115, 78, 97, 80, 112, 89};
    private final Snappy b = new Snappy();
    private boolean c;

    /* JADX INFO: Access modifiers changed from: protected */
    public void encode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, ByteBuf byteBuf2) throws Exception {
        if (byteBuf.isReadable()) {
            if (!this.c) {
                this.c = true;
                byteBuf2.writeBytes(a);
            }
            int readableBytes = byteBuf.readableBytes();
            if (readableBytes > 18) {
                while (true) {
                    int writerIndex = byteBuf2.writerIndex() + 1;
                    if (readableBytes < 18) {
                        a(byteBuf.readSlice(readableBytes), byteBuf2, readableBytes);
                        return;
                    }
                    byteBuf2.writeInt(0);
                    if (readableBytes > 32767) {
                        ByteBuf readSlice = byteBuf.readSlice(32767);
                        a(readSlice, byteBuf2);
                        this.b.encode(readSlice, byteBuf2, 32767);
                        a(byteBuf2, writerIndex);
                        readableBytes -= 32767;
                    } else {
                        ByteBuf readSlice2 = byteBuf.readSlice(readableBytes);
                        a(readSlice2, byteBuf2);
                        this.b.encode(readSlice2, byteBuf2, readableBytes);
                        a(byteBuf2, writerIndex);
                        return;
                    }
                }
            } else {
                a(byteBuf, byteBuf2, readableBytes);
            }
        }
    }

    private static void a(ByteBuf byteBuf, ByteBuf byteBuf2, int i) {
        byteBuf2.writeByte(1);
        b(byteBuf2, i + 4);
        a(byteBuf, byteBuf2);
        byteBuf2.writeBytes(byteBuf, i);
    }

    private static void a(ByteBuf byteBuf, int i) {
        int writerIndex = (byteBuf.writerIndex() - i) - 3;
        if ((writerIndex >>> 24) == 0) {
            byteBuf.setMediumLE(i, writerIndex);
            return;
        }
        throw new CompressionException("compressed data too large: " + writerIndex);
    }

    private static void b(ByteBuf byteBuf, int i) {
        byteBuf.writeMediumLE(i);
    }

    private static void a(ByteBuf byteBuf, ByteBuf byteBuf2) {
        byteBuf2.writeIntLE(Snappy.a(byteBuf));
    }
}
