package io.netty.channel;

import io.netty.util.concurrent.EventExecutor;

/* compiled from: DefaultChannelHandlerContext.java */
/* loaded from: classes4.dex */
final class c extends a {
    private final ChannelHandler d;

    /* JADX INFO: Access modifiers changed from: package-private */
    public c(DefaultChannelPipeline defaultChannelPipeline, EventExecutor eventExecutor, String str, ChannelHandler channelHandler) {
        super(defaultChannelPipeline, eventExecutor, str, a(channelHandler), b(channelHandler));
        if (channelHandler != null) {
            this.d = channelHandler;
            return;
        }
        throw new NullPointerException("handler");
    }

    @Override // io.netty.channel.ChannelHandlerContext
    public ChannelHandler handler() {
        return this.d;
    }

    private static boolean a(ChannelHandler channelHandler) {
        return channelHandler instanceof ChannelInboundHandler;
    }

    private static boolean b(ChannelHandler channelHandler) {
        return channelHandler instanceof ChannelOutboundHandler;
    }
}
