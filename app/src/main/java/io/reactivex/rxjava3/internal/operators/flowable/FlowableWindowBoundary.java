package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.exceptions.MissingBackpressureException;
import io.reactivex.rxjava3.internal.queue.MpscLinkedQueue;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.AtomicThrowable;
import io.reactivex.rxjava3.internal.util.BackpressureHelper;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import io.reactivex.rxjava3.processors.UnicastProcessor;
import io.reactivex.rxjava3.subscribers.DisposableSubscriber;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes5.dex */
public final class FlowableWindowBoundary<T, B> extends a<T, Flowable<T>> {
    final Publisher<B> b;
    final int c;

    public FlowableWindowBoundary(Flowable<T> flowable, Publisher<B> publisher, int i) {
        super(flowable);
        this.b = publisher;
        this.c = i;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    protected void subscribeActual(Subscriber<? super Flowable<T>> subscriber) {
        b bVar = new b(subscriber, this.c);
        subscriber.onSubscribe(bVar);
        bVar.a();
        this.b.subscribe(bVar.boundarySubscriber);
        this.source.subscribe((FlowableSubscriber) bVar);
    }

    /* loaded from: classes5.dex */
    static final class b<T, B> extends AtomicInteger implements FlowableSubscriber<T>, Runnable, Subscription {
        static final Object a = new Object();
        private static final long serialVersionUID = 2233020065421370272L;
        final int capacityHint;
        volatile boolean done;
        final Subscriber<? super Flowable<T>> downstream;
        long emitted;
        UnicastProcessor<T> window;
        final a<T, B> boundarySubscriber = new a<>(this);
        final AtomicReference<Subscription> upstream = new AtomicReference<>();
        final AtomicInteger windows = new AtomicInteger(1);
        final MpscLinkedQueue<Object> queue = new MpscLinkedQueue<>();
        final AtomicThrowable errors = new AtomicThrowable();
        final AtomicBoolean stopWindows = new AtomicBoolean();
        final AtomicLong requested = new AtomicLong();

        b(Subscriber<? super Flowable<T>> subscriber, int i) {
            this.downstream = subscriber;
            this.capacityHint = i;
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            SubscriptionHelper.setOnce(this.upstream, subscription, Long.MAX_VALUE);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            this.queue.offer(t);
            c();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.boundarySubscriber.dispose();
            if (this.errors.tryAddThrowableOrReport(th)) {
                this.done = true;
                c();
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.boundarySubscriber.dispose();
            this.done = true;
            c();
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (this.stopWindows.compareAndSet(false, true)) {
                this.boundarySubscriber.dispose();
                if (this.windows.decrementAndGet() == 0) {
                    SubscriptionHelper.cancel(this.upstream);
                }
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            BackpressureHelper.add(this.requested, j);
        }

        @Override // java.lang.Runnable
        public void run() {
            if (this.windows.decrementAndGet() == 0) {
                SubscriptionHelper.cancel(this.upstream);
            }
        }

        void a() {
            this.queue.offer(a);
            c();
        }

        void a(Throwable th) {
            SubscriptionHelper.cancel(this.upstream);
            if (this.errors.tryAddThrowableOrReport(th)) {
                this.done = true;
                c();
            }
        }

        void b() {
            SubscriptionHelper.cancel(this.upstream);
            this.done = true;
            c();
        }

        /* JADX WARN: Multi-variable type inference failed */
        void c() {
            if (getAndIncrement() == 0) {
                Subscriber<? super Flowable<T>> subscriber = this.downstream;
                MpscLinkedQueue<Object> mpscLinkedQueue = this.queue;
                AtomicThrowable atomicThrowable = this.errors;
                long j = this.emitted;
                int i = 1;
                while (this.windows.get() != 0) {
                    UnicastProcessor<T> unicastProcessor = this.window;
                    boolean z = this.done;
                    if (!z || atomicThrowable.get() == null) {
                        Object poll = mpscLinkedQueue.poll();
                        boolean z2 = poll == null;
                        if (z && z2) {
                            Throwable terminate = atomicThrowable.terminate();
                            if (terminate == null) {
                                if (unicastProcessor != 0) {
                                    this.window = null;
                                    unicastProcessor.onComplete();
                                }
                                subscriber.onComplete();
                                return;
                            }
                            if (unicastProcessor != 0) {
                                this.window = null;
                                unicastProcessor.onError(terminate);
                            }
                            subscriber.onError(terminate);
                            return;
                        } else if (z2) {
                            this.emitted = j;
                            i = addAndGet(-i);
                            if (i == 0) {
                                return;
                            }
                        } else if (poll != a) {
                            unicastProcessor.onNext(poll);
                        } else {
                            if (unicastProcessor != 0) {
                                this.window = null;
                                unicastProcessor.onComplete();
                            }
                            if (!this.stopWindows.get()) {
                                UnicastProcessor<T> create = UnicastProcessor.create(this.capacityHint, this);
                                this.window = create;
                                this.windows.getAndIncrement();
                                if (j != this.requested.get()) {
                                    j++;
                                    b bVar = new b(create);
                                    subscriber.onNext(bVar);
                                    if (bVar.a()) {
                                        create.onComplete();
                                    }
                                } else {
                                    SubscriptionHelper.cancel(this.upstream);
                                    this.boundarySubscriber.dispose();
                                    atomicThrowable.tryAddThrowableOrReport(new MissingBackpressureException("Could not deliver a window due to lack of requests"));
                                    this.done = true;
                                }
                            }
                        }
                    } else {
                        mpscLinkedQueue.clear();
                        Throwable terminate2 = atomicThrowable.terminate();
                        if (unicastProcessor != 0) {
                            this.window = null;
                            unicastProcessor.onError(terminate2);
                        }
                        subscriber.onError(terminate2);
                        return;
                    }
                }
                mpscLinkedQueue.clear();
                this.window = null;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public static final class a<T, B> extends DisposableSubscriber<B> {
        final b<T, B> a;
        boolean b;

        a(b<T, B> bVar) {
            this.a = bVar;
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(B b) {
            if (!this.b) {
                this.a.a();
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.b) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.b = true;
            this.a.a(th);
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
