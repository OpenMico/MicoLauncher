package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.BiFunction;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.internal.disposables.EmptyDisposable;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.Iterator;
import java.util.Objects;

/* loaded from: classes5.dex */
public final class ObservableZipIterable<T, U, V> extends Observable<V> {
    final Observable<? extends T> a;
    final Iterable<U> b;
    final BiFunction<? super T, ? super U, ? extends V> c;

    public ObservableZipIterable(Observable<? extends T> observable, Iterable<U> iterable, BiFunction<? super T, ? super U, ? extends V> biFunction) {
        this.a = observable;
        this.b = iterable;
        this.c = biFunction;
    }

    @Override // io.reactivex.rxjava3.core.Observable
    public void subscribeActual(Observer<? super V> observer) {
        try {
            Iterator it = (Iterator) Objects.requireNonNull(this.b.iterator(), "The iterator returned by other is null");
            try {
                if (!it.hasNext()) {
                    EmptyDisposable.complete(observer);
                } else {
                    this.a.subscribe(new a(observer, it, this.c));
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                EmptyDisposable.error(th, observer);
            }
        } catch (Throwable th2) {
            Exceptions.throwIfFatal(th2);
            EmptyDisposable.error(th2, observer);
        }
    }

    /* loaded from: classes5.dex */
    static final class a<T, U, V> implements Observer<T>, Disposable {
        final Observer<? super V> a;
        final Iterator<U> b;
        final BiFunction<? super T, ? super U, ? extends V> c;
        Disposable d;
        boolean e;

        a(Observer<? super V> observer, Iterator<U> it, BiFunction<? super T, ? super U, ? extends V> biFunction) {
            this.a = observer;
            this.b = it;
            this.c = biFunction;
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.d, disposable)) {
                this.d = disposable;
                this.a.onSubscribe(this);
            }
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            this.d.dispose();
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.d.isDisposed();
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onNext(T t) {
            if (!this.e) {
                try {
                    try {
                        this.a.onNext(Objects.requireNonNull(this.c.apply(t, Objects.requireNonNull(this.b.next(), "The iterator returned a null value")), "The zipper function returned a null value"));
                        try {
                            if (!this.b.hasNext()) {
                                this.e = true;
                                this.d.dispose();
                                this.a.onComplete();
                            }
                        } catch (Throwable th) {
                            Exceptions.throwIfFatal(th);
                            a(th);
                        }
                    } catch (Throwable th2) {
                        Exceptions.throwIfFatal(th2);
                        a(th2);
                    }
                } catch (Throwable th3) {
                    Exceptions.throwIfFatal(th3);
                    a(th3);
                }
            }
        }

        void a(Throwable th) {
            this.e = true;
            this.d.dispose();
            this.a.onError(th);
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onError(Throwable th) {
            if (this.e) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.e = true;
            this.a.onError(th);
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onComplete() {
            if (!this.e) {
                this.e = true;
                this.a.onComplete();
            }
        }
    }
}
