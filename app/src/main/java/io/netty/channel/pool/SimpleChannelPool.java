package io.netty.channel.pool;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoop;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.FutureListener;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.Promise;
import io.netty.util.internal.EmptyArrays;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.OneTimeTask;
import io.netty.util.internal.PlatformDependent;
import java.util.Deque;

/* loaded from: classes4.dex */
public class SimpleChannelPool implements ChannelPool {
    private final Deque<Channel> e;
    private final ChannelPoolHandler f;
    private final ChannelHealthChecker g;
    private final Bootstrap h;
    private final boolean i;
    static final /* synthetic */ boolean b = !SimpleChannelPool.class.desiredAssertionStatus();
    private static final AttributeKey<SimpleChannelPool> a = AttributeKey.newInstance("channelPool");
    private static final IllegalStateException c = new IllegalStateException("ChannelPool full");
    private static final IllegalStateException d = new IllegalStateException("Channel is unhealthy not offering it back to pool");

    static {
        c.setStackTrace(EmptyArrays.EMPTY_STACK_TRACE);
        d.setStackTrace(EmptyArrays.EMPTY_STACK_TRACE);
    }

    public SimpleChannelPool(Bootstrap bootstrap, ChannelPoolHandler channelPoolHandler) {
        this(bootstrap, channelPoolHandler, ChannelHealthChecker.ACTIVE);
    }

    public SimpleChannelPool(Bootstrap bootstrap, ChannelPoolHandler channelPoolHandler, ChannelHealthChecker channelHealthChecker) {
        this(bootstrap, channelPoolHandler, channelHealthChecker, true);
    }

    public SimpleChannelPool(Bootstrap bootstrap, final ChannelPoolHandler channelPoolHandler, ChannelHealthChecker channelHealthChecker, boolean z) {
        this.e = PlatformDependent.newConcurrentDeque();
        this.f = (ChannelPoolHandler) ObjectUtil.checkNotNull(channelPoolHandler, "handler");
        this.g = (ChannelHealthChecker) ObjectUtil.checkNotNull(channelHealthChecker, "healthCheck");
        this.i = z;
        this.h = ((Bootstrap) ObjectUtil.checkNotNull(bootstrap, "bootstrap")).clone();
        this.h.handler(new ChannelInitializer<Channel>() { // from class: io.netty.channel.pool.SimpleChannelPool.1
            static final /* synthetic */ boolean a = !SimpleChannelPool.class.desiredAssertionStatus();

            @Override // io.netty.channel.ChannelInitializer
            protected void initChannel(Channel channel) throws Exception {
                if (a || channel.eventLoop().inEventLoop()) {
                    channelPoolHandler.channelCreated(channel);
                    return;
                }
                throw new AssertionError();
            }
        });
    }

    @Override // io.netty.channel.pool.ChannelPool
    public final Future<Channel> acquire() {
        return acquire(this.h.group().next().newPromise());
    }

    @Override // io.netty.channel.pool.ChannelPool
    public Future<Channel> acquire(Promise<Channel> promise) {
        ObjectUtil.checkNotNull(promise, "promise");
        return a(promise);
    }

    private Future<Channel> a(final Promise<Channel> promise) {
        final Channel pollChannel;
        try {
            pollChannel = pollChannel();
        } catch (Throwable th) {
            promise.setFailure(th);
        }
        if (pollChannel == null) {
            Bootstrap clone = this.h.clone();
            clone.attr(a, this);
            ChannelFuture connectChannel = connectChannel(clone);
            if (connectChannel.isDone()) {
                b(connectChannel, promise);
            } else {
                connectChannel.addListener((GenericFutureListener<? extends Future<? super Void>>) new ChannelFutureListener() { // from class: io.netty.channel.pool.SimpleChannelPool.2
                    /* renamed from: a */
                    public void operationComplete(ChannelFuture channelFuture) throws Exception {
                        SimpleChannelPool.b(channelFuture, promise);
                    }
                });
            }
            return promise;
        }
        EventLoop eventLoop = pollChannel.eventLoop();
        if (eventLoop.inEventLoop()) {
            a(pollChannel, promise);
        } else {
            eventLoop.execute(new OneTimeTask() { // from class: io.netty.channel.pool.SimpleChannelPool.3
                @Override // java.lang.Runnable
                public void run() {
                    SimpleChannelPool.this.a(pollChannel, promise);
                }
            });
        }
        return promise;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void b(ChannelFuture channelFuture, Promise<Channel> promise) {
        if (channelFuture.isSuccess()) {
            promise.setSuccess(channelFuture.channel());
        } else {
            promise.setFailure(channelFuture.cause());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(final Channel channel, final Promise<Channel> promise) {
        if (b || channel.eventLoop().inEventLoop()) {
            Future<Boolean> isHealthy = this.g.isHealthy(channel);
            if (isHealthy.isDone()) {
                a(isHealthy, channel, promise);
            } else {
                isHealthy.addListener(new FutureListener<Boolean>() { // from class: io.netty.channel.pool.SimpleChannelPool.4
                    @Override // io.netty.util.concurrent.GenericFutureListener
                    public void operationComplete(Future<Boolean> future) throws Exception {
                        SimpleChannelPool.this.a(future, channel, promise);
                    }
                });
            }
        } else {
            throw new AssertionError();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Future<Boolean> future, Channel channel, Promise<Channel> promise) {
        if (!b && !channel.eventLoop().inEventLoop()) {
            throw new AssertionError();
        } else if (!future.isSuccess()) {
            a(channel);
            a(promise);
        } else if (future.getNow().booleanValue()) {
            try {
                channel.attr(a).set(this);
                this.f.channelAcquired(channel);
                promise.setSuccess(channel);
            } catch (Throwable th) {
                a(channel, th, promise);
            }
        } else {
            a(channel);
            a(promise);
        }
    }

    protected ChannelFuture connectChannel(Bootstrap bootstrap) {
        return bootstrap.connect();
    }

    @Override // io.netty.channel.pool.ChannelPool
    public final Future<Void> release(Channel channel) {
        return release(channel, channel.eventLoop().newPromise());
    }

    @Override // io.netty.channel.pool.ChannelPool
    public Future<Void> release(final Channel channel, final Promise<Void> promise) {
        ObjectUtil.checkNotNull(channel, "channel");
        ObjectUtil.checkNotNull(promise, "promise");
        try {
            EventLoop eventLoop = channel.eventLoop();
            if (eventLoop.inEventLoop()) {
                b(channel, promise);
            } else {
                eventLoop.execute(new OneTimeTask() { // from class: io.netty.channel.pool.SimpleChannelPool.5
                    @Override // java.lang.Runnable
                    public void run() {
                        SimpleChannelPool.this.b(channel, promise);
                    }
                });
            }
        } catch (Throwable th) {
            a(channel, th, promise);
        }
        return promise;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(Channel channel, Promise<Void> promise) {
        if (!b && !channel.eventLoop().inEventLoop()) {
            throw new AssertionError();
        } else if (channel.attr(a).getAndSet(null) != this) {
            a(channel, new IllegalArgumentException("Channel " + channel + " was not acquired from this ChannelPool"), promise);
        } else {
            try {
                if (this.i) {
                    c(channel, promise);
                } else {
                    d(channel, promise);
                }
            } catch (Throwable th) {
                a(channel, th, promise);
            }
        }
    }

    private void c(final Channel channel, final Promise<Void> promise) throws Exception {
        final Future<Boolean> isHealthy = this.g.isHealthy(channel);
        if (isHealthy.isDone()) {
            a(channel, promise, isHealthy);
        } else {
            isHealthy.addListener(new FutureListener<Boolean>() { // from class: io.netty.channel.pool.SimpleChannelPool.6
                @Override // io.netty.util.concurrent.GenericFutureListener
                public void operationComplete(Future<Boolean> future) throws Exception {
                    SimpleChannelPool.this.a(channel, promise, isHealthy);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Channel channel, Promise<Void> promise, Future<Boolean> future) throws Exception {
        if (future.getNow().booleanValue()) {
            d(channel, promise);
            return;
        }
        this.f.channelReleased(channel);
        a(channel, d, promise);
    }

    private void d(Channel channel, Promise<Void> promise) throws Exception {
        if (offerChannel(channel)) {
            this.f.channelReleased(channel);
            promise.setSuccess(null);
            return;
        }
        a(channel, c, promise);
    }

    private static void a(Channel channel) {
        channel.attr(a).getAndSet(null);
        channel.close();
    }

    private static void a(Channel channel, Throwable th, Promise<?> promise) {
        a(channel);
        promise.setFailure(th);
    }

    protected Channel pollChannel() {
        return this.e.pollLast();
    }

    protected boolean offerChannel(Channel channel) {
        return this.e.offer(channel);
    }

    @Override // io.netty.channel.pool.ChannelPool, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        while (true) {
            Channel pollChannel = pollChannel();
            if (pollChannel != null) {
                pollChannel.close();
            } else {
                return;
            }
        }
    }
}
