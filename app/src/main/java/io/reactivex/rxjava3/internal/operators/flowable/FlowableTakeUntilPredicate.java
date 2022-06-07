package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Predicate;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes5.dex */
public final class FlowableTakeUntilPredicate<T> extends a<T, T> {
    final Predicate<? super T> b;

    public FlowableTakeUntilPredicate(Flowable<T> flowable, Predicate<? super T> predicate) {
        super(flowable);
        this.b = predicate;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    protected void subscribeActual(Subscriber<? super T> subscriber) {
        this.source.subscribe((FlowableSubscriber) new a(subscriber, this.b));
    }

    /* loaded from: classes5.dex */
    static final class a<T> implements FlowableSubscriber<T>, Subscription {
        final Subscriber<? super T> a;
        final Predicate<? super T> b;
        Subscription c;
        boolean d;

        a(Subscriber<? super T> subscriber, Predicate<? super T> predicate) {
            this.a = subscriber;
            this.b = predicate;
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.c, subscription)) {
                this.c = subscription;
                this.a.onSubscribe(this);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (!this.d) {
                this.a.onNext(t);
                try {
                    if (this.b.test(t)) {
                        this.d = true;
                        this.c.cancel();
                        this.a.onComplete();
                    }
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    this.c.cancel();
                    onError(th);
                }
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (!this.d) {
                this.d = true;
                this.a.onError(th);
                return;
            }
            RxJavaPlugins.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (!this.d) {
                this.d = true;
                this.a.onComplete();
            }
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
