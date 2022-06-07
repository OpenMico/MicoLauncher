package io.reactivex.rxjava3.internal.observers;

import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.MaybeObserver;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.internal.util.BlockingHelper;
import io.reactivex.rxjava3.internal.util.ExceptionHelper;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public final class BlockingMultiObserver<T> extends CountDownLatch implements CompletableObserver, MaybeObserver<T>, SingleObserver<T> {
    T a;
    Throwable b;
    Disposable c;
    volatile boolean d;

    public BlockingMultiObserver() {
        super(1);
    }

    void a() {
        this.d = true;
        Disposable disposable = this.c;
        if (disposable != null) {
            disposable.dispose();
        }
    }

    @Override // io.reactivex.rxjava3.core.CompletableObserver
    public void onSubscribe(Disposable disposable) {
        this.c = disposable;
        if (this.d) {
            disposable.dispose();
        }
    }

    @Override // io.reactivex.rxjava3.core.MaybeObserver
    public void onSuccess(T t) {
        this.a = t;
        countDown();
    }

    @Override // io.reactivex.rxjava3.core.CompletableObserver
    public void onError(Throwable th) {
        this.b = th;
        countDown();
    }

    @Override // io.reactivex.rxjava3.core.CompletableObserver
    public void onComplete() {
        countDown();
    }

    public T blockingGet() {
        if (getCount() != 0) {
            try {
                BlockingHelper.verifyNonBlocking();
                await();
            } catch (InterruptedException e) {
                a();
                throw ExceptionHelper.wrapOrThrow(e);
            }
        }
        Throwable th = this.b;
        if (th == null) {
            return this.a;
        }
        throw ExceptionHelper.wrapOrThrow(th);
    }

    public T blockingGet(T t) {
        if (getCount() != 0) {
            try {
                BlockingHelper.verifyNonBlocking();
                await();
            } catch (InterruptedException e) {
                a();
                throw ExceptionHelper.wrapOrThrow(e);
            }
        }
        Throwable th = this.b;
        if (th == null) {
            T t2 = this.a;
            return t2 != null ? t2 : t;
        }
        throw ExceptionHelper.wrapOrThrow(th);
    }

    public boolean blockingAwait(long j, TimeUnit timeUnit) {
        if (getCount() != 0) {
            try {
                BlockingHelper.verifyNonBlocking();
                if (!await(j, timeUnit)) {
                    a();
                    return false;
                }
            } catch (InterruptedException e) {
                a();
                throw ExceptionHelper.wrapOrThrow(e);
            }
        }
        Throwable th = this.b;
        if (th == null) {
            return true;
        }
        throw ExceptionHelper.wrapOrThrow(th);
    }

    public void blockingConsume(Consumer<? super T> consumer, Consumer<? super Throwable> consumer2, Action action) {
        try {
            if (getCount() != 0) {
                try {
                    BlockingHelper.verifyNonBlocking();
                    await();
                } catch (InterruptedException e) {
                    a();
                    consumer2.accept(e);
                    return;
                }
            }
            Throwable th = this.b;
            if (th != null) {
                consumer2.accept(th);
                return;
            }
            Object obj = (T) this.a;
            if (obj != null) {
                consumer.accept(obj);
            } else {
                action.run();
            }
        } catch (Throwable th2) {
            Exceptions.throwIfFatal(th2);
            RxJavaPlugins.onError(th2);
        }
    }
}
