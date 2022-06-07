package io.reactivex.subscribers;

import io.reactivex.FlowableSubscriber;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.EndConsumerHelper;
import org.reactivestreams.Subscription;

/* loaded from: classes5.dex */
public abstract class DefaultSubscriber<T> implements FlowableSubscriber<T> {
    Subscription b;

    @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
    public final void onSubscribe(Subscription subscription) {
        if (EndConsumerHelper.validate(this.b, subscription, getClass())) {
            this.b = subscription;
            onStart();
        }
    }

    protected final void request(long j) {
        Subscription subscription = this.b;
        if (subscription != null) {
            subscription.request(j);
        }
    }

    protected final void cancel() {
        Subscription subscription = this.b;
        this.b = SubscriptionHelper.CANCELLED;
        subscription.cancel();
    }

    protected void onStart() {
        request(Long.MAX_VALUE);
    }
}
