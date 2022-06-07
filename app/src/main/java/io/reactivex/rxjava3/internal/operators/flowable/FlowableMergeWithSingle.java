package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.core.SingleSource;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.internal.fuseable.SimplePlainQueue;
import io.reactivex.rxjava3.internal.queue.SpscArrayQueue;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.AtomicThrowable;
import io.reactivex.rxjava3.internal.util.BackpressureHelper;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes5.dex */
public final class FlowableMergeWithSingle<T> extends a<T, T> {
    final SingleSource<? extends T> b;

    public FlowableMergeWithSingle(Flowable<T> flowable, SingleSource<? extends T> singleSource) {
        super(flowable);
        this.b = singleSource;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    protected void subscribeActual(Subscriber<? super T> subscriber) {
        a aVar = new a(subscriber);
        subscriber.onSubscribe(aVar);
        this.source.subscribe((FlowableSubscriber) aVar);
        this.b.subscribe(aVar.otherObserver);
    }

    /* loaded from: classes5.dex */
    static final class a<T> extends AtomicInteger implements FlowableSubscriber<T>, Subscription {
        private static final long serialVersionUID = -4592979584110982903L;
        volatile boolean cancelled;
        int consumed;
        final Subscriber<? super T> downstream;
        long emitted;
        final int limit;
        volatile boolean mainDone;
        volatile int otherState;
        volatile SimplePlainQueue<T> queue;
        T singleItem;
        final AtomicReference<Subscription> mainSubscription = new AtomicReference<>();
        final C0292a<T> otherObserver = new C0292a<>(this);
        final AtomicThrowable errors = new AtomicThrowable();
        final AtomicLong requested = new AtomicLong();
        final int prefetch = Flowable.bufferSize();

        a(Subscriber<? super T> subscriber) {
            this.downstream = subscriber;
            int i = this.prefetch;
            this.limit = i - (i >> 2);
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            SubscriptionHelper.setOnce(this.mainSubscription, subscription, this.prefetch);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (compareAndSet(0, 1)) {
                long j = this.emitted;
                if (this.requested.get() != j) {
                    SimplePlainQueue<T> simplePlainQueue = this.queue;
                    if (simplePlainQueue == null || simplePlainQueue.isEmpty()) {
                        this.emitted = j + 1;
                        this.downstream.onNext(t);
                        int i = this.consumed + 1;
                        if (i == this.limit) {
                            this.consumed = 0;
                            this.mainSubscription.get().request(i);
                        } else {
                            this.consumed = i;
                        }
                    } else {
                        simplePlainQueue.offer(t);
                    }
                } else {
                    a().offer(t);
                }
                if (decrementAndGet() == 0) {
                    return;
                }
            } else {
                a().offer(t);
                if (getAndIncrement() != 0) {
                    return;
                }
            }
            c();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.errors.tryAddThrowableOrReport(th)) {
                DisposableHelper.dispose(this.otherObserver);
                b();
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.mainDone = true;
            b();
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            BackpressureHelper.add(this.requested, j);
            b();
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.cancelled = true;
            SubscriptionHelper.cancel(this.mainSubscription);
            DisposableHelper.dispose(this.otherObserver);
            this.errors.tryTerminateAndReport();
            if (getAndIncrement() == 0) {
                this.queue = null;
                this.singleItem = null;
            }
        }

        void a(T t) {
            if (compareAndSet(0, 1)) {
                long j = this.emitted;
                if (this.requested.get() != j) {
                    this.emitted = j + 1;
                    this.downstream.onNext(t);
                    this.otherState = 2;
                } else {
                    this.singleItem = t;
                    this.otherState = 1;
                    if (decrementAndGet() == 0) {
                        return;
                    }
                }
            } else {
                this.singleItem = t;
                this.otherState = 1;
                if (getAndIncrement() != 0) {
                    return;
                }
            }
            c();
        }

        void a(Throwable th) {
            if (this.errors.tryAddThrowableOrReport(th)) {
                SubscriptionHelper.cancel(this.mainSubscription);
                b();
            }
        }

        SimplePlainQueue<T> a() {
            SimplePlainQueue<T> simplePlainQueue = this.queue;
            if (simplePlainQueue != null) {
                return simplePlainQueue;
            }
            SpscArrayQueue spscArrayQueue = new SpscArrayQueue(Flowable.bufferSize());
            this.queue = spscArrayQueue;
            return spscArrayQueue;
        }

        void b() {
            if (getAndIncrement() == 0) {
                c();
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:34:0x0080, code lost:
            if (r3 != 0) goto L_0x00bf;
         */
        /* JADX WARN: Code restructure failed: missing block: B:36:0x0084, code lost:
            if (r19.cancelled == false) goto L_0x008b;
         */
        /* JADX WARN: Code restructure failed: missing block: B:37:0x0086, code lost:
            r19.singleItem = null;
            r19.queue = null;
         */
        /* JADX WARN: Code restructure failed: missing block: B:38:0x008a, code lost:
            return;
         */
        /* JADX WARN: Code restructure failed: missing block: B:40:0x0091, code lost:
            if (r19.errors.get() == null) goto L_0x009f;
         */
        /* JADX WARN: Code restructure failed: missing block: B:41:0x0093, code lost:
            r19.singleItem = null;
            r19.queue = null;
            r19.errors.tryTerminateConsumer(r19.downstream);
         */
        /* JADX WARN: Code restructure failed: missing block: B:42:0x009e, code lost:
            return;
         */
        /* JADX WARN: Code restructure failed: missing block: B:43:0x009f, code lost:
            r3 = r19.mainDone;
            r6 = r19.queue;
         */
        /* JADX WARN: Code restructure failed: missing block: B:44:0x00a3, code lost:
            if (r6 == null) goto L_0x00af;
         */
        /* JADX WARN: Code restructure failed: missing block: B:46:0x00a9, code lost:
            if (r6.isEmpty() == false) goto L_0x00ac;
         */
        /* JADX WARN: Code restructure failed: missing block: B:47:0x00ac, code lost:
            r17 = false;
         */
        /* JADX WARN: Code restructure failed: missing block: B:48:0x00af, code lost:
            r17 = true;
         */
        /* JADX WARN: Code restructure failed: missing block: B:49:0x00b1, code lost:
            if (r3 == false) goto L_0x00bf;
         */
        /* JADX WARN: Code restructure failed: missing block: B:50:0x00b3, code lost:
            if (r17 == false) goto L_0x00bf;
         */
        /* JADX WARN: Code restructure failed: missing block: B:52:0x00b7, code lost:
            if (r19.otherState != 2) goto L_0x00bf;
         */
        /* JADX WARN: Code restructure failed: missing block: B:53:0x00b9, code lost:
            r19.queue = null;
            r1.onComplete();
         */
        /* JADX WARN: Code restructure failed: missing block: B:54:0x00be, code lost:
            return;
         */
        /* JADX WARN: Code restructure failed: missing block: B:55:0x00bf, code lost:
            r19.emitted = r7;
            r19.consumed = r4;
            r2 = addAndGet(-r2);
         */
        /* JADX WARN: Code restructure failed: missing block: B:56:0x00c8, code lost:
            if (r2 != 0) goto L_0x00cb;
         */
        /* JADX WARN: Code restructure failed: missing block: B:57:0x00ca, code lost:
            return;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        void c() {
            /*
                Method dump skipped, instructions count: 206
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: io.reactivex.rxjava3.internal.operators.flowable.FlowableMergeWithSingle.a.c():void");
        }

        /* renamed from: io.reactivex.rxjava3.internal.operators.flowable.FlowableMergeWithSingle$a$a  reason: collision with other inner class name */
        /* loaded from: classes5.dex */
        static final class C0292a<T> extends AtomicReference<Disposable> implements SingleObserver<T> {
            private static final long serialVersionUID = -2935427570954647017L;
            final a<T> parent;

            C0292a(a<T> aVar) {
                this.parent = aVar;
            }

            @Override // io.reactivex.rxjava3.core.SingleObserver
            public void onSubscribe(Disposable disposable) {
                DisposableHelper.setOnce(this, disposable);
            }

            @Override // io.reactivex.rxjava3.core.SingleObserver
            public void onSuccess(T t) {
                this.parent.a((a<T>) t);
            }

            @Override // io.reactivex.rxjava3.core.SingleObserver
            public void onError(Throwable th) {
                this.parent.a(th);
            }
        }
    }
}
