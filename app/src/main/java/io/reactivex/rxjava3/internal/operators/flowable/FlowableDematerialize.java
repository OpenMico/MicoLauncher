package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.core.Notification;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.Objects;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes5.dex */
public final class FlowableDematerialize<T, R> extends a<T, R> {
    final Function<? super T, ? extends Notification<R>> b;

    public FlowableDematerialize(Flowable<T> flowable, Function<? super T, ? extends Notification<R>> function) {
        super(flowable);
        this.b = function;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    protected void subscribeActual(Subscriber<? super R> subscriber) {
        this.source.subscribe((FlowableSubscriber) new a(subscriber, this.b));
    }

    /* loaded from: classes5.dex */
    static final class a<T, R> implements FlowableSubscriber<T>, Subscription {
        final Subscriber<? super R> a;
        final Function<? super T, ? extends Notification<R>> b;
        boolean c;
        Subscription d;

        a(Subscriber<? super R> subscriber, Function<? super T, ? extends Notification<R>> function) {
            this.a = subscriber;
            this.b = function;
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
            if (!this.c) {
                try {
                    Notification notification = (Notification) Objects.requireNonNull(this.b.apply(t), "The selector returned a null Notification");
                    if (notification.isOnError()) {
                        this.d.cancel();
                        onError(notification.getError());
                    } else if (notification.isOnComplete()) {
                        this.d.cancel();
                        onComplete();
                    } else {
                        this.a.onNext((Object) notification.getValue());
                    }
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    this.d.cancel();
                    onError(th);
                }
            } else if (t instanceof Notification) {
                Notification notification2 = (Notification) t;
                if (notification2.isOnError()) {
                    RxJavaPlugins.onError(notification2.getError());
                }
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.c) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.c = true;
            this.a.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (!this.c) {
                this.c = true;
                this.a.onComplete();
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            this.d.request(j);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.d.cancel();
        }
    }
}
