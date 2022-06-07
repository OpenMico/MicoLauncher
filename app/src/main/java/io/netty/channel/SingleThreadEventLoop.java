package io.netty.channel;

import io.netty.util.concurrent.SingleThreadEventExecutor;
import io.netty.util.internal.ObjectUtil;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadFactory;

/* loaded from: classes4.dex */
public abstract class SingleThreadEventLoop extends SingleThreadEventExecutor implements EventLoop {

    /* loaded from: classes4.dex */
    interface a extends Runnable {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public SingleThreadEventLoop(EventLoopGroup eventLoopGroup, ThreadFactory threadFactory, boolean z) {
        super(eventLoopGroup, threadFactory, z);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public SingleThreadEventLoop(EventLoopGroup eventLoopGroup, Executor executor, boolean z) {
        super(eventLoopGroup, executor, z);
    }

    @Override // io.netty.util.concurrent.AbstractEventExecutor, io.netty.util.concurrent.EventExecutor, io.netty.channel.EventLoop
    public EventLoopGroup parent() {
        return (EventLoopGroup) super.parent();
    }

    @Override // io.netty.util.concurrent.AbstractEventExecutor, io.netty.util.concurrent.EventExecutor, io.netty.util.concurrent.EventExecutorGroup, io.netty.channel.EventLoopGroup
    public EventLoop next() {
        return (EventLoop) super.next();
    }

    @Override // io.netty.channel.EventLoopGroup
    public ChannelFuture register(Channel channel) {
        return register(new DefaultChannelPromise(channel, this));
    }

    @Override // io.netty.channel.EventLoopGroup
    public ChannelFuture register(ChannelPromise channelPromise) {
        ObjectUtil.checkNotNull(channelPromise, "promise");
        channelPromise.channel().unsafe().register(this, channelPromise);
        return channelPromise;
    }

    @Override // io.netty.channel.EventLoopGroup
    @Deprecated
    public ChannelFuture register(Channel channel, ChannelPromise channelPromise) {
        if (channel == null) {
            throw new NullPointerException("channel");
        } else if (channelPromise != null) {
            channel.unsafe().register(this, channelPromise);
            return channelPromise;
        } else {
            throw new NullPointerException("promise");
        }
    }

    @Override // io.netty.util.concurrent.SingleThreadEventExecutor
    protected boolean wakesUpForTask(Runnable runnable) {
        return !(runnable instanceof a);
    }
}
