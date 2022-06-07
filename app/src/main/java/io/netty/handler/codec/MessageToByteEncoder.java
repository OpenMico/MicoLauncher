package io.netty.handler.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.ReferenceCounted;
import io.netty.util.internal.TypeParameterMatcher;

/* loaded from: classes4.dex */
public abstract class MessageToByteEncoder<I> extends ChannelOutboundHandlerAdapter {
    private final TypeParameterMatcher a;
    private final boolean b;

    protected abstract void encode(ChannelHandlerContext channelHandlerContext, I i, ByteBuf byteBuf) throws Exception;

    /* JADX INFO: Access modifiers changed from: protected */
    public MessageToByteEncoder() {
        this(true);
    }

    protected MessageToByteEncoder(Class<? extends I> cls) {
        this(cls, true);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public MessageToByteEncoder(boolean z) {
        this.a = TypeParameterMatcher.find(this, MessageToByteEncoder.class, "I");
        this.b = z;
    }

    protected MessageToByteEncoder(Class<? extends I> cls, boolean z) {
        this.a = TypeParameterMatcher.get(cls);
        this.b = z;
    }

    public boolean acceptOutboundMessage(Object obj) throws Exception {
        return this.a.match(obj);
    }

    /* JADX WARN: Finally extract failed */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // io.netty.channel.ChannelOutboundHandlerAdapter, io.netty.channel.ChannelOutboundHandler
    public void write(ChannelHandlerContext channelHandlerContext, Object obj, ChannelPromise channelPromise) throws Exception {
        ReferenceCounted referenceCounted;
        try {
            referenceCounted = null;
            try {
                if (acceptOutboundMessage(obj)) {
                    ByteBuf allocateBuffer = allocateBuffer(channelHandlerContext, obj, this.b);
                    try {
                        encode(channelHandlerContext, obj, allocateBuffer);
                        ReferenceCountUtil.release(obj);
                        if (allocateBuffer.isReadable()) {
                            channelHandlerContext.write(allocateBuffer, channelPromise);
                            return;
                        }
                        allocateBuffer.release();
                        channelHandlerContext.write(Unpooled.EMPTY_BUFFER, channelPromise);
                    } catch (Throwable th) {
                        ReferenceCountUtil.release(obj);
                        throw th;
                    }
                } else {
                    channelHandlerContext.write(obj, channelPromise);
                }
            } catch (EncoderException e) {
                throw e;
            } catch (Throwable th2) {
                throw new EncoderException(th2);
            }
        } catch (Throwable th3) {
            if (0 != 0) {
                referenceCounted.release();
            }
            throw th3;
        }
    }

    protected ByteBuf allocateBuffer(ChannelHandlerContext channelHandlerContext, I i, boolean z) throws Exception {
        if (z) {
            return channelHandlerContext.alloc().ioBuffer();
        }
        return channelHandlerContext.alloc().heapBuffer();
    }
}
