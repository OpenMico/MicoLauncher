package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.fuseable.FuseToFlowable;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.NoSuchElementException;
import org.reactivestreams.Subscription;

/* loaded from: classes4.dex */
public final class FlowableSingleSingle<T> extends Single<T> implements FuseToFlowable<T> {
    final Flowable<T> a;
    final T b;

    public FlowableSingleSingle(Flowable<T> flowable, T t) {
        this.a = flowable;
        this.b = t;
    }

    @Override // io.reactivex.Single
    protected void subscribeActual(SingleObserver<? super T> singleObserver) {
        this.a.subscribe((FlowableSubscriber) new a(singleObserver, this.b));
    }

    @Override // io.reactivex.internal.fuseable.FuseToFlowable
    public Flowable<T> fuseToFlowable() {
        return RxJavaPlugins.onAssembly(new FlowableSingle(this.a, this.b, true));
    }

    /* loaded from: classes4.dex */
    static final class a<T> implements FlowableSubscriber<T>, Disposable {
        final SingleObserver<? super T> a;
        final T b;
        Subscription c;
        boolean d;
        T e;

        a(SingleObserver<? super T> singleObserver, T t) {
            this.a = singleObserver;
            this.b = t;
        }

        @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.c, subscription)) {
                this.c = subscription;
                this.a.onSubscribe(this);
                subscription.request(Long.MAX_VALUE);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (!this.d) {
                if (this.e != null) {
                    this.d = true;
                    this.c.cancel();
                    this.c = SubscriptionHelper.CANCELLED;
                    this.a.onError(new IllegalArgumentException("Sequence contains more than one element!"));
                    return;
                }
                this.e = t;
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.d) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.d = true;
            this.c = SubscriptionHelper.CANCELLED;
            this.a.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (!this.d) {
                this.d = true;
                this.c = SubscriptionHelper.CANCELLED;
                T t = this.e;
                this.e = null;
                if (t == null) {
                    t = this.b;
                }
                if (t != null) {
                    this.a.onSuccess(t);
                } else {
                    this.a.onError(new NoSuchElementException());
                }
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            this.c.cancel();
            this.c = SubscriptionHelper.CANCELLED;
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.c == SubscriptionHelper.CANCELLED;
        }
    }
}
