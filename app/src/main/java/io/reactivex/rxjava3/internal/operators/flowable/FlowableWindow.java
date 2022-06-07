package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.BackpressureHelper;
import io.reactivex.rxjava3.processors.UnicastProcessor;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes5.dex */
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

    @Override // io.reactivex.rxjava3.core.Flowable
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

    /* loaded from: classes5.dex */
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

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.upstream, subscription)) {
                this.upstream = subscription;
                this.downstream.onSubscribe(this);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            b bVar;
            long j = this.index;
            UnicastProcessor<T> unicastProcessor = this.window;
            if (j == 0) {
                getAndIncrement();
                unicastProcessor = UnicastProcessor.create(this.bufferSize, this);
                this.window = unicastProcessor;
                bVar = new b(unicastProcessor);
                this.downstream.onNext(bVar);
            } else {
                bVar = null;
            }
            long j2 = j + 1;
            unicastProcessor.onNext(t);
            if (j2 == this.size) {
                this.index = 0L;
                this.window = null;
                unicastProcessor.onComplete();
            } else {
                this.index = j2;
            }
            if (bVar != null && bVar.a()) {
                bVar.b.onComplete();
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

    /* loaded from: classes5.dex */
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

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.upstream, subscription)) {
                this.upstream = subscription;
                this.downstream.onSubscribe(this);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            b bVar;
            long j = this.index;
            UnicastProcessor<T> unicastProcessor = this.window;
            if (j == 0) {
                getAndIncrement();
                unicastProcessor = UnicastProcessor.create(this.bufferSize, this);
                this.window = unicastProcessor;
                bVar = new b(unicastProcessor);
                this.downstream.onNext(bVar);
            } else {
                bVar = null;
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
            if (bVar != null && bVar.a()) {
                bVar.b.onComplete();
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

    /* loaded from: classes5.dex */
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

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.upstream, subscription)) {
                this.upstream = subscription;
                this.downstream.onSubscribe(this);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            UnicastProcessor<T> unicastProcessor;
            long j = this.index;
            if (j != 0 || this.cancelled) {
                unicastProcessor = null;
            } else {
                getAndIncrement();
                unicastProcessor = UnicastProcessor.create(this.bufferSize, this);
                this.windows.offer(unicastProcessor);
            }
            long j2 = j + 1;
            Iterator<UnicastProcessor<T>> it = this.windows.iterator();
            while (it.hasNext()) {
                it.next().onNext(t);
            }
            if (unicastProcessor != null) {
                this.queue.offer(unicastProcessor);
                a();
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

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
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
            Iterator<UnicastProcessor<T>> it = this.windows.iterator();
            while (it.hasNext()) {
                it.next().onComplete();
            }
            this.windows.clear();
            this.done = true;
            a();
        }

        /* JADX WARN: Code restructure failed: missing block: B:28:0x005d, code lost:
            if (r10 != 0) goto L_0x0071;
         */
        /* JADX WARN: Code restructure failed: missing block: B:30:0x0061, code lost:
            if (r15.cancelled == false) goto L_0x0064;
         */
        /* JADX WARN: Code restructure failed: missing block: B:32:0x006e, code lost:
            if (a(r15.done, r1.isEmpty(), r0, r1) == false) goto L_0x0071;
         */
        /* JADX WARN: Code restructure failed: missing block: B:33:0x0070, code lost:
            return;
         */
        /* JADX WARN: Code restructure failed: missing block: B:35:0x0073, code lost:
            if (r8 == 0) goto L_0x0084;
         */
        /* JADX WARN: Code restructure failed: missing block: B:37:0x007c, code lost:
            if (r4 == Long.MAX_VALUE) goto L_0x0084;
         */
        /* JADX WARN: Code restructure failed: missing block: B:38:0x007e, code lost:
            r15.requested.addAndGet(-r8);
         */
        /* JADX WARN: Code restructure failed: missing block: B:51:0x000f, code lost:
            continue;
         */
        /* JADX WARN: Code restructure failed: missing block: B:52:0x000f, code lost:
            continue;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        void a() {
            /*
                r15 = this;
                java.util.concurrent.atomic.AtomicInteger r0 = r15.wip
                int r0 = r0.getAndIncrement()
                if (r0 == 0) goto L_0x0009
                return
            L_0x0009:
                org.reactivestreams.Subscriber<? super io.reactivex.rxjava3.core.Flowable<T>> r0 = r15.downstream
                io.reactivex.rxjava3.internal.queue.SpscLinkedArrayQueue<io.reactivex.rxjava3.processors.UnicastProcessor<T>> r1 = r15.queue
                r2 = 1
                r3 = r2
            L_0x000f:
                boolean r4 = r15.cancelled
                if (r4 == 0) goto L_0x001f
            L_0x0013:
                java.lang.Object r4 = r1.poll()
                io.reactivex.rxjava3.processors.UnicastProcessor r4 = (io.reactivex.rxjava3.processors.UnicastProcessor) r4
                if (r4 == 0) goto L_0x0084
                r4.onComplete()
                goto L_0x0013
            L_0x001f:
                java.util.concurrent.atomic.AtomicLong r4 = r15.requested
                long r4 = r4.get()
                r6 = 0
                r8 = r6
            L_0x0028:
                int r10 = (r8 > r4 ? 1 : (r8 == r4 ? 0 : -1))
                if (r10 == 0) goto L_0x005d
                boolean r11 = r15.done
                java.lang.Object r12 = r1.poll()
                io.reactivex.rxjava3.processors.UnicastProcessor r12 = (io.reactivex.rxjava3.processors.UnicastProcessor) r12
                if (r12 != 0) goto L_0x0038
                r13 = r2
                goto L_0x0039
            L_0x0038:
                r13 = 0
            L_0x0039:
                boolean r14 = r15.cancelled
                if (r14 == 0) goto L_0x003e
                goto L_0x000f
            L_0x003e:
                boolean r11 = r15.a(r11, r13, r0, r1)
                if (r11 == 0) goto L_0x0045
                return
            L_0x0045:
                if (r13 == 0) goto L_0x0048
                goto L_0x005d
            L_0x0048:
                io.reactivex.rxjava3.internal.operators.flowable.b r10 = new io.reactivex.rxjava3.internal.operators.flowable.b
                r10.<init>(r12)
                r0.onNext(r10)
                boolean r10 = r10.a()
                if (r10 == 0) goto L_0x0059
                r12.onComplete()
            L_0x0059:
                r10 = 1
                long r8 = r8 + r10
                goto L_0x0028
            L_0x005d:
                if (r10 != 0) goto L_0x0071
                boolean r10 = r15.cancelled
                if (r10 == 0) goto L_0x0064
                goto L_0x000f
            L_0x0064:
                boolean r10 = r15.done
                boolean r11 = r1.isEmpty()
                boolean r10 = r15.a(r10, r11, r0, r1)
                if (r10 == 0) goto L_0x0071
                return
            L_0x0071:
                int r6 = (r8 > r6 ? 1 : (r8 == r6 ? 0 : -1))
                if (r6 == 0) goto L_0x0084
                r6 = 9223372036854775807(0x7fffffffffffffff, double:NaN)
                int r4 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
                if (r4 == 0) goto L_0x0084
                java.util.concurrent.atomic.AtomicLong r4 = r15.requested
                long r5 = -r8
                r4.addAndGet(r5)
            L_0x0084:
                java.util.concurrent.atomic.AtomicInteger r4 = r15.wip
                int r3 = -r3
                int r3 = r4.addAndGet(r3)
                if (r3 != 0) goto L_0x000f
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: io.reactivex.rxjava3.internal.operators.flowable.FlowableWindow.b.a():void");
        }

        boolean a(boolean z, boolean z2, Subscriber<?> subscriber, SpscLinkedArrayQueue<?> spscLinkedArrayQueue) {
            if (!z) {
                return false;
            }
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
            a();
        }

        @Override // java.lang.Runnable
        public void run() {
            if (decrementAndGet() == 0) {
                this.upstream.cancel();
            }
        }
    }
}
