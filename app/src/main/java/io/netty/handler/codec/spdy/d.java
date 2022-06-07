package io.netty.handler.codec.spdy;

import com.jcraft.jzlib.Deflater;
import com.jcraft.jzlib.JZlib;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.compression.CompressionException;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: SpdyHeaderBlockJZlibEncoder.java */
/* loaded from: classes4.dex */
public class d extends SpdyHeaderBlockRawEncoder {
    private final Deflater a = new Deflater();
    private boolean b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public d(SpdyVersion spdyVersion, int i, int i2, int i3) {
        super(spdyVersion);
        if (i < 0 || i > 9) {
            throw new IllegalArgumentException("compressionLevel: " + i + " (expected: 0-9)");
        } else if (i2 < 9 || i2 > 15) {
            throw new IllegalArgumentException("windowBits: " + i2 + " (expected: 9-15)");
        } else if (i3 < 1 || i3 > 9) {
            throw new IllegalArgumentException("memLevel: " + i3 + " (expected: 1-9)");
        } else {
            int deflateInit = this.a.deflateInit(i, i2, i3, JZlib.W_ZLIB);
            if (deflateInit == 0) {
                int deflateSetDictionary = this.a.deflateSetDictionary(a.a, a.a.length);
                if (deflateSetDictionary != 0) {
                    throw new CompressionException("failed to set the SPDY dictionary: " + deflateSetDictionary);
                }
                return;
            }
            throw new CompressionException("failed to initialize an SPDY header block deflater: " + deflateInit);
        }
    }

    private void a(ByteBuf byteBuf) {
        int i;
        byte[] bArr;
        int readableBytes = byteBuf.readableBytes();
        if (byteBuf.hasArray()) {
            bArr = byteBuf.array();
            i = byteBuf.arrayOffset() + byteBuf.readerIndex();
        } else {
            bArr = new byte[readableBytes];
            byteBuf.getBytes(byteBuf.readerIndex(), bArr);
            i = 0;
        }
        Deflater deflater = this.a;
        deflater.next_in = bArr;
        deflater.next_in_index = i;
        deflater.avail_in = readableBytes;
    }

    private ByteBuf a(ByteBufAllocator byteBufAllocator) {
        ByteBuf byteBuf;
        Throwable th;
        int i;
        int i2;
        int ceil;
        try {
            i = this.a.next_in_index;
            i2 = this.a.next_out_index;
            ceil = ((int) Math.ceil(this.a.next_in.length * 1.001d)) + 12;
            byteBuf = byteBufAllocator.heapBuffer(ceil);
        } catch (Throwable th2) {
            th = th2;
            byteBuf = null;
        }
        try {
            this.a.next_out = byteBuf.array();
            this.a.next_out_index = byteBuf.arrayOffset() + byteBuf.writerIndex();
            this.a.avail_out = ceil;
            int deflate = this.a.deflate(2);
            byteBuf.skipBytes(this.a.next_in_index - i);
            if (deflate == 0) {
                int i3 = this.a.next_out_index - i2;
                if (i3 > 0) {
                    byteBuf.writerIndex(byteBuf.writerIndex() + i3);
                }
                Deflater deflater = this.a;
                deflater.next_in = null;
                deflater.next_out = null;
                return byteBuf;
            }
            throw new CompressionException("compression failure: " + deflate);
        } catch (Throwable th3) {
            th = th3;
            Deflater deflater2 = this.a;
            deflater2.next_in = null;
            deflater2.next_out = null;
            if (byteBuf != null) {
                byteBuf.release();
            }
            throw th;
        }
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
                a(encode);
                return a(byteBufAllocator);
            } finally {
                encode.release();
            }
        }
    }

    @Override // io.netty.handler.codec.spdy.SpdyHeaderBlockRawEncoder, io.netty.handler.codec.spdy.c
    public void a() {
        if (!this.b) {
            this.b = true;
            this.a.deflateEnd();
            Deflater deflater = this.a;
            deflater.next_in = null;
            deflater.next_out = null;
        }
    }
}
