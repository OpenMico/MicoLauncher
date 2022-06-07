package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.exceptions.MissingBackpressureException;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.functions.Supplier;
import io.reactivex.rxjava3.internal.fuseable.QueueSubscription;
import io.reactivex.rxjava3.internal.fuseable.SimplePlainQueue;
import io.reactivex.rxjava3.internal.fuseable.SimpleQueue;
import io.reactivex.rxjava3.internal.queue.SpscArrayQueue;
import io.reactivex.rxjava3.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.AtomicThrowable;
import io.reactivex.rxjava3.internal.util.BackpressureHelper;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes5.dex */
public final class FlowableFlatMap<T, U> extends a<T, U> {
    final Function<? super T, ? extends Publisher<? extends U>> b;
    final boolean c;
    final int d;
    final int e;

    public FlowableFlatMap(Flowable<T> flowable, Function<? super T, ? extends Publisher<? extends U>> function, boolean z, int i, int i2) {
        super(flowable);
        this.b = function;
        this.c = z;
        this.d = i;
        this.e = i2;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    protected void subscribeActual(Subscriber<? super U> subscriber) {
        if (!FlowableScalarXMap.tryScalarXMapSubscribe(this.source, subscriber, this.b)) {
            this.source.subscribe((FlowableSubscriber) subscribe(subscriber, this.b, this.c, this.d, this.e));
        }
    }

    public static <T, U> FlowableSubscriber<T> subscribe(Subscriber<? super U> subscriber, Function<? super T, ? extends Publisher<? extends U>> function, boolean z, int i, int i2) {
        return new b(subscriber, function, z, i, i2);
    }

    /* loaded from: classes5.dex */
    public static final class b<T, U> extends AtomicInteger implements FlowableSubscriber<T>, Subscription {
        static final a<?, ?>[] a = new a[0];
        static final a<?, ?>[] b = new a[0];
        private static final long serialVersionUID = -2117620485640801370L;
        final int bufferSize;
        volatile boolean cancelled;
        final boolean delayErrors;
        volatile boolean done;
        final Subscriber<? super U> downstream;
        long lastId;
        int lastIndex;
        final Function<? super T, ? extends Publisher<? extends U>> mapper;
        final int maxConcurrency;
        volatile SimplePlainQueue<U> queue;
        int scalarEmitted;
        final int scalarLimit;
        long uniqueId;
        Subscription upstream;
        final AtomicThrowable errors = new AtomicThrowable();
        final AtomicReference<a<?, ?>[]> subscribers = new AtomicReference<>();
        final AtomicLong requested = new AtomicLong();

        b(Subscriber<? super U> subscriber, Function<? super T, ? extends Publisher<? extends U>> function, boolean z, int i, int i2) {
            this.downstream = subscriber;
            this.mapper = function;
            this.delayErrors = z;
            this.maxConcurrency = i;
            this.bufferSize = i2;
            this.scalarLimit = Math.max(1, i >> 1);
            this.subscribers.lazySet(a);
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.upstream, subscription)) {
                this.upstream = subscription;
                this.downstream.onSubscribe(this);
                if (!this.cancelled) {
                    int i = this.maxConcurrency;
                    if (i == Integer.MAX_VALUE) {
                        subscription.request(Long.MAX_VALUE);
                    } else {
                        subscription.request(i);
                    }
                }
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (!this.done) {
                try {
                    Publisher publisher = (Publisher) Objects.requireNonNull(this.mapper.apply(t), "The mapper returned a null Publisher");
                    if (publisher instanceof Supplier) {
                        try {
                            Object obj = ((Supplier) publisher).get();
                            if (obj != null) {
                                a((b<T, U>) obj);
                            } else if (this.maxConcurrency != Integer.MAX_VALUE && !this.cancelled) {
                                int i = this.scalarEmitted + 1;
                                this.scalarEmitted = i;
                                int i2 = this.scalarLimit;
                                if (i == i2) {
                                    this.scalarEmitted = 0;
                                    this.upstream.request(i2);
                                }
                            }
                        } catch (Throwable th) {
                            Exceptions.throwIfFatal(th);
                            this.errors.tryAddThrowableOrReport(th);
                            b();
                        }
                    } else {
                        int i3 = this.bufferSize;
                        long j = this.uniqueId;
                        this.uniqueId = 1 + j;
                        a aVar = new a(this, i3, j);
                        if (a(aVar)) {
                            publisher.subscribe(aVar);
                        }
                    }
                } catch (Throwable th2) {
                    Exceptions.throwIfFatal(th2);
                    this.upstream.cancel();
                    onError(th2);
                }
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        boolean a(a<T, U> aVar) {
            a<?, ?>[] aVarArr;
            a[] aVarArr2;
            do {
                aVarArr = this.subscribers.get();
                if (aVarArr == b) {
                    aVar.dispose();
                    return false;
                }
                int length = aVarArr.length;
                aVarArr2 = new a[length + 1];
                System.arraycopy(aVarArr, 0, aVarArr2, 0, length);
                aVarArr2[length] = aVar;
            } while (!this.subscribers.compareAndSet(aVarArr, aVarArr2));
            return true;
        }

        void b(a<T, U> aVar) {
            a<?, ?>[] aVarArr;
            a<?, ?>[] aVarArr2;
            do {
                aVarArr = this.subscribers.get();
                int length = aVarArr.length;
                if (length != 0) {
                    int i = -1;
                    int i2 = 0;
                    while (true) {
                        if (i2 >= length) {
                            break;
                        } else if (aVarArr[i2] == aVar) {
                            i = i2;
                            break;
                        } else {
                            i2++;
                        }
                    }
                    if (i >= 0) {
                        if (length == 1) {
                            aVarArr2 = a;
                        } else {
                            a<?, ?>[] aVarArr3 = new a[length - 1];
                            System.arraycopy(aVarArr, 0, aVarArr3, 0, i);
                            System.arraycopy(aVarArr, i + 1, aVarArr3, i, (length - i) - 1);
                            aVarArr2 = aVarArr3;
                        }
                    } else {
                        return;
                    }
                } else {
                    return;
                }
            } while (!this.subscribers.compareAndSet(aVarArr, aVarArr2));
        }

        SimpleQueue<U> a() {
            SimplePlainQueue<U> simplePlainQueue = this.queue;
            if (simplePlainQueue == null) {
                int i = this.maxConcurrency;
                if (i == Integer.MAX_VALUE) {
                    simplePlainQueue = new SpscLinkedArrayQueue<>(this.bufferSize);
                } else {
                    simplePlainQueue = new SpscArrayQueue(i);
                }
                this.queue = simplePlainQueue;
            }
            return simplePlainQueue;
        }

        void a(U u) {
            if (get() == 0 && compareAndSet(0, 1)) {
                long j = this.requested.get();
                SimpleQueue<U> simpleQueue = this.queue;
                if (j == 0 || (simpleQueue != null && !simpleQueue.isEmpty())) {
                    if (simpleQueue == null) {
                        simpleQueue = a();
                    }
                    if (!simpleQueue.offer(u)) {
                        onError(new MissingBackpressureException("Scalar queue full?!"));
                    }
                } else {
                    this.downstream.onNext(u);
                    if (j != Long.MAX_VALUE) {
                        this.requested.decrementAndGet();
                    }
                    if (this.maxConcurrency != Integer.MAX_VALUE && !this.cancelled) {
                        int i = this.scalarEmitted + 1;
                        this.scalarEmitted = i;
                        int i2 = this.scalarLimit;
                        if (i == i2) {
                            this.scalarEmitted = 0;
                            this.upstream.request(i2);
                        }
                    }
                }
                if (decrementAndGet() == 0) {
                    return;
                }
            } else if (!a().offer(u)) {
                onError(new MissingBackpressureException("Scalar queue full?!"));
                return;
            } else if (getAndIncrement() != 0) {
                return;
            }
            c();
        }

        void a(U u, a<T, U> aVar) {
            if (get() != 0 || !compareAndSet(0, 1)) {
                SimpleQueue simpleQueue = aVar.queue;
                if (simpleQueue == null) {
                    simpleQueue = new SpscArrayQueue(this.bufferSize);
                    aVar.queue = simpleQueue;
                }
                if (!simpleQueue.offer(u)) {
                    onError(new MissingBackpressureException("Inner queue full?!"));
                    return;
                } else if (getAndIncrement() != 0) {
                    return;
                }
            } else {
                long j = this.requested.get();
                SimpleQueue simpleQueue2 = aVar.queue;
                if (j == 0 || (simpleQueue2 != null && !simpleQueue2.isEmpty())) {
                    if (simpleQueue2 == null) {
                        simpleQueue2 = new SpscArrayQueue(this.bufferSize);
                        aVar.queue = simpleQueue2;
                    }
                    if (!simpleQueue2.offer(u)) {
                        onError(new MissingBackpressureException("Inner queue full?!"));
                    }
                } else {
                    this.downstream.onNext(u);
                    if (j != Long.MAX_VALUE) {
                        this.requested.decrementAndGet();
                    }
                    aVar.a(1L);
                }
                if (decrementAndGet() == 0) {
                    return;
                }
            }
            c();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.done) {
                RxJavaPlugins.onError(th);
            } else if (this.errors.tryAddThrowableOrReport(th)) {
                this.done = true;
                if (!this.delayErrors) {
                    for (a<?, ?> aVar : this.subscribers.getAndSet(b)) {
                        aVar.dispose();
                    }
                }
                b();
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (!this.done) {
                this.done = true;
                b();
            }
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
            SimplePlainQueue<U> simplePlainQueue;
            if (!this.cancelled) {
                this.cancelled = true;
                this.upstream.cancel();
                f();
                if (getAndIncrement() == 0 && (simplePlainQueue = this.queue) != null) {
                    simplePlainQueue.clear();
                }
            }
        }

        void b() {
            if (getAndIncrement() == 0) {
                c();
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:79:0x0117, code lost:
            if (r10 == r12) goto L_0x012e;
         */
        /* JADX WARN: Code restructure failed: missing block: B:80:0x0119, code lost:
            if (r9 != false) goto L_0x0123;
         */
        /* JADX WARN: Code restructure failed: missing block: B:81:0x011b, code lost:
            r5 = r24.requested.addAndGet(-r10);
         */
        /* JADX WARN: Code restructure failed: missing block: B:82:0x0123, code lost:
            r5 = Long.MAX_VALUE;
         */
        /* JADX WARN: Code restructure failed: missing block: B:83:0x0128, code lost:
            r7.a(r10);
            r10 = 0;
         */
        /* JADX WARN: Code restructure failed: missing block: B:84:0x012e, code lost:
            r10 = r12;
         */
        /* JADX WARN: Code restructure failed: missing block: B:86:0x0131, code lost:
            if (r5 == r10) goto L_0x013e;
         */
        /* JADX WARN: Code restructure failed: missing block: B:87:0x0133, code lost:
            if (r22 != null) goto L_0x0136;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        void c() {
            /*
                Method dump skipped, instructions count: 428
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: io.reactivex.rxjava3.internal.operators.flowable.FlowableFlatMap.b.c():void");
        }

        boolean d() {
            if (this.cancelled) {
                e();
                return true;
            } else if (this.delayErrors || this.errors.get() == null) {
                return false;
            } else {
                e();
                this.errors.tryTerminateConsumer(this.downstream);
                return true;
            }
        }

        void e() {
            SimplePlainQueue<U> simplePlainQueue = this.queue;
            if (simplePlainQueue != null) {
                simplePlainQueue.clear();
            }
        }

        void f() {
            a<?, ?>[] andSet = this.subscribers.getAndSet(b);
            if (andSet != b) {
                for (a<?, ?> aVar : andSet) {
                    aVar.dispose();
                }
                this.errors.tryTerminateAndReport();
            }
        }

        void a(a<T, U> aVar, Throwable th) {
            if (this.errors.tryAddThrowableOrReport(th)) {
                aVar.done = true;
                if (!this.delayErrors) {
                    this.upstream.cancel();
                    for (a<?, ?> aVar2 : this.subscribers.getAndSet(b)) {
                        aVar2.dispose();
                    }
                }
                b();
            }
        }
    }

    /* loaded from: classes5.dex */
    public static final class a<T, U> extends AtomicReference<Subscription> implements FlowableSubscriber<U>, Disposable {
        private static final long serialVersionUID = -4606175640614850599L;
        final int bufferSize;
        volatile boolean done;
        int fusionMode;
        final long id;
        final int limit;
        final b<T, U> parent;
        long produced;
        volatile SimpleQueue<U> queue;

        a(b<T, U> bVar, int i, long j) {
            this.id = j;
            this.parent = bVar;
            this.bufferSize = i;
            this.limit = i >> 2;
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.setOnce(this, subscription)) {
                if (subscription instanceof QueueSubscription) {
                    QueueSubscription queueSubscription = (QueueSubscription) subscription;
                    int requestFusion = queueSubscription.requestFusion(7);
                    if (requestFusion == 1) {
                        this.fusionMode = requestFusion;
                        this.queue = queueSubscription;
                        this.done = true;
                        this.parent.b();
                        return;
                    } else if (requestFusion == 2) {
                        this.fusionMode = requestFusion;
                        this.queue = queueSubscription;
                    }
                }
                subscription.request(this.bufferSize);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(U u) {
            if (this.fusionMode != 2) {
                this.parent.a((b<T, U>) u, (a<T, b<T, U>>) this);
            } else {
                this.parent.b();
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            lazySet(SubscriptionHelper.CANCELLED);
            this.parent.a(this, th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.done = true;
            this.parent.b();
        }

        void a(long j) {
            if (this.fusionMode != 1) {
                long j2 = this.produced + j;
                if (j2 >= this.limit) {
                    this.produced = 0L;
                    get().request(j2);
                    return;
                }
                this.produced = j2;
            }
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            SubscriptionHelper.cancel(this);
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return get() == SubscriptionHelper.CANCELLED;
        }
    }
}
