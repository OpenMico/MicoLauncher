package io.reactivex.internal.operators.observable;

import io.reactivex.Notification;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;

/* loaded from: classes4.dex */
public final class ObservableMaterialize<T> extends a<T, Notification<T>> {
    public ObservableMaterialize(ObservableSource<T> observableSource) {
        super(observableSource);
    }

    @Override // io.reactivex.Observable
    public void subscribeActual(Observer<? super Notification<T>> observer) {
        this.source.subscribe(new a(observer));
    }

    /* loaded from: classes4.dex */
    static final class a<T> implements Observer<T>, Disposable {
        final Observer<? super Notification<T>> a;
        Disposable b;

        a(Observer<? super Notification<T>> observer) {
            this.a = observer;
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.b, disposable)) {
                this.b = disposable;
                this.a.onSubscribe(this);
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            this.b.dispose();
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.b.isDisposed();
        }

        @Override // io.reactivex.Observer
        public void onNext(T t) {
            this.a.onNext(Notification.createOnNext(t));
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            this.a.onNext(Notification.createOnError(th));
            this.a.onComplete();
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            this.a.onNext(Notification.createOnComplete());
            this.a.onComplete();
        }
    }
}
