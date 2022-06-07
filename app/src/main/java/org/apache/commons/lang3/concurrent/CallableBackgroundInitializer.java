package org.apache.commons.lang3.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

/* loaded from: classes5.dex */
public class CallableBackgroundInitializer<T> extends BackgroundInitializer<T> {
    private final Callable<T> a;

    public CallableBackgroundInitializer(Callable<T> callable) {
        a(callable);
        this.a = callable;
    }

    public CallableBackgroundInitializer(Callable<T> callable, ExecutorService executorService) {
        super(executorService);
        a(callable);
        this.a = callable;
    }

    @Override // org.apache.commons.lang3.concurrent.BackgroundInitializer
    protected T initialize() throws Exception {
        return this.a.call();
    }

    private void a(Callable<T> callable) {
        if (callable == null) {
            throw new IllegalArgumentException("Callable must not be null!");
        }
    }
}
