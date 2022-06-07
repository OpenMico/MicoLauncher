package io.reactivex.rxjava3.internal.jdk8;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.internal.disposables.EmptyDisposable;
import io.reactivex.rxjava3.internal.fuseable.FuseToFlowable;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collector;
import org.reactivestreams.Subscription;

/* loaded from: classes4.dex */
public final class FlowableCollectWithCollectorSingle<T, A, R> extends Single<R> implements FuseToFlowable<R> {
    final Flowable<T> a;
    final Collector<T, A, R> b;

    public FlowableCollectWithCollectorSingle(Flowable<T> flowable, Collector<T, A, R> collector) {
        this.a = flowable;
        this.b = collector;
    }

    @Override // io.reactivex.rxjava3.internal.fuseable.FuseToFlowable
    public Flowable<R> fuseToFlowable() {
        return new FlowableCollectWithCollector(this.a, this.b);
    }

    @Override // io.reactivex.rxjava3.core.Single
    protected void subscribeActual(@NonNull SingleObserver<? super R> singleObserver) {
        try {
            this.a.subscribe((FlowableSubscriber) new a(singleObserver, this.b.supplier().get(), this.b.accumulator(), this.b.finisher()));
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            EmptyDisposable.error(th, singleObserver);
        }
    }

    /* loaded from: classes4.dex */
    static final class a<T, A, R> implements FlowableSubscriber<T>, Disposable {
        final SingleObserver<? super R> a;
        final BiConsumer<A, T> b;
        final Function<A, R> c;
        Subscription d;
        boolean e;
        A f;

        a(SingleObserver<? super R> singleObserver, A a, BiConsumer<A, T> biConsumer, Function<A, R> function) {
            this.a = singleObserver;
            this.f = a;
            this.b = biConsumer;
            this.c = function;
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(@NonNull Subscription subscription) {
            if (SubscriptionHelper.validate(this.d, subscription)) {
                this.d = subscription;
                this.a.onSubscribe(this);
                subscription.request(Long.MAX_VALUE);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (!this.e) {
                try {
                    this.b.accept(this.f, t);
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    this.d.cancel();
                    onError(th);
                }
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.e) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.e = true;
            this.d = SubscriptionHelper.CANCELLED;
            this.f = null;
            this.a.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (!this.e) {
                this.e = true;
                this.d = SubscriptionHelper.CANCELLED;
                A a = this.f;
                this.f = null;
                try {
                    this.a.onSuccess(Objects.requireNonNull(this.c.apply(a), "The finisher returned a null value"));
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    this.a.onError(th);
                }
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
