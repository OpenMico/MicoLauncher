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
public final class FlowableElementAtSingle<T> extends Single<T> implements FuseToFlowable<T> {
    final Flowable<T> a;
    final long b;
    final T c;

    public FlowableElementAtSingle(Flowable<T> flowable, long j, T t) {
        this.a = flowable;
        this.b = j;
        this.c = t;
    }

    @Override // io.reactivex.Single
    protected void subscribeActual(SingleObserver<? super T> singleObserver) {
        this.a.subscribe((FlowableSubscriber) new a(singleObserver, this.b, this.c));
    }

    @Override // io.reactivex.internal.fuseable.FuseToFlowable
    public Flowable<T> fuseToFlowable() {
        return RxJavaPlugins.onAssembly(new FlowableElementAt(this.a, this.b, this.c, true));
    }

    /* loaded from: classes4.dex */
    static final class a<T> implements FlowableSubscriber<T>, Disposable {
        final SingleObserver<? super T> a;
        final long b;
        final T c;
        Subscription d;
        long e;
        boolean f;

        a(SingleObserver<? super T> singleObserver, long j, T t) {
            this.a = singleObserver;
            this.b = j;
            this.c = t;
        }

        @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.d, subscription)) {
                this.d = subscription;
                this.a.onSubscribe(this);
                subscription.request(Long.MAX_VALUE);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (!this.f) {
                long j = this.e;
                if (j == this.b) {
                    this.f = true;
                    this.d.cancel();
                    this.d = SubscriptionHelper.CANCELLED;
                    this.a.onSuccess(t);
                    return;
                }
                this.e = j + 1;
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.f) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.f = true;
            this.d = SubscriptionHelper.CANCELLED;
            this.a.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.d = SubscriptionHelper.CANCELLED;
            if (!this.f) {
                this.f = true;
                T t = this.c;
                if (t != null) {
                    this.a.onSuccess(t);
                } else {
                    this.a.onError(new NoSuchElementException());
                }
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            this.d.cancel();
            this.d = SubscriptionHelper.CANCELLED;
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.d == SubscriptionHelper.CANCELLED;
        }
    }
}
