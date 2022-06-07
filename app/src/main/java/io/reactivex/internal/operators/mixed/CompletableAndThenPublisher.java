package io.reactivex.internal.operators.mixed;

import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes4.dex */
public final class CompletableAndThenPublisher<R> extends Flowable<R> {
    final CompletableSource b;
    final Publisher<? extends R> c;

    public CompletableAndThenPublisher(CompletableSource completableSource, Publisher<? extends R> publisher) {
        this.b = completableSource;
        this.c = publisher;
    }

    @Override // io.reactivex.Flowable
    protected void subscribeActual(Subscriber<? super R> subscriber) {
        this.b.subscribe(new a(subscriber, this.c));
    }

    /* loaded from: classes4.dex */
    static final class a<R> extends AtomicReference<Subscription> implements CompletableObserver, FlowableSubscriber<R>, Subscription {
        private static final long serialVersionUID = -8948264376121066672L;
        final Subscriber<? super R> downstream;
        Publisher<? extends R> other;
        final AtomicLong requested = new AtomicLong();
        Disposable upstream;

        a(Subscriber<? super R> subscriber, Publisher<? extends R> publisher) {
            this.downstream = subscriber;
            this.other = publisher;
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(R r) {
            this.downstream.onNext(r);
        }

        @Override // io.reactivex.CompletableObserver, io.reactivex.MaybeObserver, io.reactivex.SingleObserver
        public void onError(Throwable th) {
            this.downstream.onError(th);
        }

        @Override // io.reactivex.CompletableObserver, io.reactivex.MaybeObserver
        public void onComplete() {
            Publisher<? extends R> publisher = this.other;
            if (publisher == null) {
                this.downstream.onComplete();
                return;
            }
            this.other = null;
            publisher.subscribe(this);
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            SubscriptionHelper.deferredRequest(this, this.requested, j);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.upstream.dispose();
            SubscriptionHelper.cancel(this);
        }

        @Override // io.reactivex.CompletableObserver, io.reactivex.MaybeObserver, io.reactivex.SingleObserver
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.upstream, disposable)) {
                this.upstream = disposable;
                this.downstream.onSubscribe(this);
            }
        }

        @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            SubscriptionHelper.deferredSetOnce(this, this.requested, subscription);
        }
    }
}
