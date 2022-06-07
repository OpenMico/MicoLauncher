package io.netty.channel.embedded;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelPromise;
import io.netty.channel.DefaultChannelPromise;
import io.netty.channel.EventLoop;
import io.netty.channel.EventLoopGroup;
import io.netty.util.concurrent.AbstractScheduledEventExecutor;
import io.netty.util.concurrent.Future;
import io.netty.util.internal.ObjectUtil;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

/* compiled from: EmbeddedEventLoop.java */
/* loaded from: classes4.dex */
final class b extends AbstractScheduledEventExecutor implements EventLoop {
    private final Queue<Runnable> a = new ArrayDeque(2);

    @Override // java.util.concurrent.ExecutorService
    public boolean awaitTermination(long j, TimeUnit timeUnit) {
        return false;
    }

    @Override // io.netty.util.concurrent.AbstractEventExecutor, io.netty.util.concurrent.EventExecutor
    public boolean inEventLoop() {
        return true;
    }

    @Override // io.netty.util.concurrent.EventExecutor
    public boolean inEventLoop(Thread thread) {
        return true;
    }

    @Override // java.util.concurrent.ExecutorService
    public boolean isShutdown() {
        return false;
    }

    @Override // io.netty.util.concurrent.EventExecutorGroup
    public boolean isShuttingDown() {
        return false;
    }

    @Override // java.util.concurrent.ExecutorService
    public boolean isTerminated() {
        return false;
    }

    @Override // io.netty.util.concurrent.AbstractEventExecutor, io.netty.util.concurrent.EventExecutor, io.netty.channel.EventLoop
    public EventLoopGroup parent() {
        return (EventLoopGroup) super.parent();
    }

    @Override // io.netty.util.concurrent.AbstractEventExecutor, io.netty.util.concurrent.EventExecutor, io.netty.util.concurrent.EventExecutorGroup, io.netty.channel.EventLoopGroup
    public EventLoop next() {
        return (EventLoop) super.next();
    }

    @Override // java.util.concurrent.Executor
    public void execute(Runnable runnable) {
        if (runnable != null) {
            this.a.add(runnable);
            return;
        }
        throw new NullPointerException("command");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a() {
        while (true) {
            Runnable poll = this.a.poll();
            if (poll != null) {
                poll.run();
            } else {
                return;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public long b() {
        long nanoTime = AbstractScheduledEventExecutor.nanoTime();
        while (true) {
            Runnable pollScheduledTask = pollScheduledTask(nanoTime);
            if (pollScheduledTask == null) {
                return nextScheduledTaskNano();
            }
            pollScheduledTask.run();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public long c() {
        return nextScheduledTaskNano();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.util.concurrent.AbstractScheduledEventExecutor
    public void cancelScheduledTasks() {
        super.cancelScheduledTasks();
    }

    @Override // io.netty.util.concurrent.EventExecutorGroup
    public Future<?> shutdownGracefully(long j, long j2, TimeUnit timeUnit) {
        throw new UnsupportedOperationException();
    }

    @Override // io.netty.util.concurrent.EventExecutorGroup
    public Future<?> terminationFuture() {
        throw new UnsupportedOperationException();
    }

    @Override // io.netty.util.concurrent.AbstractEventExecutor, java.util.concurrent.ExecutorService, io.netty.util.concurrent.EventExecutorGroup
    @Deprecated
    public void shutdown() {
        throw new UnsupportedOperationException();
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
        channel.unsafe().register(this, channelPromise);
        return channelPromise;
    }
}
