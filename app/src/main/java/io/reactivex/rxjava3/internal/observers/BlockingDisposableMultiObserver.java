package io.reactivex.rxjava3.internal.observers;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.MaybeObserver;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.internal.disposables.SequentialDisposable;
import io.reactivex.rxjava3.internal.util.BlockingHelper;
import java.util.concurrent.CountDownLatch;

/* loaded from: classes4.dex */
public final class BlockingDisposableMultiObserver<T> extends CountDownLatch implements CompletableObserver, MaybeObserver<T>, SingleObserver<T>, Disposable {
    T a;
    Throwable b;
    final SequentialDisposable c = new SequentialDisposable();

    public BlockingDisposableMultiObserver() {
        super(1);
    }

    @Override // io.reactivex.rxjava3.disposables.Disposable
    public void dispose() {
        this.c.dispose();
        countDown();
    }

    @Override // io.reactivex.rxjava3.disposables.Disposable
    public boolean isDisposed() {
        return this.c.isDisposed();
    }

    @Override // io.reactivex.rxjava3.core.CompletableObserver
    public void onSubscribe(@NonNull Disposable disposable) {
        DisposableHelper.setOnce(this.c, disposable);
    }

    @Override // io.reactivex.rxjava3.core.MaybeObserver
    public void onSuccess(@NonNull T t) {
        this.a = t;
        this.c.lazySet(Disposable.disposed());
        countDown();
    }

    @Override // io.reactivex.rxjava3.core.CompletableObserver
    public void onError(@NonNull Throwable th) {
        this.b = th;
        this.c.lazySet(Disposable.disposed());
        countDown();
    }

    @Override // io.reactivex.rxjava3.core.CompletableObserver
    public void onComplete() {
        this.c.lazySet(Disposable.disposed());
        countDown();
    }

    public void blockingConsume(CompletableObserver completableObserver) {
        if (getCount() != 0) {
            try {
                BlockingHelper.verifyNonBlocking();
                await();
            } catch (InterruptedException e) {
                dispose();
                completableObserver.onError(e);
                return;
            }
        }
        if (!isDisposed()) {
            Throwable th = this.b;
            if (th != null) {
                completableObserver.onError(th);
            } else {
                completableObserver.onComplete();
            }
        }
    }

    public void blockingConsume(SingleObserver<? super T> singleObserver) {
        if (getCount() != 0) {
            try {
                BlockingHelper.verifyNonBlocking();
                await();
            } catch (InterruptedException e) {
                dispose();
                singleObserver.onError(e);
                return;
            }
        }
        if (!isDisposed()) {
            Throwable th = this.b;
            if (th != null) {
                singleObserver.onError(th);
            } else {
                singleObserver.onSuccess((T) this.a);
            }
        }
    }

    public void blockingConsume(MaybeObserver<? super T> maybeObserver) {
        if (getCount() != 0) {
            try {
                BlockingHelper.verifyNonBlocking();
                await();
            } catch (InterruptedException e) {
                dispose();
                maybeObserver.onError(e);
                return;
            }
        }
        if (!isDisposed()) {
            Throwable th = this.b;
            if (th != null) {
                maybeObserver.onError(th);
                return;
            }
            Object obj = (T) this.a;
            if (obj == null) {
                maybeObserver.onComplete();
            } else {
                maybeObserver.onSuccess(obj);
            }
        }
    }
}
