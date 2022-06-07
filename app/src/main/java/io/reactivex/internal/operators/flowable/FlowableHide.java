package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes4.dex */
public final class FlowableHide<T> extends a<T, T> {
    public FlowableHide(Flowable<T> flowable) {
        super(flowable);
    }

    @Override // io.reactivex.Flowable
    protected void subscribeActual(Subscriber<? super T> subscriber) {
        this.source.subscribe((FlowableSubscriber) new a(subscriber));
    }

    /* loaded from: classes4.dex */
    static final class a<T> implements FlowableSubscriber<T>, Subscription {
        final Subscriber<? super T> a;
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
            this.b.cancel();
        }

        @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
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
            this.a.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.a.onComplete();
        }
    }
}
