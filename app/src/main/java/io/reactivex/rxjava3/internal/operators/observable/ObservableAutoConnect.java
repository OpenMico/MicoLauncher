package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.observables.ConnectableObservable;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes5.dex */
public final class ObservableAutoConnect<T> extends Observable<T> {
    final ConnectableObservable<? extends T> a;
    final int b;
    final Consumer<? super Disposable> c;
    final AtomicInteger d = new AtomicInteger();

    public ObservableAutoConnect(ConnectableObservable<? extends T> connectableObservable, int i, Consumer<? super Disposable> consumer) {
        this.a = connectableObservable;
        this.b = i;
        this.c = consumer;
    }

    @Override // io.reactivex.rxjava3.core.Observable
    public void subscribeActual(Observer<? super T> observer) {
        this.a.subscribe((Observer<? super Object>) observer);
        if (this.d.incrementAndGet() == this.b) {
            this.a.connect(this.c);
        }
    }
}
