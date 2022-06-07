package io.reactivex.rxjava3.internal.operators.single;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.core.SingleSource;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.BiFunction;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes5.dex */
public final class SingleFlatMapBiSelector<T, U, R> extends Single<R> {
    final SingleSource<T> a;
    final Function<? super T, ? extends SingleSource<? extends U>> b;
    final BiFunction<? super T, ? super U, ? extends R> c;

    public SingleFlatMapBiSelector(SingleSource<T> singleSource, Function<? super T, ? extends SingleSource<? extends U>> function, BiFunction<? super T, ? super U, ? extends R> biFunction) {
        this.a = singleSource;
        this.b = function;
        this.c = biFunction;
    }

    @Override // io.reactivex.rxjava3.core.Single
    protected void subscribeActual(SingleObserver<? super R> singleObserver) {
        this.a.subscribe(new a(singleObserver, this.b, this.c));
    }

    /* loaded from: classes5.dex */
    static final class a<T, U, R> implements SingleObserver<T>, Disposable {
        final Function<? super T, ? extends SingleSource<? extends U>> a;
        final C0340a<T, U, R> b;

        a(SingleObserver<? super R> singleObserver, Function<? super T, ? extends SingleSource<? extends U>> function, BiFunction<? super T, ? super U, ? extends R> biFunction) {
            this.b = new C0340a<>(singleObserver, biFunction);
            this.a = function;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            DisposableHelper.dispose(this.b);
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return DisposableHelper.isDisposed(this.b.get());
        }

        @Override // io.reactivex.rxjava3.core.SingleObserver
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.setOnce(this.b, disposable)) {
                this.b.downstream.onSubscribe(this);
            }
        }

        @Override // io.reactivex.rxjava3.core.SingleObserver
        public void onSuccess(T t) {
            try {
                SingleSource singleSource = (SingleSource) Objects.requireNonNull(this.a.apply(t), "The mapper returned a null MaybeSource");
                if (DisposableHelper.replace(this.b, null)) {
                    C0340a<T, U, R> aVar = this.b;
                    aVar.value = t;
                    singleSource.subscribe(aVar);
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.b.downstream.onError(th);
            }
        }

        @Override // io.reactivex.rxjava3.core.SingleObserver
        public void onError(Throwable th) {
            this.b.downstream.onError(th);
        }

        /* renamed from: io.reactivex.rxjava3.internal.operators.single.SingleFlatMapBiSelector$a$a  reason: collision with other inner class name */
        /* loaded from: classes5.dex */
        static final class C0340a<T, U, R> extends AtomicReference<Disposable> implements SingleObserver<U> {
            private static final long serialVersionUID = -2897979525538174559L;
            final SingleObserver<? super R> downstream;
            final BiFunction<? super T, ? super U, ? extends R> resultSelector;
            T value;

            C0340a(SingleObserver<? super R> singleObserver, BiFunction<? super T, ? super U, ? extends R> biFunction) {
                this.downstream = singleObserver;
                this.resultSelector = biFunction;
            }

            @Override // io.reactivex.rxjava3.core.SingleObserver
            public void onSubscribe(Disposable disposable) {
                DisposableHelper.setOnce(this, disposable);
            }

            @Override // io.reactivex.rxjava3.core.SingleObserver
            public void onSuccess(U u) {
                T t = this.value;
                this.value = null;
                try {
                    this.downstream.onSuccess(Objects.requireNonNull(this.resultSelector.apply(t, u), "The resultSelector returned a null value"));
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    this.downstream.onError(th);
                }
            }

            @Override // io.reactivex.rxjava3.core.SingleObserver
            public void onError(Throwable th) {
                this.downstream.onError(th);
            }
        }
    }
}
