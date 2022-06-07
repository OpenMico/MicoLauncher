package io.reactivex.rxjava3.internal.operators.single;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.internal.disposables.EmptyDisposable;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;

/* loaded from: classes5.dex */
public final class SingleDoOnLifecycle<T> extends Single<T> {
    final Single<T> a;
    final Consumer<? super Disposable> b;
    final Action c;

    public SingleDoOnLifecycle(Single<T> single, Consumer<? super Disposable> consumer, Action action) {
        this.a = single;
        this.b = consumer;
        this.c = action;
    }

    @Override // io.reactivex.rxjava3.core.Single
    protected void subscribeActual(SingleObserver<? super T> singleObserver) {
        this.a.subscribe(new a(singleObserver, this.b, this.c));
    }

    /* loaded from: classes5.dex */
    static final class a<T> implements SingleObserver<T>, Disposable {
        final SingleObserver<? super T> a;
        final Consumer<? super Disposable> b;
        final Action c;
        Disposable d;

        a(SingleObserver<? super T> singleObserver, Consumer<? super Disposable> consumer, Action action) {
            this.a = singleObserver;
            this.b = consumer;
            this.c = action;
        }

        @Override // io.reactivex.rxjava3.core.SingleObserver
        public void onSubscribe(@NonNull Disposable disposable) {
            try {
                this.b.accept(disposable);
                if (DisposableHelper.validate(this.d, disposable)) {
                    this.d = disposable;
                    this.a.onSubscribe(this);
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                disposable.dispose();
                this.d = DisposableHelper.DISPOSED;
                EmptyDisposable.error(th, this.a);
            }
        }

        @Override // io.reactivex.rxjava3.core.SingleObserver
        public void onSuccess(@NonNull T t) {
            if (this.d != DisposableHelper.DISPOSED) {
                this.d = DisposableHelper.DISPOSED;
                this.a.onSuccess(t);
            }
        }

        @Override // io.reactivex.rxjava3.core.SingleObserver
        public void onError(@NonNull Throwable th) {
            if (this.d != DisposableHelper.DISPOSED) {
                this.d = DisposableHelper.DISPOSED;
                this.a.onError(th);
                return;
            }
            RxJavaPlugins.onError(th);
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            try {
                this.c.run();
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                RxJavaPlugins.onError(th);
            }
            this.d.dispose();
            this.d = DisposableHelper.DISPOSED;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.d.isDisposed();
        }
    }
}
