package io.reactivex.internal.operators.single;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.observers.DeferredScalarDisposable;

/* loaded from: classes4.dex */
public final class SingleToObservable<T> extends Observable<T> {
    final SingleSource<? extends T> a;

    public SingleToObservable(SingleSource<? extends T> singleSource) {
        this.a = singleSource;
    }

    @Override // io.reactivex.Observable
    public void subscribeActual(Observer<? super T> observer) {
        this.a.subscribe(create(observer));
    }

    public static <T> SingleObserver<T> create(Observer<? super T> observer) {
        return new a(observer);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class a<T> extends DeferredScalarDisposable<T> implements SingleObserver<T> {
        private static final long serialVersionUID = 3786543492451018833L;
        Disposable upstream;

        a(Observer<? super T> observer) {
            super(observer);
        }

        @Override // io.reactivex.SingleObserver
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.upstream, disposable)) {
                this.upstream = disposable;
                this.downstream.onSubscribe(this);
            }
        }

        @Override // io.reactivex.SingleObserver
        public void onSuccess(T t) {
            complete(t);
        }

        @Override // io.reactivex.SingleObserver
        public void onError(Throwable th) {
            error(th);
        }

        @Override // io.reactivex.internal.observers.DeferredScalarDisposable, io.reactivex.disposables.Disposable
        public void dispose() {
            super.dispose();
            this.upstream.dispose();
        }
    }
}
