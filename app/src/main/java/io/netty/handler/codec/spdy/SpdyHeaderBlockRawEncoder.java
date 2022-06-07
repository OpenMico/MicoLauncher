package io.netty.handler.codec.spdy;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import java.util.Set;

/* loaded from: classes4.dex */
public class SpdyHeaderBlockRawEncoder extends c {
    private final int a;

    @Override // io.netty.handler.codec.spdy.c
    public void a() {
    }

    public SpdyHeaderBlockRawEncoder(SpdyVersion spdyVersion) {
        if (spdyVersion != null) {
            this.a = spdyVersion.a();
            return;
        }
        throw new NullPointerException("version");
    }

    private static void a(ByteBuf byteBuf, int i, int i2) {
        byteBuf.setInt(i, i2);
    }

    private static void a(ByteBuf byteBuf, int i) {
        byteBuf.writeInt(i);
    }

    @Override // io.netty.handler.codec.spdy.c
    public ByteBuf encode(ByteBufAllocator byteBufAllocator, SpdyHeadersFrame spdyHeadersFrame) throws Exception {
        Set<CharSequence> names = spdyHeadersFrame.headers().names();
        int size = names.size();
        if (size == 0) {
            return Unpooled.EMPTY_BUFFER;
        }
        if (size <= 65535) {
            ByteBuf heapBuffer = byteBufAllocator.heapBuffer();
            a(heapBuffer, size);
            for (CharSequence charSequence : names) {
                a(heapBuffer, charSequence.length());
                ByteBufUtil.writeAscii(heapBuffer, charSequence);
                int writerIndex = heapBuffer.writerIndex();
                a(heapBuffer, 0);
                int i = 0;
                for (CharSequence charSequence2 : spdyHeadersFrame.headers().getAll(charSequence)) {
                    int length = charSequence2.length();
                    if (length > 0) {
                        ByteBufUtil.writeAscii(heapBuffer, charSequence2);
                        heapBuffer.writeByte(0);
                        i += length + 1;
                    }
                }
                if (i != 0) {
                    i--;
                }
                if (i > 65535) {
                    throw new IllegalArgumentException("header exceeds allowable length: " + ((Object) charSequence));
                } else if (i > 0) {
                    a(heapBuffer, writerIndex, i);
                    heapBuffer.writerIndex(heapBuffer.writerIndex() - 1);
                }
            }
            return heapBuffer;
        }
        throw new IllegalArgumentException("header block contains too many headers");
    }
}
