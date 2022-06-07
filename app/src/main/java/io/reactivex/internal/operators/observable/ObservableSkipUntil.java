package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.ArrayCompositeDisposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.observers.SerializedObserver;

/* loaded from: classes4.dex */
public final class ObservableSkipUntil<T, U> extends a<T, T> {
    final ObservableSource<U> a;

    public ObservableSkipUntil(ObservableSource<T> observableSource, ObservableSource<U> observableSource2) {
        super(observableSource);
        this.a = observableSource2;
    }

    @Override // io.reactivex.Observable
    public void subscribeActual(Observer<? super T> observer) {
        SerializedObserver serializedObserver = new SerializedObserver(observer);
        ArrayCompositeDisposable arrayCompositeDisposable = new ArrayCompositeDisposable(2);
        serializedObserver.onSubscribe(arrayCompositeDisposable);
        b bVar = new b(serializedObserver, arrayCompositeDisposable);
        this.a.subscribe(new a(arrayCompositeDisposable, bVar, serializedObserver));
        this.source.subscribe(bVar);
    }

    /* loaded from: classes4.dex */
    static final class b<T> implements Observer<T> {
        final Observer<? super T> a;
        final ArrayCompositeDisposable b;
        Disposable c;
        volatile boolean d;
        boolean e;

        b(Observer<? super T> observer, ArrayCompositeDisposable arrayCompositeDisposable) {
            this.a = observer;
            this.b = arrayCompositeDisposable;
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.c, disposable)) {
                this.c = disposable;
                this.b.setResource(0, disposable);
            }
        }

        @Override // io.reactivex.Observer
        public void onNext(T t) {
            if (this.e) {
                this.a.onNext(t);
            } else if (this.d) {
                this.e = true;
                this.a.onNext(t);
            }
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            this.b.dispose();
            this.a.onError(th);
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            this.b.dispose();
            this.a.onComplete();
        }
    }

    /* loaded from: classes4.dex */
    final class a implements Observer<U> {
        final ArrayCompositeDisposable a;
        final b<T> b;
        final SerializedObserver<T> c;
        Disposable d;

        a(ArrayCompositeDisposable arrayCompositeDisposable, b<T> bVar, SerializedObserver<T> serializedObserver) {
            this.a = arrayCompositeDisposable;
            this.b = bVar;
            this.c = serializedObserver;
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.d, disposable)) {
                this.d = disposable;
                this.a.setResource(1, disposable);
            }
        }

        @Override // io.reactivex.Observer
        public void onNext(U u) {
            this.d.dispose();
            this.b.d = true;
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            this.a.dispose();
            this.c.onError(th);
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            this.b.d = true;
        }
    }
}
