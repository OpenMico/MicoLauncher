package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.internal.disposables.SequentialDisposable;
import io.reactivex.rxjava3.internal.fuseable.SimplePlainQueue;
import io.reactivex.rxjava3.internal.queue.MpscLinkedQueue;
import io.reactivex.rxjava3.subjects.UnicastSubject;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes5.dex */
public final class ObservableWindowTimed<T> extends a<T, Observable<T>> {
    final long a;
    final long b;
    final TimeUnit c;
    final Scheduler d;
    final long e;
    final int f;
    final boolean g;

    public ObservableWindowTimed(Observable<T> observable, long j, long j2, TimeUnit timeUnit, Scheduler scheduler, long j3, int i, boolean z) {
        super(observable);
        this.a = j;
        this.b = j2;
        this.c = timeUnit;
        this.d = scheduler;
        this.e = j3;
        this.f = i;
        this.g = z;
    }

    @Override // io.reactivex.rxjava3.core.Observable
    protected void subscribeActual(Observer<? super Observable<T>> observer) {
        if (this.a != this.b) {
            this.source.subscribe(new d(observer, this.a, this.b, this.c, this.d.createWorker(), this.f));
        } else if (this.e == Long.MAX_VALUE) {
            this.source.subscribe(new c(observer, this.a, this.c, this.d, this.f));
        } else {
            this.source.subscribe(new b(observer, this.a, this.c, this.d, this.f, this.e, this.g));
        }
    }

    /* loaded from: classes5.dex */
    static abstract class a<T> extends AtomicInteger implements Observer<T>, Disposable {
        private static final long serialVersionUID = 5724293814035355511L;
        final int bufferSize;
        volatile boolean done;
        final Observer<? super Observable<T>> downstream;
        long emitted;
        Throwable error;
        final long timespan;
        final TimeUnit unit;
        Disposable upstream;
        volatile boolean upstreamCancelled;
        final SimplePlainQueue<Object> queue = new MpscLinkedQueue();
        final AtomicBoolean downstreamCancelled = new AtomicBoolean();
        final AtomicInteger windowCount = new AtomicInteger(1);

        abstract void a();

        abstract void c();

        abstract void d();

        a(Observer<? super Observable<T>> observer, long j, TimeUnit timeUnit, int i) {
            this.downstream = observer;
            this.timespan = j;
            this.unit = timeUnit;
            this.bufferSize = i;
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public final void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.upstream, disposable)) {
                this.upstream = disposable;
                this.downstream.onSubscribe(this);
                a();
            }
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public final void onNext(T t) {
            this.queue.offer(t);
            d();
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public final void onError(Throwable th) {
            this.error = th;
            this.done = true;
            d();
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public final void onComplete() {
            this.done = true;
            d();
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public final void dispose() {
            if (this.downstreamCancelled.compareAndSet(false, true)) {
                b();
            }
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public final boolean isDisposed() {
            return this.downstreamCancelled.get();
        }

        final void b() {
            if (this.windowCount.decrementAndGet() == 0) {
                c();
                this.upstream.dispose();
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
        UnicastSubject<T> window;
        final SequentialDisposable timer = new SequentialDisposable();
        final Runnable windowRunnable = new a();

        c(Observer<? super Observable<T>> observer, long j, TimeUnit timeUnit, Scheduler scheduler, int i) {
            super(observer, j, timeUnit, i);
            this.scheduler = scheduler;
        }

        @Override // io.reactivex.rxjava3.internal.operators.observable.ObservableWindowTimed.a
        void a() {
            if (!this.downstreamCancelled.get()) {
                this.windowCount.getAndIncrement();
                this.window = UnicastSubject.create(this.bufferSize, this.windowRunnable);
                this.emitted = 1L;
                b bVar = new b(this.window);
                this.downstream.onNext(bVar);
                this.timer.replace(this.scheduler.schedulePeriodicallyDirect(this, this.timespan, this.timespan, this.unit));
                if (bVar.a()) {
                    this.window.onComplete();
                }
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // java.lang.Runnable
        public void run() {
            this.queue.offer(a);
            d();
        }

        @Override // io.reactivex.rxjava3.internal.operators.observable.ObservableWindowTimed.a
        void d() {
            if (getAndIncrement() == 0) {
                SimplePlainQueue simplePlainQueue = this.queue;
                Observer observer = this.downstream;
                UnicastSubject<T> unicastSubject = this.window;
                int i = 1;
                while (true) {
                    if (this.upstreamCancelled) {
                        simplePlainQueue.clear();
                        this.window = null;
                        unicastSubject = null;
                    } else {
                        boolean z = this.done;
                        T t = (T) simplePlainQueue.poll();
                        boolean z2 = t == null;
                        if (z && z2) {
                            Throwable th = this.error;
                            if (th != null) {
                                if (unicastSubject != null) {
                                    unicastSubject.onError(th);
                                }
                                observer.onError(th);
                            } else {
                                if (unicastSubject != null) {
                                    unicastSubject.onComplete();
                                }
                                observer.onComplete();
                            }
                            c();
                            this.upstreamCancelled = true;
                        } else if (!z2) {
                            if (t == a) {
                                if (unicastSubject != null) {
                                    unicastSubject.onComplete();
                                    this.window = null;
                                    unicastSubject = null;
                                }
                                if (this.downstreamCancelled.get()) {
                                    this.timer.dispose();
                                } else {
                                    this.emitted++;
                                    this.windowCount.getAndIncrement();
                                    unicastSubject = UnicastSubject.create(this.bufferSize, this.windowRunnable);
                                    this.window = unicastSubject;
                                    b bVar = new b(unicastSubject);
                                    observer.onNext(bVar);
                                    if (bVar.a()) {
                                        unicastSubject.onComplete();
                                    }
                                }
                            } else if (unicastSubject != null) {
                                unicastSubject.onNext(t);
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

        @Override // io.reactivex.rxjava3.internal.operators.observable.ObservableWindowTimed.a
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
        UnicastSubject<T> window;
        final Scheduler.Worker worker;

        b(Observer<? super Observable<T>> observer, long j, TimeUnit timeUnit, Scheduler scheduler, int i, long j2, boolean z) {
            super(observer, j, timeUnit, i);
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

        @Override // io.reactivex.rxjava3.internal.operators.observable.ObservableWindowTimed.a
        void a() {
            if (!this.downstreamCancelled.get()) {
                this.emitted = 1L;
                this.windowCount.getAndIncrement();
                this.window = UnicastSubject.create(this.bufferSize, this);
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
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            b();
        }

        @Override // io.reactivex.rxjava3.internal.operators.observable.ObservableWindowTimed.a
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

        @Override // io.reactivex.rxjava3.internal.operators.observable.ObservableWindowTimed.a
        void d() {
            if (getAndIncrement() == 0) {
                SimplePlainQueue simplePlainQueue = this.queue;
                Observer observer = this.downstream;
                UnicastSubject<T> unicastSubject = this.window;
                int i = 1;
                while (true) {
                    if (this.upstreamCancelled) {
                        simplePlainQueue.clear();
                        this.window = null;
                        unicastSubject = null;
                    } else {
                        boolean z = this.done;
                        T t = (T) simplePlainQueue.poll();
                        boolean z2 = t == null;
                        if (z && z2) {
                            Throwable th = this.error;
                            if (th != null) {
                                if (unicastSubject != null) {
                                    unicastSubject.onError(th);
                                }
                                observer.onError(th);
                            } else {
                                if (unicastSubject != null) {
                                    unicastSubject.onComplete();
                                }
                                observer.onComplete();
                            }
                            c();
                            this.upstreamCancelled = true;
                        } else if (!z2) {
                            if (t instanceof a) {
                                if (((a) t).b == this.emitted || !this.restartTimerOnMaxSize) {
                                    this.count = 0L;
                                    unicastSubject = a(unicastSubject);
                                }
                            } else if (unicastSubject != null) {
                                unicastSubject.onNext(t);
                                long j = this.count + 1;
                                if (j == this.maxSize) {
                                    this.count = 0L;
                                    unicastSubject = a(unicastSubject);
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

        UnicastSubject<T> a(UnicastSubject<T> unicastSubject) {
            if (unicastSubject != null) {
                unicastSubject.onComplete();
                unicastSubject = null;
            }
            if (this.downstreamCancelled.get()) {
                c();
            } else {
                long j = this.emitted + 1;
                this.emitted = j;
                this.windowCount.getAndIncrement();
                unicastSubject = UnicastSubject.create(this.bufferSize, this);
                this.window = unicastSubject;
                b bVar = new b(unicastSubject);
                this.downstream.onNext(bVar);
                if (this.restartTimerOnMaxSize) {
                    this.timer.update(this.worker.schedulePeriodically(new a(this, j), this.timespan, this.timespan, this.unit));
                }
                if (bVar.a()) {
                    unicastSubject.onComplete();
                }
            }
            return unicastSubject;
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
        final List<UnicastSubject<T>> windows = new LinkedList();
        final Scheduler.Worker worker;

        d(Observer<? super Observable<T>> observer, long j, long j2, TimeUnit timeUnit, Scheduler.Worker worker, int i) {
            super(observer, j, timeUnit, i);
            this.timeskip = j2;
            this.worker = worker;
        }

        @Override // io.reactivex.rxjava3.internal.operators.observable.ObservableWindowTimed.a
        void a() {
            if (!this.downstreamCancelled.get()) {
                this.emitted = 1L;
                this.windowCount.getAndIncrement();
                UnicastSubject<T> create = UnicastSubject.create(this.bufferSize, this);
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
            }
        }

        @Override // io.reactivex.rxjava3.internal.operators.observable.ObservableWindowTimed.a
        void c() {
            this.worker.dispose();
        }

        @Override // io.reactivex.rxjava3.internal.operators.observable.ObservableWindowTimed.a
        void d() {
            if (getAndIncrement() == 0) {
                SimplePlainQueue simplePlainQueue = this.queue;
                Observer observer = this.downstream;
                List<UnicastSubject<T>> list = this.windows;
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
                                for (UnicastSubject<T> unicastSubject : list) {
                                    unicastSubject.onError(th);
                                }
                                observer.onError(th);
                            } else {
                                for (UnicastSubject<T> unicastSubject2 : list) {
                                    unicastSubject2.onComplete();
                                }
                                observer.onComplete();
                            }
                            c();
                            this.upstreamCancelled = true;
                        } else if (!z2) {
                            if (t == a) {
                                if (!this.downstreamCancelled.get()) {
                                    this.emitted++;
                                    this.windowCount.getAndIncrement();
                                    UnicastSubject<T> create = UnicastSubject.create(this.bufferSize, this);
                                    list.add(create);
                                    b bVar = new b(create);
                                    observer.onNext(bVar);
                                    this.worker.schedule(new a(this, false), this.timespan, this.unit);
                                    if (bVar.a()) {
                                        create.onComplete();
                                    }
                                }
                            } else if (t != b) {
                                for (UnicastSubject<T> unicastSubject3 : list) {
                                    unicastSubject3.onNext(t);
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
}
