package io.netty.handler.codec.compression;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import lzma.sdk.ICodeProgress;
import lzma.sdk.lzma.Encoder;

/* loaded from: classes4.dex */
public class LzmaFrameEncoder extends MessageToByteEncoder<ByteBuf> {
    private static final InternalLogger a = InternalLoggerFactory.getInstance(LzmaFrameEncoder.class);
    private static boolean e;
    private final Encoder b;
    private final byte c;
    private final int d;

    private static int a(int i) {
        return ((int) (i * (i < 200 ? 1.5d : i < 500 ? 1.2d : i < 1000 ? 1.1d : i < 10000 ? 1.05d : 1.02d))) + 13;
    }

    public LzmaFrameEncoder() {
        this(65536);
    }

    public LzmaFrameEncoder(int i, int i2, int i3) {
        this(i, i2, i3, 65536);
    }

    public LzmaFrameEncoder(int i) {
        this(3, 0, 2, i);
    }

    public LzmaFrameEncoder(int i, int i2, int i3, int i4) {
        this(i, i2, i3, i4, false, 32);
    }

    public LzmaFrameEncoder(int i, int i2, int i3, int i4, boolean z, int i5) {
        if (i < 0 || i > 8) {
            throw new IllegalArgumentException("lc: " + i + " (expected: 0-8)");
        } else if (i2 < 0 || i2 > 4) {
            throw new IllegalArgumentException("lp: " + i2 + " (expected: 0-4)");
        } else if (i3 < 0 || i3 > 4) {
            throw new IllegalArgumentException("pb: " + i3 + " (expected: 0-4)");
        } else {
            if (i + i2 > 4 && !e) {
                a.warn("The latest versions of LZMA libraries (for example, XZ Utils) has an additional requirement: lc + lp <= 4. Data which don't follow this requirement cannot be decompressed with this libraries.");
                e = true;
            }
            if (i4 < 0) {
                throw new IllegalArgumentException("dictionarySize: " + i4 + " (expected: 0+)");
            } else if (i5 < 5 || i5 > 273) {
                throw new IllegalArgumentException(String.format("numFastBytes: %d (expected: %d-%d)", Integer.valueOf(i5), 5, 273));
            } else {
                this.b = new Encoder();
                this.b.setDictionarySize(i4);
                this.b.setEndMarkerMode(z);
                this.b.setMatchFinder(1);
                this.b.setNumFastBytes(i5);
                this.b.setLcLpPb(i, i2, i3);
                this.c = (byte) ((((i3 * 5) + i2) * 9) + i);
                this.d = Integer.reverseBytes(i4);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void encode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, ByteBuf byteBuf2) throws Exception {
        int readableBytes = byteBuf.readableBytes();
        ByteBufInputStream byteBufInputStream = new ByteBufInputStream(byteBuf);
        ByteBufOutputStream byteBufOutputStream = new ByteBufOutputStream(byteBuf2);
        byteBufOutputStream.writeByte(this.c);
        byteBufOutputStream.writeInt(this.d);
        byteBufOutputStream.writeLong(Long.reverseBytes(readableBytes));
        this.b.code(byteBufInputStream, byteBufOutputStream, -1L, -1L, (ICodeProgress) null);
        byteBufInputStream.close();
        byteBufOutputStream.close();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public ByteBuf allocateBuffer(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, boolean z) throws Exception {
        return channelHandlerContext.alloc().ioBuffer(a(byteBuf.readableBytes()));
    }
}
