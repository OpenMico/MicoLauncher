package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.internal.subscriptions.SubscriptionArbiter;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.UnicastProcessor;
import io.reactivex.subscribers.SerializedSubscriber;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes4.dex */
public final class FlowableRepeatWhen<T> extends a<T, T> {
    final Function<? super Flowable<Object>, ? extends Publisher<?>> b;

    public FlowableRepeatWhen(Flowable<T> flowable, Function<? super Flowable<Object>, ? extends Publisher<?>> function) {
        super(flowable);
        this.b = function;
    }

    @Override // io.reactivex.Flowable
    public void subscribeActual(Subscriber<? super T> subscriber) {
        SerializedSubscriber serializedSubscriber = new SerializedSubscriber(subscriber);
        FlowableProcessor<T> serialized = UnicastProcessor.create(8).toSerialized();
        try {
            Publisher publisher = (Publisher) ObjectHelper.requireNonNull(this.b.apply(serialized), "handler returned a null Publisher");
            b bVar = new b(this.source);
            a aVar = new a(serializedSubscriber, serialized, bVar);
            bVar.subscriber = aVar;
            subscriber.onSubscribe(aVar);
            publisher.subscribe(bVar);
            bVar.onNext(0);
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            EmptySubscription.error(th, subscriber);
        }
    }

    /* loaded from: classes4.dex */
    static final class b<T, U> extends AtomicInteger implements FlowableSubscriber<Object>, Subscription {
        private static final long serialVersionUID = 2827772011130406689L;
        final Publisher<T> source;
        c<T, U> subscriber;
        final AtomicReference<Subscription> upstream = new AtomicReference<>();
        final AtomicLong requested = new AtomicLong();

        public b(Publisher<T> publisher) {
            this.source = publisher;
        }

        @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            SubscriptionHelper.deferredSetOnce(this.upstream, this.requested, subscription);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(Object obj) {
            if (getAndIncrement() == 0) {
                while (this.upstream.get() != SubscriptionHelper.CANCELLED) {
                    this.source.subscribe(this.subscriber);
                    if (decrementAndGet() == 0) {
                        return;
                    }
                }
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.subscriber.cancel();
            this.subscriber.downstream.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.subscriber.cancel();
            this.subscriber.downstream.onComplete();
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            SubscriptionHelper.deferredRequest(this.upstream, this.requested, j);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            SubscriptionHelper.cancel(this.upstream);
        }
    }

    /* loaded from: classes4.dex */
    public static abstract class c<T, U> extends SubscriptionArbiter implements FlowableSubscriber<T> {
        private static final long serialVersionUID = -5604623027276966720L;
        protected final Subscriber<? super T> downstream;
        protected final FlowableProcessor<U> processor;
        private long produced;
        protected final Subscription receiver;

        public c(Subscriber<? super T> subscriber, FlowableProcessor<U> flowableProcessor, Subscription subscription) {
            super(false);
            this.downstream = subscriber;
            this.processor = flowableProcessor;
            this.receiver = subscription;
        }

        @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
        public final void onSubscribe(Subscription subscription) {
            setSubscription(subscription);
        }

        @Override // org.reactivestreams.Subscriber
        public final void onNext(T t) {
            this.produced++;
            this.downstream.onNext(t);
        }

        protected final void a(U u) {
            setSubscription(EmptySubscription.INSTANCE);
            long j = this.produced;
            if (j != 0) {
                this.produced = 0L;
                produced(j);
            }
            this.receiver.request(1L);
            this.processor.onNext(u);
        }

        @Override // io.reactivex.internal.subscriptions.SubscriptionArbiter, org.reactivestreams.Subscription
        public final void cancel() {
            super.cancel();
            this.receiver.cancel();
        }
    }

    /* loaded from: classes4.dex */
    static final class a<T> extends c<T, Object> {
        private static final long serialVersionUID = -2680129890138081029L;

        a(Subscriber<? super T> subscriber, FlowableProcessor<Object> flowableProcessor, Subscription subscription) {
            super(subscriber, flowableProcessor, subscription);
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.receiver.cancel();
            this.downstream.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            a(0);
        }
    }
}
