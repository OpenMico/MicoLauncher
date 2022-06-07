package io.netty.handler.codec.spdy;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

/* compiled from: SpdyHeaderBlockZlibDecoder.java */
/* loaded from: classes4.dex */
final class e extends SpdyHeaderBlockRawDecoder {
    private static final SpdyProtocolException a = new SpdyProtocolException("Invalid Header Block");
    private final Inflater b = new Inflater();
    private ByteBuf c;

    /* JADX INFO: Access modifiers changed from: package-private */
    public e(SpdyVersion spdyVersion, int i) {
        super(spdyVersion, i);
    }

    @Override // io.netty.handler.codec.spdy.SpdyHeaderBlockRawDecoder, io.netty.handler.codec.spdy.b
    void a(ByteBufAllocator byteBufAllocator, ByteBuf byteBuf, SpdyHeadersFrame spdyHeadersFrame) throws Exception {
        int a2 = a(byteBuf);
        do {
        } while (a(byteBufAllocator, spdyHeadersFrame) > 0);
        if (this.b.getRemaining() == 0) {
            byteBuf.skipBytes(a2);
            return;
        }
        throw a;
    }

    private int a(ByteBuf byteBuf) {
        int readableBytes = byteBuf.readableBytes();
        if (byteBuf.hasArray()) {
            this.b.setInput(byteBuf.array(), byteBuf.arrayOffset() + byteBuf.readerIndex(), readableBytes);
        } else {
            byte[] bArr = new byte[readableBytes];
            byteBuf.getBytes(byteBuf.readerIndex(), bArr);
            this.b.setInput(bArr, 0, bArr.length);
        }
        return readableBytes;
    }

    private int a(ByteBufAllocator byteBufAllocator, SpdyHeadersFrame spdyHeadersFrame) throws Exception {
        a(byteBufAllocator);
        byte[] array = this.c.array();
        int arrayOffset = this.c.arrayOffset() + this.c.writerIndex();
        try {
            int inflate = this.b.inflate(array, arrayOffset, this.c.writableBytes());
            if (inflate == 0 && this.b.needsDictionary()) {
                try {
                    this.b.setDictionary(a.a);
                    inflate = this.b.inflate(array, arrayOffset, this.c.writableBytes());
                } catch (IllegalArgumentException unused) {
                    throw a;
                }
            }
            if (spdyHeadersFrame != null) {
                this.c.writerIndex(this.c.writerIndex() + inflate);
                decodeHeaderBlock(this.c, spdyHeadersFrame);
                this.c.discardReadBytes();
            }
            return inflate;
        } catch (DataFormatException e) {
            throw new SpdyProtocolException("Received invalid header block", e);
        }
    }

    private void a(ByteBufAllocator byteBufAllocator) {
        if (this.c == null) {
            this.c = byteBufAllocator.heapBuffer(4096);
        }
        this.c.ensureWritable(1);
    }

    @Override // io.netty.handler.codec.spdy.SpdyHeaderBlockRawDecoder, io.netty.handler.codec.spdy.b
    void a(SpdyHeadersFrame spdyHeadersFrame) throws Exception {
        super.a(spdyHeadersFrame);
        b();
    }

    @Override // io.netty.handler.codec.spdy.SpdyHeaderBlockRawDecoder, io.netty.handler.codec.spdy.b
    public void a() {
        super.a();
        b();
        this.b.end();
    }

    private void b() {
        ByteBuf byteBuf = this.c;
        if (byteBuf != null) {
            byteBuf.release();
            this.c = null;
        }
    }
}
