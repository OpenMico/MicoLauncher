package io.reactivex.rxjava3.internal.operators.single;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.core.SingleSource;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes5.dex */
public final class SingleFlatMap<T, R> extends Single<R> {
    final SingleSource<? extends T> a;
    final Function<? super T, ? extends SingleSource<? extends R>> b;

    public SingleFlatMap(SingleSource<? extends T> singleSource, Function<? super T, ? extends SingleSource<? extends R>> function) {
        this.b = function;
        this.a = singleSource;
    }

    @Override // io.reactivex.rxjava3.core.Single
    protected void subscribeActual(SingleObserver<? super R> singleObserver) {
        this.a.subscribe(new a(singleObserver, this.b));
    }

    /* loaded from: classes5.dex */
    static final class a<T, R> extends AtomicReference<Disposable> implements SingleObserver<T>, Disposable {
        private static final long serialVersionUID = 3258103020495908596L;
        final SingleObserver<? super R> downstream;
        final Function<? super T, ? extends SingleSource<? extends R>> mapper;

        a(SingleObserver<? super R> singleObserver, Function<? super T, ? extends SingleSource<? extends R>> function) {
            this.downstream = singleObserver;
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
                SingleSource singleSource = (SingleSource) Objects.requireNonNull(this.mapper.apply(t), "The single returned by the mapper is null");
                if (!isDisposed()) {
                    singleSource.subscribe(new C0339a(this, this.downstream));
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.downstream.onError(th);
            }
        }

        @Override // io.reactivex.rxjava3.core.SingleObserver
        public void onError(Throwable th) {
            this.downstream.onError(th);
        }

        /* renamed from: io.reactivex.rxjava3.internal.operators.single.SingleFlatMap$a$a  reason: collision with other inner class name */
        /* loaded from: classes5.dex */
        static final class C0339a<R> implements SingleObserver<R> {
            final AtomicReference<Disposable> a;
            final SingleObserver<? super R> b;

            C0339a(AtomicReference<Disposable> atomicReference, SingleObserver<? super R> singleObserver) {
                this.a = atomicReference;
                this.b = singleObserver;
            }

            @Override // io.reactivex.rxjava3.core.SingleObserver
            public void onSubscribe(Disposable disposable) {
                DisposableHelper.replace(this.a, disposable);
            }

            @Override // io.reactivex.rxjava3.core.SingleObserver
            public void onSuccess(R r) {
                this.b.onSuccess(r);
            }

            @Override // io.reactivex.rxjava3.core.SingleObserver
            public void onError(Throwable th) {
                this.b.onError(th);
            }
        }
    }
}
