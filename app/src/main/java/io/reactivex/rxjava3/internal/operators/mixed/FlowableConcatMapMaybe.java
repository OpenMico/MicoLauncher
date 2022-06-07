package io.reactivex.rxjava3.internal.operators.mixed;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.core.MaybeObserver;
import io.reactivex.rxjava3.core.MaybeSource;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.exceptions.MissingBackpressureException;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.internal.fuseable.SimplePlainQueue;
import io.reactivex.rxjava3.internal.queue.SpscArrayQueue;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.AtomicThrowable;
import io.reactivex.rxjava3.internal.util.BackpressureHelper;
import io.reactivex.rxjava3.internal.util.ErrorMode;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes5.dex */
public final class FlowableConcatMapMaybe<T, R> extends Flowable<R> {
    final Flowable<T> b;
    final Function<? super T, ? extends MaybeSource<? extends R>> c;
    final ErrorMode d;
    final int e;

    public FlowableConcatMapMaybe(Flowable<T> flowable, Function<? super T, ? extends MaybeSource<? extends R>> function, ErrorMode errorMode, int i) {
        this.b = flowable;
        this.c = function;
        this.d = errorMode;
        this.e = i;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    protected void subscribeActual(Subscriber<? super R> subscriber) {
        this.b.subscribe((FlowableSubscriber) new a(subscriber, this.c, this.e, this.d));
    }

    /* loaded from: classes5.dex */
    static final class a<T, R> extends AtomicInteger implements FlowableSubscriber<T>, Subscription {
        private static final long serialVersionUID = -9140123220065488293L;
        volatile boolean cancelled;
        int consumed;
        volatile boolean done;
        final Subscriber<? super R> downstream;
        long emitted;
        final ErrorMode errorMode;
        R item;
        final Function<? super T, ? extends MaybeSource<? extends R>> mapper;
        final int prefetch;
        final SimplePlainQueue<T> queue;
        volatile int state;
        Subscription upstream;
        final AtomicLong requested = new AtomicLong();
        final AtomicThrowable errors = new AtomicThrowable();
        final C0307a<R> inner = new C0307a<>(this);

        /* JADX INFO: Access modifiers changed from: package-private */
        public a(Subscriber<? super R> subscriber, Function<? super T, ? extends MaybeSource<? extends R>> function, int i, ErrorMode errorMode) {
            this.downstream = subscriber;
            this.mapper = function;
            this.prefetch = i;
            this.errorMode = errorMode;
            this.queue = new SpscArrayQueue(i);
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.upstream, subscription)) {
                this.upstream = subscription;
                this.downstream.onSubscribe(this);
                subscription.request(this.prefetch);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (!this.queue.offer(t)) {
                this.upstream.cancel();
                onError(new MissingBackpressureException("queue full?!"));
                return;
            }
            b();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.errors.tryAddThrowableOrReport(th)) {
                if (this.errorMode == ErrorMode.IMMEDIATE) {
                    this.inner.a();
                }
                this.done = true;
                b();
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.done = true;
            b();
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            BackpressureHelper.add(this.requested, j);
            b();
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.cancelled = true;
            this.upstream.cancel();
            this.inner.a();
            this.errors.tryTerminateAndReport();
            if (getAndIncrement() == 0) {
                this.queue.clear();
                this.item = null;
            }
        }

        void a(R r) {
            this.item = r;
            this.state = 2;
            b();
        }

        void a() {
            this.state = 0;
            b();
        }

        void a(Throwable th) {
            if (this.errors.tryAddThrowableOrReport(th)) {
                if (this.errorMode != ErrorMode.END) {
                    this.upstream.cancel();
                }
                this.state = 0;
                b();
            }
        }

        void b() {
            if (getAndIncrement() == 0) {
                Subscriber<? super R> subscriber = this.downstream;
                ErrorMode errorMode = this.errorMode;
                SimplePlainQueue<T> simplePlainQueue = this.queue;
                AtomicThrowable atomicThrowable = this.errors;
                AtomicLong atomicLong = this.requested;
                int i = this.prefetch;
                int i2 = i - (i >> 1);
                int i3 = 1;
                while (true) {
                    if (this.cancelled) {
                        simplePlainQueue.clear();
                        this.item = null;
                    } else {
                        int i4 = this.state;
                        if (atomicThrowable.get() == null || !(errorMode == ErrorMode.IMMEDIATE || (errorMode == ErrorMode.BOUNDARY && i4 == 0))) {
                            if (i4 == 0) {
                                boolean z = this.done;
                                T poll = simplePlainQueue.poll();
                                boolean z2 = poll == null;
                                if (z && z2) {
                                    atomicThrowable.tryTerminateConsumer(subscriber);
                                    return;
                                } else if (!z2) {
                                    int i5 = this.consumed + 1;
                                    if (i5 == i2) {
                                        this.consumed = 0;
                                        this.upstream.request(i2);
                                    } else {
                                        this.consumed = i5;
                                    }
                                    try {
                                        MaybeSource maybeSource = (MaybeSource) Objects.requireNonNull(this.mapper.apply(poll), "The mapper returned a null MaybeSource");
                                        this.state = 1;
                                        maybeSource.subscribe(this.inner);
                                    } catch (Throwable th) {
                                        Exceptions.throwIfFatal(th);
                                        this.upstream.cancel();
                                        simplePlainQueue.clear();
                                        atomicThrowable.tryAddThrowableOrReport(th);
                                        atomicThrowable.tryTerminateConsumer(subscriber);
                                        return;
                                    }
                                }
                            } else if (i4 == 2) {
                                long j = this.emitted;
                                if (j != atomicLong.get()) {
                                    this.item = null;
                                    subscriber.onNext((R) this.item);
                                    this.emitted = j + 1;
                                    this.state = 0;
                                }
                            }
                        }
                    }
                    i3 = addAndGet(-i3);
                    if (i3 == 0) {
                        return;
                    }
                }
                simplePlainQueue.clear();
                this.item = null;
                atomicThrowable.tryTerminateConsumer(subscriber);
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: io.reactivex.rxjava3.internal.operators.mixed.FlowableConcatMapMaybe$a$a  reason: collision with other inner class name */
        /* loaded from: classes5.dex */
        public static final class C0307a<R> extends AtomicReference<Disposable> implements MaybeObserver<R> {
            private static final long serialVersionUID = -3051469169682093892L;
            final a<?, R> parent;

            C0307a(a<?, R> aVar) {
                this.parent = aVar;
            }

            @Override // io.reactivex.rxjava3.core.MaybeObserver
            public void onSubscribe(Disposable disposable) {
                DisposableHelper.replace(this, disposable);
            }

            @Override // io.reactivex.rxjava3.core.MaybeObserver
            public void onSuccess(R r) {
                this.parent.a((a<?, R>) r);
            }

            @Override // io.reactivex.rxjava3.core.MaybeObserver
            public void onError(Throwable th) {
                this.parent.a(th);
            }

            @Override // io.reactivex.rxjava3.core.MaybeObserver
            public void onComplete() {
                this.parent.a();
            }

            void a() {
                DisposableHelper.dispose(this);
            }
        }
    }
}
