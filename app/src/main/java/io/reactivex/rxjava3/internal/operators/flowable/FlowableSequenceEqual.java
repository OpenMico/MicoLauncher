package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.exceptions.MissingBackpressureException;
import io.reactivex.rxjava3.functions.BiPredicate;
import io.reactivex.rxjava3.internal.fuseable.QueueSubscription;
import io.reactivex.rxjava3.internal.fuseable.SimpleQueue;
import io.reactivex.rxjava3.internal.queue.SpscArrayQueue;
import io.reactivex.rxjava3.internal.subscriptions.DeferredScalarSubscription;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.AtomicThrowable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes5.dex */
public final class FlowableSequenceEqual<T> extends Flowable<Boolean> {
    final Publisher<? extends T> b;
    final Publisher<? extends T> c;
    final BiPredicate<? super T, ? super T> d;
    final int e;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public interface b {
        void a(Throwable th);

        void b();
    }

    public FlowableSequenceEqual(Publisher<? extends T> publisher, Publisher<? extends T> publisher2, BiPredicate<? super T, ? super T> biPredicate, int i) {
        this.b = publisher;
        this.c = publisher2;
        this.d = biPredicate;
        this.e = i;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber<? super Boolean> subscriber) {
        a aVar = new a(subscriber, this.e, this.d);
        subscriber.onSubscribe(aVar);
        aVar.a(this.b, this.c);
    }

    /* loaded from: classes5.dex */
    static final class a<T> extends DeferredScalarSubscription<Boolean> implements b {
        private static final long serialVersionUID = -6178010334400373240L;
        final BiPredicate<? super T, ? super T> comparer;
        final c<T> first;
        final c<T> second;
        T v1;
        T v2;
        final AtomicInteger wip = new AtomicInteger();
        final AtomicThrowable errors = new AtomicThrowable();

        a(Subscriber<? super Boolean> subscriber, int i, BiPredicate<? super T, ? super T> biPredicate) {
            super(subscriber);
            this.comparer = biPredicate;
            this.first = new c<>(this, i);
            this.second = new c<>(this, i);
        }

        void a(Publisher<? extends T> publisher, Publisher<? extends T> publisher2) {
            publisher.subscribe(this.first);
            publisher2.subscribe(this.second);
        }

        @Override // io.reactivex.rxjava3.internal.subscriptions.DeferredScalarSubscription, org.reactivestreams.Subscription
        public void cancel() {
            super.cancel();
            this.first.b();
            this.second.b();
            this.errors.tryTerminateAndReport();
            if (this.wip.getAndIncrement() == 0) {
                this.first.c();
                this.second.c();
            }
        }

        void a() {
            this.first.b();
            this.first.c();
            this.second.b();
            this.second.c();
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableSequenceEqual.b
        public void b() {
            if (this.wip.getAndIncrement() == 0) {
                int i = 1;
                do {
                    SimpleQueue<T> simpleQueue = this.first.queue;
                    SimpleQueue<T> simpleQueue2 = this.second.queue;
                    if (simpleQueue != null && simpleQueue2 != null) {
                        while (!isCancelled()) {
                            if (this.errors.get() != null) {
                                a();
                                this.errors.tryTerminateConsumer(this.downstream);
                                return;
                            }
                            boolean z = this.first.done;
                            T t = this.v1;
                            if (t == null) {
                                try {
                                    t = simpleQueue.poll();
                                    this.v1 = t;
                                } catch (Throwable th) {
                                    Exceptions.throwIfFatal(th);
                                    a();
                                    this.errors.tryAddThrowableOrReport(th);
                                    this.errors.tryTerminateConsumer(this.downstream);
                                    return;
                                }
                            }
                            boolean z2 = t == null;
                            boolean z3 = this.second.done;
                            T t2 = this.v2;
                            if (t2 == null) {
                                try {
                                    t2 = simpleQueue2.poll();
                                    this.v2 = t2;
                                } catch (Throwable th2) {
                                    Exceptions.throwIfFatal(th2);
                                    a();
                                    this.errors.tryAddThrowableOrReport(th2);
                                    this.errors.tryTerminateConsumer(this.downstream);
                                    return;
                                }
                            }
                            boolean z4 = t2 == null;
                            if (z && z3 && z2 && z4) {
                                complete(true);
                                return;
                            } else if (z && z3 && z2 != z4) {
                                a();
                                complete(false);
                                return;
                            } else if (!z2 && !z4) {
                                try {
                                    if (!this.comparer.test(t, t2)) {
                                        a();
                                        complete(false);
                                        return;
                                    }
                                    this.v1 = null;
                                    this.v2 = null;
                                    this.first.a();
                                    this.second.a();
                                } catch (Throwable th3) {
                                    Exceptions.throwIfFatal(th3);
                                    a();
                                    this.errors.tryAddThrowableOrReport(th3);
                                    this.errors.tryTerminateConsumer(this.downstream);
                                    return;
                                }
                            }
                        }
                        this.first.c();
                        this.second.c();
                        return;
                    } else if (isCancelled()) {
                        this.first.c();
                        this.second.c();
                        return;
                    } else if (this.errors.get() != null) {
                        a();
                        this.errors.tryTerminateConsumer(this.downstream);
                        return;
                    }
                    i = this.wip.addAndGet(-i);
                } while (i != 0);
            }
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableSequenceEqual.b
        public void a(Throwable th) {
            if (this.errors.tryAddThrowableOrReport(th)) {
                b();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public static final class c<T> extends AtomicReference<Subscription> implements FlowableSubscriber<T> {
        private static final long serialVersionUID = 4804128302091633067L;
        volatile boolean done;
        final int limit;
        final b parent;
        final int prefetch;
        long produced;
        volatile SimpleQueue<T> queue;
        int sourceMode;

        /* JADX INFO: Access modifiers changed from: package-private */
        public c(b bVar, int i) {
            this.parent = bVar;
            this.limit = i - (i >> 2);
            this.prefetch = i;
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.setOnce(this, subscription)) {
                if (subscription instanceof QueueSubscription) {
                    QueueSubscription queueSubscription = (QueueSubscription) subscription;
                    int requestFusion = queueSubscription.requestFusion(3);
                    if (requestFusion == 1) {
                        this.sourceMode = requestFusion;
                        this.queue = queueSubscription;
                        this.done = true;
                        this.parent.b();
                        return;
                    } else if (requestFusion == 2) {
                        this.sourceMode = requestFusion;
                        this.queue = queueSubscription;
                        subscription.request(this.prefetch);
                        return;
                    }
                }
                this.queue = new SpscArrayQueue(this.prefetch);
                subscription.request(this.prefetch);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (this.sourceMode != 0 || this.queue.offer(t)) {
                this.parent.b();
            } else {
                onError(new MissingBackpressureException());
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.parent.a(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.done = true;
            this.parent.b();
        }

        public void a() {
            if (this.sourceMode != 1) {
                long j = this.produced + 1;
                if (j >= this.limit) {
                    this.produced = 0L;
                    get().request(j);
                    return;
                }
                this.produced = j;
            }
        }

        public void b() {
            SubscriptionHelper.cancel(this);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public void c() {
            SimpleQueue<T> simpleQueue = this.queue;
            if (simpleQueue != null) {
                simpleQueue.clear();
            }
        }
    }
}
