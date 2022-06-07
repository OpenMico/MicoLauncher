package io.netty.util.concurrent;

import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public abstract class CompleteFuture<V> extends AbstractFuture<V> {
    private final EventExecutor a;

    @Override // io.netty.util.concurrent.Future
    public Future<V> awaitUninterruptibly() {
        return this;
    }

    @Override // io.netty.util.concurrent.Future
    public boolean awaitUninterruptibly(long j) {
        return true;
    }

    @Override // io.netty.util.concurrent.Future
    public boolean awaitUninterruptibly(long j, TimeUnit timeUnit) {
        return true;
    }

    @Override // io.netty.util.concurrent.Future, java.util.concurrent.Future
    public boolean cancel(boolean z) {
        return false;
    }

    @Override // io.netty.util.concurrent.Future
    public boolean isCancellable() {
        return false;
    }

    @Override // java.util.concurrent.Future
    public boolean isCancelled() {
        return false;
    }

    @Override // java.util.concurrent.Future
    public boolean isDone() {
        return true;
    }

    @Override // io.netty.util.concurrent.Future
    public Future<V> removeListener(GenericFutureListener<? extends Future<? super V>> genericFutureListener) {
        return this;
    }

    @Override // io.netty.util.concurrent.Future
    public Future<V> removeListeners(GenericFutureListener<? extends Future<? super V>>... genericFutureListenerArr) {
        return this;
    }

    @Override // io.netty.util.concurrent.Future
    public Future<V> sync() throws InterruptedException {
        return this;
    }

    @Override // io.netty.util.concurrent.Future
    public Future<V> syncUninterruptibly() {
        return this;
    }

    public CompleteFuture(EventExecutor eventExecutor) {
        this.a = eventExecutor;
    }

    public EventExecutor executor() {
        return this.a;
    }

    @Override // io.netty.util.concurrent.Future
    public Future<V> addListener(GenericFutureListener<? extends Future<? super V>> genericFutureListener) {
        if (genericFutureListener != null) {
            DefaultPromise.notifyListener(executor(), this, genericFutureListener);
            return this;
        }
        throw new NullPointerException("listener");
    }

    @Override // io.netty.util.concurrent.Future
    public Future<V> addListeners(GenericFutureListener<? extends Future<? super V>>... genericFutureListenerArr) {
        if (genericFutureListenerArr != null) {
            for (GenericFutureListener<? extends Future<? super V>> genericFutureListener : genericFutureListenerArr) {
                if (genericFutureListener == null) {
                    break;
                }
                DefaultPromise.notifyListener(executor(), this, genericFutureListener);
            }
            return this;
        }
        throw new NullPointerException("listeners");
    }

    @Override // io.netty.util.concurrent.Future
    public Future<V> await() throws InterruptedException {
        if (!Thread.interrupted()) {
            return this;
        }
        throw new InterruptedException();
    }

    @Override // io.netty.util.concurrent.Future
    public boolean await(long j, TimeUnit timeUnit) throws InterruptedException {
        if (!Thread.interrupted()) {
            return true;
        }
        throw new InterruptedException();
    }

    @Override // io.netty.util.concurrent.Future
    public boolean await(long j) throws InterruptedException {
        if (!Thread.interrupted()) {
            return true;
        }
        throw new InterruptedException();
    }
}
