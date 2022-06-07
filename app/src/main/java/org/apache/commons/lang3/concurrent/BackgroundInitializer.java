package org.apache.commons.lang3.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/* loaded from: classes5.dex */
public abstract class BackgroundInitializer<T> implements ConcurrentInitializer<T> {
    private ExecutorService a;
    private ExecutorService b;
    private Future<T> c;

    /* JADX INFO: Access modifiers changed from: protected */
    public int getTaskCount() {
        return 1;
    }

    protected abstract T initialize() throws Exception;

    /* JADX INFO: Access modifiers changed from: protected */
    public BackgroundInitializer() {
        this(null);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public BackgroundInitializer(ExecutorService executorService) {
        setExternalExecutor(executorService);
    }

    public final synchronized ExecutorService getExternalExecutor() {
        return this.a;
    }

    public synchronized boolean isStarted() {
        return this.c != null;
    }

    public final synchronized void setExternalExecutor(ExecutorService executorService) {
        if (!isStarted()) {
            this.a = executorService;
        } else {
            throw new IllegalStateException("Cannot set ExecutorService after start()!");
        }
    }

    public synchronized boolean start() {
        ExecutorService executorService;
        if (isStarted()) {
            return false;
        }
        this.b = getExternalExecutor();
        if (this.b == null) {
            executorService = a();
            this.b = executorService;
        } else {
            executorService = null;
        }
        this.c = this.b.submit(a(executorService));
        return true;
    }

    @Override // org.apache.commons.lang3.concurrent.ConcurrentInitializer
    public T get() throws ConcurrentException {
        try {
            return getFuture().get();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new ConcurrentException(e);
        } catch (ExecutionException e2) {
            ConcurrentUtils.handleCause(e2);
            return null;
        }
    }

    public synchronized Future<T> getFuture() {
        if (this.c != null) {
        } else {
            throw new IllegalStateException("start() must be called first!");
        }
        return this.c;
    }

    protected final synchronized ExecutorService getActiveExecutor() {
        return this.b;
    }

    private Callable<T> a(ExecutorService executorService) {
        return new a(executorService);
    }

    private ExecutorService a() {
        return Executors.newFixedThreadPool(getTaskCount());
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes5.dex */
    public class a implements Callable<T> {
        private final ExecutorService b;

        public a(ExecutorService executorService) {
            this.b = executorService;
        }

        @Override // java.util.concurrent.Callable
        public T call() throws Exception {
            try {
                return (T) BackgroundInitializer.this.initialize();
            } finally {
                ExecutorService executorService = this.b;
                if (executorService != null) {
                    executorService.shutdown();
                }
            }
        }
    }
}
