package io.reactivex.internal.operators.single;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Action;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.plugins.RxJavaPlugins;

/* loaded from: classes4.dex */
public final class SingleDoAfterTerminate<T> extends Single<T> {
    final SingleSource<T> a;
    final Action b;

    public SingleDoAfterTerminate(SingleSource<T> singleSource, Action action) {
        this.a = singleSource;
        this.b = action;
    }

    @Override // io.reactivex.Single
    protected void subscribeActual(SingleObserver<? super T> singleObserver) {
        this.a.subscribe(new a(singleObserver, this.b));
    }

    /* loaded from: classes4.dex */
    static final class a<T> implements SingleObserver<T>, Disposable {
        final SingleObserver<? super T> a;
        final Action b;
        Disposable c;

        a(SingleObserver<? super T> singleObserver, Action action) {
            this.a = singleObserver;
            this.b = action;
        }

        @Override // io.reactivex.SingleObserver
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.c, disposable)) {
                this.c = disposable;
                this.a.onSubscribe(this);
            }
        }

        @Override // io.reactivex.SingleObserver
        public void onSuccess(T t) {
            this.a.onSuccess(t);
            a();
        }

        @Override // io.reactivex.SingleObserver
        public void onError(Throwable th) {
            this.a.onError(th);
            a();
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            this.c.dispose();
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.c.isDisposed();
        }

        private void a() {
            try {
                this.b.run();
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                RxJavaPlugins.onError(th);
            }
        }
    }
}
