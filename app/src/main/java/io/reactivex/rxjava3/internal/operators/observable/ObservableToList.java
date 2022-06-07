package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Supplier;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.internal.disposables.EmptyDisposable;
import io.reactivex.rxjava3.internal.util.ExceptionHelper;
import java.util.Collection;

/* loaded from: classes5.dex */
public final class ObservableToList<T, U extends Collection<? super T>> extends a<T, U> {
    final Supplier<U> a;

    public ObservableToList(ObservableSource<T> observableSource, Supplier<U> supplier) {
        super(observableSource);
        this.a = supplier;
    }

    @Override // io.reactivex.rxjava3.core.Observable
    public void subscribeActual(Observer<? super U> observer) {
        try {
            this.source.subscribe(new a(observer, (Collection) ExceptionHelper.nullCheck(this.a.get(), "The collectionSupplier returned a null Collection.")));
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            EmptyDisposable.error(th, observer);
        }
    }

    /* loaded from: classes5.dex */
    static final class a<T, U extends Collection<? super T>> implements Observer<T>, Disposable {
        final Observer<? super U> a;
        Disposable b;
        U c;

        a(Observer<? super U> observer, U u) {
            this.a = observer;
            this.c = u;
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.b, disposable)) {
                this.b = disposable;
                this.a.onSubscribe(this);
            }
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            this.b.dispose();
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.b.isDisposed();
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onNext(T t) {
            this.c.add(t);
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onError(Throwable th) {
            this.c = null;
            this.a.onError(th);
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onComplete() {
            U u = this.c;
            this.c = null;
            this.a.onNext(u);
            this.a.onComplete();
        }
    }
}
