package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.queue.MpscLinkedQueue;
import io.reactivex.internal.subscribers.QueueDrainSubscriber;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.QueueDrainHelper;
import io.reactivex.subscribers.DisposableSubscriber;
import io.reactivex.subscribers.SerializedSubscriber;
import java.util.Collection;
import java.util.concurrent.Callable;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes4.dex */
public final class FlowableBufferExactBoundary<T, U extends Collection<? super T>, B> extends a<T, U> {
    final Publisher<B> b;
    final Callable<U> c;

    public FlowableBufferExactBoundary(Flowable<T> flowable, Publisher<B> publisher, Callable<U> callable) {
        super(flowable);
        this.b = publisher;
        this.c = callable;
    }

    @Override // io.reactivex.Flowable
    protected void subscribeActual(Subscriber<? super U> subscriber) {
        this.source.subscribe((FlowableSubscriber) new b(new SerializedSubscriber(subscriber), this.c, this.b));
    }

    /* loaded from: classes4.dex */
    static final class b<T, U extends Collection<? super T>, B> extends QueueDrainSubscriber<T, U, U> implements FlowableSubscriber<T>, Disposable, Subscription {
        final Callable<U> a;
        final Publisher<B> b;
        Subscription c;
        Disposable d;
        U e;

        b(Subscriber<? super U> subscriber, Callable<U> callable, Publisher<B> publisher) {
            super(subscriber, new MpscLinkedQueue());
            this.a = callable;
            this.b = publisher;
        }

        @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.c, subscription)) {
                this.c = subscription;
                try {
                    this.e = (U) ((Collection) ObjectHelper.requireNonNull(this.a.call(), "The buffer supplied is null"));
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
                U u = (U) ((Collection) ObjectHelper.requireNonNull(this.a.call(), "The buffer supplied is null"));
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

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            cancel();
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.cancelled;
        }

        /* renamed from: a */
        public boolean accept(Subscriber<? super U> subscriber, U u) {
            this.downstream.onNext(u);
            return true;
        }
    }

    /* loaded from: classes4.dex */
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
