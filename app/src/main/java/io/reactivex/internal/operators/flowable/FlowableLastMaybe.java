package io.reactivex.internal.operators.flowable;

import io.reactivex.FlowableSubscriber;
import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;

/* loaded from: classes4.dex */
public final class FlowableLastMaybe<T> extends Maybe<T> {
    final Publisher<T> a;

    public FlowableLastMaybe(Publisher<T> publisher) {
        this.a = publisher;
    }

    @Override // io.reactivex.Maybe
    protected void subscribeActual(MaybeObserver<? super T> maybeObserver) {
        this.a.subscribe(new a(maybeObserver));
    }

    /* loaded from: classes4.dex */
    static final class a<T> implements FlowableSubscriber<T>, Disposable {
        final MaybeObserver<? super T> a;
        Subscription b;
        T c;

        a(MaybeObserver<? super T> maybeObserver) {
            this.a = maybeObserver;
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            this.b.cancel();
            this.b = SubscriptionHelper.CANCELLED;
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.b == SubscriptionHelper.CANCELLED;
        }

        @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.b, subscription)) {
                this.b = subscription;
                this.a.onSubscribe(this);
                subscription.request(Long.MAX_VALUE);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            this.c = t;
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.b = SubscriptionHelper.CANCELLED;
            this.c = null;
            this.a.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.b = SubscriptionHelper.CANCELLED;
            T t = this.c;
            if (t != null) {
                this.c = null;
                this.a.onSuccess(t);
                return;
            }
            this.a.onComplete();
        }
    }
}
