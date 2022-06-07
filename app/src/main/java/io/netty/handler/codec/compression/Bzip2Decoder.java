package io.netty.handler.codec.compression;

import io.netty.handler.codec.ByteToMessageDecoder;

/* loaded from: classes4.dex */
public class Bzip2Decoder extends ByteToMessageDecoder {
    private a a = a.INIT;
    private final a c = new a();
    private d d;
    private g e;
    private int f;
    private int g;
    private int h;

    /* loaded from: classes4.dex */
    private enum a {
        INIT,
        INIT_BLOCK,
        INIT_BLOCK_PARAMS,
        RECEIVE_HUFFMAN_USED_MAP,
        RECEIVE_HUFFMAN_USED_BITMAPS,
        RECEIVE_SELECTORS_NUMBER,
        RECEIVE_SELECTORS,
        RECEIVE_HUFFMAN_LENGTH,
        DECODE_HUFFMAN_DATA,
        EOF
    }

    /* JADX WARN: Code restructure failed: missing block: B:105:0x01b3, code lost:
        r5[r9][r13] = (byte) r7;
        r13 = r13 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:141:0x024d, code lost:
        throw new io.netty.handler.codec.compression.DecompressionException("incorrect selectors number");
     */
    /* JADX WARN: Code restructure failed: missing block: B:145:0x025d, code lost:
        throw new io.netty.handler.codec.compression.DecompressionException("incorrect huffman groups number");
     */
    /* JADX WARN: Code restructure failed: missing block: B:147:0x0265, code lost:
        throw new io.netty.handler.codec.compression.DecompressionException("bad block header");
     */
    /* JADX WARN: Code restructure failed: missing block: B:149:0x026d, code lost:
        throw new io.netty.handler.codec.compression.DecompressionException("block size is invalid");
     */
    /* JADX WARN: Removed duplicated region for block: B:122:0x01ec  */
    /* JADX WARN: Removed duplicated region for block: B:126:0x0202  */
    /* JADX WARN: Removed duplicated region for block: B:174:0x013d A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:181:0x00a7 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:182:0x00c7 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:183:0x01e3 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:184:0x00e8 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:185:0x0201 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:198:0x01e0 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:42:0x00a8  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x00c8  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x00e9  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x013e  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x015d  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x018e  */
    @Override // io.netty.handler.codec.ByteToMessageDecoder
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void decode(io.netty.channel.ChannelHandlerContext r17, io.netty.buffer.ByteBuf r18, java.util.List<java.lang.Object> r19) throws java.lang.Exception {
        /*
            Method dump skipped, instructions count: 654
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.handler.codec.compression.Bzip2Decoder.decode(io.netty.channel.ChannelHandlerContext, io.netty.buffer.ByteBuf, java.util.List):void");
    }

    public boolean isClosed() {
        return this.a == a.EOF;
    }
}
