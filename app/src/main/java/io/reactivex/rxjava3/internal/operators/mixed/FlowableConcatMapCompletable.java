package io.reactivex.rxjava3.internal.operators.mixed;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.CompletableSource;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.exceptions.MissingBackpressureException;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.internal.fuseable.SimplePlainQueue;
import io.reactivex.rxjava3.internal.queue.SpscArrayQueue;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.AtomicThrowable;
import io.reactivex.rxjava3.internal.util.ErrorMode;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscription;

/* loaded from: classes5.dex */
public final class FlowableConcatMapCompletable<T> extends Completable {
    final Flowable<T> a;
    final Function<? super T, ? extends CompletableSource> b;
    final ErrorMode c;
    final int d;

    public FlowableConcatMapCompletable(Flowable<T> flowable, Function<? super T, ? extends CompletableSource> function, ErrorMode errorMode, int i) {
        this.a = flowable;
        this.b = function;
        this.c = errorMode;
        this.d = i;
    }

    @Override // io.reactivex.rxjava3.core.Completable
    protected void subscribeActual(CompletableObserver completableObserver) {
        this.a.subscribe((FlowableSubscriber) new a(completableObserver, this.b, this.c, this.d));
    }

    /* loaded from: classes5.dex */
    static final class a<T> extends AtomicInteger implements FlowableSubscriber<T>, Disposable {
        private static final long serialVersionUID = 3610901111000061034L;
        volatile boolean active;
        int consumed;
        volatile boolean disposed;
        volatile boolean done;
        final CompletableObserver downstream;
        final ErrorMode errorMode;
        final AtomicThrowable errors = new AtomicThrowable();
        final C0306a inner = new C0306a(this);
        final Function<? super T, ? extends CompletableSource> mapper;
        final int prefetch;
        final SimplePlainQueue<T> queue;
        Subscription upstream;

        a(CompletableObserver completableObserver, Function<? super T, ? extends CompletableSource> function, ErrorMode errorMode, int i) {
            this.downstream = completableObserver;
            this.mapper = function;
            this.errorMode = errorMode;
            this.prefetch = i;
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
            if (this.queue.offer(t)) {
                b();
                return;
            }
            this.upstream.cancel();
            onError(new MissingBackpressureException("Queue full?!"));
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (!this.errors.tryAddThrowableOrReport(th)) {
                return;
            }
            if (this.errorMode == ErrorMode.IMMEDIATE) {
                this.inner.a();
                this.errors.tryTerminateConsumer(this.downstream);
                if (getAndIncrement() == 0) {
                    this.queue.clear();
                    return;
                }
                return;
            }
            this.done = true;
            b();
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.done = true;
            b();
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            this.disposed = true;
            this.upstream.cancel();
            this.inner.a();
            this.errors.tryTerminateAndReport();
            if (getAndIncrement() == 0) {
                this.queue.clear();
            }
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.disposed;
        }

        void a(Throwable th) {
            if (!this.errors.tryAddThrowableOrReport(th)) {
                return;
            }
            if (this.errorMode == ErrorMode.IMMEDIATE) {
                this.upstream.cancel();
                this.errors.tryTerminateConsumer(this.downstream);
                if (getAndIncrement() == 0) {
                    this.queue.clear();
                    return;
                }
                return;
            }
            this.active = false;
            b();
        }

        void a() {
            this.active = false;
            b();
        }

        void b() {
            if (getAndIncrement() == 0) {
                while (!this.disposed) {
                    if (!this.active) {
                        if (this.errorMode != ErrorMode.BOUNDARY || this.errors.get() == null) {
                            boolean z = this.done;
                            T poll = this.queue.poll();
                            boolean z2 = poll == null;
                            if (z && z2) {
                                this.errors.tryTerminateConsumer(this.downstream);
                                return;
                            } else if (!z2) {
                                int i = this.prefetch;
                                int i2 = i - (i >> 1);
                                int i3 = this.consumed + 1;
                                if (i3 == i2) {
                                    this.consumed = 0;
                                    this.upstream.request(i2);
                                } else {
                                    this.consumed = i3;
                                }
                                try {
                                    CompletableSource completableSource = (CompletableSource) Objects.requireNonNull(this.mapper.apply(poll), "The mapper returned a null CompletableSource");
                                    this.active = true;
                                    completableSource.subscribe(this.inner);
                                } catch (Throwable th) {
                                    Exceptions.throwIfFatal(th);
                                    this.queue.clear();
                                    this.upstream.cancel();
                                    this.errors.tryAddThrowableOrReport(th);
                                    this.errors.tryTerminateConsumer(this.downstream);
                                    return;
                                }
                            }
                        } else {
                            this.queue.clear();
                            this.errors.tryTerminateConsumer(this.downstream);
                            return;
                        }
                    }
                    if (decrementAndGet() == 0) {
                        return;
                    }
                }
                this.queue.clear();
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: io.reactivex.rxjava3.internal.operators.mixed.FlowableConcatMapCompletable$a$a  reason: collision with other inner class name */
        /* loaded from: classes5.dex */
        public static final class C0306a extends AtomicReference<Disposable> implements CompletableObserver {
            private static final long serialVersionUID = 5638352172918776687L;
            final a<?> parent;

            C0306a(a<?> aVar) {
                this.parent = aVar;
            }

            @Override // io.reactivex.rxjava3.core.CompletableObserver
            public void onSubscribe(Disposable disposable) {
                DisposableHelper.replace(this, disposable);
            }

            @Override // io.reactivex.rxjava3.core.CompletableObserver
            public void onError(Throwable th) {
                this.parent.a(th);
            }

            @Override // io.reactivex.rxjava3.core.CompletableObserver
            public void onComplete() {
                this.parent.a();
            }

            void a() {
                DisposableHelper.dispose(this);
            }
        }
    }
}
