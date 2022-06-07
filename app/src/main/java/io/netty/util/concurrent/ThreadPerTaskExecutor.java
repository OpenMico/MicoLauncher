package io.netty.util.concurrent;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadFactory;

/* loaded from: classes4.dex */
public final class ThreadPerTaskExecutor implements Executor {
    private final ThreadFactory a;

    public ThreadPerTaskExecutor(ThreadFactory threadFactory) {
        if (threadFactory != null) {
            this.a = threadFactory;
            return;
        }
        throw new NullPointerException("threadFactory");
    }

    @Override // java.util.concurrent.Executor
    public void execute(Runnable runnable) {
        this.a.newThread(runnable).start();
    }
}
