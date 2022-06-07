package io.netty.channel;

import io.netty.channel.ChannelFlushPromiseNotifier;
import io.netty.util.concurrent.DefaultPromise;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

/* loaded from: classes4.dex */
public class DefaultChannelPromise extends DefaultPromise<Void> implements ChannelFlushPromiseNotifier.b, ChannelPromise {
    private final Channel a;
    private long b;

    @Override // io.netty.channel.ChannelFuture
    public boolean isVoid() {
        return false;
    }

    @Override // io.netty.channel.ChannelFlushPromiseNotifier.b
    public ChannelPromise promise() {
        return this;
    }

    @Override // io.netty.channel.ChannelPromise
    public ChannelPromise unvoid() {
        return this;
    }

    public DefaultChannelPromise(Channel channel) {
        this.a = channel;
    }

    public DefaultChannelPromise(Channel channel, EventExecutor eventExecutor) {
        super(eventExecutor);
        this.a = channel;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.util.concurrent.DefaultPromise
    public EventExecutor executor() {
        EventExecutor executor = super.executor();
        return executor == null ? channel().eventLoop() : executor;
    }

    @Override // io.netty.channel.ChannelPromise, io.netty.channel.ChannelFuture
    public Channel channel() {
        return this.a;
    }

    public ChannelPromise setSuccess() {
        return setSuccess((Void) null);
    }

    @Override // io.netty.channel.ChannelPromise
    public ChannelPromise setSuccess(Void r1) {
        super.setSuccess((DefaultChannelPromise) r1);
        return this;
    }

    public boolean trySuccess() {
        return trySuccess(null);
    }

    @Override // io.netty.util.concurrent.DefaultPromise, io.netty.util.concurrent.Promise, io.netty.channel.ChannelPromise
    public ChannelPromise setFailure(Throwable th) {
        super.setFailure(th);
        return this;
    }

    @Override // io.netty.util.concurrent.DefaultPromise, io.netty.util.concurrent.Future
    public Future<Void> addListener(GenericFutureListener<? extends Future<? super Void>> genericFutureListener) {
        super.addListener((GenericFutureListener) genericFutureListener);
        return this;
    }

    @Override // io.netty.util.concurrent.DefaultPromise, io.netty.util.concurrent.Future
    public Future<Void> addListeners(GenericFutureListener<? extends Future<? super Void>>... genericFutureListenerArr) {
        super.addListeners((GenericFutureListener[]) genericFutureListenerArr);
        return this;
    }

    @Override // io.netty.util.concurrent.DefaultPromise, io.netty.util.concurrent.Future
    public Future<Void> removeListener(GenericFutureListener<? extends Future<? super Void>> genericFutureListener) {
        super.removeListener((GenericFutureListener) genericFutureListener);
        return this;
    }

    @Override // io.netty.util.concurrent.DefaultPromise, io.netty.util.concurrent.Future
    public Future<Void> removeListeners(GenericFutureListener<? extends Future<? super Void>>... genericFutureListenerArr) {
        super.removeListeners((GenericFutureListener[]) genericFutureListenerArr);
        return this;
    }

    @Override // io.netty.util.concurrent.DefaultPromise, io.netty.util.concurrent.Future
    public Future<Void> sync() throws InterruptedException {
        super.sync();
        return this;
    }

    @Override // io.netty.util.concurrent.DefaultPromise, io.netty.util.concurrent.Future
    public Future<Void> syncUninterruptibly() {
        super.syncUninterruptibly();
        return this;
    }

    @Override // io.netty.util.concurrent.DefaultPromise, io.netty.util.concurrent.Future
    public Future<Void> await() throws InterruptedException {
        super.await();
        return this;
    }

    @Override // io.netty.util.concurrent.DefaultPromise, io.netty.util.concurrent.Future
    public Future<Void> awaitUninterruptibly() {
        super.awaitUninterruptibly();
        return this;
    }

    @Override // io.netty.channel.ChannelFlushPromiseNotifier.b
    public long flushCheckpoint() {
        return this.b;
    }

    @Override // io.netty.channel.ChannelFlushPromiseNotifier.b
    public void flushCheckpoint(long j) {
        this.b = j;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.util.concurrent.DefaultPromise
    public void checkDeadLock() {
        if (channel().isRegistered()) {
            super.checkDeadLock();
        }
    }
}
