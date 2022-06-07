package io.netty.handler.codec.compression;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import java.util.zip.CRC32;
import java.util.zip.Inflater;

/* loaded from: classes4.dex */
public class JdkZlibDecoder extends ZlibDecoder {
    private Inflater a;
    private final byte[] c;
    private final CRC32 d;
    private a e;
    private int f;
    private int g;
    private volatile boolean h;
    private boolean i;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public enum a {
        HEADER_START,
        HEADER_END,
        FLG_READ,
        XLEN_READ,
        SKIP_FNAME,
        SKIP_COMMENT,
        PROCESS_FHCRC,
        FOOTER_START
    }

    public JdkZlibDecoder() {
        this(ZlibWrapper.ZLIB, null);
    }

    public JdkZlibDecoder(byte[] bArr) {
        this(ZlibWrapper.ZLIB, bArr);
    }

    public JdkZlibDecoder(ZlibWrapper zlibWrapper) {
        this(zlibWrapper, null);
    }

    private JdkZlibDecoder(ZlibWrapper zlibWrapper, byte[] bArr) {
        this.e = a.HEADER_START;
        this.f = -1;
        this.g = -1;
        if (zlibWrapper != null) {
            switch (zlibWrapper) {
                case GZIP:
                    this.a = new Inflater(true);
                    this.d = new CRC32();
                    break;
                case NONE:
                    this.a = new Inflater(true);
                    this.d = null;
                    break;
                case ZLIB:
                    this.a = new Inflater();
                    this.d = null;
                    break;
                case ZLIB_OR_NONE:
                    this.i = true;
                    this.d = null;
                    break;
                default:
                    throw new IllegalArgumentException("Only GZIP or ZLIB is supported, but you used " + zlibWrapper);
            }
            this.c = bArr;
            return;
        }
        throw new NullPointerException("wrapper");
    }

    @Override // io.netty.handler.codec.compression.ZlibDecoder
    public boolean isClosed() {
        return this.h;
    }

    /* JADX WARN: Code restructure failed: missing block: B:58:0x0101, code lost:
        r12.skipBytes(r0 - r10.a.getRemaining());
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x010b, code lost:
        if (r2 == false) goto L_0x0119;
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x010d, code lost:
        r10.e = io.netty.handler.codec.compression.JdkZlibDecoder.a.h;
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x0115, code lost:
        if (b(r12) == false) goto L_0x0119;
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x0117, code lost:
        r10.h = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x0126, code lost:
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:88:?, code lost:
        return;
     */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // io.netty.handler.codec.ByteToMessageDecoder
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void decode(io.netty.channel.ChannelHandlerContext r11, io.netty.buffer.ByteBuf r12, java.util.List<java.lang.Object> r13) throws java.lang.Exception {
        /*
            Method dump skipped, instructions count: 320
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.handler.codec.compression.JdkZlibDecoder.decode(io.netty.channel.ChannelHandlerContext, io.netty.buffer.ByteBuf, java.util.List):void");
    }

    @Override // io.netty.handler.codec.ByteToMessageDecoder
    protected void handlerRemoved0(ChannelHandlerContext channelHandlerContext) throws Exception {
        super.handlerRemoved0(channelHandlerContext);
        Inflater inflater = this.a;
        if (inflater != null) {
            inflater.end();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0094  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x00bd  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00d9  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x00fc  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x011e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private boolean a(io.netty.buffer.ByteBuf r8) {
        /*
            Method dump skipped, instructions count: 370
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.handler.codec.compression.JdkZlibDecoder.a(io.netty.buffer.ByteBuf):boolean");
    }

    private boolean b(ByteBuf byteBuf) {
        if (byteBuf.readableBytes() < 8) {
            return false;
        }
        c(byteBuf);
        int i = 0;
        for (int i2 = 0; i2 < 4; i2++) {
            i |= byteBuf.readUnsignedByte() << (i2 * 8);
        }
        int totalOut = this.a.getTotalOut();
        if (i == totalOut) {
            return true;
        }
        throw new DecompressionException("Number of bytes mismatch. Expected: " + i + ", Got: " + totalOut);
    }

    private void c(ByteBuf byteBuf) {
        long j = 0;
        for (int i = 0; i < 4; i++) {
            j |= byteBuf.readUnsignedByte() << (i * 8);
        }
        long value = this.d.getValue();
        if (j != value) {
            throw new DecompressionException("CRC value missmatch. Expected: " + j + ", Got: " + value);
        }
    }

    private static boolean a(short s) {
        return (s & 30720) == 30720 && s % 31 == 0;
    }
}
