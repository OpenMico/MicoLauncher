package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Function;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.util.concurrent.AbstractFuture;
import com.google.common.util.concurrent.e;
import com.google.common.util.concurrent.m;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.DoNotCall;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@Beta
@GwtCompatible(emulated = true)
/* loaded from: classes2.dex */
public final class Futures extends l {
    private Futures() {
    }

    @GwtIncompatible
    @Deprecated
    public static <V, X extends Exception> CheckedFuture<V, X> makeChecked(ListenableFuture<V> listenableFuture, Function<? super Exception, X> function) {
        return new d((ListenableFuture) Preconditions.checkNotNull(listenableFuture), function);
    }

    public static <V> ListenableFuture<V> immediateFuture(@NullableDecl V v) {
        if (v == null) {
            return m.e.a;
        }
        return new m.e(v);
    }

    @GwtIncompatible
    @Deprecated
    public static <V, X extends Exception> CheckedFuture<V, X> immediateCheckedFuture(@NullableDecl V v) {
        return new m.d(v);
    }

    public static <V> ListenableFuture<V> immediateFailedFuture(Throwable th) {
        Preconditions.checkNotNull(th);
        return new m.c(th);
    }

    public static <V> ListenableFuture<V> immediateCancelledFuture() {
        return new m.a();
    }

    @GwtIncompatible
    @Deprecated
    public static <V, X extends Exception> CheckedFuture<V, X> immediateFailedCheckedFuture(X x) {
        Preconditions.checkNotNull(x);
        return new m.b(x);
    }

    public static <O> ListenableFuture<O> submitAsync(AsyncCallable<O> asyncCallable, Executor executor) {
        t a2 = t.a((AsyncCallable) asyncCallable);
        executor.execute(a2);
        return a2;
    }

    @GwtIncompatible
    public static <O> ListenableFuture<O> scheduleAsync(AsyncCallable<O> asyncCallable, long j, TimeUnit timeUnit, ScheduledExecutorService scheduledExecutorService) {
        t a2 = t.a((AsyncCallable) asyncCallable);
        final ScheduledFuture<?> schedule = scheduledExecutorService.schedule(a2, j, timeUnit);
        a2.addListener(new Runnable() { // from class: com.google.common.util.concurrent.Futures.1
            @Override // java.lang.Runnable
            public void run() {
                schedule.cancel(false);
            }
        }, MoreExecutors.directExecutor());
        return a2;
    }

    @DoNotCall
    @Deprecated
    public static <V, X extends Throwable> ListenableFuture<V> catching(ListenableFuture<? extends V> listenableFuture, Class<X> cls, Function<? super X, ? extends V> function) {
        return a.a(listenableFuture, cls, function, MoreExecutors.directExecutor());
    }

    public static <V, X extends Throwable> ListenableFuture<V> catching(ListenableFuture<? extends V> listenableFuture, Class<X> cls, Function<? super X, ? extends V> function, Executor executor) {
        return a.a(listenableFuture, cls, function, executor);
    }

    @CanIgnoreReturnValue
    @DoNotCall
    @Deprecated
    public static <V, X extends Throwable> ListenableFuture<V> catchingAsync(ListenableFuture<? extends V> listenableFuture, Class<X> cls, AsyncFunction<? super X, ? extends V> asyncFunction) {
        return a.a(listenableFuture, cls, asyncFunction, MoreExecutors.directExecutor());
    }

    @CanIgnoreReturnValue
    public static <V, X extends Throwable> ListenableFuture<V> catchingAsync(ListenableFuture<? extends V> listenableFuture, Class<X> cls, AsyncFunction<? super X, ? extends V> asyncFunction, Executor executor) {
        return a.a(listenableFuture, cls, asyncFunction, executor);
    }

    @GwtIncompatible
    public static <V> ListenableFuture<V> withTimeout(ListenableFuture<V> listenableFuture, long j, TimeUnit timeUnit, ScheduledExecutorService scheduledExecutorService) {
        return s.a(listenableFuture, j, timeUnit, scheduledExecutorService);
    }

    @DoNotCall
    @Deprecated
    public static <I, O> ListenableFuture<O> transformAsync(ListenableFuture<I> listenableFuture, AsyncFunction<? super I, ? extends O> asyncFunction) {
        return b.a(listenableFuture, asyncFunction, MoreExecutors.directExecutor());
    }

    public static <I, O> ListenableFuture<O> transformAsync(ListenableFuture<I> listenableFuture, AsyncFunction<? super I, ? extends O> asyncFunction, Executor executor) {
        return b.a(listenableFuture, asyncFunction, executor);
    }

    @DoNotCall
    @Deprecated
    public static <I, O> ListenableFuture<O> transform(ListenableFuture<I> listenableFuture, Function<? super I, ? extends O> function) {
        return b.a(listenableFuture, function, MoreExecutors.directExecutor());
    }

    public static <I, O> ListenableFuture<O> transform(ListenableFuture<I> listenableFuture, Function<? super I, ? extends O> function, Executor executor) {
        return b.a(listenableFuture, function, executor);
    }

    @GwtIncompatible
    public static <I, O> Future<O> lazyTransform(final Future<I> future, final Function<? super I, ? extends O> function) {
        Preconditions.checkNotNull(future);
        Preconditions.checkNotNull(function);
        return new Future<O>() { // from class: com.google.common.util.concurrent.Futures.2
            @Override // java.util.concurrent.Future
            public boolean cancel(boolean z) {
                return future.cancel(z);
            }

            @Override // java.util.concurrent.Future
            public boolean isCancelled() {
                return future.isCancelled();
            }

            @Override // java.util.concurrent.Future
            public boolean isDone() {
                return future.isDone();
            }

            /* JADX WARN: Type inference failed for: r0v2, types: [java.lang.Object, O] */
            @Override // java.util.concurrent.Future
            public O get() throws InterruptedException, ExecutionException {
                return a(future.get());
            }

            /* JADX WARN: Type inference failed for: r2v2, types: [java.lang.Object, O] */
            @Override // java.util.concurrent.Future
            public O get(long j, TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException {
                return a(future.get(j, timeUnit));
            }

            /* JADX WARN: Type inference failed for: r2v2, types: [java.lang.Object, O] */
            private O a(I i) throws ExecutionException {
                try {
                    return function.apply(i);
                } catch (Throwable th) {
                    throw new ExecutionException(th);
                }
            }
        };
    }

    @SafeVarargs
    @Beta
    public static <V> ListenableFuture<List<V>> allAsList(ListenableFuture<? extends V>... listenableFutureArr) {
        return new e.b(ImmutableList.copyOf(listenableFutureArr), true);
    }

    @Beta
    public static <V> ListenableFuture<List<V>> allAsList(Iterable<? extends ListenableFuture<? extends V>> iterable) {
        return new e.b(ImmutableList.copyOf(iterable), true);
    }

    @SafeVarargs
    public static <V> FutureCombiner<V> whenAllComplete(ListenableFuture<? extends V>... listenableFutureArr) {
        return new FutureCombiner<>(false, ImmutableList.copyOf(listenableFutureArr));
    }

    public static <V> FutureCombiner<V> whenAllComplete(Iterable<? extends ListenableFuture<? extends V>> iterable) {
        return new FutureCombiner<>(false, ImmutableList.copyOf(iterable));
    }

    @SafeVarargs
    public static <V> FutureCombiner<V> whenAllSucceed(ListenableFuture<? extends V>... listenableFutureArr) {
        return new FutureCombiner<>(true, ImmutableList.copyOf(listenableFutureArr));
    }

    public static <V> FutureCombiner<V> whenAllSucceed(Iterable<? extends ListenableFuture<? extends V>> iterable) {
        return new FutureCombiner<>(true, ImmutableList.copyOf(iterable));
    }

    @Beta
    @GwtCompatible
    @CanIgnoreReturnValue
    /* loaded from: classes2.dex */
    public static final class FutureCombiner<V> {
        private final boolean a;
        private final ImmutableList<ListenableFuture<? extends V>> b;

        private FutureCombiner(boolean z, ImmutableList<ListenableFuture<? extends V>> immutableList) {
            this.a = z;
            this.b = immutableList;
        }

        public <C> ListenableFuture<C> callAsync(AsyncCallable<C> asyncCallable, Executor executor) {
            return new f(this.b, this.a, executor, asyncCallable);
        }

        @DoNotCall
        @Deprecated
        public <C> ListenableFuture<C> callAsync(AsyncCallable<C> asyncCallable) {
            return callAsync(asyncCallable, MoreExecutors.directExecutor());
        }

        @CanIgnoreReturnValue
        public <C> ListenableFuture<C> call(Callable<C> callable, Executor executor) {
            return new f(this.b, this.a, executor, callable);
        }

        @CanIgnoreReturnValue
        @DoNotCall
        @Deprecated
        public <C> ListenableFuture<C> call(Callable<C> callable) {
            return call(callable, MoreExecutors.directExecutor());
        }

        public ListenableFuture<?> run(final Runnable runnable, Executor executor) {
            return call(new Callable<Void>() { // from class: com.google.common.util.concurrent.Futures.FutureCombiner.1
                /* renamed from: a */
                public Void call() throws Exception {
                    runnable.run();
                    return null;
                }
            }, executor);
        }
    }

    public static <V> ListenableFuture<V> nonCancellationPropagating(ListenableFuture<V> listenableFuture) {
        if (listenableFuture.isDone()) {
            return listenableFuture;
        }
        e eVar = new e(listenableFuture);
        listenableFuture.addListener(eVar, MoreExecutors.directExecutor());
        return eVar;
    }

    /* loaded from: classes2.dex */
    private static final class e<V> extends AbstractFuture.h<V> implements Runnable {
        private ListenableFuture<V> a;

        e(ListenableFuture<V> listenableFuture) {
            this.a = listenableFuture;
        }

        @Override // java.lang.Runnable
        public void run() {
            ListenableFuture<V> listenableFuture = this.a;
            if (listenableFuture != null) {
                setFuture(listenableFuture);
            }
        }

        @Override // com.google.common.util.concurrent.AbstractFuture
        protected String pendingToString() {
            ListenableFuture<V> listenableFuture = this.a;
            if (listenableFuture == null) {
                return null;
            }
            return "delegate=[" + listenableFuture + "]";
        }

        @Override // com.google.common.util.concurrent.AbstractFuture
        protected void afterDone() {
            this.a = null;
        }
    }

    @SafeVarargs
    @Beta
    public static <V> ListenableFuture<List<V>> successfulAsList(ListenableFuture<? extends V>... listenableFutureArr) {
        return new e.b(ImmutableList.copyOf(listenableFutureArr), false);
    }

    @Beta
    public static <V> ListenableFuture<List<V>> successfulAsList(Iterable<? extends ListenableFuture<? extends V>> iterable) {
        return new e.b(ImmutableList.copyOf(iterable), false);
    }

    @Beta
    public static <T> ImmutableList<ListenableFuture<T>> inCompletionOrder(Iterable<? extends ListenableFuture<? extends T>> iterable) {
        Collection collection;
        if (iterable instanceof Collection) {
            collection = (Collection) iterable;
        } else {
            collection = ImmutableList.copyOf(iterable);
        }
        ListenableFuture[] listenableFutureArr = (ListenableFuture[]) collection.toArray(new ListenableFuture[collection.size()]);
        final c cVar = new c(listenableFutureArr);
        ImmutableList.Builder builder = ImmutableList.builder();
        for (int i = 0; i < listenableFutureArr.length; i++) {
            builder.add((ImmutableList.Builder) new b(cVar));
        }
        final ImmutableList<ListenableFuture<T>> build = builder.build();
        for (final int i2 = 0; i2 < listenableFutureArr.length; i2++) {
            listenableFutureArr[i2].addListener(new Runnable() { // from class: com.google.common.util.concurrent.Futures.3
                @Override // java.lang.Runnable
                public void run() {
                    cVar.a(build, i2);
                }
            }, MoreExecutors.directExecutor());
        }
        return build;
    }

    /* loaded from: classes2.dex */
    private static final class b<T> extends AbstractFuture<T> {
        private c<T> a;

        private b(c<T> cVar) {
            this.a = cVar;
        }

        @Override // com.google.common.util.concurrent.AbstractFuture, java.util.concurrent.Future
        public boolean cancel(boolean z) {
            c<T> cVar = this.a;
            if (!super.cancel(z)) {
                return false;
            }
            cVar.a(z);
            return true;
        }

        @Override // com.google.common.util.concurrent.AbstractFuture
        protected void afterDone() {
            this.a = null;
        }

        @Override // com.google.common.util.concurrent.AbstractFuture
        protected String pendingToString() {
            c<T> cVar = this.a;
            if (cVar == null) {
                return null;
            }
            return "inputCount=[" + ((c) cVar).d.length + "], remaining=[" + ((c) cVar).c.get() + "]";
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static final class c<T> {
        private boolean a;
        private boolean b;
        private final AtomicInteger c;
        private final ListenableFuture<? extends T>[] d;
        private volatile int e;

        private c(ListenableFuture<? extends T>[] listenableFutureArr) {
            this.a = false;
            this.b = true;
            this.e = 0;
            this.d = listenableFutureArr;
            this.c = new AtomicInteger(listenableFutureArr.length);
        }

        public void a(boolean z) {
            this.a = true;
            if (!z) {
                this.b = false;
            }
            a();
        }

        public void a(ImmutableList<AbstractFuture<T>> immutableList, int i) {
            ListenableFuture<? extends T>[] listenableFutureArr = this.d;
            ListenableFuture<? extends T> listenableFuture = listenableFutureArr[i];
            listenableFutureArr[i] = null;
            for (int i2 = this.e; i2 < immutableList.size(); i2++) {
                if (immutableList.get(i2).setFuture(listenableFuture)) {
                    a();
                    this.e = i2 + 1;
                    return;
                }
            }
            this.e = immutableList.size();
        }

        private void a() {
            if (this.c.decrementAndGet() == 0 && this.a) {
                ListenableFuture<? extends T>[] listenableFutureArr = this.d;
                for (ListenableFuture<? extends T> listenableFuture : listenableFutureArr) {
                    if (listenableFuture != null) {
                        listenableFuture.cancel(this.b);
                    }
                }
            }
        }
    }

    @DoNotCall
    @Deprecated
    public static <V> void addCallback(ListenableFuture<V> listenableFuture, FutureCallback<? super V> futureCallback) {
        addCallback(listenableFuture, futureCallback, MoreExecutors.directExecutor());
    }

    public static <V> void addCallback(ListenableFuture<V> listenableFuture, FutureCallback<? super V> futureCallback, Executor executor) {
        Preconditions.checkNotNull(futureCallback);
        listenableFuture.addListener(new a(listenableFuture, futureCallback), executor);
    }

    /* loaded from: classes2.dex */
    public static final class a<V> implements Runnable {
        final Future<V> a;
        final FutureCallback<? super V> b;

        a(Future<V> future, FutureCallback<? super V> futureCallback) {
            this.a = future;
            this.b = futureCallback;
        }

        @Override // java.lang.Runnable
        public void run() {
            Throwable e;
            try {
                this.b.onSuccess(Futures.getDone(this.a));
            } catch (Error e2) {
                e = e2;
                this.b.onFailure(e);
            } catch (RuntimeException e3) {
                e = e3;
                this.b.onFailure(e);
            } catch (ExecutionException e4) {
                this.b.onFailure(e4.getCause());
            }
        }

        public String toString() {
            return MoreObjects.toStringHelper(this).addValue(this.b).toString();
        }
    }

    @CanIgnoreReturnValue
    public static <V> V getDone(Future<V> future) throws ExecutionException {
        Preconditions.checkState(future.isDone(), "Future was expected to be done: %s", future);
        return (V) Uninterruptibles.getUninterruptibly(future);
    }

    @CanIgnoreReturnValue
    @GwtIncompatible
    public static <V, X extends Exception> V getChecked(Future<V> future, Class<X> cls) throws Exception {
        return (V) j.a(future, cls);
    }

    @CanIgnoreReturnValue
    @GwtIncompatible
    public static <V, X extends Exception> V getChecked(Future<V> future, Class<X> cls, long j, TimeUnit timeUnit) throws Exception {
        return (V) j.a(future, cls, j, timeUnit);
    }

    @CanIgnoreReturnValue
    public static <V> V getUnchecked(Future<V> future) {
        Preconditions.checkNotNull(future);
        try {
            return (V) Uninterruptibles.getUninterruptibly(future);
        } catch (ExecutionException e2) {
            a(e2.getCause());
            throw new AssertionError();
        }
    }

    private static void a(Throwable th) {
        if (th instanceof Error) {
            throw new ExecutionError((Error) th);
        }
        throw new UncheckedExecutionException(th);
    }

    @GwtIncompatible
    /* loaded from: classes2.dex */
    private static class d<V, X extends Exception> extends AbstractCheckedFuture<V, X> {
        final Function<? super Exception, X> a;

        d(ListenableFuture<V> listenableFuture, Function<? super Exception, X> function) {
            super(listenableFuture);
            this.a = (Function) Preconditions.checkNotNull(function);
        }

        @Override // com.google.common.util.concurrent.AbstractCheckedFuture
        protected X mapException(Exception exc) {
            return this.a.apply(exc);
        }
    }
}
