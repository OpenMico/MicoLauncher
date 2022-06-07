package io.netty.util.concurrent;

import java.util.concurrent.Executor;

/* loaded from: classes4.dex */
public final class ImmediateExecutor implements Executor {
    public static final ImmediateExecutor INSTANCE = new ImmediateExecutor();

    private ImmediateExecutor() {
    }

    @Override // java.util.concurrent.Executor
    public void execute(Runnable runnable) {
        if (runnable != null) {
            runnable.run();
            return;
        }
        throw new NullPointerException("command");
    }
}
