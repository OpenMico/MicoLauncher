package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.subscribers.DisposableSubscriber;
import io.reactivex.subscribers.SerializedSubscriber;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes4.dex */
public final class FlowableDebounce<T, U> extends a<T, T> {
    final Function<? super T, ? extends Publisher<U>> b;

    public FlowableDebounce(Flowable<T> flowable, Function<? super T, ? extends Publisher<U>> function) {
        super(flowable);
        this.b = function;
    }

    @Override // io.reactivex.Flowable
    protected void subscribeActual(Subscriber<? super T> subscriber) {
        this.source.subscribe((FlowableSubscriber) new a(new SerializedSubscriber(subscriber), this.b));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class a<T, U> extends AtomicLong implements FlowableSubscriber<T>, Subscription {
        private static final long serialVersionUID = 6725975399620862591L;
        final Function<? super T, ? extends Publisher<U>> debounceSelector;
        final AtomicReference<Disposable> debouncer = new AtomicReference<>();
        boolean done;
        final Subscriber<? super T> downstream;
        volatile long index;
        Subscription upstream;

        a(Subscriber<? super T> subscriber, Function<? super T, ? extends Publisher<U>> function) {
            this.downstream = subscriber;
            this.debounceSelector = function;
        }

        @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.upstream, subscription)) {
                this.upstream = subscription;
                this.downstream.onSubscribe(this);
                subscription.request(Long.MAX_VALUE);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (!this.done) {
                long j = this.index + 1;
                this.index = j;
                Disposable disposable = this.debouncer.get();
                if (disposable != null) {
                    disposable.dispose();
                }
                try {
                    Publisher publisher = (Publisher) ObjectHelper.requireNonNull(this.debounceSelector.apply(t), "The publisher supplied is null");
                    C0218a aVar = new C0218a(this, j, t);
                    if (this.debouncer.compareAndSet(disposable, aVar)) {
                        publisher.subscribe(aVar);
                    }
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    cancel();
                    this.downstream.onError(th);
                }
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            DisposableHelper.dispose(this.debouncer);
            this.downstream.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (!this.done) {
                this.done = true;
                Disposable disposable = this.debouncer.get();
                if (!DisposableHelper.isDisposed(disposable)) {
                    C0218a aVar = (C0218a) disposable;
                    if (aVar != null) {
                        aVar.a();
                    }
                    DisposableHelper.dispose(this.debouncer);
                    this.downstream.onComplete();
                }
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (SubscriptionHelper.validate(j)) {
                BackpressureHelper.add(this, j);
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.upstream.cancel();
            DisposableHelper.dispose(this.debouncer);
        }

        void a(long j, T t) {
            if (j != this.index) {
                return;
            }
            if (get() != 0) {
                this.downstream.onNext(t);
                BackpressureHelper.produced(this, 1L);
                return;
            }
            cancel();
            this.downstream.onError(new MissingBackpressureException("Could not deliver value due to lack of requests"));
        }

        /* renamed from: io.reactivex.internal.operators.flowable.FlowableDebounce$a$a  reason: collision with other inner class name */
        /* loaded from: classes4.dex */
        static final class C0218a<T, U> extends DisposableSubscriber<U> {
            final a<T, U> a;
            final long b;
            final T c;
            boolean d;
            final AtomicBoolean e = new AtomicBoolean();

            C0218a(a<T, U> aVar, long j, T t) {
                this.a = aVar;
                this.b = j;
                this.c = t;
            }

            @Override // org.reactivestreams.Subscriber
            public void onNext(U u) {
                if (!this.d) {
                    this.d = true;
                    cancel();
                    a();
                }
            }

            void a() {
                if (this.e.compareAndSet(false, true)) {
                    this.a.a(this.b, this.c);
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
                    a();
                }
            }
        }
    }
}
