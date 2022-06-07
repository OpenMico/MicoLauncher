package io.reactivex.internal.observers;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.util.BlockingHelper;
import io.reactivex.internal.util.ExceptionHelper;
import java.util.concurrent.CountDownLatch;

/* loaded from: classes4.dex */
public abstract class BlockingBaseObserver<T> extends CountDownLatch implements Observer<T>, Disposable {
    T a;
    Throwable b;
    Disposable c;
    volatile boolean d;

    public BlockingBaseObserver() {
        super(1);
    }

    @Override // io.reactivex.Observer
    public final void onSubscribe(Disposable disposable) {
        this.c = disposable;
        if (this.d) {
            disposable.dispose();
        }
    }

    @Override // io.reactivex.Observer
    public final void onComplete() {
        countDown();
    }

    @Override // io.reactivex.disposables.Disposable
    public final void dispose() {
        this.d = true;
        Disposable disposable = this.c;
        if (disposable != null) {
            disposable.dispose();
        }
    }

    @Override // io.reactivex.disposables.Disposable
    public final boolean isDisposed() {
        return this.d;
    }

    public final T blockingGet() {
        if (getCount() != 0) {
            try {
                BlockingHelper.verifyNonBlocking();
                await();
            } catch (InterruptedException e) {
                dispose();
                throw ExceptionHelper.wrapOrThrow(e);
            }
        }
        Throwable th = this.b;
        if (th == null) {
            return this.a;
        }
        throw ExceptionHelper.wrapOrThrow(th);
    }
}
