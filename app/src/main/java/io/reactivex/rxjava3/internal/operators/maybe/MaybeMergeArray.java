package io.reactivex.rxjava3.internal.operators.maybe;

import io.reactivex.rxjava3.annotations.Nullable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.MaybeObserver;
import io.reactivex.rxjava3.core.MaybeSource;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.fuseable.SimpleQueue;
import io.reactivex.rxjava3.internal.subscriptions.BasicIntQueueSubscription;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.AtomicThrowable;
import io.reactivex.rxjava3.internal.util.BackpressureHelper;
import io.reactivex.rxjava3.internal.util.NotificationLite;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReferenceArray;
import org.reactivestreams.Subscriber;

/* loaded from: classes5.dex */
public final class MaybeMergeArray<T> extends Flowable<T> {
    final MaybeSource<? extends T>[] b;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public interface d<T> extends SimpleQueue<T> {
        int a();

        int b();

        void c();

        T peek();

        @Override // java.util.Queue, io.reactivex.rxjava3.internal.operators.maybe.MaybeMergeArray.d, io.reactivex.rxjava3.internal.fuseable.SimpleQueue
        @Nullable
        T poll();
    }

    public MaybeMergeArray(MaybeSource<? extends T>[] maybeSourceArr) {
        this.b = maybeSourceArr;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    protected void subscribeActual(Subscriber<? super T> subscriber) {
        d dVar;
        MaybeSource[] maybeSourceArr = this.b;
        int length = maybeSourceArr.length;
        if (length <= bufferSize()) {
            dVar = new c(length);
        } else {
            dVar = new a();
        }
        b bVar = new b(subscriber, length, dVar);
        subscriber.onSubscribe(bVar);
        AtomicThrowable atomicThrowable = bVar.errors;
        for (MaybeSource maybeSource : maybeSourceArr) {
            if (!bVar.a() && atomicThrowable.get() == null) {
                maybeSource.subscribe(bVar);
            } else {
                return;
            }
        }
    }

    /* loaded from: classes5.dex */
    static final class b<T> extends BasicIntQueueSubscription<T> implements MaybeObserver<T> {
        private static final long serialVersionUID = -660395290758764731L;
        volatile boolean cancelled;
        long consumed;
        final Subscriber<? super T> downstream;
        boolean outputFused;
        final d<Object> queue;
        final int sourceCount;
        final CompositeDisposable set = new CompositeDisposable();
        final AtomicLong requested = new AtomicLong();
        final AtomicThrowable errors = new AtomicThrowable();

        b(Subscriber<? super T> subscriber, int i, d<Object> dVar) {
            this.downstream = subscriber;
            this.sourceCount = i;
            this.queue = dVar;
        }

        @Override // io.reactivex.rxjava3.internal.fuseable.QueueFuseable
        public int requestFusion(int i) {
            if ((i & 2) == 0) {
                return 0;
            }
            this.outputFused = true;
            return 2;
        }

        @Override // io.reactivex.rxjava3.internal.fuseable.SimpleQueue
        @Nullable
        public T poll() {
            T t;
            do {
                t = (T) this.queue.poll();
            } while (t == NotificationLite.COMPLETE);
            return t;
        }

        @Override // io.reactivex.rxjava3.internal.fuseable.SimpleQueue
        public boolean isEmpty() {
            return this.queue.isEmpty();
        }

        @Override // io.reactivex.rxjava3.internal.fuseable.SimpleQueue
        public void clear() {
            this.queue.clear();
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (SubscriptionHelper.validate(j)) {
                BackpressureHelper.add(this.requested, j);
                d();
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (!this.cancelled) {
                this.cancelled = true;
                this.set.dispose();
                if (getAndIncrement() == 0) {
                    this.queue.clear();
                }
            }
        }

        @Override // io.reactivex.rxjava3.core.MaybeObserver
        public void onSubscribe(Disposable disposable) {
            this.set.add(disposable);
        }

        @Override // io.reactivex.rxjava3.core.MaybeObserver
        public void onSuccess(T t) {
            this.queue.offer(t);
            d();
        }

        @Override // io.reactivex.rxjava3.core.MaybeObserver
        public void onError(Throwable th) {
            if (this.errors.tryAddThrowableOrReport(th)) {
                this.set.dispose();
                this.queue.offer(NotificationLite.COMPLETE);
                d();
            }
        }

        @Override // io.reactivex.rxjava3.core.MaybeObserver
        public void onComplete() {
            this.queue.offer(NotificationLite.COMPLETE);
            d();
        }

        boolean a() {
            return this.cancelled;
        }

        void b() {
            int i;
            Subscriber<? super T> subscriber = this.downstream;
            d<Object> dVar = this.queue;
            long j = this.consumed;
            int i2 = 1;
            do {
                long j2 = this.requested.get();
                while (true) {
                    i = (j > j2 ? 1 : (j == j2 ? 0 : -1));
                    if (i == 0) {
                        break;
                    } else if (this.cancelled) {
                        dVar.clear();
                        return;
                    } else if (this.errors.get() != null) {
                        dVar.clear();
                        this.errors.tryTerminateConsumer(this.downstream);
                        return;
                    } else if (dVar.a() == this.sourceCount) {
                        subscriber.onComplete();
                        return;
                    } else {
                        Object poll = dVar.poll();
                        if (poll == null) {
                            break;
                        } else if (poll != NotificationLite.COMPLETE) {
                            subscriber.onNext(poll);
                            j++;
                        }
                    }
                }
                if (i == 0) {
                    if (this.errors.get() != null) {
                        dVar.clear();
                        this.errors.tryTerminateConsumer(this.downstream);
                        return;
                    }
                    while (dVar.peek() == NotificationLite.COMPLETE) {
                        dVar.c();
                    }
                    if (dVar.a() == this.sourceCount) {
                        subscriber.onComplete();
                        return;
                    }
                }
                this.consumed = j;
                i2 = addAndGet(-i2);
            } while (i2 != 0);
        }

        void c() {
            Subscriber<? super T> subscriber = this.downstream;
            d<Object> dVar = this.queue;
            int i = 1;
            while (!this.cancelled) {
                Throwable th = this.errors.get();
                if (th != null) {
                    dVar.clear();
                    subscriber.onError(th);
                    return;
                }
                boolean z = dVar.b() == this.sourceCount;
                if (!dVar.isEmpty()) {
                    subscriber.onNext(null);
                }
                if (z) {
                    subscriber.onComplete();
                    return;
                }
                i = addAndGet(-i);
                if (i == 0) {
                    return;
                }
            }
            dVar.clear();
        }

        void d() {
            if (getAndIncrement() == 0) {
                if (this.outputFused) {
                    c();
                } else {
                    b();
                }
            }
        }
    }

    /* loaded from: classes5.dex */
    static final class c<T> extends AtomicReferenceArray<T> implements d<T> {
        private static final long serialVersionUID = -7969063454040569579L;
        int consumerIndex;
        final AtomicInteger producerIndex = new AtomicInteger();

        c(int i) {
            super(i);
        }

        @Override // io.reactivex.rxjava3.internal.fuseable.SimpleQueue
        public boolean offer(T t) {
            Objects.requireNonNull(t, "value is null");
            int andIncrement = this.producerIndex.getAndIncrement();
            if (andIncrement >= length()) {
                return false;
            }
            lazySet(andIncrement, t);
            return true;
        }

        @Override // io.reactivex.rxjava3.internal.fuseable.SimpleQueue
        public boolean offer(T t, T t2) {
            throw new UnsupportedOperationException();
        }

        @Override // io.reactivex.rxjava3.internal.operators.maybe.MaybeMergeArray.d, java.util.Queue, io.reactivex.rxjava3.internal.fuseable.SimpleQueue
        @Nullable
        public T poll() {
            int i = this.consumerIndex;
            if (i == length()) {
                return null;
            }
            AtomicInteger atomicInteger = this.producerIndex;
            do {
                T t = get(i);
                if (t != null) {
                    this.consumerIndex = i + 1;
                    lazySet(i, null);
                    return t;
                }
            } while (atomicInteger.get() != i);
            return null;
        }

        @Override // io.reactivex.rxjava3.internal.operators.maybe.MaybeMergeArray.d
        public T peek() {
            int i = this.consumerIndex;
            if (i == length()) {
                return null;
            }
            return get(i);
        }

        @Override // io.reactivex.rxjava3.internal.operators.maybe.MaybeMergeArray.d
        public void c() {
            int i = this.consumerIndex;
            lazySet(i, null);
            this.consumerIndex = i + 1;
        }

        @Override // io.reactivex.rxjava3.internal.fuseable.SimpleQueue
        public boolean isEmpty() {
            return this.consumerIndex == b();
        }

        @Override // io.reactivex.rxjava3.internal.fuseable.SimpleQueue
        public void clear() {
            while (poll() != null && !isEmpty()) {
            }
        }

        @Override // io.reactivex.rxjava3.internal.operators.maybe.MaybeMergeArray.d
        public int a() {
            return this.consumerIndex;
        }

        @Override // io.reactivex.rxjava3.internal.operators.maybe.MaybeMergeArray.d
        public int b() {
            return this.producerIndex.get();
        }
    }

    /* loaded from: classes5.dex */
    static final class a<T> extends ConcurrentLinkedQueue<T> implements d<T> {
        private static final long serialVersionUID = -4025173261791142821L;
        int consumerIndex;
        final AtomicInteger producerIndex = new AtomicInteger();

        a() {
        }

        @Override // io.reactivex.rxjava3.internal.fuseable.SimpleQueue
        public boolean offer(T t, T t2) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.concurrent.ConcurrentLinkedQueue, java.util.Queue, io.reactivex.rxjava3.internal.fuseable.SimpleQueue
        public boolean offer(T t) {
            this.producerIndex.getAndIncrement();
            return super.offer(t);
        }

        @Override // java.util.concurrent.ConcurrentLinkedQueue, java.util.Queue, io.reactivex.rxjava3.internal.operators.maybe.MaybeMergeArray.d, io.reactivex.rxjava3.internal.fuseable.SimpleQueue
        @Nullable
        public T poll() {
            T t = (T) super.poll();
            if (t != null) {
                this.consumerIndex++;
            }
            return t;
        }

        @Override // io.reactivex.rxjava3.internal.operators.maybe.MaybeMergeArray.d
        public int a() {
            return this.consumerIndex;
        }

        @Override // io.reactivex.rxjava3.internal.operators.maybe.MaybeMergeArray.d
        public int b() {
            return this.producerIndex.get();
        }

        @Override // io.reactivex.rxjava3.internal.operators.maybe.MaybeMergeArray.d
        public void c() {
            poll();
        }
    }
}
