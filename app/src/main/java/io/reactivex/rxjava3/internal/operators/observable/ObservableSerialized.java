package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.observers.SerializedObserver;

/* loaded from: classes5.dex */
public final class ObservableSerialized<T> extends a<T, T> {
    public ObservableSerialized(Observable<T> observable) {
        super(observable);
    }

    @Override // io.reactivex.rxjava3.core.Observable
    protected void subscribeActual(Observer<? super T> observer) {
        this.source.subscribe(new SerializedObserver(observer));
    }
}
