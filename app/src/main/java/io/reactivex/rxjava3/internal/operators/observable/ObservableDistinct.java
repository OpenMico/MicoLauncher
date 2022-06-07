package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.annotations.Nullable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.functions.Supplier;
import io.reactivex.rxjava3.internal.disposables.EmptyDisposable;
import io.reactivex.rxjava3.internal.observers.BasicFuseableObserver;
import io.reactivex.rxjava3.internal.util.ExceptionHelper;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.Collection;
import java.util.Objects;

/* loaded from: classes5.dex */
public final class ObservableDistinct<T, K> extends a<T, T> {
    final Function<? super T, K> a;
    final Supplier<? extends Collection<? super K>> b;

    public ObservableDistinct(ObservableSource<T> observableSource, Function<? super T, K> function, Supplier<? extends Collection<? super K>> supplier) {
        super(observableSource);
        this.a = function;
        this.b = supplier;
    }

    @Override // io.reactivex.rxjava3.core.Observable
    protected void subscribeActual(Observer<? super T> observer) {
        try {
            this.source.subscribe(new a(observer, this.a, (Collection) ExceptionHelper.nullCheck(this.b.get(), "The collectionSupplier returned a null Collection.")));
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            EmptyDisposable.error(th, observer);
        }
    }

    /* loaded from: classes5.dex */
    static final class a<T, K> extends BasicFuseableObserver<T, T> {
        final Collection<? super K> a;
        final Function<? super T, K> b;

        a(Observer<? super T> observer, Function<? super T, K> function, Collection<? super K> collection) {
            super(observer);
            this.b = function;
            this.a = collection;
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onNext(T t) {
            if (!this.done) {
                if (this.sourceMode == 0) {
                    try {
                        if (this.a.add(Objects.requireNonNull(this.b.apply(t), "The keySelector returned a null key"))) {
                            this.downstream.onNext(t);
                        }
                    } catch (Throwable th) {
                        fail(th);
                    }
                } else {
                    this.downstream.onNext(null);
                }
            }
        }

        @Override // io.reactivex.rxjava3.internal.observers.BasicFuseableObserver, io.reactivex.rxjava3.core.Observer
        public void onError(Throwable th) {
            if (this.done) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.done = true;
            this.a.clear();
            this.downstream.onError(th);
        }

        @Override // io.reactivex.rxjava3.internal.observers.BasicFuseableObserver, io.reactivex.rxjava3.core.Observer
        public void onComplete() {
            if (!this.done) {
                this.done = true;
                this.a.clear();
                this.downstream.onComplete();
            }
        }

        @Override // io.reactivex.rxjava3.internal.fuseable.QueueFuseable
        public int requestFusion(int i) {
            return transitiveBoundaryFusion(i);
        }

        @Override // io.reactivex.rxjava3.internal.fuseable.SimpleQueue
        @Nullable
        public T poll() throws Throwable {
            T poll;
            do {
                poll = this.qd.poll();
                if (poll == null) {
                    break;
                }
            } while (!this.a.add((Object) Objects.requireNonNull(this.b.apply(poll), "The keySelector returned a null key")));
            return poll;
        }

        @Override // io.reactivex.rxjava3.internal.observers.BasicFuseableObserver, io.reactivex.rxjava3.internal.fuseable.SimpleQueue
        public void clear() {
            this.a.clear();
            super.clear();
        }
    }
}
