package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.QueueSubscription;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.queue.SpscArrayQueue;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes4.dex */
public final class FlowableSwitchMap<T, R> extends a<T, R> {
    final Function<? super T, ? extends Publisher<? extends R>> b;
    final int c;
    final boolean d;

    public FlowableSwitchMap(Flowable<T> flowable, Function<? super T, ? extends Publisher<? extends R>> function, int i, boolean z) {
        super(flowable);
        this.b = function;
        this.c = i;
        this.d = z;
    }

    @Override // io.reactivex.Flowable
    protected void subscribeActual(Subscriber<? super R> subscriber) {
        if (!FlowableScalarXMap.tryScalarXMapSubscribe(this.source, subscriber, this.b)) {
            this.source.subscribe((FlowableSubscriber) new b(subscriber, this.b, this.c, this.d));
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class b<T, R> extends AtomicInteger implements FlowableSubscriber<T>, Subscription {
        static final a<Object, Object> a = new a<>(null, -1, 1);
        private static final long serialVersionUID = -3491074160481096299L;
        final int bufferSize;
        volatile boolean cancelled;
        final boolean delayErrors;
        volatile boolean done;
        final Subscriber<? super R> downstream;
        final Function<? super T, ? extends Publisher<? extends R>> mapper;
        volatile long unique;
        Subscription upstream;
        final AtomicReference<a<T, R>> active = new AtomicReference<>();
        final AtomicLong requested = new AtomicLong();
        final AtomicThrowable error = new AtomicThrowable();

        static {
            a.a();
        }

        b(Subscriber<? super R> subscriber, Function<? super T, ? extends Publisher<? extends R>> function, int i, boolean z) {
            this.downstream = subscriber;
            this.mapper = function;
            this.bufferSize = i;
            this.delayErrors = z;
        }

        @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.upstream, subscription)) {
                this.upstream = subscription;
                this.downstream.onSubscribe(this);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            a<T, R> aVar;
            if (!this.done) {
                long j = this.unique + 1;
                this.unique = j;
                a<T, R> aVar2 = this.active.get();
                if (aVar2 != null) {
                    aVar2.a();
                }
                try {
                    Publisher publisher = (Publisher) ObjectHelper.requireNonNull(this.mapper.apply(t), "The publisher returned is null");
                    a<T, R> aVar3 = new a<>(this, j, this.bufferSize);
                    do {
                        aVar = this.active.get();
                        if (aVar == a) {
                            return;
                        }
                    } while (!this.active.compareAndSet(aVar, aVar3));
                    publisher.subscribe(aVar3);
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    this.upstream.cancel();
                    onError(th);
                }
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.done || !this.error.addThrowable(th)) {
                RxJavaPlugins.onError(th);
                return;
            }
            if (!this.delayErrors) {
                a();
            }
            this.done = true;
            b();
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (!this.done) {
                this.done = true;
                b();
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (SubscriptionHelper.validate(j)) {
                BackpressureHelper.add(this.requested, j);
                if (this.unique == 0) {
                    this.upstream.request(Long.MAX_VALUE);
                } else {
                    b();
                }
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (!this.cancelled) {
                this.cancelled = true;
                this.upstream.cancel();
                a();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        void a() {
            a<Object, Object> aVar;
            a<T, R> aVar2 = this.active.get();
            a<Object, Object> aVar3 = a;
            if (aVar2 != aVar3 && (aVar = (a) this.active.getAndSet(aVar3)) != a && aVar != null) {
                aVar.a();
            }
        }

        void b() {
            boolean z;
            R r;
            if (getAndIncrement() == 0) {
                Subscriber<? super R> subscriber = this.downstream;
                int i = 1;
                while (!this.cancelled) {
                    if (this.done) {
                        if (this.delayErrors) {
                            if (this.active.get() == null) {
                                if (this.error.get() != null) {
                                    subscriber.onError(this.error.terminate());
                                    return;
                                } else {
                                    subscriber.onComplete();
                                    return;
                                }
                            }
                        } else if (this.error.get() != null) {
                            a();
                            subscriber.onError(this.error.terminate());
                            return;
                        } else if (this.active.get() == null) {
                            subscriber.onComplete();
                            return;
                        }
                    }
                    a<T, R> aVar = this.active.get();
                    SimpleQueue<R> simpleQueue = aVar != null ? aVar.queue : null;
                    if (simpleQueue != null) {
                        if (aVar.done) {
                            if (!this.delayErrors) {
                                if (this.error.get() != null) {
                                    a();
                                    subscriber.onError(this.error.terminate());
                                    return;
                                } else if (simpleQueue.isEmpty()) {
                                    this.active.compareAndSet(aVar, null);
                                }
                            } else if (simpleQueue.isEmpty()) {
                                this.active.compareAndSet(aVar, null);
                            }
                        }
                        long j = this.requested.get();
                        long j2 = 0;
                        while (true) {
                            z = false;
                            if (j2 == j) {
                                break;
                            } else if (!this.cancelled) {
                                boolean z2 = aVar.done;
                                try {
                                    r = simpleQueue.poll();
                                } catch (Throwable th) {
                                    Exceptions.throwIfFatal(th);
                                    aVar.a();
                                    this.error.addThrowable(th);
                                    r = (Object) null;
                                    z2 = true;
                                }
                                boolean z3 = r == null;
                                if (aVar != this.active.get()) {
                                    z = true;
                                    break;
                                }
                                if (z2) {
                                    if (this.delayErrors) {
                                        if (z3) {
                                            this.active.compareAndSet(aVar, null);
                                            z = true;
                                            break;
                                        }
                                    } else if (this.error.get() == null) {
                                        if (z3) {
                                            this.active.compareAndSet(aVar, null);
                                            z = true;
                                            break;
                                        }
                                    } else {
                                        subscriber.onError(this.error.terminate());
                                        return;
                                    }
                                }
                                if (z3) {
                                    break;
                                }
                                subscriber.onNext(r);
                                j2++;
                            } else {
                                return;
                            }
                        }
                        if (j2 != 0 && !this.cancelled) {
                            if (j != Long.MAX_VALUE) {
                                this.requested.addAndGet(-j2);
                            }
                            aVar.a(j2);
                        }
                        if (z) {
                            continue;
                        }
                    }
                    i = addAndGet(-i);
                    if (i == 0) {
                        return;
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class a<T, R> extends AtomicReference<Subscription> implements FlowableSubscriber<R> {
        private static final long serialVersionUID = 3837284832786408377L;
        final int bufferSize;
        volatile boolean done;
        int fusionMode;
        final long index;
        final b<T, R> parent;
        volatile SimpleQueue<R> queue;

        a(b<T, R> bVar, long j, int i) {
            this.parent = bVar;
            this.index = j;
            this.bufferSize = i;
        }

        @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.setOnce(this, subscription)) {
                if (subscription instanceof QueueSubscription) {
                    QueueSubscription queueSubscription = (QueueSubscription) subscription;
                    int requestFusion = queueSubscription.requestFusion(7);
                    if (requestFusion == 1) {
                        this.fusionMode = requestFusion;
                        this.queue = queueSubscription;
                        this.done = true;
                        this.parent.b();
                        return;
                    } else if (requestFusion == 2) {
                        this.fusionMode = requestFusion;
                        this.queue = queueSubscription;
                        subscription.request(this.bufferSize);
                        return;
                    }
                }
                this.queue = new SpscArrayQueue(this.bufferSize);
                subscription.request(this.bufferSize);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(R r) {
            b<T, R> bVar = this.parent;
            if (this.index != bVar.unique) {
                return;
            }
            if (this.fusionMode != 0 || this.queue.offer(r)) {
                bVar.b();
            } else {
                onError(new MissingBackpressureException("Queue full?!"));
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            b<T, R> bVar = this.parent;
            if (this.index != bVar.unique || !bVar.error.addThrowable(th)) {
                RxJavaPlugins.onError(th);
                return;
            }
            if (!bVar.delayErrors) {
                bVar.upstream.cancel();
                bVar.done = true;
            }
            this.done = true;
            bVar.b();
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            b<T, R> bVar = this.parent;
            if (this.index == bVar.unique) {
                this.done = true;
                bVar.b();
            }
        }

        public void a() {
            SubscriptionHelper.cancel(this);
        }

        public void a(long j) {
            if (this.fusionMode != 1) {
                get().request(j);
            }
        }
    }
}
