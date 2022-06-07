package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.observers.SerializedObserver;

/* loaded from: classes4.dex */
public final class ObservableSerialized<T> extends a<T, T> {
    public ObservableSerialized(Observable<T> observable) {
        super(observable);
    }

    @Override // io.reactivex.Observable
    protected void subscribeActual(Observer<? super T> observer) {
        this.source.subscribe(new SerializedObserver(observer));
    }
}
