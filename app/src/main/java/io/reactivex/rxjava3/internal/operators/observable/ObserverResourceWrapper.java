package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes5.dex */
public final class ObserverResourceWrapper<T> extends AtomicReference<Disposable> implements Observer<T>, Disposable {
    private static final long serialVersionUID = -8612022020200669122L;
    final Observer<? super T> downstream;
    final AtomicReference<Disposable> upstream = new AtomicReference<>();

    public ObserverResourceWrapper(Observer<? super T> observer) {
        this.downstream = observer;
    }

    @Override // io.reactivex.rxjava3.core.Observer
    public void onSubscribe(Disposable disposable) {
        if (DisposableHelper.setOnce(this.upstream, disposable)) {
            this.downstream.onSubscribe(this);
        }
    }

    @Override // io.reactivex.rxjava3.core.Observer
    public void onNext(T t) {
        this.downstream.onNext(t);
    }

    @Override // io.reactivex.rxjava3.core.Observer
    public void onError(Throwable th) {
        dispose();
        this.downstream.onError(th);
    }

    @Override // io.reactivex.rxjava3.core.Observer
    public void onComplete() {
        dispose();
        this.downstream.onComplete();
    }

    @Override // io.reactivex.rxjava3.disposables.Disposable
    public void dispose() {
        DisposableHelper.dispose(this.upstream);
        DisposableHelper.dispose(this);
    }

    @Override // io.reactivex.rxjava3.disposables.Disposable
    public boolean isDisposed() {
        return this.upstream.get() == DisposableHelper.DISPOSED;
    }

    public void setResource(Disposable disposable) {
        DisposableHelper.set(this, disposable);
    }
}
