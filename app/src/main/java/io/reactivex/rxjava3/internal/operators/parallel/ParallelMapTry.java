package io.reactivex.rxjava3.internal.operators.parallel;

import io.reactivex.rxjava3.exceptions.CompositeException;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.BiFunction;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.fuseable.ConditionalSubscriber;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.parallel.ParallelFailureHandling;
import io.reactivex.rxjava3.parallel.ParallelFlowable;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.Objects;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes5.dex */
public final class ParallelMapTry<T, R> extends ParallelFlowable<R> {
    final ParallelFlowable<T> a;
    final Function<? super T, ? extends R> b;
    final BiFunction<? super Long, ? super Throwable, ParallelFailureHandling> c;

    public ParallelMapTry(ParallelFlowable<T> parallelFlowable, Function<? super T, ? extends R> function, BiFunction<? super Long, ? super Throwable, ParallelFailureHandling> biFunction) {
        this.a = parallelFlowable;
        this.b = function;
        this.c = biFunction;
    }

    @Override // io.reactivex.rxjava3.parallel.ParallelFlowable, autodispose2.ParallelFlowableSubscribeProxy
    public void subscribe(Subscriber<? super R>[] subscriberArr) {
        if (validate(subscriberArr)) {
            int length = subscriberArr.length;
            Subscriber<? super T>[] subscriberArr2 = new Subscriber[length];
            for (int i = 0; i < length; i++) {
                Subscriber<? super R> subscriber = subscriberArr[i];
                if (subscriber instanceof ConditionalSubscriber) {
                    subscriberArr2[i] = new a((ConditionalSubscriber) subscriber, this.b, this.c);
                } else {
                    subscriberArr2[i] = new b(subscriber, this.b, this.c);
                }
            }
            this.a.subscribe(subscriberArr2);
        }
    }

    @Override // io.reactivex.rxjava3.parallel.ParallelFlowable
    public int parallelism() {
        return this.a.parallelism();
    }

    /* loaded from: classes5.dex */
    static final class b<T, R> implements ConditionalSubscriber<T>, Subscription {
        final Subscriber<? super R> a;
        final Function<? super T, ? extends R> b;
        final BiFunction<? super Long, ? super Throwable, ParallelFailureHandling> c;
        Subscription d;
        boolean e;

        b(Subscriber<? super R> subscriber, Function<? super T, ? extends R> function, BiFunction<? super Long, ? super Throwable, ParallelFailureHandling> biFunction) {
            this.a = subscriber;
            this.b = function;
            this.c = biFunction;
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            this.d.request(j);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.d.cancel();
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.d, subscription)) {
                this.d = subscription;
                this.a.onSubscribe(this);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (!tryOnNext(t) && !this.e) {
                this.d.request(1L);
            }
        }

        @Override // io.reactivex.rxjava3.internal.fuseable.ConditionalSubscriber
        public boolean tryOnNext(T t) {
            if (this.e) {
                return false;
            }
            long j = 0;
            while (true) {
                try {
                    this.a.onNext(Objects.requireNonNull(this.b.apply(t), "The mapper returned a null value"));
                    return true;
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    try {
                        j++;
                        switch ((ParallelFailureHandling) Objects.requireNonNull(this.c.apply(Long.valueOf(j), th), "The errorHandler returned a null ParallelFailureHandling")) {
                            case RETRY:
                                break;
                            case SKIP:
                                return false;
                            case STOP:
                                cancel();
                                onComplete();
                                return false;
                            default:
                                cancel();
                                onError(th);
                                return false;
                        }
                    } catch (Throwable th2) {
                        Exceptions.throwIfFatal(th2);
                        cancel();
                        onError(new CompositeException(th, th2));
                        return false;
                    }
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
            this.a.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (!this.e) {
                this.e = true;
                this.a.onComplete();
            }
        }
    }

    /* loaded from: classes5.dex */
    static final class a<T, R> implements ConditionalSubscriber<T>, Subscription {
        final ConditionalSubscriber<? super R> a;
        final Function<? super T, ? extends R> b;
        final BiFunction<? super Long, ? super Throwable, ParallelFailureHandling> c;
        Subscription d;
        boolean e;

        a(ConditionalSubscriber<? super R> conditionalSubscriber, Function<? super T, ? extends R> function, BiFunction<? super Long, ? super Throwable, ParallelFailureHandling> biFunction) {
            this.a = conditionalSubscriber;
            this.b = function;
            this.c = biFunction;
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            this.d.request(j);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.d.cancel();
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.d, subscription)) {
                this.d = subscription;
                this.a.onSubscribe(this);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (!tryOnNext(t) && !this.e) {
                this.d.request(1L);
            }
        }

        @Override // io.reactivex.rxjava3.internal.fuseable.ConditionalSubscriber
        public boolean tryOnNext(T t) {
            if (this.e) {
                return false;
            }
            long j = 0;
            while (true) {
                try {
                    return this.a.tryOnNext(Objects.requireNonNull(this.b.apply(t), "The mapper returned a null value"));
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    try {
                        j++;
                        switch ((ParallelFailureHandling) Objects.requireNonNull(this.c.apply(Long.valueOf(j), th), "The errorHandler returned a null ParallelFailureHandling")) {
                            case RETRY:
                                break;
                            case SKIP:
                                return false;
                            case STOP:
                                cancel();
                                onComplete();
                                return false;
                            default:
                                cancel();
                                onError(th);
                                return false;
                        }
                    } catch (Throwable th2) {
                        Exceptions.throwIfFatal(th2);
                        cancel();
                        onError(new CompositeException(th, th2));
                        return false;
                    }
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
            this.a.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (!this.e) {
                this.e = true;
                this.a.onComplete();
            }
        }
    }
}
