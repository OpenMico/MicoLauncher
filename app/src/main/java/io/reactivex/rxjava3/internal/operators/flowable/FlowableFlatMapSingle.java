package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.core.SingleSource;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.AtomicThrowable;
import io.reactivex.rxjava3.internal.util.BackpressureHelper;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes5.dex */
public final class FlowableFlatMapSingle<T, R> extends a<T, R> {
    final Function<? super T, ? extends SingleSource<? extends R>> b;
    final boolean c;
    final int d;

    public FlowableFlatMapSingle(Flowable<T> flowable, Function<? super T, ? extends SingleSource<? extends R>> function, boolean z, int i) {
        super(flowable);
        this.b = function;
        this.c = z;
        this.d = i;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    protected void subscribeActual(Subscriber<? super R> subscriber) {
        this.source.subscribe((FlowableSubscriber) new a(subscriber, this.b, this.c, this.d));
    }

    /* loaded from: classes5.dex */
    static final class a<T, R> extends AtomicInteger implements FlowableSubscriber<T>, Subscription {
        private static final long serialVersionUID = 8600231336733376951L;
        volatile boolean cancelled;
        final boolean delayErrors;
        final Subscriber<? super R> downstream;
        final Function<? super T, ? extends SingleSource<? extends R>> mapper;
        final int maxConcurrency;
        Subscription upstream;
        final AtomicLong requested = new AtomicLong();
        final CompositeDisposable set = new CompositeDisposable();
        final AtomicThrowable errors = new AtomicThrowable();
        final AtomicInteger active = new AtomicInteger(1);
        final AtomicReference<SpscLinkedArrayQueue<R>> queue = new AtomicReference<>();

        /* JADX INFO: Access modifiers changed from: package-private */
        public a(Subscriber<? super R> subscriber, Function<? super T, ? extends SingleSource<? extends R>> function, boolean z, int i) {
            this.downstream = subscriber;
            this.mapper = function;
            this.delayErrors = z;
            this.maxConcurrency = i;
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.upstream, subscription)) {
                this.upstream = subscription;
                this.downstream.onSubscribe(this);
                int i = this.maxConcurrency;
                if (i == Integer.MAX_VALUE) {
                    subscription.request(Long.MAX_VALUE);
                } else {
                    subscription.request(i);
                }
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            try {
                SingleSource singleSource = (SingleSource) Objects.requireNonNull(this.mapper.apply(t), "The mapper returned a null SingleSource");
                this.active.getAndIncrement();
                C0289a aVar = new C0289a();
                if (!this.cancelled && this.set.add(aVar)) {
                    singleSource.subscribe(aVar);
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.upstream.cancel();
                onError(th);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.active.decrementAndGet();
            if (this.errors.tryAddThrowableOrReport(th)) {
                if (!this.delayErrors) {
                    this.set.dispose();
                }
                b();
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.active.decrementAndGet();
            b();
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.cancelled = true;
            this.upstream.cancel();
            this.set.dispose();
            this.errors.tryTerminateAndReport();
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (SubscriptionHelper.validate(j)) {
                BackpressureHelper.add(this.requested, j);
                b();
            }
        }

        void a(a<T, R>.C0289a aVar, R r) {
            this.set.delete(aVar);
            if (get() == 0) {
                boolean z = true;
                if (compareAndSet(0, 1)) {
                    if (this.active.decrementAndGet() != 0) {
                        z = false;
                    }
                    if (this.requested.get() != 0) {
                        this.downstream.onNext(r);
                        SpscLinkedArrayQueue<R> spscLinkedArrayQueue = this.queue.get();
                        if (!z || (spscLinkedArrayQueue != null && !spscLinkedArrayQueue.isEmpty())) {
                            BackpressureHelper.produced(this.requested, 1L);
                            if (this.maxConcurrency != Integer.MAX_VALUE) {
                                this.upstream.request(1L);
                            }
                        } else {
                            this.errors.tryTerminateConsumer(this.downstream);
                            return;
                        }
                    } else {
                        SpscLinkedArrayQueue<R> a = a();
                        synchronized (a) {
                            a.offer(r);
                        }
                    }
                    if (decrementAndGet() == 0) {
                        return;
                    }
                    d();
                }
            }
            SpscLinkedArrayQueue<R> a2 = a();
            synchronized (a2) {
                a2.offer(r);
            }
            this.active.decrementAndGet();
            if (getAndIncrement() != 0) {
                return;
            }
            d();
        }

        SpscLinkedArrayQueue<R> a() {
            SpscLinkedArrayQueue<R> spscLinkedArrayQueue = this.queue.get();
            if (spscLinkedArrayQueue != null) {
                return spscLinkedArrayQueue;
            }
            SpscLinkedArrayQueue<R> spscLinkedArrayQueue2 = new SpscLinkedArrayQueue<>(Flowable.bufferSize());
            return this.queue.compareAndSet(null, spscLinkedArrayQueue2) ? spscLinkedArrayQueue2 : this.queue.get();
        }

        void a(a<T, R>.C0289a aVar, Throwable th) {
            this.set.delete(aVar);
            if (this.errors.tryAddThrowableOrReport(th)) {
                if (!this.delayErrors) {
                    this.upstream.cancel();
                    this.set.dispose();
                } else if (this.maxConcurrency != Integer.MAX_VALUE) {
                    this.upstream.request(1L);
                }
                this.active.decrementAndGet();
                b();
            }
        }

        void b() {
            if (getAndIncrement() == 0) {
                d();
            }
        }

        void c() {
            SpscLinkedArrayQueue<R> spscLinkedArrayQueue = this.queue.get();
            if (spscLinkedArrayQueue != null) {
                spscLinkedArrayQueue.clear();
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:33:0x006b, code lost:
            if (r12 != 0) goto L_0x00ae;
         */
        /* JADX WARN: Code restructure failed: missing block: B:35:0x006f, code lost:
            if (r17.cancelled == false) goto L_0x0075;
         */
        /* JADX WARN: Code restructure failed: missing block: B:36:0x0071, code lost:
            c();
         */
        /* JADX WARN: Code restructure failed: missing block: B:37:0x0074, code lost:
            return;
         */
        /* JADX WARN: Code restructure failed: missing block: B:39:0x0077, code lost:
            if (r17.delayErrors != false) goto L_0x008c;
         */
        /* JADX WARN: Code restructure failed: missing block: B:41:0x0081, code lost:
            if (r17.errors.get() == null) goto L_0x008c;
         */
        /* JADX WARN: Code restructure failed: missing block: B:42:0x0083, code lost:
            c();
            r17.errors.tryTerminateConsumer(r1);
         */
        /* JADX WARN: Code restructure failed: missing block: B:43:0x008b, code lost:
            return;
         */
        /* JADX WARN: Code restructure failed: missing block: B:45:0x0090, code lost:
            if (r2.get() != 0) goto L_0x0094;
         */
        /* JADX WARN: Code restructure failed: missing block: B:46:0x0092, code lost:
            r6 = true;
         */
        /* JADX WARN: Code restructure failed: missing block: B:47:0x0094, code lost:
            r6 = false;
         */
        /* JADX WARN: Code restructure failed: missing block: B:48:0x0095, code lost:
            r7 = r3.get();
         */
        /* JADX WARN: Code restructure failed: missing block: B:49:0x009b, code lost:
            if (r7 == null) goto L_0x00a3;
         */
        /* JADX WARN: Code restructure failed: missing block: B:51:0x00a1, code lost:
            if (r7.isEmpty() == false) goto L_0x00a4;
         */
        /* JADX WARN: Code restructure failed: missing block: B:52:0x00a3, code lost:
            r13 = true;
         */
        /* JADX WARN: Code restructure failed: missing block: B:53:0x00a4, code lost:
            if (r6 == false) goto L_0x00ae;
         */
        /* JADX WARN: Code restructure failed: missing block: B:54:0x00a6, code lost:
            if (r13 == false) goto L_0x00ae;
         */
        /* JADX WARN: Code restructure failed: missing block: B:55:0x00a8, code lost:
            r17.errors.tryTerminateConsumer(r1);
         */
        /* JADX WARN: Code restructure failed: missing block: B:56:0x00ad, code lost:
            return;
         */
        /* JADX WARN: Code restructure failed: missing block: B:58:0x00b0, code lost:
            if (r10 == 0) goto L_0x00c3;
         */
        /* JADX WARN: Code restructure failed: missing block: B:59:0x00b2, code lost:
            io.reactivex.rxjava3.internal.util.BackpressureHelper.produced(r17.requested, r10);
         */
        /* JADX WARN: Code restructure failed: missing block: B:60:0x00bc, code lost:
            if (r17.maxConcurrency == Integer.MAX_VALUE) goto L_0x00c3;
         */
        /* JADX WARN: Code restructure failed: missing block: B:61:0x00be, code lost:
            r17.upstream.request(r10);
         */
        /* JADX WARN: Code restructure failed: missing block: B:62:0x00c3, code lost:
            r5 = addAndGet(-r5);
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        void d() {
            /*
                r17 = this;
                r0 = r17
                org.reactivestreams.Subscriber<? super R> r1 = r0.downstream
                java.util.concurrent.atomic.AtomicInteger r2 = r0.active
                java.util.concurrent.atomic.AtomicReference<io.reactivex.rxjava3.internal.queue.SpscLinkedArrayQueue<R>> r3 = r0.queue
                r4 = 1
                r5 = r4
            L_0x000a:
                java.util.concurrent.atomic.AtomicLong r6 = r0.requested
                long r6 = r6.get()
                r8 = 0
                r10 = r8
            L_0x0013:
                int r12 = (r10 > r6 ? 1 : (r10 == r6 ? 0 : -1))
                r13 = 0
                if (r12 == 0) goto L_0x006b
                boolean r14 = r0.cancelled
                if (r14 == 0) goto L_0x0020
                r17.c()
                return
            L_0x0020:
                boolean r14 = r0.delayErrors
                if (r14 != 0) goto L_0x0039
                io.reactivex.rxjava3.internal.util.AtomicThrowable r14 = r0.errors
                java.lang.Object r14 = r14.get()
                java.lang.Throwable r14 = (java.lang.Throwable) r14
                if (r14 == 0) goto L_0x0039
                r17.c()
                io.reactivex.rxjava3.internal.util.AtomicThrowable r1 = r0.errors
                org.reactivestreams.Subscriber<? super R> r2 = r0.downstream
                r1.tryTerminateConsumer(r2)
                return
            L_0x0039:
                int r14 = r2.get()
                if (r14 != 0) goto L_0x0041
                r14 = r4
                goto L_0x0042
            L_0x0041:
                r14 = r13
            L_0x0042:
                java.lang.Object r15 = r3.get()
                io.reactivex.rxjava3.internal.queue.SpscLinkedArrayQueue r15 = (io.reactivex.rxjava3.internal.queue.SpscLinkedArrayQueue) r15
                if (r15 == 0) goto L_0x004f
                java.lang.Object r15 = r15.poll()
                goto L_0x0050
            L_0x004f:
                r15 = 0
            L_0x0050:
                if (r15 != 0) goto L_0x0055
                r16 = r4
                goto L_0x0057
            L_0x0055:
                r16 = r13
            L_0x0057:
                if (r14 == 0) goto L_0x0061
                if (r16 == 0) goto L_0x0061
                io.reactivex.rxjava3.internal.util.AtomicThrowable r2 = r0.errors
                r2.tryTerminateConsumer(r1)
                return
            L_0x0061:
                if (r16 == 0) goto L_0x0064
                goto L_0x006b
            L_0x0064:
                r1.onNext(r15)
                r12 = 1
                long r10 = r10 + r12
                goto L_0x0013
            L_0x006b:
                if (r12 != 0) goto L_0x00ae
                boolean r6 = r0.cancelled
                if (r6 == 0) goto L_0x0075
                r17.c()
                return
            L_0x0075:
                boolean r6 = r0.delayErrors
                if (r6 != 0) goto L_0x008c
                io.reactivex.rxjava3.internal.util.AtomicThrowable r6 = r0.errors
                java.lang.Object r6 = r6.get()
                java.lang.Throwable r6 = (java.lang.Throwable) r6
                if (r6 == 0) goto L_0x008c
                r17.c()
                io.reactivex.rxjava3.internal.util.AtomicThrowable r2 = r0.errors
                r2.tryTerminateConsumer(r1)
                return
            L_0x008c:
                int r6 = r2.get()
                if (r6 != 0) goto L_0x0094
                r6 = r4
                goto L_0x0095
            L_0x0094:
                r6 = r13
            L_0x0095:
                java.lang.Object r7 = r3.get()
                io.reactivex.rxjava3.internal.queue.SpscLinkedArrayQueue r7 = (io.reactivex.rxjava3.internal.queue.SpscLinkedArrayQueue) r7
                if (r7 == 0) goto L_0x00a3
                boolean r7 = r7.isEmpty()
                if (r7 == 0) goto L_0x00a4
            L_0x00a3:
                r13 = r4
            L_0x00a4:
                if (r6 == 0) goto L_0x00ae
                if (r13 == 0) goto L_0x00ae
                io.reactivex.rxjava3.internal.util.AtomicThrowable r2 = r0.errors
                r2.tryTerminateConsumer(r1)
                return
            L_0x00ae:
                int r6 = (r10 > r8 ? 1 : (r10 == r8 ? 0 : -1))
                if (r6 == 0) goto L_0x00c3
                java.util.concurrent.atomic.AtomicLong r6 = r0.requested
                io.reactivex.rxjava3.internal.util.BackpressureHelper.produced(r6, r10)
                int r6 = r0.maxConcurrency
                r7 = 2147483647(0x7fffffff, float:NaN)
                if (r6 == r7) goto L_0x00c3
                org.reactivestreams.Subscription r6 = r0.upstream
                r6.request(r10)
            L_0x00c3:
                int r5 = -r5
                int r5 = r0.addAndGet(r5)
                if (r5 != 0) goto L_0x000a
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: io.reactivex.rxjava3.internal.operators.flowable.FlowableFlatMapSingle.a.d():void");
        }

        /* renamed from: io.reactivex.rxjava3.internal.operators.flowable.FlowableFlatMapSingle$a$a  reason: collision with other inner class name */
        /* loaded from: classes5.dex */
        final class C0289a extends AtomicReference<Disposable> implements SingleObserver<R>, Disposable {
            private static final long serialVersionUID = -502562646270949838L;

            C0289a() {
            }

            @Override // io.reactivex.rxjava3.core.SingleObserver
            public void onSubscribe(Disposable disposable) {
                DisposableHelper.setOnce(this, disposable);
            }

            @Override // io.reactivex.rxjava3.core.SingleObserver
            public void onSuccess(R r) {
                a.this.a((a<T, C0289a>.C0289a) this, (C0289a) r);
            }

            @Override // io.reactivex.rxjava3.core.SingleObserver
            public void onError(Throwable th) {
                a.this.a(this, th);
            }

            @Override // io.reactivex.rxjava3.disposables.Disposable
            public boolean isDisposed() {
                return DisposableHelper.isDisposed(get());
            }

            @Override // io.reactivex.rxjava3.disposables.Disposable
            public void dispose() {
                DisposableHelper.dispose(this);
            }
        }
    }
}
