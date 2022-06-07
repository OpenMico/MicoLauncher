package io.reactivex.internal.operators.observable;

import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;

/* loaded from: classes4.dex */
public final class ObservableLastMaybe<T> extends Maybe<T> {
    final ObservableSource<T> a;

    public ObservableLastMaybe(ObservableSource<T> observableSource) {
        this.a = observableSource;
    }

    @Override // io.reactivex.Maybe
    protected void subscribeActual(MaybeObserver<? super T> maybeObserver) {
        this.a.subscribe(new a(maybeObserver));
    }

    /* loaded from: classes4.dex */
    static final class a<T> implements Observer<T>, Disposable {
        final MaybeObserver<? super T> a;
        Disposable b;
        T c;

        a(MaybeObserver<? super T> maybeObserver) {
            this.a = maybeObserver;
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            this.b.dispose();
            this.b = DisposableHelper.DISPOSED;
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.b == DisposableHelper.DISPOSED;
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.b, disposable)) {
                this.b = disposable;
                this.a.onSubscribe(this);
            }
        }

        @Override // io.reactivex.Observer
        public void onNext(T t) {
            this.c = t;
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            this.b = DisposableHelper.DISPOSED;
            this.c = null;
            this.a.onError(th);
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            this.b = DisposableHelper.DISPOSED;
            T t = this.c;
            if (t != null) {
                this.c = null;
                this.a.onSuccess(t);
                return;
            }
            this.a.onComplete();
        }
    }
}
