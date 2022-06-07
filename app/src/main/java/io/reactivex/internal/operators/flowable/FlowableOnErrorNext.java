package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.subscriptions.SubscriptionArbiter;
import io.reactivex.plugins.RxJavaPlugins;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes4.dex */
public final class FlowableOnErrorNext<T> extends a<T, T> {
    final Function<? super Throwable, ? extends Publisher<? extends T>> b;
    final boolean c;

    public FlowableOnErrorNext(Flowable<T> flowable, Function<? super Throwable, ? extends Publisher<? extends T>> function, boolean z) {
        super(flowable);
        this.b = function;
        this.c = z;
    }

    @Override // io.reactivex.Flowable
    protected void subscribeActual(Subscriber<? super T> subscriber) {
        a aVar = new a(subscriber, this.b, this.c);
        subscriber.onSubscribe(aVar);
        this.source.subscribe((FlowableSubscriber) aVar);
    }

    /* loaded from: classes4.dex */
    static final class a<T> extends SubscriptionArbiter implements FlowableSubscriber<T> {
        private static final long serialVersionUID = 4063763155303814625L;
        final boolean allowFatal;
        boolean done;
        final Subscriber<? super T> downstream;
        final Function<? super Throwable, ? extends Publisher<? extends T>> nextSupplier;
        boolean once;
        long produced;

        a(Subscriber<? super T> subscriber, Function<? super Throwable, ? extends Publisher<? extends T>> function, boolean z) {
            super(false);
            this.downstream = subscriber;
            this.nextSupplier = function;
            this.allowFatal = z;
        }

        @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
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
                if (!this.allowFatal || (th instanceof Exception)) {
                    try {
                        Publisher publisher = (Publisher) ObjectHelper.requireNonNull(this.nextSupplier.apply(th), "The nextSupplier returned a null Publisher");
                        long j = this.produced;
                        if (j != 0) {
                            produced(j);
                        }
                        publisher.subscribe(this);
                    } catch (Throwable th2) {
                        Exceptions.throwIfFatal(th2);
                        this.downstream.onError(new CompositeException(th, th2));
                    }
                } else {
                    this.downstream.onError(th);
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
