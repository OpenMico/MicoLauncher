package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Supplier;
import io.reactivex.rxjava3.internal.queue.MpscLinkedQueue;
import io.reactivex.rxjava3.internal.subscribers.QueueDrainSubscriber;
import io.reactivex.rxjava3.internal.subscriptions.EmptySubscription;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.QueueDrainHelper;
import io.reactivex.rxjava3.subscribers.DisposableSubscriber;
import io.reactivex.rxjava3.subscribers.SerializedSubscriber;
import java.util.Collection;
import java.util.Objects;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes5.dex */
public final class FlowableBufferExactBoundary<T, U extends Collection<? super T>, B> extends a<T, U> {
    final Publisher<B> b;
    final Supplier<U> c;

    public FlowableBufferExactBoundary(Flowable<T> flowable, Publisher<B> publisher, Supplier<U> supplier) {
        super(flowable);
        this.b = publisher;
        this.c = supplier;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    protected void subscribeActual(Subscriber<? super U> subscriber) {
        this.source.subscribe((FlowableSubscriber) new b(new SerializedSubscriber(subscriber), this.c, this.b));
    }

    /* loaded from: classes5.dex */
    static final class b<T, U extends Collection<? super T>, B> extends QueueDrainSubscriber<T, U, U> implements FlowableSubscriber<T>, Disposable, Subscription {
        final Supplier<U> a;
        final Publisher<B> b;
        Subscription c;
        Disposable d;
        U e;

        b(Subscriber<? super U> subscriber, Supplier<U> supplier, Publisher<B> publisher) {
            super(subscriber, new MpscLinkedQueue());
            this.a = supplier;
            this.b = publisher;
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.c, subscription)) {
                this.c = subscription;
                try {
                    this.e = (U) ((Collection) Objects.requireNonNull(this.a.get(), "The buffer supplied is null"));
                    a aVar = new a(this);
                    this.d = aVar;
                    this.downstream.onSubscribe(this);
                    if (!this.cancelled) {
                        subscription.request(Long.MAX_VALUE);
                        this.b.subscribe(aVar);
                    }
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    this.cancelled = true;
                    subscription.cancel();
                    EmptySubscription.error(th, this.downstream);
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
                this.d.dispose();
                this.c.cancel();
                if (enter()) {
                    this.queue.clear();
                }
            }
        }

        void a() {
            try {
                U u = (U) ((Collection) Objects.requireNonNull(this.a.get(), "The buffer supplied is null"));
                synchronized (this) {
                    U u2 = this.e;
                    if (u2 != null) {
                        this.e = u;
                        fastPathEmitMax(u2, false, this);
                    }
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                cancel();
                this.downstream.onError(th);
            }
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            cancel();
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.cancelled;
        }

        /* renamed from: a */
        public boolean accept(Subscriber<? super U> subscriber, U u) {
            this.downstream.onNext(u);
            return true;
        }
    }

    /* loaded from: classes5.dex */
    static final class a<T, U extends Collection<? super T>, B> extends DisposableSubscriber<B> {
        final b<T, U, B> a;

        a(b<T, U, B> bVar) {
            this.a = bVar;
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(B b) {
            this.a.a();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.a.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.a.onComplete();
        }
    }
}
