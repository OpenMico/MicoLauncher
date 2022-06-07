package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.internal.subscriptions.EmptySubscription;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicLong;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes5.dex */
public final class FlowableTake<T> extends a<T, T> {
    final long b;

    public FlowableTake(Flowable<T> flowable, long j) {
        super(flowable);
        this.b = j;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    protected void subscribeActual(Subscriber<? super T> subscriber) {
        this.source.subscribe((FlowableSubscriber) new a(subscriber, this.b));
    }

    /* loaded from: classes5.dex */
    static final class a<T> extends AtomicLong implements FlowableSubscriber<T>, Subscription {
        private static final long serialVersionUID = 2288246011222124525L;
        final Subscriber<? super T> downstream;
        long remaining;
        Subscription upstream;

        /* JADX INFO: Access modifiers changed from: package-private */
        public a(Subscriber<? super T> subscriber, long j) {
            this.downstream = subscriber;
            this.remaining = j;
            lazySet(j);
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (!SubscriptionHelper.validate(this.upstream, subscription)) {
                return;
            }
            if (this.remaining == 0) {
                subscription.cancel();
                EmptySubscription.complete(this.downstream);
                return;
            }
            this.upstream = subscription;
            this.downstream.onSubscribe(this);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            long j = this.remaining;
            if (j > 0) {
                long j2 = j - 1;
                this.remaining = j2;
                this.downstream.onNext(t);
                if (j2 == 0) {
                    this.upstream.cancel();
                    this.downstream.onComplete();
                }
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.remaining > 0) {
                this.remaining = 0L;
                this.downstream.onError(th);
                return;
            }
            RxJavaPlugins.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.remaining > 0) {
                this.remaining = 0L;
                this.downstream.onComplete();
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            long j2;
            long min;
            if (SubscriptionHelper.validate(j)) {
                do {
                    j2 = get();
                    if (j2 != 0) {
                        min = Math.min(j2, j);
                    } else {
                        return;
                    }
                } while (!compareAndSet(j2, j2 - min));
                this.upstream.request(min);
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.upstream.cancel();
        }
    }
}
