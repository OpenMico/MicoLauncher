package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes4.dex */
public final class FlowableFlatMapMaybe<T, R> extends a<T, R> {
    final Function<? super T, ? extends MaybeSource<? extends R>> b;
    final boolean c;
    final int d;

    public FlowableFlatMapMaybe(Flowable<T> flowable, Function<? super T, ? extends MaybeSource<? extends R>> function, boolean z, int i) {
        super(flowable);
        this.b = function;
        this.c = z;
        this.d = i;
    }

    @Override // io.reactivex.Flowable
    protected void subscribeActual(Subscriber<? super R> subscriber) {
        this.source.subscribe((FlowableSubscriber) new a(subscriber, this.b, this.c, this.d));
    }

    /* loaded from: classes4.dex */
    static final class a<T, R> extends AtomicInteger implements FlowableSubscriber<T>, Subscription {
        private static final long serialVersionUID = 8600231336733376951L;
        volatile boolean cancelled;
        final boolean delayErrors;
        final Subscriber<? super R> downstream;
        final Function<? super T, ? extends MaybeSource<? extends R>> mapper;
        final int maxConcurrency;
        Subscription upstream;
        final AtomicLong requested = new AtomicLong();
        final CompositeDisposable set = new CompositeDisposable();
        final AtomicThrowable errors = new AtomicThrowable();
        final AtomicInteger active = new AtomicInteger(1);
        final AtomicReference<SpscLinkedArrayQueue<R>> queue = new AtomicReference<>();

        a(Subscriber<? super R> subscriber, Function<? super T, ? extends MaybeSource<? extends R>> function, boolean z, int i) {
            this.downstream = subscriber;
            this.mapper = function;
            this.delayErrors = z;
            this.maxConcurrency = i;
        }

        @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
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
                MaybeSource maybeSource = (MaybeSource) ObjectHelper.requireNonNull(this.mapper.apply(t), "The mapper returned a null MaybeSource");
                this.active.getAndIncrement();
                C0223a aVar = new C0223a();
                if (!this.cancelled && this.set.add(aVar)) {
                    maybeSource.subscribe(aVar);
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
            if (this.errors.addThrowable(th)) {
                if (!this.delayErrors) {
                    this.set.dispose();
                }
                b();
                return;
            }
            RxJavaPlugins.onError(th);
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
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (SubscriptionHelper.validate(j)) {
                BackpressureHelper.add(this.requested, j);
                b();
            }
        }

        void a(a<T, R>.C0223a aVar, R r) {
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
                            Throwable terminate = this.errors.terminate();
                            if (terminate != null) {
                                this.downstream.onError(terminate);
                                return;
                            } else {
                                this.downstream.onComplete();
                                return;
                            }
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
            SpscLinkedArrayQueue<R> spscLinkedArrayQueue;
            do {
                SpscLinkedArrayQueue<R> spscLinkedArrayQueue2 = this.queue.get();
                if (spscLinkedArrayQueue2 != null) {
                    return spscLinkedArrayQueue2;
                }
                spscLinkedArrayQueue = new SpscLinkedArrayQueue<>(Flowable.bufferSize());
            } while (!this.queue.compareAndSet(null, spscLinkedArrayQueue));
            return spscLinkedArrayQueue;
        }

        void a(a<T, R>.C0223a aVar, Throwable th) {
            this.set.delete(aVar);
            if (this.errors.addThrowable(th)) {
                if (!this.delayErrors) {
                    this.upstream.cancel();
                    this.set.dispose();
                } else if (this.maxConcurrency != Integer.MAX_VALUE) {
                    this.upstream.request(1L);
                }
                this.active.decrementAndGet();
                b();
                return;
            }
            RxJavaPlugins.onError(th);
        }

        void a(a<T, R>.C0223a aVar) {
            this.set.delete(aVar);
            if (get() == 0) {
                boolean z = true;
                if (compareAndSet(0, 1)) {
                    if (this.active.decrementAndGet() != 0) {
                        z = false;
                    }
                    SpscLinkedArrayQueue<R> spscLinkedArrayQueue = this.queue.get();
                    if (!z || (spscLinkedArrayQueue != null && !spscLinkedArrayQueue.isEmpty())) {
                        if (this.maxConcurrency != Integer.MAX_VALUE) {
                            this.upstream.request(1L);
                        }
                        if (decrementAndGet() != 0) {
                            d();
                            return;
                        }
                        return;
                    }
                    Throwable terminate = this.errors.terminate();
                    if (terminate != null) {
                        this.downstream.onError(terminate);
                        return;
                    } else {
                        this.downstream.onComplete();
                        return;
                    }
                }
            }
            this.active.decrementAndGet();
            if (this.maxConcurrency != Integer.MAX_VALUE) {
                this.upstream.request(1L);
            }
            b();
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

        /* JADX WARN: Code restructure failed: missing block: B:36:0x0077, code lost:
            if (r12 != 0) goto L_0x00c8;
         */
        /* JADX WARN: Code restructure failed: missing block: B:38:0x007b, code lost:
            if (r17.cancelled == false) goto L_0x0081;
         */
        /* JADX WARN: Code restructure failed: missing block: B:39:0x007d, code lost:
            c();
         */
        /* JADX WARN: Code restructure failed: missing block: B:40:0x0080, code lost:
            return;
         */
        /* JADX WARN: Code restructure failed: missing block: B:42:0x0083, code lost:
            if (r17.delayErrors != false) goto L_0x009c;
         */
        /* JADX WARN: Code restructure failed: missing block: B:44:0x008d, code lost:
            if (r17.errors.get() == null) goto L_0x009c;
         */
        /* JADX WARN: Code restructure failed: missing block: B:45:0x008f, code lost:
            r2 = r17.errors.terminate();
            c();
            r1.onError(r2);
         */
        /* JADX WARN: Code restructure failed: missing block: B:46:0x009b, code lost:
            return;
         */
        /* JADX WARN: Code restructure failed: missing block: B:48:0x00a0, code lost:
            if (r2.get() != 0) goto L_0x00a4;
         */
        /* JADX WARN: Code restructure failed: missing block: B:49:0x00a2, code lost:
            r6 = true;
         */
        /* JADX WARN: Code restructure failed: missing block: B:50:0x00a4, code lost:
            r6 = false;
         */
        /* JADX WARN: Code restructure failed: missing block: B:51:0x00a5, code lost:
            r7 = r3.get();
         */
        /* JADX WARN: Code restructure failed: missing block: B:52:0x00ab, code lost:
            if (r7 == null) goto L_0x00b3;
         */
        /* JADX WARN: Code restructure failed: missing block: B:54:0x00b1, code lost:
            if (r7.isEmpty() == false) goto L_0x00b4;
         */
        /* JADX WARN: Code restructure failed: missing block: B:55:0x00b3, code lost:
            r13 = true;
         */
        /* JADX WARN: Code restructure failed: missing block: B:56:0x00b4, code lost:
            if (r6 == false) goto L_0x00c8;
         */
        /* JADX WARN: Code restructure failed: missing block: B:57:0x00b6, code lost:
            if (r13 == false) goto L_0x00c8;
         */
        /* JADX WARN: Code restructure failed: missing block: B:58:0x00b8, code lost:
            r2 = r17.errors.terminate();
         */
        /* JADX WARN: Code restructure failed: missing block: B:59:0x00be, code lost:
            if (r2 == null) goto L_0x00c4;
         */
        /* JADX WARN: Code restructure failed: missing block: B:60:0x00c0, code lost:
            r1.onError(r2);
         */
        /* JADX WARN: Code restructure failed: missing block: B:61:0x00c4, code lost:
            r1.onComplete();
         */
        /* JADX WARN: Code restructure failed: missing block: B:62:0x00c7, code lost:
            return;
         */
        /* JADX WARN: Code restructure failed: missing block: B:64:0x00ca, code lost:
            if (r10 == 0) goto L_0x00dd;
         */
        /* JADX WARN: Code restructure failed: missing block: B:65:0x00cc, code lost:
            io.reactivex.internal.util.BackpressureHelper.produced(r17.requested, r10);
         */
        /* JADX WARN: Code restructure failed: missing block: B:66:0x00d6, code lost:
            if (r17.maxConcurrency == Integer.MAX_VALUE) goto L_0x00dd;
         */
        /* JADX WARN: Code restructure failed: missing block: B:67:0x00d8, code lost:
            r17.upstream.request(r10);
         */
        /* JADX WARN: Code restructure failed: missing block: B:68:0x00dd, code lost:
            r5 = addAndGet(-r5);
         */
        /* JADX WARN: Code restructure failed: missing block: B:81:?, code lost:
            return;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        void d() {
            /*
                Method dump skipped, instructions count: 229
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: io.reactivex.internal.operators.flowable.FlowableFlatMapMaybe.a.d():void");
        }

        /* renamed from: io.reactivex.internal.operators.flowable.FlowableFlatMapMaybe$a$a  reason: collision with other inner class name */
        /* loaded from: classes4.dex */
        final class C0223a extends AtomicReference<Disposable> implements MaybeObserver<R>, Disposable {
            private static final long serialVersionUID = -502562646270949838L;

            C0223a() {
            }

            @Override // io.reactivex.MaybeObserver, io.reactivex.SingleObserver
            public void onSubscribe(Disposable disposable) {
                DisposableHelper.setOnce(this, disposable);
            }

            @Override // io.reactivex.MaybeObserver, io.reactivex.SingleObserver
            public void onSuccess(R r) {
                a.this.a((a<T, C0223a>.C0223a) this, (C0223a) r);
            }

            @Override // io.reactivex.MaybeObserver, io.reactivex.SingleObserver
            public void onError(Throwable th) {
                a.this.a(this, th);
            }

            @Override // io.reactivex.MaybeObserver
            public void onComplete() {
                a.this.a(this);
            }

            @Override // io.reactivex.disposables.Disposable
            public boolean isDisposed() {
                return DisposableHelper.isDisposed(get());
            }

            @Override // io.reactivex.disposables.Disposable
            public void dispose() {
                DisposableHelper.dispose(this);
            }
        }
    }
}