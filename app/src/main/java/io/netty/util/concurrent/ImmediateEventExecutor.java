package io.netty.util.concurrent;

import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public final class ImmediateEventExecutor extends AbstractEventExecutor {
    private final Future<?> d = new FailedFuture(GlobalEventExecutor.INSTANCE, new UnsupportedOperationException());
    private static final InternalLogger a = InternalLoggerFactory.getInstance(ImmediateEventExecutor.class);
    public static final ImmediateEventExecutor INSTANCE = new ImmediateEventExecutor();
    private static final FastThreadLocal<Queue<Runnable>> b = new FastThreadLocal<Queue<Runnable>>() { // from class: io.netty.util.concurrent.ImmediateEventExecutor.1
        /* JADX INFO: Access modifiers changed from: protected */
        /* renamed from: a */
        public Queue<Runnable> initialValue() throws Exception {
            return new ArrayDeque();
        }
    };
    private static final FastThreadLocal<Boolean> c = new FastThreadLocal<Boolean>() { // from class: io.netty.util.concurrent.ImmediateEventExecutor.2
        /* JADX INFO: Access modifiers changed from: protected */
        /* renamed from: a */
        public Boolean initialValue() throws Exception {
            return false;
        }
    };

    @Override // java.util.concurrent.ExecutorService
    public boolean awaitTermination(long j, TimeUnit timeUnit) {
        return false;
    }

    @Override // io.netty.util.concurrent.AbstractEventExecutor, io.netty.util.concurrent.EventExecutor
    public boolean inEventLoop() {
        return true;
    }

    @Override // io.netty.util.concurrent.EventExecutor
    public boolean inEventLoop(Thread thread) {
        return true;
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

    @Override // io.netty.util.concurrent.AbstractEventExecutor, java.util.concurrent.ExecutorService, io.netty.util.concurrent.EventExecutorGroup
    @Deprecated
    public void shutdown() {
    }

    private ImmediateEventExecutor() {
    }

    @Override // io.netty.util.concurrent.EventExecutorGroup
    public Future<?> shutdownGracefully(long j, long j2, TimeUnit timeUnit) {
        return terminationFuture();
    }

    @Override // io.netty.util.concurrent.EventExecutorGroup
    public Future<?> terminationFuture() {
        return this.d;
    }

    @Override // java.util.concurrent.Executor
    public void execute(Runnable runnable) {
        Runnable poll;
        if (runnable == null) {
            throw new NullPointerException("command");
        } else if (!c.get().booleanValue()) {
            c.set(true);
            try {
                runnable.run();
                while (true) {
                    if (poll == null) {
                        return;
                    }
                }
            } finally {
                Queue<Runnable> queue = b.get();
                while (true) {
                    poll = queue.poll();
                    if (poll == null) {
                        break;
                    }
                    try {
                        poll.run();
                    } catch (Throwable th) {
                        a.info("Throwable caught while executing Runnable {}", poll, th);
                    }
                }
                c.set(false);
            }
        } else {
            b.get().add(runnable);
        }
    }

    @Override // io.netty.util.concurrent.AbstractEventExecutor, io.netty.util.concurrent.EventExecutor
    public <V> Promise<V> newPromise() {
        return new b(this);
    }

    @Override // io.netty.util.concurrent.AbstractEventExecutor, io.netty.util.concurrent.EventExecutor
    public <V> ProgressivePromise<V> newProgressivePromise() {
        return new a(this);
    }

    /* loaded from: classes4.dex */
    static class b<V> extends DefaultPromise<V> {
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // io.netty.util.concurrent.DefaultPromise
        public void checkDeadLock() {
        }

        b(EventExecutor eventExecutor) {
            super(eventExecutor);
        }
    }

    /* loaded from: classes4.dex */
    static class a<V> extends DefaultProgressivePromise<V> {
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // io.netty.util.concurrent.DefaultPromise
        public void checkDeadLock() {
        }

        a(EventExecutor eventExecutor) {
            super(eventExecutor);
        }
    }
}
