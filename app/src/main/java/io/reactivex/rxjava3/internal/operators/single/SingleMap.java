package io.reactivex.rxjava3.internal.operators.single;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.core.SingleSource;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Function;
import java.util.Objects;

/* loaded from: classes5.dex */
public final class SingleMap<T, R> extends Single<R> {
    final SingleSource<? extends T> a;
    final Function<? super T, ? extends R> b;

    public SingleMap(SingleSource<? extends T> singleSource, Function<? super T, ? extends R> function) {
        this.a = singleSource;
        this.b = function;
    }

    @Override // io.reactivex.rxjava3.core.Single
    protected void subscribeActual(SingleObserver<? super R> singleObserver) {
        this.a.subscribe(new a(singleObserver, this.b));
    }

    /* loaded from: classes5.dex */
    static final class a<T, R> implements SingleObserver<T> {
        final SingleObserver<? super R> a;
        final Function<? super T, ? extends R> b;

        /* JADX INFO: Access modifiers changed from: package-private */
        public a(SingleObserver<? super R> singleObserver, Function<? super T, ? extends R> function) {
            this.a = singleObserver;
            this.b = function;
        }

        @Override // io.reactivex.rxjava3.core.SingleObserver
        public void onSubscribe(Disposable disposable) {
            this.a.onSubscribe(disposable);
        }

        @Override // io.reactivex.rxjava3.core.SingleObserver
        public void onSuccess(T t) {
            try {
                this.a.onSuccess(Objects.requireNonNull(this.b.apply(t), "The mapper function returned a null value."));
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                onError(th);
            }
        }

        @Override // io.reactivex.rxjava3.core.SingleObserver
        public void onError(Throwable th) {
            this.a.onError(th);
        }
    }
}
