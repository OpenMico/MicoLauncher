package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.internal.subscriptions.ScalarSubscription;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.Callable;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

/* loaded from: classes4.dex */
public final class FlowableScalarXMap {
    private FlowableScalarXMap() {
        throw new IllegalStateException("No instances!");
    }

    public static <T, R> boolean tryScalarXMapSubscribe(Publisher<T> publisher, Subscriber<? super R> subscriber, Function<? super T, ? extends Publisher<? extends R>> function) {
        if (!(publisher instanceof Callable)) {
            return false;
        }
        try {
            Object obj = (Object) ((Callable) publisher).call();
            if (obj == 0) {
                EmptySubscription.complete(subscriber);
                return true;
            }
            try {
                Publisher publisher2 = (Publisher) ObjectHelper.requireNonNull(function.apply(obj), "The mapper returned a null Publisher");
                if (publisher2 instanceof Callable) {
                    try {
                        Object call = ((Callable) publisher2).call();
                        if (call == null) {
                            EmptySubscription.complete(subscriber);
                            return true;
                        }
                        subscriber.onSubscribe(new ScalarSubscription(subscriber, call));
                    } catch (Throwable th) {
                        Exceptions.throwIfFatal(th);
                        EmptySubscription.error(th, subscriber);
                        return true;
                    }
                } else {
                    publisher2.subscribe(subscriber);
                }
                return true;
            } catch (Throwable th2) {
                Exceptions.throwIfFatal(th2);
                EmptySubscription.error(th2, subscriber);
                return true;
            }
        } catch (Throwable th3) {
            Exceptions.throwIfFatal(th3);
            EmptySubscription.error(th3, subscriber);
            return true;
        }
    }

    public static <T, U> Flowable<U> scalarXMap(T t, Function<? super T, ? extends Publisher<? extends U>> function) {
        return RxJavaPlugins.onAssembly(new a(t, function));
    }

    /* loaded from: classes4.dex */
    public static final class a<T, R> extends Flowable<R> {
        final T b;
        final Function<? super T, ? extends Publisher<? extends R>> c;

        a(T t, Function<? super T, ? extends Publisher<? extends R>> function) {
            this.b = t;
            this.c = function;
        }

        @Override // io.reactivex.Flowable
        public void subscribeActual(Subscriber<? super R> subscriber) {
            try {
                Publisher publisher = (Publisher) ObjectHelper.requireNonNull(this.c.apply((T) this.b), "The mapper returned a null Publisher");
                if (publisher instanceof Callable) {
                    try {
                        Object call = ((Callable) publisher).call();
                        if (call == null) {
                            EmptySubscription.complete(subscriber);
                        } else {
                            subscriber.onSubscribe(new ScalarSubscription(subscriber, call));
                        }
                    } catch (Throwable th) {
                        Exceptions.throwIfFatal(th);
                        EmptySubscription.error(th, subscriber);
                    }
                } else {
                    publisher.subscribe(subscriber);
                }
            } catch (Throwable th2) {
                EmptySubscription.error(th2, subscriber);
            }
        }
    }
}
