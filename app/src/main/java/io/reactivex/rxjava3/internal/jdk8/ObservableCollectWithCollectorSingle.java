package io.reactivex.rxjava3.internal.jdk8;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.internal.disposables.EmptyDisposable;
import io.reactivex.rxjava3.internal.fuseable.FuseToObservable;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collector;

/* loaded from: classes4.dex */
public final class ObservableCollectWithCollectorSingle<T, A, R> extends Single<R> implements FuseToObservable<R> {
    final Observable<T> a;
    final Collector<T, A, R> b;

    public ObservableCollectWithCollectorSingle(Observable<T> observable, Collector<T, A, R> collector) {
        this.a = observable;
        this.b = collector;
    }

    @Override // io.reactivex.rxjava3.internal.fuseable.FuseToObservable
    public Observable<R> fuseToObservable() {
        return new ObservableCollectWithCollector(this.a, this.b);
    }

    @Override // io.reactivex.rxjava3.core.Single
    protected void subscribeActual(@NonNull SingleObserver<? super R> singleObserver) {
        try {
            this.a.subscribe(new a(singleObserver, this.b.supplier().get(), this.b.accumulator(), this.b.finisher()));
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            EmptyDisposable.error(th, singleObserver);
        }
    }

    /* loaded from: classes4.dex */
    static final class a<T, A, R> implements Observer<T>, Disposable {
        final SingleObserver<? super R> a;
        final BiConsumer<A, T> b;
        final Function<A, R> c;
        Disposable d;
        boolean e;
        A f;

        a(SingleObserver<? super R> singleObserver, A a, BiConsumer<A, T> biConsumer, Function<A, R> function) {
            this.a = singleObserver;
            this.f = a;
            this.b = biConsumer;
            this.c = function;
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onSubscribe(@NonNull Disposable disposable) {
            if (DisposableHelper.validate(this.d, disposable)) {
                this.d = disposable;
                this.a.onSubscribe(this);
            }
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onNext(T t) {
            if (!this.e) {
                try {
                    this.b.accept(this.f, t);
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    this.d.dispose();
                    onError(th);
                }
            }
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onError(Throwable th) {
            if (this.e) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.e = true;
            this.d = DisposableHelper.DISPOSED;
            this.f = null;
            this.a.onError(th);
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onComplete() {
            if (!this.e) {
                this.e = true;
                this.d = DisposableHelper.DISPOSED;
                A a = this.f;
                this.f = null;
                try {
                    this.a.onSuccess(Objects.requireNonNull(this.c.apply(a), "The finisher returned a null value"));
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    this.a.onError(th);
                }
            }
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            this.d.dispose();
            this.d = DisposableHelper.DISPOSED;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.d == DisposableHelper.DISPOSED;
        }
    }
}
