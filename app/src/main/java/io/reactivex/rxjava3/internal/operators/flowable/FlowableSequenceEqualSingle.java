package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.BiPredicate;
import io.reactivex.rxjava3.internal.fuseable.FuseToFlowable;
import io.reactivex.rxjava3.internal.fuseable.SimpleQueue;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableSequenceEqual;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.AtomicThrowable;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import org.reactivestreams.Publisher;

/* loaded from: classes5.dex */
public final class FlowableSequenceEqualSingle<T> extends Single<Boolean> implements FuseToFlowable<Boolean> {
    final Publisher<? extends T> a;
    final Publisher<? extends T> b;
    final BiPredicate<? super T, ? super T> c;
    final int d;

    public FlowableSequenceEqualSingle(Publisher<? extends T> publisher, Publisher<? extends T> publisher2, BiPredicate<? super T, ? super T> biPredicate, int i) {
        this.a = publisher;
        this.b = publisher2;
        this.c = biPredicate;
        this.d = i;
    }

    @Override // io.reactivex.rxjava3.core.Single
    public void subscribeActual(SingleObserver<? super Boolean> singleObserver) {
        a aVar = new a(singleObserver, this.d, this.c);
        singleObserver.onSubscribe(aVar);
        aVar.a(this.a, this.b);
    }

    @Override // io.reactivex.rxjava3.internal.fuseable.FuseToFlowable
    public Flowable<Boolean> fuseToFlowable() {
        return RxJavaPlugins.onAssembly(new FlowableSequenceEqual(this.a, this.b, this.c, this.d));
    }

    /* loaded from: classes5.dex */
    static final class a<T> extends AtomicInteger implements Disposable, FlowableSequenceEqual.b {
        private static final long serialVersionUID = -6178010334400373240L;
        final BiPredicate<? super T, ? super T> comparer;
        final SingleObserver<? super Boolean> downstream;
        final AtomicThrowable errors = new AtomicThrowable();
        final FlowableSequenceEqual.c<T> first;
        final FlowableSequenceEqual.c<T> second;
        T v1;
        T v2;

        a(SingleObserver<? super Boolean> singleObserver, int i, BiPredicate<? super T, ? super T> biPredicate) {
            this.downstream = singleObserver;
            this.comparer = biPredicate;
            this.first = new FlowableSequenceEqual.c<>(this, i);
            this.second = new FlowableSequenceEqual.c<>(this, i);
        }

        void a(Publisher<? extends T> publisher, Publisher<? extends T> publisher2) {
            publisher.subscribe(this.first);
            publisher2.subscribe(this.second);
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            this.first.b();
            this.second.b();
            this.errors.tryTerminateAndReport();
            if (getAndIncrement() == 0) {
                this.first.c();
                this.second.c();
            }
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.first.get() == SubscriptionHelper.CANCELLED;
        }

        void a() {
            this.first.b();
            this.first.c();
            this.second.b();
            this.second.c();
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableSequenceEqual.b
        public void b() {
            if (getAndIncrement() == 0) {
                int i = 1;
                do {
                    SimpleQueue<T> simpleQueue = this.first.queue;
                    SimpleQueue<T> simpleQueue2 = this.second.queue;
                    if (simpleQueue != null && simpleQueue2 != null) {
                        while (!isDisposed()) {
                            if (this.errors.get() != null) {
                                a();
                                this.errors.tryTerminateConsumer(this.downstream);
                                return;
                            }
                            boolean z = this.first.done;
                            T t = this.v1;
                            if (t == null) {
                                try {
                                    t = simpleQueue.poll();
                                    this.v1 = t;
                                } catch (Throwable th) {
                                    Exceptions.throwIfFatal(th);
                                    a();
                                    this.errors.tryAddThrowableOrReport(th);
                                    this.errors.tryTerminateConsumer(this.downstream);
                                    return;
                                }
                            }
                            boolean z2 = t == null;
                            boolean z3 = this.second.done;
                            T t2 = this.v2;
                            if (t2 == null) {
                                try {
                                    t2 = simpleQueue2.poll();
                                    this.v2 = t2;
                                } catch (Throwable th2) {
                                    Exceptions.throwIfFatal(th2);
                                    a();
                                    this.errors.tryAddThrowableOrReport(th2);
                                    this.errors.tryTerminateConsumer(this.downstream);
                                    return;
                                }
                            }
                            boolean z4 = t2 == null;
                            if (z && z3 && z2 && z4) {
                                this.downstream.onSuccess(true);
                                return;
                            } else if (z && z3 && z2 != z4) {
                                a();
                                this.downstream.onSuccess(false);
                                return;
                            } else if (!z2 && !z4) {
                                try {
                                    if (!this.comparer.test(t, t2)) {
                                        a();
                                        this.downstream.onSuccess(false);
                                        return;
                                    }
                                    this.v1 = null;
                                    this.v2 = null;
                                    this.first.a();
                                    this.second.a();
                                } catch (Throwable th3) {
                                    Exceptions.throwIfFatal(th3);
                                    a();
                                    this.errors.tryAddThrowableOrReport(th3);
                                    this.errors.tryTerminateConsumer(this.downstream);
                                    return;
                                }
                            }
                        }
                        this.first.c();
                        this.second.c();
                        return;
                    } else if (isDisposed()) {
                        this.first.c();
                        this.second.c();
                        return;
                    } else if (this.errors.get() != null) {
                        a();
                        this.errors.tryTerminateConsumer(this.downstream);
                        return;
                    }
                    i = addAndGet(-i);
                } while (i != 0);
            }
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableSequenceEqual.b
        public void a(Throwable th) {
            if (this.errors.tryAddThrowableOrReport(th)) {
                b();
            }
        }
    }
}
