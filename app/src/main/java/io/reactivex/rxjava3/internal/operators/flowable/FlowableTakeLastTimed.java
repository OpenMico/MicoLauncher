package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.BackpressureHelper;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes5.dex */
public final class FlowableTakeLastTimed<T> extends a<T, T> {
    final long b;
    final long c;
    final TimeUnit d;
    final Scheduler e;
    final int f;
    final boolean g;

    public FlowableTakeLastTimed(Flowable<T> flowable, long j, long j2, TimeUnit timeUnit, Scheduler scheduler, int i, boolean z) {
        super(flowable);
        this.b = j;
        this.c = j2;
        this.d = timeUnit;
        this.e = scheduler;
        this.f = i;
        this.g = z;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    protected void subscribeActual(Subscriber<? super T> subscriber) {
        this.source.subscribe((FlowableSubscriber) new a(subscriber, this.b, this.c, this.d, this.e, this.f, this.g));
    }

    /* loaded from: classes5.dex */
    static final class a<T> extends AtomicInteger implements FlowableSubscriber<T>, Subscription {
        private static final long serialVersionUID = -5677354903406201275L;
        volatile boolean cancelled;
        final long count;
        final boolean delayError;
        volatile boolean done;
        final Subscriber<? super T> downstream;
        Throwable error;
        final SpscLinkedArrayQueue<Object> queue;
        final AtomicLong requested = new AtomicLong();
        final Scheduler scheduler;
        final long time;
        final TimeUnit unit;
        Subscription upstream;

        a(Subscriber<? super T> subscriber, long j, long j2, TimeUnit timeUnit, Scheduler scheduler, int i, boolean z) {
            this.downstream = subscriber;
            this.count = j;
            this.time = j2;
            this.unit = timeUnit;
            this.scheduler = scheduler;
            this.queue = new SpscLinkedArrayQueue<>(i);
            this.delayError = z;
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.upstream, subscription)) {
                this.upstream = subscription;
                this.downstream.onSubscribe(this);
                subscription.request(Long.MAX_VALUE);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            SpscLinkedArrayQueue<Object> spscLinkedArrayQueue = this.queue;
            long now = this.scheduler.now(this.unit);
            spscLinkedArrayQueue.offer(Long.valueOf(now), t);
            a(now, spscLinkedArrayQueue);
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.delayError) {
                a(this.scheduler.now(this.unit), this.queue);
            }
            this.error = th;
            this.done = true;
            a();
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            a(this.scheduler.now(this.unit), this.queue);
            this.done = true;
            a();
        }

        void a(long j, SpscLinkedArrayQueue<Object> spscLinkedArrayQueue) {
            long j2 = this.time;
            long j3 = this.count;
            boolean z = j3 == Long.MAX_VALUE;
            while (!spscLinkedArrayQueue.isEmpty()) {
                if (((Long) spscLinkedArrayQueue.peek()).longValue() < j - j2 || (!z && (spscLinkedArrayQueue.size() >> 1) > j3)) {
                    spscLinkedArrayQueue.poll();
                    spscLinkedArrayQueue.poll();
                } else {
                    return;
                }
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (SubscriptionHelper.validate(j)) {
                BackpressureHelper.add(this.requested, j);
                a();
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (!this.cancelled) {
                this.cancelled = true;
                this.upstream.cancel();
                if (getAndIncrement() == 0) {
                    this.queue.clear();
                }
            }
        }

        void a() {
            if (getAndIncrement() == 0) {
                Subscriber<? super T> subscriber = this.downstream;
                SpscLinkedArrayQueue<Object> spscLinkedArrayQueue = this.queue;
                boolean z = this.delayError;
                int i = 1;
                do {
                    if (this.done) {
                        if (!a(spscLinkedArrayQueue.isEmpty(), subscriber, z)) {
                            long j = this.requested.get();
                            long j2 = 0;
                            while (true) {
                                if (!a(spscLinkedArrayQueue.peek() == null, subscriber, z)) {
                                    if (j != j2) {
                                        spscLinkedArrayQueue.poll();
                                        subscriber.onNext(spscLinkedArrayQueue.poll());
                                        j2++;
                                    } else if (j2 != 0) {
                                        BackpressureHelper.produced(this.requested, j2);
                                    }
                                } else {
                                    return;
                                }
                            }
                        } else {
                            return;
                        }
                    }
                    i = addAndGet(-i);
                } while (i != 0);
            }
        }

        boolean a(boolean z, Subscriber<? super T> subscriber, boolean z2) {
            if (this.cancelled) {
                this.queue.clear();
                return true;
            } else if (!z2) {
                Throwable th = this.error;
                if (th != null) {
                    this.queue.clear();
                    subscriber.onError(th);
                    return true;
                } else if (!z) {
                    return false;
                } else {
                    subscriber.onComplete();
                    return true;
                }
            } else if (!z) {
                return false;
            } else {
                Throwable th2 = this.error;
                if (th2 != null) {
                    subscriber.onError(th2);
                } else {
                    subscriber.onComplete();
                }
                return true;
            }
        }
    }
}
