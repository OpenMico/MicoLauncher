package io.netty.channel;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

@ChannelHandler.Sharable
/* loaded from: classes4.dex */
public abstract class ChannelInitializer<C extends Channel> extends ChannelInboundHandlerAdapter {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(ChannelInitializer.class);

    protected abstract void initChannel(C c) throws Exception;

    /* JADX WARN: Multi-variable type inference failed */
    @Override // io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelInboundHandler
    public final void channelRegistered(ChannelHandlerContext channelHandlerContext) throws Exception {
        initChannel(channelHandlerContext.channel());
        channelHandlerContext.pipeline().remove(this);
        channelHandlerContext.pipeline().fireChannelRegistered();
    }

    @Override // io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelHandlerAdapter, io.netty.channel.ChannelHandler, io.netty.channel.ChannelInboundHandler
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable th) throws Exception {
        InternalLogger internalLogger = logger;
        internalLogger.warn("Failed to initialize a channel. Closing: " + channelHandlerContext.channel(), th);
        try {
            ChannelPipeline pipeline = channelHandlerContext.pipeline();
            if (pipeline.context(this) != null) {
                pipeline.remove(this);
            }
        } finally {
            channelHandlerContext.close();
        }
    }
}
