package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.internal.subscriptions.SubscriptionArbiter;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes4.dex */
public final class FlowableSwitchIfEmpty<T> extends a<T, T> {
    final Publisher<? extends T> b;

    public FlowableSwitchIfEmpty(Flowable<T> flowable, Publisher<? extends T> publisher) {
        super(flowable);
        this.b = publisher;
    }

    @Override // io.reactivex.Flowable
    protected void subscribeActual(Subscriber<? super T> subscriber) {
        a aVar = new a(subscriber, this.b);
        subscriber.onSubscribe(aVar.c);
        this.source.subscribe((FlowableSubscriber) aVar);
    }

    /* loaded from: classes4.dex */
    static final class a<T> implements FlowableSubscriber<T> {
        final Subscriber<? super T> a;
        final Publisher<? extends T> b;
        boolean d = true;
        final SubscriptionArbiter c = new SubscriptionArbiter(false);

        a(Subscriber<? super T> subscriber, Publisher<? extends T> publisher) {
            this.a = subscriber;
            this.b = publisher;
        }

        @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            this.c.setSubscription(subscription);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (this.d) {
                this.d = false;
            }
            this.a.onNext(t);
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.a.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.d) {
                this.d = false;
                this.b.subscribe(this);
                return;
            }
            this.a.onComplete();
        }
    }
}
