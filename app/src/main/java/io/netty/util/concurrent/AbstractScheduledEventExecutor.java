package io.netty.util.concurrent;

import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.OneTimeTask;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public abstract class AbstractScheduledEventExecutor extends AbstractEventExecutor {
    static final /* synthetic */ boolean c = !AbstractScheduledEventExecutor.class.desiredAssertionStatus();
    Queue<c<?>> b;

    public AbstractScheduledEventExecutor() {
    }

    public AbstractScheduledEventExecutor(EventExecutorGroup eventExecutorGroup) {
        super(eventExecutorGroup);
    }

    public static long nanoTime() {
        return c.b();
    }

    Queue<c<?>> d() {
        if (this.b == null) {
            this.b = new PriorityQueue();
        }
        return this.b;
    }

    private static boolean a(Queue<c<?>> queue) {
        return queue == null || queue.isEmpty();
    }

    public void cancelScheduledTasks() {
        if (c || inEventLoop()) {
            Queue<c<?>> queue = this.b;
            if (!a(queue)) {
                for (c cVar : (c[]) queue.toArray(new c[queue.size()])) {
                    cVar.a(false);
                }
                queue.clear();
                return;
            }
            return;
        }
        throw new AssertionError();
    }

    protected final Runnable pollScheduledTask() {
        return pollScheduledTask(nanoTime());
    }

    protected final Runnable pollScheduledTask(long j) {
        if (c || inEventLoop()) {
            Queue<c<?>> queue = this.b;
            c<?> peek = queue == null ? null : queue.peek();
            if (peek == null || peek.c() > j) {
                return null;
            }
            queue.remove();
            return peek;
        }
        throw new AssertionError();
    }

    protected final long nextScheduledTaskNano() {
        Queue<c<?>> queue = this.b;
        c<?> peek = queue == null ? null : queue.peek();
        if (peek == null) {
            return -1L;
        }
        return Math.max(0L, peek.c() - nanoTime());
    }

    final c<?> e() {
        Queue<c<?>> queue = this.b;
        if (queue == null) {
            return null;
        }
        return queue.peek();
    }

    protected final boolean hasScheduledTasks() {
        Queue<c<?>> queue = this.b;
        c<?> peek = queue == null ? null : queue.peek();
        return peek != null && peek.c() <= nanoTime();
    }

    @Override // io.netty.util.concurrent.AbstractEventExecutor, io.netty.util.concurrent.EventExecutorGroup, java.util.concurrent.ScheduledExecutorService
    public ScheduledFuture<?> schedule(Runnable runnable, long j, TimeUnit timeUnit) {
        ObjectUtil.checkNotNull(runnable, "command");
        ObjectUtil.checkNotNull(timeUnit, "unit");
        if (j >= 0) {
            return a(new c(this, runnable, (Object) null, c.a(timeUnit.toNanos(j))));
        }
        throw new IllegalArgumentException(String.format("delay: %d (expected: >= 0)", Long.valueOf(j)));
    }

    @Override // io.netty.util.concurrent.AbstractEventExecutor, io.netty.util.concurrent.EventExecutorGroup, java.util.concurrent.ScheduledExecutorService
    public <V> ScheduledFuture<V> schedule(Callable<V> callable, long j, TimeUnit timeUnit) {
        ObjectUtil.checkNotNull(callable, "callable");
        ObjectUtil.checkNotNull(timeUnit, "unit");
        if (j >= 0) {
            return a(new c<>(this, callable, c.a(timeUnit.toNanos(j))));
        }
        throw new IllegalArgumentException(String.format("delay: %d (expected: >= 0)", Long.valueOf(j)));
    }

    @Override // io.netty.util.concurrent.AbstractEventExecutor, io.netty.util.concurrent.EventExecutorGroup, java.util.concurrent.ScheduledExecutorService
    public ScheduledFuture<?> scheduleAtFixedRate(Runnable runnable, long j, long j2, TimeUnit timeUnit) {
        ObjectUtil.checkNotNull(runnable, "command");
        ObjectUtil.checkNotNull(timeUnit, "unit");
        if (j < 0) {
            throw new IllegalArgumentException(String.format("initialDelay: %d (expected: >= 0)", Long.valueOf(j)));
        } else if (j2 > 0) {
            return a(new c(this, Executors.callable(runnable, null), c.a(timeUnit.toNanos(j)), timeUnit.toNanos(j2)));
        } else {
            throw new IllegalArgumentException(String.format("period: %d (expected: > 0)", Long.valueOf(j2)));
        }
    }

    @Override // io.netty.util.concurrent.AbstractEventExecutor, io.netty.util.concurrent.EventExecutorGroup, java.util.concurrent.ScheduledExecutorService
    public ScheduledFuture<?> scheduleWithFixedDelay(Runnable runnable, long j, long j2, TimeUnit timeUnit) {
        ObjectUtil.checkNotNull(runnable, "command");
        ObjectUtil.checkNotNull(timeUnit, "unit");
        if (j < 0) {
            throw new IllegalArgumentException(String.format("initialDelay: %d (expected: >= 0)", Long.valueOf(j)));
        } else if (j2 > 0) {
            return a(new c(this, Executors.callable(runnable, null), c.a(timeUnit.toNanos(j)), -timeUnit.toNanos(j2)));
        } else {
            throw new IllegalArgumentException(String.format("delay: %d (expected: > 0)", Long.valueOf(j2)));
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    <V> ScheduledFuture<V> a(final c<V> cVar) {
        if (inEventLoop()) {
            d().add(cVar);
        } else {
            execute(new OneTimeTask() { // from class: io.netty.util.concurrent.AbstractScheduledEventExecutor.1
                @Override // java.lang.Runnable
                public void run() {
                    AbstractScheduledEventExecutor.this.d().add(cVar);
                }
            });
        }
        return cVar;
    }

    public final void b(final c<?> cVar) {
        if (inEventLoop()) {
            d().remove(cVar);
        } else {
            execute(new OneTimeTask() { // from class: io.netty.util.concurrent.AbstractScheduledEventExecutor.2
                @Override // java.lang.Runnable
                public void run() {
                    AbstractScheduledEventExecutor.this.b(cVar);
                }
            });
        }
    }
}
