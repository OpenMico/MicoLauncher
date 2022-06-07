package io.reactivex.rxjava3.internal.operators.single;

import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.MaybeObserver;
import io.reactivex.rxjava3.core.MaybeSource;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.core.SingleSource;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes5.dex */
public final class SingleFlatMapMaybe<T, R> extends Maybe<R> {
    final SingleSource<? extends T> a;
    final Function<? super T, ? extends MaybeSource<? extends R>> b;

    public SingleFlatMapMaybe(SingleSource<? extends T> singleSource, Function<? super T, ? extends MaybeSource<? extends R>> function) {
        this.b = function;
        this.a = singleSource;
    }

    @Override // io.reactivex.rxjava3.core.Maybe
    protected void subscribeActual(MaybeObserver<? super R> maybeObserver) {
        this.a.subscribe(new b(maybeObserver, this.b));
    }

    /* loaded from: classes5.dex */
    static final class b<T, R> extends AtomicReference<Disposable> implements SingleObserver<T>, Disposable {
        private static final long serialVersionUID = -5843758257109742742L;
        final MaybeObserver<? super R> downstream;
        final Function<? super T, ? extends MaybeSource<? extends R>> mapper;

        b(MaybeObserver<? super R> maybeObserver, Function<? super T, ? extends MaybeSource<? extends R>> function) {
            this.downstream = maybeObserver;
            this.mapper = function;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            DisposableHelper.dispose(this);
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return DisposableHelper.isDisposed(get());
        }

        @Override // io.reactivex.rxjava3.core.SingleObserver
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.setOnce(this, disposable)) {
                this.downstream.onSubscribe(this);
            }
        }

        @Override // io.reactivex.rxjava3.core.SingleObserver
        public void onSuccess(T t) {
            try {
                MaybeSource maybeSource = (MaybeSource) Objects.requireNonNull(this.mapper.apply(t), "The mapper returned a null MaybeSource");
                if (!isDisposed()) {
                    maybeSource.subscribe(new a(this, this.downstream));
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                onError(th);
            }
        }

        @Override // io.reactivex.rxjava3.core.SingleObserver
        public void onError(Throwable th) {
            this.downstream.onError(th);
        }
    }

    /* loaded from: classes5.dex */
    static final class a<R> implements MaybeObserver<R> {
        final AtomicReference<Disposable> a;
        final MaybeObserver<? super R> b;

        a(AtomicReference<Disposable> atomicReference, MaybeObserver<? super R> maybeObserver) {
            this.a = atomicReference;
            this.b = maybeObserver;
        }

        @Override // io.reactivex.rxjava3.core.MaybeObserver
        public void onSubscribe(Disposable disposable) {
            DisposableHelper.replace(this.a, disposable);
        }

        @Override // io.reactivex.rxjava3.core.MaybeObserver
        public void onSuccess(R r) {
            this.b.onSuccess(r);
        }

        @Override // io.reactivex.rxjava3.core.MaybeObserver
        public void onError(Throwable th) {
            this.b.onError(th);
        }

        @Override // io.reactivex.rxjava3.core.MaybeObserver
        public void onComplete() {
            this.b.onComplete();
        }
    }
}
