package com.google.common.util.concurrent;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.AbstractFuture;
import com.google.errorprone.annotations.ForOverride;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: AbstractTransformFuture.java */
@GwtCompatible
/* loaded from: classes2.dex */
public abstract class b<I, O, F, T> extends AbstractFuture.h<O> implements Runnable {
    @NullableDecl
    ListenableFuture<? extends I> a;
    @NullableDecl
    F b;

    @NullableDecl
    @ForOverride
    abstract T a(F f, @NullableDecl I i) throws Exception;

    @ForOverride
    abstract void a(@NullableDecl T t);

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <I, O> ListenableFuture<O> a(ListenableFuture<I> listenableFuture, AsyncFunction<? super I, ? extends O> asyncFunction, Executor executor) {
        Preconditions.checkNotNull(executor);
        a aVar = new a(listenableFuture, asyncFunction);
        listenableFuture.addListener(aVar, MoreExecutors.a(executor, aVar));
        return aVar;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <I, O> ListenableFuture<O> a(ListenableFuture<I> listenableFuture, Function<? super I, ? extends O> function, Executor executor) {
        Preconditions.checkNotNull(function);
        C0124b bVar = new C0124b(listenableFuture, function);
        listenableFuture.addListener(bVar, MoreExecutors.a(executor, bVar));
        return bVar;
    }

    b(ListenableFuture<? extends I> listenableFuture, F f) {
        this.a = (ListenableFuture) Preconditions.checkNotNull(listenableFuture);
        this.b = (F) Preconditions.checkNotNull(f);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.lang.Runnable
    public final void run() {
        ListenableFuture<? extends I> listenableFuture = this.a;
        F f = this.b;
        boolean z = true;
        boolean isCancelled = isCancelled() | (listenableFuture == null);
        if (f != null) {
            z = false;
        }
        if (!isCancelled && !z) {
            this.a = null;
            try {
                try {
                    try {
                        Object a2 = a((b<I, O, F, T>) f, (F) Futures.getDone(listenableFuture));
                        this.b = null;
                        a((b<I, O, F, T>) a2);
                    } catch (UndeclaredThrowableException e) {
                        setException(e.getCause());
                        this.b = null;
                    } catch (Throwable th) {
                        setException(th);
                        this.b = null;
                    }
                } catch (Throwable th2) {
                    this.b = null;
                    throw th2;
                }
            } catch (Error e2) {
                setException(e2);
            } catch (CancellationException unused) {
                cancel(false);
            } catch (RuntimeException e3) {
                setException(e3);
            } catch (ExecutionException e4) {
                setException(e4.getCause());
            }
        }
    }

    @Override // com.google.common.util.concurrent.AbstractFuture
    protected final void afterDone() {
        a((Future<?>) this.a);
        this.a = null;
        this.b = null;
    }

    @Override // com.google.common.util.concurrent.AbstractFuture
    protected String pendingToString() {
        ListenableFuture<? extends I> listenableFuture = this.a;
        F f = this.b;
        String pendingToString = super.pendingToString();
        String str = "";
        if (listenableFuture != null) {
            str = "inputFuture=[" + listenableFuture + "], ";
        }
        if (f != null) {
            return str + "function=[" + f + "]";
        } else if (pendingToString == null) {
            return null;
        } else {
            return str + pendingToString;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: AbstractTransformFuture.java */
    /* loaded from: classes2.dex */
    public static final class a<I, O> extends b<I, O, AsyncFunction<? super I, ? extends O>, ListenableFuture<? extends O>> {
        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.common.util.concurrent.b
        /* bridge */ /* synthetic */ Object a(Object obj, @NullableDecl Object obj2) throws Exception {
            return a((AsyncFunction<? super AsyncFunction<? super I, ? extends O>, ? extends O>) obj, (AsyncFunction<? super I, ? extends O>) obj2);
        }

        @Override // com.google.common.util.concurrent.b
        /* bridge */ /* synthetic */ void a(Object obj) {
            a((ListenableFuture) ((ListenableFuture) obj));
        }

        a(ListenableFuture<? extends I> listenableFuture, AsyncFunction<? super I, ? extends O> asyncFunction) {
            super(listenableFuture, asyncFunction);
        }

        /* JADX WARN: Multi-variable type inference failed */
        ListenableFuture<? extends O> a(AsyncFunction<? super I, ? extends O> asyncFunction, @NullableDecl I i) throws Exception {
            ListenableFuture<? extends O> apply = asyncFunction.apply(i);
            Preconditions.checkNotNull(apply, "AsyncFunction.apply returned null instead of a Future. Did you mean to return immediateFuture(null)?");
            return apply;
        }

        /* JADX WARN: Multi-variable type inference failed */
        void a(ListenableFuture<? extends O> listenableFuture) {
            setFuture(listenableFuture);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: AbstractTransformFuture.java */
    /* renamed from: com.google.common.util.concurrent.b$b  reason: collision with other inner class name */
    /* loaded from: classes2.dex */
    public static final class C0124b<I, O> extends b<I, O, Function<? super I, ? extends O>, O> {
        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.common.util.concurrent.b
        @NullableDecl
        /* bridge */ /* synthetic */ Object a(Object obj, @NullableDecl Object obj2) throws Exception {
            return a((Function<? super Function<? super I, ? extends O>, ? extends O>) obj, (Function<? super I, ? extends O>) obj2);
        }

        C0124b(ListenableFuture<? extends I> listenableFuture, Function<? super I, ? extends O> function) {
            super(listenableFuture, function);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @NullableDecl
        O a(Function<? super I, ? extends O> function, @NullableDecl I i) {
            return (O) function.apply(i);
        }

        @Override // com.google.common.util.concurrent.b
        void a(@NullableDecl O o) {
            set(o);
        }
    }
}
