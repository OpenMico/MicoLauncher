package io.netty.handler.codec.http.websocketx;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.CorruptedFrameException;
import io.netty.handler.codec.TooLongFrameException;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.nio.ByteOrder;

/* loaded from: classes4.dex */
public class WebSocket08FrameDecoder extends ByteToMessageDecoder implements WebSocketFrameDecoder {
    private static final InternalLogger a = InternalLoggerFactory.getInstance(WebSocket08FrameDecoder.class);
    private final long c;
    private final boolean d;
    private final boolean e;
    private final boolean f;
    private int g;
    private boolean h;
    private boolean i;
    private int j;
    private int k;
    private long l;
    private byte[] m;
    private int n;
    private boolean o;
    private a p;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public enum a {
        READING_FIRST,
        READING_SECOND,
        READING_SIZE,
        MASKING_KEY,
        PAYLOAD,
        CORRUPT
    }

    public WebSocket08FrameDecoder(boolean z, boolean z2, int i) {
        this(z, z2, i, false);
    }

    public WebSocket08FrameDecoder(boolean z, boolean z2, int i, boolean z3) {
        this.p = a.READING_FIRST;
        this.e = z;
        this.f = z3;
        this.d = z2;
        this.c = i;
    }

    /* JADX WARN: Removed duplicated region for block: B:104:0x018f  */
    /* JADX WARN: Removed duplicated region for block: B:106:0x01ab  */
    /* JADX WARN: Removed duplicated region for block: B:112:0x01c8  */
    /* JADX WARN: Removed duplicated region for block: B:122:0x01eb A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:123:0x01ec  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x007f A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0080  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x014a  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x0166  */
    @Override // io.netty.handler.codec.ByteToMessageDecoder
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void decode(io.netty.channel.ChannelHandlerContext r18, io.netty.buffer.ByteBuf r19, java.util.List<java.lang.Object> r20) throws java.lang.Exception {
        /*
            Method dump skipped, instructions count: 696
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.handler.codec.http.websocketx.WebSocket08FrameDecoder.decode(io.netty.channel.ChannelHandlerContext, io.netty.buffer.ByteBuf, java.util.List):void");
    }

    private void a(ByteBuf byteBuf) {
        int readerIndex = byteBuf.readerIndex();
        int writerIndex = byteBuf.writerIndex();
        ByteOrder order = byteBuf.order();
        byte[] bArr = this.m;
        int i = (bArr[3] & 255) | ((bArr[0] & 255) << 24) | ((bArr[1] & 255) << 16) | ((bArr[2] & 255) << 8);
        if (order == ByteOrder.LITTLE_ENDIAN) {
            i = Integer.reverseBytes(i);
        }
        while (readerIndex + 3 < writerIndex) {
            byteBuf.setInt(readerIndex, byteBuf.getInt(readerIndex) ^ i);
            readerIndex += 4;
        }
        while (readerIndex < writerIndex) {
            byteBuf.setByte(readerIndex, byteBuf.getByte(readerIndex) ^ this.m[readerIndex % 4]);
            readerIndex++;
        }
    }

    private void a(ChannelHandlerContext channelHandlerContext, String str) {
        a(channelHandlerContext, new CorruptedFrameException(str));
    }

    private void a(ChannelHandlerContext channelHandlerContext, CorruptedFrameException corruptedFrameException) {
        Object obj;
        this.p = a.CORRUPT;
        if (channelHandlerContext.channel().isActive()) {
            if (this.o) {
                obj = Unpooled.EMPTY_BUFFER;
            } else {
                obj = new CloseWebSocketFrame(1002, (String) null);
            }
            channelHandlerContext.writeAndFlush(obj).addListener((GenericFutureListener<? extends Future<? super Void>>) ChannelFutureListener.CLOSE);
        }
        throw corruptedFrameException;
    }

    private static int a(long j) {
        if (j <= 2147483647L) {
            return (int) j;
        }
        throw new TooLongFrameException("Length:" + j);
    }

    protected void checkCloseFrameBody(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) {
        if (byteBuf != null && byteBuf.isReadable()) {
            if (byteBuf.readableBytes() == 1) {
                a(channelHandlerContext, "Invalid close frame body");
            }
            int readerIndex = byteBuf.readerIndex();
            byteBuf.readerIndex(0);
            short readShort = byteBuf.readShort();
            if ((readShort >= 0 && readShort <= 999) || ((readShort >= 1004 && readShort <= 1006) || (readShort >= 1012 && readShort <= 2999))) {
                a(channelHandlerContext, "Invalid close frame getStatus code: " + ((int) readShort));
            }
            if (byteBuf.isReadable()) {
                try {
                    new a().a(byteBuf);
                } catch (CorruptedFrameException e) {
                    a(channelHandlerContext, e);
                }
            }
            byteBuf.readerIndex(readerIndex);
        }
    }
}
