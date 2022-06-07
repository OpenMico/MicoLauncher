package io.netty.handler.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.internal.ObjectUtil;
import java.nio.ByteOrder;
import java.util.List;

@ChannelHandler.Sharable
/* loaded from: classes4.dex */
public class LengthFieldPrepender extends MessageToMessageEncoder<ByteBuf> {
    private final ByteOrder a;
    private final int b;
    private final boolean c;
    private final int d;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.handler.codec.MessageToMessageEncoder
    public /* bridge */ /* synthetic */ void encode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List list) throws Exception {
        encode2(channelHandlerContext, byteBuf, (List<Object>) list);
    }

    public LengthFieldPrepender(int i) {
        this(i, false);
    }

    public LengthFieldPrepender(int i, boolean z) {
        this(i, 0, z);
    }

    public LengthFieldPrepender(int i, int i2) {
        this(i, i2, false);
    }

    public LengthFieldPrepender(int i, int i2, boolean z) {
        this(ByteOrder.BIG_ENDIAN, i, i2, z);
    }

    public LengthFieldPrepender(ByteOrder byteOrder, int i, int i2, boolean z) {
        if (i == 1 || i == 2 || i == 3 || i == 4 || i == 8) {
            ObjectUtil.checkNotNull(byteOrder, "byteOrder");
            this.a = byteOrder;
            this.b = i;
            this.c = z;
            this.d = i2;
            return;
        }
        throw new IllegalArgumentException("lengthFieldLength must be either 1, 2, 3, 4, or 8: " + i);
    }

    /* renamed from: encode  reason: avoid collision after fix types in other method */
    protected void encode2(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        int readableBytes = byteBuf.readableBytes() + this.d;
        if (this.c) {
            readableBytes += this.b;
        }
        if (readableBytes >= 0) {
            int i = this.b;
            if (i != 8) {
                switch (i) {
                    case 1:
                        if (readableBytes < 256) {
                            list.add(channelHandlerContext.alloc().buffer(1).order(this.a).writeByte((byte) readableBytes));
                            break;
                        } else {
                            throw new IllegalArgumentException("length does not fit into a byte: " + readableBytes);
                        }
                    case 2:
                        if (readableBytes < 65536) {
                            list.add(channelHandlerContext.alloc().buffer(2).order(this.a).writeShort((short) readableBytes));
                            break;
                        } else {
                            throw new IllegalArgumentException("length does not fit into a short integer: " + readableBytes);
                        }
                    case 3:
                        if (readableBytes < 16777216) {
                            list.add(channelHandlerContext.alloc().buffer(3).order(this.a).writeMedium(readableBytes));
                            break;
                        } else {
                            throw new IllegalArgumentException("length does not fit into a medium integer: " + readableBytes);
                        }
                    case 4:
                        list.add(channelHandlerContext.alloc().buffer(4).order(this.a).writeInt(readableBytes));
                        break;
                    default:
                        throw new Error("should not reach here");
                }
            } else {
                list.add(channelHandlerContext.alloc().buffer(8).order(this.a).writeLong(readableBytes));
            }
            list.add(byteBuf.retain());
            return;
        }
        throw new IllegalArgumentException("Adjusted frame length (" + readableBytes + ") is less than zero");
    }
}
