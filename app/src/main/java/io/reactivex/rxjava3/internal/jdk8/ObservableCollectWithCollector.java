package io.reactivex.rxjava3.internal.jdk8;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.internal.disposables.EmptyDisposable;
import io.reactivex.rxjava3.internal.observers.DeferredScalarDisposable;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collector;

/* loaded from: classes4.dex */
public final class ObservableCollectWithCollector<T, A, R> extends Observable<R> {
    final Observable<T> a;
    final Collector<T, A, R> b;

    public ObservableCollectWithCollector(Observable<T> observable, Collector<T, A, R> collector) {
        this.a = observable;
        this.b = collector;
    }

    @Override // io.reactivex.rxjava3.core.Observable
    protected void subscribeActual(@NonNull Observer<? super R> observer) {
        try {
            this.a.subscribe(new a(observer, this.b.supplier().get(), this.b.accumulator(), this.b.finisher()));
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            EmptyDisposable.error(th, observer);
        }
    }

    /* loaded from: classes4.dex */
    static final class a<T, A, R> extends DeferredScalarDisposable<R> implements Observer<T> {
        private static final long serialVersionUID = -229544830565448758L;
        final BiConsumer<A, T> accumulator;
        A container;
        boolean done;
        final Function<A, R> finisher;
        Disposable upstream;

        a(Observer<? super R> observer, A a, BiConsumer<A, T> biConsumer, Function<A, R> function) {
            super(observer);
            this.container = a;
            this.accumulator = biConsumer;
            this.finisher = function;
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onSubscribe(@NonNull Disposable disposable) {
            if (DisposableHelper.validate(this.upstream, disposable)) {
                this.upstream = disposable;
                this.downstream.onSubscribe(this);
            }
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onNext(T t) {
            if (!this.done) {
                try {
                    this.accumulator.accept(this.container, t);
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    this.upstream.dispose();
                    onError(th);
                }
            }
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onError(Throwable th) {
            if (this.done) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.done = true;
            this.upstream = DisposableHelper.DISPOSED;
            this.container = null;
            this.downstream.onError(th);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // io.reactivex.rxjava3.core.Observer
        public void onComplete() {
            if (!this.done) {
                this.done = true;
                this.upstream = DisposableHelper.DISPOSED;
                A a = this.container;
                this.container = null;
                try {
                    complete(Objects.requireNonNull(this.finisher.apply(a), "The finisher returned a null value"));
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    this.downstream.onError(th);
                }
            }
        }

        @Override // io.reactivex.rxjava3.internal.observers.DeferredScalarDisposable, io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            super.dispose();
            this.upstream.dispose();
        }
    }
}
