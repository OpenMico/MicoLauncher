package com.google.common.util.concurrent;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.AbstractFuture;
import com.google.errorprone.annotations.ForOverride;
import java.lang.Throwable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: AbstractCatchingFuture.java */
@GwtCompatible
/* loaded from: classes2.dex */
public abstract class a<V, X extends Throwable, F, T> extends AbstractFuture.h<V> implements Runnable {
    @NullableDecl
    ListenableFuture<? extends V> a;
    @NullableDecl
    Class<X> b;
    @NullableDecl
    F c;

    @NullableDecl
    @ForOverride
    abstract T a(F f, X x) throws Exception;

    @ForOverride
    abstract void a(@NullableDecl T t);

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <V, X extends Throwable> ListenableFuture<V> a(ListenableFuture<? extends V> listenableFuture, Class<X> cls, Function<? super X, ? extends V> function, Executor executor) {
        b bVar = new b(listenableFuture, cls, function);
        listenableFuture.addListener(bVar, MoreExecutors.a(executor, bVar));
        return bVar;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <X extends Throwable, V> ListenableFuture<V> a(ListenableFuture<? extends V> listenableFuture, Class<X> cls, AsyncFunction<? super X, ? extends V> asyncFunction, Executor executor) {
        C0123a aVar = new C0123a(listenableFuture, cls, asyncFunction);
        listenableFuture.addListener(aVar, MoreExecutors.a(executor, aVar));
        return aVar;
    }

    a(ListenableFuture<? extends V> listenableFuture, Class<X> cls, F f) {
        this.a = (ListenableFuture) Preconditions.checkNotNull(listenableFuture);
        this.b = (Class) Preconditions.checkNotNull(cls);
        this.c = (F) Preconditions.checkNotNull(f);
    }

    /* JADX WARN: Finally extract failed */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.lang.Runnable
    public final void run() {
        Object obj;
        Throwable th;
        ListenableFuture<? extends V> listenableFuture = this.a;
        Class<X> cls = this.b;
        F f = this.c;
        boolean z = true;
        boolean z2 = (listenableFuture == null) | (cls == null);
        if (f != null) {
            z = false;
        }
        if (!(z | z2) && !isCancelled()) {
            this.a = null;
            try {
                obj = Futures.getDone(listenableFuture);
                th = null;
            } catch (ExecutionException e) {
                th = (Throwable) Preconditions.checkNotNull(e.getCause());
                obj = null;
            } catch (Throwable th2) {
                th = th2;
                obj = null;
            }
            if (th == null) {
                set(obj);
            } else if (!p.a(th, cls)) {
                setException(th);
            } else {
                try {
                    Object a = a((a<V, X, F, T>) f, (F) th);
                    this.b = null;
                    this.c = null;
                    a((a<V, X, F, T>) a);
                } catch (Throwable th3) {
                    this.b = null;
                    this.c = null;
                    throw th3;
                }
            }
        }
    }

    @Override // com.google.common.util.concurrent.AbstractFuture
    protected String pendingToString() {
        ListenableFuture<? extends V> listenableFuture = this.a;
        Class<X> cls = this.b;
        F f = this.c;
        String pendingToString = super.pendingToString();
        String str = "";
        if (listenableFuture != null) {
            str = "inputFuture=[" + listenableFuture + "], ";
        }
        if (cls != null && f != null) {
            return str + "exceptionType=[" + cls + "], fallback=[" + f + "]";
        } else if (pendingToString == null) {
            return null;
        } else {
            return str + pendingToString;
        }
    }

    @Override // com.google.common.util.concurrent.AbstractFuture
    protected final void afterDone() {
        a((Future<?>) this.a);
        this.a = null;
        this.b = null;
        this.c = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: AbstractCatchingFuture.java */
    /* renamed from: com.google.common.util.concurrent.a$a  reason: collision with other inner class name */
    /* loaded from: classes2.dex */
    public static final class C0123a<V, X extends Throwable> extends a<V, X, AsyncFunction<? super X, ? extends V>, ListenableFuture<? extends V>> {
        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.common.util.concurrent.a
        /* bridge */ /* synthetic */ Object a(Object obj, Throwable th) throws Exception {
            return a((AsyncFunction<? super AsyncFunction<? super X, ? extends V>, ? extends V>) obj, (AsyncFunction<? super X, ? extends V>) th);
        }

        @Override // com.google.common.util.concurrent.a
        /* bridge */ /* synthetic */ void a(Object obj) {
            a((ListenableFuture) ((ListenableFuture) obj));
        }

        C0123a(ListenableFuture<? extends V> listenableFuture, Class<X> cls, AsyncFunction<? super X, ? extends V> asyncFunction) {
            super(listenableFuture, cls, asyncFunction);
        }

        ListenableFuture<? extends V> a(AsyncFunction<? super X, ? extends V> asyncFunction, X x) throws Exception {
            ListenableFuture<? extends V> apply = asyncFunction.apply(x);
            Preconditions.checkNotNull(apply, "AsyncFunction.apply returned null instead of a Future. Did you mean to return immediateFuture(null)?");
            return apply;
        }

        void a(ListenableFuture<? extends V> listenableFuture) {
            setFuture(listenableFuture);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: AbstractCatchingFuture.java */
    /* loaded from: classes2.dex */
    public static final class b<V, X extends Throwable> extends a<V, X, Function<? super X, ? extends V>, V> {
        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.common.util.concurrent.a
        @NullableDecl
        /* bridge */ /* synthetic */ Object a(Object obj, Throwable th) throws Exception {
            return a((Function<? super Function<? super X, ? extends V>, ? extends V>) obj, (Function<? super X, ? extends V>) th);
        }

        b(ListenableFuture<? extends V> listenableFuture, Class<X> cls, Function<? super X, ? extends V> function) {
            super(listenableFuture, cls, function);
        }

        @NullableDecl
        V a(Function<? super X, ? extends V> function, X x) throws Exception {
            return (V) function.apply(x);
        }

        @Override // com.google.common.util.concurrent.a
        void a(@NullableDecl V v) {
            set(v);
        }
    }
}
