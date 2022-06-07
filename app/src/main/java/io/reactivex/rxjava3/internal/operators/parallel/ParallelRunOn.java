package io.reactivex.rxjava3.internal.operators.parallel;

import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.exceptions.MissingBackpressureException;
import io.reactivex.rxjava3.internal.fuseable.ConditionalSubscriber;
import io.reactivex.rxjava3.internal.queue.SpscArrayQueue;
import io.reactivex.rxjava3.internal.schedulers.SchedulerMultiWorkerSupport;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.BackpressureHelper;
import io.reactivex.rxjava3.parallel.ParallelFlowable;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes5.dex */
public final class ParallelRunOn<T> extends ParallelFlowable<T> {
    final ParallelFlowable<? extends T> a;
    final Scheduler b;
    final int c;

    public ParallelRunOn(ParallelFlowable<? extends T> parallelFlowable, Scheduler scheduler, int i) {
        this.a = parallelFlowable;
        this.b = scheduler;
        this.c = i;
    }

    @Override // io.reactivex.rxjava3.parallel.ParallelFlowable, autodispose2.ParallelFlowableSubscribeProxy
    public void subscribe(Subscriber<? super T>[] subscriberArr) {
        if (validate(subscriberArr)) {
            int length = subscriberArr.length;
            Subscriber<T>[] subscriberArr2 = new Subscriber[length];
            Scheduler scheduler = this.b;
            if (scheduler instanceof SchedulerMultiWorkerSupport) {
                ((SchedulerMultiWorkerSupport) scheduler).createWorkers(length, new b(subscriberArr, subscriberArr2));
            } else {
                for (int i = 0; i < length; i++) {
                    a(i, subscriberArr, subscriberArr2, this.b.createWorker());
                }
            }
            this.a.subscribe(subscriberArr2);
        }
    }

    void a(int i, Subscriber<? super T>[] subscriberArr, Subscriber<T>[] subscriberArr2, Scheduler.Worker worker) {
        Subscriber<? super T> subscriber = subscriberArr[i];
        SpscArrayQueue spscArrayQueue = new SpscArrayQueue(this.c);
        if (subscriber instanceof ConditionalSubscriber) {
            subscriberArr2[i] = new c((ConditionalSubscriber) subscriber, this.c, spscArrayQueue, worker);
        } else {
            subscriberArr2[i] = new d(subscriber, this.c, spscArrayQueue, worker);
        }
    }

    /* loaded from: classes5.dex */
    final class b implements SchedulerMultiWorkerSupport.WorkerCallback {
        final Subscriber<? super T>[] a;
        final Subscriber<T>[] b;

        b(Subscriber<? super T>[] subscriberArr, Subscriber<T>[] subscriberArr2) {
            this.a = subscriberArr;
            this.b = subscriberArr2;
        }

        @Override // io.reactivex.rxjava3.internal.schedulers.SchedulerMultiWorkerSupport.WorkerCallback
        public void onWorker(int i, Scheduler.Worker worker) {
            ParallelRunOn.this.a(i, this.a, this.b, worker);
        }
    }

    @Override // io.reactivex.rxjava3.parallel.ParallelFlowable
    public int parallelism() {
        return this.a.parallelism();
    }

    /* loaded from: classes5.dex */
    static abstract class a<T> extends AtomicInteger implements FlowableSubscriber<T>, Runnable, Subscription {
        private static final long serialVersionUID = 9222303586456402150L;
        volatile boolean cancelled;
        int consumed;
        volatile boolean done;
        Throwable error;
        final int limit;
        final int prefetch;
        final SpscArrayQueue<T> queue;
        final AtomicLong requested = new AtomicLong();
        Subscription upstream;
        final Scheduler.Worker worker;

        a(int i, SpscArrayQueue<T> spscArrayQueue, Scheduler.Worker worker) {
            this.prefetch = i;
            this.queue = spscArrayQueue;
            this.limit = i - (i >> 2);
            this.worker = worker;
        }

        @Override // org.reactivestreams.Subscriber
        public final void onNext(T t) {
            if (!this.done) {
                if (!this.queue.offer(t)) {
                    this.upstream.cancel();
                    onError(new MissingBackpressureException("Queue is full?!"));
                    return;
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
                if (getAndIncrement() == 0) {
                    this.queue.clear();
                }
            }
        }

        final void a() {
            if (getAndIncrement() == 0) {
                this.worker.schedule(this);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public static final class d<T> extends a<T> {
        private static final long serialVersionUID = 1075119423897941642L;
        final Subscriber<? super T> downstream;

        d(Subscriber<? super T> subscriber, int i, SpscArrayQueue<T> spscArrayQueue, Scheduler.Worker worker) {
            super(i, spscArrayQueue, worker);
            this.downstream = subscriber;
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.upstream, subscription)) {
                this.upstream = subscription;
                this.downstream.onSubscribe(this);
                subscription.request(this.prefetch);
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:28:0x0065, code lost:
            if (r13 != 0) goto L_0x0092;
         */
        /* JADX WARN: Code restructure failed: missing block: B:30:0x0069, code lost:
            if (r18.cancelled == false) goto L_0x006f;
         */
        /* JADX WARN: Code restructure failed: missing block: B:31:0x006b, code lost:
            r2.clear();
         */
        /* JADX WARN: Code restructure failed: missing block: B:32:0x006e, code lost:
            return;
         */
        /* JADX WARN: Code restructure failed: missing block: B:34:0x0071, code lost:
            if (r18.done == false) goto L_0x0092;
         */
        /* JADX WARN: Code restructure failed: missing block: B:35:0x0073, code lost:
            r13 = r18.error;
         */
        /* JADX WARN: Code restructure failed: missing block: B:36:0x0075, code lost:
            if (r13 == null) goto L_0x0083;
         */
        /* JADX WARN: Code restructure failed: missing block: B:37:0x0077, code lost:
            r2.clear();
            r3.onError(r13);
            r18.worker.dispose();
         */
        /* JADX WARN: Code restructure failed: missing block: B:38:0x0082, code lost:
            return;
         */
        /* JADX WARN: Code restructure failed: missing block: B:40:0x0087, code lost:
            if (r2.isEmpty() == false) goto L_0x0092;
         */
        /* JADX WARN: Code restructure failed: missing block: B:41:0x0089, code lost:
            r3.onComplete();
            r18.worker.dispose();
         */
        /* JADX WARN: Code restructure failed: missing block: B:42:0x0091, code lost:
            return;
         */
        /* JADX WARN: Code restructure failed: missing block: B:44:0x0094, code lost:
            if (r11 == 0) goto L_0x00a5;
         */
        /* JADX WARN: Code restructure failed: missing block: B:46:0x009d, code lost:
            if (r7 == Long.MAX_VALUE) goto L_0x00a5;
         */
        /* JADX WARN: Code restructure failed: missing block: B:47:0x009f, code lost:
            r18.requested.addAndGet(-r11);
         */
        /* JADX WARN: Code restructure failed: missing block: B:48:0x00a5, code lost:
            r7 = get();
         */
        /* JADX WARN: Code restructure failed: missing block: B:49:0x00a9, code lost:
            if (r7 != r6) goto L_0x00b5;
         */
        /* JADX WARN: Code restructure failed: missing block: B:50:0x00ab, code lost:
            r18.consumed = r1;
            r6 = addAndGet(-r6);
         */
        /* JADX WARN: Code restructure failed: missing block: B:51:0x00b2, code lost:
            if (r6 != 0) goto L_0x000c;
         */
        /* JADX WARN: Code restructure failed: missing block: B:52:0x00b4, code lost:
            return;
         */
        /* JADX WARN: Code restructure failed: missing block: B:53:0x00b5, code lost:
            r6 = r7;
         */
        @Override // java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void run() {
            /*
                r18 = this;
                r0 = r18
                int r1 = r0.consumed
                io.reactivex.rxjava3.internal.queue.SpscArrayQueue r2 = r0.queue
                org.reactivestreams.Subscriber<? super T> r3 = r0.downstream
                int r4 = r0.limit
                r5 = 1
                r6 = r5
            L_0x000c:
                java.util.concurrent.atomic.AtomicLong r7 = r0.requested
                long r7 = r7.get()
                r9 = 0
                r11 = r9
            L_0x0015:
                int r13 = (r11 > r7 ? 1 : (r11 == r7 ? 0 : -1))
                if (r13 == 0) goto L_0x0065
                boolean r14 = r0.cancelled
                if (r14 == 0) goto L_0x0021
                r2.clear()
                return
            L_0x0021:
                boolean r14 = r0.done
                if (r14 == 0) goto L_0x0035
                java.lang.Throwable r15 = r0.error
                if (r15 == 0) goto L_0x0035
                r2.clear()
                r3.onError(r15)
                io.reactivex.rxjava3.core.Scheduler$Worker r1 = r0.worker
                r1.dispose()
                return
            L_0x0035:
                java.lang.Object r15 = r2.poll()
                r16 = 0
                if (r15 != 0) goto L_0x0040
                r17 = r5
                goto L_0x0042
            L_0x0040:
                r17 = r16
            L_0x0042:
                if (r14 == 0) goto L_0x004f
                if (r17 == 0) goto L_0x004f
                r3.onComplete()
                io.reactivex.rxjava3.core.Scheduler$Worker r1 = r0.worker
                r1.dispose()
                return
            L_0x004f:
                if (r17 == 0) goto L_0x0052
                goto L_0x0065
            L_0x0052:
                r3.onNext(r15)
                r13 = 1
                long r11 = r11 + r13
                int r1 = r1 + 1
                if (r1 != r4) goto L_0x0015
                org.reactivestreams.Subscription r13 = r0.upstream
                long r14 = (long) r1
                r13.request(r14)
                r1 = r16
                goto L_0x0015
            L_0x0065:
                if (r13 != 0) goto L_0x0092
                boolean r13 = r0.cancelled
                if (r13 == 0) goto L_0x006f
                r2.clear()
                return
            L_0x006f:
                boolean r13 = r0.done
                if (r13 == 0) goto L_0x0092
                java.lang.Throwable r13 = r0.error
                if (r13 == 0) goto L_0x0083
                r2.clear()
                r3.onError(r13)
                io.reactivex.rxjava3.core.Scheduler$Worker r1 = r0.worker
                r1.dispose()
                return
            L_0x0083:
                boolean r13 = r2.isEmpty()
                if (r13 == 0) goto L_0x0092
                r3.onComplete()
                io.reactivex.rxjava3.core.Scheduler$Worker r1 = r0.worker
                r1.dispose()
                return
            L_0x0092:
                int r9 = (r11 > r9 ? 1 : (r11 == r9 ? 0 : -1))
                if (r9 == 0) goto L_0x00a5
                r9 = 9223372036854775807(0x7fffffffffffffff, double:NaN)
                int r7 = (r7 > r9 ? 1 : (r7 == r9 ? 0 : -1))
                if (r7 == 0) goto L_0x00a5
                java.util.concurrent.atomic.AtomicLong r7 = r0.requested
                long r8 = -r11
                r7.addAndGet(r8)
            L_0x00a5:
                int r7 = r18.get()
                if (r7 != r6) goto L_0x00b5
                r0.consumed = r1
                int r6 = -r6
                int r6 = r0.addAndGet(r6)
                if (r6 != 0) goto L_0x000c
                return
            L_0x00b5:
                r6 = r7
                goto L_0x000c
            */
            throw new UnsupportedOperationException("Method not decompiled: io.reactivex.rxjava3.internal.operators.parallel.ParallelRunOn.d.run():void");
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public static final class c<T> extends a<T> {
        private static final long serialVersionUID = 1075119423897941642L;
        final ConditionalSubscriber<? super T> downstream;

        c(ConditionalSubscriber<? super T> conditionalSubscriber, int i, SpscArrayQueue<T> spscArrayQueue, Scheduler.Worker worker) {
            super(i, spscArrayQueue, worker);
            this.downstream = conditionalSubscriber;
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.upstream, subscription)) {
                this.upstream = subscription;
                this.downstream.onSubscribe(this);
                subscription.request(this.prefetch);
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:31:0x0068, code lost:
            if (r13 != 0) goto L_0x0095;
         */
        /* JADX WARN: Code restructure failed: missing block: B:33:0x006c, code lost:
            if (r18.cancelled == false) goto L_0x0072;
         */
        /* JADX WARN: Code restructure failed: missing block: B:34:0x006e, code lost:
            r2.clear();
         */
        /* JADX WARN: Code restructure failed: missing block: B:35:0x0071, code lost:
            return;
         */
        /* JADX WARN: Code restructure failed: missing block: B:37:0x0074, code lost:
            if (r18.done == false) goto L_0x0095;
         */
        /* JADX WARN: Code restructure failed: missing block: B:38:0x0076, code lost:
            r7 = r18.error;
         */
        /* JADX WARN: Code restructure failed: missing block: B:39:0x0078, code lost:
            if (r7 == null) goto L_0x0086;
         */
        /* JADX WARN: Code restructure failed: missing block: B:40:0x007a, code lost:
            r2.clear();
            r3.onError(r7);
            r18.worker.dispose();
         */
        /* JADX WARN: Code restructure failed: missing block: B:41:0x0085, code lost:
            return;
         */
        /* JADX WARN: Code restructure failed: missing block: B:43:0x008a, code lost:
            if (r2.isEmpty() == false) goto L_0x0095;
         */
        /* JADX WARN: Code restructure failed: missing block: B:44:0x008c, code lost:
            r3.onComplete();
            r18.worker.dispose();
         */
        /* JADX WARN: Code restructure failed: missing block: B:45:0x0094, code lost:
            return;
         */
        /* JADX WARN: Code restructure failed: missing block: B:47:0x0097, code lost:
            if (r11 == 0) goto L_0x009e;
         */
        /* JADX WARN: Code restructure failed: missing block: B:48:0x0099, code lost:
            io.reactivex.rxjava3.internal.util.BackpressureHelper.produced(r18.requested, r11);
         */
        /* JADX WARN: Code restructure failed: missing block: B:49:0x009e, code lost:
            r18.consumed = r1;
            r6 = addAndGet(-r6);
         */
        @Override // java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void run() {
            /*
                r18 = this;
                r0 = r18
                int r1 = r0.consumed
                io.reactivex.rxjava3.internal.queue.SpscArrayQueue r2 = r0.queue
                io.reactivex.rxjava3.internal.fuseable.ConditionalSubscriber<? super T> r3 = r0.downstream
                int r4 = r0.limit
                r5 = 1
                r6 = r5
            L_0x000c:
                java.util.concurrent.atomic.AtomicLong r7 = r0.requested
                long r7 = r7.get()
                r9 = 0
                r11 = r9
            L_0x0015:
                int r13 = (r11 > r7 ? 1 : (r11 == r7 ? 0 : -1))
                if (r13 == 0) goto L_0x0068
                boolean r14 = r0.cancelled
                if (r14 == 0) goto L_0x0021
                r2.clear()
                return
            L_0x0021:
                boolean r14 = r0.done
                if (r14 == 0) goto L_0x0035
                java.lang.Throwable r15 = r0.error
                if (r15 == 0) goto L_0x0035
                r2.clear()
                r3.onError(r15)
                io.reactivex.rxjava3.core.Scheduler$Worker r1 = r0.worker
                r1.dispose()
                return
            L_0x0035:
                java.lang.Object r15 = r2.poll()
                r16 = 0
                if (r15 != 0) goto L_0x0040
                r17 = r5
                goto L_0x0042
            L_0x0040:
                r17 = r16
            L_0x0042:
                if (r14 == 0) goto L_0x004f
                if (r17 == 0) goto L_0x004f
                r3.onComplete()
                io.reactivex.rxjava3.core.Scheduler$Worker r1 = r0.worker
                r1.dispose()
                return
            L_0x004f:
                if (r17 == 0) goto L_0x0052
                goto L_0x0068
            L_0x0052:
                boolean r13 = r3.tryOnNext(r15)
                if (r13 == 0) goto L_0x005b
                r13 = 1
                long r11 = r11 + r13
            L_0x005b:
                int r1 = r1 + 1
                if (r1 != r4) goto L_0x0015
                org.reactivestreams.Subscription r13 = r0.upstream
                long r14 = (long) r1
                r13.request(r14)
                r1 = r16
                goto L_0x0015
            L_0x0068:
                if (r13 != 0) goto L_0x0095
                boolean r7 = r0.cancelled
                if (r7 == 0) goto L_0x0072
                r2.clear()
                return
            L_0x0072:
                boolean r7 = r0.done
                if (r7 == 0) goto L_0x0095
                java.lang.Throwable r7 = r0.error
                if (r7 == 0) goto L_0x0086
                r2.clear()
                r3.onError(r7)
                io.reactivex.rxjava3.core.Scheduler$Worker r1 = r0.worker
                r1.dispose()
                return
            L_0x0086:
                boolean r7 = r2.isEmpty()
                if (r7 == 0) goto L_0x0095
                r3.onComplete()
                io.reactivex.rxjava3.core.Scheduler$Worker r1 = r0.worker
                r1.dispose()
                return
            L_0x0095:
                int r7 = (r11 > r9 ? 1 : (r11 == r9 ? 0 : -1))
                if (r7 == 0) goto L_0x009e
                java.util.concurrent.atomic.AtomicLong r7 = r0.requested
                io.reactivex.rxjava3.internal.util.BackpressureHelper.produced(r7, r11)
            L_0x009e:
                r0.consumed = r1
                int r6 = -r6
                int r6 = r0.addAndGet(r6)
                if (r6 != 0) goto L_0x000c
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: io.reactivex.rxjava3.internal.operators.parallel.ParallelRunOn.c.run():void");
        }
    }
}
