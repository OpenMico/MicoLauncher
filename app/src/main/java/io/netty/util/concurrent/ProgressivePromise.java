package io.netty.util.concurrent;

/* loaded from: classes4.dex */
public interface ProgressivePromise<V> extends ProgressiveFuture<V>, Promise<V> {
    @Override // io.netty.util.concurrent.ProgressiveFuture, io.netty.util.concurrent.Future
    ProgressivePromise<V> addListener(GenericFutureListener<? extends Future<? super V>> genericFutureListener);

    @Override // io.netty.util.concurrent.ProgressiveFuture, io.netty.util.concurrent.Future
    ProgressivePromise<V> addListeners(GenericFutureListener<? extends Future<? super V>>... genericFutureListenerArr);

    @Override // io.netty.util.concurrent.ProgressiveFuture, io.netty.util.concurrent.Future
    ProgressivePromise<V> await() throws InterruptedException;

    @Override // io.netty.util.concurrent.ProgressiveFuture, io.netty.util.concurrent.Future
    ProgressivePromise<V> awaitUninterruptibly();

    @Override // io.netty.util.concurrent.ProgressiveFuture, io.netty.util.concurrent.Future
    ProgressivePromise<V> removeListener(GenericFutureListener<? extends Future<? super V>> genericFutureListener);

    @Override // io.netty.util.concurrent.ProgressiveFuture, io.netty.util.concurrent.Future
    ProgressivePromise<V> removeListeners(GenericFutureListener<? extends Future<? super V>>... genericFutureListenerArr);

    @Override // io.netty.util.concurrent.Promise, io.netty.channel.ChannelPromise
    ProgressivePromise<V> setFailure(Throwable th);

    ProgressivePromise<V> setProgress(long j, long j2);

    ProgressivePromise<V> setSuccess(V v);

    @Override // io.netty.util.concurrent.ProgressiveFuture, io.netty.util.concurrent.Future
    ProgressivePromise<V> sync() throws InterruptedException;

    @Override // io.netty.util.concurrent.ProgressiveFuture, io.netty.util.concurrent.Future
    ProgressivePromise<V> syncUninterruptibly();

    boolean tryProgress(long j, long j2);
}
