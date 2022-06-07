package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.plugins.RxJavaPlugins;

/* loaded from: classes4.dex */
public final class ObservableFlattenIterable<T, R> extends a<T, R> {
    final Function<? super T, ? extends Iterable<? extends R>> a;

    public ObservableFlattenIterable(ObservableSource<T> observableSource, Function<? super T, ? extends Iterable<? extends R>> function) {
        super(observableSource);
        this.a = function;
    }

    @Override // io.reactivex.Observable
    protected void subscribeActual(Observer<? super R> observer) {
        this.source.subscribe(new a(observer, this.a));
    }

    /* loaded from: classes4.dex */
    static final class a<T, R> implements Observer<T>, Disposable {
        final Observer<? super R> a;
        final Function<? super T, ? extends Iterable<? extends R>> b;
        Disposable c;

        a(Observer<? super R> observer, Function<? super T, ? extends Iterable<? extends R>> function) {
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
            if (this.c != DisposableHelper.DISPOSED) {
                try {
                    Observer<? super R> observer = this.a;
                    for (T t2 : (Iterable) this.b.apply(t)) {
                        try {
                            try {
                                observer.onNext((Object) ObjectHelper.requireNonNull(t2, "The iterator returned a null value"));
                            } catch (Throwable th) {
                                Exceptions.throwIfFatal(th);
                                this.c.dispose();
                                onError(th);
                                return;
                            }
                        } catch (Throwable th2) {
                            Exceptions.throwIfFatal(th2);
                            this.c.dispose();
                            onError(th2);
                            return;
                        }
                    }
                } catch (Throwable th3) {
                    Exceptions.throwIfFatal(th3);
                    this.c.dispose();
                    onError(th3);
                }
            }
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            if (this.c == DisposableHelper.DISPOSED) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.c = DisposableHelper.DISPOSED;
            this.a.onError(th);
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            if (this.c != DisposableHelper.DISPOSED) {
                this.c = DisposableHelper.DISPOSED;
                this.a.onComplete();
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.c.isDisposed();
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            this.c.dispose();
            this.c = DisposableHelper.DISPOSED;
        }
    }
}
