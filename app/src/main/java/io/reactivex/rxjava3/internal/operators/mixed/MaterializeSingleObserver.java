package io.reactivex.rxjava3.internal.operators.mixed;

import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.MaybeObserver;
import io.reactivex.rxjava3.core.Notification;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;

/* loaded from: classes5.dex */
public final class MaterializeSingleObserver<T> implements CompletableObserver, MaybeObserver<T>, SingleObserver<T>, Disposable {
    final SingleObserver<? super Notification<T>> a;
    Disposable b;

    public MaterializeSingleObserver(SingleObserver<? super Notification<T>> singleObserver) {
        this.a = singleObserver;
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
        this.a.onSuccess(Notification.createOnComplete());
    }

    @Override // io.reactivex.rxjava3.core.MaybeObserver
    public void onSuccess(T t) {
        this.a.onSuccess(Notification.createOnNext(t));
    }

    @Override // io.reactivex.rxjava3.core.CompletableObserver
    public void onError(Throwable th) {
        this.a.onSuccess(Notification.createOnError(th));
    }

    @Override // io.reactivex.rxjava3.disposables.Disposable
    public boolean isDisposed() {
        return this.b.isDisposed();
    }

    @Override // io.reactivex.rxjava3.disposables.Disposable
    public void dispose() {
        this.b.dispose();
    }
}
