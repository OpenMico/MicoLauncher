package io.reactivex.rxjava3.internal.operators.parallel;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.exceptions.MissingBackpressureException;
import io.reactivex.rxjava3.internal.fuseable.SimplePlainQueue;
import io.reactivex.rxjava3.internal.queue.SpscArrayQueue;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.AtomicThrowable;
import io.reactivex.rxjava3.internal.util.BackpressureHelper;
import io.reactivex.rxjava3.parallel.ParallelFlowable;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes5.dex */
public final class ParallelJoin<T> extends Flowable<T> {
    final ParallelFlowable<? extends T> b;
    final int c;
    final boolean d;

    public ParallelJoin(ParallelFlowable<? extends T> parallelFlowable, int i, boolean z) {
        this.b = parallelFlowable;
        this.c = i;
        this.d = z;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    protected void subscribeActual(Subscriber<? super T> subscriber) {
        c cVar;
        if (this.d) {
            cVar = new d(subscriber, this.b.parallelism(), this.c);
        } else {
            cVar = new b(subscriber, this.b.parallelism(), this.c);
        }
        subscriber.onSubscribe(cVar);
        this.b.subscribe(cVar.subscribers);
    }

    /* loaded from: classes5.dex */
    static abstract class c<T> extends AtomicInteger implements Subscription {
        private static final long serialVersionUID = 3100232009247827843L;
        volatile boolean cancelled;
        final Subscriber<? super T> downstream;
        final a<T>[] subscribers;
        final AtomicThrowable errors = new AtomicThrowable();
        final AtomicLong requested = new AtomicLong();
        final AtomicInteger done = new AtomicInteger();

        abstract void a();

        abstract void a(a<T> aVar, T t);

        abstract void a(Throwable th);

        abstract void b();

        c(Subscriber<? super T> subscriber, int i, int i2) {
            this.downstream = subscriber;
            a<T>[] aVarArr = new a[i];
            for (int i3 = 0; i3 < i; i3++) {
                aVarArr[i3] = new a<>(this, i2);
            }
            this.subscribers = aVarArr;
            this.done.lazySet(i);
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (SubscriptionHelper.validate(j)) {
                BackpressureHelper.add(this.requested, j);
                b();
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (!this.cancelled) {
                this.cancelled = true;
                d();
                if (getAndIncrement() == 0) {
                    e();
                }
            }
        }

        void d() {
            for (a<T> aVar : this.subscribers) {
                aVar.b();
            }
        }

        void e() {
            for (a<T> aVar : this.subscribers) {
                aVar.queue = null;
            }
        }
    }

    /* loaded from: classes5.dex */
    static final class b<T> extends c<T> {
        private static final long serialVersionUID = 6312374661811000451L;

        b(Subscriber<? super T> subscriber, int i, int i2) {
            super(subscriber, i, i2);
        }

        @Override // io.reactivex.rxjava3.internal.operators.parallel.ParallelJoin.c
        public void a(a<T> aVar, T t) {
            if (get() == 0 && compareAndSet(0, 1)) {
                if (this.requested.get() != 0) {
                    this.downstream.onNext(t);
                    if (this.requested.get() != Long.MAX_VALUE) {
                        this.requested.decrementAndGet();
                    }
                    aVar.a(1L);
                } else if (!aVar.c().offer(t)) {
                    d();
                    MissingBackpressureException missingBackpressureException = new MissingBackpressureException("Queue full?!");
                    if (this.errors.compareAndSet(null, missingBackpressureException)) {
                        this.downstream.onError(missingBackpressureException);
                        return;
                    } else {
                        RxJavaPlugins.onError(missingBackpressureException);
                        return;
                    }
                }
                if (decrementAndGet() == 0) {
                    return;
                }
            } else if (!aVar.c().offer(t)) {
                d();
                a(new MissingBackpressureException("Queue full?!"));
                return;
            } else if (getAndIncrement() != 0) {
                return;
            }
            c();
        }

        @Override // io.reactivex.rxjava3.internal.operators.parallel.ParallelJoin.c
        public void a(Throwable th) {
            if (this.errors.compareAndSet(null, th)) {
                d();
                b();
            } else if (th != this.errors.get()) {
                RxJavaPlugins.onError(th);
            }
        }

        @Override // io.reactivex.rxjava3.internal.operators.parallel.ParallelJoin.c
        public void a() {
            this.done.decrementAndGet();
            b();
        }

        @Override // io.reactivex.rxjava3.internal.operators.parallel.ParallelJoin.c
        void b() {
            if (getAndIncrement() == 0) {
                c();
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:30:0x0060, code lost:
            if (r12 == false) goto L_0x0068;
         */
        /* JADX WARN: Code restructure failed: missing block: B:31:0x0062, code lost:
            if (r11 == false) goto L_0x0068;
         */
        /* JADX WARN: Code restructure failed: missing block: B:32:0x0064, code lost:
            r3.onComplete();
         */
        /* JADX WARN: Code restructure failed: missing block: B:33:0x0067, code lost:
            return;
         */
        /* JADX WARN: Code restructure failed: missing block: B:34:0x0068, code lost:
            if (r11 == false) goto L_0x006c;
         */
        /* JADX WARN: Code restructure failed: missing block: B:35:0x006a, code lost:
            r10 = r14;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        void c() {
            /*
                Method dump skipped, instructions count: 198
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: io.reactivex.rxjava3.internal.operators.parallel.ParallelJoin.b.c():void");
        }
    }

    /* loaded from: classes5.dex */
    static final class d<T> extends c<T> {
        private static final long serialVersionUID = -5737965195918321883L;

        d(Subscriber<? super T> subscriber, int i, int i2) {
            super(subscriber, i, i2);
        }

        @Override // io.reactivex.rxjava3.internal.operators.parallel.ParallelJoin.c
        void a(a<T> aVar, T t) {
            if (get() != 0 || !compareAndSet(0, 1)) {
                if (!aVar.c().offer(t)) {
                    aVar.b();
                    this.errors.tryAddThrowableOrReport(new MissingBackpressureException("Queue full?!"));
                    this.done.decrementAndGet();
                }
                if (getAndIncrement() != 0) {
                    return;
                }
            } else {
                if (this.requested.get() != 0) {
                    this.downstream.onNext(t);
                    if (this.requested.get() != Long.MAX_VALUE) {
                        this.requested.decrementAndGet();
                    }
                    aVar.a(1L);
                } else if (!aVar.c().offer(t)) {
                    aVar.b();
                    this.errors.tryAddThrowableOrReport(new MissingBackpressureException("Queue full?!"));
                    this.done.decrementAndGet();
                    c();
                    return;
                }
                if (decrementAndGet() == 0) {
                    return;
                }
            }
            c();
        }

        @Override // io.reactivex.rxjava3.internal.operators.parallel.ParallelJoin.c
        void a(Throwable th) {
            if (this.errors.tryAddThrowableOrReport(th)) {
                this.done.decrementAndGet();
                b();
            }
        }

        @Override // io.reactivex.rxjava3.internal.operators.parallel.ParallelJoin.c
        void a() {
            this.done.decrementAndGet();
            b();
        }

        @Override // io.reactivex.rxjava3.internal.operators.parallel.ParallelJoin.c
        void b() {
            if (getAndIncrement() == 0) {
                c();
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:25:0x004e, code lost:
            if (r12 == false) goto L_0x0058;
         */
        /* JADX WARN: Code restructure failed: missing block: B:26:0x0050, code lost:
            if (r11 == false) goto L_0x0058;
         */
        /* JADX WARN: Code restructure failed: missing block: B:27:0x0052, code lost:
            r19.errors.tryTerminateConsumer(r3);
         */
        /* JADX WARN: Code restructure failed: missing block: B:28:0x0057, code lost:
            return;
         */
        /* JADX WARN: Code restructure failed: missing block: B:29:0x0058, code lost:
            if (r11 == false) goto L_0x005c;
         */
        /* JADX WARN: Code restructure failed: missing block: B:30:0x005a, code lost:
            r10 = r14;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        void c() {
            /*
                r19 = this;
                r0 = r19
                io.reactivex.rxjava3.internal.operators.parallel.ParallelJoin$a[] r1 = r0.subscribers
                int r2 = r1.length
                org.reactivestreams.Subscriber r3 = r0.downstream
                r5 = 1
            L_0x0008:
                java.util.concurrent.atomic.AtomicLong r6 = r0.requested
                long r6 = r6.get()
                r8 = 0
                r10 = r8
            L_0x0011:
                int r12 = (r10 > r6 ? 1 : (r10 == r6 ? 0 : -1))
                if (r12 == 0) goto L_0x005e
                boolean r12 = r0.cancelled
                if (r12 == 0) goto L_0x001d
                r19.e()
                return
            L_0x001d:
                java.util.concurrent.atomic.AtomicInteger r12 = r0.done
                int r12 = r12.get()
                if (r12 != 0) goto L_0x0027
                r12 = 1
                goto L_0x0028
            L_0x0027:
                r12 = 0
            L_0x0028:
                r14 = r10
                r10 = 0
                r11 = 1
            L_0x002b:
                if (r10 >= r2) goto L_0x004e
                r4 = r1[r10]
                io.reactivex.rxjava3.internal.fuseable.SimplePlainQueue<T> r13 = r4.queue
                if (r13 == 0) goto L_0x004b
                java.lang.Object r13 = r13.poll()
                if (r13 == 0) goto L_0x004b
                r3.onNext(r13)
                r4.a()
                r17 = 1
                long r13 = r14 + r17
                int r4 = (r13 > r6 ? 1 : (r13 == r6 ? 0 : -1))
                if (r4 != 0) goto L_0x0049
                r10 = r13
                goto L_0x005e
            L_0x0049:
                r14 = r13
                r11 = 0
            L_0x004b:
                int r10 = r10 + 1
                goto L_0x002b
            L_0x004e:
                if (r12 == 0) goto L_0x0058
                if (r11 == 0) goto L_0x0058
                io.reactivex.rxjava3.internal.util.AtomicThrowable r1 = r0.errors
                r1.tryTerminateConsumer(r3)
                return
            L_0x0058:
                if (r11 == 0) goto L_0x005c
                r10 = r14
                goto L_0x005e
            L_0x005c:
                r10 = r14
                goto L_0x0011
            L_0x005e:
                int r4 = (r10 > r6 ? 1 : (r10 == r6 ? 0 : -1))
                if (r4 != 0) goto L_0x0096
                boolean r4 = r0.cancelled
                if (r4 == 0) goto L_0x006a
                r19.e()
                return
            L_0x006a:
                java.util.concurrent.atomic.AtomicInteger r4 = r0.done
                int r4 = r4.get()
                if (r4 != 0) goto L_0x0074
                r4 = 1
                goto L_0x0075
            L_0x0074:
                r4 = 0
            L_0x0075:
                r6 = 0
            L_0x0076:
                if (r6 >= r2) goto L_0x008a
                r7 = r1[r6]
                io.reactivex.rxjava3.internal.fuseable.SimplePlainQueue<T> r7 = r7.queue
                if (r7 == 0) goto L_0x0087
                boolean r7 = r7.isEmpty()
                if (r7 != 0) goto L_0x0087
                r16 = 0
                goto L_0x008c
            L_0x0087:
                int r6 = r6 + 1
                goto L_0x0076
            L_0x008a:
                r16 = 1
            L_0x008c:
                if (r4 == 0) goto L_0x0096
                if (r16 == 0) goto L_0x0096
                io.reactivex.rxjava3.internal.util.AtomicThrowable r1 = r0.errors
                r1.tryTerminateConsumer(r3)
                return
            L_0x0096:
                int r4 = (r10 > r8 ? 1 : (r10 == r8 ? 0 : -1))
                if (r4 == 0) goto L_0x009f
                java.util.concurrent.atomic.AtomicLong r4 = r0.requested
                io.reactivex.rxjava3.internal.util.BackpressureHelper.produced(r4, r10)
            L_0x009f:
                int r4 = -r5
                int r5 = r0.addAndGet(r4)
                if (r5 != 0) goto L_0x0008
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: io.reactivex.rxjava3.internal.operators.parallel.ParallelJoin.d.c():void");
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public static final class a<T> extends AtomicReference<Subscription> implements FlowableSubscriber<T> {
        private static final long serialVersionUID = 8410034718427740355L;
        final int limit;
        final c<T> parent;
        final int prefetch;
        long produced;
        volatile SimplePlainQueue<T> queue;

        a(c<T> cVar, int i) {
            this.parent = cVar;
            this.prefetch = i;
            this.limit = i - (i >> 2);
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            SubscriptionHelper.setOnce(this, subscription, this.prefetch);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            this.parent.a(this, t);
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.parent.a(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.parent.a();
        }

        public void a() {
            long j = this.produced + 1;
            if (j == this.limit) {
                this.produced = 0L;
                get().request(j);
                return;
            }
            this.produced = j;
        }

        public void a(long j) {
            long j2 = this.produced + j;
            if (j2 >= this.limit) {
                this.produced = 0L;
                get().request(j2);
                return;
            }
            this.produced = j2;
        }

        public boolean b() {
            return SubscriptionHelper.cancel(this);
        }

        SimplePlainQueue<T> c() {
            SimplePlainQueue<T> simplePlainQueue = this.queue;
            if (simplePlainQueue != null) {
                return simplePlainQueue;
            }
            SpscArrayQueue spscArrayQueue = new SpscArrayQueue(this.prefetch);
            this.queue = spscArrayQueue;
            return spscArrayQueue;
        }
    }
}
