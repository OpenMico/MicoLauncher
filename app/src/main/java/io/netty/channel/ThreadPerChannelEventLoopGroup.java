package io.netty.channel;

import io.netty.util.concurrent.AbstractEventExecutorGroup;
import io.netty.util.concurrent.DefaultPromise;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.FutureListener;
import io.netty.util.concurrent.GlobalEventExecutor;
import io.netty.util.concurrent.Promise;
import io.netty.util.concurrent.ThreadPerTaskExecutor;
import io.netty.util.internal.EmptyArrays;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.ReadOnlyIterator;
import java.util.Collections;
import java.util.Iterator;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class ThreadPerChannelEventLoopGroup extends AbstractEventExecutorGroup implements EventLoopGroup {
    final Executor a;
    final Set<EventLoop> b;
    final Queue<EventLoop> c;
    private final Object[] d;
    private final int e;
    private final ChannelException f;
    private volatile boolean g;
    private final Promise<?> h;
    private final FutureListener<Object> i;

    protected ThreadPerChannelEventLoopGroup() {
        this(0);
    }

    protected ThreadPerChannelEventLoopGroup(int i) {
        this(i, Executors.defaultThreadFactory(), new Object[0]);
    }

    public ThreadPerChannelEventLoopGroup(int i, ThreadFactory threadFactory, Object... objArr) {
        this(i, new ThreadPerTaskExecutor(threadFactory), objArr);
    }

    public ThreadPerChannelEventLoopGroup(int i, Executor executor, Object... objArr) {
        this.b = Collections.newSetFromMap(PlatformDependent.newConcurrentHashMap());
        this.c = new ConcurrentLinkedQueue();
        this.h = new DefaultPromise(GlobalEventExecutor.INSTANCE);
        this.i = new FutureListener<Object>() { // from class: io.netty.channel.ThreadPerChannelEventLoopGroup.1
            @Override // io.netty.util.concurrent.GenericFutureListener
            public void operationComplete(Future<Object> future) throws Exception {
                if (ThreadPerChannelEventLoopGroup.this.isTerminated()) {
                    ThreadPerChannelEventLoopGroup.this.h.trySuccess(null);
                }
            }
        };
        if (i < 0) {
            throw new IllegalArgumentException(String.format("maxChannels: %d (expected: >= 0)", Integer.valueOf(i)));
        } else if (executor != null) {
            if (objArr == null) {
                this.d = EmptyArrays.EMPTY_OBJECTS;
            } else {
                this.d = (Object[]) objArr.clone();
            }
            this.e = i;
            this.a = executor;
            this.f = new ChannelException("too many channels (max: " + i + ')');
            this.f.setStackTrace(EmptyArrays.EMPTY_STACK_TRACE);
        } else {
            throw new NullPointerException("executor");
        }
    }

    protected EventLoop newChild(Object... objArr) throws Exception {
        return new ThreadPerChannelEventLoop(this);
    }

    @Override // io.netty.util.concurrent.EventExecutorGroup, java.lang.Iterable
    public Iterator<EventExecutor> iterator() {
        return new ReadOnlyIterator(this.b.iterator());
    }

    @Override // io.netty.util.concurrent.EventExecutorGroup, io.netty.channel.EventLoopGroup
    public EventLoop next() {
        throw new UnsupportedOperationException();
    }

    @Override // io.netty.util.concurrent.EventExecutorGroup
    public Future<?> shutdownGracefully(long j, long j2, TimeUnit timeUnit) {
        this.g = true;
        for (EventLoop eventLoop : this.b) {
            eventLoop.shutdownGracefully(j, j2, timeUnit);
        }
        for (EventLoop eventLoop2 : this.c) {
            eventLoop2.shutdownGracefully(j, j2, timeUnit);
        }
        if (isTerminated()) {
            this.h.trySuccess(null);
        }
        return terminationFuture();
    }

    @Override // io.netty.util.concurrent.EventExecutorGroup
    public Future<?> terminationFuture() {
        return this.h;
    }

    @Override // io.netty.util.concurrent.AbstractEventExecutorGroup, io.netty.util.concurrent.EventExecutorGroup, java.util.concurrent.ExecutorService
    @Deprecated
    public void shutdown() {
        this.g = true;
        for (EventLoop eventLoop : this.b) {
            eventLoop.shutdown();
        }
        for (EventLoop eventLoop2 : this.c) {
            eventLoop2.shutdown();
        }
        if (isTerminated()) {
            this.h.trySuccess(null);
        }
    }

    @Override // io.netty.util.concurrent.EventExecutorGroup
    public boolean isShuttingDown() {
        for (EventLoop eventLoop : this.b) {
            if (!eventLoop.isShuttingDown()) {
                return false;
            }
        }
        for (EventLoop eventLoop2 : this.c) {
            if (!eventLoop2.isShuttingDown()) {
                return false;
            }
        }
        return true;
    }

    @Override // java.util.concurrent.ExecutorService
    public boolean isShutdown() {
        for (EventLoop eventLoop : this.b) {
            if (!eventLoop.isShutdown()) {
                return false;
            }
        }
        for (EventLoop eventLoop2 : this.c) {
            if (!eventLoop2.isShutdown()) {
                return false;
            }
        }
        return true;
    }

    @Override // java.util.concurrent.ExecutorService
    public boolean isTerminated() {
        for (EventLoop eventLoop : this.b) {
            if (!eventLoop.isTerminated()) {
                return false;
            }
        }
        for (EventLoop eventLoop2 : this.c) {
            if (!eventLoop2.isTerminated()) {
                return false;
            }
        }
        return true;
    }

    @Override // java.util.concurrent.ExecutorService
    public boolean awaitTermination(long j, TimeUnit timeUnit) throws InterruptedException {
        long nanoTime;
        long nanoTime2;
        long nanoTime3 = System.nanoTime() + timeUnit.toNanos(j);
        for (EventLoop eventLoop : this.b) {
            do {
                nanoTime2 = nanoTime3 - System.nanoTime();
                if (nanoTime2 <= 0) {
                    return isTerminated();
                }
            } while (!eventLoop.awaitTermination(nanoTime2, TimeUnit.NANOSECONDS));
        }
        for (EventLoop eventLoop2 : this.c) {
            do {
                nanoTime = nanoTime3 - System.nanoTime();
                if (nanoTime <= 0) {
                    return isTerminated();
                }
            } while (!eventLoop2.awaitTermination(nanoTime, TimeUnit.NANOSECONDS));
        }
        return isTerminated();
    }

    @Override // io.netty.channel.EventLoopGroup
    public ChannelFuture register(Channel channel) {
        if (channel != null) {
            try {
                EventLoop a = a();
                return a.register(new DefaultChannelPromise(channel, a));
            } catch (Throwable th) {
                return new e(channel, GlobalEventExecutor.INSTANCE, th);
            }
        } else {
            throw new NullPointerException("channel");
        }
    }

    @Override // io.netty.channel.EventLoopGroup
    public ChannelFuture register(ChannelPromise channelPromise) {
        try {
            return a().register(channelPromise);
        } catch (Throwable th) {
            channelPromise.setFailure(th);
            return channelPromise;
        }
    }

    @Override // io.netty.channel.EventLoopGroup
    @Deprecated
    public ChannelFuture register(Channel channel, ChannelPromise channelPromise) {
        if (channel != null) {
            try {
                return a().register(channel, channelPromise);
            } catch (Throwable th) {
                channelPromise.setFailure(th);
                return channelPromise;
            }
        } else {
            throw new NullPointerException("channel");
        }
    }

    private EventLoop a() throws Exception {
        if (!this.g) {
            EventLoop poll = this.c.poll();
            if (poll == null) {
                if (this.e <= 0 || this.b.size() < this.e) {
                    poll = newChild(this.d);
                    poll.terminationFuture().addListener(this.i);
                } else {
                    throw this.f;
                }
            }
            this.b.add(poll);
            return poll;
        }
        throw new RejectedExecutionException("shutting down");
    }
}
