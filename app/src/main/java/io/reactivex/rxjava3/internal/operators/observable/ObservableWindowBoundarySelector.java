package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.internal.fuseable.SimplePlainQueue;
import io.reactivex.rxjava3.internal.queue.MpscLinkedQueue;
import io.reactivex.rxjava3.internal.util.AtomicThrowable;
import io.reactivex.rxjava3.internal.util.ExceptionHelper;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import io.reactivex.rxjava3.subjects.UnicastSubject;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes5.dex */
public final class ObservableWindowBoundarySelector<T, B, V> extends a<T, Observable<T>> {
    final ObservableSource<B> a;
    final Function<? super B, ? extends ObservableSource<V>> b;
    final int c;

    public ObservableWindowBoundarySelector(ObservableSource<T> observableSource, ObservableSource<B> observableSource2, Function<? super B, ? extends ObservableSource<V>> function, int i) {
        super(observableSource);
        this.a = observableSource2;
        this.b = function;
        this.c = i;
    }

    @Override // io.reactivex.rxjava3.core.Observable
    public void subscribeActual(Observer<? super Observable<T>> observer) {
        this.source.subscribe(new a(observer, this.a, this.b, this.c));
    }

    /* loaded from: classes5.dex */
    static final class a<T, B, V> extends AtomicInteger implements Observer<T>, Disposable, Runnable {
        private static final long serialVersionUID = 8646217640096099753L;
        final int bufferSize;
        final Function<? super B, ? extends ObservableSource<V>> closingIndicator;
        final Observer<? super Observable<T>> downstream;
        long emitted;
        final ObservableSource<B> open;
        volatile boolean openDone;
        Disposable upstream;
        volatile boolean upstreamCanceled;
        volatile boolean upstreamDone;
        final SimplePlainQueue<Object> queue = new MpscLinkedQueue();
        final CompositeDisposable resources = new CompositeDisposable();
        final List<UnicastSubject<T>> windows = new ArrayList();
        final AtomicLong windowCount = new AtomicLong(1);
        final AtomicBoolean downstreamDisposed = new AtomicBoolean();
        final AtomicThrowable error = new AtomicThrowable();
        final c<B> startObserver = new c<>(this);
        final AtomicLong requested = new AtomicLong();

        a(Observer<? super Observable<T>> observer, ObservableSource<B> observableSource, Function<? super B, ? extends ObservableSource<V>> function, int i) {
            this.downstream = observer;
            this.open = observableSource;
            this.closingIndicator = function;
            this.bufferSize = i;
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.upstream, disposable)) {
                this.upstream = disposable;
                this.downstream.onSubscribe(this);
                this.open.subscribe(this.startObserver);
            }
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onNext(T t) {
            this.queue.offer(t);
            b();
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onError(Throwable th) {
            this.startObserver.a();
            this.resources.dispose();
            if (this.error.tryAddThrowableOrReport(th)) {
                this.upstreamDone = true;
                b();
            }
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onComplete() {
            this.startObserver.a();
            this.resources.dispose();
            this.upstreamDone = true;
            b();
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            if (!this.downstreamDisposed.compareAndSet(false, true)) {
                return;
            }
            if (this.windowCount.decrementAndGet() == 0) {
                this.upstream.dispose();
                this.startObserver.a();
                this.resources.dispose();
                this.error.tryTerminateAndReport();
                this.upstreamCanceled = true;
                b();
                return;
            }
            this.startObserver.a();
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.downstreamDisposed.get();
        }

        @Override // java.lang.Runnable
        public void run() {
            if (this.windowCount.decrementAndGet() == 0) {
                this.upstream.dispose();
                this.startObserver.a();
                this.resources.dispose();
                this.error.tryTerminateAndReport();
                this.upstreamCanceled = true;
                b();
            }
        }

        void a(B b2) {
            this.queue.offer(new b(b2));
            b();
        }

        void a(Throwable th) {
            this.upstream.dispose();
            this.resources.dispose();
            if (this.error.tryAddThrowableOrReport(th)) {
                this.upstreamDone = true;
                b();
            }
        }

        void a() {
            this.openDone = true;
            b();
        }

        void a(C0336a<T, V> aVar) {
            this.queue.offer(aVar);
            b();
        }

        void b(Throwable th) {
            this.upstream.dispose();
            this.startObserver.a();
            this.resources.dispose();
            if (this.error.tryAddThrowableOrReport(th)) {
                this.upstreamDone = true;
                b();
            }
        }

        void b() {
            if (getAndIncrement() == 0) {
                Observer<? super Observable<T>> observer = this.downstream;
                SimplePlainQueue<Object> simplePlainQueue = this.queue;
                List<UnicastSubject<T>> list = this.windows;
                int i = 1;
                while (true) {
                    if (this.upstreamCanceled) {
                        simplePlainQueue.clear();
                        list.clear();
                    } else {
                        boolean z = this.upstreamDone;
                        Object poll = simplePlainQueue.poll();
                        boolean z2 = poll == null;
                        if (z && (z2 || this.error.get() != null)) {
                            a((Observer<?>) observer);
                            this.upstreamCanceled = true;
                        } else if (!z2) {
                            if (poll instanceof b) {
                                if (!this.downstreamDisposed.get()) {
                                    try {
                                        ObservableSource observableSource = (ObservableSource) Objects.requireNonNull(this.closingIndicator.apply(((b) poll).a), "The closingIndicator returned a null ObservableSource");
                                        this.windowCount.getAndIncrement();
                                        UnicastSubject<T> create = UnicastSubject.create(this.bufferSize, this);
                                        C0336a aVar = new C0336a(this, create);
                                        observer.onNext(aVar);
                                        if (aVar.a()) {
                                            create.onComplete();
                                        } else {
                                            list.add(create);
                                            this.resources.add(aVar);
                                            observableSource.subscribe(aVar);
                                        }
                                    } catch (Throwable th) {
                                        Exceptions.throwIfFatal(th);
                                        this.upstream.dispose();
                                        this.startObserver.a();
                                        this.resources.dispose();
                                        Exceptions.throwIfFatal(th);
                                        this.error.tryAddThrowableOrReport(th);
                                        this.upstreamDone = true;
                                    }
                                }
                            } else if (poll instanceof C0336a) {
                                UnicastSubject<T> unicastSubject = ((C0336a) poll).b;
                                list.remove(unicastSubject);
                                this.resources.delete((Disposable) poll);
                                unicastSubject.onComplete();
                            } else {
                                for (UnicastSubject<T> unicastSubject2 : list) {
                                    unicastSubject2.onNext(poll);
                                }
                            }
                        } else if (this.openDone && list.size() == 0) {
                            this.upstream.dispose();
                            this.startObserver.a();
                            this.resources.dispose();
                            a((Observer<?>) observer);
                            this.upstreamCanceled = true;
                        }
                    }
                    i = addAndGet(-i);
                    if (i == 0) {
                        return;
                    }
                }
            }
        }

        void a(Observer<?> observer) {
            Throwable terminate = this.error.terminate();
            if (terminate == null) {
                for (UnicastSubject<T> unicastSubject : this.windows) {
                    unicastSubject.onComplete();
                }
                observer.onComplete();
            } else if (terminate != ExceptionHelper.TERMINATED) {
                for (UnicastSubject<T> unicastSubject2 : this.windows) {
                    unicastSubject2.onError(terminate);
                }
                observer.onError(terminate);
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* loaded from: classes5.dex */
        public static final class b<B> {
            final B a;

            b(B b) {
                this.a = b;
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* loaded from: classes5.dex */
        public static final class c<B> extends AtomicReference<Disposable> implements Observer<B> {
            private static final long serialVersionUID = -3326496781427702834L;
            final a<?, B, ?> parent;

            c(a<?, B, ?> aVar) {
                this.parent = aVar;
            }

            @Override // io.reactivex.rxjava3.core.Observer
            public void onSubscribe(Disposable disposable) {
                DisposableHelper.setOnce(this, disposable);
            }

            @Override // io.reactivex.rxjava3.core.Observer
            public void onNext(B b) {
                this.parent.a((a<?, B, ?>) b);
            }

            @Override // io.reactivex.rxjava3.core.Observer
            public void onError(Throwable th) {
                this.parent.a(th);
            }

            @Override // io.reactivex.rxjava3.core.Observer
            public void onComplete() {
                this.parent.a();
            }

            void a() {
                DisposableHelper.dispose(this);
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: io.reactivex.rxjava3.internal.operators.observable.ObservableWindowBoundarySelector$a$a  reason: collision with other inner class name */
        /* loaded from: classes5.dex */
        public static final class C0336a<T, V> extends Observable<T> implements Observer<V>, Disposable {
            final a<T, ?, V> a;
            final UnicastSubject<T> b;
            final AtomicReference<Disposable> c = new AtomicReference<>();
            final AtomicBoolean d = new AtomicBoolean();

            C0336a(a<T, ?, V> aVar, UnicastSubject<T> unicastSubject) {
                this.a = aVar;
                this.b = unicastSubject;
            }

            @Override // io.reactivex.rxjava3.core.Observer
            public void onSubscribe(Disposable disposable) {
                DisposableHelper.setOnce(this.c, disposable);
            }

            @Override // io.reactivex.rxjava3.core.Observer
            public void onNext(V v) {
                if (DisposableHelper.dispose(this.c)) {
                    this.a.a((C0336a) this);
                }
            }

            @Override // io.reactivex.rxjava3.core.Observer
            public void onError(Throwable th) {
                if (isDisposed()) {
                    RxJavaPlugins.onError(th);
                } else {
                    this.a.b(th);
                }
            }

            @Override // io.reactivex.rxjava3.core.Observer
            public void onComplete() {
                this.a.a((C0336a) this);
            }

            @Override // io.reactivex.rxjava3.disposables.Disposable
            public void dispose() {
                DisposableHelper.dispose(this.c);
            }

            @Override // io.reactivex.rxjava3.disposables.Disposable
            public boolean isDisposed() {
                return this.c.get() == DisposableHelper.DISPOSED;
            }

            @Override // io.reactivex.rxjava3.core.Observable
            protected void subscribeActual(Observer<? super T> observer) {
                this.b.subscribe(observer);
                this.d.set(true);
            }

            boolean a() {
                return !this.d.get() && this.d.compareAndSet(false, true);
            }
        }
    }
}
