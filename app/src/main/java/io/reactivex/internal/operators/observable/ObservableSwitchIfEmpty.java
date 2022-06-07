package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.SequentialDisposable;

/* loaded from: classes4.dex */
public final class ObservableSwitchIfEmpty<T> extends a<T, T> {
    final ObservableSource<? extends T> a;

    public ObservableSwitchIfEmpty(ObservableSource<T> observableSource, ObservableSource<? extends T> observableSource2) {
        super(observableSource);
        this.a = observableSource2;
    }

    @Override // io.reactivex.Observable
    public void subscribeActual(Observer<? super T> observer) {
        a aVar = new a(observer, this.a);
        observer.onSubscribe(aVar.c);
        this.source.subscribe(aVar);
    }

    /* loaded from: classes4.dex */
    static final class a<T> implements Observer<T> {
        final Observer<? super T> a;
        final ObservableSource<? extends T> b;
        boolean d = true;
        final SequentialDisposable c = new SequentialDisposable();

        a(Observer<? super T> observer, ObservableSource<? extends T> observableSource) {
            this.a = observer;
            this.b = observableSource;
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            this.c.update(disposable);
        }

        @Override // io.reactivex.Observer
        public void onNext(T t) {
            if (this.d) {
                this.d = false;
            }
            this.a.onNext(t);
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            this.a.onError(th);
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            if (this.d) {
                this.d = false;
                this.b.subscribe(this);
                return;
            }
            this.a.onComplete();
        }
    }
}
