package io.netty.channel;

import io.netty.util.ReferenceCountUtil;
import io.netty.util.internal.TypeParameterMatcher;

/* loaded from: classes4.dex */
public abstract class SimpleChannelInboundHandler<I> extends ChannelInboundHandlerAdapter {
    private final boolean autoRelease;
    private final TypeParameterMatcher matcher;

    protected abstract void channelRead0(ChannelHandlerContext channelHandlerContext, I i) throws Exception;

    /* JADX INFO: Access modifiers changed from: protected */
    public SimpleChannelInboundHandler() {
        this(true);
    }

    protected SimpleChannelInboundHandler(boolean z) {
        this.matcher = TypeParameterMatcher.find(this, SimpleChannelInboundHandler.class, "I");
        this.autoRelease = z;
    }

    protected SimpleChannelInboundHandler(Class<? extends I> cls) {
        this(cls, true);
    }

    protected SimpleChannelInboundHandler(Class<? extends I> cls, boolean z) {
        this.matcher = TypeParameterMatcher.get(cls);
        this.autoRelease = z;
    }

    public boolean acceptInboundMessage(Object obj) throws Exception {
        return this.matcher.match(obj);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelInboundHandler
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object obj) throws Exception {
        boolean z = true;
        try {
            if (acceptInboundMessage(obj)) {
                channelRead0(channelHandlerContext, obj);
            } else {
                z = false;
                channelHandlerContext.fireChannelRead(obj);
            }
        } finally {
            if (this.autoRelease && 1 != 0) {
                ReferenceCountUtil.release(obj);
            }
        }
    }
}
