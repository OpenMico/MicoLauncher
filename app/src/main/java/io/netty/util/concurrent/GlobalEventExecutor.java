package io.netty.util.concurrent;

import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes4.dex */
public final class GlobalEventExecutor extends AbstractScheduledEventExecutor {
    volatile Thread e;
    private static final InternalLogger f = InternalLoggerFactory.getInstance(GlobalEventExecutor.class);
    private static final long g = TimeUnit.SECONDS.toNanos(1);
    public static final GlobalEventExecutor INSTANCE = new GlobalEventExecutor();
    final BlockingQueue<Runnable> a = new LinkedBlockingQueue();
    final c<Void> d = new c<>(this, Executors.callable(new Runnable() { // from class: io.netty.util.concurrent.GlobalEventExecutor.1
        @Override // java.lang.Runnable
        public void run() {
        }
    }, null), c.a(g), -g);
    private final ThreadFactory h = new DefaultThreadFactory(getClass());
    private final a i = new a();
    private final AtomicBoolean j = new AtomicBoolean();
    private final Future<?> k = new FailedFuture(this, new UnsupportedOperationException());

    @Override // java.util.concurrent.ExecutorService
    public boolean awaitTermination(long j, TimeUnit timeUnit) {
        return false;
    }

    @Override // java.util.concurrent.ExecutorService
    public boolean isShutdown() {
        return false;
    }

    @Override // io.netty.util.concurrent.EventExecutorGroup
    public boolean isShuttingDown() {
        return false;
    }

    @Override // java.util.concurrent.ExecutorService
    public boolean isTerminated() {
        return false;
    }

    private GlobalEventExecutor() {
        d().add(this.d);
    }

    Runnable a() {
        Runnable poll;
        BlockingQueue<Runnable> blockingQueue = this.a;
        do {
            c<?> e = e();
            if (e == null) {
                try {
                    return blockingQueue.take();
                } catch (InterruptedException unused) {
                    return null;
                }
            } else {
                long d = e.d();
                if (d > 0) {
                    try {
                        poll = blockingQueue.poll(d, TimeUnit.NANOSECONDS);
                    } catch (InterruptedException unused2) {
                        return null;
                    }
                } else {
                    poll = blockingQueue.poll();
                }
                if (poll == null) {
                    c();
                    poll = blockingQueue.poll();
                    continue;
                }
            }
        } while (poll == null);
        return poll;
    }

    private void c() {
        if (hasScheduledTasks()) {
            long nanoTime = AbstractScheduledEventExecutor.nanoTime();
            while (true) {
                Runnable pollScheduledTask = pollScheduledTask(nanoTime);
                if (pollScheduledTask != null) {
                    this.a.add(pollScheduledTask);
                } else {
                    return;
                }
            }
        }
    }

    public int pendingTasks() {
        return this.a.size();
    }

    private void a(Runnable runnable) {
        if (runnable != null) {
            this.a.add(runnable);
            return;
        }
        throw new NullPointerException("task");
    }

    @Override // io.netty.util.concurrent.EventExecutor
    public boolean inEventLoop(Thread thread) {
        return thread == this.e;
    }

    @Override // io.netty.util.concurrent.EventExecutorGroup
    public Future<?> shutdownGracefully(long j, long j2, TimeUnit timeUnit) {
        return terminationFuture();
    }

    @Override // io.netty.util.concurrent.EventExecutorGroup
    public Future<?> terminationFuture() {
        return this.k;
    }

    @Override // io.netty.util.concurrent.AbstractEventExecutor, java.util.concurrent.ExecutorService, io.netty.util.concurrent.EventExecutorGroup
    @Deprecated
    public void shutdown() {
        throw new UnsupportedOperationException();
    }

    public boolean awaitInactivity(long j, TimeUnit timeUnit) throws InterruptedException {
        if (timeUnit != null) {
            Thread thread = this.e;
            if (thread != null) {
                thread.join(timeUnit.toMillis(j));
                return !thread.isAlive();
            }
            throw new IllegalStateException("thread was not started");
        }
        throw new NullPointerException("unit");
    }

    @Override // java.util.concurrent.Executor
    public void execute(Runnable runnable) {
        if (runnable != null) {
            a(runnable);
            if (!inEventLoop()) {
                f();
                return;
            }
            return;
        }
        throw new NullPointerException("task");
    }

    private void f() {
        if (this.j.compareAndSet(false, true)) {
            Thread newThread = this.h.newThread(this.i);
            this.e = newThread;
            newThread.start();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public final class a implements Runnable {
        static final /* synthetic */ boolean a = !GlobalEventExecutor.class.desiredAssertionStatus();

        a() {
        }

        @Override // java.lang.Runnable
        public void run() {
            while (true) {
                Runnable a2 = GlobalEventExecutor.this.a();
                if (a2 != null) {
                    try {
                        a2.run();
                    } catch (Throwable th) {
                        GlobalEventExecutor.f.warn("Unexpected exception from the global event executor: ", th);
                    }
                    if (a2 != GlobalEventExecutor.this.d) {
                        continue;
                    }
                }
                Queue queue = GlobalEventExecutor.this.b;
                if (GlobalEventExecutor.this.a.isEmpty() && (queue == null || queue.size() == 1)) {
                    boolean compareAndSet = GlobalEventExecutor.this.j.compareAndSet(true, false);
                    if (!a && !compareAndSet) {
                        throw new AssertionError();
                    } else if ((GlobalEventExecutor.this.a.isEmpty() && (queue == null || queue.size() == 1)) || !GlobalEventExecutor.this.j.compareAndSet(false, true)) {
                        return;
                    }
                }
            }
        }
    }
}
