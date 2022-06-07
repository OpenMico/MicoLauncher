package io.netty.util.concurrent;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadFactory;

/* loaded from: classes4.dex */
public class DefaultEventExecutorGroup extends MultithreadEventExecutorGroup {
    public DefaultEventExecutorGroup(int i) {
        this(i, null);
    }

    public DefaultEventExecutorGroup(int i, ThreadFactory threadFactory) {
        super(i, threadFactory, new Object[0]);
    }

    @Override // io.netty.util.concurrent.MultithreadEventExecutorGroup
    protected EventExecutor newChild(Executor executor, Object... objArr) throws Exception {
        return new DefaultEventExecutor(this, executor);
    }
}
