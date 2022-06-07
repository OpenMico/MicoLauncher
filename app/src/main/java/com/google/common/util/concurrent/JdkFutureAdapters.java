package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicBoolean;

@Beta
@GwtIncompatible
/* loaded from: classes2.dex */
public final class JdkFutureAdapters {
    public static <V> ListenableFuture<V> listenInPoolThread(Future<V> future) {
        if (future instanceof ListenableFuture) {
            return (ListenableFuture) future;
        }
        return new a(future);
    }

    public static <V> ListenableFuture<V> listenInPoolThread(Future<V> future, Executor executor) {
        Preconditions.checkNotNull(executor);
        if (future instanceof ListenableFuture) {
            return (ListenableFuture) future;
        }
        return new a(future, executor);
    }

    /* loaded from: classes2.dex */
    private static class a<V> extends ForwardingFuture<V> implements ListenableFuture<V> {
        private static final ThreadFactory a = new ThreadFactoryBuilder().setDaemon(true).setNameFormat("ListenableFutureAdapter-thread-%d").build();
        private static final Executor b = Executors.newCachedThreadPool(a);
        private final Executor c;
        private final ExecutionList d;
        private final AtomicBoolean e;
        private final Future<V> f;

        a(Future<V> future) {
            this(future, b);
        }

        a(Future<V> future, Executor executor) {
            this.d = new ExecutionList();
            this.e = new AtomicBoolean(false);
            this.f = (Future) Preconditions.checkNotNull(future);
            this.c = (Executor) Preconditions.checkNotNull(executor);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.common.util.concurrent.ForwardingFuture, com.google.common.collect.ForwardingObject
        public Future<V> delegate() {
            return this.f;
        }

        @Override // com.google.common.util.concurrent.ListenableFuture
        public void addListener(Runnable runnable, Executor executor) {
            this.d.add(runnable, executor);
            if (!this.e.compareAndSet(false, true)) {
                return;
            }
            if (this.f.isDone()) {
                this.d.execute();
            } else {
                this.c.execute(new Runnable() { // from class: com.google.common.util.concurrent.JdkFutureAdapters.a.1
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            Uninterruptibles.getUninterruptibly(a.this.f);
                        } catch (Throwable unused) {
                        }
                        a.this.d.execute();
                    }
                });
            }
        }
    }

    private JdkFutureAdapters() {
    }
}
