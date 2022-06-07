package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.SequentialDisposable;
import io.reactivex.plugins.RxJavaPlugins;

/* loaded from: classes4.dex */
public final class ObservableOnErrorNext<T> extends a<T, T> {
    final Function<? super Throwable, ? extends ObservableSource<? extends T>> a;
    final boolean b;

    public ObservableOnErrorNext(ObservableSource<T> observableSource, Function<? super Throwable, ? extends ObservableSource<? extends T>> function, boolean z) {
        super(observableSource);
        this.a = function;
        this.b = z;
    }

    @Override // io.reactivex.Observable
    public void subscribeActual(Observer<? super T> observer) {
        a aVar = new a(observer, this.a, this.b);
        observer.onSubscribe(aVar.d);
        this.source.subscribe(aVar);
    }

    /* loaded from: classes4.dex */
    static final class a<T> implements Observer<T> {
        final Observer<? super T> a;
        final Function<? super Throwable, ? extends ObservableSource<? extends T>> b;
        final boolean c;
        final SequentialDisposable d = new SequentialDisposable();
        boolean e;
        boolean f;

        a(Observer<? super T> observer, Function<? super Throwable, ? extends ObservableSource<? extends T>> function, boolean z) {
            this.a = observer;
            this.b = function;
            this.c = z;
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            this.d.replace(disposable);
        }

        @Override // io.reactivex.Observer
        public void onNext(T t) {
            if (!this.f) {
                this.a.onNext(t);
            }
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            if (!this.e) {
                this.e = true;
                if (!this.c || (th instanceof Exception)) {
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
                } else {
                    this.a.onError(th);
                }
            } else if (this.f) {
                RxJavaPlugins.onError(th);
            } else {
                this.a.onError(th);
            }
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            if (!this.f) {
                this.f = true;
                this.e = true;
                this.a.onComplete();
            }
        }
    }
}
