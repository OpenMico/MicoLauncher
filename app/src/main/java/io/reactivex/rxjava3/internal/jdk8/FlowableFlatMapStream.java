package io.reactivex.rxjava3.internal.jdk8;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.exceptions.MissingBackpressureException;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.functions.Supplier;
import io.reactivex.rxjava3.internal.fuseable.QueueSubscription;
import io.reactivex.rxjava3.internal.fuseable.SimpleQueue;
import io.reactivex.rxjava3.internal.queue.SpscArrayQueue;
import io.reactivex.rxjava3.internal.subscriptions.EmptySubscription;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.AtomicThrowable;
import io.reactivex.rxjava3.internal.util.BackpressureHelper;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes4.dex */
public final class FlowableFlatMapStream<T, R> extends Flowable<R> {
    final Flowable<T> b;
    final Function<? super T, ? extends Stream<? extends R>> c;
    final int d;

    public FlowableFlatMapStream(Flowable<T> flowable, Function<? super T, ? extends Stream<? extends R>> function, int i) {
        this.b = flowable;
        this.c = function;
        this.d = i;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    protected void subscribeActual(Subscriber<? super R> subscriber) {
        Flowable<T> flowable = this.b;
        if (flowable instanceof Supplier) {
            Stream stream = null;
            try {
                Object obj = ((Supplier) flowable).get();
                if (obj != null) {
                    stream = (Stream) Objects.requireNonNull(this.c.apply(obj), "The mapper returned a null Stream");
                }
                if (stream != null) {
                    FlowableFromStream.subscribeStream(subscriber, stream);
                } else {
                    EmptySubscription.complete(subscriber);
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                EmptySubscription.error(th, subscriber);
            }
        } else {
            flowable.subscribe(subscribe(subscriber, this.c, this.d));
        }
    }

    public static <T, R> Subscriber<T> subscribe(Subscriber<? super R> subscriber, Function<? super T, ? extends Stream<? extends R>> function, int i) {
        return new a(subscriber, function, i);
    }

    /* loaded from: classes4.dex */
    public static final class a<T, R> extends AtomicInteger implements FlowableSubscriber<T>, Subscription {
        private static final long serialVersionUID = -5127032662980523968L;
        volatile boolean cancelled;
        int consumed;
        AutoCloseable currentCloseable;
        Iterator<? extends R> currentIterator;
        final Subscriber<? super R> downstream;
        long emitted;
        final Function<? super T, ? extends Stream<? extends R>> mapper;
        final int prefetch;
        SimpleQueue<T> queue;
        int sourceMode;
        Subscription upstream;
        volatile boolean upstreamDone;
        final AtomicLong requested = new AtomicLong();
        final AtomicThrowable error = new AtomicThrowable();

        a(Subscriber<? super R> subscriber, Function<? super T, ? extends Stream<? extends R>> function, int i) {
            this.downstream = subscriber;
            this.mapper = function;
            this.prefetch = i;
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(@NonNull Subscription subscription) {
            if (SubscriptionHelper.validate(this.upstream, subscription)) {
                this.upstream = subscription;
                if (subscription instanceof QueueSubscription) {
                    QueueSubscription queueSubscription = (QueueSubscription) subscription;
                    int requestFusion = queueSubscription.requestFusion(7);
                    if (requestFusion == 1) {
                        this.sourceMode = requestFusion;
                        this.queue = queueSubscription;
                        this.upstreamDone = true;
                        this.downstream.onSubscribe(this);
                        return;
                    } else if (requestFusion == 2) {
                        this.sourceMode = requestFusion;
                        this.queue = queueSubscription;
                        this.downstream.onSubscribe(this);
                        subscription.request(this.prefetch);
                        return;
                    }
                }
                this.queue = new SpscArrayQueue(this.prefetch);
                this.downstream.onSubscribe(this);
                subscription.request(this.prefetch);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (this.sourceMode == 2 || this.queue.offer(t)) {
                a();
                return;
            }
            this.upstream.cancel();
            onError(new MissingBackpressureException("Queue full?!"));
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.error.compareAndSet(null, th)) {
                this.upstreamDone = true;
                a();
                return;
            }
            RxJavaPlugins.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.upstreamDone = true;
            a();
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (SubscriptionHelper.validate(j)) {
                BackpressureHelper.add(this.requested, j);
                a();
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.cancelled = true;
            this.upstream.cancel();
            a();
        }

        void a() {
            T poll;
            int i;
            Object obj;
            Throwable th;
            if (getAndIncrement() == 0) {
                Subscriber<? super R> subscriber = this.downstream;
                SimpleQueue<T> simpleQueue = this.queue;
                AtomicThrowable atomicThrowable = this.error;
                Iterator<? extends R> it = this.currentIterator;
                long j = this.requested.get();
                long j2 = this.emitted;
                int i2 = this.prefetch;
                int i3 = i2 - (i2 >> 2);
                int i4 = 0;
                boolean z = true;
                Object[] objArr = this.sourceMode != 1 ? 1 : null;
                int i5 = 1;
                Iterator<? extends R> it2 = it;
                while (true) {
                    if (this.cancelled) {
                        simpleQueue.clear();
                        c();
                    } else {
                        boolean z2 = this.upstreamDone;
                        if (atomicThrowable.get() != null) {
                            subscriber.onError(atomicThrowable.get());
                            this.cancelled = z;
                        } else {
                            if (it2 == null) {
                                try {
                                    poll = simpleQueue.poll();
                                    if (poll == null) {
                                        int i6 = z ? 1 : 0;
                                        Object[] objArr2 = z ? 1 : 0;
                                        Object[] objArr3 = z ? 1 : 0;
                                        Object[] objArr4 = z ? 1 : 0;
                                        Object[] objArr5 = z ? 1 : 0;
                                        Object[] objArr6 = z ? 1 : 0;
                                        Object[] objArr7 = z ? 1 : 0;
                                        Object[] objArr8 = z ? 1 : 0;
                                        Object[] objArr9 = z ? 1 : 0;
                                        i = i6;
                                    } else {
                                        i = i4;
                                    }
                                } catch (Throwable th2) {
                                    Exceptions.throwIfFatal(th2);
                                    a(subscriber, th2);
                                }
                                if (z2 && i != 0) {
                                    subscriber.onComplete();
                                    this.cancelled = z;
                                } else if (i == 0) {
                                    if (objArr != null) {
                                        int i7 = this.consumed;
                                        int i8 = z ? 1 : 0;
                                        int i9 = z ? 1 : 0;
                                        int i10 = z ? 1 : 0;
                                        int i11 = z ? 1 : 0;
                                        int i12 = z ? 1 : 0;
                                        int i13 = z ? 1 : 0;
                                        int i14 = z ? 1 : 0;
                                        int i15 = z ? 1 : 0;
                                        int i16 = z ? 1 : 0;
                                        int i17 = i7 + i8;
                                        this.consumed = i17;
                                        if (i17 == i3) {
                                            this.consumed = i4;
                                            this.upstream.request(i3);
                                        }
                                    }
                                    try {
                                        Stream stream = (Stream) Objects.requireNonNull(this.mapper.apply(poll), "The mapper returned a null Stream");
                                        it2 = stream.iterator();
                                        if (it2.hasNext()) {
                                            this.currentIterator = it2;
                                            this.currentCloseable = stream;
                                        } else {
                                            it2 = null;
                                        }
                                        i4 = 0;
                                        z = true;
                                    } catch (Throwable th3) {
                                        Exceptions.throwIfFatal(th3);
                                        a(subscriber, th3);
                                        i4 = 0;
                                        z = true;
                                    }
                                }
                            }
                            if (!(it2 == null || j2 == j)) {
                                try {
                                    obj = (Object) Objects.requireNonNull(it2.next(), "The Stream.Iterator returned a null value");
                                } catch (Throwable th4) {
                                    Exceptions.throwIfFatal(th4);
                                    a(subscriber, th4);
                                }
                                if (!this.cancelled) {
                                    subscriber.onNext(obj);
                                    j2++;
                                    if (!this.cancelled) {
                                        try {
                                            if (!it2.hasNext()) {
                                                try {
                                                    b();
                                                    it2 = null;
                                                } catch (Throwable th5) {
                                                    th = th5;
                                                    it2 = null;
                                                    Exceptions.throwIfFatal(th);
                                                    a(subscriber, th);
                                                    i4 = 0;
                                                    z = true;
                                                }
                                            }
                                        } catch (Throwable th6) {
                                            th = th6;
                                        }
                                        i4 = 0;
                                        z = true;
                                    } else {
                                        i4 = 0;
                                        z = true;
                                    }
                                }
                            }
                        }
                        i4 = 0;
                        z = true;
                    }
                    this.emitted = j2;
                    i5 = addAndGet(-i5);
                    if (i5 != 0) {
                        j = this.requested.get();
                        i4 = 0;
                        z = true;
                    } else {
                        return;
                    }
                }
            }
        }

        void b() throws Throwable {
            this.currentIterator = null;
            AutoCloseable autoCloseable = this.currentCloseable;
            this.currentCloseable = null;
            if (autoCloseable != null) {
                autoCloseable.close();
            }
        }

        void c() {
            try {
                b();
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                RxJavaPlugins.onError(th);
            }
        }

        void a(Subscriber<?> subscriber, Throwable th) {
            if (this.error.compareAndSet(null, th)) {
                this.upstream.cancel();
                this.cancelled = true;
                subscriber.onError(th);
                return;
            }
            RxJavaPlugins.onError(th);
        }
    }
}
