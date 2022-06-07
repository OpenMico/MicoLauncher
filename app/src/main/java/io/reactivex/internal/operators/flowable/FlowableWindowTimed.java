package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.internal.disposables.SequentialDisposable;
import io.reactivex.internal.fuseable.SimplePlainQueue;
import io.reactivex.internal.queue.MpscLinkedQueue;
import io.reactivex.internal.subscribers.QueueDrainSubscriber;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.NotificationLite;
import io.reactivex.processors.UnicastProcessor;
import io.reactivex.subscribers.SerializedSubscriber;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes4.dex */
public final class FlowableWindowTimed<T> extends a<T, Flowable<T>> {
    final long b;
    final long c;
    final TimeUnit d;
    final Scheduler e;
    final long f;
    final int g;
    final boolean h;

    public FlowableWindowTimed(Flowable<T> flowable, long j, long j2, TimeUnit timeUnit, Scheduler scheduler, long j3, int i, boolean z) {
        super(flowable);
        this.b = j;
        this.c = j2;
        this.d = timeUnit;
        this.e = scheduler;
        this.f = j3;
        this.g = i;
        this.h = z;
    }

    @Override // io.reactivex.Flowable
    protected void subscribeActual(Subscriber<? super Flowable<T>> subscriber) {
        SerializedSubscriber serializedSubscriber = new SerializedSubscriber(subscriber);
        if (this.b != this.c) {
            this.source.subscribe((FlowableSubscriber) new c(serializedSubscriber, this.b, this.c, this.d, this.e.createWorker(), this.g));
        } else if (this.f == Long.MAX_VALUE) {
            this.source.subscribe((FlowableSubscriber) new b(serializedSubscriber, this.b, this.d, this.e, this.g));
        } else {
            this.source.subscribe((FlowableSubscriber) new a(serializedSubscriber, this.b, this.d, this.e, this.g, this.f, this.h));
        }
    }

    /* loaded from: classes4.dex */
    static final class b<T> extends QueueDrainSubscriber<T, Object, Flowable<T>> implements FlowableSubscriber<T>, Runnable, Subscription {
        static final Object h = new Object();
        final long a;
        final TimeUnit b;
        final Scheduler c;
        final int d;
        Subscription e;
        UnicastProcessor<T> f;
        final SequentialDisposable g = new SequentialDisposable();
        volatile boolean i;

        b(Subscriber<? super Flowable<T>> subscriber, long j, TimeUnit timeUnit, Scheduler scheduler, int i) {
            super(subscriber, new MpscLinkedQueue());
            this.a = j;
            this.b = timeUnit;
            this.c = scheduler;
            this.d = i;
        }

        @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.e, subscription)) {
                this.e = subscription;
                this.f = UnicastProcessor.create(this.d);
                Subscriber subscriber = this.downstream;
                subscriber.onSubscribe(this);
                long requested = requested();
                if (requested != 0) {
                    subscriber.onNext(this.f);
                    if (requested != Long.MAX_VALUE) {
                        produced(1L);
                    }
                    if (!this.cancelled) {
                        SequentialDisposable sequentialDisposable = this.g;
                        Scheduler scheduler = this.c;
                        long j = this.a;
                        if (sequentialDisposable.replace(scheduler.schedulePeriodicallyDirect(this, j, j, this.b))) {
                            subscription.request(Long.MAX_VALUE);
                            return;
                        }
                        return;
                    }
                    return;
                }
                this.cancelled = true;
                subscription.cancel();
                subscriber.onError(new MissingBackpressureException("Could not deliver first window due to lack of requests."));
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (!this.i) {
                if (fastEnter()) {
                    this.f.onNext(t);
                    if (leave(-1) == 0) {
                        return;
                    }
                } else {
                    this.queue.offer(NotificationLite.next(t));
                    if (!enter()) {
                        return;
                    }
                }
                a();
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.error = th;
            this.done = true;
            if (enter()) {
                a();
            }
            this.downstream.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.done = true;
            if (enter()) {
                a();
            }
            this.downstream.onComplete();
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            requested(j);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.cancelled = true;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // java.lang.Runnable
        public void run() {
            if (this.cancelled) {
                this.i = true;
            }
            this.queue.offer(h);
            if (enter()) {
                a();
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:10:0x0021, code lost:
            r2.onError(r0);
         */
        /* JADX WARN: Code restructure failed: missing block: B:11:0x0025, code lost:
            r2.onComplete();
         */
        /* JADX WARN: Code restructure failed: missing block: B:12:0x0028, code lost:
            r10.g.dispose();
         */
        /* JADX WARN: Code restructure failed: missing block: B:13:0x002d, code lost:
            return;
         */
        /* JADX WARN: Code restructure failed: missing block: B:8:0x0018, code lost:
            r10.f = null;
            r0.clear();
            r0 = r10.error;
         */
        /* JADX WARN: Code restructure failed: missing block: B:9:0x001f, code lost:
            if (r0 == null) goto L_0x0025;
         */
        /* JADX WARN: Multi-variable type inference failed */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        void a() {
            /*
                r10 = this;
                io.reactivex.internal.fuseable.SimplePlainQueue r0 = r10.queue
                org.reactivestreams.Subscriber r1 = r10.downstream
                io.reactivex.processors.UnicastProcessor<T> r2 = r10.f
                r3 = 1
            L_0x0007:
                boolean r4 = r10.i
                boolean r5 = r10.done
                java.lang.Object r6 = r0.poll()
                r7 = 0
                if (r5 == 0) goto L_0x002e
                if (r6 == 0) goto L_0x0018
                java.lang.Object r5 = io.reactivex.internal.operators.flowable.FlowableWindowTimed.b.h
                if (r6 != r5) goto L_0x002e
            L_0x0018:
                r10.f = r7
                r0.clear()
                java.lang.Throwable r0 = r10.error
                if (r0 == 0) goto L_0x0025
                r2.onError(r0)
                goto L_0x0028
            L_0x0025:
                r2.onComplete()
            L_0x0028:
                io.reactivex.internal.disposables.SequentialDisposable r0 = r10.g
                r0.dispose()
                return
            L_0x002e:
                if (r6 != 0) goto L_0x0038
                int r3 = -r3
                int r3 = r10.leave(r3)
                if (r3 != 0) goto L_0x0007
                return
            L_0x0038:
                java.lang.Object r5 = io.reactivex.internal.operators.flowable.FlowableWindowTimed.b.h
                if (r6 != r5) goto L_0x0087
                r2.onComplete()
                if (r4 != 0) goto L_0x0081
                int r2 = r10.d
                io.reactivex.processors.UnicastProcessor r2 = io.reactivex.processors.UnicastProcessor.create(r2)
                r10.f = r2
                long r4 = r10.requested()
                r8 = 0
                int r6 = (r4 > r8 ? 1 : (r4 == r8 ? 0 : -1))
                if (r6 == 0) goto L_0x0065
                r1.onNext(r2)
                r6 = 9223372036854775807(0x7fffffffffffffff, double:NaN)
                int r4 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
                if (r4 == 0) goto L_0x0007
                r4 = 1
                r10.produced(r4)
                goto L_0x0007
            L_0x0065:
                r10.f = r7
                io.reactivex.internal.fuseable.SimplePlainQueue r0 = r10.queue
                r0.clear()
                org.reactivestreams.Subscription r0 = r10.e
                r0.cancel()
                io.reactivex.exceptions.MissingBackpressureException r0 = new io.reactivex.exceptions.MissingBackpressureException
                java.lang.String r2 = "Could not deliver first window due to lack of requests."
                r0.<init>(r2)
                r1.onError(r0)
                io.reactivex.internal.disposables.SequentialDisposable r0 = r10.g
                r0.dispose()
                return
            L_0x0081:
                org.reactivestreams.Subscription r4 = r10.e
                r4.cancel()
                goto L_0x0007
            L_0x0087:
                java.lang.Object r4 = io.reactivex.internal.util.NotificationLite.getValue(r6)
                r2.onNext(r4)
                goto L_0x0007
            */
            throw new UnsupportedOperationException("Method not decompiled: io.reactivex.internal.operators.flowable.FlowableWindowTimed.b.a():void");
        }
    }

    /* loaded from: classes4.dex */
    static final class a<T> extends QueueDrainSubscriber<T, Object, Flowable<T>> implements Subscription {
        final long a;
        final TimeUnit b;
        final Scheduler c;
        final int d;
        final boolean e;
        final long f;
        final Scheduler.Worker g;
        long h;
        long i;
        Subscription j;
        UnicastProcessor<T> k;
        volatile boolean l;
        final SequentialDisposable m = new SequentialDisposable();

        a(Subscriber<? super Flowable<T>> subscriber, long j, TimeUnit timeUnit, Scheduler scheduler, int i, long j2, boolean z) {
            super(subscriber, new MpscLinkedQueue());
            this.a = j;
            this.b = timeUnit;
            this.c = scheduler;
            this.d = i;
            this.f = j2;
            this.e = z;
            if (z) {
                this.g = scheduler.createWorker();
            } else {
                this.g = null;
            }
        }

        @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            Disposable disposable;
            if (SubscriptionHelper.validate(this.j, subscription)) {
                this.j = subscription;
                Subscriber subscriber = this.downstream;
                subscriber.onSubscribe(this);
                if (!this.cancelled) {
                    UnicastProcessor<T> create = UnicastProcessor.create(this.d);
                    this.k = create;
                    long requested = requested();
                    if (requested != 0) {
                        subscriber.onNext(create);
                        if (requested != Long.MAX_VALUE) {
                            produced(1L);
                        }
                        RunnableC0232a aVar = new RunnableC0232a(this.i, this);
                        if (this.e) {
                            Scheduler.Worker worker = this.g;
                            long j = this.a;
                            disposable = worker.schedulePeriodically(aVar, j, j, this.b);
                        } else {
                            Scheduler scheduler = this.c;
                            long j2 = this.a;
                            disposable = scheduler.schedulePeriodicallyDirect(aVar, j2, j2, this.b);
                        }
                        if (this.m.replace(disposable)) {
                            subscription.request(Long.MAX_VALUE);
                            return;
                        }
                        return;
                    }
                    this.cancelled = true;
                    subscription.cancel();
                    subscriber.onError(new MissingBackpressureException("Could not deliver initial window due to lack of requests."));
                }
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (!this.l) {
                if (fastEnter()) {
                    UnicastProcessor<T> unicastProcessor = this.k;
                    unicastProcessor.onNext(t);
                    long j = this.h + 1;
                    if (j >= this.f) {
                        this.i++;
                        this.h = 0L;
                        unicastProcessor.onComplete();
                        long requested = requested();
                        if (requested != 0) {
                            UnicastProcessor<T> create = UnicastProcessor.create(this.d);
                            this.k = create;
                            this.downstream.onNext(create);
                            if (requested != Long.MAX_VALUE) {
                                produced(1L);
                            }
                            if (this.e) {
                                this.m.get().dispose();
                                Scheduler.Worker worker = this.g;
                                RunnableC0232a aVar = new RunnableC0232a(this.i, this);
                                long j2 = this.a;
                                this.m.replace(worker.schedulePeriodically(aVar, j2, j2, this.b));
                            }
                        } else {
                            this.k = null;
                            this.j.cancel();
                            this.downstream.onError(new MissingBackpressureException("Could not deliver window due to lack of requests"));
                            a();
                            return;
                        }
                    } else {
                        this.h = j;
                    }
                    if (leave(-1) == 0) {
                        return;
                    }
                } else {
                    this.queue.offer(NotificationLite.next(t));
                    if (!enter()) {
                        return;
                    }
                }
                b();
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.error = th;
            this.done = true;
            if (enter()) {
                b();
            }
            this.downstream.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.done = true;
            if (enter()) {
                b();
            }
            this.downstream.onComplete();
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            requested(j);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.cancelled = true;
        }

        public void a() {
            this.m.dispose();
            Scheduler.Worker worker = this.g;
            if (worker != null) {
                worker.dispose();
            }
        }

        void b() {
            SimplePlainQueue simplePlainQueue = this.queue;
            Subscriber subscriber = this.downstream;
            UnicastProcessor<T> unicastProcessor = this.k;
            int i = 1;
            while (!this.l) {
                boolean z = this.done;
                Object poll = simplePlainQueue.poll();
                boolean z2 = poll == null;
                boolean z3 = poll instanceof RunnableC0232a;
                if (z && (z2 || z3)) {
                    this.k = null;
                    simplePlainQueue.clear();
                    Throwable th = this.error;
                    if (th != null) {
                        unicastProcessor.onError(th);
                    } else {
                        unicastProcessor.onComplete();
                    }
                    a();
                    return;
                } else if (z2) {
                    i = leave(-i);
                    if (i == 0) {
                        return;
                    }
                } else if (z3) {
                    RunnableC0232a aVar = (RunnableC0232a) poll;
                    if (!this.e || this.i == aVar.a) {
                        unicastProcessor.onComplete();
                        this.h = 0L;
                        unicastProcessor = UnicastProcessor.create(this.d);
                        this.k = unicastProcessor;
                        long requested = requested();
                        if (requested != 0) {
                            subscriber.onNext(unicastProcessor);
                            if (requested != Long.MAX_VALUE) {
                                produced(1L);
                            }
                            i = i;
                        } else {
                            this.k = null;
                            this.queue.clear();
                            this.j.cancel();
                            subscriber.onError(new MissingBackpressureException("Could not deliver first window due to lack of requests."));
                            a();
                            return;
                        }
                    } else {
                        i = i;
                    }
                } else {
                    unicastProcessor.onNext(NotificationLite.getValue(poll));
                    long j = this.h + 1;
                    if (j >= this.f) {
                        this.i++;
                        this.h = 0L;
                        unicastProcessor.onComplete();
                        long requested2 = requested();
                        if (requested2 != 0) {
                            UnicastProcessor<T> create = UnicastProcessor.create(this.d);
                            this.k = create;
                            this.downstream.onNext(create);
                            if (requested2 != Long.MAX_VALUE) {
                                produced(1L);
                            }
                            if (this.e) {
                                this.m.get().dispose();
                                Scheduler.Worker worker = this.g;
                                RunnableC0232a aVar2 = new RunnableC0232a(this.i, this);
                                long j2 = this.a;
                                this.m.replace(worker.schedulePeriodically(aVar2, j2, j2, this.b));
                            }
                            unicastProcessor = create;
                        } else {
                            this.k = null;
                            this.j.cancel();
                            this.downstream.onError(new MissingBackpressureException("Could not deliver window due to lack of requests"));
                            a();
                            return;
                        }
                    } else {
                        this.h = j;
                    }
                    i = i;
                }
            }
            this.j.cancel();
            simplePlainQueue.clear();
            a();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: io.reactivex.internal.operators.flowable.FlowableWindowTimed$a$a  reason: collision with other inner class name */
        /* loaded from: classes4.dex */
        public static final class RunnableC0232a implements Runnable {
            final long a;
            final a<?> b;

            RunnableC0232a(long j, a<?> aVar) {
                this.a = j;
                this.b = aVar;
            }

            @Override // java.lang.Runnable
            public void run() {
                a<?> aVar = this.b;
                if (!aVar.cancelled) {
                    aVar.queue.offer(this);
                } else {
                    aVar.l = true;
                }
                if (aVar.enter()) {
                    aVar.b();
                }
            }
        }
    }

    /* loaded from: classes4.dex */
    static final class c<T> extends QueueDrainSubscriber<T, Object, Flowable<T>> implements Runnable, Subscription {
        final long a;
        final long b;
        final TimeUnit c;
        final Scheduler.Worker d;
        final int e;
        final List<UnicastProcessor<T>> f = new LinkedList();
        Subscription g;
        volatile boolean h;

        c(Subscriber<? super Flowable<T>> subscriber, long j, long j2, TimeUnit timeUnit, Scheduler.Worker worker, int i) {
            super(subscriber, new MpscLinkedQueue());
            this.a = j;
            this.b = j2;
            this.c = timeUnit;
            this.d = worker;
            this.e = i;
        }

        @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.g, subscription)) {
                this.g = subscription;
                this.downstream.onSubscribe(this);
                if (!this.cancelled) {
                    long requested = requested();
                    if (requested != 0) {
                        UnicastProcessor<T> create = UnicastProcessor.create(this.e);
                        this.f.add(create);
                        this.downstream.onNext(create);
                        if (requested != Long.MAX_VALUE) {
                            produced(1L);
                        }
                        this.d.schedule(new a(create), this.a, this.c);
                        Scheduler.Worker worker = this.d;
                        long j = this.b;
                        worker.schedulePeriodically(this, j, j, this.c);
                        subscription.request(Long.MAX_VALUE);
                        return;
                    }
                    subscription.cancel();
                    this.downstream.onError(new MissingBackpressureException("Could not emit the first window due to lack of requests"));
                }
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (fastEnter()) {
                for (UnicastProcessor<T> unicastProcessor : this.f) {
                    unicastProcessor.onNext(t);
                }
                if (leave(-1) == 0) {
                    return;
                }
            } else {
                this.queue.offer(t);
                if (!enter()) {
                    return;
                }
            }
            a();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.error = th;
            this.done = true;
            if (enter()) {
                a();
            }
            this.downstream.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.done = true;
            if (enter()) {
                a();
            }
            this.downstream.onComplete();
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            requested(j);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.cancelled = true;
        }

        void a(UnicastProcessor<T> unicastProcessor) {
            this.queue.offer(new b(unicastProcessor, false));
            if (enter()) {
                a();
            }
        }

        void a() {
            SimplePlainQueue simplePlainQueue = this.queue;
            Subscriber subscriber = this.downstream;
            List<UnicastProcessor<T>> list = this.f;
            int i = 1;
            while (!this.h) {
                boolean z = this.done;
                T t = (T) simplePlainQueue.poll();
                boolean z2 = t == null;
                boolean z3 = t instanceof b;
                if (z && (z2 || z3)) {
                    simplePlainQueue.clear();
                    Throwable th = this.error;
                    if (th != null) {
                        for (UnicastProcessor<T> unicastProcessor : list) {
                            unicastProcessor.onError(th);
                        }
                    } else {
                        for (UnicastProcessor<T> unicastProcessor2 : list) {
                            unicastProcessor2.onComplete();
                        }
                    }
                    list.clear();
                    this.d.dispose();
                    return;
                } else if (z2) {
                    i = leave(-i);
                    if (i == 0) {
                        return;
                    }
                } else if (z3) {
                    b bVar = (b) t;
                    if (!bVar.b) {
                        list.remove(bVar.a);
                        bVar.a.onComplete();
                        if (list.isEmpty() && this.cancelled) {
                            this.h = true;
                        }
                    } else if (!this.cancelled) {
                        long requested = requested();
                        if (requested != 0) {
                            UnicastProcessor<T> create = UnicastProcessor.create(this.e);
                            list.add(create);
                            subscriber.onNext(create);
                            if (requested != Long.MAX_VALUE) {
                                produced(1L);
                            }
                            this.d.schedule(new a(create), this.a, this.c);
                        } else {
                            subscriber.onError(new MissingBackpressureException("Can't emit window due to lack of requests"));
                        }
                    }
                } else {
                    for (UnicastProcessor<T> unicastProcessor3 : list) {
                        unicastProcessor3.onNext(t);
                    }
                }
            }
            this.g.cancel();
            simplePlainQueue.clear();
            list.clear();
            this.d.dispose();
        }

        @Override // java.lang.Runnable
        public void run() {
            b bVar = new b(UnicastProcessor.create(this.e), true);
            if (!this.cancelled) {
                this.queue.offer(bVar);
            }
            if (enter()) {
                a();
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* loaded from: classes4.dex */
        public static final class b<T> {
            final UnicastProcessor<T> a;
            final boolean b;

            b(UnicastProcessor<T> unicastProcessor, boolean z) {
                this.a = unicastProcessor;
                this.b = z;
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* loaded from: classes4.dex */
        public final class a implements Runnable {
            private final UnicastProcessor<T> b;

            a(UnicastProcessor<T> unicastProcessor) {
                this.b = unicastProcessor;
            }

            @Override // java.lang.Runnable
            public void run() {
                c.this.a(this.b);
            }
        }
    }
}
