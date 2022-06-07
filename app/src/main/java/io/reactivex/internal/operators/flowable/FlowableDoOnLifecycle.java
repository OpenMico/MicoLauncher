package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.LongConsumer;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes4.dex */
public final class FlowableDoOnLifecycle<T> extends a<T, T> {
    private final Consumer<? super Subscription> b;
    private final LongConsumer c;
    private final Action d;

    public FlowableDoOnLifecycle(Flowable<T> flowable, Consumer<? super Subscription> consumer, LongConsumer longConsumer, Action action) {
        super(flowable);
        this.b = consumer;
        this.c = longConsumer;
        this.d = action;
    }

    @Override // io.reactivex.Flowable
    protected void subscribeActual(Subscriber<? super T> subscriber) {
        this.source.subscribe((FlowableSubscriber) new a(subscriber, this.b, this.c, this.d));
    }

    /* loaded from: classes4.dex */
    static final class a<T> implements FlowableSubscriber<T>, Subscription {
        final Subscriber<? super T> a;
        final Consumer<? super Subscription> b;
        final LongConsumer c;
        final Action d;
        Subscription e;

        a(Subscriber<? super T> subscriber, Consumer<? super Subscription> consumer, LongConsumer longConsumer, Action action) {
            this.a = subscriber;
            this.b = consumer;
            this.d = action;
            this.c = longConsumer;
        }

        @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            try {
                this.b.accept(subscription);
                if (SubscriptionHelper.validate(this.e, subscription)) {
                    this.e = subscription;
                    this.a.onSubscribe(this);
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                subscription.cancel();
                this.e = SubscriptionHelper.CANCELLED;
                EmptySubscription.error(th, this.a);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            this.a.onNext(t);
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.e != SubscriptionHelper.CANCELLED) {
                this.a.onError(th);
            } else {
                RxJavaPlugins.onError(th);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.e != SubscriptionHelper.CANCELLED) {
                this.a.onComplete();
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            try {
                this.c.accept(j);
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                RxJavaPlugins.onError(th);
            }
            this.e.request(j);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            Subscription subscription = this.e;
            if (subscription != SubscriptionHelper.CANCELLED) {
                this.e = SubscriptionHelper.CANCELLED;
                try {
                    this.d.run();
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    RxJavaPlugins.onError(th);
                }
                subscription.cancel();
            }
        }
    }
}
