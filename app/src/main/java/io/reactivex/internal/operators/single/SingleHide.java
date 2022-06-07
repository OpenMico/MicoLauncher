package io.reactivex.internal.operators.single;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;

/* loaded from: classes4.dex */
public final class SingleHide<T> extends Single<T> {
    final SingleSource<? extends T> a;

    public SingleHide(SingleSource<? extends T> singleSource) {
        this.a = singleSource;
    }

    @Override // io.reactivex.Single
    protected void subscribeActual(SingleObserver<? super T> singleObserver) {
        this.a.subscribe(new a(singleObserver));
    }

    /* loaded from: classes4.dex */
    static final class a<T> implements SingleObserver<T>, Disposable {
        final SingleObserver<? super T> a;
        Disposable b;

        a(SingleObserver<? super T> singleObserver) {
            this.a = singleObserver;
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            this.b.dispose();
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.b.isDisposed();
        }

        @Override // io.reactivex.SingleObserver
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.b, disposable)) {
                this.b = disposable;
                this.a.onSubscribe(this);
            }
        }

        @Override // io.reactivex.SingleObserver
        public void onSuccess(T t) {
            this.a.onSuccess(t);
        }

        @Override // io.reactivex.SingleObserver
        public void onError(Throwable th) {
            this.a.onError(th);
        }
    }
}
