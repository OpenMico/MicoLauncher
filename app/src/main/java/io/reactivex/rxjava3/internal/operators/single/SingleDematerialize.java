package io.reactivex.rxjava3.internal.operators.single;

import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.MaybeObserver;
import io.reactivex.rxjava3.core.Notification;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import java.util.Objects;

/* loaded from: classes5.dex */
public final class SingleDematerialize<T, R> extends Maybe<R> {
    final Single<T> a;
    final Function<? super T, Notification<R>> b;

    public SingleDematerialize(Single<T> single, Function<? super T, Notification<R>> function) {
        this.a = single;
        this.b = function;
    }

    @Override // io.reactivex.rxjava3.core.Maybe
    protected void subscribeActual(MaybeObserver<? super R> maybeObserver) {
        this.a.subscribe(new a(maybeObserver, this.b));
    }

    /* loaded from: classes5.dex */
    static final class a<T, R> implements SingleObserver<T>, Disposable {
        final MaybeObserver<? super R> a;
        final Function<? super T, Notification<R>> b;
        Disposable c;

        a(MaybeObserver<? super R> maybeObserver, Function<? super T, Notification<R>> function) {
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

        @Override // io.reactivex.rxjava3.core.SingleObserver
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.c, disposable)) {
                this.c = disposable;
                this.a.onSubscribe(this);
            }
        }

        @Override // io.reactivex.rxjava3.core.SingleObserver
        public void onSuccess(T t) {
            try {
                Notification notification = (Notification) Objects.requireNonNull(this.b.apply(t), "The selector returned a null Notification");
                if (notification.isOnNext()) {
                    this.a.onSuccess((Object) notification.getValue());
                } else if (notification.isOnComplete()) {
                    this.a.onComplete();
                } else {
                    this.a.onError(notification.getError());
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.a.onError(th);
            }
        }

        @Override // io.reactivex.rxjava3.core.SingleObserver
        public void onError(Throwable th) {
            this.a.onError(th);
        }
    }
}
