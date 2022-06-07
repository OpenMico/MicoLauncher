package io.reactivex.internal.operators.parallel;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.BiFunction;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.subscriptions.DeferredScalarSubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.parallel.ParallelFlowable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes4.dex */
public final class ParallelReduceFull<T> extends Flowable<T> {
    final ParallelFlowable<? extends T> b;
    final BiFunction<T, T, T> c;

    public ParallelReduceFull(ParallelFlowable<? extends T> parallelFlowable, BiFunction<T, T, T> biFunction) {
        this.b = parallelFlowable;
        this.c = biFunction;
    }

    @Override // io.reactivex.Flowable
    protected void subscribeActual(Subscriber<? super T> subscriber) {
        b bVar = new b(subscriber, this.b.parallelism(), this.c);
        subscriber.onSubscribe(bVar);
        this.b.subscribe(bVar.subscribers);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class b<T> extends DeferredScalarSubscription<T> {
        private static final long serialVersionUID = -5370107872170712765L;
        final BiFunction<T, T, T> reducer;
        final a<T>[] subscribers;
        final AtomicReference<c<T>> current = new AtomicReference<>();
        final AtomicInteger remaining = new AtomicInteger();
        final AtomicReference<Throwable> error = new AtomicReference<>();

        b(Subscriber<? super T> subscriber, int i, BiFunction<T, T, T> biFunction) {
            super(subscriber);
            a<T>[] aVarArr = new a[i];
            for (int i2 = 0; i2 < i; i2++) {
                aVarArr[i2] = new a<>(this, biFunction);
            }
            this.subscribers = aVarArr;
            this.reducer = biFunction;
            this.remaining.lazySet(i);
        }

        c<T> a(T t) {
            c<T> cVar;
            int a;
            while (true) {
                cVar = this.current.get();
                if (cVar == null) {
                    cVar = new c<>();
                    if (!this.current.compareAndSet(null, cVar)) {
                        continue;
                    }
                }
                a = cVar.a();
                if (a >= 0) {
                    break;
                }
                this.current.compareAndSet(cVar, null);
            }
            if (a == 0) {
                cVar.first = t;
            } else {
                cVar.second = t;
            }
            if (!cVar.b()) {
                return null;
            }
            this.current.compareAndSet(cVar, null);
            return cVar;
        }

        @Override // io.reactivex.internal.subscriptions.DeferredScalarSubscription, org.reactivestreams.Subscription
        public void cancel() {
            for (a<T> aVar : this.subscribers) {
                aVar.a();
            }
        }

        void a(Throwable th) {
            if (this.error.compareAndSet(null, th)) {
                cancel();
                this.downstream.onError(th);
            } else if (th != this.error.get()) {
                RxJavaPlugins.onError(th);
            }
        }

        void b(T t) {
            if (t != null) {
                while (true) {
                    c<T> a = a((b<T>) t);
                    if (a == null) {
                        break;
                    }
                    try {
                        t = (T) ObjectHelper.requireNonNull(this.reducer.apply(a.first, a.second), "The reducer returned a null value");
                    } catch (Throwable th) {
                        Exceptions.throwIfFatal(th);
                        a(th);
                        return;
                    }
                }
            }
            if (this.remaining.decrementAndGet() == 0) {
                c<T> cVar = this.current.get();
                this.current.lazySet(null);
                if (cVar != null) {
                    complete(cVar.first);
                } else {
                    this.downstream.onComplete();
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class a<T> extends AtomicReference<Subscription> implements FlowableSubscriber<T> {
        private static final long serialVersionUID = -7954444275102466525L;
        boolean done;
        final b<T> parent;
        final BiFunction<T, T, T> reducer;
        T value;

        a(b<T> bVar, BiFunction<T, T, T> biFunction) {
            this.parent = bVar;
            this.reducer = biFunction;
        }

        @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            SubscriptionHelper.setOnce(this, subscription, Long.MAX_VALUE);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (!this.done) {
                T t2 = this.value;
                if (t2 == null) {
                    this.value = t;
                    return;
                }
                try {
                    this.value = (T) ObjectHelper.requireNonNull(this.reducer.apply(t2, t), "The reducer returned a null value");
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    get().cancel();
                    onError(th);
                }
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.done) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.done = true;
            this.parent.a(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (!this.done) {
                this.done = true;
                this.parent.b(this.value);
            }
        }

        void a() {
            SubscriptionHelper.cancel(this);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class c<T> extends AtomicInteger {
        private static final long serialVersionUID = 473971317683868662L;
        T first;
        final AtomicInteger releaseIndex = new AtomicInteger();
        T second;

        c() {
        }

        int a() {
            int i;
            do {
                i = get();
                if (i >= 2) {
                    return -1;
                }
            } while (!compareAndSet(i, i + 1));
            return i;
        }

        boolean b() {
            return this.releaseIndex.incrementAndGet() == 2;
        }
    }
}
