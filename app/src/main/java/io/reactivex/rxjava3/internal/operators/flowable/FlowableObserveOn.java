package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.annotations.Nullable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.exceptions.MissingBackpressureException;
import io.reactivex.rxjava3.internal.fuseable.ConditionalSubscriber;
import io.reactivex.rxjava3.internal.fuseable.QueueSubscription;
import io.reactivex.rxjava3.internal.fuseable.SimpleQueue;
import io.reactivex.rxjava3.internal.queue.SpscArrayQueue;
import io.reactivex.rxjava3.internal.subscriptions.BasicIntQueueSubscription;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.BackpressureHelper;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicLong;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes5.dex */
public final class FlowableObserveOn<T> extends a<T, T> {
    final Scheduler b;
    final boolean c;
    final int d;

    public FlowableObserveOn(Flowable<T> flowable, Scheduler scheduler, boolean z, int i) {
        super(flowable);
        this.b = scheduler;
        this.c = z;
        this.d = i;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber<? super T> subscriber) {
        Scheduler.Worker createWorker = this.b.createWorker();
        if (subscriber instanceof ConditionalSubscriber) {
            this.source.subscribe((FlowableSubscriber) new b((ConditionalSubscriber) subscriber, createWorker, this.c, this.d));
        } else {
            this.source.subscribe((FlowableSubscriber) new c(subscriber, createWorker, this.c, this.d));
        }
    }

    /* loaded from: classes5.dex */
    static abstract class a<T> extends BasicIntQueueSubscription<T> implements FlowableSubscriber<T>, Runnable {
        private static final long serialVersionUID = -8241002408341274697L;
        volatile boolean cancelled;
        final boolean delayError;
        volatile boolean done;
        Throwable error;
        final int limit;
        boolean outputFused;
        final int prefetch;
        long produced;
        SimpleQueue<T> queue;
        final AtomicLong requested = new AtomicLong();
        int sourceMode;
        Subscription upstream;
        final Scheduler.Worker worker;

        abstract void b();

        abstract void c();

        abstract void d();

        a(Scheduler.Worker worker, boolean z, int i) {
            this.worker = worker;
            this.delayError = z;
            this.prefetch = i;
            this.limit = i - (i >> 2);
        }

        @Override // org.reactivestreams.Subscriber
        public final void onNext(T t) {
            if (!this.done) {
                if (this.sourceMode == 2) {
                    a();
                    return;
                }
                if (!this.queue.offer(t)) {
                    this.upstream.cancel();
                    this.error = new MissingBackpressureException("Queue is full?!");
                    this.done = true;
                }
                a();
            }
        }

        @Override // org.reactivestreams.Subscriber
        public final void onError(Throwable th) {
            if (this.done) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.error = th;
            this.done = true;
            a();
        }

        @Override // org.reactivestreams.Subscriber
        public final void onComplete() {
            if (!this.done) {
                this.done = true;
                a();
            }
        }

        @Override // org.reactivestreams.Subscription
        public final void request(long j) {
            if (SubscriptionHelper.validate(j)) {
                BackpressureHelper.add(this.requested, j);
                a();
            }
        }

        @Override // org.reactivestreams.Subscription
        public final void cancel() {
            if (!this.cancelled) {
                this.cancelled = true;
                this.upstream.cancel();
                this.worker.dispose();
                if (!this.outputFused && getAndIncrement() == 0) {
                    this.queue.clear();
                }
            }
        }

        final void a() {
            if (getAndIncrement() == 0) {
                this.worker.schedule(this);
            }
        }

        @Override // java.lang.Runnable
        public final void run() {
            if (this.outputFused) {
                b();
            } else if (this.sourceMode == 1) {
                c();
            } else {
                d();
            }
        }

        final boolean a(boolean z, boolean z2, Subscriber<?> subscriber) {
            if (this.cancelled) {
                clear();
                return true;
            } else if (!z) {
                return false;
            } else {
                if (!this.delayError) {
                    Throwable th = this.error;
                    if (th != null) {
                        this.cancelled = true;
                        clear();
                        subscriber.onError(th);
                        this.worker.dispose();
                        return true;
                    } else if (!z2) {
                        return false;
                    } else {
                        this.cancelled = true;
                        subscriber.onComplete();
                        this.worker.dispose();
                        return true;
                    }
                } else if (!z2) {
                    return false;
                } else {
                    this.cancelled = true;
                    Throwable th2 = this.error;
                    if (th2 != null) {
                        subscriber.onError(th2);
                    } else {
                        subscriber.onComplete();
                    }
                    this.worker.dispose();
                    return true;
                }
            }
        }

        @Override // io.reactivex.rxjava3.internal.fuseable.QueueFuseable
        public final int requestFusion(int i) {
            if ((i & 2) == 0) {
                return 0;
            }
            this.outputFused = true;
            return 2;
        }

        @Override // io.reactivex.rxjava3.internal.fuseable.SimpleQueue
        public final void clear() {
            this.queue.clear();
        }

        @Override // io.reactivex.rxjava3.internal.fuseable.SimpleQueue
        public final boolean isEmpty() {
            return this.queue.isEmpty();
        }
    }

    /* loaded from: classes5.dex */
    static final class c<T> extends a<T> implements FlowableSubscriber<T> {
        private static final long serialVersionUID = -4547113800637756442L;
        final Subscriber<? super T> downstream;

        c(Subscriber<? super T> subscriber, Scheduler.Worker worker, boolean z, int i) {
            super(worker, z, i);
            this.downstream = subscriber;
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.upstream, subscription)) {
                this.upstream = subscription;
                if (subscription instanceof QueueSubscription) {
                    QueueSubscription queueSubscription = (QueueSubscription) subscription;
                    int requestFusion = queueSubscription.requestFusion(7);
                    if (requestFusion == 1) {
                        this.sourceMode = 1;
                        this.queue = queueSubscription;
                        this.done = true;
                        this.downstream.onSubscribe(this);
                        return;
                    } else if (requestFusion == 2) {
                        this.sourceMode = 2;
                        this.queue = queueSubscription;
                        this.downstream.onSubscribe(this);
                        subscription.request(this.prefetch);
                        return;
                    }
                }
                this.queue = new SpscArrayQueue(this.prefetch);
                this.downstream.onSubscribe(this);
                subscription.request(this.prefetch);
            }
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableObserveOn.a
        void c() {
            Subscriber<? super T> subscriber = this.downstream;
            SimpleQueue simpleQueue = this.queue;
            long j = this.produced;
            int i = 1;
            do {
                long j2 = this.requested.get();
                while (j != j2) {
                    try {
                        Object obj = (Object) simpleQueue.poll();
                        if (!this.cancelled) {
                            if (obj == 0) {
                                this.cancelled = true;
                                subscriber.onComplete();
                                this.worker.dispose();
                                return;
                            }
                            subscriber.onNext(obj);
                            j++;
                        } else {
                            return;
                        }
                    } catch (Throwable th) {
                        Exceptions.throwIfFatal(th);
                        this.cancelled = true;
                        this.upstream.cancel();
                        subscriber.onError(th);
                        this.worker.dispose();
                        return;
                    }
                }
                if (!this.cancelled) {
                    if (simpleQueue.isEmpty()) {
                        this.cancelled = true;
                        subscriber.onComplete();
                        this.worker.dispose();
                        return;
                    }
                    this.produced = j;
                    i = addAndGet(-i);
                } else {
                    return;
                }
            } while (i != 0);
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableObserveOn.a
        void d() {
            int i;
            Subscriber<? super T> subscriber = this.downstream;
            SimpleQueue simpleQueue = this.queue;
            long j = this.produced;
            int i2 = 1;
            while (true) {
                long j2 = this.requested.get();
                while (true) {
                    i = (j > j2 ? 1 : (j == j2 ? 0 : -1));
                    if (i == 0) {
                        break;
                    }
                    boolean z = this.done;
                    try {
                        Object obj = (Object) simpleQueue.poll();
                        boolean z2 = obj == 0;
                        if (!a(z, z2, subscriber)) {
                            if (z2) {
                                break;
                            }
                            subscriber.onNext(obj);
                            j++;
                            if (j == this.limit) {
                                if (j2 != Long.MAX_VALUE) {
                                    j2 = this.requested.addAndGet(-j);
                                }
                                this.upstream.request(j);
                                j = 0;
                            }
                        } else {
                            return;
                        }
                    } catch (Throwable th) {
                        Exceptions.throwIfFatal(th);
                        this.cancelled = true;
                        this.upstream.cancel();
                        simpleQueue.clear();
                        subscriber.onError(th);
                        this.worker.dispose();
                        return;
                    }
                }
                if (i != 0 || !a(this.done, simpleQueue.isEmpty(), subscriber)) {
                    int i3 = get();
                    if (i2 == i3) {
                        this.produced = j;
                        i2 = addAndGet(-i2);
                        if (i2 == 0) {
                            return;
                        }
                    } else {
                        i2 = i3;
                    }
                } else {
                    return;
                }
            }
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableObserveOn.a
        void b() {
            int i = 1;
            while (!this.cancelled) {
                boolean z = this.done;
                this.downstream.onNext(null);
                if (z) {
                    this.cancelled = true;
                    Throwable th = this.error;
                    if (th != null) {
                        this.downstream.onError(th);
                    } else {
                        this.downstream.onComplete();
                    }
                    this.worker.dispose();
                    return;
                }
                i = addAndGet(-i);
                if (i == 0) {
                    return;
                }
            }
        }

        @Override // io.reactivex.rxjava3.internal.fuseable.SimpleQueue
        @Nullable
        public T poll() throws Throwable {
            T t = (T) this.queue.poll();
            if (!(t == null || this.sourceMode == 1)) {
                long j = this.produced + 1;
                if (j == this.limit) {
                    this.produced = 0L;
                    this.upstream.request(j);
                } else {
                    this.produced = j;
                }
            }
            return t;
        }
    }

    /* loaded from: classes5.dex */
    static final class b<T> extends a<T> {
        private static final long serialVersionUID = 644624475404284533L;
        long consumed;
        final ConditionalSubscriber<? super T> downstream;

        b(ConditionalSubscriber<? super T> conditionalSubscriber, Scheduler.Worker worker, boolean z, int i) {
            super(worker, z, i);
            this.downstream = conditionalSubscriber;
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.upstream, subscription)) {
                this.upstream = subscription;
                if (subscription instanceof QueueSubscription) {
                    QueueSubscription queueSubscription = (QueueSubscription) subscription;
                    int requestFusion = queueSubscription.requestFusion(7);
                    if (requestFusion == 1) {
                        this.sourceMode = 1;
                        this.queue = queueSubscription;
                        this.done = true;
                        this.downstream.onSubscribe(this);
                        return;
                    } else if (requestFusion == 2) {
                        this.sourceMode = 2;
                        this.queue = queueSubscription;
                        this.downstream.onSubscribe(this);
                        subscription.request(this.prefetch);
                        return;
                    }
                }
                this.queue = new SpscArrayQueue(this.prefetch);
                this.downstream.onSubscribe(this);
                subscription.request(this.prefetch);
            }
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableObserveOn.a
        void c() {
            ConditionalSubscriber<? super T> conditionalSubscriber = this.downstream;
            SimpleQueue simpleQueue = this.queue;
            long j = this.produced;
            int i = 1;
            do {
                long j2 = this.requested.get();
                while (j != j2) {
                    try {
                        Object obj = (Object) simpleQueue.poll();
                        if (!this.cancelled) {
                            if (obj == 0) {
                                this.cancelled = true;
                                conditionalSubscriber.onComplete();
                                this.worker.dispose();
                                return;
                            } else if (conditionalSubscriber.tryOnNext(obj)) {
                                j++;
                            }
                        } else {
                            return;
                        }
                    } catch (Throwable th) {
                        Exceptions.throwIfFatal(th);
                        this.cancelled = true;
                        this.upstream.cancel();
                        conditionalSubscriber.onError(th);
                        this.worker.dispose();
                        return;
                    }
                }
                if (!this.cancelled) {
                    if (simpleQueue.isEmpty()) {
                        this.cancelled = true;
                        conditionalSubscriber.onComplete();
                        this.worker.dispose();
                        return;
                    }
                    this.produced = j;
                    i = addAndGet(-i);
                } else {
                    return;
                }
            } while (i != 0);
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableObserveOn.a
        void d() {
            int i;
            ConditionalSubscriber<? super T> conditionalSubscriber = this.downstream;
            SimpleQueue simpleQueue = this.queue;
            long j = this.produced;
            long j2 = this.consumed;
            int i2 = 1;
            do {
                long j3 = this.requested.get();
                while (true) {
                    i = (j > j3 ? 1 : (j == j3 ? 0 : -1));
                    if (i == 0) {
                        break;
                    }
                    boolean z = this.done;
                    try {
                        Object obj = (Object) simpleQueue.poll();
                        boolean z2 = obj == 0;
                        if (!a(z, z2, conditionalSubscriber)) {
                            if (z2) {
                                break;
                            }
                            if (conditionalSubscriber.tryOnNext(obj)) {
                                j++;
                            }
                            j2++;
                            if (j2 == this.limit) {
                                this.upstream.request(j2);
                                j2 = 0;
                            }
                        } else {
                            return;
                        }
                    } catch (Throwable th) {
                        Exceptions.throwIfFatal(th);
                        this.cancelled = true;
                        this.upstream.cancel();
                        simpleQueue.clear();
                        conditionalSubscriber.onError(th);
                        this.worker.dispose();
                        return;
                    }
                }
                if (i != 0 || !a(this.done, simpleQueue.isEmpty(), conditionalSubscriber)) {
                    this.produced = j;
                    this.consumed = j2;
                    i2 = addAndGet(-i2);
                } else {
                    return;
                }
            } while (i2 != 0);
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableObserveOn.a
        void b() {
            int i = 1;
            while (!this.cancelled) {
                boolean z = this.done;
                this.downstream.onNext(null);
                if (z) {
                    this.cancelled = true;
                    Throwable th = this.error;
                    if (th != null) {
                        this.downstream.onError(th);
                    } else {
                        this.downstream.onComplete();
                    }
                    this.worker.dispose();
                    return;
                }
                i = addAndGet(-i);
                if (i == 0) {
                    return;
                }
            }
        }

        @Override // io.reactivex.rxjava3.internal.fuseable.SimpleQueue
        @Nullable
        public T poll() throws Throwable {
            T t = (T) this.queue.poll();
            if (!(t == null || this.sourceMode == 1)) {
                long j = this.consumed + 1;
                if (j == this.limit) {
                    this.consumed = 0L;
                    this.upstream.request(j);
                } else {
                    this.consumed = j;
                }
            }
            return t;
        }
    }
}
