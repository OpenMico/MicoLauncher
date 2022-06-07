package io.reactivex.internal.operators.maybe;

import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes4.dex */
public final class MaybeFlatMapBiSelector<T, U, R> extends a<T, R> {
    final Function<? super T, ? extends MaybeSource<? extends U>> a;
    final BiFunction<? super T, ? super U, ? extends R> b;

    public MaybeFlatMapBiSelector(MaybeSource<T> maybeSource, Function<? super T, ? extends MaybeSource<? extends U>> function, BiFunction<? super T, ? super U, ? extends R> biFunction) {
        super(maybeSource);
        this.a = function;
        this.b = biFunction;
    }

    @Override // io.reactivex.Maybe
    protected void subscribeActual(MaybeObserver<? super R> maybeObserver) {
        this.source.subscribe(new a(maybeObserver, this.a, this.b));
    }

    /* loaded from: classes4.dex */
    static final class a<T, U, R> implements MaybeObserver<T>, Disposable {
        final Function<? super T, ? extends MaybeSource<? extends U>> a;
        final C0233a<T, U, R> b;

        a(MaybeObserver<? super R> maybeObserver, Function<? super T, ? extends MaybeSource<? extends U>> function, BiFunction<? super T, ? super U, ? extends R> biFunction) {
            this.b = new C0233a<>(maybeObserver, biFunction);
            this.a = function;
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            DisposableHelper.dispose(this.b);
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return DisposableHelper.isDisposed(this.b.get());
        }

        @Override // io.reactivex.MaybeObserver, io.reactivex.SingleObserver
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.setOnce(this.b, disposable)) {
                this.b.downstream.onSubscribe(this);
            }
        }

        @Override // io.reactivex.MaybeObserver, io.reactivex.SingleObserver
        public void onSuccess(T t) {
            try {
                MaybeSource maybeSource = (MaybeSource) ObjectHelper.requireNonNull(this.a.apply(t), "The mapper returned a null MaybeSource");
                if (DisposableHelper.replace(this.b, null)) {
                    C0233a<T, U, R> aVar = this.b;
                    aVar.value = t;
                    maybeSource.subscribe(aVar);
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.b.downstream.onError(th);
            }
        }

        @Override // io.reactivex.MaybeObserver, io.reactivex.SingleObserver
        public void onError(Throwable th) {
            this.b.downstream.onError(th);
        }

        @Override // io.reactivex.MaybeObserver
        public void onComplete() {
            this.b.downstream.onComplete();
        }

        /* renamed from: io.reactivex.internal.operators.maybe.MaybeFlatMapBiSelector$a$a  reason: collision with other inner class name */
        /* loaded from: classes4.dex */
        static final class C0233a<T, U, R> extends AtomicReference<Disposable> implements MaybeObserver<U> {
            private static final long serialVersionUID = -2897979525538174559L;
            final MaybeObserver<? super R> downstream;
            final BiFunction<? super T, ? super U, ? extends R> resultSelector;
            T value;

            C0233a(MaybeObserver<? super R> maybeObserver, BiFunction<? super T, ? super U, ? extends R> biFunction) {
                this.downstream = maybeObserver;
                this.resultSelector = biFunction;
            }

            @Override // io.reactivex.MaybeObserver, io.reactivex.SingleObserver
            public void onSubscribe(Disposable disposable) {
                DisposableHelper.setOnce(this, disposable);
            }

            @Override // io.reactivex.MaybeObserver, io.reactivex.SingleObserver
            public void onSuccess(U u) {
                T t = this.value;
                this.value = null;
                try {
                    this.downstream.onSuccess(ObjectHelper.requireNonNull(this.resultSelector.apply(t, u), "The resultSelector returned a null value"));
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    this.downstream.onError(th);
                }
            }

            @Override // io.reactivex.MaybeObserver, io.reactivex.SingleObserver
            public void onError(Throwable th) {
                this.downstream.onError(th);
            }

            @Override // io.reactivex.MaybeObserver
            public void onComplete() {
                this.downstream.onComplete();
            }
        }
    }
}
