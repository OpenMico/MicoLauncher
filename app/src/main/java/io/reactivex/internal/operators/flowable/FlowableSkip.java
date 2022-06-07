package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes4.dex */
public final class FlowableSkip<T> extends a<T, T> {
    final long b;

    public FlowableSkip(Flowable<T> flowable, long j) {
        super(flowable);
        this.b = j;
    }

    @Override // io.reactivex.Flowable
    protected void subscribeActual(Subscriber<? super T> subscriber) {
        this.source.subscribe((FlowableSubscriber) new a(subscriber, this.b));
    }

    /* loaded from: classes4.dex */
    static final class a<T> implements FlowableSubscriber<T>, Subscription {
        final Subscriber<? super T> a;
        long b;
        Subscription c;

        a(Subscriber<? super T> subscriber, long j) {
            this.a = subscriber;
            this.b = j;
        }

        @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.c, subscription)) {
                long j = this.b;
                this.c = subscription;
                this.a.onSubscribe(this);
                subscription.request(j);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            long j = this.b;
            if (j != 0) {
                this.b = j - 1;
            } else {
                this.a.onNext(t);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.a.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.a.onComplete();
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            this.c.request(j);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.c.cancel();
        }
    }
}
