package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.fuseable.FuseToFlowable;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import org.reactivestreams.Subscription;

/* loaded from: classes4.dex */
public final class FlowableCountSingle<T> extends Single<Long> implements FuseToFlowable<Long> {
    final Flowable<T> a;

    public FlowableCountSingle(Flowable<T> flowable) {
        this.a = flowable;
    }

    @Override // io.reactivex.Single
    protected void subscribeActual(SingleObserver<? super Long> singleObserver) {
        this.a.subscribe((FlowableSubscriber) new a(singleObserver));
    }

    @Override // io.reactivex.internal.fuseable.FuseToFlowable
    public Flowable<Long> fuseToFlowable() {
        return RxJavaPlugins.onAssembly(new FlowableCount(this.a));
    }

    /* loaded from: classes4.dex */
    static final class a implements FlowableSubscriber<Object>, Disposable {
        final SingleObserver<? super Long> a;
        Subscription b;
        long c;

        a(SingleObserver<? super Long> singleObserver) {
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
        public void onNext(Object obj) {
            this.c++;
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.b = SubscriptionHelper.CANCELLED;
            this.a.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.b = SubscriptionHelper.CANCELLED;
            this.a.onSuccess(Long.valueOf(this.c));
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
    }
}
