package io.reactivex.internal.operators.mixed;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes4.dex */
public final class MaybeFlatMapPublisher<T, R> extends Flowable<R> {
    final MaybeSource<T> b;
    final Function<? super T, ? extends Publisher<? extends R>> c;

    public MaybeFlatMapPublisher(MaybeSource<T> maybeSource, Function<? super T, ? extends Publisher<? extends R>> function) {
        this.b = maybeSource;
        this.c = function;
    }

    @Override // io.reactivex.Flowable
    protected void subscribeActual(Subscriber<? super R> subscriber) {
        this.b.subscribe(new a(subscriber, this.c));
    }

    /* loaded from: classes4.dex */
    static final class a<T, R> extends AtomicReference<Subscription> implements FlowableSubscriber<R>, MaybeObserver<T>, Subscription {
        private static final long serialVersionUID = -8948264376121066672L;
        final Subscriber<? super R> downstream;
        final Function<? super T, ? extends Publisher<? extends R>> mapper;
        final AtomicLong requested = new AtomicLong();
        Disposable upstream;

        a(Subscriber<? super R> subscriber, Function<? super T, ? extends Publisher<? extends R>> function) {
            this.downstream = subscriber;
            this.mapper = function;
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(R r) {
            this.downstream.onNext(r);
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.downstream.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.downstream.onComplete();
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            SubscriptionHelper.deferredRequest(this, this.requested, j);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.upstream.dispose();
            SubscriptionHelper.cancel(this);
        }

        @Override // io.reactivex.MaybeObserver, io.reactivex.SingleObserver
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.upstream, disposable)) {
                this.upstream = disposable;
                this.downstream.onSubscribe(this);
            }
        }

        @Override // io.reactivex.MaybeObserver, io.reactivex.SingleObserver
        public void onSuccess(T t) {
            try {
                ((Publisher) ObjectHelper.requireNonNull(this.mapper.apply(t), "The mapper returned a null Publisher")).subscribe(this);
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.downstream.onError(th);
            }
        }

        @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            SubscriptionHelper.deferredSetOnce(this, this.requested, subscription);
        }
    }
}
