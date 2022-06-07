package io.reactivex.internal.operators.mixed;

import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes4.dex */
public final class CompletableAndThenObservable<R> extends Observable<R> {
    final CompletableSource a;
    final ObservableSource<? extends R> b;

    public CompletableAndThenObservable(CompletableSource completableSource, ObservableSource<? extends R> observableSource) {
        this.a = completableSource;
        this.b = observableSource;
    }

    @Override // io.reactivex.Observable
    protected void subscribeActual(Observer<? super R> observer) {
        a aVar = new a(observer, this.b);
        observer.onSubscribe(aVar);
        this.a.subscribe(aVar);
    }

    /* loaded from: classes4.dex */
    static final class a<R> extends AtomicReference<Disposable> implements CompletableObserver, Observer<R>, Disposable {
        private static final long serialVersionUID = -8948264376121066672L;
        final Observer<? super R> downstream;
        ObservableSource<? extends R> other;

        a(Observer<? super R> observer, ObservableSource<? extends R> observableSource) {
            this.other = observableSource;
            this.downstream = observer;
        }

        @Override // io.reactivex.Observer
        public void onNext(R r) {
            this.downstream.onNext(r);
        }

        @Override // io.reactivex.CompletableObserver, io.reactivex.MaybeObserver, io.reactivex.SingleObserver
        public void onError(Throwable th) {
            this.downstream.onError(th);
        }

        @Override // io.reactivex.CompletableObserver, io.reactivex.MaybeObserver
        public void onComplete() {
            ObservableSource<? extends R> observableSource = this.other;
            if (observableSource == null) {
                this.downstream.onComplete();
                return;
            }
            this.other = null;
            observableSource.subscribe(this);
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            DisposableHelper.dispose(this);
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return DisposableHelper.isDisposed(get());
        }

        @Override // io.reactivex.CompletableObserver, io.reactivex.MaybeObserver, io.reactivex.SingleObserver
        public void onSubscribe(Disposable disposable) {
            DisposableHelper.replace(this, disposable);
        }
    }
}
