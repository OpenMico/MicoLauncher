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
import java.util.Collection;
import java.util.concurrent.Callable;

/* loaded from: classes4.dex */
public final class ObservableBufferExactBoundary<T, U extends Collection<? super T>, B> extends a<T, U> {
    final ObservableSource<B> a;
    final Callable<U> b;

    public ObservableBufferExactBoundary(ObservableSource<T> observableSource, ObservableSource<B> observableSource2, Callable<U> callable) {
        super(observableSource);
        this.a = observableSource2;
        this.b = callable;
    }

    @Override // io.reactivex.Observable
    protected void subscribeActual(Observer<? super U> observer) {
        this.source.subscribe(new b(new SerializedObserver(observer), this.b, this.a));
    }

    /* loaded from: classes4.dex */
    static final class b<T, U extends Collection<? super T>, B> extends QueueDrainObserver<T, U, U> implements Observer<T>, Disposable {
        final Callable<U> b;
        final ObservableSource<B> c;
        Disposable d;
        Disposable e;
        U f;

        b(Observer<? super U> observer, Callable<U> callable, ObservableSource<B> observableSource) {
            super(observer, new MpscLinkedQueue());
            this.b = callable;
            this.c = observableSource;
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.d, disposable)) {
                this.d = disposable;
                try {
                    this.f = (U) ((Collection) ObjectHelper.requireNonNull(this.b.call(), "The buffer supplied is null"));
                    a aVar = new a(this);
                    this.e = aVar;
                    this.downstream.onSubscribe(this);
                    if (!this.cancelled) {
                        this.c.subscribe(aVar);
                    }
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    this.cancelled = true;
                    disposable.dispose();
                    EmptyDisposable.error(th, this.downstream);
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
                this.e.dispose();
                this.d.dispose();
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
            try {
                U u = (U) ((Collection) ObjectHelper.requireNonNull(this.b.call(), "The buffer supplied is null"));
                synchronized (this) {
                    U u2 = this.f;
                    if (u2 != null) {
                        this.f = u;
                        fastPathEmit(u2, false, this);
                    }
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                dispose();
                this.downstream.onError(th);
            }
        }

        /* renamed from: a */
        public void accept(Observer<? super U> observer, U u) {
            this.downstream.onNext(u);
        }
    }

    /* loaded from: classes4.dex */
    static final class a<T, U extends Collection<? super T>, B> extends DisposableObserver<B> {
        final b<T, U, B> a;

        a(b<T, U, B> bVar) {
            this.a = bVar;
        }

        @Override // io.reactivex.Observer
        public void onNext(B b) {
            this.a.a();
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            this.a.onError(th);
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            this.a.onComplete();
        }
    }
}
