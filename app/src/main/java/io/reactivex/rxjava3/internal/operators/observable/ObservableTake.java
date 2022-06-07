package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.internal.disposables.EmptyDisposable;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;

/* loaded from: classes5.dex */
public final class ObservableTake<T> extends a<T, T> {
    final long a;

    public ObservableTake(ObservableSource<T> observableSource, long j) {
        super(observableSource);
        this.a = j;
    }

    @Override // io.reactivex.rxjava3.core.Observable
    protected void subscribeActual(Observer<? super T> observer) {
        this.source.subscribe(new a(observer, this.a));
    }

    /* loaded from: classes5.dex */
    static final class a<T> implements Observer<T>, Disposable {
        final Observer<? super T> a;
        boolean b;
        Disposable c;
        long d;

        a(Observer<? super T> observer, long j) {
            this.a = observer;
            this.d = j;
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.c, disposable)) {
                this.c = disposable;
                if (this.d == 0) {
                    this.b = true;
                    disposable.dispose();
                    EmptyDisposable.complete(this.a);
                    return;
                }
                this.a.onSubscribe(this);
            }
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onNext(T t) {
            if (!this.b) {
                long j = this.d;
                this.d = j - 1;
                if (j > 0) {
                    boolean z = this.d == 0;
                    this.a.onNext(t);
                    if (z) {
                        onComplete();
                    }
                }
            }
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onError(Throwable th) {
            if (this.b) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.b = true;
            this.c.dispose();
            this.a.onError(th);
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onComplete() {
            if (!this.b) {
                this.b = true;
                this.c.dispose();
                this.a.onComplete();
            }
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            this.c.dispose();
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.c.isDisposed();
        }
    }
}
