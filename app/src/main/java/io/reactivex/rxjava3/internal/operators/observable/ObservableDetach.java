package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.internal.util.EmptyComponent;

/* loaded from: classes5.dex */
public final class ObservableDetach<T> extends a<T, T> {
    public ObservableDetach(ObservableSource<T> observableSource) {
        super(observableSource);
    }

    @Override // io.reactivex.rxjava3.core.Observable
    protected void subscribeActual(Observer<? super T> observer) {
        this.source.subscribe(new a(observer));
    }

    /* loaded from: classes5.dex */
    static final class a<T> implements Observer<T>, Disposable {
        Observer<? super T> a;
        Disposable b;

        a(Observer<? super T> observer) {
            this.a = observer;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            Disposable disposable = this.b;
            this.b = EmptyComponent.INSTANCE;
            this.a = EmptyComponent.asObserver();
            disposable.dispose();
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.b.isDisposed();
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.b, disposable)) {
                this.b = disposable;
                this.a.onSubscribe(this);
            }
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onNext(T t) {
            this.a.onNext(t);
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onError(Throwable th) {
            Observer<? super T> observer = this.a;
            this.b = EmptyComponent.INSTANCE;
            this.a = EmptyComponent.asObserver();
            observer.onError(th);
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onComplete() {
            Observer<? super T> observer = this.a;
            this.b = EmptyComponent.INSTANCE;
            this.a = EmptyComponent.asObserver();
            observer.onComplete();
        }
    }
}
