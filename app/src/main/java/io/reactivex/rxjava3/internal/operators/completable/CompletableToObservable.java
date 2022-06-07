package io.reactivex.rxjava3.internal.operators.completable;

import io.reactivex.rxjava3.core.CompletableSource;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.internal.operators.observable.ObservableFromCompletable;

/* loaded from: classes4.dex */
public final class CompletableToObservable<T> extends Observable<T> {
    final CompletableSource a;

    public CompletableToObservable(CompletableSource completableSource) {
        this.a = completableSource;
    }

    @Override // io.reactivex.rxjava3.core.Observable
    protected void subscribeActual(Observer<? super T> observer) {
        this.a.subscribe(new ObservableFromCompletable.FromCompletableObserver(observer));
    }
}
