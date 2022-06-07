package io.netty.handler.codec.haproxy;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.ProtocolDetectionResult;
import io.netty.util.CharsetUtil;
import java.util.List;

/* loaded from: classes4.dex */
public class HAProxyMessageDecoder extends ByteToMessageDecoder {
    private static final byte[] a = {13, 10, 13, 10, 0, 13, 10, 81, 85, 73, 84, 10};
    private static final byte[] c = {80, 82, 79, 88, 89};
    private static final int d = a.length;
    private static final ProtocolDetectionResult<HAProxyProtocolVersion> e = ProtocolDetectionResult.detected(HAProxyProtocolVersion.V1);
    private static final ProtocolDetectionResult<HAProxyProtocolVersion> f = ProtocolDetectionResult.detected(HAProxyProtocolVersion.V2);
    private boolean g;
    private int h;
    private boolean i;
    private int j;
    private final int k;

    @Override // io.netty.handler.codec.ByteToMessageDecoder
    public boolean isSingleDecode() {
        return true;
    }

    public HAProxyMessageDecoder() {
        this.j = -1;
        this.k = 65551;
    }

    public HAProxyMessageDecoder(int i) {
        this.j = -1;
        if (i < 1) {
            this.k = 232;
        } else if (i > 65319) {
            this.k = 65551;
        } else {
            int i2 = i + 232;
            if (i2 > 65551) {
                this.k = 65551;
            } else {
                this.k = i2;
            }
        }
    }

    private static int a(ByteBuf byteBuf) {
        if (byteBuf.readableBytes() < 13) {
            return -1;
        }
        int readerIndex = byteBuf.readerIndex();
        if (a(a, byteBuf, readerIndex)) {
            return byteBuf.getByte(readerIndex + d);
        }
        return 1;
    }

    private static int b(ByteBuf byteBuf) {
        int unsignedShort;
        int readableBytes = byteBuf.readableBytes();
        if (readableBytes >= 16 && readableBytes >= (unsignedShort = byteBuf.getUnsignedShort(byteBuf.readerIndex() + 14) + 16)) {
            return unsignedShort;
        }
        return -1;
    }

    private static int c(ByteBuf byteBuf) {
        int writerIndex = byteBuf.writerIndex();
        for (int readerIndex = byteBuf.readerIndex(); readerIndex < writerIndex; readerIndex++) {
            if (byteBuf.getByte(readerIndex) == 13 && readerIndex < writerIndex - 1 && byteBuf.getByte(readerIndex + 1) == 10) {
                return readerIndex;
            }
        }
        return -1;
    }

    @Override // io.netty.handler.codec.ByteToMessageDecoder, io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelInboundHandler
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object obj) throws Exception {
        super.channelRead(channelHandlerContext, obj);
        if (this.i) {
            channelHandlerContext.pipeline().remove(this);
        }
    }

    @Override // io.netty.handler.codec.ByteToMessageDecoder
    protected final void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        ByteBuf byteBuf2;
        if (this.j == -1) {
            int a2 = a(byteBuf);
            this.j = a2;
            if (a2 == -1) {
                return;
            }
        }
        if (this.j == 1) {
            byteBuf2 = b(channelHandlerContext, byteBuf);
        } else {
            byteBuf2 = a(channelHandlerContext, byteBuf);
        }
        if (byteBuf2 != null) {
            this.i = true;
            try {
                if (this.j == 1) {
                    list.add(HAProxyMessage.a(byteBuf2.toString(CharsetUtil.US_ASCII)));
                } else {
                    list.add(HAProxyMessage.a(byteBuf2));
                }
            } catch (HAProxyProtocolException e2) {
                a(channelHandlerContext, (String) null, e2);
            }
        }
    }

    private ByteBuf a(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
        int b = b(byteBuf);
        if (this.g) {
            if (b >= 0) {
                byteBuf.readerIndex(b);
                this.h = 0;
                this.g = false;
            } else {
                this.h = byteBuf.readableBytes();
                byteBuf.skipBytes(this.h);
            }
            return null;
        } else if (b >= 0) {
            int readerIndex = b - byteBuf.readerIndex();
            if (readerIndex <= this.k) {
                return byteBuf.readSlice(readerIndex);
            }
            byteBuf.readerIndex(b);
            a(channelHandlerContext, readerIndex);
            return null;
        } else {
            int readableBytes = byteBuf.readableBytes();
            if (readableBytes > this.k) {
                this.h = readableBytes;
                byteBuf.skipBytes(readableBytes);
                this.g = true;
                a(channelHandlerContext, "over " + this.h);
            }
            return null;
        }
    }

    private ByteBuf b(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
        int c2 = c(byteBuf);
        int i = 1;
        if (this.g) {
            if (c2 >= 0) {
                if (byteBuf.getByte(c2) == 13) {
                    i = 2;
                }
                byteBuf.readerIndex(c2 + i);
                this.h = 0;
                this.g = false;
            } else {
                this.h = byteBuf.readableBytes();
                byteBuf.skipBytes(this.h);
            }
            return null;
        } else if (c2 >= 0) {
            int readerIndex = c2 - byteBuf.readerIndex();
            if (readerIndex > 108) {
                byteBuf.readerIndex(c2 + 2);
                a(channelHandlerContext, readerIndex);
                return null;
            }
            ByteBuf readSlice = byteBuf.readSlice(readerIndex);
            byteBuf.skipBytes(2);
            return readSlice;
        } else {
            int readableBytes = byteBuf.readableBytes();
            if (readableBytes > 108) {
                this.h = readableBytes;
                byteBuf.skipBytes(readableBytes);
                this.g = true;
                a(channelHandlerContext, "over " + this.h);
            }
            return null;
        }
    }

    private void a(ChannelHandlerContext channelHandlerContext, int i) {
        a(channelHandlerContext, String.valueOf(i));
    }

    private void a(ChannelHandlerContext channelHandlerContext, String str) {
        int i = this.j == 1 ? 108 : this.k;
        a(channelHandlerContext, "header length (" + str + ") exceeds the allowed maximum (" + i + ')', (Throwable) null);
    }

    private void a(ChannelHandlerContext channelHandlerContext, String str, Throwable th) {
        this.i = true;
        channelHandlerContext.close();
        if (str != null && th != null) {
            throw new HAProxyProtocolException(str, th);
        }
        if (str != null) {
            throw new HAProxyProtocolException(str);
        } else if (th == null) {
        }
    }

    public static ProtocolDetectionResult<HAProxyProtocolVersion> detectProtocol(ByteBuf byteBuf) {
        if (byteBuf.readableBytes() < 12) {
            return ProtocolDetectionResult.needsMoreData();
        }
        int readerIndex = byteBuf.readerIndex();
        if (a(a, byteBuf, readerIndex)) {
            return f;
        }
        if (a(c, byteBuf, readerIndex)) {
            return e;
        }
        return ProtocolDetectionResult.invalid();
    }

    private static boolean a(byte[] bArr, ByteBuf byteBuf, int i) {
        for (int i2 = 0; i2 < bArr.length; i2++) {
            if (byteBuf.getByte(i + i2) != bArr[i2]) {
                return false;
            }
        }
        return true;
    }
}
