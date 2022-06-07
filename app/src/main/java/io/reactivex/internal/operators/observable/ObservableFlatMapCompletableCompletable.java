package io.reactivex.internal.operators.observable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.FuseToObservable;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes4.dex */
public final class ObservableFlatMapCompletableCompletable<T> extends Completable implements FuseToObservable<T> {
    final ObservableSource<T> a;
    final Function<? super T, ? extends CompletableSource> b;
    final boolean c;

    public ObservableFlatMapCompletableCompletable(ObservableSource<T> observableSource, Function<? super T, ? extends CompletableSource> function, boolean z) {
        this.a = observableSource;
        this.b = function;
        this.c = z;
    }

    @Override // io.reactivex.Completable
    protected void subscribeActual(CompletableObserver completableObserver) {
        this.a.subscribe(new a(completableObserver, this.b, this.c));
    }

    @Override // io.reactivex.internal.fuseable.FuseToObservable
    public Observable<T> fuseToObservable() {
        return RxJavaPlugins.onAssembly(new ObservableFlatMapCompletable(this.a, this.b, this.c));
    }

    /* loaded from: classes4.dex */
    static final class a<T> extends AtomicInteger implements Observer<T>, Disposable {
        private static final long serialVersionUID = 8443155186132538303L;
        final boolean delayErrors;
        volatile boolean disposed;
        final CompletableObserver downstream;
        final Function<? super T, ? extends CompletableSource> mapper;
        Disposable upstream;
        final AtomicThrowable errors = new AtomicThrowable();
        final CompositeDisposable set = new CompositeDisposable();

        a(CompletableObserver completableObserver, Function<? super T, ? extends CompletableSource> function, boolean z) {
            this.downstream = completableObserver;
            this.mapper = function;
            this.delayErrors = z;
            lazySet(1);
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.upstream, disposable)) {
                this.upstream = disposable;
                this.downstream.onSubscribe(this);
            }
        }

        @Override // io.reactivex.Observer
        public void onNext(T t) {
            try {
                CompletableSource completableSource = (CompletableSource) ObjectHelper.requireNonNull(this.mapper.apply(t), "The mapper returned a null CompletableSource");
                getAndIncrement();
                C0260a aVar = new C0260a();
                if (!this.disposed && this.set.add(aVar)) {
                    completableSource.subscribe(aVar);
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.upstream.dispose();
                onError(th);
            }
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            if (!this.errors.addThrowable(th)) {
                RxJavaPlugins.onError(th);
            } else if (!this.delayErrors) {
                dispose();
                if (getAndSet(0) > 0) {
                    this.downstream.onError(this.errors.terminate());
                }
            } else if (decrementAndGet() == 0) {
                this.downstream.onError(this.errors.terminate());
            }
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            if (decrementAndGet() == 0) {
                Throwable terminate = this.errors.terminate();
                if (terminate != null) {
                    this.downstream.onError(terminate);
                } else {
                    this.downstream.onComplete();
                }
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            this.disposed = true;
            this.upstream.dispose();
            this.set.dispose();
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.upstream.isDisposed();
        }

        void a(a<T>.C0260a aVar) {
            this.set.delete(aVar);
            onComplete();
        }

        void a(a<T>.C0260a aVar, Throwable th) {
            this.set.delete(aVar);
            onError(th);
        }

        /* renamed from: io.reactivex.internal.operators.observable.ObservableFlatMapCompletableCompletable$a$a  reason: collision with other inner class name */
        /* loaded from: classes4.dex */
        final class C0260a extends AtomicReference<Disposable> implements CompletableObserver, Disposable {
            private static final long serialVersionUID = 8606673141535671828L;

            C0260a() {
            }

            @Override // io.reactivex.CompletableObserver, io.reactivex.MaybeObserver, io.reactivex.SingleObserver
            public void onSubscribe(Disposable disposable) {
                DisposableHelper.setOnce(this, disposable);
            }

            @Override // io.reactivex.CompletableObserver, io.reactivex.MaybeObserver
            public void onComplete() {
                a.this.a(this);
            }

            @Override // io.reactivex.CompletableObserver, io.reactivex.MaybeObserver, io.reactivex.SingleObserver
            public void onError(Throwable th) {
                a.this.a(this, th);
            }

            @Override // io.reactivex.disposables.Disposable
            public void dispose() {
                DisposableHelper.dispose(this);
            }

            @Override // io.reactivex.disposables.Disposable
            public boolean isDisposed() {
                return DisposableHelper.isDisposed(get());
            }
        }
    }
}
