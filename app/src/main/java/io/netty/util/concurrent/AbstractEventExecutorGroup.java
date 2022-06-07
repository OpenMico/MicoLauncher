package io.netty.util.concurrent;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* loaded from: classes4.dex */
public abstract class AbstractEventExecutorGroup implements EventExecutorGroup {
    @Override // io.netty.util.concurrent.EventExecutorGroup, java.util.concurrent.ExecutorService
    @Deprecated
    public abstract void shutdown();

    @Override // io.netty.util.concurrent.EventExecutorGroup, java.util.concurrent.ExecutorService
    public Future<?> submit(Runnable runnable) {
        return next().submit(runnable);
    }

    @Override // io.netty.util.concurrent.EventExecutorGroup, java.util.concurrent.ExecutorService
    public <T> Future<T> submit(Runnable runnable, T t) {
        return next().submit(runnable, (Runnable) t);
    }

    @Override // io.netty.util.concurrent.EventExecutorGroup, java.util.concurrent.ExecutorService
    public <T> Future<T> submit(Callable<T> callable) {
        return next().submit((Callable) callable);
    }

    @Override // io.netty.util.concurrent.EventExecutorGroup, java.util.concurrent.ScheduledExecutorService
    public ScheduledFuture<?> schedule(Runnable runnable, long j, TimeUnit timeUnit) {
        return next().schedule(runnable, j, timeUnit);
    }

    @Override // io.netty.util.concurrent.EventExecutorGroup, java.util.concurrent.ScheduledExecutorService
    public <V> ScheduledFuture<V> schedule(Callable<V> callable, long j, TimeUnit timeUnit) {
        return next().schedule((Callable) callable, j, timeUnit);
    }

    @Override // io.netty.util.concurrent.EventExecutorGroup, java.util.concurrent.ScheduledExecutorService
    public ScheduledFuture<?> scheduleAtFixedRate(Runnable runnable, long j, long j2, TimeUnit timeUnit) {
        return next().scheduleAtFixedRate(runnable, j, j2, timeUnit);
    }

    @Override // io.netty.util.concurrent.EventExecutorGroup, java.util.concurrent.ScheduledExecutorService
    public ScheduledFuture<?> scheduleWithFixedDelay(Runnable runnable, long j, long j2, TimeUnit timeUnit) {
        return next().scheduleWithFixedDelay(runnable, j, j2, timeUnit);
    }

    @Override // io.netty.util.concurrent.EventExecutorGroup
    public Future<?> shutdownGracefully() {
        return shutdownGracefully(2L, 15L, TimeUnit.SECONDS);
    }

    @Override // io.netty.util.concurrent.EventExecutorGroup
    @Deprecated
    public List<Runnable> shutdownNow() {
        shutdown();
        return Collections.emptyList();
    }

    @Override // java.util.concurrent.ExecutorService
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> collection) throws InterruptedException {
        return next().invokeAll(collection);
    }

    @Override // java.util.concurrent.ExecutorService
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> collection, long j, TimeUnit timeUnit) throws InterruptedException {
        return next().invokeAll(collection, j, timeUnit);
    }

    @Override // java.util.concurrent.ExecutorService
    public <T> T invokeAny(Collection<? extends Callable<T>> collection) throws InterruptedException, ExecutionException {
        return (T) next().invokeAny(collection);
    }

    @Override // java.util.concurrent.ExecutorService
    public <T> T invokeAny(Collection<? extends Callable<T>> collection, long j, TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException {
        return (T) next().invokeAny(collection, j, timeUnit);
    }

    @Override // java.util.concurrent.Executor
    public void execute(Runnable runnable) {
        next().execute(runnable);
    }
}
