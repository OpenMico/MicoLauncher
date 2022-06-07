package com.google.common.util.concurrent;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableCollection;
import com.google.common.util.concurrent.c;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: CombinedFuture.java */
@GwtCompatible
/* loaded from: classes2.dex */
public final class f<V> extends c<Object, V> {
    /* JADX INFO: Access modifiers changed from: package-private */
    public f(ImmutableCollection<? extends ListenableFuture<?>> immutableCollection, boolean z, Executor executor, AsyncCallable<V> asyncCallable) {
        a((c.a) new d(immutableCollection, z, new a(asyncCallable, executor)));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public f(ImmutableCollection<? extends ListenableFuture<?>> immutableCollection, boolean z, Executor executor, Callable<V> callable) {
        a((c.a) new d(immutableCollection, z, new b(callable, executor)));
    }

    /* compiled from: CombinedFuture.java */
    /* loaded from: classes2.dex */
    private final class d extends c<Object, V>.a {
        private c c;

        @Override // com.google.common.util.concurrent.c.a
        void a(boolean z, int i, @NullableDecl Object obj) {
        }

        d(ImmutableCollection<? extends ListenableFuture<?>> immutableCollection, boolean z, c cVar) {
            super(immutableCollection, z, false);
            this.c = cVar;
        }

        @Override // com.google.common.util.concurrent.c.a
        void b() {
            c cVar = this.c;
            if (cVar != null) {
                cVar.e();
            } else {
                Preconditions.checkState(f.this.isDone());
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.google.common.util.concurrent.c.a
        public void a() {
            super.a();
            this.c = null;
        }

        @Override // com.google.common.util.concurrent.c.a
        void c() {
            c cVar = this.c;
            if (cVar != null) {
                cVar.f();
            }
        }
    }

    /* compiled from: CombinedFuture.java */
    /* loaded from: classes2.dex */
    private abstract class c<T> extends n<T> {
        private final Executor listenerExecutor;
        boolean thrownByExecute = true;

        abstract void a(T t);

        public c(Executor executor) {
            this.listenerExecutor = (Executor) Preconditions.checkNotNull(executor);
        }

        @Override // com.google.common.util.concurrent.n
        final boolean d() {
            return f.this.isDone();
        }

        final void e() {
            try {
                this.listenerExecutor.execute(this);
            } catch (RejectedExecutionException e) {
                if (this.thrownByExecute) {
                    f.this.setException(e);
                }
            }
        }

        @Override // com.google.common.util.concurrent.n
        final void a(T t, Throwable th) {
            if (th == null) {
                a(t);
            } else if (th instanceof ExecutionException) {
                f.this.setException(th.getCause());
            } else if (th instanceof CancellationException) {
                f.this.cancel(false);
            } else {
                f.this.setException(th);
            }
        }
    }

    /* compiled from: CombinedFuture.java */
    /* loaded from: classes2.dex */
    private final class a extends f<V>.c {
        private final AsyncCallable<V> callable;

        @Override // com.google.common.util.concurrent.f.c
        /* bridge */ /* synthetic */ void a(Object obj) {
            a((ListenableFuture) ((ListenableFuture) obj));
        }

        public a(AsyncCallable<V> asyncCallable, Executor executor) {
            super(executor);
            this.callable = (AsyncCallable) Preconditions.checkNotNull(asyncCallable);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: a */
        public ListenableFuture<V> c() throws Exception {
            this.thrownByExecute = false;
            return (ListenableFuture) Preconditions.checkNotNull(this.callable.call(), "AsyncCallable.call returned null instead of a Future. Did you mean to return immediateFuture(null)?");
        }

        void a(ListenableFuture<V> listenableFuture) {
            f.this.setFuture(listenableFuture);
        }

        @Override // com.google.common.util.concurrent.n
        String b() {
            return this.callable.toString();
        }
    }

    /* compiled from: CombinedFuture.java */
    /* loaded from: classes2.dex */
    private final class b extends f<V>.c {
        private final Callable<V> callable;

        public b(Callable<V> callable, Executor executor) {
            super(executor);
            this.callable = (Callable) Preconditions.checkNotNull(callable);
        }

        @Override // com.google.common.util.concurrent.n
        V c() throws Exception {
            this.thrownByExecute = false;
            return this.callable.call();
        }

        @Override // com.google.common.util.concurrent.f.c
        void a(V v) {
            f.this.set(v);
        }

        @Override // com.google.common.util.concurrent.n
        String b() {
            return this.callable.toString();
        }
    }
}
