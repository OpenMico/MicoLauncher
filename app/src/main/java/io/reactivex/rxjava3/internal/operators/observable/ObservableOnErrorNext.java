package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.CompositeException;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.disposables.SequentialDisposable;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;

/* loaded from: classes5.dex */
public final class ObservableOnErrorNext<T> extends a<T, T> {
    final Function<? super Throwable, ? extends ObservableSource<? extends T>> a;

    public ObservableOnErrorNext(ObservableSource<T> observableSource, Function<? super Throwable, ? extends ObservableSource<? extends T>> function) {
        super(observableSource);
        this.a = function;
    }

    @Override // io.reactivex.rxjava3.core.Observable
    public void subscribeActual(Observer<? super T> observer) {
        a aVar = new a(observer, this.a);
        observer.onSubscribe(aVar.c);
        this.source.subscribe(aVar);
    }

    /* loaded from: classes5.dex */
    static final class a<T> implements Observer<T> {
        final Observer<? super T> a;
        final Function<? super Throwable, ? extends ObservableSource<? extends T>> b;
        final SequentialDisposable c = new SequentialDisposable();
        boolean d;
        boolean e;

        a(Observer<? super T> observer, Function<? super Throwable, ? extends ObservableSource<? extends T>> function) {
            this.a = observer;
            this.b = function;
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onSubscribe(Disposable disposable) {
            this.c.replace(disposable);
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onNext(T t) {
            if (!this.e) {
                this.a.onNext(t);
            }
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onError(Throwable th) {
            if (!this.d) {
                this.d = true;
                try {
                    ObservableSource observableSource = (ObservableSource) this.b.apply(th);
                    if (observableSource == null) {
                        NullPointerException nullPointerException = new NullPointerException("Observable is null");
                        nullPointerException.initCause(th);
                        this.a.onError(nullPointerException);
                        return;
                    }
                    observableSource.subscribe(this);
                } catch (Throwable th2) {
                    Exceptions.throwIfFatal(th2);
                    this.a.onError(new CompositeException(th, th2));
                }
            } else if (this.e) {
                RxJavaPlugins.onError(th);
            } else {
                this.a.onError(th);
            }
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onComplete() {
            if (!this.e) {
                this.e = true;
                this.d = true;
                this.a.onComplete();
            }
        }
    }
}
