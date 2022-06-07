package io.reactivex.internal.operators.single;

import io.reactivex.FlowableSubscriber;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.CancellationException;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;

/* loaded from: classes4.dex */
public final class SingleTakeUntil<T, U> extends Single<T> {
    final SingleSource<T> a;
    final Publisher<U> b;

    public SingleTakeUntil(SingleSource<T> singleSource, Publisher<U> publisher) {
        this.a = singleSource;
        this.b = publisher;
    }

    @Override // io.reactivex.Single
    protected void subscribeActual(SingleObserver<? super T> singleObserver) {
        a aVar = new a(singleObserver);
        singleObserver.onSubscribe(aVar);
        this.b.subscribe(aVar.other);
        this.a.subscribe(aVar);
    }

    /* loaded from: classes4.dex */
    static final class a<T> extends AtomicReference<Disposable> implements SingleObserver<T>, Disposable {
        private static final long serialVersionUID = -622603812305745221L;
        final SingleObserver<? super T> downstream;
        final b other = new b(this);

        a(SingleObserver<? super T> singleObserver) {
            this.downstream = singleObserver;
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            DisposableHelper.dispose(this);
            this.other.a();
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return DisposableHelper.isDisposed(get());
        }

        @Override // io.reactivex.SingleObserver
        public void onSubscribe(Disposable disposable) {
            DisposableHelper.setOnce(this, disposable);
        }

        @Override // io.reactivex.SingleObserver
        public void onSuccess(T t) {
            this.other.a();
            if (getAndSet(DisposableHelper.DISPOSED) != DisposableHelper.DISPOSED) {
                this.downstream.onSuccess(t);
            }
        }

        @Override // io.reactivex.SingleObserver
        public void onError(Throwable th) {
            this.other.a();
            if (get() == DisposableHelper.DISPOSED || getAndSet(DisposableHelper.DISPOSED) == DisposableHelper.DISPOSED) {
                RxJavaPlugins.onError(th);
            } else {
                this.downstream.onError(th);
            }
        }

        void a(Throwable th) {
            Disposable andSet;
            if (get() == DisposableHelper.DISPOSED || (andSet = getAndSet(DisposableHelper.DISPOSED)) == DisposableHelper.DISPOSED) {
                RxJavaPlugins.onError(th);
                return;
            }
            if (andSet != null) {
                andSet.dispose();
            }
            this.downstream.onError(th);
        }
    }

    /* loaded from: classes4.dex */
    static final class b extends AtomicReference<Subscription> implements FlowableSubscriber<Object> {
        private static final long serialVersionUID = 5170026210238877381L;
        final a<?> parent;

        b(a<?> aVar) {
            this.parent = aVar;
        }

        @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            SubscriptionHelper.setOnce(this, subscription, Long.MAX_VALUE);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(Object obj) {
            if (SubscriptionHelper.cancel(this)) {
                this.parent.a(new CancellationException());
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.parent.a(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (get() != SubscriptionHelper.CANCELLED) {
                lazySet(SubscriptionHelper.CANCELLED);
                this.parent.a(new CancellationException());
            }
        }

        public void a() {
            SubscriptionHelper.cancel(this);
        }
    }
}
