package io.netty.channel;

import io.netty.util.concurrent.CompleteFuture;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: CompleteChannelFuture.java */
/* loaded from: classes4.dex */
public abstract class b extends CompleteFuture<Void> implements ChannelFuture {
    private final Channel a;

    /* renamed from: a */
    public Void getNow() {
        return null;
    }

    @Override // io.netty.util.concurrent.CompleteFuture, io.netty.util.concurrent.Future
    /* renamed from: await */
    public Future<Void> await2() throws InterruptedException {
        return this;
    }

    @Override // io.netty.util.concurrent.CompleteFuture, io.netty.util.concurrent.Future
    /* renamed from: awaitUninterruptibly */
    public Future<Void> awaitUninterruptibly2() {
        return this;
    }

    @Override // io.netty.channel.ChannelFuture
    public boolean isVoid() {
        return false;
    }

    @Override // io.netty.util.concurrent.CompleteFuture, io.netty.util.concurrent.Future
    /* renamed from: sync */
    public Future<Void> sync2() throws InterruptedException {
        return this;
    }

    @Override // io.netty.util.concurrent.CompleteFuture, io.netty.util.concurrent.Future
    /* renamed from: syncUninterruptibly */
    public Future<Void> syncUninterruptibly2() {
        return this;
    }

    public b(Channel channel, EventExecutor eventExecutor) {
        super(eventExecutor);
        if (channel != null) {
            this.a = channel;
            return;
        }
        throw new NullPointerException("channel");
    }

    @Override // io.netty.util.concurrent.CompleteFuture
    public EventExecutor executor() {
        EventExecutor executor = super.executor();
        return executor == null ? channel().eventLoop() : executor;
    }

    @Override // io.netty.util.concurrent.CompleteFuture, io.netty.util.concurrent.Future
    /* renamed from: addListener */
    public Future<Void> addListener2(GenericFutureListener<? extends Future<? super Void>> genericFutureListener) {
        super.addListener((GenericFutureListener) genericFutureListener);
        return this;
    }

    @Override // io.netty.util.concurrent.CompleteFuture, io.netty.util.concurrent.Future
    /* renamed from: addListeners */
    public Future<Void> addListeners2(GenericFutureListener<? extends Future<? super Void>>... genericFutureListenerArr) {
        super.addListeners((GenericFutureListener[]) genericFutureListenerArr);
        return this;
    }

    @Override // io.netty.util.concurrent.CompleteFuture, io.netty.util.concurrent.Future
    /* renamed from: removeListener */
    public Future<Void> removeListener2(GenericFutureListener<? extends Future<? super Void>> genericFutureListener) {
        super.removeListener((GenericFutureListener) genericFutureListener);
        return this;
    }

    @Override // io.netty.util.concurrent.CompleteFuture, io.netty.util.concurrent.Future
    /* renamed from: removeListeners */
    public Future<Void> removeListeners2(GenericFutureListener<? extends Future<? super Void>>... genericFutureListenerArr) {
        super.removeListeners((GenericFutureListener[]) genericFutureListenerArr);
        return this;
    }

    @Override // io.netty.channel.ChannelFuture
    public Channel channel() {
        return this.a;
    }
}
