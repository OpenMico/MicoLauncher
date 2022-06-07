package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;

/* loaded from: classes5.dex */
public final class ObservableFromUnsafeSource<T> extends Observable<T> {
    final ObservableSource<T> a;

    public ObservableFromUnsafeSource(ObservableSource<T> observableSource) {
        this.a = observableSource;
    }

    @Override // io.reactivex.rxjava3.core.Observable
    protected void subscribeActual(Observer<? super T> observer) {
        this.a.subscribe(observer);
    }
}
