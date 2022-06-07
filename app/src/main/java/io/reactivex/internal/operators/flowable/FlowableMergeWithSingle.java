package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.fuseable.SimplePlainQueue;
import io.reactivex.internal.queue.SpscArrayQueue;
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
public final class FlowableMergeWithSingle<T> extends a<T, T> {
    final SingleSource<? extends T> b;

    public FlowableMergeWithSingle(Flowable<T> flowable, SingleSource<? extends T> singleSource) {
        super(flowable);
        this.b = singleSource;
    }

    @Override // io.reactivex.Flowable
    protected void subscribeActual(Subscriber<? super T> subscriber) {
        a aVar = new a(subscriber);
        subscriber.onSubscribe(aVar);
        this.source.subscribe((FlowableSubscriber) aVar);
        this.b.subscribe(aVar.otherObserver);
    }

    /* loaded from: classes4.dex */
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
        final C0227a<T> otherObserver = new C0227a<>(this);
        final AtomicThrowable error = new AtomicThrowable();
        final AtomicLong requested = new AtomicLong();
        final int prefetch = Flowable.bufferSize();

        a(Subscriber<? super T> subscriber) {
            this.downstream = subscriber;
            int i = this.prefetch;
            this.limit = i - (i >> 2);
        }

        @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
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
            if (this.error.addThrowable(th)) {
                DisposableHelper.dispose(this.otherObserver);
                b();
                return;
            }
            RxJavaPlugins.onError(th);
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
            if (this.error.addThrowable(th)) {
                SubscriptionHelper.cancel(this.mainSubscription);
                b();
                return;
            }
            RxJavaPlugins.onError(th);
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

        void c() {
            int i;
            Subscriber<? super T> subscriber = this.downstream;
            long j = this.emitted;
            int i2 = this.consumed;
            int i3 = this.limit;
            int i4 = 1;
            long j2 = j;
            int i5 = 1;
            while (true) {
                long j3 = this.requested.get();
                while (true) {
                    i = (j2 > j3 ? 1 : (j2 == j3 ? 0 : -1));
                    if (i == 0) {
                        break;
                    } else if (this.cancelled) {
                        this.singleItem = null;
                        this.queue = null;
                        return;
                    } else if (this.error.get() != null) {
                        this.singleItem = null;
                        this.queue = null;
                        subscriber.onError(this.error.terminate());
                        return;
                    } else {
                        int i6 = this.otherState;
                        if (i6 == i4) {
                            this.singleItem = null;
                            this.otherState = 2;
                            subscriber.onNext((T) this.singleItem);
                            j2++;
                        } else {
                            boolean z = this.mainDone;
                            SimplePlainQueue<T> simplePlainQueue = this.queue;
                            T poll = simplePlainQueue != null ? simplePlainQueue.poll() : (Object) null;
                            boolean z2 = poll == null;
                            if (z && z2 && i6 == 2) {
                                this.queue = null;
                                subscriber.onComplete();
                                return;
                            } else if (z2) {
                                break;
                            } else {
                                subscriber.onNext(poll);
                                j2++;
                                i2++;
                                if (i2 == i3) {
                                    this.mainSubscription.get().request(i3);
                                    i2 = 0;
                                }
                                i4 = 1;
                            }
                        }
                    }
                }
                if (i == 0) {
                    if (this.cancelled) {
                        this.singleItem = null;
                        this.queue = null;
                        return;
                    } else if (this.error.get() != null) {
                        this.singleItem = null;
                        this.queue = null;
                        subscriber.onError(this.error.terminate());
                        return;
                    } else {
                        boolean z3 = this.mainDone;
                        SimplePlainQueue<T> simplePlainQueue2 = this.queue;
                        boolean z4 = simplePlainQueue2 == null || simplePlainQueue2.isEmpty();
                        if (z3 && z4 && this.otherState == 2) {
                            this.queue = null;
                            subscriber.onComplete();
                            return;
                        }
                    }
                }
                this.emitted = j2;
                this.consumed = i2;
                i5 = addAndGet(-i5);
                if (i5 != 0) {
                    i4 = 1;
                } else {
                    return;
                }
            }
        }

        /* renamed from: io.reactivex.internal.operators.flowable.FlowableMergeWithSingle$a$a  reason: collision with other inner class name */
        /* loaded from: classes4.dex */
        static final class C0227a<T> extends AtomicReference<Disposable> implements SingleObserver<T> {
            private static final long serialVersionUID = -2935427570954647017L;
            final a<T> parent;

            C0227a(a<T> aVar) {
                this.parent = aVar;
            }

            @Override // io.reactivex.SingleObserver
            public void onSubscribe(Disposable disposable) {
                DisposableHelper.setOnce(this, disposable);
            }

            @Override // io.reactivex.SingleObserver
            public void onSuccess(T t) {
                this.parent.a((a<T>) t);
            }

            @Override // io.reactivex.SingleObserver
            public void onError(Throwable th) {
                this.parent.a(th);
            }
        }
    }
}
