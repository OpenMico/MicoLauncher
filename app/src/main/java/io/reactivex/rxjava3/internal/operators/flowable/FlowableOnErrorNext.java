package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.exceptions.CompositeException;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionArbiter;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.Objects;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes5.dex */
public final class FlowableOnErrorNext<T> extends a<T, T> {
    final Function<? super Throwable, ? extends Publisher<? extends T>> b;

    public FlowableOnErrorNext(Flowable<T> flowable, Function<? super Throwable, ? extends Publisher<? extends T>> function) {
        super(flowable);
        this.b = function;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    protected void subscribeActual(Subscriber<? super T> subscriber) {
        a aVar = new a(subscriber, this.b);
        subscriber.onSubscribe(aVar);
        this.source.subscribe((FlowableSubscriber) aVar);
    }

    /* loaded from: classes5.dex */
    static final class a<T> extends SubscriptionArbiter implements FlowableSubscriber<T> {
        private static final long serialVersionUID = 4063763155303814625L;
        boolean done;
        final Subscriber<? super T> downstream;
        final Function<? super Throwable, ? extends Publisher<? extends T>> nextSupplier;
        boolean once;
        long produced;

        a(Subscriber<? super T> subscriber, Function<? super Throwable, ? extends Publisher<? extends T>> function) {
            super(false);
            this.downstream = subscriber;
            this.nextSupplier = function;
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            setSubscription(subscription);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (!this.done) {
                if (!this.once) {
                    this.produced++;
                }
                this.downstream.onNext(t);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (!this.once) {
                this.once = true;
                try {
                    Publisher publisher = (Publisher) Objects.requireNonNull(this.nextSupplier.apply(th), "The nextSupplier returned a null Publisher");
                    long j = this.produced;
                    if (j != 0) {
                        produced(j);
                    }
                    publisher.subscribe(this);
                } catch (Throwable th2) {
                    Exceptions.throwIfFatal(th2);
                    this.downstream.onError(new CompositeException(th, th2));
                }
            } else if (this.done) {
                RxJavaPlugins.onError(th);
            } else {
                this.downstream.onError(th);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (!this.done) {
                this.done = true;
                this.once = true;
                this.downstream.onComplete();
            }
        }
    }
}
