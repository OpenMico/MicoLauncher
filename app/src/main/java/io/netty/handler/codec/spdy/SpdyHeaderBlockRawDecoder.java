package io.netty.handler.codec.spdy;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

/* loaded from: classes4.dex */
public class SpdyHeaderBlockRawDecoder extends b {
    private final int a;
    private a b;
    private ByteBuf c;
    private int d;
    private int e;
    private int f;
    private String g;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public enum a {
        READ_NUM_HEADERS,
        READ_NAME_LENGTH,
        READ_NAME,
        SKIP_NAME,
        READ_VALUE_LENGTH,
        READ_VALUE,
        SKIP_VALUE,
        END_HEADER_BLOCK,
        ERROR
    }

    public SpdyHeaderBlockRawDecoder(SpdyVersion spdyVersion, int i) {
        if (spdyVersion != null) {
            this.a = i;
            this.b = a.READ_NUM_HEADERS;
            return;
        }
        throw new NullPointerException("spdyVersion");
    }

    private static int a(ByteBuf byteBuf) {
        int d = a.d(byteBuf, byteBuf.readerIndex());
        byteBuf.skipBytes(4);
        return d;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // io.netty.handler.codec.spdy.b
    public void a(ByteBufAllocator byteBufAllocator, ByteBuf byteBuf, SpdyHeadersFrame spdyHeadersFrame) throws Exception {
        if (byteBuf == null) {
            throw new NullPointerException("headerBlock");
        } else if (spdyHeadersFrame != null) {
            ByteBuf byteBuf2 = this.c;
            if (byteBuf2 == null) {
                decodeHeaderBlock(byteBuf, spdyHeadersFrame);
                if (byteBuf.isReadable()) {
                    this.c = byteBufAllocator.buffer(byteBuf.readableBytes());
                    this.c.writeBytes(byteBuf);
                    return;
                }
                return;
            }
            byteBuf2.writeBytes(byteBuf);
            decodeHeaderBlock(this.c, spdyHeadersFrame);
            if (this.c.isReadable()) {
                this.c.discardReadBytes();
            } else {
                b();
            }
        } else {
            throw new NullPointerException("frame");
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:119:0x00ba A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:153:0x0000 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void decodeHeaderBlock(io.netty.buffer.ByteBuf r8, io.netty.handler.codec.spdy.SpdyHeadersFrame r9) throws java.lang.Exception {
        /*
            Method dump skipped, instructions count: 510
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.handler.codec.spdy.SpdyHeaderBlockRawDecoder.decodeHeaderBlock(io.netty.buffer.ByteBuf, io.netty.handler.codec.spdy.SpdyHeadersFrame):void");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // io.netty.handler.codec.spdy.b
    public void a(SpdyHeadersFrame spdyHeadersFrame) throws Exception {
        if (this.b != a.END_HEADER_BLOCK) {
            spdyHeadersFrame.setInvalid();
        }
        b();
        this.d = 0;
        this.g = null;
        this.b = a.READ_NUM_HEADERS;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // io.netty.handler.codec.spdy.b
    public void a() {
        b();
    }

    private void b() {
        ByteBuf byteBuf = this.c;
        if (byteBuf != null) {
            byteBuf.release();
            this.c = null;
        }
    }
}
