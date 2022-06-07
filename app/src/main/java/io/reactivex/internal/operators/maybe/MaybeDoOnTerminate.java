package io.reactivex.internal.operators.maybe;

import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Action;

/* loaded from: classes4.dex */
public final class MaybeDoOnTerminate<T> extends Maybe<T> {
    final MaybeSource<T> a;
    final Action b;

    public MaybeDoOnTerminate(MaybeSource<T> maybeSource, Action action) {
        this.a = maybeSource;
        this.b = action;
    }

    @Override // io.reactivex.Maybe
    protected void subscribeActual(MaybeObserver<? super T> maybeObserver) {
        this.a.subscribe(new a(maybeObserver));
    }

    /* loaded from: classes4.dex */
    final class a implements MaybeObserver<T> {
        final MaybeObserver<? super T> a;

        a(MaybeObserver<? super T> maybeObserver) {
            this.a = maybeObserver;
        }

        @Override // io.reactivex.MaybeObserver, io.reactivex.SingleObserver
        public void onSubscribe(Disposable disposable) {
            this.a.onSubscribe(disposable);
        }

        @Override // io.reactivex.MaybeObserver, io.reactivex.SingleObserver
        public void onSuccess(T t) {
            try {
                MaybeDoOnTerminate.this.b.run();
                this.a.onSuccess(t);
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.a.onError(th);
            }
        }

        @Override // io.reactivex.MaybeObserver, io.reactivex.SingleObserver
        public void onError(Throwable th) {
            try {
                MaybeDoOnTerminate.this.b.run();
            } catch (Throwable th2) {
                Exceptions.throwIfFatal(th2);
                th = new CompositeException(th, th2);
            }
            this.a.onError(th);
        }

        @Override // io.reactivex.MaybeObserver
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
