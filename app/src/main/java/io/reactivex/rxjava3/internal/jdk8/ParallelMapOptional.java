package io.reactivex.rxjava3.internal.jdk8;

import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.fuseable.ConditionalSubscriber;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.parallel.ParallelFlowable;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.Objects;
import java.util.Optional;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes4.dex */
public final class ParallelMapOptional<T, R> extends ParallelFlowable<R> {
    final ParallelFlowable<T> a;
    final Function<? super T, Optional<? extends R>> b;

    public ParallelMapOptional(ParallelFlowable<T> parallelFlowable, Function<? super T, Optional<? extends R>> function) {
        this.a = parallelFlowable;
        this.b = function;
    }

    @Override // io.reactivex.rxjava3.parallel.ParallelFlowable, autodispose2.ParallelFlowableSubscribeProxy
    public void subscribe(Subscriber<? super R>[] subscriberArr) {
        if (validate(subscriberArr)) {
            int length = subscriberArr.length;
            Subscriber<? super T>[] subscriberArr2 = new Subscriber[length];
            for (int i = 0; i < length; i++) {
                Subscriber<? super R> subscriber = subscriberArr[i];
                if (subscriber instanceof ConditionalSubscriber) {
                    subscriberArr2[i] = new a((ConditionalSubscriber) subscriber, this.b);
                } else {
                    subscriberArr2[i] = new b(subscriber, this.b);
                }
            }
            this.a.subscribe(subscriberArr2);
        }
    }

    @Override // io.reactivex.rxjava3.parallel.ParallelFlowable
    public int parallelism() {
        return this.a.parallelism();
    }

    /* loaded from: classes4.dex */
    static final class b<T, R> implements ConditionalSubscriber<T>, Subscription {
        final Subscriber<? super R> a;
        final Function<? super T, Optional<? extends R>> b;
        Subscription c;
        boolean d;

        b(Subscriber<? super R> subscriber, Function<? super T, Optional<? extends R>> function) {
            this.a = subscriber;
            this.b = function;
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            this.c.request(j);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.c.cancel();
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.c, subscription)) {
                this.c = subscription;
                this.a.onSubscribe(this);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (!tryOnNext(t)) {
                this.c.request(1L);
            }
        }

        @Override // io.reactivex.rxjava3.internal.fuseable.ConditionalSubscriber
        public boolean tryOnNext(T t) {
            if (this.d) {
                return true;
            }
            try {
                Optional optional = (Optional) Objects.requireNonNull(this.b.apply(t), "The mapper returned a null Optional");
                if (!optional.isPresent()) {
                    return false;
                }
                this.a.onNext((Object) optional.get());
                return true;
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                cancel();
                onError(th);
                return true;
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.d) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.d = true;
            this.a.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (!this.d) {
                this.d = true;
                this.a.onComplete();
            }
        }
    }

    /* loaded from: classes4.dex */
    static final class a<T, R> implements ConditionalSubscriber<T>, Subscription {
        final ConditionalSubscriber<? super R> a;
        final Function<? super T, Optional<? extends R>> b;
        Subscription c;
        boolean d;

        a(ConditionalSubscriber<? super R> conditionalSubscriber, Function<? super T, Optional<? extends R>> function) {
            this.a = conditionalSubscriber;
            this.b = function;
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            this.c.request(j);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.c.cancel();
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.c, subscription)) {
                this.c = subscription;
                this.a.onSubscribe(this);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (!tryOnNext(t)) {
                this.c.request(1L);
            }
        }

        @Override // io.reactivex.rxjava3.internal.fuseable.ConditionalSubscriber
        public boolean tryOnNext(T t) {
            if (this.d) {
                return false;
            }
            try {
                Optional optional = (Optional) Objects.requireNonNull(this.b.apply(t), "The mapper returned a null value");
                return optional.isPresent() && this.a.tryOnNext((Object) optional.get());
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                cancel();
                onError(th);
                return false;
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.d) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.d = true;
            this.a.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (!this.d) {
                this.d = true;
                this.a.onComplete();
            }
        }
    }
}
