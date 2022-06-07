package io.netty.channel;

import io.netty.util.concurrent.DefaultThreadFactory;
import io.netty.util.concurrent.EventExecutorChooserFactory;
import io.netty.util.concurrent.MultithreadEventExecutorGroup;
import io.netty.util.internal.SystemPropertyUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadFactory;

/* loaded from: classes4.dex */
public abstract class MultithreadEventLoopGroup extends MultithreadEventExecutorGroup implements EventLoopGroup {
    private static final InternalLogger a = InternalLoggerFactory.getInstance(MultithreadEventLoopGroup.class);
    private static final int b = Math.max(1, SystemPropertyUtil.getInt("io.netty.eventLoopThreads", Runtime.getRuntime().availableProcessors() * 2));

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.util.concurrent.MultithreadEventExecutorGroup
    public abstract EventLoop newChild(Executor executor, Object... objArr) throws Exception;

    static {
        if (a.isDebugEnabled()) {
            a.debug("-Dio.netty.eventLoopThreads: {}", Integer.valueOf(b));
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public MultithreadEventLoopGroup(int i, Executor executor, Object... objArr) {
        super(i == 0 ? b : i, executor, objArr);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public MultithreadEventLoopGroup(int i, ThreadFactory threadFactory, Object... objArr) {
        super(i == 0 ? b : i, threadFactory, objArr);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public MultithreadEventLoopGroup(int i, Executor executor, EventExecutorChooserFactory eventExecutorChooserFactory, Object... objArr) {
        super(i == 0 ? b : i, executor, eventExecutorChooserFactory, objArr);
    }

    @Override // io.netty.util.concurrent.MultithreadEventExecutorGroup
    protected ThreadFactory newDefaultThreadFactory() {
        return new DefaultThreadFactory(getClass(), 10);
    }

    @Override // io.netty.util.concurrent.MultithreadEventExecutorGroup, io.netty.util.concurrent.EventExecutorGroup, io.netty.channel.EventLoopGroup
    public EventLoop next() {
        return (EventLoop) super.next();
    }

    @Override // io.netty.channel.EventLoopGroup
    public ChannelFuture register(Channel channel) {
        return next().register(channel);
    }

    @Override // io.netty.channel.EventLoopGroup
    public ChannelFuture register(ChannelPromise channelPromise) {
        return next().register(channelPromise);
    }

    @Override // io.netty.channel.EventLoopGroup
    @Deprecated
    public ChannelFuture register(Channel channel, ChannelPromise channelPromise) {
        return next().register(channel, channelPromise);
    }
}
