package io.reactivex.rxjava3.internal.operators.single;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.core.SingleSource;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;

/* loaded from: classes5.dex */
public final class SingleDetach<T> extends Single<T> {
    final SingleSource<T> a;

    public SingleDetach(SingleSource<T> singleSource) {
        this.a = singleSource;
    }

    @Override // io.reactivex.rxjava3.core.Single
    protected void subscribeActual(SingleObserver<? super T> singleObserver) {
        this.a.subscribe(new a(singleObserver));
    }

    /* loaded from: classes5.dex */
    static final class a<T> implements SingleObserver<T>, Disposable {
        SingleObserver<? super T> a;
        Disposable b;

        a(SingleObserver<? super T> singleObserver) {
            this.a = singleObserver;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            this.a = null;
            this.b.dispose();
            this.b = DisposableHelper.DISPOSED;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.b.isDisposed();
        }

        @Override // io.reactivex.rxjava3.core.SingleObserver
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.b, disposable)) {
                this.b = disposable;
                this.a.onSubscribe(this);
            }
        }

        @Override // io.reactivex.rxjava3.core.SingleObserver
        public void onSuccess(T t) {
            this.b = DisposableHelper.DISPOSED;
            SingleObserver<? super T> singleObserver = this.a;
            if (singleObserver != null) {
                this.a = null;
                singleObserver.onSuccess(t);
            }
        }

        @Override // io.reactivex.rxjava3.core.SingleObserver
        public void onError(Throwable th) {
            this.b = DisposableHelper.DISPOSED;
            SingleObserver<? super T> singleObserver = this.a;
            if (singleObserver != null) {
                this.a = null;
                singleObserver.onError(th);
            }
        }
    }
}
