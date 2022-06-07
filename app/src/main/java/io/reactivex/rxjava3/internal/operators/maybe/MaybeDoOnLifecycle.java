package io.reactivex.rxjava3.internal.operators.maybe;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.MaybeObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.internal.disposables.EmptyDisposable;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;

/* loaded from: classes5.dex */
public final class MaybeDoOnLifecycle<T> extends a<T, T> {
    final Consumer<? super Disposable> a;
    final Action b;

    public MaybeDoOnLifecycle(Maybe<T> maybe, Consumer<? super Disposable> consumer, Action action) {
        super(maybe);
        this.a = consumer;
        this.b = action;
    }

    @Override // io.reactivex.rxjava3.core.Maybe
    protected void subscribeActual(MaybeObserver<? super T> maybeObserver) {
        this.source.subscribe(new a(maybeObserver, this.a, this.b));
    }

    /* loaded from: classes5.dex */
    static final class a<T> implements MaybeObserver<T>, Disposable {
        final MaybeObserver<? super T> a;
        final Consumer<? super Disposable> b;
        final Action c;
        Disposable d;

        a(MaybeObserver<? super T> maybeObserver, Consumer<? super Disposable> consumer, Action action) {
            this.a = maybeObserver;
            this.b = consumer;
            this.c = action;
        }

        @Override // io.reactivex.rxjava3.core.MaybeObserver
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

        @Override // io.reactivex.rxjava3.core.MaybeObserver
        public void onSuccess(@NonNull T t) {
            if (this.d != DisposableHelper.DISPOSED) {
                this.d = DisposableHelper.DISPOSED;
                this.a.onSuccess(t);
            }
        }

        @Override // io.reactivex.rxjava3.core.MaybeObserver
        public void onError(@NonNull Throwable th) {
            if (this.d != DisposableHelper.DISPOSED) {
                this.d = DisposableHelper.DISPOSED;
                this.a.onError(th);
                return;
            }
            RxJavaPlugins.onError(th);
        }

        @Override // io.reactivex.rxjava3.core.MaybeObserver
        public void onComplete() {
            if (this.d != DisposableHelper.DISPOSED) {
                this.d = DisposableHelper.DISPOSED;
                this.a.onComplete();
            }
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
