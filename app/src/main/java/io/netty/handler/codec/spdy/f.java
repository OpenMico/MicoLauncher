package io.netty.handler.codec.spdy;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.Unpooled;
import java.util.zip.Deflater;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: SpdyHeaderBlockZlibEncoder.java */
/* loaded from: classes4.dex */
public class f extends SpdyHeaderBlockRawEncoder {
    private final Deflater a;
    private boolean b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public f(SpdyVersion spdyVersion, int i) {
        super(spdyVersion);
        if (i < 0 || i > 9) {
            throw new IllegalArgumentException("compressionLevel: " + i + " (expected: 0-9)");
        }
        this.a = new Deflater(i);
        this.a.setDictionary(a.a);
    }

    private int a(ByteBuf byteBuf) {
        int readableBytes = byteBuf.readableBytes();
        if (byteBuf.hasArray()) {
            this.a.setInput(byteBuf.array(), byteBuf.arrayOffset() + byteBuf.readerIndex(), readableBytes);
        } else {
            byte[] bArr = new byte[readableBytes];
            byteBuf.getBytes(byteBuf.readerIndex(), bArr);
            this.a.setInput(bArr, 0, bArr.length);
        }
        return readableBytes;
    }

    private ByteBuf a(ByteBufAllocator byteBufAllocator, int i) {
        ByteBuf heapBuffer = byteBufAllocator.heapBuffer(i);
        while (b(heapBuffer)) {
            try {
                heapBuffer.ensureWritable(heapBuffer.capacity() << 1);
            } catch (Throwable th) {
                heapBuffer.release();
                throw th;
            }
        }
        return heapBuffer;
    }

    private boolean b(ByteBuf byteBuf) {
        byte[] array = byteBuf.array();
        int arrayOffset = byteBuf.arrayOffset() + byteBuf.writerIndex();
        int writableBytes = byteBuf.writableBytes();
        int deflate = this.a.deflate(array, arrayOffset, writableBytes, 2);
        byteBuf.writerIndex(byteBuf.writerIndex() + deflate);
        return deflate == writableBytes;
    }

    @Override // io.netty.handler.codec.spdy.SpdyHeaderBlockRawEncoder, io.netty.handler.codec.spdy.c
    public ByteBuf encode(ByteBufAllocator byteBufAllocator, SpdyHeadersFrame spdyHeadersFrame) throws Exception {
        if (spdyHeadersFrame == null) {
            throw new IllegalArgumentException("frame");
        } else if (this.b) {
            return Unpooled.EMPTY_BUFFER;
        } else {
            ByteBuf encode = super.encode(byteBufAllocator, spdyHeadersFrame);
            try {
                if (!encode.isReadable()) {
                    return Unpooled.EMPTY_BUFFER;
                }
                return a(byteBufAllocator, a(encode));
            } finally {
                encode.release();
            }
        }
    }

    @Override // io.netty.handler.codec.spdy.SpdyHeaderBlockRawEncoder, io.netty.handler.codec.spdy.c
    public void a() {
        if (!this.b) {
            this.b = true;
            this.a.end();
            super.a();
        }
    }
}
