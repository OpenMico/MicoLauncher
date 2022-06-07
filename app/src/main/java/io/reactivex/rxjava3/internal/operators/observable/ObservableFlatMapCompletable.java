package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.annotations.Nullable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.CompletableSource;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.internal.observers.BasicIntQueueDisposable;
import io.reactivex.rxjava3.internal.util.AtomicThrowable;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes5.dex */
public final class ObservableFlatMapCompletable<T> extends a<T, T> {
    final Function<? super T, ? extends CompletableSource> a;
    final boolean b;

    public ObservableFlatMapCompletable(ObservableSource<T> observableSource, Function<? super T, ? extends CompletableSource> function, boolean z) {
        super(observableSource);
        this.a = function;
        this.b = z;
    }

    @Override // io.reactivex.rxjava3.core.Observable
    protected void subscribeActual(Observer<? super T> observer) {
        this.source.subscribe(new a(observer, this.a, this.b));
    }

    /* loaded from: classes5.dex */
    static final class a<T> extends BasicIntQueueDisposable<T> implements Observer<T> {
        private static final long serialVersionUID = 8443155186132538303L;
        final boolean delayErrors;
        volatile boolean disposed;
        final Observer<? super T> downstream;
        final Function<? super T, ? extends CompletableSource> mapper;
        Disposable upstream;
        final AtomicThrowable errors = new AtomicThrowable();
        final CompositeDisposable set = new CompositeDisposable();

        @Override // io.reactivex.rxjava3.internal.fuseable.SimpleQueue
        public void clear() {
        }

        @Override // io.reactivex.rxjava3.internal.fuseable.SimpleQueue
        public boolean isEmpty() {
            return true;
        }

        @Override // io.reactivex.rxjava3.internal.fuseable.SimpleQueue
        @Nullable
        public T poll() {
            return null;
        }

        @Override // io.reactivex.rxjava3.internal.fuseable.QueueFuseable
        public int requestFusion(int i) {
            return i & 2;
        }

        a(Observer<? super T> observer, Function<? super T, ? extends CompletableSource> function, boolean z) {
            this.downstream = observer;
            this.mapper = function;
            this.delayErrors = z;
            lazySet(1);
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
                CompletableSource completableSource = (CompletableSource) Objects.requireNonNull(this.mapper.apply(t), "The mapper returned a null CompletableSource");
                getAndIncrement();
                C0325a aVar = new C0325a();
                if (!this.disposed && this.set.add(aVar)) {
                    completableSource.subscribe(aVar);
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.upstream.dispose();
                onError(th);
            }
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onError(Throwable th) {
            if (!this.errors.tryAddThrowableOrReport(th)) {
                return;
            }
            if (!this.delayErrors) {
                this.disposed = true;
                this.upstream.dispose();
                this.set.dispose();
                this.errors.tryTerminateConsumer(this.downstream);
            } else if (decrementAndGet() == 0) {
                this.errors.tryTerminateConsumer(this.downstream);
            }
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onComplete() {
            if (decrementAndGet() == 0) {
                this.errors.tryTerminateConsumer(this.downstream);
            }
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            this.disposed = true;
            this.upstream.dispose();
            this.set.dispose();
            this.errors.tryTerminateAndReport();
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.upstream.isDisposed();
        }

        void a(a<T>.C0325a aVar) {
            this.set.delete(aVar);
            onComplete();
        }

        void a(a<T>.C0325a aVar, Throwable th) {
            this.set.delete(aVar);
            onError(th);
        }

        /* renamed from: io.reactivex.rxjava3.internal.operators.observable.ObservableFlatMapCompletable$a$a  reason: collision with other inner class name */
        /* loaded from: classes5.dex */
        final class C0325a extends AtomicReference<Disposable> implements CompletableObserver, Disposable {
            private static final long serialVersionUID = 8606673141535671828L;

            C0325a() {
            }

            @Override // io.reactivex.rxjava3.core.CompletableObserver
            public void onSubscribe(Disposable disposable) {
                DisposableHelper.setOnce(this, disposable);
            }

            @Override // io.reactivex.rxjava3.core.CompletableObserver
            public void onComplete() {
                a.this.a(this);
            }

            @Override // io.reactivex.rxjava3.core.CompletableObserver
            public void onError(Throwable th) {
                a.this.a(this, th);
            }

            @Override // io.reactivex.rxjava3.disposables.Disposable
            public void dispose() {
                DisposableHelper.dispose(this);
            }

            @Override // io.reactivex.rxjava3.disposables.Disposable
            public boolean isDisposed() {
                return DisposableHelper.isDisposed(get());
            }
        }
    }
}
