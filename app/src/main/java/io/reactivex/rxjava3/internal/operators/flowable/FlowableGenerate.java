package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.Emitter;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.BiFunction;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Supplier;
import io.reactivex.rxjava3.internal.subscriptions.EmptySubscription;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.BackpressureHelper;
import io.reactivex.rxjava3.internal.util.ExceptionHelper;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicLong;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes5.dex */
public final class FlowableGenerate<T, S> extends Flowable<T> {
    final Supplier<S> b;
    final BiFunction<S, Emitter<T>, S> c;
    final Consumer<? super S> d;

    public FlowableGenerate(Supplier<S> supplier, BiFunction<S, Emitter<T>, S> biFunction, Consumer<? super S> consumer) {
        this.b = supplier;
        this.c = biFunction;
        this.d = consumer;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber<? super T> subscriber) {
        try {
            subscriber.onSubscribe(new a(subscriber, this.c, this.d, this.b.get()));
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            EmptySubscription.error(th, subscriber);
        }
    }

    /* loaded from: classes5.dex */
    static final class a<T, S> extends AtomicLong implements Emitter<T>, Subscription {
        private static final long serialVersionUID = 7565982551505011832L;
        volatile boolean cancelled;
        final Consumer<? super S> disposeState;
        final Subscriber<? super T> downstream;
        final BiFunction<S, ? super Emitter<T>, S> generator;
        boolean hasNext;
        S state;
        boolean terminate;

        a(Subscriber<? super T> subscriber, BiFunction<S, ? super Emitter<T>, S> biFunction, Consumer<? super S> consumer, S s) {
            this.downstream = subscriber;
            this.generator = biFunction;
            this.disposeState = consumer;
            this.state = s;
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (SubscriptionHelper.validate(j) && BackpressureHelper.add(this, j) == 0) {
                S s = this.state;
                BiFunction<S, ? super Emitter<T>, S> biFunction = this.generator;
                long j2 = j;
                long j3 = 0;
                while (true) {
                    if (j3 == j2) {
                        j2 = get();
                        if (j3 == j2) {
                            this.state = s;
                            j2 = addAndGet(-j3);
                            if (j2 != 0) {
                                j3 = 0;
                            } else {
                                return;
                            }
                        } else {
                            continue;
                        }
                    } else if (this.cancelled) {
                        this.state = null;
                        a(s);
                        return;
                    } else {
                        this.hasNext = false;
                        try {
                            s = biFunction.apply(s, this);
                            if (this.terminate) {
                                this.cancelled = true;
                                this.state = null;
                                a(s);
                                return;
                            }
                            j3++;
                        } catch (Throwable th) {
                            Exceptions.throwIfFatal(th);
                            this.cancelled = true;
                            this.state = null;
                            onError(th);
                            a(s);
                            return;
                        }
                    }
                }
            }
        }

        private void a(S s) {
            try {
                this.disposeState.accept(s);
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                RxJavaPlugins.onError(th);
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (!this.cancelled) {
                this.cancelled = true;
                if (BackpressureHelper.add(this, 1L) == 0) {
                    S s = this.state;
                    this.state = null;
                    a(s);
                }
            }
        }

        @Override // io.reactivex.rxjava3.core.Emitter
        public void onNext(T t) {
            if (this.terminate) {
                return;
            }
            if (this.hasNext) {
                onError(new IllegalStateException("onNext already called in this generate turn"));
            } else if (t == null) {
                onError(ExceptionHelper.createNullPointerException("onNext called with a null value."));
            } else {
                this.hasNext = true;
                this.downstream.onNext(t);
            }
        }

        @Override // io.reactivex.rxjava3.core.Emitter
        public void onError(Throwable th) {
            if (this.terminate) {
                RxJavaPlugins.onError(th);
                return;
            }
            if (th == null) {
                th = ExceptionHelper.createNullPointerException("onError called with a null Throwable.");
            }
            this.terminate = true;
            this.downstream.onError(th);
        }

        @Override // io.reactivex.rxjava3.core.Emitter
        public void onComplete() {
            if (!this.terminate) {
                this.terminate = true;
                this.downstream.onComplete();
            }
        }
    }
}
