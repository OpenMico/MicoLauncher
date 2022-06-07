package io.netty.channel;

import io.netty.util.concurrent.DefaultThreadFactory;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadFactory;

/* loaded from: classes4.dex */
public class DefaultEventLoop extends SingleThreadEventLoop {
    public DefaultEventLoop() {
        this((EventLoopGroup) null);
    }

    public DefaultEventLoop(ThreadFactory threadFactory) {
        this((EventLoopGroup) null, threadFactory);
    }

    public DefaultEventLoop(Executor executor) {
        this((EventLoopGroup) null, executor);
    }

    public DefaultEventLoop(EventLoopGroup eventLoopGroup) {
        this(eventLoopGroup, new DefaultThreadFactory(DefaultEventLoop.class));
    }

    public DefaultEventLoop(EventLoopGroup eventLoopGroup, ThreadFactory threadFactory) {
        super(eventLoopGroup, threadFactory, true);
    }

    public DefaultEventLoop(EventLoopGroup eventLoopGroup, Executor executor) {
        super(eventLoopGroup, executor, true);
    }

    @Override // io.netty.util.concurrent.SingleThreadEventExecutor
    protected void run() {
        do {
            Runnable takeTask = takeTask();
            if (takeTask != null) {
                takeTask.run();
                updateLastExecutionTime();
            }
        } while (!confirmShutdown());
    }
}
