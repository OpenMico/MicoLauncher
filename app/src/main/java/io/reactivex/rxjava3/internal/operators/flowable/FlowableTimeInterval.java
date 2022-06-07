package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.schedulers.Timed;
import java.util.concurrent.TimeUnit;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes5.dex */
public final class FlowableTimeInterval<T> extends a<T, Timed<T>> {
    final Scheduler b;
    final TimeUnit c;

    public FlowableTimeInterval(Flowable<T> flowable, TimeUnit timeUnit, Scheduler scheduler) {
        super(flowable);
        this.b = scheduler;
        this.c = timeUnit;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    protected void subscribeActual(Subscriber<? super Timed<T>> subscriber) {
        this.source.subscribe((FlowableSubscriber) new a(subscriber, this.c, this.b));
    }

    /* loaded from: classes5.dex */
    static final class a<T> implements FlowableSubscriber<T>, Subscription {
        final Subscriber<? super Timed<T>> a;
        final TimeUnit b;
        final Scheduler c;
        Subscription d;
        long e;

        a(Subscriber<? super Timed<T>> subscriber, TimeUnit timeUnit, Scheduler scheduler) {
            this.a = subscriber;
            this.c = scheduler;
            this.b = timeUnit;
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.d, subscription)) {
                this.e = this.c.now(this.b);
                this.d = subscription;
                this.a.onSubscribe(this);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            long now = this.c.now(this.b);
            long j = this.e;
            this.e = now;
            this.a.onNext(new Timed(t, now - j, this.b));
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
            this.d.request(j);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.d.cancel();
        }
    }
}
