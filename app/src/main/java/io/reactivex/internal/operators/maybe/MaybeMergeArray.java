package io.reactivex.internal.operators.maybe;

import io.reactivex.Flowable;
import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.annotations.Nullable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.subscriptions.BasicIntQueueSubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.internal.util.NotificationLite;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReferenceArray;
import org.reactivestreams.Subscriber;

/* loaded from: classes4.dex */
public final class MaybeMergeArray<T> extends Flowable<T> {
    final MaybeSource<? extends T>[] b;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public interface d<T> extends SimpleQueue<T> {
        int a();

        int b();

        void c();

        T peek();

        @Override // java.util.Queue, io.reactivex.internal.operators.maybe.MaybeMergeArray.d, io.reactivex.internal.fuseable.SimpleQueue
        @Nullable
        T poll();
    }

    public MaybeMergeArray(MaybeSource<? extends T>[] maybeSourceArr) {
        this.b = maybeSourceArr;
    }

    @Override // io.reactivex.Flowable
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
        AtomicThrowable atomicThrowable = bVar.error;
        for (MaybeSource maybeSource : maybeSourceArr) {
            if (!bVar.a() && atomicThrowable.get() == null) {
                maybeSource.subscribe(bVar);
            } else {
                return;
            }
        }
    }

    /* loaded from: classes4.dex */
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
        final AtomicThrowable error = new AtomicThrowable();

        b(Subscriber<? super T> subscriber, int i, d<Object> dVar) {
            this.downstream = subscriber;
            this.sourceCount = i;
            this.queue = dVar;
        }

        @Override // io.reactivex.internal.fuseable.QueueFuseable
        public int requestFusion(int i) {
            if ((i & 2) == 0) {
                return 0;
            }
            this.outputFused = true;
            return 2;
        }

        @Override // io.reactivex.internal.fuseable.SimpleQueue
        @Nullable
        public T poll() throws Exception {
            T t;
            do {
                t = (T) this.queue.poll();
            } while (t == NotificationLite.COMPLETE);
            return t;
        }

        @Override // io.reactivex.internal.fuseable.SimpleQueue
        public boolean isEmpty() {
            return this.queue.isEmpty();
        }

        @Override // io.reactivex.internal.fuseable.SimpleQueue
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

        @Override // io.reactivex.MaybeObserver, io.reactivex.SingleObserver
        public void onSubscribe(Disposable disposable) {
            this.set.add(disposable);
        }

        @Override // io.reactivex.MaybeObserver, io.reactivex.SingleObserver
        public void onSuccess(T t) {
            this.queue.offer(t);
            d();
        }

        @Override // io.reactivex.MaybeObserver, io.reactivex.SingleObserver
        public void onError(Throwable th) {
            if (this.error.addThrowable(th)) {
                this.set.dispose();
                this.queue.offer(NotificationLite.COMPLETE);
                d();
                return;
            }
            RxJavaPlugins.onError(th);
        }

        @Override // io.reactivex.MaybeObserver
        public void onComplete() {
            this.queue.offer(NotificationLite.COMPLETE);
            d();
        }

        boolean a() {
            return this.cancelled;
        }

        /* JADX WARN: Code restructure failed: missing block: B:23:0x004e, code lost:
            if (r7 != 0) goto L_0x007f;
         */
        /* JADX WARN: Code restructure failed: missing block: B:25:0x0058, code lost:
            if (r10.error.get() == null) goto L_0x0067;
         */
        /* JADX WARN: Code restructure failed: missing block: B:26:0x005a, code lost:
            r1.clear();
            r0.onError(r10.error.terminate());
         */
        /* JADX WARN: Code restructure failed: missing block: B:27:0x0066, code lost:
            return;
         */
        /* JADX WARN: Code restructure failed: missing block: B:29:0x006d, code lost:
            if (r1.peek() != io.reactivex.internal.util.NotificationLite.COMPLETE) goto L_0x0073;
         */
        /* JADX WARN: Code restructure failed: missing block: B:30:0x006f, code lost:
            r1.c();
         */
        /* JADX WARN: Code restructure failed: missing block: B:32:0x0079, code lost:
            if (r1.a() != r10.sourceCount) goto L_0x007f;
         */
        /* JADX WARN: Code restructure failed: missing block: B:33:0x007b, code lost:
            r0.onComplete();
         */
        /* JADX WARN: Code restructure failed: missing block: B:34:0x007e, code lost:
            return;
         */
        /* JADX WARN: Code restructure failed: missing block: B:35:0x007f, code lost:
            r10.consumed = r2;
            r4 = addAndGet(-r4);
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        void b() {
            /*
                r10 = this;
                org.reactivestreams.Subscriber<? super T> r0 = r10.downstream
                io.reactivex.internal.operators.maybe.MaybeMergeArray$d<java.lang.Object> r1 = r10.queue
                long r2 = r10.consumed
                r4 = 1
            L_0x0007:
                java.util.concurrent.atomic.AtomicLong r5 = r10.requested
                long r5 = r5.get()
            L_0x000d:
                int r7 = (r2 > r5 ? 1 : (r2 == r5 ? 0 : -1))
                if (r7 == 0) goto L_0x004e
                boolean r8 = r10.cancelled
                if (r8 == 0) goto L_0x0019
                r1.clear()
                return
            L_0x0019:
                io.reactivex.internal.util.AtomicThrowable r8 = r10.error
                java.lang.Object r8 = r8.get()
                java.lang.Throwable r8 = (java.lang.Throwable) r8
                if (r8 == 0) goto L_0x0030
                r1.clear()
                io.reactivex.internal.util.AtomicThrowable r1 = r10.error
                java.lang.Throwable r1 = r1.terminate()
                r0.onError(r1)
                return
            L_0x0030:
                int r8 = r1.a()
                int r9 = r10.sourceCount
                if (r8 != r9) goto L_0x003c
                r0.onComplete()
                return
            L_0x003c:
                java.lang.Object r8 = r1.poll()
                if (r8 != 0) goto L_0x0043
                goto L_0x004e
            L_0x0043:
                io.reactivex.internal.util.NotificationLite r7 = io.reactivex.internal.util.NotificationLite.COMPLETE
                if (r8 == r7) goto L_0x000d
                r0.onNext(r8)
                r7 = 1
                long r2 = r2 + r7
                goto L_0x000d
            L_0x004e:
                if (r7 != 0) goto L_0x007f
                io.reactivex.internal.util.AtomicThrowable r5 = r10.error
                java.lang.Object r5 = r5.get()
                java.lang.Throwable r5 = (java.lang.Throwable) r5
                if (r5 == 0) goto L_0x0067
                r1.clear()
                io.reactivex.internal.util.AtomicThrowable r1 = r10.error
                java.lang.Throwable r1 = r1.terminate()
                r0.onError(r1)
                return
            L_0x0067:
                java.lang.Object r5 = r1.peek()
                io.reactivex.internal.util.NotificationLite r6 = io.reactivex.internal.util.NotificationLite.COMPLETE
                if (r5 != r6) goto L_0x0073
                r1.c()
                goto L_0x0067
            L_0x0073:
                int r5 = r1.a()
                int r6 = r10.sourceCount
                if (r5 != r6) goto L_0x007f
                r0.onComplete()
                return
            L_0x007f:
                r10.consumed = r2
                int r4 = -r4
                int r4 = r10.addAndGet(r4)
                if (r4 != 0) goto L_0x0007
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: io.reactivex.internal.operators.maybe.MaybeMergeArray.b.b():void");
        }

        void c() {
            Subscriber<? super T> subscriber = this.downstream;
            d<Object> dVar = this.queue;
            int i = 1;
            while (!this.cancelled) {
                Throwable th = this.error.get();
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

    /* loaded from: classes4.dex */
    static final class c<T> extends AtomicReferenceArray<T> implements d<T> {
        private static final long serialVersionUID = -7969063454040569579L;
        int consumerIndex;
        final AtomicInteger producerIndex = new AtomicInteger();

        c(int i) {
            super(i);
        }

        @Override // io.reactivex.internal.fuseable.SimpleQueue
        public boolean offer(T t) {
            ObjectHelper.requireNonNull(t, "value is null");
            int andIncrement = this.producerIndex.getAndIncrement();
            if (andIncrement >= length()) {
                return false;
            }
            lazySet(andIncrement, t);
            return true;
        }

        @Override // io.reactivex.internal.fuseable.SimpleQueue
        public boolean offer(T t, T t2) {
            throw new UnsupportedOperationException();
        }

        @Override // io.reactivex.internal.operators.maybe.MaybeMergeArray.d, java.util.Queue, io.reactivex.internal.fuseable.SimpleQueue
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

        @Override // io.reactivex.internal.operators.maybe.MaybeMergeArray.d
        public T peek() {
            int i = this.consumerIndex;
            if (i == length()) {
                return null;
            }
            return get(i);
        }

        @Override // io.reactivex.internal.operators.maybe.MaybeMergeArray.d
        public void c() {
            int i = this.consumerIndex;
            lazySet(i, null);
            this.consumerIndex = i + 1;
        }

        @Override // io.reactivex.internal.fuseable.SimpleQueue
        public boolean isEmpty() {
            return this.consumerIndex == b();
        }

        @Override // io.reactivex.internal.fuseable.SimpleQueue
        public void clear() {
            while (poll() != null && !isEmpty()) {
            }
        }

        @Override // io.reactivex.internal.operators.maybe.MaybeMergeArray.d
        public int a() {
            return this.consumerIndex;
        }

        @Override // io.reactivex.internal.operators.maybe.MaybeMergeArray.d
        public int b() {
            return this.producerIndex.get();
        }
    }

    /* loaded from: classes4.dex */
    static final class a<T> extends ConcurrentLinkedQueue<T> implements d<T> {
        private static final long serialVersionUID = -4025173261791142821L;
        int consumerIndex;
        final AtomicInteger producerIndex = new AtomicInteger();

        a() {
        }

        @Override // io.reactivex.internal.fuseable.SimpleQueue
        public boolean offer(T t, T t2) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.concurrent.ConcurrentLinkedQueue, java.util.Queue, io.reactivex.internal.fuseable.SimpleQueue
        public boolean offer(T t) {
            this.producerIndex.getAndIncrement();
            return super.offer(t);
        }

        @Override // java.util.concurrent.ConcurrentLinkedQueue, java.util.Queue, io.reactivex.internal.operators.maybe.MaybeMergeArray.d, io.reactivex.internal.fuseable.SimpleQueue
        @Nullable
        public T poll() {
            T t = (T) super.poll();
            if (t != null) {
                this.consumerIndex++;
            }
            return t;
        }

        @Override // io.reactivex.internal.operators.maybe.MaybeMergeArray.d
        public int a() {
            return this.consumerIndex;
        }

        @Override // io.reactivex.internal.operators.maybe.MaybeMergeArray.d
        public int b() {
            return this.producerIndex.get();
        }

        @Override // io.reactivex.internal.operators.maybe.MaybeMergeArray.d
        public void c() {
            poll();
        }
    }
}
