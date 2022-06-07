package io.reactivex.rxjava3.internal.operators.maybe;

import io.reactivex.rxjava3.core.MaybeObserver;
import io.reactivex.rxjava3.core.MaybeSource;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.CompositeException;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import java.util.Objects;

/* loaded from: classes5.dex */
public final class MaybeOnErrorReturn<T> extends a<T, T> {
    final Function<? super Throwable, ? extends T> a;

    public MaybeOnErrorReturn(MaybeSource<T> maybeSource, Function<? super Throwable, ? extends T> function) {
        super(maybeSource);
        this.a = function;
    }

    @Override // io.reactivex.rxjava3.core.Maybe
    protected void subscribeActual(MaybeObserver<? super T> maybeObserver) {
        this.source.subscribe(new a(maybeObserver, this.a));
    }

    /* loaded from: classes5.dex */
    static final class a<T> implements MaybeObserver<T>, Disposable {
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

        @Override // io.reactivex.rxjava3.core.MaybeObserver
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.c, disposable)) {
                this.c = disposable;
                this.a.onSubscribe(this);
            }
        }

        @Override // io.reactivex.rxjava3.core.MaybeObserver
        public void onSuccess(T t) {
            this.a.onSuccess(t);
        }

        @Override // io.reactivex.rxjava3.core.MaybeObserver
        public void onError(Throwable th) {
            try {
                this.a.onSuccess(Objects.requireNonNull(this.b.apply(th), "The itemSupplier returned a null value"));
            } catch (Throwable th2) {
                Exceptions.throwIfFatal(th2);
                this.a.onError(new CompositeException(th, th2));
            }
        }

        @Override // io.reactivex.rxjava3.core.MaybeObserver
        public void onComplete() {
            this.a.onComplete();
        }
    }
}
