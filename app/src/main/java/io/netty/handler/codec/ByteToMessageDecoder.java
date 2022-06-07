package io.netty.handler.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.ChannelInputShutdownEvent;
import io.netty.util.internal.StringUtil;
import java.util.List;

/* loaded from: classes4.dex */
public abstract class ByteToMessageDecoder extends ChannelInboundHandlerAdapter {
    ByteBuf b;
    private boolean c;
    private boolean d;
    private boolean e;
    private int g;
    public static final Cumulator MERGE_CUMULATOR = new Cumulator() { // from class: io.netty.handler.codec.ByteToMessageDecoder.1
        @Override // io.netty.handler.codec.ByteToMessageDecoder.Cumulator
        public ByteBuf cumulate(ByteBufAllocator byteBufAllocator, ByteBuf byteBuf, ByteBuf byteBuf2) {
            if (byteBuf.writerIndex() > byteBuf.maxCapacity() - byteBuf2.readableBytes() || byteBuf.refCnt() > 1) {
                byteBuf = ByteToMessageDecoder.a(byteBufAllocator, byteBuf, byteBuf2.readableBytes());
            }
            byteBuf.writeBytes(byteBuf2);
            byteBuf2.release();
            return byteBuf;
        }
    };
    public static final Cumulator COMPOSITE_CUMULATOR = new Cumulator() { // from class: io.netty.handler.codec.ByteToMessageDecoder.2
        @Override // io.netty.handler.codec.ByteToMessageDecoder.Cumulator
        public ByteBuf cumulate(ByteBufAllocator byteBufAllocator, ByteBuf byteBuf, ByteBuf byteBuf2) {
            CompositeByteBuf compositeByteBuf;
            if (byteBuf.refCnt() > 1) {
                ByteBuf a = ByteToMessageDecoder.a(byteBufAllocator, byteBuf, byteBuf2.readableBytes());
                a.writeBytes(byteBuf2);
                byteBuf2.release();
                return a;
            }
            if (byteBuf instanceof CompositeByteBuf) {
                compositeByteBuf = (CompositeByteBuf) byteBuf;
            } else {
                compositeByteBuf = byteBufAllocator.compositeBuffer(Integer.MAX_VALUE);
                compositeByteBuf.addComponent(true, byteBuf);
            }
            compositeByteBuf.addComponent(true, byteBuf2);
            return compositeByteBuf;
        }
    };
    private Cumulator a = MERGE_CUMULATOR;
    private int f = 16;

    /* loaded from: classes4.dex */
    public interface Cumulator {
        ByteBuf cumulate(ByteBufAllocator byteBufAllocator, ByteBuf byteBuf, ByteBuf byteBuf2);
    }

    protected abstract void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception;

    protected void handlerRemoved0(ChannelHandlerContext channelHandlerContext) throws Exception {
    }

    public ByteToMessageDecoder() {
        b.a(this);
    }

    public void setSingleDecode(boolean z) {
        this.c = z;
    }

    public boolean isSingleDecode() {
        return this.c;
    }

    public void setCumulator(Cumulator cumulator) {
        if (cumulator != null) {
            this.a = cumulator;
            return;
        }
        throw new NullPointerException("cumulator");
    }

    public void setDiscardAfterReads(int i) {
        if (i > 0) {
            this.f = i;
            return;
        }
        throw new IllegalArgumentException("discardAfterReads must be > 0");
    }

    protected int actualReadableBytes() {
        return internalBuffer().readableBytes();
    }

    protected ByteBuf internalBuffer() {
        ByteBuf byteBuf = this.b;
        return byteBuf != null ? byteBuf : Unpooled.EMPTY_BUFFER;
    }

    @Override // io.netty.channel.ChannelHandlerAdapter, io.netty.channel.ChannelHandler
    public final void handlerRemoved(ChannelHandlerContext channelHandlerContext) throws Exception {
        ByteBuf byteBuf = this.b;
        if (byteBuf != null) {
            this.b = null;
            int readableBytes = byteBuf.readableBytes();
            if (readableBytes > 0) {
                ByteBuf readBytes = byteBuf.readBytes(readableBytes);
                byteBuf.release();
                channelHandlerContext.fireChannelRead((Object) readBytes);
            } else {
                byteBuf.release();
            }
            this.g = 0;
            channelHandlerContext.fireChannelReadComplete();
        }
        handlerRemoved0(channelHandlerContext);
    }

    /* JADX WARN: Finally extract failed */
    @Override // io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelInboundHandler
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object obj) throws Exception {
        if (obj instanceof ByteBuf) {
            a a = a.a();
            try {
                try {
                    ByteBuf byteBuf = (ByteBuf) obj;
                    this.e = this.b == null;
                    if (this.e) {
                        this.b = byteBuf;
                    } else {
                        this.b = this.a.cumulate(channelHandlerContext.alloc(), this.b, byteBuf);
                    }
                    callDecode(channelHandlerContext, this.b, a);
                    ByteBuf byteBuf2 = this.b;
                    if (byteBuf2 == null || byteBuf2.isReadable()) {
                        int i = this.g + 1;
                        this.g = i;
                        if (i >= this.f) {
                            this.g = 0;
                            discardSomeReadBytes();
                        }
                    } else {
                        this.g = 0;
                        this.b.release();
                        this.b = null;
                    }
                    int size = a.size();
                    this.d = !a.b();
                    a(channelHandlerContext, a, size);
                    a.c();
                } catch (DecoderException e) {
                    throw e;
                }
            } catch (Throwable th) {
                ByteBuf byteBuf3 = this.b;
                if (byteBuf3 == null || byteBuf3.isReadable()) {
                    int i2 = this.g + 1;
                    this.g = i2;
                    if (i2 >= this.f) {
                        this.g = 0;
                        discardSomeReadBytes();
                    }
                } else {
                    this.g = 0;
                    this.b.release();
                    this.b = null;
                }
                int size2 = a.size();
                this.d = !a.b();
                a(channelHandlerContext, a, size2);
                a.c();
                throw th;
            }
        } else {
            channelHandlerContext.fireChannelRead(obj);
        }
    }

    static void a(ChannelHandlerContext channelHandlerContext, List<Object> list, int i) {
        if (list instanceof a) {
            a(channelHandlerContext, (a) list, i);
            return;
        }
        for (int i2 = 0; i2 < i; i2++) {
            channelHandlerContext.fireChannelRead(list.get(i2));
        }
    }

    static void a(ChannelHandlerContext channelHandlerContext, a aVar, int i) {
        for (int i2 = 0; i2 < i; i2++) {
            channelHandlerContext.fireChannelRead(aVar.a(i2));
        }
    }

    @Override // io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelInboundHandler
    public void channelReadComplete(ChannelHandlerContext channelHandlerContext) throws Exception {
        this.g = 0;
        discardSomeReadBytes();
        if (this.d) {
            this.d = false;
            if (!channelHandlerContext.channel().config().isAutoRead()) {
                channelHandlerContext.read();
            }
        }
        channelHandlerContext.fireChannelReadComplete();
    }

    protected final void discardSomeReadBytes() {
        ByteBuf byteBuf = this.b;
        if (byteBuf != null && !this.e && byteBuf.refCnt() == 1) {
            this.b.discardSomeReadBytes();
        }
    }

    @Override // io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelInboundHandler
    public void channelInactive(ChannelHandlerContext channelHandlerContext) throws Exception {
        a(channelHandlerContext, true);
    }

    @Override // io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelInboundHandler
    public void userEventTriggered(ChannelHandlerContext channelHandlerContext, Object obj) throws Exception {
        if (obj instanceof ChannelInputShutdownEvent) {
            a(channelHandlerContext, false);
        }
        super.userEventTriggered(channelHandlerContext, obj);
    }

    private void a(ChannelHandlerContext channelHandlerContext, boolean z) throws Exception {
        a a;
        try {
            a = a.a();
            try {
                a(channelHandlerContext, a);
                try {
                    if (this.b != null) {
                        this.b.release();
                        this.b = null;
                    }
                    int size = a.size();
                    a(channelHandlerContext, a, size);
                    if (size > 0) {
                        channelHandlerContext.fireChannelReadComplete();
                    }
                    if (z) {
                        channelHandlerContext.fireChannelInactive();
                    }
                    a.c();
                } catch (Throwable th) {
                    a.c();
                    throw th;
                }
            } catch (DecoderException e) {
                throw e;
            } catch (Exception e2) {
                throw new DecoderException(e2);
            }
        } catch (Throwable th2) {
            try {
                if (this.b != null) {
                    this.b.release();
                    this.b = null;
                }
                int size2 = a.size();
                a(channelHandlerContext, a, size2);
                if (size2 > 0) {
                    channelHandlerContext.fireChannelReadComplete();
                }
                if (z) {
                    channelHandlerContext.fireChannelInactive();
                }
                a.c();
                throw th2;
            } catch (Throwable th3) {
                a.c();
                throw th3;
            }
        }
    }

    void a(ChannelHandlerContext channelHandlerContext, List<Object> list) throws Exception {
        ByteBuf byteBuf = this.b;
        if (byteBuf != null) {
            callDecode(channelHandlerContext, byteBuf, list);
            decodeLast(channelHandlerContext, this.b, list);
            return;
        }
        decodeLast(channelHandlerContext, Unpooled.EMPTY_BUFFER, list);
    }

    protected void callDecode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) {
        while (byteBuf.isReadable()) {
            try {
                int size = list.size();
                if (size > 0) {
                    a(channelHandlerContext, list, size);
                    list.clear();
                    if (!channelHandlerContext.isRemoved()) {
                        size = 0;
                    } else {
                        return;
                    }
                }
                int readableBytes = byteBuf.readableBytes();
                decode(channelHandlerContext, byteBuf, list);
                if (!channelHandlerContext.isRemoved()) {
                    if (size == list.size()) {
                        if (readableBytes == byteBuf.readableBytes()) {
                            return;
                        }
                    } else if (readableBytes == byteBuf.readableBytes()) {
                        throw new DecoderException(StringUtil.simpleClassName(getClass()) + ".decode() did not read anything but decoded a message.");
                    } else if (isSingleDecode()) {
                        return;
                    }
                } else {
                    return;
                }
            } catch (DecoderException e) {
                throw e;
            } catch (Throwable th) {
                throw new DecoderException(th);
            }
        }
    }

    public void decodeLast(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if (byteBuf.isReadable()) {
            decode(channelHandlerContext, byteBuf, list);
        }
    }

    static ByteBuf a(ByteBufAllocator byteBufAllocator, ByteBuf byteBuf, int i) {
        ByteBuf buffer = byteBufAllocator.buffer(byteBuf.readableBytes() + i);
        buffer.writeBytes(byteBuf);
        byteBuf.release();
        return buffer;
    }
}
