package io.reactivex.rxjava3.internal.operators.mixed;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.core.SingleSource;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.AtomicThrowable;
import io.reactivex.rxjava3.internal.util.BackpressureHelper;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes5.dex */
public final class FlowableSwitchMapSingle<T, R> extends Flowable<R> {
    final Flowable<T> b;
    final Function<? super T, ? extends SingleSource<? extends R>> c;
    final boolean d;

    public FlowableSwitchMapSingle(Flowable<T> flowable, Function<? super T, ? extends SingleSource<? extends R>> function, boolean z) {
        this.b = flowable;
        this.c = function;
        this.d = z;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    protected void subscribeActual(Subscriber<? super R> subscriber) {
        this.b.subscribe((FlowableSubscriber) new a(subscriber, this.c, this.d));
    }

    /* loaded from: classes5.dex */
    static final class a<T, R> extends AtomicInteger implements FlowableSubscriber<T>, Subscription {
        static final C0311a<Object> a = new C0311a<>(null);
        private static final long serialVersionUID = -5402190102429853762L;
        volatile boolean cancelled;
        final boolean delayErrors;
        volatile boolean done;
        final Subscriber<? super R> downstream;
        long emitted;
        final Function<? super T, ? extends SingleSource<? extends R>> mapper;
        Subscription upstream;
        final AtomicThrowable errors = new AtomicThrowable();
        final AtomicLong requested = new AtomicLong();
        final AtomicReference<C0311a<R>> inner = new AtomicReference<>();

        /* JADX INFO: Access modifiers changed from: package-private */
        public a(Subscriber<? super R> subscriber, Function<? super T, ? extends SingleSource<? extends R>> function, boolean z) {
            this.downstream = subscriber;
            this.mapper = function;
            this.delayErrors = z;
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.upstream, subscription)) {
                this.upstream = subscription;
                this.downstream.onSubscribe(this);
                subscription.request(Long.MAX_VALUE);
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            C0311a<R> aVar;
            C0311a<R> aVar2 = this.inner.get();
            if (aVar2 != null) {
                aVar2.a();
            }
            try {
                SingleSource singleSource = (SingleSource) Objects.requireNonNull(this.mapper.apply(t), "The mapper returned a null SingleSource");
                C0311a<R> aVar3 = new C0311a<>(this);
                do {
                    aVar = this.inner.get();
                    if (aVar == a) {
                        return;
                    }
                } while (!this.inner.compareAndSet(aVar, aVar3));
                singleSource.subscribe(aVar3);
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.upstream.cancel();
                this.inner.getAndSet(a);
                onError(th);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.errors.tryAddThrowableOrReport(th)) {
                if (!this.delayErrors) {
                    a();
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

        /* JADX WARN: Multi-variable type inference failed */
        void a() {
            C0311a<Object> aVar = (C0311a) this.inner.getAndSet(a);
            if (aVar != null && aVar != a) {
                aVar.a();
            }
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
            a();
            this.errors.tryTerminateAndReport();
        }

        void a(C0311a<R> aVar, Throwable th) {
            if (!this.inner.compareAndSet(aVar, null)) {
                RxJavaPlugins.onError(th);
            } else if (this.errors.tryAddThrowableOrReport(th)) {
                if (!this.delayErrors) {
                    this.upstream.cancel();
                    a();
                }
                b();
            }
        }

        void b() {
            if (getAndIncrement() == 0) {
                Subscriber<? super R> subscriber = this.downstream;
                AtomicThrowable atomicThrowable = this.errors;
                AtomicReference<C0311a<R>> atomicReference = this.inner;
                AtomicLong atomicLong = this.requested;
                long j = this.emitted;
                int i = 1;
                while (!this.cancelled) {
                    if (atomicThrowable.get() == null || this.delayErrors) {
                        boolean z = this.done;
                        C0311a<R> aVar = atomicReference.get();
                        boolean z2 = aVar == null;
                        if (z && z2) {
                            atomicThrowable.tryTerminateConsumer(subscriber);
                            return;
                        } else if (z2 || aVar.item == null || j == atomicLong.get()) {
                            this.emitted = j;
                            i = addAndGet(-i);
                            if (i == 0) {
                                return;
                            }
                        } else {
                            atomicReference.compareAndSet(aVar, null);
                            subscriber.onNext((R) aVar.item);
                            j++;
                        }
                    } else {
                        atomicThrowable.tryTerminateConsumer(subscriber);
                        return;
                    }
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: io.reactivex.rxjava3.internal.operators.mixed.FlowableSwitchMapSingle$a$a  reason: collision with other inner class name */
        /* loaded from: classes5.dex */
        public static final class C0311a<R> extends AtomicReference<Disposable> implements SingleObserver<R> {
            private static final long serialVersionUID = 8042919737683345351L;
            volatile R item;
            final a<?, R> parent;

            C0311a(a<?, R> aVar) {
                this.parent = aVar;
            }

            @Override // io.reactivex.rxjava3.core.SingleObserver
            public void onSubscribe(Disposable disposable) {
                DisposableHelper.setOnce(this, disposable);
            }

            @Override // io.reactivex.rxjava3.core.SingleObserver
            public void onSuccess(R r) {
                this.item = r;
                this.parent.b();
            }

            @Override // io.reactivex.rxjava3.core.SingleObserver
            public void onError(Throwable th) {
                this.parent.a(this, th);
            }

            void a() {
                DisposableHelper.dispose(this);
            }
        }
    }
}
