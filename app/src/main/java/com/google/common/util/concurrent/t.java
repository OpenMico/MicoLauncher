package com.google.common.util.concurrent;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.AbstractFuture;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.RunnableFuture;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: TrustedListenableFutureTask.java */
@GwtCompatible
/* loaded from: classes2.dex */
public class t<V> extends AbstractFuture.h<V> implements RunnableFuture<V> {
    private volatile n<?> a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <V> t<V> a(AsyncCallable<V> asyncCallable) {
        return new t<>(asyncCallable);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <V> t<V> a(Callable<V> callable) {
        return new t<>(callable);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <V> t<V> a(Runnable runnable, @NullableDecl V v) {
        return new t<>(Executors.callable(runnable, v));
    }

    t(Callable<V> callable) {
        this.a = new b(callable);
    }

    t(AsyncCallable<V> asyncCallable) {
        this.a = new a(asyncCallable);
    }

    @Override // java.util.concurrent.RunnableFuture, java.lang.Runnable
    public void run() {
        n<?> nVar = this.a;
        if (nVar != null) {
            nVar.run();
        }
        this.a = null;
    }

    @Override // com.google.common.util.concurrent.AbstractFuture
    protected void afterDone() {
        n<?> nVar;
        super.afterDone();
        if (wasInterrupted() && (nVar = this.a) != null) {
            nVar.f();
        }
        this.a = null;
    }

    @Override // com.google.common.util.concurrent.AbstractFuture
    protected String pendingToString() {
        n<?> nVar = this.a;
        if (nVar == null) {
            return super.pendingToString();
        }
        return "task=[" + nVar + "]";
    }

    /* compiled from: TrustedListenableFutureTask.java */
    /* loaded from: classes2.dex */
    private final class b extends n<V> {
        private final Callable<V> callable;

        b(Callable<V> callable) {
            this.callable = (Callable) Preconditions.checkNotNull(callable);
        }

        @Override // com.google.common.util.concurrent.n
        final boolean d() {
            return t.this.isDone();
        }

        @Override // com.google.common.util.concurrent.n
        V c() throws Exception {
            return this.callable.call();
        }

        @Override // com.google.common.util.concurrent.n
        void a(V v, Throwable th) {
            if (th == null) {
                t.this.set(v);
            } else {
                t.this.setException(th);
            }
        }

        @Override // com.google.common.util.concurrent.n
        String b() {
            return this.callable.toString();
        }
    }

    /* compiled from: TrustedListenableFutureTask.java */
    /* loaded from: classes2.dex */
    private final class a extends n<ListenableFuture<V>> {
        private final AsyncCallable<V> callable;

        @Override // com.google.common.util.concurrent.n
        /* bridge */ /* synthetic */ void a(Object obj, Throwable th) {
            a((ListenableFuture) ((ListenableFuture) obj), th);
        }

        a(AsyncCallable<V> asyncCallable) {
            this.callable = (AsyncCallable) Preconditions.checkNotNull(asyncCallable);
        }

        @Override // com.google.common.util.concurrent.n
        final boolean d() {
            return t.this.isDone();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: a */
        public ListenableFuture<V> c() throws Exception {
            return (ListenableFuture) Preconditions.checkNotNull(this.callable.call(), "AsyncCallable.call returned null instead of a Future. Did you mean to return immediateFuture(null)?");
        }

        void a(ListenableFuture<V> listenableFuture, Throwable th) {
            if (th == null) {
                t.this.setFuture(listenableFuture);
            } else {
                t.this.setException(th);
            }
        }

        @Override // com.google.common.util.concurrent.n
        String b() {
            return this.callable.toString();
        }
    }
}
