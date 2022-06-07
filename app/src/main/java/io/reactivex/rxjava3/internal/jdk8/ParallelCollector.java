package io.reactivex.rxjava3.internal.jdk8;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.internal.subscriptions.DeferredScalarSubscription;
import io.reactivex.rxjava3.internal.subscriptions.EmptySubscription;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.AtomicThrowable;
import io.reactivex.rxjava3.parallel.ParallelFlowable;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collector;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes4.dex */
public final class ParallelCollector<T, A, R> extends Flowable<R> {
    final ParallelFlowable<? extends T> b;
    final Collector<T, A, R> c;

    public ParallelCollector(ParallelFlowable<? extends T> parallelFlowable, Collector<T, A, R> collector) {
        this.b = parallelFlowable;
        this.c = collector;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    protected void subscribeActual(Subscriber<? super R> subscriber) {
        try {
            b bVar = new b(subscriber, this.b.parallelism(), this.c);
            subscriber.onSubscribe(bVar);
            this.b.subscribe(bVar.subscribers);
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            EmptySubscription.error(th, subscriber);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class b<T, A, R> extends DeferredScalarSubscription<R> {
        private static final long serialVersionUID = -5370107872170712765L;
        final Function<A, R> finisher;
        final a<T, A, R>[] subscribers;
        final AtomicReference<c<A>> current = new AtomicReference<>();
        final AtomicInteger remaining = new AtomicInteger();
        final AtomicThrowable error = new AtomicThrowable();

        b(Subscriber<? super R> subscriber, int i, Collector<T, A, R> collector) {
            super(subscriber);
            this.finisher = collector.finisher();
            a<T, A, R>[] aVarArr = new a[i];
            for (int i2 = 0; i2 < i; i2++) {
                aVarArr[i2] = new a<>(this, collector.supplier().get(), collector.accumulator(), collector.combiner());
            }
            this.subscribers = aVarArr;
            this.remaining.lazySet(i);
        }

        /* JADX WARN: Multi-variable type inference failed */
        c<A> a(A a) {
            c<A> cVar;
            int a2;
            while (true) {
                cVar = this.current.get();
                if (cVar == null) {
                    cVar = new c<>();
                    if (!this.current.compareAndSet(null, cVar)) {
                        continue;
                    }
                }
                a2 = cVar.a();
                if (a2 >= 0) {
                    break;
                }
                this.current.compareAndSet(cVar, null);
            }
            if (a2 == 0) {
                cVar.first = a;
            } else {
                cVar.second = a;
            }
            if (!cVar.b()) {
                return null;
            }
            this.current.compareAndSet(cVar, null);
            return cVar;
        }

        @Override // io.reactivex.rxjava3.internal.subscriptions.DeferredScalarSubscription, org.reactivestreams.Subscription
        public void cancel() {
            for (a<T, A, R> aVar : this.subscribers) {
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

        /* JADX WARN: Multi-variable type inference failed */
        void a(A a, BinaryOperator<A> binaryOperator) {
            while (true) {
                c a2 = a((b<T, A, R>) a);
                if (a2 == null) {
                    break;
                }
                try {
                    a = (A) binaryOperator.apply(a2.first, a2.second);
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    a(th);
                    return;
                }
            }
            if (this.remaining.decrementAndGet() == 0) {
                c<A> cVar = this.current.get();
                this.current.lazySet(null);
                try {
                    complete(Objects.requireNonNull(this.finisher.apply(cVar.first), "The finisher returned a null value"));
                } catch (Throwable th2) {
                    Exceptions.throwIfFatal(th2);
                    a(th2);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class a<T, A, R> extends AtomicReference<Subscription> implements FlowableSubscriber<T> {
        private static final long serialVersionUID = -7954444275102466525L;
        final BiConsumer<A, T> accumulator;
        final BinaryOperator<A> combiner;
        A container;
        boolean done;
        final b<T, A, R> parent;

        a(b<T, A, R> bVar, A a, BiConsumer<A, T> biConsumer, BinaryOperator<A> binaryOperator) {
            this.parent = bVar;
            this.accumulator = biConsumer;
            this.combiner = binaryOperator;
            this.container = a;
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            SubscriptionHelper.setOnce(this, subscription, Long.MAX_VALUE);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (!this.done) {
                try {
                    this.accumulator.accept(this.container, t);
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
            this.container = null;
            this.done = true;
            this.parent.a(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (!this.done) {
                A a = this.container;
                this.container = null;
                this.done = true;
                this.parent.a(a, this.combiner);
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
