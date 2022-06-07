package io.reactivex.internal.operators.observable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.fuseable.FuseToObservable;
import io.reactivex.plugins.RxJavaPlugins;

/* loaded from: classes4.dex */
public final class ObservableIgnoreElementsCompletable<T> extends Completable implements FuseToObservable<T> {
    final ObservableSource<T> a;

    public ObservableIgnoreElementsCompletable(ObservableSource<T> observableSource) {
        this.a = observableSource;
    }

    @Override // io.reactivex.Completable
    public void subscribeActual(CompletableObserver completableObserver) {
        this.a.subscribe(new a(completableObserver));
    }

    @Override // io.reactivex.internal.fuseable.FuseToObservable
    public Observable<T> fuseToObservable() {
        return RxJavaPlugins.onAssembly(new ObservableIgnoreElements(this.a));
    }

    /* loaded from: classes4.dex */
    static final class a<T> implements Observer<T>, Disposable {
        final CompletableObserver a;
        Disposable b;

        @Override // io.reactivex.Observer
        public void onNext(T t) {
        }

        a(CompletableObserver completableObserver) {
            this.a = completableObserver;
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            this.b = disposable;
            this.a.onSubscribe(this);
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            this.a.onError(th);
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            this.a.onComplete();
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            this.b.dispose();
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.b.isDisposed();
        }
    }
}
