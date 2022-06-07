package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;

/* loaded from: classes5.dex */
public final class ObservableFromPublisher<T> extends Observable<T> {
    final Publisher<? extends T> a;

    public ObservableFromPublisher(Publisher<? extends T> publisher) {
        this.a = publisher;
    }

    @Override // io.reactivex.rxjava3.core.Observable
    protected void subscribeActual(Observer<? super T> observer) {
        this.a.subscribe(new a(observer));
    }

    /* loaded from: classes5.dex */
    static final class a<T> implements FlowableSubscriber<T>, Disposable {
        final Observer<? super T> a;
        Subscription b;

        a(Observer<? super T> observer) {
            this.a = observer;
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.a.onComplete();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.a.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            this.a.onNext(t);
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.b, subscription)) {
                this.b = subscription;
                this.a.onSubscribe(this);
                subscription.request(Long.MAX_VALUE);
            }
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            this.b.cancel();
            this.b = SubscriptionHelper.CANCELLED;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.b == SubscriptionHelper.CANCELLED;
        }
    }
}
