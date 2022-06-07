package io.reactivex.rxjava3.internal.operators.parallel;

import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.exceptions.CompositeException;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.LongConsumer;
import io.reactivex.rxjava3.internal.subscriptions.EmptySubscription;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.parallel.ParallelFlowable;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.Objects;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes5.dex */
public final class ParallelPeek<T> extends ParallelFlowable<T> {
    final ParallelFlowable<T> a;
    final Consumer<? super T> b;
    final Consumer<? super T> c;
    final Consumer<? super Throwable> d;
    final Action e;
    final Action f;
    final Consumer<? super Subscription> g;
    final LongConsumer h;
    final Action i;

    public ParallelPeek(ParallelFlowable<T> parallelFlowable, Consumer<? super T> consumer, Consumer<? super T> consumer2, Consumer<? super Throwable> consumer3, Action action, Action action2, Consumer<? super Subscription> consumer4, LongConsumer longConsumer, Action action3) {
        this.a = parallelFlowable;
        this.b = (Consumer) Objects.requireNonNull(consumer, "onNext is null");
        this.c = (Consumer) Objects.requireNonNull(consumer2, "onAfterNext is null");
        this.d = (Consumer) Objects.requireNonNull(consumer3, "onError is null");
        this.e = (Action) Objects.requireNonNull(action, "onComplete is null");
        this.f = (Action) Objects.requireNonNull(action2, "onAfterTerminated is null");
        this.g = (Consumer) Objects.requireNonNull(consumer4, "onSubscribe is null");
        this.h = (LongConsumer) Objects.requireNonNull(longConsumer, "onRequest is null");
        this.i = (Action) Objects.requireNonNull(action3, "onCancel is null");
    }

    @Override // io.reactivex.rxjava3.parallel.ParallelFlowable, autodispose2.ParallelFlowableSubscribeProxy
    public void subscribe(Subscriber<? super T>[] subscriberArr) {
        if (validate(subscriberArr)) {
            int length = subscriberArr.length;
            Subscriber<? super T>[] subscriberArr2 = new Subscriber[length];
            for (int i = 0; i < length; i++) {
                subscriberArr2[i] = new a(subscriberArr[i], this);
            }
            this.a.subscribe(subscriberArr2);
        }
    }

    @Override // io.reactivex.rxjava3.parallel.ParallelFlowable
    public int parallelism() {
        return this.a.parallelism();
    }

    /* loaded from: classes5.dex */
    static final class a<T> implements FlowableSubscriber<T>, Subscription {
        final Subscriber<? super T> a;
        final ParallelPeek<T> b;
        Subscription c;
        boolean d;

        a(Subscriber<? super T> subscriber, ParallelPeek<T> parallelPeek) {
            this.a = subscriber;
            this.b = parallelPeek;
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            try {
                this.b.h.accept(j);
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                RxJavaPlugins.onError(th);
            }
            this.c.request(j);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            try {
                this.b.i.run();
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                RxJavaPlugins.onError(th);
            }
            this.c.cancel();
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.c, subscription)) {
                this.c = subscription;
                try {
                    this.b.g.accept(subscription);
                    this.a.onSubscribe(this);
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    subscription.cancel();
                    this.a.onSubscribe(EmptySubscription.INSTANCE);
                    onError(th);
                }
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (!this.d) {
                try {
                    this.b.b.accept(t);
                    this.a.onNext(t);
                    try {
                        this.b.c.accept(t);
                    } catch (Throwable th) {
                        Exceptions.throwIfFatal(th);
                        onError(th);
                    }
                } catch (Throwable th2) {
                    Exceptions.throwIfFatal(th2);
                    onError(th2);
                }
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.d) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.d = true;
            try {
                this.b.d.accept(th);
            } catch (Throwable th2) {
                Exceptions.throwIfFatal(th2);
                th = new CompositeException(th, th2);
            }
            this.a.onError(th);
            try {
                this.b.f.run();
            } catch (Throwable th3) {
                Exceptions.throwIfFatal(th3);
                RxJavaPlugins.onError(th3);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (!this.d) {
                this.d = true;
                try {
                    this.b.e.run();
                    this.a.onComplete();
                    try {
                        this.b.f.run();
                    } catch (Throwable th) {
                        Exceptions.throwIfFatal(th);
                        RxJavaPlugins.onError(th);
                    }
                } catch (Throwable th2) {
                    Exceptions.throwIfFatal(th2);
                    this.a.onError(th2);
                }
            }
        }
    }
}
