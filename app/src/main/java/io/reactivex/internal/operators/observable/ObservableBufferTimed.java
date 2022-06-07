package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.observers.QueueDrainObserver;
import io.reactivex.internal.queue.MpscLinkedQueue;
import io.reactivex.internal.util.QueueDrainHelper;
import io.reactivex.observers.SerializedObserver;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes4.dex */
public final class ObservableBufferTimed<T, U extends Collection<? super T>> extends a<T, U> {
    final long a;
    final long b;
    final TimeUnit c;
    final Scheduler d;
    final Callable<U> e;
    final int f;
    final boolean g;

    public ObservableBufferTimed(ObservableSource<T> observableSource, long j, long j2, TimeUnit timeUnit, Scheduler scheduler, Callable<U> callable, int i, boolean z) {
        super(observableSource);
        this.a = j;
        this.b = j2;
        this.c = timeUnit;
        this.d = scheduler;
        this.e = callable;
        this.f = i;
        this.g = z;
    }

    @Override // io.reactivex.Observable
    protected void subscribeActual(Observer<? super U> observer) {
        if (this.a == this.b && this.f == Integer.MAX_VALUE) {
            this.source.subscribe(new b(new SerializedObserver(observer), this.e, this.a, this.c, this.d));
            return;
        }
        Scheduler.Worker createWorker = this.d.createWorker();
        if (this.a == this.b) {
            this.source.subscribe(new a(new SerializedObserver(observer), this.e, this.a, this.c, this.f, this.g, createWorker));
        } else {
            this.source.subscribe(new c(new SerializedObserver(observer), this.e, this.a, this.b, this.c, createWorker));
        }
    }

    /* loaded from: classes4.dex */
    static final class b<T, U extends Collection<? super T>> extends QueueDrainObserver<T, U, U> implements Disposable, Runnable {
        final Callable<U> b;
        final long c;
        final TimeUnit d;
        final Scheduler e;
        Disposable f;
        U g;
        final AtomicReference<Disposable> h = new AtomicReference<>();

        b(Observer<? super U> observer, Callable<U> callable, long j, TimeUnit timeUnit, Scheduler scheduler) {
            super(observer, new MpscLinkedQueue());
            this.b = callable;
            this.c = j;
            this.d = timeUnit;
            this.e = scheduler;
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.f, disposable)) {
                this.f = disposable;
                try {
                    this.g = (U) ((Collection) ObjectHelper.requireNonNull(this.b.call(), "The buffer supplied is null"));
                    this.downstream.onSubscribe(this);
                    if (!this.cancelled) {
                        Scheduler scheduler = this.e;
                        long j = this.c;
                        Disposable schedulePeriodicallyDirect = scheduler.schedulePeriodicallyDirect(this, j, j, this.d);
                        if (!this.h.compareAndSet(null, schedulePeriodicallyDirect)) {
                            schedulePeriodicallyDirect.dispose();
                        }
                    }
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    dispose();
                    EmptyDisposable.error(th, this.downstream);
                }
            }
        }

        @Override // io.reactivex.Observer
        public void onNext(T t) {
            synchronized (this) {
                U u = this.g;
                if (u != null) {
                    u.add(t);
                }
            }
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            synchronized (this) {
                this.g = null;
            }
            this.downstream.onError(th);
            DisposableHelper.dispose(this.h);
        }

        @Override // io.reactivex.Observer
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
                    QueueDrainHelper.drainLoop(this.queue, this.downstream, false, null, this);
                }
            }
            DisposableHelper.dispose(this.h);
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            DisposableHelper.dispose(this.h);
            this.f.dispose();
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.h.get() == DisposableHelper.DISPOSED;
        }

        @Override // java.lang.Runnable
        public void run() {
            U u;
            try {
                U u2 = (U) ((Collection) ObjectHelper.requireNonNull(this.b.call(), "The bufferSupplier returned a null buffer"));
                synchronized (this) {
                    u = this.g;
                    if (u != null) {
                        this.g = u2;
                    }
                }
                if (u == null) {
                    DisposableHelper.dispose(this.h);
                } else {
                    fastPathEmit(u, false, this);
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.downstream.onError(th);
                dispose();
            }
        }

        /* renamed from: a */
        public void accept(Observer<? super U> observer, U u) {
            this.downstream.onNext(u);
        }
    }

    /* loaded from: classes4.dex */
    static final class c<T, U extends Collection<? super T>> extends QueueDrainObserver<T, U, U> implements Disposable, Runnable {
        final Callable<U> b;
        final long c;
        final long d;
        final TimeUnit e;
        final Scheduler.Worker f;
        final List<U> g = new LinkedList();
        Disposable h;

        c(Observer<? super U> observer, Callable<U> callable, long j, long j2, TimeUnit timeUnit, Scheduler.Worker worker) {
            super(observer, new MpscLinkedQueue());
            this.b = callable;
            this.c = j;
            this.d = j2;
            this.e = timeUnit;
            this.f = worker;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.h, disposable)) {
                this.h = disposable;
                try {
                    Collection collection = (Collection) ObjectHelper.requireNonNull(this.b.call(), "The buffer supplied is null");
                    this.g.add(collection);
                    this.downstream.onSubscribe(this);
                    Scheduler.Worker worker = this.f;
                    long j = this.d;
                    worker.schedulePeriodically(this, j, j, this.e);
                    this.f.schedule(new b(collection), this.c, this.e);
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    disposable.dispose();
                    EmptyDisposable.error(th, this.downstream);
                    this.f.dispose();
                }
            }
        }

        @Override // io.reactivex.Observer
        public void onNext(T t) {
            synchronized (this) {
                for (U u : this.g) {
                    u.add(t);
                }
            }
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            this.done = true;
            a();
            this.downstream.onError(th);
            this.f.dispose();
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            ArrayList<Collection> arrayList;
            synchronized (this) {
                arrayList = new ArrayList(this.g);
                this.g.clear();
            }
            for (Collection collection : arrayList) {
                this.queue.offer(collection);
            }
            this.done = true;
            if (enter()) {
                QueueDrainHelper.drainLoop(this.queue, this.downstream, false, this.f, this);
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            if (!this.cancelled) {
                this.cancelled = true;
                a();
                this.h.dispose();
                this.f.dispose();
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.cancelled;
        }

        void a() {
            synchronized (this) {
                this.g.clear();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // java.lang.Runnable
        public void run() {
            if (!this.cancelled) {
                try {
                    Collection collection = (Collection) ObjectHelper.requireNonNull(this.b.call(), "The bufferSupplier returned a null buffer");
                    synchronized (this) {
                        if (!this.cancelled) {
                            this.g.add(collection);
                            this.f.schedule(new a(collection), this.c, this.e);
                        }
                    }
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    this.downstream.onError(th);
                    dispose();
                }
            }
        }

        /* renamed from: a */
        public void accept(Observer<? super U> observer, U u) {
            observer.onNext(u);
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
                    c.this.g.remove(this.b);
                }
                c cVar = c.this;
                cVar.fastPathOrderedEmit(this.b, false, cVar.f);
            }
        }

        /* JADX WARN: Incorrect field signature: TU; */
        /* loaded from: classes4.dex */
        final class b implements Runnable {
            private final Collection b;

            b(U u) {
                this.b = u;
            }

            @Override // java.lang.Runnable
            public void run() {
                synchronized (c.this) {
                    c.this.g.remove(this.b);
                }
                c cVar = c.this;
                cVar.fastPathOrderedEmit(this.b, false, cVar.f);
            }
        }
    }

    /* loaded from: classes4.dex */
    static final class a<T, U extends Collection<? super T>> extends QueueDrainObserver<T, U, U> implements Disposable, Runnable {
        final Callable<U> b;
        final long c;
        final TimeUnit d;
        final int e;
        final boolean f;
        final Scheduler.Worker g;
        U h;
        Disposable i;
        Disposable j;
        long k;
        long l;

        a(Observer<? super U> observer, Callable<U> callable, long j, TimeUnit timeUnit, int i, boolean z, Scheduler.Worker worker) {
            super(observer, new MpscLinkedQueue());
            this.b = callable;
            this.c = j;
            this.d = timeUnit;
            this.e = i;
            this.f = z;
            this.g = worker;
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.j, disposable)) {
                this.j = disposable;
                try {
                    this.h = (U) ((Collection) ObjectHelper.requireNonNull(this.b.call(), "The buffer supplied is null"));
                    this.downstream.onSubscribe(this);
                    Scheduler.Worker worker = this.g;
                    long j = this.c;
                    this.i = worker.schedulePeriodically(this, j, j, this.d);
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    disposable.dispose();
                    EmptyDisposable.error(th, this.downstream);
                    this.g.dispose();
                }
            }
        }

        @Override // io.reactivex.Observer
        public void onNext(T t) {
            synchronized (this) {
                U u = this.h;
                if (u != null) {
                    u.add(t);
                    if (u.size() >= this.e) {
                        this.h = null;
                        this.k++;
                        if (this.f) {
                            this.i.dispose();
                        }
                        fastPathOrderedEmit(u, false, this);
                        try {
                            U u2 = (U) ((Collection) ObjectHelper.requireNonNull(this.b.call(), "The buffer supplied is null"));
                            synchronized (this) {
                                this.h = u2;
                                this.l++;
                            }
                            if (this.f) {
                                Scheduler.Worker worker = this.g;
                                long j = this.c;
                                this.i = worker.schedulePeriodically(this, j, j, this.d);
                            }
                        } catch (Throwable th) {
                            Exceptions.throwIfFatal(th);
                            this.downstream.onError(th);
                            dispose();
                        }
                    }
                }
            }
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            synchronized (this) {
                this.h = null;
            }
            this.downstream.onError(th);
            this.g.dispose();
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            U u;
            this.g.dispose();
            synchronized (this) {
                u = this.h;
                this.h = null;
            }
            if (u != null) {
                this.queue.offer(u);
                this.done = true;
                if (enter()) {
                    QueueDrainHelper.drainLoop(this.queue, this.downstream, false, this, this);
                }
            }
        }

        /* renamed from: a */
        public void accept(Observer<? super U> observer, U u) {
            observer.onNext(u);
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            if (!this.cancelled) {
                this.cancelled = true;
                this.j.dispose();
                this.g.dispose();
                synchronized (this) {
                    this.h = null;
                }
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.cancelled;
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                U u = (U) ((Collection) ObjectHelper.requireNonNull(this.b.call(), "The bufferSupplier returned a null buffer"));
                synchronized (this) {
                    U u2 = this.h;
                    if (u2 != null && this.k == this.l) {
                        this.h = u;
                        fastPathOrderedEmit(u2, false, this);
                    }
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                dispose();
                this.downstream.onError(th);
            }
        }
    }
}
