package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.internal.fuseable.ScalarCallable;
import io.reactivex.internal.operators.observable.ObservableScalarXMap;

/* loaded from: classes4.dex */
public final class ObservableJust<T> extends Observable<T> implements ScalarCallable<T> {
    private final T a;

    public ObservableJust(T t) {
        this.a = t;
    }

    @Override // io.reactivex.Observable
    protected void subscribeActual(Observer<? super T> observer) {
        ObservableScalarXMap.ScalarDisposable scalarDisposable = new ObservableScalarXMap.ScalarDisposable(observer, this.a);
        observer.onSubscribe(scalarDisposable);
        scalarDisposable.run();
    }

    @Override // io.reactivex.internal.fuseable.ScalarCallable, java.util.concurrent.Callable
    public T call() {
        return this.a;
    }
}
