package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.SerializedObserver;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes4.dex */
public final class ObservableDebounce<T, U> extends a<T, T> {
    final Function<? super T, ? extends ObservableSource<U>> a;

    public ObservableDebounce(ObservableSource<T> observableSource, Function<? super T, ? extends ObservableSource<U>> function) {
        super(observableSource);
        this.a = function;
    }

    @Override // io.reactivex.Observable
    public void subscribeActual(Observer<? super T> observer) {
        this.source.subscribe(new a(new SerializedObserver(observer), this.a));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class a<T, U> implements Observer<T>, Disposable {
        final Observer<? super T> a;
        final Function<? super T, ? extends ObservableSource<U>> b;
        Disposable c;
        final AtomicReference<Disposable> d = new AtomicReference<>();
        volatile long e;
        boolean f;

        a(Observer<? super T> observer, Function<? super T, ? extends ObservableSource<U>> function) {
            this.a = observer;
            this.b = function;
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.c, disposable)) {
                this.c = disposable;
                this.a.onSubscribe(this);
            }
        }

        @Override // io.reactivex.Observer
        public void onNext(T t) {
            if (!this.f) {
                long j = this.e + 1;
                this.e = j;
                Disposable disposable = this.d.get();
                if (disposable != null) {
                    disposable.dispose();
                }
                try {
                    ObservableSource observableSource = (ObservableSource) ObjectHelper.requireNonNull(this.b.apply(t), "The ObservableSource supplied is null");
                    C0256a aVar = new C0256a(this, j, t);
                    if (this.d.compareAndSet(disposable, aVar)) {
                        observableSource.subscribe(aVar);
                    }
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    dispose();
                    this.a.onError(th);
                }
            }
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            DisposableHelper.dispose(this.d);
            this.a.onError(th);
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            if (!this.f) {
                this.f = true;
                Disposable disposable = this.d.get();
                if (disposable != DisposableHelper.DISPOSED) {
                    C0256a aVar = (C0256a) disposable;
                    if (aVar != null) {
                        aVar.a();
                    }
                    DisposableHelper.dispose(this.d);
                    this.a.onComplete();
                }
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            this.c.dispose();
            DisposableHelper.dispose(this.d);
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.c.isDisposed();
        }

        void a(long j, T t) {
            if (j == this.e) {
                this.a.onNext(t);
            }
        }

        /* renamed from: io.reactivex.internal.operators.observable.ObservableDebounce$a$a  reason: collision with other inner class name */
        /* loaded from: classes4.dex */
        static final class C0256a<T, U> extends DisposableObserver<U> {
            final a<T, U> a;
            final long b;
            final T c;
            boolean d;
            final AtomicBoolean e = new AtomicBoolean();

            C0256a(a<T, U> aVar, long j, T t) {
                this.a = aVar;
                this.b = j;
                this.c = t;
            }

            @Override // io.reactivex.Observer
            public void onNext(U u) {
                if (!this.d) {
                    this.d = true;
                    dispose();
                    a();
                }
            }

            void a() {
                if (this.e.compareAndSet(false, true)) {
                    this.a.a(this.b, this.c);
                }
            }

            @Override // io.reactivex.Observer
            public void onError(Throwable th) {
                if (this.d) {
                    RxJavaPlugins.onError(th);
                    return;
                }
                this.d = true;
                this.a.onError(th);
            }

            @Override // io.reactivex.Observer
            public void onComplete() {
                if (!this.d) {
                    this.d = true;
                    a();
                }
            }
        }
    }
}
