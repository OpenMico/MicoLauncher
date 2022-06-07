package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.functions.Supplier;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.internal.fuseable.QueueDisposable;
import io.reactivex.rxjava3.internal.fuseable.SimpleQueue;
import io.reactivex.rxjava3.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.rxjava3.internal.util.AtomicThrowable;
import io.reactivex.rxjava3.internal.util.ErrorMode;
import io.reactivex.rxjava3.observers.SerializedObserver;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes5.dex */
public final class ObservableConcatMapScheduler<T, U> extends a<T, U> {
    final Function<? super T, ? extends ObservableSource<? extends U>> a;
    final int b;
    final ErrorMode c;
    final Scheduler d;

    public ObservableConcatMapScheduler(ObservableSource<T> observableSource, Function<? super T, ? extends ObservableSource<? extends U>> function, int i, ErrorMode errorMode, Scheduler scheduler) {
        super(observableSource);
        this.a = function;
        this.c = errorMode;
        this.b = Math.max(8, i);
        this.d = scheduler;
    }

    @Override // io.reactivex.rxjava3.core.Observable
    public void subscribeActual(Observer<? super U> observer) {
        if (this.c == ErrorMode.IMMEDIATE) {
            this.source.subscribe(new b(new SerializedObserver(observer), this.a, this.b, this.d.createWorker()));
        } else {
            this.source.subscribe(new a(observer, this.a, this.b, this.c == ErrorMode.END, this.d.createWorker()));
        }
    }

    /* loaded from: classes5.dex */
    static final class b<T, U> extends AtomicInteger implements Observer<T>, Disposable, Runnable {
        private static final long serialVersionUID = 8828587559905699186L;
        volatile boolean active;
        final int bufferSize;
        volatile boolean disposed;
        volatile boolean done;
        final Observer<? super U> downstream;
        int fusionMode;
        final a<U> inner;
        final Function<? super T, ? extends ObservableSource<? extends U>> mapper;
        SimpleQueue<T> queue;
        Disposable upstream;
        final Scheduler.Worker worker;

        b(Observer<? super U> observer, Function<? super T, ? extends ObservableSource<? extends U>> function, int i, Scheduler.Worker worker) {
            this.downstream = observer;
            this.mapper = function;
            this.bufferSize = i;
            this.inner = new a<>(observer, this);
            this.worker = worker;
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.upstream, disposable)) {
                this.upstream = disposable;
                if (disposable instanceof QueueDisposable) {
                    QueueDisposable queueDisposable = (QueueDisposable) disposable;
                    int requestFusion = queueDisposable.requestFusion(3);
                    if (requestFusion == 1) {
                        this.fusionMode = requestFusion;
                        this.queue = queueDisposable;
                        this.done = true;
                        this.downstream.onSubscribe(this);
                        b();
                        return;
                    } else if (requestFusion == 2) {
                        this.fusionMode = requestFusion;
                        this.queue = queueDisposable;
                        this.downstream.onSubscribe(this);
                        return;
                    }
                }
                this.queue = new SpscLinkedArrayQueue(this.bufferSize);
                this.downstream.onSubscribe(this);
            }
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onNext(T t) {
            if (!this.done) {
                if (this.fusionMode == 0) {
                    this.queue.offer(t);
                }
                b();
            }
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onError(Throwable th) {
            if (this.done) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.done = true;
            dispose();
            this.downstream.onError(th);
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onComplete() {
            if (!this.done) {
                this.done = true;
                b();
            }
        }

        void a() {
            this.active = false;
            b();
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.disposed;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            this.disposed = true;
            this.inner.a();
            this.upstream.dispose();
            this.worker.dispose();
            if (getAndIncrement() == 0) {
                this.queue.clear();
            }
        }

        void b() {
            if (getAndIncrement() == 0) {
                this.worker.schedule(this);
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            while (!this.disposed) {
                if (!this.active) {
                    boolean z = this.done;
                    try {
                        T poll = this.queue.poll();
                        boolean z2 = poll == null;
                        if (z && z2) {
                            this.disposed = true;
                            this.downstream.onComplete();
                            this.worker.dispose();
                            return;
                        } else if (!z2) {
                            try {
                                ObservableSource observableSource = (ObservableSource) Objects.requireNonNull(this.mapper.apply(poll), "The mapper returned a null ObservableSource");
                                this.active = true;
                                observableSource.subscribe(this.inner);
                            } catch (Throwable th) {
                                Exceptions.throwIfFatal(th);
                                dispose();
                                this.queue.clear();
                                this.downstream.onError(th);
                                this.worker.dispose();
                                return;
                            }
                        }
                    } catch (Throwable th2) {
                        Exceptions.throwIfFatal(th2);
                        dispose();
                        this.queue.clear();
                        this.downstream.onError(th2);
                        this.worker.dispose();
                        return;
                    }
                }
                if (decrementAndGet() == 0) {
                    return;
                }
            }
            this.queue.clear();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* loaded from: classes5.dex */
        public static final class a<U> extends AtomicReference<Disposable> implements Observer<U> {
            private static final long serialVersionUID = -7449079488798789337L;
            final Observer<? super U> downstream;
            final b<?, ?> parent;

            a(Observer<? super U> observer, b<?, ?> bVar) {
                this.downstream = observer;
                this.parent = bVar;
            }

            @Override // io.reactivex.rxjava3.core.Observer
            public void onSubscribe(Disposable disposable) {
                DisposableHelper.replace(this, disposable);
            }

            @Override // io.reactivex.rxjava3.core.Observer
            public void onNext(U u) {
                this.downstream.onNext(u);
            }

            @Override // io.reactivex.rxjava3.core.Observer
            public void onError(Throwable th) {
                this.parent.dispose();
                this.downstream.onError(th);
            }

            @Override // io.reactivex.rxjava3.core.Observer
            public void onComplete() {
                this.parent.a();
            }

            void a() {
                DisposableHelper.dispose(this);
            }
        }
    }

    /* loaded from: classes5.dex */
    static final class a<T, R> extends AtomicInteger implements Observer<T>, Disposable, Runnable {
        private static final long serialVersionUID = -6951100001833242599L;
        volatile boolean active;
        final int bufferSize;
        volatile boolean cancelled;
        volatile boolean done;
        final Observer<? super R> downstream;
        final AtomicThrowable errors = new AtomicThrowable();
        final Function<? super T, ? extends ObservableSource<? extends R>> mapper;
        final C0321a<R> observer;
        SimpleQueue<T> queue;
        int sourceMode;
        final boolean tillTheEnd;
        Disposable upstream;
        final Scheduler.Worker worker;

        a(Observer<? super R> observer, Function<? super T, ? extends ObservableSource<? extends R>> function, int i, boolean z, Scheduler.Worker worker) {
            this.downstream = observer;
            this.mapper = function;
            this.bufferSize = i;
            this.tillTheEnd = z;
            this.observer = new C0321a<>(observer, this);
            this.worker = worker;
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.upstream, disposable)) {
                this.upstream = disposable;
                if (disposable instanceof QueueDisposable) {
                    QueueDisposable queueDisposable = (QueueDisposable) disposable;
                    int requestFusion = queueDisposable.requestFusion(3);
                    if (requestFusion == 1) {
                        this.sourceMode = requestFusion;
                        this.queue = queueDisposable;
                        this.done = true;
                        this.downstream.onSubscribe(this);
                        a();
                        return;
                    } else if (requestFusion == 2) {
                        this.sourceMode = requestFusion;
                        this.queue = queueDisposable;
                        this.downstream.onSubscribe(this);
                        return;
                    }
                }
                this.queue = new SpscLinkedArrayQueue(this.bufferSize);
                this.downstream.onSubscribe(this);
            }
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onNext(T t) {
            if (this.sourceMode == 0) {
                this.queue.offer(t);
            }
            a();
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onError(Throwable th) {
            if (this.errors.tryAddThrowableOrReport(th)) {
                this.done = true;
                a();
            }
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onComplete() {
            this.done = true;
            a();
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.cancelled;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            this.cancelled = true;
            this.upstream.dispose();
            this.observer.a();
            this.worker.dispose();
            this.errors.tryTerminateAndReport();
        }

        void a() {
            if (getAndIncrement() == 0) {
                this.worker.schedule(this);
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            Observer<? super R> observer = this.downstream;
            SimpleQueue<T> simpleQueue = this.queue;
            AtomicThrowable atomicThrowable = this.errors;
            while (true) {
                if (!this.active) {
                    if (this.cancelled) {
                        simpleQueue.clear();
                        return;
                    } else if (this.tillTheEnd || atomicThrowable.get() == null) {
                        boolean z = this.done;
                        try {
                            T poll = simpleQueue.poll();
                            boolean z2 = poll == null;
                            if (z && z2) {
                                this.cancelled = true;
                                atomicThrowable.tryTerminateConsumer(observer);
                                this.worker.dispose();
                                return;
                            } else if (!z2) {
                                try {
                                    ObservableSource observableSource = (ObservableSource) Objects.requireNonNull(this.mapper.apply(poll), "The mapper returned a null ObservableSource");
                                    if (observableSource instanceof Supplier) {
                                        try {
                                            Object obj = (Object) ((Supplier) observableSource).get();
                                            if (obj != 0 && !this.cancelled) {
                                                observer.onNext(obj);
                                            }
                                        } catch (Throwable th) {
                                            Exceptions.throwIfFatal(th);
                                            atomicThrowable.tryAddThrowableOrReport(th);
                                        }
                                    } else {
                                        this.active = true;
                                        observableSource.subscribe(this.observer);
                                    }
                                } catch (Throwable th2) {
                                    Exceptions.throwIfFatal(th2);
                                    this.cancelled = true;
                                    this.upstream.dispose();
                                    simpleQueue.clear();
                                    atomicThrowable.tryAddThrowableOrReport(th2);
                                    atomicThrowable.tryTerminateConsumer(observer);
                                    this.worker.dispose();
                                    return;
                                }
                            }
                        } catch (Throwable th3) {
                            Exceptions.throwIfFatal(th3);
                            this.cancelled = true;
                            this.upstream.dispose();
                            atomicThrowable.tryAddThrowableOrReport(th3);
                            atomicThrowable.tryTerminateConsumer(observer);
                            this.worker.dispose();
                            return;
                        }
                    } else {
                        simpleQueue.clear();
                        this.cancelled = true;
                        atomicThrowable.tryTerminateConsumer(observer);
                        this.worker.dispose();
                        return;
                    }
                }
                if (decrementAndGet() == 0) {
                    return;
                }
            }
        }

        /* renamed from: io.reactivex.rxjava3.internal.operators.observable.ObservableConcatMapScheduler$a$a  reason: collision with other inner class name */
        /* loaded from: classes5.dex */
        static final class C0321a<R> extends AtomicReference<Disposable> implements Observer<R> {
            private static final long serialVersionUID = 2620149119579502636L;
            final Observer<? super R> downstream;
            final a<?, R> parent;

            C0321a(Observer<? super R> observer, a<?, R> aVar) {
                this.downstream = observer;
                this.parent = aVar;
            }

            @Override // io.reactivex.rxjava3.core.Observer
            public void onSubscribe(Disposable disposable) {
                DisposableHelper.replace(this, disposable);
            }

            @Override // io.reactivex.rxjava3.core.Observer
            public void onNext(R r) {
                this.downstream.onNext(r);
            }

            @Override // io.reactivex.rxjava3.core.Observer
            public void onError(Throwable th) {
                a<?, R> aVar = this.parent;
                if (aVar.errors.tryAddThrowableOrReport(th)) {
                    if (!aVar.tillTheEnd) {
                        aVar.upstream.dispose();
                    }
                    aVar.active = false;
                    aVar.a();
                }
            }

            @Override // io.reactivex.rxjava3.core.Observer
            public void onComplete() {
                a<?, R> aVar = this.parent;
                aVar.active = false;
                aVar.a();
            }

            void a() {
                DisposableHelper.dispose(this);
            }
        }
    }
}
