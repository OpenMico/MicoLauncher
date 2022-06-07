package io.netty.util.concurrent;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public interface EventExecutorGroup extends Iterable<EventExecutor>, ScheduledExecutorService {
    boolean isShuttingDown();

    @Override // java.lang.Iterable
    Iterator<EventExecutor> iterator();

    EventExecutor next();

    ScheduledFuture<?> schedule(Runnable runnable, long j, TimeUnit timeUnit);

    <V> ScheduledFuture<V> schedule(Callable<V> callable, long j, TimeUnit timeUnit);

    ScheduledFuture<?> scheduleAtFixedRate(Runnable runnable, long j, long j2, TimeUnit timeUnit);

    ScheduledFuture<?> scheduleWithFixedDelay(Runnable runnable, long j, long j2, TimeUnit timeUnit);

    @Deprecated
    void shutdown();

    Future<?> shutdownGracefully();

    Future<?> shutdownGracefully(long j, long j2, TimeUnit timeUnit);

    @Deprecated
    List<Runnable> shutdownNow();

    Future<?> submit(Runnable runnable);

    <T> Future<T> submit(Runnable runnable, T t);

    <T> Future<T> submit(Callable<T> callable);

    Future<?> terminationFuture();
}
