package io.reactivex.internal.operators.parallel;

import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Predicate;
import io.reactivex.internal.fuseable.ConditionalSubscriber;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.parallel.ParallelFlowable;
import io.reactivex.plugins.RxJavaPlugins;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes4.dex */
public final class ParallelFilter<T> extends ParallelFlowable<T> {
    final ParallelFlowable<T> a;
    final Predicate<? super T> b;

    public ParallelFilter(ParallelFlowable<T> parallelFlowable, Predicate<? super T> predicate) {
        this.a = parallelFlowable;
        this.b = predicate;
    }

    @Override // io.reactivex.parallel.ParallelFlowable
    public void subscribe(Subscriber<? super T>[] subscriberArr) {
        if (validate(subscriberArr)) {
            int length = subscriberArr.length;
            Subscriber<? super T>[] subscriberArr2 = new Subscriber[length];
            for (int i = 0; i < length; i++) {
                Subscriber<? super T> subscriber = subscriberArr[i];
                if (subscriber instanceof ConditionalSubscriber) {
                    subscriberArr2[i] = new b((ConditionalSubscriber) subscriber, this.b);
                } else {
                    subscriberArr2[i] = new c(subscriber, this.b);
                }
            }
            this.a.subscribe(subscriberArr2);
        }
    }

    @Override // io.reactivex.parallel.ParallelFlowable
    public int parallelism() {
        return this.a.parallelism();
    }

    /* loaded from: classes4.dex */
    static abstract class a<T> implements ConditionalSubscriber<T>, Subscription {
        final Predicate<? super T> a;
        Subscription b;
        boolean c;

        a(Predicate<? super T> predicate) {
            this.a = predicate;
        }

        @Override // org.reactivestreams.Subscription
        public final void request(long j) {
            this.b.request(j);
        }

        @Override // org.reactivestreams.Subscription
        public final void cancel() {
            this.b.cancel();
        }

        @Override // org.reactivestreams.Subscriber
        public final void onNext(T t) {
            if (!tryOnNext(t) && !this.c) {
                this.b.request(1L);
            }
        }
    }

    /* loaded from: classes4.dex */
    static final class c<T> extends a<T> {
        final Subscriber<? super T> d;

        c(Subscriber<? super T> subscriber, Predicate<? super T> predicate) {
            super(predicate);
            this.d = subscriber;
        }

        @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.b, subscription)) {
                this.b = subscription;
                this.d.onSubscribe(this);
            }
        }

        @Override // io.reactivex.internal.fuseable.ConditionalSubscriber
        public boolean tryOnNext(T t) {
            if (!this.c) {
                try {
                    if (this.a.test(t)) {
                        this.d.onNext(t);
                        return true;
                    }
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    cancel();
                    onError(th);
                    return false;
                }
            }
            return false;
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.c) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.c = true;
            this.d.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (!this.c) {
                this.c = true;
                this.d.onComplete();
            }
        }
    }

    /* loaded from: classes4.dex */
    static final class b<T> extends a<T> {
        final ConditionalSubscriber<? super T> d;

        b(ConditionalSubscriber<? super T> conditionalSubscriber, Predicate<? super T> predicate) {
            super(predicate);
            this.d = conditionalSubscriber;
        }

        @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.b, subscription)) {
                this.b = subscription;
                this.d.onSubscribe(this);
            }
        }

        @Override // io.reactivex.internal.fuseable.ConditionalSubscriber
        public boolean tryOnNext(T t) {
            if (!this.c) {
                try {
                    if (this.a.test(t)) {
                        return this.d.tryOnNext(t);
                    }
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    cancel();
                    onError(th);
                    return false;
                }
            }
            return false;
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.c) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.c = true;
            this.d.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (!this.c) {
                this.c = true;
                this.d.onComplete();
            }
        }
    }
}
