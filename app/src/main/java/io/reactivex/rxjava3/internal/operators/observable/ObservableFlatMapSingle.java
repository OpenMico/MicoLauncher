package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.core.SingleSource;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.rxjava3.internal.util.AtomicThrowable;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes5.dex */
public final class ObservableFlatMapSingle<T, R> extends a<T, R> {
    final Function<? super T, ? extends SingleSource<? extends R>> a;
    final boolean b;

    public ObservableFlatMapSingle(ObservableSource<T> observableSource, Function<? super T, ? extends SingleSource<? extends R>> function, boolean z) {
        super(observableSource);
        this.a = function;
        this.b = z;
    }

    @Override // io.reactivex.rxjava3.core.Observable
    protected void subscribeActual(Observer<? super R> observer) {
        this.source.subscribe(new a(observer, this.a, this.b));
    }

    /* loaded from: classes5.dex */
    static final class a<T, R> extends AtomicInteger implements Observer<T>, Disposable {
        private static final long serialVersionUID = 8600231336733376951L;
        volatile boolean cancelled;
        final boolean delayErrors;
        final Observer<? super R> downstream;
        final Function<? super T, ? extends SingleSource<? extends R>> mapper;
        Disposable upstream;
        final CompositeDisposable set = new CompositeDisposable();
        final AtomicThrowable errors = new AtomicThrowable();
        final AtomicInteger active = new AtomicInteger(1);
        final AtomicReference<SpscLinkedArrayQueue<R>> queue = new AtomicReference<>();

        a(Observer<? super R> observer, Function<? super T, ? extends SingleSource<? extends R>> function, boolean z) {
            this.downstream = observer;
            this.mapper = function;
            this.delayErrors = z;
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.upstream, disposable)) {
                this.upstream = disposable;
                this.downstream.onSubscribe(this);
            }
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onNext(T t) {
            try {
                SingleSource singleSource = (SingleSource) Objects.requireNonNull(this.mapper.apply(t), "The mapper returned a null SingleSource");
                this.active.getAndIncrement();
                C0328a aVar = new C0328a();
                if (!this.cancelled && this.set.add(aVar)) {
                    singleSource.subscribe(aVar);
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.upstream.dispose();
                onError(th);
            }
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onError(Throwable th) {
            this.active.decrementAndGet();
            if (this.errors.tryAddThrowableOrReport(th)) {
                if (!this.delayErrors) {
                    this.set.dispose();
                }
                b();
            }
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onComplete() {
            this.active.decrementAndGet();
            b();
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            this.cancelled = true;
            this.upstream.dispose();
            this.set.dispose();
            this.errors.tryTerminateAndReport();
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.cancelled;
        }

        void a(a<T, R>.C0328a aVar, R r) {
            this.set.delete(aVar);
            if (get() == 0) {
                boolean z = true;
                if (compareAndSet(0, 1)) {
                    this.downstream.onNext(r);
                    if (this.active.decrementAndGet() != 0) {
                        z = false;
                    }
                    SpscLinkedArrayQueue<R> spscLinkedArrayQueue = this.queue.get();
                    if (!z || (spscLinkedArrayQueue != null && !spscLinkedArrayQueue.isEmpty())) {
                        if (decrementAndGet() == 0) {
                            return;
                        }
                        d();
                    }
                    this.errors.tryTerminateConsumer(this.downstream);
                    return;
                }
            }
            SpscLinkedArrayQueue<R> a = a();
            synchronized (a) {
                a.offer(r);
            }
            this.active.decrementAndGet();
            if (getAndIncrement() != 0) {
                return;
            }
            d();
        }

        SpscLinkedArrayQueue<R> a() {
            SpscLinkedArrayQueue<R> spscLinkedArrayQueue = this.queue.get();
            if (spscLinkedArrayQueue != null) {
                return spscLinkedArrayQueue;
            }
            SpscLinkedArrayQueue<R> spscLinkedArrayQueue2 = new SpscLinkedArrayQueue<>(Observable.bufferSize());
            return this.queue.compareAndSet(null, spscLinkedArrayQueue2) ? spscLinkedArrayQueue2 : this.queue.get();
        }

        void a(a<T, R>.C0328a aVar, Throwable th) {
            this.set.delete(aVar);
            if (this.errors.tryAddThrowableOrReport(th)) {
                if (!this.delayErrors) {
                    this.upstream.dispose();
                    this.set.dispose();
                }
                this.active.decrementAndGet();
                b();
            }
        }

        void b() {
            if (getAndIncrement() == 0) {
                d();
            }
        }

        void c() {
            SpscLinkedArrayQueue<R> spscLinkedArrayQueue = this.queue.get();
            if (spscLinkedArrayQueue != null) {
                spscLinkedArrayQueue.clear();
            }
        }

        void d() {
            Observer<? super R> observer = this.downstream;
            AtomicInteger atomicInteger = this.active;
            AtomicReference<SpscLinkedArrayQueue<R>> atomicReference = this.queue;
            int i = 1;
            while (!this.cancelled) {
                if (this.delayErrors || this.errors.get() == null) {
                    boolean z = false;
                    boolean z2 = atomicInteger.get() == 0;
                    SpscLinkedArrayQueue<R> spscLinkedArrayQueue = atomicReference.get();
                    R poll = spscLinkedArrayQueue != null ? spscLinkedArrayQueue.poll() : (Object) null;
                    if (poll == null) {
                        z = true;
                    }
                    if (z2 && z) {
                        this.errors.tryTerminateConsumer(this.downstream);
                        return;
                    } else if (z) {
                        i = addAndGet(-i);
                        if (i == 0) {
                            return;
                        }
                    } else {
                        observer.onNext(poll);
                    }
                } else {
                    c();
                    this.errors.tryTerminateConsumer(observer);
                    return;
                }
            }
            c();
        }

        /* renamed from: io.reactivex.rxjava3.internal.operators.observable.ObservableFlatMapSingle$a$a  reason: collision with other inner class name */
        /* loaded from: classes5.dex */
        final class C0328a extends AtomicReference<Disposable> implements SingleObserver<R>, Disposable {
            private static final long serialVersionUID = -502562646270949838L;

            C0328a() {
            }

            @Override // io.reactivex.rxjava3.core.SingleObserver
            public void onSubscribe(Disposable disposable) {
                DisposableHelper.setOnce(this, disposable);
            }

            @Override // io.reactivex.rxjava3.core.SingleObserver
            public void onSuccess(R r) {
                a.this.a((a<T, C0328a>.C0328a) this, (C0328a) r);
            }

            @Override // io.reactivex.rxjava3.core.SingleObserver
            public void onError(Throwable th) {
                a.this.a(this, th);
            }

            @Override // io.reactivex.rxjava3.disposables.Disposable
            public boolean isDisposed() {
                return DisposableHelper.isDisposed(get());
            }

            @Override // io.reactivex.rxjava3.disposables.Disposable
            public void dispose() {
                DisposableHelper.dispose(this);
            }
        }
    }
}
