package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.queue.MpscLinkedQueue;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.subjects.UnicastSubject;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes4.dex */
public final class ObservableWindowBoundarySupplier<T, B> extends a<T, Observable<T>> {
    final Callable<? extends ObservableSource<B>> a;
    final int b;

    public ObservableWindowBoundarySupplier(ObservableSource<T> observableSource, Callable<? extends ObservableSource<B>> callable, int i) {
        super(observableSource);
        this.a = callable;
        this.b = i;
    }

    @Override // io.reactivex.Observable
    public void subscribeActual(Observer<? super Observable<T>> observer) {
        this.source.subscribe(new b(observer, this.b, this.a));
    }

    /* loaded from: classes4.dex */
    static final class b<T, B> extends AtomicInteger implements Observer<T>, Disposable, Runnable {
        static final a<Object, Object> a = new a<>(null);
        static final Object b = new Object();
        private static final long serialVersionUID = 2233020065421370272L;
        final int capacityHint;
        volatile boolean done;
        final Observer<? super Observable<T>> downstream;
        final Callable<? extends ObservableSource<B>> other;
        Disposable upstream;
        UnicastSubject<T> window;
        final AtomicReference<a<T, B>> boundaryObserver = new AtomicReference<>();
        final AtomicInteger windows = new AtomicInteger(1);
        final MpscLinkedQueue<Object> queue = new MpscLinkedQueue<>();
        final AtomicThrowable errors = new AtomicThrowable();
        final AtomicBoolean stopWindows = new AtomicBoolean();

        b(Observer<? super Observable<T>> observer, int i, Callable<? extends ObservableSource<B>> callable) {
            this.downstream = observer;
            this.capacityHint = i;
            this.other = callable;
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.upstream, disposable)) {
                this.upstream = disposable;
                this.downstream.onSubscribe(this);
                this.queue.offer(b);
                c();
            }
        }

        @Override // io.reactivex.Observer
        public void onNext(T t) {
            this.queue.offer(t);
            c();
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            a();
            if (this.errors.addThrowable(th)) {
                this.done = true;
                c();
                return;
            }
            RxJavaPlugins.onError(th);
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            a();
            this.done = true;
            c();
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            if (this.stopWindows.compareAndSet(false, true)) {
                a();
                if (this.windows.decrementAndGet() == 0) {
                    this.upstream.dispose();
                }
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        void a() {
            Disposable disposable = (Disposable) this.boundaryObserver.getAndSet(a);
            if (disposable != null && disposable != a) {
                disposable.dispose();
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.stopWindows.get();
        }

        @Override // java.lang.Runnable
        public void run() {
            if (this.windows.decrementAndGet() == 0) {
                this.upstream.dispose();
            }
        }

        void a(a<T, B> aVar) {
            this.boundaryObserver.compareAndSet(aVar, null);
            this.queue.offer(b);
            c();
        }

        void a(Throwable th) {
            this.upstream.dispose();
            if (this.errors.addThrowable(th)) {
                this.done = true;
                c();
                return;
            }
            RxJavaPlugins.onError(th);
        }

        void b() {
            this.upstream.dispose();
            this.done = true;
            c();
        }

        /* JADX WARN: Multi-variable type inference failed */
        void c() {
            if (getAndIncrement() == 0) {
                Observer<? super Observable<T>> observer = this.downstream;
                MpscLinkedQueue<Object> mpscLinkedQueue = this.queue;
                AtomicThrowable atomicThrowable = this.errors;
                int i = 1;
                while (this.windows.get() != 0) {
                    UnicastSubject<T> unicastSubject = this.window;
                    boolean z = this.done;
                    if (!z || atomicThrowable.get() == null) {
                        Object poll = mpscLinkedQueue.poll();
                        boolean z2 = poll == null;
                        if (z && z2) {
                            Throwable terminate = atomicThrowable.terminate();
                            if (terminate == null) {
                                if (unicastSubject != 0) {
                                    this.window = null;
                                    unicastSubject.onComplete();
                                }
                                observer.onComplete();
                                return;
                            }
                            if (unicastSubject != 0) {
                                this.window = null;
                                unicastSubject.onError(terminate);
                            }
                            observer.onError(terminate);
                            return;
                        } else if (z2) {
                            i = addAndGet(-i);
                            if (i == 0) {
                                return;
                            }
                        } else if (poll != b) {
                            unicastSubject.onNext(poll);
                        } else {
                            if (unicastSubject != 0) {
                                this.window = null;
                                unicastSubject.onComplete();
                            }
                            if (!this.stopWindows.get()) {
                                UnicastSubject<T> create = UnicastSubject.create(this.capacityHint, this);
                                this.window = create;
                                this.windows.getAndIncrement();
                                try {
                                    ObservableSource observableSource = (ObservableSource) ObjectHelper.requireNonNull(this.other.call(), "The other Callable returned a null ObservableSource");
                                    a<T, B> aVar = new a<>(this);
                                    if (this.boundaryObserver.compareAndSet(null, aVar)) {
                                        observableSource.subscribe(aVar);
                                        observer.onNext(create);
                                    }
                                } catch (Throwable th) {
                                    Exceptions.throwIfFatal(th);
                                    atomicThrowable.addThrowable(th);
                                    this.done = true;
                                }
                            }
                        }
                    } else {
                        mpscLinkedQueue.clear();
                        Throwable terminate2 = atomicThrowable.terminate();
                        if (unicastSubject != 0) {
                            this.window = null;
                            unicastSubject.onError(terminate2);
                        }
                        observer.onError(terminate2);
                        return;
                    }
                }
                mpscLinkedQueue.clear();
                this.window = null;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class a<T, B> extends DisposableObserver<B> {
        final b<T, B> a;
        boolean b;

        a(b<T, B> bVar) {
            this.a = bVar;
        }

        @Override // io.reactivex.Observer
        public void onNext(B b) {
            if (!this.b) {
                this.b = true;
                dispose();
                this.a.a(this);
            }
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            if (this.b) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.b = true;
            this.a.a(th);
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            if (!this.b) {
                this.b = true;
                this.a.b();
            }
        }
    }
}
