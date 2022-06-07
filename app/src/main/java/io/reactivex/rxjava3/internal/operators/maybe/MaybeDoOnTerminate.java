package io.reactivex.rxjava3.internal.operators.maybe;

import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.MaybeObserver;
import io.reactivex.rxjava3.core.MaybeSource;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.CompositeException;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Action;

/* loaded from: classes5.dex */
public final class MaybeDoOnTerminate<T> extends Maybe<T> {
    final MaybeSource<T> a;
    final Action b;

    public MaybeDoOnTerminate(MaybeSource<T> maybeSource, Action action) {
        this.a = maybeSource;
        this.b = action;
    }

    @Override // io.reactivex.rxjava3.core.Maybe
    protected void subscribeActual(MaybeObserver<? super T> maybeObserver) {
        this.a.subscribe(new a(maybeObserver));
    }

    /* loaded from: classes5.dex */
    final class a implements MaybeObserver<T> {
        final MaybeObserver<? super T> a;

        a(MaybeObserver<? super T> maybeObserver) {
            this.a = maybeObserver;
        }

        @Override // io.reactivex.rxjava3.core.MaybeObserver
        public void onSubscribe(Disposable disposable) {
            this.a.onSubscribe(disposable);
        }

        @Override // io.reactivex.rxjava3.core.MaybeObserver
        public void onSuccess(T t) {
            try {
                MaybeDoOnTerminate.this.b.run();
                this.a.onSuccess(t);
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.a.onError(th);
            }
        }

        @Override // io.reactivex.rxjava3.core.MaybeObserver
        public void onError(Throwable th) {
            try {
                MaybeDoOnTerminate.this.b.run();
            } catch (Throwable th2) {
                Exceptions.throwIfFatal(th2);
                th = new CompositeException(th, th2);
            }
            this.a.onError(th);
        }

        @Override // io.reactivex.rxjava3.core.MaybeObserver
        public void onComplete() {
            try {
                MaybeDoOnTerminate.this.b.run();
                this.a.onComplete();
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.a.onError(th);
            }
        }
    }
}
