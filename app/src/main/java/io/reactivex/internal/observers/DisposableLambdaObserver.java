package io.reactivex.internal.observers;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.plugins.RxJavaPlugins;

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

    @Override // io.reactivex.Observer
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

    @Override // io.reactivex.Observer
    public void onNext(T t) {
        this.a.onNext(t);
    }

    @Override // io.reactivex.Observer
    public void onError(Throwable th) {
        if (this.d != DisposableHelper.DISPOSED) {
            this.d = DisposableHelper.DISPOSED;
            this.a.onError(th);
            return;
        }
        RxJavaPlugins.onError(th);
    }

    @Override // io.reactivex.Observer
    public void onComplete() {
        if (this.d != DisposableHelper.DISPOSED) {
            this.d = DisposableHelper.DISPOSED;
            this.a.onComplete();
        }
    }

    @Override // io.reactivex.disposables.Disposable
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

    @Override // io.reactivex.disposables.Disposable
    public boolean isDisposed() {
        return this.d.isDisposed();
    }
}
