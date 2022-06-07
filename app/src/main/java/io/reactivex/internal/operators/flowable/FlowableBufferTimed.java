package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.queue.MpscLinkedQueue;
import io.reactivex.internal.subscribers.QueueDrainSubscriber;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.QueueDrainHelper;
import io.reactivex.subscribers.SerializedSubscriber;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes4.dex */
public final class FlowableBufferTimed<T, U extends Collection<? super T>> extends a<T, U> {
    final long b;
    final long c;
    final TimeUnit d;
    final Scheduler e;
    final Callable<U> f;
    final int g;
    final boolean h;

    public FlowableBufferTimed(Flowable<T> flowable, long j, long j2, TimeUnit timeUnit, Scheduler scheduler, Callable<U> callable, int i, boolean z) {
        super(flowable);
        this.b = j;
        this.c = j2;
        this.d = timeUnit;
        this.e = scheduler;
        this.f = callable;
        this.g = i;
        this.h = z;
    }

    @Override // io.reactivex.Flowable
    protected void subscribeActual(Subscriber<? super U> subscriber) {
        if (this.b == this.c && this.g == Integer.MAX_VALUE) {
            this.source.subscribe((FlowableSubscriber) new b(new SerializedSubscriber(subscriber), this.f, this.b, this.d, this.e));
            return;
        }
        Scheduler.Worker createWorker = this.e.createWorker();
        if (this.b == this.c) {
            this.source.subscribe((FlowableSubscriber) new a(new SerializedSubscriber(subscriber), this.f, this.b, this.d, this.g, this.h, createWorker));
        } else {
            this.source.subscribe((FlowableSubscriber) new c(new SerializedSubscriber(subscriber), this.f, this.b, this.c, this.d, createWorker));
        }
    }

    /* loaded from: classes4.dex */
    static final class b<T, U extends Collection<? super T>> extends QueueDrainSubscriber<T, U, U> implements Disposable, Runnable, Subscription {
        final Callable<U> a;
        final long b;
        final TimeUnit c;
        final Scheduler d;
        Subscription e;
        U f;
        final AtomicReference<Disposable> g = new AtomicReference<>();

        b(Subscriber<? super U> subscriber, Callable<U> callable, long j, TimeUnit timeUnit, Scheduler scheduler) {
            super(subscriber, new MpscLinkedQueue());
            this.a = callable;
            this.b = j;
            this.c = timeUnit;
            this.d = scheduler;
        }

        @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.e, subscription)) {
                this.e = subscription;
                try {
                    this.f = (U) ((Collection) ObjectHelper.requireNonNull(this.a.call(), "The supplied buffer is null"));
                    this.downstream.onSubscribe(this);
                    if (!this.cancelled) {
                        subscription.request(Long.MAX_VALUE);
                        Scheduler scheduler = this.d;
                        long j = this.b;
                        Disposable schedulePeriodicallyDirect = scheduler.schedulePeriodicallyDirect(this, j, j, this.c);
                        if (!this.g.compareAndSet(null, schedulePeriodicallyDirect)) {
                            schedulePeriodicallyDirect.dispose();
                        }
                    }
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    cancel();
                    EmptySubscription.error(th, this.downstream);
                }
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            synchronized (this) {
                U u = this.f;
                if (u != null) {
                    u.add(t);
                }
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            DisposableHelper.dispose(this.g);
            synchronized (this) {
                this.f = null;
            }
            this.downstream.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            DisposableHelper.dispose(this.g);
            synchronized (this) {
                U u = this.f;
                if (u != null) {
                    this.f = null;
                    this.queue.offer(u);
                    this.done = true;
                    if (enter()) {
                        QueueDrainHelper.drainMaxLoop(this.queue, this.downstream, false, null, this);
                    }
                }
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            requested(j);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.cancelled = true;
            this.e.cancel();
            DisposableHelper.dispose(this.g);
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                U u = (U) ((Collection) ObjectHelper.requireNonNull(this.a.call(), "The supplied buffer is null"));
                synchronized (this) {
                    U u2 = this.f;
                    if (u2 != null) {
                        this.f = u;
                        fastPathEmitMax(u2, false, this);
                    }
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                cancel();
                this.downstream.onError(th);
            }
        }

        /* renamed from: a */
        public boolean accept(Subscriber<? super U> subscriber, U u) {
            this.downstream.onNext(u);
            return true;
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            cancel();
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.g.get() == DisposableHelper.DISPOSED;
        }
    }

    /* loaded from: classes4.dex */
    static final class c<T, U extends Collection<? super T>> extends QueueDrainSubscriber<T, U, U> implements Runnable, Subscription {
        final Callable<U> a;
        final long b;
        final long c;
        final TimeUnit d;
        final Scheduler.Worker e;
        final List<U> f = new LinkedList();
        Subscription g;

        c(Subscriber<? super U> subscriber, Callable<U> callable, long j, long j2, TimeUnit timeUnit, Scheduler.Worker worker) {
            super(subscriber, new MpscLinkedQueue());
            this.a = callable;
            this.b = j;
            this.c = j2;
            this.d = timeUnit;
            this.e = worker;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.g, subscription)) {
                this.g = subscription;
                try {
                    Collection collection = (Collection) ObjectHelper.requireNonNull(this.a.call(), "The supplied buffer is null");
                    this.f.add(collection);
                    this.downstream.onSubscribe(this);
                    subscription.request(Long.MAX_VALUE);
                    Scheduler.Worker worker = this.e;
                    long j = this.c;
                    worker.schedulePeriodically(this, j, j, this.d);
                    this.e.schedule(new a(collection), this.b, this.d);
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    this.e.dispose();
                    subscription.cancel();
                    EmptySubscription.error(th, this.downstream);
                }
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            synchronized (this) {
                for (U u : this.f) {
                    u.add(t);
                }
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.done = true;
            this.e.dispose();
            a();
            this.downstream.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            ArrayList<Collection> arrayList;
            synchronized (this) {
                arrayList = new ArrayList(this.f);
                this.f.clear();
            }
            for (Collection collection : arrayList) {
                this.queue.offer(collection);
            }
            this.done = true;
            if (enter()) {
                QueueDrainHelper.drainMaxLoop(this.queue, this.downstream, false, this.e, this);
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            requested(j);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.cancelled = true;
            this.g.cancel();
            this.e.dispose();
            a();
        }

        void a() {
            synchronized (this) {
                this.f.clear();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // java.lang.Runnable
        public void run() {
            if (!this.cancelled) {
                try {
                    Collection collection = (Collection) ObjectHelper.requireNonNull(this.a.call(), "The supplied buffer is null");
                    synchronized (this) {
                        if (!this.cancelled) {
                            this.f.add(collection);
                            this.e.schedule(new a(collection), this.b, this.d);
                        }
                    }
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    cancel();
                    this.downstream.onError(th);
                }
            }
        }

        /* renamed from: a */
        public boolean accept(Subscriber<? super U> subscriber, U u) {
            subscriber.onNext(u);
            return true;
        }

        /* JADX WARN: Incorrect field signature: TU; */
        /* loaded from: classes4.dex */
        final class a implements Runnable {
            private final Collection b;

            a(U u) {
                this.b = u;
            }

            @Override // java.lang.Runnable
            public void run() {
                synchronized (c.this) {
                    c.this.f.remove(this.b);
                }
                c cVar = c.this;
                cVar.fastPathOrderedEmitMax(this.b, false, cVar.e);
            }
        }
    }

    /* loaded from: classes4.dex */
    static final class a<T, U extends Collection<? super T>> extends QueueDrainSubscriber<T, U, U> implements Disposable, Runnable, Subscription {
        final Callable<U> a;
        final long b;
        final TimeUnit c;
        final int d;
        final boolean e;
        final Scheduler.Worker f;
        U g;
        Disposable h;
        Subscription i;
        long j;
        long k;

        a(Subscriber<? super U> subscriber, Callable<U> callable, long j, TimeUnit timeUnit, int i, boolean z, Scheduler.Worker worker) {
            super(subscriber, new MpscLinkedQueue());
            this.a = callable;
            this.b = j;
            this.c = timeUnit;
            this.d = i;
            this.e = z;
            this.f = worker;
        }

        @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.i, subscription)) {
                this.i = subscription;
                try {
                    this.g = (U) ((Collection) ObjectHelper.requireNonNull(this.a.call(), "The supplied buffer is null"));
                    this.downstream.onSubscribe(this);
                    Scheduler.Worker worker = this.f;
                    long j = this.b;
                    this.h = worker.schedulePeriodically(this, j, j, this.c);
                    subscription.request(Long.MAX_VALUE);
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    this.f.dispose();
                    subscription.cancel();
                    EmptySubscription.error(th, this.downstream);
                }
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            synchronized (this) {
                U u = this.g;
                if (u != null) {
                    u.add(t);
                    if (u.size() >= this.d) {
                        this.g = null;
                        this.j++;
                        if (this.e) {
                            this.h.dispose();
                        }
                        fastPathOrderedEmitMax(u, false, this);
                        try {
                            U u2 = (U) ((Collection) ObjectHelper.requireNonNull(this.a.call(), "The supplied buffer is null"));
                            synchronized (this) {
                                this.g = u2;
                                this.k++;
                            }
                            if (this.e) {
                                Scheduler.Worker worker = this.f;
                                long j = this.b;
                                this.h = worker.schedulePeriodically(this, j, j, this.c);
                            }
                        } catch (Throwable th) {
                            Exceptions.throwIfFatal(th);
                            cancel();
                            this.downstream.onError(th);
                        }
                    }
                }
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            synchronized (this) {
                this.g = null;
            }
            this.downstream.onError(th);
            this.f.dispose();
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            U u;
            synchronized (this) {
                u = this.g;
                this.g = null;
            }
            if (u != null) {
                this.queue.offer(u);
                this.done = true;
                if (enter()) {
                    QueueDrainHelper.drainMaxLoop(this.queue, this.downstream, false, this, this);
                }
                this.f.dispose();
            }
        }

        /* renamed from: a */
        public boolean accept(Subscriber<? super U> subscriber, U u) {
            subscriber.onNext(u);
            return true;
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            requested(j);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (!this.cancelled) {
                this.cancelled = true;
                dispose();
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            synchronized (this) {
                this.g = null;
            }
            this.i.cancel();
            this.f.dispose();
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.f.isDisposed();
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                U u = (U) ((Collection) ObjectHelper.requireNonNull(this.a.call(), "The supplied buffer is null"));
                synchronized (this) {
                    U u2 = this.g;
                    if (u2 != null && this.j == this.k) {
                        this.g = u;
                        fastPathOrderedEmitMax(u2, false, this);
                    }
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                cancel();
                this.downstream.onError(th);
            }
        }
    }
}
