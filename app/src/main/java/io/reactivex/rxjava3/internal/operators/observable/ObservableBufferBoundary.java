package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.functions.Supplier;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.rxjava3.internal.util.AtomicThrowable;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes5.dex */
public final class ObservableBufferBoundary<T, U extends Collection<? super T>, Open, Close> extends a<T, U> {
    final Supplier<U> a;
    final ObservableSource<? extends Open> b;
    final Function<? super Open, ? extends ObservableSource<? extends Close>> c;

    public ObservableBufferBoundary(ObservableSource<T> observableSource, ObservableSource<? extends Open> observableSource2, Function<? super Open, ? extends ObservableSource<? extends Close>> function, Supplier<U> supplier) {
        super(observableSource);
        this.b = observableSource2;
        this.c = function;
        this.a = supplier;
    }

    @Override // io.reactivex.rxjava3.core.Observable
    protected void subscribeActual(Observer<? super U> observer) {
        a aVar = new a(observer, this.b, this.c, this.a);
        observer.onSubscribe(aVar);
        this.source.subscribe(aVar);
    }

    /* loaded from: classes5.dex */
    static final class a<T, C extends Collection<? super T>, Open, Close> extends AtomicInteger implements Observer<T>, Disposable {
        private static final long serialVersionUID = -8466418554264089604L;
        final Function<? super Open, ? extends ObservableSource<? extends Close>> bufferClose;
        final ObservableSource<? extends Open> bufferOpen;
        final Supplier<C> bufferSupplier;
        volatile boolean cancelled;
        volatile boolean done;
        final Observer<? super C> downstream;
        long index;
        final SpscLinkedArrayQueue<C> queue = new SpscLinkedArrayQueue<>(Observable.bufferSize());
        final CompositeDisposable observers = new CompositeDisposable();
        final AtomicReference<Disposable> upstream = new AtomicReference<>();
        Map<Long, C> buffers = new LinkedHashMap();
        final AtomicThrowable errors = new AtomicThrowable();

        a(Observer<? super C> observer, ObservableSource<? extends Open> observableSource, Function<? super Open, ? extends ObservableSource<? extends Close>> function, Supplier<C> supplier) {
            this.downstream = observer;
            this.bufferSupplier = supplier;
            this.bufferOpen = observableSource;
            this.bufferClose = function;
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.setOnce(this.upstream, disposable)) {
                C0319a aVar = new C0319a(this);
                this.observers.add(aVar);
                this.bufferOpen.subscribe(aVar);
            }
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onNext(T t) {
            synchronized (this) {
                Map<Long, C> map = this.buffers;
                if (map != null) {
                    for (C c : map.values()) {
                        c.add(t);
                    }
                }
            }
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onError(Throwable th) {
            if (this.errors.tryAddThrowableOrReport(th)) {
                this.observers.dispose();
                synchronized (this) {
                    this.buffers = null;
                }
                this.done = true;
                a();
            }
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onComplete() {
            this.observers.dispose();
            synchronized (this) {
                Map<Long, C> map = this.buffers;
                if (map != null) {
                    for (C c : map.values()) {
                        this.queue.offer(c);
                    }
                    this.buffers = null;
                    this.done = true;
                    a();
                }
            }
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            if (DisposableHelper.dispose(this.upstream)) {
                this.cancelled = true;
                this.observers.dispose();
                synchronized (this) {
                    this.buffers = null;
                }
                if (getAndIncrement() != 0) {
                    this.queue.clear();
                }
            }
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return DisposableHelper.isDisposed(this.upstream.get());
        }

        /* JADX WARN: Multi-variable type inference failed */
        void a(Open open) {
            try {
                Collection collection = (Collection) Objects.requireNonNull(this.bufferSupplier.get(), "The bufferSupplier returned a null Collection");
                ObservableSource observableSource = (ObservableSource) Objects.requireNonNull(this.bufferClose.apply(open), "The bufferClose returned a null ObservableSource");
                long j = this.index;
                this.index = 1 + j;
                synchronized (this) {
                    Map<Long, C> map = this.buffers;
                    if (map != 0) {
                        map.put(Long.valueOf(j), collection);
                        b bVar = new b(this, j);
                        this.observers.add(bVar);
                        observableSource.subscribe(bVar);
                    }
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                DisposableHelper.dispose(this.upstream);
                onError(th);
            }
        }

        void a(C0319a<Open> aVar) {
            this.observers.delete(aVar);
            if (this.observers.size() == 0) {
                DisposableHelper.dispose(this.upstream);
                this.done = true;
                a();
            }
        }

        void a(b<T, C> bVar, long j) {
            boolean z;
            this.observers.delete(bVar);
            if (this.observers.size() == 0) {
                DisposableHelper.dispose(this.upstream);
                z = true;
            } else {
                z = false;
            }
            synchronized (this) {
                if (this.buffers != null) {
                    this.queue.offer(this.buffers.remove(Long.valueOf(j)));
                    if (z) {
                        this.done = true;
                    }
                    a();
                }
            }
        }

        void a(Disposable disposable, Throwable th) {
            DisposableHelper.dispose(this.upstream);
            this.observers.delete(disposable);
            onError(th);
        }

        void a() {
            if (getAndIncrement() == 0) {
                Observer<? super C> observer = this.downstream;
                SpscLinkedArrayQueue<C> spscLinkedArrayQueue = this.queue;
                int i = 1;
                while (!this.cancelled) {
                    boolean z = this.done;
                    if (!z || this.errors.get() == null) {
                        C poll = spscLinkedArrayQueue.poll();
                        boolean z2 = poll == null;
                        if (z && z2) {
                            observer.onComplete();
                            return;
                        } else if (z2) {
                            i = addAndGet(-i);
                            if (i == 0) {
                                return;
                            }
                        } else {
                            observer.onNext(poll);
                        }
                    } else {
                        spscLinkedArrayQueue.clear();
                        this.errors.tryTerminateConsumer(observer);
                        return;
                    }
                }
                spscLinkedArrayQueue.clear();
            }
        }

        /* renamed from: io.reactivex.rxjava3.internal.operators.observable.ObservableBufferBoundary$a$a  reason: collision with other inner class name */
        /* loaded from: classes5.dex */
        static final class C0319a<Open> extends AtomicReference<Disposable> implements Observer<Open>, Disposable {
            private static final long serialVersionUID = -8498650778633225126L;
            final a<?, ?, Open, ?> parent;

            C0319a(a<?, ?, Open, ?> aVar) {
                this.parent = aVar;
            }

            @Override // io.reactivex.rxjava3.core.Observer
            public void onSubscribe(Disposable disposable) {
                DisposableHelper.setOnce(this, disposable);
            }

            @Override // io.reactivex.rxjava3.core.Observer
            public void onNext(Open open) {
                this.parent.a((a<?, ?, Open, ?>) open);
            }

            @Override // io.reactivex.rxjava3.core.Observer
            public void onError(Throwable th) {
                lazySet(DisposableHelper.DISPOSED);
                this.parent.a(this, th);
            }

            @Override // io.reactivex.rxjava3.core.Observer
            public void onComplete() {
                lazySet(DisposableHelper.DISPOSED);
                this.parent.a((C0319a) this);
            }

            @Override // io.reactivex.rxjava3.disposables.Disposable
            public void dispose() {
                DisposableHelper.dispose(this);
            }

            @Override // io.reactivex.rxjava3.disposables.Disposable
            public boolean isDisposed() {
                return get() == DisposableHelper.DISPOSED;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public static final class b<T, C extends Collection<? super T>> extends AtomicReference<Disposable> implements Observer<Object>, Disposable {
        private static final long serialVersionUID = -8498650778633225126L;
        final long index;
        final a<T, C, ?, ?> parent;

        b(a<T, C, ?, ?> aVar, long j) {
            this.parent = aVar;
            this.index = j;
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onSubscribe(Disposable disposable) {
            DisposableHelper.setOnce(this, disposable);
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onNext(Object obj) {
            Disposable disposable = get();
            if (disposable != DisposableHelper.DISPOSED) {
                lazySet(DisposableHelper.DISPOSED);
                disposable.dispose();
                this.parent.a(this, this.index);
            }
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onError(Throwable th) {
            if (get() != DisposableHelper.DISPOSED) {
                lazySet(DisposableHelper.DISPOSED);
                this.parent.a(this, th);
                return;
            }
            RxJavaPlugins.onError(th);
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onComplete() {
            if (get() != DisposableHelper.DISPOSED) {
                lazySet(DisposableHelper.DISPOSED);
                this.parent.a(this, this.index);
            }
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            DisposableHelper.dispose(this);
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return get() == DisposableHelper.DISPOSED;
        }
    }
}
