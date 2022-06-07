package io.netty.handler.codec;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.internal.TypeParameterMatcher;
import java.util.List;

/* loaded from: classes4.dex */
public abstract class MessageToMessageDecoder<I> extends ChannelInboundHandlerAdapter {
    private final TypeParameterMatcher a;

    /* JADX INFO: Access modifiers changed from: protected */
    public abstract void decode(ChannelHandlerContext channelHandlerContext, I i, List<Object> list) throws Exception;

    /* JADX INFO: Access modifiers changed from: protected */
    public MessageToMessageDecoder() {
        this.a = TypeParameterMatcher.find(this, MessageToMessageDecoder.class, "I");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public MessageToMessageDecoder(Class<? extends I> cls) {
        this.a = TypeParameterMatcher.get(cls);
    }

    public boolean acceptInboundMessage(Object obj) throws Exception {
        return this.a.match(obj);
    }

    /* JADX WARN: Finally extract failed */
    @Override // io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelInboundHandler
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object obj) throws Exception {
        int i;
        a a;
        try {
            a = a.a();
            i = 0;
            try {
                if (acceptInboundMessage(obj)) {
                    try {
                        decode(channelHandlerContext, obj, a);
                        ReferenceCountUtil.release(obj);
                    } catch (Throwable th) {
                        ReferenceCountUtil.release(obj);
                        throw th;
                    }
                } else {
                    a.add(obj);
                }
            } catch (DecoderException e) {
                throw e;
            } catch (Exception e2) {
                throw new DecoderException(e2);
            }
        } finally {
            int size = a.size();
            while (i < size) {
                channelHandlerContext.fireChannelRead(a.a(i));
                i++;
            }
            a.c();
        }
    }
}
