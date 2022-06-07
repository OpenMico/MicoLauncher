package io.netty.handler.codec.compression;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import java.util.zip.Adler32;
import java.util.zip.Checksum;

/* loaded from: classes4.dex */
public class FastLzFrameEncoder extends MessageToByteEncoder<ByteBuf> {
    private final int a;
    private final Checksum b;

    public FastLzFrameEncoder() {
        this(0, null);
    }

    public FastLzFrameEncoder(int i) {
        this(i, null);
    }

    public FastLzFrameEncoder(boolean z) {
        this(0, z ? new Adler32() : null);
    }

    public FastLzFrameEncoder(int i, Checksum checksum) {
        super(false);
        if (i == 0 || i == 1 || i == 2) {
            this.a = i;
            this.b = checksum;
            return;
        }
        throw new IllegalArgumentException(String.format("level: %d (expected: %d or %d or %d)", Integer.valueOf(i), 0, 1, 2));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void encode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, ByteBuf byteBuf2) throws Exception {
        int i;
        int i2;
        int i3;
        byte[] bArr;
        byte[] bArr2;
        int i4;
        Checksum checksum = this.b;
        while (byteBuf.isReadable()) {
            int readerIndex = byteBuf.readerIndex();
            int min = Math.min(byteBuf.readableBytes(), 65535);
            int writerIndex = byteBuf2.writerIndex();
            byteBuf2.setMedium(writerIndex, 4607066);
            int i5 = writerIndex + 4;
            int i6 = 4;
            if (checksum == null) {
                i6 = 0;
            }
            int i7 = i5 + i6;
            if (min < 32) {
                byteBuf2.ensureWritable(i7 + 2 + min);
                byte[] array = byteBuf2.array();
                int arrayOffset = byteBuf2.arrayOffset() + i7 + 2;
                if (checksum != null) {
                    if (byteBuf.hasArray()) {
                        bArr2 = byteBuf.array();
                        i4 = readerIndex + byteBuf.arrayOffset();
                    } else {
                        bArr2 = new byte[min];
                        byteBuf.getBytes(readerIndex, bArr2);
                        i4 = 0;
                    }
                    checksum.reset();
                    checksum.update(bArr2, i4, min);
                    byteBuf2.setInt(i5, (int) checksum.getValue());
                    System.arraycopy(bArr2, i4, array, arrayOffset, min);
                } else {
                    byteBuf.getBytes(readerIndex, array, arrayOffset, min);
                }
                i2 = min;
                i = 0;
            } else {
                if (byteBuf.hasArray()) {
                    byte[] array2 = byteBuf.array();
                    i3 = byteBuf.arrayOffset() + readerIndex;
                    bArr = array2;
                } else {
                    byte[] bArr3 = new byte[min];
                    byteBuf.getBytes(readerIndex, bArr3);
                    bArr = bArr3;
                    i3 = 0;
                }
                if (checksum != null) {
                    checksum.reset();
                    checksum.update(bArr, i3, min);
                    byteBuf2.setInt(i5, (int) checksum.getValue());
                }
                byteBuf2.ensureWritable(i7 + 4 + n.a(min));
                byte[] array3 = byteBuf2.array();
                int arrayOffset2 = byteBuf2.arrayOffset() + i7 + 4;
                i2 = n.a(bArr, i3, min, array3, arrayOffset2, this.a);
                if (i2 < min) {
                    i = 1;
                    byteBuf2.setShort(i7, i2);
                    i7 += 2;
                } else {
                    System.arraycopy(bArr, i3, array3, arrayOffset2 - 2, min);
                    i2 = min;
                    i = 0;
                }
            }
            byteBuf2.setShort(i7, min);
            byteBuf2.setByte(writerIndex + 3, (checksum != null ? 16 : 0) | i);
            byteBuf2.writerIndex(i7 + 2 + i2);
            byteBuf.skipBytes(min);
        }
    }
}
