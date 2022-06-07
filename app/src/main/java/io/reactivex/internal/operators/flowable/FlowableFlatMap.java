package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.QueueSubscription;
import io.reactivex.internal.fuseable.SimplePlainQueue;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.queue.SpscArrayQueue;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes4.dex */
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

    @Override // io.reactivex.Flowable
    protected void subscribeActual(Subscriber<? super U> subscriber) {
        if (!FlowableScalarXMap.tryScalarXMapSubscribe(this.source, subscriber, this.b)) {
            this.source.subscribe((FlowableSubscriber) subscribe(subscriber, this.b, this.c, this.d, this.e));
        }
    }

    public static <T, U> FlowableSubscriber<T> subscribe(Subscriber<? super U> subscriber, Function<? super T, ? extends Publisher<? extends U>> function, boolean z, int i, int i2) {
        return new b(subscriber, function, z, i, i2);
    }

    /* loaded from: classes4.dex */
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
        final AtomicThrowable errs = new AtomicThrowable();
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

        @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
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
                    Publisher publisher = (Publisher) ObjectHelper.requireNonNull(this.mapper.apply(t), "The mapper returned a null Publisher");
                    if (publisher instanceof Callable) {
                        try {
                            Object call = ((Callable) publisher).call();
                            if (call != null) {
                                a((b<T, U>) call);
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
                            this.errs.addThrowable(th);
                            b();
                        }
                    } else {
                        long j = this.uniqueId;
                        this.uniqueId = 1 + j;
                        a aVar = new a(this, j);
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
                        onError(new IllegalStateException("Scalar queue full?!"));
                        return;
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
                onError(new IllegalStateException("Scalar queue full?!"));
                return;
            } else if (getAndIncrement() != 0) {
                return;
            }
            c();
        }

        SimpleQueue<U> c(a<T, U> aVar) {
            SimpleQueue<U> simpleQueue = aVar.queue;
            if (simpleQueue != null) {
                return simpleQueue;
            }
            SpscArrayQueue spscArrayQueue = new SpscArrayQueue(this.bufferSize);
            aVar.queue = spscArrayQueue;
            return spscArrayQueue;
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
                SimpleQueue<U> simpleQueue2 = aVar.queue;
                if (j == 0 || (simpleQueue2 != null && !simpleQueue2.isEmpty())) {
                    if (simpleQueue2 == null) {
                        simpleQueue2 = c(aVar);
                    }
                    if (!simpleQueue2.offer(u)) {
                        onError(new MissingBackpressureException("Inner queue full?!"));
                        return;
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
            } else if (this.errs.addThrowable(th)) {
                this.done = true;
                if (!this.delayErrors) {
                    for (a<?, ?> aVar : this.subscribers.getAndSet(b)) {
                        aVar.dispose();
                    }
                }
                b();
            } else {
                RxJavaPlugins.onError(th);
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

        /* JADX WARN: Code restructure failed: missing block: B:120:0x019a, code lost:
            r24.lastIndex = r4;
            r24.lastId = r8[r4].id;
            r5 = 0;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        void c() {
            /*
                Method dump skipped, instructions count: 462
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: io.reactivex.internal.operators.flowable.FlowableFlatMap.b.c():void");
        }

        boolean d() {
            if (this.cancelled) {
                e();
                return true;
            } else if (this.delayErrors || this.errs.get() == null) {
                return false;
            } else {
                e();
                Throwable terminate = this.errs.terminate();
                if (terminate != ExceptionHelper.TERMINATED) {
                    this.downstream.onError(terminate);
                }
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
            a<?, ?>[] andSet;
            a<?, ?>[] aVarArr = this.subscribers.get();
            a<?, ?>[] aVarArr2 = b;
            if (!(aVarArr == aVarArr2 || (andSet = this.subscribers.getAndSet(aVarArr2)) == b)) {
                for (a<?, ?> aVar : andSet) {
                    aVar.dispose();
                }
                Throwable terminate = this.errs.terminate();
                if (!(terminate == null || terminate == ExceptionHelper.TERMINATED)) {
                    RxJavaPlugins.onError(terminate);
                }
            }
        }

        void a(a<T, U> aVar, Throwable th) {
            if (this.errs.addThrowable(th)) {
                aVar.done = true;
                if (!this.delayErrors) {
                    this.upstream.cancel();
                    for (a<?, ?> aVar2 : this.subscribers.getAndSet(b)) {
                        aVar2.dispose();
                    }
                }
                b();
                return;
            }
            RxJavaPlugins.onError(th);
        }
    }

    /* loaded from: classes4.dex */
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

        a(b<T, U> bVar, long j) {
            this.id = j;
            this.parent = bVar;
            this.bufferSize = bVar.bufferSize;
            this.limit = this.bufferSize >> 2;
        }

        @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
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

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            SubscriptionHelper.cancel(this);
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return get() == SubscriptionHelper.CANCELLED;
        }
    }
}
