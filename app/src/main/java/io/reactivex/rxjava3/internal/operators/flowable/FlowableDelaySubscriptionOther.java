package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes5.dex */
public final class FlowableDelaySubscriptionOther<T, U> extends Flowable<T> {
    final Publisher<? extends T> b;
    final Publisher<U> c;

    public FlowableDelaySubscriptionOther(Publisher<? extends T> publisher, Publisher<U> publisher2) {
        this.b = publisher;
        this.c = publisher2;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber<? super T> subscriber) {
        a aVar = new a(subscriber, this.b);
        subscriber.onSubscribe(aVar);
        this.c.subscribe(aVar.other);
    }

    /* loaded from: classes5.dex */
    static final class a<T> extends AtomicLong implements FlowableSubscriber<T>, Subscription {
        private static final long serialVersionUID = 2259811067697317255L;
        final Subscriber<? super T> downstream;
        final Publisher<? extends T> main;
        final a<T>.C0285a other = new C0285a();
        final AtomicReference<Subscription> upstream = new AtomicReference<>();

        a(Subscriber<? super T> subscriber, Publisher<? extends T> publisher) {
            this.downstream = subscriber;
            this.main = publisher;
        }

        void a() {
            this.main.subscribe(this);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            this.downstream.onNext(t);
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.downstream.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.downstream.onComplete();
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (SubscriptionHelper.validate(j)) {
                SubscriptionHelper.deferredRequest(this.upstream, this, j);
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            SubscriptionHelper.cancel(this.other);
            SubscriptionHelper.cancel(this.upstream);
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            SubscriptionHelper.deferredSetOnce(this.upstream, this, subscription);
        }

        /* renamed from: io.reactivex.rxjava3.internal.operators.flowable.FlowableDelaySubscriptionOther$a$a  reason: collision with other inner class name */
        /* loaded from: classes5.dex */
        final class C0285a extends AtomicReference<Subscription> implements FlowableSubscriber<Object> {
            private static final long serialVersionUID = -3892798459447644106L;

            C0285a() {
            }

            @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
            public void onSubscribe(Subscription subscription) {
                if (SubscriptionHelper.setOnce(this, subscription)) {
                    subscription.request(Long.MAX_VALUE);
                }
            }

            @Override // org.reactivestreams.Subscriber
            public void onNext(Object obj) {
                Subscription subscription = get();
                if (subscription != SubscriptionHelper.CANCELLED) {
                    lazySet(SubscriptionHelper.CANCELLED);
                    subscription.cancel();
                    a.this.a();
                }
            }

            @Override // org.reactivestreams.Subscriber
            public void onError(Throwable th) {
                if (get() != SubscriptionHelper.CANCELLED) {
                    a.this.downstream.onError(th);
                } else {
                    RxJavaPlugins.onError(th);
                }
            }

            @Override // org.reactivestreams.Subscriber
            public void onComplete() {
                if (get() != SubscriptionHelper.CANCELLED) {
                    a.this.a();
                }
            }
        }
    }
}
