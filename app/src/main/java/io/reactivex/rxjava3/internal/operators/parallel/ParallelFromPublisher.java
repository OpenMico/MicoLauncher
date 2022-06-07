package io.reactivex.rxjava3.internal.operators.parallel;

import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.exceptions.MissingBackpressureException;
import io.reactivex.rxjava3.internal.fuseable.QueueSubscription;
import io.reactivex.rxjava3.internal.fuseable.SimpleQueue;
import io.reactivex.rxjava3.internal.queue.SpscArrayQueue;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.BackpressureHelper;
import io.reactivex.rxjava3.parallel.ParallelFlowable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLongArray;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes5.dex */
public final class ParallelFromPublisher<T> extends ParallelFlowable<T> {
    final Publisher<? extends T> a;
    final int b;
    final int c;

    public ParallelFromPublisher(Publisher<? extends T> publisher, int i, int i2) {
        this.a = publisher;
        this.b = i;
        this.c = i2;
    }

    @Override // io.reactivex.rxjava3.parallel.ParallelFlowable
    public int parallelism() {
        return this.b;
    }

    @Override // io.reactivex.rxjava3.parallel.ParallelFlowable, autodispose2.ParallelFlowableSubscribeProxy
    public void subscribe(Subscriber<? super T>[] subscriberArr) {
        if (validate(subscriberArr)) {
            this.a.subscribe(new a(subscriberArr, this.c));
        }
    }

    /* loaded from: classes5.dex */
    static final class a<T> extends AtomicInteger implements FlowableSubscriber<T> {
        private static final long serialVersionUID = -4470634016609963609L;
        volatile boolean cancelled;
        volatile boolean done;
        final long[] emissions;
        Throwable error;
        int index;
        final int limit;
        final int prefetch;
        int produced;
        SimpleQueue<T> queue;
        final AtomicLongArray requests;
        int sourceMode;
        final AtomicInteger subscriberCount = new AtomicInteger();
        final Subscriber<? super T>[] subscribers;
        Subscription upstream;

        a(Subscriber<? super T>[] subscriberArr, int i) {
            this.subscribers = subscriberArr;
            this.prefetch = i;
            this.limit = i - (i >> 2);
            int length = subscriberArr.length;
            int i2 = length + length;
            this.requests = new AtomicLongArray(i2 + 1);
            this.requests.lazySet(i2, length);
            this.emissions = new long[length];
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.upstream, subscription)) {
                this.upstream = subscription;
                if (subscription instanceof QueueSubscription) {
                    QueueSubscription queueSubscription = (QueueSubscription) subscription;
                    int requestFusion = queueSubscription.requestFusion(7);
                    if (requestFusion == 1) {
                        this.sourceMode = requestFusion;
                        this.queue = queueSubscription;
                        this.done = true;
                        a();
                        d();
                        return;
                    } else if (requestFusion == 2) {
                        this.sourceMode = requestFusion;
                        this.queue = queueSubscription;
                        a();
                        subscription.request(this.prefetch);
                        return;
                    }
                }
                this.queue = new SpscArrayQueue(this.prefetch);
                a();
                subscription.request(this.prefetch);
            }
        }

        void a() {
            Subscriber<? super T>[] subscriberArr = this.subscribers;
            int length = subscriberArr.length;
            int i = 0;
            while (i < length) {
                int i2 = i + 1;
                this.subscriberCount.lazySet(i2);
                subscriberArr[i].onSubscribe(new C0337a(i, length));
                i = i2;
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: io.reactivex.rxjava3.internal.operators.parallel.ParallelFromPublisher$a$a  reason: collision with other inner class name */
        /* loaded from: classes5.dex */
        public final class C0337a implements Subscription {
            final int a;
            final int b;

            C0337a(int i, int i2) {
                this.a = i;
                this.b = i2;
            }

            @Override // org.reactivestreams.Subscription
            public void request(long j) {
                long j2;
                if (SubscriptionHelper.validate(j)) {
                    AtomicLongArray atomicLongArray = a.this.requests;
                    do {
                        j2 = atomicLongArray.get(this.a);
                        if (j2 != Long.MAX_VALUE) {
                        } else {
                            return;
                        }
                    } while (!atomicLongArray.compareAndSet(this.a, j2, BackpressureHelper.addCap(j2, j)));
                    if (a.this.subscriberCount.get() == this.b) {
                        a.this.d();
                    }
                }
            }

            @Override // org.reactivestreams.Subscription
            public void cancel() {
                if (a.this.requests.compareAndSet(this.a + this.b, 0L, 1L)) {
                    a aVar = a.this;
                    int i = this.b;
                    aVar.a(i + i);
                }
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (this.sourceMode != 0 || this.queue.offer(t)) {
                d();
                return;
            }
            this.upstream.cancel();
            onError(new MissingBackpressureException("Queue is full?"));
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.error = th;
            this.done = true;
            d();
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.done = true;
            d();
        }

        void a(int i) {
            if (this.requests.decrementAndGet(i) == 0) {
                this.cancelled = true;
                this.upstream.cancel();
                if (getAndIncrement() == 0) {
                    this.queue.clear();
                }
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:56:0x00b3 A[SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:59:0x00a7 A[SYNTHETIC] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        void b() {
            /*
                r18 = this;
                r1 = r18
                io.reactivex.rxjava3.internal.fuseable.SimpleQueue<T> r0 = r1.queue
                org.reactivestreams.Subscriber<? super T>[] r2 = r1.subscribers
                java.util.concurrent.atomic.AtomicLongArray r3 = r1.requests
                long[] r4 = r1.emissions
                int r5 = r4.length
                int r6 = r1.index
                int r7 = r1.produced
                r8 = 1
                r9 = r8
            L_0x0011:
                r10 = 0
                r11 = r7
                r7 = r10
            L_0x0014:
                boolean r12 = r1.cancelled
                if (r12 == 0) goto L_0x001c
                r0.clear()
                return
            L_0x001c:
                boolean r12 = r1.done
                if (r12 == 0) goto L_0x0033
                java.lang.Throwable r13 = r1.error
                if (r13 == 0) goto L_0x0033
                r0.clear()
                int r0 = r2.length
            L_0x0028:
                if (r10 >= r0) goto L_0x0032
                r3 = r2[r10]
                r3.onError(r13)
                int r10 = r10 + 1
                goto L_0x0028
            L_0x0032:
                return
            L_0x0033:
                boolean r13 = r0.isEmpty()
                if (r12 == 0) goto L_0x0047
                if (r13 == 0) goto L_0x0047
                int r0 = r2.length
            L_0x003c:
                if (r10 >= r0) goto L_0x0046
                r3 = r2[r10]
                r3.onComplete()
                int r10 = r10 + 1
                goto L_0x003c
            L_0x0046:
                return
            L_0x0047:
                if (r13 == 0) goto L_0x004a
                goto L_0x0066
            L_0x004a:
                long r12 = r3.get(r6)
                r14 = r4[r6]
                int r12 = (r12 > r14 ? 1 : (r12 == r14 ? 0 : -1))
                if (r12 == 0) goto L_0x0098
                int r12 = r5 + r6
                long r12 = r3.get(r12)
                r16 = 0
                int r12 = (r12 > r16 ? 1 : (r12 == r16 ? 0 : -1))
                if (r12 != 0) goto L_0x0098
                java.lang.Object r7 = r0.poll()     // Catch: Throwable -> 0x0082
                if (r7 != 0) goto L_0x0068
            L_0x0066:
                r7 = r11
                goto L_0x00a1
            L_0x0068:
                r12 = r2[r6]
                r12.onNext(r7)
                r12 = 1
                long r14 = r14 + r12
                r4[r6] = r14
                int r7 = r11 + 1
                int r11 = r1.limit
                if (r7 != r11) goto L_0x007f
                org.reactivestreams.Subscription r11 = r1.upstream
                long r12 = (long) r7
                r11.request(r12)
                r7 = r10
            L_0x007f:
                r11 = r7
                r7 = r10
                goto L_0x0099
            L_0x0082:
                r0 = move-exception
                r3 = r0
                io.reactivex.rxjava3.exceptions.Exceptions.throwIfFatal(r3)
                org.reactivestreams.Subscription r0 = r1.upstream
                r0.cancel()
                int r0 = r2.length
            L_0x008d:
                if (r10 >= r0) goto L_0x0097
                r4 = r2[r10]
                r4.onError(r3)
                int r10 = r10 + 1
                goto L_0x008d
            L_0x0097:
                return
            L_0x0098:
                int r7 = r7 + r8
            L_0x0099:
                int r6 = r6 + 1
                if (r6 != r5) goto L_0x009e
                r6 = r10
            L_0x009e:
                if (r7 != r5) goto L_0x0014
                r7 = r11
            L_0x00a1:
                int r10 = r18.get()
                if (r10 != r9) goto L_0x00b3
                r1.index = r6
                r1.produced = r7
                int r9 = -r9
                int r9 = r1.addAndGet(r9)
                if (r9 != 0) goto L_0x0011
                return
            L_0x00b3:
                r9 = r10
                goto L_0x0011
            */
            throw new UnsupportedOperationException("Method not decompiled: io.reactivex.rxjava3.internal.operators.parallel.ParallelFromPublisher.a.b():void");
        }

        void c() {
            SimpleQueue<T> simpleQueue = this.queue;
            Subscriber<? super T>[] subscriberArr = this.subscribers;
            AtomicLongArray atomicLongArray = this.requests;
            long[] jArr = this.emissions;
            int length = jArr.length;
            int i = this.index;
            int i2 = 1;
            while (true) {
                int i3 = 0;
                int i4 = 0;
                while (!this.cancelled) {
                    if (simpleQueue.isEmpty()) {
                        int length2 = subscriberArr.length;
                        while (i3 < length2) {
                            subscriberArr[i3].onComplete();
                            i3++;
                        }
                        return;
                    }
                    long j = atomicLongArray.get(i);
                    long j2 = jArr[i];
                    if (j == j2 || atomicLongArray.get(length + i) != 0) {
                        i4++;
                    } else {
                        try {
                            T poll = simpleQueue.poll();
                            if (poll == null) {
                                int length3 = subscriberArr.length;
                                while (i3 < length3) {
                                    subscriberArr[i3].onComplete();
                                    i3++;
                                }
                                return;
                            }
                            subscriberArr[i].onNext(poll);
                            jArr[i] = j2 + 1;
                            i4 = 0;
                        } catch (Throwable th) {
                            Exceptions.throwIfFatal(th);
                            this.upstream.cancel();
                            int length4 = subscriberArr.length;
                            while (i3 < length4) {
                                subscriberArr[i3].onError(th);
                                i3++;
                            }
                            return;
                        }
                    }
                    i++;
                    if (i == length) {
                        i = 0;
                        continue;
                    }
                    if (i4 == length) {
                        int i5 = get();
                        if (i5 == i2) {
                            this.index = i;
                            i2 = addAndGet(-i2);
                            if (i2 == 0) {
                                return;
                            }
                        } else {
                            i2 = i5;
                        }
                    }
                }
                simpleQueue.clear();
                return;
            }
        }

        void d() {
            if (getAndIncrement() == 0) {
                if (this.sourceMode == 1) {
                    c();
                } else {
                    b();
                }
            }
        }
    }
}
