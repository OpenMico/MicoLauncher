package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.FuseToFlowable;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.ArrayListSupplier;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.Collection;
import java.util.concurrent.Callable;
import org.reactivestreams.Subscription;

/* loaded from: classes4.dex */
public final class FlowableToListSingle<T, U extends Collection<? super T>> extends Single<U> implements FuseToFlowable<U> {
    final Flowable<T> a;
    final Callable<U> b;

    public FlowableToListSingle(Flowable<T> flowable) {
        this(flowable, ArrayListSupplier.asCallable());
    }

    public FlowableToListSingle(Flowable<T> flowable, Callable<U> callable) {
        this.a = flowable;
        this.b = callable;
    }

    @Override // io.reactivex.Single
    protected void subscribeActual(SingleObserver<? super U> singleObserver) {
        try {
            this.a.subscribe((FlowableSubscriber) new a(singleObserver, (Collection) ObjectHelper.requireNonNull(this.b.call(), "The collectionSupplier returned a null collection. Null values are generally not allowed in 2.x operators and sources.")));
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            EmptyDisposable.error(th, singleObserver);
        }
    }

    @Override // io.reactivex.internal.fuseable.FuseToFlowable
    public Flowable<U> fuseToFlowable() {
        return RxJavaPlugins.onAssembly(new FlowableToList(this.a, this.b));
    }

    /* loaded from: classes4.dex */
    static final class a<T, U extends Collection<? super T>> implements FlowableSubscriber<T>, Disposable {
        final SingleObserver<? super U> a;
        Subscription b;
        U c;

        a(SingleObserver<? super U> singleObserver, U u) {
            this.a = singleObserver;
            this.c = u;
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
            this.c.add(t);
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.c = null;
            this.b = SubscriptionHelper.CANCELLED;
            this.a.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.b = SubscriptionHelper.CANCELLED;
            this.a.onSuccess(this.c);
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
