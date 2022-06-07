package io.netty.util.concurrent;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.Callable;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public abstract class AbstractEventExecutor extends AbstractExecutorService implements EventExecutor {
    private final EventExecutorGroup a;
    private final Collection<EventExecutor> b;

    public EventExecutor next() {
        return this;
    }

    @Override // java.util.concurrent.ExecutorService, io.netty.util.concurrent.EventExecutorGroup
    @Deprecated
    public abstract void shutdown();

    /* JADX INFO: Access modifiers changed from: protected */
    public AbstractEventExecutor() {
        this(null);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public AbstractEventExecutor(EventExecutorGroup eventExecutorGroup) {
        this.b = Collections.singleton(this);
        this.a = eventExecutorGroup;
    }

    public EventExecutorGroup parent() {
        return this.a;
    }

    public boolean inEventLoop() {
        return inEventLoop(Thread.currentThread());
    }

    @Override // io.netty.util.concurrent.EventExecutorGroup, java.lang.Iterable
    public Iterator<EventExecutor> iterator() {
        return this.b.iterator();
    }

    @Override // io.netty.util.concurrent.EventExecutorGroup
    public Future<?> shutdownGracefully() {
        return shutdownGracefully(2L, 15L, TimeUnit.SECONDS);
    }

    @Override // java.util.concurrent.ExecutorService, io.netty.util.concurrent.EventExecutorGroup
    @Deprecated
    public List<Runnable> shutdownNow() {
        shutdown();
        return Collections.emptyList();
    }

    @Override // io.netty.util.concurrent.EventExecutor
    public <V> Promise<V> newPromise() {
        return new DefaultPromise(this);
    }

    @Override // io.netty.util.concurrent.EventExecutor
    public <V> ProgressivePromise<V> newProgressivePromise() {
        return new DefaultProgressivePromise(this);
    }

    @Override // io.netty.util.concurrent.EventExecutor
    public <V> Future<V> newSucceededFuture(V v) {
        return new SucceededFuture(this, v);
    }

    @Override // io.netty.util.concurrent.EventExecutor
    public <V> Future<V> newFailedFuture(Throwable th) {
        return new FailedFuture(this, th);
    }

    @Override // java.util.concurrent.AbstractExecutorService, java.util.concurrent.ExecutorService, io.netty.util.concurrent.EventExecutorGroup
    public Future<?> submit(Runnable runnable) {
        return (Future) super.submit(runnable);
    }

    @Override // java.util.concurrent.AbstractExecutorService, java.util.concurrent.ExecutorService, io.netty.util.concurrent.EventExecutorGroup
    public <T> Future<T> submit(Runnable runnable, T t) {
        return (Future) super.submit(runnable, (Runnable) t);
    }

    @Override // java.util.concurrent.AbstractExecutorService, java.util.concurrent.ExecutorService, io.netty.util.concurrent.EventExecutorGroup
    public <T> Future<T> submit(Callable<T> callable) {
        return (Future) super.submit((Callable) callable);
    }

    @Override // java.util.concurrent.AbstractExecutorService
    protected final <T> RunnableFuture<T> newTaskFor(Runnable runnable, T t) {
        return new b(this, runnable, t);
    }

    @Override // java.util.concurrent.AbstractExecutorService
    protected final <T> RunnableFuture<T> newTaskFor(Callable<T> callable) {
        return new b(this, callable);
    }

    @Override // io.netty.util.concurrent.EventExecutorGroup, java.util.concurrent.ScheduledExecutorService
    public ScheduledFuture<?> schedule(Runnable runnable, long j, TimeUnit timeUnit) {
        throw new UnsupportedOperationException();
    }

    @Override // io.netty.util.concurrent.EventExecutorGroup, java.util.concurrent.ScheduledExecutorService
    public <V> ScheduledFuture<V> schedule(Callable<V> callable, long j, TimeUnit timeUnit) {
        throw new UnsupportedOperationException();
    }

    @Override // io.netty.util.concurrent.EventExecutorGroup, java.util.concurrent.ScheduledExecutorService
    public ScheduledFuture<?> scheduleAtFixedRate(Runnable runnable, long j, long j2, TimeUnit timeUnit) {
        throw new UnsupportedOperationException();
    }

    @Override // io.netty.util.concurrent.EventExecutorGroup, java.util.concurrent.ScheduledExecutorService
    public ScheduledFuture<?> scheduleWithFixedDelay(Runnable runnable, long j, long j2, TimeUnit timeUnit) {
        throw new UnsupportedOperationException();
    }
}
