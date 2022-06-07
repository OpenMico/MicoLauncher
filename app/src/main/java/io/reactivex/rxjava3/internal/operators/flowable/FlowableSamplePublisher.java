package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.exceptions.MissingBackpressureException;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.BackpressureHelper;
import io.reactivex.rxjava3.subscribers.SerializedSubscriber;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes5.dex */
public final class FlowableSamplePublisher<T> extends Flowable<T> {
    final Publisher<T> b;
    final Publisher<?> c;
    final boolean d;

    public FlowableSamplePublisher(Publisher<T> publisher, Publisher<?> publisher2, boolean z) {
        this.b = publisher;
        this.c = publisher2;
        this.d = z;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    protected void subscribeActual(Subscriber<? super T> subscriber) {
        SerializedSubscriber serializedSubscriber = new SerializedSubscriber(subscriber);
        if (this.d) {
            this.b.subscribe(new a(serializedSubscriber, this.c));
        } else {
            this.b.subscribe(new b(serializedSubscriber, this.c));
        }
    }

    /* loaded from: classes5.dex */
    static abstract class c<T> extends AtomicReference<T> implements FlowableSubscriber<T>, Subscription {
        private static final long serialVersionUID = -3517602651313910099L;
        final Subscriber<? super T> downstream;
        final Publisher<?> sampler;
        Subscription upstream;
        final AtomicLong requested = new AtomicLong();
        final AtomicReference<Subscription> other = new AtomicReference<>();

        abstract void a();

        abstract void b();

        c(Subscriber<? super T> subscriber, Publisher<?> publisher) {
            this.downstream = subscriber;
            this.sampler = publisher;
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.upstream, subscription)) {
                this.upstream = subscription;
                this.downstream.onSubscribe(this);
                if (this.other.get() == null) {
                    this.sampler.subscribe(new d(this));
                    subscription.request(Long.MAX_VALUE);
                }
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            lazySet(t);
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            SubscriptionHelper.cancel(this.other);
            this.downstream.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            SubscriptionHelper.cancel(this.other);
            a();
        }

        void a(Subscription subscription) {
            SubscriptionHelper.setOnce(this.other, subscription, Long.MAX_VALUE);
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (SubscriptionHelper.validate(j)) {
                BackpressureHelper.add(this.requested, j);
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            SubscriptionHelper.cancel(this.other);
            this.upstream.cancel();
        }

        public void a(Throwable th) {
            this.upstream.cancel();
            this.downstream.onError(th);
        }

        public void c() {
            this.upstream.cancel();
            a();
        }

        void d() {
            T andSet = getAndSet(null);
            if (andSet == null) {
                return;
            }
            if (this.requested.get() != 0) {
                this.downstream.onNext(andSet);
                BackpressureHelper.produced(this.requested, 1L);
                return;
            }
            cancel();
            this.downstream.onError(new MissingBackpressureException("Couldn't emit value due to lack of requests!"));
        }
    }

    /* loaded from: classes5.dex */
    static final class d<T> implements FlowableSubscriber<Object> {
        final c<T> a;

        d(c<T> cVar) {
            this.a = cVar;
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            this.a.a(subscription);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(Object obj) {
            this.a.b();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.a.a(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.a.c();
        }
    }

    /* loaded from: classes5.dex */
    static final class b<T> extends c<T> {
        private static final long serialVersionUID = -3029755663834015785L;

        b(Subscriber<? super T> subscriber, Publisher<?> publisher) {
            super(subscriber, publisher);
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableSamplePublisher.c
        void a() {
            this.downstream.onComplete();
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableSamplePublisher.c
        void b() {
            d();
        }
    }

    /* loaded from: classes5.dex */
    static final class a<T> extends c<T> {
        private static final long serialVersionUID = -3029755663834015785L;
        volatile boolean done;
        final AtomicInteger wip = new AtomicInteger();

        a(Subscriber<? super T> subscriber, Publisher<?> publisher) {
            super(subscriber, publisher);
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableSamplePublisher.c
        void a() {
            this.done = true;
            if (this.wip.getAndIncrement() == 0) {
                d();
                this.downstream.onComplete();
            }
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableSamplePublisher.c
        void b() {
            if (this.wip.getAndIncrement() == 0) {
                do {
                    boolean z = this.done;
                    d();
                    if (z) {
                        this.downstream.onComplete();
                        return;
                    }
                } while (this.wip.decrementAndGet() != 0);
            }
        }
    }
}
