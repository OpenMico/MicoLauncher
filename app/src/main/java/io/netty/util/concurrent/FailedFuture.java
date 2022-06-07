package io.netty.util.concurrent;

import io.netty.util.internal.PlatformDependent;

/* loaded from: classes4.dex */
public final class FailedFuture<V> extends CompleteFuture<V> {
    private final Throwable a;

    @Override // io.netty.util.concurrent.Future
    public V getNow() {
        return null;
    }

    @Override // io.netty.util.concurrent.Future
    public boolean isSuccess() {
        return false;
    }

    public FailedFuture(EventExecutor eventExecutor, Throwable th) {
        super(eventExecutor);
        if (th != null) {
            this.a = th;
            return;
        }
        throw new NullPointerException("cause");
    }

    @Override // io.netty.util.concurrent.Future
    public Throwable cause() {
        return this.a;
    }

    @Override // io.netty.util.concurrent.CompleteFuture, io.netty.util.concurrent.Future
    public Future<V> sync() {
        PlatformDependent.throwException(this.a);
        return this;
    }

    @Override // io.netty.util.concurrent.CompleteFuture, io.netty.util.concurrent.Future
    public Future<V> syncUninterruptibly() {
        PlatformDependent.throwException(this.a);
        return this;
    }
}
