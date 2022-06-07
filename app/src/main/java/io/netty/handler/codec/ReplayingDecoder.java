package io.netty.handler.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.Signal;
import io.netty.util.internal.StringUtil;
import java.util.List;

/* loaded from: classes4.dex */
public abstract class ReplayingDecoder<S> extends ByteToMessageDecoder {
    static final Signal a = Signal.valueOf(ReplayingDecoder.class, "REPLAY");
    private final c c;
    private S d;
    private int e;

    public ReplayingDecoder() {
        this(null);
    }

    public ReplayingDecoder(S s) {
        this.c = new c();
        this.e = -1;
        this.d = s;
    }

    protected void checkpoint() {
        this.e = internalBuffer().readerIndex();
    }

    protected void checkpoint(S s) {
        checkpoint();
        state(s);
    }

    protected S state() {
        return this.d;
    }

    protected S state(S s) {
        S s2 = this.d;
        this.d = s;
        return s2;
    }

    @Override // io.netty.handler.codec.ByteToMessageDecoder
    final void a(ChannelHandlerContext channelHandlerContext, List<Object> list) throws Exception {
        try {
            this.c.a();
            if (this.b != null) {
                callDecode(channelHandlerContext, internalBuffer(), list);
                decodeLast(channelHandlerContext, this.c, list);
            } else {
                this.c.a(Unpooled.EMPTY_BUFFER);
                decodeLast(channelHandlerContext, this.c, list);
            }
        } catch (Signal e) {
            e.expect(a);
        }
    }

    @Override // io.netty.handler.codec.ByteToMessageDecoder
    protected void callDecode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) {
        int i;
        this.c.a(byteBuf);
        while (byteBuf.isReadable()) {
            try {
                int readerIndex = byteBuf.readerIndex();
                this.e = readerIndex;
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
                S s = this.d;
                int readableBytes = byteBuf.readableBytes();
                try {
                    decode(channelHandlerContext, this.c, list);
                    if (!channelHandlerContext.isRemoved()) {
                        if (size != list.size()) {
                            if (readerIndex == byteBuf.readerIndex() && s == this.d) {
                                throw new DecoderException(StringUtil.simpleClassName(getClass()) + ".decode() method must consume the inbound data or change its state if it decoded something.");
                            }
                            if (isSingleDecode()) {
                                return;
                            }
                        } else if (readableBytes == byteBuf.readableBytes() && s == this.d) {
                            throw new DecoderException(StringUtil.simpleClassName(getClass()) + ".decode() must consume the inbound data or change its state if it did not decode anything.");
                        }
                    } else {
                        return;
                    }
                } catch (Signal e) {
                    e.expect(a);
                    if (!channelHandlerContext.isRemoved() && (i = this.e) >= 0) {
                        byteBuf.readerIndex(i);
                        return;
                    }
                    return;
                }
            } catch (DecoderException e2) {
                throw e2;
            } catch (Throwable th) {
                throw new DecoderException(th);
            }
        }
    }
}
