package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.Functions;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.FuseToObservable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.Collection;
import java.util.concurrent.Callable;

/* loaded from: classes4.dex */
public final class ObservableToListSingle<T, U extends Collection<? super T>> extends Single<U> implements FuseToObservable<U> {
    final ObservableSource<T> a;
    final Callable<U> b;

    public ObservableToListSingle(ObservableSource<T> observableSource, int i) {
        this.a = observableSource;
        this.b = Functions.createArrayList(i);
    }

    public ObservableToListSingle(ObservableSource<T> observableSource, Callable<U> callable) {
        this.a = observableSource;
        this.b = callable;
    }

    @Override // io.reactivex.Single
    public void subscribeActual(SingleObserver<? super U> singleObserver) {
        try {
            this.a.subscribe(new a(singleObserver, (Collection) ObjectHelper.requireNonNull(this.b.call(), "The collectionSupplier returned a null collection. Null values are generally not allowed in 2.x operators and sources.")));
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            EmptyDisposable.error(th, singleObserver);
        }
    }

    @Override // io.reactivex.internal.fuseable.FuseToObservable
    public Observable<U> fuseToObservable() {
        return RxJavaPlugins.onAssembly(new ObservableToList(this.a, this.b));
    }

    /* loaded from: classes4.dex */
    static final class a<T, U extends Collection<? super T>> implements Observer<T>, Disposable {
        final SingleObserver<? super U> a;
        U b;
        Disposable c;

        a(SingleObserver<? super U> singleObserver, U u) {
            this.a = singleObserver;
            this.b = u;
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.c, disposable)) {
                this.c = disposable;
                this.a.onSubscribe(this);
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            this.c.dispose();
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.c.isDisposed();
        }

        @Override // io.reactivex.Observer
        public void onNext(T t) {
            this.b.add(t);
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            this.b = null;
            this.a.onError(th);
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            U u = this.b;
            this.b = null;
            this.a.onSuccess(u);
        }
    }
}
