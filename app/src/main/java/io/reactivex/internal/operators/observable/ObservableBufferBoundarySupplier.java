package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.observers.QueueDrainObserver;
import io.reactivex.internal.queue.MpscLinkedQueue;
import io.reactivex.internal.util.QueueDrainHelper;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.SerializedObserver;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes4.dex */
public final class ObservableBufferBoundarySupplier<T, U extends Collection<? super T>, B> extends a<T, U> {
    final Callable<? extends ObservableSource<B>> a;
    final Callable<U> b;

    public ObservableBufferBoundarySupplier(ObservableSource<T> observableSource, Callable<? extends ObservableSource<B>> callable, Callable<U> callable2) {
        super(observableSource);
        this.a = callable;
        this.b = callable2;
    }

    @Override // io.reactivex.Observable
    protected void subscribeActual(Observer<? super U> observer) {
        this.source.subscribe(new b(new SerializedObserver(observer), this.b, this.a));
    }

    /* loaded from: classes4.dex */
    static final class b<T, U extends Collection<? super T>, B> extends QueueDrainObserver<T, U, U> implements Observer<T>, Disposable {
        final Callable<U> b;
        final Callable<? extends ObservableSource<B>> c;
        Disposable d;
        final AtomicReference<Disposable> e = new AtomicReference<>();
        U f;

        b(Observer<? super U> observer, Callable<U> callable, Callable<? extends ObservableSource<B>> callable2) {
            super(observer, new MpscLinkedQueue());
            this.b = callable;
            this.c = callable2;
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.d, disposable)) {
                this.d = disposable;
                Observer observer = this.downstream;
                try {
                    this.f = (U) ((Collection) ObjectHelper.requireNonNull(this.b.call(), "The buffer supplied is null"));
                    try {
                        ObservableSource observableSource = (ObservableSource) ObjectHelper.requireNonNull(this.c.call(), "The boundary ObservableSource supplied is null");
                        a aVar = new a(this);
                        this.e.set(aVar);
                        observer.onSubscribe(this);
                        if (!this.cancelled) {
                            observableSource.subscribe(aVar);
                        }
                    } catch (Throwable th) {
                        Exceptions.throwIfFatal(th);
                        this.cancelled = true;
                        disposable.dispose();
                        EmptyDisposable.error(th, observer);
                    }
                } catch (Throwable th2) {
                    Exceptions.throwIfFatal(th2);
                    this.cancelled = true;
                    disposable.dispose();
                    EmptyDisposable.error(th2, observer);
                }
            }
        }

        @Override // io.reactivex.Observer
        public void onNext(T t) {
            synchronized (this) {
                U u = this.f;
                if (u != null) {
                    u.add(t);
                }
            }
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            dispose();
            this.downstream.onError(th);
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            synchronized (this) {
                U u = this.f;
                if (u != null) {
                    this.f = null;
                    this.queue.offer(u);
                    this.done = true;
                    if (enter()) {
                        QueueDrainHelper.drainLoop(this.queue, this.downstream, false, this, this);
                    }
                }
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            if (!this.cancelled) {
                this.cancelled = true;
                this.d.dispose();
                a();
                if (enter()) {
                    this.queue.clear();
                }
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.cancelled;
        }

        void a() {
            DisposableHelper.dispose(this.e);
        }

        void b() {
            try {
                U u = (U) ((Collection) ObjectHelper.requireNonNull(this.b.call(), "The buffer supplied is null"));
                try {
                    ObservableSource observableSource = (ObservableSource) ObjectHelper.requireNonNull(this.c.call(), "The boundary ObservableSource supplied is null");
                    a aVar = new a(this);
                    if (DisposableHelper.replace(this.e, aVar)) {
                        synchronized (this) {
                            U u2 = this.f;
                            if (u2 != null) {
                                this.f = u;
                                observableSource.subscribe(aVar);
                                fastPathEmit(u2, false, this);
                            }
                        }
                    }
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    this.cancelled = true;
                    this.d.dispose();
                    this.downstream.onError(th);
                }
            } catch (Throwable th2) {
                Exceptions.throwIfFatal(th2);
                dispose();
                this.downstream.onError(th2);
            }
        }

        /* renamed from: a */
        public void accept(Observer<? super U> observer, U u) {
            this.downstream.onNext(u);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class a<T, U extends Collection<? super T>, B> extends DisposableObserver<B> {
        final b<T, U, B> a;
        boolean b;

        a(b<T, U, B> bVar) {
            this.a = bVar;
        }

        @Override // io.reactivex.Observer
        public void onNext(B b) {
            if (!this.b) {
                this.b = true;
                dispose();
                this.a.b();
            }
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            if (this.b) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.b = true;
            this.a.onError(th);
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
