package io.netty.channel;

import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

/* loaded from: classes4.dex */
public class ThreadPerChannelEventLoop extends SingleThreadEventLoop {
    private final ThreadPerChannelEventLoopGroup a;
    private Channel e;

    public ThreadPerChannelEventLoop(ThreadPerChannelEventLoopGroup threadPerChannelEventLoopGroup) {
        super((EventLoopGroup) threadPerChannelEventLoopGroup, threadPerChannelEventLoopGroup.a, true);
        this.a = threadPerChannelEventLoopGroup;
    }

    /* JADX WARN: Type inference failed for: r2v2, types: [io.netty.channel.ChannelFuture] */
    @Override // io.netty.channel.SingleThreadEventLoop, io.netty.channel.EventLoopGroup
    public ChannelFuture register(ChannelPromise channelPromise) {
        return super.register(channelPromise).addListener((GenericFutureListener<? extends Future<? super Void>>) new ChannelFutureListener() { // from class: io.netty.channel.ThreadPerChannelEventLoop.1
            /* renamed from: a */
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if (channelFuture.isSuccess()) {
                    ThreadPerChannelEventLoop.this.e = channelFuture.channel();
                    return;
                }
                ThreadPerChannelEventLoop.this.deregister();
            }
        });
    }

    /* JADX WARN: Type inference failed for: r1v2, types: [io.netty.channel.ChannelFuture] */
    @Override // io.netty.channel.SingleThreadEventLoop, io.netty.channel.EventLoopGroup
    @Deprecated
    public ChannelFuture register(Channel channel, ChannelPromise channelPromise) {
        return super.register(channel, channelPromise).addListener((GenericFutureListener<? extends Future<? super Void>>) new ChannelFutureListener() { // from class: io.netty.channel.ThreadPerChannelEventLoop.2
            /* renamed from: a */
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if (channelFuture.isSuccess()) {
                    ThreadPerChannelEventLoop.this.e = channelFuture.channel();
                    return;
                }
                ThreadPerChannelEventLoop.this.deregister();
            }
        });
    }

    @Override // io.netty.util.concurrent.SingleThreadEventExecutor
    protected void run() {
        while (true) {
            Runnable takeTask = takeTask();
            if (takeTask != null) {
                takeTask.run();
                updateLastExecutionTime();
            }
            Channel channel = this.e;
            if (isShuttingDown()) {
                if (channel != null) {
                    channel.unsafe().close(channel.unsafe().voidPromise());
                }
                if (confirmShutdown()) {
                    return;
                }
            } else if (channel != null && !channel.isRegistered()) {
                runAllTasks();
                deregister();
            }
        }
    }

    protected void deregister() {
        this.e = null;
        this.a.b.remove(this);
        this.a.c.add(this);
    }
}
