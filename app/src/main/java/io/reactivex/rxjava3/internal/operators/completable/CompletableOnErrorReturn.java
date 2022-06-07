package io.reactivex.rxjava3.internal.operators.completable;

import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.CompletableSource;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.MaybeObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.CompositeException;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import java.util.Objects;

/* loaded from: classes4.dex */
public final class CompletableOnErrorReturn<T> extends Maybe<T> {
    final CompletableSource a;
    final Function<? super Throwable, ? extends T> b;

    public CompletableOnErrorReturn(CompletableSource completableSource, Function<? super Throwable, ? extends T> function) {
        this.a = completableSource;
        this.b = function;
    }

    @Override // io.reactivex.rxjava3.core.Maybe
    protected void subscribeActual(MaybeObserver<? super T> maybeObserver) {
        this.a.subscribe(new a(maybeObserver, this.b));
    }

    /* loaded from: classes4.dex */
    static final class a<T> implements CompletableObserver, Disposable {
        final MaybeObserver<? super T> a;
        final Function<? super Throwable, ? extends T> b;
        Disposable c;

        a(MaybeObserver<? super T> maybeObserver, Function<? super Throwable, ? extends T> function) {
            this.a = maybeObserver;
            this.b = function;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            this.c.dispose();
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.c.isDisposed();
        }

        @Override // io.reactivex.rxjava3.core.CompletableObserver
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.c, disposable)) {
                this.c = disposable;
                this.a.onSubscribe(this);
            }
        }

        @Override // io.reactivex.rxjava3.core.CompletableObserver
        public void onError(Throwable th) {
            try {
                this.a.onSuccess(Objects.requireNonNull(this.b.apply(th), "The itemSupplier returned a null value"));
            } catch (Throwable th2) {
                Exceptions.throwIfFatal(th2);
                this.a.onError(new CompositeException(th, th2));
            }
        }

        @Override // io.reactivex.rxjava3.core.CompletableObserver
        public void onComplete() {
            this.a.onComplete();
        }
    }
}
