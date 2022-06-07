package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.queue.MpscLinkedQueue;
import io.reactivex.internal.subscribers.QueueDrainSubscriber;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.QueueDrainHelper;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.subscribers.DisposableSubscriber;
import io.reactivex.subscribers.SerializedSubscriber;
import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes4.dex */
public final class FlowableBufferBoundarySupplier<T, U extends Collection<? super T>, B> extends a<T, U> {
    final Callable<? extends Publisher<B>> b;
    final Callable<U> c;

    public FlowableBufferBoundarySupplier(Flowable<T> flowable, Callable<? extends Publisher<B>> callable, Callable<U> callable2) {
        super(flowable);
        this.b = callable;
        this.c = callable2;
    }

    @Override // io.reactivex.Flowable
    protected void subscribeActual(Subscriber<? super U> subscriber) {
        this.source.subscribe((FlowableSubscriber) new b(new SerializedSubscriber(subscriber), this.c, this.b));
    }

    /* loaded from: classes4.dex */
    static final class b<T, U extends Collection<? super T>, B> extends QueueDrainSubscriber<T, U, U> implements FlowableSubscriber<T>, Disposable, Subscription {
        final Callable<U> a;
        final Callable<? extends Publisher<B>> b;
        Subscription c;
        final AtomicReference<Disposable> d = new AtomicReference<>();
        U e;

        b(Subscriber<? super U> subscriber, Callable<U> callable, Callable<? extends Publisher<B>> callable2) {
            super(subscriber, new MpscLinkedQueue());
            this.a = callable;
            this.b = callable2;
        }

        @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.c, subscription)) {
                this.c = subscription;
                Subscriber subscriber = this.downstream;
                try {
                    this.e = (U) ((Collection) ObjectHelper.requireNonNull(this.a.call(), "The buffer supplied is null"));
                    try {
                        Publisher publisher = (Publisher) ObjectHelper.requireNonNull(this.b.call(), "The boundary publisher supplied is null");
                        a aVar = new a(this);
                        this.d.set(aVar);
                        subscriber.onSubscribe(this);
                        if (!this.cancelled) {
                            subscription.request(Long.MAX_VALUE);
                            publisher.subscribe(aVar);
                        }
                    } catch (Throwable th) {
                        Exceptions.throwIfFatal(th);
                        this.cancelled = true;
                        subscription.cancel();
                        EmptySubscription.error(th, subscriber);
                    }
                } catch (Throwable th2) {
                    Exceptions.throwIfFatal(th2);
                    this.cancelled = true;
                    subscription.cancel();
                    EmptySubscription.error(th2, subscriber);
                }
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            synchronized (this) {
                U u = this.e;
                if (u != null) {
                    u.add(t);
                }
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            cancel();
            this.downstream.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            synchronized (this) {
                U u = this.e;
                if (u != null) {
                    this.e = null;
                    this.queue.offer(u);
                    this.done = true;
                    if (enter()) {
                        QueueDrainHelper.drainMaxLoop(this.queue, this.downstream, false, this, this);
                    }
                }
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            requested(j);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (!this.cancelled) {
                this.cancelled = true;
                this.c.cancel();
                a();
                if (enter()) {
                    this.queue.clear();
                }
            }
        }

        void a() {
            DisposableHelper.dispose(this.d);
        }

        void b() {
            try {
                U u = (U) ((Collection) ObjectHelper.requireNonNull(this.a.call(), "The buffer supplied is null"));
                try {
                    Publisher publisher = (Publisher) ObjectHelper.requireNonNull(this.b.call(), "The boundary publisher supplied is null");
                    a aVar = new a(this);
                    if (DisposableHelper.replace(this.d, aVar)) {
                        synchronized (this) {
                            U u2 = this.e;
                            if (u2 != null) {
                                this.e = u;
                                publisher.subscribe(aVar);
                                fastPathEmitMax(u2, false, this);
                            }
                        }
                    }
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    this.cancelled = true;
                    this.c.cancel();
                    this.downstream.onError(th);
                }
            } catch (Throwable th2) {
                Exceptions.throwIfFatal(th2);
                cancel();
                this.downstream.onError(th2);
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            this.c.cancel();
            a();
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.d.get() == DisposableHelper.DISPOSED;
        }

        /* renamed from: a */
        public boolean accept(Subscriber<? super U> subscriber, U u) {
            this.downstream.onNext(u);
            return true;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class a<T, U extends Collection<? super T>, B> extends DisposableSubscriber<B> {
        final b<T, U, B> a;
        boolean b;

        a(b<T, U, B> bVar) {
            this.a = bVar;
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(B b) {
            if (!this.b) {
                this.b = true;
                cancel();
                this.a.b();
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.b) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.b = true;
            this.a.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (!this.b) {
                this.b = true;
                this.a.b();
            }
        }
    }
}
