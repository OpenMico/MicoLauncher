package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;

/* loaded from: classes4.dex */
public final class ObservableTakeLastOne<T> extends a<T, T> {
    public ObservableTakeLastOne(ObservableSource<T> observableSource) {
        super(observableSource);
    }

    @Override // io.reactivex.Observable
    public void subscribeActual(Observer<? super T> observer) {
        this.source.subscribe(new a(observer));
    }

    /* loaded from: classes4.dex */
    static final class a<T> implements Observer<T>, Disposable {
        final Observer<? super T> a;
        Disposable b;
        T c;

        a(Observer<? super T> observer) {
            this.a = observer;
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.b, disposable)) {
                this.b = disposable;
                this.a.onSubscribe(this);
            }
        }

        @Override // io.reactivex.Observer
        public void onNext(T t) {
            this.c = t;
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            this.c = null;
            this.a.onError(th);
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            a();
        }

        void a() {
            T t = this.c;
            if (t != null) {
                this.c = null;
                this.a.onNext(t);
            }
            this.a.onComplete();
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            this.c = null;
            this.b.dispose();
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.b.isDisposed();
        }
    }
}
