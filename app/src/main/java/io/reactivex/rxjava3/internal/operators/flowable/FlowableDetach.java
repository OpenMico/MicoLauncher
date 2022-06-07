package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.EmptyComponent;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes5.dex */
public final class FlowableDetach<T> extends a<T, T> {
    public FlowableDetach(Flowable<T> flowable) {
        super(flowable);
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    protected void subscribeActual(Subscriber<? super T> subscriber) {
        this.source.subscribe((FlowableSubscriber) new a(subscriber));
    }

    /* loaded from: classes5.dex */
    static final class a<T> implements FlowableSubscriber<T>, Subscription {
        Subscriber<? super T> a;
        Subscription b;

        a(Subscriber<? super T> subscriber) {
            this.a = subscriber;
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            this.b.request(j);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            Subscription subscription = this.b;
            this.b = EmptyComponent.INSTANCE;
            this.a = EmptyComponent.asSubscriber();
            subscription.cancel();
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.b, subscription)) {
                this.b = subscription;
                this.a.onSubscribe(this);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            this.a.onNext(t);
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            Subscriber<? super T> subscriber = this.a;
            this.b = EmptyComponent.INSTANCE;
            this.a = EmptyComponent.asSubscriber();
            subscriber.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            Subscriber<? super T> subscriber = this.a;
            this.b = EmptyComponent.INSTANCE;
            this.a = EmptyComponent.asSubscriber();
            subscriber.onComplete();
        }
    }
}
