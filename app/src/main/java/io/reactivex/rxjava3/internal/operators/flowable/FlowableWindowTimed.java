package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.exceptions.MissingBackpressureException;
import io.reactivex.rxjava3.internal.disposables.SequentialDisposable;
import io.reactivex.rxjava3.internal.fuseable.SimplePlainQueue;
import io.reactivex.rxjava3.internal.queue.MpscLinkedQueue;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.BackpressureHelper;
import io.reactivex.rxjava3.processors.UnicastProcessor;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes5.dex */
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

    @Override // io.reactivex.rxjava3.core.Flowable
    protected void subscribeActual(Subscriber<? super Flowable<T>> subscriber) {
        if (this.b != this.c) {
            this.source.subscribe((FlowableSubscriber) new d(subscriber, this.b, this.c, this.d, this.e.createWorker(), this.g));
        } else if (this.f == Long.MAX_VALUE) {
            this.source.subscribe((FlowableSubscriber) new c(subscriber, this.b, this.d, this.e, this.g));
        } else {
            this.source.subscribe((FlowableSubscriber) new b(subscriber, this.b, this.d, this.e, this.g, this.f, this.h));
        }
    }

    /* loaded from: classes5.dex */
    static abstract class a<T> extends AtomicInteger implements FlowableSubscriber<T>, Subscription {
        private static final long serialVersionUID = 5724293814035355511L;
        final int bufferSize;
        volatile boolean done;
        final Subscriber<? super Flowable<T>> downstream;
        long emitted;
        Throwable error;
        final long timespan;
        final TimeUnit unit;
        Subscription upstream;
        volatile boolean upstreamCancelled;
        final SimplePlainQueue<Object> queue = new MpscLinkedQueue();
        final AtomicLong requested = new AtomicLong();
        final AtomicBoolean downstreamCancelled = new AtomicBoolean();
        final AtomicInteger windowCount = new AtomicInteger(1);

        abstract void a();

        abstract void c();

        abstract void d();

        a(Subscriber<? super Flowable<T>> subscriber, long j, TimeUnit timeUnit, int i) {
            this.downstream = subscriber;
            this.timespan = j;
            this.unit = timeUnit;
            this.bufferSize = i;
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public final void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.upstream, subscription)) {
                this.upstream = subscription;
                this.downstream.onSubscribe(this);
                a();
            }
        }

        @Override // org.reactivestreams.Subscriber
        public final void onNext(T t) {
            this.queue.offer(t);
            d();
        }

        @Override // org.reactivestreams.Subscriber
        public final void onError(Throwable th) {
            this.error = th;
            this.done = true;
            d();
        }

        @Override // org.reactivestreams.Subscriber
        public final void onComplete() {
            this.done = true;
            d();
        }

        @Override // org.reactivestreams.Subscription
        public final void request(long j) {
            if (SubscriptionHelper.validate(j)) {
                BackpressureHelper.add(this.requested, j);
            }
        }

        @Override // org.reactivestreams.Subscription
        public final void cancel() {
            if (this.downstreamCancelled.compareAndSet(false, true)) {
                b();
            }
        }

        final void b() {
            if (this.windowCount.decrementAndGet() == 0) {
                c();
                this.upstream.cancel();
                this.upstreamCancelled = true;
                d();
            }
        }
    }

    /* loaded from: classes5.dex */
    static final class c<T> extends a<T> implements Runnable {
        static final Object a = new Object();
        private static final long serialVersionUID = 1155822639622580836L;
        final Scheduler scheduler;
        UnicastProcessor<T> window;
        final SequentialDisposable timer = new SequentialDisposable();
        final Runnable windowRunnable = new a();

        c(Subscriber<? super Flowable<T>> subscriber, long j, TimeUnit timeUnit, Scheduler scheduler, int i) {
            super(subscriber, j, timeUnit, i);
            this.scheduler = scheduler;
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableWindowTimed.a
        void a() {
            if (this.downstreamCancelled.get()) {
                return;
            }
            if (this.requested.get() != 0) {
                this.windowCount.getAndIncrement();
                this.window = UnicastProcessor.create(this.bufferSize, this.windowRunnable);
                this.emitted = 1L;
                b bVar = new b(this.window);
                this.downstream.onNext(bVar);
                this.timer.replace(this.scheduler.schedulePeriodicallyDirect(this, this.timespan, this.timespan, this.unit));
                if (bVar.a()) {
                    this.window.onComplete();
                }
                this.upstream.request(Long.MAX_VALUE);
                return;
            }
            this.upstream.cancel();
            this.downstream.onError(new MissingBackpressureException(FlowableWindowTimed.a(this.emitted)));
            c();
            this.upstreamCancelled = true;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // java.lang.Runnable
        public void run() {
            this.queue.offer(a);
            d();
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableWindowTimed.a
        void d() {
            if (getAndIncrement() == 0) {
                SimplePlainQueue simplePlainQueue = this.queue;
                Subscriber subscriber = this.downstream;
                UnicastProcessor<T> unicastProcessor = this.window;
                int i = 1;
                while (true) {
                    if (this.upstreamCancelled) {
                        simplePlainQueue.clear();
                        this.window = null;
                        unicastProcessor = null;
                    } else {
                        boolean z = this.done;
                        T t = (T) simplePlainQueue.poll();
                        boolean z2 = t == null;
                        if (z && z2) {
                            Throwable th = this.error;
                            if (th != null) {
                                if (unicastProcessor != null) {
                                    unicastProcessor.onError(th);
                                }
                                subscriber.onError(th);
                            } else {
                                if (unicastProcessor != null) {
                                    unicastProcessor.onComplete();
                                }
                                subscriber.onComplete();
                            }
                            c();
                            this.upstreamCancelled = true;
                        } else if (!z2) {
                            if (t == a) {
                                if (unicastProcessor != null) {
                                    unicastProcessor.onComplete();
                                    this.window = null;
                                    unicastProcessor = null;
                                }
                                if (this.downstreamCancelled.get()) {
                                    this.timer.dispose();
                                } else if (this.requested.get() == this.emitted) {
                                    this.upstream.cancel();
                                    c();
                                    this.upstreamCancelled = true;
                                    subscriber.onError(new MissingBackpressureException(FlowableWindowTimed.a(this.emitted)));
                                } else {
                                    this.emitted++;
                                    this.windowCount.getAndIncrement();
                                    unicastProcessor = UnicastProcessor.create(this.bufferSize, this.windowRunnable);
                                    this.window = unicastProcessor;
                                    b bVar = new b(unicastProcessor);
                                    subscriber.onNext(bVar);
                                    if (bVar.a()) {
                                        unicastProcessor.onComplete();
                                    }
                                }
                            } else if (unicastProcessor != null) {
                                unicastProcessor.onNext(t);
                            }
                        }
                    }
                    i = addAndGet(-i);
                    if (i == 0) {
                        return;
                    }
                }
            }
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableWindowTimed.a
        void c() {
            this.timer.dispose();
        }

        /* loaded from: classes5.dex */
        final class a implements Runnable {
            a() {
            }

            @Override // java.lang.Runnable
            public void run() {
                c.this.b();
            }
        }
    }

    /* loaded from: classes5.dex */
    static final class b<T> extends a<T> implements Runnable {
        private static final long serialVersionUID = -6130475889925953722L;
        long count;
        final long maxSize;
        final boolean restartTimerOnMaxSize;
        final Scheduler scheduler;
        final SequentialDisposable timer;
        UnicastProcessor<T> window;
        final Scheduler.Worker worker;

        b(Subscriber<? super Flowable<T>> subscriber, long j, TimeUnit timeUnit, Scheduler scheduler, int i, long j2, boolean z) {
            super(subscriber, j, timeUnit, i);
            this.scheduler = scheduler;
            this.maxSize = j2;
            this.restartTimerOnMaxSize = z;
            if (z) {
                this.worker = scheduler.createWorker();
            } else {
                this.worker = null;
            }
            this.timer = new SequentialDisposable();
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableWindowTimed.a
        void a() {
            if (this.downstreamCancelled.get()) {
                return;
            }
            if (this.requested.get() != 0) {
                this.emitted = 1L;
                this.windowCount.getAndIncrement();
                this.window = UnicastProcessor.create(this.bufferSize, this);
                b bVar = new b(this.window);
                this.downstream.onNext(bVar);
                a aVar = new a(this, 1L);
                if (this.restartTimerOnMaxSize) {
                    this.timer.replace(this.worker.schedulePeriodically(aVar, this.timespan, this.timespan, this.unit));
                } else {
                    this.timer.replace(this.scheduler.schedulePeriodicallyDirect(aVar, this.timespan, this.timespan, this.unit));
                }
                if (bVar.a()) {
                    this.window.onComplete();
                }
                this.upstream.request(Long.MAX_VALUE);
                return;
            }
            this.upstream.cancel();
            this.downstream.onError(new MissingBackpressureException(FlowableWindowTimed.a(this.emitted)));
            c();
            this.upstreamCancelled = true;
        }

        @Override // java.lang.Runnable
        public void run() {
            b();
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableWindowTimed.a
        void c() {
            this.timer.dispose();
            Scheduler.Worker worker = this.worker;
            if (worker != null) {
                worker.dispose();
            }
        }

        void a(a aVar) {
            this.queue.offer(aVar);
            d();
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableWindowTimed.a
        void d() {
            if (getAndIncrement() == 0) {
                SimplePlainQueue simplePlainQueue = this.queue;
                Subscriber subscriber = this.downstream;
                UnicastProcessor<T> unicastProcessor = this.window;
                int i = 1;
                while (true) {
                    if (this.upstreamCancelled) {
                        simplePlainQueue.clear();
                        this.window = null;
                        unicastProcessor = null;
                    } else {
                        boolean z = this.done;
                        T t = (T) simplePlainQueue.poll();
                        boolean z2 = t == null;
                        if (z && z2) {
                            Throwable th = this.error;
                            if (th != null) {
                                if (unicastProcessor != null) {
                                    unicastProcessor.onError(th);
                                }
                                subscriber.onError(th);
                            } else {
                                if (unicastProcessor != null) {
                                    unicastProcessor.onComplete();
                                }
                                subscriber.onComplete();
                            }
                            c();
                            this.upstreamCancelled = true;
                        } else if (!z2) {
                            if (t instanceof a) {
                                if (((a) t).b == this.emitted || !this.restartTimerOnMaxSize) {
                                    this.count = 0L;
                                    unicastProcessor = a(unicastProcessor);
                                }
                            } else if (unicastProcessor != null) {
                                unicastProcessor.onNext(t);
                                long j = this.count + 1;
                                if (j == this.maxSize) {
                                    this.count = 0L;
                                    unicastProcessor = a(unicastProcessor);
                                } else {
                                    this.count = j;
                                }
                            }
                        }
                    }
                    i = addAndGet(-i);
                    if (i == 0) {
                        return;
                    }
                }
            }
        }

        UnicastProcessor<T> a(UnicastProcessor<T> unicastProcessor) {
            if (unicastProcessor != null) {
                unicastProcessor.onComplete();
                unicastProcessor = null;
            }
            if (this.downstreamCancelled.get()) {
                c();
            } else {
                long j = this.emitted;
                if (this.requested.get() == j) {
                    this.upstream.cancel();
                    c();
                    this.upstreamCancelled = true;
                    this.downstream.onError(new MissingBackpressureException(FlowableWindowTimed.a(j)));
                } else {
                    long j2 = j + 1;
                    this.emitted = j2;
                    this.windowCount.getAndIncrement();
                    unicastProcessor = UnicastProcessor.create(this.bufferSize, this);
                    this.window = unicastProcessor;
                    b bVar = new b(unicastProcessor);
                    this.downstream.onNext(bVar);
                    if (this.restartTimerOnMaxSize) {
                        this.timer.update(this.worker.schedulePeriodically(new a(this, j2), this.timespan, this.timespan, this.unit));
                    }
                    if (bVar.a()) {
                        unicastProcessor.onComplete();
                    }
                }
            }
            return unicastProcessor;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* loaded from: classes5.dex */
        public static final class a implements Runnable {
            final b<?> a;
            final long b;

            a(b<?> bVar, long j) {
                this.a = bVar;
                this.b = j;
            }

            @Override // java.lang.Runnable
            public void run() {
                this.a.a(this);
            }
        }
    }

    /* loaded from: classes5.dex */
    static final class d<T> extends a<T> implements Runnable {
        static final Object a = new Object();
        static final Object b = new Object();
        private static final long serialVersionUID = -7852870764194095894L;
        final long timeskip;
        final List<UnicastProcessor<T>> windows = new LinkedList();
        final Scheduler.Worker worker;

        d(Subscriber<? super Flowable<T>> subscriber, long j, long j2, TimeUnit timeUnit, Scheduler.Worker worker, int i) {
            super(subscriber, j, timeUnit, i);
            this.timeskip = j2;
            this.worker = worker;
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableWindowTimed.a
        void a() {
            if (this.downstreamCancelled.get()) {
                return;
            }
            if (this.requested.get() != 0) {
                this.emitted = 1L;
                this.windowCount.getAndIncrement();
                UnicastProcessor<T> create = UnicastProcessor.create(this.bufferSize, this);
                this.windows.add(create);
                b bVar = new b(create);
                this.downstream.onNext(bVar);
                this.worker.schedule(new a(this, false), this.timespan, this.unit);
                Scheduler.Worker worker = this.worker;
                a aVar = new a(this, true);
                long j = this.timeskip;
                worker.schedulePeriodically(aVar, j, j, this.unit);
                if (bVar.a()) {
                    create.onComplete();
                    this.windows.remove(create);
                }
                this.upstream.request(Long.MAX_VALUE);
                return;
            }
            this.upstream.cancel();
            this.downstream.onError(new MissingBackpressureException(FlowableWindowTimed.a(this.emitted)));
            c();
            this.upstreamCancelled = true;
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableWindowTimed.a
        void c() {
            this.worker.dispose();
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableWindowTimed.a
        void d() {
            if (getAndIncrement() == 0) {
                SimplePlainQueue simplePlainQueue = this.queue;
                Subscriber subscriber = this.downstream;
                List<UnicastProcessor<T>> list = this.windows;
                int i = 1;
                while (true) {
                    if (this.upstreamCancelled) {
                        simplePlainQueue.clear();
                        list.clear();
                    } else {
                        boolean z = this.done;
                        T t = (T) simplePlainQueue.poll();
                        boolean z2 = t == null;
                        if (z && z2) {
                            Throwable th = this.error;
                            if (th != null) {
                                for (UnicastProcessor<T> unicastProcessor : list) {
                                    unicastProcessor.onError(th);
                                }
                                subscriber.onError(th);
                            } else {
                                for (UnicastProcessor<T> unicastProcessor2 : list) {
                                    unicastProcessor2.onComplete();
                                }
                                subscriber.onComplete();
                            }
                            c();
                            this.upstreamCancelled = true;
                        } else if (!z2) {
                            if (t == a) {
                                if (!this.downstreamCancelled.get()) {
                                    long j = this.emitted;
                                    if (this.requested.get() != j) {
                                        this.emitted = j + 1;
                                        this.windowCount.getAndIncrement();
                                        UnicastProcessor<T> create = UnicastProcessor.create(this.bufferSize, this);
                                        list.add(create);
                                        b bVar = new b(create);
                                        subscriber.onNext(bVar);
                                        this.worker.schedule(new a(this, false), this.timespan, this.unit);
                                        if (bVar.a()) {
                                            create.onComplete();
                                        }
                                    } else {
                                        this.upstream.cancel();
                                        MissingBackpressureException missingBackpressureException = new MissingBackpressureException(FlowableWindowTimed.a(j));
                                        for (UnicastProcessor<T> unicastProcessor3 : list) {
                                            unicastProcessor3.onError(missingBackpressureException);
                                        }
                                        subscriber.onError(missingBackpressureException);
                                        c();
                                        this.upstreamCancelled = true;
                                    }
                                }
                            } else if (t != b) {
                                for (UnicastProcessor<T> unicastProcessor4 : list) {
                                    unicastProcessor4.onNext(t);
                                }
                            } else if (!list.isEmpty()) {
                                list.remove(0).onComplete();
                            }
                        }
                    }
                    i = addAndGet(-i);
                    if (i == 0) {
                        return;
                    }
                }
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            b();
        }

        /* JADX WARN: Multi-variable type inference failed */
        void a(boolean z) {
            this.queue.offer(z ? a : b);
            d();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* loaded from: classes5.dex */
        public static final class a implements Runnable {
            final d<?> a;
            final boolean b;

            a(d<?> dVar, boolean z) {
                this.a = dVar;
                this.b = z;
            }

            @Override // java.lang.Runnable
            public void run() {
                this.a.a(this.b);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static String a(long j) {
        return "Unable to emit the next window (#" + j + ") due to lack of requests. Please make sure the downstream is ready to consume windows.";
    }
}
