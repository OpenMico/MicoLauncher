package io.reactivex.internal.operators.single;

import io.reactivex.FlowableSubscriber;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.NoSuchElementException;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;

/* loaded from: classes4.dex */
public final class SingleFromPublisher<T> extends Single<T> {
    final Publisher<? extends T> a;

    public SingleFromPublisher(Publisher<? extends T> publisher) {
        this.a = publisher;
    }

    @Override // io.reactivex.Single
    protected void subscribeActual(SingleObserver<? super T> singleObserver) {
        this.a.subscribe(new a(singleObserver));
    }

    /* loaded from: classes4.dex */
    static final class a<T> implements FlowableSubscriber<T>, Disposable {
        final SingleObserver<? super T> a;
        Subscription b;
        T c;
        boolean d;
        volatile boolean e;

        a(SingleObserver<? super T> singleObserver) {
            this.a = singleObserver;
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

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.e;
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            this.e = true;
            this.b.cancel();
        }
    }
}
