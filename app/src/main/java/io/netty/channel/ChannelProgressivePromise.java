package io.netty.channel;

import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.ProgressivePromise;

/* loaded from: classes4.dex */
public interface ChannelProgressivePromise extends ChannelProgressiveFuture, ChannelPromise, ProgressivePromise<Void> {
    @Override // io.netty.channel.ChannelProgressiveFuture, io.netty.channel.ChannelFuture, io.netty.util.concurrent.Future
    Future<Void> addListener(GenericFutureListener<? extends Future<? super Void>> genericFutureListener);

    @Override // io.netty.channel.ChannelProgressiveFuture, io.netty.channel.ChannelFuture, io.netty.util.concurrent.Future
    Future<Void> addListeners(GenericFutureListener<? extends Future<? super Void>>... genericFutureListenerArr);

    @Override // io.netty.channel.ChannelProgressiveFuture, io.netty.channel.ChannelFuture, io.netty.util.concurrent.Future
    Future<Void> await() throws InterruptedException;

    @Override // io.netty.channel.ChannelProgressiveFuture, io.netty.channel.ChannelFuture, io.netty.util.concurrent.Future
    Future<Void> awaitUninterruptibly();

    @Override // io.netty.channel.ChannelProgressiveFuture, io.netty.channel.ChannelFuture, io.netty.util.concurrent.Future
    Future<Void> removeListener(GenericFutureListener<? extends Future<? super Void>> genericFutureListener);

    @Override // io.netty.channel.ChannelProgressiveFuture, io.netty.channel.ChannelFuture, io.netty.util.concurrent.Future
    Future<Void> removeListeners(GenericFutureListener<? extends Future<? super Void>>... genericFutureListenerArr);

    @Override // io.netty.channel.ChannelPromise
    ChannelProgressivePromise setFailure(Throwable th);

    @Override // io.netty.util.concurrent.ProgressivePromise
    ProgressivePromise<Void> setProgress(long j, long j2);

    @Override // io.netty.channel.ChannelPromise
    ChannelProgressivePromise setSuccess();

    @Override // io.netty.channel.ChannelPromise
    ChannelProgressivePromise setSuccess(Void v);

    @Override // io.netty.channel.ChannelProgressiveFuture, io.netty.channel.ChannelFuture, io.netty.util.concurrent.Future
    Future<Void> sync() throws InterruptedException;

    @Override // io.netty.channel.ChannelProgressiveFuture, io.netty.channel.ChannelFuture, io.netty.util.concurrent.Future
    Future<Void> syncUninterruptibly();

    @Override // io.netty.channel.ChannelPromise
    ChannelProgressivePromise unvoid();
}
