package io.netty.channel;

import io.netty.channel.ChannelFlushPromiseNotifier;
import io.netty.util.concurrent.DefaultProgressivePromise;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

/* loaded from: classes4.dex */
public class DefaultChannelProgressivePromise extends DefaultProgressivePromise<Void> implements ChannelFlushPromiseNotifier.b, ChannelProgressivePromise {
    private final Channel a;
    private long b;

    @Override // io.netty.channel.ChannelFuture
    public boolean isVoid() {
        return false;
    }

    @Override // io.netty.channel.ChannelFlushPromiseNotifier.b
    public ChannelProgressivePromise promise() {
        return this;
    }

    @Override // io.netty.channel.ChannelProgressivePromise, io.netty.channel.ChannelPromise
    public ChannelProgressivePromise unvoid() {
        return this;
    }

    public DefaultChannelProgressivePromise(Channel channel) {
        this.a = channel;
    }

    public DefaultChannelProgressivePromise(Channel channel, EventExecutor eventExecutor) {
        super(eventExecutor);
        this.a = channel;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.util.concurrent.DefaultPromise
    public EventExecutor executor() {
        EventExecutor executor = super.executor();
        return executor == null ? channel().eventLoop() : executor;
    }

    @Override // io.netty.channel.ChannelFuture
    public Channel channel() {
        return this.a;
    }

    @Override // io.netty.channel.ChannelProgressivePromise, io.netty.channel.ChannelPromise
    public ChannelProgressivePromise setSuccess() {
        return setSuccess((Void) null);
    }

    @Override // io.netty.channel.ChannelProgressivePromise, io.netty.channel.ChannelPromise
    public ChannelProgressivePromise setSuccess(Void r1) {
        super.setSuccess((DefaultChannelProgressivePromise) r1);
        return this;
    }

    @Override // io.netty.channel.ChannelPromise
    public boolean trySuccess() {
        return trySuccess(null);
    }

    @Override // io.netty.util.concurrent.DefaultProgressivePromise, io.netty.util.concurrent.DefaultPromise, io.netty.util.concurrent.Promise, io.netty.channel.ChannelPromise
    public ChannelProgressivePromise setFailure(Throwable th) {
        super.setFailure(th);
        return this;
    }

    @Override // io.netty.util.concurrent.DefaultProgressivePromise, io.netty.util.concurrent.ProgressivePromise
    public ChannelProgressivePromise setProgress(long j, long j2) {
        super.setProgress(j, j2);
        return this;
    }

    @Override // io.netty.util.concurrent.DefaultProgressivePromise, io.netty.util.concurrent.DefaultPromise, io.netty.util.concurrent.Future
    public Future<Void> addListener(GenericFutureListener<? extends Future<? super Void>> genericFutureListener) {
        super.addListener((GenericFutureListener) genericFutureListener);
        return this;
    }

    @Override // io.netty.util.concurrent.DefaultProgressivePromise, io.netty.util.concurrent.DefaultPromise, io.netty.util.concurrent.Future
    public Future<Void> addListeners(GenericFutureListener<? extends Future<? super Void>>... genericFutureListenerArr) {
        super.addListeners((GenericFutureListener[]) genericFutureListenerArr);
        return this;
    }

    @Override // io.netty.util.concurrent.DefaultProgressivePromise, io.netty.util.concurrent.DefaultPromise, io.netty.util.concurrent.Future
    public Future<Void> removeListener(GenericFutureListener<? extends Future<? super Void>> genericFutureListener) {
        super.removeListener((GenericFutureListener) genericFutureListener);
        return this;
    }

    @Override // io.netty.util.concurrent.DefaultProgressivePromise, io.netty.util.concurrent.DefaultPromise, io.netty.util.concurrent.Future
    public Future<Void> removeListeners(GenericFutureListener<? extends Future<? super Void>>... genericFutureListenerArr) {
        super.removeListeners((GenericFutureListener[]) genericFutureListenerArr);
        return this;
    }

    @Override // io.netty.util.concurrent.DefaultProgressivePromise, io.netty.util.concurrent.DefaultPromise, io.netty.util.concurrent.Future
    public Future<Void> sync() throws InterruptedException {
        super.sync();
        return this;
    }

    @Override // io.netty.util.concurrent.DefaultProgressivePromise, io.netty.util.concurrent.DefaultPromise, io.netty.util.concurrent.Future
    public Future<Void> syncUninterruptibly() {
        super.syncUninterruptibly();
        return this;
    }

    @Override // io.netty.util.concurrent.DefaultProgressivePromise, io.netty.util.concurrent.DefaultPromise, io.netty.util.concurrent.Future
    public Future<Void> await() throws InterruptedException {
        super.await();
        return this;
    }

    @Override // io.netty.util.concurrent.DefaultProgressivePromise, io.netty.util.concurrent.DefaultPromise, io.netty.util.concurrent.Future
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
