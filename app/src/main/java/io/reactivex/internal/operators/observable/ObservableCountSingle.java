package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.fuseable.FuseToObservable;
import io.reactivex.plugins.RxJavaPlugins;

/* loaded from: classes4.dex */
public final class ObservableCountSingle<T> extends Single<Long> implements FuseToObservable<Long> {
    final ObservableSource<T> a;

    public ObservableCountSingle(ObservableSource<T> observableSource) {
        this.a = observableSource;
    }

    @Override // io.reactivex.Single
    public void subscribeActual(SingleObserver<? super Long> singleObserver) {
        this.a.subscribe(new a(singleObserver));
    }

    @Override // io.reactivex.internal.fuseable.FuseToObservable
    public Observable<Long> fuseToObservable() {
        return RxJavaPlugins.onAssembly(new ObservableCount(this.a));
    }

    /* loaded from: classes4.dex */
    static final class a implements Observer<Object>, Disposable {
        final SingleObserver<? super Long> a;
        Disposable b;
        long c;

        a(SingleObserver<? super Long> singleObserver) {
            this.a = singleObserver;
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
            this.b = DisposableHelper.DISPOSED;
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.b.isDisposed();
        }

        @Override // io.reactivex.Observer
        public void onNext(Object obj) {
            this.c++;
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            this.b = DisposableHelper.DISPOSED;
            this.a.onError(th);
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            this.b = DisposableHelper.DISPOSED;
            this.a.onSuccess(Long.valueOf(this.c));
        }
    }
}
