package io.reactivex.rxjava3.subscribers;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.exceptions.CompositeException;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.internal.subscriptions.EmptySubscription;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.ExceptionHelper;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes5.dex */
public final class SafeSubscriber<T> implements FlowableSubscriber<T>, Subscription {
    final Subscriber<? super T> a;
    Subscription b;
    boolean c;

    public SafeSubscriber(@NonNull Subscriber<? super T> subscriber) {
        this.a = subscriber;
    }

    @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
    public void onSubscribe(@NonNull Subscription subscription) {
        if (SubscriptionHelper.validate(this.b, subscription)) {
            this.b = subscription;
            try {
                this.a.onSubscribe(this);
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.c = true;
                try {
                    subscription.cancel();
                    RxJavaPlugins.onError(th);
                } catch (Throwable th2) {
                    Exceptions.throwIfFatal(th2);
                    RxJavaPlugins.onError(new CompositeException(th, th2));
                }
            }
        }
    }

    @Override // org.reactivestreams.Subscriber
    public void onNext(@NonNull T t) {
        if (!this.c) {
            if (this.b == null) {
                a();
            } else if (t == null) {
                NullPointerException createNullPointerException = ExceptionHelper.createNullPointerException("onNext called with a null Throwable.");
                try {
                    this.b.cancel();
                    onError(createNullPointerException);
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    onError(new CompositeException(createNullPointerException, th));
                }
            } else {
                try {
                    this.a.onNext(t);
                } catch (Throwable th2) {
                    Exceptions.throwIfFatal(th2);
                    try {
                        this.b.cancel();
                        onError(th2);
                    } catch (Throwable th3) {
                        Exceptions.throwIfFatal(th3);
                        onError(new CompositeException(th2, th3));
                    }
                }
            }
        }
    }

    void a() {
        this.c = true;
        NullPointerException nullPointerException = new NullPointerException("Subscription not set!");
        try {
            this.a.onSubscribe(EmptySubscription.INSTANCE);
            try {
                this.a.onError(nullPointerException);
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                RxJavaPlugins.onError(new CompositeException(nullPointerException, th));
            }
        } catch (Throwable th2) {
            Exceptions.throwIfFatal(th2);
            RxJavaPlugins.onError(new CompositeException(nullPointerException, th2));
        }
    }

    @Override // org.reactivestreams.Subscriber
    public void onError(@NonNull Throwable th) {
        if (this.c) {
            RxJavaPlugins.onError(th);
            return;
        }
        this.c = true;
        if (this.b == null) {
            NullPointerException nullPointerException = new NullPointerException("Subscription not set!");
            try {
                this.a.onSubscribe(EmptySubscription.INSTANCE);
                try {
                    this.a.onError(new CompositeException(th, nullPointerException));
                } catch (Throwable th2) {
                    Exceptions.throwIfFatal(th2);
                    RxJavaPlugins.onError(new CompositeException(th, nullPointerException, th2));
                }
            } catch (Throwable th3) {
                Exceptions.throwIfFatal(th3);
                RxJavaPlugins.onError(new CompositeException(th, nullPointerException, th3));
            }
        } else {
            if (th == null) {
                th = ExceptionHelper.createNullPointerException("onError called with a null Throwable.");
            }
            try {
                this.a.onError(th);
            } catch (Throwable th4) {
                Exceptions.throwIfFatal(th4);
                RxJavaPlugins.onError(new CompositeException(th, th4));
            }
        }
    }

    @Override // org.reactivestreams.Subscriber
    public void onComplete() {
        if (!this.c) {
            this.c = true;
            if (this.b == null) {
                b();
                return;
            }
            try {
                this.a.onComplete();
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                RxJavaPlugins.onError(th);
            }
        }
    }

    void b() {
        NullPointerException nullPointerException = new NullPointerException("Subscription not set!");
        try {
            this.a.onSubscribe(EmptySubscription.INSTANCE);
            try {
                this.a.onError(nullPointerException);
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                RxJavaPlugins.onError(new CompositeException(nullPointerException, th));
            }
        } catch (Throwable th2) {
            Exceptions.throwIfFatal(th2);
            RxJavaPlugins.onError(new CompositeException(nullPointerException, th2));
        }
    }

    @Override // org.reactivestreams.Subscription
    public void request(long j) {
        try {
            this.b.request(j);
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            try {
                this.b.cancel();
                RxJavaPlugins.onError(th);
            } catch (Throwable th2) {
                Exceptions.throwIfFatal(th2);
                RxJavaPlugins.onError(new CompositeException(th, th2));
            }
        }
    }

    @Override // org.reactivestreams.Subscription
    public void cancel() {
        try {
            this.b.cancel();
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            RxJavaPlugins.onError(th);
        }
    }
}
