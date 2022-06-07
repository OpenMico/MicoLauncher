package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.AtomicThrowable;
import io.reactivex.rxjava3.internal.util.HalfSerializer;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes5.dex */
public final class FlowableTakeUntil<T, U> extends a<T, T> {
    final Publisher<? extends U> b;

    public FlowableTakeUntil(Flowable<T> flowable, Publisher<? extends U> publisher) {
        super(flowable);
        this.b = publisher;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    protected void subscribeActual(Subscriber<? super T> subscriber) {
        a aVar = new a(subscriber);
        subscriber.onSubscribe(aVar);
        this.b.subscribe(aVar.other);
        this.source.subscribe((FlowableSubscriber) aVar);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public static final class a<T> extends AtomicInteger implements FlowableSubscriber<T>, Subscription {
        private static final long serialVersionUID = -4945480365982832967L;
        final Subscriber<? super T> downstream;
        final AtomicLong requested = new AtomicLong();
        final AtomicReference<Subscription> upstream = new AtomicReference<>();
        final a<T>.C0295a other = new C0295a();
        final AtomicThrowable error = new AtomicThrowable();

        a(Subscriber<? super T> subscriber) {
            this.downstream = subscriber;
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            SubscriptionHelper.deferredSetOnce(this.upstream, this.requested, subscription);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            HalfSerializer.onNext(this.downstream, t, this, this.error);
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            SubscriptionHelper.cancel(this.other);
            HalfSerializer.onError(this.downstream, th, this, this.error);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            SubscriptionHelper.cancel(this.other);
            HalfSerializer.onComplete(this.downstream, this, this.error);
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            SubscriptionHelper.deferredRequest(this.upstream, this.requested, j);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            SubscriptionHelper.cancel(this.upstream);
            SubscriptionHelper.cancel(this.other);
        }

        /* renamed from: io.reactivex.rxjava3.internal.operators.flowable.FlowableTakeUntil$a$a  reason: collision with other inner class name */
        /* loaded from: classes5.dex */
        final class C0295a extends AtomicReference<Subscription> implements FlowableSubscriber<Object> {
            private static final long serialVersionUID = -3592821756711087922L;

            C0295a() {
            }

            @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
            public void onSubscribe(Subscription subscription) {
                SubscriptionHelper.setOnce(this, subscription, Long.MAX_VALUE);
            }

            @Override // org.reactivestreams.Subscriber
            public void onNext(Object obj) {
                SubscriptionHelper.cancel(this);
                onComplete();
            }

            @Override // org.reactivestreams.Subscriber
            public void onError(Throwable th) {
                SubscriptionHelper.cancel(a.this.upstream);
                Subscriber<? super T> subscriber = a.this.downstream;
                a aVar = a.this;
                HalfSerializer.onError(subscriber, th, aVar, aVar.error);
            }

            @Override // org.reactivestreams.Subscriber
            public void onComplete() {
                SubscriptionHelper.cancel(a.this.upstream);
                Subscriber<? super T> subscriber = a.this.downstream;
                a aVar = a.this;
                HalfSerializer.onComplete(subscriber, aVar, aVar.error);
            }
        }
    }
}
