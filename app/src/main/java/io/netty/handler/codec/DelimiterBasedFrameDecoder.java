package io.netty.handler.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import java.util.List;

/* loaded from: classes4.dex */
public class DelimiterBasedFrameDecoder extends ByteToMessageDecoder {
    private final ByteBuf[] a;
    private final int c;
    private final boolean d;
    private final boolean e;
    private boolean f;
    private int g;
    private final LineBasedFrameDecoder h;

    public DelimiterBasedFrameDecoder(int i, ByteBuf byteBuf) {
        this(i, true, byteBuf);
    }

    public DelimiterBasedFrameDecoder(int i, boolean z, ByteBuf byteBuf) {
        this(i, z, true, byteBuf);
    }

    public DelimiterBasedFrameDecoder(int i, boolean z, boolean z2, ByteBuf byteBuf) {
        this(i, z, z2, byteBuf.slice(byteBuf.readerIndex(), byteBuf.readableBytes()));
    }

    public DelimiterBasedFrameDecoder(int i, ByteBuf... byteBufArr) {
        this(i, true, byteBufArr);
    }

    public DelimiterBasedFrameDecoder(int i, boolean z, ByteBuf... byteBufArr) {
        this(i, z, true, byteBufArr);
    }

    public DelimiterBasedFrameDecoder(int i, boolean z, boolean z2, ByteBuf... byteBufArr) {
        a(i);
        if (byteBufArr == null) {
            throw new NullPointerException("delimiters");
        } else if (byteBufArr.length != 0) {
            if (!a(byteBufArr) || a()) {
                this.a = new ByteBuf[byteBufArr.length];
                for (int i2 = 0; i2 < byteBufArr.length; i2++) {
                    ByteBuf byteBuf = byteBufArr[i2];
                    a(byteBuf);
                    this.a[i2] = byteBuf.slice(byteBuf.readerIndex(), byteBuf.readableBytes());
                }
                this.h = null;
            } else {
                this.h = new LineBasedFrameDecoder(i, z, z2);
                this.a = null;
            }
            this.c = i;
            this.d = z;
            this.e = z2;
        } else {
            throw new IllegalArgumentException("empty delimiters");
        }
    }

    private static boolean a(ByteBuf[] byteBufArr) {
        if (byteBufArr.length != 2) {
            return false;
        }
        ByteBuf byteBuf = byteBufArr[0];
        ByteBuf byteBuf2 = byteBufArr[1];
        if (byteBuf.capacity() < byteBuf2.capacity()) {
            byteBuf = byteBufArr[1];
            byteBuf2 = byteBufArr[0];
        }
        return byteBuf.capacity() == 2 && byteBuf2.capacity() == 1 && byteBuf.getByte(0) == 13 && byteBuf.getByte(1) == 10 && byteBuf2.getByte(0) == 10;
    }

    private boolean a() {
        return getClass() != DelimiterBasedFrameDecoder.class;
    }

    @Override // io.netty.handler.codec.ByteToMessageDecoder
    protected final void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        Object decode = decode(channelHandlerContext, byteBuf);
        if (decode != null) {
            list.add(decode);
        }
    }

    protected Object decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
        LineBasedFrameDecoder lineBasedFrameDecoder = this.h;
        if (lineBasedFrameDecoder != null) {
            return lineBasedFrameDecoder.decode(channelHandlerContext, byteBuf);
        }
        int i = Integer.MAX_VALUE;
        ByteBuf[] byteBufArr = this.a;
        ByteBuf byteBuf2 = null;
        for (ByteBuf byteBuf3 : byteBufArr) {
            int a = a(byteBuf, byteBuf3);
            if (a >= 0 && a < i) {
                byteBuf2 = byteBuf3;
                i = a;
            }
        }
        if (byteBuf2 != null) {
            int capacity = byteBuf2.capacity();
            if (this.f) {
                this.f = false;
                byteBuf.skipBytes(i + capacity);
                int i2 = this.g;
                this.g = 0;
                if (!this.e) {
                    a(i2);
                }
                return null;
            } else if (i > this.c) {
                byteBuf.skipBytes(capacity + i);
                a(i);
                return null;
            } else if (!this.d) {
                return byteBuf.readRetainedSlice(i + capacity);
            } else {
                ByteBuf readRetainedSlice = byteBuf.readRetainedSlice(i);
                byteBuf.skipBytes(capacity);
                return readRetainedSlice;
            }
        } else {
            if (this.f) {
                this.g += byteBuf.readableBytes();
                byteBuf.skipBytes(byteBuf.readableBytes());
            } else if (byteBuf.readableBytes() > this.c) {
                this.g = byteBuf.readableBytes();
                byteBuf.skipBytes(byteBuf.readableBytes());
                this.f = true;
                if (this.e) {
                    a(this.g);
                }
            }
            return null;
        }
    }

    private void a(long j) {
        if (j > 0) {
            throw new TooLongFrameException("frame length exceeds " + this.c + ": " + j + " - discarded");
        }
        throw new TooLongFrameException("frame length exceeds " + this.c + " - discarding");
    }

    private static int a(ByteBuf byteBuf, ByteBuf byteBuf2) {
        for (int readerIndex = byteBuf.readerIndex(); readerIndex < byteBuf.writerIndex(); readerIndex++) {
            int i = 0;
            int i2 = readerIndex;
            while (i < byteBuf2.capacity() && byteBuf.getByte(i2) == byteBuf2.getByte(i)) {
                i2++;
                if (i2 == byteBuf.writerIndex() && i != byteBuf2.capacity() - 1) {
                    return -1;
                }
                i++;
            }
            if (i == byteBuf2.capacity()) {
                return readerIndex - byteBuf.readerIndex();
            }
        }
        return -1;
    }

    private static void a(ByteBuf byteBuf) {
        if (byteBuf == null) {
            throw new NullPointerException("delimiter");
        } else if (!byteBuf.isReadable()) {
            throw new IllegalArgumentException("empty delimiter");
        }
    }

    private static void a(int i) {
        if (i <= 0) {
            throw new IllegalArgumentException("maxFrameLength must be a positive integer: " + i);
        }
    }
}
