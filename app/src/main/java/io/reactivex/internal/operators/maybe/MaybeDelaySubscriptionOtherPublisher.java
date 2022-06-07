package io.reactivex.internal.operators.maybe;

import io.reactivex.FlowableSubscriber;
import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;

/* loaded from: classes4.dex */
public final class MaybeDelaySubscriptionOtherPublisher<T, U> extends a<T, T> {
    final Publisher<U> a;

    public MaybeDelaySubscriptionOtherPublisher(MaybeSource<T> maybeSource, Publisher<U> publisher) {
        super(maybeSource);
        this.a = publisher;
    }

    @Override // io.reactivex.Maybe
    protected void subscribeActual(MaybeObserver<? super T> maybeObserver) {
        this.a.subscribe(new b(maybeObserver, this.source));
    }

    /* loaded from: classes4.dex */
    static final class b<T> implements FlowableSubscriber<Object>, Disposable {
        final a<T> a;
        MaybeSource<T> b;
        Subscription c;

        b(MaybeObserver<? super T> maybeObserver, MaybeSource<T> maybeSource) {
            this.a = new a<>(maybeObserver);
            this.b = maybeSource;
        }

        @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.c, subscription)) {
                this.c = subscription;
                this.a.downstream.onSubscribe(this);
                subscription.request(Long.MAX_VALUE);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(Object obj) {
            if (this.c != SubscriptionHelper.CANCELLED) {
                this.c.cancel();
                this.c = SubscriptionHelper.CANCELLED;
                a();
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.c != SubscriptionHelper.CANCELLED) {
                this.c = SubscriptionHelper.CANCELLED;
                this.a.downstream.onError(th);
                return;
            }
            RxJavaPlugins.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.c != SubscriptionHelper.CANCELLED) {
                this.c = SubscriptionHelper.CANCELLED;
                a();
            }
        }

        void a() {
            MaybeSource<T> maybeSource = this.b;
            this.b = null;
            maybeSource.subscribe(this.a);
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return DisposableHelper.isDisposed(this.a.get());
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            this.c.cancel();
            this.c = SubscriptionHelper.CANCELLED;
            DisposableHelper.dispose(this.a);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class a<T> extends AtomicReference<Disposable> implements MaybeObserver<T> {
        private static final long serialVersionUID = 706635022205076709L;
        final MaybeObserver<? super T> downstream;

        a(MaybeObserver<? super T> maybeObserver) {
            this.downstream = maybeObserver;
        }

        @Override // io.reactivex.MaybeObserver, io.reactivex.SingleObserver
        public void onSubscribe(Disposable disposable) {
            DisposableHelper.setOnce(this, disposable);
        }

        @Override // io.reactivex.MaybeObserver, io.reactivex.SingleObserver
        public void onSuccess(T t) {
            this.downstream.onSuccess(t);
        }

        @Override // io.reactivex.MaybeObserver, io.reactivex.SingleObserver
        public void onError(Throwable th) {
            this.downstream.onError(th);
        }

        @Override // io.reactivex.MaybeObserver
        public void onComplete() {
            this.downstream.onComplete();
        }
    }
}
