package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.SequentialDisposable;
import io.reactivex.internal.observers.QueueDrainObserver;
import io.reactivex.internal.queue.MpscLinkedQueue;
import io.reactivex.internal.util.NotificationLite;
import io.reactivex.observers.SerializedObserver;
import io.reactivex.subjects.UnicastSubject;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public final class ObservableWindowTimed<T> extends a<T, Observable<T>> {
    final long a;
    final long b;
    final TimeUnit c;
    final Scheduler d;
    final long e;
    final int f;
    final boolean g;

    public ObservableWindowTimed(ObservableSource<T> observableSource, long j, long j2, TimeUnit timeUnit, Scheduler scheduler, long j3, int i, boolean z) {
        super(observableSource);
        this.a = j;
        this.b = j2;
        this.c = timeUnit;
        this.d = scheduler;
        this.e = j3;
        this.f = i;
        this.g = z;
    }

    @Override // io.reactivex.Observable
    public void subscribeActual(Observer<? super Observable<T>> observer) {
        SerializedObserver serializedObserver = new SerializedObserver(observer);
        if (this.a != this.b) {
            this.source.subscribe(new c(serializedObserver, this.a, this.b, this.c, this.d.createWorker(), this.f));
        } else if (this.e == Long.MAX_VALUE) {
            this.source.subscribe(new b(serializedObserver, this.a, this.c, this.d, this.f));
        } else {
            this.source.subscribe(new a(serializedObserver, this.a, this.c, this.d, this.f, this.e, this.g));
        }
    }

    /* loaded from: classes4.dex */
    static final class b<T> extends QueueDrainObserver<T, Object, Observable<T>> implements Observer<T>, Disposable, Runnable {
        static final Object i = new Object();
        final long b;
        final TimeUnit c;
        final Scheduler d;
        final int e;
        Disposable f;
        UnicastSubject<T> g;
        final SequentialDisposable h = new SequentialDisposable();
        volatile boolean j;

        b(Observer<? super Observable<T>> observer, long j, TimeUnit timeUnit, Scheduler scheduler, int i2) {
            super(observer, new MpscLinkedQueue());
            this.b = j;
            this.c = timeUnit;
            this.d = scheduler;
            this.e = i2;
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.f, disposable)) {
                this.f = disposable;
                this.g = UnicastSubject.create(this.e);
                Observer observer = this.downstream;
                observer.onSubscribe(this);
                observer.onNext(this.g);
                if (!this.cancelled) {
                    Scheduler scheduler = this.d;
                    long j = this.b;
                    this.h.replace(scheduler.schedulePeriodicallyDirect(this, j, j, this.c));
                }
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // io.reactivex.Observer
        public void onNext(T t) {
            if (!this.j) {
                if (fastEnter()) {
                    this.g.onNext(t);
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

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            this.error = th;
            this.done = true;
            if (enter()) {
                a();
            }
            this.downstream.onError(th);
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            this.done = true;
            if (enter()) {
                a();
            }
            this.downstream.onComplete();
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            this.cancelled = true;
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.cancelled;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // java.lang.Runnable
        public void run() {
            if (this.cancelled) {
                this.j = true;
            }
            this.queue.offer(i);
            if (enter()) {
                a();
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:10:0x0023, code lost:
            r2.onError(r0);
         */
        /* JADX WARN: Code restructure failed: missing block: B:11:0x0027, code lost:
            r2.onComplete();
         */
        /* JADX WARN: Code restructure failed: missing block: B:12:0x002a, code lost:
            r7.h.dispose();
         */
        /* JADX WARN: Code restructure failed: missing block: B:13:0x002f, code lost:
            return;
         */
        /* JADX WARN: Code restructure failed: missing block: B:8:0x0019, code lost:
            r7.g = null;
            r0.clear();
            r0 = r7.error;
         */
        /* JADX WARN: Code restructure failed: missing block: B:9:0x0021, code lost:
            if (r0 == null) goto L_0x0027;
         */
        /* JADX WARN: Multi-variable type inference failed */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        void a() {
            /*
                r7 = this;
                io.reactivex.internal.fuseable.SimplePlainQueue r0 = r7.queue
                io.reactivex.internal.queue.MpscLinkedQueue r0 = (io.reactivex.internal.queue.MpscLinkedQueue) r0
                io.reactivex.Observer r1 = r7.downstream
                io.reactivex.subjects.UnicastSubject<T> r2 = r7.g
                r3 = 1
            L_0x0009:
                boolean r4 = r7.j
                boolean r5 = r7.done
                java.lang.Object r6 = r0.poll()
                if (r5 == 0) goto L_0x0030
                if (r6 == 0) goto L_0x0019
                java.lang.Object r5 = io.reactivex.internal.operators.observable.ObservableWindowTimed.b.i
                if (r6 != r5) goto L_0x0030
            L_0x0019:
                r1 = 0
                r7.g = r1
                r0.clear()
                java.lang.Throwable r0 = r7.error
                if (r0 == 0) goto L_0x0027
                r2.onError(r0)
                goto L_0x002a
            L_0x0027:
                r2.onComplete()
            L_0x002a:
                io.reactivex.internal.disposables.SequentialDisposable r0 = r7.h
                r0.dispose()
                return
            L_0x0030:
                if (r6 != 0) goto L_0x003a
                int r3 = -r3
                int r3 = r7.leave(r3)
                if (r3 != 0) goto L_0x0009
                return
            L_0x003a:
                java.lang.Object r5 = io.reactivex.internal.operators.observable.ObservableWindowTimed.b.i
                if (r6 != r5) goto L_0x0055
                r2.onComplete()
                if (r4 != 0) goto L_0x004f
                int r2 = r7.e
                io.reactivex.subjects.UnicastSubject r2 = io.reactivex.subjects.UnicastSubject.create(r2)
                r7.g = r2
                r1.onNext(r2)
                goto L_0x0009
            L_0x004f:
                io.reactivex.disposables.Disposable r4 = r7.f
                r4.dispose()
                goto L_0x0009
            L_0x0055:
                java.lang.Object r4 = io.reactivex.internal.util.NotificationLite.getValue(r6)
                r2.onNext(r4)
                goto L_0x0009
            */
            throw new UnsupportedOperationException("Method not decompiled: io.reactivex.internal.operators.observable.ObservableWindowTimed.b.a():void");
        }
    }

    /* loaded from: classes4.dex */
    static final class a<T> extends QueueDrainObserver<T, Object, Observable<T>> implements Disposable {
        final long b;
        final TimeUnit c;
        final Scheduler d;
        final int e;
        final boolean f;
        final long g;
        final Scheduler.Worker h;
        long i;
        long j;
        Disposable k;
        UnicastSubject<T> l;
        volatile boolean m;
        final SequentialDisposable n = new SequentialDisposable();

        a(Observer<? super Observable<T>> observer, long j, TimeUnit timeUnit, Scheduler scheduler, int i, long j2, boolean z) {
            super(observer, new MpscLinkedQueue());
            this.b = j;
            this.c = timeUnit;
            this.d = scheduler;
            this.e = i;
            this.g = j2;
            this.f = z;
            if (z) {
                this.h = scheduler.createWorker();
            } else {
                this.h = null;
            }
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            Disposable disposable2;
            if (DisposableHelper.validate(this.k, disposable)) {
                this.k = disposable;
                Observer observer = this.downstream;
                observer.onSubscribe(this);
                if (!this.cancelled) {
                    UnicastSubject<T> create = UnicastSubject.create(this.e);
                    this.l = create;
                    observer.onNext(create);
                    RunnableC0270a aVar = new RunnableC0270a(this.j, this);
                    if (this.f) {
                        Scheduler.Worker worker = this.h;
                        long j = this.b;
                        disposable2 = worker.schedulePeriodically(aVar, j, j, this.c);
                    } else {
                        Scheduler scheduler = this.d;
                        long j2 = this.b;
                        disposable2 = scheduler.schedulePeriodicallyDirect(aVar, j2, j2, this.c);
                    }
                    this.n.replace(disposable2);
                }
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // io.reactivex.Observer
        public void onNext(T t) {
            if (!this.m) {
                if (fastEnter()) {
                    UnicastSubject<T> unicastSubject = this.l;
                    unicastSubject.onNext(t);
                    long j = this.i + 1;
                    if (j >= this.g) {
                        this.j++;
                        this.i = 0L;
                        unicastSubject.onComplete();
                        UnicastSubject<T> create = UnicastSubject.create(this.e);
                        this.l = create;
                        this.downstream.onNext(create);
                        if (this.f) {
                            this.n.get().dispose();
                            Scheduler.Worker worker = this.h;
                            RunnableC0270a aVar = new RunnableC0270a(this.j, this);
                            long j2 = this.b;
                            DisposableHelper.replace(this.n, worker.schedulePeriodically(aVar, j2, j2, this.c));
                        }
                    } else {
                        this.i = j;
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

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            this.error = th;
            this.done = true;
            if (enter()) {
                b();
            }
            this.downstream.onError(th);
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            this.done = true;
            if (enter()) {
                b();
            }
            this.downstream.onComplete();
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            this.cancelled = true;
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.cancelled;
        }

        void a() {
            DisposableHelper.dispose(this.n);
            Scheduler.Worker worker = this.h;
            if (worker != null) {
                worker.dispose();
            }
        }

        void b() {
            MpscLinkedQueue mpscLinkedQueue = (MpscLinkedQueue) this.queue;
            Observer observer = this.downstream;
            UnicastSubject<T> unicastSubject = this.l;
            int i = 1;
            while (!this.m) {
                boolean z = this.done;
                Object poll = mpscLinkedQueue.poll();
                boolean z2 = poll == null;
                boolean z3 = poll instanceof RunnableC0270a;
                if (z && (z2 || z3)) {
                    this.l = null;
                    mpscLinkedQueue.clear();
                    Throwable th = this.error;
                    if (th != null) {
                        unicastSubject.onError(th);
                    } else {
                        unicastSubject.onComplete();
                    }
                    a();
                    return;
                } else if (z2) {
                    i = leave(-i);
                    if (i == 0) {
                        return;
                    }
                } else if (z3) {
                    RunnableC0270a aVar = (RunnableC0270a) poll;
                    if (!this.f || this.j == aVar.a) {
                        unicastSubject.onComplete();
                        this.i = 0L;
                        unicastSubject = UnicastSubject.create(this.e);
                        this.l = unicastSubject;
                        observer.onNext(unicastSubject);
                    }
                } else {
                    unicastSubject.onNext((T) NotificationLite.getValue(poll));
                    long j = this.i + 1;
                    if (j >= this.g) {
                        this.j++;
                        this.i = 0L;
                        unicastSubject.onComplete();
                        unicastSubject = UnicastSubject.create(this.e);
                        this.l = unicastSubject;
                        this.downstream.onNext(unicastSubject);
                        if (this.f) {
                            Disposable disposable = this.n.get();
                            disposable.dispose();
                            Scheduler.Worker worker = this.h;
                            RunnableC0270a aVar2 = new RunnableC0270a(this.j, this);
                            long j2 = this.b;
                            Disposable schedulePeriodically = worker.schedulePeriodically(aVar2, j2, j2, this.c);
                            if (!this.n.compareAndSet(disposable, schedulePeriodically)) {
                                schedulePeriodically.dispose();
                            }
                        }
                    } else {
                        this.i = j;
                    }
                }
            }
            this.k.dispose();
            mpscLinkedQueue.clear();
            a();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: io.reactivex.internal.operators.observable.ObservableWindowTimed$a$a  reason: collision with other inner class name */
        /* loaded from: classes4.dex */
        public static final class RunnableC0270a implements Runnable {
            final long a;
            final a<?> b;

            RunnableC0270a(long j, a<?> aVar) {
                this.a = j;
                this.b = aVar;
            }

            @Override // java.lang.Runnable
            public void run() {
                a<?> aVar = this.b;
                if (!aVar.cancelled) {
                    aVar.queue.offer(this);
                } else {
                    aVar.m = true;
                }
                if (aVar.enter()) {
                    aVar.b();
                }
            }
        }
    }

    /* loaded from: classes4.dex */
    static final class c<T> extends QueueDrainObserver<T, Object, Observable<T>> implements Disposable, Runnable {
        final long b;
        final long c;
        final TimeUnit d;
        final Scheduler.Worker e;
        final int f;
        final List<UnicastSubject<T>> g = new LinkedList();
        Disposable h;
        volatile boolean i;

        c(Observer<? super Observable<T>> observer, long j, long j2, TimeUnit timeUnit, Scheduler.Worker worker, int i) {
            super(observer, new MpscLinkedQueue());
            this.b = j;
            this.c = j2;
            this.d = timeUnit;
            this.e = worker;
            this.f = i;
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.h, disposable)) {
                this.h = disposable;
                this.downstream.onSubscribe(this);
                if (!this.cancelled) {
                    UnicastSubject<T> create = UnicastSubject.create(this.f);
                    this.g.add(create);
                    this.downstream.onNext(create);
                    this.e.schedule(new a(create), this.b, this.d);
                    Scheduler.Worker worker = this.e;
                    long j = this.c;
                    worker.schedulePeriodically(this, j, j, this.d);
                }
            }
        }

        @Override // io.reactivex.Observer
        public void onNext(T t) {
            if (fastEnter()) {
                for (UnicastSubject<T> unicastSubject : this.g) {
                    unicastSubject.onNext(t);
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

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            this.error = th;
            this.done = true;
            if (enter()) {
                a();
            }
            this.downstream.onError(th);
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            this.done = true;
            if (enter()) {
                a();
            }
            this.downstream.onComplete();
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            this.cancelled = true;
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.cancelled;
        }

        void a(UnicastSubject<T> unicastSubject) {
            this.queue.offer(new b(unicastSubject, false));
            if (enter()) {
                a();
            }
        }

        void a() {
            MpscLinkedQueue mpscLinkedQueue = (MpscLinkedQueue) this.queue;
            Observer observer = this.downstream;
            List<UnicastSubject<T>> list = this.g;
            int i = 1;
            while (!this.i) {
                boolean z = this.done;
                T t = (T) mpscLinkedQueue.poll();
                boolean z2 = t == null;
                boolean z3 = t instanceof b;
                if (z && (z2 || z3)) {
                    mpscLinkedQueue.clear();
                    Throwable th = this.error;
                    if (th != null) {
                        for (UnicastSubject<T> unicastSubject : list) {
                            unicastSubject.onError(th);
                        }
                    } else {
                        for (UnicastSubject<T> unicastSubject2 : list) {
                            unicastSubject2.onComplete();
                        }
                    }
                    list.clear();
                    this.e.dispose();
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
                            this.i = true;
                        }
                    } else if (!this.cancelled) {
                        UnicastSubject<T> create = UnicastSubject.create(this.f);
                        list.add(create);
                        observer.onNext(create);
                        this.e.schedule(new a(create), this.b, this.d);
                    }
                } else {
                    for (UnicastSubject<T> unicastSubject3 : list) {
                        unicastSubject3.onNext(t);
                    }
                }
            }
            this.h.dispose();
            mpscLinkedQueue.clear();
            list.clear();
            this.e.dispose();
        }

        @Override // java.lang.Runnable
        public void run() {
            b bVar = new b(UnicastSubject.create(this.f), true);
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
            final UnicastSubject<T> a;
            final boolean b;

            b(UnicastSubject<T> unicastSubject, boolean z) {
                this.a = unicastSubject;
                this.b = z;
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* loaded from: classes4.dex */
        public final class a implements Runnable {
            private final UnicastSubject<T> b;

            a(UnicastSubject<T> unicastSubject) {
                this.b = unicastSubject;
            }

            @Override // java.lang.Runnable
            public void run() {
                c.this.a(this.b);
            }
        }
    }
}
