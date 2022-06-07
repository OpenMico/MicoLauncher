package io.netty.handler.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import java.nio.ByteOrder;
import java.util.List;

/* loaded from: classes4.dex */
public class LengthFieldBasedFrameDecoder extends ByteToMessageDecoder {
    private final ByteOrder a;
    private final int c;
    private final int d;
    private final int e;
    private final int f;
    private final int g;
    private final int h;
    private final boolean i;
    private boolean j;
    private long k;
    private long l;

    public LengthFieldBasedFrameDecoder(int i, int i2, int i3) {
        this(i, i2, i3, 0, 0);
    }

    public LengthFieldBasedFrameDecoder(int i, int i2, int i3, int i4, int i5) {
        this(i, i2, i3, i4, i5, true);
    }

    public LengthFieldBasedFrameDecoder(int i, int i2, int i3, int i4, int i5, boolean z) {
        this(ByteOrder.BIG_ENDIAN, i, i2, i3, i4, i5, z);
    }

    public LengthFieldBasedFrameDecoder(ByteOrder byteOrder, int i, int i2, int i3, int i4, int i5, boolean z) {
        if (byteOrder == null) {
            throw new NullPointerException("byteOrder");
        } else if (i <= 0) {
            throw new IllegalArgumentException("maxFrameLength must be a positive integer: " + i);
        } else if (i2 < 0) {
            throw new IllegalArgumentException("lengthFieldOffset must be a non-negative integer: " + i2);
        } else if (i5 < 0) {
            throw new IllegalArgumentException("initialBytesToStrip must be a non-negative integer: " + i5);
        } else if (i2 <= i - i3) {
            this.a = byteOrder;
            this.c = i;
            this.d = i2;
            this.e = i3;
            this.g = i4;
            this.f = i2 + i3;
            this.h = i5;
            this.i = z;
        } else {
            throw new IllegalArgumentException("maxFrameLength (" + i + ") must be equal to or greater than lengthFieldOffset (" + i2 + ") + lengthFieldLength (" + i3 + ").");
        }
    }

    @Override // io.netty.handler.codec.ByteToMessageDecoder
    protected final void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        Object decode = decode(channelHandlerContext, byteBuf);
        if (decode != null) {
            list.add(decode);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public Object decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
        if (this.j) {
            long j = this.l;
            int min = (int) Math.min(j, byteBuf.readableBytes());
            byteBuf.skipBytes(min);
            this.l = j - min;
            a(false);
        }
        if (byteBuf.readableBytes() < this.f) {
            return null;
        }
        long unadjustedFrameLength = getUnadjustedFrameLength(byteBuf, byteBuf.readerIndex() + this.d, this.e, this.a);
        if (unadjustedFrameLength >= 0) {
            int i = this.g;
            int i2 = this.f;
            long j2 = unadjustedFrameLength + i + i2;
            if (j2 < i2) {
                byteBuf.skipBytes(i2);
                throw new CorruptedFrameException("Adjusted frame length (" + j2 + ") is less than lengthFieldEndOffset: " + this.f);
            } else if (j2 > this.c) {
                long readableBytes = j2 - byteBuf.readableBytes();
                this.k = j2;
                if (readableBytes < 0) {
                    byteBuf.skipBytes((int) j2);
                } else {
                    this.j = true;
                    this.l = readableBytes;
                    byteBuf.skipBytes(byteBuf.readableBytes());
                }
                a(true);
                return null;
            } else {
                int i3 = (int) j2;
                if (byteBuf.readableBytes() < i3) {
                    return null;
                }
                int i4 = this.h;
                if (i4 <= i3) {
                    byteBuf.skipBytes(i4);
                    int readerIndex = byteBuf.readerIndex();
                    int i5 = i3 - this.h;
                    ByteBuf extractFrame = extractFrame(channelHandlerContext, byteBuf, readerIndex, i5);
                    byteBuf.readerIndex(readerIndex + i5);
                    return extractFrame;
                }
                byteBuf.skipBytes(i3);
                throw new CorruptedFrameException("Adjusted frame length (" + j2 + ") is less than initialBytesToStrip: " + this.h);
            }
        } else {
            byteBuf.skipBytes(this.f);
            throw new CorruptedFrameException("negative pre-adjustment length field: " + unadjustedFrameLength);
        }
    }

    protected long getUnadjustedFrameLength(ByteBuf byteBuf, int i, int i2, ByteOrder byteOrder) {
        ByteBuf order = byteBuf.order(byteOrder);
        if (i2 == 8) {
            return order.getLong(i);
        }
        switch (i2) {
            case 1:
                return order.getUnsignedByte(i);
            case 2:
                return order.getUnsignedShort(i);
            case 3:
                return order.getUnsignedMedium(i);
            case 4:
                return order.getUnsignedInt(i);
            default:
                throw new DecoderException("unsupported lengthFieldLength: " + this.e + " (expected: 1, 2, 3, 4, or 8)");
        }
    }

    private void a(boolean z) {
        if (this.l == 0) {
            long j = this.k;
            this.k = 0L;
            this.j = false;
            boolean z2 = this.i;
            if (!z2 || (z2 && z)) {
                a(j);
            }
        } else if (this.i && z) {
            a(this.k);
        }
    }

    protected ByteBuf extractFrame(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, int i, int i2) {
        return byteBuf.retainedSlice(i, i2);
    }

    private void a(long j) {
        if (j > 0) {
            throw new TooLongFrameException("Adjusted frame length exceeds " + this.c + ": " + j + " - discarded");
        }
        throw new TooLongFrameException("Adjusted frame length exceeds " + this.c + " - discarding");
    }
}
