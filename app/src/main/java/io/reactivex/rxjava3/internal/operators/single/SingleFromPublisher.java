package io.reactivex.rxjava3.internal.operators.single;

import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.NoSuchElementException;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;

/* loaded from: classes5.dex */
public final class SingleFromPublisher<T> extends Single<T> {
    final Publisher<? extends T> a;

    public SingleFromPublisher(Publisher<? extends T> publisher) {
        this.a = publisher;
    }

    @Override // io.reactivex.rxjava3.core.Single
    protected void subscribeActual(SingleObserver<? super T> singleObserver) {
        this.a.subscribe(new a(singleObserver));
    }

    /* loaded from: classes5.dex */
    static final class a<T> implements FlowableSubscriber<T>, Disposable {
        final SingleObserver<? super T> a;
        Subscription b;
        T c;
        boolean d;
        volatile boolean e;

        a(SingleObserver<? super T> singleObserver) {
            this.a = singleObserver;
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.b, subscription)) {
                this.b = subscription;
                this.a.onSubscribe(this);
                subscription.request(Long.MAX_VALUE);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (!this.d) {
                if (this.c != null) {
                    this.b.cancel();
                    this.d = true;
                    this.c = null;
                    this.a.onError(new IndexOutOfBoundsException("Too many elements in the Publisher"));
                    return;
                }
                this.c = t;
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.d) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.d = true;
            this.c = null;
            this.a.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (!this.d) {
                this.d = true;
                T t = this.c;
                this.c = null;
                if (t == null) {
                    this.a.onError(new NoSuchElementException("The source Publisher is empty"));
                } else {
                    this.a.onSuccess(t);
                }
            }
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.e;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            this.e = true;
            this.b.cancel();
        }
    }
}
