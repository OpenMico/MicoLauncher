package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.BiFunction;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.Objects;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;

/* loaded from: classes5.dex */
public final class FlowableReduceSeedSingle<T, R> extends Single<R> {
    final Publisher<T> a;
    final R b;
    final BiFunction<R, ? super T, R> c;

    public FlowableReduceSeedSingle(Publisher<T> publisher, R r, BiFunction<R, ? super T, R> biFunction) {
        this.a = publisher;
        this.b = r;
        this.c = biFunction;
    }

    @Override // io.reactivex.rxjava3.core.Single
    protected void subscribeActual(SingleObserver<? super R> singleObserver) {
        this.a.subscribe(new a(singleObserver, this.c, this.b));
    }

    /* loaded from: classes5.dex */
    static final class a<T, R> implements FlowableSubscriber<T>, Disposable {
        final SingleObserver<? super R> a;
        final BiFunction<R, ? super T, R> b;
        R c;
        Subscription d;

        public a(SingleObserver<? super R> singleObserver, BiFunction<R, ? super T, R> biFunction, R r) {
            this.a = singleObserver;
            this.c = r;
            this.b = biFunction;
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.d, subscription)) {
                this.d = subscription;
                this.a.onSubscribe(this);
                subscription.request(Long.MAX_VALUE);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            R r = this.c;
            if (r != null) {
                try {
                    this.c = (R) Objects.requireNonNull(this.b.apply(r, t), "The reducer returned a null value");
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    this.d.cancel();
                    onError(th);
                }
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.c != null) {
                this.c = null;
                this.d = SubscriptionHelper.CANCELLED;
                this.a.onError(th);
                return;
            }
            RxJavaPlugins.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            R r = this.c;
            if (r != null) {
                this.c = null;
                this.d = SubscriptionHelper.CANCELLED;
                this.a.onSuccess(r);
            }
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            this.d.cancel();
            this.d = SubscriptionHelper.CANCELLED;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.d == SubscriptionHelper.CANCELLED;
        }
    }
}
