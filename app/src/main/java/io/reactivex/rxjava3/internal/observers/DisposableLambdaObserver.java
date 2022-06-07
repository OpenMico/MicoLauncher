package io.reactivex.rxjava3.internal.observers;

import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.internal.disposables.EmptyDisposable;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;

/* loaded from: classes4.dex */
public final class DisposableLambdaObserver<T> implements Observer<T>, Disposable {
    final Observer<? super T> a;
    final Consumer<? super Disposable> b;
    final Action c;
    Disposable d;

    public DisposableLambdaObserver(Observer<? super T> observer, Consumer<? super Disposable> consumer, Action action) {
        this.a = observer;
        this.b = consumer;
        this.c = action;
    }

    @Override // io.reactivex.rxjava3.core.Observer
    public void onSubscribe(Disposable disposable) {
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

    @Override // io.reactivex.rxjava3.core.Observer
    public void onNext(T t) {
        this.a.onNext(t);
    }

    @Override // io.reactivex.rxjava3.core.Observer
    public void onError(Throwable th) {
        if (this.d != DisposableHelper.DISPOSED) {
            this.d = DisposableHelper.DISPOSED;
            this.a.onError(th);
            return;
        }
        RxJavaPlugins.onError(th);
    }

    @Override // io.reactivex.rxjava3.core.Observer
    public void onComplete() {
        if (this.d != DisposableHelper.DISPOSED) {
            this.d = DisposableHelper.DISPOSED;
            this.a.onComplete();
        }
    }

    @Override // io.reactivex.rxjava3.disposables.Disposable
    public void dispose() {
        Disposable disposable = this.d;
        if (disposable != DisposableHelper.DISPOSED) {
            this.d = DisposableHelper.DISPOSED;
            try {
                this.c.run();
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                RxJavaPlugins.onError(th);
            }
            disposable.dispose();
        }
    }

    @Override // io.reactivex.rxjava3.disposables.Disposable
    public boolean isDisposed() {
        return this.d.isDisposed();
    }
}
