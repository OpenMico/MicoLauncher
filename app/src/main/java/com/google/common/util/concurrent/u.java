package com.google.common.util.concurrent;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableList;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* compiled from: WrappingExecutorService.java */
@CanIgnoreReturnValue
@GwtIncompatible
/* loaded from: classes2.dex */
abstract class u implements ExecutorService {
    private final ExecutorService a;

    protected abstract <T> Callable<T> a(Callable<T> callable);

    /* JADX INFO: Access modifiers changed from: protected */
    public u(ExecutorService executorService) {
        this.a = (ExecutorService) Preconditions.checkNotNull(executorService);
    }

    protected Runnable a(Runnable runnable) {
        final Callable a = a(Executors.callable(runnable, null));
        return new Runnable() { // from class: com.google.common.util.concurrent.u.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    a.call();
                } catch (Exception e) {
                    Throwables.throwIfUnchecked(e);
                    throw new RuntimeException(e);
                }
            }
        };
    }

    private <T> ImmutableList<Callable<T>> a(Collection<? extends Callable<T>> collection) {
        ImmutableList.Builder builder = ImmutableList.builder();
        for (Callable<T> callable : collection) {
            builder.add((ImmutableList.Builder) a(callable));
        }
        return builder.build();
    }

    @Override // java.util.concurrent.Executor
    public final void execute(Runnable runnable) {
        this.a.execute(a(runnable));
    }

    @Override // java.util.concurrent.ExecutorService
    public final <T> Future<T> submit(Callable<T> callable) {
        return this.a.submit(a((Callable) Preconditions.checkNotNull(callable)));
    }

    @Override // java.util.concurrent.ExecutorService
    public final Future<?> submit(Runnable runnable) {
        return this.a.submit(a(runnable));
    }

    @Override // java.util.concurrent.ExecutorService
    public final <T> Future<T> submit(Runnable runnable, T t) {
        return this.a.submit(a(runnable), t);
    }

    @Override // java.util.concurrent.ExecutorService
    public final <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> collection) throws InterruptedException {
        return this.a.invokeAll(a(collection));
    }

    @Override // java.util.concurrent.ExecutorService
    public final <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> collection, long j, TimeUnit timeUnit) throws InterruptedException {
        return this.a.invokeAll(a(collection), j, timeUnit);
    }

    @Override // java.util.concurrent.ExecutorService
    public final <T> T invokeAny(Collection<? extends Callable<T>> collection) throws InterruptedException, ExecutionException {
        return (T) this.a.invokeAny(a(collection));
    }

    @Override // java.util.concurrent.ExecutorService
    public final <T> T invokeAny(Collection<? extends Callable<T>> collection, long j, TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException {
        return (T) this.a.invokeAny(a(collection), j, timeUnit);
    }

    @Override // java.util.concurrent.ExecutorService
    public final void shutdown() {
        this.a.shutdown();
    }

    @Override // java.util.concurrent.ExecutorService
    public final List<Runnable> shutdownNow() {
        return this.a.shutdownNow();
    }

    @Override // java.util.concurrent.ExecutorService
    public final boolean isShutdown() {
        return this.a.isShutdown();
    }

    @Override // java.util.concurrent.ExecutorService
    public final boolean isTerminated() {
        return this.a.isTerminated();
    }

    @Override // java.util.concurrent.ExecutorService
    public final boolean awaitTermination(long j, TimeUnit timeUnit) throws InterruptedException {
        return this.a.awaitTermination(j, timeUnit);
    }
}
