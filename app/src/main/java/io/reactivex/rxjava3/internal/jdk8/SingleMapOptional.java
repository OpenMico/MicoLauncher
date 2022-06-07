package io.reactivex.rxjava3.internal.jdk8;

import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.MaybeObserver;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import java.util.Objects;
import java.util.Optional;

/* loaded from: classes4.dex */
public final class SingleMapOptional<T, R> extends Maybe<R> {
    final Single<T> a;
    final Function<? super T, Optional<? extends R>> b;

    public SingleMapOptional(Single<T> single, Function<? super T, Optional<? extends R>> function) {
        this.a = single;
        this.b = function;
    }

    @Override // io.reactivex.rxjava3.core.Maybe
    protected void subscribeActual(MaybeObserver<? super R> maybeObserver) {
        this.a.subscribe(new a(maybeObserver, this.b));
    }

    /* loaded from: classes4.dex */
    static final class a<T, R> implements SingleObserver<T>, Disposable {
        final MaybeObserver<? super R> a;
        final Function<? super T, Optional<? extends R>> b;
        Disposable c;

        a(MaybeObserver<? super R> maybeObserver, Function<? super T, Optional<? extends R>> function) {
            this.a = maybeObserver;
            this.b = function;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            Disposable disposable = this.c;
            this.c = DisposableHelper.DISPOSED;
            disposable.dispose();
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
                Optional optional = (Optional) Objects.requireNonNull(this.b.apply(t), "The mapper returned a null item");
                if (optional.isPresent()) {
                    this.a.onSuccess((Object) optional.get());
                } else {
                    this.a.onComplete();
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
