package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.functions.Supplier;
import io.reactivex.rxjava3.internal.fuseable.QueueSubscription;
import io.reactivex.rxjava3.internal.fuseable.SimpleQueue;
import io.reactivex.rxjava3.internal.queue.SpscArrayQueue;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionArbiter;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.AtomicThrowable;
import io.reactivex.rxjava3.internal.util.ErrorMode;
import io.reactivex.rxjava3.internal.util.HalfSerializer;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes5.dex */
public final class FlowableConcatMap<T, R> extends a<T, R> {
    final Function<? super T, ? extends Publisher<? extends R>> b;
    final int c;
    final ErrorMode d;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public interface e<T> {
        void a(T t);

        void a(Throwable th);

        void c();
    }

    public FlowableConcatMap(Flowable<T> flowable, Function<? super T, ? extends Publisher<? extends R>> function, int i, ErrorMode errorMode) {
        super(flowable);
        this.b = function;
        this.c = i;
        this.d = errorMode;
    }

    public static <T, R> Subscriber<T> subscribe(Subscriber<? super R> subscriber, Function<? super T, ? extends Publisher<? extends R>> function, int i, ErrorMode errorMode) {
        switch (errorMode) {
            case BOUNDARY:
                return new b(subscriber, function, i, false);
            case END:
                return new b(subscriber, function, i, true);
            default:
                return new c(subscriber, function, i);
        }
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    protected void subscribeActual(Subscriber<? super R> subscriber) {
        if (!FlowableScalarXMap.tryScalarXMapSubscribe(this.source, subscriber, this.b)) {
            this.source.subscribe(subscribe(subscriber, this.b, this.c, this.d));
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public static abstract class a<T, R> extends AtomicInteger implements FlowableSubscriber<T>, e<R>, Subscription {
        private static final long serialVersionUID = -3511336836796789179L;
        volatile boolean active;
        volatile boolean cancelled;
        int consumed;
        volatile boolean done;
        final int limit;
        final Function<? super T, ? extends Publisher<? extends R>> mapper;
        final int prefetch;
        SimpleQueue<T> queue;
        int sourceMode;
        Subscription upstream;
        final d<R> inner = new d<>(this);
        final AtomicThrowable errors = new AtomicThrowable();

        abstract void a();

        abstract void b();

        a(Function<? super T, ? extends Publisher<? extends R>> function, int i) {
            this.mapper = function;
            this.prefetch = i;
            this.limit = i - (i >> 2);
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public final void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.upstream, subscription)) {
                this.upstream = subscription;
                if (subscription instanceof QueueSubscription) {
                    QueueSubscription queueSubscription = (QueueSubscription) subscription;
                    int requestFusion = queueSubscription.requestFusion(7);
                    if (requestFusion == 1) {
                        this.sourceMode = requestFusion;
                        this.queue = queueSubscription;
                        this.done = true;
                        b();
                        a();
                        return;
                    } else if (requestFusion == 2) {
                        this.sourceMode = requestFusion;
                        this.queue = queueSubscription;
                        b();
                        subscription.request(this.prefetch);
                        return;
                    }
                }
                this.queue = new SpscArrayQueue(this.prefetch);
                b();
                subscription.request(this.prefetch);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public final void onNext(T t) {
            if (this.sourceMode == 2 || this.queue.offer(t)) {
                a();
                return;
            }
            this.upstream.cancel();
            onError(new IllegalStateException("Queue full?!"));
        }

        @Override // org.reactivestreams.Subscriber
        public final void onComplete() {
            this.done = true;
            a();
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableConcatMap.e
        public final void c() {
            this.active = false;
            a();
        }
    }

    /* loaded from: classes5.dex */
    public static final class c<T, R> extends a<T, R> {
        private static final long serialVersionUID = 7898995095634264146L;
        final Subscriber<? super R> downstream;
        final AtomicInteger wip = new AtomicInteger();

        c(Subscriber<? super R> subscriber, Function<? super T, ? extends Publisher<? extends R>> function, int i) {
            super(function, i);
            this.downstream = subscriber;
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableConcatMap.a
        void b() {
            this.downstream.onSubscribe(this);
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.inner.cancel();
            HalfSerializer.onError(this.downstream, th, this, this.errors);
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableConcatMap.e
        public void a(R r) {
            HalfSerializer.onNext(this.downstream, r, this, this.errors);
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableConcatMap.e
        public void a(Throwable th) {
            this.upstream.cancel();
            HalfSerializer.onError(this.downstream, th, this, this.errors);
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            this.inner.request(j);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (!this.cancelled) {
                this.cancelled = true;
                this.inner.cancel();
                this.upstream.cancel();
                this.errors.tryTerminateAndReport();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableConcatMap.a
        void a() {
            if (this.wip.getAndIncrement() == 0) {
                while (!this.cancelled) {
                    if (!this.active) {
                        boolean z = this.done;
                        try {
                            Object poll = this.queue.poll();
                            boolean z2 = poll == null;
                            if (z && z2) {
                                this.downstream.onComplete();
                                return;
                            } else if (!z2) {
                                try {
                                    Publisher publisher = (Publisher) Objects.requireNonNull(this.mapper.apply(poll), "The mapper returned a null Publisher");
                                    if (this.sourceMode != 1) {
                                        int i = this.consumed + 1;
                                        if (i == this.limit) {
                                            this.consumed = 0;
                                            this.upstream.request(i);
                                        } else {
                                            this.consumed = i;
                                        }
                                    }
                                    if (publisher instanceof Supplier) {
                                        try {
                                            Object obj = ((Supplier) publisher).get();
                                            if (obj == null) {
                                                continue;
                                            } else if (!this.inner.isUnbounded()) {
                                                this.active = true;
                                                this.inner.setSubscription(new f(obj, this.inner));
                                            } else if (!HalfSerializer.onNext(this.downstream, obj, this, this.errors)) {
                                                return;
                                            }
                                        } catch (Throwable th) {
                                            Exceptions.throwIfFatal(th);
                                            this.upstream.cancel();
                                            this.errors.tryAddThrowableOrReport(th);
                                            this.errors.tryTerminateConsumer(this.downstream);
                                            return;
                                        }
                                    } else {
                                        this.active = true;
                                        publisher.subscribe(this.inner);
                                    }
                                } catch (Throwable th2) {
                                    Exceptions.throwIfFatal(th2);
                                    this.upstream.cancel();
                                    this.errors.tryAddThrowableOrReport(th2);
                                    this.errors.tryTerminateConsumer(this.downstream);
                                    return;
                                }
                            }
                        } catch (Throwable th3) {
                            Exceptions.throwIfFatal(th3);
                            this.upstream.cancel();
                            this.errors.tryAddThrowableOrReport(th3);
                            this.errors.tryTerminateConsumer(this.downstream);
                            return;
                        }
                    }
                    if (this.wip.decrementAndGet() == 0) {
                        return;
                    }
                }
            }
        }
    }

    /* loaded from: classes5.dex */
    public static final class f<T> implements Subscription {
        final Subscriber<? super T> a;
        final T b;
        boolean c;

        @Override // org.reactivestreams.Subscription
        public void cancel() {
        }

        public f(T t, Subscriber<? super T> subscriber) {
            this.b = t;
            this.a = subscriber;
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (j > 0 && !this.c) {
                this.c = true;
                Subscriber<? super T> subscriber = this.a;
                subscriber.onNext((T) this.b);
                subscriber.onComplete();
            }
        }
    }

    /* loaded from: classes5.dex */
    public static final class b<T, R> extends a<T, R> {
        private static final long serialVersionUID = -2945777694260521066L;
        final Subscriber<? super R> downstream;
        final boolean veryEnd;

        b(Subscriber<? super R> subscriber, Function<? super T, ? extends Publisher<? extends R>> function, int i, boolean z) {
            super(function, i);
            this.downstream = subscriber;
            this.veryEnd = z;
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableConcatMap.a
        void b() {
            this.downstream.onSubscribe(this);
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.errors.tryAddThrowableOrReport(th)) {
                this.done = true;
                a();
            }
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableConcatMap.e
        public void a(R r) {
            this.downstream.onNext(r);
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableConcatMap.e
        public void a(Throwable th) {
            if (this.errors.tryAddThrowableOrReport(th)) {
                if (!this.veryEnd) {
                    this.upstream.cancel();
                    this.done = true;
                }
                this.active = false;
                a();
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            this.inner.request(j);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (!this.cancelled) {
                this.cancelled = true;
                this.inner.cancel();
                this.upstream.cancel();
                this.errors.tryTerminateAndReport();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableConcatMap.a
        void a() {
            Object obj;
            if (getAndIncrement() == 0) {
                while (!this.cancelled) {
                    if (!this.active) {
                        boolean z = this.done;
                        if (!z || this.veryEnd || this.errors.get() == null) {
                            try {
                                Object poll = this.queue.poll();
                                boolean z2 = poll == null;
                                if (z && z2) {
                                    this.errors.tryTerminateConsumer(this.downstream);
                                    return;
                                } else if (!z2) {
                                    try {
                                        Publisher publisher = (Publisher) Objects.requireNonNull(this.mapper.apply(poll), "The mapper returned a null Publisher");
                                        if (this.sourceMode != 1) {
                                            int i = this.consumed + 1;
                                            if (i == this.limit) {
                                                this.consumed = 0;
                                                this.upstream.request(i);
                                            } else {
                                                this.consumed = i;
                                            }
                                        }
                                        if (publisher instanceof Supplier) {
                                            try {
                                                obj = ((Supplier) publisher).get();
                                            } catch (Throwable th) {
                                                Exceptions.throwIfFatal(th);
                                                this.errors.tryAddThrowableOrReport(th);
                                                if (!this.veryEnd) {
                                                    this.upstream.cancel();
                                                    this.errors.tryTerminateConsumer(this.downstream);
                                                    return;
                                                }
                                                obj = null;
                                            }
                                            if (obj == null) {
                                                continue;
                                            } else if (this.inner.isUnbounded()) {
                                                this.downstream.onNext(obj);
                                            } else {
                                                this.active = true;
                                                this.inner.setSubscription(new f(obj, this.inner));
                                            }
                                        } else {
                                            this.active = true;
                                            publisher.subscribe(this.inner);
                                        }
                                    } catch (Throwable th2) {
                                        Exceptions.throwIfFatal(th2);
                                        this.upstream.cancel();
                                        this.errors.tryAddThrowableOrReport(th2);
                                        this.errors.tryTerminateConsumer(this.downstream);
                                        return;
                                    }
                                }
                            } catch (Throwable th3) {
                                Exceptions.throwIfFatal(th3);
                                this.upstream.cancel();
                                this.errors.tryAddThrowableOrReport(th3);
                                this.errors.tryTerminateConsumer(this.downstream);
                                return;
                            }
                        } else {
                            this.errors.tryTerminateConsumer(this.downstream);
                            return;
                        }
                    }
                    if (decrementAndGet() == 0) {
                        return;
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public static final class d<R> extends SubscriptionArbiter implements FlowableSubscriber<R> {
        private static final long serialVersionUID = 897683679971470653L;
        final e<R> parent;
        long produced;

        public d(e<R> eVar) {
            super(false);
            this.parent = eVar;
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            setSubscription(subscription);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(R r) {
            this.produced++;
            this.parent.a((e<R>) r);
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            long j = this.produced;
            if (j != 0) {
                this.produced = 0L;
                produced(j);
            }
            this.parent.a(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            long j = this.produced;
            if (j != 0) {
                this.produced = 0L;
                produced(j);
            }
            this.parent.c();
        }
    }
}
