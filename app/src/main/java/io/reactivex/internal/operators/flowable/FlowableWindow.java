package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.processors.UnicastProcessor;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes4.dex */
public final class FlowableWindow<T> extends a<T, Flowable<T>> {
    final long b;
    final long c;
    final int d;

    public FlowableWindow(Flowable<T> flowable, long j, long j2, int i) {
        super(flowable);
        this.b = j;
        this.c = j2;
        this.d = i;
    }

    @Override // io.reactivex.Flowable
    public void subscribeActual(Subscriber<? super Flowable<T>> subscriber) {
        long j = this.c;
        long j2 = this.b;
        if (j == j2) {
            this.source.subscribe((FlowableSubscriber) new a(subscriber, this.b, this.d));
        } else if (j > j2) {
            this.source.subscribe((FlowableSubscriber) new c(subscriber, this.b, this.c, this.d));
        } else {
            this.source.subscribe((FlowableSubscriber) new b(subscriber, this.b, this.c, this.d));
        }
    }

    /* loaded from: classes4.dex */
    static final class a<T> extends AtomicInteger implements FlowableSubscriber<T>, Runnable, Subscription {
        private static final long serialVersionUID = -2365647875069161133L;
        final int bufferSize;
        final Subscriber<? super Flowable<T>> downstream;
        long index;
        final AtomicBoolean once = new AtomicBoolean();
        final long size;
        Subscription upstream;
        UnicastProcessor<T> window;

        a(Subscriber<? super Flowable<T>> subscriber, long j, int i) {
            super(1);
            this.downstream = subscriber;
            this.size = j;
            this.bufferSize = i;
        }

        @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.upstream, subscription)) {
                this.upstream = subscription;
                this.downstream.onSubscribe(this);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            long j = this.index;
            UnicastProcessor<T> unicastProcessor = this.window;
            if (j == 0) {
                getAndIncrement();
                unicastProcessor = UnicastProcessor.create(this.bufferSize, this);
                this.window = unicastProcessor;
                this.downstream.onNext(unicastProcessor);
            }
            long j2 = j + 1;
            unicastProcessor.onNext(t);
            if (j2 == this.size) {
                this.index = 0L;
                this.window = null;
                unicastProcessor.onComplete();
                return;
            }
            this.index = j2;
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            UnicastProcessor<T> unicastProcessor = this.window;
            if (unicastProcessor != null) {
                this.window = null;
                unicastProcessor.onError(th);
            }
            this.downstream.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            UnicastProcessor<T> unicastProcessor = this.window;
            if (unicastProcessor != null) {
                this.window = null;
                unicastProcessor.onComplete();
            }
            this.downstream.onComplete();
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (SubscriptionHelper.validate(j)) {
                this.upstream.request(BackpressureHelper.multiplyCap(this.size, j));
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (this.once.compareAndSet(false, true)) {
                run();
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            if (decrementAndGet() == 0) {
                this.upstream.cancel();
            }
        }
    }

    /* loaded from: classes4.dex */
    static final class c<T> extends AtomicInteger implements FlowableSubscriber<T>, Runnable, Subscription {
        private static final long serialVersionUID = -8792836352386833856L;
        final int bufferSize;
        final Subscriber<? super Flowable<T>> downstream;
        long index;
        final long size;
        final long skip;
        Subscription upstream;
        UnicastProcessor<T> window;
        final AtomicBoolean once = new AtomicBoolean();
        final AtomicBoolean firstRequest = new AtomicBoolean();

        c(Subscriber<? super Flowable<T>> subscriber, long j, long j2, int i) {
            super(1);
            this.downstream = subscriber;
            this.size = j;
            this.skip = j2;
            this.bufferSize = i;
        }

        @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.upstream, subscription)) {
                this.upstream = subscription;
                this.downstream.onSubscribe(this);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            long j = this.index;
            UnicastProcessor<T> unicastProcessor = this.window;
            if (j == 0) {
                getAndIncrement();
                unicastProcessor = UnicastProcessor.create(this.bufferSize, this);
                this.window = unicastProcessor;
                this.downstream.onNext(unicastProcessor);
            }
            long j2 = j + 1;
            if (unicastProcessor != null) {
                unicastProcessor.onNext(t);
            }
            if (j2 == this.size) {
                this.window = null;
                unicastProcessor.onComplete();
            }
            if (j2 == this.skip) {
                this.index = 0L;
            } else {
                this.index = j2;
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            UnicastProcessor<T> unicastProcessor = this.window;
            if (unicastProcessor != null) {
                this.window = null;
                unicastProcessor.onError(th);
            }
            this.downstream.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            UnicastProcessor<T> unicastProcessor = this.window;
            if (unicastProcessor != null) {
                this.window = null;
                unicastProcessor.onComplete();
            }
            this.downstream.onComplete();
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (!SubscriptionHelper.validate(j)) {
                return;
            }
            if (this.firstRequest.get() || !this.firstRequest.compareAndSet(false, true)) {
                this.upstream.request(BackpressureHelper.multiplyCap(this.skip, j));
                return;
            }
            this.upstream.request(BackpressureHelper.addCap(BackpressureHelper.multiplyCap(this.size, j), BackpressureHelper.multiplyCap(this.skip - this.size, j - 1)));
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (this.once.compareAndSet(false, true)) {
                run();
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            if (decrementAndGet() == 0) {
                this.upstream.cancel();
            }
        }
    }

    /* loaded from: classes4.dex */
    static final class b<T> extends AtomicInteger implements FlowableSubscriber<T>, Runnable, Subscription {
        private static final long serialVersionUID = 2428527070996323976L;
        final int bufferSize;
        volatile boolean cancelled;
        volatile boolean done;
        final Subscriber<? super Flowable<T>> downstream;
        Throwable error;
        long index;
        long produced;
        final SpscLinkedArrayQueue<UnicastProcessor<T>> queue;
        final long size;
        final long skip;
        Subscription upstream;
        final ArrayDeque<UnicastProcessor<T>> windows = new ArrayDeque<>();
        final AtomicBoolean once = new AtomicBoolean();
        final AtomicBoolean firstRequest = new AtomicBoolean();
        final AtomicLong requested = new AtomicLong();
        final AtomicInteger wip = new AtomicInteger();

        b(Subscriber<? super Flowable<T>> subscriber, long j, long j2, int i) {
            super(1);
            this.downstream = subscriber;
            this.size = j;
            this.skip = j2;
            this.queue = new SpscLinkedArrayQueue<>(i);
            this.bufferSize = i;
        }

        @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.upstream, subscription)) {
                this.upstream = subscription;
                this.downstream.onSubscribe(this);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (!this.done) {
                long j = this.index;
                if (j == 0 && !this.cancelled) {
                    getAndIncrement();
                    UnicastProcessor<T> create = UnicastProcessor.create(this.bufferSize, this);
                    this.windows.offer(create);
                    this.queue.offer(create);
                    a();
                }
                long j2 = j + 1;
                Iterator<UnicastProcessor<T>> it = this.windows.iterator();
                while (it.hasNext()) {
                    it.next().onNext(t);
                }
                long j3 = this.produced + 1;
                if (j3 == this.size) {
                    this.produced = j3 - this.skip;
                    UnicastProcessor<T> poll = this.windows.poll();
                    if (poll != null) {
                        poll.onComplete();
                    }
                } else {
                    this.produced = j3;
                }
                if (j2 == this.skip) {
                    this.index = 0L;
                } else {
                    this.index = j2;
                }
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.done) {
                RxJavaPlugins.onError(th);
                return;
            }
            Iterator<UnicastProcessor<T>> it = this.windows.iterator();
            while (it.hasNext()) {
                it.next().onError(th);
            }
            this.windows.clear();
            this.error = th;
            this.done = true;
            a();
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (!this.done) {
                Iterator<UnicastProcessor<T>> it = this.windows.iterator();
                while (it.hasNext()) {
                    it.next().onComplete();
                }
                this.windows.clear();
                this.done = true;
                a();
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:18:0x003a, code lost:
            if (r10 != 0) goto L_0x0049;
         */
        /* JADX WARN: Code restructure failed: missing block: B:20:0x0046, code lost:
            if (a(r14.done, r1.isEmpty(), r0, r1) == false) goto L_0x0049;
         */
        /* JADX WARN: Code restructure failed: missing block: B:21:0x0048, code lost:
            return;
         */
        /* JADX WARN: Code restructure failed: missing block: B:23:0x004b, code lost:
            if (r8 == 0) goto L_0x005c;
         */
        /* JADX WARN: Code restructure failed: missing block: B:25:0x0054, code lost:
            if (r4 == Long.MAX_VALUE) goto L_0x005c;
         */
        /* JADX WARN: Code restructure failed: missing block: B:26:0x0056, code lost:
            r14.requested.addAndGet(-r8);
         */
        /* JADX WARN: Code restructure failed: missing block: B:27:0x005c, code lost:
            r3 = r14.wip.addAndGet(-r3);
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        void a() {
            /*
                r14 = this;
                java.util.concurrent.atomic.AtomicInteger r0 = r14.wip
                int r0 = r0.getAndIncrement()
                if (r0 == 0) goto L_0x0009
                return
            L_0x0009:
                org.reactivestreams.Subscriber<? super io.reactivex.Flowable<T>> r0 = r14.downstream
                io.reactivex.internal.queue.SpscLinkedArrayQueue<io.reactivex.processors.UnicastProcessor<T>> r1 = r14.queue
                r2 = 1
                r3 = r2
            L_0x000f:
                java.util.concurrent.atomic.AtomicLong r4 = r14.requested
                long r4 = r4.get()
                r6 = 0
                r8 = r6
            L_0x0018:
                int r10 = (r8 > r4 ? 1 : (r8 == r4 ? 0 : -1))
                if (r10 == 0) goto L_0x003a
                boolean r11 = r14.done
                java.lang.Object r12 = r1.poll()
                io.reactivex.processors.UnicastProcessor r12 = (io.reactivex.processors.UnicastProcessor) r12
                if (r12 != 0) goto L_0x0028
                r13 = r2
                goto L_0x0029
            L_0x0028:
                r13 = 0
            L_0x0029:
                boolean r11 = r14.a(r11, r13, r0, r1)
                if (r11 == 0) goto L_0x0030
                return
            L_0x0030:
                if (r13 == 0) goto L_0x0033
                goto L_0x003a
            L_0x0033:
                r0.onNext(r12)
                r10 = 1
                long r8 = r8 + r10
                goto L_0x0018
            L_0x003a:
                if (r10 != 0) goto L_0x0049
                boolean r10 = r14.done
                boolean r11 = r1.isEmpty()
                boolean r10 = r14.a(r10, r11, r0, r1)
                if (r10 == 0) goto L_0x0049
                return
            L_0x0049:
                int r6 = (r8 > r6 ? 1 : (r8 == r6 ? 0 : -1))
                if (r6 == 0) goto L_0x005c
                r6 = 9223372036854775807(0x7fffffffffffffff, double:NaN)
                int r4 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
                if (r4 == 0) goto L_0x005c
                java.util.concurrent.atomic.AtomicLong r4 = r14.requested
                long r5 = -r8
                r4.addAndGet(r5)
            L_0x005c:
                java.util.concurrent.atomic.AtomicInteger r4 = r14.wip
                int r3 = -r3
                int r3 = r4.addAndGet(r3)
                if (r3 != 0) goto L_0x000f
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: io.reactivex.internal.operators.flowable.FlowableWindow.b.a():void");
        }

        boolean a(boolean z, boolean z2, Subscriber<?> subscriber, SpscLinkedArrayQueue<?> spscLinkedArrayQueue) {
            if (this.cancelled) {
                spscLinkedArrayQueue.clear();
                return true;
            } else if (!z) {
                return false;
            } else {
                Throwable th = this.error;
                if (th != null) {
                    spscLinkedArrayQueue.clear();
                    subscriber.onError(th);
                    return true;
                } else if (!z2) {
                    return false;
                } else {
                    subscriber.onComplete();
                    return true;
                }
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (SubscriptionHelper.validate(j)) {
                BackpressureHelper.add(this.requested, j);
                if (this.firstRequest.get() || !this.firstRequest.compareAndSet(false, true)) {
                    this.upstream.request(BackpressureHelper.multiplyCap(this.skip, j));
                } else {
                    this.upstream.request(BackpressureHelper.addCap(this.size, BackpressureHelper.multiplyCap(this.skip, j - 1)));
                }
                a();
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.cancelled = true;
            if (this.once.compareAndSet(false, true)) {
                run();
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            if (decrementAndGet() == 0) {
                this.upstream.cancel();
            }
        }
    }
}
