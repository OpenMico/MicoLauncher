package io.reactivex.rxjava3.internal.operators.single;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.CompletableSource;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.core.SingleSource;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes5.dex */
public final class SingleFlatMapCompletable<T> extends Completable {
    final SingleSource<T> a;
    final Function<? super T, ? extends CompletableSource> b;

    public SingleFlatMapCompletable(SingleSource<T> singleSource, Function<? super T, ? extends CompletableSource> function) {
        this.a = singleSource;
        this.b = function;
    }

    @Override // io.reactivex.rxjava3.core.Completable
    protected void subscribeActual(CompletableObserver completableObserver) {
        a aVar = new a(completableObserver, this.b);
        completableObserver.onSubscribe(aVar);
        this.a.subscribe(aVar);
    }

    /* loaded from: classes5.dex */
    static final class a<T> extends AtomicReference<Disposable> implements CompletableObserver, SingleObserver<T>, Disposable {
        private static final long serialVersionUID = -2177128922851101253L;
        final CompletableObserver downstream;
        final Function<? super T, ? extends CompletableSource> mapper;

        a(CompletableObserver completableObserver, Function<? super T, ? extends CompletableSource> function) {
            this.downstream = completableObserver;
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

        @Override // io.reactivex.rxjava3.core.CompletableObserver
        public void onSubscribe(Disposable disposable) {
            DisposableHelper.replace(this, disposable);
        }

        @Override // io.reactivex.rxjava3.core.SingleObserver
        public void onSuccess(T t) {
            try {
                CompletableSource completableSource = (CompletableSource) Objects.requireNonNull(this.mapper.apply(t), "The mapper returned a null CompletableSource");
                if (!isDisposed()) {
                    completableSource.subscribe(this);
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                onError(th);
            }
        }

        @Override // io.reactivex.rxjava3.core.CompletableObserver
        public void onError(Throwable th) {
            this.downstream.onError(th);
        }

        @Override // io.reactivex.rxjava3.core.CompletableObserver
        public void onComplete() {
            this.downstream.onComplete();
        }
    }
}
