package io.netty.util.concurrent;

/* loaded from: classes4.dex */
public class DefaultProgressivePromise<V> extends DefaultPromise<V> implements ProgressivePromise<V> {
    public DefaultProgressivePromise(EventExecutor eventExecutor) {
        super(eventExecutor);
    }

    public DefaultProgressivePromise() {
    }

    public ProgressivePromise<V> setProgress(long j, long j2) {
        if (j2 < 0) {
            j2 = -1;
            if (j < 0) {
                throw new IllegalArgumentException("progress: " + j + " (expected: >= 0)");
            }
        } else if (j < 0 || j > j2) {
            throw new IllegalArgumentException("progress: " + j + " (expected: 0 <= progress <= total (" + j2 + "))");
        }
        if (!isDone()) {
            a(j, j2);
            return this;
        }
        throw new IllegalStateException("complete already");
    }

    @Override // io.netty.util.concurrent.ProgressivePromise
    public boolean tryProgress(long j, long j2) {
        if (j2 < 0) {
            j2 = -1;
            if (j < 0 || isDone()) {
                return false;
            }
        } else if (j < 0 || j > j2 || isDone()) {
            return false;
        }
        a(j, j2);
        return true;
    }

    @Override // io.netty.util.concurrent.DefaultPromise, io.netty.util.concurrent.Future
    public ProgressivePromise<V> addListener(GenericFutureListener<? extends Future<? super V>> genericFutureListener) {
        super.addListener((GenericFutureListener) genericFutureListener);
        return this;
    }

    @Override // io.netty.util.concurrent.DefaultPromise, io.netty.util.concurrent.Future
    public ProgressivePromise<V> addListeners(GenericFutureListener<? extends Future<? super V>>... genericFutureListenerArr) {
        super.addListeners((GenericFutureListener[]) genericFutureListenerArr);
        return this;
    }

    @Override // io.netty.util.concurrent.DefaultPromise, io.netty.util.concurrent.Future
    public ProgressivePromise<V> removeListener(GenericFutureListener<? extends Future<? super V>> genericFutureListener) {
        super.removeListener((GenericFutureListener) genericFutureListener);
        return this;
    }

    @Override // io.netty.util.concurrent.DefaultPromise, io.netty.util.concurrent.Future
    public ProgressivePromise<V> removeListeners(GenericFutureListener<? extends Future<? super V>>... genericFutureListenerArr) {
        super.removeListeners((GenericFutureListener[]) genericFutureListenerArr);
        return this;
    }

    @Override // io.netty.util.concurrent.DefaultPromise, io.netty.util.concurrent.Future
    public ProgressivePromise<V> sync() throws InterruptedException {
        super.sync();
        return this;
    }

    @Override // io.netty.util.concurrent.DefaultPromise, io.netty.util.concurrent.Future
    public ProgressivePromise<V> syncUninterruptibly() {
        super.syncUninterruptibly();
        return this;
    }

    @Override // io.netty.util.concurrent.DefaultPromise, io.netty.util.concurrent.Future
    public ProgressivePromise<V> await() throws InterruptedException {
        super.await();
        return this;
    }

    @Override // io.netty.util.concurrent.DefaultPromise, io.netty.util.concurrent.Future
    public ProgressivePromise<V> awaitUninterruptibly() {
        super.awaitUninterruptibly();
        return this;
    }

    @Override // io.netty.util.concurrent.DefaultPromise, io.netty.util.concurrent.Promise, io.netty.util.concurrent.ProgressivePromise
    public ProgressivePromise<V> setSuccess(V v) {
        super.setSuccess((DefaultProgressivePromise<V>) v);
        return this;
    }

    @Override // io.netty.util.concurrent.DefaultPromise, io.netty.util.concurrent.Promise, io.netty.channel.ChannelPromise
    public ProgressivePromise<V> setFailure(Throwable th) {
        super.setFailure(th);
        return this;
    }
}
