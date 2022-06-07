package io.netty.handler.codec.compression;

import com.ning.compress.BufferRecycler;
import com.ning.compress.lzf.ChunkDecoder;
import com.ning.compress.lzf.util.ChunkDecoderFactory;
import io.netty.handler.codec.ByteToMessageDecoder;

/* loaded from: classes4.dex */
public class LzfDecoder extends ByteToMessageDecoder {
    private a a;
    private ChunkDecoder c;
    private BufferRecycler d;
    private int e;
    private int f;
    private boolean g;

    /* loaded from: classes4.dex */
    private enum a {
        INIT_BLOCK,
        INIT_ORIGINAL_LENGTH,
        DECOMPRESS_DATA,
        CORRUPTED
    }

    public LzfDecoder() {
        this(false);
    }

    public LzfDecoder(boolean z) {
        this.a = a.INIT_BLOCK;
        this.c = z ? ChunkDecoderFactory.safeInstance() : ChunkDecoderFactory.optimalInstance();
        this.d = BufferRecycler.instance();
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x0056  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0058 A[Catch: Exception -> 0x0108, TryCatch #2 {Exception -> 0x0108, blocks: (B:2:0x0000, B:3:0x000c, B:4:0x000f, B:5:0x0013, B:6:0x001c, B:9:0x0025, B:11:0x002d, B:12:0x0032, B:13:0x0035, B:14:0x0039, B:15:0x0040, B:16:0x0046, B:19:0x0050, B:22:0x0058, B:23:0x0062, B:26:0x006c, B:28:0x0072, B:30:0x007c, B:31:0x0087, B:32:0x0092, B:35:0x00be, B:37:0x00c4, B:39:0x00d1, B:40:0x00d8, B:42:0x00dd, B:43:0x00fb, B:44:0x00fc, B:45:0x0103, B:46:0x0104, B:47:0x0107, B:34:0x00a8), top: B:51:0x0000 }] */
    /* JADX WARN: Removed duplicated region for block: B:25:0x006a  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x006c A[Catch: Exception -> 0x0108, TryCatch #2 {Exception -> 0x0108, blocks: (B:2:0x0000, B:3:0x000c, B:4:0x000f, B:5:0x0013, B:6:0x001c, B:9:0x0025, B:11:0x002d, B:12:0x0032, B:13:0x0035, B:14:0x0039, B:15:0x0040, B:16:0x0046, B:19:0x0050, B:22:0x0058, B:23:0x0062, B:26:0x006c, B:28:0x0072, B:30:0x007c, B:31:0x0087, B:32:0x0092, B:35:0x00be, B:37:0x00c4, B:39:0x00d1, B:40:0x00d8, B:42:0x00dd, B:43:0x00fb, B:44:0x00fc, B:45:0x0103, B:46:0x0104, B:47:0x0107, B:34:0x00a8), top: B:51:0x0000 }] */
    @Override // io.netty.handler.codec.ByteToMessageDecoder
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void decode(io.netty.channel.ChannelHandlerContext r12, io.netty.buffer.ByteBuf r13, java.util.List<java.lang.Object> r14) throws java.lang.Exception {
        /*
            Method dump skipped, instructions count: 296
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.handler.codec.compression.LzfDecoder.decode(io.netty.channel.ChannelHandlerContext, io.netty.buffer.ByteBuf, java.util.List):void");
    }
}
