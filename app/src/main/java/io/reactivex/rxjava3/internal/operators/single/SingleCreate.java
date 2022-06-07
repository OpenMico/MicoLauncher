package io.reactivex.rxjava3.internal.operators.single;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleEmitter;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.core.SingleOnSubscribe;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Cancellable;
import io.reactivex.rxjava3.internal.disposables.CancellableDisposable;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.internal.util.ExceptionHelper;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes5.dex */
public final class SingleCreate<T> extends Single<T> {
    final SingleOnSubscribe<T> a;

    public SingleCreate(SingleOnSubscribe<T> singleOnSubscribe) {
        this.a = singleOnSubscribe;
    }

    @Override // io.reactivex.rxjava3.core.Single
    protected void subscribeActual(SingleObserver<? super T> singleObserver) {
        a aVar = new a(singleObserver);
        singleObserver.onSubscribe(aVar);
        try {
            this.a.subscribe(aVar);
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            aVar.onError(th);
        }
    }

    /* loaded from: classes5.dex */
    static final class a<T> extends AtomicReference<Disposable> implements SingleEmitter<T>, Disposable {
        private static final long serialVersionUID = -2467358622224974244L;
        final SingleObserver<? super T> downstream;

        a(SingleObserver<? super T> singleObserver) {
            this.downstream = singleObserver;
        }

        @Override // io.reactivex.rxjava3.core.SingleEmitter
        public void onSuccess(T t) {
            Disposable andSet;
            if (get() != DisposableHelper.DISPOSED && (andSet = getAndSet(DisposableHelper.DISPOSED)) != DisposableHelper.DISPOSED) {
                try {
                    if (t == null) {
                        this.downstream.onError(ExceptionHelper.createNullPointerException("onSuccess called with a null value."));
                    } else {
                        this.downstream.onSuccess(t);
                    }
                    if (andSet != null) {
                        andSet.dispose();
                    }
                } catch (Throwable th) {
                    if (andSet != null) {
                        andSet.dispose();
                    }
                    throw th;
                }
            }
        }

        @Override // io.reactivex.rxjava3.core.SingleEmitter
        public void onError(Throwable th) {
            if (!tryOnError(th)) {
                RxJavaPlugins.onError(th);
            }
        }

        @Override // io.reactivex.rxjava3.core.SingleEmitter
        public boolean tryOnError(Throwable th) {
            Disposable andSet;
            if (th == null) {
                th = ExceptionHelper.createNullPointerException("onError called with a null Throwable.");
            }
            if (get() == DisposableHelper.DISPOSED || (andSet = getAndSet(DisposableHelper.DISPOSED)) == DisposableHelper.DISPOSED) {
                return false;
            }
            try {
                this.downstream.onError(th);
            } finally {
                if (andSet != null) {
                    andSet.dispose();
                }
            }
        }

        @Override // io.reactivex.rxjava3.core.SingleEmitter
        public void setDisposable(Disposable disposable) {
            DisposableHelper.set(this, disposable);
        }

        @Override // io.reactivex.rxjava3.core.SingleEmitter
        public void setCancellable(Cancellable cancellable) {
            setDisposable(new CancellableDisposable(cancellable));
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            DisposableHelper.dispose(this);
        }

        @Override // io.reactivex.rxjava3.core.SingleEmitter, io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return DisposableHelper.isDisposed(get());
        }

        @Override // java.util.concurrent.atomic.AtomicReference
        public String toString() {
            return String.format("%s{%s}", getClass().getSimpleName(), super.toString());
        }
    }
}
