package io.netty.handler.codec.compression;

import io.netty.handler.codec.ByteToMessageDecoder;
import java.util.zip.Adler32;
import java.util.zip.Checksum;

/* loaded from: classes4.dex */
public class FastLzFrameDecoder extends ByteToMessageDecoder {
    private a a;
    private final Checksum c;
    private int d;
    private int e;
    private boolean f;
    private boolean g;
    private int h;

    /* loaded from: classes4.dex */
    private enum a {
        INIT_BLOCK,
        INIT_BLOCK_PARAMS,
        DECOMPRESS_DATA,
        CORRUPTED
    }

    public FastLzFrameDecoder() {
        this(false);
    }

    public FastLzFrameDecoder(boolean z) {
        this(z ? new Adler32() : null);
    }

    public FastLzFrameDecoder(Checksum checksum) {
        this.a = a.INIT_BLOCK;
        this.c = checksum;
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x0056  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0058  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x005e  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x005f  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0063  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0065 A[Catch: Exception -> 0x0161, TryCatch #3 {Exception -> 0x0161, blocks: (B:3:0x0004, B:4:0x0012, B:5:0x0015, B:6:0x0019, B:7:0x0022, B:10:0x002a, B:12:0x0033, B:16:0x003e, B:20:0x0048, B:21:0x004e, B:25:0x0059, B:29:0x0060, B:32:0x0065, B:34:0x0069, B:36:0x006f, B:38:0x007b, B:39:0x0080, B:40:0x0082, B:41:0x0088, B:44:0x0092, B:46:0x009a, B:47:0x00b3, B:81:0x0151, B:82:0x0154, B:83:0x0155, B:84:0x015c, B:85:0x015d, B:86:0x0160), top: B:94:0x0004 }] */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0090  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x0092 A[Catch: Exception -> 0x0161, TryCatch #3 {Exception -> 0x0161, blocks: (B:3:0x0004, B:4:0x0012, B:5:0x0015, B:6:0x0019, B:7:0x0022, B:10:0x002a, B:12:0x0033, B:16:0x003e, B:20:0x0048, B:21:0x004e, B:25:0x0059, B:29:0x0060, B:32:0x0065, B:34:0x0069, B:36:0x006f, B:38:0x007b, B:39:0x0080, B:40:0x0082, B:41:0x0088, B:44:0x0092, B:46:0x009a, B:47:0x00b3, B:81:0x0151, B:82:0x0154, B:83:0x0155, B:84:0x015c, B:85:0x015d, B:86:0x0160), top: B:94:0x0004 }] */
    @Override // io.netty.handler.codec.ByteToMessageDecoder
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void decode(io.netty.channel.ChannelHandlerContext r17, io.netty.buffer.ByteBuf r18, java.util.List<java.lang.Object> r19) throws java.lang.Exception {
        /*
            Method dump skipped, instructions count: 372
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.handler.codec.compression.FastLzFrameDecoder.decode(io.netty.channel.ChannelHandlerContext, io.netty.buffer.ByteBuf, java.util.List):void");
    }
}
