package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.CompletableSource;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.internal.fuseable.AbstractEmptyQueueFuseable;
import io.reactivex.rxjava3.internal.fuseable.HasUpstreamCompletableSource;

/* loaded from: classes5.dex */
public final class ObservableFromCompletable<T> extends Observable<T> implements HasUpstreamCompletableSource {
    final CompletableSource a;

    public ObservableFromCompletable(CompletableSource completableSource) {
        this.a = completableSource;
    }

    @Override // io.reactivex.rxjava3.internal.fuseable.HasUpstreamCompletableSource
    public CompletableSource source() {
        return this.a;
    }

    @Override // io.reactivex.rxjava3.core.Observable
    protected void subscribeActual(Observer<? super T> observer) {
        this.a.subscribe(new FromCompletableObserver(observer));
    }

    /* loaded from: classes5.dex */
    public static final class FromCompletableObserver<T> extends AbstractEmptyQueueFuseable<T> implements CompletableObserver {
        final Observer<? super T> a;
        Disposable b;

        public FromCompletableObserver(Observer<? super T> observer) {
            this.a = observer;
        }

        @Override // io.reactivex.rxjava3.internal.fuseable.AbstractEmptyQueueFuseable, io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            this.b.dispose();
            this.b = DisposableHelper.DISPOSED;
        }

        @Override // io.reactivex.rxjava3.internal.fuseable.AbstractEmptyQueueFuseable, io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.b.isDisposed();
        }

        @Override // io.reactivex.rxjava3.core.CompletableObserver
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.b, disposable)) {
                this.b = disposable;
                this.a.onSubscribe(this);
            }
        }

        @Override // io.reactivex.rxjava3.core.CompletableObserver
        public void onComplete() {
            this.b = DisposableHelper.DISPOSED;
            this.a.onComplete();
        }

        @Override // io.reactivex.rxjava3.core.CompletableObserver
        public void onError(Throwable th) {
            this.b = DisposableHelper.DISPOSED;
            this.a.onError(th);
        }
    }
}
