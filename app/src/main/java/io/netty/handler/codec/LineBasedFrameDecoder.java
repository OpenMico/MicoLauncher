package io.netty.handler.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ByteProcessor;
import java.util.List;

/* loaded from: classes4.dex */
public class LineBasedFrameDecoder extends ByteToMessageDecoder {
    private final int a;
    private final boolean c;
    private final boolean d;
    private boolean e;
    private int f;

    public LineBasedFrameDecoder(int i) {
        this(i, true, false);
    }

    public LineBasedFrameDecoder(int i, boolean z, boolean z2) {
        this.a = i;
        this.c = z2;
        this.d = z;
    }

    @Override // io.netty.handler.codec.ByteToMessageDecoder
    protected final void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        Object decode = decode(channelHandlerContext, byteBuf);
        if (decode != null) {
            list.add(decode);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public Object decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
        int a = a(byteBuf);
        int i = 2;
        if (this.e) {
            if (a >= 0) {
                int readerIndex = (this.f + a) - byteBuf.readerIndex();
                if (byteBuf.getByte(a) != 13) {
                    i = 1;
                }
                byteBuf.readerIndex(a + i);
                this.f = 0;
                this.e = false;
                if (!this.c) {
                    a(channelHandlerContext, readerIndex);
                }
            } else {
                this.f += byteBuf.readableBytes();
                byteBuf.readerIndex(byteBuf.writerIndex());
            }
            return null;
        } else if (a >= 0) {
            int readerIndex2 = a - byteBuf.readerIndex();
            if (byteBuf.getByte(a) != 13) {
                i = 1;
            }
            if (readerIndex2 > this.a) {
                byteBuf.readerIndex(a + i);
                a(channelHandlerContext, readerIndex2);
                return null;
            } else if (!this.d) {
                return byteBuf.readRetainedSlice(readerIndex2 + i);
            } else {
                ByteBuf readRetainedSlice = byteBuf.readRetainedSlice(readerIndex2);
                byteBuf.skipBytes(i);
                return readRetainedSlice;
            }
        } else {
            int readableBytes = byteBuf.readableBytes();
            if (readableBytes > this.a) {
                this.f = readableBytes;
                byteBuf.readerIndex(byteBuf.writerIndex());
                this.e = true;
                if (this.c) {
                    a(channelHandlerContext, "over " + this.f);
                }
            }
            return null;
        }
    }

    private void a(ChannelHandlerContext channelHandlerContext, int i) {
        a(channelHandlerContext, String.valueOf(i));
    }

    private void a(ChannelHandlerContext channelHandlerContext, String str) {
        channelHandlerContext.fireExceptionCaught((Throwable) new TooLongFrameException("frame length (" + str + ") exceeds the allowed maximum (" + this.a + ')'));
    }

    private static int a(ByteBuf byteBuf) {
        int forEachByte = byteBuf.forEachByte(ByteProcessor.FIND_LF);
        return (forEachByte <= 0 || byteBuf.getByte(forEachByte + (-1)) != 13) ? forEachByte : forEachByte - 1;
    }
}
