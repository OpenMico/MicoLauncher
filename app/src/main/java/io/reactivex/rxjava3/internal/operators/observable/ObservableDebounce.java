package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.observers.DisposableObserver;
import io.reactivex.rxjava3.observers.SerializedObserver;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes5.dex */
public final class ObservableDebounce<T, U> extends a<T, T> {
    final Function<? super T, ? extends ObservableSource<U>> a;

    public ObservableDebounce(ObservableSource<T> observableSource, Function<? super T, ? extends ObservableSource<U>> function) {
        super(observableSource);
        this.a = function;
    }

    @Override // io.reactivex.rxjava3.core.Observable
    public void subscribeActual(Observer<? super T> observer) {
        this.source.subscribe(new a(new SerializedObserver(observer), this.a));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
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

        @Override // io.reactivex.rxjava3.core.Observer
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.c, disposable)) {
                this.c = disposable;
                this.a.onSubscribe(this);
            }
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onNext(T t) {
            if (!this.f) {
                long j = this.e + 1;
                this.e = j;
                Disposable disposable = this.d.get();
                if (disposable != null) {
                    disposable.dispose();
                }
                try {
                    ObservableSource observableSource = (ObservableSource) Objects.requireNonNull(this.b.apply(t), "The ObservableSource supplied is null");
                    C0322a aVar = new C0322a(this, j, t);
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

        @Override // io.reactivex.rxjava3.core.Observer
        public void onError(Throwable th) {
            DisposableHelper.dispose(this.d);
            this.a.onError(th);
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onComplete() {
            if (!this.f) {
                this.f = true;
                Disposable disposable = this.d.get();
                if (disposable != DisposableHelper.DISPOSED) {
                    C0322a aVar = (C0322a) disposable;
                    if (aVar != null) {
                        aVar.a();
                    }
                    DisposableHelper.dispose(this.d);
                    this.a.onComplete();
                }
            }
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            this.c.dispose();
            DisposableHelper.dispose(this.d);
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.c.isDisposed();
        }

        void a(long j, T t) {
            if (j == this.e) {
                this.a.onNext(t);
            }
        }

        /* renamed from: io.reactivex.rxjava3.internal.operators.observable.ObservableDebounce$a$a  reason: collision with other inner class name */
        /* loaded from: classes5.dex */
        static final class C0322a<T, U> extends DisposableObserver<U> {
            final a<T, U> a;
            final long b;
            final T c;
            boolean d;
            final AtomicBoolean e = new AtomicBoolean();

            C0322a(a<T, U> aVar, long j, T t) {
                this.a = aVar;
                this.b = j;
                this.c = t;
            }

            @Override // io.reactivex.rxjava3.core.Observer
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

            @Override // io.reactivex.rxjava3.core.Observer
            public void onError(Throwable th) {
                if (this.d) {
                    RxJavaPlugins.onError(th);
                    return;
                }
                this.d = true;
                this.a.onError(th);
            }

            @Override // io.reactivex.rxjava3.core.Observer
            public void onComplete() {
                if (!this.d) {
                    this.d = true;
                    a();
                }
            }
        }
    }
}
